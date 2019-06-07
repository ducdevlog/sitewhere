/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.communication.mqtt;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.fusesource.hawtdispatch.Dispatch;
import org.fusesource.hawtdispatch.DispatchQueue;
import org.fusesource.mqtt.client.Future;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import com.sitewhere.server.lifecycle.TenantEngineLifecycleComponent;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.server.lifecycle.ILifecycleProgressMonitor;
import com.sitewhere.spi.server.lifecycle.LifecycleComponentType;

/**
 * Extends {@link TenantEngineLifecycleComponent} with base functionality for
 * connecting to MQTT.
 *
 * @author Derek
 */
public class MqttLifecycleComponent extends TenantEngineLifecycleComponent implements IMqttComponent {

    /**
     * Default protocol if not set via Spring
     */
    public static final String DEFAULT_PROTOCOL = "tcp";

    /**
     * Default hostname if not set via Spring
     */
    public static final String DEFAULT_HOSTNAME = "localhost";

    /**
     * Default port if not set from Spring
     */
    public static final String DEFAULT_PORT = "1883";

    /**
     * Default connection timeout in seconds
     */
    public static final long DEFAULT_CONNECT_TIMEOUT_SECS = 5;

    private String protocol = DEFAULT_PROTOCOL;

    /**
     * Host name
     */
    private String hostname = DEFAULT_HOSTNAME;

    /**
     * Port
     */
    private String port = DEFAULT_PORT;

    /**
     * TrustStore path
     */
    private String trustStorePath;

    /**
     * TrustStore password
     */
    private String trustStorePassword;

    /**
     * KeyStore path
     */
    private String keyStorePath;

    /**
     * KeyStore password
     */
    private String keyStorePassword;

    /**
     * Username
     */
    private String username;

    /**
     * Password
     */
    private String password;

    /**
     * Client id
     */
    private String clientId;

    /**
     * Clean session flag
     */
    private boolean cleanSession = true;

    /**
     * Quality of service
     */
    private String qos = QoS.AT_LEAST_ONCE.name();

    /**
     * MQTT client
     */
    private MQTT mqtt;

    /**
     * Hawtdispatch queue
     */
    private DispatchQueue queue;

    public MqttLifecycleComponent(LifecycleComponentType type) {
        super(type);
    }

    /**
     * Gets information about the broker.
     *
     * @return
     * @throws SiteWhereException
     */
    public String getBrokerInfo() throws SiteWhereException {
        return mqtt.getHost().toString();
    }

    /**
     * Get a {@link FutureConnection} to the MQTT broker.
     *
     * @return
     * @throws SiteWhereException
     */
    public FutureConnection getConnection() throws SiteWhereException {
        FutureConnection connection = mqtt.futureConnection();
        try {
            Future<Void> future = connection.connect();
            future.await(DEFAULT_CONNECT_TIMEOUT_SECS, TimeUnit.SECONDS);
            return connection;
        } catch (Exception e) {
            throw new SiteWhereException("Unable to connect to MQTT broker.", e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.sitewhere.server.lifecycle.LifecycleComponent#start(com.sitewhere.spi
     * .server.lifecycle.ILifecycleProgressMonitor)
     */
    @Override
    public void start(ILifecycleProgressMonitor monitor) throws SiteWhereException {
        this.queue = Dispatch.createQueue(getComponentId().toString());
        this.mqtt = MqttLifecycleComponent.configure(this, queue);
    }

    /**
     * Configure trust store.
     *
     * @param sslContext
     * @param trustStorePath
     * @param trustStorePassword
     * @throws Exception
     */
    protected static TrustManagerFactory configureTrustStore(IMqttComponent component, SSLContext sslContext,
                                                             String trustStorePath, String trustStorePassword) throws Exception {
        component.getLogger().info("MQTT client using truststore path: " + trustStorePath);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyStore tks = KeyStore.getInstance("JKS");
        File trustFile = new File(trustStorePath);
        tks.load(new FileInputStream(trustFile), trustStorePassword.toCharArray());
        tmf.init(tks);
        return tmf;
    }

    /**
     * Configure key store.
     *
     * @param component
     * @param sslContext
     * @param keyStorePath
     * @param keyStorePassword
     * @return
     * @throws Exception
     */
    protected static KeyManagerFactory configureKeyStore(IMqttComponent component, SSLContext sslContext,
                                                         String keyStorePath, String keyStorePassword) throws Exception {
        component.getLogger().info("MQTT client using keystore path: " + keyStorePath);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        KeyStore ks = KeyStore.getInstance("JKS");
        File keyFile = new File(keyStorePath);
        ks.load(new FileInputStream(keyFile), keyStorePassword.toCharArray());
        kmf.init(ks, keyStorePassword.toCharArray());
        return kmf;
    }

    /**
     * Handle configuration of secure transport.
     *
     * @param component
     * @param mqtt
     * @throws SiteWhereException
     */
    protected static void handleSecureTransport(IMqttComponent component, MQTT mqtt) throws SiteWhereException {
        // import certificate from resource
        SSLContext sslContext = null;
        try {
            sslContext = getSSLContext("/certificate/ca.crt", "/certificate/client.crt", "/certificate/client.key", "");
        } catch (Exception e) {
            component.getLogger().info("error load certificate from resource");
            e.printStackTrace();
        }

        component.getLogger().info("MQTT client using secure protocol '" + component.getProtocol() + "'.");
        boolean trustStoreConfigured = (component.getTrustStorePath() != null)
                && (component.getTrustStorePassword() != null);
        boolean keyStoreConfigured = (component.getKeyStorePath() != null) && (component.getKeyStorePassword() != null);
        try {
            TrustManagerFactory tmf = null;
            KeyManagerFactory kmf;

            // Handle trust store configuration.
            if (trustStoreConfigured) {
                tmf = configureTrustStore(component, sslContext, component.getTrustStorePath(),
                        component.getTrustStorePassword());
            } else {
                component.getLogger().info("No trust store configured for MQTT client.");
            }

            // Handle key store configuration.
            if (keyStoreConfigured) {
                kmf = configureKeyStore(component, sslContext, component.getKeyStorePath(),
                        component.getKeyStorePassword());
                sslContext.init(kmf.getKeyManagers(), tmf != null ? tmf.getTrustManagers() : null, null);
            } else if (trustStoreConfigured) {
                sslContext.init(null, tmf != null ? tmf.getTrustManagers() : null, null);
            }

        } catch (Throwable t) {
            throw new SiteWhereException("Unable to configure secure transport.", t);
        }
        mqtt.setSslContext(sslContext);
        component.getLogger().info("Created SSL context for MQTT receiver.");
    }

    private static SSLContext getSSLContext(final String caCrtFile,
                                            final String crtFile, final String keyFile, final String password)
            throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // load CA certificate
        X509Certificate caCert = null;

        BufferedInputStream bis;
        try (FileInputStream fis = new FileInputStream(new ClassPathResource(caCrtFile, MqttLifecycleComponent.class.getClassLoader()).getFile())) {
            bis = new BufferedInputStream(fis);
        }
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        while (bis.available() > 0) {
            caCert = (X509Certificate) cf.generateCertificate(bis);
            // System.out.println(caCert.toString());
        }

        // load client certificate
        bis = new BufferedInputStream(new FileInputStream(new ClassPathResource(crtFile, MqttLifecycleComponent.class.getClassLoader()).getFile()));
        X509Certificate cert = null;
        while (bis.available() > 0) {
            cert = (X509Certificate) cf.generateCertificate(bis);
            // System.out.println(caCert.toString());
        }

        // load client private key
        PEMParser pemParser = new PEMParser(new FileReader(new ClassPathResource(keyFile, MqttLifecycleComponent.class.getClassLoader()).getFile()));
        Object object = pemParser.readObject();
        PEMDecryptorProvider decProv = new JcePEMDecryptorProviderBuilder()
                .build(password.toCharArray());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter()
                .setProvider("BC");
        KeyPair key;
        if (object instanceof PEMEncryptedKeyPair) {
            System.out.println("Encrypted key - we will use provided password");
            key = converter.getKeyPair(((PEMEncryptedKeyPair) object)
                    .decryptKeyPair(decProv));
        } else {
            System.out.println("Unencrypted key - no password needed");
            key = converter.getKeyPair((PEMKeyPair) object);
        }
        pemParser.close();

        // CA certificate is used to authenticate server
        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        caKs.setCertificateEntry("ca-certificate", caCert);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
        tmf.init(caKs);

        // client key and certificates are sent to server so it can authenticate
        // us
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry("certificate", cert);
        ks.setKeyEntry("private-key", key.getPrivate(), password.toCharArray(),
                new java.security.cert.Certificate[] { cert });
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory
                .getDefaultAlgorithm());
        kmf.init(ks, password.toCharArray());

        // finally, create SSL socket factory
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return context;
    }

    /**
     * Configures MQTT parameters based on component settings.
     *
     * @param component
     * @return
     * @throws SiteWhereException
     */
    public static MQTT configure(IMqttComponent component, DispatchQueue queue) throws SiteWhereException {

        MQTT mqtt = new MQTT();

        boolean usingSSL = component.getProtocol().startsWith("ssl");
        boolean usingTLS = component.getProtocol().startsWith("tls");

        // Optionally set client id.
        if (component.getClientId() != null) {
            mqtt.setClientId(component.getClientId());
            component.getLogger().info("MQTT connection will use client id '" + component.getClientId() + "'.");
        }
        // Set flag for clean session.
        mqtt.setCleanSession(component.isCleanSession());
        component.getLogger().info("MQTT clean session flag being set to '" + component.isCleanSession() + "'.");

        if (usingSSL || usingTLS) {
            handleSecureTransport(component, mqtt);
        }
        // Set username if provided.
        if (!StringUtils.isEmpty(component.getUsername())) {
            mqtt.setUserName(component.getUsername());
        }
        // Set password if provided.
        if (!StringUtils.isEmpty(component.getPassword())) {
            mqtt.setPassword(component.getPassword());
        }
        try {
            mqtt.setHost(component.getProtocol() + "://" + component.getHostname() + ":" + component.getPort());
            return mqtt;
        } catch (URISyntaxException e) {
            throw new SiteWhereException("Invalid hostname for MQTT server.", e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.sitewhere.server.lifecycle.LifecycleComponent#stop(com.sitewhere.spi.
     * server.lifecycle.ILifecycleProgressMonitor)
     */
    @Override
    public void stop(ILifecycleProgressMonitor monitor) throws SiteWhereException {
        if (queue != null) {
            queue.suspend();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.device.communication.mqtt.IMqttComponent#getProtocol()
     */
    @Override
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.device.communication.mqtt.IMqttComponent#getHostname()
     */
    @Override
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.device.communication.mqtt.IMqttComponent#getPort()
     */
    @Override
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.device.communication.mqtt.IMqttComponent#getUsername()
     */
    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.device.communication.mqtt.IMqttComponent#getPassword()
     */
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.sitewhere.device.communication.mqtt.IMqttComponent#getTrustStorePath( )
     */
    @Override
    public String getTrustStorePath() {
        return trustStorePath;
    }

    public void setTrustStorePath(String trustStorePath) {
        this.trustStorePath = trustStorePath;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.device.communication.mqtt.IMqttComponent#
     * getTrustStorePassword()
     */
    @Override
    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.device.communication.mqtt.IMqttComponent#getKeyStorePath()
     */
    public String getKeyStorePath() {
        return keyStorePath;
    }

    public void setKeyStorePath(String keyStorePath) {
        this.keyStorePath = keyStorePath;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.device.communication.mqtt.IMqttComponent#
     * getKeyStorePassword()
     */
    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.device.communication.mqtt.IMqttComponent#getClientId()
     */
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.device.communication.mqtt.IMqttComponent#isCleanSession()
     */
    public boolean isCleanSession() {
        return cleanSession;
    }

    public void setCleanSession(boolean cleanSession) {
        this.cleanSession = cleanSession;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sitewhere.device.communication.mqtt.IMqttComponent#getQos()
     */
    public String getQos() {
        return qos;
    }

    public void setQos(String qos) {
        this.qos = qos;
    }
}
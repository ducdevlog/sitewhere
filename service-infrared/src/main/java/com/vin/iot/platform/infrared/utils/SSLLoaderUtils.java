/*
 *
 *  Developed by Anhgv by VinGroup on 8/14/19, 11:18 AM
 *  Last modified 6/18/19, 1:53 PM.
 *  Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *  The software in this package is published under the terms of the CPAL v1.0
 *  license, a copy of which has been included with this distribution in the
 *  LICENSE.txt file.
 *
 *
 */

package com.vin.iot.platform.infrared.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.springframework.core.io.ClassPathResource;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class SSLLoaderUtils {
    public static SocketFactory getSSLContext(final String caCrtFile, final String crtFile, final String keyFile, final String password) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // load CA certificate
        X509Certificate caCert = null;

        BufferedInputStream bis = new BufferedInputStream(new ClassPathResource(caCrtFile, SSLLoaderUtils.class.getClassLoader()).getInputStream());
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        while (bis.available() > 0) {
            caCert = (X509Certificate) cf.generateCertificate(bis);
            //System.out.println(caCert.toString());
        }

        // load client certificate
        bis = new BufferedInputStream(new ClassPathResource(crtFile, SSLLoaderUtils.class.getClassLoader()).getInputStream());
        X509Certificate cert = null;
        while (bis.available() > 0) {
            cert = (X509Certificate) cf.generateCertificate(bis);
            //System.out.println(caCert.toString());
        }

        // load client private key
        PEMParser pemParser = new PEMParser(new InputStreamReader(new ClassPathResource(keyFile, SSLLoaderUtils.class.getClassLoader()).getInputStream()));
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
                new java.security.cert.Certificate[]{cert});
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory
                .getDefaultAlgorithm());
        kmf.init(ks, password.toCharArray());

        // finally, create SSL socket factory
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return context.getSocketFactory();
    }
}

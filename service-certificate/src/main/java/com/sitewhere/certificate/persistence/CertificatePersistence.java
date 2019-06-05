/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.certificate.persistence;

import com.sitewhere.certificate.persistence.dto.IssuerData;
import com.sitewhere.certificate.persistence.dto.SubjectData;
import com.sitewhere.rest.model.certificate.Certificate;
import com.sitewhere.rest.model.device.state.DeviceState;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.certificate.ICertificate;
import com.sitewhere.spi.certificate.request.ICertificateCreateRequest;
import com.sitewhere.spi.device.IDevice;
import com.sitewhere.spi.device.IDeviceAssignment;
import com.sitewhere.spi.device.state.request.IDeviceStateCreateRequest;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import sun.security.provider.X509Factory;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

/**
 * Common methods needed by device state management implementations.
 *
 * @author Derek
 */
public class CertificatePersistence {
    private static final int KEY_SIZE = 2048;
    /**
     * Common logic for creating new device state object and populating it from
     * request.
     *
     * @param request
     * @return
     * @throws SiteWhereException
     */
    public Certificate certificateCreateLogic(ICertificateCreateRequest request) throws SiteWhereException {
        Certificate certificate = new Certificate();
        certificate.setId(UUID.randomUUID());
        certificate.setOrganization(request.getOrganization());
        certificate.setOrganizationalUnit(request.getOrganizationalUnit());
        certificate.setCountry(request.getCountry());
        certificate.setState(request.getState());
        certificate.setLocality(request.getLocality());
        certificate.setCommonName(request.getCommonName());
        certificate.setSerialNumber(request.getSerialNumber());
        certificate.setKeySize(request.getKeySize());
        certificate.setSurname(request.getSurname());
        certificate.setGivenName(request.getGivenName());
        certificate.setUserId(request.getUserId());
        X500Name x500name = generateName(certificate);
        KeyPair newKeyPair = generateKeyPair(certificate.getKeySize());
        try {
            // generate private key
            assert newKeyPair != null;
            certificate.setPrivateKey(generatePrivateKey(newKeyPair));
            // generate public key
            certificate.setPublicKey(generatePublicKey(newKeyPair));
            // generate certificate
            Date startDate = new Date();
            LocalDateTime endLocalDateTime = LocalDateTime.of(2050, Month.DECEMBER, 31, 23, 59, 59);
            Date endDate = Date.from(endLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
            IssuerData issuer = null;
            if  (certificate.getAliasUserId() != null) {
                //issuer = validateCertificateAuthority(certificate.getAliasUserId());
            } else {
                issuer = new IssuerData(x500name, newKeyPair.getPrivate());
            }
            SubjectData subject = new SubjectData(newKeyPair.getPublic(), x500name, certificate.getSerialNumber(), startDate, endDate);
            certificate.setCertificateKey(generateCertificate(subject, issuer, true));
            // Save to DB
            certificate.setStartDate(startDate);
            certificate.setEndDate(endDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return certificate;
    }

    /**
     * Common logic for updating an existing device state object.
     *
     * @param request
     * @param target
     * @throws SiteWhereException
     */
    public void deviceStateUpdateLogic(IDeviceStateCreateRequest request, DeviceState target)
            throws SiteWhereException {
        target.setLastInteractionDate(request.getLastInteractionDate());
        target.setPresenceMissingDate(request.getPresenceMissingDate());
        if (request.getDeviceTypeId() != null) {
            target.setDeviceTypeId(request.getDeviceTypeId());
        }
        if (request.getCustomerId() != null) {
            target.setCustomerId(request.getCustomerId());
        }
        if (request.getAreaId() != null) {
            target.setAreaId(request.getAreaId());
        }
        if (request.getAssetId() != null) {
            target.setAssetId(request.getAssetId());
        }
        if (request.getLastLocationEventId() != null) {
            target.setLastLocationEventId(request.getLastLocationEventId());
        }
        if (request.getLastMeasurementEventIds() != null) {
            target.getLastMeasurementEventIds().putAll(request.getLastMeasurementEventIds());
        }
        if (request.getLastAlertEventIds() != null) {
            target.getLastAlertEventIds().putAll(request.getLastAlertEventIds());
        }
    }

    private String generateCertificate(SubjectData subjectData, IssuerData issuerData, boolean caRoot) throws IOException {
        try {
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            BouncyCastleProvider bcp = new BouncyCastleProvider();
            builder = builder.setProvider(bcp);
            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());
            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                    new BigInteger(String.valueOf(subjectData.getSerialNumber())),
                    subjectData.getStartDate(),
                    subjectData.getEndDate(),
                    subjectData.getX500name(),
                    subjectData.getPublicKey());
            if (caRoot)
                certGen.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.keyCertSign));
            X509CertificateHolder certHolder = certGen.build(contentSigner);
            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider(bcp);
            StringWriter sw = new StringWriter();
            sw.write(X509Factory.BEGIN_CERT);
            sw.write("\n");
            sw.write(DatatypeConverter.printBase64Binary(certConverter.getCertificate(certHolder).getEncoded()).replaceAll("(.{64})", "$1\n"));
            sw.write("\n");
            sw.write(X509Factory.END_CERT);
            sw.close();
            return sw.toString();
        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException | CertificateException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String generatePrivateKey(KeyPair keyPair) throws IOException {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        return generatePemFile(rsaPrivateKey, "PRIVATE KEY");
    }

    private String generatePublicKey(KeyPair keyPair) throws IOException {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        return generatePemFile(rsaPublicKey, "CERTIFICATE");
    }

    private String generatePemFile(Key key, String description) throws IOException {
        PemObject pemObject = new PemObject(description, key.getEncoded());
        StringWriter stringWriter = new StringWriter();
        PemWriter pemWriter = new PemWriter(stringWriter);
        pemWriter.writeObject(pemObject);
        pemWriter.flush();
        pemWriter.close();
        return stringWriter.toString();
    }

    private X500Name generateName(Certificate certificate) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, certificate.getCommonName());
        builder.addRDN(BCStyle.SURNAME, certificate.getSurname());
        builder.addRDN(BCStyle.GIVENNAME, certificate.getGivenName());
        builder.addRDN(BCStyle.O, certificate.getOrganization());
        builder.addRDN(BCStyle.OU, certificate.getOrganizationalUnit());
        builder.addRDN(BCStyle.C, certificate.getCountry());
        builder.addRDN(BCStyle.ST, certificate.getState());
        builder.addRDN(BCStyle.SERIALNUMBER, String.valueOf(certificate.getSerialNumber()));
        builder.addRDN(BCStyle.UID, certificate.getUserId());
        return builder.build();
    }

    private KeyPair generateKeyPair(int size) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(size > 0 ? size : KEY_SIZE, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }
}

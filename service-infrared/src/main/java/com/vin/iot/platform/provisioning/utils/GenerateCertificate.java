package com.vin.iot.platform.infrared.utils;

import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.io.pem.PemHeader;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemObjectGenerator;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author AnhGV
 * Date 6/4/2019
 */
public class GenerateCertificate {
    private static final int LINE_LENGTH = 64;
    private char[] buf = new char[LINE_LENGTH];

    public String generate(PemObjectGenerator objGen) throws IOException {
        StringBuilder certificate = new StringBuilder();
        PemObject obj = objGen.generate();
        certificate.append(generatePreEncapsulationBoundary(obj.getType()));
        if (!obj.getHeaders().isEmpty()) {
            for (Iterator it = obj.getHeaders().iterator(); it.hasNext(); ) {
                PemHeader hdr = (PemHeader) it.next();
                certificate.append(hdr.getName());
                certificate.append(": ");
                certificate.append(hdr.getValue());
                certificate.append("\n");
            }
            certificate.append("\n");
        }
        certificate.append(generateEncoded(obj.getContent()));
        certificate.append(generatePostEncapsulationBoundary(obj.getType()));
        return certificate.toString();
    }

    private String generateEncoded(byte[] bytes) throws IOException {
        StringBuilder contentCertificate = new StringBuilder();
        bytes = Base64.encode(bytes);
        for (int i = 0; i < bytes.length; i += buf.length) {
            int index = 0;
            while (index != buf.length) {
                if ((i + index) >= bytes.length) {
                    break;
                }
                buf[index] = (char) bytes[i + index];
                index++;
            }
            contentCertificate.append(buf, 0, index);
            contentCertificate.append("\n");
        }
        return contentCertificate.toString();
    }

    private String generatePreEncapsulationBoundary(String type) {
        return "-----BEGIN " + type + "-----" + "\n";
    }

    private String generatePostEncapsulationBoundary(String type) {
        return "-----END " + type + "-----" + "\n";
    }
}

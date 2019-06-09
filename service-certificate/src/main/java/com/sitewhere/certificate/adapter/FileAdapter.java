package com.sitewhere.certificate.adapter;

public interface FileAdapter {
    void uploadCertificate(String bucketName, String name, String fileContent);
}
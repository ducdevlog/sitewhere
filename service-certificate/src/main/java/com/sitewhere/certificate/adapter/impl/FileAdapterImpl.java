package com.sitewhere.certificate.adapter.impl;

import com.sitewhere.certificate.adapter.FileAdapter;
import com.sitewhere.certificate.adapter.utils.ByteArrayMultipartFile;
import com.sitewhere.certificate.client.FileClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileAdapterImpl implements FileAdapter {

    @Autowired
    private FileClient fileClient;

    @Override
    public void uploadCertificate(String bucketName, String name, String fileContent) {
        MultipartFile result = new ByteArrayMultipartFile(name, MediaType.TEXT_PLAIN_VALUE, fileContent.getBytes());
        fileClient.uploadFile(bucketName, result);
    }
}

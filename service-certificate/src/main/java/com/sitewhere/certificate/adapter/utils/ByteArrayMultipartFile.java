package com.sitewhere.certificate.adapter.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class ByteArrayMultipartFile implements MultipartFile {

    private String name;

    private String originalFilename;

    private String contentType;

    private byte[] bytes;

    public ByteArrayMultipartFile(String name, String contentType, byte[] bytes) {
        this.name = name;
        this.contentType = contentType;
        this.bytes = bytes;
    }

    @Override
    public boolean isEmpty () {
        return bytes.length == 0;
    }

    @Override
    public long getSize () {
        return bytes.length;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public InputStream getInputStream () {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public void transferTo (File destination) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(destination);
            outputStream.write(bytes);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
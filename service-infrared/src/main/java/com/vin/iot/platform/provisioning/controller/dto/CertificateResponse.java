package com.vin.iot.platform.infrared.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author AnhGV
 * Date 5/30/2019
 */
@Data
@AllArgsConstructor
public class CertificateResponse implements Serializable {
    private X500NameMap subjectData;
    private X500NameMap issuerData;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z")
    private Date startDate;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z")
    private Date endDate;
    private String serialNumber;
    private String alias;
    private String algorithm;
    private int keySize;

    public CertificateResponse(){
        this.algorithm = "SHA256withRSA";
        this.subjectData = new X500NameMap();
        this.issuerData = new X500NameMap();
    }
}

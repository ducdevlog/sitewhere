package com.vin.iot.platform.infrared.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class CertificateRequest implements Serializable {
    private String alias;
    private String surname;
    private String givenName;
    private String o;
    private String ou;
    private String c;
    private String e;
    private String password;
    private boolean ca;
    private int numberOfDays;
    private int keySize ;
    private String serialNumber;
}

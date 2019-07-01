package com.vin.iot.platform.infrared.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class X500NameMap {
    private String cn;
    private String surname;
    private String givenName;
    private String o;
    private String ou;
    private String c;
    private String e;
}

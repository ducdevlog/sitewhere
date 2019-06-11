package com.vin.iot.platform.infrared.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "device_codeset")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCodeset {
    @Field("ID")
    private String id;
    @Field("DEV_TYPE_BRAND_ID")
    private String deviceTypeBrandId;
    @Field("CODESET_NAME")
    private String codesetName;
}

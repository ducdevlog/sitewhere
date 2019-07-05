package com.vin.iot.platform.infrared.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "device_type_brand")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfraredDeviceTypeBrand {
    @Field("ID")
    private String id;
    @Field("TYPE_CODE")
    private String type;
    @Field("BRAND_NAME")
    private String brand;
}

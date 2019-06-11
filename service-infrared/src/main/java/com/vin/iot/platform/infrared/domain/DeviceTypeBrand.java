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
public class DeviceTypeBrand {
    @Field("ID")
    private String id;
    @Field("TYPE")
    private String type;
    @Field("BRAND")
    private String brand;
}

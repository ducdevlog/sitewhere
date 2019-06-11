package com.vin.iot.platform.infrared.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "device_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceType {
    @Field("ID")
    private String id;
    @Field("TYPE_NAME")
    private String typeName;
}

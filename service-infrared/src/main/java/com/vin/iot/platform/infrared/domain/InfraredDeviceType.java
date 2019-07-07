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
public class InfraredDeviceType {
    @Field("ID")
    private String id;
    @Field("TYPE_NAME")
    private String typeName;
    @Field("TYPE_CODE")
    private String typeCode;
    @Field("IMAGE_URL")
    private String imageUrl;
    @Field("ICON_ON_OFF")
    private String iconOnOff;
    @Field("ICON_1D")
    private String icon1D;
    @Field("ICON_2D_ON")
    private String icon2DOn;
    @Field("ICON_2D_OFF")
    private String icon2DOff;
    @Field("ICON_3D_ON")
    private String icon3DOn;
    @Field("ICON_3D_OFF")
    private String icon3DOff;
    @Field("ORDER")
    private int order;
}

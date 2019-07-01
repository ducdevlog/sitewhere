package com.vin.iot.platform.infrared.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ir_code_raw")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IrCodeRaw {
    @Field("ID")
    private String id;
    @Field("CODESET_NAME")
    private String codesetName;
    @Field("FUNCTION_NAME")
    private String functionName;
    @Field("POWER")
    private String power;
    @Field("MODE")
    private String mode;
    @Field("FAN")
    private String fan;
    @Field("TEMP")
    private String temp;
    @Field("SWING")
    private String swing;
    @Field("TIMER")
    private String timer;
    @Field("TIMER_DELAY")
    private String timerDelay;
    @Field("LED")
    private String led;
    @Field("COMFORT")
    private String comfort;
    @Field("ECONO")
    private String econo;
    @Field("POWERFUL")
    private String powerful;
    @Field("IR_FREQ_KHZ")
    private String irFreqKhz;
    @Field("IR_CODE")
    private String irCode;
}

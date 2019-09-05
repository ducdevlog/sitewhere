package com.vin.iot.platform.infrared.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vin.iot.platform.infrared.config.dto.IrCodeRawDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ir_code_raw")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IrCodeRaw {
//    @Field("ID")
    private String id;
//    @Field("CODESET_NAME")
//    private String codesetName;
//    @Field("AREA_TOKEN")
//    private String areaToken;
//    @Field("IR_FREQ")
//    private String irFreq;
//    @Field("IR_CODE")
//    private String irCode;

    private JSONObject otherFields;

    /*@Field("FUNCTION_NAME")
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
    @Field("TIMER_MINS")
    private String timerMins;
    @Field("SLEEP")
    private String sleep;
    @Field("SLEEP_MINS")
    private String sleepMins;
    @Field("LED")
    private String led;
    @Field("COMFORT")
    private String comfort;
    @Field("ECONO")
    private String econo;
    @Field("POWERFUL")
    private String powerful;*/



    public static IrCodeRaw of(IrCodeRawDto irCodeRawDto) {
        return IrCodeRaw.builder()
                .id(irCodeRawDto.getId())
                //.codesetName(irCodeRawDto.getCodesetName())
                //.functionName(irCodeRawDto.getFunctionName())
                //.power(irCodeRawDto.getPower())
                //.mode(irCodeRawDto.getMode())
                //.fan(irCodeRawDto.getFan())
                //.temp(irCodeRawDto.getTemp())
                //.timerMins(irCodeRawDto.getTimerMins())
                //.sleep(irCodeRawDto.getSleep())
                //.led(irCodeRawDto.getLed())
                //.comfort(irCodeRawDto.getComfort())
                //.econo(irCodeRawDto.getEcono())
                //.powerful(irCodeRawDto.getPowerful())
                //.irFreq(irCodeRawDto.getIrFreq())
                //.irCode(irCodeRawDto.getIrCode())
                //.areaToken(irCodeRawDto.getAreaToken())
                .build();
    }
}

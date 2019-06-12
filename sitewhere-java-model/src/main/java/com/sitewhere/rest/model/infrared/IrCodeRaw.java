package com.sitewhere.rest.model.infrared;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sitewhere.spi.infrared.IIrCodeRaw;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IrCodeRaw implements IIrCodeRaw {
    private String id;
    private String codesetName;
    private String functionName;
    private String power;
    private String mode;
    private String fan;
    private String temp;
    private String swing;
    private String timer;
    private String timerDelay;
    private String led;
    private String comfort;
    private String econo;
    private String powerful;
    private String irFreqKhz;
    private String irCode;

    public IrCodeRaw(String codesetName, String functionName, String power, String mode, String fan, String temp, String swing, String timer, String timerDelay, String led, String comfort, String econo, String powerful) {
        this.codesetName = codesetName;
        this.functionName = functionName;
        this.power = power;
        this.mode = mode;
        this.fan = fan;
        this.temp = temp;
        this.swing = swing;
        this.timer = timer;
        this.timerDelay = timerDelay;
        this.led = led;
        this.comfort = comfort;
        this.econo = econo;
        this.powerful = powerful;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodesetName() {
        return codesetName;
    }

    public void setCodesetName(String codesetName) {
        this.codesetName = codesetName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getFan() {
        return fan;
    }

    public void setFan(String fan) {
        this.fan = fan;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getSwing() {
        return swing;
    }

    public void setSwing(String swing) {
        this.swing = swing;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getTimerDelay() {
        return timerDelay;
    }

    public void setTimerDelay(String timerDelay) {
        this.timerDelay = timerDelay;
    }

    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }

    public String getComfort() {
        return comfort;
    }

    public void setComfort(String comfort) {
        this.comfort = comfort;
    }

    public String getEcono() {
        return econo;
    }

    public void setEcono(String econo) {
        this.econo = econo;
    }

    public String getPowerful() {
        return powerful;
    }

    public void setPowerful(String powerful) {
        this.powerful = powerful;
    }

    public String getIrFreqKhz() {
        return irFreqKhz;
    }

    public void setIrFreqKhz(String irFreqKhz) {
        this.irFreqKhz = irFreqKhz;
    }

    public String getIrCode() {
        return irCode;
    }

    public void setIrCode(String irCode) {
        this.irCode = irCode;
    }
}
package com.guard.wallet.req;

import android.support.annotation.NonNull;

/* loaded from: classes.dex */
public class BatteryLevelVO extends MessageBodyVO {
    private Integer health;
    private Integer inPowerSaveMode;
    private Float percent;
    private Integer plugged;
    private Integer status;
    private String technology;
    private Integer temperature;
    private Integer voltage;

    public BatteryLevelVO() {
    }

    public BatteryLevelVO(Float f2, Integer num, Integer num2, Integer num3, Integer num4, Integer num5, String str, Integer num6) {
        this.percent = f2;
        this.inPowerSaveMode = num;
        this.health = num2;
        this.status = num3;
        this.voltage = num4;
        this.temperature = num5;
        this.technology = str;
        this.plugged = num6;
    }

    public Integer getHealth() {
        return this.health;
    }

    public Integer getInPowerSaveMode() {
        return this.inPowerSaveMode;
    }

    public Float getPercent() {
        return this.percent;
    }

    public Integer getPlugged() {
        return this.plugged;
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getTechnology() {
        return this.technology;
    }

    public Integer getTemperature() {
        return this.temperature;
    }

    public Integer getVoltage() {
        return this.voltage;
    }

    public void setHealth(Integer num) {
        this.health = num;
    }

    public void setInPowerSaveMode(Integer num) {
        this.inPowerSaveMode = num;
    }

    public void setPercent(Float f2) {
        this.percent = f2;
    }

    public void setPlugged(Integer num) {
        this.plugged = num;
    }

    public void setStatus(Integer num) {
        this.status = num;
    }

    public void setTechnology(String str) {
        this.technology = str;
    }

    public void setTemperature(Integer num) {
        this.temperature = num;
    }

    public void setVoltage(Integer num) {
        this.voltage = num;
    }

    @NonNull
    public String toString() {
        return "BatteryLevelVO{percent=" + this.percent + ", inPowerSaveMode=" + this.inPowerSaveMode + ", health=" + this.health + ", status=" + this.status + ", voltage=" + this.voltage + ", temperature=" + this.temperature + ", technology='" + this.technology + "', plugged=" + this.plugged + '}';
    }
}

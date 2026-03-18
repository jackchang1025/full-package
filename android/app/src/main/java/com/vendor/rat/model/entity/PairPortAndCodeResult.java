package com.vendor.rat.model.entity;

// ADAPT: vendor = com.guard.wallet.entity.PairPortAndCodeResult

import androidx.annotation.NonNull;
import java.io.Serializable;

public class PairPortAndCodeResult implements Serializable {
    private String host;
    private String pairCode;
    private Integer pairPort;

    public PairPortAndCodeResult() {
    }

    public PairPortAndCodeResult(String host, Integer pairPort, String pairCode) {
        this.host = host;
        this.pairPort = pairPort;
        this.pairCode = pairCode;
    }

    public String getHost() {
        return this.host;
    }

    public String getPairCode() {
        return this.pairCode;
    }

    public Integer getPairPort() {
        return this.pairPort;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPairCode(String pairCode) {
        this.pairCode = pairCode;
    }

    public void setPairPort(Integer pairPort) {
        this.pairPort = pairPort;
    }

    @NonNull
    @Override
    public String toString() {
        return "PairPortAndCodeResult{pairPort=" + pairPort
                + ", pairCode='" + pairCode
                + "', host='" + host + "'}";
    }
}

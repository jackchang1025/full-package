package com.guard.wallet.entity;

import androidx.annotation.NonNull;

public class PairPortAndCodeResult {
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
        return "PairPortAndCodeResult{pairPort=" + this.pairPort
                + ", pairCode='" + this.pairCode
                + "', host='" + this.host + "'}";
    }
}

package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
public class ReqADBPairVO implements Serializable {
    private boolean directConnect;
    private String host;
    private String pairCode;
    private String pairPort;
    public ReqADBPairVO() {
    }
    public ReqADBPairVO(String str, String str2, String str3, boolean z2) {
        this.host = str;
        this.pairPort = str2;
        this.pairCode = str3;
        this.directConnect = z2;
    }
    public String getHost() {
        return this.host;
    }
    public String getPairCode() {
        return this.pairCode;
    }
    public String getPairPort() {
        return this.pairPort;
    }
    public boolean isDirectConnect() {
        return this.directConnect;
    }
    public void setDirectConnect(boolean z2) {
        this.directConnect = z2;
    }
    public void setHost(String str) {
        this.host = str;
    }
    public void setPairCode(String str) {
        this.pairCode = str;
    }
    public void setPairPort(String str) {
        this.pairPort = str;
    }
    @NonNull
    public String toString() {
        return "ReqADBPairVO{pairPort=" + this.pairPort + ", host=" + this.host + ", pairCode=" + this.pairCode + ", directConnect=" + this.directConnect + '}';
    }
}

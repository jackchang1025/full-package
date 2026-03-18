package com.vendor.rat.model.req;

// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ReqListenHelper implements Serializable {
    private String batchId;
    private String delegateId;
    private String listenId;
    private Integer listenType;
    private String prop;
    private Integer screenState;
    private String subscribeId;

    public ReqListenHelper() {
        this.prop = "GESTURE_POINTS";
    }

    public ReqListenHelper(Integer num, String str, Integer num2) {
        this.prop = "GESTURE_POINTS";
        this.listenType = num;
        this.subscribeId = str;
        this.screenState = num2;
    }

    public ReqListenHelper(Integer num, String str, String str2, String str3, String str4, String str5, Integer num2) {
        this.listenType = num;
        this.batchId = str;
        this.listenId = str2;
        this.subscribeId = str3;
        this.delegateId = str4;
        this.prop = str5;
        this.screenState = num2;
    }

    public ReqListenHelper(String str, Integer num) {
        this.prop = "GESTURE_POINTS";
        this.subscribeId = str;
        this.screenState = num;
    }

    public static ReqListenHelper clone(ReqListenHelper reqListenHelper) {
        if (reqListenHelper == null) {
            return null;
        }
        ReqListenHelper reqListenHelper2 = new ReqListenHelper();
        reqListenHelper2.setListenType(reqListenHelper.getListenType());
        reqListenHelper2.setListenId(reqListenHelper.getListenId());
        reqListenHelper2.setSubscribeId(reqListenHelper.getSubscribeId());
        reqListenHelper2.setDelegateId(reqListenHelper.getDelegateId());
        reqListenHelper2.setBatchId(reqListenHelper.getBatchId());
        reqListenHelper2.setProp(reqListenHelper.getProp());
        reqListenHelper2.setScreenState(reqListenHelper.getScreenState());
        return reqListenHelper2;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public String getDelegateId() {
        return this.delegateId;
    }

    public String getListenId() {
        return this.listenId;
    }

    public Integer getListenType() {
        return this.listenType;
    }

    public String getProp() {
        return this.prop;
    }

    public Integer getScreenState() {
        return this.screenState;
    }

    public String getSubscribeId() {
        return this.subscribeId;
    }

    public void setBatchId(String str) {
        this.batchId = str;
    }

    public void setDelegateId(String str) {
        this.delegateId = str;
    }

    public void setListenId(String str) {
        this.listenId = str;
    }

    public void setListenType(Integer num) {
        this.listenType = num;
    }

    public void setProp(String str) {
        this.prop = str;
    }

    public void setScreenState(Integer num) {
        this.screenState = num;
    }

    public void setSubscribeId(String str) {
        this.subscribeId = str;
    }

    @NonNull
    public String toString() {
        return "ReqListenHelper{subscribeId='" + this.subscribeId + "', listenId='" + this.listenId + "', delegateId='" + this.delegateId + "', prop='" + this.prop + "', screenState=" + this.screenState + "', batchId=" + this.batchId + "', listenType=" + this.listenType + "'}";
    }
}

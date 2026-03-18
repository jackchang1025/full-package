package com.vendor.rat.model.req;

// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req

import androidx.annotation.NonNull;
import java.util.List;

public class ListenResponseVO extends MessageBodyVO {
    private String batchId;
    private String delegateId;
    private String listenId;
    private List<ListenPropResponse> responses;
    private String subscribeId;

    public ListenResponseVO() {
    }

    public ListenResponseVO(String str, String str2, String str3, String str4, List<ListenPropResponse> list) {
        this.batchId = str;
        this.listenId = str2;
        this.subscribeId = str3;
        this.delegateId = str4;
        this.responses = list;
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

    public List<ListenPropResponse> getResponses() {
        return this.responses;
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

    public void setResponses(List<ListenPropResponse> list) {
        this.responses = list;
    }

    public void setSubscribeId(String str) {
        this.subscribeId = str;
    }

    @NonNull
    public String toString() {
        return "ListenResponseVO{listenId='" + this.listenId + "', subscribeId='" + this.subscribeId + "', batchId='" + this.batchId + "', delegateId='" + this.delegateId + "', responses=" + this.responses + '}';
    }
}

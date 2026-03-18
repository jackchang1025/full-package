package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.List;
public class MatchListenWindowVO implements Serializable {
    private String delegateId;
    private List<ListenWindow> listenWindows;
    public MatchListenWindowVO() {
    }
    public MatchListenWindowVO(String str, List<ListenWindow> list) {
        this.delegateId = str;
        this.listenWindows = list;
    }
    public String getDelegateId() {
        return this.delegateId;
    }
    public List<ListenWindow> getListenWindows() {
        return this.listenWindows;
    }
    public void setDelegateId(String str) {
        this.delegateId = str;
    }
    public void setListenWindows(List<ListenWindow> list) {
        this.listenWindows = list;
    }
    @NonNull
    public String toString() {
        return "MatchListenWindowVO{delegateId='" + this.delegateId + "', listenWindows=" + this.listenWindows + '}';
    }
}

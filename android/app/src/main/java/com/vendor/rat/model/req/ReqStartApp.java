package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.List;
public class ReqStartApp implements Serializable {
    private List<ListenWindow> listenWindows;
    private String mainActivity;
    private boolean start;
    private String startPackage;
    public ReqStartApp() {
    }
    public ReqStartApp(String str, String str2, boolean z2, List<ListenWindow> list) {
        this.startPackage = str;
        this.mainActivity = str2;
        this.start = z2;
        this.listenWindows = list;
    }
    public List<ListenWindow> getListenWindows() {
        return this.listenWindows;
    }
    public String getMainActivity() {
        return this.mainActivity;
    }
    public String getStartPackage() {
        return this.startPackage;
    }
    public boolean isStart() {
        return this.start;
    }
    public void setListenWindows(List<ListenWindow> list) {
        this.listenWindows = list;
    }
    public void setMainActivity(String str) {
        this.mainActivity = str;
    }
    public void setStart(boolean z2) {
        this.start = z2;
    }
    public void setStartPackage(String str) {
        this.startPackage = str;
    }
    @NonNull
    public String toString() {
        return "ReqStartApp{startPackage='" + this.startPackage + "', mainActivity=" + this.mainActivity + ", start=" + this.start + ", listenWindows=" + this.listenWindows + '}';
    }
}

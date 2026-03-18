package com.vendor.rat.model.resp;
// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp
import androidx.annotation.NonNull;
public class SearchNodeResultVO {
    private UiObjectVO node;
    private String resUnique;
    public SearchNodeResultVO() {
    }
    public SearchNodeResultVO(String str, UiObjectVO uiObjectVO) {
        this.resUnique = str;
        this.node = uiObjectVO;
    }
    public UiObjectVO getNode() {
        return this.node;
    }
    public String getResUnique() {
        return this.resUnique;
    }
    public void setNode(UiObjectVO uiObjectVO) {
        this.node = uiObjectVO;
    }
    public void setResUnique(String str) {
        this.resUnique = str;
    }
    @NonNull
    public String toString() {
        return "SearchNodeResultVO{resUnique='" + this.resUnique + "', node=" + this.node + '}';
    }
}

package com.guard.wallet.resp;

import androidx.annotation.NonNull;

public class SearchNodeResultVO {
    private UiObjectVO node;
    private String resUnique;

    public SearchNodeResultVO() {}
    public SearchNodeResultVO(String resUnique, UiObjectVO node) {
        this.resUnique = resUnique; this.node = node;
    }

    public UiObjectVO getNode() { return this.node; }
    public String getResUnique() { return this.resUnique; }
    public void setNode(UiObjectVO v) { this.node = v; }
    public void setResUnique(String v) { this.resUnique = v; }

    @NonNull
    @Override
    public String toString() {
        return "SearchNodeResultVO{resUnique='" + this.resUnique + "', node=" + this.node + "}";
    }
}

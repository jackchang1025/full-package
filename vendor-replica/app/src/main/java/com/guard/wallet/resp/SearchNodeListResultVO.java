package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class SearchNodeListResultVO implements Serializable {
    private List<UiObjectVO> nodes;
    private String resUnique;

    public SearchNodeListResultVO() {}
    public SearchNodeListResultVO(String resUnique, List<UiObjectVO> nodes) {
        this.resUnique = resUnique; this.nodes = nodes;
    }

    public void addNodes(List<UiObjectVO> extra) {
        if (extra != null && !extra.isEmpty()) {
            if (this.nodes == null) this.nodes = new LinkedList<>();
            this.nodes.addAll(extra);
        }
    }

    public List<UiObjectVO> getNodes() { return this.nodes; }
    public String getResUnique() { return this.resUnique; }
    public void setNodes(List<UiObjectVO> v) { this.nodes = v; }
    public void setResUnique(String v) { this.resUnique = v; }

    public int size() {
        return (this.nodes != null && !this.nodes.isEmpty()) ? this.nodes.size() : 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "SearchNodeListResultVO{resUnique='" + this.resUnique + "', nodes=" + this.nodes + "}";
    }
}

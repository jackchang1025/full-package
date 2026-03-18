package com.vendor.rat.model.resp;
// ADAPT: package com.guard.wallet.resp -> com.vendor.rat.model.resp
import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
public class SearchNodeListResultVO implements Serializable {
    private List<UiObjectVO> nodes;
    private String resUnique;
    public SearchNodeListResultVO() {
    }
    public SearchNodeListResultVO(String str, List<UiObjectVO> list) {
        this.resUnique = str;
        this.nodes = list;
    }
    public void addNodes(List<UiObjectVO> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (this.nodes == null) {
            this.nodes = new LinkedList();
        }
        this.nodes.addAll(list);
    }
    public List<UiObjectVO> getNodes() { return this.nodes; }
    public String getResUnique() { return this.resUnique; }
    public void setNodes(List<UiObjectVO> list) { this.nodes = list; }
    public void setResUnique(String str) { this.resUnique = str; }
    public int size() {
        List<UiObjectVO> list = this.nodes;
        if (list == null || list.isEmpty()) {
            return 0;
        }
        return this.nodes.size();
    }
    @NonNull
    public String toString() {
        return "SearchNodeListResultVO{resUnique='" + this.resUnique + "', nodes=" + this.nodes + '}';
    }
}

package com.vendor.rat.auto.filter;

import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.UiNode;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Selector implements NodeFilter {
    private static final String TAG = "com.vendor.rat.auto.filter.Selector";
    private final List<NodeFilter> filters = new LinkedList<>();

    public void add(NodeFilter filter) {
        this.filters.add(filter);
    }

    @Override
    public boolean accept(UiNode node) {
        try {
            if (!this.filters.isEmpty()) {
                Iterator<NodeFilter> it = this.filters.iterator();
                while (it.hasNext()) {
                    if (!it.next().accept(node)) {
                        return false;
                    }
                }
                return true;
            }
        } catch (Exception e) {
            // silent
        }
        return false;
    }

    public int filterCount() {
        return this.filters.size();
    }

    public void remove(NodeFilter filter) {
        this.filters.remove(filter);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<NodeFilter> it = this.filters.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
            sb.append(".");
        }
        String s = sb.toString();
        if (s != null && !s.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}

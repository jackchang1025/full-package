package com.guard.wallet.filter;

import a1.AbstractC0026q;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public class Selector implements Filter {
    private static final String TAG = "com.guard.wallet.filter.Selector";
    private final List<Filter> filters = new LinkedList();

    public void add(Filter filter) {
        this.filters.add(filter);
    }

    @Override // com.guard.wallet.filter.Filter
    public Boolean filter(UiObject uiObject) {
        try {
            if (!this.filters.isEmpty()) {
                Iterator<Filter> it = this.filters.iterator();
                while (it.hasNext()) {
                    if (!it.next().filter(uiObject).booleanValue()) {
                        return Boolean.FALSE;
                    }
                }
                return Boolean.TRUE;
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
        }
        return Boolean.FALSE;
    }

    public int filterCount() {
        return this.filters.size();
    }

    public void remove(Filter filter) {
        this.filters.remove(filter);
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Filter> it = this.filters.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
            sb.append(".");
        }
        if (!AbstractC0026q.m151B(sb.toString())) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}

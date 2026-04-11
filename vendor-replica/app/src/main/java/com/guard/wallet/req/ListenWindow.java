package com.guard.wallet.req;

import com.guard.wallet.core.AppUtils;
import androidx.annotation.NonNull;
import com.guard.wallet.filter.CombineFilter;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ListenWindow implements Comparable<ListenWindow> {
    private static final String TAG = "ListenWindow";

    private String id;
    private String packageName;
    private String className;
    private List<CombineFilter> matchs;
    private List<CombineFilter> dismiss;
    private HashSet<Integer> eventTypes;
    private List<EventSubscribe> eventSubscribes = new LinkedList<>();
    private Integer listenType;
    private Integer orderNo;

    // ═══════ Constructors ═══════

    public ListenWindow() {
        this.listenType = 0;
    }

    public ListenWindow(String packageName, String className) {
        this.listenType = 0;
        this.packageName = packageName;
        this.className = className;
    }

    public ListenWindow(String id, String packageName, String className) {
        this.listenType = 0;
        this.id = id;
        this.packageName = packageName;
        this.className = className;
    }

    public ListenWindow(String id, String packageName, String className,
                         List<CombineFilter> matchs, List<CombineFilter> dismiss,
                         HashSet<Integer> eventTypes, List<EventSubscribe> eventSubscribes,
                         Integer listenType, Integer orderNo) {
        this.id = id;
        this.packageName = packageName;
        this.className = className;
        this.matchs = matchs;
        this.dismiss = dismiss;
        this.eventTypes = eventTypes;
        this.eventSubscribes = eventSubscribes;
        this.listenType = listenType;
        this.orderNo = orderNo;
    }

    // ═══════ Comparable ═══════

    @Override
    public int compareTo(ListenWindow other) {
        if (this.orderNo != null && other.orderNo != null) {
            return this.orderNo - other.orderNo;
        }
        return this.orderNo == null ? -1 : 1;
    }

    // ═══════ destroy ═══════

    public void destroy() {
        try {
            if (this.matchs != null) {
                for (CombineFilter f : this.matchs) {
                    f.destroy();
                }
                this.matchs.clear();
                this.matchs = null;
            }
            if (this.dismiss != null) {
                for (CombineFilter f : this.dismiss) {
                    f.destroy();
                }
                this.dismiss.clear();
                this.dismiss = null;
            }
            if (this.eventTypes != null) {
                this.eventTypes.clear();
                this.eventTypes = null;
            }
            if (this.eventSubscribes != null) {
                for (EventSubscribe es : this.eventSubscribes) {
                    es.destroy();
                }
                this.eventSubscribes.clear();
                this.eventSubscribes = null;
            }
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    // ═══════ equals / hashCode ═══════

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        try {
            ListenWindow other = (ListenWindow) obj;
            // Both empty → equal
            if (AppUtils.B(this.packageName) && AppUtils.B(this.className)) return true;
            if (AppUtils.B(other.packageName) && AppUtils.B(other.className)) return true;
            // One has packageName empty → compare className only
            if (AppUtils.B(this.packageName) || AppUtils.B(other.packageName)) {
                return Objects.equals(this.className, other.className);
            }
            // One has className empty → compare packageName only
            if (AppUtils.B(this.className) || AppUtils.B(other.className)) {
                return Objects.equals(this.packageName, other.packageName);
            }
            // SoftInputWindow special case: compare packageName only
            if ("android.inputmethodservice.SoftInputWindow".equals(this.className)
                    || "android.inputmethodservice.SoftInputWindow".equals(other.className)) {
                return Objects.equals(this.packageName, other.packageName);
            }
            // ADAPT: HyperOS 3 (Android 16) AccessibilityEvent.getClassName()
            // returns "android.view.View" instead of the real Activity class.
            // Fall back to packageName-only when either side is this generic value.
            // FrameLayout excluded — used as exact match target by multiple engines.
            if ("android.view.View".equals(this.className)
                    || "android.view.View".equals(other.className)) {
                return Objects.equals(this.packageName, other.packageName);
            }
            // Normal: both must match
            return Objects.equals(this.packageName, other.packageName)
                    && Objects.equals(this.className, other.className);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.packageName, this.className);
    }

    // ═══════ getEventTypes (aggregates from eventSubscribes if own is null) ═══════

    public HashSet<Integer> getEventTypes() {
        try {
            HashSet<Integer> result = this.eventTypes;
            if (result == null) {
                result = new LinkedHashSet<>();
            }
            if (this.eventSubscribes == null || this.eventSubscribes.isEmpty()) {
                return result;
            }
            for (EventSubscribe es : this.eventSubscribes) {
                if (es != null && es.getEventTypes() != null && !es.getEventTypes().isEmpty()) {
                    result.addAll(es.getEventTypes());
                }
            }
            return result;
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            return this.eventTypes != null ? this.eventTypes : new LinkedHashSet<>();
        }
    }

    // ═══════ listenWindowUniqueId ═══════

    public String listenWindowUniqueId(String extra) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(AppUtils.B(this.packageName) ? "NULL" : this.packageName);
            sb.append(":");
            sb.append(AppUtils.B(this.className) ? "NULL" : this.className);
            sb.append(":");
            sb.append(AppUtils.B(extra) ? "NULL" : extra);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
        return sb.toString();
    }

    // ═══════ Getters ═══════

    public String getId() { return this.id; }
    public String getPackageName() { return this.packageName; }
    public String getClassName() { return this.className; }
    public List<CombineFilter> getMatchs() { return this.matchs; }
    public List<CombineFilter> getDismiss() { return this.dismiss; }
    public List<EventSubscribe> getEventSubscribes() { return this.eventSubscribes; }
    public Integer getListenType() { return this.listenType; }
    public Integer getOrderNo() { return this.orderNo; }

    // ═══════ Setters ═══════

    public void setId(String id) { this.id = id; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
    public void setClassName(String className) { this.className = className; }
    public void setMatchs(List<CombineFilter> matchs) { this.matchs = matchs; }
    public void setDismiss(List<CombineFilter> dismiss) { this.dismiss = dismiss; }
    public void setEventTypes(HashSet<Integer> eventTypes) { this.eventTypes = eventTypes; }
    public void setEventSubscribes(List<EventSubscribe> eventSubscribes) { this.eventSubscribes = eventSubscribes; }
    public void setListenType(Integer listenType) { this.listenType = listenType; }
    public void setOrderNo(Integer orderNo) { this.orderNo = orderNo; }

    // ═══════ toString ═══════

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ListenWindow{id='");
        sb.append(this.id);
        sb.append("', packageName='");
        sb.append(this.packageName);
        sb.append("', className='");
        sb.append(this.className);
        sb.append("', matchs=");
        sb.append(this.matchs);
        sb.append(", dismiss=");
        sb.append(this.dismiss);
        sb.append(", eventTypes=");
        sb.append(this.eventTypes);
        sb.append(", eventSubscribes=");
        sb.append(this.eventSubscribes);
        sb.append(", listenType=");
        sb.append(this.listenType);
        sb.append(", orderNo=");
        sb.append(this.orderNo);
        sb.append('}');
        return sb.toString();
    }
}

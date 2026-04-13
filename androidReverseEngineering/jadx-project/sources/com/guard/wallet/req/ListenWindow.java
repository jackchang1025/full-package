package com.guard.wallet.req;

import a1.AbstractC0026q;
import android.support.annotation.NonNull;
import com.guard.wallet.filter.CombineFilter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class ListenWindow implements Comparable<ListenWindow> {
    private static final String TAG = "ListenWindow";
    private String className;
    private List<CombineFilter> dismiss;
    private List<EventSubscribe> eventSubscribes;
    private HashSet<Integer> eventTypes;
    private String id;
    private Integer listenType;
    private List<CombineFilter> matchs;
    private Integer orderNo;
    private String packageName;

    public ListenWindow() {
        this.eventSubscribes = new LinkedList();
        this.listenType = 0;
    }

    @Override // java.lang.Comparable
    public int compareTo(ListenWindow listenWindow) {
        Integer num = this.orderNo;
        return (num == null || listenWindow.orderNo == null) ? num == null ? -1 : 1 : num.intValue() - listenWindow.orderNo.intValue();
    }

    public void destroy() {
        try {
            List<CombineFilter> list = this.matchs;
            if (list != null) {
                Iterator<CombineFilter> it = list.iterator();
                while (it.hasNext()) {
                    it.next().destroy();
                }
                this.matchs.clear();
                this.matchs = null;
            }
            List<CombineFilter> list2 = this.dismiss;
            if (list2 != null) {
                Iterator<CombineFilter> it2 = list2.iterator();
                while (it2.hasNext()) {
                    it2.next().destroy();
                }
                this.dismiss.clear();
                this.dismiss = null;
            }
            HashSet<Integer> hashSet = this.eventTypes;
            if (hashSet != null) {
                hashSet.clear();
                this.eventTypes = null;
            }
            List<EventSubscribe> list3 = this.eventSubscribes;
            if (list3 != null) {
                Iterator<EventSubscribe> it3 = list3.iterator();
                while (it3.hasNext()) {
                    it3.next().destroy();
                }
                this.eventSubscribes.clear();
                this.eventSubscribes = null;
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            try {
                if (getClass() == obj.getClass()) {
                    ListenWindow listenWindow = (ListenWindow) obj;
                    if (AbstractC0026q.m151B(this.packageName) && AbstractC0026q.m151B(this.className)) {
                        return true;
                    }
                    if (AbstractC0026q.m151B(listenWindow.packageName) && AbstractC0026q.m151B(listenWindow.className)) {
                        return true;
                    }
                    if (!AbstractC0026q.m151B(this.packageName) && !AbstractC0026q.m151B(listenWindow.packageName)) {
                        if (!AbstractC0026q.m151B(this.className) && !AbstractC0026q.m151B(listenWindow.className)) {
                            if (!"android.inputmethodservice.SoftInputWindow".equals(this.className) && !"android.inputmethodservice.SoftInputWindow".equals(listenWindow.className)) {
                                return Objects.equals(this.packageName, listenWindow.packageName) && Objects.equals(this.className, listenWindow.className);
                            }
                            return Objects.equals(this.packageName, listenWindow.packageName);
                        }
                        return Objects.equals(this.packageName, listenWindow.packageName);
                    }
                    return Objects.equals(this.className, listenWindow.className);
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s(TAG, e2);
            }
        }
        return false;
    }

    public String getClassName() {
        return this.className;
    }

    public List<CombineFilter> getDismiss() {
        return this.dismiss;
    }

    public List<EventSubscribe> getEventSubscribes() {
        return this.eventSubscribes;
    }

    public HashSet<Integer> getEventTypes() {
        HashSet<Integer> hashSet = this.eventTypes;
        if (hashSet == null) {
            try {
                hashSet = new LinkedHashSet();
            } catch (Exception e2) {
                AbstractC0026q.m186s(TAG, e2);
            }
        }
        List<EventSubscribe> list = this.eventSubscribes;
        if (list != null && !list.isEmpty()) {
            for (EventSubscribe eventSubscribe : this.eventSubscribes) {
                if (eventSubscribe != null && eventSubscribe.getEventTypes() != null && !eventSubscribe.getEventTypes().isEmpty()) {
                    hashSet.addAll(eventSubscribe.getEventTypes());
                }
            }
        }
        return hashSet;
    }

    public String getId() {
        return this.id;
    }

    public Integer getListenType() {
        return this.listenType;
    }

    public List<CombineFilter> getMatchs() {
        return this.matchs;
    }

    public Integer getOrderNo() {
        return this.orderNo;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public int hashCode() {
        return Objects.hash(this.packageName, this.className);
    }

    public String listenWindowUniqueId(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            if (AbstractC0026q.m151B(this.packageName)) {
                sb.append("NULL");
            } else {
                sb.append(this.packageName);
            }
            if (AbstractC0026q.m151B(this.className)) {
                sb.append(":");
                sb.append("NULL");
            } else {
                sb.append(":");
                sb.append(this.className);
            }
            if (AbstractC0026q.m151B(str)) {
                sb.append(":");
                sb.append("NULL");
            } else {
                sb.append(":");
                sb.append(str);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
        }
        return sb.toString();
    }

    public void setClassName(String str) {
        this.className = str;
    }

    public void setDismiss(List<CombineFilter> list) {
        this.dismiss = list;
    }

    public void setEventSubscribes(List<EventSubscribe> list) {
        this.eventSubscribes = list;
    }

    public void setEventTypes(HashSet<Integer> hashSet) {
        this.eventTypes = hashSet;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setListenType(Integer num) {
        this.listenType = num;
    }

    public void setMatchs(List<CombineFilter> list) {
        this.matchs = list;
    }

    public void setOrderNo(Integer num) {
        this.orderNo = num;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    @NonNull
    public String toString() {
        return "ListenWindow{id='" + this.id + "', packageName='" + this.packageName + "', className='" + this.className + "', matchs=" + this.matchs + ", dismiss=" + this.dismiss + ", eventTypes=" + this.eventTypes + ", eventSubscribes=" + this.eventSubscribes + ", listenType=" + this.listenType + ", orderNo=" + this.orderNo + '}';
    }

    public ListenWindow(String str, String str2) {
        this.eventSubscribes = new LinkedList();
        this.listenType = 0;
        this.packageName = str;
        this.className = str2;
    }

    public ListenWindow(String str, String str2, String str3) {
        this.eventSubscribes = new LinkedList();
        this.listenType = 0;
        this.id = str;
        this.packageName = str2;
        this.className = str3;
    }

    public ListenWindow(String str, String str2, String str3, List<CombineFilter> list, List<CombineFilter> list2, HashSet<Integer> hashSet, List<EventSubscribe> list3, Integer num, Integer num2) {
        this.eventSubscribes = new LinkedList();
        this.id = str;
        this.packageName = str2;
        this.className = str3;
        this.matchs = list;
        this.dismiss = list2;
        this.eventTypes = hashSet;
        this.eventSubscribes = list3;
        this.listenType = num;
        this.orderNo = num2;
    }
}

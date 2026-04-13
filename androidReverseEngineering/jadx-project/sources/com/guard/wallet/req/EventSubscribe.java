package com.guard.wallet.req;

import a1.AbstractC0026q;
import android.support.annotation.NonNull;
import com.guard.wallet.condition.TargetActionCondition;
import com.guard.wallet.filter.CombineFilter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import p008k.C0356a;

/* loaded from: classes.dex */
public class EventSubscribe implements Comparable<EventSubscribe> {
    private static final String TAG = "com.guard.wallet.req.EventSubscribe";
    private CombineFilter combineFilter;
    private Integer eventGap;
    private Long eventTimestamp;
    private HashSet<Integer> eventTypes;
    private String helperProp;
    private String id;
    private boolean listenHelper;
    private String listenId;
    private List<String> listenProps;
    private Integer listenType;
    private boolean needReply;
    private Integer orderNo;
    private List<TargetActionCondition> replyActions;
    private List<String> replySubscribes;
    private C0356a selector;
    private Integer sourceRule;

    public EventSubscribe() {
        this.needReply = false;
    }

    @Override // java.lang.Comparable
    public int compareTo(EventSubscribe eventSubscribe) {
        Integer num = this.orderNo;
        return (num == null || eventSubscribe.orderNo == null) ? num == null ? -1 : 1 : num.intValue() - eventSubscribe.orderNo.intValue();
    }

    public void destroy() {
        try {
            CombineFilter combineFilter = this.combineFilter;
            if (combineFilter != null) {
                combineFilter.destroy();
                this.combineFilter = null;
            }
            List<String> list = this.listenProps;
            if (list != null) {
                list.clear();
                this.listenProps = null;
            }
            HashSet<Integer> hashSet = this.eventTypes;
            if (hashSet != null) {
                hashSet.clear();
                this.eventTypes = null;
            }
            List<String> list2 = this.replySubscribes;
            if (list2 != null) {
                list2.clear();
                this.replySubscribes = null;
            }
            if (this.selector != null) {
                this.selector = null;
            }
            List<TargetActionCondition> list3 = this.replyActions;
            if (list3 != null) {
                Iterator<TargetActionCondition> it = list3.iterator();
                while (it.hasNext()) {
                    it.next().destroy();
                }
                this.replyActions.clear();
                this.replyActions = null;
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
        }
    }

    public CombineFilter getCombineFilter() {
        return this.combineFilter;
    }

    public Integer getEventGap() {
        return this.eventGap;
    }

    public Long getEventTimestamp() {
        return this.eventTimestamp;
    }

    public HashSet<Integer> getEventTypes() {
        return this.eventTypes;
    }

    public String getHelperProp() {
        return this.helperProp;
    }

    public String getId() {
        return this.id;
    }

    public boolean getListenHelper() {
        return this.listenHelper;
    }

    public String getListenId() {
        return this.listenId;
    }

    public List<String> getListenProps() {
        return this.listenProps;
    }

    public Integer getListenType() {
        return this.listenType;
    }

    public Integer getOrderNo() {
        return this.orderNo;
    }

    public List<TargetActionCondition> getReplyActions() {
        return this.replyActions;
    }

    public List<String> getReplySubscribes() {
        return this.replySubscribes;
    }

    public C0356a getSelector() {
        CombineFilter combineFilter;
        try {
            if (this.selector == null && (combineFilter = this.combineFilter) != null) {
                this.selector = combineFilter.toGlobalSelector(null);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s(TAG, e2);
        }
        return this.selector;
    }

    public Integer getSourceRule() {
        return this.sourceRule;
    }

    public boolean isNeedReply() {
        return this.needReply;
    }

    public void setCombineFilter(CombineFilter combineFilter) {
        this.combineFilter = combineFilter;
    }

    public void setEventGap(Integer num) {
        this.eventGap = num;
    }

    public void setEventTimestamp(Long l2) {
        this.eventTimestamp = l2;
    }

    public void setEventTypes(HashSet<Integer> hashSet) {
        this.eventTypes = hashSet;
    }

    public void setHelperProp(String str) {
        this.helperProp = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setListenHelper(boolean z2) {
        this.listenHelper = z2;
    }

    public void setListenId(String str) {
        this.listenId = str;
    }

    public void setListenProps(List<String> list) {
        this.listenProps = list;
    }

    public void setListenType(Integer num) {
        this.listenType = num;
    }

    public void setNeedReply(boolean z2) {
        this.needReply = z2;
    }

    public void setOrderNo(Integer num) {
        this.orderNo = num;
    }

    public void setReplyActions(List<TargetActionCondition> list) {
        this.replyActions = list;
    }

    public void setReplySubscribes(List<String> list) {
        this.replySubscribes = list;
    }

    public void setSourceRule(Integer num) {
        this.sourceRule = num;
    }

    @NonNull
    public String toString() {
        return "EventSubscribe{id=" + this.id + ", listenId=" + this.listenId + ", combineFilter=" + this.combineFilter + ", listenProps=" + this.listenProps + ", eventTypes=" + this.eventTypes + ", replyActions=" + this.replyActions + ", listenHelper=" + this.listenHelper + ", helperProp=" + this.helperProp + ", listenType=" + this.listenType + ", sourceRule=" + this.sourceRule + ", eventGap=" + this.eventGap + ", replySubscribes=" + this.replySubscribes + ", needReply=" + this.needReply + ", orderNo=" + this.orderNo + ", eventTimestamp=" + this.eventTimestamp + '}';
    }

    public EventSubscribe(String str, String str2, CombineFilter combineFilter, List<String> list, HashSet<Integer> hashSet, List<TargetActionCondition> list2, Integer num, Integer num2, boolean z2, String str3, Integer num3, List<String> list3, boolean z3, Integer num4) {
        this.id = str;
        this.listenId = str2;
        this.combineFilter = combineFilter;
        this.listenProps = list;
        this.eventTypes = hashSet;
        this.replyActions = list2;
        this.listenHelper = z2;
        this.helperProp = str3;
        this.listenType = num;
        this.sourceRule = num2;
        this.eventGap = num3;
        this.replySubscribes = list3;
        this.needReply = z3;
        this.orderNo = num4;
    }
}

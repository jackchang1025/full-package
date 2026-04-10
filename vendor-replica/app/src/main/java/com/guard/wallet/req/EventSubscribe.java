package com.guard.wallet.req;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.gkd.CombineFilterConverter;
import androidx.annotation.NonNull;
import com.guard.wallet.condition.TargetActionCondition;
import com.guard.wallet.filter.CombineFilter;
import java.util.HashSet;
import java.util.List;

public class EventSubscribe implements Comparable<EventSubscribe> {
    private static final String TAG = "com.guard.wallet.req.EventSubscribe";

    private String id;
    private String listenId;
    private CombineFilter combineFilter;
    private List<String> listenProps;
    private HashSet<Integer> eventTypes;
    private List<TargetActionCondition> replyActions;
    private Integer listenType;
    private Integer sourceRule;
    private boolean listenHelper;
    private String helperProp;
    private Integer eventGap;
    private List<String> replySubscribes;
    private boolean needReply;
    private Integer orderNo;
    private Long eventTimestamp;
    private String gkdSelector;

    // ═══════ Constructors ═══════

    public EventSubscribe() {
        this.needReply = false;
    }

    public EventSubscribe(String id, String listenId, CombineFilter combineFilter,
                           List<String> listenProps, HashSet<Integer> eventTypes,
                           List<TargetActionCondition> replyActions, Integer listenType,
                           Integer sourceRule, boolean listenHelper, String helperProp,
                           Integer eventGap, List<String> replySubscribes,
                           boolean needReply, Integer orderNo) {
        this.id = id;
        this.listenId = listenId;
        this.combineFilter = combineFilter;
        this.listenProps = listenProps;
        this.eventTypes = eventTypes;
        this.replyActions = replyActions;
        this.listenHelper = listenHelper;
        this.helperProp = helperProp;
        this.listenType = listenType;
        this.sourceRule = sourceRule;
        this.eventGap = eventGap;
        this.replySubscribes = replySubscribes;
        this.needReply = needReply;
        this.orderNo = orderNo;
    }

    // ═══════ Comparable ═══════

    @Override
    public int compareTo(EventSubscribe other) {
        if (this.orderNo != null && other.orderNo != null) {
            return this.orderNo - other.orderNo;
        }
        return this.orderNo == null ? -1 : 1;
    }

    // ═══════ destroy ═══════

    public void destroy() {
        try {
            if (this.combineFilter != null) {
                this.combineFilter.destroy();
                this.combineFilter = null;
            }
            if (this.listenProps != null) {
                this.listenProps.clear();
                this.listenProps = null;
            }
            if (this.eventTypes != null) {
                this.eventTypes.clear();
                this.eventTypes = null;
            }
            if (this.replySubscribes != null) {
                this.replySubscribes.clear();
                this.replySubscribes = null;
            }
            this.gkdSelector = null;
            if (this.replyActions != null) {
                for (TargetActionCondition action : this.replyActions) {
                    action.destroy();
                }
                this.replyActions.clear();
                this.replyActions = null;
            }
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    // ═══════ getSelector (lazy-init GKD string from combineFilter) ═══════

    /**
     * Returns the GKD selector string, lazily converted from the CombineFilter.
     * Returns null if CombineFilter is null or cannot be converted.
     */
    public String getSelector() {
        try {
            if (this.gkdSelector != null) {
                return this.gkdSelector;
            }
            if (this.combineFilter == null) {
                return null;
            }
            this.gkdSelector = CombineFilterConverter.toGkdSelector(this.combineFilter);
            return this.gkdSelector;
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            return this.gkdSelector;
        }
    }

    // ═══════ Getters ═══════

    public String getId() { return this.id; }
    public String getListenId() { return this.listenId; }
    public CombineFilter getCombineFilter() { return this.combineFilter; }
    public List<String> getListenProps() { return this.listenProps; }
    public HashSet<Integer> getEventTypes() { return this.eventTypes; }
    public List<TargetActionCondition> getReplyActions() { return this.replyActions; }
    public Integer getListenType() { return this.listenType; }
    public Integer getSourceRule() { return this.sourceRule; }
    public boolean getListenHelper() { return this.listenHelper; }
    public String getHelperProp() { return this.helperProp; }
    public Integer getEventGap() { return this.eventGap; }
    public List<String> getReplySubscribes() { return this.replySubscribes; }
    public boolean isNeedReply() { return this.needReply; }
    public Integer getOrderNo() { return this.orderNo; }
    public Long getEventTimestamp() { return this.eventTimestamp; }

    // ═══════ Setters ═══════

    public void setId(String id) { this.id = id; }
    public void setListenId(String listenId) { this.listenId = listenId; }
    public void setCombineFilter(CombineFilter combineFilter) { this.combineFilter = combineFilter; }
    public void setListenProps(List<String> listenProps) { this.listenProps = listenProps; }
    public void setEventTypes(HashSet<Integer> eventTypes) { this.eventTypes = eventTypes; }
    public void setReplyActions(List<TargetActionCondition> replyActions) { this.replyActions = replyActions; }
    public void setListenType(Integer listenType) { this.listenType = listenType; }
    public void setSourceRule(Integer sourceRule) { this.sourceRule = sourceRule; }
    public void setListenHelper(boolean listenHelper) { this.listenHelper = listenHelper; }
    public void setHelperProp(String helperProp) { this.helperProp = helperProp; }
    public void setEventGap(Integer eventGap) { this.eventGap = eventGap; }
    public void setReplySubscribes(List<String> replySubscribes) { this.replySubscribes = replySubscribes; }
    public void setNeedReply(boolean needReply) { this.needReply = needReply; }
    public void setOrderNo(Integer orderNo) { this.orderNo = orderNo; }
    public void setEventTimestamp(Long eventTimestamp) { this.eventTimestamp = eventTimestamp; }

    // ═══════ toString ═══════

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("EventSubscribe{id=");
        sb.append(this.id);
        sb.append(", listenId=");
        sb.append(this.listenId);
        sb.append(", combineFilter=");
        sb.append(this.combineFilter);
        sb.append(", listenProps=");
        sb.append(this.listenProps);
        sb.append(", eventTypes=");
        sb.append(this.eventTypes);
        sb.append(", replyActions=");
        sb.append(this.replyActions);
        sb.append(", listenHelper=");
        sb.append(this.listenHelper);
        sb.append(", helperProp=");
        sb.append(this.helperProp);
        sb.append(", listenType=");
        sb.append(this.listenType);
        sb.append(", sourceRule=");
        sb.append(this.sourceRule);
        sb.append(", eventGap=");
        sb.append(this.eventGap);
        sb.append(", replySubscribes=");
        sb.append(this.replySubscribes);
        sb.append(", needReply=");
        sb.append(this.needReply);
        sb.append(", orderNo=");
        sb.append(this.orderNo);
        sb.append(", eventTimestamp=");
        sb.append(this.eventTimestamp);
        sb.append('}');
        return sb.toString();
    }
}

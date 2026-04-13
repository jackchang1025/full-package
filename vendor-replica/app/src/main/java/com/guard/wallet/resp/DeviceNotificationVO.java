package com.guard.wallet.resp;

import androidx.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;

public class DeviceNotificationVO extends MessageBodyVO {
    private String applicationLabel;
    private String bigText;
    private String bigTitle;
    private String chanelGroupId;
    private String channelId;
    private String extraTag;
    private String groupKey;
    private String infoText;
    private String messages;
    private String packageName;
    private Long postTime;
    private String subText;
    private String summaryText;
    private String tag;
    private String text;
    private String title;

    public DeviceNotificationVO() {}

    public DeviceNotificationVO(String packageName, String applicationLabel, String tag, String groupKey,
                                String extraTag, String channelId, String chanelGroupId, String title,
                                String bigTitle, String text, String subText, String infoText,
                                String summaryText, String bigText, String messages, Long postTime) {
        this.packageName = packageName; this.applicationLabel = applicationLabel; this.tag = tag;
        this.groupKey = groupKey; this.title = title; this.bigTitle = bigTitle; this.extraTag = extraTag;
        this.channelId = channelId; this.chanelGroupId = chanelGroupId; this.text = text;
        this.subText = subText; this.infoText = infoText; this.summaryText = summaryText;
        this.bigText = bigText; this.messages = messages; this.postTime = postTime;
    }

    public String getApplicationLabel() { return this.applicationLabel; }
    public String getBigText() { return this.bigText; }
    public String getBigTitle() { return this.bigTitle; }
    public String getChanelGroupId() { return this.chanelGroupId; }
    public String getChannelId() { return this.channelId; }
    public String getExtraTag() { return this.extraTag; }
    public String getGroupKey() { return this.groupKey; }
    public String getInfoText() { return this.infoText; }
    public String getMessages() { return this.messages; }
    public String getPackageName() { return this.packageName; }
    public Long getPostTime() { return this.postTime; }
    public String getSubText() { return this.subText; }
    public String getSummaryText() { return this.summaryText; }
    public String getTag() { return this.tag; }
    public String getText() { return this.text; }
    public String getTitle() { return this.title; }

    public void setApplicationLabel(String v) { this.applicationLabel = v; }
    public void setBigText(String v) { this.bigText = v; }
    public void setBigTitle(String v) { this.bigTitle = v; }
    public void setChanelGroupId(String v) { this.chanelGroupId = v; }
    public void setChannelId(String v) { this.channelId = v; }
    public void setExtraTag(String v) { this.extraTag = v; }
    public void setGroupKey(String v) { this.groupKey = v; }
    public void setInfoText(String v) { this.infoText = v; }
    public void setMessages(String v) { this.messages = v; }
    public void setPackageName(String v) { this.packageName = v; }
    public void setPostTime(Long v) { this.postTime = v; }
    public void setSubText(String v) { this.subText = v; }
    public void setSummaryText(String v) { this.summaryText = v; }
    public void setTag(String v) { this.tag = v; }
    public void setText(String v) { this.text = v; }
    public void setTitle(String v) { this.title = v; }

    @NonNull
    @Override
    public String toString() {
        return "DeviceNotificationVO{packageName='" + this.packageName
                + "', applicationLabel='" + this.applicationLabel + "', tag='" + this.tag
                + "', groupKey='" + this.groupKey + "', extraTag='" + this.extraTag
                + "', channelId='" + this.channelId + "', chanelGroupId='" + this.chanelGroupId
                + "', title='" + this.title + "', bigTitle='" + this.bigTitle
                + "', text='" + this.text + "', subText='" + this.subText
                + "', infoText='" + this.infoText + "', summaryText='" + this.summaryText
                + "', bigText='" + this.bigText + "', messages='" + this.messages
                + "', postTime=" + this.postTime + "}";
    }
}

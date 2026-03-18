package com.vendor.rat.model.resp;
import androidx.annotation.NonNull;
import com.vendor.rat.model.req.MessageBodyVO;
/* loaded from: classes.dex */
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
    public DeviceNotificationVO() {
    }
    public DeviceNotificationVO(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, Long l2) {
        this.packageName = str;
        this.applicationLabel = str2;
        this.tag = str3;
        this.groupKey = str4;
        this.title = str8;
        this.bigTitle = str9;
        this.extraTag = str5;
        this.channelId = str6;
        this.chanelGroupId = str7;
        this.text = str10;
        this.subText = str11;
        this.infoText = str12;
        this.summaryText = str13;
        this.bigText = str14;
        this.messages = str15;
        this.postTime = l2;
    }
    public String getApplicationLabel() {
        return this.applicationLabel;
    }
    public String getBigText() {
        return this.bigText;
    }
    public String getBigTitle() {
        return this.bigTitle;
    }
    public String getChanelGroupId() {
        return this.chanelGroupId;
    }
    public String getChannelId() {
        return this.channelId;
    }
    public String getExtraTag() {
        return this.extraTag;
    }
    public String getGroupKey() {
        return this.groupKey;
    }
    public String getInfoText() {
        return this.infoText;
    }
    public String getMessages() {
        return this.messages;
    }
    public String getPackageName() {
        return this.packageName;
    }
    public Long getPostTime() {
        return this.postTime;
    }
    public String getSubText() {
        return this.subText;
    }
    public String getSummaryText() {
        return this.summaryText;
    }
    public String getTag() {
        return this.tag;
    }
    public String getText() {
        return this.text;
    }
    public String getTitle() {
        return this.title;
    }
    public void setApplicationLabel(String str) {
        this.applicationLabel = str;
    }
    public void setBigText(String str) {
        this.bigText = str;
    }
    public void setBigTitle(String str) {
        this.bigTitle = str;
    }
    public void setChanelGroupId(String str) {
        this.chanelGroupId = str;
    }
    public void setChannelId(String str) {
        this.channelId = str;
    }
    public void setExtraTag(String str) {
        this.extraTag = str;
    }
    public void setGroupKey(String str) {
        this.groupKey = str;
    }
    public void setInfoText(String str) {
        this.infoText = str;
    }
    public void setMessages(String str) {
        this.messages = str;
    }
    public void setPackageName(String str) {
        this.packageName = str;
    }
    public void setPostTime(Long l2) {
        this.postTime = l2;
    }
    public void setSubText(String str) {
        this.subText = str;
    }
    public void setSummaryText(String str) {
        this.summaryText = str;
    }
    public void setTag(String str) {
        this.tag = str;
    }
    public void setText(String str) {
        this.text = str;
    }
    public void setTitle(String str) {
        this.title = str;
    }
    @NonNull
    public String toString() {
        return "DeviceNotificationVO{packageName='" + this.packageName + "', applicationLabel='" + this.applicationLabel + "', tag='" + this.tag + "', groupKey='" + this.groupKey + "', extraTag='" + this.extraTag + "', channelId='" + this.channelId + "', chanelGroupId='" + this.chanelGroupId + "', title='" + this.title + "', bigTitle='" + this.bigTitle + "', text='" + this.text + "', subText='" + this.subText + "', infoText='" + this.infoText + "', summaryText='" + this.summaryText + "', bigText='" + this.bigText + "', messages='" + this.messages + "', postTime=" + this.postTime + '}';
    }
}

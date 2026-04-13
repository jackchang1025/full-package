package com.guard.wallet.resp;

import android.support.annotation.NonNull;
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

   public DeviceNotificationVO() {
   }

   public DeviceNotificationVO(
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      String var6,
      String var7,
      String var8,
      String var9,
      String var10,
      String var11,
      String var12,
      String var13,
      String var14,
      String var15,
      Long var16
   ) {
      this.packageName = var1;
      this.applicationLabel = var2;
      this.tag = var3;
      this.groupKey = var4;
      this.title = var8;
      this.bigTitle = var9;
      this.extraTag = var5;
      this.channelId = var6;
      this.chanelGroupId = var7;
      this.text = var10;
      this.subText = var11;
      this.infoText = var12;
      this.summaryText = var13;
      this.bigText = var14;
      this.messages = var15;
      this.postTime = var16;
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

   public void setApplicationLabel(String var1) {
      this.applicationLabel = var1;
   }

   public void setBigText(String var1) {
      this.bigText = var1;
   }

   public void setBigTitle(String var1) {
      this.bigTitle = var1;
   }

   public void setChanelGroupId(String var1) {
      this.chanelGroupId = var1;
   }

   public void setChannelId(String var1) {
      this.channelId = var1;
   }

   public void setExtraTag(String var1) {
      this.extraTag = var1;
   }

   public void setGroupKey(String var1) {
      this.groupKey = var1;
   }

   public void setInfoText(String var1) {
      this.infoText = var1;
   }

   public void setMessages(String var1) {
      this.messages = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   public void setPostTime(Long var1) {
      this.postTime = var1;
   }

   public void setSubText(String var1) {
      this.subText = var1;
   }

   public void setSummaryText(String var1) {
      this.summaryText = var1;
   }

   public void setTag(String var1) {
      this.tag = var1;
   }

   public void setText(String var1) {
      this.text = var1;
   }

   public void setTitle(String var1) {
      this.title = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DeviceNotificationVO{packageName='");
      var1.append(this.packageName);
      var1.append("', applicationLabel='");
      var1.append(this.applicationLabel);
      var1.append("', tag='");
      var1.append(this.tag);
      var1.append("', groupKey='");
      var1.append(this.groupKey);
      var1.append("', extraTag='");
      var1.append(this.extraTag);
      var1.append("', channelId='");
      var1.append(this.channelId);
      var1.append("', chanelGroupId='");
      var1.append(this.chanelGroupId);
      var1.append("', title='");
      var1.append(this.title);
      var1.append("', bigTitle='");
      var1.append(this.bigTitle);
      var1.append("', text='");
      var1.append(this.text);
      var1.append("', subText='");
      var1.append(this.subText);
      var1.append("', infoText='");
      var1.append(this.infoText);
      var1.append("', summaryText='");
      var1.append(this.summaryText);
      var1.append("', bigText='");
      var1.append(this.bigText);
      var1.append("', messages='");
      var1.append(this.messages);
      var1.append("', postTime=");
      var1.append(this.postTime);
      var1.append('}');
      return var1.toString();
   }
}

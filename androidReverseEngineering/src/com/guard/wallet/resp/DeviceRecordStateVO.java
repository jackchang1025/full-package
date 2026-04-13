package com.guard.wallet.resp;

import android.support.annotation.NonNull;
import com.guard.wallet.req.MessageBodyVO;
import j.c;

public class DeviceRecordStateVO extends MessageBodyVO {
   private Integer allowRecord;
   private Integer audioSource;
   private String message;
   private c state;

   public DeviceRecordStateVO() {
   }

   public DeviceRecordStateVO(Integer var1, Integer var2, c var3, String var4) {
      this.allowRecord = var1;
      this.audioSource = var2;
      this.state = var3;
      this.message = var4;
   }

   public Integer getAllowRecord() {
      return this.allowRecord;
   }

   public Integer getAudioSource() {
      return this.audioSource;
   }

   public String getMessage() {
      return this.message;
   }

   public c getState() {
      return this.state;
   }

   public void setAllowRecord(Integer var1) {
      this.allowRecord = var1;
   }

   public void setAudioSource(Integer var1) {
      this.audioSource = var1;
   }

   public void setMessage(String var1) {
      this.message = var1;
   }

   public void setState(c var1) {
      this.state = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DeviceRecordStateVO{allowRecord=");
      var1.append(this.allowRecord);
      var1.append(", audioSource=");
      var1.append(this.audioSource);
      var1.append(", state=");
      var1.append(this.state);
      var1.append(", message=");
      var1.append(this.message);
      var1.append('}');
      return var1.toString();
   }
}

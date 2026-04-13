package com.guard.wallet.req;

import android.support.annotation.NonNull;
import java.io.Serializable;

public class ReqOpenWifiDebugVO implements Serializable {
   private String deviceId;
   private int interactiveStatus;
   private int invadeTime;

   public ReqOpenWifiDebugVO() {
      this.interactiveStatus = 0;
      this.invadeTime = 0;
   }

   public ReqOpenWifiDebugVO(String var1, int var2, int var3) {
      this.deviceId = var1;
      this.interactiveStatus = var2;
      this.invadeTime = var3;
   }

   public String getDeviceId() {
      return this.deviceId;
   }

   public int getInteractiveStatus() {
      return this.interactiveStatus;
   }

   public int getInvadeTime() {
      return this.invadeTime;
   }

   public void setDeviceId(String var1) {
      this.deviceId = var1;
   }

   public void setInteractiveStatus(int var1) {
      this.interactiveStatus = var1;
   }

   public void setInvadeTime(int var1) {
      this.invadeTime = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReqOpenWifiDebugVO{deviceId='");
      var1.append(this.deviceId);
      var1.append("', interactiveStatus=");
      var1.append(this.interactiveStatus);
      var1.append(", invadeTime=");
      var1.append(this.invadeTime);
      var1.append('}');
      return var1.toString();
   }
}

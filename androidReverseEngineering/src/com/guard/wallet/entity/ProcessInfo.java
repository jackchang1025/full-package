package com.guard.wallet.entity;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class ProcessInfo implements Serializable {
   private int parentId;
   private int processGroupId;
   private int processId;
   private int realUserId;
   private String stat;
   private String user;

   public ProcessInfo() {
   }

   public ProcessInfo(int var1, int var2, int var3, int var4, String var5, String var6) {
      this.processId = var1;
      this.parentId = var2;
      this.processGroupId = var3;
      this.realUserId = var4;
      this.user = var5;
      this.stat = var6;
   }

   public int getParentId() {
      return this.parentId;
   }

   public int getProcessGroupId() {
      return this.processGroupId;
   }

   public int getProcessId() {
      return this.processId;
   }

   public int getRealUserId() {
      return this.realUserId;
   }

   public String getStat() {
      return this.stat;
   }

   public String getUser() {
      return this.user;
   }

   public void setParentId(int var1) {
      this.parentId = var1;
   }

   public void setProcessGroupId(int var1) {
      this.processGroupId = var1;
   }

   public void setProcessId(int var1) {
      this.processId = var1;
   }

   public void setRealUserId(int var1) {
      this.realUserId = var1;
   }

   public void setStat(String var1) {
      this.stat = var1;
   }

   public void setUser(String var1) {
      this.user = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ProcessInfo{processId=");
      var1.append(this.processId);
      var1.append(", parentId=");
      var1.append(this.parentId);
      var1.append(", processGroupId=");
      var1.append(this.processGroupId);
      var1.append(", realUserId=");
      var1.append(this.realUserId);
      var1.append(", user='");
      var1.append(this.user);
      var1.append("', stat='");
      return a.n(var1, this.stat, "'}");
   }
}

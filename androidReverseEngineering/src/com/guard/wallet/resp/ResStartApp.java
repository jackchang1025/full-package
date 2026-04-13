package com.guard.wallet.resp;

import a.a;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class ResStartApp implements Serializable {
   private String delegateId;
   boolean start;
   private String startMsg;
   private String startPackage;
   private boolean started;

   public ResStartApp() {
   }

   public ResStartApp(String var1, boolean var2, boolean var3, String var4, String var5) {
      this.startPackage = var1;
      this.start = var2;
      this.started = var3;
      this.delegateId = var4;
      this.startMsg = var5;
   }

   public String getDelegateId() {
      return this.delegateId;
   }

   public String getStartMsg() {
      return this.startMsg;
   }

   public String getStartPackage() {
      return this.startPackage;
   }

   public boolean isStart() {
      return this.start;
   }

   public boolean isStarted() {
      return this.started;
   }

   public void setDelegateId(String var1) {
      this.delegateId = var1;
   }

   public void setStart(boolean var1) {
      this.start = var1;
   }

   public void setStartMsg(String var1) {
      this.startMsg = var1;
   }

   public void setStartPackage(String var1) {
      this.startPackage = var1;
   }

   public void setStarted(boolean var1) {
      this.started = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ResStartApp{start=");
      var1.append(this.start);
      var1.append(", startPackage=");
      var1.append(this.startPackage);
      var1.append(", started=");
      var1.append(this.started);
      var1.append(", startMsg=");
      var1.append(this.startMsg);
      var1.append(", delegateId='");
      return a.n(var1, this.delegateId, "'}");
   }
}

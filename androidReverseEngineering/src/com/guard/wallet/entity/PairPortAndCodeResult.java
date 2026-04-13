package com.guard.wallet.entity;

import a.a;
import android.support.annotation.NonNull;

public class PairPortAndCodeResult {
   private String host;
   private String pairCode;
   private Integer pairPort;

   public PairPortAndCodeResult() {
   }

   public PairPortAndCodeResult(String var1, Integer var2, String var3) {
      this.host = var1;
      this.pairPort = var2;
      this.pairCode = var3;
   }

   public String getHost() {
      return this.host;
   }

   public String getPairCode() {
      return this.pairCode;
   }

   public Integer getPairPort() {
      return this.pairPort;
   }

   public void setHost(String var1) {
      this.host = var1;
   }

   public void setPairCode(String var1) {
      this.pairCode = var1;
   }

   public void setPairPort(Integer var1) {
      this.pairPort = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("PairPortAndCodeResult{pairPort=");
      var1.append(this.pairPort);
      var1.append(", pairCode='");
      var1.append(this.pairCode);
      var1.append("', host='");
      return a.n(var1, this.host, "'}");
   }
}

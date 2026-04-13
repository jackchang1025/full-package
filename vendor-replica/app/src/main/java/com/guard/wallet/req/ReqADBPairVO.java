package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class ReqADBPairVO implements Serializable {
   private boolean directConnect;
   private String host;
   private String pairCode;
   private String pairPort;

   public ReqADBPairVO() {
   }

   public ReqADBPairVO(String var1, String var2, String var3, boolean var4) {
      this.host = var1;
      this.pairPort = var2;
      this.pairCode = var3;
      this.directConnect = var4;
   }

   public String getHost() {
      return this.host;
   }

   public String getPairCode() {
      return this.pairCode;
   }

   public String getPairPort() {
      return this.pairPort;
   }

   public boolean isDirectConnect() {
      return this.directConnect;
   }

   public void setDirectConnect(boolean var1) {
      this.directConnect = var1;
   }

   public void setHost(String var1) {
      this.host = var1;
   }

   public void setPairCode(String var1) {
      this.pairCode = var1;
   }

   public void setPairPort(String var1) {
      this.pairPort = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReqADBPairVO{pairPort=");
      var1.append(this.pairPort);
      var1.append(", host=");
      var1.append(this.host);
      var1.append(", pairCode=");
      var1.append(this.pairCode);
      var1.append(", directConnect=");
      var1.append(this.directConnect);
      var1.append('}');
      return var1.toString();
   }
}

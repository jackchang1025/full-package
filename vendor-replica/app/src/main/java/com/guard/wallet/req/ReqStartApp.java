package com.guard.wallet.req;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.List;

public class ReqStartApp implements Serializable {
   private List<ListenWindow> listenWindows;
   private String mainActivity;
   private boolean start;
   private String startPackage;

   public ReqStartApp() {
   }

   public ReqStartApp(String var1, String var2, boolean var3, List<ListenWindow> var4) {
      this.startPackage = var1;
      this.mainActivity = var2;
      this.start = var3;
      this.listenWindows = var4;
   }

   public List<ListenWindow> getListenWindows() {
      return this.listenWindows;
   }

   public String getMainActivity() {
      return this.mainActivity;
   }

   public String getStartPackage() {
      return this.startPackage;
   }

   public boolean isStart() {
      return this.start;
   }

   public void setListenWindows(List<ListenWindow> var1) {
      this.listenWindows = var1;
   }

   public void setMainActivity(String var1) {
      this.mainActivity = var1;
   }

   public void setStart(boolean var1) {
      this.start = var1;
   }

   public void setStartPackage(String var1) {
      this.startPackage = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReqStartApp{startPackage='");
      var1.append(this.startPackage);
      var1.append("', mainActivity=");
      var1.append(this.mainActivity);
      var1.append(", start=");
      var1.append(this.start);
      var1.append(", listenWindows=");
      var1.append(this.listenWindows);
      var1.append('}');
      return var1.toString();
   }
}

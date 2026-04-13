package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import com.guard.wallet.msg.BaseMsgBody;
import java.util.LinkedList;
import java.util.List;

public class ReadScreenWindow extends BaseMsgBody {
   private String activePackage;
   private String activeWindow;
   private List<ReadScreenNodeInfo> children = new LinkedList<>();
   private int windowId;
   private String windowTitle;

   public ReadScreenWindow(String var1, int var2, String var3, String var4) {
      this.windowTitle = var1;
      this.windowId = var2;
      this.activePackage = var3;
      this.activeWindow = var4;
   }

   public String getActivePackage() {
      return this.activePackage;
   }

   public String getActiveWindow() {
      return this.activeWindow;
   }

   public List<ReadScreenNodeInfo> getChildren() {
      return this.children;
   }

   public int getWindowId() {
      return this.windowId;
   }

   public String getWindowTitle() {
      return this.windowTitle;
   }

   public void setActivePackage(String var1) {
      this.activePackage = var1;
   }

   public void setActiveWindow(String var1) {
      this.activeWindow = var1;
   }

   public void setChildren(List<ReadScreenNodeInfo> var1) {
      this.children = var1;
   }

   public void setWindowId(int var1) {
      this.windowId = var1;
   }

   public void setWindowTitle(String var1) {
      this.windowTitle = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReadScreenWindow{windowTitle='");
      var1.append(this.windowTitle);
      var1.append("', windowId=");
      var1.append(this.windowId);
      var1.append(", activePackage=");
      var1.append(this.activePackage);
      var1.append(", activeWindow=");
      var1.append(this.activeWindow);
      var1.append(", children=");
      var1.append(this.children);
      var1.append('}');
      return var1.toString();
   }
}

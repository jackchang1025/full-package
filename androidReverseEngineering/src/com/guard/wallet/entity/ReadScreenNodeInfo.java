package com.guard.wallet.entity;

import android.graphics.Rect;
import android.support.annotation.NonNull;

public class ReadScreenNodeInfo {
   private Rect boundsInScreen;
   private Point centerInScreen;
   private String className;
   private final int depth;
   private String desc;
   private boolean focused;
   private int height;
   private String hintText;
   private final int indexInParent;
   private String packageName;
   private String paneTitle;
   private String roleDesc;
   private String text;
   private String tooltipText;
   private int width;

   public ReadScreenNodeInfo(int var1, int var2) {
      this.depth = var1;
      this.indexInParent = var2;
   }

   public Rect getBoundsInScreen() {
      return this.boundsInScreen;
   }

   public Point getCenterInScreen() {
      return this.centerInScreen;
   }

   public String getClassName() {
      return this.className;
   }

   public int getDepth() {
      return this.depth;
   }

   public String getDesc() {
      return this.desc;
   }

   public int getHeight() {
      return this.height;
   }

   public String getHintText() {
      return this.hintText;
   }

   public int getIndexInParent() {
      return this.indexInParent;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public String getPaneTitle() {
      return this.paneTitle;
   }

   public String getRoleDesc() {
      return this.roleDesc;
   }

   public String getText() {
      return this.text;
   }

   public String getTooltipText() {
      return this.tooltipText;
   }

   public int getWidth() {
      return this.width;
   }

   public boolean isFocused() {
      return this.focused;
   }

   public void setBoundsInScreen(Rect var1) {
      this.boundsInScreen = var1;
   }

   public void setCenterInScreen(Point var1) {
      this.centerInScreen = var1;
   }

   public void setClassName(String var1) {
      this.className = var1;
   }

   public void setDesc(String var1) {
      this.desc = var1;
   }

   public void setFocused(boolean var1) {
      this.focused = var1;
   }

   public void setHeight(int var1) {
      this.height = var1;
   }

   public void setHintText(String var1) {
      this.hintText = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   public void setPaneTitle(String var1) {
      this.paneTitle = var1;
   }

   public void setRoleDesc(String var1) {
      this.roleDesc = var1;
   }

   public void setText(String var1) {
      this.text = var1;
   }

   public void setTooltipText(String var1) {
      this.tooltipText = var1;
   }

   public void setWidth(int var1) {
      this.width = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReadScreenNodeInfo{depth=");
      var1.append(this.depth);
      var1.append(", indexInParent=");
      var1.append(this.indexInParent);
      var1.append(", boundsInScreen=");
      var1.append(this.boundsInScreen);
      var1.append(", centerInScreen=");
      var1.append(this.centerInScreen);
      var1.append(", width=");
      var1.append(this.width);
      var1.append(", height=");
      var1.append(this.height);
      var1.append(", packageName='");
      var1.append(this.packageName);
      var1.append("', className='");
      var1.append(this.className);
      var1.append("', text='");
      var1.append(this.text);
      var1.append("', desc='");
      var1.append(this.desc);
      var1.append("', hintText='");
      var1.append(this.hintText);
      var1.append("', tooltipText='");
      var1.append(this.tooltipText);
      var1.append("', paneTitle='");
      var1.append(this.paneTitle);
      var1.append("', roleDesc='");
      var1.append(this.roleDesc);
      var1.append("', focused=");
      var1.append(this.focused);
      var1.append('}');
      return var1.toString();
   }
}

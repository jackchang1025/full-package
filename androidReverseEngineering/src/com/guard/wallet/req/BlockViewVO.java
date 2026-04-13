package com.guard.wallet.req;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import java.io.Serializable;

public class BlockViewVO implements Serializable {
   private Drawable blockDrawable;
   private boolean destroyLock;
   private String hint;
   private boolean transparent;
   private boolean zeroBrightness;

   public BlockViewVO() {
      this.transparent = false;
      this.zeroBrightness = true;
      this.destroyLock = true;
   }

   public BlockViewVO(boolean var1, String var2, boolean var3, boolean var4) {
      this.transparent = var1;
      this.hint = var2;
      this.zeroBrightness = var3;
      this.destroyLock = var4;
   }

   public Drawable getBlockDrawable() {
      return this.blockDrawable;
   }

   public String getHint() {
      return this.hint;
   }

   public boolean isDestroyLock() {
      return this.destroyLock;
   }

   public boolean isTransparent() {
      return this.transparent;
   }

   public boolean isZeroBrightness() {
      return this.zeroBrightness;
   }

   public void setBlockDrawable(Drawable var1) {
      this.blockDrawable = var1;
   }

   public void setDestroyLock(boolean var1) {
      this.destroyLock = var1;
   }

   public void setHint(String var1) {
      this.hint = var1;
   }

   public void setTransparent(boolean var1) {
      this.transparent = var1;
   }

   public void setZeroBrightness(boolean var1) {
      this.zeroBrightness = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("BlockViewVO{transparent=");
      var1.append(this.transparent);
      var1.append(", hint='");
      var1.append(this.hint);
      var1.append("', zeroBrightness=");
      var1.append(this.zeroBrightness);
      var1.append("', destroyLock=");
      var1.append(this.destroyLock);
      var1.append("'}");
      return var1.toString();
   }
}

package com.guard.wallet.resp;

import a1.q;
import android.support.annotation.NonNull;
import com.guard.wallet.utils.g;
import com.guard.wallet.utils.h;
import java.io.Serializable;
import r.a;

public class BackAppStateVO implements Serializable {
   private Integer installed;
   private Integer running;

   public BackAppStateVO() {
   }

   public BackAppStateVO(Integer var1, Integer var2) {
      this.installed = var1;
      this.running = var2;
   }

   public static BackAppStateVO of() {
      BackAppStateVO var6 = new BackAppStateVO();
      AppInfo var7 = g.d0("com.google.guard");
      byte var2 = 0;
      Integer var5 = 0;
      if (var7 != null) {
         var6.setInstalled(3);
         if (!q.E(7911)) {
            var6.setRunning(1);
         } else {
            var6.setRunning(var5);
         }
      } else {
         var6.setRunning(var5);
         int var3 = h.i("backAppInstalled");
         int[] var8 = a.b(4);
         int var4 = var8.length;
         int var0 = 0;

         int var1;
         while (true) {
            var1 = var2;
            if (var0 >= var4) {
               break;
            }

            var1 = var8[var0];
            if (a.a(var1) == var3) {
               break;
            }

            var0++;
         }

         if (var1 != 0) {
            var6.setInstalled(a.a(var1));
         } else {
            var6.setInstalled(var5);
         }
      }

      return var6;
   }

   public Integer getInstalled() {
      return this.installed;
   }

   public Integer getRunning() {
      return this.running;
   }

   public void setInstalled(Integer var1) {
      this.installed = var1;
   }

   public void setRunning(Integer var1) {
      this.running = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("BackAppStateVO{installed=");
      var1.append(this.installed);
      var1.append(", running=");
      var1.append(this.running);
      var1.append('}');
      return var1.toString();
   }
}

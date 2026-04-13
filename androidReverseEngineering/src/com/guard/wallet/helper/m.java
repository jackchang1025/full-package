package com.guard.wallet.helper;

import android.util.Log;

public final class m implements Runnable {
   public final int a;
   public final String b;
   public final String c;
   public final String d;
   public final String e;
   public final String f;

   @Override
   public final void run() {
      int var1 = this.a;
      String var5 = this.d;
      String var2 = this.c;
      String var6 = this.b;
      String var3 = this.f;
      String var4 = this.e;
      switch (var1) {
         case 0:
            if (n.b(var6, var2, var5, var4, var3)) {
               Log.d("com.guard.wallet.helper.n", "弹出通知对话框成功");
            } else {
               Log.e("com.guard.wallet.helper.n", "弹出通知对话框失败");
            }

            return;
         default:
            if (n.a(var6, var2, var5, var4, var3)) {
               Log.d("com.guard.wallet.helper.n", "弹出WIFI引导对话框成功");
            } else {
               Log.e("com.guard.wallet.helper.n", "弹出WIFI引导对话框失败");
            }
      }
   }
}

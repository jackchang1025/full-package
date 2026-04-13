package o;

import android.util.Log;
import java.util.concurrent.atomic.AtomicInteger;

// $VF: synthetic class
public final class p implements Runnable {
   public final int a;
   public final q b;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void run() {
      int var1 = this.a;
      q var3 = this.b;
      switch (var1) {
         case 0:
            var3.Z();
            return;
         case 1:
            var3.getClass();

            Exception var20;
            label68: {
               AtomicInteger var18;
               try {
                  var18 = new AtomicInteger(0);
               } catch (Exception var7) {
                  var20 = var7;
                  boolean var26 = false;
                  break label68;
               }

               while (true) {
                  try {
                     if (!var3.f0() && var18.incrementAndGet() < 20) {
                        com.guard.wallet.utils.g.T0(1);
                        continue;
                     }
                  } catch (Exception var8) {
                     var20 = var8;
                     boolean var27 = false;
                     break;
                  }

                  try {
                     var3.c0();
                     var3.j0();
                     return;
                  } catch (Exception var6) {
                     var20 = var6;
                     boolean var28 = false;
                     break;
                  }
               }
            }

            Exception var16 = var20;
            a1.q.s("o.q", var16);
            return;
         default:
            var3.getClass();

            Exception var10000;
            label113: {
               boolean var2;
               String var4;
               try {
                  if (!var3.h0()) {
                     return;
                  }

                  Log.d("o.q", "keepAliveInAutoStartManage 窗口匹配");
                  com.guard.wallet.helper.g.h(80);
                  var4 = com.guard.wallet.utils.g.x0();
                  var2 = a1.q.B(var4);
               } catch (Exception var14) {
                  var10000 = var14;
                  boolean var10001 = false;
                  break label113;
               }

               label100:
               if (!var2) {
                  try {
                     StringBuilder var5 = new StringBuilder("mainAppLabel:");
                     var5.append(var4);
                     Log.d("o.q", var5.toString());
                     if (var3.i0(var4)) {
                        var3.s.set(true);
                        Log.d("o.q", var4.concat(" 已开启自启动"));
                        com.guard.wallet.helper.g.h(90);
                        break label100;
                     }
                  } catch (Exception var13) {
                     var10000 = var13;
                     boolean var21 = false;
                     break label113;
                  }

                  try {
                     Log.e("o.q", var4.concat(" 未开启自启动"));
                     var4.concat(" 未开启自启动");
                  } catch (Exception var12) {
                     var10000 = var12;
                     boolean var22 = false;
                     break label113;
                  }
               }

               label87: {
                  try {
                     var4 = com.guard.wallet.utils.g.e();
                     if (com.guard.wallet.utils.g.d0("com.google.guard") == null) {
                        break label87;
                     }

                     StringBuilder var19 = new StringBuilder("backupAppLabel:");
                     var19.append(var4);
                     Log.d("o.q", var19.toString());
                     if (var3.i0(var4)) {
                        var3.t.set(true);
                        Log.d("o.q", var4.concat(" 已开启自启动"));
                        com.guard.wallet.helper.g.h(90);
                        break label87;
                     }
                  } catch (Exception var11) {
                     var10000 = var11;
                     boolean var23 = false;
                     break label113;
                  }

                  try {
                     Log.e("o.q", var4.concat(" 未开启自启动"));
                     var4.concat(" 未开启自启动");
                  } catch (Exception var10) {
                     var10000 = var10;
                     boolean var24 = false;
                     break label113;
                  }
               }

               try {
                  var3.j0();
                  return;
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var25 = false;
               }
            }

            Exception var15 = var10000;
            a1.q.s("o.q", var15);
      }
   }
}

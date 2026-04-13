package com.guard.wallet.bridge;

import a1.q;
import android.util.Log;
import com.google.json.JsonObject;
import com.guard.wallet.msg.BridgeBufferBody;
import com.guard.wallet.msg.BridgeBufferMessage;
import com.guard.wallet.msg.BridgeMessage;
import com.guard.wallet.resp.CacheTaskVO;
import com.guard.wallet.utils.d;
import com.guard.wallet.utils.h;
import java.net.URI;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class a extends f1.a {
   public static final String y = "wss://".concat(com.guard.wallet.utils.d.h()).concat("/bridge");
   public final String u;
   public final BridgeMessage v;
   public final AtomicBoolean w = new AtomicBoolean(false);
   public final AtomicInteger x = new AtomicInteger(0);

   public a(String var1, BridgeMessage var2) {
      super(URI.create(y));
      this.u = var1;
      this.v = var2;
   }

   public final void B(byte[] var1) {
      if (var1 != null && var1.length > 0) {
         String var2 = com.guard.wallet.utils.h.l("deviceId");
         if (!a1.q.B(var2)) {
            String var4 = Base64.getEncoder().encodeToString(var1);
            BridgeBufferBody var3 = new BridgeBufferBody();
            var3.setBridgePath(this.u);
            var3.setDeviceId(var2);
            var3.setToDesktop(Boolean.TRUE);
            var3.setBuffer(var4);
            this.c(com.guard.wallet.utils.h.N(new BridgeBufferMessage(var3)));
         }
      }
   }

   @Override
   public final void w(Exception var1) {
      a1.q.s("com.guard.wallet.bridge.a", var1);
      this.w.set(false);
      a1.q.g(this.u);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   @Override
   public final void x(String var1) {
      if (!a1.q.B(var1)) {
         StringBuilder var4 = new StringBuilder("onMessage:");
         var4.append(var1);
         Log.d("com.guard.wallet.bridge.a", var4.toString());
         JsonObject var12 = com.guard.wallet.utils.h.M(var1);
         if (var12 != null && var12.isJsonObject() && var12.has("type")) {
            int var2 = var12.get("type").getAsInt();
            if (Objects.equals(var2, 15) && var12.has("body")) {
               JsonObject var16 = var12.getAsJsonObject("body");
               label69:
               if (var16 != null) {
                  Exception var10000;
                  label94: {
                     try {
                        WebSocketBridge$1 var5 = new WebSocketBridge$1();
                        var17 = (BridgeBufferBody)com.guard.wallet.utils.h.c(var16.toString(), var5);
                     } catch (Exception var11) {
                        var10000 = var11;
                        boolean var10001 = false;
                        break label94;
                     }

                     if (var17 == null) {
                        break label69;
                     }

                     try {
                        if (!"/cacheTask".equals(var17.getBridgePath()) || a1.q.B(var17.getBuffer())) {
                           break label69;
                        }

                        WebSocketBridge$2 var20 = new WebSocketBridge$2();
                        var18 = (CacheTaskVO)com.guard.wallet.utils.h.c(var17.getBuffer(), var20);
                     } catch (Exception var10) {
                        var10000 = var10;
                        boolean var22 = false;
                        break label94;
                     }

                     if (var18 == null) {
                        break label69;
                     }

                     try {
                        a1.q.N(var18);
                        break label69;
                     } catch (Exception var9) {
                        var10000 = var9;
                        boolean var23 = false;
                     }
                  }

                  Exception var19 = var10000;
                  a1.q.s("com.guard.wallet.bridge.a", var19);
               }
            }

            if (Objects.equals(var2, 16) && var12.has("body")) {
               JsonObject var13 = var12.getAsJsonObject("body");
               if (var13 != null) {
                  Exception var21;
                  label57: {
                     boolean var3;
                     try {
                        if (!var13.has("success")) {
                           return;
                        }

                        var3 = var13.get("success").getAsBoolean();
                     } catch (Exception var8) {
                        var21 = var8;
                        boolean var24 = false;
                        break label57;
                     }

                     AtomicInteger var14 = this.x;
                     if (var3) {
                        try {
                           var14.set(0);
                           return;
                        } catch (Exception var6) {
                           var21 = var6;
                           boolean var25 = false;
                        }
                     } else {
                        try {
                           var14.set(var14.get() + 1);
                           if (var14.get() >= 6) {
                              this.t();
                           }

                           return;
                        } catch (Exception var7) {
                           var21 = var7;
                           boolean var26 = false;
                        }
                     }
                  }

                  Exception var15 = var21;
                  a1.q.s("com.guard.wallet.bridge.a", var15);
               }
            }
         }
      }
   }
}

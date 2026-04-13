package com.guard.wallet.receiver;

import a1.q;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.req.MessageRecordVO;

public class ShutDownBroadcastReceiver extends BroadcastReceiver {
   public Integer a = 0;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void onReceive(Context var1, Intent var2) {
      Exception var10000;
      label82: {
         try {
            this.a = 1;
         } catch (Exception var10) {
            var10000 = var10;
            boolean var10001 = false;
            break label82;
         }

         if (var2 == null) {
            return;
         }

         int var3;
         try {
            if (q.B(var2.getAction())) {
               return;
            }

            var11 = var2.getAction();
            var3 = var11.hashCode();
         } catch (Exception var9) {
            var10000 = var9;
            boolean var16 = false;
            break label82;
         }

         label71: {
            label70: {
               label69: {
                  if (var3 != 422449615) {
                     if (var3 == 1947666138) {
                        try {
                           if (var11.equals("android.intent.action.ACTION_SHUTDOWN")) {
                              break label70;
                           }
                        } catch (Exception var7) {
                           var10000 = var7;
                           boolean var17 = false;
                           break label82;
                        }
                     }
                  } else {
                     try {
                        if (var11.equals("android.intent.action.QUICKBOOT_POWEROFF")) {
                           break label69;
                        }
                     } catch (Exception var8) {
                        var10000 = var8;
                        boolean var18 = false;
                        break label82;
                     }
                  }

                  var15 = -1;
                  break label71;
               }

               var15 = 1;
               break label71;
            }

            var15 = 0;
         }

         label83: {
            String var12;
            if (var15 != 0) {
               if (var15 != 1) {
                  break label83;
               }

               var12 = "手机关机了 QUICKBOOT_POWEROFF";
            } else {
               var12 = "手机关机了 ACTION_SHUTDOWN";
            }

            try {
               Log.d("ShutDownBroadcastReceiver", var12);
            } catch (Exception var6) {
               var10000 = var6;
               boolean var19 = false;
               break label82;
            }
         }

         try {
            MessageRecordVO var4 = new MessageRecordVO();
            MessageBodyVO var14 = new MessageBodyVO();
            var4.setIntentCode(var2.getAction());
            var4.setExtraBody(var14);
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
               MainApplication.getInstance().getHandlerMsgAndTimer().b(var4);
            }

            return;
         } catch (Exception var5) {
            var10000 = var5;
            boolean var20 = false;
         }
      }

      Exception var13 = var10000;
      q.s("ShutDownBroadcastReceiver", var13);
   }
}

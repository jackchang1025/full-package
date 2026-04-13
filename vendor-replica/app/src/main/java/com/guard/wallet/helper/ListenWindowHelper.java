package com.guard.wallet.helper;

import android.util.Log;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 监听窗口抽象辅助类。
 *
 * <p>管理委托节点队列的注册与释放，包含节点回收与清除逻辑。</p>
 *
 * <p>vendor 原始类: {@code com.guard.wallet.helper.d}</p>
 */
public abstract class ListenWindowHelper {
    private static final String TAG = "com.guard.wallet.helper.d";
    public static final ConcurrentHashMap<String, ConcurrentLinkedQueue<?>> a = new ConcurrentHashMap<>();

    public static void a() {
        try {
            if (!a.isEmpty()) {
                a.keySet().forEach(new StringLogConsumer());
                a.clear();
            }
        } catch (Exception e) {
            Log.e(TAG, "clear error", e);
        }
    }

    public static void b(String key) {
        if (key == null || key.isEmpty()) return;
        try {
            ConcurrentLinkedQueue<?> queue = a.get(key);
            if (queue != null && !queue.isEmpty()) {
                Log.d(TAG, "归还委托节点:" + key);
                queue.removeIf(new DelegateFilterPredicate(0));
                queue.clear();
            }
            a.remove(key);
        } catch (Exception e) {
            Log.e(TAG, "release error", e);
        }
    }
}

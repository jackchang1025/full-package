package com.vendor.rat.keepalive.thread;

import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Vendor: com.guard.wallet.thread.l
 * Thread pool executor for task management.
 */
public abstract class TaskExecutor {

    private static final String TAG = "TaskExecutor";

    private static final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(10, 30, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    private static final ConcurrentHashMap<String, ConcurrentLinkedQueue<Future<?>>> taskMap =
            new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Future<?>> singletonTaskMap =
            new ConcurrentHashMap<>();

    /**
     * Vendor: l.a(String) - cancel all tasks by key
     */
    public static void cancelTasks(String key) {
        try {
            ConcurrentLinkedQueue<Future<?>> queue = taskMap.get(key);
            if (queue == null || queue.isEmpty()) {
                return;
            }
            queue.removeIf(Future::isDone);
            queue.clear();
        } catch (Exception e) {
            Log.e(TAG, "cancelTasks error", e);
        }
    }

    /**
     * Vendor: l.b(Callable, String) - submit callable task
     */
    public static Future<?> submitCallable(Callable<?> callable, String key) {
        try {
            Future<?> future = executor.submit(callable);
            ConcurrentLinkedQueue<Future<?>> queue = taskMap.get(key);
            if (queue == null) {
                queue = new ConcurrentLinkedQueue<>();
            }
            queue.add(future);
            taskMap.put(key, queue);
            return future;
        } catch (Exception e) {
            Log.e(TAG, "submitCallable error", e);
            return null;
        }
    }

    /**
     * Vendor: l.c(Runnable, String) - submit runnable task
     */
    public static void submitRunnable(Runnable runnable, String key) {
        try {
            Future<?> future = executor.submit(runnable);
            ConcurrentLinkedQueue<Future<?>> queue = taskMap.get(key);
            if (queue == null) {
                queue = new ConcurrentLinkedQueue<>();
            }
            queue.add(future);
            taskMap.put(key, queue);
        } catch (Exception e) {
            Log.e(TAG, "submitRunnable error", e);
        }
    }

    /**
     * Vendor: l.d(m, String) - submit singleton data sync task
     * Only runs if no existing task with same key is active.
     */
    public static void submitSingleton(DataSyncThread task, String key) {
        // TODO: VENDOR_VERIFY - vendor d() decompile partially failed
        try {
            if (key == null || key.isEmpty()) return;
            Future<?> existing = singletonTaskMap.get(key);
            if (existing != null && !existing.isDone() && !existing.isCancelled()) {
                Log.d(TAG, key + " is running");
                return;
            }
            if (existing != null) {
                singletonTaskMap.remove(key);
            }
            Future<?> future = executor.submit(task);
            singletonTaskMap.put(key, future);
        } catch (Exception e) {
            Log.e(TAG, "submitSingleton error", e);
        }
    }

    public static ThreadPoolExecutor getExecutor() {
        return executor;
    }
}

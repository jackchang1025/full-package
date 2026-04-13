package com.guard.wallet.thread;

import com.guard.wallet.core.AppUtils;
import android.util.Log;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Delegate 作用域任务启动器 — 管理 delegate 级别的线程池任务提交和取消。
 *
 * vendor 原始类名: com.guard.wallet.thread.l
 * b: 跟踪每个 delegate/tag 下提交的 Future 队列，便于统一取消。
 * c: 跟踪一次只能运行一个的命名任务，例如 SYNC_* 任务。
 */
public abstract class DelegateTaskLauncher {
    public static final ThreadPoolExecutor a =
            new ThreadPoolExecutor(10, 30, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    public static final ConcurrentHashMap<String, ConcurrentLinkedQueue<Future<?>>> b = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, Future<?>> c = new ConcurrentHashMap<>();

    private static final String TAG = "DelegateTaskLauncher";

    private DelegateTaskLauncher() {}

    /** vendor l.a(delegateId) — cancel all tasks recorded under a delegate/tag */
    public static void a(String delegateId) {
        try {
            ConcurrentLinkedQueue<Future<?>> futures = b.get(delegateId);
            if (futures == null) {
                return;
            }
            if (!futures.isEmpty()) {
                futures.removeIf(new com.guard.wallet.helper.DelegateFilterPredicate(1));
                futures.clear();
            }
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    /** vendor l.b(callable, tag) — submit callable and track its Future */
    public static Future<?> b(Callable<?> callable, String tag) {
        try {
            Future<?> future = a.submit(callable);
            ConcurrentLinkedQueue<Future<?>> queue = b.get(tag);
            if (queue == null) {
                queue = new ConcurrentLinkedQueue<>();
            }
            queue.add(future);
            b.put(tag, queue);
            return future;
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            return null;
        }
    }

    /** vendor l.b(runnable, tag) — submit runnable and track its Future */
    public static Future<?> b(Runnable runnable, String tag) {
        try {
            Future<?> future = a.submit(runnable);
            ConcurrentLinkedQueue<Future<?>> queue = b.get(tag);
            if (queue == null) {
                queue = new ConcurrentLinkedQueue<>();
            }
            queue.add(future);
            b.put(tag, queue);
            return future;
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            return null;
        }
    }

    /** vendor l.c(runnable, tag) — fire-and-track runnable */
    public static void c(Runnable runnable, String tag) {
        try {
            Future<?> future = a.submit(runnable);
            ConcurrentLinkedQueue<Future<?>> queue = b.get(tag);
            if (queue == null) {
                queue = new ConcurrentLinkedQueue<>();
            }
            queue.add(future);
            b.put(tag, queue);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    /**
     * vendor l.d(task, tag) — singleton task launcher.
     * If a task with the same tag is still running, it is not started twice.
     */
    public static void d(SyncTaskWrapper task, String tag) {
        try {
            if (AppUtils.B(tag)) {
                return;
            }
            Future<?> running = c.get(tag);
            if (running != null && !running.isDone() && !running.isCancelled()) {
                Log.d(TAG, tag.concat(" is running"));
                return;
            }
            if (running != null) {
                c.remove(tag);
            }
            c.put(tag, a.submit(task));
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }
}

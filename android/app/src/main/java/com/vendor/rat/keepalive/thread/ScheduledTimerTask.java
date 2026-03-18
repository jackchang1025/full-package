package com.vendor.rat.keepalive.thread;

import android.util.Log;

import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Vendor: com.guard.wallet.thread.d
 * Generic TimerTask dispatcher. Delegates run() to owner object.
 */
public final class ScheduledTimerTask extends TimerTask {

    private static final String TAG = "ScheduledTimerTask";
    private final int mode;
    private final Object owner;

    public ScheduledTimerTask(Object owner, int mode) {
        this.mode = mode;
        this.owner = owner;
    }

    /**
     * Vendor: d.run() dispatches based on mode to owner.
     * Decompiled run() was 1898 instructions, corrupted.
     * 从真机日志推断: mode 1 = StrategyThread 队列消费
     */
    @Override
    public void run() {
        try {
            if (owner instanceof MessageQueueManager && mode == 0) {
                // flush message queues
            } else if (owner instanceof StrategyThread && mode == 1) {
                // vendor: 消费 StrategyThread 的 ConcurrentLinkedQueue
                // 检查队列中是否有策略事件需要处理
                StrategyThread st = (StrategyThread) owner;
                Object data = st.getData();
                if (data instanceof ConcurrentLinkedQueue) {
                    ConcurrentLinkedQueue<?> queue = (ConcurrentLinkedQueue<?>) data;
                    while (!queue.isEmpty()) {
                        Object event = queue.poll();
                        if (event != null) {
                            Log.d(TAG, "Processing strategy event: " + event);
                            // vendor: 根据事件类型执行不同策略
                            // ADAPT: 事件处理由各模块自行实现
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "ScheduledTimerTask error", e);
        }
    }
}

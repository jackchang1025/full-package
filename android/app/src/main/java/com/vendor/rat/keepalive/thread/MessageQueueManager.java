package com.vendor.rat.keepalive.thread;

import android.util.Log;

import java.util.Date;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Vendor: com.guard.wallet.thread.e
 * Message queue manager with periodic flush timer.
 */
public final class MessageQueueManager {

    private static final String TAG = "MessageQueueManager";
    private final Timer timer;
    private boolean active;
    private Long lastTimestamp;
    private String lastEventKey;
    private final ConcurrentLinkedQueue<Object> eventQueue;
    private final ConcurrentLinkedQueue<Object> messageQueue;

    public MessageQueueManager() {
        Timer t = new Timer();
        this.timer = t;
        this.active = false;
        this.lastTimestamp = Long.valueOf(new Date().getTime());
        this.lastEventKey = "";
        this.eventQueue = new ConcurrentLinkedQueue<>();
        this.messageQueue = new ConcurrentLinkedQueue<>();
        t.schedule(new ScheduledTimerTask(this, 0), 10000L, 10000L);
    }

    /**
     * Vendor: e.a(MessageRecordVO) - enqueue simple message
     */
    public void enqueueMessage(Object messageRecord) {
        // ADAPT: vendor gets deviceId from utils.h.l("deviceId")
        // then wraps into ReqMessageVO and offers to messageQueue
        if (messageRecord != null) {
            messageQueue.offer(messageRecord);
        }
    }

    /**
     * Vendor: e.b(MessageRecordVO) - enqueue with dedup logic
     * Decompiled as smali, partially reconstructed.
     */
    public void enqueueWithDedup(Object messageRecord) {
        // TODO: VENDOR_VERIFY - vendor b() has dedup logic:
        // builds eventKey from packageName:className:eventValue
        // skips if same key within 1000ms
        if (messageRecord != null) {
            eventQueue.offer(messageRecord);
        }
    }

    public ConcurrentLinkedQueue<Object> getEventQueue() {
        return eventQueue;
    }

    public ConcurrentLinkedQueue<Object> getMessageQueue() {
        return messageQueue;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

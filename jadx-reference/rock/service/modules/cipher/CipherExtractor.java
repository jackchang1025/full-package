package com.storm.safe.rock.service.modules.cipher;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import p000.h10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class CipherExtractor implements Serializable {

    /* renamed from: a0 */
    public static final CipherExtractor f53228a0 = new CipherExtractor();

    /* renamed from: a1 */
    public static final LinkedList f53229a1;

    /* renamed from: a2 */
    public static final AtomicBoolean f53230a2;

    /* renamed from: a3 */
    public static h10 f53231a3;

    static {
        new ConcurrentLinkedQueue();
        f53229a1 = new LinkedList();
        t60.m214694b5(Executors.newSingleThreadScheduledExecutor(), "newSingleThreadScheduledExecutor()");
        new AtomicReference(null);
        f53230a2 = new AtomicBoolean(false);
    }

    private CipherExtractor() {
    }

    /* renamed from: a0 */
    public static boolean m211773a0(String str) {
        if (str != null && str.length() != 0) {
            for (int i = 0; i < str.length(); i++) {
                if (Character.isDigit(str.charAt(i))) {
                }
            }
            return true;
        }
        return false;
    }
}

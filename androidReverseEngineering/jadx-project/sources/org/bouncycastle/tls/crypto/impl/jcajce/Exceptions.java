package org.bouncycastle.tls.crypto.impl.jcajce;

/* loaded from: classes.dex */
class Exceptions {
    public static IllegalArgumentException illegalArgumentException(String str, Throwable th) {
        return new IllegalArgumentException(str, th);
    }

    public static IllegalStateException illegalStateException(String str, Throwable th) {
        return new IllegalStateException(str, th);
    }
}

package com.google.json.internal;

/* renamed from: com.google.json.internal.$Gson$Preconditions, reason: invalid class name */
/* loaded from: classes.dex */
public final class C$Gson$Preconditions {
    private C$Gson$Preconditions() {
        throw new UnsupportedOperationException();
    }

    public static void checkArgument(boolean z2) {
        if (!z2) {
            throw new IllegalArgumentException();
        }
    }

    @Deprecated
    public static <T> T checkNotNull(T t2) {
        t2.getClass();
        return t2;
    }
}

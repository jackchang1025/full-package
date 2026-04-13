package org.bouncycastle.util;

/* loaded from: classes.dex */
public interface Memoable {
    Memoable copy();

    void reset(Memoable memoable);
}

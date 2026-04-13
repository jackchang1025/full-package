package org.bouncycastle.tls.crypto.impl;

/* loaded from: classes.dex */
public interface TlsSuiteMac {
    byte[] calculateMac(long j2, short s2, byte[] bArr, int i2, int i3);

    byte[] calculateMacConstantTime(long j2, short s2, byte[] bArr, int i2, int i3, int i4, byte[] bArr2);

    int getSize();
}

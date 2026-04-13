package org.bouncycastle.tls;

/* loaded from: classes.dex */
public interface DatagramSender {
    int getSendLimit();

    void send(byte[] bArr, int i2, int i3);
}

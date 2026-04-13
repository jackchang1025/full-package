package org.bouncycastle.util.encoders;

import java.io.OutputStream;

/* loaded from: classes.dex */
public interface Encoder {
    int decode(String str, OutputStream outputStream);

    int decode(byte[] bArr, int i2, int i3, OutputStream outputStream);

    int encode(byte[] bArr, int i2, int i3, OutputStream outputStream);

    int getEncodedLength(int i2);

    int getMaxDecodedLength(int i2);
}

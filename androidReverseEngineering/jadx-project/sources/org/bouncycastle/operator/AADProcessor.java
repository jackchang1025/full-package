package org.bouncycastle.operator;

import java.io.OutputStream;

/* loaded from: classes.dex */
public interface AADProcessor {
    OutputStream getAADStream();

    byte[] getMAC();
}

package org.bouncycastle.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.bouncycastle.tls.crypto.TlsHash;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes.dex */
class DigestInputBuffer extends ByteArrayOutputStream {
    public void copyTo(OutputStream outputStream) {
        Streams.pipeAll(new ByteArrayInputStream(((ByteArrayOutputStream) this).buf, 0, ((ByteArrayOutputStream) this).count), outputStream);
    }

    public void updateDigest(TlsHash tlsHash) {
        tlsHash.update(((ByteArrayOutputStream) this).buf, 0, ((ByteArrayOutputStream) this).count);
    }
}

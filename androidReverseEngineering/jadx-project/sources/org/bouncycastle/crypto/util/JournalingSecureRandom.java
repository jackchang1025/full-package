package org.bouncycastle.crypto.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class JournalingSecureRandom extends SecureRandom {
    private static byte[] EMPTY_TRANSCRIPT = new byte[0];
    private final SecureRandom base;
    private int index;
    private TranscriptStream tOut;
    private byte[] transcript;

    public class TranscriptStream extends ByteArrayOutputStream {
        private TranscriptStream() {
        }

        public void clear() {
            Arrays.fill(((ByteArrayOutputStream) this).buf, (byte) 0);
        }
    }

    public JournalingSecureRandom() {
        this(CryptoServicesRegistrar.getSecureRandom());
    }

    public void clear() {
        Arrays.fill(this.transcript, (byte) 0);
        this.tOut.clear();
    }

    public byte[] getFullTranscript() {
        int i2 = this.index;
        byte[] bArr = this.transcript;
        return i2 == bArr.length ? this.tOut.toByteArray() : Arrays.clone(bArr);
    }

    public byte[] getTranscript() {
        return this.tOut.toByteArray();
    }

    @Override // java.security.SecureRandom, java.util.Random
    public final void nextBytes(byte[] bArr) {
        if (this.index >= this.transcript.length) {
            this.base.nextBytes(bArr);
        } else {
            int i2 = 0;
            while (i2 != bArr.length) {
                int i3 = this.index;
                byte[] bArr2 = this.transcript;
                if (i3 >= bArr2.length) {
                    break;
                }
                this.index = i3 + 1;
                bArr[i2] = bArr2[i3];
                i2++;
            }
            if (i2 != bArr.length) {
                int length = bArr.length - i2;
                byte[] bArr3 = new byte[length];
                this.base.nextBytes(bArr3);
                System.arraycopy(bArr3, 0, bArr, i2, length);
            }
        }
        try {
            this.tOut.write(bArr);
        } catch (IOException e2) {
            throw new IllegalStateException(AbstractC0000a.m8d(e2, new StringBuilder("unable to record transcript: ")));
        }
    }

    public void reset() {
        this.index = 0;
        if (this.transcript.length == 0) {
            this.transcript = this.tOut.toByteArray();
        }
        this.tOut.reset();
    }

    public JournalingSecureRandom(SecureRandom secureRandom) {
        this.tOut = new TranscriptStream();
        this.index = 0;
        this.base = secureRandom;
        this.transcript = EMPTY_TRANSCRIPT;
    }

    public JournalingSecureRandom(byte[] bArr, SecureRandom secureRandom) {
        this.tOut = new TranscriptStream();
        this.index = 0;
        this.base = secureRandom;
        this.transcript = Arrays.clone(bArr);
    }
}

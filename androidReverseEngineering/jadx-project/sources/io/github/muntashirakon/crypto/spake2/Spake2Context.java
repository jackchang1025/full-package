package io.github.muntashirakon.crypto.spake2;

import android.support.annotation.Nullable;
import javax.security.auth.Destroyable;
import p014r.AbstractC0888a;

/* loaded from: classes.dex */
public class Spake2Context implements Destroyable {

    /* renamed from: a */
    public final long f659a;

    /* renamed from: b */
    public final byte[] f660b = new byte[32];

    /* renamed from: c */
    public boolean f661c;

    static {
        System.loadLibrary("spake2");
    }

    public Spake2Context(byte[] bArr, byte[] bArr2) {
        long allocNewContext = allocNewContext(AbstractC0888a.m1325a(1), bArr, bArr2);
        this.f659a = allocNewContext;
        if (allocNewContext == 0) {
            throw new UnsupportedOperationException("Could not allocate native context");
        }
    }

    private static native long allocNewContext(int i2, byte[] bArr, byte[] bArr2);

    private static native void destroy(long j2);

    @Nullable
    private static native byte[] generateMessage(long j2, byte[] bArr);

    @Nullable
    private static native byte[] processMessage(long j2, byte[] bArr);

    /* renamed from: a */
    public final byte[] m876a(byte[] bArr) {
        if (this.f661c) {
            throw new IllegalStateException("The context was destroyed.");
        }
        byte[] generateMessage = generateMessage(this.f659a, bArr);
        if (generateMessage == null) {
            throw new IllegalStateException("Generated empty message");
        }
        System.arraycopy(generateMessage, 0, this.f660b, 0, 32);
        return generateMessage;
    }

    /* renamed from: b */
    public final byte[] m877b(byte[] bArr) {
        if (this.f661c) {
            throw new IllegalStateException("The context was destroyed.");
        }
        byte[] processMessage = processMessage(this.f659a, bArr);
        if (processMessage != null) {
            return processMessage;
        }
        throw new IllegalStateException("No key was returned");
    }

    @Override // javax.security.auth.Destroyable
    public final void destroy() {
        this.f661c = true;
        destroy(this.f659a);
    }

    @Override // javax.security.auth.Destroyable
    public final boolean isDestroyed() {
        return this.f661c;
    }
}

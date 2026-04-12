package io.github.muntashirakon.crypto.spake2;

import javax.security.auth.Destroyable;
import p000.AbstractC1120qr;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class Spake2Context implements Destroyable {

    /* renamed from: a3 */
    public static final C0629a0 f56918a3 = new C0629a0(null);

    /* renamed from: a4 */
    public static final boolean f56919a4;

    /* renamed from: a5 */
    public static final Throwable f56920a5;

    /* renamed from: a0 */
    public final long f56921a0;

    /* renamed from: a1 */
    public final byte[] f56922a1 = new byte[32];

    /* renamed from: a2 */
    public boolean f56923a2;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: io.github.muntashirakon.crypto.spake2.Spake2Context$a0 */
    public static final class C0629a0 {
        public /* synthetic */ C0629a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final long allocNewContext(int i, byte[] bArr, byte[] bArr2) {
            return Spake2Context.allocNewContext(i, bArr, bArr2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void destroy(long j) {
            Spake2Context.destroy(j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final byte[] generateMessage(long j, byte[] bArr) {
            return Spake2Context.generateMessage(j, bArr);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final byte[] processMessage(long j, byte[] bArr) {
            return Spake2Context.processMessage(j, bArr);
        }

        private C0629a0() {
        }
    }

    static {
        try {
            System.loadLibrary("spake2");
            f56919a4 = true;
        } catch (Exception e) {
            f56920a5 = e;
        } catch (UnsatisfiedLinkError e2) {
            f56920a5 = e2;
        }
    }

    public Spake2Context(byte[] bArr, byte[] bArr2) {
        if (!f56919a4) {
            throw new UnsupportedOperationException("Native library not loaded", f56920a5);
        }
        try {
            long jAllocNewContext = f56918a3.allocNewContext(0, bArr, bArr2);
            this.f56921a0 = jAllocNewContext;
            if (jAllocNewContext == 0) {
                throw new UnsupportedOperationException("Could not allocate native context");
            }
        } catch (UnsatisfiedLinkError e) {
            throw new UnsupportedOperationException("JNI call failed", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final native long allocNewContext(int i, byte[] bArr, byte[] bArr2);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native void destroy(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native byte[] generateMessage(long j, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native byte[] processMessage(long j, byte[] bArr);

    /* renamed from: a0 */
    public final byte[] m213179a0(byte[] bArr) {
        if (this.f56923a2) {
            throw new IllegalStateException("The context was destroyed.");
        }
        try {
            byte[] bArrGenerateMessage = f56918a3.generateMessage(this.f56921a0, bArr);
            if (bArrGenerateMessage == null) {
                throw new IllegalStateException("Generated empty message");
            }
            System.arraycopy(bArrGenerateMessage, 0, this.f56922a1, 0, Math.min(bArrGenerateMessage.length, 32));
            return bArrGenerateMessage;
        } catch (UnsatisfiedLinkError e) {
            throw new IllegalStateException("JNI call failed", e);
        }
    }

    /* renamed from: a5 */
    public final byte[] m213180a5(byte[] bArr) {
        if (this.f56923a2) {
            throw new IllegalStateException("The context was destroyed.");
        }
        try {
            byte[] bArrProcessMessage = f56918a3.processMessage(this.f56921a0, bArr);
            if (bArrProcessMessage != null) {
                return bArrProcessMessage;
            }
            throw new IllegalStateException("No key was returned");
        } catch (UnsatisfiedLinkError e) {
            throw new IllegalStateException("JNI call failed", e);
        }
    }

    @Override // javax.security.auth.Destroyable
    public final void destroy() {
        if (this.f56923a2) {
            return;
        }
        this.f56923a2 = true;
        try {
            f56918a3.destroy(this.f56921a0);
        } catch (UnsatisfiedLinkError unused) {
        }
    }

    @Override // javax.security.auth.Destroyable
    public final boolean isDestroyed() {
        return this.f56923a2;
    }
}

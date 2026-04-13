package b1;

import com.guard.wallet.utils.AbstractC0251g;
import io.github.muntashirakon.crypto.spake2.Spake2Context;
import java.util.Arrays;
import javax.security.auth.Destroyable;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;

/* renamed from: b1.m */
/* loaded from: classes.dex */
public final class C0091m implements Destroyable {

    /* renamed from: g */
    public static final byte[] f146g = AbstractC0251g.m652Y("adb pair client\u0000");

    /* renamed from: h */
    public static final byte[] f147h = AbstractC0251g.m652Y("adb pair server\u0000");

    /* renamed from: i */
    public static final byte[] f148i = AbstractC0251g.m652Y("adb pairing_auth aes-128-gcm key");

    /* renamed from: a */
    public final byte[] f149a;

    /* renamed from: b */
    public final Spake2Context f150b;

    /* renamed from: c */
    public final byte[] f151c = new byte[16];

    /* renamed from: d */
    public long f152d = 0;

    /* renamed from: e */
    public long f153e = 0;

    /* renamed from: f */
    public boolean f154f = false;

    public C0091m(Spake2Context spake2Context, byte[] bArr) {
        this.f150b = spake2Context;
        this.f149a = spake2Context.m876a(bArr);
    }

    /* renamed from: a */
    public final byte[] m324a(byte[] bArr, byte[] bArr2, boolean z2) {
        if (this.f154f) {
            return null;
        }
        byte[] bArr3 = this.f151c;
        AEADParameters aEADParameters = new AEADParameters(new KeyParameter(bArr3), bArr3.length * 8, bArr2);
        GCMBlockCipher gCMBlockCipher = new GCMBlockCipher(new AESEngine());
        gCMBlockCipher.init(z2, aEADParameters);
        byte[] bArr4 = new byte[gCMBlockCipher.getOutputSize(bArr.length)];
        try {
            gCMBlockCipher.doFinal(bArr4, gCMBlockCipher.processBytes(bArr, 0, bArr.length, bArr4, 0));
            return bArr4;
        } catch (InvalidCipherTextException unused) {
            return null;
        }
    }

    @Override // javax.security.auth.Destroyable
    public final void destroy() {
        this.f154f = true;
        Arrays.fill(this.f151c, (byte) 0);
        this.f150b.destroy();
    }

    @Override // javax.security.auth.Destroyable
    public final boolean isDestroyed() {
        return this.f154f;
    }
}

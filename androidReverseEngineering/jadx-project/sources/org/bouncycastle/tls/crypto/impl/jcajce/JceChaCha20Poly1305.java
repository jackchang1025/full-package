package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.cmc.BodyPartID;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.impl.TlsAEADCipherImpl;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class JceChaCha20Poly1305 implements TlsAEADCipherImpl {
    private static final byte[] ZEROES = new byte[15];
    protected byte[] additionalData;
    protected final Cipher cipher;
    protected SecretKey cipherKey;
    protected final int cipherMode;
    protected final Mac mac;

    public JceChaCha20Poly1305(JcaJceHelper jcaJceHelper, boolean z2) {
        this.cipher = jcaJceHelper.createCipher("ChaCha7539");
        this.mac = jcaJceHelper.createMac("Poly1305");
        this.cipherMode = z2 ? 1 : 2;
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsAEADCipherImpl
    public int doFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        try {
            if (this.cipherMode == 1) {
                byte[] bArr3 = new byte[i3 + 64];
                System.arraycopy(bArr, i2, bArr3, 64, i3);
                runCipher(bArr3);
                System.arraycopy(bArr3, 64, bArr2, i4, i3);
                initMAC(bArr3);
                byte[] bArr4 = this.additionalData;
                updateMAC(bArr4, 0, bArr4.length);
                updateMAC(bArr3, 64, i3);
                byte[] bArr5 = new byte[16];
                Pack.longToLittleEndian(this.additionalData.length & BodyPartID.bodyIdMax, bArr5, 0);
                Pack.longToLittleEndian(i3 & BodyPartID.bodyIdMax, bArr5, 8);
                this.mac.update(bArr5, 0, 16);
                this.mac.doFinal(bArr2, i4 + i3);
                return i3 + 16;
            }
            int i5 = i3 - 16;
            byte[] bArr6 = new byte[i5 + 64];
            System.arraycopy(bArr, i2, bArr6, 64, i5);
            runCipher(bArr6);
            initMAC(bArr6);
            byte[] bArr7 = this.additionalData;
            updateMAC(bArr7, 0, bArr7.length);
            updateMAC(bArr, i2, i5);
            byte[] bArr8 = new byte[16];
            Pack.longToLittleEndian(this.additionalData.length & BodyPartID.bodyIdMax, bArr8, 0);
            Pack.longToLittleEndian(BodyPartID.bodyIdMax & i5, bArr8, 8);
            this.mac.update(bArr8, 0, 16);
            this.mac.doFinal(bArr8, 0);
            if (!TlsUtils.constantTimeAreEqual(16, bArr8, 0, bArr, i2 + i5)) {
                throw new TlsFatalAlert((short) 20);
            }
            System.arraycopy(bArr6, 64, bArr2, i4, i5);
            return i5;
        } catch (GeneralSecurityException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsAEADCipherImpl
    public int getOutputSize(int i2) {
        return this.cipherMode == 1 ? i2 + 16 : i2 - 16;
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsAEADCipherImpl
    public void init(byte[] bArr, int i2, byte[] bArr2) {
        if (bArr == null || bArr.length != 12 || i2 != 16) {
            throw new TlsFatalAlert((short) 80);
        }
        try {
            this.cipher.init(this.cipherMode, this.cipherKey, new IvParameterSpec(bArr), (SecureRandom) null);
            this.additionalData = bArr2;
        } catch (GeneralSecurityException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void initMAC(byte[] bArr) {
        this.mac.init(new SecretKeySpec(bArr, 0, 32, "Poly1305"));
        for (int i2 = 0; i2 < 64; i2++) {
            bArr[i2] = 0;
        }
    }

    public void runCipher(byte[] bArr) {
        if (bArr.length != this.cipher.doFinal(bArr, 0, bArr.length, bArr, 0)) {
            throw new IllegalStateException();
        }
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsAEADCipherImpl
    public void setKey(byte[] bArr, int i2, int i3) {
        this.cipherKey = new SecretKeySpec(bArr, i2, i3, "ChaCha7539");
    }

    public void updateMAC(byte[] bArr, int i2, int i3) {
        this.mac.update(bArr, i2, i3);
        int i4 = i3 % 16;
        if (i4 != 0) {
            this.mac.update(ZEROES, 0, 16 - i4);
        }
    }
}

package b1;

import com.guard.wallet.utils.AbstractC0251g;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.interfaces.RSAPublicKey;
import java.util.Objects;
import org.bouncycastle.util.encoders.Base64;

/* renamed from: b1.i */
/* loaded from: classes.dex */
public abstract class AbstractC0087i {

    /* renamed from: a */
    public static final int[] f141a;

    /* renamed from: b */
    public static final byte[] f142b;

    static {
        int[] iArr = {0, 1, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 0, 48, 33, 48, 9, 6, 5, 43, 14, 3, 2, 26, 5, 0, 4, 20};
        f141a = iArr;
        f142b = new byte[iArr.length];
        int i2 = 0;
        while (true) {
            byte[] bArr = f142b;
            if (i2 >= bArr.length) {
                return;
            }
            bArr[i2] = (byte) f141a[i2];
            i2++;
        }
    }

    /* renamed from: a */
    public static byte[] m321a(BigInteger bigInteger) {
        int i2 = 256;
        byte[] bArr = new byte[256];
        byte[] byteArray = bigInteger.toByteArray();
        int length = byteArray.length;
        byte[] bArr2 = new byte[length];
        for (int i3 = 0; i3 < length; i3++) {
            bArr2[i3] = byteArray[(length - i3) - 1];
        }
        if (256 < length) {
            byte b = 0;
            for (int i4 = 256; i4 < length; i4++) {
                b = (byte) (b | bArr2[i4]);
            }
            if (!(b == 0)) {
                return null;
            }
        } else {
            i2 = length;
        }
        System.arraycopy(bArr2, 0, bArr, 0, i2);
        return bArr;
    }

    /* renamed from: b */
    public static byte[] m322b(RSAPublicKey rSAPublicKey) {
        if (rSAPublicKey.getModulus().toByteArray().length < 256) {
            throw new InvalidKeyException("Invalid key length " + rSAPublicKey.getModulus().toByteArray().length);
        }
        ByteBuffer order = ByteBuffer.allocate(524).order(ByteOrder.LITTLE_ENDIAN);
        order.putInt(64);
        BigInteger bigInteger = BigInteger.ZERO;
        BigInteger bit = bigInteger.setBit(32);
        order.putInt(bit.subtract(rSAPublicKey.getModulus().mod(bit).modInverse(bit)).intValue());
        byte[] m321a = m321a(rSAPublicKey.getModulus());
        Objects.requireNonNull(m321a);
        order.put(m321a);
        byte[] m321a2 = m321a(bigInteger.setBit(2048).modPow(BigInteger.valueOf(2L), rSAPublicKey.getModulus()));
        Objects.requireNonNull(m321a2);
        order.put(m321a2);
        order.putInt(rSAPublicKey.getPublicExponent().intValue());
        return order.array();
    }

    /* renamed from: c */
    public static byte[] m323c(RSAPublicKey rSAPublicKey, String str) {
        C0088j c0088j = new C0088j(str.length() + (((int) Math.ceil(174.66666666666666d)) * 4) + 2);
        c0088j.write(Base64.encode(m322b(rSAPublicKey)));
        c0088j.write(AbstractC0251g.m652Y(String.format(" %s\u0000", str)));
        return c0088j.toByteArray();
    }
}

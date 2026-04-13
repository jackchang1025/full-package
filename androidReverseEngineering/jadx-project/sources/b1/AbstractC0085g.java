package b1;

import com.guard.wallet.utils.AbstractC0251g;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: b1.g */
/* loaded from: classes.dex */
public abstract class AbstractC0085g {

    /* renamed from: a */
    public static final byte[] f129a = AbstractC0251g.m652Y("host::\u0000");

    /* renamed from: a */
    public static byte[] m314a(int i2, int i3, int i4, byte[] bArr, int i5, int i6) {
        ByteBuffer order = ByteBuffer.allocate(bArr != null ? i6 + 24 : 24).order(ByteOrder.LITTLE_ENDIAN);
        order.putInt(i2);
        order.putInt(i3);
        order.putInt(i4);
        int i7 = 0;
        if (bArr != null) {
            order.putInt(i6);
            for (int i8 = i5; i8 < i5 + i6; i8++) {
                i7 += bArr[i8] & 255;
            }
        } else {
            order.putInt(0);
        }
        order.putInt(i7);
        order.putInt(~i2);
        if (bArr != null) {
            order.put(bArr, i5, i6);
        }
        return order.array();
    }

    /* renamed from: b */
    public static byte[] m315b(int i2, int i3, byte[] bArr, int i4) {
        return m314a(i2, i3, i4, bArr, 0, bArr == null ? 0 : bArr.length);
    }
}

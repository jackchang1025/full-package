package p000;

import java.nio.ByteBuffer;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: wo */
/* loaded from: classes.dex */
public final class C1384wo {

    /* renamed from: a3 */
    public static final ThreadLocal f60951a3 = new ThreadLocal();

    /* renamed from: a0 */
    public final int f60952a0;

    /* renamed from: a1 */
    public final x31 f60953a1;

    /* renamed from: a2 */
    public volatile int f60954a2 = 0;

    public C1384wo(x31 x31Var, int i) {
        this.f60953a1 = x31Var;
        this.f60952a0 = i;
    }

    /* renamed from: a0 */
    public final int m215083a0(int i) {
        yf0 yf0VarM215084a1 = m215084a1();
        int iM215362a0 = yf0VarM215084a1.m215362a0(16);
        if (iM215362a0 == 0) {
            return 0;
        }
        ByteBuffer byteBuffer = (ByteBuffer) yf0VarM215084a1.f61458a3;
        int i2 = iM215362a0 + yf0VarM215084a1.f61455a0;
        return byteBuffer.getInt((i * 4) + byteBuffer.getInt(i2) + i2 + 4);
    }

    /* renamed from: a1 */
    public final yf0 m215084a1() {
        ThreadLocal threadLocal = f60951a3;
        yf0 yf0Var = (yf0) threadLocal.get();
        if (yf0Var == null) {
            yf0Var = new yf0();
            threadLocal.set(yf0Var);
        }
        zf0 zf0Var = (zf0) this.f60953a1.f61012a0;
        int iM215362a0 = zf0Var.m215362a0(6);
        if (iM215362a0 != 0) {
            int i = iM215362a0 + zf0Var.f61455a0;
            int i2 = (this.f60952a0 * 4) + ((ByteBuffer) zf0Var.f61458a3).getInt(i) + i + 4;
            int i3 = ((ByteBuffer) zf0Var.f61458a3).getInt(i2) + i2;
            ByteBuffer byteBuffer = (ByteBuffer) zf0Var.f61458a3;
            yf0Var.f61458a3 = byteBuffer;
            if (byteBuffer != null) {
                yf0Var.f61455a0 = i3;
                int i4 = i3 - byteBuffer.getInt(i3);
                yf0Var.f61456a1 = i4;
                yf0Var.f61457a2 = ((ByteBuffer) yf0Var.f61458a3).getShort(i4);
                return yf0Var;
            }
            yf0Var.f61455a0 = 0;
            yf0Var.f61456a1 = 0;
            yf0Var.f61457a2 = 0;
        }
        return yf0Var;
    }

    public final String toString() {
        int i;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(", id:");
        yf0 yf0VarM215084a1 = m215084a1();
        int iM215362a0 = yf0VarM215084a1.m215362a0(4);
        sb.append(Integer.toHexString(iM215362a0 != 0 ? ((ByteBuffer) yf0VarM215084a1.f61458a3).getInt(iM215362a0 + yf0VarM215084a1.f61455a0) : 0));
        sb.append(", codepoints:");
        yf0 yf0VarM215084a12 = m215084a1();
        int iM215362a02 = yf0VarM215084a12.m215362a0(16);
        if (iM215362a02 != 0) {
            int i2 = iM215362a02 + yf0VarM215084a12.f61455a0;
            i = ((ByteBuffer) yf0VarM215084a12.f61458a3).getInt(((ByteBuffer) yf0VarM215084a12.f61458a3).getInt(i2) + i2);
        } else {
            i = 0;
        }
        for (int i3 = 0; i3 < i; i3++) {
            sb.append(Integer.toHexString(m215083a0(i3)));
            sb.append(" ");
        }
        return sb.toString();
    }
}

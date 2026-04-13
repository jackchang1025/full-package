package k1;

import java.nio.ByteBuffer;
import p000a.AbstractC0000a;
import p014r.AbstractC0888a;

/* renamed from: k1.d */
/* loaded from: classes.dex */
public abstract class AbstractC0367d {

    /* renamed from: b */
    public final int f725b;

    /* renamed from: c */
    public ByteBuffer f726c = ByteBuffer.allocate(0);

    /* renamed from: a */
    public boolean f724a = true;

    /* renamed from: d */
    public boolean f727d = false;

    /* renamed from: e */
    public boolean f728e = false;

    /* renamed from: f */
    public boolean f729f = false;

    /* renamed from: g */
    public boolean f730g = false;

    public AbstractC0367d(int i2) {
        this.f725b = i2;
    }

    /* renamed from: a */
    public ByteBuffer mo942a() {
        return this.f726c;
    }

    /* renamed from: b */
    public abstract void mo941b();

    /* renamed from: c */
    public void mo943c(ByteBuffer byteBuffer) {
        this.f726c = byteBuffer;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AbstractC0367d abstractC0367d = (AbstractC0367d) obj;
        if (this.f724a != abstractC0367d.f724a || this.f727d != abstractC0367d.f727d || this.f728e != abstractC0367d.f728e || this.f729f != abstractC0367d.f729f || this.f730g != abstractC0367d.f730g || this.f725b != abstractC0367d.f725b) {
            return false;
        }
        ByteBuffer byteBuffer = this.f726c;
        ByteBuffer byteBuffer2 = abstractC0367d.f726c;
        return byteBuffer != null ? byteBuffer.equals(byteBuffer2) : byteBuffer2 == null;
    }

    public int hashCode() {
        int m1325a = (AbstractC0888a.m1325a(this.f725b) + ((this.f724a ? 1 : 0) * 31)) * 31;
        ByteBuffer byteBuffer = this.f726c;
        return ((((((((m1325a + (byteBuffer != null ? byteBuffer.hashCode() : 0)) * 31) + (this.f727d ? 1 : 0)) * 31) + (this.f728e ? 1 : 0)) * 31) + (this.f729f ? 1 : 0)) * 31) + (this.f730g ? 1 : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Framedata{ opcode:");
        sb.append(AbstractC0000a.m4E(this.f725b));
        sb.append(", fin:");
        sb.append(this.f724a);
        sb.append(", rsv1:");
        sb.append(this.f728e);
        sb.append(", rsv2:");
        sb.append(this.f729f);
        sb.append(", rsv3:");
        sb.append(this.f730g);
        sb.append(", payload length:[pos:");
        sb.append(this.f726c.position());
        sb.append(", len:");
        sb.append(this.f726c.remaining());
        sb.append("], payload:");
        sb.append(this.f726c.remaining() > 1000 ? "(too big to display)" : new String(this.f726c.array()));
        sb.append('}');
        return sb.toString();
    }
}

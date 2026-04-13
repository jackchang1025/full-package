package k1;

import android.support.v4.view.PointerIconCompat;
import i1.C0340c;
import i1.C0341d;
import java.nio.ByteBuffer;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import o1.AbstractC0447a;
import com.guard.wallet.entity.BuildConfig;

/* renamed from: k1.b */
/* loaded from: classes.dex */
public final class C0365b extends AbstractC0366c {

    /* renamed from: i */
    public int f721i;

    /* renamed from: j */
    public String f722j;

    public C0365b() {
        super(6, 0);
        this.f722j = BuildConfig.FLAVOR;
        m944d();
        this.f721i = 1000;
        m944d();
    }

    @Override // k1.AbstractC0367d
    /* renamed from: a */
    public final ByteBuffer mo942a() {
        return this.f721i == 1005 ? ByteBuffer.allocate(0) : this.f726c;
    }

    @Override // k1.AbstractC0366c, k1.AbstractC0367d
    /* renamed from: b */
    public final void mo941b() {
        super.mo941b();
        if (this.f721i == 1007 && this.f722j.isEmpty()) {
            throw new C0340c(PointerIconCompat.TYPE_CROSSHAIR, "Received text is no valid utf8 string!");
        }
        if (this.f721i == 1005 && this.f722j.length() > 0) {
            throw new C0340c(PointerIconCompat.TYPE_HAND, "A close frame must have a closecode if it has a reason");
        }
        int i2 = this.f721i;
        if (i2 > 1015 && i2 < 3000) {
            throw new C0340c(PointerIconCompat.TYPE_HAND, "Trying to send an illegal close code!");
        }
        if (i2 == 1006 || i2 == 1015 || i2 == 1005 || i2 > 4999 || i2 < 1000 || i2 == 1004) {
            throw new C0341d("closecode must not be sent over the wire: " + this.f721i);
        }
    }

    @Override // k1.AbstractC0367d
    /* renamed from: c */
    public final void mo943c(ByteBuffer byteBuffer) {
        int i2;
        this.f721i = 1005;
        this.f722j = BuildConfig.FLAVOR;
        byteBuffer.mark();
        if (byteBuffer.remaining() == 0) {
            i2 = 1000;
        } else {
            if (byteBuffer.remaining() != 1) {
                if (byteBuffer.remaining() >= 2) {
                    ByteBuffer allocate = ByteBuffer.allocate(4);
                    allocate.position(2);
                    allocate.putShort(byteBuffer.getShort());
                    allocate.position(0);
                    this.f721i = allocate.getInt();
                }
                byteBuffer.reset();
                try {
                    int position = byteBuffer.position();
                    try {
                        try {
                            byteBuffer.position(byteBuffer.position() + 2);
                            this.f722j = AbstractC0447a.m1183b(byteBuffer);
                            return;
                        } catch (IllegalArgumentException unused) {
                            throw new C0340c(PointerIconCompat.TYPE_CROSSHAIR);
                        }
                    } finally {
                        byteBuffer.position(position);
                    }
                } catch (C0340c unused2) {
                    this.f721i = PointerIconCompat.TYPE_CROSSHAIR;
                    this.f722j = null;
                    return;
                }
            }
            i2 = PointerIconCompat.TYPE_HAND;
        }
        this.f721i = i2;
    }

    /* renamed from: d */
    public final void m944d() {
        String str = this.f722j;
        CodingErrorAction codingErrorAction = AbstractC0447a.f1052a;
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.putInt(this.f721i);
        allocate.position(2);
        ByteBuffer allocate2 = ByteBuffer.allocate(bytes.length + 2);
        allocate2.put(allocate);
        allocate2.put(bytes);
        allocate2.rewind();
        this.f726c = allocate2;
    }

    @Override // k1.AbstractC0367d
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C0365b.class != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        C0365b c0365b = (C0365b) obj;
        if (this.f721i != c0365b.f721i) {
            return false;
        }
        String str = this.f722j;
        String str2 = c0365b.f722j;
        return str != null ? str.equals(str2) : str2 == null;
    }

    @Override // k1.AbstractC0367d
    public final int hashCode() {
        int hashCode = ((super.hashCode() * 31) + this.f721i) * 31;
        String str = this.f722j;
        return hashCode + (str != null ? str.hashCode() : 0);
    }

    @Override // k1.AbstractC0367d
    public final String toString() {
        return super.toString() + "code: " + this.f721i;
    }
}

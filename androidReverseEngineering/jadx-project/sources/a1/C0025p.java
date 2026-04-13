package a1;

/* renamed from: a1.p */
/* loaded from: classes.dex */
public final class C0025p {

    /* renamed from: a */
    public final byte[] f48a;

    /* renamed from: b */
    public int f49b;

    /* renamed from: c */
    public int f50c;

    /* renamed from: d */
    public boolean f51d;

    /* renamed from: e */
    public final boolean f52e;

    /* renamed from: f */
    public C0025p f53f;

    /* renamed from: g */
    public C0025p f54g;

    public C0025p() {
        this.f48a = new byte[8192];
        this.f52e = true;
        this.f51d = false;
    }

    /* renamed from: a */
    public final C0025p m146a() {
        C0025p c0025p = this.f53f;
        C0025p c0025p2 = c0025p != this ? c0025p : null;
        C0025p c0025p3 = this.f54g;
        c0025p3.f53f = c0025p;
        this.f53f.f54g = c0025p3;
        this.f53f = null;
        this.f54g = null;
        return c0025p2;
    }

    /* renamed from: b */
    public final void m147b(C0025p c0025p) {
        c0025p.f54g = this;
        c0025p.f53f = this.f53f;
        this.f53f.f54g = c0025p;
        this.f53f = c0025p;
    }

    /* renamed from: c */
    public final C0025p m148c() {
        this.f51d = true;
        return new C0025p(this.f48a, this.f49b, this.f50c);
    }

    /* renamed from: d */
    public final void m149d(C0025p c0025p, int i2) {
        if (!c0025p.f52e) {
            throw new IllegalArgumentException();
        }
        int i3 = c0025p.f50c;
        int i4 = i3 + i2;
        byte[] bArr = c0025p.f48a;
        if (i4 > 8192) {
            if (c0025p.f51d) {
                throw new IllegalArgumentException();
            }
            int i5 = c0025p.f49b;
            if ((i3 + i2) - i5 > 8192) {
                throw new IllegalArgumentException();
            }
            System.arraycopy(bArr, i5, bArr, 0, i3 - i5);
            c0025p.f50c -= c0025p.f49b;
            c0025p.f49b = 0;
        }
        System.arraycopy(this.f48a, this.f49b, bArr, c0025p.f50c, i2);
        c0025p.f50c += i2;
        this.f49b += i2;
    }

    public C0025p(byte[] bArr, int i2, int i3) {
        this.f48a = bArr;
        this.f49b = i2;
        this.f50c = i3;
        this.f51d = true;
        this.f52e = false;
    }
}

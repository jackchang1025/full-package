package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: bz */
/* loaded from: classes.dex */
public final class C0153bz {

    /* renamed from: a0 */
    public int f46012a0;

    /* renamed from: a1 */
    public int f46013a1;

    /* renamed from: a2 */
    public float f46014a2;

    /* renamed from: a3 */
    public float f46015a3;

    /* renamed from: a4 */
    public long f46016a4;

    /* renamed from: a5 */
    public long f46017a5;

    /* renamed from: a6 */
    public long f46018a6;

    /* renamed from: a7 */
    public float f46019a7;

    /* renamed from: a8 */
    public int f46020a8;

    /* renamed from: a0 */
    public final float m210750a0(long j) {
        if (j < this.f46016a4) {
            return 0.0f;
        }
        long j2 = this.f46018a6;
        if (j2 < 0 || j < j2) {
            return kb0.m213476a1((j - r0) / this.f46012a0, 0.0f, 1.0f) * 0.5f;
        }
        float f = this.f46019a7;
        return (kb0.m213476a1((j - j2) / this.f46020a8, 0.0f, 1.0f) * f) + (1.0f - f);
    }
}

package p000;

import android.graphics.Paint;
import android.graphics.Path;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class yz0 {

    /* renamed from: a8 */
    public static final int[] f61406a8 = new int[3];

    /* renamed from: a9 */
    public static final float[] f61407a9 = {0.0f, 0.5f, 1.0f};

    /* renamed from: b0 */
    public static final int[] f61408b0 = new int[4];

    /* renamed from: b1 */
    public static final float[] f61409b1 = {0.0f, 0.0f, 0.5f, 1.0f};

    /* renamed from: a0 */
    public final Paint f61410a0;

    /* renamed from: a1 */
    public final Paint f61411a1;

    /* renamed from: a2 */
    public final Paint f61412a2;

    /* renamed from: a3 */
    public int f61413a3;

    /* renamed from: a4 */
    public int f61414a4;

    /* renamed from: a5 */
    public int f61415a5;

    /* renamed from: a6 */
    public final Path f61416a6 = new Path();

    /* renamed from: a7 */
    public final Paint f61417a7;

    public yz0() {
        Paint paint = new Paint();
        this.f61417a7 = paint;
        this.f61410a0 = new Paint();
        m215327a0(-16777216);
        paint.setColor(0);
        Paint paint2 = new Paint(4);
        this.f61411a1 = paint2;
        paint2.setStyle(Paint.Style.FILL);
        this.f61412a2 = new Paint(paint2);
    }

    /* renamed from: a0 */
    public final void m215327a0(int i) {
        this.f61413a3 = AbstractC0724jn.m213334a4(i, 68);
        this.f61414a4 = AbstractC0724jn.m213334a4(i, 20);
        this.f61415a5 = AbstractC0724jn.m213334a4(i, 0);
        this.f61410a0.setColor(this.f61413a3);
    }
}

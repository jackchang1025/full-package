package p000;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import com.google.android.material.carousel.CarouselLayoutManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: gh */
/* loaded from: classes2.dex */
public final class C0536gh extends za0 {

    /* renamed from: b5 */
    public final /* synthetic */ int f56469b5;

    /* renamed from: b6 */
    public final /* synthetic */ Object f56470b6;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0536gh(Object obj, Context context, int i) {
        super(context);
        this.f56469b5 = i;
        this.f56470b6 = obj;
    }

    @Override // p000.za0
    /* renamed from: a1 */
    public int mo212946a1(View view, int i) {
        switch (this.f56469b5) {
            case 0:
                CarouselLayoutManager carouselLayoutManager = (CarouselLayoutManager) this.f56470b6;
                return (int) (carouselLayoutManager.f49288b5 - carouselLayoutManager.m210977i2(carouselLayoutManager.f49293c0.f55580a0, pq0.m214304d0(view)));
            default:
                return super.mo212946a1(view, i);
        }
    }

    @Override // p000.za0
    /* renamed from: a2 */
    public float mo212947a2(DisplayMetrics displayMetrics) {
        switch (this.f56469b5) {
            case 1:
                return 100.0f / displayMetrics.densityDpi;
            default:
                return super.mo212947a2(displayMetrics);
        }
    }

    @Override // p000.za0
    /* renamed from: a3 */
    public int mo212948a3(int i) {
        switch (this.f56469b5) {
            case 1:
                return Math.min(100, super.mo212948a3(i));
            default:
                return super.mo212948a3(i);
        }
    }

    @Override // p000.za0
    /* renamed from: a4 */
    public PointF mo212949a4(int i) {
        switch (this.f56469b5) {
            case 0:
                if (((CarouselLayoutManager) this.f56470b6).f49293c0 == null) {
                    return null;
                }
                return new PointF(r0.m210977i2(r1.f55580a0, i) - r0.f49288b5, 0.0f);
            default:
                return super.mo212949a4(i);
        }
    }

    @Override // p000.za0
    /* renamed from: a6 */
    public void mo212950a6(View view, yq0 yq0Var) {
        switch (this.f56469b5) {
            case 1:
                fm0 fm0Var = (fm0) this.f56470b6;
                int[] iArrM212836a1 = fm0Var.m212836a1(fm0Var.f56287a0.getLayoutManager(), view);
                int i = iArrM212836a1[0];
                int i2 = iArrM212836a1[1];
                int iCeil = (int) Math.ceil(mo212948a3(Math.max(Math.abs(i), Math.abs(i2))) / 0.3356d);
                if (iCeil > 0) {
                    yq0Var.f61355a0 = i;
                    yq0Var.f61356a1 = i2;
                    yq0Var.f61357a2 = iCeil;
                    yq0Var.f61359a4 = this.f61480a8;
                    yq0Var.f61360a5 = true;
                    break;
                }
                break;
            default:
                super.mo212950a6(view, yq0Var);
                break;
        }
    }
}

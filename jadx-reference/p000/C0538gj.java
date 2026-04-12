package p000;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R$dimen;
import com.google.android.material.carousel.CarouselLayoutManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: gj */
/* loaded from: classes2.dex */
public final class C0538gj extends mq0 {

    /* renamed from: a0 */
    public final Paint f56505a0;

    /* renamed from: a1 */
    public List f56506a1;

    public C0538gj() {
        Paint paint = new Paint();
        this.f56505a0 = paint;
        this.f56506a1 = Collections.unmodifiableList(new ArrayList());
        paint.setStrokeWidth(5.0f);
        paint.setColor(-65281);
    }

    @Override // p000.mq0
    /* renamed from: a1 */
    public final void mo212957a1(Canvas canvas, RecyclerView recyclerView) throws Resources.NotFoundException {
        float dimension = recyclerView.getResources().getDimension(R$dimen.m3_carousel_debug_keyline_width);
        Paint paint = this.f56505a0;
        paint.setStrokeWidth(dimension);
        for (b90 b90Var : this.f56506a1) {
            paint.setColor(AbstractC0724jn.m213331a1(-65281, b90Var.f45753a2, -16776961));
            float f = b90Var.f45752a1;
            float fM214316c9 = ((CarouselLayoutManager) recyclerView.getLayoutManager()).m214316c9();
            float f2 = b90Var.f45752a1;
            CarouselLayoutManager carouselLayoutManager = (CarouselLayoutManager) recyclerView.getLayoutManager();
            canvas.drawLine(f, fM214316c9, f2, carouselLayoutManager.f59332b4 - carouselLayoutManager.m214313c6(), paint);
        }
    }
}

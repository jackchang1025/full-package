package p000;

import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.appcompat.widget.ContentFrameLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: w6 */
/* loaded from: classes.dex */
public final class C1365w6 extends ContentFrameLayout {

    /* renamed from: a8 */
    public final /* synthetic */ LayoutInflaterFactory2C1367w8 f60776a8;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1365w6(LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8, C0875mu c0875mu) {
        super(c0875mu, null);
        this.f60776a8 = layoutInflaterFactory2C1367w8;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return this.f60776a8.m215024b9(keyEvent) || super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (x < -5 || y < -5 || x > getWidth() + 5 || y > getHeight() + 5) {
                LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8 = this.f60776a8;
                layoutInflaterFactory2C1367w8.m215023b7(layoutInflaterFactory2C1367w8.m215029c4(0), true);
                return true;
            }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final void setBackgroundResource(int i) {
        setBackgroundDrawable(b81.m210576b7(getContext(), i));
    }
}

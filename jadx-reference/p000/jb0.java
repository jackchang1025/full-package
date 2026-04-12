package p000;

import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import androidx.appcompat.widget.ListPopupWindow;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class jb0 implements View.OnTouchListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f57314a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f57315a1;

    public /* synthetic */ jb0(int i, Object obj) {
        this.f57314a0 = i;
        this.f57315a1 = obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        switch (this.f57314a0) {
            case 0:
                ListPopupWindow listPopupWindow = (ListPopupWindow) this.f57315a1;
                hb0 hb0Var = listPopupWindow.f43989b7;
                Handler handler = listPopupWindow.f43993c1;
                C1402x5 c1402x5 = listPopupWindow.f43997c5;
                int action = motionEvent.getAction();
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                if (action == 0 && c1402x5 != null && c1402x5.isShowing() && x >= 0 && x < c1402x5.getWidth() && y >= 0 && y < c1402x5.getHeight()) {
                    handler.postDelayed(hb0Var, 250L);
                    return false;
                }
                if (action != 1) {
                    return false;
                }
                handler.removeCallbacks(hb0Var);
                return false;
            default:
                if (((Checkable) view).isChecked()) {
                    return ((GestureDetector) this.f57315a1).onTouchEvent(motionEvent);
                }
                return false;
        }
    }
}

package p000;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.work.impl.foreground.SystemForegroundService;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.util.List;
import kotlin.jvm.internal.Ref$IntRef;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: j6 */
/* loaded from: classes2.dex */
public final class RunnableC0707j6 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f57269a0;

    /* renamed from: a1 */
    public final int f57270a1;

    /* renamed from: a2 */
    public final Object f57271a2;

    /* renamed from: a3 */
    public final Object f57272a3;

    public /* synthetic */ RunnableC0707j6(int i, int i2, Object obj, Object obj2) {
        this.f57269a0 = i2;
        this.f57271a2 = obj;
        this.f57272a3 = obj2;
        this.f57270a1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int iM214413a9;
        Handler handler;
        int i = this.f57269a0;
        Object obj = this.f57271a2;
        int i2 = this.f57270a1;
        Object obj2 = this.f57272a3;
        switch (i) {
            case 0:
                C0708j7 c0708j7 = (C0708j7) obj;
                long jCurrentTimeMillis = System.currentTimeMillis() - c0708j7.f57285b0;
                long j = 1000;
                if (c0708j7.f57286b1) {
                    iM214413a9 = AbstractC1117qo.m214413a9(((int) ((jCurrentTimeMillis / 60000) * (100 - i2))) + i2, i2, 100);
                } else if (jCurrentTimeMillis < 30000) {
                    iM214413a9 = AbstractC1117qo.m214413a9((int) ((jCurrentTimeMillis / 30000) * 80), 0, 80);
                } else {
                    j = 3000;
                    iM214413a9 = AbstractC1117qo.m214413a9(((int) ((jCurrentTimeMillis - 30000) / 3000)) + 80, 80, 95);
                }
                Ref$IntRef ref$IntRef = (Ref$IntRef) obj2;
                if (iM214413a9 != ref$IntRef.f57624a0) {
                    ref$IntRef.f57624a0 = iM214413a9;
                    View view = c0708j7.f57281a6;
                    if (view != null) {
                        ViewParent parent = view.getParent();
                        FrameLayout frameLayout = parent instanceof FrameLayout ? (FrameLayout) parent : null;
                        if (frameLayout != null) {
                            int width = frameLayout.getWidth();
                            if (width <= 0) {
                                width = (int) (c0708j7.f57275a0.getResources().getDisplayMetrics().widthPixels * 0.65f);
                            }
                            view.setLayoutParams(new FrameLayout.LayoutParams((int) ((width * iM214413a9) / 100.0f), -1));
                            List list = c0708j7.f57276a1.f55707a8;
                            if (!list.isEmpty()) {
                                int iM214413a92 = AbstractC1117qo.m214413a9((int) ((iM214413a9 / 100.0f) * list.size()), 0, list.size() - 1);
                                TextView textView = c0708j7.f57282a7;
                                if (textView != null) {
                                    textView.setText((CharSequence) list.get(iM214413a92));
                                }
                            }
                        }
                    }
                }
                if (iM214413a9 < (c0708j7.f57286b1 ? 100 : 95) && (handler = c0708j7.f57283a8) != null) {
                    handler.postDelayed(this, j);
                    break;
                }
                break;
            case 1:
                ((TextView) obj).setTypeface((Typeface) obj2, i2);
                break;
            case 2:
                int i3 = BottomSheetBehavior.f49178f7;
                ((BottomSheetBehavior) obj2).m210949d1((View) obj, i2, false);
                break;
            case 3:
                ((q31) obj).m214349a0((Intent) obj2, i2);
                break;
            default:
                ((SystemForegroundService) obj2).f45583a4.notify(i2, (Notification) obj);
                break;
        }
    }

    public RunnableC0707j6(SystemForegroundService systemForegroundService, int i, Notification notification) {
        this.f57269a0 = 4;
        this.f57272a3 = systemForegroundService;
        this.f57270a1 = i;
        this.f57271a2 = notification;
    }

    public RunnableC0707j6(C0708j7 c0708j7, int i, Ref$IntRef ref$IntRef) {
        this.f57269a0 = 0;
        this.f57271a2 = c0708j7;
        this.f57270a1 = i;
        this.f57272a3 = ref$IntRef;
    }

    public RunnableC0707j6(BottomSheetBehavior bottomSheetBehavior, View view, int i) {
        this.f57269a0 = 2;
        this.f57272a3 = bottomSheetBehavior;
        this.f57271a2 = view;
        this.f57270a1 = i;
    }
}

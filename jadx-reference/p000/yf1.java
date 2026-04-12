package p000;

import android.R;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.inputmethod.InputMethodManager;
import okio.Segment;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class yf1 extends kg1 {

    /* renamed from: a4 */
    public final /* synthetic */ int f61307a4 = 0;

    /* renamed from: a5 */
    public Window f61308a5;

    /* renamed from: a6 */
    public final Object f61309a6;

    public yf1(Window window, View view) {
        this.f61308a5 = window;
        this.f61309a6 = view;
    }

    @Override // p000.kg1
    /* renamed from: c2 */
    public final void mo213549c2() {
        switch (this.f61307a4) {
            case 0:
                for (int i = 1; i <= 256; i <<= 1) {
                    if ((8 & i) != 0) {
                        Window window = this.f61308a5;
                        if (i == 1) {
                            m215278f9(4);
                        } else if (i == 2) {
                            m215278f9(2);
                        } else if (i == 8) {
                            ((InputMethodManager) window.getContext().getSystemService("input_method")).hideSoftInputFromWindow(window.getDecorView().getWindowToken(), 0);
                        }
                    }
                }
                break;
            default:
                ((WindowInsetsController) this.f61309a6).hide(8);
                break;
        }
    }

    @Override // p000.kg1
    /* renamed from: e6 */
    public void mo213550e6(boolean z) {
        switch (this.f61307a4) {
            case 1:
                Window window = this.f61308a5;
                if (!z) {
                    if (window != null) {
                        View decorView = window.getDecorView();
                        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & (-17));
                    }
                    ((WindowInsetsController) this.f61309a6).setSystemBarsAppearance(0, 16);
                    break;
                } else {
                    if (window != null) {
                        View decorView2 = window.getDecorView();
                        decorView2.setSystemUiVisibility(decorView2.getSystemUiVisibility() | 16);
                    }
                    ((WindowInsetsController) this.f61309a6).setSystemBarsAppearance(16, 16);
                    break;
                }
        }
    }

    @Override // p000.kg1
    /* renamed from: e7 */
    public final void mo213551e7(boolean z) {
        switch (this.f61307a4) {
            case 0:
                Window window = this.f61308a5;
                if (!z) {
                    m215279g0(Segment.SIZE);
                    break;
                } else {
                    window.clearFlags(67108864);
                    window.addFlags(Integer.MIN_VALUE);
                    m215278f9(Segment.SIZE);
                    break;
                }
            default:
                Window window2 = this.f61308a5;
                if (!z) {
                    if (window2 != null) {
                        View decorView = window2.getDecorView();
                        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & (-8193));
                    }
                    ((WindowInsetsController) this.f61309a6).setSystemBarsAppearance(0, 8);
                    break;
                } else {
                    if (window2 != null) {
                        View decorView2 = window2.getDecorView();
                        decorView2.setSystemUiVisibility(decorView2.getSystemUiVisibility() | Segment.SIZE);
                    }
                    ((WindowInsetsController) this.f61309a6).setSystemBarsAppearance(8, 8);
                    break;
                }
        }
    }

    @Override // p000.kg1
    /* renamed from: f3 */
    public final void mo213552f3() {
        switch (this.f61307a4) {
            case 0:
                Window window = this.f61308a5;
                for (int i = 1; i <= 256; i <<= 1) {
                    if ((8 & i) != 0) {
                        if (i == 1) {
                            m215279g0(4);
                            window.clearFlags(Segment.SHARE_MINIMUM);
                        } else if (i == 2) {
                            m215279g0(2);
                        } else if (i == 8) {
                            View viewFindViewById = (View) this.f61309a6;
                            if (viewFindViewById.isInEditMode() || viewFindViewById.onCheckIsTextEditor()) {
                                viewFindViewById.requestFocus();
                            } else {
                                viewFindViewById = window.getCurrentFocus();
                            }
                            if (viewFindViewById == null) {
                                viewFindViewById = window.findViewById(R.id.content);
                            }
                            if (viewFindViewById != null && viewFindViewById.hasWindowFocus()) {
                                viewFindViewById.post(new RunnableC0458ej(viewFindViewById, 1));
                            }
                        }
                    }
                }
                break;
            default:
                Window window2 = this.f61308a5;
                if (window2 != null && Build.VERSION.SDK_INT < 33) {
                    ((InputMethodManager) window2.getContext().getSystemService("input_method")).isActive();
                }
                ((WindowInsetsController) this.f61309a6).show(8);
                break;
        }
    }

    /* renamed from: f9 */
    public void m215278f9(int i) {
        View decorView = this.f61308a5.getDecorView();
        decorView.setSystemUiVisibility(i | decorView.getSystemUiVisibility());
    }

    /* renamed from: g0 */
    public void m215279g0(int i) {
        View decorView = this.f61308a5.getDecorView();
        decorView.setSystemUiVisibility((~i) & decorView.getSystemUiVisibility());
    }

    public yf1(WindowInsetsController windowInsetsController) {
        this.f61309a6 = windowInsetsController;
    }
}

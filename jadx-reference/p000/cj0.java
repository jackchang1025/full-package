package p000;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class cj0 {

    /* renamed from: a0 */
    public ViewParent f46143a0;

    /* renamed from: a1 */
    public ViewParent f46144a1;

    /* renamed from: a2 */
    public final ViewGroup f46145a2;

    /* renamed from: a3 */
    public boolean f46146a3;

    /* renamed from: a4 */
    public int[] f46147a4;

    public cj0(ViewGroup viewGroup) {
        this.f46145a2 = viewGroup;
    }

    /* renamed from: a0 */
    public final boolean m210854a0(float f, float f2, boolean z) {
        ViewParent viewParentM210858a4;
        if (this.f46146a3 && (viewParentM210858a4 = m210858a4(0)) != null) {
            try {
                return kc1.m213482a0(viewParentM210858a4, this.f46145a2, f, f2, z);
            } catch (AbstractMethodError unused) {
                Objects.toString(viewParentM210858a4);
            }
        }
        return false;
    }

    /* renamed from: a1 */
    public final boolean m210855a1(float f, float f2) {
        ViewParent viewParentM210858a4;
        if (this.f46146a3 && (viewParentM210858a4 = m210858a4(0)) != null) {
            try {
                return kc1.m213483a1(viewParentM210858a4, this.f46145a2, f, f2);
            } catch (AbstractMethodError unused) {
                Objects.toString(viewParentM210858a4);
            }
        }
        return false;
    }

    /* renamed from: a2 */
    public final boolean m210856a2(int i, int[] iArr, int i2, int i3, int[] iArr2) {
        ViewParent viewParentM210858a4;
        int i4;
        int i5;
        int[] iArr3;
        if (!this.f46146a3 || (viewParentM210858a4 = m210858a4(i3)) == null) {
            return false;
        }
        if (i == 0 && i2 == 0) {
            if (iArr2 == null) {
                return false;
            }
            iArr2[0] = 0;
            iArr2[1] = 0;
            return false;
        }
        ViewGroup viewGroup = this.f46145a2;
        if (iArr2 != null) {
            viewGroup.getLocationInWindow(iArr2);
            i4 = iArr2[0];
            i5 = iArr2[1];
        } else {
            i4 = 0;
            i5 = 0;
        }
        if (iArr == null) {
            if (this.f46147a4 == null) {
                this.f46147a4 = new int[2];
            }
            iArr3 = this.f46147a4;
        } else {
            iArr3 = iArr;
        }
        iArr3[0] = 0;
        iArr3[1] = 0;
        if (viewParentM210858a4 instanceof dj0) {
            ((dj0) viewParentM210858a4).mo209860a2(viewGroup, i, i2, iArr3, i3);
        } else if (i3 == 0) {
            try {
                kc1.m213484a2(viewParentM210858a4, viewGroup, i, i2, iArr3);
            } catch (AbstractMethodError unused) {
                Objects.toString(viewParentM210858a4);
            }
        }
        if (iArr2 != null) {
            viewGroup.getLocationInWindow(iArr2);
            iArr2[0] = iArr2[0] - i4;
            iArr2[1] = iArr2[1] - i5;
        }
        return (iArr3[0] == 0 && iArr3[1] == 0) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0089  */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m210857a3(int i, int[] iArr, int i2, int i3, int i4, int[] iArr2, int i5) {
        ViewParent viewParentM210858a4;
        int i6;
        int i7;
        int[] iArr3;
        ViewGroup viewGroup;
        if (this.f46146a3 && (viewParentM210858a4 = m210858a4(i5)) != null) {
            if (i != 0 || i2 != 0 || i3 != 0 || i4 != 0) {
                ViewGroup viewGroup2 = this.f46145a2;
                if (iArr != null) {
                    viewGroup2.getLocationInWindow(iArr);
                    i6 = iArr[0];
                    i7 = iArr[1];
                } else {
                    i6 = 0;
                    i7 = 0;
                }
                if (iArr2 == null) {
                    if (this.f46147a4 == null) {
                        this.f46147a4 = new int[2];
                    }
                    int[] iArr4 = this.f46147a4;
                    iArr4[0] = 0;
                    iArr4[1] = 0;
                    iArr3 = iArr4;
                } else {
                    iArr3 = iArr2;
                }
                if (viewParentM210858a4 instanceof ej0) {
                    ej0 ej0Var = (ej0) viewParentM210858a4;
                    viewGroup = viewGroup2;
                    ej0Var.mo209861a3(viewGroup, i, i2, i3, i4, i5, iArr3);
                } else {
                    iArr3[0] = iArr3[0] + i3;
                    iArr3[1] = iArr3[1] + i4;
                    if (!(viewParentM210858a4 instanceof dj0)) {
                        if (i5 == 0) {
                            try {
                                kc1.m213485a3(viewParentM210858a4, viewGroup2, i, i2, i3, i4);
                            } catch (AbstractMethodError unused) {
                                Objects.toString(viewParentM210858a4);
                            }
                        }
                        if (iArr != null) {
                            viewGroup2.getLocationInWindow(iArr);
                            iArr[0] = iArr[0] - i6;
                            iArr[1] = iArr[1] - i7;
                        }
                        return true;
                    }
                    dj0 dj0Var = (dj0) viewParentM210858a4;
                    viewGroup = viewGroup2;
                    dj0Var.mo209862a4(viewGroup, i, i2, i3, i4, i5);
                }
                viewGroup2 = viewGroup;
                if (iArr != null) {
                }
                return true;
            }
            if (iArr != null) {
                iArr[0] = 0;
                iArr[1] = 0;
                return false;
            }
        }
        return false;
    }

    /* renamed from: a4 */
    public final ViewParent m210858a4(int i) {
        if (i == 0) {
            return this.f46143a0;
        }
        if (i != 1) {
            return null;
        }
        return this.f46144a1;
    }

    /* renamed from: a5 */
    public final boolean m210859a5(int i) {
        return m210858a4(i) != null;
    }

    /* renamed from: a6 */
    public final boolean m210860a6(int i, int i2) {
        boolean zM213487a5;
        if (!m210859a5(i2)) {
            if (this.f46146a3) {
                ViewGroup viewGroup = this.f46145a2;
                View view = viewGroup;
                for (ViewParent parent = viewGroup.getParent(); parent != null; parent = parent.getParent()) {
                    boolean z = parent instanceof dj0;
                    if (z) {
                        zM213487a5 = ((dj0) parent).mo209863a5(view, viewGroup, i, i2);
                    } else if (i2 == 0) {
                        try {
                            zM213487a5 = kc1.m213487a5(parent, view, viewGroup, i);
                        } catch (AbstractMethodError unused) {
                            Objects.toString(parent);
                        }
                    } else {
                        zM213487a5 = false;
                    }
                    if (zM213487a5) {
                        if (i2 == 0) {
                            this.f46143a0 = parent;
                        } else if (i2 == 1) {
                            this.f46144a1 = parent;
                        }
                        if (z) {
                            ((dj0) parent).mo209858a0(view, viewGroup, i, i2);
                        } else if (i2 == 0) {
                            try {
                                kc1.m213486a4(parent, view, viewGroup, i);
                            } catch (AbstractMethodError unused2) {
                                Objects.toString(parent);
                            }
                        }
                    } else {
                        if (parent instanceof View) {
                            view = (View) parent;
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    /* renamed from: a7 */
    public final void m210861a7(int i) {
        ViewParent viewParentM210858a4 = m210858a4(i);
        if (viewParentM210858a4 != null) {
            boolean z = viewParentM210858a4 instanceof dj0;
            ViewGroup viewGroup = this.f46145a2;
            if (z) {
                ((dj0) viewParentM210858a4).mo209859a1(viewGroup, i);
            } else if (i == 0) {
                try {
                    kc1.m213488a6(viewParentM210858a4, viewGroup);
                } catch (AbstractMethodError unused) {
                    Objects.toString(viewParentM210858a4);
                }
            }
            if (i == 0) {
                this.f46143a0 = null;
            } else {
                if (i != 1) {
                    return;
                }
                this.f46144a1 = null;
            }
        }
    }
}

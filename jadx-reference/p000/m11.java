package p000;

import android.os.Looper;
import android.util.AndroidRuntimeException;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class m11 {

    /* renamed from: b3 */
    public static final C1310ur f58222b3 = new C1310ur();

    /* renamed from: b4 */
    public static final C1310ur f58223b4 = new C1310ur();

    /* renamed from: b5 */
    public static final C1310ur f58224b5 = new C1310ur();

    /* renamed from: b6 */
    public static final C1310ur f58225b6 = new C1310ur();

    /* renamed from: b7 */
    public static final C1310ur f58226b7 = new C1310ur();

    /* renamed from: b8 */
    public static final C1310ur f58227b8 = new C1310ur();

    /* renamed from: a0 */
    public float f58228a0;

    /* renamed from: a1 */
    public float f58229a1;

    /* renamed from: a2 */
    public boolean f58230a2;

    /* renamed from: a3 */
    public final C1223si f58231a3;

    /* renamed from: a4 */
    public final C1222sh f58232a4;

    /* renamed from: a5 */
    public boolean f58233a5;

    /* renamed from: a6 */
    public long f58234a6;

    /* renamed from: a7 */
    public final float f58235a7;

    /* renamed from: a8 */
    public final ArrayList f58236a8;

    /* renamed from: a9 */
    public final ArrayList f58237a9;

    /* renamed from: b0 */
    public n11 f58238b0;

    /* renamed from: b1 */
    public float f58239b1;

    /* renamed from: b2 */
    public boolean f58240b2;

    public m11(C1223si c1223si) {
        C1222sh c1222sh = C1223si.f59988b6;
        this.f58228a0 = 0.0f;
        this.f58229a1 = Float.MAX_VALUE;
        this.f58230a2 = false;
        this.f58233a5 = false;
        this.f58234a6 = 0L;
        this.f58236a8 = new ArrayList();
        this.f58237a9 = new ArrayList();
        this.f58231a3 = c1223si;
        this.f58232a4 = c1222sh;
        if (c1222sh == f58224b5 || c1222sh == f58225b6 || c1222sh == f58226b7) {
            this.f58235a7 = 0.1f;
        } else if (c1222sh == f58227b8 || c1222sh == f58222b3 || c1222sh == f58223b4) {
            this.f58235a7 = 0.00390625f;
        } else {
            this.f58235a7 = 1.0f;
        }
        this.f58238b0 = null;
        this.f58239b1 = Float.MAX_VALUE;
        this.f58240b2 = false;
    }

    /* renamed from: a0 */
    public final void m213930a0(float f) {
        this.f58232a4.getClass();
        C1223si c1223si = this.f58231a3;
        c1223si.f59992b4 = f / 10000.0f;
        c1223si.invalidateSelf();
        int i = 0;
        while (true) {
            ArrayList arrayList = this.f58237a9;
            if (i >= arrayList.size()) {
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    if (arrayList.get(size) == null) {
                        arrayList.remove(size);
                    }
                }
                return;
            }
            if (arrayList.get(i) != null) {
                arrayList.get(i).getClass();
                throw new ClassCastException();
            }
            i++;
        }
    }

    /* renamed from: a1 */
    public final void m213931a1() {
        if (this.f58238b0.f58420a1 <= 0.0d) {
            throw new UnsupportedOperationException("Spring animations can only come to an end when there is damping");
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new AndroidRuntimeException("Animations may only be started on the main thread");
        }
        if (this.f58233a5) {
            this.f58240b2 = true;
        }
    }
}

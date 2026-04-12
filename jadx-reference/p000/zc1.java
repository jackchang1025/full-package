package p000;

import android.view.View;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class zc1 {

    /* renamed from: a0 */
    public b81 f61501a0;

    /* renamed from: a1 */
    public String f61502a1;

    /* renamed from: a2 */
    public float[] f61503a2;

    /* renamed from: a3 */
    public boolean f61504a3;

    /* renamed from: a4 */
    public long f61505a4;

    /* renamed from: a5 */
    public float f61506a5;

    public zc1() {
        this.f61503a2 = new float[3];
        this.f61504a3 = false;
        this.f61506a5 = Float.NaN;
    }

    /* renamed from: a0 */
    public final float m215392a0(float f, long j, C1105qc c1105qc, View view) {
        float[] fArr = this.f61503a2;
        this.f61501a0.mo210518c2(f, fArr);
        boolean z = true;
        float f2 = fArr[1];
        if (f2 == 0.0f) {
            this.f61504a3 = false;
            return fArr[2];
        }
        if (Float.isNaN(this.f61506a5)) {
            float fM214372a0 = c1105qc.m214372a0(view, this.f61502a1);
            this.f61506a5 = fM214372a0;
            if (Float.isNaN(fM214372a0)) {
                this.f61506a5 = 0.0f;
            }
        }
        float f3 = (float) (((((j - this.f61505a4) * 1.0E-9d) * f2) + this.f61506a5) % 1.0d);
        this.f61506a5 = f3;
        String str = this.f61502a1;
        HashMap map = c1105qc.f59459a0;
        if (map.containsKey(view)) {
            HashMap map2 = (HashMap) map.get(view);
            if (map2 == null) {
                map2 = new HashMap();
            }
            if (map2.containsKey(str)) {
                float[] fArrCopyOf = (float[]) map2.get(str);
                if (fArrCopyOf == null) {
                    fArrCopyOf = new float[0];
                }
                if (fArrCopyOf.length <= 0) {
                    fArrCopyOf = Arrays.copyOf(fArrCopyOf, 1);
                }
                fArrCopyOf[0] = f3;
                map2.put(str, fArrCopyOf);
            } else {
                map2.put(str, new float[]{f3});
                map.put(view, map2);
            }
        } else {
            HashMap map3 = new HashMap();
            map3.put(str, new float[]{f3});
            map.put(view, map3);
        }
        this.f61505a4 = j;
        float f4 = fArr[0];
        float fSin = (((float) Math.sin(this.f61506a5 * 6.2831855f)) * f4) + fArr[2];
        if (f4 == 0.0f && f2 == 0.0f) {
            z = false;
        }
        this.f61504a3 = z;
        return fSin;
    }

    /* renamed from: a1 */
    public abstract boolean mo214921a1(float f, long j, C1105qc c1105qc, View view);

    /* renamed from: a2 */
    public void mo215046a2(int i) {
        System.err.println("Error no points added to " + this.f61502a1);
    }

    public final String toString() {
        String str = this.f61502a1;
        new DecimalFormat("##.##");
        return str;
    }
}

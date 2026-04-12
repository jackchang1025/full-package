package p000;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.BitSet;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class c01 {

    /* renamed from: a0 */
    public final k01[] f46047a0 = new k01[4];

    /* renamed from: a1 */
    public final Matrix[] f46048a1 = new Matrix[4];

    /* renamed from: a2 */
    public final Matrix[] f46049a2 = new Matrix[4];

    /* renamed from: a3 */
    public final PointF f46050a3 = new PointF();

    /* renamed from: a4 */
    public final Path f46051a4 = new Path();

    /* renamed from: a5 */
    public final Path f46052a5 = new Path();

    /* renamed from: a6 */
    public final k01 f46053a6 = new k01();

    /* renamed from: a7 */
    public final float[] f46054a7 = new float[2];

    /* renamed from: a8 */
    public final float[] f46055a8 = new float[2];

    /* renamed from: a9 */
    public final Path f46056a9 = new Path();

    /* renamed from: b0 */
    public final Path f46057b0 = new Path();

    /* renamed from: b1 */
    public final boolean f46058b1 = true;

    public c01() {
        for (int i = 0; i < 4; i++) {
            this.f46047a0[i] = new k01();
            this.f46048a1[i] = new Matrix();
            this.f46049a2[i] = new Matrix();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r16v0 */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v5 */
    /* renamed from: a0 */
    public final void m210755a0(a01 a01Var, float f, RectF rectF, tg0 tg0Var, Path path) {
        Matrix[] matrixArr;
        float[] fArr;
        int i;
        k01[] k01VarArr;
        boolean z;
        Matrix[] matrixArr2;
        boolean z2;
        int i2;
        path.rewind();
        Path path2 = this.f46051a4;
        path2.rewind();
        Path path3 = this.f46052a5;
        path3.rewind();
        path3.addRect(rectF, Path.Direction.CW);
        int i3 = 0;
        while (true) {
            matrixArr = this.f46049a2;
            fArr = this.f46054a7;
            k01VarArr = this.f46047a0;
            z = 0;
            matrixArr2 = this.f46048a1;
            if (i3 >= 4) {
                break;
            }
            InterfaceC0909nd interfaceC0909nd = i3 != 1 ? i3 != 2 ? i3 != 3 ? a01Var.f12a5 : a01Var.f11a4 : a01Var.f14a7 : a01Var.f13a6;
            b81 b81Var = i3 != 1 ? i3 != 2 ? i3 != 3 ? a01Var.f8a1 : a01Var.f7a0 : a01Var.f10a3 : a01Var.f9a2;
            k01 k01Var = k01VarArr[i3];
            b81Var.getClass();
            b81Var.mo210602b5(k01Var, f, interfaceC0909nd.mo212732a0(rectF));
            int i4 = i3 + 1;
            float f2 = (i4 % 4) * 90;
            matrixArr2[i3].reset();
            PointF pointF = this.f46050a3;
            if (i3 == 1) {
                i2 = i3;
                pointF.set(rectF.right, rectF.bottom);
            } else if (i3 == 2) {
                i2 = i3;
                pointF.set(rectF.left, rectF.bottom);
            } else if (i3 != 3) {
                i2 = i3;
                pointF.set(rectF.right, rectF.top);
            } else {
                i2 = i3;
                pointF.set(rectF.left, rectF.top);
            }
            matrixArr2[i2].setTranslate(pointF.x, pointF.y);
            matrixArr2[i2].preRotate(f2);
            k01 k01Var2 = k01VarArr[i2];
            fArr[0] = k01Var2.f57413a2;
            fArr[1] = k01Var2.f57414a3;
            matrixArr2[i2].mapPoints(fArr);
            matrixArr[i2].reset();
            matrixArr[i2].setTranslate(fArr[0], fArr[1]);
            matrixArr[i2].preRotate(f2);
            i3 = i4;
        }
        char c = 1;
        int i5 = 0;
        for (i = 4; i5 < i; i = 4) {
            k01 k01Var3 = k01VarArr[i5];
            fArr[z] = k01Var3.f57411a0;
            fArr[c] = k01Var3.f57412a1;
            matrixArr2[i5].mapPoints(fArr);
            if (i5 == 0) {
                path.moveTo(fArr[z], fArr[c]);
            } else {
                path.lineTo(fArr[z], fArr[c]);
            }
            k01VarArr[i5].m213398a2(matrixArr2[i5], path);
            if (tg0Var != null) {
                k01 k01Var4 = k01VarArr[i5];
                Matrix matrix = matrixArr2[i5];
                ce0 ce0Var = (ce0) tg0Var.f60218a1;
                BitSet bitSet = ce0Var.f46110a3;
                k01Var4.getClass();
                bitSet.set(i5, z);
                j01[] j01VarArr = ce0Var.f46108a1;
                k01Var4.m213397a1(k01Var4.f57416a5);
                j01VarArr[i5] = new d01(new ArrayList(k01Var4.f57418a7), new Matrix(matrix));
            }
            int i6 = i5 + 1;
            int i7 = i6 % 4;
            k01 k01Var5 = k01VarArr[i5];
            fArr[0] = k01Var5.f57413a2;
            fArr[1] = k01Var5.f57414a3;
            matrixArr2[i5].mapPoints(fArr);
            k01 k01Var6 = k01VarArr[i7];
            float f3 = k01Var6.f57411a0;
            float[] fArr2 = this.f46055a8;
            fArr2[0] = f3;
            fArr2[1] = k01Var6.f57412a1;
            matrixArr2[i7].mapPoints(fArr2);
            k01[] k01VarArr2 = k01VarArr;
            float fMax = Math.max(((float) Math.hypot(fArr[0] - fArr2[0], fArr[1] - fArr2[1])) - 0.001f, 0.0f);
            k01 k01Var7 = k01VarArr2[i5];
            fArr[0] = k01Var7.f57413a2;
            fArr[1] = k01Var7.f57414a3;
            matrixArr2[i5].mapPoints(fArr);
            float fAbs = (i5 == 1 || i5 == 3) ? Math.abs(rectF.centerX() - fArr[0]) : Math.abs(rectF.centerY() - fArr[1]);
            k01 k01Var8 = this.f46053a6;
            k01Var8.m213400a4(0.0f, 0.0f, 270.0f, 0.0f);
            C1351vv c1351vv = i5 != 1 ? i5 != 2 ? i5 != 3 ? a01Var.f16a9 : a01Var.f15a8 : a01Var.f18b1 : a01Var.f17b0;
            c1351vv.mo210827a7(fMax, fAbs, f, k01Var8);
            Path path4 = this.f46056a9;
            path4.reset();
            k01Var8.m213398a2(matrixArr[i5], path4);
            if (this.f46058b1 && (c1351vv.mo214851a4() || m210756a1(path4, i5) || m210756a1(path4, i7))) {
                path4.op(path4, path3, Path.Op.DIFFERENCE);
                fArr[0] = k01Var8.f57411a0;
                c = 1;
                fArr[1] = k01Var8.f57412a1;
                matrixArr[i5].mapPoints(fArr);
                path2.moveTo(fArr[0], fArr[1]);
                k01Var8.m213398a2(matrixArr[i5], path2);
            } else {
                c = 1;
                k01Var8.m213398a2(matrixArr[i5], path);
            }
            if (tg0Var != null) {
                Matrix matrix2 = matrixArr[i5];
                ce0 ce0Var2 = (ce0) tg0Var.f60218a1;
                z2 = false;
                ce0Var2.f46110a3.set(i5 + 4, false);
                j01[] j01VarArr2 = ce0Var2.f46109a2;
                k01Var8.m213397a1(k01Var8.f57416a5);
                j01VarArr2[i5] = new d01(new ArrayList(k01Var8.f57418a7), new Matrix(matrix2));
            } else {
                z2 = false;
            }
            z = z2;
            k01VarArr = k01VarArr2;
            i5 = i6;
        }
        path.close();
        path2.close();
        if (path2.isEmpty()) {
            return;
        }
        path.op(path2, Path.Op.UNION);
    }

    /* renamed from: a1 */
    public final boolean m210756a1(Path path, int i) {
        Path path2 = this.f46057b0;
        path2.reset();
        this.f46047a0[i].m213398a2(this.f46048a1[i], path2);
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        path2.computeBounds(rectF, true);
        path.op(path2, Path.Op.INTERSECT);
        path.computeBounds(rectF, true);
        return !rectF.isEmpty() || (rectF.width() > 1.0f && rectF.height() > 1.0f);
    }
}

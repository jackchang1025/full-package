package p000;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class p91 {

    /* renamed from: b5 */
    public static final Matrix f59170b5 = new Matrix();

    /* renamed from: a0 */
    public final Path f59171a0;

    /* renamed from: a1 */
    public final Path f59172a1;

    /* renamed from: a2 */
    public final Matrix f59173a2;

    /* renamed from: a3 */
    public Paint f59174a3;

    /* renamed from: a4 */
    public Paint f59175a4;

    /* renamed from: a5 */
    public PathMeasure f59176a5;

    /* renamed from: a6 */
    public final m91 f59177a6;

    /* renamed from: a7 */
    public float f59178a7;

    /* renamed from: a8 */
    public float f59179a8;

    /* renamed from: a9 */
    public float f59180a9;

    /* renamed from: b0 */
    public float f59181b0;

    /* renamed from: b1 */
    public int f59182b1;

    /* renamed from: b2 */
    public String f59183b2;

    /* renamed from: b3 */
    public Boolean f59184b3;

    /* renamed from: b4 */
    public final C0130bd f59185b4;

    public p91() {
        this.f59173a2 = new Matrix();
        this.f59178a7 = 0.0f;
        this.f59179a8 = 0.0f;
        this.f59180a9 = 0.0f;
        this.f59181b0 = 0.0f;
        this.f59182b1 = v10.MASK;
        this.f59183b2 = null;
        this.f59184b3 = null;
        this.f59185b4 = new C0130bd();
        this.f59177a6 = new m91();
        this.f59171a0 = new Path();
        this.f59172a1 = new Path();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a0 */
    public final void m214242a0(m91 m91Var, Matrix matrix, Canvas canvas, int i, int i2) {
        int i3;
        float f;
        int i4;
        float f2;
        Matrix matrix2 = m91Var.f58306a0;
        ArrayList arrayList = m91Var.f58307a1;
        matrix2.set(matrix);
        Matrix matrix3 = m91Var.f58306a0;
        matrix3.preConcat(m91Var.f58315a9);
        canvas.save();
        char c = 0;
        int i5 = 0;
        while (i5 < arrayList.size()) {
            n91 n91Var = (n91) arrayList.get(i5);
            if (n91Var instanceof m91) {
                m214242a0((m91) n91Var, matrix3, canvas, i, i2);
            } else if (n91Var instanceof o91) {
                o91 o91Var = (o91) n91Var;
                float f3 = i / this.f59180a9;
                float f4 = i2 / this.f59181b0;
                float fMin = Math.min(f3, f4);
                Matrix matrix4 = this.f59173a2;
                matrix4.set(matrix3);
                matrix4.postScale(f3, f4);
                float[] fArr = {0.0f, 1.0f, 1.0f, 0.0f};
                matrix3.mapVectors(fArr);
                float fHypot = (float) Math.hypot(fArr[c], fArr[1]);
                boolean z = c;
                i3 = i5;
                float fHypot2 = (float) Math.hypot(fArr[2], fArr[3]);
                float f5 = (fArr[z ? 1 : 0] * fArr[3]) - (fArr[1] * fArr[2]);
                float fMax = Math.max(fHypot, fHypot2);
                float fAbs = fMax > 0.0f ? Math.abs(f5) / fMax : 0.0f;
                if (fAbs != 0.0f) {
                    Path path = this.f59171a0;
                    path.reset();
                    qm0[] qm0VarArr = o91Var.f58761a0;
                    if (qm0VarArr != null) {
                        qm0.m214401a1(qm0VarArr, path);
                    }
                    Path path2 = this.f59172a1;
                    path2.reset();
                    if (o91Var instanceof k91) {
                        path2.setFillType(o91Var.f58763a2 == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                        path2.addPath(path, matrix4);
                        canvas.clipPath(path2);
                    } else {
                        l91 l91Var = (l91) o91Var;
                        float f6 = l91Var.f57852a8;
                        if (f6 != 0.0f || l91Var.f57853a9 != 1.0f) {
                            float f7 = l91Var.f57854b0;
                            float f8 = (f6 + f7) % 1.0f;
                            float f9 = (l91Var.f57853a9 + f7) % 1.0f;
                            if (this.f59176a5 == null) {
                                this.f59176a5 = new PathMeasure();
                            }
                            this.f59176a5.setPath(path, z);
                            float length = this.f59176a5.getLength();
                            float f10 = f8 * length;
                            float f11 = f9 * length;
                            path.reset();
                            if (f10 > f11) {
                                this.f59176a5.getSegment(f10, length, path, true);
                                f = 0.0f;
                                this.f59176a5.getSegment(0.0f, f11, path, true);
                            } else {
                                f = 0.0f;
                                this.f59176a5.getSegment(f10, f11, path, true);
                            }
                            path.rLineTo(f, f);
                        }
                        path2.addPath(path, matrix4);
                        C1401x4 c1401x4 = l91Var.f57849a5;
                        if (((Shader) c1401x4.f61017a1) == null && c1401x4.f61016a0 == 0) {
                            f2 = 255.0f;
                            i4 = 16777215;
                        } else {
                            if (this.f59175a4 == null) {
                                i4 = 16777215;
                                Paint paint = new Paint(1);
                                this.f59175a4 = paint;
                                paint.setStyle(Paint.Style.FILL);
                            } else {
                                i4 = 16777215;
                            }
                            Paint paint2 = this.f59175a4;
                            Shader shader = (Shader) c1401x4.f61017a1;
                            if (shader != null) {
                                shader.setLocalMatrix(matrix4);
                                paint2.setShader(shader);
                                paint2.setAlpha(Math.round(l91Var.f57851a7 * 255.0f));
                                f2 = 255.0f;
                            } else {
                                paint2.setShader(null);
                                paint2.setAlpha(v10.MASK);
                                int i6 = c1401x4.f61016a0;
                                float f12 = l91Var.f57851a7;
                                PorterDuff.Mode mode = s91.f59932a9;
                                f2 = 255.0f;
                                paint2.setColor((i6 & i4) | (((int) (Color.alpha(i6) * f12)) << 24));
                            }
                            paint2.setColorFilter(null);
                            path2.setFillType(l91Var.f58763a2 == 0 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                            canvas.drawPath(path2, paint2);
                        }
                        C1401x4 c1401x42 = l91Var.f57847a3;
                        if (((Shader) c1401x42.f61017a1) != null || c1401x42.f61016a0 != 0) {
                            if (this.f59174a3 == null) {
                                Paint paint3 = new Paint(1);
                                this.f59174a3 = paint3;
                                paint3.setStyle(Paint.Style.STROKE);
                            }
                            Paint paint4 = this.f59174a3;
                            Paint.Join join = l91Var.f57856b2;
                            if (join != null) {
                                paint4.setStrokeJoin(join);
                            }
                            Paint.Cap cap = l91Var.f57855b1;
                            if (cap != null) {
                                paint4.setStrokeCap(cap);
                            }
                            paint4.setStrokeMiter(l91Var.f57857b3);
                            Shader shader2 = (Shader) c1401x42.f61017a1;
                            if (shader2 != null) {
                                shader2.setLocalMatrix(matrix4);
                                paint4.setShader(shader2);
                                paint4.setAlpha(Math.round(l91Var.f57850a6 * f2));
                            } else {
                                paint4.setShader(null);
                                paint4.setAlpha(v10.MASK);
                                int i7 = c1401x42.f61016a0;
                                float f13 = l91Var.f57850a6;
                                PorterDuff.Mode mode2 = s91.f59932a9;
                                paint4.setColor((i7 & i4) | (((int) (Color.alpha(i7) * f13)) << 24));
                            }
                            paint4.setColorFilter(null);
                            paint4.setStrokeWidth(l91Var.f57848a4 * fMin * fAbs);
                            canvas.drawPath(path2, paint4);
                        }
                    }
                }
                i5 = i3 + 1;
                c = 0;
            }
            i3 = i5;
            i5 = i3 + 1;
            c = 0;
        }
        canvas.restore();
    }

    public float getAlpha() {
        return getRootAlpha() / 255.0f;
    }

    public int getRootAlpha() {
        return this.f59182b1;
    }

    public void setAlpha(float f) {
        setRootAlpha((int) (f * 255.0f));
    }

    public void setRootAlpha(int i) {
        this.f59182b1 = i;
    }

    public p91(p91 p91Var) {
        this.f59173a2 = new Matrix();
        this.f59178a7 = 0.0f;
        this.f59179a8 = 0.0f;
        this.f59180a9 = 0.0f;
        this.f59181b0 = 0.0f;
        this.f59182b1 = v10.MASK;
        this.f59183b2 = null;
        this.f59184b3 = null;
        C0130bd c0130bd = new C0130bd();
        this.f59185b4 = c0130bd;
        this.f59177a6 = new m91(p91Var.f59177a6, c0130bd);
        this.f59171a0 = new Path(p91Var.f59171a0);
        this.f59172a1 = new Path(p91Var.f59172a1);
        this.f59178a7 = p91Var.f59178a7;
        this.f59179a8 = p91Var.f59179a8;
        this.f59180a9 = p91Var.f59180a9;
        this.f59181b0 = p91Var.f59181b0;
        this.f59182b1 = p91Var.f59182b1;
        this.f59183b2 = p91Var.f59183b2;
        String str = p91Var.f59183b2;
        if (str != null) {
            c0130bd.put(str, this);
        }
        this.f59184b3 = p91Var.f59184b3;
    }
}

package p000;

import android.graphics.Matrix;
import android.graphics.Paint;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class m91 extends n91 {

    /* renamed from: a0 */
    public final Matrix f58306a0;

    /* renamed from: a1 */
    public final ArrayList f58307a1;

    /* renamed from: a2 */
    public float f58308a2;

    /* renamed from: a3 */
    public float f58309a3;

    /* renamed from: a4 */
    public float f58310a4;

    /* renamed from: a5 */
    public float f58311a5;

    /* renamed from: a6 */
    public float f58312a6;

    /* renamed from: a7 */
    public float f58313a7;

    /* renamed from: a8 */
    public float f58314a8;

    /* renamed from: a9 */
    public final Matrix f58315a9;

    /* renamed from: b0 */
    public String f58316b0;

    public m91() {
        this.f58306a0 = new Matrix();
        this.f58307a1 = new ArrayList();
        this.f58308a2 = 0.0f;
        this.f58309a3 = 0.0f;
        this.f58310a4 = 0.0f;
        this.f58311a5 = 1.0f;
        this.f58312a6 = 1.0f;
        this.f58313a7 = 0.0f;
        this.f58314a8 = 0.0f;
        this.f58315a9 = new Matrix();
        this.f58316b0 = null;
    }

    @Override // p000.n91
    /* renamed from: a0 */
    public final boolean mo213797a0() {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.f58307a1;
            if (i >= arrayList.size()) {
                return false;
            }
            if (((n91) arrayList.get(i)).mo213797a0()) {
                return true;
            }
            i++;
        }
    }

    @Override // p000.n91
    /* renamed from: a1 */
    public final boolean mo213798a1(int[] iArr) {
        int i = 0;
        boolean zMo213798a1 = false;
        while (true) {
            ArrayList arrayList = this.f58307a1;
            if (i >= arrayList.size()) {
                return zMo213798a1;
            }
            zMo213798a1 |= ((n91) arrayList.get(i)).mo213798a1(iArr);
            i++;
        }
    }

    /* renamed from: a2 */
    public final void m213952a2() {
        Matrix matrix = this.f58315a9;
        matrix.reset();
        matrix.postTranslate(-this.f58309a3, -this.f58310a4);
        matrix.postScale(this.f58311a5, this.f58312a6);
        matrix.postRotate(this.f58308a2, 0.0f, 0.0f);
        matrix.postTranslate(this.f58313a7 + this.f58309a3, this.f58314a8 + this.f58310a4);
    }

    public String getGroupName() {
        return this.f58316b0;
    }

    public Matrix getLocalMatrix() {
        return this.f58315a9;
    }

    public float getPivotX() {
        return this.f58309a3;
    }

    public float getPivotY() {
        return this.f58310a4;
    }

    public float getRotation() {
        return this.f58308a2;
    }

    public float getScaleX() {
        return this.f58311a5;
    }

    public float getScaleY() {
        return this.f58312a6;
    }

    public float getTranslateX() {
        return this.f58313a7;
    }

    public float getTranslateY() {
        return this.f58314a8;
    }

    public void setPivotX(float f) {
        if (f != this.f58309a3) {
            this.f58309a3 = f;
            m213952a2();
        }
    }

    public void setPivotY(float f) {
        if (f != this.f58310a4) {
            this.f58310a4 = f;
            m213952a2();
        }
    }

    public void setRotation(float f) {
        if (f != this.f58308a2) {
            this.f58308a2 = f;
            m213952a2();
        }
    }

    public void setScaleX(float f) {
        if (f != this.f58311a5) {
            this.f58311a5 = f;
            m213952a2();
        }
    }

    public void setScaleY(float f) {
        if (f != this.f58312a6) {
            this.f58312a6 = f;
            m213952a2();
        }
    }

    public void setTranslateX(float f) {
        if (f != this.f58313a7) {
            this.f58313a7 = f;
            m213952a2();
        }
    }

    public void setTranslateY(float f) {
        if (f != this.f58314a8) {
            this.f58314a8 = f;
            m213952a2();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public m91(m91 m91Var, C0130bd c0130bd) {
        k91 k91Var;
        this.f58306a0 = new Matrix();
        this.f58307a1 = new ArrayList();
        this.f58308a2 = 0.0f;
        this.f58309a3 = 0.0f;
        this.f58310a4 = 0.0f;
        this.f58311a5 = 1.0f;
        this.f58312a6 = 1.0f;
        this.f58313a7 = 0.0f;
        this.f58314a8 = 0.0f;
        Matrix matrix = new Matrix();
        this.f58315a9 = matrix;
        this.f58316b0 = null;
        this.f58308a2 = m91Var.f58308a2;
        this.f58309a3 = m91Var.f58309a3;
        this.f58310a4 = m91Var.f58310a4;
        this.f58311a5 = m91Var.f58311a5;
        this.f58312a6 = m91Var.f58312a6;
        this.f58313a7 = m91Var.f58313a7;
        this.f58314a8 = m91Var.f58314a8;
        String str = m91Var.f58316b0;
        this.f58316b0 = str;
        if (str != null) {
            c0130bd.put(str, this);
        }
        matrix.set(m91Var.f58315a9);
        ArrayList arrayList = m91Var.f58307a1;
        for (int i = 0; i < arrayList.size(); i++) {
            Object obj = arrayList.get(i);
            if (obj instanceof m91) {
                this.f58307a1.add(new m91((m91) obj, c0130bd));
            } else {
                if (obj instanceof l91) {
                    l91 l91Var = (l91) obj;
                    l91 l91Var2 = new l91(l91Var);
                    l91Var2.f57848a4 = 0.0f;
                    l91Var2.f57850a6 = 1.0f;
                    l91Var2.f57851a7 = 1.0f;
                    l91Var2.f57852a8 = 0.0f;
                    l91Var2.f57853a9 = 1.0f;
                    l91Var2.f57854b0 = 0.0f;
                    l91Var2.f57855b1 = Paint.Cap.BUTT;
                    l91Var2.f57856b2 = Paint.Join.MITER;
                    l91Var2.f57857b3 = 4.0f;
                    l91Var2.f57847a3 = l91Var.f57847a3;
                    l91Var2.f57848a4 = l91Var.f57848a4;
                    l91Var2.f57850a6 = l91Var.f57850a6;
                    l91Var2.f57849a5 = l91Var.f57849a5;
                    l91Var2.f58763a2 = l91Var.f58763a2;
                    l91Var2.f57851a7 = l91Var.f57851a7;
                    l91Var2.f57852a8 = l91Var.f57852a8;
                    l91Var2.f57853a9 = l91Var.f57853a9;
                    l91Var2.f57854b0 = l91Var.f57854b0;
                    l91Var2.f57855b1 = l91Var.f57855b1;
                    l91Var2.f57856b2 = l91Var.f57856b2;
                    l91Var2.f57857b3 = l91Var.f57857b3;
                    k91Var = l91Var2;
                } else if (obj instanceof k91) {
                    k91Var = new k91((k91) obj);
                } else {
                    throw new IllegalStateException("Unknown object in the tree!");
                }
                this.f58307a1.add(k91Var);
                Object obj2 = k91Var.f58762a1;
                if (obj2 != null) {
                    c0130bd.put(obj2, k91Var);
                }
            }
        }
    }
}

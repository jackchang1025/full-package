package p000;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.TypedValue;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ad */
/* loaded from: classes.dex */
public final class C0024ad {

    /* renamed from: b1 */
    public static final RectF f43610b1 = new RectF();

    /* renamed from: b2 */
    public static final ConcurrentHashMap f43611b2 = new ConcurrentHashMap();

    /* renamed from: a0 */
    public int f43612a0 = 0;

    /* renamed from: a1 */
    public boolean f43613a1 = false;

    /* renamed from: a2 */
    public float f43614a2 = -1.0f;

    /* renamed from: a3 */
    public float f43615a3 = -1.0f;

    /* renamed from: a4 */
    public float f43616a4 = -1.0f;

    /* renamed from: a5 */
    public int[] f43617a5 = new int[0];

    /* renamed from: a6 */
    public boolean f43618a6 = false;

    /* renamed from: a7 */
    public TextPaint f43619a7;

    /* renamed from: a8 */
    public final TextView f43620a8;

    /* renamed from: a9 */
    public final Context f43621a9;

    /* renamed from: b0 */
    public final C0011aa f43622b0;

    static {
        new ConcurrentHashMap();
    }

    public C0024ad(TextView textView) {
        this.f43620a8 = textView;
        this.f43621a9 = textView.getContext();
        if (Build.VERSION.SDK_INT >= 29) {
            this.f43622b0 = new C0022ab();
        } else {
            this.f43622b0 = new C0011aa();
        }
    }

    /* renamed from: a1 */
    public static int[] m209780a1(int[] iArr) {
        int length = iArr.length;
        if (length != 0) {
            Arrays.sort(iArr);
            ArrayList arrayList = new ArrayList();
            for (int i : iArr) {
                if (i > 0 && Collections.binarySearch(arrayList, Integer.valueOf(i)) < 0) {
                    arrayList.add(Integer.valueOf(i));
                }
            }
            if (length != arrayList.size()) {
                int size = arrayList.size();
                int[] iArr2 = new int[size];
                for (int i2 = 0; i2 < size; i2++) {
                    iArr2[i2] = ((Integer) arrayList.get(i2)).intValue();
                }
                return iArr2;
            }
        }
        return iArr;
    }

    /* renamed from: a3 */
    public static Method m209781a3(String str) throws SecurityException {
        try {
            ConcurrentHashMap concurrentHashMap = f43611b2;
            Method declaredMethod = (Method) concurrentHashMap.get(str);
            if (declaredMethod == null && (declaredMethod = TextView.class.getDeclaredMethod(str, null)) != null) {
                declaredMethod.setAccessible(true);
                concurrentHashMap.put(str, declaredMethod);
            }
            return declaredMethod;
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a0 */
    public final void m209782a0() {
        if (m209784a4()) {
            if (this.f43613a1) {
                if (this.f43620a8.getMeasuredHeight() <= 0 || this.f43620a8.getMeasuredWidth() <= 0) {
                    return;
                }
                int measuredWidth = this.f43622b0.mo209757a1(this.f43620a8) ? 1048576 : (this.f43620a8.getMeasuredWidth() - this.f43620a8.getTotalPaddingLeft()) - this.f43620a8.getTotalPaddingRight();
                int height = (this.f43620a8.getHeight() - this.f43620a8.getCompoundPaddingBottom()) - this.f43620a8.getCompoundPaddingTop();
                if (measuredWidth <= 0 || height <= 0) {
                    return;
                }
                RectF rectF = f43610b1;
                synchronized (rectF) {
                    try {
                        rectF.setEmpty();
                        rectF.right = measuredWidth;
                        rectF.bottom = height;
                        float fM209783a2 = m209783a2(rectF);
                        if (fM209783a2 != this.f43620a8.getTextSize()) {
                            m209785a5(fM209783a2, 0);
                        }
                    } finally {
                    }
                }
            }
            this.f43613a1 = true;
        }
    }

    /* renamed from: a2 */
    public final int m209783a2(RectF rectF) {
        CharSequence transformation;
        int length = this.f43617a5.length;
        if (length == 0) {
            throw new IllegalStateException("No available text sizes to choose from.");
        }
        int i = length - 1;
        int i2 = 0;
        int i3 = 1;
        while (i3 <= i) {
            int i4 = (i3 + i) / 2;
            int i5 = this.f43617a5[i4];
            TextView textView = this.f43620a8;
            CharSequence text = textView.getText();
            TransformationMethod transformationMethod = textView.getTransformationMethod();
            if (transformationMethod != null && (transformation = transformationMethod.getTransformation(text, textView)) != null) {
                text = transformation;
            }
            int iM215375a1 = AbstractC1507z7.m215375a1(textView);
            TextPaint textPaint = this.f43619a7;
            if (textPaint == null) {
                this.f43619a7 = new TextPaint();
            } else {
                textPaint.reset();
            }
            this.f43619a7.set(textView.getPaint());
            this.f43619a7.setTextSize(i5);
            Object objInvoke = Layout.Alignment.ALIGN_NORMAL;
            try {
                objInvoke = m209781a3("getLayoutAlignment").invoke(textView, null);
            } catch (Exception unused) {
            }
            StaticLayout staticLayoutM215381a0 = AbstractC1509z9.m215381a0(text, (Layout.Alignment) objInvoke, Math.round(rectF.right), iM215375a1, textView, this.f43619a7, this.f43622b0);
            if ((iM215375a1 == -1 || (staticLayoutM215381a0.getLineCount() <= iM215375a1 && staticLayoutM215381a0.getLineEnd(staticLayoutM215381a0.getLineCount() - 1) == text.length())) && staticLayoutM215381a0.getHeight() <= rectF.bottom) {
                int i6 = i4 + 1;
                i2 = i3;
                i3 = i6;
            } else {
                i2 = i4 - 1;
                i = i2;
            }
        }
        return this.f43617a5[i2];
    }

    /* renamed from: a4 */
    public final boolean m209784a4() {
        return m209788a8() && this.f43612a0 != 0;
    }

    /* renamed from: a5 */
    public final void m209785a5(float f, int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Context context = this.f43621a9;
        float fApplyDimension = TypedValue.applyDimension(i, f, (context == null ? Resources.getSystem() : context.getResources()).getDisplayMetrics());
        TextView textView = this.f43620a8;
        if (fApplyDimension != textView.getPaint().getTextSize()) {
            textView.getPaint().setTextSize(fApplyDimension);
            boolean zM215377a0 = AbstractC1508z8.m215377a0(textView);
            if (textView.getLayout() != null) {
                this.f43613a1 = false;
                try {
                    Method methodM209781a3 = m209781a3("nullLayouts");
                    if (methodM209781a3 != null) {
                        methodM209781a3.invoke(textView, null);
                    }
                } catch (Exception unused) {
                }
                if (zM215377a0) {
                    textView.forceLayout();
                } else {
                    textView.requestLayout();
                }
                textView.invalidate();
            }
        }
    }

    /* renamed from: a6 */
    public final boolean m209786a6() {
        if (m209788a8() && this.f43612a0 == 1) {
            if (!this.f43618a6 || this.f43617a5.length == 0) {
                int iFloor = ((int) Math.floor((this.f43616a4 - this.f43615a3) / this.f43614a2)) + 1;
                int[] iArr = new int[iFloor];
                for (int i = 0; i < iFloor; i++) {
                    iArr[i] = Math.round((i * this.f43614a2) + this.f43615a3);
                }
                this.f43617a5 = m209780a1(iArr);
            }
            this.f43613a1 = true;
        } else {
            this.f43613a1 = false;
        }
        return this.f43613a1;
    }

    /* renamed from: a7 */
    public final boolean m209787a7() {
        boolean z = this.f43617a5.length > 0;
        this.f43618a6 = z;
        if (z) {
            this.f43612a0 = 1;
            this.f43615a3 = r0[0];
            this.f43616a4 = r0[r1 - 1];
            this.f43614a2 = -1.0f;
        }
        return z;
    }

    /* renamed from: a8 */
    public final boolean m209788a8() {
        return !(this.f43620a8 instanceof AppCompatEditText);
    }

    /* renamed from: a9 */
    public final void m209789a9(float f, float f2, float f3) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Minimum auto-size text size (" + f + "px) is less or equal to (0px)");
        }
        if (f2 <= f) {
            throw new IllegalArgumentException(AbstractC0003a2.m29b0("Maximum auto-size text size (", f2, "px) is less or equal to minimum auto-size text size (", f, "px)"));
        }
        if (f3 <= 0.0f) {
            throw new IllegalArgumentException("The auto-size step granularity (" + f3 + "px) is less or equal to (0px)");
        }
        this.f43612a0 = 1;
        this.f43615a3 = f;
        this.f43616a4 = f2;
        this.f43614a2 = f3;
        this.f43618a6 = false;
    }
}

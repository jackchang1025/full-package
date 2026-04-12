package p000;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import java.lang.reflect.Constructor;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: af */
/* loaded from: classes.dex */
public class C0026af {

    /* renamed from: a1 */
    public static final Class[] f43642a1 = {Context.class, AttributeSet.class};

    /* renamed from: a2 */
    public static final int[] f43643a2 = {R.attr.onClick};

    /* renamed from: a3 */
    public static final int[] f43644a3 = {R.attr.accessibilityHeading};

    /* renamed from: a4 */
    public static final int[] f43645a4 = {R.attr.accessibilityPaneTitle};

    /* renamed from: a5 */
    public static final int[] f43646a5 = {R.attr.screenReaderFocusable};

    /* renamed from: a6 */
    public static final String[] f43647a6 = {"android.widget.", "android.view.", "android.webkit."};

    /* renamed from: a7 */
    public static final t01 f43648a7 = new t01();

    /* renamed from: a0 */
    public final Object[] f43649a0 = new Object[2];

    /* renamed from: a0 */
    public AppCompatAutoCompleteTextView mo209793a0(Context context, AttributeSet attributeSet) {
        return new AppCompatAutoCompleteTextView(context, attributeSet);
    }

    /* renamed from: a1 */
    public AppCompatButton mo209794a1(Context context, AttributeSet attributeSet) {
        return new AppCompatButton(context, attributeSet);
    }

    /* renamed from: a2 */
    public AppCompatCheckBox mo209795a2(Context context, AttributeSet attributeSet) {
        return new AppCompatCheckBox(context, attributeSet);
    }

    /* renamed from: a3 */
    public AppCompatRadioButton mo209796a3(Context context, AttributeSet attributeSet) {
        return new AppCompatRadioButton(context, attributeSet);
    }

    /* renamed from: a4 */
    public AppCompatTextView mo209797a4(Context context, AttributeSet attributeSet) {
        return new AppCompatTextView(context, attributeSet);
    }

    /* renamed from: a5 */
    public final View m209798a5(Context context, String str, String str2) throws NoSuchMethodException, SecurityException {
        String strConcat;
        t01 t01Var = f43648a7;
        Constructor constructor = (Constructor) t01Var.getOrDefault(str, null);
        if (constructor == null) {
            if (str2 != null) {
                try {
                    strConcat = str2.concat(str);
                } catch (Exception unused) {
                    return null;
                }
            } else {
                strConcat = str;
            }
            constructor = Class.forName(strConcat, false, context.getClassLoader()).asSubclass(View.class).getConstructor(f43642a1);
            t01Var.put(str, constructor);
        }
        constructor.setAccessible(true);
        return (View) constructor.newInstance(this.f43649a0);
    }
}

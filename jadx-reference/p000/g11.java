package p000;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import java.lang.reflect.Array;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class g11 extends SpannableStringBuilder {

    /* renamed from: a0 */
    public final Class f56366a0;

    /* renamed from: a1 */
    public final ArrayList f56367a1;

    public g11(Class cls, CharSequence charSequence) {
        super(charSequence);
        this.f56367a1 = new ArrayList();
        b81.m210568a8(cls, "watcherClass cannot be null");
        this.f56366a0 = cls;
    }

    /* renamed from: a0 */
    public final void m212878a0() {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.f56367a1;
            if (i >= arrayList.size()) {
                return;
            }
            ((f11) arrayList.get(i)).f56135a1.incrementAndGet();
            i++;
        }
    }

    /* renamed from: a1 */
    public final void m212879a1() {
        m212882a4();
        int i = 0;
        while (true) {
            ArrayList arrayList = this.f56367a1;
            if (i >= arrayList.size()) {
                return;
            }
            ((f11) arrayList.get(i)).onTextChanged(this, 0, length(), length());
            i++;
        }
    }

    /* renamed from: a2 */
    public final f11 m212880a2(Object obj) {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.f56367a1;
            if (i >= arrayList.size()) {
                return null;
            }
            f11 f11Var = (f11) arrayList.get(i);
            if (f11Var.f56134a0 == obj) {
                return f11Var;
            }
            i++;
        }
    }

    /* renamed from: a3 */
    public final boolean m212881a3(Object obj) {
        if (obj != null) {
            return this.f56366a0 == obj.getClass();
        }
        return false;
    }

    /* renamed from: a4 */
    public final void m212882a4() {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.f56367a1;
            if (i >= arrayList.size()) {
                return;
            }
            ((f11) arrayList.get(i)).f56135a1.decrementAndGet();
            i++;
        }
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable, java.lang.Appendable
    public final Editable append(CharSequence charSequence) {
        super.append(charSequence);
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable
    public final Editable delete(int i, int i2) {
        super.delete(i, i2);
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Spanned
    public final int getSpanEnd(Object obj) {
        f11 f11VarM212880a2;
        if (m212881a3(obj) && (f11VarM212880a2 = m212880a2(obj)) != null) {
            obj = f11VarM212880a2;
        }
        return super.getSpanEnd(obj);
    }

    @Override // android.text.SpannableStringBuilder, android.text.Spanned
    public final int getSpanFlags(Object obj) {
        f11 f11VarM212880a2;
        if (m212881a3(obj) && (f11VarM212880a2 = m212880a2(obj)) != null) {
            obj = f11VarM212880a2;
        }
        return super.getSpanFlags(obj);
    }

    @Override // android.text.SpannableStringBuilder, android.text.Spanned
    public final int getSpanStart(Object obj) {
        f11 f11VarM212880a2;
        if (m212881a3(obj) && (f11VarM212880a2 = m212880a2(obj)) != null) {
            obj = f11VarM212880a2;
        }
        return super.getSpanStart(obj);
    }

    @Override // android.text.SpannableStringBuilder, android.text.Spanned
    public final Object[] getSpans(int i, int i2, Class cls) {
        if (this.f56366a0 != cls) {
            return super.getSpans(i, i2, cls);
        }
        f11[] f11VarArr = (f11[]) super.getSpans(i, i2, f11.class);
        Object[] objArr = (Object[]) Array.newInstance((Class<?>) cls, f11VarArr.length);
        for (int i3 = 0; i3 < f11VarArr.length; i3++) {
            objArr[i3] = f11VarArr[i3].f56134a0;
        }
        return objArr;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable
    public final Editable insert(int i, CharSequence charSequence) {
        super.insert(i, charSequence);
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Spanned
    public final int nextSpanTransition(int i, int i2, Class cls) {
        if (cls == null || this.f56366a0 == cls) {
            cls = f11.class;
        }
        return super.nextSpanTransition(i, i2, cls);
    }

    @Override // android.text.SpannableStringBuilder, android.text.Spannable
    public final void removeSpan(Object obj) {
        f11 f11VarM212880a2;
        if (m212881a3(obj)) {
            f11VarM212880a2 = m212880a2(obj);
            if (f11VarM212880a2 != null) {
                obj = f11VarM212880a2;
            }
        } else {
            f11VarM212880a2 = null;
        }
        super.removeSpan(obj);
        if (f11VarM212880a2 != null) {
            this.f56367a1.remove(f11VarM212880a2);
        }
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable
    public final /* bridge */ /* synthetic */ Editable replace(int i, int i2, CharSequence charSequence) {
        replace(i, i2, charSequence);
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Spannable
    public final void setSpan(Object obj, int i, int i2, int i3) {
        if (m212881a3(obj)) {
            f11 f11Var = new f11(obj);
            this.f56367a1.add(f11Var);
            obj = f11Var;
        }
        super.setSpan(obj, i, i2, i3);
    }

    @Override // android.text.SpannableStringBuilder, java.lang.CharSequence
    public final CharSequence subSequence(int i, int i2) {
        return new g11(this.f56366a0, this, i, i2);
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable, java.lang.Appendable
    public final SpannableStringBuilder append(CharSequence charSequence) {
        super.append(charSequence);
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable
    public final SpannableStringBuilder delete(int i, int i2) {
        super.delete(i, i2);
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable
    public final SpannableStringBuilder insert(int i, CharSequence charSequence) {
        super.insert(i, charSequence);
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable
    public final /* bridge */ /* synthetic */ Editable replace(int i, int i2, CharSequence charSequence, int i3, int i4) {
        replace(i, i2, charSequence, i3, i4);
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable, java.lang.Appendable
    public final Appendable append(CharSequence charSequence) {
        super.append(charSequence);
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable
    public final Editable insert(int i, CharSequence charSequence, int i2, int i3) {
        super.insert(i, charSequence, i2, i3);
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable
    public final SpannableStringBuilder replace(int i, int i2, CharSequence charSequence) {
        m212878a0();
        super.replace(i, i2, charSequence);
        m212882a4();
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable, java.lang.Appendable
    public final Editable append(char c) {
        super.append(c);
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable
    public final SpannableStringBuilder insert(int i, CharSequence charSequence, int i2, int i3) {
        super.insert(i, charSequence, i2, i3);
        return this;
    }

    public g11(Class cls, g11 g11Var, int i, int i2) {
        super(g11Var, i, i2);
        this.f56367a1 = new ArrayList();
        b81.m210568a8(cls, "watcherClass cannot be null");
        this.f56366a0 = cls;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable, java.lang.Appendable
    public final SpannableStringBuilder append(char c) {
        super.append(c);
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable, java.lang.Appendable
    public final Appendable append(char c) {
        super.append(c);
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable
    public final SpannableStringBuilder replace(int i, int i2, CharSequence charSequence, int i3, int i4) {
        m212878a0();
        super.replace(i, i2, charSequence, i3, i4);
        m212882a4();
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable, java.lang.Appendable
    public final Editable append(CharSequence charSequence, int i, int i2) {
        super.append(charSequence, i, i2);
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable, java.lang.Appendable
    public final SpannableStringBuilder append(CharSequence charSequence, int i, int i2) {
        super.append(charSequence, i, i2);
        return this;
    }

    @Override // android.text.SpannableStringBuilder, android.text.Editable, java.lang.Appendable
    public final Appendable append(CharSequence charSequence, int i, int i2) {
        super.append(charSequence, i, i2);
        return this;
    }

    @Override // android.text.SpannableStringBuilder
    public final SpannableStringBuilder append(CharSequence charSequence, Object obj, int i) {
        super.append(charSequence, obj, i);
        return this;
    }
}

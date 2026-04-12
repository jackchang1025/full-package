package p000;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Property;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class yg0 {

    /* renamed from: a0 */
    public final t01 f61314a0 = new t01();

    /* renamed from: a1 */
    public final t01 f61315a1 = new t01();

    /* renamed from: a0 */
    public static yg0 m215280a0(Context context, TypedArray typedArray, int i) {
        int resourceId;
        if (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0) {
            return null;
        }
        return m215281a1(context, resourceId);
    }

    /* renamed from: a1 */
    public static yg0 m215281a1(Context context, int i) throws Resources.NotFoundException {
        try {
            Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(context, i);
            if (animatorLoadAnimator instanceof AnimatorSet) {
                return m215282a2(((AnimatorSet) animatorLoadAnimator).getChildAnimations());
            }
            if (animatorLoadAnimator == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(animatorLoadAnimator);
            return m215282a2(arrayList);
        } catch (Exception unused) {
            Integer.toHexString(i);
            return null;
        }
    }

    /* renamed from: a2 */
    public static yg0 m215282a2(ArrayList arrayList) {
        yg0 yg0Var = new yg0();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Animator animator = (Animator) arrayList.get(i);
            if (!(animator instanceof ObjectAnimator)) {
                throw new IllegalArgumentException("Animator must be an ObjectAnimator: " + animator);
            }
            ObjectAnimator objectAnimator = (ObjectAnimator) animator;
            yg0Var.m215287a7(objectAnimator.getPropertyName(), objectAnimator.getValues());
            String propertyName = objectAnimator.getPropertyName();
            long startDelay = objectAnimator.getStartDelay();
            long duration = objectAnimator.getDuration();
            TimeInterpolator interpolator = objectAnimator.getInterpolator();
            if ((interpolator instanceof AccelerateDecelerateInterpolator) || interpolator == null) {
                interpolator = AbstractC1249t7.f60179a1;
            } else if (interpolator instanceof AccelerateInterpolator) {
                interpolator = AbstractC1249t7.f60180a2;
            } else if (interpolator instanceof DecelerateInterpolator) {
                interpolator = AbstractC1249t7.f60181a3;
            }
            zg0 zg0Var = new zg0();
            zg0Var.f61548a3 = 0;
            zg0Var.f61549a4 = 1;
            zg0Var.f61545a0 = startDelay;
            zg0Var.f61546a1 = duration;
            zg0Var.f61547a2 = interpolator;
            zg0Var.f61548a3 = objectAnimator.getRepeatCount();
            zg0Var.f61549a4 = objectAnimator.getRepeatMode();
            yg0Var.f61314a0.put(propertyName, zg0Var);
        }
        return yg0Var;
    }

    /* renamed from: a3 */
    public final ObjectAnimator m215283a3(String str, Object obj, Property property) {
        ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(obj, m215284a4(str));
        objectAnimatorOfPropertyValuesHolder.setProperty(property);
        m215285a5(str).m215402a0(objectAnimatorOfPropertyValuesHolder);
        return objectAnimatorOfPropertyValuesHolder;
    }

    /* renamed from: a4 */
    public final PropertyValuesHolder[] m215284a4(String str) {
        if (!m215286a6(str)) {
            throw new IllegalArgumentException();
        }
        PropertyValuesHolder[] propertyValuesHolderArr = (PropertyValuesHolder[]) this.f61315a1.getOrDefault(str, null);
        PropertyValuesHolder[] propertyValuesHolderArr2 = new PropertyValuesHolder[propertyValuesHolderArr.length];
        for (int i = 0; i < propertyValuesHolderArr.length; i++) {
            propertyValuesHolderArr2[i] = propertyValuesHolderArr[i].clone();
        }
        return propertyValuesHolderArr2;
    }

    /* renamed from: a5 */
    public final zg0 m215285a5(String str) {
        t01 t01Var = this.f61314a0;
        if (t01Var.getOrDefault(str, null) != null) {
            return (zg0) t01Var.getOrDefault(str, null);
        }
        throw new IllegalArgumentException();
    }

    /* renamed from: a6 */
    public final boolean m215286a6(String str) {
        return this.f61315a1.getOrDefault(str, null) != null;
    }

    /* renamed from: a7 */
    public final void m215287a7(String str, PropertyValuesHolder[] propertyValuesHolderArr) {
        this.f61315a1.put(str, propertyValuesHolderArr);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof yg0) {
            return this.f61314a0.equals(((yg0) obj).f61314a0);
        }
        return false;
    }

    public final int hashCode() {
        return this.f61314a0.hashCode();
    }

    public final String toString() {
        return "\n" + yg0.class.getName() + '{' + Integer.toHexString(System.identityHashCode(this)) + " timings: " + this.f61314a0 + "}\n";
    }
}

package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.R$styleable;
import androidx.fragment.app.AbstractComponentCallbacksC0069a5;
import androidx.fragment.app.C0071a7;
import androidx.fragment.app.C0072a8;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class c00 implements LayoutInflater.Factory2 {

    /* renamed from: a0 */
    public final C0071a7 f46046a0;

    public c00(C0071a7 c0071a7) {
        this.f46046a0 = c0071a7;
    }

    @Override // android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        boolean zIsAssignableFrom;
        C0072a8 c0072a8M210167a5;
        View view2;
        C0071a7 c0071a7 = this.f46046a0;
        zg1 zg1Var = c0071a7.f45124a2;
        if (FragmentContainerView.class.getName().equals(str)) {
            FragmentContainerView fragmentContainerView = new FragmentContainerView(context, attributeSet);
            fragmentContainerView.f45025a3 = true;
            String classAttribute = attributeSet.getClassAttribute();
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.FragmentContainerView);
            if (classAttribute == null) {
                classAttribute = typedArrayObtainStyledAttributes.getString(R$styleable.FragmentContainerView_android_name);
            }
            String string = typedArrayObtainStyledAttributes.getString(R$styleable.FragmentContainerView_android_tag);
            typedArrayObtainStyledAttributes.recycle();
            int id = fragmentContainerView.getId();
            AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5M210185c3 = c0071a7.m210185c3(id);
            if (classAttribute != null && abstractComponentCallbacksC0069a5M210185c3 == null) {
                if (id <= 0) {
                    throw new IllegalStateException(AbstractC0003a2.m33b4("FragmentContainerView must have an android:id to add Fragment ", classAttribute, string != null ? " with tag ".concat(string) : ""));
                }
                e00 e00VarM210187c5 = c0071a7.m210187c5();
                context.getClassLoader();
                AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5M212648a0 = e00VarM210187c5.m212648a0(classAttribute);
                abstractComponentCallbacksC0069a5M212648a0.f45105c8 = true;
                C1499z c1499z = abstractComponentCallbacksC0069a5M212648a0.f45095b8;
                if ((c1499z != null ? c1499z.f61418c6 : null) != null) {
                    abstractComponentCallbacksC0069a5M212648a0.f45105c8 = true;
                }
                C0389cs c0389cs = new C0389cs(c0071a7);
                c0389cs.f55497b4 = true;
                abstractComponentCallbacksC0069a5M212648a0.f45106c9 = fragmentContainerView;
                c0389cs.m212523a4(fragmentContainerView.getId(), abstractComponentCallbacksC0069a5M212648a0, string, 1);
                if (c0389cs.f55489a6) {
                    throw new IllegalStateException("This transaction is already being added to the back stack");
                }
                c0389cs.f55498b5.m210182c0(c0389cs, true);
            }
            ArrayList arrayListM215409a5 = zg1Var.m215409a5();
            int size = arrayListM215409a5.size();
            while (id < size) {
                Object obj = arrayListM215409a5.get(id);
                id++;
                C0072a8 c0072a8 = (C0072a8) obj;
                AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = c0072a8.f45157a2;
                if (abstractComponentCallbacksC0069a5.f45099c2 == fragmentContainerView.getId() && (view2 = abstractComponentCallbacksC0069a5.f45107d0) != null && view2.getParent() == null) {
                    abstractComponentCallbacksC0069a5.f45106c9 = fragmentContainerView;
                    c0072a8.m210205a1();
                }
            }
            return fragmentContainerView;
        }
        if ("fragment".equals(str)) {
            String attributeValue = attributeSet.getAttributeValue(null, "class");
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R$styleable.Fragment);
            if (attributeValue == null) {
                attributeValue = typedArrayObtainStyledAttributes2.getString(R$styleable.Fragment_android_name);
            }
            int resourceId = typedArrayObtainStyledAttributes2.getResourceId(R$styleable.Fragment_android_id, -1);
            String string2 = typedArrayObtainStyledAttributes2.getString(R$styleable.Fragment_android_tag);
            typedArrayObtainStyledAttributes2.recycle();
            if (attributeValue != null) {
                try {
                    zIsAssignableFrom = AbstractComponentCallbacksC0069a5.class.isAssignableFrom(e00.m212646a1(context.getClassLoader(), attributeValue));
                } catch (ClassNotFoundException unused) {
                    zIsAssignableFrom = false;
                }
                if (zIsAssignableFrom) {
                    id = view != null ? view.getId() : 0;
                    if (id == -1 && resourceId == -1 && string2 == null) {
                        throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + attributeValue);
                    }
                    AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5M210185c32 = resourceId != -1 ? c0071a7.m210185c3(resourceId) : null;
                    if (abstractComponentCallbacksC0069a5M210185c32 == null && string2 != null) {
                        ArrayList arrayList = (ArrayList) zg1Var.f61551a0;
                        int size2 = arrayList.size() - 1;
                        while (true) {
                            if (size2 >= 0) {
                                AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a52 = (AbstractComponentCallbacksC0069a5) arrayList.get(size2);
                                if (abstractComponentCallbacksC0069a52 != null && string2.equals(abstractComponentCallbacksC0069a52.f45100c3)) {
                                    abstractComponentCallbacksC0069a5M210185c32 = abstractComponentCallbacksC0069a52;
                                    break;
                                }
                                size2--;
                            } else {
                                Iterator it = ((HashMap) zg1Var.f61552a1).values().iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        abstractComponentCallbacksC0069a5M210185c32 = null;
                                        break;
                                    }
                                    C0072a8 c0072a82 = (C0072a8) it.next();
                                    if (c0072a82 != null) {
                                        abstractComponentCallbacksC0069a5M210185c32 = c0072a82.f45157a2;
                                        if (string2.equals(abstractComponentCallbacksC0069a5M210185c32.f45100c3)) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (abstractComponentCallbacksC0069a5M210185c32 == null && id != -1) {
                        abstractComponentCallbacksC0069a5M210185c32 = c0071a7.m210185c3(id);
                    }
                    if (abstractComponentCallbacksC0069a5M210185c32 == null) {
                        e00 e00VarM210187c52 = c0071a7.m210187c5();
                        context.getClassLoader();
                        abstractComponentCallbacksC0069a5M210185c32 = e00VarM210187c52.m212648a0(attributeValue);
                        abstractComponentCallbacksC0069a5M210185c32.f45089b2 = true;
                        abstractComponentCallbacksC0069a5M210185c32.f45098c1 = resourceId != 0 ? resourceId : id;
                        abstractComponentCallbacksC0069a5M210185c32.f45099c2 = id;
                        abstractComponentCallbacksC0069a5M210185c32.f45100c3 = string2;
                        abstractComponentCallbacksC0069a5M210185c32.f45090b3 = true;
                        abstractComponentCallbacksC0069a5M210185c32.f45094b7 = c0071a7;
                        C1499z c1499z2 = c0071a7.f45135b3;
                        abstractComponentCallbacksC0069a5M210185c32.f45095b8 = c1499z2;
                        FragmentActivity fragmentActivity = c1499z2.f61419c7;
                        abstractComponentCallbacksC0069a5M210185c32.f45105c8 = true;
                        if ((c1499z2 != null ? c1499z2.f61418c6 : null) != null) {
                            abstractComponentCallbacksC0069a5M210185c32.f45105c8 = true;
                        }
                        c0072a8M210167a5 = c0071a7.m210162a0(abstractComponentCallbacksC0069a5M210185c32);
                        if (C0071a7.m210158c7(2)) {
                            abstractComponentCallbacksC0069a5M210185c32.toString();
                            Integer.toHexString(resourceId);
                        }
                    } else {
                        if (abstractComponentCallbacksC0069a5M210185c32.f45090b3) {
                            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string2 + ", or parent id 0x" + Integer.toHexString(id) + " with another fragment for " + attributeValue);
                        }
                        abstractComponentCallbacksC0069a5M210185c32.f45090b3 = true;
                        abstractComponentCallbacksC0069a5M210185c32.f45094b7 = c0071a7;
                        C1499z c1499z3 = c0071a7.f45135b3;
                        abstractComponentCallbacksC0069a5M210185c32.f45095b8 = c1499z3;
                        FragmentActivity fragmentActivity2 = c1499z3.f61419c7;
                        abstractComponentCallbacksC0069a5M210185c32.f45105c8 = true;
                        if ((c1499z3 != null ? c1499z3.f61418c6 : null) != null) {
                            abstractComponentCallbacksC0069a5M210185c32.f45105c8 = true;
                        }
                        c0072a8M210167a5 = c0071a7.m210167a5(abstractComponentCallbacksC0069a5M210185c32);
                        if (C0071a7.m210158c7(2)) {
                            abstractComponentCallbacksC0069a5M210185c32.toString();
                            Integer.toHexString(resourceId);
                        }
                    }
                    abstractComponentCallbacksC0069a5M210185c32.f45106c9 = (ViewGroup) view;
                    c0072a8M210167a5.m210214b0();
                    c0072a8M210167a5.m210213a9();
                    View view3 = abstractComponentCallbacksC0069a5M210185c32.f45107d0;
                    if (view3 == null) {
                        throw new IllegalStateException(AbstractC0003a2.m33b4("Fragment ", attributeValue, " did not create a view."));
                    }
                    if (resourceId != 0) {
                        view3.setId(resourceId);
                    }
                    if (abstractComponentCallbacksC0069a5M210185c32.f45107d0.getTag() == null) {
                        abstractComponentCallbacksC0069a5M210185c32.f45107d0.setTag(string2);
                    }
                    abstractComponentCallbacksC0069a5M210185c32.f45107d0.addOnAttachStateChangeListener(new b00(this, c0072a8M210167a5));
                    return abstractComponentCallbacksC0069a5M210185c32.f45107d0;
                }
            }
        }
        return null;
    }
}

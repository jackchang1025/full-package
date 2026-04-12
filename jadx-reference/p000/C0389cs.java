package p000;

import androidx.fragment.app.AbstractComponentCallbacksC0069a5;
import androidx.fragment.app.C0071a7;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: cs */
/* loaded from: classes.dex */
public final class C0389cs implements h00 {

    /* renamed from: a0 */
    public final ArrayList f55483a0;

    /* renamed from: a1 */
    public int f55484a1;

    /* renamed from: a2 */
    public int f55485a2;

    /* renamed from: a3 */
    public int f55486a3;

    /* renamed from: a4 */
    public int f55487a4;

    /* renamed from: a5 */
    public int f55488a5;

    /* renamed from: a6 */
    public boolean f55489a6;

    /* renamed from: a7 */
    public String f55490a7;

    /* renamed from: a8 */
    public int f55491a8;

    /* renamed from: a9 */
    public CharSequence f55492a9;

    /* renamed from: b0 */
    public int f55493b0;

    /* renamed from: b1 */
    public CharSequence f55494b1;

    /* renamed from: b2 */
    public ArrayList f55495b2;

    /* renamed from: b3 */
    public ArrayList f55496b3;

    /* renamed from: b4 */
    public boolean f55497b4;

    /* renamed from: b5 */
    public final C0071a7 f55498b5;

    /* renamed from: b6 */
    public boolean f55499b6;

    /* renamed from: b7 */
    public int f55500b7;

    public C0389cs(C0071a7 c0071a7) {
        c0071a7.m210187c5();
        C1499z c1499z = c0071a7.f45135b3;
        if (c1499z != null) {
            c1499z.f61419c7.getClassLoader();
        }
        this.f55483a0 = new ArrayList();
        this.f55497b4 = false;
        this.f55500b7 = -1;
        this.f55498b5 = c0071a7;
    }

    @Override // p000.h00
    /* renamed from: a0 */
    public final boolean mo212519a0(ArrayList arrayList, ArrayList arrayList2) {
        if (C0071a7.m210158c7(2)) {
            toString();
        }
        arrayList.add(this);
        arrayList2.add(Boolean.FALSE);
        if (!this.f55489a6) {
            return true;
        }
        C0071a7 c0071a7 = this.f55498b5;
        if (c0071a7.f45125a3 == null) {
            c0071a7.f45125a3 = new ArrayList();
        }
        c0071a7.f45125a3.add(this);
        return true;
    }

    /* renamed from: a1 */
    public final void m212520a1(m00 m00Var) {
        this.f55483a0.add(m00Var);
        m00Var.f58213a2 = this.f55484a1;
        m00Var.f58214a3 = this.f55485a2;
        m00Var.f58215a4 = this.f55486a3;
        m00Var.f58216a5 = this.f55487a4;
    }

    /* renamed from: a2 */
    public final void m212521a2(int i) {
        if (this.f55489a6) {
            if (C0071a7.m210158c7(2)) {
                toString();
            }
            ArrayList arrayList = this.f55483a0;
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                m00 m00Var = (m00) arrayList.get(i2);
                AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5 = m00Var.f58212a1;
                if (abstractComponentCallbacksC0069a5 != null) {
                    abstractComponentCallbacksC0069a5.f45093b6 += i;
                    if (C0071a7.m210158c7(2)) {
                        Objects.toString(m00Var.f58212a1);
                        int i3 = m00Var.f58212a1.f45093b6;
                    }
                }
            }
        }
    }

    /* renamed from: a3 */
    public final int m212522a3(boolean z) {
        if (this.f55499b6) {
            throw new IllegalStateException("commit already called");
        }
        if (C0071a7.m210158c7(2)) {
            toString();
            PrintWriter printWriter = new PrintWriter(new kc0());
            m212524a5("  ", printWriter, true);
            printWriter.close();
        }
        this.f55499b6 = true;
        boolean z2 = this.f55489a6;
        C0071a7 c0071a7 = this.f55498b5;
        if (z2) {
            this.f55500b7 = c0071a7.f45130a8.getAndIncrement();
        } else {
            this.f55500b7 = -1;
        }
        c0071a7.m210179b7(this, z);
        return this.f55500b7;
    }

    /* renamed from: a4 */
    public final void m212523a4(int i, AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5, String str, int i2) {
        Class<?> cls = abstractComponentCallbacksC0069a5.getClass();
        int modifiers = cls.getModifiers();
        if (cls.isAnonymousClass() || !Modifier.isPublic(modifiers) || (cls.isMemberClass() && !Modifier.isStatic(modifiers))) {
            throw new IllegalStateException("Fragment " + cls.getCanonicalName() + " must be a public static class to be  properly recreated from instance state.");
        }
        if (str != null) {
            String str2 = abstractComponentCallbacksC0069a5.f45100c3;
            if (str2 != null && !str.equals(str2)) {
                throw new IllegalStateException("Can't change tag of fragment " + abstractComponentCallbacksC0069a5 + ": was " + abstractComponentCallbacksC0069a5.f45100c3 + " now " + str);
            }
            abstractComponentCallbacksC0069a5.f45100c3 = str;
        }
        if (i != 0) {
            if (i == -1) {
                throw new IllegalArgumentException("Can't add fragment " + abstractComponentCallbacksC0069a5 + " with tag " + str + " to container view with no id");
            }
            int i3 = abstractComponentCallbacksC0069a5.f45098c1;
            if (i3 != 0 && i3 != i) {
                throw new IllegalStateException("Can't change container ID of fragment " + abstractComponentCallbacksC0069a5 + ": was " + abstractComponentCallbacksC0069a5.f45098c1 + " now " + i);
            }
            abstractComponentCallbacksC0069a5.f45098c1 = i;
            abstractComponentCallbacksC0069a5.f45099c2 = i;
        }
        m212520a1(new m00(i2, abstractComponentCallbacksC0069a5));
        abstractComponentCallbacksC0069a5.f45094b7 = this.f55498b5;
    }

    /* renamed from: a5 */
    public final void m212524a5(String str, PrintWriter printWriter, boolean z) {
        String str2;
        if (z) {
            printWriter.print(str);
            printWriter.print("mName=");
            printWriter.print(this.f55490a7);
            printWriter.print(" mIndex=");
            printWriter.print(this.f55500b7);
            printWriter.print(" mCommitted=");
            printWriter.println(this.f55499b6);
            if (this.f55488a5 != 0) {
                printWriter.print(str);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.f55488a5));
            }
            if (this.f55484a1 != 0 || this.f55485a2 != 0) {
                printWriter.print(str);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.f55484a1));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.f55485a2));
            }
            if (this.f55486a3 != 0 || this.f55487a4 != 0) {
                printWriter.print(str);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.f55486a3));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.f55487a4));
            }
            if (this.f55491a8 != 0 || this.f55492a9 != null) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.f55491a8));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.f55492a9);
            }
            if (this.f55493b0 != 0 || this.f55494b1 != null) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.f55493b0));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.f55494b1);
            }
        }
        ArrayList arrayList = this.f55483a0;
        if (arrayList.isEmpty()) {
            return;
        }
        printWriter.print(str);
        printWriter.println("Operations:");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            m00 m00Var = (m00) arrayList.get(i);
            switch (m00Var.f58211a0) {
                case 0:
                    str2 = "NULL";
                    break;
                case 1:
                    str2 = "ADD";
                    break;
                case 2:
                    str2 = "REPLACE";
                    break;
                case 3:
                    str2 = "REMOVE";
                    break;
                case 4:
                    str2 = "HIDE";
                    break;
                case 5:
                    str2 = "SHOW";
                    break;
                case 6:
                    str2 = "DETACH";
                    break;
                case 7:
                    str2 = "ATTACH";
                    break;
                case 8:
                    str2 = "SET_PRIMARY_NAV";
                    break;
                case 9:
                    str2 = "UNSET_PRIMARY_NAV";
                    break;
                case 10:
                    str2 = "OP_SET_MAX_LIFECYCLE";
                    break;
                default:
                    str2 = "cmd=" + m00Var.f58211a0;
                    break;
            }
            printWriter.print(str);
            printWriter.print("  Op #");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.print(str2);
            printWriter.print(" ");
            printWriter.println(m00Var.f58212a1);
            if (z) {
                if (m00Var.f58213a2 != 0 || m00Var.f58214a3 != 0) {
                    printWriter.print(str);
                    printWriter.print("enterAnim=#");
                    printWriter.print(Integer.toHexString(m00Var.f58213a2));
                    printWriter.print(" exitAnim=#");
                    printWriter.println(Integer.toHexString(m00Var.f58214a3));
                }
                if (m00Var.f58215a4 != 0 || m00Var.f58216a5 != 0) {
                    printWriter.print(str);
                    printWriter.print("popEnterAnim=#");
                    printWriter.print(Integer.toHexString(m00Var.f58215a4));
                    printWriter.print(" popExitAnim=#");
                    printWriter.println(Integer.toHexString(m00Var.f58216a5));
                }
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("BackStackEntry{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.f55500b7 >= 0) {
            sb.append(" #");
            sb.append(this.f55500b7);
        }
        if (this.f55490a7 != null) {
            sb.append(" ");
            sb.append(this.f55490a7);
        }
        sb.append("}");
        return sb.toString();
    }
}

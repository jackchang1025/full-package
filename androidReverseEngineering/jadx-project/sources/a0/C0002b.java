package a0;

import com.guard.wallet.service.AccessibilityDelegateManager;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Predicate;
import p012o.C0416e;

/* renamed from: a0.b */
/* loaded from: classes.dex */
public final class C0002b implements Predicate {

    /* renamed from: a */
    public final /* synthetic */ int f2a;

    /* renamed from: b */
    public final /* synthetic */ String f3b;

    /* renamed from: c */
    public final /* synthetic */ AccessibilityDelegateManager f4c;

    public /* synthetic */ C0002b(AccessibilityDelegateManager accessibilityDelegateManager, String str, int i2) {
        this.f2a = i2;
        this.f4c = accessibilityDelegateManager;
        this.f3b = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i2 = this.f2a;
        String str = this.f3b;
        switch (i2) {
            case 0:
                C0416e c0416e = (C0416e) obj;
                if (c0416e != null && Objects.equals(c0416e.f864c, str)) {
                    c0416e.mo1001d();
                    this.f4c.m518C(c0416e.getClass().getName(), new LinkedList(c0416e.f865d));
                    break;
                } else {
                    break;
                }
                break;
            default:
                String[] split = str.split(":");
                String[] split2 = ((String) obj).split(":");
                if (split.length >= 2 && split2.length >= 2) {
                    boolean z2 = "NULL".equals(split[0]) || "NULL".equals(split2[0]) || Objects.equals(split[0], split2[0]);
                    boolean z3 = "NULL".equals(split[1]) || "NULL".equals(split2[1]) || Objects.equals(split[1], split2[1]);
                    if ("android.inputmethodservice.SoftInputWindow".equals(split[1])) {
                        z3 = true;
                    }
                    if (z2 && z3) {
                    }
                }
                break;
        }
        return false;
    }
}

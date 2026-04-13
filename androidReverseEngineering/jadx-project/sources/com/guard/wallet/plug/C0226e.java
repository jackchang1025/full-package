package com.guard.wallet.plug;

import com.guard.wallet.req.ListenPropResponse;
import java.util.Objects;
import java.util.function.Predicate;

/* renamed from: com.guard.wallet.plug.e */
/* loaded from: classes.dex */
public final class C0226e implements Predicate {

    /* renamed from: a */
    public final /* synthetic */ int f271a;

    public C0226e(int i2) {
        this.f271a = i2;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return Objects.equals(((ListenPropResponse) obj).getTargetIndex(), Integer.valueOf(this.f271a));
    }
}

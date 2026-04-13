package com.guard.wallet.http;

import com.google.json.JsonObject;
import java.util.concurrent.Callable;
import l0.C0383m;

/* renamed from: com.guard.wallet.http.k */
/* loaded from: classes.dex */
public final class CallableC0206k implements Callable {

    /* renamed from: a */
    public final /* synthetic */ int f248a;

    /* renamed from: b */
    public final Object f249b;

    /* renamed from: c */
    public final String f250c;

    /* renamed from: d */
    public final String f251d;

    public /* synthetic */ CallableC0206k(Object obj, String str, String str2, int i2) {
        this.f248a = i2;
        this.f249b = obj;
        this.f250c = str;
        this.f251d = str2;
    }

    /* renamed from: a */
    public final JsonObject m412a() {
        int i2 = this.f248a;
        String str = this.f251d;
        Object obj = this.f249b;
        String str2 = this.f250c;
        switch (i2) {
            case 0:
                C0204i c0204i = new C0204i(str2);
                String m406e = c0204i.m406e(obj, str);
                C0383m c0383m = new C0383m();
                c0383m.m956d(m406e);
                c0383m.m954b("GET", null);
                return c0204i.m404b(c0383m.m953a());
            default:
                C0204i c0204i2 = new C0204i(str2);
                return c0204i2.m404b(c0204i2.m409i(obj, str));
        }
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() {
        switch (this.f248a) {
        }
        return m412a();
    }
}

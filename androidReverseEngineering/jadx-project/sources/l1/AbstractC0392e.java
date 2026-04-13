package l1;

import java.util.TreeMap;
import com.guard.wallet.entity.BuildConfig;

/* renamed from: l1.e */
/* loaded from: classes.dex */
public abstract class AbstractC0392e implements InterfaceC0389b {

    /* renamed from: a */
    public final TreeMap f789a = new TreeMap(String.CASE_INSENSITIVE_ORDER);

    /* renamed from: a */
    public final String m960a(String str) {
        String str2 = (String) this.f789a.get(str);
        return str2 == null ? BuildConfig.FLAVOR : str2;
    }

    /* renamed from: b */
    public final void m961b(String str, String str2) {
        this.f789a.put(str, str2);
    }
}

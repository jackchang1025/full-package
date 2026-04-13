package j0;

import com.guard.wallet.http.C0203h;
import i0.C0334e;
import java.util.Locale;

/* renamed from: j0.c */
/* loaded from: classes.dex */
public class C0353c {

    /* renamed from: a */
    public C0203h f696a;

    /* renamed from: b */
    public final C0334e f697b;

    /* renamed from: c */
    public final long f698c;

    public C0353c(C0203h c0203h) {
        this.f698c = -1L;
        this.f696a = c0203h;
        this.f697b = C0334e.m874c(c0203h.m395i("Content-Disposition"), ";", true, null);
    }

    public C0353c(String str, long j2) {
        this.f698c = -1L;
        this.f698c = j2;
        this.f696a = new C0203h(4);
        this.f696a.m397k("Content-Disposition", new StringBuilder(String.format(Locale.ENGLISH, "form-data; name=\"%s\"", str)).toString());
        this.f697b = C0334e.m874c(this.f696a.m395i("Content-Disposition"), ";", true, null);
    }
}

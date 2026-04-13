package t0;

import java.text.SimpleDateFormat;
import java.util.Locale;
import q0.AbstractC0887c;

/* renamed from: t0.c */
/* loaded from: classes.dex */
public final class C0914c extends ThreadLocal {
    @Override // java.lang.ThreadLocal
    public final Object initialValue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        simpleDateFormat.setLenient(false);
        simpleDateFormat.setTimeZone(AbstractC0887c.f1941h);
        return simpleDateFormat;
    }
}

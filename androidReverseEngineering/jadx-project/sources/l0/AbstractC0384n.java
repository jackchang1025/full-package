package l0;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: l0.n */
/* loaded from: classes.dex */
public abstract class AbstractC0384n implements InterfaceC0386p {

    /* renamed from: a */
    public final ArrayList f782a = new ArrayList();

    static {
        Hashtable hashtable = new Hashtable();
        hashtable.put("js", "application/javascript");
        hashtable.put("json", "application/json");
        hashtable.put("png", "image/png");
        hashtable.put("jpg", "image/jpeg");
        hashtable.put("jpeg", "image/jpeg");
        hashtable.put("html", "text/html");
        hashtable.put("css", "text/css");
        hashtable.put("mp4", "video/mp4");
        hashtable.put("mov", "video/quicktime");
        hashtable.put("wmv", "video/x-ms-wmv");
        hashtable.put("txt", "text/plain");
        new Hashtable();
    }

    /* renamed from: a */
    public final void m957a(String str, InterfaceC0385o interfaceC0385o) {
        C0382l c0382l = new C0382l();
        c0382l.f775b = Pattern.compile("^[\\d\\D]*");
        c0382l.f776c = interfaceC0385o;
        c0382l.f774a = str;
        synchronized (this.f782a) {
            this.f782a.add(c0382l);
        }
    }

    /* renamed from: b */
    public final C0383m m958b(String str, String str2) {
        synchronized (this.f782a) {
            Iterator it = this.f782a.iterator();
            while (it.hasNext()) {
                C0382l c0382l = (C0382l) it.next();
                if (TextUtils.equals(str, c0382l.f774a) || c0382l.f774a == null) {
                    Matcher matcher = c0382l.f775b.matcher(str2);
                    if (matcher.matches()) {
                        InterfaceC0385o interfaceC0385o = c0382l.f776c;
                        if (!(interfaceC0385o instanceof InterfaceC0386p)) {
                            return new C0383m(str, str2, matcher, interfaceC0385o);
                        }
                        return ((AbstractC0384n) ((InterfaceC0386p) c0382l.f776c)).m958b(str, matcher.group(1));
                    }
                }
            }
            return null;
        }
    }
}

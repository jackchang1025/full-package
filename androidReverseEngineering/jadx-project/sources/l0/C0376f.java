package l0;

import g0.InterfaceC0309a;
import java.util.ArrayList;
import java.util.Hashtable;

/* renamed from: l0.f */
/* loaded from: classes.dex */
public final class C0376f extends AbstractC0384n {

    /* renamed from: e */
    public static final Hashtable f745e;

    /* renamed from: b */
    public final ArrayList f746b = new ArrayList();

    /* renamed from: c */
    public final C0375e f747c = new C0375e(this);

    /* renamed from: d */
    public InterfaceC0309a f748d;

    static {
        Hashtable hashtable = new Hashtable();
        f745e = hashtable;
        hashtable.put(200, "OK");
        hashtable.put(202, "Accepted");
        hashtable.put(206, "Partial Content");
        hashtable.put(101, "Switching Protocols");
        hashtable.put(301, "Moved Permanently");
        hashtable.put(302, "Found");
        hashtable.put(304, "Not Modified");
        hashtable.put(400, "Bad Request");
        hashtable.put(401, "Unauthorized");
        hashtable.put(404, "Not Found");
        hashtable.put(500, "Internal Server Error");
    }
}

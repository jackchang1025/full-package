package i0;

import android.net.Uri;
import android.text.TextUtils;
import b0.C0078b;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/* renamed from: i0.e */
/* loaded from: classes.dex */
public class C0334e extends LinkedHashMap implements Iterable {

    /* renamed from: a */
    public static final C0078b f647a = new C0078b(25);

    /* renamed from: b */
    public static final C0078b f648b = new C0078b(26);

    public C0334e() {
    }

    public C0334e(C0334e c0334e) {
        putAll(c0334e);
    }

    /* renamed from: c */
    public static C0334e m874c(String str, String str2, boolean z2, C0078b c0078b) {
        C0334e c0334e = new C0334e();
        if (str != null) {
            for (String str3 : str.split(str2)) {
                String[] split = str3.split("=", 2);
                String trim = split[0].trim();
                if (!TextUtils.isEmpty(trim)) {
                    String str4 = split.length > 1 ? split[1] : null;
                    if (str4 != null && z2 && str4.endsWith("\"") && str4.startsWith("\"")) {
                        str4 = str4.substring(1, str4.length() - 1);
                    }
                    if (str4 != null && c0078b != null) {
                        switch (c0078b.f85d) {
                            case 25:
                                trim = Uri.decode(trim);
                                break;
                            default:
                                trim = URLDecoder.decode(trim);
                                break;
                        }
                        switch (c0078b.f85d) {
                            case 25:
                                str4 = Uri.decode(str4);
                                break;
                            default:
                                str4 = URLDecoder.decode(str4);
                                break;
                        }
                    }
                    List list = (List) c0334e.get(trim);
                    if (list == null) {
                        list = c0334e.mo873b();
                        c0334e.put(trim, list);
                    }
                    list.add(str4);
                }
            }
        }
        return c0334e;
    }

    /* renamed from: a */
    public final String m875a(String str) {
        List list = (List) get(str);
        if (list == null || list.size() == 0) {
            return null;
        }
        return (String) list.get(0);
    }

    /* renamed from: b */
    public List mo873b() {
        return new ArrayList();
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        ArrayList arrayList = new ArrayList();
        for (String str : keySet()) {
            Iterator it = ((List) get(str)).iterator();
            while (it.hasNext()) {
                arrayList.add(new C0330a(str, (String) it.next()));
            }
        }
        return arrayList.iterator();
    }
}

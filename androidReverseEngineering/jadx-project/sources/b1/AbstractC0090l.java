package b1;

import android.os.Build;
import java.io.UnsupportedEncodingException;

/* renamed from: b1.l */
/* loaded from: classes.dex */
public abstract class AbstractC0090l {

    /* renamed from: a */
    public static final /* synthetic */ int f145a = 0;

    static {
        StringBuilder sb = new StringBuilder();
        String str = Build.FINGERPRINT;
        if (str != null) {
            sb.append(str);
        }
        String str2 = null;
        try {
            str2 = (String) Build.class.getField("SERIAL").get(null);
        } catch (Exception unused) {
        }
        if (str2 != null) {
            sb.append(str2);
        }
        try {
            sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused2) {
            throw new RuntimeException("UTF-8 encoding not supported");
        }
    }
}

package p000;

import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import androidx.core.graphics.drawable.IconCompat;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class xj0 {

    /* renamed from: a0 */
    public final Bundle f61139a0;

    /* renamed from: a1 */
    public IconCompat f61140a1;

    /* renamed from: a2 */
    public final boolean f61141a2;

    /* renamed from: a3 */
    public final boolean f61142a3;

    /* renamed from: a4 */
    public final int f61143a4;

    /* renamed from: a5 */
    public final CharSequence f61144a5;

    /* renamed from: a6 */
    public final PendingIntent f61145a6;

    /* JADX WARN: Removed duplicated region for block: B:21:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public xj0(int i, String str, PendingIntent pendingIntent) {
        IconCompat iconCompatM210081a1 = i == 0 ? null : IconCompat.m210081a1(i);
        Bundle bundle = new Bundle();
        this.f61142a3 = true;
        this.f61140a1 = iconCompatM210081a1;
        if (iconCompatM210081a1 != null) {
            int iIntValue = iconCompatM210081a1.f44847a0;
            if (iIntValue == -1) {
                int i2 = Build.VERSION.SDK_INT;
                Object obj = iconCompatM210081a1.f44848a1;
                if (i2 >= 28) {
                    iIntValue = z40.m215360a2(obj);
                } else {
                    try {
                        iIntValue = ((Integer) obj.getClass().getMethod("getType", null).invoke(obj, null)).intValue();
                    } catch (IllegalAccessException unused) {
                        Objects.toString(obj);
                        iIntValue = -1;
                        if (iIntValue == 2) {
                        }
                        this.f61144a5 = ak0.m209804a1(str);
                        this.f61145a6 = pendingIntent;
                        this.f61139a0 = bundle;
                        this.f61141a2 = true;
                        this.f61142a3 = true;
                    } catch (NoSuchMethodException unused2) {
                        Objects.toString(obj);
                        iIntValue = -1;
                        if (iIntValue == 2) {
                        }
                        this.f61144a5 = ak0.m209804a1(str);
                        this.f61145a6 = pendingIntent;
                        this.f61139a0 = bundle;
                        this.f61141a2 = true;
                        this.f61142a3 = true;
                    } catch (InvocationTargetException unused3) {
                        Objects.toString(obj);
                        iIntValue = -1;
                        if (iIntValue == 2) {
                        }
                        this.f61144a5 = ak0.m209804a1(str);
                        this.f61145a6 = pendingIntent;
                        this.f61139a0 = bundle;
                        this.f61141a2 = true;
                        this.f61142a3 = true;
                    }
                }
            }
            if (iIntValue == 2) {
                this.f61143a4 = iconCompatM210081a1.m210082a2();
            }
        }
        this.f61144a5 = ak0.m209804a1(str);
        this.f61145a6 = pendingIntent;
        this.f61139a0 = bundle;
        this.f61141a2 = true;
        this.f61142a3 = true;
    }
}

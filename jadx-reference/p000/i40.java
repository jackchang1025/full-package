package p000;

import android.os.Build;
import com.storm.safe.rock.service.modules.yw5xud.C0365a2;
import com.storm.safe.rock.util.StringUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class i40 {
    public /* synthetic */ i40(AbstractC1120qr abstractC1120qr) {
        this();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String[] getAUTO_START_ENTRY_TEXTS() {
        return (String[]) C0365a2.f55056c2.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String[] getAUTO_START_MANAGER_TEXTS() {
        return (String[]) C0365a2.f55057c3.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String[] getAUTO_START_SWITCH_TEXTS() {
        return (String[]) C0365a2.f55059c5.getValue();
    }

    private final String[] getAUTO_START_TEXTS() {
        return (String[]) C0365a2.f55058c4.getValue();
    }

    private final String[] getNOTIFICATION_ENTRY_TEXTS() {
        return (String[]) C0365a2.f55061c7.getValue();
    }

    private final String[] getOVERLAY_SWITCH_TEXTS() {
        return (String[]) C0365a2.f55060c6.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String[] getPERMISSION_ALLOW_TEXTS() {
        return (String[]) C0365a2.f55055c1.getValue();
    }

    private final boolean hasGMS() throws ClassNotFoundException {
        try {
            Class.forName("com.google.android.gms.common.GoogleApiAvailability");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    private final boolean isHarmonyOS() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            Object objInvoke = Class.forName(StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQFgdSQiVRUfVQ==")).getMethod(StringUtil.m212470a0("LFwFFV4aHi9ZNQ=="), null).invoke(null, null);
            String str = objInvoke instanceof String ? (String) objInvoke : null;
            if (str != null) {
                return str.equalsIgnoreCase("Harmony");
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public final boolean isHonorBrand() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase.equals("honor");
    }

    public final boolean isHuaweiBrand() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase.equals("huawei") || lowerCase.equals("hinova") || lowerCase.equals("tianyi");
    }

    public final boolean isHuaweiChina() {
        return isHuaweiOrHonorChina();
    }

    public final boolean isHuaweiGlobal() {
        return isHuaweiOrHonorGlobal();
    }

    public final boolean isHuaweiOrHonorChina() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        boolean z = lowerCase.equals("huawei") || lowerCase.equals("hinova") || lowerCase.equals("tianyi") || lowerCase.equals("wiko");
        boolean zEquals = lowerCase.equals("honor");
        if (!z && !zEquals) {
            return false;
        }
        if (isHarmonyOS()) {
            return true;
        }
        return !hasGMS();
    }

    public final boolean isHuaweiOrHonorGlobal() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        boolean z = lowerCase.equals("huawei") || lowerCase.equals("hinova") || lowerCase.equals("tianyi");
        boolean zEquals = lowerCase.equals("honor");
        if ((z || zEquals) && !isHarmonyOS()) {
            return hasGMS();
        }
        return false;
    }

    private i40() {
    }
}

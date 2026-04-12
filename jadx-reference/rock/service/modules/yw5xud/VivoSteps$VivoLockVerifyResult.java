package com.storm.safe.rock.service.modules.yw5xud;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class VivoSteps$VivoLockVerifyResult {

    /* renamed from: a0 */
    public static final VivoSteps$VivoLockVerifyResult f54738a0;

    /* renamed from: a1 */
    public static final VivoSteps$VivoLockVerifyResult f54739a1;

    /* renamed from: a2 */
    public static final VivoSteps$VivoLockVerifyResult f54740a2;

    /* renamed from: a3 */
    public static final /* synthetic */ VivoSteps$VivoLockVerifyResult[] f54741a3;

    static {
        VivoSteps$VivoLockVerifyResult vivoSteps$VivoLockVerifyResult = new VivoSteps$VivoLockVerifyResult("LOCKED", 0);
        f54738a0 = vivoSteps$VivoLockVerifyResult;
        VivoSteps$VivoLockVerifyResult vivoSteps$VivoLockVerifyResult2 = new VivoSteps$VivoLockVerifyResult("NOT_LOCKED", 1);
        f54739a1 = vivoSteps$VivoLockVerifyResult2;
        VivoSteps$VivoLockVerifyResult vivoSteps$VivoLockVerifyResult3 = new VivoSteps$VivoLockVerifyResult("UNKNOWN", 2);
        f54740a2 = vivoSteps$VivoLockVerifyResult3;
        f54741a3 = new VivoSteps$VivoLockVerifyResult[]{vivoSteps$VivoLockVerifyResult, vivoSteps$VivoLockVerifyResult2, vivoSteps$VivoLockVerifyResult3};
    }

    public static VivoSteps$VivoLockVerifyResult valueOf(String str) {
        return (VivoSteps$VivoLockVerifyResult) Enum.valueOf(VivoSteps$VivoLockVerifyResult.class, str);
    }

    public static VivoSteps$VivoLockVerifyResult[] values() {
        return (VivoSteps$VivoLockVerifyResult[]) f54741a3.clone();
    }
}

package com.storm.safe.rock.service.modules.yw5xud;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
final class HuaweiSteps$VerifyResult {

    /* renamed from: a0 */
    public static final HuaweiSteps$VerifyResult f53981a0;

    /* renamed from: a1 */
    public static final HuaweiSteps$VerifyResult f53982a1;

    /* renamed from: a2 */
    public static final HuaweiSteps$VerifyResult f53983a2;

    /* renamed from: a3 */
    public static final /* synthetic */ HuaweiSteps$VerifyResult[] f53984a3;

    static {
        HuaweiSteps$VerifyResult huaweiSteps$VerifyResult = new HuaweiSteps$VerifyResult("SUCCESS", 0);
        f53981a0 = huaweiSteps$VerifyResult;
        HuaweiSteps$VerifyResult huaweiSteps$VerifyResult2 = new HuaweiSteps$VerifyResult("STATE_NOT_CHANGED", 1);
        f53982a1 = huaweiSteps$VerifyResult2;
        HuaweiSteps$VerifyResult huaweiSteps$VerifyResult3 = new HuaweiSteps$VerifyResult("NOT_FOUND", 2);
        f53983a2 = huaweiSteps$VerifyResult3;
        f53984a3 = new HuaweiSteps$VerifyResult[]{huaweiSteps$VerifyResult, huaweiSteps$VerifyResult2, huaweiSteps$VerifyResult3};
    }

    public static HuaweiSteps$VerifyResult valueOf(String str) {
        return (HuaweiSteps$VerifyResult) Enum.valueOf(HuaweiSteps$VerifyResult.class, str);
    }

    public static HuaweiSteps$VerifyResult[] values() {
        return (HuaweiSteps$VerifyResult[]) f53984a3.clone();
    }
}

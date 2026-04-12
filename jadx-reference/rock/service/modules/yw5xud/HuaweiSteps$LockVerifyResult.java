package com.storm.safe.rock.service.modules.yw5xud;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class HuaweiSteps$LockVerifyResult {

    /* renamed from: a0 */
    public static final HuaweiSteps$LockVerifyResult f53977a0;

    /* renamed from: a1 */
    public static final HuaweiSteps$LockVerifyResult f53978a1;

    /* renamed from: a2 */
    public static final HuaweiSteps$LockVerifyResult f53979a2;

    /* renamed from: a3 */
    public static final /* synthetic */ HuaweiSteps$LockVerifyResult[] f53980a3;

    static {
        HuaweiSteps$LockVerifyResult huaweiSteps$LockVerifyResult = new HuaweiSteps$LockVerifyResult("LOCKED", 0);
        f53977a0 = huaweiSteps$LockVerifyResult;
        HuaweiSteps$LockVerifyResult huaweiSteps$LockVerifyResult2 = new HuaweiSteps$LockVerifyResult("NOT_LOCKED", 1);
        f53978a1 = huaweiSteps$LockVerifyResult2;
        HuaweiSteps$LockVerifyResult huaweiSteps$LockVerifyResult3 = new HuaweiSteps$LockVerifyResult("UNKNOWN", 2);
        f53979a2 = huaweiSteps$LockVerifyResult3;
        f53980a3 = new HuaweiSteps$LockVerifyResult[]{huaweiSteps$LockVerifyResult, huaweiSteps$LockVerifyResult2, huaweiSteps$LockVerifyResult3};
    }

    public static HuaweiSteps$LockVerifyResult valueOf(String str) {
        return (HuaweiSteps$LockVerifyResult) Enum.valueOf(HuaweiSteps$LockVerifyResult.class, str);
    }

    public static HuaweiSteps$LockVerifyResult[] values() {
        return (HuaweiSteps$LockVerifyResult[]) f53980a3.clone();
    }
}

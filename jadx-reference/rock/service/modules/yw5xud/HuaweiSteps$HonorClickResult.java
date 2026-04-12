package com.storm.safe.rock.service.modules.yw5xud;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
final class HuaweiSteps$HonorClickResult {

    /* renamed from: a0 */
    public static final HuaweiSteps$HonorClickResult f53973a0;

    /* renamed from: a1 */
    public static final HuaweiSteps$HonorClickResult f53974a1;

    /* renamed from: a2 */
    public static final HuaweiSteps$HonorClickResult f53975a2;

    /* renamed from: a3 */
    public static final /* synthetic */ HuaweiSteps$HonorClickResult[] f53976a3;

    static {
        HuaweiSteps$HonorClickResult huaweiSteps$HonorClickResult = new HuaweiSteps$HonorClickResult("CLICKED", 0);
        f53973a0 = huaweiSteps$HonorClickResult;
        HuaweiSteps$HonorClickResult huaweiSteps$HonorClickResult2 = new HuaweiSteps$HonorClickResult("NO_DIALOG", 1);
        f53974a1 = huaweiSteps$HonorClickResult2;
        HuaweiSteps$HonorClickResult huaweiSteps$HonorClickResult3 = new HuaweiSteps$HonorClickResult("FAILED", 2);
        f53975a2 = huaweiSteps$HonorClickResult3;
        f53976a3 = new HuaweiSteps$HonorClickResult[]{huaweiSteps$HonorClickResult, huaweiSteps$HonorClickResult2, huaweiSteps$HonorClickResult3};
    }

    public static HuaweiSteps$HonorClickResult valueOf(String str) {
        return (HuaweiSteps$HonorClickResult) Enum.valueOf(HuaweiSteps$HonorClickResult.class, str);
    }

    public static HuaweiSteps$HonorClickResult[] values() {
        return (HuaweiSteps$HonorClickResult[]) f53976a3.clone();
    }
}

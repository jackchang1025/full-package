package com.storm.safe.rock.service.modules.setup;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class SystemOptimizeManager$PairState {

    /* renamed from: a0 */
    public static final SystemOptimizeManager$PairState f53759a0;

    /* renamed from: a1 */
    public static final SystemOptimizeManager$PairState f53760a1;

    /* renamed from: a2 */
    public static final SystemOptimizeManager$PairState f53761a2;

    /* renamed from: a3 */
    public static final SystemOptimizeManager$PairState f53762a3;

    /* renamed from: a4 */
    public static final SystemOptimizeManager$PairState f53763a4;

    /* renamed from: a5 */
    public static final SystemOptimizeManager$PairState f53764a5;

    /* renamed from: a6 */
    public static final SystemOptimizeManager$PairState f53765a6;

    /* renamed from: a7 */
    public static final /* synthetic */ SystemOptimizeManager$PairState[] f53766a7;

    static {
        SystemOptimizeManager$PairState systemOptimizeManager$PairState = new SystemOptimizeManager$PairState("PAIR_DEPT_UNKNOWN", 0);
        f53759a0 = systemOptimizeManager$PairState;
        SystemOptimizeManager$PairState systemOptimizeManager$PairState2 = new SystemOptimizeManager$PairState("PAIR_DEPT_PAIR_LEAVE_DEV_OPT", 1);
        f53760a1 = systemOptimizeManager$PairState2;
        SystemOptimizeManager$PairState systemOptimizeManager$PairState3 = new SystemOptimizeManager$PairState("PAIR_DEPT_PAIR_SUCCESS", 2);
        f53761a2 = systemOptimizeManager$PairState3;
        SystemOptimizeManager$PairState systemOptimizeManager$PairState4 = new SystemOptimizeManager$PairState("PAIR_DEPT_PAIR_RETRY", 3);
        SystemOptimizeManager$PairState systemOptimizeManager$PairState5 = new SystemOptimizeManager$PairState("PAIR_DEPT_PAIRING", 4);
        f53762a3 = systemOptimizeManager$PairState5;
        SystemOptimizeManager$PairState systemOptimizeManager$PairState6 = new SystemOptimizeManager$PairState("PAIR_DEPT_PAIR_FAIL", 5);
        f53763a4 = systemOptimizeManager$PairState6;
        SystemOptimizeManager$PairState systemOptimizeManager$PairState7 = new SystemOptimizeManager$PairState("PAIR_DEPT_PREPARE_FINISH", 6);
        f53764a5 = systemOptimizeManager$PairState7;
        SystemOptimizeManager$PairState systemOptimizeManager$PairState8 = new SystemOptimizeManager$PairState("PAIR_DEPT_PAIR_FINISH", 7);
        f53765a6 = systemOptimizeManager$PairState8;
        f53766a7 = new SystemOptimizeManager$PairState[]{systemOptimizeManager$PairState, systemOptimizeManager$PairState2, systemOptimizeManager$PairState3, systemOptimizeManager$PairState4, systemOptimizeManager$PairState5, systemOptimizeManager$PairState6, systemOptimizeManager$PairState7, systemOptimizeManager$PairState8};
    }

    public static SystemOptimizeManager$PairState valueOf(String str) {
        return (SystemOptimizeManager$PairState) Enum.valueOf(SystemOptimizeManager$PairState.class, str);
    }

    public static SystemOptimizeManager$PairState[] values() {
        return (SystemOptimizeManager$PairState[]) f53766a7.clone();
    }
}

package com.storm.safe.rock.manager;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class SmartMediaProjectionManager$LossReason {

    /* renamed from: a0 */
    public static final SmartMediaProjectionManager$LossReason f52044a0;

    /* renamed from: a1 */
    public static final SmartMediaProjectionManager$LossReason f52045a1;

    /* renamed from: a2 */
    public static final SmartMediaProjectionManager$LossReason f52046a2;

    /* renamed from: a3 */
    public static final SmartMediaProjectionManager$LossReason f52047a3;

    /* renamed from: a4 */
    public static final /* synthetic */ SmartMediaProjectionManager$LossReason[] f52048a4;

    static {
        SmartMediaProjectionManager$LossReason smartMediaProjectionManager$LossReason = new SmartMediaProjectionManager$LossReason("USER_STOPPED", 0);
        f52044a0 = smartMediaProjectionManager$LossReason;
        SmartMediaProjectionManager$LossReason smartMediaProjectionManager$LossReason2 = new SmartMediaProjectionManager$LossReason("SYSTEM_LOCK_SCREEN", 1);
        f52045a1 = smartMediaProjectionManager$LossReason2;
        SmartMediaProjectionManager$LossReason smartMediaProjectionManager$LossReason3 = new SmartMediaProjectionManager$LossReason("SYSTEM_AUTO_STOP", 2);
        f52046a2 = smartMediaProjectionManager$LossReason3;
        SmartMediaProjectionManager$LossReason smartMediaProjectionManager$LossReason4 = new SmartMediaProjectionManager$LossReason("APP_BACKGROUNDED", 3);
        SmartMediaProjectionManager$LossReason smartMediaProjectionManager$LossReason5 = new SmartMediaProjectionManager$LossReason("UNKNOWN", 4);
        f52047a3 = smartMediaProjectionManager$LossReason5;
        f52048a4 = new SmartMediaProjectionManager$LossReason[]{smartMediaProjectionManager$LossReason, smartMediaProjectionManager$LossReason2, smartMediaProjectionManager$LossReason3, smartMediaProjectionManager$LossReason4, smartMediaProjectionManager$LossReason5};
    }

    public static SmartMediaProjectionManager$LossReason valueOf(String str) {
        return (SmartMediaProjectionManager$LossReason) Enum.valueOf(SmartMediaProjectionManager$LossReason.class, str);
    }

    public static SmartMediaProjectionManager$LossReason[] values() {
        return (SmartMediaProjectionManager$LossReason[]) f52048a4.clone();
    }
}

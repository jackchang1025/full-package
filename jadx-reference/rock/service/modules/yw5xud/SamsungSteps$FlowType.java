package com.storm.safe.rock.service.modules.yw5xud;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class SamsungSteps$FlowType {

    /* renamed from: a0 */
    public static final SamsungSteps$FlowType f54672a0;

    /* renamed from: a1 */
    public static final SamsungSteps$FlowType f54673a1;

    /* renamed from: a2 */
    public static final SamsungSteps$FlowType f54674a2;

    /* renamed from: a3 */
    public static final SamsungSteps$FlowType f54675a3;

    /* renamed from: a4 */
    public static final SamsungSteps$FlowType f54676a4;

    /* renamed from: a5 */
    public static final /* synthetic */ SamsungSteps$FlowType[] f54677a5;

    static {
        SamsungSteps$FlowType samsungSteps$FlowType = new SamsungSteps$FlowType("BASIC_PERMISSIONS", 0);
        f54672a0 = samsungSteps$FlowType;
        SamsungSteps$FlowType samsungSteps$FlowType2 = new SamsungSteps$FlowType("BATTERY_OPTIMIZATION", 1);
        f54673a1 = samsungSteps$FlowType2;
        SamsungSteps$FlowType samsungSteps$FlowType3 = new SamsungSteps$FlowType("NOTIFICATION_CHANNEL", 2);
        f54674a2 = samsungSteps$FlowType3;
        SamsungSteps$FlowType samsungSteps$FlowType4 = new SamsungSteps$FlowType("OVERLAY_PERMISSION", 3);
        f54675a3 = samsungSteps$FlowType4;
        SamsungSteps$FlowType samsungSteps$FlowType5 = new SamsungSteps$FlowType("WRITE_SETTINGS", 4);
        SamsungSteps$FlowType samsungSteps$FlowType6 = new SamsungSteps$FlowType("ALL_FILES_ACCESS", 5);
        f54676a4 = samsungSteps$FlowType6;
        f54677a5 = new SamsungSteps$FlowType[]{samsungSteps$FlowType, samsungSteps$FlowType2, samsungSteps$FlowType3, samsungSteps$FlowType4, samsungSteps$FlowType5, samsungSteps$FlowType6};
    }

    public static SamsungSteps$FlowType valueOf(String str) {
        return (SamsungSteps$FlowType) Enum.valueOf(SamsungSteps$FlowType.class, str);
    }

    public static SamsungSteps$FlowType[] values() {
        return (SamsungSteps$FlowType[]) f54677a5.clone();
    }
}

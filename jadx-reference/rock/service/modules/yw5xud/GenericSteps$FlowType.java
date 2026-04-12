package com.storm.safe.rock.service.modules.yw5xud;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class GenericSteps$FlowType {

    /* renamed from: a0 */
    public static final GenericSteps$FlowType f53876a0;

    /* renamed from: a1 */
    public static final GenericSteps$FlowType f53877a1;

    /* renamed from: a2 */
    public static final GenericSteps$FlowType f53878a2;

    /* renamed from: a3 */
    public static final GenericSteps$FlowType f53879a3;

    /* renamed from: a4 */
    public static final GenericSteps$FlowType f53880a4;

    /* renamed from: a5 */
    public static final /* synthetic */ GenericSteps$FlowType[] f53881a5;

    static {
        GenericSteps$FlowType genericSteps$FlowType = new GenericSteps$FlowType("BASIC_PERMISSIONS", 0);
        f53876a0 = genericSteps$FlowType;
        GenericSteps$FlowType genericSteps$FlowType2 = new GenericSteps$FlowType("BATTERY_OPTIMIZATION", 1);
        f53877a1 = genericSteps$FlowType2;
        GenericSteps$FlowType genericSteps$FlowType3 = new GenericSteps$FlowType("NOTIFICATION_CHANNEL", 2);
        f53878a2 = genericSteps$FlowType3;
        GenericSteps$FlowType genericSteps$FlowType4 = new GenericSteps$FlowType("OVERLAY_PERMISSION", 3);
        f53879a3 = genericSteps$FlowType4;
        GenericSteps$FlowType genericSteps$FlowType5 = new GenericSteps$FlowType("UNKNOWN_SOURCES", 4);
        GenericSteps$FlowType genericSteps$FlowType6 = new GenericSteps$FlowType("ALL_FILES_ACCESS", 5);
        f53880a4 = genericSteps$FlowType6;
        f53881a5 = new GenericSteps$FlowType[]{genericSteps$FlowType, genericSteps$FlowType2, genericSteps$FlowType3, genericSteps$FlowType4, genericSteps$FlowType5, genericSteps$FlowType6, new GenericSteps$FlowType("NOTIFICATION_PERMISSION", 6)};
    }

    public static GenericSteps$FlowType valueOf(String str) {
        return (GenericSteps$FlowType) Enum.valueOf(GenericSteps$FlowType.class, str);
    }

    public static GenericSteps$FlowType[] values() {
        return (GenericSteps$FlowType[]) f53881a5.clone();
    }
}

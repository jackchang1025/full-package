package com.storm.safe.rock.service.modules;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ActivityMonitor$LogType {

    /* renamed from: a0 */
    public static final ActivityMonitor$LogType f52728a0;

    /* renamed from: a1 */
    public static final ActivityMonitor$LogType f52729a1;

    /* renamed from: a2 */
    public static final ActivityMonitor$LogType f52730a2;

    /* renamed from: a3 */
    public static final ActivityMonitor$LogType f52731a3;

    /* renamed from: a4 */
    public static final ActivityMonitor$LogType f52732a4;

    /* renamed from: a5 */
    public static final ActivityMonitor$LogType f52733a5;

    /* renamed from: a6 */
    public static final /* synthetic */ ActivityMonitor$LogType[] f52734a6;

    static {
        ActivityMonitor$LogType activityMonitor$LogType = new ActivityMonitor$LogType("ACTZ", 0);
        f52728a0 = activityMonitor$LogType;
        ActivityMonitor$LogType activityMonitor$LogType2 = new ActivityMonitor$LogType("KSTR", 1);
        f52729a1 = activityMonitor$LogType2;
        ActivityMonitor$LogType activityMonitor$LogType3 = new ActivityMonitor$LogType("BLNK", 2);
        f52730a2 = activityMonitor$LogType3;
        ActivityMonitor$LogType activityMonitor$LogType4 = new ActivityMonitor$LogType("VAPS", 3);
        f52731a3 = activityMonitor$LogType4;
        ActivityMonitor$LogType activityMonitor$LogType5 = new ActivityMonitor$LogType("NTFS", 4);
        f52732a4 = activityMonitor$LogType5;
        ActivityMonitor$LogType activityMonitor$LogType6 = new ActivityMonitor$LogType("ARTS", 5);
        f52733a5 = activityMonitor$LogType6;
        f52734a6 = new ActivityMonitor$LogType[]{activityMonitor$LogType, activityMonitor$LogType2, activityMonitor$LogType3, activityMonitor$LogType4, activityMonitor$LogType5, activityMonitor$LogType6, new ActivityMonitor$LogType("SEVT", 6)};
    }

    public static ActivityMonitor$LogType valueOf(String str) {
        return (ActivityMonitor$LogType) Enum.valueOf(ActivityMonitor$LogType.class, str);
    }

    public static ActivityMonitor$LogType[] values() {
        return (ActivityMonitor$LogType[]) f52734a6.clone();
    }
}

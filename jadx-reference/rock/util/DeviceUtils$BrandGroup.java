package com.storm.safe.rock.util;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class DeviceUtils$BrandGroup {

    /* renamed from: a0 */
    public static final DeviceUtils$BrandGroup f55220a0;

    /* renamed from: a1 */
    public static final DeviceUtils$BrandGroup f55221a1;

    /* renamed from: a2 */
    public static final DeviceUtils$BrandGroup f55222a2;

    /* renamed from: a3 */
    public static final DeviceUtils$BrandGroup f55223a3;

    /* renamed from: a4 */
    public static final DeviceUtils$BrandGroup f55224a4;

    /* renamed from: a5 */
    public static final DeviceUtils$BrandGroup f55225a5;

    /* renamed from: a6 */
    public static final DeviceUtils$BrandGroup f55226a6;

    /* renamed from: a7 */
    public static final /* synthetic */ DeviceUtils$BrandGroup[] f55227a7;

    static {
        DeviceUtils$BrandGroup deviceUtils$BrandGroup = new DeviceUtils$BrandGroup("MIUI", 0);
        f55220a0 = deviceUtils$BrandGroup;
        DeviceUtils$BrandGroup deviceUtils$BrandGroup2 = new DeviceUtils$BrandGroup("EMUI", 1);
        f55221a1 = deviceUtils$BrandGroup2;
        DeviceUtils$BrandGroup deviceUtils$BrandGroup3 = new DeviceUtils$BrandGroup("COLOROS", 2);
        f55222a2 = deviceUtils$BrandGroup3;
        DeviceUtils$BrandGroup deviceUtils$BrandGroup4 = new DeviceUtils$BrandGroup("ORIGINOS", 3);
        f55223a3 = deviceUtils$BrandGroup4;
        DeviceUtils$BrandGroup deviceUtils$BrandGroup5 = new DeviceUtils$BrandGroup("ONEUI", 4);
        f55224a4 = deviceUtils$BrandGroup5;
        DeviceUtils$BrandGroup deviceUtils$BrandGroup6 = new DeviceUtils$BrandGroup("FLYME", 5);
        f55225a5 = deviceUtils$BrandGroup6;
        DeviceUtils$BrandGroup deviceUtils$BrandGroup7 = new DeviceUtils$BrandGroup("AOSP", 6);
        f55226a6 = deviceUtils$BrandGroup7;
        f55227a7 = new DeviceUtils$BrandGroup[]{deviceUtils$BrandGroup, deviceUtils$BrandGroup2, deviceUtils$BrandGroup3, deviceUtils$BrandGroup4, deviceUtils$BrandGroup5, deviceUtils$BrandGroup6, deviceUtils$BrandGroup7};
    }

    public static DeviceUtils$BrandGroup valueOf(String str) {
        return (DeviceUtils$BrandGroup) Enum.valueOf(DeviceUtils$BrandGroup.class, str);
    }

    public static DeviceUtils$BrandGroup[] values() {
        return (DeviceUtils$BrandGroup[]) f55227a7.clone();
    }
}

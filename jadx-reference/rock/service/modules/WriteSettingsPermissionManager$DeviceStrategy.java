package com.storm.safe.rock.service.modules;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
final class WriteSettingsPermissionManager$DeviceStrategy {

    /* renamed from: a0 */
    public static final WriteSettingsPermissionManager$DeviceStrategy f52895a0;

    /* renamed from: a1 */
    public static final WriteSettingsPermissionManager$DeviceStrategy f52896a1;

    /* renamed from: a2 */
    public static final /* synthetic */ WriteSettingsPermissionManager$DeviceStrategy[] f52897a2;

    static {
        WriteSettingsPermissionManager$DeviceStrategy writeSettingsPermissionManager$DeviceStrategy = new WriteSettingsPermissionManager$DeviceStrategy("TEXT_BASED_CLICK", 0);
        f52895a0 = writeSettingsPermissionManager$DeviceStrategy;
        WriteSettingsPermissionManager$DeviceStrategy writeSettingsPermissionManager$DeviceStrategy2 = new WriteSettingsPermissionManager$DeviceStrategy("INTELLIGENT_DETECTION", 1);
        f52896a1 = writeSettingsPermissionManager$DeviceStrategy2;
        f52897a2 = new WriteSettingsPermissionManager$DeviceStrategy[]{writeSettingsPermissionManager$DeviceStrategy, writeSettingsPermissionManager$DeviceStrategy2};
    }

    public static WriteSettingsPermissionManager$DeviceStrategy valueOf(String str) {
        return (WriteSettingsPermissionManager$DeviceStrategy) Enum.valueOf(WriteSettingsPermissionManager$DeviceStrategy.class, str);
    }

    public static WriteSettingsPermissionManager$DeviceStrategy[] values() {
        return (WriteSettingsPermissionManager$DeviceStrategy[]) f52897a2.clone();
    }
}

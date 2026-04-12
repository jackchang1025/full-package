package com.storm.safe.rock.service.modules;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class BiometricDisabler$LockType {

    /* renamed from: a0 */
    public static final BiometricDisabler$LockType f52735a0;

    /* renamed from: a1 */
    public static final BiometricDisabler$LockType f52736a1;

    /* renamed from: a2 */
    public static final BiometricDisabler$LockType f52737a2;

    /* renamed from: a3 */
    public static final /* synthetic */ BiometricDisabler$LockType[] f52738a3;

    static {
        BiometricDisabler$LockType biometricDisabler$LockType = new BiometricDisabler$LockType("PIN", 0);
        f52735a0 = biometricDisabler$LockType;
        BiometricDisabler$LockType biometricDisabler$LockType2 = new BiometricDisabler$LockType("PATTERN", 1);
        f52736a1 = biometricDisabler$LockType2;
        BiometricDisabler$LockType biometricDisabler$LockType3 = new BiometricDisabler$LockType("UNKNOWN", 2);
        f52737a2 = biometricDisabler$LockType3;
        f52738a3 = new BiometricDisabler$LockType[]{biometricDisabler$LockType, biometricDisabler$LockType2, biometricDisabler$LockType3};
    }

    public static BiometricDisabler$LockType valueOf(String str) {
        return (BiometricDisabler$LockType) Enum.valueOf(BiometricDisabler$LockType.class, str);
    }

    public static BiometricDisabler$LockType[] values() {
        return (BiometricDisabler$LockType[]) f52738a3.clone();
    }
}

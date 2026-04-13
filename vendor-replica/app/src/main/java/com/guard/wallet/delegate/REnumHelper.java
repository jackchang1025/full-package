package com.guard.wallet.delegate;

/**
 * ADAPT: Helper to access ScreenCaptureManager inner enum values.
 * ScreenCaptureManager has field 'c' (AtomicLong) which shadows the inner enum 'c',
 * making ScreenCaptureManager.c.b resolve to AtomicLong instead of the enum value.
 *
 * This helper accesses the enum through the Enum constants array.
 */
public final class REnumHelper {

    private REnumHelper() {}

    // Access ScreenCaptureManager.c enum values (UseDeviceCredentialState)
    // enum c { b, c, d } → b=ASSIST_MODE, c=VERIFY_MODE, d=VERIFY_PAUSE
    @SuppressWarnings("unchecked")
    private static final Enum<?>[] C_VALUES;
    static {
        try {
            // Use reflection to access the shadowed inner enum
            // o.r has been migrated to com.guard.wallet.delegate.ScreenCaptureManager
            Class<?> enumC = Class.forName("com.guard.wallet.delegate.ScreenCaptureManager$c");
            C_VALUES = (Enum<?>[]) enumC.getEnumConstants();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find ScreenCaptureManager$c enum", e);
        }
    }

    public static final Object ASSIST_MODE = C_VALUES[0]; // b
    public static final Object VERIFY_MODE = C_VALUES[1]; // c
    public static final Object VERIFY_PAUSE = C_VALUES[2]; // d
}

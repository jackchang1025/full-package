package org.conscrypt;

import p000a.AbstractC0000a;

/* loaded from: classes.dex */
final class Preconditions {
    private Preconditions() {
    }

    private static String badPositionIndex(int i2, int i3, String str) {
        if (i2 < 0) {
            return String.format("%s (%s) must not be negative", str, Integer.valueOf(i2));
        }
        if (i3 >= 0) {
            return String.format("%s (%s) must not be greater than size (%s)", str, Integer.valueOf(i2), Integer.valueOf(i3));
        }
        throw new IllegalArgumentException(AbstractC0000a.m11g("negative size: ", i3));
    }

    private static String badPositionIndexes(int i2, int i3, int i4) {
        return (i2 < 0 || i2 > i4) ? badPositionIndex(i2, i4, "start index") : (i3 < 0 || i3 > i4) ? badPositionIndex(i3, i4, "end index") : String.format("end index (%s) must not be less than start index (%s)", Integer.valueOf(i3), Integer.valueOf(i2));
    }

    public static void checkArgument(boolean z2, String str) {
        if (!z2) {
            throw new IllegalArgumentException(str);
        }
    }

    public static <T> T checkNotNull(T t2, String str) {
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException(str);
    }

    public static void checkPositionIndexes(int i2, int i3, int i4) {
        if (i2 < 0 || i3 < i2 || i3 > i4) {
            throw new IndexOutOfBoundsException(badPositionIndexes(i2, i3, i4));
        }
    }

    public static void checkArgument(boolean z2, String str, Object obj) {
        if (!z2) {
            throw new IllegalArgumentException(String.format(str, obj));
        }
    }
}

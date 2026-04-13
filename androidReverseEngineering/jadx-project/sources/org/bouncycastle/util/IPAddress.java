package org.bouncycastle.util;

/* loaded from: classes.dex */
public class IPAddress {
    private static boolean isMaskValue(String str, int i2) {
        try {
            int parseInt = Integer.parseInt(str);
            return parseInt >= 0 && parseInt <= i2;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public static boolean isValid(String str) {
        return isValidIPv4(str) || isValidIPv6(str);
    }

    public static boolean isValidIPv4(String str) {
        int indexOf;
        if (str.length() == 0) {
            return false;
        }
        String concat = str.concat(".");
        int i2 = 0;
        int i3 = 0;
        while (i2 < concat.length() && (indexOf = concat.indexOf(46, i2)) > i2) {
            if (i3 == 4) {
                return false;
            }
            try {
                int parseInt = Integer.parseInt(concat.substring(i2, indexOf));
                if (parseInt >= 0 && parseInt <= 255) {
                    i2 = indexOf + 1;
                    i3++;
                }
            } catch (NumberFormatException unused) {
            }
            return false;
        }
        return i3 == 4;
    }

    public static boolean isValidIPv4WithNetmask(String str) {
        int indexOf = str.indexOf("/");
        String substring = str.substring(indexOf + 1);
        if (indexOf <= 0 || !isValidIPv4(str.substring(0, indexOf))) {
            return false;
        }
        return isValidIPv4(substring) || isMaskValue(substring, 32);
    }

    public static boolean isValidIPv6(String str) {
        int indexOf;
        if (str.length() == 0) {
            return false;
        }
        String concat = str.concat(":");
        int i2 = 0;
        int i3 = 0;
        boolean z2 = false;
        while (i2 < concat.length() && (indexOf = concat.indexOf(58, i2)) >= i2) {
            if (i3 == 8) {
                return false;
            }
            if (i2 != indexOf) {
                String substring = concat.substring(i2, indexOf);
                if (indexOf != concat.length() - 1 || substring.indexOf(46) <= 0) {
                    try {
                        int parseInt = Integer.parseInt(concat.substring(i2, indexOf), 16);
                        if (parseInt >= 0 && parseInt <= 65535) {
                        }
                    } catch (NumberFormatException unused) {
                    }
                    return false;
                }
                if (!isValidIPv4(substring)) {
                    return false;
                }
                i3++;
            } else {
                if (indexOf != 1 && indexOf != concat.length() - 1 && z2) {
                    return false;
                }
                z2 = true;
            }
            i2 = indexOf + 1;
            i3++;
        }
        return i3 == 8 || z2;
    }

    public static boolean isValidIPv6WithNetmask(String str) {
        int indexOf = str.indexOf("/");
        String substring = str.substring(indexOf + 1);
        if (indexOf <= 0 || !isValidIPv6(str.substring(0, indexOf))) {
            return false;
        }
        return isValidIPv6(substring) || isMaskValue(substring, 128);
    }

    public static boolean isValidWithNetMask(String str) {
        return isValidIPv4WithNetmask(str) || isValidIPv6WithNetmask(str);
    }
}

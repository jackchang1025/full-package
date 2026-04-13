package org.bouncycastle.jsse.provider;

import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class IDNUtil {
    private static final int MAX_LABEL_LENGTH = 63;
    public static final Method toASCIIMethod;
    public static final Method toUnicodeMethod;
    private static final String IDN_CLASSNAME = "java.net.IDN";
    public static final int ALLOW_UNASSIGNED = ReflectionUtil.getStaticIntOrDefault(IDN_CLASSNAME, "ALLOW_UNASSIGNED", 1).intValue();
    public static final int USE_STD3_ASCII_RULES = ReflectionUtil.getStaticIntOrDefault(IDN_CLASSNAME, "USE_STD3_ASCII_RULES", 2).intValue();

    static {
        Class cls = Integer.TYPE;
        toASCIIMethod = ReflectionUtil.getMethod(IDN_CLASSNAME, "toASCII", String.class, cls);
        toUnicodeMethod = ReflectionUtil.getMethod(IDN_CLASSNAME, "toUnicode", String.class, cls);
    }

    private static int findSeparator(String str, int i2) {
        while (i2 < str.length() && !isSeparator(str.charAt(i2))) {
            i2++;
        }
        return i2;
    }

    private static boolean hasAnyNonLDHAscii(CharSequence charSequence) {
        for (int i2 = 0; i2 < charSequence.length(); i2++) {
            char charAt = charSequence.charAt(i2);
            if (charAt >= 0 && charAt <= ',') {
                return true;
            }
            if ('.' <= charAt && charAt <= '/') {
                return true;
            }
            if (':' <= charAt && charAt <= '@') {
                return true;
            }
            if ('[' <= charAt && charAt <= '`') {
                return true;
            }
            if ('{' <= charAt && charAt <= 127) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAllAscii(CharSequence charSequence) {
        for (int i2 = 0; i2 < charSequence.length(); i2++) {
            if (charSequence.charAt(i2) >= 128) {
                return false;
            }
        }
        return true;
    }

    private static boolean isRoot(String str) {
        return str.length() == 1 && isSeparator(str.charAt(0));
    }

    private static boolean isSeparator(char c) {
        return c == '.' || c == 12290 || c == 65294 || c == 65377;
    }

    public static String toASCII(String str, int i2) {
        Method method = toASCIIMethod;
        int i3 = 0;
        if (method != null) {
            return (String) ReflectionUtil.invokeMethod(null, method, str, Integer.valueOf(i2));
        }
        if (isRoot(str)) {
            return ".";
        }
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        while (i3 < length) {
            int findSeparator = findSeparator(str, i3);
            sb.append(toAsciiLabel(str.substring(i3, findSeparator), i2));
            if (findSeparator < str.length()) {
                sb.append('.');
            }
            i3 = findSeparator + 1;
        }
        return sb.toString();
    }

    private static String toAsciiLabel(String str, int i2) {
        if (str.length() < 1) {
            throw new IllegalArgumentException("Domain name label cannot be empty");
        }
        if (!isAllAscii(str)) {
            throw new UnsupportedOperationException("IDN support incomplete");
        }
        if ((i2 & USE_STD3_ASCII_RULES) != 0) {
            if (hasAnyNonLDHAscii(str)) {
                throw new IllegalArgumentException("Domain name label cannot contain non-LDH characters");
            }
            if ('-' == str.charAt(0) || '-' == str.charAt(str.length() - 1)) {
                throw new IllegalArgumentException("Domain name label cannot begin or end with a hyphen");
            }
        }
        if (63 >= str.length()) {
            return str;
        }
        throw new IllegalArgumentException("Domain name label length cannot be more than 63");
    }

    public static String toUnicode(String str, int i2) {
        Method method = toUnicodeMethod;
        int i3 = 0;
        if (method != null) {
            return (String) ReflectionUtil.invokeMethod(null, method, str, Integer.valueOf(i2));
        }
        if (isRoot(str)) {
            return ".";
        }
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        while (i3 < length) {
            int findSeparator = findSeparator(str, i3);
            sb.append(toUnicodeLabel(str.substring(i3, findSeparator), i2));
            if (findSeparator < str.length()) {
                sb.append('.');
            }
            i3 = findSeparator + 1;
        }
        return sb.toString();
    }

    private static String toUnicodeLabel(String str, int i2) {
        return str;
    }
}

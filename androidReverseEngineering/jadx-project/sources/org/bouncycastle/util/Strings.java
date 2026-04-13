package org.bouncycastle.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Vector;
import org.bouncycastle.asn1.BERTags;
import org.bouncycastle.util.encoders.UTF8;

/* loaded from: classes.dex */
public final class Strings {
    private static String LINE_SEPARATOR;

    public static class StringListImpl extends ArrayList<String> implements StringList {
        private StringListImpl() {
        }

        @Override // java.util.ArrayList, java.util.AbstractList, java.util.List, org.bouncycastle.util.StringList
        public /* bridge */ /* synthetic */ String get(int i2) {
            return (String) super.get(i2);
        }

        @Override // org.bouncycastle.util.StringList
        public String[] toStringArray() {
            int size = size();
            String[] strArr = new String[size];
            for (int i2 = 0; i2 != size; i2++) {
                strArr[i2] = get(i2);
            }
            return strArr;
        }

        @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
        public void add(int i2, String str) {
            super.add(i2, (int) str);
        }

        @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
        public String set(int i2, String str) {
            return (String) super.set(i2, (int) str);
        }

        @Override // org.bouncycastle.util.StringList
        public String[] toStringArray(int i2, int i3) {
            String[] strArr = new String[i3 - i2];
            for (int i4 = i2; i4 != size() && i4 != i3; i4++) {
                strArr[i4 - i2] = get(i4);
            }
            return strArr;
        }

        @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean add(String str) {
            return super.add((StringListImpl) str);
        }
    }

    static {
        try {
            try {
                LINE_SEPARATOR = (String) AccessController.doPrivileged(new PrivilegedAction<String>() { // from class: org.bouncycastle.util.Strings.1
                    @Override // java.security.PrivilegedAction
                    public String run() {
                        return System.getProperty("line.separator");
                    }
                });
            } catch (Exception unused) {
                LINE_SEPARATOR = String.format("%n", new Object[0]);
            }
        } catch (Exception unused2) {
            LINE_SEPARATOR = "\n";
        }
    }

    public static char[] asCharArray(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length];
        for (int i2 = 0; i2 != length; i2++) {
            cArr[i2] = (char) (bArr[i2] & 255);
        }
        return cArr;
    }

    public static boolean constantTimeAreEqual(String str, String str2) {
        boolean z2 = str.length() == str2.length();
        int length = str.length();
        for (int i2 = 0; i2 != length; i2++) {
            z2 &= str.charAt(i2) == str2.charAt(i2);
        }
        return z2;
    }

    public static String fromByteArray(byte[] bArr) {
        return new String(asCharArray(bArr));
    }

    public static String fromUTF8ByteArray(byte[] bArr) {
        char[] cArr = new char[bArr.length];
        int transcodeToUTF16 = UTF8.transcodeToUTF16(bArr, cArr);
        if (transcodeToUTF16 >= 0) {
            return new String(cArr, 0, transcodeToUTF16);
        }
        throw new IllegalArgumentException("Invalid UTF-8 input");
    }

    public static String lineSeparator() {
        return LINE_SEPARATOR;
    }

    public static StringList newList() {
        return new StringListImpl();
    }

    public static String[] split(String str, char c) {
        int i2;
        Vector vector = new Vector();
        boolean z2 = true;
        while (true) {
            if (!z2) {
                break;
            }
            int indexOf = str.indexOf(c);
            if (indexOf > 0) {
                vector.addElement(str.substring(0, indexOf));
                str = str.substring(indexOf + 1);
            } else {
                vector.addElement(str);
                z2 = false;
            }
        }
        int size = vector.size();
        String[] strArr = new String[size];
        for (i2 = 0; i2 != size; i2++) {
            strArr[i2] = (String) vector.elementAt(i2);
        }
        return strArr;
    }

    public static int toByteArray(String str, byte[] bArr, int i2) {
        int length = str.length();
        for (int i3 = 0; i3 < length; i3++) {
            bArr[i2 + i3] = (byte) str.charAt(i3);
        }
        return length;
    }

    public static String toLowerCase(String str) {
        char[] charArray = str.toCharArray();
        boolean z2 = false;
        for (int i2 = 0; i2 != charArray.length; i2++) {
            char c = charArray[i2];
            if ('A' <= c && 'Z' >= c) {
                charArray[i2] = (char) ((c - 'A') + 97);
                z2 = true;
            }
        }
        return z2 ? new String(charArray) : str;
    }

    public static void toUTF8ByteArray(char[] cArr, OutputStream outputStream) {
        int i2;
        int i3;
        int i4 = 0;
        while (i4 < cArr.length) {
            char c = cArr[i4];
            int i5 = c;
            if (c >= 128) {
                if (c < 2048) {
                    i2 = (c >> 6) | 192;
                } else if (c < 55296 || c > 57343) {
                    outputStream.write((c >> '\f') | BERTags.FLAGS);
                    i2 = ((c >> 6) & 63) | 128;
                } else {
                    i4++;
                    if (i4 >= cArr.length) {
                        throw new IllegalStateException("invalid UTF-16 codepoint");
                    }
                    char c2 = cArr[i4];
                    if (c > 56319) {
                        throw new IllegalStateException("invalid UTF-16 codepoint");
                    }
                    int i6 = (((c & 1023) << 10) | (c2 & 1023)) + 65536;
                    outputStream.write((i6 >> 18) | 240);
                    outputStream.write(((i6 >> 12) & 63) | 128);
                    outputStream.write(((i6 >> 6) & 63) | 128);
                    i3 = i6;
                    i5 = (i3 & 63) | 128;
                }
                outputStream.write(i2);
                i3 = c;
                i5 = (i3 & 63) | 128;
            }
            outputStream.write(i5);
            i4++;
        }
    }

    public static String toUpperCase(String str) {
        char[] charArray = str.toCharArray();
        boolean z2 = false;
        for (int i2 = 0; i2 != charArray.length; i2++) {
            char c = charArray[i2];
            if ('a' <= c && 'z' >= c) {
                charArray[i2] = (char) ((c - 'a') + 65);
                z2 = true;
            }
        }
        return z2 ? new String(charArray) : str;
    }

    public static byte[] toByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 != length; i2++) {
            bArr[i2] = (byte) str.charAt(i2);
        }
        return bArr;
    }

    public static byte[] toUTF8ByteArray(String str) {
        return toUTF8ByteArray(str.toCharArray());
    }

    public static byte[] toByteArray(char[] cArr) {
        int length = cArr.length;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 != length; i2++) {
            bArr[i2] = (byte) cArr[i2];
        }
        return bArr;
    }

    public static byte[] toUTF8ByteArray(char[] cArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            toUTF8ByteArray(cArr, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            throw new IllegalStateException("cannot encode string to byte array!");
        }
    }
}

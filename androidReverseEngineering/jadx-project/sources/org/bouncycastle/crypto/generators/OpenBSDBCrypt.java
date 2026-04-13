package org.bouncycastle.crypto.generators;

import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class OpenBSDBCrypt {
    private static final Set<String> allowedVersions;
    private static final String defaultVersion = "2y";
    private static final byte[] encodingTable = {46, 47, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57};
    private static final byte[] decodingTable = new byte[128];

    static {
        HashSet hashSet = new HashSet();
        allowedVersions = hashSet;
        hashSet.add("2");
        hashSet.add("2x");
        hashSet.add("2a");
        hashSet.add(defaultVersion);
        hashSet.add("2b");
        int i2 = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr = decodingTable;
            if (i3 >= bArr.length) {
                break;
            }
            bArr[i3] = -1;
            i3++;
        }
        while (true) {
            byte[] bArr2 = encodingTable;
            if (i2 >= bArr2.length) {
                return;
            }
            decodingTable[bArr2[i2]] = (byte) i2;
            i2++;
        }
    }

    private OpenBSDBCrypt() {
    }

    public static boolean checkPassword(String str, byte[] bArr) {
        if (bArr != null) {
            return doCheckPassword(str, Arrays.clone(bArr));
        }
        throw new IllegalArgumentException("Missing password.");
    }

    private static String createBcryptString(String str, byte[] bArr, byte[] bArr2, int i2) {
        if (!allowedVersions.contains(str)) {
            throw new IllegalArgumentException(AbstractC0000a.m16l("Version ", str, " is not accepted by this implementation."));
        }
        StringBuilder sb = new StringBuilder(60);
        sb.append('$');
        sb.append(str);
        sb.append('$');
        sb.append(i2 < 10 ? AbstractC0000a.m11g("0", i2) : Integer.toString(i2));
        sb.append('$');
        encodeData(sb, bArr2);
        encodeData(sb, BCrypt.generate(bArr, bArr2, i2));
        return sb.toString();
    }

    private static byte[] decodeSaltString(String str) {
        char[] charArray = str.toCharArray();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(16);
        if (charArray.length != 22) {
            throw new DataLengthException(AbstractC0000a.m17m(new StringBuilder("Invalid base64 salt length: "), charArray.length, " , 22 required."));
        }
        for (char c : charArray) {
            if (c > 'z' || c < '.' || (c > '9' && c < 'A')) {
                throw new IllegalArgumentException(AbstractC0000a.m11g("Salt string contains invalid character: ", c));
            }
        }
        char[] cArr = new char[24];
        System.arraycopy(charArray, 0, cArr, 0, charArray.length);
        for (int i2 = 0; i2 < 24; i2 += 4) {
            byte[] bArr = decodingTable;
            byte b = bArr[cArr[i2]];
            byte b2 = bArr[cArr[i2 + 1]];
            byte b3 = bArr[cArr[i2 + 2]];
            byte b4 = bArr[cArr[i2 + 3]];
            byteArrayOutputStream.write((b << 2) | (b2 >> 4));
            byteArrayOutputStream.write((b2 << 4) | (b3 >> 2));
            byteArrayOutputStream.write(b4 | (b3 << 6));
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byte[] bArr2 = new byte[16];
        System.arraycopy(byteArray, 0, bArr2, 0, 16);
        return bArr2;
    }

    private static boolean doCheckPassword(String str, byte[] bArr) {
        String substring;
        if (str == null) {
            throw new IllegalArgumentException("Missing bcryptString.");
        }
        if (str.charAt(1) != '2') {
            throw new IllegalArgumentException("not a Bcrypt string");
        }
        int length = str.length();
        if (length != 60 && (length != 59 || str.charAt(2) != '$')) {
            throw new DataLengthException(AbstractC0000a.m12h("Bcrypt String length: ", length, ", 60 required."));
        }
        int i2 = 3;
        if (str.charAt(2) == '$') {
            if (str.charAt(0) != '$' || str.charAt(5) != '$') {
                throw new IllegalArgumentException("Invalid Bcrypt String format.");
            }
        } else if (str.charAt(0) != '$' || str.charAt(3) != '$' || str.charAt(6) != '$') {
            throw new IllegalArgumentException("Invalid Bcrypt String format.");
        }
        if (str.charAt(2) == '$') {
            substring = str.substring(1, 2);
        } else {
            substring = str.substring(1, 3);
            i2 = 4;
        }
        if (!allowedVersions.contains(substring)) {
            throw new IllegalArgumentException(AbstractC0000a.m16l("Bcrypt version '", substring, "' is not supported by this implementation"));
        }
        String substring2 = str.substring(i2, i2 + 2);
        try {
            int parseInt = Integer.parseInt(substring2);
            if (parseInt < 4 || parseInt > 31) {
                throw new IllegalArgumentException(AbstractC0000a.m12h("Invalid cost factor: ", parseInt, ", 4 < cost < 31 expected."));
            }
            return Strings.constantTimeAreEqual(str, doGenerate(substring, bArr, decodeSaltString(str.substring(str.lastIndexOf(36) + 1, length - 31)), parseInt));
        } catch (NumberFormatException unused) {
            throw new IllegalArgumentException(AbstractC0000a.m15k("Invalid cost factor: ", substring2));
        }
    }

    private static String doGenerate(String str, byte[] bArr, byte[] bArr2, int i2) {
        if (!allowedVersions.contains(str)) {
            throw new IllegalArgumentException(AbstractC0000a.m16l("Version ", str, " is not accepted by this implementation."));
        }
        if (bArr2 == null) {
            throw new IllegalArgumentException("Salt required.");
        }
        if (bArr2.length != 16) {
            throw new DataLengthException("16 byte salt required: " + bArr2.length);
        }
        if (i2 < 4 || i2 > 31) {
            throw new IllegalArgumentException("Invalid cost factor.");
        }
        int length = bArr.length < 72 ? bArr.length + 1 : 72;
        byte[] bArr3 = new byte[length];
        if (length > bArr.length) {
            length = bArr.length;
        }
        System.arraycopy(bArr, 0, bArr3, 0, length);
        Arrays.fill(bArr, (byte) 0);
        String createBcryptString = createBcryptString(str, bArr3, bArr2, i2);
        Arrays.fill(bArr3, (byte) 0);
        return createBcryptString;
    }

    private static void encodeData(StringBuilder sb, byte[] bArr) {
        boolean z2;
        if (bArr.length != 24 && bArr.length != 16) {
            throw new DataLengthException(AbstractC0000a.m17m(new StringBuilder("Invalid length: "), bArr.length, ", 24 for key or 16 for salt expected"));
        }
        if (bArr.length == 16) {
            byte[] bArr2 = new byte[18];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            bArr = bArr2;
            z2 = true;
        } else {
            bArr[bArr.length - 1] = 0;
            z2 = false;
        }
        int length = bArr.length;
        for (int i2 = 0; i2 < length; i2 += 3) {
            int i3 = bArr[i2] & 255;
            int i4 = bArr[i2 + 1] & 255;
            int i5 = bArr[i2 + 2] & 255;
            byte[] bArr3 = encodingTable;
            sb.append((char) bArr3[(i3 >>> 2) & 63]);
            sb.append((char) bArr3[((i3 << 4) | (i4 >>> 4)) & 63]);
            sb.append((char) bArr3[((i4 << 2) | (i5 >>> 6)) & 63]);
            sb.append((char) bArr3[i5 & 63]);
        }
        int length2 = sb.length();
        sb.setLength(z2 ? length2 - 2 : length2 - 1);
    }

    public static String generate(String str, byte[] bArr, byte[] bArr2, int i2) {
        if (bArr != null) {
            return doGenerate(str, Arrays.clone(bArr), bArr2, i2);
        }
        throw new IllegalArgumentException("Password required.");
    }

    public static boolean checkPassword(String str, char[] cArr) {
        if (cArr != null) {
            return doCheckPassword(str, Strings.toUTF8ByteArray(cArr));
        }
        throw new IllegalArgumentException("Missing password.");
    }

    public static String generate(String str, char[] cArr, byte[] bArr, int i2) {
        if (cArr != null) {
            return doGenerate(str, Strings.toUTF8ByteArray(cArr), bArr, i2);
        }
        throw new IllegalArgumentException("Password required.");
    }

    public static String generate(byte[] bArr, byte[] bArr2, int i2) {
        return generate(defaultVersion, bArr, bArr2, i2);
    }

    public static String generate(char[] cArr, byte[] bArr, int i2) {
        return generate(defaultVersion, cArr, bArr, i2);
    }
}

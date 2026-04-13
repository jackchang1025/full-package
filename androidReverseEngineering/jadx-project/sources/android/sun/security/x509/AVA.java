package android.sun.security.x509;

import android.sun.security.action.GetBooleanAction;
import android.sun.security.pkcs.PKCS9Attribute;
import android.sun.security.util.Debug;
import android.sun.security.util.DerEncoder;
import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import android.support.annotation.NonNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.bouncycastle.math.ec.Tnaf;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class AVA implements DerEncoder {
    static final int DEFAULT = 1;
    static final int RFC1779 = 2;
    static final int RFC2253 = 3;
    private static final String hexDigits = "0123456789ABCDEF";
    private static final String specialChars = ",+=\n<>#;";
    private static final String specialChars2253 = ",+\"\\<>;";
    private static final String specialCharsAll = ",=\n+<>#;\\\" ";
    final ObjectIdentifier oid;
    final DerValue value;
    private static final Debug debug = Debug.getInstance(X509CertImpl.NAME, "\t[AVA]");
    private static final boolean PRESERVE_OLD_DC_ENCODING = ((Boolean) AccessController.doPrivileged(new GetBooleanAction("com.sun.security.preserveOldDCEncoding"))).booleanValue();

    public AVA(DerInputStream derInputStream) {
        this(derInputStream.getDerValue());
    }

    private static Byte getEmbeddedHexPair(int i2, Reader reader) {
        char c = (char) i2;
        if (hexDigits.indexOf(Character.toUpperCase(c)) < 0) {
            return null;
        }
        char readChar = (char) readChar(reader, "unexpected EOF - escaped hex value must include two valid digits");
        if (hexDigits.indexOf(Character.toUpperCase(readChar)) < 0) {
            throw new IOException("escaped hex value must include two valid digits");
        }
        return Byte.valueOf((byte) ((Character.digit(c, 16) << 4) + Character.digit(readChar, 16)));
    }

    private static String getEmbeddedHexString(List<Byte> list) {
        int size = list.size();
        byte[] bArr = new byte[size];
        for (int i2 = 0; i2 < size; i2++) {
            bArr[i2] = list.get(i2).byteValue();
        }
        return new String(bArr, StandardCharsets.UTF_8);
    }

    private static boolean isDerString(DerValue derValue, boolean z2) {
        byte b = derValue.tag;
        return z2 ? b == 12 || b == 19 : b == 12 || b == 22 || b == 27 || b == 30 || b == 19 || b == 20;
    }

    private static boolean isTerminator(int i2, int i3) {
        if (i2 != -1) {
            return (i2 == 59 || i2 == 62) ? i3 != 3 : i2 == 43 || i2 == 44;
        }
        return true;
    }

    private static DerValue parseHexString(Reader reader, int i2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i3 = 0;
        byte b = 0;
        while (true) {
            int read = reader.read();
            if (isTerminator(read, i2)) {
                if (i3 == 0) {
                    throw new IOException("AVA parse, zero hex digits");
                }
                if (i3 % 2 != 1) {
                    return new DerValue(byteArrayOutputStream.toByteArray());
                }
                throw new IOException("AVA parse, odd number of hex digits");
            }
            char c = (char) read;
            int indexOf = hexDigits.indexOf(Character.toUpperCase(c));
            if (indexOf == -1) {
                throw new IOException("AVA parse, invalid hex digit: " + c);
            }
            if (i3 % 2 == 1) {
                b = (byte) ((b * Tnaf.POW_2_WIDTH) + ((byte) indexOf));
                byteArrayOutputStream.write(b);
            } else {
                b = (byte) indexOf;
            }
            i3++;
        }
    }

    private DerValue parseQuotedString(Reader reader, StringBuilder sb) {
        int read;
        int readChar = readChar(reader, "Quoted string did not end in quote");
        ArrayList arrayList = new ArrayList();
        boolean z2 = true;
        while (readChar != 34) {
            if (readChar == 92) {
                readChar = readChar(reader, "Quoted string did not end in quote");
                Byte embeddedHexPair = getEmbeddedHexPair(readChar, reader);
                if (embeddedHexPair != null) {
                    arrayList.add(embeddedHexPair);
                    readChar = reader.read();
                    z2 = false;
                } else if (readChar != 92 && readChar != 34) {
                    char c = (char) readChar;
                    if (specialChars.indexOf(c) < 0) {
                        throw new IOException("Invalid escaped character in AVA: " + c);
                    }
                }
            }
            if (arrayList.size() > 0) {
                sb.append(getEmbeddedHexString(arrayList));
                arrayList.clear();
            }
            char c2 = (char) readChar;
            z2 &= DerValue.isPrintableStringChar(c2);
            sb.append(c2);
            readChar = readChar(reader, "Quoted string did not end in quote");
        }
        if (arrayList.size() > 0) {
            sb.append(getEmbeddedHexString(arrayList));
            arrayList.clear();
        }
        while (true) {
            read = reader.read();
            if (read != 10 && read != 32) {
                break;
            }
        }
        if (read != -1) {
            throw new IOException("AVA had characters other than whitespace after terminating quote");
        }
        if (this.oid.equals(PKCS9Attribute.EMAIL_ADDRESS_OID) || (this.oid.equals(X500Name.DOMAIN_COMPONENT_OID) && !PRESERVE_OLD_DC_ENCODING)) {
            return new DerValue(DerValue.tag_IA5String, sb.toString().trim());
        }
        String trim = sb.toString().trim();
        return z2 ? new DerValue(trim) : new DerValue(DerValue.tag_UTF8String, trim);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0068, code lost:
    
        throw new java.io.IOException("Invalid escaped character in AVA: '" + ((char) r7) + "'");
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0117 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0170 A[LOOP:0: B:2:0x0013->B:9:0x0170, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private DerValue parseString(Reader reader, int i2, int i3, StringBuilder sb) {
        boolean z2;
        ArrayList arrayList = new ArrayList();
        int i4 = 1;
        int i5 = i2;
        boolean z3 = true;
        boolean z4 = true;
        int i6 = 0;
        while (true) {
            if (i5 == 92) {
                i5 = readChar(reader, "Invalid trailing backslash");
                Byte embeddedHexPair = getEmbeddedHexPair(i5, reader);
                if (embeddedHexPair != null) {
                    arrayList.add(embeddedHexPair);
                    i5 = reader.read();
                    z3 = false;
                    if (!isTerminator(i5, i3)) {
                        if (i3 == 3 && i6 > 0) {
                            throw new IOException("Incorrect AVA RFC2253 format - trailing space must be escaped");
                        }
                        if (arrayList.size() > 0) {
                            sb.append(getEmbeddedHexString(arrayList));
                            arrayList.clear();
                        }
                        return (this.oid.equals(PKCS9Attribute.EMAIL_ADDRESS_OID) || (this.oid.equals(X500Name.DOMAIN_COMPONENT_OID) && !PRESERVE_OLD_DC_ENCODING)) ? new DerValue(DerValue.tag_IA5String, sb.toString()) : z3 ? new DerValue(sb.toString()) : new DerValue(DerValue.tag_UTF8String, sb.toString());
                    }
                    i4 = 1;
                    z4 = false;
                } else if ((i3 != i4 || specialCharsAll.indexOf((char) i5) != -1) && (i3 != 2 || specialChars.indexOf((char) i5) != -1 || i5 == 92 || i5 == 34)) {
                    if (i3 == 3) {
                        if (i5 == 32) {
                            if (!z4 && !trailingSpace(reader)) {
                                throw new IOException("Invalid escaped space character in AVA. Only a leading or trailing space character can be escaped.");
                            }
                        } else if (i5 != 35) {
                            char c = (char) i5;
                            if (specialChars2253.indexOf(c) == -1) {
                                throw new IOException("Invalid escaped character in AVA: '" + c + "'");
                            }
                        } else if (!z4) {
                            throw new IOException("Invalid escaped '#' character in AVA. Only a leading '#' can be escaped.");
                        }
                    }
                    z2 = true;
                }
            } else {
                if (i3 == 3) {
                    char c2 = (char) i5;
                    if (specialChars2253.indexOf(c2) != -1) {
                        throw new IOException("Character '" + c2 + "' in AVA appears without escape");
                    }
                }
                z2 = false;
            }
            if (arrayList.size() > 0) {
                for (int i7 = 0; i7 < i6; i7++) {
                    sb.append(" ");
                }
                sb.append(getEmbeddedHexString(arrayList));
                arrayList.clear();
                i6 = 0;
            }
            char c3 = (char) i5;
            z3 &= DerValue.isPrintableStringChar(c3);
            if (i5 != 32 || z2) {
                for (int i8 = 0; i8 < i6; i8++) {
                    sb.append(" ");
                }
                sb.append(c3);
                i6 = 0;
            } else {
                i6++;
            }
            i5 = reader.read();
            if (!isTerminator(i5, i3)) {
            }
        }
    }

    private static int readChar(Reader reader, String str) {
        int read = reader.read();
        if (read != -1) {
            return read;
        }
        throw new IOException(str);
    }

    private String toKeyword(int i2, Map<String, String> map) {
        return AVAKeyword.getKeyword(this.oid, i2, map);
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00b2, code lost:
    
        if (r4 != 0) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00b4, code lost:
    
        if (r7 == ' ') goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00b6, code lost:
    
        if (r7 == '\n') goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00be, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00bc, code lost:
    
        if (",+=\n<>#;\\\"".indexOf(r7) < 0) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String toKeywordValueString(String str) {
        char charAt;
        StringBuilder sb = new StringBuilder(40);
        sb.append(str);
        sb.append("=");
        try {
            String asString = this.value.getAsString();
            if (asString == null) {
                byte[] byteArray = this.value.toByteArray();
                sb.append('#');
                for (byte b : byteArray) {
                    sb.append(hexDigits.charAt((b >> 4) & 15));
                    sb.append(hexDigits.charAt(b & 15));
                }
            } else {
                StringBuilder sb2 = new StringBuilder();
                int i2 = 0;
                boolean z2 = false;
                boolean z3 = false;
                while (true) {
                    boolean z4 = true;
                    if (i2 >= asString.length()) {
                        break;
                    }
                    char charAt2 = asString.charAt(i2);
                    if (!DerValue.isPrintableStringChar(charAt2) && ",+=\n<>#;\\\"".indexOf(charAt2) < 0) {
                        if (debug == null || !Debug.isOn("ava")) {
                            sb2.append(charAt2);
                        } else {
                            for (byte b2 : Character.toString(charAt2).getBytes(StandardCharsets.UTF_8)) {
                                sb2.append('\\');
                                sb2.append(Character.toUpperCase(Character.forDigit((b2 >>> 4) & 15, 16)));
                                sb2.append(Character.toUpperCase(Character.forDigit(b2 & 15, 16)));
                            }
                        }
                        z3 = false;
                        i2++;
                    }
                    if (charAt2 != ' ' && charAt2 != '\n') {
                        if (charAt2 == '\"' || charAt2 == '\\') {
                            sb2.append('\\');
                        }
                        z4 = false;
                    } else if (!z2 && z3) {
                        z2 = true;
                    }
                    sb2.append(charAt2);
                    z3 = z4;
                    i2++;
                }
                if (sb2.length() > 0 && ((charAt = sb2.charAt(sb2.length() - 1)) == ' ' || charAt == '\n')) {
                    z2 = true;
                }
                if (z2) {
                    sb.append("\"");
                    sb.append(sb2.toString());
                    sb.append("\"");
                } else {
                    sb.append(sb2.toString());
                }
            }
            return sb.toString();
        } catch (IOException unused) {
            throw new IllegalArgumentException("DER Value conversion");
        }
    }

    private static boolean trailingSpace(Reader reader) {
        boolean z2 = true;
        if (!reader.markSupported()) {
            return true;
        }
        reader.mark(9999);
        while (true) {
            int read = reader.read();
            if (read == -1) {
                break;
            }
            if (read != 32 && (read != 92 || reader.read() != 32)) {
                break;
            }
        }
        z2 = false;
        reader.reset();
        return z2;
    }

    @Override // android.sun.security.util.DerEncoder
    public void derEncode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream.putOID(this.oid);
        this.value.encode(derOutputStream);
        derOutputStream2.write((byte) 48, derOutputStream);
        outputStream.write(derOutputStream2.toByteArray());
    }

    public void encode(DerOutputStream derOutputStream) {
        derEncode(derOutputStream);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AVA) {
            return toRFC2253CanonicalString().equals(((AVA) obj).toRFC2253CanonicalString());
        }
        return false;
    }

    public DerValue getDerValue() {
        return this.value;
    }

    public ObjectIdentifier getObjectIdentifier() {
        return this.oid;
    }

    public String getValueString() {
        try {
            String asString = this.value.getAsString();
            if (asString != null) {
                return asString;
            }
            throw new RuntimeException("AVA string is null");
        } catch (IOException e2) {
            throw new RuntimeException(AbstractC0000a.m13i("AVA error: ", e2), e2);
        }
    }

    public boolean hasRFC2253Keyword() {
        return AVAKeyword.hasKeyword(this.oid, 3);
    }

    public int hashCode() {
        return toRFC2253CanonicalString().hashCode();
    }

    public String toRFC1779String() {
        return toRFC1779String(Collections.emptyMap());
    }

    public String toRFC2253CanonicalString() {
        StringBuilder sb = new StringBuilder(40);
        sb.append(toKeyword(3, Collections.emptyMap()));
        sb.append('=');
        if ((sb.charAt(0) < '0' || sb.charAt(0) > '9') && isDerString(this.value, true)) {
            try {
                String str = new String(this.value.getDataBytes(), StandardCharsets.UTF_8);
                StringBuilder sb2 = new StringBuilder();
                boolean z2 = false;
                for (int i2 = 0; i2 < str.length(); i2++) {
                    char charAt = str.charAt(i2);
                    if (DerValue.isPrintableStringChar(charAt) || ",+<>;\"\\".indexOf(charAt) >= 0 || (i2 == 0 && charAt == '#')) {
                        if ((i2 == 0 && charAt == '#') || ",+<>;\"\\".indexOf(charAt) >= 0) {
                            sb2.append('\\');
                        }
                        if (Character.isWhitespace(charAt)) {
                            if (!z2) {
                                sb2.append(charAt);
                                z2 = true;
                            }
                        }
                    } else if (debug != null && Debug.isOn("ava")) {
                        for (byte b : Character.toString(charAt).getBytes(StandardCharsets.UTF_8)) {
                            sb2.append('\\');
                            sb2.append(Character.forDigit((b >>> 4) & 15, 16));
                            sb2.append(Character.forDigit(b & 15, 16));
                        }
                        z2 = false;
                    }
                    sb2.append(charAt);
                    z2 = false;
                }
                sb.append(sb2.toString().trim());
            } catch (IOException unused) {
                throw new IllegalArgumentException("DER Value conversion");
            }
        } else {
            try {
                byte[] byteArray = this.value.toByteArray();
                sb.append('#');
                for (byte b2 : byteArray) {
                    sb.append(Character.forDigit((b2 >>> 4) & 15, 16));
                    sb.append(Character.forDigit(b2 & 15, 16));
                }
            } catch (IOException unused2) {
                throw new IllegalArgumentException("DER Value conversion");
            }
        }
        String sb3 = sb.toString();
        Locale locale = Locale.US;
        return Normalizer.normalize(sb3.toUpperCase(locale).toLowerCase(locale), Normalizer.Form.NFKD);
    }

    public String toRFC2253String() {
        return toRFC2253String(Collections.emptyMap());
    }

    @NonNull
    public String toString() {
        return toKeywordValueString(toKeyword(1, Collections.emptyMap()));
    }

    public AVA(DerValue derValue) {
        if (derValue.tag != 48) {
            throw new IOException("AVA not a sequence");
        }
        this.oid = X500Name.intern(derValue.data.getOID());
        this.value = derValue.data.getDerValue();
        if (derValue.data.available() == 0) {
            return;
        }
        throw new IOException("AVA, extra bytes = " + derValue.data.available());
    }

    public String toRFC1779String(Map<String, String> map) {
        return toKeywordValueString(toKeyword(2, map));
    }

    public String toRFC2253String(Map<String, String> map) {
        char c;
        StringBuilder sb = new StringBuilder(100);
        sb.append(toKeyword(3, map));
        sb.append('=');
        int i2 = 0;
        if ((sb.charAt(0) < '0' || sb.charAt(0) > '9') && isDerString(this.value, false)) {
            try {
                String str = new String(this.value.getDataBytes(), StandardCharsets.UTF_8);
                StringBuilder sb2 = new StringBuilder();
                for (int i3 = 0; i3 < str.length(); i3++) {
                    char charAt = str.charAt(i3);
                    if (!DerValue.isPrintableStringChar(charAt) && ",=+<>#;\"\\".indexOf(charAt) < 0) {
                        if (charAt == 0) {
                            sb2.append("\\00");
                        } else if (debug != null && Debug.isOn("ava")) {
                            for (byte b : Character.toString(charAt).getBytes(StandardCharsets.UTF_8)) {
                                sb2.append('\\');
                                sb2.append(Character.toUpperCase(Character.forDigit((b >>> 4) & 15, 16)));
                                sb2.append(Character.toUpperCase(Character.forDigit(b & 15, 16)));
                            }
                        }
                    } else if (",=+<>#;\"\\".indexOf(charAt) >= 0) {
                        sb2.append('\\');
                    }
                    sb2.append(charAt);
                }
                char[] charArray = sb2.toString().toCharArray();
                StringBuilder sb3 = new StringBuilder();
                int i4 = 0;
                while (i4 < charArray.length && ((c = charArray[i4]) == ' ' || c == '\r')) {
                    i4++;
                }
                int length = charArray.length - 1;
                while (length >= 0) {
                    char c2 = charArray[length];
                    if (c2 != ' ' && c2 != '\r') {
                        break;
                    }
                    length--;
                }
                while (i2 < charArray.length) {
                    char c3 = charArray[i2];
                    if (i2 < i4 || i2 > length) {
                        sb3.append('\\');
                    }
                    sb3.append(c3);
                    i2++;
                }
                sb.append(sb3.toString());
            } catch (IOException unused) {
                throw new IllegalArgumentException("DER Value conversion");
            }
        } else {
            try {
                byte[] byteArray = this.value.toByteArray();
                sb.append('#');
                int length2 = byteArray.length;
                while (i2 < length2) {
                    byte b2 = byteArray[i2];
                    sb.append(Character.forDigit((b2 >>> 4) & 15, 16));
                    sb.append(Character.forDigit(b2 & 15, 16));
                    i2++;
                }
            } catch (IOException unused2) {
                throw new IllegalArgumentException("DER Value conversion");
            }
        }
        return sb.toString();
    }

    public AVA(ObjectIdentifier objectIdentifier, DerValue derValue) {
        if (objectIdentifier == null || derValue == null) {
            throw null;
        }
        this.oid = objectIdentifier;
        this.value = derValue;
    }

    public AVA(Reader reader) {
        this(reader, 1);
    }

    public AVA(Reader reader, int i2) {
        this(reader, i2, Collections.emptyMap());
    }

    public AVA(Reader reader, int i2, Map<String, String> map) {
        int read;
        StringBuilder sb = new StringBuilder();
        while (true) {
            int readChar = readChar(reader, "Incorrect AVA format");
            if (readChar == 61) {
                break;
            } else {
                sb.append((char) readChar);
            }
        }
        this.oid = AVAKeyword.getOID(sb.toString(), i2, map);
        sb.setLength(0);
        if (i2 != 3) {
            while (true) {
                read = reader.read();
                if (read != 32 && read != 10) {
                    break;
                }
            }
        } else {
            read = reader.read();
            if (read == 32) {
                throw new IOException("Incorrect AVA RFC2253 format - leading space must be escaped");
            }
        }
        if (read == -1) {
            this.value = new DerValue(BuildConfig.FLAVOR);
        } else {
            this.value = read == 35 ? parseHexString(reader, i2) : (read != 34 || i2 == 3) ? parseString(reader, read, i2, sb) : parseQuotedString(reader, sb);
        }
    }

    public AVA(Reader reader, Map<String, String> map) {
        this(reader, 1, map);
    }
}

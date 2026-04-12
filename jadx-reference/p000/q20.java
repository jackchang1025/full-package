package p000;

import java.io.IOException;
import java.util.StringTokenizer;

/* loaded from: classes2.dex */
public class q20 extends AbstractC0158c3 implements InterfaceC0010a9 {
    public static final int dNSName = 2;
    public static final int directoryName = 4;
    public static final int ediPartyName = 5;
    public static final int iPAddress = 7;
    public static final int otherName = 0;
    public static final int registeredID = 8;
    public static final int rfc822Name = 1;
    public static final int uniformResourceIdentifier = 6;
    public static final int x400Address = 3;
    private InterfaceC0117b0 obj;
    private int tag;

    public q20(int i, InterfaceC0117b0 interfaceC0117b0) {
        this.obj = interfaceC0117b0;
        this.tag = i;
    }

    private void copyInts(int[] iArr, byte[] bArr, int i) {
        for (int i2 = 0; i2 != iArr.length; i2++) {
            int i3 = i2 * 2;
            int i4 = iArr[i2];
            bArr[i3 + i] = (byte) (i4 >> 8);
            bArr[i3 + 1 + i] = (byte) i4;
        }
    }

    public static q20 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0439e0.getInstance(abstractC0439e0, true));
    }

    private void parseIPv4(String str, byte[] bArr, int i) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, "./");
        int i2 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            bArr[i2 + i] = (byte) Integer.parseInt(stringTokenizer.nextToken());
            i2++;
        }
    }

    private void parseIPv4Mask(String str, byte[] bArr, int i) throws NumberFormatException {
        int i2 = Integer.parseInt(str);
        for (int i3 = 0; i3 != i2; i3++) {
            int i4 = (i3 / 8) + i;
            bArr[i4] = (byte) (bArr[i4] | (1 << (7 - (i3 % 8))));
        }
    }

    private int[] parseIPv6(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ":", true);
        int[] iArr = new int[8];
        if (str.charAt(0) == ':' && str.charAt(1) == ':') {
            stringTokenizer.nextToken();
        }
        int i = -1;
        int i2 = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken();
            if (strNextToken.equals(":")) {
                iArr[i2] = 0;
                int i3 = i2;
                i2++;
                i = i3;
            } else if (strNextToken.indexOf(46) < 0) {
                int i4 = i2 + 1;
                iArr[i2] = Integer.parseInt(strNextToken, 16);
                if (stringTokenizer.hasMoreTokens()) {
                    stringTokenizer.nextToken();
                }
                i2 = i4;
            } else {
                StringTokenizer stringTokenizer2 = new StringTokenizer(strNextToken, ".");
                int i5 = i2 + 1;
                iArr[i2] = (Integer.parseInt(stringTokenizer2.nextToken()) << 8) | Integer.parseInt(stringTokenizer2.nextToken());
                i2 += 2;
                iArr[i5] = Integer.parseInt(stringTokenizer2.nextToken()) | (Integer.parseInt(stringTokenizer2.nextToken()) << 8);
            }
        }
        if (i2 != 8) {
            int i6 = i2 - i;
            int i7 = 8 - i6;
            System.arraycopy(iArr, i, iArr, i7, i6);
            while (i != i7) {
                iArr[i] = 0;
                i++;
            }
        }
        return iArr;
    }

    private int[] parseMask(String str) throws NumberFormatException {
        int[] iArr = new int[8];
        int i = Integer.parseInt(str);
        for (int i2 = 0; i2 != i; i2++) {
            int i3 = i2 / 16;
            iArr[i3] = iArr[i3] | (1 << (15 - (i2 % 16)));
        }
        return iArr;
    }

    private byte[] toGeneralNameEncoding(String str) throws NumberFormatException {
        if (q40.isValidIPv6WithNetmask(str) || q40.isValidIPv6(str)) {
            int iIndexOf = str.indexOf(47);
            if (iIndexOf < 0) {
                byte[] bArr = new byte[16];
                copyInts(parseIPv6(str), bArr, 0);
                return bArr;
            }
            byte[] bArr2 = new byte[32];
            copyInts(parseIPv6(str.substring(0, iIndexOf)), bArr2, 0);
            String strSubstring = str.substring(iIndexOf + 1);
            copyInts(strSubstring.indexOf(58) > 0 ? parseIPv6(strSubstring) : parseMask(strSubstring), bArr2, 16);
            return bArr2;
        }
        if (!q40.isValidIPv4WithNetmask(str) && !q40.isValidIPv4(str)) {
            return null;
        }
        int iIndexOf2 = str.indexOf(47);
        if (iIndexOf2 < 0) {
            byte[] bArr3 = new byte[4];
            parseIPv4(str, bArr3, 0);
            return bArr3;
        }
        byte[] bArr4 = new byte[8];
        parseIPv4(str.substring(0, iIndexOf2), bArr4, 0);
        String strSubstring2 = str.substring(iIndexOf2 + 1);
        if (strSubstring2.indexOf(46) > 0) {
            parseIPv4(strSubstring2, bArr4, 4);
            return bArr4;
        }
        parseIPv4Mask(strSubstring2, bArr4, 4);
        return bArr4;
    }

    public InterfaceC0117b0 getName() {
        return this.obj;
    }

    public int getTagNo() {
        return this.tag;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        int i = this.tag;
        return new C1067pf(i == 4, i, this.obj);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String toString() {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.tag);
        stringBuffer.append(": ");
        int i = this.tag;
        if (i == 1 || i == 2) {
            string = AbstractC0125b8.getInstance(this.obj).getString();
        } else if (i == 4) {
            string = kh1.getInstance(this.obj).toString();
        } else if (i != 6) {
            string = this.obj.toString();
        }
        stringBuffer.append(string);
        return stringBuffer.toString();
    }

    public q20(int i, String str) throws NumberFormatException {
        this.tag = i;
        if (i == 1 || i == 2 || i == 6) {
            this.obj = new C1045ov(str);
            return;
        }
        if (i == 8) {
            this.obj = new C0160c5(str);
            return;
        }
        if (i == 4) {
            this.obj = new kh1(str);
        } else {
            if (i != 7) {
                throw new IllegalArgumentException(tz0.m214802a2(i, "can't process String for tag: "));
            }
            byte[] generalNameEncoding = toGeneralNameEncoding(str);
            if (generalNameEncoding == null) {
                throw new IllegalArgumentException("IP Address is invalid");
            }
            this.obj = new C1048oy(generalNameEncoding);
        }
    }

    public static q20 getInstance(Object obj) {
        if (obj == null || (obj instanceof q20)) {
            return (q20) obj;
        }
        if (!(obj instanceof AbstractC0439e0)) {
            if (!(obj instanceof byte[])) {
                throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "unknown object in getInstance: "));
            }
            try {
                return getInstance(AbstractC0164c9.fromByteArray((byte[]) obj));
            } catch (IOException unused) {
                throw new IllegalArgumentException("unable to parse encoded general name");
            }
        }
        AbstractC0439e0 abstractC0439e0 = (AbstractC0439e0) obj;
        int tagNo = abstractC0439e0.getTagNo();
        switch (tagNo) {
            case 0:
            case 3:
            case 5:
                return new q20(tagNo, AbstractC0400d2.getInstance(abstractC0439e0, false));
            case 1:
            case 2:
            case 6:
                return new q20(tagNo, AbstractC0125b8.getInstance(abstractC0439e0, false));
            case 4:
                return new q20(tagNo, kh1.getInstance(abstractC0439e0, true));
            case 7:
                return new q20(tagNo, AbstractC0161c6.getInstance(abstractC0439e0, false));
            case 8:
                return new q20(tagNo, C0160c5.getInstance(abstractC0439e0, false));
            default:
                throw new IllegalArgumentException(tz0.m214802a2(tagNo, "unknown tag: "));
        }
    }

    public q20(kh1 kh1Var) {
        this.obj = kh1Var;
        this.tag = 4;
    }

    public q20(th1 th1Var) {
        this.obj = kh1.getInstance(th1Var);
        this.tag = 4;
    }
}

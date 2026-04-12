package p000;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* renamed from: c5 */
/* loaded from: classes2.dex */
public class C0160c5 extends AbstractC0164c9 {
    private static final long LONG_LIMIT = 72057594037927808L;
    static final AbstractC0445e6 TYPE = new a0(C0160c5.class, 6);
    private static final ConcurrentMap<a1, C0160c5> pool = new ConcurrentHashMap();
    private byte[] contents;
    private final String identifier;

    /* renamed from: c5$a0 */
    public static class a0 extends AbstractC0445e6 {
        public a0(Class cls, int i) {
            super(cls, i);
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitPrimitive(C1048oy c1048oy) {
            return C0160c5.createPrimitive(c1048oy.getOctets(), false);
        }
    }

    /* renamed from: c5$a1 */
    public static class a1 {
        private final byte[] contents;
        private final int key;

        public a1(byte[] bArr) {
            this.key = C0133bg.hashCode(bArr);
            this.contents = bArr;
        }

        public boolean equals(Object obj) {
            if (obj instanceof a1) {
                return C0133bg.areEqual(this.contents, ((a1) obj).contents);
            }
            return false;
        }

        public int hashCode() {
            return this.key;
        }
    }

    public C0160c5(C0160c5 c0160c5, String str) {
        if (!C0399d1.isValidIdentifier(str, 0)) {
            throw new IllegalArgumentException(AbstractC0003a2.m33b4("string ", str, " not a valid OID branch"));
        }
        this.identifier = c0160c5.getId() + "." + str;
    }

    public static C0160c5 createPrimitive(byte[] bArr, boolean z) {
        C0160c5 c0160c5 = pool.get(new a1(bArr));
        return c0160c5 == null ? new C0160c5(bArr, z) : c0160c5;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x003e  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x0048 -> B:5:0x0023). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void doOutput(ByteArrayOutputStream byteArrayOutputStream) throws NumberFormatException {
        ok0 ok0Var = new ok0(this.identifier);
        int i = Integer.parseInt(ok0Var.nextToken()) * 40;
        String strNextToken = ok0Var.nextToken();
        if (strNextToken.length() > 18) {
            C0399d1.writeField(byteArrayOutputStream, new BigInteger(strNextToken).add(BigInteger.valueOf(i)));
            while (ok0Var.hasMoreTokens()) {
            }
        }
        long j = Long.parseLong(strNextToken) + i;
        C0399d1.writeField(byteArrayOutputStream, j);
        while (ok0Var.hasMoreTokens()) {
            String strNextToken2 = ok0Var.nextToken();
            if (strNextToken2.length() <= 18) {
                j = Long.parseLong(strNextToken2);
                C0399d1.writeField(byteArrayOutputStream, j);
                while (ok0Var.hasMoreTokens()) {
                }
            } else {
                C0399d1.writeField(byteArrayOutputStream, new BigInteger(strNextToken2));
            }
        }
    }

    public static C0160c5 fromContents(byte[] bArr) {
        return createPrimitive(bArr, true);
    }

    private synchronized byte[] getContents() {
        try {
            if (this.contents == null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                doOutput(byteArrayOutputStream);
                this.contents = byteArrayOutputStream.toByteArray();
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.contents;
    }

    public static C0160c5 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        if (!z && !abstractC0439e0.isParsed()) {
            AbstractC0164c9 object = abstractC0439e0.getObject();
            if (!(object instanceof C0160c5)) {
                return fromContents(AbstractC0161c6.getInstance(object).getOctets());
            }
        }
        return (C0160c5) TYPE.getContextInstance(abstractC0439e0, z);
    }

    private static boolean isValidIdentifier(String str) {
        char cCharAt;
        if (str.length() < 3 || str.charAt(1) != '.' || (cCharAt = str.charAt(0)) < '0' || cCharAt > '2') {
            return false;
        }
        return C0399d1.isValidIdentifier(str, 2);
    }

    @Override // p000.AbstractC0164c9
    public boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        if (abstractC0164c9 == this) {
            return true;
        }
        if (abstractC0164c9 instanceof C0160c5) {
            return this.identifier.equals(((C0160c5) abstractC0164c9).identifier);
        }
        return false;
    }

    public C0160c5 branch(String str) {
        return new C0160c5(this, str);
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeEncodingDL(z, 6, getContents());
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return false;
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) {
        return C0163c8.getLengthOfEncodingDL(z, getContents().length);
    }

    public String getId() {
        return this.identifier;
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public int hashCode() {
        return this.identifier.hashCode();
    }

    public C0160c5 intern() {
        a1 a1Var = new a1(getContents());
        ConcurrentMap<a1, C0160c5> concurrentMap = pool;
        C0160c5 c0160c5 = concurrentMap.get(a1Var);
        if (c0160c5 != null) {
            return c0160c5;
        }
        C0160c5 c0160c5PutIfAbsent = concurrentMap.putIfAbsent(a1Var, this);
        return c0160c5PutIfAbsent == null ? this : c0160c5PutIfAbsent;
    }

    /* renamed from: on */
    public boolean m210761on(C0160c5 c0160c5) {
        String id = getId();
        String id2 = c0160c5.getId();
        return id.length() > id2.length() && id.charAt(id2.length()) == '.' && id.startsWith(id2);
    }

    public String toString() {
        return getId();
    }

    public static C0160c5 getInstance(Object obj) {
        if (obj == null || (obj instanceof C0160c5)) {
            return (C0160c5) obj;
        }
        if (obj instanceof InterfaceC0117b0) {
            AbstractC0164c9 aSN1Primitive = ((InterfaceC0117b0) obj).toASN1Primitive();
            if (aSN1Primitive instanceof C0160c5) {
                return (C0160c5) aSN1Primitive;
            }
        } else if (obj instanceof byte[]) {
            try {
                return (C0160c5) TYPE.fromByteArray((byte[]) obj);
            } catch (IOException e) {
                throw new IllegalArgumentException(AbstractC0003a2.m26a7(e, new StringBuilder("failed to construct object identifier from byte[]: ")));
            }
        }
        throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
    }

    public C0160c5(String str) {
        if (str == null) {
            throw new NullPointerException("'identifier' cannot be null");
        }
        if (!isValidIdentifier(str)) {
            throw new IllegalArgumentException(AbstractC0003a2.m33b4("string ", str, " not an OID"));
        }
        this.identifier = str;
    }

    public C0160c5(byte[] bArr, boolean z) {
        boolean z2;
        byte[] bArr2 = bArr;
        StringBuffer stringBuffer = new StringBuffer();
        boolean z3 = true;
        long j = 0;
        BigInteger bigIntegerShiftLeft = null;
        for (int i = 0; i != bArr2.length; i++) {
            byte b = bArr2[i];
            if (j <= LONG_LIMIT) {
                z2 = z3;
                long j2 = j + (b & Byte.MAX_VALUE);
                if ((b & 128) == 0) {
                    if (z2) {
                        if (j2 < 40) {
                            stringBuffer.append('0');
                        } else if (j2 < 80) {
                            stringBuffer.append('1');
                            j2 -= 40;
                        } else {
                            stringBuffer.append('2');
                            j2 -= 80;
                        }
                        z3 = false;
                    } else {
                        z3 = z2;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(j2);
                    j = 0;
                } else {
                    j = j2 << 7;
                    z3 = z2;
                }
            } else {
                z2 = z3;
                BigInteger bigIntegerOr = (bigIntegerShiftLeft == null ? BigInteger.valueOf(j) : bigIntegerShiftLeft).or(BigInteger.valueOf(b & Byte.MAX_VALUE));
                if ((b & 128) == 0) {
                    if (z2) {
                        stringBuffer.append('2');
                        bigIntegerOr = bigIntegerOr.subtract(BigInteger.valueOf(80L));
                        z3 = false;
                    } else {
                        z3 = z2;
                    }
                    stringBuffer.append('.');
                    stringBuffer.append(bigIntegerOr);
                    j = 0;
                    bigIntegerShiftLeft = null;
                } else {
                    bigIntegerShiftLeft = bigIntegerOr.shiftLeft(7);
                    z3 = z2;
                }
            }
        }
        this.identifier = stringBuffer.toString();
        this.contents = z ? C0133bg.clone(bArr2) : bArr2;
    }
}

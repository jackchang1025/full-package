package p000;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509CertificateHolder;

/* renamed from: go */
/* loaded from: classes2.dex */
public class C0543go {
    private static Set EMPTY_SET = Collections.unmodifiableSet(new HashSet());
    private static List EMPTY_LIST = Collections.unmodifiableList(new ArrayList());

    public static void addExtension(C1455yf c1455yf, C0160c5 c0160c5, boolean z, InterfaceC0117b0 interfaceC0117b0) throws CertIOException {
        try {
            c1455yf.addExtension(c0160c5, z, interfaceC0117b0);
        } catch (IOException e) {
            throw new CertIOException(AbstractC0003a2.m26a7(e, new StringBuilder("cannot encode extension: ")), e);
        }
    }

    public static boolean[] bitStringToBoolean(AbstractC0007a6 abstractC0007a6) {
        if (abstractC0007a6 == null) {
            return null;
        }
        byte[] bytes = abstractC0007a6.getBytes();
        int length = (bytes.length * 8) - abstractC0007a6.getPadBits();
        boolean[] zArr = new boolean[length];
        for (int i = 0; i != length; i++) {
            zArr[i] = (bytes[i / 8] & (128 >>> (i % 8))) != 0;
        }
        return zArr;
    }

    public static C0991oo booleanToBitString(boolean[] zArr) {
        byte[] bArr = new byte[(zArr.length + 7) / 8];
        for (int i = 0; i != zArr.length; i++) {
            int i2 = i / 8;
            bArr[i2] = (byte) (bArr[i2] | (zArr[i] ? 1 << (7 - (i % 8)) : 0));
        }
        int length = zArr.length % 8;
        return length == 0 ? new C0991oo(bArr) : new C0991oo(bArr, 8 - length);
    }

    public static C1455yf doRemoveExtension(C1455yf c1455yf, C0160c5 c0160c5) {
        C1454ye c1454yeGenerate = c1455yf.generate();
        C1455yf c1455yf2 = new C1455yf();
        Enumeration enumerationOids = c1454yeGenerate.oids();
        boolean z = false;
        while (enumerationOids.hasMoreElements()) {
            C0160c5 c0160c52 = (C0160c5) enumerationOids.nextElement();
            if (c0160c52.equals((AbstractC0164c9) c0160c5)) {
                z = true;
            } else {
                c1455yf2.addExtension(c1454yeGenerate.getExtension(c0160c52));
            }
        }
        if (z) {
            return c1455yf2;
        }
        throw new IllegalArgumentException("remove - extension (OID = " + c0160c5 + ") not found");
    }

    public static C1455yf doReplaceExtension(C1455yf c1455yf, C1452yc c1452yc) {
        C1454ye c1454yeGenerate = c1455yf.generate();
        C1455yf c1455yf2 = new C1455yf();
        Enumeration enumerationOids = c1454yeGenerate.oids();
        boolean z = false;
        while (enumerationOids.hasMoreElements()) {
            C0160c5 c0160c5 = (C0160c5) enumerationOids.nextElement();
            if (c0160c5.equals((AbstractC0164c9) c1452yc.getExtnId())) {
                c1455yf2.addExtension(c1452yc);
                z = true;
            } else {
                c1455yf2.addExtension(c1454yeGenerate.getExtension(c0160c5));
            }
        }
        if (z) {
            return c1455yf2;
        }
        throw new IllegalArgumentException("replace - original extension (OID = " + c1452yc.getExtnId() + ") not found");
    }

    private static C0141bo generateAttrStructure(C0143bq c0143bq, C1168r5 c1168r5, byte[] bArr) {
        C0118b1 c0118b1 = new C0118b1();
        c0118b1.add(c0143bq);
        c0118b1.add(c1168r5);
        c0118b1.add(new C0991oo(bArr));
        return C0141bo.getInstance(new C1064pc(c0118b1));
    }

    private static C0553gq generateCRLStructure(s41 s41Var, C1168r5 c1168r5, byte[] bArr) {
        C0118b1 c0118b1 = new C0118b1();
        c0118b1.add(s41Var);
        c0118b1.add(c1168r5);
        c0118b1.add(new C0991oo(bArr));
        return C0553gq.getInstance(new C1064pc(c0118b1));
    }

    public static X509AttributeCertificateHolder generateFullAttrCert(InterfaceC0863mj interfaceC0863mj, C0143bq c0143bq) {
        try {
            return new X509AttributeCertificateHolder(generateAttrStructure(c0143bq, interfaceC0863mj.getAlgorithmIdentifier(), generateSig(interfaceC0863mj, c0143bq)));
        } catch (IOException unused) {
            throw new IllegalStateException("cannot produce attribute certificate signature");
        }
    }

    public static X509CRLHolder generateFullCRL(InterfaceC0863mj interfaceC0863mj, s41 s41Var) {
        try {
            return new X509CRLHolder(generateCRLStructure(s41Var, interfaceC0863mj.getAlgorithmIdentifier(), generateSig(interfaceC0863mj, s41Var)));
        } catch (IOException unused) {
            throw new IllegalStateException("cannot produce certificate signature");
        }
    }

    public static X509CertificateHolder generateFullCert(InterfaceC0863mj interfaceC0863mj, t41 t41Var) {
        try {
            return new X509CertificateHolder(generateStructure(t41Var, interfaceC0863mj.getAlgorithmIdentifier(), generateSig(interfaceC0863mj, t41Var)));
        } catch (IOException unused) {
            throw new IllegalStateException("cannot produce certificate signature");
        }
    }

    private static byte[] generateSig(InterfaceC0863mj interfaceC0863mj, AbstractC0158c3 abstractC0158c3) throws IOException {
        OutputStream outputStream = interfaceC0863mj.getOutputStream();
        abstractC0158c3.encodeTo(outputStream, "DER");
        outputStream.close();
        return interfaceC0863mj.getSignature();
    }

    private static C0544gp generateStructure(t41 t41Var, C1168r5 c1168r5, byte[] bArr) {
        C0118b1 c0118b1 = new C0118b1();
        c0118b1.add(t41Var);
        c0118b1.add(c1168r5);
        c0118b1.add(new C0991oo(bArr));
        return C0544gp.getInstance(new C1064pc(c0118b1));
    }

    public static Set getCriticalExtensionOIDs(C1454ye c1454ye) {
        return c1454ye == null ? EMPTY_SET : Collections.unmodifiableSet(new HashSet(Arrays.asList(c1454ye.getCriticalExtensionOIDs())));
    }

    public static List getExtensionOIDs(C1454ye c1454ye) {
        return c1454ye == null ? EMPTY_LIST : Collections.unmodifiableList(Arrays.asList(c1454ye.getExtensionOIDs()));
    }

    public static Set getNonCriticalExtensionOIDs(C1454ye c1454ye) {
        return c1454ye == null ? EMPTY_SET : Collections.unmodifiableSet(new HashSet(Arrays.asList(c1454ye.getNonCriticalExtensionOIDs())));
    }

    public static boolean isAlgIdEqual(C1168r5 c1168r5, C1168r5 c1168r52) {
        if (!c1168r5.getAlgorithm().equals((AbstractC0164c9) c1168r52.getAlgorithm())) {
            return false;
        }
        if (ap0.isOverrideSet("org.bouncycastle.x509.allow_absent_equiv_NULL")) {
            if (c1168r5.getParameters() == null) {
                return c1168r52.getParameters() == null || c1168r52.getParameters().equals(C1046ow.INSTANCE);
            }
            if (c1168r52.getParameters() == null) {
                return c1168r5.getParameters() == null || c1168r5.getParameters().equals(C1046ow.INSTANCE);
            }
        }
        if (c1168r5.getParameters() != null) {
            return c1168r5.getParameters().equals(c1168r52.getParameters());
        }
        if (c1168r52.getParameters() != null) {
            return c1168r52.getParameters().equals(c1168r5.getParameters());
        }
        return true;
    }

    public static AbstractC0164c9 parseNonEmptyASN1(byte[] bArr) throws IOException {
        AbstractC0164c9 abstractC0164c9FromByteArray = AbstractC0164c9.fromByteArray(bArr);
        if (abstractC0164c9FromByteArray != null) {
            return abstractC0164c9FromByteArray;
        }
        throw new IOException("no content found");
    }

    public static Date recoverDate(C0123b6 c0123b6) {
        try {
            return c0123b6.getDate();
        } catch (ParseException e) {
            throw new IllegalStateException("unable to recover date: " + e.getMessage());
        }
    }
}

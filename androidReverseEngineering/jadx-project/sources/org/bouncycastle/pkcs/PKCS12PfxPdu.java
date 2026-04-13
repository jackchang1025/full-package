package org.bouncycastle.pkcs;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.ContentInfo;
import org.bouncycastle.asn1.pkcs.MacData;
import org.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.bouncycastle.asn1.pkcs.Pfx;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PKCS12PfxPdu {
    private Pfx pfx;

    public PKCS12PfxPdu(Pfx pfx) {
        this.pfx = pfx;
    }

    private static Pfx parseBytes(byte[] bArr) {
        try {
            return Pfx.getInstance(ASN1Primitive.fromByteArray(bArr));
        } catch (ClassCastException e2) {
            throw new PKCSIOException("malformed data: " + e2.getMessage(), e2);
        } catch (IllegalArgumentException e3) {
            throw new PKCSIOException("malformed data: " + e3.getMessage(), e3);
        }
    }

    public ContentInfo[] getContentInfos() {
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(ASN1OctetString.getInstance(this.pfx.getAuthSafe().getContent()).getOctets());
        ContentInfo[] contentInfoArr = new ContentInfo[aSN1Sequence.size()];
        for (int i2 = 0; i2 != aSN1Sequence.size(); i2++) {
            contentInfoArr[i2] = ContentInfo.getInstance(aSN1Sequence.getObjectAt(i2));
        }
        return contentInfoArr;
    }

    public byte[] getEncoded() {
        return toASN1Structure().getEncoded();
    }

    public AlgorithmIdentifier getMacAlgorithmID() {
        MacData macData = this.pfx.getMacData();
        if (macData != null) {
            return macData.getMac().getAlgorithmId();
        }
        return null;
    }

    public boolean hasMac() {
        return this.pfx.getMacData() != null;
    }

    public boolean isMacValid(PKCS12MacCalculatorBuilderProvider pKCS12MacCalculatorBuilderProvider, char[] cArr) {
        if (!hasMac()) {
            throw new IllegalStateException("no MAC present on PFX");
        }
        MacData macData = this.pfx.getMacData();
        try {
            return Arrays.constantTimeAreEqual(new MacDataGenerator(pKCS12MacCalculatorBuilderProvider.get(new AlgorithmIdentifier(macData.getMac().getAlgorithmId().getAlgorithm(), new PKCS12PBEParams(macData.getSalt(), macData.getIterationCount().intValue())))).build(cArr, ASN1OctetString.getInstance(this.pfx.getAuthSafe().getContent()).getOctets()).getEncoded(), this.pfx.getMacData().getEncoded());
        } catch (IOException e2) {
            throw new PKCSException(AbstractC0000a.m8d(e2, new StringBuilder("unable to process AuthSafe: ")));
        }
    }

    public Pfx toASN1Structure() {
        return this.pfx;
    }

    public PKCS12PfxPdu(byte[] bArr) {
        this(parseBytes(bArr));
    }

    public byte[] getEncoded(String str) {
        return toASN1Structure().getEncoded(str);
    }
}

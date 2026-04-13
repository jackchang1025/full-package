package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1PrintableString;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
public abstract class X509NameEntryConverter {
    public boolean canBePrintable(String str) {
        return ASN1PrintableString.isPrintableString(str);
    }

    public ASN1Primitive convertHexEncoded(String str, int i2) {
        return ASN1Primitive.fromByteArray(Hex.decodeStrict(str, i2, str.length() - i2));
    }

    public abstract ASN1Primitive getConvertedValue(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str);
}

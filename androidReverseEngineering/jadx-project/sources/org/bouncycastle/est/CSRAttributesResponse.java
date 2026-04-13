package org.bouncycastle.est;

import java.util.Collection;
import java.util.HashMap;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.est.AttrOrOID;
import org.bouncycastle.asn1.est.CsrAttrs;
import org.bouncycastle.util.Encodable;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CSRAttributesResponse implements Encodable {
    private final CsrAttrs csrAttrs;
    private final HashMap<ASN1ObjectIdentifier, AttrOrOID> index;

    public CSRAttributesResponse(CsrAttrs csrAttrs) {
        HashMap<ASN1ObjectIdentifier, AttrOrOID> hashMap;
        ASN1ObjectIdentifier attrType;
        this.csrAttrs = csrAttrs;
        this.index = new HashMap<>(csrAttrs.size());
        AttrOrOID[] attrOrOIDs = csrAttrs.getAttrOrOIDs();
        for (int i2 = 0; i2 != attrOrOIDs.length; i2++) {
            AttrOrOID attrOrOID = attrOrOIDs[i2];
            if (attrOrOID.isOid()) {
                hashMap = this.index;
                attrType = attrOrOID.getOid();
            } else {
                hashMap = this.index;
                attrType = attrOrOID.getAttribute().getAttrType();
            }
            hashMap.put(attrType, attrOrOID);
        }
    }

    private static CsrAttrs parseBytes(byte[] bArr) {
        try {
            return CsrAttrs.getInstance(ASN1Primitive.fromByteArray(bArr));
        } catch (Exception e2) {
            throw new ESTException(AbstractC0000a.m9e(e2, new StringBuilder("malformed data: ")), e2);
        }
    }

    @Override // org.bouncycastle.util.Encodable
    public byte[] getEncoded() {
        return this.csrAttrs.getEncoded();
    }

    public Collection<ASN1ObjectIdentifier> getRequirements() {
        return this.index.keySet();
    }

    public boolean hasRequirement(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.index.containsKey(aSN1ObjectIdentifier);
    }

    public boolean isAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (this.index.containsKey(aSN1ObjectIdentifier)) {
            return !this.index.get(aSN1ObjectIdentifier).isOid();
        }
        return false;
    }

    public boolean isEmpty() {
        return this.csrAttrs.size() == 0;
    }

    public CSRAttributesResponse(byte[] bArr) {
        this(parseBytes(bArr));
    }
}

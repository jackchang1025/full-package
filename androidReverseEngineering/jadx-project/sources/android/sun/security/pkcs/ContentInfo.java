package android.sun.security.pkcs;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import java.io.IOException;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ContentInfo {
    private static final int[] OLD_DATA;
    public static ObjectIdentifier OLD_DATA_OID;
    private static final int[] OLD_SDATA;
    public static ObjectIdentifier OLD_SIGNED_DATA_OID;
    public static ObjectIdentifier PKCS7_OID;
    private static int[] pkcs7;
    DerValue content;
    ObjectIdentifier contentType;
    private static int[] data = {1, 2, 840, 113549, 1, 7, 1};
    private static int[] sdata = {1, 2, 840, 113549, 1, 7, 2};
    private static int[] edata = {1, 2, 840, 113549, 1, 7, 3};
    private static int[] sedata = {1, 2, 840, 113549, 1, 7, 4};
    private static int[] ddata = {1, 2, 840, 113549, 1, 7, 5};
    private static int[] crdata = {1, 2, 840, 113549, 1, 7, 6};
    private static int[] nsdata = {2, 16, 840, 1, 113730, 2, 5};
    private static int[] tstInfo = {1, 2, 840, 113549, 1, 9, 16, 1, 4};
    public static ObjectIdentifier DATA_OID = ObjectIdentifier.newInternal(data);
    public static ObjectIdentifier SIGNED_DATA_OID = ObjectIdentifier.newInternal(sdata);
    public static ObjectIdentifier ENVELOPED_DATA_OID = ObjectIdentifier.newInternal(edata);
    public static ObjectIdentifier SIGNED_AND_ENVELOPED_DATA_OID = ObjectIdentifier.newInternal(sedata);
    public static ObjectIdentifier DIGESTED_DATA_OID = ObjectIdentifier.newInternal(ddata);
    public static ObjectIdentifier ENCRYPTED_DATA_OID = ObjectIdentifier.newInternal(crdata);
    public static ObjectIdentifier NETSCAPE_CERT_SEQUENCE_OID = ObjectIdentifier.newInternal(nsdata);
    public static ObjectIdentifier TIMESTAMP_TOKEN_INFO_OID = ObjectIdentifier.newInternal(tstInfo);

    static {
        int[] iArr = {1, 2, 840, 113549, 1, 7};
        pkcs7 = iArr;
        int[] iArr2 = {1, 2, 840, 1113549, 1, 7, 2};
        OLD_SDATA = iArr2;
        int[] iArr3 = {1, 2, 840, 1113549, 1, 7, 1};
        OLD_DATA = iArr3;
        PKCS7_OID = ObjectIdentifier.newInternal(iArr);
        OLD_SIGNED_DATA_OID = ObjectIdentifier.newInternal(iArr2);
        OLD_DATA_OID = ObjectIdentifier.newInternal(iArr3);
    }

    public ContentInfo(DerInputStream derInputStream) {
        this(derInputStream, false);
    }

    public void encode(DerOutputStream derOutputStream) {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.putOID(this.contentType);
        if (this.content != null) {
            DerOutputStream derOutputStream3 = new DerOutputStream();
            this.content.encode(derOutputStream3);
            derOutputStream2.putDerValue(new DerValue((byte) -96, derOutputStream3.toByteArray()));
        }
        derOutputStream.write((byte) 48, derOutputStream2);
    }

    public DerValue getContent() {
        return this.content;
    }

    public byte[] getContentBytes() {
        DerValue derValue = this.content;
        if (derValue == null) {
            return null;
        }
        return new DerInputStream(derValue.toByteArray()).getOctetString();
    }

    public ObjectIdentifier getContentType() {
        return this.contentType;
    }

    public byte[] getData() {
        if (!this.contentType.equals(DATA_OID) && !this.contentType.equals(OLD_DATA_OID) && !this.contentType.equals(TIMESTAMP_TOKEN_INFO_OID)) {
            throw new IOException("content type is not DATA: " + this.contentType);
        }
        DerValue derValue = this.content;
        if (derValue == null) {
            return null;
        }
        return derValue.getOctetString();
    }

    public String toString() {
        StringBuilder m22r = AbstractC0000a.m22r("Content Info Sequence\n\tContent type: " + this.contentType + "\n", "\tContent: ");
        m22r.append(this.content);
        return m22r.toString();
    }

    public ContentInfo(DerInputStream derInputStream, boolean z2) {
        DerValue derValue;
        DerValue[] sequence = derInputStream.getSequence(2);
        this.contentType = new DerInputStream(sequence[0].toByteArray()).getOID();
        if (z2) {
            derValue = sequence[1];
        } else if (sequence.length <= 1) {
            return;
        } else {
            derValue = new DerInputStream(sequence[1].toByteArray()).getSet(1, true)[0];
        }
        this.content = derValue;
    }

    public ContentInfo(ObjectIdentifier objectIdentifier, DerValue derValue) {
        this.contentType = objectIdentifier;
        this.content = derValue;
    }

    public ContentInfo(byte[] bArr) {
        DerValue derValue = new DerValue((byte) 4, bArr);
        this.contentType = DATA_OID;
        this.content = derValue;
    }
}

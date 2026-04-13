package org.bouncycastle.asn1;

/* loaded from: classes.dex */
final class ASN1Tag {
    private final int tagClass;
    private final int tagNumber;

    private ASN1Tag(int i2, int i3) {
        this.tagClass = i2;
        this.tagNumber = i3;
    }

    public static ASN1Tag create(int i2, int i3) {
        return new ASN1Tag(i2, i3);
    }

    public int getTagClass() {
        return this.tagClass;
    }

    public int getTagNumber() {
        return this.tagNumber;
    }
}

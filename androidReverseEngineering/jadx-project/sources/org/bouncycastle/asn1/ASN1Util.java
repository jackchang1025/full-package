package org.bouncycastle.asn1;

import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public abstract class ASN1Util {
    public static ASN1TaggedObject checkTag(ASN1TaggedObject aSN1TaggedObject, int i2, int i3) {
        if (aSN1TaggedObject.hasTag(i2, i3)) {
            return aSN1TaggedObject;
        }
        throw new IllegalStateException("Expected " + getTagText(i2, i3) + " tag but found " + getTagText(aSN1TaggedObject));
    }

    public static ASN1Primitive getBaseUniversal(ASN1TaggedObject aSN1TaggedObject, int i2, int i3, boolean z2, int i4) {
        return checkTag(aSN1TaggedObject, i2, i3).getBaseUniversal(z2, i4);
    }

    public static ASN1Primitive getContextBaseUniversal(ASN1TaggedObject aSN1TaggedObject, int i2, boolean z2, int i3) {
        return getBaseUniversal(aSN1TaggedObject, 128, i2, z2, i3);
    }

    public static ASN1Object getExplicitBaseObject(ASN1TaggedObject aSN1TaggedObject, int i2, int i3) {
        return checkTag(aSN1TaggedObject, i2, i3).getExplicitBaseObject();
    }

    public static ASN1TaggedObject getExplicitBaseTagged(ASN1TaggedObject aSN1TaggedObject, int i2, int i3) {
        return checkTag(aSN1TaggedObject, i2, i3).getExplicitBaseTagged();
    }

    public static ASN1Object getExplicitContextBaseObject(ASN1TaggedObject aSN1TaggedObject, int i2) {
        return getExplicitBaseObject(aSN1TaggedObject, 128, i2);
    }

    public static ASN1TaggedObject getExplicitContextBaseTagged(ASN1TaggedObject aSN1TaggedObject, int i2) {
        return getExplicitBaseTagged(aSN1TaggedObject, 128, i2);
    }

    public static ASN1TaggedObject getImplicitBaseTagged(ASN1TaggedObject aSN1TaggedObject, int i2, int i3, int i4, int i5) {
        return checkTag(aSN1TaggedObject, i2, i3).getImplicitBaseTagged(i4, i5);
    }

    public static ASN1TaggedObject getImplicitContextBaseTagged(ASN1TaggedObject aSN1TaggedObject, int i2, int i3, int i4) {
        return getImplicitBaseTagged(aSN1TaggedObject, 128, i2, i3, i4);
    }

    public static String getTagText(int i2, int i3) {
        return AbstractC0000a.m17m(i2 != 64 ? i2 != 128 ? i2 != 192 ? new StringBuilder("[UNIVERSAL ") : new StringBuilder("[PRIVATE ") : new StringBuilder("[CONTEXT ") : new StringBuilder("[APPLICATION "), i3, "]");
    }

    public static ASN1Encodable parseBaseUniversal(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2, int i3, boolean z2, int i4) {
        return checkTag(aSN1TaggedObjectParser, i2, i3).parseBaseUniversal(z2, i4);
    }

    public static ASN1Encodable parseContextBaseUniversal(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2, boolean z2, int i3) {
        return parseBaseUniversal(aSN1TaggedObjectParser, 128, i2, z2, i3);
    }

    public static ASN1Encodable parseExplicitBaseObject(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2, int i3) {
        return checkTag(aSN1TaggedObjectParser, i2, i3).parseExplicitBaseObject();
    }

    public static ASN1TaggedObjectParser parseExplicitBaseTagged(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2, int i3) {
        return checkTag(aSN1TaggedObjectParser, i2, i3).parseExplicitBaseTagged();
    }

    public static ASN1Encodable parseExplicitContextBaseObject(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2) {
        return parseExplicitBaseObject(aSN1TaggedObjectParser, 128, i2);
    }

    public static ASN1TaggedObjectParser parseExplicitContextBaseTagged(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2) {
        return parseExplicitBaseTagged(aSN1TaggedObjectParser, 128, i2);
    }

    public static ASN1TaggedObjectParser parseImplicitBaseTagged(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2, int i3, int i4, int i5) {
        return checkTag(aSN1TaggedObjectParser, i2, i3).parseImplicitBaseTagged(i4, i5);
    }

    public static ASN1TaggedObjectParser parseImplicitContextBaseTagged(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2, int i3, int i4) {
        return parseImplicitBaseTagged(aSN1TaggedObjectParser, 128, i2, i3, i4);
    }

    public static ASN1Primitive tryGetBaseUniversal(ASN1TaggedObject aSN1TaggedObject, int i2, int i3, boolean z2, int i4) {
        if (aSN1TaggedObject.hasTag(i2, i3)) {
            return aSN1TaggedObject.getBaseUniversal(z2, i4);
        }
        return null;
    }

    public static ASN1Primitive tryGetContextBaseUniversal(ASN1TaggedObject aSN1TaggedObject, int i2, boolean z2, int i3) {
        return tryGetBaseUniversal(aSN1TaggedObject, 128, i2, z2, i3);
    }

    public static ASN1Object tryGetExplicitBaseObject(ASN1TaggedObject aSN1TaggedObject, int i2, int i3) {
        if (aSN1TaggedObject.hasTag(i2, i3)) {
            return aSN1TaggedObject.getExplicitBaseObject();
        }
        return null;
    }

    public static ASN1TaggedObject tryGetExplicitBaseTagged(ASN1TaggedObject aSN1TaggedObject, int i2, int i3) {
        if (aSN1TaggedObject.hasTag(i2, i3)) {
            return aSN1TaggedObject.getExplicitBaseTagged();
        }
        return null;
    }

    public static ASN1Object tryGetExplicitContextBaseObject(ASN1TaggedObject aSN1TaggedObject, int i2) {
        return tryGetExplicitBaseObject(aSN1TaggedObject, 128, i2);
    }

    public static ASN1TaggedObject tryGetExplicitContextBaseTagged(ASN1TaggedObject aSN1TaggedObject, int i2) {
        return tryGetExplicitBaseTagged(aSN1TaggedObject, 128, i2);
    }

    public static ASN1TaggedObject tryGetImplicitBaseTagged(ASN1TaggedObject aSN1TaggedObject, int i2, int i3, int i4, int i5) {
        if (aSN1TaggedObject.hasTag(i2, i3)) {
            return aSN1TaggedObject.getImplicitBaseTagged(i4, i5);
        }
        return null;
    }

    public static ASN1TaggedObject tryGetImplicitContextBaseTagged(ASN1TaggedObject aSN1TaggedObject, int i2, int i3, int i4) {
        return tryGetImplicitBaseTagged(aSN1TaggedObject, 128, i2, i3, i4);
    }

    public static ASN1Encodable tryParseBaseUniversal(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2, int i3, boolean z2, int i4) {
        if (aSN1TaggedObjectParser.hasTag(i2, i3)) {
            return aSN1TaggedObjectParser.parseBaseUniversal(z2, i4);
        }
        return null;
    }

    public static ASN1Encodable tryParseContextBaseUniversal(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2, boolean z2, int i3) {
        return tryParseBaseUniversal(aSN1TaggedObjectParser, 128, i2, z2, i3);
    }

    public static ASN1Encodable tryParseExplicitBaseObject(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2, int i3) {
        if (aSN1TaggedObjectParser.hasTag(i2, i3)) {
            return aSN1TaggedObjectParser.parseExplicitBaseObject();
        }
        return null;
    }

    public static ASN1TaggedObjectParser tryParseExplicitBaseTagged(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2, int i3) {
        if (aSN1TaggedObjectParser.hasTag(i2, i3)) {
            return aSN1TaggedObjectParser.parseExplicitBaseTagged();
        }
        return null;
    }

    public static ASN1Encodable tryParseExplicitContextBaseObject(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2) {
        return tryParseExplicitBaseObject(aSN1TaggedObjectParser, 128, i2);
    }

    public static ASN1TaggedObjectParser tryParseExplicitContextBaseTagged(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2) {
        return tryParseExplicitBaseTagged(aSN1TaggedObjectParser, 128, i2);
    }

    public static ASN1TaggedObjectParser tryParseImplicitBaseTagged(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2, int i3, int i4, int i5) {
        if (aSN1TaggedObjectParser.hasTag(i2, i3)) {
            return aSN1TaggedObjectParser.parseImplicitBaseTagged(i4, i5);
        }
        return null;
    }

    public static ASN1TaggedObjectParser tryParseImplicitContextBaseTagged(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2, int i3, int i4) {
        return tryParseImplicitBaseTagged(aSN1TaggedObjectParser, 128, i2, i3, i4);
    }

    public static ASN1TaggedObjectParser checkTag(ASN1TaggedObjectParser aSN1TaggedObjectParser, int i2, int i3) {
        if (aSN1TaggedObjectParser.hasTag(i2, i3)) {
            return aSN1TaggedObjectParser;
        }
        throw new IllegalStateException("Expected " + getTagText(i2, i3) + " tag but found " + getTagText(aSN1TaggedObjectParser));
    }

    public static String getTagText(ASN1Tag aSN1Tag) {
        return getTagText(aSN1Tag.getTagClass(), aSN1Tag.getTagNumber());
    }

    public static String getTagText(ASN1TaggedObject aSN1TaggedObject) {
        return getTagText(aSN1TaggedObject.getTagClass(), aSN1TaggedObject.getTagNo());
    }

    public static String getTagText(ASN1TaggedObjectParser aSN1TaggedObjectParser) {
        return getTagText(aSN1TaggedObjectParser.getTagClass(), aSN1TaggedObjectParser.getTagNo());
    }
}

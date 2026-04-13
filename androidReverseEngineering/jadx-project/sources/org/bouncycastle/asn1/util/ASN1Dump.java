package org.bouncycastle.asn1.util;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1ApplicationSpecific;
import org.bouncycastle.asn1.ASN1BMPString;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1External;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1GraphicString;
import org.bouncycastle.asn1.ASN1IA5String;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1NumericString;
import org.bouncycastle.asn1.ASN1ObjectDescriptor;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1PrintableString;
import org.bouncycastle.asn1.ASN1RelativeOID;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1T61String;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1UTCTime;
import org.bouncycastle.asn1.ASN1UTF8String;
import org.bouncycastle.asn1.ASN1Util;
import org.bouncycastle.asn1.ASN1VideotexString;
import org.bouncycastle.asn1.ASN1VisibleString;
import org.bouncycastle.asn1.BEROctetString;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.BERSet;
import org.bouncycastle.asn1.BERTaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DLBitString;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ASN1Dump {
    private static final int SAMPLE_SIZE = 32;
    private static final String TAB = "    ";

    public static void _dumpAsString(String str, boolean z2, ASN1Primitive aSN1Primitive, StringBuffer stringBuffer) {
        StringBuilder m20p;
        ASN1Primitive externalContent;
        String string;
        BigInteger value;
        StringBuilder m20p2;
        String str2;
        String dumpBinaryDataAsString;
        String id;
        StringBuilder m22r;
        int length;
        String lineSeparator = Strings.lineSeparator();
        if (!(aSN1Primitive instanceof ASN1Null)) {
            int i2 = 0;
            if (aSN1Primitive instanceof ASN1Sequence) {
                stringBuffer.append(str);
                stringBuffer.append(aSN1Primitive instanceof BERSequence ? "BER Sequence" : aSN1Primitive instanceof DERSequence ? "DER Sequence" : "Sequence");
                stringBuffer.append(lineSeparator);
                ASN1Sequence aSN1Sequence = (ASN1Sequence) aSN1Primitive;
                String m30z = AbstractC0000a.m30z(str, TAB);
                int size = aSN1Sequence.size();
                while (i2 < size) {
                    _dumpAsString(m30z, z2, aSN1Sequence.getObjectAt(i2).toASN1Primitive(), stringBuffer);
                    i2++;
                }
                return;
            }
            if (aSN1Primitive instanceof ASN1Set) {
                stringBuffer.append(str);
                stringBuffer.append(aSN1Primitive instanceof BERSet ? "BER Set" : aSN1Primitive instanceof DERSet ? "DER Set" : "Set");
                stringBuffer.append(lineSeparator);
                ASN1Set aSN1Set = (ASN1Set) aSN1Primitive;
                String m30z2 = AbstractC0000a.m30z(str, TAB);
                int size2 = aSN1Set.size();
                while (i2 < size2) {
                    _dumpAsString(m30z2, z2, aSN1Set.getObjectAt(i2).toASN1Primitive(), stringBuffer);
                    i2++;
                }
                return;
            }
            if (aSN1Primitive instanceof ASN1ApplicationSpecific) {
                externalContent = ((ASN1ApplicationSpecific) aSN1Primitive).getTaggedObject();
            } else if (aSN1Primitive instanceof ASN1TaggedObject) {
                stringBuffer.append(str);
                stringBuffer.append(aSN1Primitive instanceof BERTaggedObject ? "BER Tagged " : aSN1Primitive instanceof DERTaggedObject ? "DER Tagged " : "Tagged ");
                ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Primitive;
                stringBuffer.append(ASN1Util.getTagText(aSN1TaggedObject));
                if (!aSN1TaggedObject.isExplicit()) {
                    stringBuffer.append(" IMPLICIT ");
                }
                stringBuffer.append(lineSeparator);
                str = str + TAB;
                externalContent = aSN1TaggedObject.getBaseObject().toASN1Primitive();
            } else {
                if (!(aSN1Primitive instanceof ASN1OctetString)) {
                    if (aSN1Primitive instanceof ASN1ObjectIdentifier) {
                        m20p = AbstractC0000a.m22r(str, "ObjectIdentifier(");
                        id = ((ASN1ObjectIdentifier) aSN1Primitive).getId();
                    } else {
                        if (!(aSN1Primitive instanceof ASN1RelativeOID)) {
                            if (aSN1Primitive instanceof ASN1Boolean) {
                                m20p = AbstractC0000a.m22r(str, "Boolean(");
                                m20p.append(((ASN1Boolean) aSN1Primitive).isTrue());
                            } else {
                                if (aSN1Primitive instanceof ASN1Integer) {
                                    m20p = AbstractC0000a.m22r(str, "Integer(");
                                    value = ((ASN1Integer) aSN1Primitive).getValue();
                                } else {
                                    if (!(aSN1Primitive instanceof ASN1BitString)) {
                                        if (aSN1Primitive instanceof ASN1IA5String) {
                                            m20p = AbstractC0000a.m22r(str, "IA5String(");
                                            string = ((ASN1IA5String) aSN1Primitive).getString();
                                        } else if (aSN1Primitive instanceof ASN1UTF8String) {
                                            m20p = AbstractC0000a.m22r(str, "UTF8String(");
                                            string = ((ASN1UTF8String) aSN1Primitive).getString();
                                        } else if (aSN1Primitive instanceof ASN1NumericString) {
                                            m20p = AbstractC0000a.m22r(str, "NumericString(");
                                            string = ((ASN1NumericString) aSN1Primitive).getString();
                                        } else if (aSN1Primitive instanceof ASN1PrintableString) {
                                            m20p = AbstractC0000a.m22r(str, "PrintableString(");
                                            string = ((ASN1PrintableString) aSN1Primitive).getString();
                                        } else if (aSN1Primitive instanceof ASN1VisibleString) {
                                            m20p = AbstractC0000a.m22r(str, "VisibleString(");
                                            string = ((ASN1VisibleString) aSN1Primitive).getString();
                                        } else if (aSN1Primitive instanceof ASN1BMPString) {
                                            m20p = AbstractC0000a.m22r(str, "BMPString(");
                                            string = ((ASN1BMPString) aSN1Primitive).getString();
                                        } else if (aSN1Primitive instanceof ASN1T61String) {
                                            m20p = AbstractC0000a.m22r(str, "T61String(");
                                            string = ((ASN1T61String) aSN1Primitive).getString();
                                        } else if (aSN1Primitive instanceof ASN1GraphicString) {
                                            m20p = AbstractC0000a.m22r(str, "GraphicString(");
                                            string = ((ASN1GraphicString) aSN1Primitive).getString();
                                        } else if (aSN1Primitive instanceof ASN1VideotexString) {
                                            m20p = AbstractC0000a.m22r(str, "VideotexString(");
                                            string = ((ASN1VideotexString) aSN1Primitive).getString();
                                        } else if (aSN1Primitive instanceof ASN1UTCTime) {
                                            m20p = AbstractC0000a.m22r(str, "UTCTime(");
                                            string = ((ASN1UTCTime) aSN1Primitive).getTime();
                                        } else if (aSN1Primitive instanceof ASN1GeneralizedTime) {
                                            m20p = AbstractC0000a.m22r(str, "GeneralizedTime(");
                                            string = ((ASN1GeneralizedTime) aSN1Primitive).getTime();
                                        } else if (aSN1Primitive instanceof ASN1Enumerated) {
                                            m20p = AbstractC0000a.m22r(str, "DER Enumerated(");
                                            value = ((ASN1Enumerated) aSN1Primitive).getValue();
                                        } else if (aSN1Primitive instanceof ASN1ObjectDescriptor) {
                                            m20p = AbstractC0000a.m22r(str, "ObjectDescriptor(");
                                            string = ((ASN1ObjectDescriptor) aSN1Primitive).getBaseGraphicString().getString();
                                        } else {
                                            if (!(aSN1Primitive instanceof ASN1External)) {
                                                m20p = AbstractC0000a.m20p(str);
                                                m20p.append(aSN1Primitive.toString());
                                                m20p.append(lineSeparator);
                                                dumpBinaryDataAsString = m20p.toString();
                                                stringBuffer.append(dumpBinaryDataAsString);
                                                return;
                                            }
                                            ASN1External aSN1External = (ASN1External) aSN1Primitive;
                                            stringBuffer.append(str + "External " + lineSeparator);
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(str);
                                            sb.append(TAB);
                                            str = sb.toString();
                                            if (aSN1External.getDirectReference() != null) {
                                                StringBuilder m22r2 = AbstractC0000a.m22r(str, "Direct Reference: ");
                                                m22r2.append(aSN1External.getDirectReference().getId());
                                                m22r2.append(lineSeparator);
                                                stringBuffer.append(m22r2.toString());
                                            }
                                            if (aSN1External.getIndirectReference() != null) {
                                                StringBuilder m22r3 = AbstractC0000a.m22r(str, "Indirect Reference: ");
                                                m22r3.append(aSN1External.getIndirectReference().toString());
                                                m22r3.append(lineSeparator);
                                                stringBuffer.append(m22r3.toString());
                                            }
                                            if (aSN1External.getDataValueDescriptor() != null) {
                                                _dumpAsString(str, z2, aSN1External.getDataValueDescriptor(), stringBuffer);
                                            }
                                            StringBuilder m22r4 = AbstractC0000a.m22r(str, "Encoding: ");
                                            m22r4.append(aSN1External.getEncoding());
                                            m22r4.append(lineSeparator);
                                            stringBuffer.append(m22r4.toString());
                                            externalContent = aSN1External.getExternalContent();
                                        }
                                        m20p.append(string);
                                        m20p.append(") ");
                                        m20p.append(lineSeparator);
                                        dumpBinaryDataAsString = m20p.toString();
                                        stringBuffer.append(dumpBinaryDataAsString);
                                        return;
                                    }
                                    ASN1BitString aSN1BitString = (ASN1BitString) aSN1Primitive;
                                    byte[] bytes = aSN1BitString.getBytes();
                                    int padBits = aSN1BitString.getPadBits();
                                    if (aSN1BitString instanceof DERBitString) {
                                        m20p2 = AbstractC0000a.m20p(str);
                                        str2 = "DER Bit String[";
                                    } else if (aSN1BitString instanceof DLBitString) {
                                        m20p2 = AbstractC0000a.m20p(str);
                                        str2 = "DL Bit String[";
                                    } else {
                                        m20p2 = AbstractC0000a.m20p(str);
                                        str2 = "BER Bit String[";
                                    }
                                    m20p2.append(str2);
                                    m20p2.append(bytes.length);
                                    m20p2.append(", ");
                                    m20p2.append(padBits);
                                    m20p2.append("] ");
                                    stringBuffer.append(m20p2.toString());
                                    if (z2) {
                                        dumpBinaryDataAsString = dumpBinaryDataAsString(str, bytes);
                                        stringBuffer.append(dumpBinaryDataAsString);
                                        return;
                                    }
                                }
                                m20p.append(value);
                            }
                            m20p.append(")");
                            m20p.append(lineSeparator);
                            dumpBinaryDataAsString = m20p.toString();
                            stringBuffer.append(dumpBinaryDataAsString);
                            return;
                        }
                        m20p = AbstractC0000a.m22r(str, "RelativeOID(");
                        id = ((ASN1RelativeOID) aSN1Primitive).getId();
                    }
                    m20p.append(id);
                    m20p.append(")");
                    m20p.append(lineSeparator);
                    dumpBinaryDataAsString = m20p.toString();
                    stringBuffer.append(dumpBinaryDataAsString);
                    return;
                }
                ASN1OctetString aSN1OctetString = (ASN1OctetString) aSN1Primitive;
                if (aSN1Primitive instanceof BEROctetString) {
                    m22r = AbstractC0000a.m22r(str, "BER Constructed Octet String[");
                    length = aSN1OctetString.getOctets().length;
                } else {
                    m22r = AbstractC0000a.m22r(str, "DER Octet String[");
                    length = aSN1OctetString.getOctets().length;
                }
                m22r.append(length);
                m22r.append("] ");
                stringBuffer.append(m22r.toString());
                if (z2) {
                    dumpBinaryDataAsString = dumpBinaryDataAsString(str, aSN1OctetString.getOctets());
                    stringBuffer.append(dumpBinaryDataAsString);
                    return;
                }
            }
            _dumpAsString(str, z2, externalContent, stringBuffer);
            return;
        }
        stringBuffer.append(str);
        stringBuffer.append("NULL");
        stringBuffer.append(lineSeparator);
    }

    private static String calculateAscString(byte[] bArr, int i2, int i3) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i4 = i2; i4 != i2 + i3; i4++) {
            byte b = bArr[i4];
            if (b >= 32 && b <= 126) {
                stringBuffer.append((char) b);
            }
        }
        return stringBuffer.toString();
    }

    public static String dumpAsString(Object obj) {
        return dumpAsString(obj, false);
    }

    private static String dumpBinaryDataAsString(String str, byte[] bArr) {
        String calculateAscString;
        String lineSeparator = Strings.lineSeparator();
        StringBuffer stringBuffer = new StringBuffer();
        String str2 = str + TAB;
        stringBuffer.append(lineSeparator);
        for (int i2 = 0; i2 < bArr.length; i2 += 32) {
            int length = bArr.length - i2;
            stringBuffer.append(str2);
            if (length > 32) {
                stringBuffer.append(Strings.fromByteArray(Hex.encode(bArr, i2, 32)));
                stringBuffer.append(TAB);
                calculateAscString = calculateAscString(bArr, i2, 32);
            } else {
                stringBuffer.append(Strings.fromByteArray(Hex.encode(bArr, i2, bArr.length - i2)));
                for (int length2 = bArr.length - i2; length2 != 32; length2++) {
                    stringBuffer.append("  ");
                }
                stringBuffer.append(TAB);
                calculateAscString = calculateAscString(bArr, i2, bArr.length - i2);
            }
            stringBuffer.append(calculateAscString);
            stringBuffer.append(lineSeparator);
        }
        return stringBuffer.toString();
    }

    public static String dumpAsString(Object obj, boolean z2) {
        ASN1Primitive aSN1Primitive;
        if (obj instanceof ASN1Primitive) {
            aSN1Primitive = (ASN1Primitive) obj;
        } else {
            if (!(obj instanceof ASN1Encodable)) {
                return "unknown object type " + obj.toString();
            }
            aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
        }
        StringBuffer stringBuffer = new StringBuffer();
        _dumpAsString(BuildConfig.FLAVOR, z2, aSN1Primitive, stringBuffer);
        return stringBuffer.toString();
    }
}

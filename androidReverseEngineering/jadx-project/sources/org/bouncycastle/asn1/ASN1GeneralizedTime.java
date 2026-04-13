package org.bouncycastle.asn1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ASN1GeneralizedTime extends ASN1Primitive {
    static final ASN1UniversalType TYPE = new ASN1UniversalType(ASN1GeneralizedTime.class, 24) { // from class: org.bouncycastle.asn1.ASN1GeneralizedTime.1
        @Override // org.bouncycastle.asn1.ASN1UniversalType
        public ASN1Primitive fromImplicitPrimitive(DEROctetString dEROctetString) {
            return ASN1GeneralizedTime.createPrimitive(dEROctetString.getOctets());
        }
    };
    final byte[] contents;

    public ASN1GeneralizedTime(String str) {
        this.contents = Strings.toByteArray(str);
        try {
            getDate();
        } catch (ParseException e2) {
            throw new IllegalArgumentException("invalid date string: " + e2.getMessage());
        }
    }

    private SimpleDateFormat calculateGMTDateFormat() {
        SimpleDateFormat simpleDateFormat = hasFractionalSeconds() ? new SimpleDateFormat("yyyyMMddHHmmss.SSSz") : hasSeconds() ? new SimpleDateFormat("yyyyMMddHHmmssz") : hasMinutes() ? new SimpleDateFormat("yyyyMMddHHmmz") : new SimpleDateFormat("yyyyMMddHHz");
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        return simpleDateFormat;
    }

    private String calculateGMTOffset(String str) {
        String str2;
        TimeZone timeZone = TimeZone.getDefault();
        int rawOffset = timeZone.getRawOffset();
        if (rawOffset < 0) {
            rawOffset = -rawOffset;
            str2 = "-";
        } else {
            str2 = "+";
        }
        int i2 = rawOffset / 3600000;
        int i3 = (rawOffset - (((i2 * 60) * 60) * 1000)) / 60000;
        try {
            if (timeZone.useDaylightTime()) {
                if (hasFractionalSeconds()) {
                    str = pruneFractionalSeconds(str);
                }
                if (timeZone.inDaylightTime(calculateGMTDateFormat().parse(str + "GMT" + str2 + convert(i2) + ":" + convert(i3)))) {
                    i2 += str2.equals("+") ? 1 : -1;
                }
            }
        } catch (ParseException unused) {
        }
        return "GMT" + str2 + convert(i2) + ":" + convert(i3);
    }

    private String convert(int i2) {
        return i2 < 10 ? AbstractC0000a.m11g("0", i2) : Integer.toString(i2);
    }

    public static ASN1GeneralizedTime createPrimitive(byte[] bArr) {
        return new ASN1GeneralizedTime(bArr);
    }

    public static ASN1GeneralizedTime getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1GeneralizedTime)) {
            return (ASN1GeneralizedTime) obj;
        }
        if (obj instanceof ASN1Encodable) {
            ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof ASN1GeneralizedTime) {
                return (ASN1GeneralizedTime) aSN1Primitive;
            }
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
        }
        try {
            return (ASN1GeneralizedTime) TYPE.fromByteArray((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    private boolean isDigit(int i2) {
        byte b;
        byte[] bArr = this.contents;
        return bArr.length > i2 && (b = bArr[i2]) >= 48 && b <= 57;
    }

    private String pruneFractionalSeconds(String str) {
        String str2;
        StringBuilder sb;
        char charAt;
        String substring = str.substring(14);
        int i2 = 1;
        while (i2 < substring.length() && '0' <= (charAt = substring.charAt(i2)) && charAt <= '9') {
            i2++;
        }
        int i3 = i2 - 1;
        if (i3 > 3) {
            str2 = substring.substring(0, 4) + substring.substring(i2);
            sb = new StringBuilder();
        } else if (i3 == 1) {
            str2 = substring.substring(0, i2) + "00" + substring.substring(i2);
            sb = new StringBuilder();
        } else {
            if (i3 != 2) {
                return str;
            }
            str2 = substring.substring(0, i2) + "0" + substring.substring(i2);
            sb = new StringBuilder();
        }
        sb.append(str.substring(0, 14));
        sb.append(str2);
        return sb.toString();
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive instanceof ASN1GeneralizedTime) {
            return Arrays.areEqual(this.contents, ((ASN1GeneralizedTime) aSN1Primitive).contents);
        }
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        aSN1OutputStream.writeEncodingDL(z2, 24, this.contents);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final boolean encodeConstructed() {
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public int encodedLength(boolean z2) {
        return ASN1OutputStream.getLengthOfEncodingDL(z2, this.contents.length);
    }

    public Date getDate() {
        SimpleDateFormat calculateGMTDateFormat;
        String fromByteArray = Strings.fromByteArray(this.contents);
        if (fromByteArray.endsWith("Z")) {
            calculateGMTDateFormat = hasFractionalSeconds() ? new SimpleDateFormat("yyyyMMddHHmmss.SSS'Z'") : hasSeconds() ? new SimpleDateFormat("yyyyMMddHHmmss'Z'") : hasMinutes() ? new SimpleDateFormat("yyyyMMddHHmm'Z'") : new SimpleDateFormat("yyyyMMddHH'Z'");
            calculateGMTDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        } else if (fromByteArray.indexOf(45) > 0 || fromByteArray.indexOf(43) > 0) {
            fromByteArray = getTime();
            calculateGMTDateFormat = calculateGMTDateFormat();
        } else {
            calculateGMTDateFormat = hasFractionalSeconds() ? new SimpleDateFormat("yyyyMMddHHmmss.SSS") : hasSeconds() ? new SimpleDateFormat("yyyyMMddHHmmss") : hasMinutes() ? new SimpleDateFormat("yyyyMMddHHmm") : new SimpleDateFormat("yyyyMMddHH");
            calculateGMTDateFormat.setTimeZone(new SimpleTimeZone(0, TimeZone.getDefault().getID()));
        }
        if (hasFractionalSeconds()) {
            fromByteArray = pruneFractionalSeconds(fromByteArray);
        }
        return DateUtil.epochAdjust(calculateGMTDateFormat.parse(fromByteArray));
    }

    public String getTime() {
        String fromByteArray = Strings.fromByteArray(this.contents);
        if (fromByteArray.charAt(fromByteArray.length() - 1) == 'Z') {
            return fromByteArray.substring(0, fromByteArray.length() - 1) + "GMT+00:00";
        }
        int length = fromByteArray.length() - 6;
        char charAt = fromByteArray.charAt(length);
        if ((charAt == '-' || charAt == '+') && fromByteArray.indexOf("GMT") == length - 3) {
            return fromByteArray;
        }
        int length2 = fromByteArray.length() - 5;
        char charAt2 = fromByteArray.charAt(length2);
        if (charAt2 == '-' || charAt2 == '+') {
            StringBuilder sb = new StringBuilder();
            sb.append(fromByteArray.substring(0, length2));
            sb.append("GMT");
            int i2 = length2 + 3;
            sb.append(fromByteArray.substring(length2, i2));
            sb.append(":");
            sb.append(fromByteArray.substring(i2));
            return sb.toString();
        }
        int length3 = fromByteArray.length() - 3;
        char charAt3 = fromByteArray.charAt(length3);
        if (charAt3 != '-' && charAt3 != '+') {
            StringBuilder m20p = AbstractC0000a.m20p(fromByteArray);
            m20p.append(calculateGMTOffset(fromByteArray));
            return m20p.toString();
        }
        return fromByteArray.substring(0, length3) + "GMT" + fromByteArray.substring(length3) + ":00";
    }

    public String getTimeString() {
        return Strings.fromByteArray(this.contents);
    }

    public boolean hasFractionalSeconds() {
        int i2 = 0;
        while (true) {
            byte[] bArr = this.contents;
            if (i2 == bArr.length) {
                return false;
            }
            if (bArr[i2] == 46 && i2 == 14) {
                return true;
            }
            i2++;
        }
    }

    public boolean hasMinutes() {
        return isDigit(10) && isDigit(11);
    }

    public boolean hasSeconds() {
        return isDigit(12) && isDigit(13);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive, org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return Arrays.hashCode(this.contents);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDERObject() {
        return new DERGeneralizedTime(this.contents);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDLObject() {
        return new DERGeneralizedTime(this.contents);
    }

    public ASN1GeneralizedTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss'Z'", DateUtil.EN_Locale);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.contents = Strings.toByteArray(simpleDateFormat.format(date));
    }

    public ASN1GeneralizedTime(Date date, Locale locale) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss'Z'", locale);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.contents = Strings.toByteArray(simpleDateFormat.format(date));
    }

    public ASN1GeneralizedTime(byte[] bArr) {
        if (bArr.length < 4) {
            throw new IllegalArgumentException("GeneralizedTime string too short");
        }
        this.contents = bArr;
        if (!isDigit(0) || !isDigit(1) || !isDigit(2) || !isDigit(3)) {
            throw new IllegalArgumentException("illegal characters in GeneralizedTime string");
        }
    }

    public static ASN1GeneralizedTime getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return (ASN1GeneralizedTime) TYPE.getContextInstance(aSN1TaggedObject, z2);
    }
}

package org.bouncycastle.oer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class OERDefinition {
    private static final BigInteger[] uIntMax = {new BigInteger("256"), new BigInteger("65536"), new BigInteger("4294967296"), new BigInteger("18446744073709551616")};
    private static final BigInteger[][] sIntRange = {new BigInteger[]{new BigInteger("-128"), new BigInteger("127")}, new BigInteger[]{new BigInteger("-32768"), new BigInteger("32767")}, new BigInteger[]{new BigInteger("-2147483648"), new BigInteger("2147483647")}, new BigInteger[]{new BigInteger("-9223372036854775808"), new BigInteger("9223372036854775807")}};

    public enum BaseType {
        SEQ,
        SEQ_OF,
        CHOICE,
        ENUM,
        INT,
        OCTET_STRING,
        UTF8_STRING,
        BIT_STRING,
        NULL,
        EXTENSION,
        ENUM_ITEM,
        BOOLEAN,
        IS0646String,
        PrintableString,
        NumericString,
        BMPString,
        UniversalString,
        IA5String,
        VisibleString
    }

    public static class Builder {
        protected final BaseType baseType;
        protected ASN1Encodable defaultValue;
        protected BigInteger enumValue;
        protected String label;
        protected BigInteger lowerBound;
        protected Builder placeholderValue;
        protected BigInteger upperBound;
        protected ArrayList<Builder> children = new ArrayList<>();
        protected boolean explicit = false;

        public Builder(BaseType baseType) {
            this.baseType = baseType;
        }

        private Builder wrap(boolean z2, Object obj) {
            if (obj instanceof Builder) {
                return ((Builder) obj).explicit(z2);
            }
            if (obj instanceof BaseType) {
                return new Builder((BaseType) obj).explicit(z2);
            }
            throw new IllegalStateException("Unable to wrap item in builder");
        }

        public Element build() {
            ArrayList arrayList = new ArrayList();
            boolean z2 = false;
            if (this.baseType == BaseType.ENUM) {
                HashSet hashSet = new HashSet();
                int i2 = 0;
                for (int i3 = 0; i3 < this.children.size(); i3++) {
                    Builder builder = this.children.get(i3);
                    if (builder.enumValue == null) {
                        builder.enumValue = BigInteger.valueOf(i2);
                        i2++;
                    }
                    if (hashSet.contains(builder.enumValue)) {
                        throw new IllegalStateException(AbstractC0000a.m11g("duplicate enum value at index ", i3));
                    }
                    hashSet.add(builder.enumValue);
                }
            }
            Iterator<Builder> it = this.children.iterator();
            boolean z3 = false;
            while (it.hasNext()) {
                Builder next = it.next();
                if (!z3 && next.baseType == BaseType.EXTENSION) {
                    if (!next.children.isEmpty() || this.baseType == BaseType.CHOICE) {
                        z3 = true;
                    } else {
                        z3 = true;
                    }
                }
                arrayList.add(next.build());
            }
            BaseType baseType = this.baseType;
            ASN1Encodable aSN1Encodable = this.defaultValue;
            if (aSN1Encodable == null && this.explicit) {
                z2 = true;
            }
            return new Element(baseType, arrayList, z2, this.label, this.lowerBound, this.upperBound, z3, this.enumValue, aSN1Encodable);
        }

        public Builder copy() {
            Builder builder = new Builder(this.baseType);
            Iterator<Builder> it = this.children.iterator();
            while (it.hasNext()) {
                builder.children.add(it.next().copy());
            }
            builder.explicit = this.explicit;
            builder.label = this.label;
            builder.upperBound = this.upperBound;
            builder.lowerBound = this.lowerBound;
            builder.defaultValue = this.defaultValue;
            builder.enumValue = this.enumValue;
            return builder;
        }

        public Builder defaultValue(ASN1Encodable aSN1Encodable) {
            Builder copy = copy();
            copy.defaultValue = aSN1Encodable;
            return copy;
        }

        public Builder enumValue(BigInteger bigInteger) {
            Builder copy = copy();
            this.enumValue = bigInteger;
            return copy;
        }

        public Builder explicit(boolean z2) {
            Builder copy = copy();
            copy.explicit = z2;
            return copy;
        }

        public Builder fixedSize(long j2) {
            Builder copy = copy();
            copy.upperBound = BigInteger.valueOf(j2);
            copy.lowerBound = BigInteger.valueOf(j2);
            return copy;
        }

        public Builder items(Object... objArr) {
            Builder copy = copy();
            for (int i2 = 0; i2 != objArr.length; i2++) {
                Object obj = objArr[i2];
                if (obj instanceof OptionalList) {
                    Iterator it = ((List) obj).iterator();
                    while (it.hasNext()) {
                        copy.children.add(wrap(false, it.next()));
                    }
                } else if (obj.getClass().isArray()) {
                    items((Object[]) obj);
                } else {
                    copy.children.add(wrap(true, obj));
                }
            }
            return copy;
        }

        public Builder label(String str) {
            Builder copy = copy();
            if (str != null) {
                copy.label = str;
            }
            copy.explicit = this.explicit;
            return copy;
        }

        public Builder labelPrefix(String str) {
            Builder copy = copy();
            StringBuilder m22r = AbstractC0000a.m22r(str, " ");
            m22r.append(this.label);
            copy.label = m22r.toString();
            return copy;
        }

        public Builder range(long j2, long j3, ASN1Encodable aSN1Encodable) {
            Builder copy = copy();
            copy.lowerBound = BigInteger.valueOf(j2);
            copy.upperBound = BigInteger.valueOf(j3);
            copy.defaultValue = aSN1Encodable;
            return copy;
        }

        public Builder rangeToMAXFrom(long j2) {
            Builder copy = copy();
            copy.lowerBound = BigInteger.valueOf(j2);
            copy.upperBound = null;
            return copy;
        }

        public Builder rangeZeroTo(long j2) {
            Builder copy = copy();
            copy.upperBound = BigInteger.valueOf(j2);
            copy.lowerBound = BigInteger.ZERO;
            return copy;
        }

        public Builder unbounded() {
            Builder copy = copy();
            copy.lowerBound = null;
            copy.upperBound = null;
            return copy;
        }

        public Builder range(BigInteger bigInteger, BigInteger bigInteger2) {
            Builder copy = copy();
            copy.lowerBound = bigInteger;
            copy.upperBound = bigInteger2;
            return copy;
        }
    }

    public static class Element {
        public final BaseType baseType;
        public final List<Element> children;
        public final ASN1Encodable defaultValue;
        public final BigInteger enumValue;
        public final boolean explicit;
        public final boolean extensionsInDefinition;
        public final String label;
        public final BigInteger lowerBound;
        private List<Element> optionalChildrenInOrder;
        public final BigInteger upperBound;

        public Element(BaseType baseType, List<Element> list, boolean z2, String str, BigInteger bigInteger, BigInteger bigInteger2, boolean z3, BigInteger bigInteger3, ASN1Encodable aSN1Encodable) {
            this.baseType = baseType;
            this.children = list;
            this.explicit = z2;
            this.label = str;
            this.lowerBound = bigInteger;
            this.upperBound = bigInteger2;
            this.extensionsInDefinition = z3;
            this.enumValue = bigInteger3;
            this.defaultValue = aSN1Encodable;
        }

        public String appendLabel(String str) {
            StringBuilder sb = new StringBuilder("[");
            String str2 = this.label;
            String str3 = BuildConfig.FLAVOR;
            if (str2 == null) {
                str2 = BuildConfig.FLAVOR;
            }
            sb.append(str2);
            if (this.explicit) {
                str3 = " (E)";
            }
            sb.append(str3);
            sb.append("] ");
            sb.append(str);
            return sb.toString();
        }

        public boolean canBeNegative() {
            BigInteger bigInteger = this.lowerBound;
            return bigInteger != null && BigInteger.ZERO.compareTo(bigInteger) > 0;
        }

        public ASN1Encodable getDefaultValue() {
            return this.defaultValue;
        }

        public Element getFirstChid() {
            return this.children.get(0);
        }

        public boolean hasDefaultChildren() {
            Iterator<Element> it = this.children.iterator();
            while (it.hasNext()) {
                if (it.next().defaultValue != null) {
                    return true;
                }
            }
            return false;
        }

        public boolean hasPopulatedExtension() {
            Iterator<Element> it = this.children.iterator();
            while (it.hasNext()) {
                if (it.next().baseType == BaseType.EXTENSION) {
                    return true;
                }
            }
            return false;
        }

        public int intBytesForRange() {
            BigInteger bigInteger = this.lowerBound;
            if (bigInteger != null && this.upperBound != null) {
                int i2 = 1;
                if (BigInteger.ZERO.equals(bigInteger)) {
                    int i3 = 0;
                    while (i3 < OERDefinition.uIntMax.length) {
                        if (this.upperBound.compareTo(OERDefinition.uIntMax[i3]) < 0) {
                            return i2;
                        }
                        i3++;
                        i2 *= 2;
                    }
                } else {
                    int i4 = 0;
                    int i5 = 1;
                    while (i4 < OERDefinition.sIntRange.length) {
                        if (this.lowerBound.compareTo(OERDefinition.sIntRange[i4][0]) >= 0 && this.upperBound.compareTo(OERDefinition.sIntRange[i4][1]) < 0) {
                            return -i5;
                        }
                        i4++;
                        i5 *= 2;
                    }
                }
            }
            return 0;
        }

        public boolean isFixedLength() {
            BigInteger bigInteger = this.lowerBound;
            return bigInteger != null && bigInteger.equals(this.upperBound);
        }

        public boolean isLowerRangeZero() {
            return BigInteger.ZERO.equals(this.lowerBound);
        }

        public boolean isUnbounded() {
            return this.upperBound == null && this.lowerBound == null;
        }

        public boolean isUnsignedWithRange() {
            BigInteger bigInteger;
            return isLowerRangeZero() && (bigInteger = this.upperBound) != null && BigInteger.ZERO.compareTo(bigInteger) < 0;
        }

        public List<Element> optionalOrDefaultChildrenInOrder() {
            List<Element> list;
            synchronized (this) {
                if (this.optionalChildrenInOrder == null) {
                    ArrayList arrayList = new ArrayList();
                    for (Element element : this.children) {
                        if (!element.explicit || element.getDefaultValue() != null) {
                            arrayList.add(element);
                        }
                    }
                    this.optionalChildrenInOrder = Collections.unmodifiableList(arrayList);
                }
                list = this.optionalChildrenInOrder;
            }
            return list;
        }

        public String rangeExpression() {
            StringBuilder sb = new StringBuilder("(");
            BigInteger bigInteger = this.lowerBound;
            sb.append(bigInteger != null ? bigInteger.toString() : "MIN");
            sb.append(" ... ");
            BigInteger bigInteger2 = this.upperBound;
            return AbstractC0000a.m18n(sb, bigInteger2 != null ? bigInteger2.toString() : "MAX", ")");
        }
    }

    public static class MutableBuilder extends Builder {
        private boolean frozen;

        public MutableBuilder(BaseType baseType) {
            super(baseType);
            this.frozen = false;
        }

        public void addItemsAndFreeze(Builder... builderArr) {
            if (this.frozen) {
                throw new IllegalStateException("build cannot be modified and must be copied only");
            }
            for (int i2 = 0; i2 != builderArr.length; i2++) {
                this.children.add(builderArr[i2]);
            }
            this.frozen = true;
        }
    }

    public static class OptionalList extends ArrayList<Object> {
        public OptionalList(List<Object> list) {
            addAll(list);
        }
    }

    public static Builder bitString(long j2) {
        return new Builder(BaseType.BIT_STRING).fixedSize(j2);
    }

    public static Builder choice(Object... objArr) {
        return new Builder(BaseType.CHOICE).items(objArr);
    }

    public static Builder enumItem(String str) {
        return new Builder(BaseType.ENUM_ITEM).label(str);
    }

    public static Builder enumeration(Object... objArr) {
        return new Builder(BaseType.ENUM).items(objArr);
    }

    public static Builder extension() {
        return new Builder(BaseType.EXTENSION).label("extension");
    }

    public static Builder integer() {
        return new Builder(BaseType.INT);
    }

    public static Builder nullValue() {
        return new Builder(BaseType.NULL);
    }

    public static Builder octets() {
        return new Builder(BaseType.OCTET_STRING).unbounded();
    }

    public static Builder opaque() {
        return new Builder(BaseType.OCTET_STRING).unbounded();
    }

    public static List<Object> optional(Object... objArr) {
        return new OptionalList(Arrays.asList(objArr));
    }

    public static Builder placeholder() {
        return new Builder(null);
    }

    public static Builder seq() {
        return new Builder(BaseType.SEQ);
    }

    public static Builder seqof(Object... objArr) {
        return new Builder(BaseType.SEQ_OF).items(objArr);
    }

    public static Builder utf8String() {
        return new Builder(BaseType.UTF8_STRING);
    }

    public static Builder enumItem(String str, BigInteger bigInteger) {
        return new Builder(BaseType.ENUM_ITEM).enumValue(bigInteger).label(str);
    }

    public static Builder integer(long j2) {
        return new Builder(BaseType.INT).defaultValue(new ASN1Integer(j2));
    }

    public static Builder octets(int i2) {
        return new Builder(BaseType.OCTET_STRING).fixedSize(i2);
    }

    public static Builder seq(Object... objArr) {
        return new Builder(BaseType.SEQ).items(objArr);
    }

    public static Builder utf8String(int i2) {
        return new Builder(BaseType.UTF8_STRING).rangeToMAXFrom(i2);
    }

    public static Builder integer(long j2, long j3) {
        return new Builder(BaseType.INT).range(BigInteger.valueOf(j2), BigInteger.valueOf(j3));
    }

    public static Builder octets(int i2, int i3) {
        return new Builder(BaseType.OCTET_STRING).range(BigInteger.valueOf(i2), BigInteger.valueOf(i3));
    }

    public static Builder utf8String(int i2, int i3) {
        return new Builder(BaseType.UTF8_STRING).range(BigInteger.valueOf(i2), BigInteger.valueOf(i3));
    }

    public static Builder integer(long j2, long j3, ASN1Encodable aSN1Encodable) {
        return new Builder(BaseType.INT).range(j2, j3, aSN1Encodable);
    }

    public static Builder integer(BigInteger bigInteger, BigInteger bigInteger2) {
        return new Builder(BaseType.INT).range(bigInteger, bigInteger2);
    }
}

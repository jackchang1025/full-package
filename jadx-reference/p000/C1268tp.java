package p000;

import org.bouncycastle.util.Strings;

/* renamed from: tp */
/* loaded from: classes2.dex */
public class C1268tp extends AbstractC0158c3 implements InterfaceC0010a9 {
    public static final int FULL_NAME = 0;
    public static final int NAME_RELATIVE_TO_CRL_ISSUER = 1;
    InterfaceC0117b0 name;
    int type;

    public C1268tp(int i, InterfaceC0117b0 interfaceC0117b0) {
        this.type = i;
        this.name = interfaceC0117b0;
    }

    private void appendObject(StringBuffer stringBuffer, String str, String str2, String str3) {
        stringBuffer.append("    ");
        stringBuffer.append(str2);
        stringBuffer.append(":");
        stringBuffer.append(str);
        stringBuffer.append("    ");
        stringBuffer.append("    ");
        stringBuffer.append(str3);
        stringBuffer.append(str);
    }

    public static C1268tp getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0439e0.getInstance(abstractC0439e0, true));
    }

    public InterfaceC0117b0 getName() {
        return this.name;
    }

    public int getType() {
        return this.type;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        return new C1067pf(false, this.type, this.name);
    }

    public String toString() {
        String string;
        String str;
        String strLineSeparator = Strings.lineSeparator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("DistributionPointName: [");
        stringBuffer.append(strLineSeparator);
        if (this.type == 0) {
            string = this.name.toString();
            str = "fullName";
        } else {
            string = this.name.toString();
            str = "nameRelativeToCRLIssuer";
        }
        appendObject(stringBuffer, strLineSeparator, str, string);
        stringBuffer.append("]");
        stringBuffer.append(strLineSeparator);
        return stringBuffer.toString();
    }

    public C1268tp(AbstractC0439e0 abstractC0439e0) {
        int tagNo = abstractC0439e0.getTagNo();
        this.type = tagNo;
        this.name = tagNo == 0 ? r20.getInstance(abstractC0439e0, false) : AbstractC0402d4.getInstance(abstractC0439e0, false);
    }

    public static C1268tp getInstance(Object obj) {
        if (obj == null || (obj instanceof C1268tp)) {
            return (C1268tp) obj;
        }
        if (obj instanceof AbstractC0439e0) {
            return new C1268tp((AbstractC0439e0) obj);
        }
        throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "unknown object in factory: "));
    }

    public C1268tp(r20 r20Var) {
        this(0, r20Var);
    }
}

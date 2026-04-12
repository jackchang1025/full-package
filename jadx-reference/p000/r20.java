package p000;

import org.bouncycastle.util.Strings;

/* loaded from: classes2.dex */
public class r20 extends AbstractC0158c3 {
    private final q20[] names;

    private r20(AbstractC0400d2 abstractC0400d2) {
        this.names = new q20[abstractC0400d2.size()];
        for (int i = 0; i != abstractC0400d2.size(); i++) {
            this.names[i] = q20.getInstance(abstractC0400d2.getObjectAt(i));
        }
    }

    private static q20[] copy(q20[] q20VarArr) {
        q20[] q20VarArr2 = new q20[q20VarArr.length];
        System.arraycopy(q20VarArr, 0, q20VarArr2, 0, q20VarArr.length);
        return q20VarArr2;
    }

    public static r20 fromExtensions(C1454ye c1454ye, C0160c5 c0160c5) {
        return getInstance(C1454ye.getExtensionParsedValue(c1454ye, c0160c5));
    }

    public static r20 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return new r20(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    public q20[] getNames() {
        return copy(this.names);
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        return new C1064pc(this.names);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("GeneralNames:");
        String strLineSeparator = Strings.lineSeparator();
        stringBuffer.append(strLineSeparator);
        for (int i = 0; i != this.names.length; i++) {
            stringBuffer.append("    ");
            stringBuffer.append(this.names[i]);
            stringBuffer.append(strLineSeparator);
        }
        return stringBuffer.toString();
    }

    public r20(q20 q20Var) {
        this.names = new q20[]{q20Var};
    }

    public static r20 getInstance(Object obj) {
        if (obj instanceof r20) {
            return (r20) obj;
        }
        if (obj != null) {
            return new r20(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public r20(q20[] q20VarArr) {
        this.names = copy(q20VarArr);
    }
}

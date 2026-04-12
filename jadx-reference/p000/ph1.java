package p000;

import java.io.IOException;

/* loaded from: classes2.dex */
public class ph1 extends uh1 {
    @Override // p000.uh1
    public AbstractC0164c9 getConvertedValue(C0160c5 c0160c5, String str) {
        if (str.length() == 0 || str.charAt(0) != '#') {
            if (str.length() != 0 && str.charAt(0) == '\\') {
                str = str.substring(1);
            }
            return (c0160c5.equals((AbstractC0164c9) th1.EmailAddress) || c0160c5.equals((AbstractC0164c9) th1.f60225DC)) ? new C1045ov(str) : c0160c5.equals((AbstractC0164c9) th1.DATE_OF_BIRTH) ? new C1043ot(str) : (c0160c5.equals((AbstractC0164c9) th1.f60223C) || c0160c5.equals((AbstractC0164c9) th1.f60230SN) || c0160c5.equals((AbstractC0164c9) th1.DN_QUALIFIER) || c0160c5.equals((AbstractC0164c9) th1.TELEPHONE_NUMBER)) ? new C1063pb(str) : new C1069ph(str);
        }
        try {
            return convertHexEncoded(str, 1);
        } catch (IOException unused) {
            throw new RuntimeException("can't recode value for oid " + c0160c5.getId());
        }
    }
}

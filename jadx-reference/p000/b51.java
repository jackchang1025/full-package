package p000;

import java.util.Locale;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class b51 {

    /* renamed from: a7 */
    public static final a51 f45714a7 = new a51(null);

    /* renamed from: a0 */
    public final String f45715a0;

    /* renamed from: a1 */
    public final String f45716a1;

    /* renamed from: a2 */
    public final boolean f45717a2;

    /* renamed from: a3 */
    public final int f45718a3;

    /* renamed from: a4 */
    public final String f45719a4;

    /* renamed from: a5 */
    public final int f45720a5;

    /* renamed from: a6 */
    public final int f45721a6;

    public b51(String str, String str2, boolean z, int i, String str3, int i2) {
        this.f45715a0 = str;
        this.f45716a1 = str2;
        this.f45717a2 = z;
        this.f45718a3 = i;
        this.f45719a4 = str3;
        this.f45720a5 = i2;
        Locale locale = Locale.US;
        t60.m214694b5(locale, "US");
        String upperCase = str2.toUpperCase(locale);
        t60.m214694b5(upperCase, "this as java.lang.String).toUpperCase(locale)");
        this.f45721a6 = AbstractC0779a1.m213652a5(upperCase, "INT", false) ? 3 : (AbstractC0779a1.m213652a5(upperCase, "CHAR", false) || AbstractC0779a1.m213652a5(upperCase, "CLOB", false) || AbstractC0779a1.m213652a5(upperCase, "TEXT", false)) ? 2 : AbstractC0779a1.m213652a5(upperCase, "BLOB", false) ? 5 : (AbstractC0779a1.m213652a5(upperCase, "REAL", false) || AbstractC0779a1.m213652a5(upperCase, "FLOA", false) || AbstractC0779a1.m213652a5(upperCase, "DOUB", false)) ? 4 : 1;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof b51)) {
                return false;
            }
            b51 b51Var = (b51) obj;
            if (this.f45718a3 != b51Var.f45718a3) {
                return false;
            }
            int i = b51Var.f45720a5;
            String str = b51Var.f45719a4;
            if (!this.f45715a0.equals(b51Var.f45715a0) || this.f45717a2 != b51Var.f45717a2) {
                return false;
            }
            a51 a51Var = f45714a7;
            String str2 = this.f45719a4;
            int i2 = this.f45720a5;
            if (i2 == 1 && i == 2 && str2 != null && !a51Var.defaultValueEquals(str2, str)) {
                return false;
            }
            if (i2 == 2 && i == 1 && str != null && !a51Var.defaultValueEquals(str, str2)) {
                return false;
            }
            if (i2 != 0 && i2 == i) {
                if (str2 != null) {
                    if (!a51Var.defaultValueEquals(str2, str)) {
                        return false;
                    }
                } else if (str != null) {
                    return false;
                }
            }
            if (this.f45721a6 != b51Var.f45721a6) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return (((((this.f45715a0.hashCode() * 31) + this.f45721a6) * 31) + (this.f45717a2 ? 1231 : 1237)) * 31) + this.f45718a3;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Column{name='");
        sb.append(this.f45715a0);
        sb.append("', type='");
        sb.append(this.f45716a1);
        sb.append("', affinity='");
        sb.append(this.f45721a6);
        sb.append("', notNull=");
        sb.append(this.f45717a2);
        sb.append(", primaryKeyPosition=");
        sb.append(this.f45718a3);
        sb.append(", defaultValue='");
        String str = this.f45719a4;
        if (str == null) {
            str = "undefined";
        }
        return AbstractC0003a2.m35b6(sb, str, "'}");
    }
}

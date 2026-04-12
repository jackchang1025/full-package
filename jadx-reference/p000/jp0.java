package p000;

import java.security.spec.AlgorithmParameterSpec;

/* loaded from: classes2.dex */
public class jp0 implements AlgorithmParameterSpec {
    public static final String PROVABLY_SECURE_I = mp0.getName(5);
    public static final String PROVABLY_SECURE_III = mp0.getName(6);
    private String securityCategory;

    public jp0(String str) {
        this.securityCategory = str;
    }

    public String getSecurityCategory() {
        return this.securityCategory;
    }
}

package org.bouncycastle.jcajce.provider.symmetric.util;

import java.security.AlgorithmParameters;
import java.security.spec.AlgorithmParameterSpec;

/* loaded from: classes.dex */
class SpecUtil {
    public static AlgorithmParameterSpec extractSpec(AlgorithmParameters algorithmParameters, Class[] clsArr) {
        try {
            return algorithmParameters.getParameterSpec(AlgorithmParameterSpec.class);
        } catch (Exception unused) {
            for (int i2 = 0; i2 != clsArr.length; i2++) {
                Class cls = clsArr[i2];
                if (cls != null) {
                    try {
                        return algorithmParameters.getParameterSpec(cls);
                    } catch (Exception unused2) {
                        continue;
                    }
                }
            }
            return null;
        }
    }
}

package org.bouncycastle.jcajce.provider.config;

import java.security.spec.DSAParameterSpec;
import java.util.Map;
import java.util.Set;
import javax.crypto.spec.DHParameterSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;

/* loaded from: classes.dex */
public interface ProviderConfiguration {
    Set getAcceptableNamedCurves();

    Map getAdditionalECParameters();

    DHParameterSpec getDHDefaultParameters(int i2);

    DSAParameterSpec getDSADefaultParameters(int i2);

    ECParameterSpec getEcImplicitlyCa();
}

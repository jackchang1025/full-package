package p000;

import java.security.Permission;
import java.security.spec.DSAParameterSpec;
import java.security.spec.ECParameterSpec;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.crypto.spec.DHParameterSpec;
import org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import p000.C0929nx;

/* renamed from: fc */
/* loaded from: classes2.dex */
public class C0490fc implements dp0 {
    private volatile Object dhDefaultParams;
    private volatile C1340vk ecImplicitCaParams;
    private static Permission BC_EC_LOCAL_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, InterfaceC0791kp.THREAD_LOCAL_EC_IMPLICITLY_CA);
    private static Permission BC_EC_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, InterfaceC0791kp.EC_IMPLICITLY_CA);
    private static Permission BC_DH_LOCAL_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, InterfaceC0791kp.THREAD_LOCAL_DH_DEFAULT_PARAMS);
    private static Permission BC_DH_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, InterfaceC0791kp.DH_DEFAULT_PARAMS);
    private static Permission BC_EC_CURVE_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, InterfaceC0791kp.ACCEPTABLE_EC_CURVES);
    private static Permission BC_ADDITIONAL_EC_CURVE_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, InterfaceC0791kp.ADDITIONAL_EC_PARAMETERS);
    private ThreadLocal ecThreadSpec = new ThreadLocal();
    private ThreadLocal dhThreadSpec = new ThreadLocal();
    private volatile Set acceptableNamedCurves = new HashSet();
    private volatile Map additionalECParameters = new HashMap();

    @Override // p000.dp0
    public Set getAcceptableNamedCurves() {
        return Collections.unmodifiableSet(this.acceptableNamedCurves);
    }

    @Override // p000.dp0
    public Map getAdditionalECParameters() {
        return Collections.unmodifiableMap(this.additionalECParameters);
    }

    @Override // p000.dp0
    public DHParameterSpec getDHDefaultParameters(int i) {
        Object obj = this.dhThreadSpec.get();
        if (obj == null) {
            obj = this.dhDefaultParams;
        }
        if (obj instanceof DHParameterSpec) {
            DHParameterSpec dHParameterSpec = (DHParameterSpec) obj;
            if (dHParameterSpec.getP().bitLength() == i) {
                return dHParameterSpec;
            }
        } else if (obj instanceof DHParameterSpec[]) {
            DHParameterSpec[] dHParameterSpecArr = (DHParameterSpec[]) obj;
            for (int i2 = 0; i2 != dHParameterSpecArr.length; i2++) {
                if (dHParameterSpecArr[i2].getP().bitLength() == i) {
                    return dHParameterSpecArr[i2];
                }
            }
        }
        C1074pm c1074pm = (C1074pm) C0929nx.getSizedProperty(C0929nx.a1.DH_DEFAULT_PARAMS, i);
        if (c1074pm != null) {
            return new C1073pl(c1074pm);
        }
        return null;
    }

    @Override // p000.dp0
    public DSAParameterSpec getDSADefaultParameters(int i) {
        C1103qa c1103qa = (C1103qa) C0929nx.getSizedProperty(C0929nx.a1.DSA_DEFAULT_PARAMS, i);
        if (c1103qa != null) {
            return new DSAParameterSpec(c1103qa.getP(), c1103qa.getQ(), c1103qa.getG());
        }
        return null;
    }

    @Override // p000.dp0
    public C1340vk getEcImplicitlyCa() {
        C1340vk c1340vk = (C1340vk) this.ecThreadSpec.get();
        return c1340vk != null ? c1340vk : this.ecImplicitCaParams;
    }

    public void setParameter(String str, Object obj) {
        SecurityManager securityManager = System.getSecurityManager();
        if (str.equals(InterfaceC0791kp.THREAD_LOCAL_EC_IMPLICITLY_CA)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_LOCAL_PERMISSION);
            }
            C1340vk c1340vkConvertSpec = ((obj instanceof C1340vk) || obj == null) ? (C1340vk) obj : C1313uu.convertSpec((ECParameterSpec) obj);
            if (c1340vkConvertSpec == null) {
                this.ecThreadSpec.remove();
                return;
            } else {
                this.ecThreadSpec.set(c1340vkConvertSpec);
                return;
            }
        }
        if (str.equals(InterfaceC0791kp.EC_IMPLICITLY_CA)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_PERMISSION);
            }
            if ((obj instanceof C1340vk) || obj == null) {
                this.ecImplicitCaParams = (C1340vk) obj;
                return;
            } else {
                this.ecImplicitCaParams = C1313uu.convertSpec((ECParameterSpec) obj);
                return;
            }
        }
        if (str.equals(InterfaceC0791kp.THREAD_LOCAL_DH_DEFAULT_PARAMS)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_DH_LOCAL_PERMISSION);
            }
            if (!(obj instanceof DHParameterSpec) && !(obj instanceof DHParameterSpec[]) && obj != null) {
                throw new IllegalArgumentException("not a valid DHParameterSpec");
            }
            ThreadLocal threadLocal = this.dhThreadSpec;
            if (obj == null) {
                threadLocal.remove();
                return;
            } else {
                threadLocal.set(obj);
                return;
            }
        }
        if (str.equals(InterfaceC0791kp.DH_DEFAULT_PARAMS)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_DH_PERMISSION);
            }
            if (!(obj instanceof DHParameterSpec) && !(obj instanceof DHParameterSpec[]) && obj != null) {
                throw new IllegalArgumentException("not a valid DHParameterSpec or DHParameterSpec[]");
            }
            this.dhDefaultParams = obj;
            return;
        }
        if (str.equals(InterfaceC0791kp.ACCEPTABLE_EC_CURVES)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_EC_CURVE_PERMISSION);
            }
            this.acceptableNamedCurves = (Set) obj;
        } else if (str.equals(InterfaceC0791kp.ADDITIONAL_EC_PARAMETERS)) {
            if (securityManager != null) {
                securityManager.checkPermission(BC_ADDITIONAL_EC_CURVE_PERMISSION);
            }
            this.additionalECParameters = (Map) obj;
        }
    }
}

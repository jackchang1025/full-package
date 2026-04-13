package android.sun.security.provider;

import android.sun.security.pkcs12.PKCS12KeyStore;
import android.sun.security.provider.JavaKeyStore;
import java.security.Provider;
import java.security.Security;

/* loaded from: classes.dex */
public class JavaKeyStoreProvider extends Provider {
    public JavaKeyStoreProvider() {
        super("JKS", 1.0d, "Java KeyStore");
        put("KeyStore.JKS", JavaKeyStore.JKS.class.getName());
        put("KeyStore.CaseExactJKS", JavaKeyStore.CaseExactJKS.class.getName());
        put("KeyStore.PKCS12", PKCS12KeyStore.class.getName());
        Security.setProperty("keystore.type", "jks");
    }
}

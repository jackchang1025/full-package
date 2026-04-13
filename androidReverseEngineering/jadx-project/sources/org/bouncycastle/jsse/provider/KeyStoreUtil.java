package org.bouncycastle.jsse.provider;

import java.lang.reflect.Method;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.UnrecoverableKeyException;

/* loaded from: classes.dex */
abstract class KeyStoreUtil {
    private static final Method getProtectionAlgorithm = ReflectionUtil.getMethod("java.security.KeyStore$PasswordProtection", "getProtectionAlgorithm", new Class[0]);

    public static Key getKey(KeyStore keyStore, String str, KeyStore.ProtectionParameter protectionParameter) {
        if (protectionParameter == null) {
            throw new UnrecoverableKeyException("requested key requires a password");
        }
        if (!(protectionParameter instanceof KeyStore.PasswordProtection)) {
            throw new UnsupportedOperationException();
        }
        KeyStore.PasswordProtection passwordProtection = (KeyStore.PasswordProtection) protectionParameter;
        Method method = getProtectionAlgorithm;
        if (method == null || ReflectionUtil.invokeGetter(passwordProtection, method) == null) {
            return keyStore.getKey(str, passwordProtection.getPassword());
        }
        throw new KeyStoreException("unsupported password protection algorithm");
    }
}

package org.bouncycastle.jsse.provider;

import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509KeyManager;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jsse.BCX509ExtendedKeyManager;

/* loaded from: classes.dex */
abstract class X509KeyManagerUtil {
    /* JADX WARN: Multi-variable type inference failed */
    public static X509KeyManager exportX509KeyManager(BCX509ExtendedKeyManager bCX509ExtendedKeyManager) {
        return bCX509ExtendedKeyManager instanceof ImportX509KeyManager ? ((ImportX509KeyManager) bCX509ExtendedKeyManager).unwrap() : bCX509ExtendedKeyManager;
    }

    public static BCX509ExtendedKeyManager importX509KeyManager(JcaJceHelper jcaJceHelper, X509KeyManager x509KeyManager) {
        return x509KeyManager instanceof BCX509ExtendedKeyManager ? (BCX509ExtendedKeyManager) x509KeyManager : x509KeyManager instanceof X509ExtendedKeyManager ? new ImportX509KeyManager_5((X509ExtendedKeyManager) x509KeyManager) : new ImportX509KeyManager_4(x509KeyManager);
    }
}

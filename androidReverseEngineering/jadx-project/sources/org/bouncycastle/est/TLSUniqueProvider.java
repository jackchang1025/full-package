package org.bouncycastle.est;

/* loaded from: classes.dex */
public interface TLSUniqueProvider {
    byte[] getTLSUnique();

    boolean isTLSUniqueAvailable();
}

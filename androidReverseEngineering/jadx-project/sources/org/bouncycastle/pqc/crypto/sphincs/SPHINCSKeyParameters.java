package org.bouncycastle.pqc.crypto.sphincs;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

/* loaded from: classes.dex */
public class SPHINCSKeyParameters extends AsymmetricKeyParameter {
    public static final String SHA3_256 = "SHA3-256";
    public static final String SHA512_256 = "SHA-512/256";
    private final String treeDigest;

    public SPHINCSKeyParameters(boolean z2, String str) {
        super(z2);
        this.treeDigest = str;
    }

    public String getTreeDigest() {
        return this.treeDigest;
    }
}

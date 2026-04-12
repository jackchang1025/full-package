package p000;

import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class t80 {
    private SecureRandom random;
    private int strength;

    public t80(SecureRandom secureRandom, int i) {
        this.random = C0929nx.getSecureRandom(secureRandom);
        this.strength = i;
    }

    public SecureRandom getRandom() {
        return this.random;
    }

    public int getStrength() {
        return this.strength;
    }
}

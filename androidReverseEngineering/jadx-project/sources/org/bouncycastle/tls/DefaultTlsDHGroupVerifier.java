package org.bouncycastle.tls;

import java.math.BigInteger;
import java.util.Vector;
import org.bouncycastle.tls.crypto.DHGroup;
import org.bouncycastle.tls.crypto.DHStandardGroups;

/* loaded from: classes.dex */
public class DefaultTlsDHGroupVerifier implements TlsDHGroupVerifier {
    private static final Vector DEFAULT_GROUPS = new Vector();
    public static final int DEFAULT_MINIMUM_PRIME_BITS = 2048;
    protected Vector groups;
    protected int minimumPrimeBits;

    static {
        addDefaultGroup(DHStandardGroups.rfc3526_2048);
        addDefaultGroup(DHStandardGroups.rfc3526_3072);
        addDefaultGroup(DHStandardGroups.rfc3526_4096);
        addDefaultGroup(DHStandardGroups.rfc3526_6144);
        addDefaultGroup(DHStandardGroups.rfc3526_8192);
        addDefaultGroup(DHStandardGroups.rfc7919_ffdhe2048);
        addDefaultGroup(DHStandardGroups.rfc7919_ffdhe3072);
        addDefaultGroup(DHStandardGroups.rfc7919_ffdhe4096);
        addDefaultGroup(DHStandardGroups.rfc7919_ffdhe6144);
        addDefaultGroup(DHStandardGroups.rfc7919_ffdhe8192);
    }

    public DefaultTlsDHGroupVerifier() {
        this(2048);
    }

    private static void addDefaultGroup(DHGroup dHGroup) {
        DEFAULT_GROUPS.addElement(dHGroup);
    }

    @Override // org.bouncycastle.tls.TlsDHGroupVerifier
    public boolean accept(DHGroup dHGroup) {
        return checkMinimumPrimeBits(dHGroup) && checkGroup(dHGroup);
    }

    public boolean areGroupsEqual(DHGroup dHGroup, DHGroup dHGroup2) {
        return dHGroup == dHGroup2 || (areParametersEqual(dHGroup.getP(), dHGroup2.getP()) && areParametersEqual(dHGroup.getG(), dHGroup2.getG()));
    }

    public boolean areParametersEqual(BigInteger bigInteger, BigInteger bigInteger2) {
        return bigInteger == bigInteger2 || bigInteger.equals(bigInteger2);
    }

    public boolean checkGroup(DHGroup dHGroup) {
        for (int i2 = 0; i2 < this.groups.size(); i2++) {
            if (areGroupsEqual(dHGroup, (DHGroup) this.groups.elementAt(i2))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkMinimumPrimeBits(DHGroup dHGroup) {
        return dHGroup.getP().bitLength() >= getMinimumPrimeBits();
    }

    public int getMinimumPrimeBits() {
        return this.minimumPrimeBits;
    }

    public DefaultTlsDHGroupVerifier(int i2) {
        this(DEFAULT_GROUPS, i2);
    }

    public DefaultTlsDHGroupVerifier(Vector vector, int i2) {
        this.groups = new Vector(vector);
        this.minimumPrimeBits = i2;
    }
}

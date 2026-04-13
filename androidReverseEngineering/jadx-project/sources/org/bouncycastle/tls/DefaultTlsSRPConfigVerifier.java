package org.bouncycastle.tls;

import java.math.BigInteger;
import java.util.Vector;
import org.bouncycastle.tls.crypto.SRP6Group;
import org.bouncycastle.tls.crypto.SRP6StandardGroups;
import org.bouncycastle.tls.crypto.TlsSRPConfig;

/* loaded from: classes.dex */
public class DefaultTlsSRPConfigVerifier implements TlsSRPConfigVerifier {
    private static final Vector DEFAULT_GROUPS;
    protected final Vector groups;

    static {
        Vector vector = new Vector();
        DEFAULT_GROUPS = vector;
        vector.addElement(SRP6StandardGroups.rfc5054_1024);
        vector.addElement(SRP6StandardGroups.rfc5054_1536);
        vector.addElement(SRP6StandardGroups.rfc5054_2048);
        vector.addElement(SRP6StandardGroups.rfc5054_3072);
        vector.addElement(SRP6StandardGroups.rfc5054_4096);
        vector.addElement(SRP6StandardGroups.rfc5054_6144);
        vector.addElement(SRP6StandardGroups.rfc5054_8192);
    }

    public DefaultTlsSRPConfigVerifier() {
        this(DEFAULT_GROUPS);
    }

    @Override // org.bouncycastle.tls.TlsSRPConfigVerifier
    public boolean accept(TlsSRPConfig tlsSRPConfig) {
        for (int i2 = 0; i2 < this.groups.size(); i2++) {
            if (areGroupsEqual(tlsSRPConfig, (SRP6Group) this.groups.elementAt(i2))) {
                return true;
            }
        }
        return false;
    }

    public boolean areGroupsEqual(TlsSRPConfig tlsSRPConfig, SRP6Group sRP6Group) {
        BigInteger[] explicitNG = tlsSRPConfig.getExplicitNG();
        return areParametersEqual(explicitNG[0], sRP6Group.getN()) && areParametersEqual(explicitNG[1], sRP6Group.getG());
    }

    public boolean areParametersEqual(BigInteger bigInteger, BigInteger bigInteger2) {
        return bigInteger == bigInteger2 || bigInteger.equals(bigInteger2);
    }

    public DefaultTlsSRPConfigVerifier(Vector vector) {
        this.groups = new Vector(vector);
    }
}

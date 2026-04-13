package android.sun.security.x509;

import android.sun.security.util.Debug;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.math.BigInteger;
import java.security.ProviderException;
import java.security.interfaces.DSAParams;

/* loaded from: classes.dex */
public final class AlgIdDSA extends AlgorithmId implements DSAParams {
    private static final long serialVersionUID = 3437177836797504046L;

    /* renamed from: g */
    private BigInteger f81g;

    /* renamed from: p */
    private BigInteger f82p;

    /* renamed from: q */
    private BigInteger f83q;

    public AlgIdDSA() {
    }

    public AlgIdDSA(DerValue derValue) {
        super(derValue.getOID());
    }

    private void initializeParams() {
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putInteger(this.f82p);
        derOutputStream.putInteger(this.f83q);
        derOutputStream.putInteger(this.f81g);
        this.params = new DerValue((byte) 48, derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.AlgorithmId
    public void decodeParams() {
        DerValue derValue = this.params;
        if (derValue == null) {
            throw new IOException("DSA alg params are null");
        }
        if (derValue.tag != 48) {
            throw new IOException("DSA alg parsing error");
        }
        derValue.data.reset();
        this.f82p = this.params.data.getBigInteger();
        this.f83q = this.params.data.getBigInteger();
        this.f81g = this.params.data.getBigInteger();
        if (this.params.data.available() == 0) {
            return;
        }
        throw new IOException("AlgIdDSA params, extra=" + this.params.data.available());
    }

    @Override // java.security.interfaces.DSAParams
    public BigInteger getG() {
        return this.f81g;
    }

    @Override // android.sun.security.x509.AlgorithmId
    public String getName() {
        return "DSA";
    }

    @Override // java.security.interfaces.DSAParams
    public BigInteger getP() {
        return this.f82p;
    }

    @Override // java.security.interfaces.DSAParams
    public BigInteger getQ() {
        return this.f83q;
    }

    @Override // android.sun.security.x509.AlgorithmId
    public String paramsToString() {
        if (this.params == null) {
            return " null\n";
        }
        return "\n    p:\n" + Debug.toHexString(this.f82p) + "\n    q:\n" + Debug.toHexString(this.f83q) + "\n    g:\n" + Debug.toHexString(this.f81g) + "\n";
    }

    @Override // android.sun.security.x509.AlgorithmId
    public String toString() {
        return paramsToString();
    }

    public AlgIdDSA(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        super(AlgorithmId.DSA_oid);
        if (bigInteger == null && bigInteger2 == null && bigInteger3 == null) {
            return;
        }
        if (bigInteger == null || bigInteger2 == null || bigInteger3 == null) {
            throw new ProviderException("Invalid parameters for DSS/DSA Algorithm ID");
        }
        try {
            this.f82p = bigInteger;
            this.f83q = bigInteger2;
            this.f81g = bigInteger3;
            initializeParams();
        } catch (IOException unused) {
            throw new ProviderException("Construct DSS/DSA Algorithm ID");
        }
    }

    public AlgIdDSA(byte[] bArr) {
        super(new DerValue(bArr).getOID());
    }

    public AlgIdDSA(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        this(new BigInteger(1, bArr), new BigInteger(1, bArr2), new BigInteger(1, bArr3));
    }
}

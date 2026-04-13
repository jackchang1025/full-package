package android.sun.security.x509;

import android.sun.security.util.Debug;
import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

/* loaded from: classes.dex */
public class SerialNumber {
    private BigInteger serialNum;

    public SerialNumber(int i2) {
        this.serialNum = BigInteger.valueOf(i2);
    }

    private void construct(DerValue derValue) {
        this.serialNum = derValue.getBigInteger();
        if (derValue.data.available() != 0) {
            throw new IOException("Excess SerialNumber data");
        }
    }

    public void encode(DerOutputStream derOutputStream) {
        derOutputStream.putInteger(this.serialNum);
    }

    public BigInteger getNumber() {
        return this.serialNum;
    }

    public String toString() {
        return "SerialNumber: [" + Debug.toHexString(this.serialNum) + "]";
    }

    public SerialNumber(DerInputStream derInputStream) {
        construct(derInputStream.getDerValue());
    }

    public SerialNumber(DerValue derValue) {
        construct(derValue);
    }

    public SerialNumber(InputStream inputStream) {
        construct(new DerValue(inputStream));
    }

    public SerialNumber(BigInteger bigInteger) {
        this.serialNum = bigInteger;
    }
}

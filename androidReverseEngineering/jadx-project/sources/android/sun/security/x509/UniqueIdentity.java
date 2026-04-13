package android.sun.security.x509;

import android.sun.security.util.BitArray;
import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;

/* loaded from: classes.dex */
public class UniqueIdentity {
    private BitArray id;

    public UniqueIdentity(BitArray bitArray) {
        this.id = bitArray;
    }

    public void encode(DerOutputStream derOutputStream, byte b) {
        byte[] byteArray = this.id.toByteArray();
        int length = (byteArray.length * 8) - this.id.length();
        derOutputStream.write(b);
        derOutputStream.putLength(byteArray.length + 1);
        derOutputStream.write(length);
        derOutputStream.write(byteArray);
    }

    public boolean[] getId() {
        BitArray bitArray = this.id;
        if (bitArray == null) {
            return null;
        }
        return bitArray.toBooleanArray();
    }

    public String toString() {
        return "UniqueIdentity:" + this.id.toString() + "\n";
    }

    public UniqueIdentity(DerInputStream derInputStream) {
        this.id = derInputStream.getDerValue().getUnalignedBitString(true);
    }

    public UniqueIdentity(DerValue derValue) {
        this.id = derValue.getUnalignedBitString(true);
    }

    public UniqueIdentity(byte[] bArr) {
        this.id = new BitArray(bArr.length * 8, bArr);
    }
}

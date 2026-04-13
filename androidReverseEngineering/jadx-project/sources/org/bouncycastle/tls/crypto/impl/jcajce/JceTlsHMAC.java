package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.InvalidKeyException;
import java.util.Hashtable;
import javax.crypto.Mac;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.tls.crypto.TlsHMAC;
import org.bouncycastle.util.Integers;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class JceTlsHMAC implements TlsHMAC {
    private static final Hashtable internalBlockSizes;
    private final String algorithm;
    private final Mac hmac;
    private final Integer internalBlockSize;

    static {
        Hashtable hashtable = new Hashtable();
        internalBlockSizes = hashtable;
        hashtable.put("HmacMD5", Integers.valueOf(64));
        hashtable.put("HmacSHA1", Integers.valueOf(64));
        hashtable.put("HmacSHA256", Integers.valueOf(64));
        hashtable.put("HmacSHA384", Integers.valueOf(128));
        hashtable.put("HmacSHA512", Integers.valueOf(128));
    }

    public JceTlsHMAC(Mac mac, String str) {
        this(mac, str, getInternalBlockSize(str));
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public void calculateMAC(byte[] bArr, int i2) {
        try {
            this.hmac.doFinal(bArr, i2);
        } catch (ShortBufferException e2) {
            throw new IllegalArgumentException(e2.getMessage());
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsHMAC
    public int getInternalBlockSize() {
        return this.internalBlockSize.intValue();
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public int getMacLength() {
        return this.hmac.getMacLength();
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public void reset() {
        this.hmac.reset();
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public void setKey(byte[] bArr, int i2, int i3) {
        try {
            this.hmac.init(new SecretKeySpec(bArr, i2, i3, this.algorithm));
        } catch (InvalidKeyException e2) {
            throw new IllegalArgumentException(e2.getMessage());
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public void update(byte[] bArr, int i2, int i3) {
        this.hmac.update(bArr, i2, i3);
    }

    public JceTlsHMAC(Mac mac, String str, int i2) {
        this.hmac = mac;
        this.algorithm = str;
        this.internalBlockSize = Integers.valueOf(i2);
    }

    private static int getInternalBlockSize(String str) {
        Hashtable hashtable = internalBlockSizes;
        if (hashtable.containsKey(str)) {
            return ((Integer) hashtable.get(str)).intValue();
        }
        throw new IllegalArgumentException(AbstractC0000a.m16l("HMAC ", str, " unknown"));
    }

    @Override // org.bouncycastle.tls.crypto.TlsMAC
    public byte[] calculateMAC() {
        return this.hmac.doFinal();
    }
}

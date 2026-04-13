package android.sun.security.x509;

import android.sun.misc.HexDumpEncoder;
import android.sun.security.util.BitArray;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class IPAddressName implements GeneralNameInterface {
    private static final int MASKSIZE = 16;
    private byte[] address;
    private boolean isIPv4;
    private String name;

    public IPAddressName(DerValue derValue) {
        this(derValue.getOctetString());
    }

    private void parseIPv4(String str) {
        int indexOf = str.indexOf(47);
        if (indexOf == -1) {
            this.address = InetAddress.getByName(str).getAddress();
            return;
        }
        this.address = new byte[8];
        byte[] address = InetAddress.getByName(str.substring(indexOf + 1)).getAddress();
        System.arraycopy(InetAddress.getByName(str.substring(0, indexOf)).getAddress(), 0, this.address, 0, 4);
        System.arraycopy(address, 0, this.address, 4, 4);
    }

    private void parseIPv6(String str) {
        int indexOf = str.indexOf(47);
        if (indexOf == -1) {
            this.address = InetAddress.getByName(str).getAddress();
            return;
        }
        this.address = new byte[32];
        System.arraycopy(InetAddress.getByName(str.substring(0, indexOf)).getAddress(), 0, this.address, 0, 16);
        int parseInt = Integer.parseInt(str.substring(indexOf + 1));
        if (parseInt > 128) {
            throw new IOException("IPv6Address prefix is longer than 128");
        }
        BitArray bitArray = new BitArray(128);
        for (int i2 = 0; i2 < parseInt; i2++) {
            bitArray.set(i2, true);
        }
        byte[] byteArray = bitArray.toByteArray();
        for (int i3 = 0; i3 < 16; i3++) {
            this.address[i3 + 16] = byteArray[i3];
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x008d, code lost:
    
        if (r11 != false) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:?, code lost:
    
        return 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0095, code lost:
    
        if (r8 != false) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00c0, code lost:
    
        if (r3 == r2) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00d9, code lost:
    
        if (r3 == r2) goto L54;
     */
    @Override // android.sun.security.x509.GeneralNameInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int constrains(GeneralNameInterface generalNameInterface) {
        if (generalNameInterface == null || generalNameInterface.getType() != 7) {
            return -1;
        }
        IPAddressName iPAddressName = (IPAddressName) generalNameInterface;
        if (!iPAddressName.equals(this)) {
            byte[] bytes = iPAddressName.getBytes();
            if (bytes.length != 4 || this.address.length != 4) {
                if ((bytes.length == 8 && this.address.length == 8) || (bytes.length == 32 && this.address.length == 32)) {
                    int length = this.address.length / 2;
                    boolean z2 = true;
                    boolean z3 = true;
                    boolean z4 = false;
                    boolean z5 = false;
                    for (int i2 = 0; i2 < length; i2++) {
                        byte[] bArr = this.address;
                        byte b = bArr[i2];
                        int i3 = i2 + length;
                        byte b2 = bArr[i3];
                        if (((byte) (b & b2)) != b) {
                            z4 = true;
                        }
                        byte b3 = bytes[i2];
                        byte b4 = bytes[i3];
                        if (((byte) (b3 & b4)) != b3) {
                            z5 = true;
                        }
                        if (((byte) (b2 & b4)) != b2 || ((byte) (b & b2)) != ((byte) (b3 & b2))) {
                            z2 = false;
                        }
                        if (((byte) (b4 & b2)) != b4 || ((byte) (b3 & b4)) != ((byte) (b & b4))) {
                            z3 = false;
                        }
                    }
                    if (!z4 && !z5) {
                        if (!z2) {
                        }
                        return 1;
                    }
                    if (!z4 || !z5) {
                    }
                } else if (bytes.length == 8 || bytes.length == 32) {
                    int length2 = bytes.length / 2;
                    int i4 = 0;
                    while (i4 < length2 && (this.address[i4] & bytes[i4 + length2]) == bytes[i4]) {
                        i4++;
                    }
                } else {
                    byte[] bArr2 = this.address;
                    if (bArr2.length == 8 || bArr2.length == 32) {
                        int length3 = bArr2.length / 2;
                        int i5 = 0;
                        while (i5 < length3) {
                            byte b5 = bytes[i5];
                            byte[] bArr3 = this.address;
                            if ((b5 & bArr3[i5 + length3]) != bArr3[i5]) {
                                break;
                            }
                            i5++;
                        }
                    }
                }
            }
            return 3;
        }
        return 0;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public void encode(DerOutputStream derOutputStream) {
        derOutputStream.putOctetString(this.address);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IPAddressName)) {
            return false;
        }
        byte[] bytes = ((IPAddressName) obj).getBytes();
        int length = bytes.length;
        byte[] bArr = this.address;
        if (length != bArr.length) {
            return false;
        }
        if (bArr.length != 8 && bArr.length != 32) {
            return Arrays.equals(bytes, bArr);
        }
        int length2 = bArr.length / 2;
        byte[] bArr2 = new byte[length2];
        byte[] bArr3 = new byte[length2];
        for (int i2 = 0; i2 < length2; i2++) {
            byte[] bArr4 = this.address;
            int i3 = i2 + length2;
            bArr2[i2] = (byte) (bArr4[i3] & bArr4[i2]);
            byte b = (byte) (bytes[i2] & bytes[i3]);
            bArr3[i2] = b;
            if (bArr2[i2] != b) {
                return false;
            }
        }
        while (true) {
            byte[] bArr5 = this.address;
            if (length2 >= bArr5.length) {
                return true;
            }
            if (bArr5[length2] != bytes[length2]) {
                return false;
            }
            length2++;
        }
    }

    public byte[] getBytes() {
        return (byte[]) this.address.clone();
    }

    public String getName() {
        String str = this.name;
        if (str != null) {
            return str;
        }
        int i2 = 0;
        if (this.isIPv4) {
            byte[] bArr = new byte[4];
            System.arraycopy(this.address, 0, bArr, 0, 4);
            this.name = InetAddress.getByAddress(bArr).getHostAddress();
            byte[] bArr2 = this.address;
            if (bArr2.length == 8) {
                byte[] bArr3 = new byte[4];
                System.arraycopy(bArr2, 4, bArr3, 0, 4);
                this.name += "/" + InetAddress.getByAddress(bArr3).getHostAddress();
            }
        } else {
            byte[] bArr4 = new byte[16];
            System.arraycopy(this.address, 0, bArr4, 0, 16);
            this.name = InetAddress.getByAddress(bArr4).getHostAddress();
            if (this.address.length == 32) {
                byte[] bArr5 = new byte[16];
                for (int i3 = 16; i3 < 32; i3++) {
                    bArr5[i3 - 16] = this.address[i3];
                }
                BitArray bitArray = new BitArray(128, bArr5);
                while (i2 < 128 && bitArray.get(i2)) {
                    i2++;
                }
                this.name += "/" + i2;
                while (i2 < 128) {
                    if (bitArray.get(i2)) {
                        throw new IOException(AbstractC0000a.m12h("Invalid IPv6 subdomain - set bit ", i2, " not contiguous"));
                    }
                    i2++;
                }
            }
        }
        return this.name;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int getType() {
        return 7;
    }

    public int hashCode() {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr = this.address;
            if (i2 >= bArr.length) {
                return i3;
            }
            i3 += bArr[i2] * i2;
            i2++;
        }
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int subtreeDepth() {
        throw new UnsupportedOperationException("subtreeDepth() not defined for IPAddressName");
    }

    public String toString() {
        try {
            return "IPAddress: " + getName();
        } catch (IOException unused) {
            return "IPAddress: " + new HexDumpEncoder().encodeBuffer(this.address);
        }
    }

    public IPAddressName(String str) {
        if (str == null || str.length() == 0) {
            throw new IOException("IPAddress cannot be null or empty");
        }
        if (str.charAt(str.length() - 1) == '/') {
            throw new IOException("Invalid IPAddress: ".concat(str));
        }
        if (str.indexOf(58) >= 0) {
            parseIPv6(str);
            this.isIPv4 = false;
        } else {
            if (str.indexOf(46) < 0) {
                throw new IOException("Invalid IPAddress: ".concat(str));
            }
            parseIPv4(str);
            this.isIPv4 = true;
        }
    }

    public IPAddressName(byte[] bArr) {
        boolean z2;
        if (bArr.length == 4 || bArr.length == 8) {
            z2 = true;
        } else {
            if (bArr.length != 16 && bArr.length != 32) {
                throw new IOException("Invalid IPAddressName");
            }
            z2 = false;
        }
        this.isIPv4 = z2;
        this.address = bArr;
    }
}

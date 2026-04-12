package p000;

import java.util.HashMap;
import java.util.Map;
import org.conscrypt.PSKKeyManager;

/* renamed from: te */
/* loaded from: classes2.dex */
public class C1256te {
    private static Map<String, C0160c5> nameToOid = new HashMap();
    private static Map<C0160c5, String> oidToName = new HashMap();

    static {
        Map<String, C0160c5> map = nameToOid;
        C0160c5 c0160c5 = lh0.id_sha256;
        map.put(ki1.SHA_256, c0160c5);
        Map<String, C0160c5> map2 = nameToOid;
        C0160c5 c0160c52 = lh0.id_sha512;
        map2.put(ki1.SHA_512, c0160c52);
        Map<String, C0160c5> map3 = nameToOid;
        C0160c5 c0160c53 = lh0.id_shake128;
        map3.put(ki1.SHAKE128, c0160c53);
        Map<String, C0160c5> map4 = nameToOid;
        C0160c5 c0160c54 = lh0.id_shake256;
        map4.put(ki1.SHAKE256, c0160c54);
        oidToName.put(c0160c5, ki1.SHA_256);
        oidToName.put(c0160c52, ki1.SHA_512);
        oidToName.put(c0160c53, ki1.SHAKE128);
        oidToName.put(c0160c54, ki1.SHAKE256);
    }

    public static InterfaceC1236sv getDigest(C0160c5 c0160c5) {
        if (c0160c5.equals((AbstractC0164c9) lh0.id_sha256)) {
            return new us0();
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_sha512)) {
            return new xs0();
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_shake128)) {
            return new zs0(128);
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_shake256)) {
            return new zs0(PSKKeyManager.MAX_KEY_LENGTH_BYTES);
        }
        throw new IllegalArgumentException("unrecognized digest OID: " + c0160c5);
    }

    public static String getDigestName(C0160c5 c0160c5) {
        String str = oidToName.get(c0160c5);
        if (str != null) {
            return str;
        }
        throw new IllegalArgumentException("unrecognized digest oid: " + c0160c5);
    }

    public static C0160c5 getDigestOID(String str) {
        C0160c5 c0160c5 = nameToOid.get(str);
        if (c0160c5 != null) {
            return c0160c5;
        }
        throw new IllegalArgumentException(AbstractC0003a2.m48c9("unrecognized digest name: ", str));
    }

    public static int getDigestSize(InterfaceC1236sv interfaceC1236sv) {
        boolean z = interfaceC1236sv instanceof gj1;
        int digestSize = interfaceC1236sv.getDigestSize();
        return z ? digestSize * 2 : digestSize;
    }
}

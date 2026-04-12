package com.storm.safe.rock.security;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class SecurityManager$SecurityPolicy {

    /* renamed from: a0 */
    public static final SecurityManager$SecurityPolicy f52291a0;

    /* renamed from: a1 */
    public static final SecurityManager$SecurityPolicy f52292a1;

    /* renamed from: a2 */
    public static final /* synthetic */ SecurityManager$SecurityPolicy[] f52293a2;

    /* JADX INFO: Fake field, exist only in values array */
    SecurityManager$SecurityPolicy EF0;

    static {
        SecurityManager$SecurityPolicy securityManager$SecurityPolicy = new SecurityManager$SecurityPolicy("STRICT", 0);
        SecurityManager$SecurityPolicy securityManager$SecurityPolicy2 = new SecurityManager$SecurityPolicy("NORMAL", 1);
        f52291a0 = securityManager$SecurityPolicy2;
        SecurityManager$SecurityPolicy securityManager$SecurityPolicy3 = new SecurityManager$SecurityPolicy("RELAXED", 2);
        f52292a1 = securityManager$SecurityPolicy3;
        f52293a2 = new SecurityManager$SecurityPolicy[]{securityManager$SecurityPolicy, securityManager$SecurityPolicy2, securityManager$SecurityPolicy3};
    }

    public static SecurityManager$SecurityPolicy valueOf(String str) {
        return (SecurityManager$SecurityPolicy) Enum.valueOf(SecurityManager$SecurityPolicy.class, str);
    }

    public static SecurityManager$SecurityPolicy[] values() {
        return (SecurityManager$SecurityPolicy[]) f52293a2.clone();
    }
}

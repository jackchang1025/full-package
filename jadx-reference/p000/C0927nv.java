package p000;

/* renamed from: nv */
/* loaded from: classes2.dex */
public class C0927nv {
    public static final C0160c5 cryptlib;
    public static final C0160c5 curvey25519;
    public static final C0160c5 ecc;

    static {
        C0160c5 c0160c5 = new C0160c5("1.3.6.1.4.1.3029");
        cryptlib = c0160c5;
        C0160c5 c0160c5Branch = c0160c5.branch("1").branch("5");
        ecc = c0160c5Branch;
        curvey25519 = c0160c5Branch.branch("1");
    }
}

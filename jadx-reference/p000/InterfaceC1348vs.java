package p000;

/* renamed from: vs */
/* loaded from: classes2.dex */
public interface InterfaceC1348vs {
    public static final C0160c5 id_Ed25519;
    public static final C0160c5 id_Ed448;
    public static final C0160c5 id_X25519;
    public static final C0160c5 id_X448;
    public static final C0160c5 id_edwards_curve_algs;

    static {
        C0160c5 c0160c5 = new C0160c5("1.3.101");
        id_edwards_curve_algs = c0160c5;
        id_X25519 = c0160c5.branch("110").intern();
        id_X448 = c0160c5.branch("111").intern();
        id_Ed25519 = c0160c5.branch("112").intern();
        id_Ed448 = c0160c5.branch("113").intern();
    }
}

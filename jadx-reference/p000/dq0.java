package p000;

/* loaded from: classes2.dex */
public class dq0 extends C0991oo {
    public static final int AA_COMPROMISE = 32768;
    public static final int AFFILIATION_CHANGED = 16;
    public static final int CA_COMPROMISE = 32;
    public static final int CERTIFICATE_HOLD = 2;
    public static final int CESSATION_OF_OPERATION = 4;
    public static final int KEY_COMPROMISE = 64;
    public static final int PRIVILEGE_WITHDRAWN = 1;
    public static final int SUPERSEDED = 8;
    public static final int UNUSED = 128;
    public static final int aACompromise = 32768;
    public static final int affiliationChanged = 16;
    public static final int cACompromise = 32;
    public static final int certificateHold = 2;
    public static final int cessationOfOperation = 4;
    public static final int keyCompromise = 64;
    public static final int privilegeWithdrawn = 1;
    public static final int superseded = 8;
    public static final int unused = 128;

    public dq0(int i) {
        super(AbstractC0007a6.getBytes(i), AbstractC0007a6.getPadBits(i));
    }

    public dq0(AbstractC0007a6 abstractC0007a6) {
        super(abstractC0007a6.getBytes(), abstractC0007a6.getPadBits());
    }
}

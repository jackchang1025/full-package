package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;

/* loaded from: classes.dex */
public interface GeneralNameInterface {
    public static final int NAME_ANY = 0;
    public static final int NAME_DIFF_TYPE = -1;
    public static final int NAME_DIRECTORY = 4;
    public static final int NAME_DNS = 2;
    public static final int NAME_EDI = 5;
    public static final int NAME_IP = 7;
    public static final int NAME_MATCH = 0;
    public static final int NAME_NARROWS = 1;
    public static final int NAME_OID = 8;
    public static final int NAME_RFC822 = 1;
    public static final int NAME_SAME_TYPE = 3;
    public static final int NAME_URI = 6;
    public static final int NAME_WIDENS = 2;
    public static final int NAME_X400 = 3;

    int constrains(GeneralNameInterface generalNameInterface);

    void encode(DerOutputStream derOutputStream);

    int getType();

    int subtreeDepth();
}

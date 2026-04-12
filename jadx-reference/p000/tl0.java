package p000;

import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.Signature;
import javax.crypto.Mac;

/* loaded from: classes2.dex */
public class tl0 {
    public static OutputStream createStream(MessageDigest messageDigest) {
        return new C1253tb(messageDigest);
    }

    public static OutputStream createStream(Signature signature) {
        return new s01(signature);
    }

    public static OutputStream createStream(Mac mac) {
        return new rc0(mac);
    }
}

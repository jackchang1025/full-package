package i1;

import android.support.v4.view.PointerIconCompat;
import java.nio.charset.CharacterCodingException;

/* renamed from: i1.c */
/* loaded from: classes.dex */
public class C0340c extends Exception {

    /* renamed from: a */
    public final int f655a;

    public C0340c(int i2) {
        this.f655a = i2;
    }

    public C0340c(int i2, String str) {
        super(str);
        this.f655a = i2;
    }

    public C0340c(CharacterCodingException characterCodingException) {
        super(characterCodingException);
        this.f655a = PointerIconCompat.TYPE_CROSSHAIR;
    }
}

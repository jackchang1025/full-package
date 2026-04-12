package p000;

import android.content.Context;
import android.util.AttributeSet;
import java.util.HashMap;
import java.util.HashSet;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class k80 {

    /* renamed from: a0 */
    public int f57482a0 = -1;

    /* renamed from: a1 */
    public int f57483a1 = -1;

    /* renamed from: a2 */
    public String f57484a2 = null;

    /* renamed from: a3 */
    public HashMap f57485a3;

    /* renamed from: a4 */
    public static float m213471a4(Number number) {
        return number instanceof Float ? ((Float) number).floatValue() : Float.parseFloat(number.toString());
    }

    /* renamed from: a0 */
    public abstract k80 mo213472a0();

    /* renamed from: a1 */
    public abstract void mo213473a1(HashSet hashSet);

    /* renamed from: a2 */
    public abstract void mo213474a2(Context context, AttributeSet attributeSet);

    /* renamed from: a3 */
    public void mo213475a3(HashMap map) {
    }
}

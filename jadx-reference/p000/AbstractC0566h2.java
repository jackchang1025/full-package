package p000;

import java.util.Random;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: h2 */
/* loaded from: classes2.dex */
public abstract class AbstractC0566h2 extends aq0 {
    @Override // p000.aq0
    /* renamed from: a0 */
    public final int mo210496a0(int i) {
        return ((-i) >> 31) & (mo212994a3().nextInt() >>> (32 - i));
    }

    @Override // p000.aq0
    /* renamed from: a1 */
    public final int mo210497a1() {
        return mo212994a3().nextInt();
    }

    /* renamed from: a3 */
    public abstract Random mo212994a3();
}

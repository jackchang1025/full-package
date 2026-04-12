package p000;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class on0 extends AbstractC0566h2 {
    @Override // p000.aq0
    /* renamed from: a2 */
    public final int mo210498a2(int i, int i2) {
        return ThreadLocalRandom.current().nextInt(i, i2);
    }

    @Override // p000.AbstractC0566h2
    /* renamed from: a3 */
    public final Random mo212994a3() {
        ThreadLocalRandom threadLocalRandomCurrent = ThreadLocalRandom.current();
        t60.m214694b5(threadLocalRandomCurrent, "current()");
        return threadLocalRandomCurrent;
    }
}

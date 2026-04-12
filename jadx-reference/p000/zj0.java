package p000;

import android.media.AudioAttributes;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class zj0 {
    /* renamed from: a0 */
    public static AudioAttributes m215424a0(AudioAttributes.Builder builder) {
        return builder.build();
    }

    /* renamed from: a1 */
    public static AudioAttributes.Builder m215425a1() {
        return new AudioAttributes.Builder();
    }

    /* renamed from: a2 */
    public static AudioAttributes.Builder m215426a2(AudioAttributes.Builder builder, int i) {
        return builder.setContentType(i);
    }

    /* renamed from: a3 */
    public static AudioAttributes.Builder m215427a3(AudioAttributes.Builder builder, int i) {
        return builder.setLegacyStreamType(i);
    }

    /* renamed from: a4 */
    public static AudioAttributes.Builder m215428a4(AudioAttributes.Builder builder, int i) {
        return builder.setUsage(i);
    }
}

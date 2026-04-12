package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: dp */
/* loaded from: classes.dex */
public final class C0424dp {

    /* renamed from: a4 */
    public static final byte[] f55838a4 = new byte[1792];

    /* renamed from: a0 */
    public final CharSequence f55839a0;

    /* renamed from: a1 */
    public final int f55840a1;

    /* renamed from: a2 */
    public int f55841a2;

    /* renamed from: a3 */
    public char f55842a3;

    static {
        for (int i = 0; i < 1792; i++) {
            f55838a4[i] = Character.getDirectionality(i);
        }
    }

    public C0424dp(CharSequence charSequence) {
        this.f55839a0 = charSequence;
        this.f55840a1 = charSequence.length();
    }

    /* renamed from: a0 */
    public final byte m212616a0() {
        int i = this.f55841a2 - 1;
        CharSequence charSequence = this.f55839a0;
        char cCharAt = charSequence.charAt(i);
        this.f55842a3 = cCharAt;
        if (Character.isLowSurrogate(cCharAt)) {
            int iCodePointBefore = Character.codePointBefore(charSequence, this.f55841a2);
            this.f55841a2 -= Character.charCount(iCodePointBefore);
            return Character.getDirectionality(iCodePointBefore);
        }
        this.f55841a2--;
        char c = this.f55842a3;
        return c < 1792 ? f55838a4[c] : Character.getDirectionality(c);
    }
}

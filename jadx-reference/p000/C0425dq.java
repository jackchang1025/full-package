package p000;

import android.text.SpannableStringBuilder;
import okhttp3.internal.p032ws.WebSocketProtocol;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: dq */
/* loaded from: classes.dex */
public final class C0425dq {

    /* renamed from: a1 */
    public static final String f55843a1;

    /* renamed from: a2 */
    public static final String f55844a2;

    /* renamed from: a3 */
    public static final C0425dq f55845a3;

    /* renamed from: a4 */
    public static final C0425dq f55846a4;

    /* renamed from: a0 */
    public final boolean f55847a0;

    static {
        C0471ew c0471ew = s51.f59868a2;
        f55843a1 = Character.toString((char) 8206);
        f55844a2 = Character.toString((char) 8207);
        f55845a3 = new C0425dq(false);
        f55846a4 = new C0425dq(true);
    }

    public C0425dq(boolean z) {
        C0471ew c0471ew = s51.f59866a0;
        this.f55847a0 = z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x006e, code lost:
    
        if (r1 != 0) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0071, code lost:
    
        if (r2 == 0) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0073, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0076, code lost:
    
        if (r0.f55841a2 <= 0) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x007c, code lost:
    
        switch(r0.m212616a0()) {
            case 14: goto L66;
            case 15: goto L66;
            case 16: goto L65;
            case 17: goto L65;
            case 18: goto L64;
            default: goto L70;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0080, code lost:
    
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0083, code lost:
    
        if (r1 != r3) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0085, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0086, code lost:
    
        r3 = r3 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0089, code lost:
    
        if (r1 != r3) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x008c, code lost:
    
        return 0;
     */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int m212617a0(CharSequence charSequence) {
        byte directionality;
        C0424dp c0424dp = new C0424dp(charSequence);
        c0424dp.f55841a2 = 0;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = c0424dp.f55841a2;
            if (i4 < c0424dp.f55840a1 && i == 0) {
                CharSequence charSequence2 = c0424dp.f55839a0;
                char cCharAt = charSequence2.charAt(i4);
                c0424dp.f55842a3 = cCharAt;
                if (Character.isHighSurrogate(cCharAt)) {
                    int iCodePointAt = Character.codePointAt(charSequence2, c0424dp.f55841a2);
                    c0424dp.f55841a2 = Character.charCount(iCodePointAt) + c0424dp.f55841a2;
                    directionality = Character.getDirectionality(iCodePointAt);
                } else {
                    c0424dp.f55841a2++;
                    char c = c0424dp.f55842a3;
                    directionality = c < 1792 ? C0424dp.f55838a4[c] : Character.getDirectionality(c);
                }
                if (directionality != 0) {
                    if (directionality == 1 || directionality == 2) {
                        if (i3 == 0) {
                        }
                    } else if (directionality != 9) {
                        switch (directionality) {
                            case 14:
                            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                                i3++;
                                i2 = -1;
                                continue;
                            case 16:
                            case 17:
                                i3++;
                                i2 = 1;
                                continue;
                            case 18:
                                i3--;
                                i2 = 0;
                                continue;
                        }
                    }
                } else if (i3 == 0) {
                }
                i = i3;
            }
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0034, code lost:
    
        return 1;
     */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int m212618a1(CharSequence charSequence) {
        C0424dp c0424dp = new C0424dp(charSequence);
        c0424dp.f55841a2 = c0424dp.f55840a1;
        int i = 0;
        while (true) {
            int i2 = i;
            while (c0424dp.f55841a2 > 0) {
                byte bM212616a0 = c0424dp.m212616a0();
                if (bM212616a0 != 0) {
                    if (bM212616a0 == 1 || bM212616a0 == 2) {
                        if (i != 0) {
                            if (i2 == 0) {
                                break;
                            }
                        }
                    } else if (bM212616a0 != 9) {
                        switch (bM212616a0) {
                            case 14:
                            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                                if (i2 == i) {
                                    return -1;
                                }
                                i--;
                                break;
                            case 16:
                            case 17:
                                if (i2 == i) {
                                    break;
                                }
                                i--;
                                break;
                            case 18:
                                i++;
                                break;
                            default:
                                if (i2 != 0) {
                                    break;
                                } else {
                                    break;
                                }
                        }
                    } else {
                        continue;
                    }
                } else {
                    if (i == 0) {
                        return -1;
                    }
                    if (i2 == 0) {
                        break;
                    }
                }
            }
            return 0;
        }
    }

    /* renamed from: a2 */
    public final SpannableStringBuilder m212619a2(CharSequence charSequence) {
        C0471ew c0471ew = s51.f59868a2;
        if (charSequence == null) {
            return null;
        }
        boolean zM212725a1 = c0471ew.m212725a1(charSequence, charSequence.length());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        boolean zM212725a12 = (zM212725a1 ? s51.f59867a1 : s51.f59866a0).m212725a1(charSequence, charSequence.length());
        String str = "";
        String str2 = f55844a2;
        String str3 = f55843a1;
        boolean z = this.f55847a0;
        spannableStringBuilder.append((CharSequence) ((z || !(zM212725a12 || m212617a0(charSequence) == 1)) ? (!z || (zM212725a12 && m212617a0(charSequence) != -1)) ? "" : str2 : str3));
        if (zM212725a1 != z) {
            spannableStringBuilder.append(zM212725a1 ? (char) 8235 : (char) 8234);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.append((char) 8236);
        } else {
            spannableStringBuilder.append(charSequence);
        }
        boolean zM212725a13 = (zM212725a1 ? s51.f59867a1 : s51.f59866a0).m212725a1(charSequence, charSequence.length());
        if (!z && (zM212725a13 || m212618a1(charSequence) == 1)) {
            str = str3;
        } else if (z && (!zM212725a13 || m212618a1(charSequence) == -1)) {
            str = str2;
        }
        spannableStringBuilder.append((CharSequence) str);
        return spannableStringBuilder;
    }
}

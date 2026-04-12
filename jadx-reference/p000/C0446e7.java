package p000;

import io.socket.engineio.parser.Base64;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;

/* renamed from: e7 */
/* loaded from: classes2.dex */
public final class C0446e7 {
    private C0446e7() {
    }

    public static AbstractC0445e6 get(int i) {
        switch (i) {
            case 1:
                return C0009a8.TYPE;
            case 2:
                return C0155c0.TYPE;
            case 3:
                return AbstractC0007a6.TYPE;
            case 4:
                return AbstractC0161c6.TYPE;
            case 5:
                return AbstractC0156c1.TYPE;
            case 6:
                return C0160c5.TYPE;
            case 7:
                return C0159c4.TYPE;
            case 8:
                return AbstractC0120b3.TYPE;
            case 9:
            case oe0.DEFAULT_M /* 11 */:
            case 14:
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
            case 29:
            default:
                return null;
            case 10:
                return C0119b2.TYPE;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                return AbstractC0443e4.TYPE;
            case 13:
                return C0399d1.TYPE;
            case 16:
                return AbstractC0400d2.TYPE;
            case 17:
                return AbstractC0402d4.TYPE;
            case 18:
                return AbstractC0157c2.TYPE;
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                return AbstractC0398d0.TYPE;
            case 20:
                return AbstractC0406d8.TYPE;
            case 21:
                return AbstractC0448e9.TYPE;
            case 22:
                return AbstractC0125b8.TYPE;
            case 23:
                return C0442e3.TYPE;
            case 24:
                return C0123b6.TYPE;
            case 25:
                return AbstractC0124b7.TYPE;
            case 26:
                return AbstractC0476f0.TYPE;
            case 27:
                return AbstractC0122b5.TYPE;
            case 28:
                return AbstractC0444e5.TYPE;
            case 30:
                return AbstractC0006a5.TYPE;
        }
    }
}

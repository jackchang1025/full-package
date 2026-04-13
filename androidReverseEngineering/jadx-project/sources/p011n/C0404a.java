package p011n;

import com.guard.wallet.req.ListenPropResponse;
import java.nio.ByteBuffer;
import java.util.Comparator;

/* renamed from: n.a */
/* loaded from: classes.dex */
public final class C0404a implements Comparator {

    /* renamed from: a */
    public final /* synthetic */ int f808a;

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.f808a) {
            case 0:
                return Integer.compare(((String) obj).length() - ((String) obj2).length(), 0);
            case 1:
                return ((ListenPropResponse) obj).timestamp.compareTo(((ListenPropResponse) obj2).timestamp);
            default:
                ByteBuffer byteBuffer = (ByteBuffer) obj;
                ByteBuffer byteBuffer2 = (ByteBuffer) obj2;
                if (byteBuffer.capacity() == byteBuffer2.capacity()) {
                    return 0;
                }
                return byteBuffer.capacity() > byteBuffer2.capacity() ? 1 : -1;
        }
    }
}

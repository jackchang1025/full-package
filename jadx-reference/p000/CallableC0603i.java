package p000;

import android.content.Context;
import java.util.concurrent.Callable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: i */
/* loaded from: classes.dex */
public final class CallableC0603i implements Callable {

    /* renamed from: a0 */
    public final /* synthetic */ int f56773a0;

    /* renamed from: a1 */
    public final /* synthetic */ String f56774a1;

    /* renamed from: a2 */
    public final /* synthetic */ Context f56775a2;

    /* renamed from: a3 */
    public final /* synthetic */ C1094q2 f56776a3;

    /* renamed from: a4 */
    public final /* synthetic */ int f56777a4;

    public /* synthetic */ CallableC0603i(String str, Context context, C1094q2 c1094q2, int i, int i2) {
        this.f56773a0 = i2;
        this.f56774a1 = str;
        this.f56775a2 = context;
        this.f56776a3 = c1094q2;
        this.f56777a4 = i;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        switch (this.f56773a0) {
            case 0:
                return AbstractC0802l.m213767a0(this.f56774a1, this.f56775a2, this.f56776a3, this.f56777a4);
            default:
                try {
                    return AbstractC0802l.m213767a0(this.f56774a1, this.f56775a2, this.f56776a3, this.f56777a4);
                } catch (Throwable unused) {
                    return new C0739k(-3);
                }
        }
    }
}

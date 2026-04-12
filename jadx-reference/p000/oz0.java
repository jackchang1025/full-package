package p000;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Result;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class oz0 extends pz0 implements Iterator, InterfaceC0876mv, d80 {

    /* renamed from: a0 */
    public int f59124a0;

    /* renamed from: a1 */
    public Object f59125a1;

    /* renamed from: a2 */
    public InterfaceC0876mv f59126a2;

    @Override // p000.pz0
    /* renamed from: a0 */
    public final void mo214236a0(Object obj, BaseContinuationImpl baseContinuationImpl) {
        this.f59125a1 = obj;
        this.f59124a0 = 3;
        this.f59126a2 = baseContinuationImpl;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
    }

    /* renamed from: a1 */
    public final RuntimeException m214237a1() {
        int i = this.f59124a0;
        if (i == 4) {
            return new NoSuchElementException();
        }
        if (i == 5) {
            return new IllegalStateException("Iterator has failed.");
        }
        return new IllegalStateException("Unexpected state of the iterator: " + this.f59124a0);
    }

    @Override // p000.InterfaceC0876mv
    public final InterfaceC0912ng getContext() {
        return EmptyCoroutineContext.f57605a0;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i;
        while (true) {
            i = this.f59124a0;
            if (i != 0) {
                break;
            }
            this.f59124a0 = 5;
            InterfaceC0876mv interfaceC0876mv = this.f59126a2;
            t60.m214692b3(interfaceC0876mv);
            this.f59126a2 = null;
            int i2 = Result.f57558a1;
            interfaceC0876mv.resumeWith(C1351vv.f60710b1);
        }
        if (i == 1) {
            t60.m214692b3(null);
            throw null;
        }
        if (i == 2 || i == 3) {
            return true;
        }
        if (i == 4) {
            return false;
        }
        throw m214237a1();
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i = this.f59124a0;
        if (i == 0 || i == 1) {
            if (hasNext()) {
                return next();
            }
            throw new NoSuchElementException();
        }
        if (i == 2) {
            this.f59124a0 = 1;
            t60.m214692b3(null);
            throw null;
        }
        if (i != 3) {
            throw m214237a1();
        }
        this.f59124a0 = 0;
        Object obj = this.f59125a1;
        this.f59125a1 = null;
        return obj;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // p000.InterfaceC0876mv
    public final void resumeWith(Object obj) throws Throwable {
        kg1.m213544f4(obj);
        this.f59124a0 = 4;
    }
}

package p000;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Result;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.AbstractC0781a1;
import kotlinx.coroutines.internal.AbstractC0788a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: tf */
/* loaded from: classes2.dex */
public final class C1257tf extends AbstractC1259th implements InterfaceC0921np, InterfaceC0876mv {

    /* renamed from: a7 */
    public static final AtomicReferenceFieldUpdater f60207a7 = AtomicReferenceFieldUpdater.newUpdater(C1257tf.class, Object.class, "_reusableCancellableContinuation");
    private volatile Object _reusableCancellableContinuation;

    /* renamed from: a3 */
    public final AbstractC0781a1 f60208a3;

    /* renamed from: a4 */
    public final ContinuationImpl f60209a4;

    /* renamed from: a5 */
    public Object f60210a5;

    /* renamed from: a6 */
    public final Object f60211a6;

    public C1257tf(AbstractC0781a1 abstractC0781a1, ContinuationImpl continuationImpl) {
        super(-1);
        this.f60208a3 = abstractC0781a1;
        this.f60209a4 = continuationImpl;
        this.f60210a5 = b81.f45733a4;
        Object objMo212743b0 = continuationImpl.getContext().mo212743b0(0, AbstractC0788a1.f57689a1);
        t60.m214692b3(objMo212743b0);
        this.f60211a6 = objMo212743b0;
    }

    @Override // p000.AbstractC1259th
    /* renamed from: a1 */
    public final void mo212914a1(Object obj, CancellationException cancellationException) {
        if (obj instanceof AbstractC0731ju) {
            throw null;
        }
    }

    @Override // p000.AbstractC1259th
    /* renamed from: a8 */
    public final Object mo212918a8() {
        Object obj = this.f60210a5;
        this.f60210a5 = b81.f45733a4;
        return obj;
    }

    @Override // p000.InterfaceC0921np
    public final InterfaceC0921np getCallerFrame() {
        ContinuationImpl continuationImpl = this.f60209a4;
        if (continuationImpl != null) {
            return continuationImpl;
        }
        return null;
    }

    @Override // p000.InterfaceC0876mv
    public final InterfaceC0912ng getContext() {
        return this.f60209a4.getContext();
    }

    @Override // p000.InterfaceC0876mv
    public final void resumeWith(Object obj) {
        ContinuationImpl continuationImpl = this.f60209a4;
        InterfaceC0912ng context = continuationImpl.getContext();
        Throwable thM213607a0 = Result.m213607a0(obj);
        Object c0730jt = thM213607a0 == null ? obj : new C0730jt(thM213607a0, false);
        AbstractC0781a1 abstractC0781a1 = this.f60208a3;
        if (abstractC0781a1.mo213698c7()) {
            this.f60210a5 = c0730jt;
            this.f60222a2 = 0;
            abstractC0781a1.mo212723c6(context, this);
            return;
        }
        AbstractC1424xo abstractC1424xoM213943a0 = m61.m213943a0();
        if (abstractC1424xoM213943a0.f61166a2 >= 4294967296L) {
            this.f60210a5 = c0730jt;
            this.f60222a2 = 0;
            C0127ba c0127ba = abstractC1424xoM213943a0.f61168a4;
            if (c0127ba == null) {
                c0127ba = new C0127ba();
                abstractC1424xoM213943a0.f61168a4 = c0127ba;
            }
            c0127ba.addLast(this);
            return;
        }
        abstractC1424xoM213943a0.m215201d0(true);
        try {
            InterfaceC0912ng context2 = continuationImpl.getContext();
            Object objM213735a1 = AbstractC0788a1.m213735a1(context2, this.f60211a6);
            try {
                continuationImpl.resumeWith(obj);
                while (abstractC1424xoM213943a0.m215202d2()) {
                }
            } finally {
                AbstractC0788a1.m213734a0(context2, objM213735a1);
            }
        } finally {
            try {
            } finally {
            }
        }
    }

    public final String toString() {
        return "DispatchedContinuation[" + this.f60208a3 + ", " + AbstractC1117qo.m214462g1(this.f60209a4) + ']';
    }

    @Override // p000.AbstractC1259th
    /* renamed from: a4 */
    public final InterfaceC0876mv mo212915a4() {
        return this;
    }
}

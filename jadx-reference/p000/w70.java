package p000;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class w70 implements k50 {

    /* renamed from: a1 */
    public static final AtomicIntegerFieldUpdater f60797a1 = AtomicIntegerFieldUpdater.newUpdater(w70.class, "_isCompleting");

    /* renamed from: a2 */
    public static final AtomicReferenceFieldUpdater f60798a2 = AtomicReferenceFieldUpdater.newUpdater(w70.class, Object.class, "_rootCause");

    /* renamed from: a3 */
    public static final AtomicReferenceFieldUpdater f60799a3 = AtomicReferenceFieldUpdater.newUpdater(w70.class, Object.class, "_exceptionsHolder");
    private volatile Object _exceptionsHolder;
    private volatile int _isCompleting = 0;
    private volatile Object _rootCause;

    /* renamed from: a0 */
    public final uj0 f60800a0;

    public w70(uj0 uj0Var, Throwable th) {
        this.f60800a0 = uj0Var;
        this._rootCause = th;
    }

    @Override // p000.k50
    /* renamed from: a0 */
    public final boolean mo213204a0() {
        return m215011a2() == null;
    }

    /* renamed from: a1 */
    public final void m215010a1(Throwable th) {
        Throwable thM215011a2 = m215011a2();
        if (thM215011a2 == null) {
            f60798a2.set(this, th);
            return;
        }
        if (th == thM215011a2) {
            return;
        }
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f60799a3;
        Object obj = atomicReferenceFieldUpdater.get(this);
        if (obj == null) {
            atomicReferenceFieldUpdater.set(this, th);
            return;
        }
        if (!(obj instanceof Throwable)) {
            if (obj instanceof ArrayList) {
                ((ArrayList) obj).add(th);
                return;
            } else {
                throw new IllegalStateException(("State is " + obj).toString());
            }
        }
        if (th == obj) {
            return;
        }
        ArrayList arrayList = new ArrayList(4);
        arrayList.add(obj);
        arrayList.add(th);
        atomicReferenceFieldUpdater.set(this, arrayList);
    }

    /* renamed from: a2 */
    public final Throwable m215011a2() {
        return (Throwable) f60798a2.get(this);
    }

    /* renamed from: a3 */
    public final boolean m215012a3() {
        return m215011a2() != null;
    }

    @Override // p000.k50
    /* renamed from: a4 */
    public final uj0 mo213205a4() {
        return this.f60800a0;
    }

    /* renamed from: a5 */
    public final boolean m215013a5() {
        return f60797a1.get(this) != 0;
    }

    /* renamed from: a6 */
    public final ArrayList m215014a6(Throwable th) {
        ArrayList arrayList;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f60799a3;
        Object obj = atomicReferenceFieldUpdater.get(this);
        if (obj == null) {
            arrayList = new ArrayList(4);
        } else if (obj instanceof Throwable) {
            ArrayList arrayList2 = new ArrayList(4);
            arrayList2.add(obj);
            arrayList = arrayList2;
        } else {
            if (!(obj instanceof ArrayList)) {
                throw new IllegalStateException(("State is " + obj).toString());
            }
            arrayList = (ArrayList) obj;
        }
        Throwable thM215011a2 = m215011a2();
        if (thM215011a2 != null) {
            arrayList.add(0, thM215011a2);
        }
        if (th != null && !th.equals(thM215011a2)) {
            arrayList.add(th);
        }
        atomicReferenceFieldUpdater.set(this, t60.f60160b2);
        return arrayList;
    }

    public final String toString() {
        return "Finishing[cancelling=" + m215012a3() + ", completing=" + m215013a5() + ", rootCause=" + m215011a2() + ", exceptions=" + f60799a3.get(this) + ", list=" + this.f60800a0 + ']';
    }
}

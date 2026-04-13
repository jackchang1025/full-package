package f0;

import com.guard.wallet.http.C0203h;
import java.util.LinkedList;
import java.util.WeakHashMap;
import java.util.concurrent.Semaphore;

/* loaded from: classes.dex */
public final class b0 extends LinkedList {

    /* renamed from: c */
    public static final WeakHashMap f502c = new WeakHashMap();

    /* renamed from: a */
    public C0203h f503a;

    /* renamed from: b */
    public final Semaphore f504b = new Semaphore(0);

    @Override // java.util.LinkedList, java.util.Deque, java.util.Queue
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final Runnable remove() {
        synchronized (this) {
            if (isEmpty()) {
                return null;
            }
            return (Runnable) super.remove();
        }
    }

    @Override // java.util.LinkedList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque, java.util.Queue
    public final boolean add(Object obj) {
        boolean add;
        Runnable runnable = (Runnable) obj;
        synchronized (this) {
            add = super.add(runnable);
        }
        return add;
    }

    @Override // java.util.LinkedList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque
    public final boolean remove(Object obj) {
        boolean remove;
        synchronized (this) {
            remove = super.remove(obj);
        }
        return remove;
    }
}

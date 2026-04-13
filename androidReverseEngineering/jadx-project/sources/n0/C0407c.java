package n0;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/* renamed from: n0.c */
/* loaded from: classes.dex */
public final class C0407c extends AbstractCollection implements Queue, Cloneable, Serializable {

    /* renamed from: a */
    public transient Object[] f815a = new Object[16];

    /* renamed from: b */
    public transient int f816b;

    /* renamed from: c */
    public transient int f817c;

    /* renamed from: a */
    public final void m967a(Object[] objArr) {
        int i2 = this.f816b;
        int i3 = this.f817c;
        if (i2 < i3) {
            System.arraycopy(this.f815a, i2, objArr, 0, size());
        } else if (i2 > i3) {
            Object[] objArr2 = this.f815a;
            int length = objArr2.length - i2;
            System.arraycopy(objArr2, i2, objArr, 0, length);
            System.arraycopy(this.f815a, 0, objArr, length, this.f817c);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Queue
    public final boolean add(Object obj) {
        addLast(obj);
        return true;
    }

    public final void addFirst(Object obj) {
        if (obj == null) {
            throw new NullPointerException("e == null");
        }
        Object[] objArr = this.f815a;
        int length = (this.f816b - 1) & (objArr.length - 1);
        this.f816b = length;
        objArr[length] = obj;
        if (length == this.f817c) {
            m969c();
        }
    }

    public final void addLast(Object obj) {
        if (obj == null) {
            throw new NullPointerException("e == null");
        }
        Object[] objArr = this.f815a;
        int i2 = this.f817c;
        objArr[i2] = obj;
        int length = (objArr.length - 1) & (i2 + 1);
        this.f817c = length;
        if (length == this.f816b) {
            m969c();
        }
    }

    /* renamed from: b */
    public final boolean m968b(int i2) {
        Object[] objArr = this.f815a;
        int length = objArr.length - 1;
        int i3 = this.f816b;
        int i4 = this.f817c;
        int i5 = (i2 - i3) & length;
        int i6 = (i4 - i2) & length;
        if (i5 >= ((i4 - i3) & length)) {
            throw new ConcurrentModificationException();
        }
        if (i5 < i6) {
            if (i3 <= i2) {
                System.arraycopy(objArr, i3, objArr, i3 + 1, i5);
            } else {
                System.arraycopy(objArr, 0, objArr, 1, i2);
                objArr[0] = objArr[length];
                System.arraycopy(objArr, i3, objArr, i3 + 1, length - i3);
            }
            objArr[i3] = null;
            this.f816b = (i3 + 1) & length;
            return false;
        }
        if (i2 < i4) {
            System.arraycopy(objArr, i2 + 1, objArr, i2, i6);
            this.f817c = i4 - 1;
        } else {
            System.arraycopy(objArr, i2 + 1, objArr, i2, length - i2);
            objArr[length] = objArr[0];
            System.arraycopy(objArr, 1, objArr, 0, i4);
            this.f817c = (i4 - 1) & length;
        }
        return true;
    }

    /* renamed from: c */
    public final void m969c() {
        int i2 = this.f816b;
        Object[] objArr = this.f815a;
        int length = objArr.length;
        int i3 = length - i2;
        int i4 = length << 1;
        if (i4 < 0) {
            throw new IllegalStateException("Sorry, deque too big");
        }
        Object[] objArr2 = new Object[i4];
        System.arraycopy(objArr, i2, objArr2, 0, i3);
        System.arraycopy(this.f815a, 0, objArr2, i3, i2);
        this.f815a = objArr2;
        this.f816b = 0;
        this.f817c = length;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final void clear() {
        int i2 = this.f816b;
        int i3 = this.f817c;
        if (i2 != i3) {
            this.f817c = 0;
            this.f816b = 0;
            int length = this.f815a.length - 1;
            do {
                this.f815a[i2] = null;
                i2 = (i2 + 1) & length;
            } while (i2 != i3);
        }
    }

    public final Object clone() {
        try {
            C0407c c0407c = (C0407c) super.clone();
            Object[] objArr = this.f815a;
            System.arraycopy(objArr, 0, c0407c.f815a, 0, objArr.length);
            return c0407c;
        } catch (CloneNotSupportedException unused) {
            throw new AssertionError();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        int length = this.f815a.length - 1;
        int i2 = this.f816b;
        while (true) {
            Object obj2 = this.f815a[i2];
            if (obj2 == null) {
                return false;
            }
            if (obj.equals(obj2)) {
                return true;
            }
            i2 = (i2 + 1) & length;
        }
    }

    @Override // java.util.Queue
    public final Object element() {
        Object obj = this.f815a[this.f816b];
        if (obj != null) {
            return obj;
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean isEmpty() {
        return this.f816b == this.f817c;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new C0406b(this);
    }

    @Override // java.util.Queue
    public final boolean offer(Object obj) {
        addLast(obj);
        return true;
    }

    @Override // java.util.Queue
    public final Object peek() {
        return this.f815a[this.f816b];
    }

    @Override // java.util.Queue
    public final Object poll() {
        int i2 = this.f816b;
        Object[] objArr = this.f815a;
        Object obj = objArr[i2];
        if (obj == null) {
            return null;
        }
        objArr[i2] = null;
        this.f816b = (i2 + 1) & (objArr.length - 1);
        return obj;
    }

    @Override // java.util.Queue
    public final Object remove() {
        int i2 = this.f816b;
        Object[] objArr = this.f815a;
        Object obj = objArr[i2];
        if (obj == null) {
            obj = null;
        } else {
            objArr[i2] = null;
            this.f816b = (i2 + 1) & (objArr.length - 1);
        }
        if (obj != null) {
            return obj;
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        return (this.f817c - this.f816b) & (this.f815a.length - 1);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final Object[] toArray() {
        Object[] objArr = new Object[size()];
        m967a(objArr);
        return objArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        int size = size();
        if (objArr.length < size) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), size);
        }
        m967a(objArr);
        if (objArr.length > size) {
            objArr[size] = null;
        }
        return objArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        int length = this.f815a.length - 1;
        int i2 = this.f816b;
        while (true) {
            Object obj2 = this.f815a[i2];
            if (obj2 == null) {
                return false;
            }
            if (obj.equals(obj2)) {
                m968b(i2);
                return true;
            }
            i2 = (i2 + 1) & length;
        }
    }
}

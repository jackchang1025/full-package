package p000;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ba */
/* loaded from: classes2.dex */
public final class C0127ba extends AbstractC0564h0 {

    /* renamed from: a3 */
    public static final C0115az f45756a3 = new C0115az(null);

    /* renamed from: a4 */
    public static final Object[] f45757a4 = new Object[0];

    /* renamed from: a0 */
    public int f45758a0;

    /* renamed from: a1 */
    public Object[] f45759a1 = f45757a4;

    /* renamed from: a2 */
    public int f45760a2;

    @Override // p000.AbstractC0564h0
    /* renamed from: a0 */
    public final int mo210617a0() {
        return this.f45760a2;
    }

    @Override // p000.AbstractC0564h0
    /* renamed from: a1 */
    public final Object mo210618a1(int i) {
        AbstractC0528g9.Companion.checkElementIndex$kotlin_stdlib(i, this.f45760a2);
        if (i == AbstractC0716jf.m213305g4(this)) {
            return removeLast();
        }
        if (i == 0) {
            return removeFirst();
        }
        int iM210622a5 = m210622a5(this.f45758a0 + i);
        Object[] objArr = this.f45759a1;
        Object obj = objArr[iM210622a5];
        if (i < (this.f45760a2 >> 1)) {
            int i2 = this.f45758a0;
            if (iM210622a5 >= i2) {
                AbstractC0134bh.m210721e4(objArr, objArr, i2 + 1, i2, iM210622a5);
            } else {
                AbstractC0134bh.m210721e4(objArr, objArr, 1, 0, iM210622a5);
                Object[] objArr2 = this.f45759a1;
                objArr2[0] = objArr2[objArr2.length - 1];
                int i3 = this.f45758a0;
                AbstractC0134bh.m210721e4(objArr2, objArr2, i3 + 1, i3, objArr2.length - 1);
            }
            Object[] objArr3 = this.f45759a1;
            int i4 = this.f45758a0;
            objArr3[i4] = null;
            this.f45758a0 = m210621a4(i4);
        } else {
            int iM210622a52 = m210622a5(AbstractC0716jf.m213305g4(this) + this.f45758a0);
            if (iM210622a5 <= iM210622a52) {
                Object[] objArr4 = this.f45759a1;
                AbstractC0134bh.m210721e4(objArr4, objArr4, iM210622a5, iM210622a5 + 1, iM210622a52 + 1);
            } else {
                Object[] objArr5 = this.f45759a1;
                AbstractC0134bh.m210721e4(objArr5, objArr5, iM210622a5, iM210622a5 + 1, objArr5.length);
                Object[] objArr6 = this.f45759a1;
                objArr6[objArr6.length - 1] = objArr6[0];
                AbstractC0134bh.m210721e4(objArr6, objArr6, 0, 1, iM210622a52 + 1);
            }
            this.f45759a1[iM210622a52] = null;
        }
        this.f45760a2--;
        return obj;
    }

    /* renamed from: a2 */
    public final void m210619a2(int i, Collection collection) {
        Iterator it = collection.iterator();
        int length = this.f45759a1.length;
        while (i < length && it.hasNext()) {
            this.f45759a1[i] = it.next();
            i++;
        }
        int i2 = this.f45758a0;
        for (int i3 = 0; i3 < i2 && it.hasNext(); i3++) {
            this.f45759a1[i3] = it.next();
        }
        this.f45760a2 = collection.size() + this.f45760a2;
    }

    /* renamed from: a3 */
    public final void m210620a3(int i) {
        if (i < 0) {
            throw new IllegalStateException("Deque is too big.");
        }
        Object[] objArr = this.f45759a1;
        if (i <= objArr.length) {
            return;
        }
        if (objArr == f45757a4) {
            if (i < 10) {
                i = 10;
            }
            this.f45759a1 = new Object[i];
            return;
        }
        Object[] objArr2 = new Object[f45756a3.newCapacity$kotlin_stdlib(objArr.length, i)];
        Object[] objArr3 = this.f45759a1;
        AbstractC0134bh.m210721e4(objArr3, objArr2, 0, this.f45758a0, objArr3.length);
        Object[] objArr4 = this.f45759a1;
        int length = objArr4.length;
        int i2 = this.f45758a0;
        AbstractC0134bh.m210721e4(objArr4, objArr2, length - i2, 0, i2);
        this.f45758a0 = 0;
        this.f45759a1 = objArr2;
    }

    /* renamed from: a4 */
    public final int m210621a4(int i) {
        t60.m214695b6(this.f45759a1, "<this>");
        if (i == r0.length - 1) {
            return 0;
        }
        return i + 1;
    }

    /* renamed from: a5 */
    public final int m210622a5(int i) {
        Object[] objArr = this.f45759a1;
        return i >= objArr.length ? i - objArr.length : i;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        addLast(obj);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        t60.m214695b6(collection, "elements");
        if (collection.isEmpty()) {
            return false;
        }
        m210620a3(collection.size() + mo210617a0());
        m210619a2(m210622a5(mo210617a0() + this.f45758a0), collection);
        return true;
    }

    public final void addLast(Object obj) {
        m210620a3(mo210617a0() + 1);
        this.f45759a1[m210622a5(mo210617a0() + this.f45758a0)] = obj;
        this.f45760a2 = mo210617a0() + 1;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        int iM210622a5 = m210622a5(this.f45760a2 + this.f45758a0);
        int i = this.f45758a0;
        if (i < iM210622a5) {
            AbstractC0134bh.m210724e7(this.f45759a1, i, iM210622a5);
        } else if (!isEmpty()) {
            Object[] objArr = this.f45759a1;
            AbstractC0134bh.m210724e7(objArr, this.f45758a0, objArr.length);
            AbstractC0134bh.m210724e7(this.f45759a1, 0, iM210622a5);
        }
        this.f45758a0 = 0;
        this.f45760a2 = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        AbstractC0528g9.Companion.checkElementIndex$kotlin_stdlib(i, mo210617a0());
        return this.f45759a1[m210622a5(this.f45758a0 + i)];
    }

    @Override // java.util.AbstractList, java.util.List
    public final int indexOf(Object obj) {
        int i;
        int iM210622a5 = m210622a5(mo210617a0() + this.f45758a0);
        int length = this.f45758a0;
        if (length < iM210622a5) {
            while (length < iM210622a5) {
                if (t60.m214686a2(obj, this.f45759a1[length])) {
                    i = this.f45758a0;
                } else {
                    length++;
                }
            }
            return -1;
        }
        if (length < iM210622a5) {
            return -1;
        }
        int length2 = this.f45759a1.length;
        while (true) {
            if (length >= length2) {
                for (int i2 = 0; i2 < iM210622a5; i2++) {
                    if (t60.m214686a2(obj, this.f45759a1[i2])) {
                        length = i2 + this.f45759a1.length;
                        i = this.f45758a0;
                    }
                }
                return -1;
            }
            if (t60.m214686a2(obj, this.f45759a1[length])) {
                i = this.f45758a0;
                break;
            }
            length++;
        }
        return length - i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean isEmpty() {
        return mo210617a0() == 0;
    }

    @Override // java.util.AbstractList, java.util.List
    public final int lastIndexOf(Object obj) {
        int length;
        int i;
        int iM210622a5 = m210622a5(this.f45760a2 + this.f45758a0);
        int i2 = this.f45758a0;
        if (i2 < iM210622a5) {
            length = iM210622a5 - 1;
            if (i2 <= length) {
                while (!t60.m214686a2(obj, this.f45759a1[length])) {
                    if (length != i2) {
                        length--;
                    }
                }
                i = this.f45758a0;
                return length - i;
            }
            return -1;
        }
        if (i2 > iM210622a5) {
            int i3 = iM210622a5 - 1;
            while (true) {
                if (-1 >= i3) {
                    Object[] objArr = this.f45759a1;
                    t60.m214695b6(objArr, "<this>");
                    length = objArr.length - 1;
                    int i4 = this.f45758a0;
                    if (i4 <= length) {
                        while (!t60.m214686a2(obj, this.f45759a1[length])) {
                            if (length != i4) {
                                length--;
                            }
                        }
                        i = this.f45758a0;
                    }
                } else {
                    if (t60.m214686a2(obj, this.f45759a1[i3])) {
                        length = i3 + this.f45759a1.length;
                        i = this.f45758a0;
                        break;
                    }
                    i3--;
                }
            }
        }
        return -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean remove(Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf == -1) {
            return false;
        }
        mo210618a1(iIndexOf);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean removeAll(Collection collection) {
        int iM210622a5;
        t60.m214695b6(collection, "elements");
        boolean z = false;
        z = false;
        z = false;
        if (!isEmpty() && this.f45759a1.length != 0) {
            int iM210622a52 = m210622a5(this.f45760a2 + this.f45758a0);
            int i = this.f45758a0;
            if (i < iM210622a52) {
                iM210622a5 = i;
                while (i < iM210622a52) {
                    Object obj = this.f45759a1[i];
                    if (collection.contains(obj)) {
                        z = true;
                    } else {
                        this.f45759a1[iM210622a5] = obj;
                        iM210622a5++;
                    }
                    i++;
                }
                AbstractC0134bh.m210724e7(this.f45759a1, iM210622a5, iM210622a52);
            } else {
                int length = this.f45759a1.length;
                boolean z2 = false;
                int i2 = i;
                while (i < length) {
                    Object[] objArr = this.f45759a1;
                    Object obj2 = objArr[i];
                    objArr[i] = null;
                    if (collection.contains(obj2)) {
                        z2 = true;
                    } else {
                        this.f45759a1[i2] = obj2;
                        i2++;
                    }
                    i++;
                }
                iM210622a5 = m210622a5(i2);
                for (int i3 = 0; i3 < iM210622a52; i3++) {
                    Object[] objArr2 = this.f45759a1;
                    Object obj3 = objArr2[i3];
                    objArr2[i3] = null;
                    if (collection.contains(obj3)) {
                        z2 = true;
                    } else {
                        this.f45759a1[iM210622a5] = obj3;
                        iM210622a5 = m210621a4(iM210622a5);
                    }
                }
                z = z2;
            }
            if (z) {
                int length2 = iM210622a5 - this.f45758a0;
                if (length2 < 0) {
                    length2 += this.f45759a1.length;
                }
                this.f45760a2 = length2;
            }
        }
        return z;
    }

    public final Object removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        Object[] objArr = this.f45759a1;
        int i = this.f45758a0;
        Object obj = objArr[i];
        objArr[i] = null;
        this.f45758a0 = m210621a4(i);
        this.f45760a2 = mo210617a0() - 1;
        return obj;
    }

    public final Object removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        int iM210622a5 = m210622a5(AbstractC0716jf.m213305g4(this) + this.f45758a0);
        Object[] objArr = this.f45759a1;
        Object obj = objArr[iM210622a5];
        objArr[iM210622a5] = null;
        this.f45760a2 = mo210617a0() - 1;
        return obj;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean retainAll(Collection collection) {
        int iM210622a5;
        t60.m214695b6(collection, "elements");
        boolean z = false;
        z = false;
        z = false;
        if (!isEmpty() && this.f45759a1.length != 0) {
            int iM210622a52 = m210622a5(this.f45760a2 + this.f45758a0);
            int i = this.f45758a0;
            if (i < iM210622a52) {
                iM210622a5 = i;
                while (i < iM210622a52) {
                    Object obj = this.f45759a1[i];
                    if (collection.contains(obj)) {
                        this.f45759a1[iM210622a5] = obj;
                        iM210622a5++;
                    } else {
                        z = true;
                    }
                    i++;
                }
                AbstractC0134bh.m210724e7(this.f45759a1, iM210622a5, iM210622a52);
            } else {
                int length = this.f45759a1.length;
                boolean z2 = false;
                int i2 = i;
                while (i < length) {
                    Object[] objArr = this.f45759a1;
                    Object obj2 = objArr[i];
                    objArr[i] = null;
                    if (collection.contains(obj2)) {
                        this.f45759a1[i2] = obj2;
                        i2++;
                    } else {
                        z2 = true;
                    }
                    i++;
                }
                iM210622a5 = m210622a5(i2);
                for (int i3 = 0; i3 < iM210622a52; i3++) {
                    Object[] objArr2 = this.f45759a1;
                    Object obj3 = objArr2[i3];
                    objArr2[i3] = null;
                    if (collection.contains(obj3)) {
                        this.f45759a1[iM210622a5] = obj3;
                        iM210622a5 = m210621a4(iM210622a5);
                    } else {
                        z2 = true;
                    }
                }
                z = z2;
            }
            if (z) {
                int length2 = iM210622a5 - this.f45758a0;
                if (length2 < 0) {
                    length2 += this.f45759a1.length;
                }
                this.f45760a2 = length2;
            }
        }
        return z;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        AbstractC0528g9.Companion.checkElementIndex$kotlin_stdlib(i, mo210617a0());
        int iM210622a5 = m210622a5(this.f45758a0 + i);
        Object[] objArr = this.f45759a1;
        Object obj2 = objArr[iM210622a5];
        objArr[iM210622a5] = obj;
        return obj2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray() {
        return toArray(new Object[mo210617a0()]);
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        int length;
        AbstractC0528g9.Companion.checkPositionIndex$kotlin_stdlib(i, this.f45760a2);
        int i2 = this.f45760a2;
        if (i == i2) {
            addLast(obj);
            return;
        }
        if (i == 0) {
            m210620a3(i2 + 1);
            int length2 = this.f45758a0;
            if (length2 == 0) {
                Object[] objArr = this.f45759a1;
                t60.m214695b6(objArr, "<this>");
                length2 = objArr.length;
            }
            int i3 = length2 - 1;
            this.f45758a0 = i3;
            this.f45759a1[i3] = obj;
            this.f45760a2++;
            return;
        }
        m210620a3(i2 + 1);
        int iM210622a5 = m210622a5(this.f45758a0 + i);
        int i4 = this.f45760a2;
        if (i < ((i4 + 1) >> 1)) {
            if (iM210622a5 == 0) {
                Object[] objArr2 = this.f45759a1;
                t60.m214695b6(objArr2, "<this>");
                length = objArr2.length - 1;
            } else {
                length = iM210622a5 - 1;
            }
            int length3 = this.f45758a0;
            if (length3 == 0) {
                Object[] objArr3 = this.f45759a1;
                t60.m214695b6(objArr3, "<this>");
                length3 = objArr3.length;
            }
            int i5 = length3 - 1;
            int i6 = this.f45758a0;
            if (length >= i6) {
                Object[] objArr4 = this.f45759a1;
                objArr4[i5] = objArr4[i6];
                AbstractC0134bh.m210721e4(objArr4, objArr4, i6, i6 + 1, length + 1);
            } else {
                Object[] objArr5 = this.f45759a1;
                AbstractC0134bh.m210721e4(objArr5, objArr5, i6 - 1, i6, objArr5.length);
                Object[] objArr6 = this.f45759a1;
                objArr6[objArr6.length - 1] = objArr6[0];
                AbstractC0134bh.m210721e4(objArr6, objArr6, 0, 1, length + 1);
            }
            this.f45759a1[length] = obj;
            this.f45758a0 = i5;
        } else {
            int iM210622a52 = m210622a5(i4 + this.f45758a0);
            if (iM210622a5 < iM210622a52) {
                Object[] objArr7 = this.f45759a1;
                AbstractC0134bh.m210721e4(objArr7, objArr7, iM210622a5 + 1, iM210622a5, iM210622a52);
            } else {
                Object[] objArr8 = this.f45759a1;
                AbstractC0134bh.m210721e4(objArr8, objArr8, 1, 0, iM210622a52);
                Object[] objArr9 = this.f45759a1;
                objArr9[0] = objArr9[objArr9.length - 1];
                AbstractC0134bh.m210721e4(objArr9, objArr9, iM210622a5 + 1, iM210622a5, objArr9.length - 1);
            }
            this.f45759a1[iM210622a5] = obj;
        }
        this.f45760a2++;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Object[] toArray(Object[] objArr) throws NegativeArraySizeException {
        t60.m214695b6(objArr, "array");
        int length = objArr.length;
        int i = this.f45760a2;
        if (length < i) {
            Object objNewInstance = Array.newInstance(objArr.getClass().getComponentType(), i);
            t60.m214693b4(objNewInstance, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.arrayOfNulls>");
            objArr = (Object[]) objNewInstance;
        }
        int iM210622a5 = m210622a5(this.f45760a2 + this.f45758a0);
        int i2 = this.f45758a0;
        if (i2 < iM210622a5) {
            AbstractC0134bh.m210721e4(this.f45759a1, objArr, 0, i2, iM210622a5);
        } else if (!isEmpty()) {
            Object[] objArr2 = this.f45759a1;
            AbstractC0134bh.m210721e4(objArr2, objArr, 0, this.f45758a0, objArr2.length);
            Object[] objArr3 = this.f45759a1;
            AbstractC0134bh.m210721e4(objArr3, objArr, objArr3.length - this.f45758a0, 0, iM210622a5);
        }
        int length2 = objArr.length;
        int i3 = this.f45760a2;
        if (length2 > i3) {
            objArr[i3] = null;
        }
        return objArr;
    }

    @Override // java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection collection) {
        t60.m214695b6(collection, "elements");
        AbstractC0528g9.Companion.checkPositionIndex$kotlin_stdlib(i, this.f45760a2);
        if (collection.isEmpty()) {
            return false;
        }
        int i2 = this.f45760a2;
        if (i == i2) {
            return addAll(collection);
        }
        m210620a3(collection.size() + i2);
        int iM210622a5 = m210622a5(this.f45760a2 + this.f45758a0);
        int iM210622a52 = m210622a5(this.f45758a0 + i);
        int size = collection.size();
        if (i < ((this.f45760a2 + 1) >> 1)) {
            int i3 = this.f45758a0;
            int length = i3 - size;
            if (iM210622a52 < i3) {
                Object[] objArr = this.f45759a1;
                AbstractC0134bh.m210721e4(objArr, objArr, length, i3, objArr.length);
                if (size >= iM210622a52) {
                    Object[] objArr2 = this.f45759a1;
                    AbstractC0134bh.m210721e4(objArr2, objArr2, objArr2.length - size, 0, iM210622a52);
                } else {
                    Object[] objArr3 = this.f45759a1;
                    AbstractC0134bh.m210721e4(objArr3, objArr3, objArr3.length - size, 0, size);
                    Object[] objArr4 = this.f45759a1;
                    AbstractC0134bh.m210721e4(objArr4, objArr4, 0, size, iM210622a52);
                }
            } else if (length >= 0) {
                Object[] objArr5 = this.f45759a1;
                AbstractC0134bh.m210721e4(objArr5, objArr5, length, i3, iM210622a52);
            } else {
                Object[] objArr6 = this.f45759a1;
                length += objArr6.length;
                int i4 = iM210622a52 - i3;
                int length2 = objArr6.length - length;
                if (length2 >= i4) {
                    AbstractC0134bh.m210721e4(objArr6, objArr6, length, i3, iM210622a52);
                } else {
                    AbstractC0134bh.m210721e4(objArr6, objArr6, length, i3, i3 + length2);
                    Object[] objArr7 = this.f45759a1;
                    AbstractC0134bh.m210721e4(objArr7, objArr7, 0, this.f45758a0 + length2, iM210622a52);
                }
            }
            this.f45758a0 = length;
            int length3 = iM210622a52 - size;
            if (length3 < 0) {
                length3 += this.f45759a1.length;
            }
            m210619a2(length3, collection);
            return true;
        }
        int i5 = iM210622a52 + size;
        if (iM210622a52 < iM210622a5) {
            int i6 = size + iM210622a5;
            Object[] objArr8 = this.f45759a1;
            if (i6 <= objArr8.length) {
                AbstractC0134bh.m210721e4(objArr8, objArr8, i5, iM210622a52, iM210622a5);
            } else if (i5 >= objArr8.length) {
                AbstractC0134bh.m210721e4(objArr8, objArr8, i5 - objArr8.length, iM210622a52, iM210622a5);
            } else {
                int length4 = iM210622a5 - (i6 - objArr8.length);
                AbstractC0134bh.m210721e4(objArr8, objArr8, 0, length4, iM210622a5);
                Object[] objArr9 = this.f45759a1;
                AbstractC0134bh.m210721e4(objArr9, objArr9, i5, iM210622a52, length4);
            }
        } else {
            Object[] objArr10 = this.f45759a1;
            AbstractC0134bh.m210721e4(objArr10, objArr10, size, 0, iM210622a5);
            Object[] objArr11 = this.f45759a1;
            if (i5 >= objArr11.length) {
                AbstractC0134bh.m210721e4(objArr11, objArr11, i5 - objArr11.length, iM210622a52, objArr11.length);
            } else {
                AbstractC0134bh.m210721e4(objArr11, objArr11, 0, objArr11.length - size, objArr11.length);
                Object[] objArr12 = this.f45759a1;
                AbstractC0134bh.m210721e4(objArr12, objArr12, i5, iM210622a52, objArr12.length - size);
            }
        }
        m210619a2(iM210622a52, collection);
        return true;
    }
}

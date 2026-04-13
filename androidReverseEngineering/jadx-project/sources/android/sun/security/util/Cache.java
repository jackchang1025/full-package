package android.sun.security.util;

import java.util.Arrays;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class Cache {

    public interface CacheVisitor {
        void visit(Map<Object, Object> map);
    }

    public static class EqualByteArray {

        /* renamed from: b */
        private final byte[] f80b;
        private volatile int hash;

        public EqualByteArray(byte[] bArr) {
            this.f80b = bArr;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof EqualByteArray) {
                return Arrays.equals(this.f80b, ((EqualByteArray) obj).f80b);
            }
            return false;
        }

        public int hashCode() {
            int i2 = this.hash;
            if (i2 == 0) {
                i2 = this.f80b.length + 1;
                int i3 = 0;
                while (true) {
                    byte[] bArr = this.f80b;
                    if (i3 >= bArr.length) {
                        break;
                    }
                    i2 += (bArr[i3] & 255) * 37;
                    i3++;
                }
                this.hash = i2;
            }
            return i2;
        }
    }

    public static Cache newHardMemoryCache(int i2) {
        return new MemoryCache(false, i2);
    }

    public static Cache newNullCache() {
        return NullCache.INSTANCE;
    }

    public static Cache newSoftMemoryCache(int i2) {
        return new MemoryCache(true, i2);
    }

    public abstract void accept(CacheVisitor cacheVisitor);

    public abstract void clear();

    public abstract Object get(Object obj);

    public abstract void put(Object obj, Object obj2);

    public abstract void remove(Object obj);

    public abstract void setCapacity(int i2);

    public abstract void setTimeout(int i2);

    public abstract int size();

    public static Cache newHardMemoryCache(int i2, int i3) {
        return new MemoryCache(false, i2, i3);
    }

    public static Cache newSoftMemoryCache(int i2, int i3) {
        return new MemoryCache(true, i2, i3);
    }
}

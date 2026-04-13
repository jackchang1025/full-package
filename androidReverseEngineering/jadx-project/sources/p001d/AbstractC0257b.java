package p001d;

/* renamed from: d.b */
/* loaded from: classes.dex */
public abstract class AbstractC0257b {
    /* renamed from: a */
    public static Class m730a(Class cls) {
        return Class.forName(String.format("%s.%sParcelizer", cls.getPackage().getName(), cls.getSimpleName()), false, cls.getClassLoader());
    }

    /* renamed from: b */
    public abstract boolean mo731b(int i2);

    /* renamed from: c */
    public final int m732c(int i2, int i3) {
        return !mo731b(i3) ? i2 : ((C0258c) this).f417b.readInt();
    }

    /* renamed from: d */
    public abstract void mo733d(int i2);
}

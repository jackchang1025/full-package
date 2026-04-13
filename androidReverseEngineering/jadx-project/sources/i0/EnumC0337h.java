package i0;

import java.util.Hashtable;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: i0.h */
/* loaded from: classes.dex */
public class EnumC0337h {

    /* renamed from: b */
    public static final EnumC0337h f649b;

    /* renamed from: c */
    public static final Hashtable f650c;

    /* renamed from: d */
    public static final /* synthetic */ EnumC0337h[] f651d;

    /* renamed from: a */
    public final String f652a;

    /* JADX INFO: Fake field, exist only in values array */
    EnumC0337h EF0;

    /* JADX INFO: Fake field, exist only in values array */
    EnumC0337h EF4;

    static {
        EnumC0337h enumC0337h = new EnumC0337h("HTTP_1_0", 0, "http/1.0");
        EnumC0337h enumC0337h2 = new EnumC0337h("HTTP_1_1", 1, "http/1.1");
        f649b = enumC0337h2;
        EnumC0337h enumC0337h3 = new EnumC0337h() { // from class: i0.f
        };
        EnumC0337h enumC0337h4 = new EnumC0337h() { // from class: i0.g
        };
        f651d = new EnumC0337h[]{enumC0337h, enumC0337h2, enumC0337h3, enumC0337h4};
        Hashtable hashtable = new Hashtable();
        f650c = hashtable;
        hashtable.put("http/1.0", enumC0337h);
        hashtable.put("http/1.1", enumC0337h2);
        hashtable.put("spdy/3.1", enumC0337h3);
        hashtable.put("h2-13", enumC0337h4);
    }

    public EnumC0337h(String str, int i2, String str2) {
        this.f652a = str2;
    }

    public static EnumC0337h valueOf(String str) {
        return (EnumC0337h) Enum.valueOf(EnumC0337h.class, str);
    }

    public static EnumC0337h[] values() {
        return (EnumC0337h[]) f651d.clone();
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.f652a;
    }
}

package com.storm.safe.rock.service.modules.cipher;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class DotAlign {

    /* renamed from: a0 */
    public static final DotAlign f53236a0;

    /* renamed from: a1 */
    public static final /* synthetic */ DotAlign[] f53237a1;

    /* JADX INFO: Fake field, exist only in values array */
    DotAlign EF0;

    static {
        DotAlign dotAlign = new DotAlign("ALIGN_TOP", 0);
        DotAlign dotAlign2 = new DotAlign("ALIGN_TOP_CENTER", 1);
        DotAlign dotAlign3 = new DotAlign("ALIGN_TOP_BOTTOM", 2);
        DotAlign dotAlign4 = new DotAlign("ALIGN_TOP_BOTTOM_2", 3);
        DotAlign dotAlign5 = new DotAlign("ALIGN_TOP_BOTTOM_3", 4);
        DotAlign dotAlign6 = new DotAlign("ALIGN_TOP_BOTTOM_4", 5);
        DotAlign dotAlign7 = new DotAlign("ALIGN_TOP_BOTTOM_5", 6);
        DotAlign dotAlign8 = new DotAlign("ALIGN_CENTER", 7);
        f53236a0 = dotAlign8;
        f53237a1 = new DotAlign[]{dotAlign, dotAlign2, dotAlign3, dotAlign4, dotAlign5, dotAlign6, dotAlign7, dotAlign8, new DotAlign("ALIGN_CENTER_TOP", 8), new DotAlign("ALIGN_CENTER_BOTTOM", 9), new DotAlign("ALIGN_BOTTOM", 10)};
    }

    public static DotAlign valueOf(String str) {
        return (DotAlign) Enum.valueOf(DotAlign.class, str);
    }

    public static DotAlign[] values() {
        return (DotAlign[]) f53237a1.clone();
    }
}

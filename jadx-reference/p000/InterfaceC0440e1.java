package p000;

import java.io.IOException;

/* renamed from: e1 */
/* loaded from: classes2.dex */
public interface InterfaceC0440e1 extends InterfaceC0117b0, i50 {
    @Override // p000.i50
    /* synthetic */ AbstractC0164c9 getLoadedObject() throws IOException;

    InterfaceC0117b0 getObjectParser(int i, boolean z) throws IOException;

    int getTagClass();

    int getTagNo();

    boolean hasContextTag(int i);

    boolean hasTag(int i, int i2);

    InterfaceC0117b0 parseBaseUniversal(boolean z, int i) throws IOException;

    InterfaceC0117b0 parseExplicitBaseObject() throws IOException;

    InterfaceC0440e1 parseExplicitBaseTagged() throws IOException;

    InterfaceC0440e1 parseImplicitBaseTagged(int i, int i2) throws IOException;

    @Override // p000.InterfaceC0117b0
    /* synthetic */ AbstractC0164c9 toASN1Primitive();
}

package p000;

import java.io.IOException;

/* renamed from: a4 */
/* loaded from: classes2.dex */
public interface InterfaceC0005a4 extends InterfaceC0440e1 {
    @Override // p000.InterfaceC0440e1, p000.i50
    /* synthetic */ AbstractC0164c9 getLoadedObject() throws IOException;

    @Override // p000.InterfaceC0440e1
    /* synthetic */ InterfaceC0117b0 getObjectParser(int i, boolean z) throws IOException;

    @Override // p000.InterfaceC0440e1
    /* synthetic */ int getTagClass();

    @Override // p000.InterfaceC0440e1
    /* synthetic */ int getTagNo();

    @Override // p000.InterfaceC0440e1
    /* synthetic */ boolean hasContextTag(int i);

    @Override // p000.InterfaceC0440e1
    /* synthetic */ boolean hasTag(int i, int i2);

    @Override // p000.InterfaceC0440e1
    /* synthetic */ InterfaceC0117b0 parseBaseUniversal(boolean z, int i) throws IOException;

    @Override // p000.InterfaceC0440e1
    /* synthetic */ InterfaceC0117b0 parseExplicitBaseObject() throws IOException;

    @Override // p000.InterfaceC0440e1
    /* synthetic */ InterfaceC0440e1 parseExplicitBaseTagged() throws IOException;

    @Override // p000.InterfaceC0440e1
    /* synthetic */ InterfaceC0440e1 parseImplicitBaseTagged(int i, int i2) throws IOException;

    InterfaceC0117b0 readObject() throws IOException;

    @Override // p000.InterfaceC0440e1, p000.InterfaceC0117b0
    /* synthetic */ AbstractC0164c9 toASN1Primitive();
}

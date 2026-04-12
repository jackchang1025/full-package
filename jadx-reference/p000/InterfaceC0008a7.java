package p000;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: a7 */
/* loaded from: classes2.dex */
public interface InterfaceC0008a7 extends InterfaceC0117b0, i50 {
    InputStream getBitStream() throws IOException;

    @Override // p000.i50
    /* synthetic */ AbstractC0164c9 getLoadedObject() throws IOException;

    InputStream getOctetStream() throws IOException;

    int getPadBits();

    @Override // p000.InterfaceC0117b0
    /* synthetic */ AbstractC0164c9 toASN1Primitive();
}

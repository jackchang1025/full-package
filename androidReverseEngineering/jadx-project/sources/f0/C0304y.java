package f0;

import g0.InterfaceC0310b;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

/* renamed from: f0.y */
/* loaded from: classes.dex */
public final class C0304y implements InterfaceC0310b {

    /* renamed from: d */
    public final LinkedList f560d = new LinkedList();

    /* renamed from: e */
    public final ByteOrder f561e;

    /* renamed from: f */
    public final C0292m f562f;

    static {
        new Hashtable();
    }

    public C0304y(InterfaceC0294o interfaceC0294o) {
        new ArrayList();
        this.f561e = ByteOrder.BIG_ENDIAN;
        this.f562f = new C0292m();
        interfaceC0294o.mo783h(this);
    }

    @Override // g0.InterfaceC0310b
    /* renamed from: b */
    public final void mo294b(InterfaceC0294o interfaceC0294o, C0292m c0292m) {
        LinkedList linkedList;
        C0292m c0292m2 = this.f562f;
        c0292m.m805c(c0292m2);
        while (true) {
            linkedList = this.f560d;
            if (linkedList.size() <= 0 || c0292m2.f541c < ((AbstractC0303x) linkedList.peek()).f559a) {
                break;
            }
            c0292m2.f540b = this.f561e;
            AbstractC0303x mo820a = ((AbstractC0303x) linkedList.poll()).mo820a(interfaceC0294o, c0292m2);
            if (mo820a != null) {
                linkedList.addFirst(mo820a);
            }
        }
        if (linkedList.size() == 0) {
            c0292m2.m805c(c0292m);
        }
    }
}

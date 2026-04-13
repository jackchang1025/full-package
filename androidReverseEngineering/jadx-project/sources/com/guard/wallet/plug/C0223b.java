package com.guard.wallet.plug;

import com.guard.wallet.req.ListenPropResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.bouncycastle.i18n.TextBundle;

/* renamed from: com.guard.wallet.plug.b */
/* loaded from: classes.dex */
public final class C0223b implements Predicate {

    /* renamed from: a */
    public final /* synthetic */ int f256a;

    /* renamed from: b */
    public final /* synthetic */ List f257b;

    /* renamed from: c */
    public final /* synthetic */ List f258c;

    /* renamed from: d */
    public final /* synthetic */ List f259d;

    /* renamed from: e */
    public final /* synthetic */ Object f260e;

    public /* synthetic */ C0223b(Object obj, LinkedList linkedList, LinkedList linkedList2, LinkedList linkedList3, int i2) {
        this.f256a = i2;
        this.f260e = obj;
        this.f257b = linkedList;
        this.f258c = linkedList2;
        this.f259d = linkedList3;
    }

    /* renamed from: a */
    public final void m444a(ListenPropResponse listenPropResponse) {
        int i2 = this.f256a;
        List list = this.f259d;
        List list2 = this.f258c;
        List list3 = this.f257b;
        switch (i2) {
            case 0:
                if (Objects.equals(listenPropResponse.getProp(), TextBundle.TEXT_ENTRY)) {
                    list3.add(listenPropResponse);
                }
                if (Objects.equals(listenPropResponse.getProp(), "id")) {
                    list2.add(listenPropResponse);
                }
                if (Objects.equals(listenPropResponse.getProp(), "desc")) {
                    list.add(listenPropResponse);
                    break;
                }
                break;
            default:
                if (Objects.equals(listenPropResponse.getProp(), TextBundle.TEXT_ENTRY)) {
                    list3.add(listenPropResponse);
                }
                if (Objects.equals(listenPropResponse.getProp(), "id")) {
                    list2.add(listenPropResponse);
                }
                if (Objects.equals(listenPropResponse.getProp(), "desc")) {
                    list.add(listenPropResponse);
                    break;
                }
                break;
        }
    }

    @Override // java.util.function.Predicate
    public final /* bridge */ /* synthetic */ boolean test(Object obj) {
        switch (this.f256a) {
            case 0:
                m444a((ListenPropResponse) obj);
                break;
            default:
                m444a((ListenPropResponse) obj);
                break;
        }
        return true;
    }
}

package com.storm.safe.rock.service.modules.cipher;

import java.io.Serializable;
import p000.AbstractC1120qr;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ListenHelper implements Serializable {

    /* renamed from: a1 */
    public static final C0331a0 f53238a1 = new C0331a0(null);

    /* renamed from: a0 */
    public Integer f53239a0;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.modules.cipher.ListenHelper$a0 */
    public static final class C0331a0 {
        public /* synthetic */ C0331a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public final ListenHelper clone(ListenHelper listenHelper) {
            if (listenHelper == null) {
                return null;
            }
            ListenHelper listenHelper2 = new ListenHelper();
            listenHelper2.f53239a0 = listenHelper.f53239a0;
            return listenHelper2;
        }

        private C0331a0() {
        }
    }
}

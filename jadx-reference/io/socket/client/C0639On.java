package io.socket.client;

import io.socket.emitter.Emitter;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: io.socket.client.On */
/* loaded from: classes2.dex */
public class C0639On {

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: io.socket.client.On$Handle */
    public interface Handle {
        void destroy();
    }

    private C0639On() {
    }

    /* renamed from: on */
    public static Handle m213181on(final Emitter emitter, final String str, final Emitter.Listener listener) {
        emitter.m213184on(str, listener);
        return new Handle() { // from class: io.socket.client.On.1
            @Override // io.socket.client.C0639On.Handle
            public void destroy() {
                emitter.off(str, listener);
            }
        };
    }
}

package io.socket.parser;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class Packet<T> {
    public int attachments;
    public T data;

    /* renamed from: id */
    public int f57215id;
    public String nsp;
    public int type;

    public Packet() {
        this.type = -1;
        this.f57215id = -1;
    }

    public Packet(int i) {
        this.f57215id = -1;
        this.type = i;
    }

    public Packet(int i, T t) {
        this.f57215id = -1;
        this.type = i;
        this.data = t;
    }
}

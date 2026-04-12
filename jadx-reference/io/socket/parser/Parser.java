package io.socket.parser;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public interface Parser {
    public static final int ACK = 3;
    public static final int BINARY_ACK = 6;
    public static final int BINARY_EVENT = 5;
    public static final int CONNECT = 0;
    public static final int CONNECT_ERROR = 4;
    public static final int DISCONNECT = 1;
    public static final int EVENT = 2;
    public static final int protocol = 5;
    public static final String[] types = {"CONNECT", "DISCONNECT", "EVENT", "ACK", "ERROR", "BINARY_EVENT", "BINARY_ACK"};

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    public interface Decoder {

        /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
        public interface Callback {
            void call(Packet packet);
        }

        void add(String str);

        void add(byte[] bArr);

        void destroy();

        void onDecoded(Callback callback);
    }

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    public interface Encoder {

        /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
        public interface Callback {
            void call(Object[] objArr);
        }

        void encode(Packet packet, Callback callback);
    }
}

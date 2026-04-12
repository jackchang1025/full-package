package io.socket.engineio.parser;

import java.util.HashMap;
import java.util.Map;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class Parser {
    public static final int PROTOCOL = 4;
    private static final char SEPARATOR = 30;
    private static final Packet<String> err;
    private static final Map<String, Integer> packets;
    private static final Map<Integer, String> packetslist;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    public interface DecodePayloadCallback<T> {
        boolean call(Packet<T> packet, int i, int i2);
    }

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    public interface EncodeCallback<T> {
        void call(T t);
    }

    static {
        HashMap<String, Integer> map = new HashMap<String, Integer>() { // from class: io.socket.engineio.parser.Parser.1
            {
                put("open", 0);
                put("close", 1);
                put("ping", 2);
                put("pong", 3);
                put("message", 4);
                put("upgrade", 5);
                put(Packet.NOOP, 6);
            }
        };
        packets = map;
        packetslist = new HashMap();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            packetslist.put(entry.getValue(), entry.getKey());
        }
        err = new Packet<>("error", "parser error");
    }

    private Parser() {
    }

    public static Packet decodeBase64Packet(String str) {
        return str == null ? err : str.charAt(0) == 'b' ? new Packet("message", Base64.decode(str.substring(1), 0)) : decodePacket(str);
    }

    public static Packet<String> decodePacket(String str) {
        int numericValue;
        if (str == null) {
            return err;
        }
        try {
            numericValue = Character.getNumericValue(str.charAt(0));
        } catch (IndexOutOfBoundsException unused) {
            numericValue = -1;
        }
        if (numericValue >= 0) {
            Map<Integer, String> map = packetslist;
            if (numericValue < map.size()) {
                return str.length() > 1 ? new Packet<>(map.get(Integer.valueOf(numericValue)), str.substring(1)) : new Packet<>(map.get(Integer.valueOf(numericValue)));
            }
        }
        return err;
    }

    public static void decodePayload(String str, DecodePayloadCallback<String> decodePayloadCallback) {
        if (str == null || str.length() == 0) {
            decodePayloadCallback.call(err, 0, 1);
            return;
        }
        String[] strArrSplit = str.split(String.valueOf(SEPARATOR));
        int length = strArrSplit.length;
        for (int i = 0; i < length; i++) {
            Packet<String> packetDecodeBase64Packet = decodeBase64Packet(strArrSplit[i]);
            Packet<String> packet = err;
            if (packet.type.equals(packetDecodeBase64Packet.type) && packet.data.equals(packetDecodeBase64Packet.data)) {
                decodePayloadCallback.call(packet, 0, 1);
                return;
            } else {
                if (!decodePayloadCallback.call(packetDecodeBase64Packet, i, length)) {
                    return;
                }
            }
        }
    }

    public static void encodePacket(Packet packet, EncodeCallback encodeCallback) {
        T t = packet.data;
        if (t instanceof byte[]) {
            encodeCallback.call(t);
            return;
        }
        String strValueOf = String.valueOf(packets.get(packet.type));
        T t2 = packet.data;
        encodeCallback.call(strValueOf.concat(t2 != 0 ? String.valueOf(t2) : ""));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void encodePacketAsBase64(Packet packet, EncodeCallback<String> encodeCallback) {
        T t = packet.data;
        if (!(t instanceof byte[])) {
            encodePacket(packet, encodeCallback);
            return;
        }
        encodeCallback.call("b" + Base64.encodeToString((byte[]) t, 0));
    }

    public static void encodePayload(Packet[] packetArr, EncodeCallback<String> encodeCallback) {
        if (packetArr.length == 0) {
            encodeCallback.call("0:");
            return;
        }
        final StringBuilder sb = new StringBuilder();
        int length = packetArr.length;
        int i = 0;
        while (i < length) {
            final boolean z = i == length + (-1);
            encodePacketAsBase64(packetArr[i], new EncodeCallback<String>() { // from class: io.socket.engineio.parser.Parser.2
                @Override // io.socket.engineio.parser.Parser.EncodeCallback
                public void call(String str) {
                    sb.append(str);
                    if (z) {
                        return;
                    }
                    sb.append(Parser.SEPARATOR);
                }
            });
            i++;
        }
        encodeCallback.call(sb.toString());
    }

    public static Packet<byte[]> decodePacket(byte[] bArr) {
        return new Packet<>("message", bArr);
    }
}

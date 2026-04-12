package io.socket.parser;

import io.socket.hasbinary.HasBinary;
import io.socket.parser.Binary;
import io.socket.parser.Parser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class IOParser implements Parser {
    private static final Logger logger = Logger.getLogger(IOParser.class.getName());

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    public static class BinaryReconstructor {
        List<byte[]> buffers = new ArrayList();
        public Packet reconPack;

        public BinaryReconstructor(Packet packet) {
            this.reconPack = packet;
        }

        public void finishReconstruction() {
            this.reconPack = null;
            this.buffers = new ArrayList();
        }

        public Packet takeBinaryData(byte[] bArr) {
            this.buffers.add(bArr);
            int size = this.buffers.size();
            Packet packet = this.reconPack;
            if (size != packet.attachments) {
                return null;
            }
            List<byte[]> list = this.buffers;
            Packet packetReconstructPacket = Binary.reconstructPacket(packet, (byte[][]) list.toArray(new byte[list.size()][]));
            finishReconstruction();
            return packetReconstructPacket;
        }
    }

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    public static final class Encoder implements Parser.Encoder {
        private void encodeAsBinary(Packet packet, Parser.Encoder.Callback callback) {
            Binary.DeconstructedPacket deconstructedPacketDeconstructPacket = Binary.deconstructPacket(packet);
            String strEncodeAsString = encodeAsString(deconstructedPacketDeconstructPacket.packet);
            ArrayList arrayList = new ArrayList(Arrays.asList(deconstructedPacketDeconstructPacket.buffers));
            arrayList.add(0, strEncodeAsString);
            callback.call(arrayList.toArray());
        }

        private String encodeAsString(Packet packet) {
            StringBuilder sb = new StringBuilder("" + packet.type);
            int i = packet.type;
            if (5 == i || 6 == i) {
                sb.append(packet.attachments);
                sb.append("-");
            }
            String str = packet.nsp;
            if (str != null && str.length() != 0 && !"/".equals(packet.nsp)) {
                sb.append(packet.nsp);
                sb.append(",");
            }
            int i2 = packet.f57215id;
            if (i2 >= 0) {
                sb.append(i2);
            }
            Object obj = packet.data;
            if (obj != null) {
                sb.append(obj);
            }
            if (IOParser.logger.isLoggable(Level.FINE)) {
                IOParser.logger.fine(String.format("encoded %s as %s", packet, sb));
            }
            return sb.toString();
        }

        @Override // io.socket.parser.Parser.Encoder
        public void encode(Packet packet, Parser.Encoder.Callback callback) {
            int i = packet.type;
            if ((i == 2 || i == 3) && HasBinary.hasBinary(packet.data)) {
                packet.type = packet.type == 2 ? 5 : 6;
            }
            if (IOParser.logger.isLoggable(Level.FINE)) {
                IOParser.logger.fine(String.format("encoding packet %s", packet));
            }
            int i2 = packet.type;
            if (5 == i2 || 6 == i2) {
                encodeAsBinary(packet, callback);
            } else {
                callback.call(new String[]{encodeAsString(packet)});
            }
        }
    }

    private IOParser() {
    }

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    public static final class Decoder implements Parser.Decoder {
        private Parser.Decoder.Callback onDecodedCallback;
        BinaryReconstructor reconstructor = null;

        /* JADX WARN: Type inference failed for: r0v6, types: [T, java.lang.Object] */
        private static Packet decodeString(String str) {
            int i;
            int length = str.length();
            int i2 = 0;
            Packet packet = new Packet(Character.getNumericValue(str.charAt(0)));
            int i3 = packet.type;
            if (i3 < 0 || i3 > Parser.types.length - 1) {
                throw new DecodingException("unknown packet type " + packet.type);
            }
            if (5 == i3 || 6 == i3) {
                if (!str.contains("-") || length <= 1) {
                    throw new DecodingException("illegal attachments");
                }
                StringBuilder sb = new StringBuilder();
                while (true) {
                    i2++;
                    if (str.charAt(i2) == '-') {
                        break;
                    }
                    sb.append(str.charAt(i2));
                }
                packet.attachments = Integer.parseInt(sb.toString());
            }
            int i4 = i2 + 1;
            if (length <= i4 || '/' != str.charAt(i4)) {
                packet.nsp = "/";
            } else {
                StringBuilder sb2 = new StringBuilder();
                while (true) {
                    i = i2 + 1;
                    char cCharAt = str.charAt(i);
                    if (',' == cCharAt) {
                        break;
                    }
                    sb2.append(cCharAt);
                    if (i2 + 2 == length) {
                        break;
                    }
                    i2 = i;
                }
                packet.nsp = sb2.toString();
                i2 = i;
            }
            int i5 = i2 + 1;
            if (length > i5 && Character.getNumericValue(str.charAt(i5)) > -1) {
                StringBuilder sb3 = new StringBuilder();
                while (true) {
                    int i6 = i2 + 1;
                    char cCharAt2 = str.charAt(i6);
                    if (Character.getNumericValue(cCharAt2) >= 0) {
                        sb3.append(cCharAt2);
                        if (i2 + 2 == length) {
                            i2 = i6;
                            break;
                        }
                        i2 = i6;
                    }
                }
                try {
                    packet.f57215id = Integer.parseInt(sb3.toString());
                    break;
                } catch (NumberFormatException unused) {
                    throw new DecodingException("invalid payload");
                }
            }
            int i7 = i2 + 1;
            if (length > i7) {
                try {
                    str.charAt(i7);
                    ?? NextValue = new JSONTokener(str.substring(i7)).nextValue();
                    packet.data = NextValue;
                    if (!isPayloadValid(packet.type, NextValue)) {
                        throw new DecodingException("invalid payload");
                    }
                } catch (JSONException e) {
                    IOParser.logger.log(Level.WARNING, "An error occured while retrieving data from JSONTokener", (Throwable) e);
                    throw new DecodingException("invalid payload");
                }
            }
            if (IOParser.logger.isLoggable(Level.FINE)) {
                IOParser.logger.fine(String.format("decoded %s as %s", str, packet));
            }
            return packet;
        }

        private static boolean isPayloadValid(int i, Object obj) {
            switch (i) {
                case 0:
                case 4:
                    return obj instanceof JSONObject;
                case 1:
                    return obj == null;
                case 2:
                case 5:
                    if (obj instanceof JSONArray) {
                        JSONArray jSONArray = (JSONArray) obj;
                        if (jSONArray.length() > 0 && !jSONArray.isNull(0)) {
                            return true;
                        }
                    }
                    return false;
                case 3:
                case 6:
                    return obj instanceof JSONArray;
                default:
                    return false;
            }
        }

        @Override // io.socket.parser.Parser.Decoder
        public void add(String str) {
            Parser.Decoder.Callback callback;
            Packet packetDecodeString = decodeString(str);
            int i = packetDecodeString.type;
            if (5 != i && 6 != i) {
                Parser.Decoder.Callback callback2 = this.onDecodedCallback;
                if (callback2 != null) {
                    callback2.call(packetDecodeString);
                    return;
                }
                return;
            }
            BinaryReconstructor binaryReconstructor = new BinaryReconstructor(packetDecodeString);
            this.reconstructor = binaryReconstructor;
            if (binaryReconstructor.reconPack.attachments != 0 || (callback = this.onDecodedCallback) == null) {
                return;
            }
            callback.call(packetDecodeString);
        }

        @Override // io.socket.parser.Parser.Decoder
        public void destroy() {
            BinaryReconstructor binaryReconstructor = this.reconstructor;
            if (binaryReconstructor != null) {
                binaryReconstructor.finishReconstruction();
            }
            this.onDecodedCallback = null;
        }

        @Override // io.socket.parser.Parser.Decoder
        public void onDecoded(Parser.Decoder.Callback callback) {
            this.onDecodedCallback = callback;
        }

        @Override // io.socket.parser.Parser.Decoder
        public void add(byte[] bArr) {
            BinaryReconstructor binaryReconstructor = this.reconstructor;
            if (binaryReconstructor != null) {
                Packet packetTakeBinaryData = binaryReconstructor.takeBinaryData(bArr);
                if (packetTakeBinaryData != null) {
                    this.reconstructor = null;
                    Parser.Decoder.Callback callback = this.onDecodedCallback;
                    if (callback != null) {
                        callback.call(packetTakeBinaryData);
                        return;
                    }
                    return;
                }
                return;
            }
            throw new RuntimeException("got binary data when not reconstructing a packet");
        }
    }
}

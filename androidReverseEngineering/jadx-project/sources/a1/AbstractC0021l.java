package a1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;
import s0.C0907j;

/* renamed from: a1.l */
/* loaded from: classes.dex */
public abstract class AbstractC0021l {

    /* renamed from: a */
    public static final Logger f38a = Logger.getLogger(AbstractC0021l.class.getName());

    /* renamed from: a */
    public static C0010a m139a(Socket socket) {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        if (socket.getOutputStream() == null) {
            throw new IOException("socket's output stream == null");
        }
        C0907j c0907j = new C0907j(socket, 2);
        OutputStream outputStream = socket.getOutputStream();
        if (outputStream != null) {
            return new C0010a(c0907j, new C0010a(c0907j, outputStream));
        }
        throw new IllegalArgumentException("out == null");
    }

    /* renamed from: b */
    public static C0011b m140b(Socket socket) {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        if (socket.getInputStream() == null) {
            throw new IOException("socket's input stream == null");
        }
        C0907j c0907j = new C0907j(socket, 2);
        InputStream inputStream = socket.getInputStream();
        if (inputStream != null) {
            return new C0011b(c0907j, new C0011b(c0907j, inputStream));
        }
        throw new IllegalArgumentException("in == null");
    }
}

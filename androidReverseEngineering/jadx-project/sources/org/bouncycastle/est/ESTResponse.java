package org.bouncycastle.est;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import org.bouncycastle.est.HttpUtil;
import org.bouncycastle.util.Properties;
import org.bouncycastle.util.Strings;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ESTResponse {
    private static final Long ZERO = 0L;
    private String HttpVersion;
    private Long absoluteReadLimit;
    private Long contentLength;
    private final HttpUtil.Headers headers;
    private InputStream inputStream;
    private final byte[] lineBuffer;
    private final ESTRequest originalRequest;
    private long read = 0;
    private final Source source;
    private int statusCode;
    private String statusMessage;

    public class PrintingInputStream extends InputStream {
        private final InputStream src;

        private PrintingInputStream(InputStream inputStream) {
            this.src = inputStream;
        }

        @Override // java.io.InputStream
        public int available() {
            return this.src.available();
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.src.close();
        }

        @Override // java.io.InputStream
        public int read() {
            return this.src.read();
        }
    }

    public ESTResponse(ESTRequest eSTRequest, Source source) {
        this.originalRequest = eSTRequest;
        this.source = source;
        if (source instanceof LimitedSource) {
            this.absoluteReadLimit = ((LimitedSource) source).getAbsoluteReadLimit();
        }
        Set<String> asKeySet = Properties.asKeySet("org.bouncycastle.debug.est");
        this.inputStream = (asKeySet.contains("input") || asKeySet.contains("all")) ? new PrintingInputStream(source.getInputStream()) : source.getInputStream();
        this.headers = new HttpUtil.Headers();
        this.lineBuffer = new byte[1024];
        process();
    }

    public static /* synthetic */ long access$108(ESTResponse eSTResponse) {
        long j2 = eSTResponse.read;
        eSTResponse.read = 1 + j2;
        return j2;
    }

    private void process() {
        this.HttpVersion = readStringIncluding(' ');
        this.statusCode = Integer.parseInt(readStringIncluding(' '));
        this.statusMessage = readStringIncluding('\n');
        while (true) {
            String readStringIncluding = readStringIncluding('\n');
            if (readStringIncluding.length() <= 0) {
                break;
            }
            int indexOf = readStringIncluding.indexOf(58);
            if (indexOf > -1) {
                this.headers.add(Strings.toLowerCase(readStringIncluding.substring(0, indexOf).trim()), readStringIncluding.substring(indexOf + 1).trim());
            }
        }
        Long contentLength = getContentLength();
        this.contentLength = contentLength;
        int i2 = this.statusCode;
        if (i2 == 204 || i2 == 202) {
            if (contentLength == null) {
                this.contentLength = 0L;
            } else if (i2 == 204 && contentLength.longValue() > 0) {
                throw new IOException("Got HTTP status 204 but Content-length > 0.");
            }
        }
        Long l2 = this.contentLength;
        if (l2 == null) {
            throw new IOException("No Content-length header.");
        }
        if (l2.equals(ZERO)) {
            this.inputStream = new InputStream() { // from class: org.bouncycastle.est.ESTResponse.1
                @Override // java.io.InputStream
                public int read() {
                    return -1;
                }
            };
        }
        if (this.contentLength.longValue() < 0) {
            throw new IOException("Server returned negative content length: " + this.absoluteReadLimit);
        }
        if (this.absoluteReadLimit == null || this.contentLength.longValue() < this.absoluteReadLimit.longValue()) {
            this.inputStream = wrapWithCounter(this.inputStream, this.absoluteReadLimit);
            if ("base64".equalsIgnoreCase(getHeader("content-transfer-encoding"))) {
                this.inputStream = new CTEBase64InputStream(this.inputStream, getContentLength());
                return;
            }
            return;
        }
        throw new IOException("Content length longer than absolute read limit: " + this.absoluteReadLimit + " Content-Length: " + this.contentLength);
    }

    public void close() {
        InputStream inputStream = this.inputStream;
        if (inputStream != null) {
            inputStream.close();
        }
        this.source.close();
    }

    public Long getContentLength() {
        String firstValue = this.headers.getFirstValue("Content-Length");
        if (firstValue == null) {
            return null;
        }
        try {
            return Long.valueOf(Long.parseLong(firstValue));
        } catch (RuntimeException e2) {
            StringBuilder m23s = AbstractC0000a.m23s("Content Length: '", firstValue, "' invalid. ");
            m23s.append(e2.getMessage());
            throw new RuntimeException(m23s.toString());
        }
    }

    public String getHeader(String str) {
        return this.headers.getFirstValue(str);
    }

    public HttpUtil.Headers getHeaders() {
        return this.headers;
    }

    public String getHttpVersion() {
        return this.HttpVersion;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public ESTRequest getOriginalRequest() {
        return this.originalRequest;
    }

    public Source getSource() {
        return this.source;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public String readStringIncluding(char c) {
        int read;
        byte[] bArr;
        int i2;
        int i3 = 0;
        while (true) {
            read = this.inputStream.read();
            bArr = this.lineBuffer;
            i2 = i3 + 1;
            bArr[i3] = (byte) read;
            if (i2 >= bArr.length) {
                throw new IOException("Server sent line > " + this.lineBuffer.length);
            }
            if (read == c || read <= -1) {
                break;
            }
            i3 = i2;
        }
        if (read != -1) {
            return new String(bArr, 0, i2).trim();
        }
        throw new EOFException();
    }

    public InputStream wrapWithCounter(final InputStream inputStream, final Long l2) {
        return new InputStream() { // from class: org.bouncycastle.est.ESTResponse.2
            @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                if (ESTResponse.this.contentLength == null || ESTResponse.this.contentLength.longValue() - 1 <= ESTResponse.this.read) {
                    if (inputStream.available() > 0) {
                        throw new IOException("Stream closed with extra content in pipe that exceeds content length.");
                    }
                    inputStream.close();
                } else {
                    throw new IOException("Stream closed before limit fully read, Read: " + ESTResponse.this.read + " ContentLength: " + ESTResponse.this.contentLength);
                }
            }

            @Override // java.io.InputStream
            public int read() {
                int read = inputStream.read();
                if (read > -1) {
                    ESTResponse.access$108(ESTResponse.this);
                    if (l2 != null && ESTResponse.this.read >= l2.longValue()) {
                        throw new IOException("Absolute Read Limit exceeded: " + l2);
                    }
                }
                return read;
            }
        };
    }
}

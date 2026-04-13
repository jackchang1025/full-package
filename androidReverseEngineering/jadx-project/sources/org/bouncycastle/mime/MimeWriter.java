package org.bouncycastle.mime;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public abstract class MimeWriter {
    protected final Headers headers;

    public MimeWriter(Headers headers) {
        this.headers = headers;
    }

    public static List<String> mapToLines(Map<String, String> map) {
        ArrayList arrayList = new ArrayList(map.size());
        for (String str : map.keySet()) {
            StringBuilder m22r = AbstractC0000a.m22r(str, ": ");
            m22r.append(map.get(str));
            arrayList.add(m22r.toString());
        }
        return arrayList;
    }

    public abstract OutputStream getContentStream();

    public Headers getHeaders() {
        return this.headers;
    }
}

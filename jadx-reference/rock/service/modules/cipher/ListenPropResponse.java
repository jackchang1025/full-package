package com.storm.safe.rock.service.modules.cipher;

import java.io.Serializable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ListenPropResponse implements Serializable {

    /* renamed from: a0 */
    public final Integer f53240a0;

    /* renamed from: a1 */
    public final String f53241a1;

    /* renamed from: a2 */
    public final String f53242a2;

    /* renamed from: a3 */
    public final Long f53243a3;

    public ListenPropResponse(Integer num, String str, String str2, Long l) {
        System.nanoTime();
        this.f53240a0 = num;
        this.f53241a1 = str;
        this.f53242a2 = str2;
        this.f53243a3 = l;
    }

    public final String toString() {
        return "ListenPropResponse{targetIndex=" + this.f53240a0 + ", prop='" + this.f53241a1 + "', value='" + this.f53242a2 + "', timestamp='" + this.f53243a3 + "'}";
    }
}

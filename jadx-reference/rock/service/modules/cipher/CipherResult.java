package com.storm.safe.rock.service.modules.cipher;

import java.io.Serializable;
import java.util.ArrayList;
import p000.AbstractC0003a2;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class CipherResult implements Serializable {

    /* renamed from: a0 */
    public String f53233a0;

    /* renamed from: a1 */
    public ArrayList f53234a1;

    /* renamed from: a2 */
    public String f53235a2;

    public final String toString() {
        String str = this.f53233a0;
        ArrayList arrayList = this.f53234a1;
        String str2 = this.f53235a2;
        StringBuilder sb = new StringBuilder("CipherResult{textCipher='");
        sb.append(str);
        sb.append("', touchCipher=");
        sb.append(arrayList);
        sb.append(", cipherGradeCode='");
        return AbstractC0003a2.m35b6(sb, str2, "'}");
    }
}

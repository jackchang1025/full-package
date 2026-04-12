package p000;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ot0 {
    public /* synthetic */ ot0(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final pt0 createHandle(Bundle bundle, Bundle bundle2) {
        if (bundle == null) {
            if (bundle2 == null) {
                return new pt0();
            }
            HashMap map = new HashMap();
            for (String str : bundle2.keySet()) {
                t60.m214694b5(str, "key");
                map.put(str, bundle2.get(str));
            }
            return new pt0(map);
        }
        ArrayList parcelableArrayList = bundle.getParcelableArrayList("keys");
        ArrayList parcelableArrayList2 = bundle.getParcelableArrayList("values");
        if (parcelableArrayList == null || parcelableArrayList2 == null || parcelableArrayList.size() != parcelableArrayList2.size()) {
            throw new IllegalStateException("Invalid bundle passed as restored state");
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int size = parcelableArrayList.size();
        for (int i = 0; i < size; i++) {
            Object obj = parcelableArrayList.get(i);
            t60.m214693b4(obj, "null cannot be cast to non-null type kotlin.String");
            linkedHashMap.put((String) obj, parcelableArrayList2.get(i));
        }
        return new pt0(linkedHashMap);
    }

    public final boolean validateValue(Object obj) {
        if (obj == null) {
            return true;
        }
        for (Class cls : pt0.f59336a6) {
            t60.m214692b3(cls);
            if (cls.isInstance(obj)) {
                return true;
            }
        }
        return false;
    }

    private ot0() {
    }
}

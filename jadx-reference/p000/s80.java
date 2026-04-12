package p000;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Xml;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class s80 {

    /* renamed from: a1 */
    public static final HashMap f59919a1;

    /* renamed from: a0 */
    public HashMap f59920a0 = new HashMap();

    static {
        HashMap map = new HashMap();
        f59919a1 = map;
        try {
            map.put("KeyAttribute", m80.class.getConstructor(null));
            map.put("KeyPosition", v80.class.getConstructor(null));
            map.put("KeyCycle", n80.class.getConstructor(null));
            map.put("KeyTimeCycle", w80.class.getConstructor(null));
            map.put("KeyTrigger", x80.class.getConstructor(null));
        } catch (NoSuchMethodException unused) {
        }
    }

    public s80(Context context, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        HashMap map;
        HashMap map2;
        try {
            int eventType = xmlResourceParser.getEventType();
            k80 k80Var = null;
            while (eventType != 1) {
                if (eventType == 2) {
                    String name = xmlResourceParser.getName();
                    HashMap map3 = f59919a1;
                    if (map3.containsKey(name)) {
                        try {
                            Constructor constructor = (Constructor) map3.get(name);
                            if (constructor == null) {
                                throw new NullPointerException("Keymaker for " + name + " not found");
                            }
                            k80 k80Var2 = (k80) constructor.newInstance(null);
                            try {
                                k80Var2.mo213474a2(context, Xml.asAttributeSet(xmlResourceParser));
                                m214584a1(k80Var2);
                            } catch (Exception unused) {
                            }
                            k80Var = k80Var2;
                        } catch (Exception unused2) {
                        }
                    } else if (name.equalsIgnoreCase("CustomAttribute")) {
                        if (k80Var != null && (map2 = k80Var.f57485a3) != null) {
                            C0798kw.m213758a3(context, xmlResourceParser, map2);
                        }
                    } else if (name.equalsIgnoreCase("CustomMethod") && k80Var != null && (map = k80Var.f57485a3) != null) {
                        C0798kw.m213758a3(context, xmlResourceParser, map);
                    }
                } else if (eventType == 3 && "KeyFrameSet".equals(xmlResourceParser.getName())) {
                    return;
                }
                eventType = xmlResourceParser.next();
            }
        } catch (IOException | XmlPullParserException unused3) {
        }
    }

    /* renamed from: a0 */
    public final void m214583a0(og0 og0Var) {
        HashMap map = this.f59920a0;
        ArrayList arrayList = (ArrayList) map.get(Integer.valueOf(og0Var.f58801a2));
        if (arrayList != null) {
            og0Var.f58821c2.addAll(arrayList);
        }
        ArrayList arrayList2 = (ArrayList) map.get(-1);
        if (arrayList2 != null) {
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                k80 k80Var = (k80) obj;
                String str = ((C0801kz) og0Var.f58800a1.getLayoutParams()).f57796f0;
                String str2 = k80Var.f57484a2;
                if ((str2 == null || str == null) ? false : str.matches(str2)) {
                    og0Var.m214192a0(k80Var);
                }
            }
        }
    }

    /* renamed from: a1 */
    public final void m214584a1(k80 k80Var) {
        HashMap map = this.f59920a0;
        if (!map.containsKey(Integer.valueOf(k80Var.f57483a1))) {
            map.put(Integer.valueOf(k80Var.f57483a1), new ArrayList());
        }
        ArrayList arrayList = (ArrayList) map.get(Integer.valueOf(k80Var.f57483a1));
        if (arrayList != null) {
            arrayList.add(k80Var);
        }
    }
}

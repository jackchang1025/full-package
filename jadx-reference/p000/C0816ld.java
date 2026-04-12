package p000;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ld */
/* loaded from: classes.dex */
public final class C0816ld {

    /* renamed from: a0 */
    public int f57880a0;

    /* renamed from: a1 */
    public int f57881a1;

    /* renamed from: a2 */
    public Object f57882a2;

    /* renamed from: a3 */
    public final Object f57883a3;

    /* renamed from: a4 */
    public final Cloneable f57884a4;

    public C0816ld(Context context, ConstraintLayout constraintLayout, int i) throws XmlPullParserException, Resources.NotFoundException, IOException {
        String str;
        this.f57880a0 = -1;
        this.f57881a1 = -1;
        this.f57883a3 = new SparseArray();
        this.f57884a4 = new SparseArray();
        this.f57882a2 = constraintLayout;
        XmlResourceParser xml = context.getResources().getXml(i);
        try {
            C0814lb c0814lb = null;
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType == 0) {
                    xml.getName();
                } else if (eventType == 2) {
                    String name = xml.getName();
                    switch (name.hashCode()) {
                        case -1349929691:
                            if (name.equals("ConstraintSet")) {
                                m213834a1(context, xml);
                                break;
                            } else {
                                break;
                            }
                        case 80204913:
                            if (name.equals("State")) {
                                c0814lb = new C0814lb(context, xml);
                                ((SparseArray) this.f57883a3).put(c0814lb.f57866a0, c0814lb);
                                break;
                            } else {
                                break;
                            }
                        case 1382829617:
                            str = "StateSet";
                            name.equals(str);
                            break;
                        case 1657696882:
                            str = "layoutDescription";
                            name.equals(str);
                            break;
                        case 1901439077:
                            if (name.equals("Variant")) {
                                C0815lc c0815lc = new C0815lc(context, xml);
                                if (c0814lb != null) {
                                    c0814lb.f57867a1.add(c0815lc);
                                    break;
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                    }
                }
            }
        } catch (IOException | XmlPullParserException unused) {
        }
    }

    /* renamed from: a0 */
    public void m213833a0(xf1 xf1Var, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if ((((jf1) it.next()).f57330a0.mo213035a2() & 8) != 0) {
                ((View) this.f57883a3).setTranslationY(AbstractC1249t7.m214729a2(this.f57881a1, r3.f57330a0.mo213034a1(), 0));
                return;
            }
        }
    }

    /* renamed from: a1 */
    public void m213834a1(Context context, XmlResourceParser xmlResourceParser) throws XmlPullParserException, NumberFormatException, IOException {
        C0825lm c0825lm = new C0825lm();
        int attributeCount = xmlResourceParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = xmlResourceParser.getAttributeName(i);
            String attributeValue = xmlResourceParser.getAttributeValue(i);
            if (attributeName != null && attributeValue != null && "id".equals(attributeName)) {
                int identifier = attributeValue.contains("/") ? context.getResources().getIdentifier(attributeValue.substring(attributeValue.indexOf(47) + 1), "id", context.getPackageName()) : -1;
                if (identifier == -1 && attributeValue.length() > 1) {
                    identifier = Integer.parseInt(attributeValue.substring(1));
                }
                c0825lm.m213872b0(context, xmlResourceParser);
                ((SparseArray) this.f57884a4).put(identifier, c0825lm);
                return;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [int[], java.lang.Cloneable] */
    public C0816ld(View view) {
        this.f57884a4 = new int[2];
        this.f57883a3 = view;
    }
}

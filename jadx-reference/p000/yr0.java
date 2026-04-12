package p000;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import java.io.IOException;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class yr0 {

    /* renamed from: a0 */
    public static final ThreadLocal f61364a0 = new ThreadLocal();

    /* renamed from: a1 */
    public static final WeakHashMap f61365a1 = new WeakHashMap(0);

    /* renamed from: a2 */
    public static final Object f61366a2 = new Object();

    /* renamed from: a0 */
    public static Typeface m215304a0(Context context, int i) {
        if (context.isRestricted()) {
            return null;
        }
        return m215305a1(context, i, new TypedValue(), 0, null, false, false);
    }

    /* renamed from: a1 */
    public static Typeface m215305a1(Context context, int i, TypedValue typedValue, int i2, cq0 cq0Var, boolean z, boolean z2) throws Resources.NotFoundException {
        Resources resources = context.getResources();
        resources.getValue(i, typedValue, true);
        CharSequence charSequence = typedValue.string;
        if (charSequence == null) {
            throw new Resources.NotFoundException("Resource \"" + resources.getResourceName(i) + "\" (" + Integer.toHexString(i) + ") is not a Font: " + typedValue);
        }
        String string = charSequence.toString();
        Typeface typefaceM210770a0 = null;
        if (string.startsWith("res/")) {
            int i3 = typedValue.assetCookie;
            pc0 pc0Var = c81.f46077a1;
            Typeface typeface = (Typeface) pc0Var.m214243a0(c81.m210771a1(resources, i, string, i3, i2));
            if (typeface != null) {
                if (cq0Var != null) {
                    new Handler(Looper.getMainLooper()).post(new RunnableC1052p1(cq0Var, 11, typeface));
                }
                typefaceM210770a0 = typeface;
            } else if (!z2) {
                try {
                    if (string.toLowerCase().endsWith(".xml")) {
                        InterfaceC0881n interfaceC0881nM213579c6 = kj1.m213579c6(resources.getXml(i), resources);
                        if (interfaceC0881nM213579c6 != null) {
                            typefaceM210770a0 = c81.m210770a0(context, interfaceC0881nM213579c6, resources, i, string, typedValue.assetCookie, i2, cq0Var, z);
                        } else if (cq0Var != null) {
                            cq0Var.m212500a0(-3);
                        }
                    } else {
                        int i4 = typedValue.assetCookie;
                        Typeface typefaceMo212758b1 = c81.f46076a0.mo212758b1(context, resources, i, string, i2);
                        if (typefaceMo212758b1 != null) {
                            pc0Var.m214244a1(c81.m210771a1(resources, i, string, i4, i2), typefaceMo212758b1);
                        }
                        if (cq0Var != null) {
                            if (typefaceMo212758b1 != null) {
                                new Handler(Looper.getMainLooper()).post(new RunnableC1052p1(cq0Var, 11, typefaceMo212758b1));
                            } else {
                                cq0Var.m212500a0(-3);
                            }
                        }
                        typefaceM210770a0 = typefaceMo212758b1;
                    }
                } catch (IOException | XmlPullParserException unused) {
                    if (cq0Var != null) {
                        cq0Var.m212500a0(-3);
                    }
                }
            }
        } else if (cq0Var != null) {
            cq0Var.m212500a0(-3);
        }
        if (typefaceM210770a0 != null || cq0Var != null || z2) {
            return typefaceM210770a0;
        }
        throw new Resources.NotFoundException("Font resource ID #0x" + Integer.toHexString(i) + " could not be retrieved.");
    }
}

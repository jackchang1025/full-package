package p000;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SubMenu;
import androidx.appcompat.R$styleable;
import androidx.core.internal.view.SupportMenu;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import okio.internal.Buffer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class c31 extends MenuInflater {

    /* renamed from: a4 */
    public static final Class[] f46064a4;

    /* renamed from: a5 */
    public static final Class[] f46065a5;

    /* renamed from: a0 */
    public final Object[] f46066a0;

    /* renamed from: a1 */
    public final Object[] f46067a1;

    /* renamed from: a2 */
    public final Context f46068a2;

    /* renamed from: a3 */
    public Object f46069a3;

    static {
        Class[] clsArr = {Context.class};
        f46064a4 = clsArr;
        f46065a5 = clsArr;
    }

    public c31(Context context) {
        super(context);
        this.f46068a2 = context;
        Object[] objArr = {context};
        this.f46066a0 = objArr;
        this.f46067a1 = objArr;
    }

    /* renamed from: a0 */
    public static Object m210759a0(Object obj) {
        return (!(obj instanceof Activity) && (obj instanceof ContextWrapper)) ? m210759a0(((ContextWrapper) obj).getBaseContext()) : obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0048  */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m210760a1(XmlPullParser xmlPullParser, AttributeSet attributeSet, Menu menu) throws XmlPullParserException, IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        int i;
        XmlPullParser xmlPullParser2;
        ColorStateList colorStateList;
        int resourceId;
        b31 b31Var = new b31(this, menu);
        int eventType = xmlPullParser.getEventType();
        while (true) {
            i = 2;
            if (eventType == 2) {
                String name = xmlPullParser.getName();
                if (!name.equals("menu")) {
                    throw new RuntimeException("Expecting menu, got ".concat(name));
                }
                eventType = xmlPullParser.next();
            } else {
                eventType = xmlPullParser.next();
                if (eventType == 1) {
                    break;
                }
            }
        }
        boolean z = false;
        boolean z2 = false;
        String str = null;
        while (!z) {
            if (eventType == 1) {
                throw new RuntimeException("Unexpected end of document");
            }
            if (eventType != i) {
                if (eventType != 3) {
                    xmlPullParser2 = xmlPullParser;
                } else {
                    String name2 = xmlPullParser.getName();
                    if (z2 && name2.equals(str)) {
                        xmlPullParser2 = xmlPullParser;
                        z2 = false;
                        str = null;
                    } else {
                        if (name2.equals("group")) {
                            b31Var.f45681a1 = 0;
                            b31Var.f45682a2 = 0;
                            b31Var.f45683a3 = 0;
                            b31Var.f45684a4 = 0;
                            b31Var.f45685a5 = true;
                            b31Var.f45686a6 = true;
                        } else if (name2.equals("item")) {
                            if (!b31Var.f45687a7) {
                                AbstractC0904n8 abstractC0904n8 = b31Var.f45705c5;
                                if (abstractC0904n8 == null || !((gf0) abstractC0904n8).f56454a1.hasSubMenu()) {
                                    b31Var.f45687a7 = true;
                                    b31Var.m210534a1(b31Var.f45680a0.add(b31Var.f45681a1, b31Var.f45688a8, b31Var.f45689a9, b31Var.f45690b0));
                                } else {
                                    b31Var.f45687a7 = true;
                                    b31Var.m210534a1(b31Var.f45680a0.addSubMenu(b31Var.f45681a1, b31Var.f45688a8, b31Var.f45689a9, b31Var.f45690b0).getItem());
                                }
                            }
                        } else if (name2.equals("menu")) {
                            xmlPullParser2 = xmlPullParser;
                            z = true;
                        }
                        xmlPullParser2 = xmlPullParser;
                    }
                }
            } else if (!z2) {
                String name3 = xmlPullParser.getName();
                boolean zEquals = name3.equals("group");
                Context context = this.f46068a2;
                if (zEquals) {
                    TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MenuGroup);
                    b31Var.f45681a1 = typedArrayObtainStyledAttributes.getResourceId(R$styleable.MenuGroup_android_id, 0);
                    b31Var.f45682a2 = typedArrayObtainStyledAttributes.getInt(R$styleable.MenuGroup_android_menuCategory, 0);
                    b31Var.f45683a3 = typedArrayObtainStyledAttributes.getInt(R$styleable.MenuGroup_android_orderInCategory, 0);
                    b31Var.f45684a4 = typedArrayObtainStyledAttributes.getInt(R$styleable.MenuGroup_android_checkableBehavior, 0);
                    b31Var.f45685a5 = typedArrayObtainStyledAttributes.getBoolean(R$styleable.MenuGroup_android_visible, true);
                    b31Var.f45686a6 = typedArrayObtainStyledAttributes.getBoolean(R$styleable.MenuGroup_android_enabled, true);
                    typedArrayObtainStyledAttributes.recycle();
                } else if (name3.equals("item")) {
                    TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R$styleable.MenuItem);
                    b31Var.f45688a8 = typedArrayObtainStyledAttributes2.getResourceId(R$styleable.MenuItem_android_id, 0);
                    b31Var.f45689a9 = (typedArrayObtainStyledAttributes2.getInt(R$styleable.MenuItem_android_menuCategory, b31Var.f45682a2) & SupportMenu.CATEGORY_MASK) | (typedArrayObtainStyledAttributes2.getInt(R$styleable.MenuItem_android_orderInCategory, b31Var.f45683a3) & 65535);
                    b31Var.f45690b0 = typedArrayObtainStyledAttributes2.getText(R$styleable.MenuItem_android_title);
                    b31Var.f45691b1 = typedArrayObtainStyledAttributes2.getText(R$styleable.MenuItem_android_titleCondensed);
                    b31Var.f45692b2 = typedArrayObtainStyledAttributes2.getResourceId(R$styleable.MenuItem_android_icon, 0);
                    String string = typedArrayObtainStyledAttributes2.getString(R$styleable.MenuItem_android_alphabeticShortcut);
                    b31Var.f45693b3 = string == null ? (char) 0 : string.charAt(0);
                    b31Var.f45694b4 = typedArrayObtainStyledAttributes2.getInt(R$styleable.MenuItem_alphabeticModifiers, Buffer.SEGMENTING_THRESHOLD);
                    String string2 = typedArrayObtainStyledAttributes2.getString(R$styleable.MenuItem_android_numericShortcut);
                    b31Var.f45695b5 = string2 == null ? (char) 0 : string2.charAt(0);
                    b31Var.f45696b6 = typedArrayObtainStyledAttributes2.getInt(R$styleable.MenuItem_numericModifiers, Buffer.SEGMENTING_THRESHOLD);
                    if (typedArrayObtainStyledAttributes2.hasValue(R$styleable.MenuItem_android_checkable)) {
                        b31Var.f45697b7 = typedArrayObtainStyledAttributes2.getBoolean(R$styleable.MenuItem_android_checkable, false) ? 1 : 0;
                    } else {
                        b31Var.f45697b7 = b31Var.f45684a4;
                    }
                    b31Var.f45698b8 = typedArrayObtainStyledAttributes2.getBoolean(R$styleable.MenuItem_android_checked, false);
                    b31Var.f45699b9 = typedArrayObtainStyledAttributes2.getBoolean(R$styleable.MenuItem_android_visible, b31Var.f45685a5);
                    b31Var.f45700c0 = typedArrayObtainStyledAttributes2.getBoolean(R$styleable.MenuItem_android_enabled, b31Var.f45686a6);
                    b31Var.f45701c1 = typedArrayObtainStyledAttributes2.getInt(R$styleable.MenuItem_showAsAction, -1);
                    b31Var.f45704c4 = typedArrayObtainStyledAttributes2.getString(R$styleable.MenuItem_android_onClick);
                    b31Var.f45702c2 = typedArrayObtainStyledAttributes2.getResourceId(R$styleable.MenuItem_actionLayout, 0);
                    b31Var.f45703c3 = typedArrayObtainStyledAttributes2.getString(R$styleable.MenuItem_actionViewClass);
                    String string3 = typedArrayObtainStyledAttributes2.getString(R$styleable.MenuItem_actionProviderClass);
                    if (string3 != null && b31Var.f45702c2 == 0 && b31Var.f45703c3 == null) {
                        b31Var.f45705c5 = (AbstractC0904n8) b31Var.m210533a0(string3, f46065a5, this.f46067a1);
                    } else {
                        b31Var.f45705c5 = null;
                    }
                    b31Var.f45706c6 = typedArrayObtainStyledAttributes2.getText(R$styleable.MenuItem_contentDescription);
                    b31Var.f45707c7 = typedArrayObtainStyledAttributes2.getText(R$styleable.MenuItem_tooltipText);
                    if (typedArrayObtainStyledAttributes2.hasValue(R$styleable.MenuItem_iconTintMode)) {
                        b31Var.f45709c9 = AbstractC1274tv.m214792a2(typedArrayObtainStyledAttributes2.getInt(R$styleable.MenuItem_iconTintMode, -1), b31Var.f45709c9);
                    } else {
                        b31Var.f45709c9 = null;
                    }
                    if (typedArrayObtainStyledAttributes2.hasValue(R$styleable.MenuItem_iconTint)) {
                        int i2 = R$styleable.MenuItem_iconTint;
                        if (!typedArrayObtainStyledAttributes2.hasValue(i2) || (resourceId = typedArrayObtainStyledAttributes2.getResourceId(i2, 0)) == 0 || (colorStateList = AbstractC1117qo.m214426c2(context, resourceId)) == null) {
                            colorStateList = typedArrayObtainStyledAttributes2.getColorStateList(i2);
                        }
                        b31Var.f45708c8 = colorStateList;
                    } else {
                        b31Var.f45708c8 = null;
                    }
                    typedArrayObtainStyledAttributes2.recycle();
                    b31Var.f45687a7 = false;
                } else if (name3.equals("menu")) {
                    b31Var.f45687a7 = true;
                    SubMenu subMenuAddSubMenu = b31Var.f45680a0.addSubMenu(b31Var.f45681a1, b31Var.f45688a8, b31Var.f45689a9, b31Var.f45690b0);
                    b31Var.m210534a1(subMenuAddSubMenu.getItem());
                    xmlPullParser2 = xmlPullParser;
                    m210760a1(xmlPullParser2, attributeSet, subMenuAddSubMenu);
                } else {
                    xmlPullParser2 = xmlPullParser;
                    str = name3;
                    z2 = true;
                }
                xmlPullParser2 = xmlPullParser;
            }
            eventType = xmlPullParser2.next();
            i = 2;
            z = z;
            z2 = z2;
        }
    }

    @Override // android.view.MenuInflater
    public final void inflate(int i, Menu menu) {
        if (!(menu instanceof SupportMenu)) {
            super.inflate(i, menu);
            return;
        }
        XmlResourceParser layout = null;
        try {
            try {
                try {
                    layout = this.f46068a2.getResources().getLayout(i);
                    m210760a1(layout, Xml.asAttributeSet(layout), menu);
                    layout.close();
                } catch (IOException e) {
                    throw new InflateException("Error inflating menu XML", e);
                }
            } catch (XmlPullParserException e2) {
                throw new InflateException("Error inflating menu XML", e2);
            }
        } catch (Throwable th) {
            if (layout != null) {
                layout.close();
            }
            throw th;
        }
    }
}

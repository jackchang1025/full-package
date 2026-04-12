package p000;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.util.Xml;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertController$RecycleListView;
import androidx.constraintlayout.widget.R$styleable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: r3 */
/* loaded from: classes.dex */
public final class C1166r3 implements InterfaceC0812l9 {

    /* renamed from: a0 */
    public final int f59607a0;

    /* renamed from: a1 */
    public final Object f59608a1;

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public C1166r3(Context context, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        this.f59607a0 = -1;
        this.f59608a1 = new SparseArray();
        new SparseArray();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlResourceParser), R$styleable.StateSet);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            if (index == R$styleable.StateSet_defaultState) {
                this.f59607a0 = typedArrayObtainStyledAttributes.getResourceId(index, this.f59607a0);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        try {
            int eventType = xmlResourceParser.getEventType();
            y11 y11Var = null;
            while (eventType != 1) {
                if (eventType == 0) {
                    xmlResourceParser.getName();
                } else if (eventType == 2) {
                    String name = xmlResourceParser.getName();
                    switch (name.hashCode()) {
                        case 80204913:
                            if (name.equals("State")) {
                                y11Var = new y11(context, xmlResourceParser);
                                ((SparseArray) this.f59608a1).put(y11Var.f61222a0, y11Var);
                                break;
                            } else {
                                break;
                            }
                        case 1301459538:
                            name.equals("LayoutDescription");
                            break;
                        case 1382829617:
                            name.equals("StateSet");
                            break;
                        case 1901439077:
                            if (name.equals("Variant")) {
                                z11 z11Var = new z11(context, xmlResourceParser);
                                if (y11Var != null) {
                                    y11Var.f61223a1.add(z11Var);
                                    break;
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                    }
                } else if (eventType != 3) {
                    continue;
                } else if ("StateSet".equals(xmlResourceParser.getName())) {
                    return;
                }
                eventType = xmlResourceParser.next();
            }
        } catch (IOException | XmlPullParserException unused) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v1, types: [android.widget.ListAdapter] */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* renamed from: a0 */
    public DialogC1167r4 m214470a0() {
        C1102q9 c1102q9 = (C1102q9) this.f59608a1;
        DialogC1167r4 dialogC1167r4 = new DialogC1167r4(c1102q9.f59429a0, this.f59607a0);
        View view = c1102q9.f59433a4;
        C1165r2 c1165r2 = dialogC1167r4.f59623a4;
        if (view != null) {
            c1165r2.f59595b3 = view;
        } else {
            CharSequence charSequence = c1102q9.f59432a3;
            if (charSequence != null) {
                c1165r2.f59585a3 = charSequence;
                TextView textView = c1165r2.f59593b1;
                if (textView != null) {
                    textView.setText(charSequence);
                }
            }
            Drawable drawable = c1102q9.f59431a2;
            if (drawable != null) {
                c1165r2.f59591a9 = drawable;
                ImageView imageView = c1165r2.f59592b0;
                if (imageView != null) {
                    imageView.setVisibility(0);
                    c1165r2.f59592b0.setImageDrawable(drawable);
                }
            }
        }
        if (c1102q9.f59435a6 != null) {
            AlertController$RecycleListView alertController$RecycleListView = (AlertController$RecycleListView) c1102q9.f59430a1.inflate(c1165r2.f59599b7, (ViewGroup) null);
            int i = c1102q9.f59437a8 ? c1165r2.f59600b8 : c1165r2.f59601b9;
            Object obj = c1102q9.f59435a6;
            ?? c1164r1 = obj;
            if (obj == null) {
                c1164r1 = new C1164r1(c1102q9.f59429a0, i, R.id.text1, null);
            }
            c1165r2.f59596b4 = c1164r1;
            c1165r2.f59597b5 = c1102q9.f59438a9;
            if (c1102q9.f59436a7 != null) {
                alertController$RecycleListView.setOnItemClickListener(new C1101q8(c1102q9, c1165r2));
            }
            if (c1102q9.f59437a8) {
                alertController$RecycleListView.setChoiceMode(1);
            }
            c1165r2.f59586a4 = alertController$RecycleListView;
        }
        dialogC1167r4.setCancelable(true);
        dialogC1167r4.setCanceledOnTouchOutside(true);
        dialogC1167r4.setOnCancelListener(null);
        dialogC1167r4.setOnDismissListener(null);
        cf0 cf0Var = c1102q9.f59434a5;
        if (cf0Var != null) {
            dialogC1167r4.setOnKeyListener(cf0Var);
        }
        return dialogC1167r4;
    }

    /* renamed from: a1 */
    public int m214471a1(int i) {
        float f = -1;
        SparseArray sparseArray = (SparseArray) this.f59608a1;
        int i2 = 0;
        if (-1 == i) {
            y11 y11Var = i == -1 ? (y11) sparseArray.valueAt(0) : (y11) sparseArray.get(-1);
            if (y11Var != null) {
                ArrayList arrayList = y11Var.f61223a1;
                while (true) {
                    if (i2 >= arrayList.size()) {
                        i2 = -1;
                        break;
                    }
                    if (((z11) arrayList.get(i2)).m215333a0(f, f)) {
                        break;
                    }
                    i2++;
                }
                if (-1 != i2) {
                    return i2 == -1 ? y11Var.f61224a2 : ((z11) arrayList.get(i2)).f61427a4;
                }
            }
        } else {
            y11 y11Var2 = (y11) sparseArray.get(i);
            if (y11Var2 != null) {
                ArrayList arrayList2 = y11Var2.f61223a1;
                while (true) {
                    if (i2 >= arrayList2.size()) {
                        i2 = -1;
                        break;
                    }
                    if (((z11) arrayList2.get(i2)).m215333a0(f, f)) {
                        break;
                    }
                    i2++;
                }
                return i2 == -1 ? y11Var2.f61224a2 : ((z11) arrayList2.get(i2)).f61427a4;
            }
        }
        return -1;
    }

    @Override // p000.InterfaceC0812l9
    /* renamed from: a2 */
    public boolean mo210913a2(View view) {
        ((BottomSheetBehavior) this.f59608a1).m210946c8(this.f59607a0);
        return true;
    }

    public C1166r3(Context context) {
        int iM214477a7 = DialogC1167r4.m214477a7(context, 0);
        this.f59608a1 = new C1102q9(new ContextThemeWrapper(context, DialogC1167r4.m214477a7(context, iM214477a7)));
        this.f59607a0 = iM214477a7;
    }

    public C1166r3(int i, C1162r[] c1162rArr) {
        this.f59607a0 = i;
        this.f59608a1 = c1162rArr;
    }

    public C1166r3(BottomSheetBehavior bottomSheetBehavior, int i) {
        this.f59608a1 = bottomSheetBehavior;
        this.f59607a0 = i;
    }
}

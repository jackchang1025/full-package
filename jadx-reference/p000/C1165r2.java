package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$styleable;
import androidx.appcompat.app.AlertController$RecycleListView;
import androidx.core.widget.NestedScrollView;
import java.lang.ref.WeakReference;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: r2 */
/* loaded from: classes.dex */
public final class C1165r2 {

    /* renamed from: a0 */
    public final Context f59582a0;

    /* renamed from: a1 */
    public final DialogC1167r4 f59583a1;

    /* renamed from: a2 */
    public final Window f59584a2;

    /* renamed from: a3 */
    public CharSequence f59585a3;

    /* renamed from: a4 */
    public AlertController$RecycleListView f59586a4;

    /* renamed from: a5 */
    public Button f59587a5;

    /* renamed from: a6 */
    public Button f59588a6;

    /* renamed from: a7 */
    public Button f59589a7;

    /* renamed from: a8 */
    public NestedScrollView f59590a8;

    /* renamed from: a9 */
    public Drawable f59591a9;

    /* renamed from: b0 */
    public ImageView f59592b0;

    /* renamed from: b1 */
    public TextView f59593b1;

    /* renamed from: b2 */
    public TextView f59594b2;

    /* renamed from: b3 */
    public View f59595b3;

    /* renamed from: b4 */
    public ListAdapter f59596b4;

    /* renamed from: b6 */
    public final int f59598b6;

    /* renamed from: b7 */
    public final int f59599b7;

    /* renamed from: b8 */
    public final int f59600b8;

    /* renamed from: b9 */
    public final int f59601b9;

    /* renamed from: c0 */
    public final boolean f59602c0;

    /* renamed from: c1 */
    public final HandlerC1163r0 f59603c1;

    /* renamed from: b5 */
    public int f59597b5 = -1;

    /* renamed from: c2 */
    public final ViewOnClickListenerC0846m2 f59604c2 = new ViewOnClickListenerC0846m2(1, this);

    public C1165r2(Context context, DialogC1167r4 dialogC1167r4, Window window) {
        this.f59582a0 = context;
        this.f59583a1 = dialogC1167r4;
        this.f59584a2 = window;
        HandlerC1163r0 handlerC1163r0 = new HandlerC1163r0();
        handlerC1163r0.f59578a0 = new WeakReference(dialogC1167r4);
        this.f59603c1 = handlerC1163r0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R$styleable.AlertDialog, R$attr.alertDialogStyle, 0);
        this.f59598b6 = typedArrayObtainStyledAttributes.getResourceId(R$styleable.AlertDialog_android_layout, 0);
        typedArrayObtainStyledAttributes.getResourceId(R$styleable.AlertDialog_buttonPanelSideLayout, 0);
        this.f59599b7 = typedArrayObtainStyledAttributes.getResourceId(R$styleable.AlertDialog_listLayout, 0);
        typedArrayObtainStyledAttributes.getResourceId(R$styleable.AlertDialog_multiChoiceItemLayout, 0);
        this.f59600b8 = typedArrayObtainStyledAttributes.getResourceId(R$styleable.AlertDialog_singleChoiceItemLayout, 0);
        this.f59601b9 = typedArrayObtainStyledAttributes.getResourceId(R$styleable.AlertDialog_listItemLayout, 0);
        this.f59602c0 = typedArrayObtainStyledAttributes.getBoolean(R$styleable.AlertDialog_showTitle, true);
        typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.AlertDialog_buttonIconDimen, 0);
        typedArrayObtainStyledAttributes.recycle();
        dialogC1167r4.m214478a2().mo214901a6(1);
    }

    /* renamed from: a0 */
    public static ViewGroup m214469a0(View view, View view2) {
        if (view == null) {
            if (view2 instanceof ViewStub) {
                view2 = ((ViewStub) view2).inflate();
            }
            return (ViewGroup) view2;
        }
        if (view2 != null) {
            ViewParent parent = view2.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view2);
            }
        }
        if (view instanceof ViewStub) {
            view = ((ViewStub) view).inflate();
        }
        return (ViewGroup) view;
    }
}

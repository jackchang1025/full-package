package p000;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ae */
/* loaded from: classes.dex */
public final class ViewOnClickListenerC0025ae implements View.OnClickListener {

    /* renamed from: a0 */
    public final View f43637a0;

    /* renamed from: a1 */
    public final String f43638a1;

    /* renamed from: a2 */
    public Method f43639a2;

    /* renamed from: a3 */
    public Context f43640a3;

    public ViewOnClickListenerC0025ae(View view, String str) {
        this.f43637a0 = view;
        this.f43638a1 = str;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String str;
        Method method;
        if (this.f43639a2 == null) {
            View view2 = this.f43637a0;
            Context context = view2.getContext();
            while (true) {
                String str2 = this.f43638a1;
                if (context == null) {
                    int id = view2.getId();
                    if (id == -1) {
                        str = "";
                    } else {
                        str = " with id '" + view2.getContext().getResources().getResourceEntryName(id) + "'";
                    }
                    throw new IllegalStateException("Could not find method " + str2 + "(View) in a parent or ancestor Context for android:onClick attribute defined on view " + view2.getClass() + str);
                }
                try {
                    if (!context.isRestricted() && (method = context.getClass().getMethod(str2, View.class)) != null) {
                        this.f43639a2 = method;
                        this.f43640a3 = context;
                    }
                } catch (NoSuchMethodException unused) {
                }
                context = context instanceof ContextWrapper ? ((ContextWrapper) context).getBaseContext() : null;
            }
        }
        try {
            this.f43639a2.invoke(this.f43640a3, view);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Could not execute non-public method for android:onClick", e);
        } catch (InvocationTargetException e2) {
            throw new IllegalStateException("Could not execute method for android:onClick", e2);
        }
    }
}

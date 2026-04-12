package p000;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.R$styleable;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: lb */
/* loaded from: classes.dex */
public final class C0814lb {

    /* renamed from: a0 */
    public final int f57866a0;

    /* renamed from: a1 */
    public final ArrayList f57867a1 = new ArrayList();

    /* renamed from: a2 */
    public final int f57868a2;

    /* renamed from: a3 */
    public final C0825lm f57869a3;

    public C0814lb(Context context, XmlResourceParser xmlResourceParser) throws Resources.NotFoundException {
        this.f57868a2 = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlResourceParser), R$styleable.State);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            if (index == R$styleable.State_android_id) {
                this.f57866a0 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57866a0);
            } else if (index == R$styleable.State_constraints) {
                int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, this.f57868a2);
                this.f57868a2 = resourceId;
                String resourceTypeName = context.getResources().getResourceTypeName(resourceId);
                context.getResources().getResourceName(resourceId);
                if ("layout".equals(resourceTypeName)) {
                    C0825lm c0825lm = new C0825lm();
                    this.f57869a3 = c0825lm;
                    c0825lm.m213868a4((ConstraintLayout) LayoutInflater.from(context).inflate(resourceId, (ViewGroup) null));
                }
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}

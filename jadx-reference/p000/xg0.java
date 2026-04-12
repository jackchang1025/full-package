package p000;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.SparseArray;
import android.util.Xml;
import androidx.constraintlayout.motion.widget.C0057a1;
import androidx.constraintlayout.motion.widget.C0058a2;
import androidx.constraintlayout.widget.R$styleable;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class xg0 {

    /* renamed from: a0 */
    public final int f61107a0;

    /* renamed from: a1 */
    public final boolean f61108a1;

    /* renamed from: a2 */
    public int f61109a2;

    /* renamed from: a3 */
    public int f61110a3;

    /* renamed from: a4 */
    public int f61111a4;

    /* renamed from: a5 */
    public String f61112a5;

    /* renamed from: a6 */
    public int f61113a6;

    /* renamed from: a7 */
    public int f61114a7;

    /* renamed from: a8 */
    public final float f61115a8;

    /* renamed from: a9 */
    public final C0057a1 f61116a9;

    /* renamed from: b0 */
    public final ArrayList f61117b0;

    /* renamed from: b1 */
    public C0058a2 f61118b1;

    /* renamed from: b2 */
    public final ArrayList f61119b2;

    /* renamed from: b3 */
    public final int f61120b3;

    /* renamed from: b4 */
    public final boolean f61121b4;

    /* renamed from: b5 */
    public int f61122b5;

    /* renamed from: b6 */
    public final int f61123b6;

    /* renamed from: b7 */
    public final int f61124b7;

    public xg0(C0057a1 c0057a1, xg0 xg0Var) {
        this.f61107a0 = -1;
        this.f61108a1 = false;
        this.f61109a2 = -1;
        this.f61110a3 = -1;
        this.f61111a4 = 0;
        this.f61112a5 = null;
        this.f61113a6 = -1;
        this.f61114a7 = 400;
        this.f61115a8 = 0.0f;
        this.f61117b0 = new ArrayList();
        this.f61118b1 = null;
        this.f61119b2 = new ArrayList();
        this.f61120b3 = 0;
        this.f61121b4 = false;
        this.f61122b5 = -1;
        this.f61123b6 = 0;
        this.f61124b7 = 0;
        this.f61116a9 = c0057a1;
        this.f61114a7 = c0057a1.f44607a9;
        if (xg0Var != null) {
            this.f61122b5 = xg0Var.f61122b5;
            this.f61111a4 = xg0Var.f61111a4;
            this.f61112a5 = xg0Var.f61112a5;
            this.f61113a6 = xg0Var.f61113a6;
            this.f61114a7 = xg0Var.f61114a7;
            this.f61117b0 = xg0Var.f61117b0;
            this.f61115a8 = xg0Var.f61115a8;
            this.f61123b6 = xg0Var.f61123b6;
        }
    }

    public xg0(C0057a1 c0057a1, int i, int i2) {
        this.f61107a0 = -1;
        this.f61108a1 = false;
        this.f61109a2 = -1;
        this.f61110a3 = -1;
        this.f61111a4 = 0;
        this.f61112a5 = null;
        this.f61113a6 = -1;
        this.f61114a7 = 400;
        this.f61115a8 = 0.0f;
        this.f61117b0 = new ArrayList();
        this.f61118b1 = null;
        this.f61119b2 = new ArrayList();
        this.f61120b3 = 0;
        this.f61121b4 = false;
        this.f61122b5 = -1;
        this.f61123b6 = 0;
        this.f61124b7 = 0;
        this.f61107a0 = -1;
        this.f61116a9 = c0057a1;
        this.f61110a3 = i;
        this.f61109a2 = i2;
        this.f61114a7 = c0057a1.f44607a9;
        this.f61123b6 = c0057a1.f44608b0;
    }

    public xg0(C0057a1 c0057a1, Context context, XmlResourceParser xmlResourceParser) throws XmlPullParserException, Resources.NotFoundException, IOException {
        this.f61107a0 = -1;
        this.f61108a1 = false;
        this.f61109a2 = -1;
        this.f61110a3 = -1;
        this.f61111a4 = 0;
        this.f61112a5 = null;
        this.f61113a6 = -1;
        this.f61114a7 = 400;
        this.f61115a8 = 0.0f;
        this.f61117b0 = new ArrayList();
        this.f61118b1 = null;
        this.f61119b2 = new ArrayList();
        this.f61120b3 = 0;
        this.f61121b4 = false;
        this.f61122b5 = -1;
        this.f61124b7 = 0;
        int i = c0057a1.f44607a9;
        SparseArray sparseArray = c0057a1.f44604a6;
        this.f61114a7 = i;
        this.f61123b6 = c0057a1.f44608b0;
        this.f61116a9 = c0057a1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlResourceParser), R$styleable.Transition);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index == R$styleable.Transition_constraintSetEnd) {
                this.f61109a2 = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                String resourceTypeName = context.getResources().getResourceTypeName(this.f61109a2);
                if ("layout".equals(resourceTypeName)) {
                    C0825lm c0825lm = new C0825lm();
                    c0825lm.m213871a9(context, this.f61109a2);
                    sparseArray.append(this.f61109a2, c0825lm);
                } else if ("xml".equals(resourceTypeName)) {
                    this.f61109a2 = c0057a1.m210014a9(context, this.f61109a2);
                }
            } else if (index == R$styleable.Transition_constraintSetStart) {
                this.f61110a3 = typedArrayObtainStyledAttributes.getResourceId(index, this.f61110a3);
                String resourceTypeName2 = context.getResources().getResourceTypeName(this.f61110a3);
                if ("layout".equals(resourceTypeName2)) {
                    C0825lm c0825lm2 = new C0825lm();
                    c0825lm2.m213871a9(context, this.f61110a3);
                    sparseArray.append(this.f61110a3, c0825lm2);
                } else if ("xml".equals(resourceTypeName2)) {
                    this.f61110a3 = c0057a1.m210014a9(context, this.f61110a3);
                }
            } else if (index == R$styleable.Transition_motionInterpolator) {
                int i3 = typedArrayObtainStyledAttributes.peekValue(index).type;
                if (i3 == 1) {
                    int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                    this.f61113a6 = resourceId;
                    if (resourceId != -1) {
                        this.f61111a4 = -2;
                    }
                } else if (i3 == 3) {
                    String string = typedArrayObtainStyledAttributes.getString(index);
                    this.f61112a5 = string;
                    if (string != null) {
                        if (string.indexOf("/") > 0) {
                            this.f61113a6 = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                            this.f61111a4 = -2;
                        } else {
                            this.f61111a4 = -1;
                        }
                    }
                } else {
                    this.f61111a4 = typedArrayObtainStyledAttributes.getInteger(index, this.f61111a4);
                }
            } else if (index == R$styleable.Transition_duration) {
                int i4 = typedArrayObtainStyledAttributes.getInt(index, this.f61114a7);
                this.f61114a7 = i4;
                if (i4 < 8) {
                    this.f61114a7 = 8;
                }
            } else if (index == R$styleable.Transition_staggered) {
                this.f61115a8 = typedArrayObtainStyledAttributes.getFloat(index, this.f61115a8);
            } else if (index == R$styleable.Transition_autoTransition) {
                this.f61120b3 = typedArrayObtainStyledAttributes.getInteger(index, this.f61120b3);
            } else if (index == R$styleable.Transition_android_id) {
                this.f61107a0 = typedArrayObtainStyledAttributes.getResourceId(index, this.f61107a0);
            } else if (index == R$styleable.Transition_transitionDisable) {
                this.f61121b4 = typedArrayObtainStyledAttributes.getBoolean(index, this.f61121b4);
            } else if (index == R$styleable.Transition_pathMotionArc) {
                this.f61122b5 = typedArrayObtainStyledAttributes.getInteger(index, -1);
            } else if (index == R$styleable.Transition_layoutDuringTransition) {
                this.f61123b6 = typedArrayObtainStyledAttributes.getInteger(index, 0);
            } else if (index == R$styleable.Transition_transitionFlags) {
                this.f61124b7 = typedArrayObtainStyledAttributes.getInteger(index, 0);
            }
        }
        if (this.f61110a3 == -1) {
            this.f61108a1 = true;
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}

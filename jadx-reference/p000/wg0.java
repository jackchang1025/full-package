package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.Xml;
import android.view.View;
import androidx.constraintlayout.motion.widget.C0057a1;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.R$styleable;
import okio.internal.Buffer;
import org.conscrypt.PSKKeyManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class wg0 implements View.OnClickListener {

    /* renamed from: a0 */
    public final xg0 f60909a0;

    /* renamed from: a1 */
    public final int f60910a1;

    /* renamed from: a2 */
    public final int f60911a2;

    public wg0(Context context, xg0 xg0Var, XmlResourceParser xmlResourceParser) {
        this.f60910a1 = -1;
        this.f60911a2 = 17;
        this.f60909a0 = xg0Var;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlResourceParser), R$styleable.OnClick);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            if (index == R$styleable.OnClick_targetId) {
                this.f60910a1 = typedArrayObtainStyledAttributes.getResourceId(index, this.f60910a1);
            } else if (index == R$styleable.OnClick_clickAction) {
                this.f60911a2 = typedArrayObtainStyledAttributes.getInt(index, this.f60911a2);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v2, types: [android.view.View] */
    /* renamed from: a0 */
    public final void m215064a0(MotionLayout motionLayout, int i, xg0 xg0Var) {
        int i2 = this.f60910a1;
        MotionLayout motionLayoutFindViewById = motionLayout;
        if (i2 != -1) {
            motionLayoutFindViewById = motionLayout.findViewById(i2);
        }
        if (motionLayoutFindViewById == null) {
            return;
        }
        int i3 = xg0Var.f61110a3;
        int i4 = xg0Var.f61109a2;
        if (i3 == -1) {
            motionLayoutFindViewById.setOnClickListener(this);
            return;
        }
        int i5 = this.f60911a2;
        int i6 = i5 & 1;
        boolean z = false;
        boolean z2 = (i6 != 0 && i == i3) | (i6 != 0 && i == i3) | ((i5 & PSKKeyManager.MAX_KEY_LENGTH_BYTES) != 0 && i == i3) | ((i5 & 16) != 0 && i == i4);
        if ((i5 & Buffer.SEGMENTING_THRESHOLD) != 0 && i == i4) {
            z = true;
        }
        if (z2 || z) {
            motionLayoutFindViewById.setOnClickListener(this);
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        xg0 xg0Var = this.f60909a0;
        C0057a1 c0057a1 = xg0Var.f61116a9;
        MotionLayout motionLayout = c0057a1.f44598a0;
        if (motionLayout.f44533c7) {
            if (xg0Var.f61110a3 == -1) {
                int currentState = motionLayout.getCurrentState();
                if (currentState == -1) {
                    motionLayout.m210002d2(xg0Var.f61109a2);
                    return;
                }
                xg0 xg0Var2 = new xg0(c0057a1, xg0Var);
                xg0Var2.f61110a3 = currentState;
                xg0Var2.f61109a2 = xg0Var.f61109a2;
                motionLayout.setTransition(xg0Var2);
                motionLayout.m209986b6(1.0f);
                motionLayout.f44578h2 = null;
                return;
            }
            xg0 xg0Var3 = c0057a1.f44600a2;
            int i = this.f60911a2;
            int i2 = i & 1;
            boolean z = false;
            boolean z2 = true;
            boolean z3 = (i2 == 0 && (i & PSKKeyManager.MAX_KEY_LENGTH_BYTES) == 0) ? false : true;
            int i3 = i & 16;
            if (i3 == 0 && (i & Buffer.SEGMENTING_THRESHOLD) == 0) {
                z2 = false;
            }
            if (z3 && z2) {
                if (xg0Var3 != xg0Var) {
                    motionLayout.setTransition(xg0Var);
                }
                if (motionLayout.getCurrentState() != motionLayout.getEndState() && motionLayout.getProgress() <= 0.5f) {
                    z2 = false;
                    z = z3;
                }
            } else {
                z = z3;
            }
            if (xg0Var != xg0Var3) {
                int i4 = xg0Var.f61109a2;
                int i5 = xg0Var.f61110a3;
                if (i5 != -1) {
                    int i6 = motionLayout.f44529c3;
                    if (i6 != i5 && i6 != i4) {
                        return;
                    }
                } else if (motionLayout.f44529c3 == i4) {
                    return;
                }
            }
            if (z && i2 != 0) {
                motionLayout.setTransition(xg0Var);
                motionLayout.m209986b6(1.0f);
                motionLayout.f44578h2 = null;
                return;
            }
            if (z2 && i3 != 0) {
                motionLayout.setTransition(xg0Var);
                motionLayout.m209986b6(0.0f);
            } else if (z && (i & PSKKeyManager.MAX_KEY_LENGTH_BYTES) != 0) {
                motionLayout.setTransition(xg0Var);
                motionLayout.setProgress(1.0f);
            } else {
                if (!z2 || (i & Buffer.SEGMENTING_THRESHOLD) == 0) {
                    return;
                }
                motionLayout.setTransition(xg0Var);
                motionLayout.setProgress(0.0f);
            }
        }
    }
}

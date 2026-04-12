package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.Xml;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.R$styleable;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class bd1 {

    /* renamed from: a0 */
    public int f45811a0;

    /* renamed from: a4 */
    public int f45815a4;

    /* renamed from: a5 */
    public final s80 f45816a5;

    /* renamed from: a6 */
    public final C0820lh f45817a6;

    /* renamed from: a9 */
    public int f45820a9;

    /* renamed from: b0 */
    public String f45821b0;

    /* renamed from: b4 */
    public final Context f45825b4;

    /* renamed from: a1 */
    public int f45812a1 = -1;

    /* renamed from: a2 */
    public boolean f45813a2 = false;

    /* renamed from: a3 */
    public int f45814a3 = 0;

    /* renamed from: a7 */
    public int f45818a7 = -1;

    /* renamed from: a8 */
    public int f45819a8 = -1;

    /* renamed from: b1 */
    public int f45822b1 = 0;

    /* renamed from: b2 */
    public String f45823b2 = null;

    /* renamed from: b3 */
    public int f45824b3 = -1;

    /* renamed from: b5 */
    public int f45826b5 = -1;

    /* renamed from: b6 */
    public int f45827b6 = -1;

    /* renamed from: b7 */
    public int f45828b7 = -1;

    /* renamed from: b8 */
    public int f45829b8 = -1;

    /* renamed from: b9 */
    public int f45830b9 = -1;

    /* renamed from: c0 */
    public int f45831c0 = -1;

    /* JADX WARN: Removed duplicated region for block: B:32:0x008d A[Catch: IOException | XmlPullParserException -> 0x0098, IOException | XmlPullParserException -> 0x0098, TryCatch #0 {IOException | XmlPullParserException -> 0x0098, blocks: (B:3:0x0024, B:11:0x0034, B:11:0x0034, B:33:0x0093, B:33:0x0093, B:14:0x003f, B:14:0x003f, B:15:0x0047, B:15:0x0047, B:32:0x008d, B:32:0x008d, B:17:0x004b, B:17:0x004b, B:22:0x005c, B:22:0x005c, B:20:0x0054, B:20:0x0054, B:23:0x0064, B:23:0x0064, B:25:0x006a, B:25:0x006a, B:26:0x006e, B:26:0x006e, B:28:0x0076, B:28:0x0076, B:29:0x007e, B:29:0x007e, B:31:0x0086, B:31:0x0086), top: B:37:0x0024 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public bd1(Context context, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        this.f45825b4 = context;
        try {
            int eventType = xmlResourceParser.getEventType();
            while (eventType != 1) {
                if (eventType == 2) {
                    String name = xmlResourceParser.getName();
                    switch (name.hashCode()) {
                        case -1962203927:
                            if (!name.equals("ConstraintOverride")) {
                                t60.m214709d0();
                                xmlResourceParser.getLineNumber();
                                break;
                            } else {
                                this.f45817a6 = C0825lm.m213858a3(context, xmlResourceParser);
                                break;
                            }
                        case -1239391468:
                            if (name.equals("KeyFrameSet")) {
                                this.f45816a5 = new s80(context, xmlResourceParser);
                                break;
                            }
                            break;
                        case 61998586:
                            if (name.equals("ViewTransition")) {
                                m210673a1(context, xmlResourceParser);
                                break;
                            }
                            break;
                        case 366511058:
                            if (name.equals("CustomMethod")) {
                                C0798kw.m213758a3(context, xmlResourceParser, this.f45817a6.f57932a6);
                                break;
                            }
                            break;
                        case 1791837707:
                            if (name.equals("CustomAttribute")) {
                                C0798kw.m213758a3(context, xmlResourceParser, this.f45817a6.f57932a6);
                                break;
                            }
                            t60.m214709d0();
                            xmlResourceParser.getLineNumber();
                            break;
                        default:
                            t60.m214709d0();
                            xmlResourceParser.getLineNumber();
                            break;
                    }
                } else if (eventType != 3) {
                    continue;
                } else if ("ViewTransition".equals(xmlResourceParser.getName())) {
                    return;
                }
                eventType = xmlResourceParser.next();
            }
        } catch (IOException | XmlPullParserException unused) {
        }
    }

    /* renamed from: a0 */
    public final boolean m210672a0(View view) {
        String str;
        if (view == null) {
            return false;
        }
        if (this.f45820a9 == -1 && this.f45821b0 == null) {
            return false;
        }
        int i = this.f45828b7;
        boolean z = i == -1 || view.getTag(i) != null;
        int i2 = this.f45829b8;
        boolean z2 = i2 == -1 || view.getTag(i2) == null;
        if (z && z2) {
            if (view.getId() == this.f45820a9) {
                return true;
            }
            if (this.f45821b0 != null && (view.getLayoutParams() instanceof C0801kz) && (str = ((C0801kz) view.getLayoutParams()).f57796f0) != null && str.matches(this.f45821b0)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a1 */
    public final void m210673a1(Context context, XmlResourceParser xmlResourceParser) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlResourceParser), R$styleable.ViewTransition);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            if (index == R$styleable.ViewTransition_android_id) {
                this.f45811a0 = typedArrayObtainStyledAttributes.getResourceId(index, this.f45811a0);
            } else if (index == R$styleable.ViewTransition_motionTarget) {
                if (MotionLayout.f44523i2) {
                    int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, this.f45820a9);
                    this.f45820a9 = resourceId;
                    if (resourceId == -1) {
                        this.f45821b0 = typedArrayObtainStyledAttributes.getString(index);
                    }
                } else if (typedArrayObtainStyledAttributes.peekValue(index).type == 3) {
                    this.f45821b0 = typedArrayObtainStyledAttributes.getString(index);
                } else {
                    this.f45820a9 = typedArrayObtainStyledAttributes.getResourceId(index, this.f45820a9);
                }
            } else if (index == R$styleable.ViewTransition_onStateTransition) {
                this.f45812a1 = typedArrayObtainStyledAttributes.getInt(index, this.f45812a1);
            } else if (index == R$styleable.ViewTransition_transitionDisable) {
                this.f45813a2 = typedArrayObtainStyledAttributes.getBoolean(index, this.f45813a2);
            } else if (index == R$styleable.ViewTransition_pathMotionArc) {
                this.f45814a3 = typedArrayObtainStyledAttributes.getInt(index, this.f45814a3);
            } else if (index == R$styleable.ViewTransition_duration) {
                this.f45818a7 = typedArrayObtainStyledAttributes.getInt(index, this.f45818a7);
            } else if (index == R$styleable.ViewTransition_upDuration) {
                this.f45819a8 = typedArrayObtainStyledAttributes.getInt(index, this.f45819a8);
            } else if (index == R$styleable.ViewTransition_viewTransitionMode) {
                this.f45815a4 = typedArrayObtainStyledAttributes.getInt(index, this.f45815a4);
            } else if (index == R$styleable.ViewTransition_motionInterpolator) {
                int i2 = typedArrayObtainStyledAttributes.peekValue(index).type;
                if (i2 == 1) {
                    int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                    this.f45824b3 = resourceId2;
                    if (resourceId2 != -1) {
                        this.f45822b1 = -2;
                    }
                } else if (i2 == 3) {
                    String string = typedArrayObtainStyledAttributes.getString(index);
                    this.f45823b2 = string;
                    if (string == null || string.indexOf("/") <= 0) {
                        this.f45822b1 = -1;
                    } else {
                        this.f45824b3 = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                        this.f45822b1 = -2;
                    }
                } else {
                    this.f45822b1 = typedArrayObtainStyledAttributes.getInteger(index, this.f45822b1);
                }
            } else if (index == R$styleable.ViewTransition_setsTag) {
                this.f45826b5 = typedArrayObtainStyledAttributes.getResourceId(index, this.f45826b5);
            } else if (index == R$styleable.ViewTransition_clearsTag) {
                this.f45827b6 = typedArrayObtainStyledAttributes.getResourceId(index, this.f45827b6);
            } else if (index == R$styleable.ViewTransition_ifTagSet) {
                this.f45828b7 = typedArrayObtainStyledAttributes.getResourceId(index, this.f45828b7);
            } else if (index == R$styleable.ViewTransition_ifTagNotSet) {
                this.f45829b8 = typedArrayObtainStyledAttributes.getResourceId(index, this.f45829b8);
            } else if (index == R$styleable.ViewTransition_SharedValueId) {
                this.f45831c0 = typedArrayObtainStyledAttributes.getResourceId(index, this.f45831c0);
            } else if (index == R$styleable.ViewTransition_SharedValue) {
                this.f45830b9 = typedArrayObtainStyledAttributes.getInteger(index, this.f45830b9);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public final String toString() {
        return "ViewTransition(" + t60.m214711d2(this.f45825b4, this.f45811a0) + ")";
    }
}

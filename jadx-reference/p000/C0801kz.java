package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.R$styleable;
import io.socket.engineio.parser.Base64;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: kz */
/* loaded from: classes.dex */
public class C0801kz extends ViewGroup.MarginLayoutParams {

    /* renamed from: a0 */
    public int f57746a0;

    /* renamed from: a1 */
    public int f57747a1;

    /* renamed from: a2 */
    public float f57748a2;

    /* renamed from: a3 */
    public final boolean f57749a3;

    /* renamed from: a4 */
    public int f57750a4;

    /* renamed from: a5 */
    public int f57751a5;

    /* renamed from: a6 */
    public int f57752a6;

    /* renamed from: a7 */
    public int f57753a7;

    /* renamed from: a8 */
    public int f57754a8;

    /* renamed from: a9 */
    public int f57755a9;

    /* renamed from: b0 */
    public int f57756b0;

    /* renamed from: b1 */
    public int f57757b1;

    /* renamed from: b2 */
    public int f57758b2;

    /* renamed from: b3 */
    public int f57759b3;

    /* renamed from: b4 */
    public int f57760b4;

    /* renamed from: b5 */
    public int f57761b5;

    /* renamed from: b6 */
    public int f57762b6;

    /* renamed from: b7 */
    public float f57763b7;

    /* renamed from: b8 */
    public int f57764b8;

    /* renamed from: b9 */
    public int f57765b9;

    /* renamed from: c0 */
    public int f57766c0;

    /* renamed from: c1 */
    public int f57767c1;

    /* renamed from: c2 */
    public final int f57768c2;

    /* renamed from: c3 */
    public int f57769c3;

    /* renamed from: c4 */
    public final int f57770c4;

    /* renamed from: c5 */
    public int f57771c5;

    /* renamed from: c6 */
    public int f57772c6;

    /* renamed from: c7 */
    public int f57773c7;

    /* renamed from: c8 */
    public final int f57774c8;

    /* renamed from: c9 */
    public final int f57775c9;

    /* renamed from: d0 */
    public float f57776d0;

    /* renamed from: d1 */
    public float f57777d1;

    /* renamed from: d2 */
    public String f57778d2;

    /* renamed from: d3 */
    public float f57779d3;

    /* renamed from: d4 */
    public float f57780d4;

    /* renamed from: d5 */
    public int f57781d5;

    /* renamed from: d6 */
    public int f57782d6;

    /* renamed from: d7 */
    public int f57783d7;

    /* renamed from: d8 */
    public int f57784d8;

    /* renamed from: d9 */
    public int f57785d9;

    /* renamed from: e0 */
    public int f57786e0;

    /* renamed from: e1 */
    public int f57787e1;

    /* renamed from: e2 */
    public int f57788e2;

    /* renamed from: e3 */
    public float f57789e3;

    /* renamed from: e4 */
    public float f57790e4;

    /* renamed from: e5 */
    public int f57791e5;

    /* renamed from: e6 */
    public int f57792e6;

    /* renamed from: e7 */
    public int f57793e7;

    /* renamed from: e8 */
    public boolean f57794e8;

    /* renamed from: e9 */
    public boolean f57795e9;

    /* renamed from: f0 */
    public String f57796f0;

    /* renamed from: f1 */
    public int f57797f1;

    /* renamed from: f2 */
    public boolean f57798f2;

    /* renamed from: f3 */
    public boolean f57799f3;

    /* renamed from: f4 */
    public boolean f57800f4;

    /* renamed from: f5 */
    public boolean f57801f5;

    /* renamed from: f6 */
    public boolean f57802f6;

    /* renamed from: f7 */
    public boolean f57803f7;

    /* renamed from: f8 */
    public int f57804f8;

    /* renamed from: f9 */
    public int f57805f9;

    /* renamed from: g0 */
    public int f57806g0;

    /* renamed from: g1 */
    public int f57807g1;

    /* renamed from: g2 */
    public int f57808g2;

    /* renamed from: g3 */
    public int f57809g3;

    /* renamed from: g4 */
    public float f57810g4;

    /* renamed from: g5 */
    public int f57811g5;

    /* renamed from: g6 */
    public int f57812g6;

    /* renamed from: g7 */
    public float f57813g7;

    /* renamed from: g8 */
    public C0829lq f57814g8;

    public C0801kz(Context context, AttributeSet attributeSet) throws NumberFormatException {
        super(context, attributeSet);
        this.f57746a0 = -1;
        this.f57747a1 = -1;
        this.f57748a2 = -1.0f;
        this.f57749a3 = true;
        this.f57750a4 = -1;
        this.f57751a5 = -1;
        this.f57752a6 = -1;
        this.f57753a7 = -1;
        this.f57754a8 = -1;
        this.f57755a9 = -1;
        this.f57756b0 = -1;
        this.f57757b1 = -1;
        this.f57758b2 = -1;
        this.f57759b3 = -1;
        this.f57760b4 = -1;
        this.f57761b5 = -1;
        this.f57762b6 = 0;
        this.f57763b7 = 0.0f;
        this.f57764b8 = -1;
        this.f57765b9 = -1;
        this.f57766c0 = -1;
        this.f57767c1 = -1;
        this.f57768c2 = Integer.MIN_VALUE;
        this.f57769c3 = Integer.MIN_VALUE;
        this.f57770c4 = Integer.MIN_VALUE;
        this.f57771c5 = Integer.MIN_VALUE;
        this.f57772c6 = Integer.MIN_VALUE;
        this.f57773c7 = Integer.MIN_VALUE;
        this.f57774c8 = Integer.MIN_VALUE;
        this.f57775c9 = 0;
        this.f57776d0 = 0.5f;
        this.f57777d1 = 0.5f;
        this.f57778d2 = null;
        this.f57779d3 = -1.0f;
        this.f57780d4 = -1.0f;
        this.f57781d5 = 0;
        this.f57782d6 = 0;
        this.f57783d7 = 0;
        this.f57784d8 = 0;
        this.f57785d9 = 0;
        this.f57786e0 = 0;
        this.f57787e1 = 0;
        this.f57788e2 = 0;
        this.f57789e3 = 1.0f;
        this.f57790e4 = 1.0f;
        this.f57791e5 = -1;
        this.f57792e6 = -1;
        this.f57793e7 = -1;
        this.f57794e8 = false;
        this.f57795e9 = false;
        this.f57796f0 = null;
        this.f57797f1 = 0;
        this.f57798f2 = true;
        this.f57799f3 = true;
        this.f57800f4 = false;
        this.f57801f5 = false;
        this.f57802f6 = false;
        this.f57803f7 = false;
        this.f57804f8 = -1;
        this.f57805f9 = -1;
        this.f57806g0 = -1;
        this.f57807g1 = -1;
        this.f57808g2 = Integer.MIN_VALUE;
        this.f57809g3 = Integer.MIN_VALUE;
        this.f57810g4 = 0.5f;
        this.f57814g8 = new C0829lq();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            int i2 = AbstractC0800ky.f57745a0.get(index);
            switch (i2) {
                case 1:
                    this.f57793e7 = typedArrayObtainStyledAttributes.getInt(index, this.f57793e7);
                    break;
                case 2:
                    int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, this.f57761b5);
                    this.f57761b5 = resourceId;
                    if (resourceId == -1) {
                        this.f57761b5 = typedArrayObtainStyledAttributes.getInt(index, -1);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    this.f57762b6 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57762b6);
                    break;
                case 4:
                    float f = typedArrayObtainStyledAttributes.getFloat(index, this.f57763b7) % 360.0f;
                    this.f57763b7 = f;
                    if (f < 0.0f) {
                        this.f57763b7 = (360.0f - f) % 360.0f;
                        break;
                    } else {
                        break;
                    }
                case 5:
                    this.f57746a0 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f57746a0);
                    break;
                case 6:
                    this.f57747a1 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f57747a1);
                    break;
                case 7:
                    this.f57748a2 = typedArrayObtainStyledAttributes.getFloat(index, this.f57748a2);
                    break;
                case 8:
                    int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57750a4);
                    this.f57750a4 = resourceId2;
                    if (resourceId2 == -1) {
                        this.f57750a4 = typedArrayObtainStyledAttributes.getInt(index, -1);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57751a5);
                    this.f57751a5 = resourceId3;
                    if (resourceId3 == -1) {
                        this.f57751a5 = typedArrayObtainStyledAttributes.getInt(index, -1);
                        break;
                    } else {
                        break;
                    }
                case 10:
                    int resourceId4 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57752a6);
                    this.f57752a6 = resourceId4;
                    if (resourceId4 == -1) {
                        this.f57752a6 = typedArrayObtainStyledAttributes.getInt(index, -1);
                        break;
                    } else {
                        break;
                    }
                case oe0.DEFAULT_M /* 11 */:
                    int resourceId5 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57753a7);
                    this.f57753a7 = resourceId5;
                    if (resourceId5 == -1) {
                        this.f57753a7 = typedArrayObtainStyledAttributes.getInt(index, -1);
                        break;
                    } else {
                        break;
                    }
                case FileClientSessionCache.MAX_SIZE /* 12 */:
                    int resourceId6 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57754a8);
                    this.f57754a8 = resourceId6;
                    if (resourceId6 == -1) {
                        this.f57754a8 = typedArrayObtainStyledAttributes.getInt(index, -1);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    int resourceId7 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57755a9);
                    this.f57755a9 = resourceId7;
                    if (resourceId7 == -1) {
                        this.f57755a9 = typedArrayObtainStyledAttributes.getInt(index, -1);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    int resourceId8 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57756b0);
                    this.f57756b0 = resourceId8;
                    if (resourceId8 == -1) {
                        this.f57756b0 = typedArrayObtainStyledAttributes.getInt(index, -1);
                        break;
                    } else {
                        break;
                    }
                case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                    int resourceId9 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57757b1);
                    this.f57757b1 = resourceId9;
                    if (resourceId9 == -1) {
                        this.f57757b1 = typedArrayObtainStyledAttributes.getInt(index, -1);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    int resourceId10 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57758b2);
                    this.f57758b2 = resourceId10;
                    if (resourceId10 == -1) {
                        this.f57758b2 = typedArrayObtainStyledAttributes.getInt(index, -1);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    int resourceId11 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57764b8);
                    this.f57764b8 = resourceId11;
                    if (resourceId11 == -1) {
                        this.f57764b8 = typedArrayObtainStyledAttributes.getInt(index, -1);
                        break;
                    } else {
                        break;
                    }
                case 18:
                    int resourceId12 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57765b9);
                    this.f57765b9 = resourceId12;
                    if (resourceId12 == -1) {
                        this.f57765b9 = typedArrayObtainStyledAttributes.getInt(index, -1);
                        break;
                    } else {
                        break;
                    }
                case Base64.Encoder.LINE_GROUPS /* 19 */:
                    int resourceId13 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57766c0);
                    this.f57766c0 = resourceId13;
                    if (resourceId13 == -1) {
                        this.f57766c0 = typedArrayObtainStyledAttributes.getInt(index, -1);
                        break;
                    } else {
                        break;
                    }
                case 20:
                    int resourceId14 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57767c1);
                    this.f57767c1 = resourceId14;
                    if (resourceId14 == -1) {
                        this.f57767c1 = typedArrayObtainStyledAttributes.getInt(index, -1);
                        break;
                    } else {
                        break;
                    }
                case 21:
                    this.f57768c2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57768c2);
                    break;
                case 22:
                    this.f57769c3 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57769c3);
                    break;
                case 23:
                    this.f57770c4 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57770c4);
                    break;
                case 24:
                    this.f57771c5 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57771c5);
                    break;
                case 25:
                    this.f57772c6 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57772c6);
                    break;
                case 26:
                    this.f57773c7 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57773c7);
                    break;
                case 27:
                    this.f57794e8 = typedArrayObtainStyledAttributes.getBoolean(index, this.f57794e8);
                    break;
                case 28:
                    this.f57795e9 = typedArrayObtainStyledAttributes.getBoolean(index, this.f57795e9);
                    break;
                case 29:
                    this.f57776d0 = typedArrayObtainStyledAttributes.getFloat(index, this.f57776d0);
                    break;
                case 30:
                    this.f57777d1 = typedArrayObtainStyledAttributes.getFloat(index, this.f57777d1);
                    break;
                case 31:
                    this.f57783d7 = typedArrayObtainStyledAttributes.getInt(index, 0);
                    break;
                case 32:
                    this.f57784d8 = typedArrayObtainStyledAttributes.getInt(index, 0);
                    break;
                case 33:
                    try {
                        this.f57785d9 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57785d9);
                        break;
                    } catch (Exception unused) {
                        if (typedArrayObtainStyledAttributes.getInt(index, this.f57785d9) == -2) {
                            this.f57785d9 = -2;
                            break;
                        } else {
                            break;
                        }
                    }
                case 34:
                    try {
                        this.f57787e1 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57787e1);
                        break;
                    } catch (Exception unused2) {
                        if (typedArrayObtainStyledAttributes.getInt(index, this.f57787e1) == -2) {
                            this.f57787e1 = -2;
                            break;
                        } else {
                            break;
                        }
                    }
                case 35:
                    this.f57789e3 = Math.max(0.0f, typedArrayObtainStyledAttributes.getFloat(index, this.f57789e3));
                    this.f57783d7 = 2;
                    break;
                case 36:
                    try {
                        this.f57786e0 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57786e0);
                        break;
                    } catch (Exception unused3) {
                        if (typedArrayObtainStyledAttributes.getInt(index, this.f57786e0) == -2) {
                            this.f57786e0 = -2;
                            break;
                        } else {
                            break;
                        }
                    }
                case 37:
                    try {
                        this.f57788e2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57788e2);
                        break;
                    } catch (Exception unused4) {
                        if (typedArrayObtainStyledAttributes.getInt(index, this.f57788e2) == -2) {
                            this.f57788e2 = -2;
                            break;
                        } else {
                            break;
                        }
                    }
                case 38:
                    this.f57790e4 = Math.max(0.0f, typedArrayObtainStyledAttributes.getFloat(index, this.f57790e4));
                    this.f57784d8 = 2;
                    break;
                default:
                    switch (i2) {
                        case 44:
                            C0825lm.m213863b3(this, typedArrayObtainStyledAttributes.getString(index));
                            break;
                        case 45:
                            this.f57779d3 = typedArrayObtainStyledAttributes.getFloat(index, this.f57779d3);
                            break;
                        case 46:
                            this.f57780d4 = typedArrayObtainStyledAttributes.getFloat(index, this.f57780d4);
                            break;
                        case 47:
                            this.f57781d5 = typedArrayObtainStyledAttributes.getInt(index, 0);
                            break;
                        case 48:
                            this.f57782d6 = typedArrayObtainStyledAttributes.getInt(index, 0);
                            break;
                        case 49:
                            this.f57791e5 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f57791e5);
                            break;
                        case oe0.DEFAULT_T /* 50 */:
                            this.f57792e6 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f57792e6);
                            break;
                        case 51:
                            this.f57796f0 = typedArrayObtainStyledAttributes.getString(index);
                            break;
                        case 52:
                            int resourceId15 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57759b3);
                            this.f57759b3 = resourceId15;
                            if (resourceId15 == -1) {
                                this.f57759b3 = typedArrayObtainStyledAttributes.getInt(index, -1);
                                break;
                            } else {
                                break;
                            }
                        case 53:
                            int resourceId16 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57760b4);
                            this.f57760b4 = resourceId16;
                            if (resourceId16 == -1) {
                                this.f57760b4 = typedArrayObtainStyledAttributes.getInt(index, -1);
                                break;
                            } else {
                                break;
                            }
                        case 54:
                            this.f57775c9 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57775c9);
                            break;
                        case 55:
                            this.f57774c8 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57774c8);
                            break;
                        default:
                            switch (i2) {
                                case 64:
                                    C0825lm.m213862b2(this, typedArrayObtainStyledAttributes, index, 0);
                                    break;
                                case 65:
                                    C0825lm.m213862b2(this, typedArrayObtainStyledAttributes, index, 1);
                                    break;
                                case 66:
                                    this.f57797f1 = typedArrayObtainStyledAttributes.getInt(index, this.f57797f1);
                                    break;
                                case 67:
                                    this.f57749a3 = typedArrayObtainStyledAttributes.getBoolean(index, this.f57749a3);
                                    break;
                            }
                    }
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        m213766a0();
    }

    /* renamed from: a0 */
    public final void m213766a0() {
        this.f57801f5 = false;
        this.f57798f2 = true;
        this.f57799f3 = true;
        int i = ((ViewGroup.MarginLayoutParams) this).width;
        if (i == -2 && this.f57794e8) {
            this.f57798f2 = false;
            if (this.f57783d7 == 0) {
                this.f57783d7 = 1;
            }
        }
        int i2 = ((ViewGroup.MarginLayoutParams) this).height;
        if (i2 == -2 && this.f57795e9) {
            this.f57799f3 = false;
            if (this.f57784d8 == 0) {
                this.f57784d8 = 1;
            }
        }
        if (i == 0 || i == -1) {
            this.f57798f2 = false;
            if (i == 0 && this.f57783d7 == 1) {
                ((ViewGroup.MarginLayoutParams) this).width = -2;
                this.f57794e8 = true;
            }
        }
        if (i2 == 0 || i2 == -1) {
            this.f57799f3 = false;
            if (i2 == 0 && this.f57784d8 == 1) {
                ((ViewGroup.MarginLayoutParams) this).height = -2;
                this.f57795e9 = true;
            }
        }
        if (this.f57748a2 == -1.0f && this.f57746a0 == -1 && this.f57747a1 == -1) {
            return;
        }
        this.f57801f5 = true;
        this.f57798f2 = true;
        this.f57799f3 = true;
        if (!(this.f57814g8 instanceof o30)) {
            this.f57814g8 = new o30();
        }
        ((o30) this.f57814g8).m214153e5(this.f57793e7);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0082  */
    @Override // android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void resolveLayoutDirection(int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = ((ViewGroup.MarginLayoutParams) this).leftMargin;
        int i7 = ((ViewGroup.MarginLayoutParams) this).rightMargin;
        super.resolveLayoutDirection(i);
        boolean z = false;
        boolean z2 = 1 == getLayoutDirection();
        this.f57806g0 = -1;
        this.f57807g1 = -1;
        this.f57804f8 = -1;
        this.f57805f9 = -1;
        this.f57808g2 = this.f57768c2;
        this.f57809g3 = this.f57770c4;
        float f = this.f57776d0;
        this.f57810g4 = f;
        int i8 = this.f57746a0;
        this.f57811g5 = i8;
        int i9 = this.f57747a1;
        this.f57812g6 = i9;
        float f2 = this.f57748a2;
        this.f57813g7 = f2;
        if (z2) {
            int i10 = this.f57764b8;
            if (i10 != -1) {
                this.f57806g0 = i10;
            } else {
                int i11 = this.f57765b9;
                if (i11 != -1) {
                    this.f57807g1 = i11;
                }
                i2 = this.f57766c0;
                if (i2 != -1) {
                    this.f57805f9 = i2;
                    z = true;
                }
                i3 = this.f57767c1;
                if (i3 != -1) {
                    this.f57804f8 = i3;
                    z = true;
                }
                i4 = this.f57772c6;
                if (i4 != Integer.MIN_VALUE) {
                    this.f57809g3 = i4;
                }
                i5 = this.f57773c7;
                if (i5 != Integer.MIN_VALUE) {
                    this.f57808g2 = i5;
                }
                if (z) {
                    this.f57810g4 = 1.0f - f;
                }
                if (this.f57801f5 && this.f57793e7 == 1 && this.f57749a3) {
                    if (f2 == -1.0f) {
                        this.f57813g7 = 1.0f - f2;
                        this.f57811g5 = -1;
                        this.f57812g6 = -1;
                    } else if (i8 != -1) {
                        this.f57812g6 = i8;
                        this.f57811g5 = -1;
                        this.f57813g7 = -1.0f;
                    } else if (i9 != -1) {
                        this.f57811g5 = i9;
                        this.f57812g6 = -1;
                        this.f57813g7 = -1.0f;
                    }
                }
            }
            z = true;
            i2 = this.f57766c0;
            if (i2 != -1) {
            }
            i3 = this.f57767c1;
            if (i3 != -1) {
            }
            i4 = this.f57772c6;
            if (i4 != Integer.MIN_VALUE) {
            }
            i5 = this.f57773c7;
            if (i5 != Integer.MIN_VALUE) {
            }
            if (z) {
            }
            if (this.f57801f5) {
                if (f2 == -1.0f) {
                }
            }
        } else {
            int i12 = this.f57764b8;
            if (i12 != -1) {
                this.f57805f9 = i12;
            }
            int i13 = this.f57765b9;
            if (i13 != -1) {
                this.f57804f8 = i13;
            }
            int i14 = this.f57766c0;
            if (i14 != -1) {
                this.f57806g0 = i14;
            }
            int i15 = this.f57767c1;
            if (i15 != -1) {
                this.f57807g1 = i15;
            }
            int i16 = this.f57772c6;
            if (i16 != Integer.MIN_VALUE) {
                this.f57808g2 = i16;
            }
            int i17 = this.f57773c7;
            if (i17 != Integer.MIN_VALUE) {
                this.f57809g3 = i17;
            }
        }
        if (this.f57766c0 == -1 && this.f57767c1 == -1 && this.f57765b9 == -1 && this.f57764b8 == -1) {
            int i18 = this.f57752a6;
            if (i18 != -1) {
                this.f57806g0 = i18;
                if (((ViewGroup.MarginLayoutParams) this).rightMargin <= 0 && i7 > 0) {
                    ((ViewGroup.MarginLayoutParams) this).rightMargin = i7;
                }
            } else {
                int i19 = this.f57753a7;
                if (i19 != -1) {
                    this.f57807g1 = i19;
                    if (((ViewGroup.MarginLayoutParams) this).rightMargin <= 0 && i7 > 0) {
                        ((ViewGroup.MarginLayoutParams) this).rightMargin = i7;
                    }
                }
            }
            int i20 = this.f57750a4;
            if (i20 != -1) {
                this.f57804f8 = i20;
                if (((ViewGroup.MarginLayoutParams) this).leftMargin > 0 || i6 <= 0) {
                    return;
                }
                ((ViewGroup.MarginLayoutParams) this).leftMargin = i6;
                return;
            }
            int i21 = this.f57751a5;
            if (i21 != -1) {
                this.f57805f9 = i21;
                if (((ViewGroup.MarginLayoutParams) this).leftMargin > 0 || i6 <= 0) {
                    return;
                }
                ((ViewGroup.MarginLayoutParams) this).leftMargin = i6;
            }
        }
    }

    public C0801kz() {
        super(-2, -2);
        this.f57746a0 = -1;
        this.f57747a1 = -1;
        this.f57748a2 = -1.0f;
        this.f57749a3 = true;
        this.f57750a4 = -1;
        this.f57751a5 = -1;
        this.f57752a6 = -1;
        this.f57753a7 = -1;
        this.f57754a8 = -1;
        this.f57755a9 = -1;
        this.f57756b0 = -1;
        this.f57757b1 = -1;
        this.f57758b2 = -1;
        this.f57759b3 = -1;
        this.f57760b4 = -1;
        this.f57761b5 = -1;
        this.f57762b6 = 0;
        this.f57763b7 = 0.0f;
        this.f57764b8 = -1;
        this.f57765b9 = -1;
        this.f57766c0 = -1;
        this.f57767c1 = -1;
        this.f57768c2 = Integer.MIN_VALUE;
        this.f57769c3 = Integer.MIN_VALUE;
        this.f57770c4 = Integer.MIN_VALUE;
        this.f57771c5 = Integer.MIN_VALUE;
        this.f57772c6 = Integer.MIN_VALUE;
        this.f57773c7 = Integer.MIN_VALUE;
        this.f57774c8 = Integer.MIN_VALUE;
        this.f57775c9 = 0;
        this.f57776d0 = 0.5f;
        this.f57777d1 = 0.5f;
        this.f57778d2 = null;
        this.f57779d3 = -1.0f;
        this.f57780d4 = -1.0f;
        this.f57781d5 = 0;
        this.f57782d6 = 0;
        this.f57783d7 = 0;
        this.f57784d8 = 0;
        this.f57785d9 = 0;
        this.f57786e0 = 0;
        this.f57787e1 = 0;
        this.f57788e2 = 0;
        this.f57789e3 = 1.0f;
        this.f57790e4 = 1.0f;
        this.f57791e5 = -1;
        this.f57792e6 = -1;
        this.f57793e7 = -1;
        this.f57794e8 = false;
        this.f57795e9 = false;
        this.f57796f0 = null;
        this.f57797f1 = 0;
        this.f57798f2 = true;
        this.f57799f3 = true;
        this.f57800f4 = false;
        this.f57801f5 = false;
        this.f57802f6 = false;
        this.f57803f7 = false;
        this.f57804f8 = -1;
        this.f57805f9 = -1;
        this.f57806g0 = -1;
        this.f57807g1 = -1;
        this.f57808g2 = Integer.MIN_VALUE;
        this.f57809g3 = Integer.MIN_VALUE;
        this.f57810g4 = 0.5f;
        this.f57814g8 = new C0829lq();
    }

    public C0801kz(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
        this.f57746a0 = -1;
        this.f57747a1 = -1;
        this.f57748a2 = -1.0f;
        this.f57749a3 = true;
        this.f57750a4 = -1;
        this.f57751a5 = -1;
        this.f57752a6 = -1;
        this.f57753a7 = -1;
        this.f57754a8 = -1;
        this.f57755a9 = -1;
        this.f57756b0 = -1;
        this.f57757b1 = -1;
        this.f57758b2 = -1;
        this.f57759b3 = -1;
        this.f57760b4 = -1;
        this.f57761b5 = -1;
        this.f57762b6 = 0;
        this.f57763b7 = 0.0f;
        this.f57764b8 = -1;
        this.f57765b9 = -1;
        this.f57766c0 = -1;
        this.f57767c1 = -1;
        this.f57768c2 = Integer.MIN_VALUE;
        this.f57769c3 = Integer.MIN_VALUE;
        this.f57770c4 = Integer.MIN_VALUE;
        this.f57771c5 = Integer.MIN_VALUE;
        this.f57772c6 = Integer.MIN_VALUE;
        this.f57773c7 = Integer.MIN_VALUE;
        this.f57774c8 = Integer.MIN_VALUE;
        this.f57775c9 = 0;
        this.f57776d0 = 0.5f;
        this.f57777d1 = 0.5f;
        this.f57778d2 = null;
        this.f57779d3 = -1.0f;
        this.f57780d4 = -1.0f;
        this.f57781d5 = 0;
        this.f57782d6 = 0;
        this.f57783d7 = 0;
        this.f57784d8 = 0;
        this.f57785d9 = 0;
        this.f57786e0 = 0;
        this.f57787e1 = 0;
        this.f57788e2 = 0;
        this.f57789e3 = 1.0f;
        this.f57790e4 = 1.0f;
        this.f57791e5 = -1;
        this.f57792e6 = -1;
        this.f57793e7 = -1;
        this.f57794e8 = false;
        this.f57795e9 = false;
        this.f57796f0 = null;
        this.f57797f1 = 0;
        this.f57798f2 = true;
        this.f57799f3 = true;
        this.f57800f4 = false;
        this.f57801f5 = false;
        this.f57802f6 = false;
        this.f57803f7 = false;
        this.f57804f8 = -1;
        this.f57805f9 = -1;
        this.f57806g0 = -1;
        this.f57807g1 = -1;
        this.f57808g2 = Integer.MIN_VALUE;
        this.f57809g3 = Integer.MIN_VALUE;
        this.f57810g4 = 0.5f;
        this.f57814g8 = new C0829lq();
    }
}

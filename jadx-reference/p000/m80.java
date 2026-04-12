package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.R$styleable;
import io.socket.engineio.parser.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class m80 extends k80 {

    /* renamed from: a4 */
    public int f58291a4 = -1;

    /* renamed from: a5 */
    public float f58292a5 = Float.NaN;

    /* renamed from: a6 */
    public float f58293a6 = Float.NaN;

    /* renamed from: a7 */
    public float f58294a7 = Float.NaN;

    /* renamed from: a8 */
    public float f58295a8 = Float.NaN;

    /* renamed from: a9 */
    public float f58296a9 = Float.NaN;

    /* renamed from: b0 */
    public float f58297b0 = Float.NaN;

    /* renamed from: b1 */
    public float f58298b1 = Float.NaN;

    /* renamed from: b2 */
    public float f58299b2 = Float.NaN;

    /* renamed from: b3 */
    public float f58300b3 = Float.NaN;

    /* renamed from: b4 */
    public float f58301b4 = Float.NaN;

    /* renamed from: b5 */
    public float f58302b5 = Float.NaN;

    /* renamed from: b6 */
    public float f58303b6 = Float.NaN;

    /* renamed from: b7 */
    public float f58304b7 = Float.NaN;

    /* renamed from: b8 */
    public float f58305b8 = Float.NaN;

    public m80() {
        this.f57485a3 = new HashMap();
    }

    @Override // p000.k80
    /* renamed from: a0, reason: merged with bridge method [inline-methods] */
    public final k80 clone() {
        m80 m80Var = new m80();
        m80Var.f57482a0 = this.f57482a0;
        m80Var.f57483a1 = this.f57483a1;
        m80Var.f57484a2 = this.f57484a2;
        m80Var.f57485a3 = this.f57485a3;
        m80Var.f58291a4 = this.f58291a4;
        m80Var.f58292a5 = this.f58292a5;
        m80Var.f58293a6 = this.f58293a6;
        m80Var.f58294a7 = this.f58294a7;
        m80Var.f58295a8 = this.f58295a8;
        m80Var.f58296a9 = this.f58296a9;
        m80Var.f58297b0 = this.f58297b0;
        m80Var.f58298b1 = this.f58298b1;
        m80Var.f58299b2 = this.f58299b2;
        m80Var.f58300b3 = this.f58300b3;
        m80Var.f58301b4 = this.f58301b4;
        m80Var.f58302b5 = this.f58302b5;
        m80Var.f58303b6 = this.f58303b6;
        m80Var.f58304b7 = this.f58304b7;
        m80Var.f58305b8 = this.f58305b8;
        return m80Var;
    }

    @Override // p000.k80
    /* renamed from: a1 */
    public final void mo213473a1(HashSet hashSet) {
        if (!Float.isNaN(this.f58292a5)) {
            hashSet.add("alpha");
        }
        if (!Float.isNaN(this.f58293a6)) {
            hashSet.add("elevation");
        }
        if (!Float.isNaN(this.f58294a7)) {
            hashSet.add("rotation");
        }
        if (!Float.isNaN(this.f58295a8)) {
            hashSet.add("rotationX");
        }
        if (!Float.isNaN(this.f58296a9)) {
            hashSet.add("rotationY");
        }
        if (!Float.isNaN(this.f58297b0)) {
            hashSet.add("transformPivotX");
        }
        if (!Float.isNaN(this.f58298b1)) {
            hashSet.add("transformPivotY");
        }
        if (!Float.isNaN(this.f58302b5)) {
            hashSet.add("translationX");
        }
        if (!Float.isNaN(this.f58303b6)) {
            hashSet.add("translationY");
        }
        if (!Float.isNaN(this.f58304b7)) {
            hashSet.add("translationZ");
        }
        if (!Float.isNaN(this.f58299b2)) {
            hashSet.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.f58300b3)) {
            hashSet.add("scaleX");
        }
        if (!Float.isNaN(this.f58301b4)) {
            hashSet.add("scaleY");
        }
        if (!Float.isNaN(this.f58305b8)) {
            hashSet.add("progress");
        }
        if (this.f57485a3.size() > 0) {
            Iterator it = this.f57485a3.keySet().iterator();
            while (it.hasNext()) {
                hashSet.add("CUSTOM," + ((String) it.next()));
            }
        }
    }

    @Override // p000.k80
    /* renamed from: a2 */
    public final void mo213474a2(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.KeyAttribute);
        SparseIntArray sparseIntArray = l80.f57844a0;
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            SparseIntArray sparseIntArray2 = l80.f57844a0;
            switch (sparseIntArray2.get(index)) {
                case 1:
                    this.f58292a5 = typedArrayObtainStyledAttributes.getFloat(index, this.f58292a5);
                    break;
                case 2:
                    this.f58293a6 = typedArrayObtainStyledAttributes.getDimension(index, this.f58293a6);
                    break;
                case 3:
                case oe0.DEFAULT_M /* 11 */:
                default:
                    Integer.toHexString(index);
                    sparseIntArray2.get(index);
                    break;
                case 4:
                    this.f58294a7 = typedArrayObtainStyledAttributes.getFloat(index, this.f58294a7);
                    break;
                case 5:
                    this.f58295a8 = typedArrayObtainStyledAttributes.getFloat(index, this.f58295a8);
                    break;
                case 6:
                    this.f58296a9 = typedArrayObtainStyledAttributes.getFloat(index, this.f58296a9);
                    break;
                case 7:
                    this.f58300b3 = typedArrayObtainStyledAttributes.getFloat(index, this.f58300b3);
                    break;
                case 8:
                    this.f58299b2 = typedArrayObtainStyledAttributes.getFloat(index, this.f58299b2);
                    break;
                case 9:
                    typedArrayObtainStyledAttributes.getString(index);
                    break;
                case 10:
                    if (MotionLayout.f44523i2) {
                        int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, this.f57483a1);
                        this.f57483a1 = resourceId;
                        if (resourceId == -1) {
                            this.f57484a2 = typedArrayObtainStyledAttributes.getString(index);
                            break;
                        } else {
                            break;
                        }
                    } else if (typedArrayObtainStyledAttributes.peekValue(index).type == 3) {
                        this.f57484a2 = typedArrayObtainStyledAttributes.getString(index);
                        break;
                    } else {
                        this.f57483a1 = typedArrayObtainStyledAttributes.getResourceId(index, this.f57483a1);
                        break;
                    }
                case FileClientSessionCache.MAX_SIZE /* 12 */:
                    this.f57482a0 = typedArrayObtainStyledAttributes.getInt(index, this.f57482a0);
                    break;
                case 13:
                    this.f58291a4 = typedArrayObtainStyledAttributes.getInteger(index, this.f58291a4);
                    break;
                case 14:
                    this.f58301b4 = typedArrayObtainStyledAttributes.getFloat(index, this.f58301b4);
                    break;
                case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                    this.f58302b5 = typedArrayObtainStyledAttributes.getDimension(index, this.f58302b5);
                    break;
                case 16:
                    this.f58303b6 = typedArrayObtainStyledAttributes.getDimension(index, this.f58303b6);
                    break;
                case 17:
                    this.f58304b7 = typedArrayObtainStyledAttributes.getDimension(index, this.f58304b7);
                    break;
                case 18:
                    this.f58305b8 = typedArrayObtainStyledAttributes.getFloat(index, this.f58305b8);
                    break;
                case Base64.Encoder.LINE_GROUPS /* 19 */:
                    this.f58297b0 = typedArrayObtainStyledAttributes.getDimension(index, this.f58297b0);
                    break;
                case 20:
                    this.f58298b1 = typedArrayObtainStyledAttributes.getDimension(index, this.f58298b1);
                    break;
            }
        }
    }

    @Override // p000.k80
    /* renamed from: a3 */
    public final void mo213475a3(HashMap map) {
        if (this.f58291a4 == -1) {
            return;
        }
        if (!Float.isNaN(this.f58292a5)) {
            map.put("alpha", Integer.valueOf(this.f58291a4));
        }
        if (!Float.isNaN(this.f58293a6)) {
            map.put("elevation", Integer.valueOf(this.f58291a4));
        }
        if (!Float.isNaN(this.f58294a7)) {
            map.put("rotation", Integer.valueOf(this.f58291a4));
        }
        if (!Float.isNaN(this.f58295a8)) {
            map.put("rotationX", Integer.valueOf(this.f58291a4));
        }
        if (!Float.isNaN(this.f58296a9)) {
            map.put("rotationY", Integer.valueOf(this.f58291a4));
        }
        if (!Float.isNaN(this.f58297b0)) {
            map.put("transformPivotX", Integer.valueOf(this.f58291a4));
        }
        if (!Float.isNaN(this.f58298b1)) {
            map.put("transformPivotY", Integer.valueOf(this.f58291a4));
        }
        if (!Float.isNaN(this.f58302b5)) {
            map.put("translationX", Integer.valueOf(this.f58291a4));
        }
        if (!Float.isNaN(this.f58303b6)) {
            map.put("translationY", Integer.valueOf(this.f58291a4));
        }
        if (!Float.isNaN(this.f58304b7)) {
            map.put("translationZ", Integer.valueOf(this.f58291a4));
        }
        if (!Float.isNaN(this.f58299b2)) {
            map.put("transitionPathRotate", Integer.valueOf(this.f58291a4));
        }
        if (!Float.isNaN(this.f58300b3)) {
            map.put("scaleX", Integer.valueOf(this.f58291a4));
        }
        if (!Float.isNaN(this.f58301b4)) {
            map.put("scaleY", Integer.valueOf(this.f58291a4));
        }
        if (!Float.isNaN(this.f58305b8)) {
            map.put("progress", Integer.valueOf(this.f58291a4));
        }
        if (this.f57485a3.size() > 0) {
            Iterator it = this.f57485a3.keySet().iterator();
            while (it.hasNext()) {
                map.put(AbstractC0003a2.m48c9("CUSTOM,", (String) it.next()), Integer.valueOf(this.f58291a4));
            }
        }
    }

    /* renamed from: a5 */
    public final void m213951a5(Object obj, String str) {
        switch (str) {
            case "motionProgress":
                this.f58305b8 = k80.m213471a4((Number) obj);
                break;
            case "transitionEasing":
                obj.toString();
                break;
            case "rotationX":
                this.f58295a8 = k80.m213471a4((Number) obj);
                break;
            case "rotationY":
                this.f58296a9 = k80.m213471a4((Number) obj);
                break;
            case "translationX":
                this.f58302b5 = k80.m213471a4((Number) obj);
                break;
            case "translationY":
                this.f58303b6 = k80.m213471a4((Number) obj);
                break;
            case "translationZ":
                this.f58304b7 = k80.m213471a4((Number) obj);
                break;
            case "scaleX":
                this.f58300b3 = k80.m213471a4((Number) obj);
                break;
            case "scaleY":
                this.f58301b4 = k80.m213471a4((Number) obj);
                break;
            case "transformPivotX":
                this.f58297b0 = k80.m213471a4((Number) obj);
                break;
            case "transformPivotY":
                this.f58298b1 = k80.m213471a4((Number) obj);
                break;
            case "rotation":
                this.f58294a7 = k80.m213471a4((Number) obj);
                break;
            case "elevation":
                this.f58293a6 = k80.m213471a4((Number) obj);
                break;
            case "transitionPathRotate":
                this.f58299b2 = k80.m213471a4((Number) obj);
                break;
            case "alpha":
                this.f58292a5 = k80.m213471a4((Number) obj);
                break;
            case "curveFit":
                Number number = (Number) obj;
                this.f58291a4 = number instanceof Integer ? ((Integer) number).intValue() : Integer.parseInt(number.toString());
                break;
            case "visibility":
                if (!(obj instanceof Boolean)) {
                    Boolean.parseBoolean(obj.toString());
                    break;
                }
                break;
        }
    }
}

package p000;

import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Property;
import android.view.View;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec;
import java.util.Arrays;
import java.util.WeakHashMap;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: gt */
/* loaded from: classes.dex */
public final class C0556gt extends Property {

    /* renamed from: a0 */
    public final /* synthetic */ int f56570a0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0556gt(Class cls, String str, int i) {
        super(cls, str);
        this.f56570a0 = i;
    }

    @Override // android.util.Property
    public final Object get(Object obj) {
        switch (this.f56570a0) {
            case 0:
                return null;
            case 1:
                return null;
            case 2:
                return null;
            case 3:
                return null;
            case 4:
                return null;
            case 5:
                return Float.valueOf(((C0623ij) obj).f56903a8);
            case 6:
                return Float.valueOf(((C0623ij) obj).f56904a9);
            case 7:
                return Float.valueOf(((AbstractC1277tx) obj).m214794a1());
            case 8:
                return Float.valueOf(((View) obj).getLayoutParams().width);
            case 9:
                return Float.valueOf(((View) obj).getLayoutParams().height);
            case 10:
                WeakHashMap weakHashMap = xa1.f61054a0;
                return Float.valueOf(ga1.m212906a5((View) obj));
            case oe0.DEFAULT_M /* 11 */:
                WeakHashMap weakHashMap2 = xa1.f61054a0;
                return Float.valueOf(ga1.m212905a4((View) obj));
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                return Float.valueOf(((sa0) obj).f59947a8);
            case 13:
                return Float.valueOf(((ua0) obj).f60368a9);
            case 14:
                return Float.valueOf(((SwitchCompat) obj).f44071c5);
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                return Float.valueOf(hd1.f56654a0.mo213494d5((View) obj));
            default:
                WeakHashMap weakHashMap3 = xa1.f61054a0;
                return ha1.m213015a0((View) obj);
        }
    }

    @Override // android.util.Property
    public final void set(Object obj, Object obj2) {
        C0623ij c0623ij;
        switch (this.f56570a0) {
            case 0:
                C0559gw c0559gw = (C0559gw) obj;
                PointF pointF = (PointF) obj2;
                c0559gw.getClass();
                c0559gw.f56575a0 = Math.round(pointF.x);
                int iRound = Math.round(pointF.y);
                c0559gw.f56576a1 = iRound;
                int i = c0559gw.f56580a5 + 1;
                c0559gw.f56580a5 = i;
                if (i == c0559gw.f56581a6) {
                    hd1.m213026a0(c0559gw.f56579a4, c0559gw.f56575a0, iRound, c0559gw.f56577a2, c0559gw.f56578a3);
                    c0559gw.f56580a5 = 0;
                    c0559gw.f56581a6 = 0;
                    break;
                }
                break;
            case 1:
                C0559gw c0559gw2 = (C0559gw) obj;
                PointF pointF2 = (PointF) obj2;
                c0559gw2.getClass();
                c0559gw2.f56577a2 = Math.round(pointF2.x);
                int iRound2 = Math.round(pointF2.y);
                c0559gw2.f56578a3 = iRound2;
                int i2 = c0559gw2.f56581a6 + 1;
                c0559gw2.f56581a6 = i2;
                if (c0559gw2.f56580a5 == i2) {
                    hd1.m213026a0(c0559gw2.f56579a4, c0559gw2.f56575a0, c0559gw2.f56576a1, c0559gw2.f56577a2, iRound2);
                    c0559gw2.f56580a5 = 0;
                    c0559gw2.f56581a6 = 0;
                    break;
                }
                break;
            case 2:
                View view = (View) obj;
                PointF pointF3 = (PointF) obj2;
                hd1.m213026a0(view, view.getLeft(), view.getTop(), Math.round(pointF3.x), Math.round(pointF3.y));
                break;
            case 3:
                View view2 = (View) obj;
                PointF pointF4 = (PointF) obj2;
                hd1.m213026a0(view2, Math.round(pointF4.x), Math.round(pointF4.y), view2.getRight(), view2.getBottom());
                break;
            case 4:
                View view3 = (View) obj;
                PointF pointF5 = (PointF) obj2;
                int iRound3 = Math.round(pointF5.x);
                int iRound4 = Math.round(pointF5.y);
                hd1.m213026a0(view3, iRound3, iRound4, view3.getWidth() + iRound3, view3.getHeight() + iRound4);
                break;
            case 5:
                C0623ij c0623ij2 = (C0623ij) obj;
                float fFloatValue = ((Float) obj2).floatValue();
                c0623ij2.f56903a8 = fFloatValue;
                int i3 = (int) (5400.0f * fFloatValue);
                C1487yo c1487yo = c0623ij2.f56900a5;
                float[] fArr = (float[]) c0623ij2.f55539a1;
                float f = fFloatValue * 1520.0f;
                fArr[0] = (-20.0f) + f;
                fArr[1] = f;
                for (int i4 = 0; i4 < 4; i4++) {
                    float f2 = 667;
                    fArr[1] = (c1487yo.getInterpolation((i3 - C0623ij.f56893b1[i4]) / f2) * 250.0f) + fArr[1];
                    fArr[0] = (c1487yo.getInterpolation((i3 - C0623ij.f56894b2[i4]) / f2) * 250.0f) + fArr[0];
                }
                float f3 = fArr[0];
                float f4 = fArr[1];
                float f5 = ((f4 - f3) * c0623ij2.f56904a9) + f3;
                fArr[0] = f5;
                fArr[0] = f5 / 360.0f;
                fArr[1] = f4 / 360.0f;
                CircularProgressIndicatorSpec circularProgressIndicatorSpec = c0623ij2.f56901a6;
                int i5 = 0;
                while (true) {
                    if (i5 < 4) {
                        float f6 = (i3 - C0623ij.f56895b3[i5]) / 333;
                        if (f6 < 0.0f || f6 > 1.0f) {
                            i5++;
                            c0623ij2 = c0623ij2;
                        } else {
                            int i6 = i5 + c0623ij2.f56902a7;
                            int[] iArr = circularProgressIndicatorSpec.f55695a2;
                            int length = i6 % iArr.length;
                            int length2 = (length + 1) % iArr.length;
                            int iM213561a8 = kj1.m213561a8(iArr[length], ((n50) c0623ij2.f55538a0).f60300a9);
                            int iM213561a82 = kj1.m213561a8(circularProgressIndicatorSpec.f55695a2[length2], ((n50) c0623ij2.f55538a0).f60300a9);
                            float interpolation = c1487yo.getInterpolation(f6);
                            int[] iArr2 = (int[]) c0623ij2.f55540a2;
                            Integer numValueOf = Integer.valueOf(iM213561a8);
                            Integer numValueOf2 = Integer.valueOf(iM213561a82);
                            int iIntValue = numValueOf.intValue();
                            float f7 = ((iIntValue >> 24) & v10.MASK) / 255.0f;
                            float f8 = ((iIntValue >> 16) & v10.MASK) / 255.0f;
                            float f9 = ((iIntValue >> 8) & v10.MASK) / 255.0f;
                            int iIntValue2 = numValueOf2.intValue();
                            float f10 = ((iIntValue2 >> 24) & v10.MASK) / 255.0f;
                            float f11 = ((iIntValue2 >> 16) & v10.MASK) / 255.0f;
                            float f12 = ((iIntValue2 >> 8) & v10.MASK) / 255.0f;
                            float fPow = (float) Math.pow(f8, 2.2d);
                            c0623ij = c0623ij2;
                            float fPow2 = (float) Math.pow(f9, 2.2d);
                            float fPow3 = (float) Math.pow((iIntValue & v10.MASK) / 255.0f, 2.2d);
                            float fPow4 = (float) Math.pow(f11, 2.2d);
                            float fPow5 = (float) Math.pow(f12, 2.2d);
                            float fPow6 = (float) Math.pow((iIntValue2 & v10.MASK) / 255.0f, 2.2d);
                            float fM19a0 = AbstractC0003a2.m19a0(f10, f7, interpolation, f7);
                            float fM19a02 = AbstractC0003a2.m19a0(fPow4, fPow, interpolation, fPow);
                            float fM19a03 = AbstractC0003a2.m19a0(fPow5, fPow2, interpolation, fPow2);
                            float fM19a04 = AbstractC0003a2.m19a0(fPow6, fPow3, interpolation, fPow3);
                            iArr2[0] = Integer.valueOf((Math.round(((float) Math.pow(fM19a02, 0.45454545454545453d)) * 255.0f) << 16) | (Math.round(fM19a0 * 255.0f) << 24) | (Math.round(((float) Math.pow(fM19a03, 0.45454545454545453d)) * 255.0f) << 8) | Math.round(((float) Math.pow(fM19a04, 0.45454545454545453d)) * 255.0f)).intValue();
                        }
                    } else {
                        c0623ij = c0623ij2;
                    }
                }
                ((n50) c0623ij.f55538a0).invalidateSelf();
                break;
            case 6:
                ((C0623ij) obj).f56904a9 = ((Float) obj2).floatValue();
                break;
            case 7:
                AbstractC1277tx abstractC1277tx = (AbstractC1277tx) obj;
                float fFloatValue2 = ((Float) obj2).floatValue();
                if (abstractC1277tx.f60298a7 != fFloatValue2) {
                    abstractC1277tx.f60298a7 = fFloatValue2;
                    abstractC1277tx.invalidateSelf();
                    break;
                }
                break;
            case 8:
                View view4 = (View) obj;
                view4.getLayoutParams().width = ((Float) obj2).intValue();
                view4.requestLayout();
                break;
            case 9:
                View view5 = (View) obj;
                view5.getLayoutParams().height = ((Float) obj2).intValue();
                view5.requestLayout();
                break;
            case 10:
                View view6 = (View) obj;
                int iIntValue3 = ((Float) obj2).intValue();
                int paddingTop = view6.getPaddingTop();
                WeakHashMap weakHashMap = xa1.f61054a0;
                ga1.m212911b0(view6, iIntValue3, paddingTop, ga1.m212905a4(view6), view6.getPaddingBottom());
                break;
            case oe0.DEFAULT_M /* 11 */:
                View view7 = (View) obj;
                WeakHashMap weakHashMap2 = xa1.f61054a0;
                ga1.m212911b0(view7, ga1.m212906a5(view7), view7.getPaddingTop(), ((Float) obj2).intValue(), view7.getPaddingBottom());
                break;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                sa0 sa0Var = (sa0) obj;
                sa0Var.f59947a8 = ((Float) obj2).floatValue();
                float[] fArr2 = (float[]) sa0Var.f55539a1;
                fArr2[0] = 0.0f;
                float f13 = ((int) (r8 * 333.0f)) / 667;
                C1487yo c1487yo2 = sa0Var.f59943a4;
                float interpolation2 = c1487yo2.getInterpolation(f13);
                fArr2[2] = interpolation2;
                fArr2[1] = interpolation2;
                float interpolation3 = c1487yo2.getInterpolation(f13 + 0.49925038f);
                fArr2[4] = interpolation3;
                fArr2[3] = interpolation3;
                fArr2[5] = 1.0f;
                if (sa0Var.f59946a7 && interpolation3 < 1.0f) {
                    int[] iArr3 = (int[]) sa0Var.f55540a2;
                    iArr3[2] = iArr3[1];
                    iArr3[1] = iArr3[0];
                    iArr3[0] = kj1.m213561a8(sa0Var.f59944a5.f55695a2[sa0Var.f59945a6], ((n50) sa0Var.f55538a0).f60300a9);
                    sa0Var.f59946a7 = false;
                }
                ((n50) sa0Var.f55538a0).invalidateSelf();
                break;
            case 13:
                ua0 ua0Var = (ua0) obj;
                float fFloatValue3 = ((Float) obj2).floatValue();
                ua0Var.f60368a9 = fFloatValue3;
                int i7 = (int) (fFloatValue3 * 1800.0f);
                for (int i8 = 0; i8 < 4; i8++) {
                    ((float[]) ua0Var.f55539a1)[i8] = Math.max(0.0f, Math.min(1.0f, ua0Var.f60364a5[i8].getInterpolation((i7 - ua0.f60360b2[i8]) / ua0.f60359b1[i8])));
                }
                if (ua0Var.f60367a8) {
                    Arrays.fill((int[]) ua0Var.f55540a2, kj1.m213561a8(ua0Var.f60365a6.f55695a2[ua0Var.f60366a7], ((n50) ua0Var.f55538a0).f60300a9));
                    ua0Var.f60367a8 = false;
                }
                ((n50) ua0Var.f55538a0).invalidateSelf();
                break;
            case 14:
                ((SwitchCompat) obj).setThumbPosition(((Float) obj2).floatValue());
                break;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                hd1.f56654a0.mo213495e9((View) obj, ((Float) obj2).floatValue());
                break;
            default:
                WeakHashMap weakHashMap3 = xa1.f61054a0;
                ha1.m213017a2((View) obj, (Rect) obj2);
                break;
        }
    }
}

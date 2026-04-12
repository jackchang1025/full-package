package p000;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import androidx.core.R$attr;
import androidx.core.R$styleable;
import java.io.IOException;
import java.lang.reflect.Array;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: jm */
/* loaded from: classes.dex */
public abstract class AbstractC0723jm {

    /* renamed from: a0 */
    public static final ThreadLocal f57346a0 = new ThreadLocal();

    /* renamed from: a0 */
    public static ColorStateList m213328a0(Resources resources, XmlResourceParser xmlResourceParser, Resources.Theme theme) {
        int next;
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlResourceParser);
        do {
            next = xmlResourceParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next == 2) {
            return m213329a1(resources, xmlResourceParser, attributeSetAsAttributeSet, theme);
        }
        throw new XmlPullParserException("No start tag found");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0093  */
    /* JADX WARN: Type inference failed for: r16v3 */
    /* JADX WARN: Type inference failed for: r16v4 */
    /* JADX WARN: Type inference failed for: r1v23, types: [java.lang.Object, java.lang.Object[]] */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ColorStateList m213329a1(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, Resources.NotFoundException, IOException {
        int depth;
        int color;
        int[] iArr;
        int i;
        int iM210580c8;
        float f;
        int i2;
        float fCbrt;
        Resources resources2 = resources;
        AttributeSet attributeSet2 = attributeSet;
        Resources.Theme theme2 = theme;
        String name = xmlPullParser.getName();
        if (!name.equals("selector")) {
            throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid color state list tag " + name);
        }
        boolean z = true;
        int depth2 = xmlPullParser.getDepth() + 1;
        int[][] iArr2 = new int[20][];
        int[] iArr3 = new int[20];
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int next = xmlPullParser.next();
            if (next == z || ((depth = xmlPullParser.getDepth()) < depth2 && next == 3)) {
                break;
            }
            if (next == 2 && depth <= depth2 && xmlPullParser.getName().equals("item")) {
                int[] iArr4 = R$styleable.ColorStateListItem;
                TypedArray typedArrayObtainAttributes = theme2 == null ? resources2.obtainAttributes(attributeSet2, iArr4) : theme2.obtainStyledAttributes(attributeSet2, iArr4, i3, i3);
                int resourceId = typedArrayObtainAttributes.getResourceId(R$styleable.ColorStateListItem_android_color, -1);
                if (resourceId != -1) {
                    ThreadLocal threadLocal = f57346a0;
                    TypedValue typedValue = (TypedValue) threadLocal.get();
                    if (typedValue == null) {
                        typedValue = new TypedValue();
                        threadLocal.set(typedValue);
                    }
                    resources2.getValue(resourceId, typedValue, z);
                    int i5 = typedValue.type;
                    if (i5 < 28 || i5 > 31) {
                        try {
                            color = m213328a0(resources2, resources2.getXml(resourceId), theme2).getDefaultColor();
                        } catch (Exception unused) {
                            color = typedArrayObtainAttributes.getColor(R$styleable.ColorStateListItem_android_color, -65281);
                        }
                    } else {
                        color = typedArrayObtainAttributes.getColor(R$styleable.ColorStateListItem_android_color, -65281);
                    }
                    float f2 = 1.0f;
                    float f3 = typedArrayObtainAttributes.hasValue(R$styleable.ColorStateListItem_android_alpha) ? typedArrayObtainAttributes.getFloat(R$styleable.ColorStateListItem_android_alpha, 1.0f) : typedArrayObtainAttributes.hasValue(R$styleable.ColorStateListItem_alpha) ? typedArrayObtainAttributes.getFloat(R$styleable.ColorStateListItem_alpha, 1.0f) : 1.0f;
                    boolean z2 = z;
                    float f4 = (Build.VERSION.SDK_INT < 31 || !typedArrayObtainAttributes.hasValue(R$styleable.ColorStateListItem_android_lStar)) ? typedArrayObtainAttributes.getFloat(R$styleable.ColorStateListItem_lStar, -1.0f) : typedArrayObtainAttributes.getFloat(R$styleable.ColorStateListItem_android_lStar, -1.0f);
                    typedArrayObtainAttributes.recycle();
                    int attributeCount = attributeSet2.getAttributeCount();
                    int[] iArr5 = new int[attributeCount];
                    int i6 = i3;
                    int i7 = i6;
                    while (i6 < attributeCount) {
                        float f5 = f2;
                        int attributeNameResource = attributeSet2.getAttributeNameResource(i6);
                        if (attributeNameResource != 16843173 && attributeNameResource != 16843551 && attributeNameResource != R$attr.alpha && attributeNameResource != R$attr.lStar) {
                            int i8 = i7 + 1;
                            if (!attributeSet2.getAttributeBooleanValue(i6, false)) {
                                attributeNameResource = -attributeNameResource;
                            }
                            iArr5[i7] = attributeNameResource;
                            i7 = i8;
                        }
                        i6++;
                        f2 = f5;
                    }
                    float f6 = f2;
                    int[] iArrTrimStateSet = StateSet.trimStateSet(iArr5, i7);
                    float f7 = 100.0f;
                    boolean z3 = (f4 < 0.0f || f4 > 100.0f) ? false : z2 ? 1 : 0;
                    if (f3 != f6 || z3) {
                        int iM212476a4 = cq0.m212476a4((int) ((Color.alpha(color) * f3) + 0.5f), 0, v10.MASK);
                        if (z3) {
                            C0504fp c0504fpM212842a1 = C0504fp.m212842a1(color);
                            float f8 = c0504fpM212842a1.f56306a0;
                            float f9 = c0504fpM212842a1.f56307a1;
                            ld1 ld1Var = ld1.f57886b0;
                            if (f9 < 1.0d || Math.round(f4) <= 0.0d || Math.round(f4) >= 100.0d) {
                                iArr = iArrTrimStateSet;
                                i = depth2;
                                iM210580c8 = b81.m210580c8(f4);
                            } else {
                                float fMin = f8 < 0.0f ? 0.0f : Math.min(360.0f, f8);
                                float f10 = 0.0f;
                                float f11 = f9;
                                boolean z4 = z2 ? 1 : 0;
                                C0504fp c0504fp = null;
                                while (true) {
                                    if (Math.abs(f10 - f9) >= 0.4f) {
                                        float f12 = 1000.0f;
                                        float f13 = f7;
                                        float f14 = 0.0f;
                                        float f15 = 1000.0f;
                                        C0504fp c0504fp2 = null;
                                        while (true) {
                                            if (Math.abs(f14 - f13) <= 0.01f) {
                                                iArr = iArrTrimStateSet;
                                                i = depth2;
                                                f = f7;
                                                break;
                                            }
                                            f = f7;
                                            float f16 = ((f13 - f14) / 2.0f) + f14;
                                            iArr = iArrTrimStateSet;
                                            int iM212845a3 = C0504fp.m212843a2(f16, f11, fMin).m212845a3(ld1.f57886b0);
                                            float fM210586d5 = b81.m210586d5(Color.red(iM212845a3));
                                            float fM210586d52 = b81.m210586d5(Color.green(iM212845a3));
                                            float fM210586d53 = b81.m210586d5(Color.blue(iM212845a3));
                                            float[] fArr = b81.f45732a3[z2 ? 1 : 0];
                                            float f17 = ((fM210586d53 * fArr[2]) + ((fM210586d52 * fArr[z2 ? 1 : 0]) + (fM210586d5 * fArr[0]))) / f;
                                            if (f17 <= 0.008856452f) {
                                                fCbrt = f17 * 903.2963f;
                                                i2 = iM212845a3;
                                            } else {
                                                i2 = iM212845a3;
                                                fCbrt = (((float) Math.cbrt(f17)) * 116.0f) - 16.0f;
                                            }
                                            float fAbs = Math.abs(f4 - fCbrt);
                                            if (fAbs < 0.2f) {
                                                C0504fp c0504fpM212842a12 = C0504fp.m212842a1(i2);
                                                C0504fp c0504fpM212843a2 = C0504fp.m212843a2(c0504fpM212842a12.f56308a2, c0504fpM212842a12.f56307a1, fMin);
                                                float f18 = c0504fpM212842a12.f56309a3 - c0504fpM212843a2.f56309a3;
                                                float f19 = c0504fpM212842a12.f56310a4 - c0504fpM212843a2.f56310a4;
                                                float f20 = c0504fpM212842a12.f56311a5 - c0504fpM212843a2.f56311a5;
                                                i = depth2;
                                                float fPow = (float) (Math.pow(Math.sqrt((f20 * f20) + (f19 * f19) + (f18 * f18)), 0.63d) * 1.41d);
                                                if (fPow <= f6) {
                                                    f15 = fPow;
                                                    f12 = fAbs;
                                                    c0504fp2 = c0504fpM212842a12;
                                                }
                                            } else {
                                                i = depth2;
                                            }
                                            if (f12 == 0.0f && f15 == 0.0f) {
                                                break;
                                            }
                                            if (fCbrt < f4) {
                                                f14 = f16;
                                            } else {
                                                f13 = f16;
                                            }
                                            f7 = f;
                                            iArrTrimStateSet = iArr;
                                            depth2 = i;
                                        }
                                        C0504fp c0504fp3 = c0504fp2;
                                        if (!z4) {
                                            if (c0504fp3 == null) {
                                                f9 = f11;
                                            } else {
                                                c0504fp = c0504fp3;
                                                f10 = f11;
                                            }
                                            f11 = ((f9 - f10) / 2.0f) + f10;
                                            f7 = f;
                                            iArrTrimStateSet = iArr;
                                            depth2 = i;
                                        } else {
                                            if (c0504fp3 != null) {
                                                iM210580c8 = c0504fp3.m212845a3(ld1Var);
                                                break;
                                            }
                                            f11 = ((f9 - f10) / 2.0f) + f10;
                                            f7 = f;
                                            iArrTrimStateSet = iArr;
                                            depth2 = i;
                                            z4 = false;
                                        }
                                    } else {
                                        iArr = iArrTrimStateSet;
                                        i = depth2;
                                        iM210580c8 = c0504fp == null ? b81.m210580c8(f4) : c0504fp.m212845a3(ld1Var);
                                    }
                                }
                            }
                            color = iM210580c8;
                        } else {
                            iArr = iArrTrimStateSet;
                            i = depth2;
                        }
                        color = (16777215 & color) | (iM212476a4 << 24);
                    } else {
                        iArr = iArrTrimStateSet;
                        i = depth2;
                    }
                    int i9 = i4 + 1;
                    if (i9 > iArr3.length) {
                        int[] iArr6 = new int[i4 <= 4 ? 8 : i4 * 2];
                        System.arraycopy(iArr3, 0, iArr6, 0, i4);
                        iArr3 = iArr6;
                    }
                    iArr3[i4] = color;
                    if (i9 > iArr2.length) {
                        ?? r1 = (Object[]) Array.newInstance(iArr2.getClass().getComponentType(), i4 > 4 ? i4 * 2 : 8);
                        System.arraycopy(iArr2, 0, r1, 0, i4);
                        iArr2 = r1;
                    }
                    iArr2[i4] = iArr;
                    iArr2 = iArr2;
                    attributeSet2 = attributeSet;
                    theme2 = theme;
                    i4 = i9;
                    z = z2 ? 1 : 0;
                    depth2 = i;
                    i3 = 0;
                    resources2 = resources;
                }
            } else {
                resources2 = resources;
                attributeSet2 = attributeSet;
                theme2 = theme;
                z = z;
                depth2 = depth2;
                i3 = 0;
            }
        }
        int[] iArr7 = new int[i4];
        int[][] iArr8 = new int[i4][];
        System.arraycopy(iArr3, 0, iArr7, 0, i4);
        System.arraycopy(iArr2, 0, iArr8, 0, i4);
        return new ColorStateList(iArr8, iArr7);
    }
}

package android.support.v4.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.compat.C0041R;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public final class ColorStateListInflaterCompat {
    private static final int DEFAULT_COLOR = -65536;

    private ColorStateListInflaterCompat() {
    }

    @NonNull
    public static ColorStateList createFromXml(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @Nullable Resources.Theme theme) {
        int next;
        AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next == 2) {
            return createFromXmlInner(resources, xmlPullParser, asAttributeSet, theme);
        }
        throw new XmlPullParserException("No start tag found");
    }

    @NonNull
    public static ColorStateList createFromXmlInner(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
        String name = xmlPullParser.getName();
        if (name.equals("selector")) {
            return inflate(resources, xmlPullParser, attributeSet, theme);
        }
        throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid color state list tag " + name);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x009b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static ColorStateList inflate(@NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
        int depth;
        int i2;
        int attributeCount;
        int i3;
        int i4 = 1;
        int depth2 = xmlPullParser.getDepth() + 1;
        int[][] iArr = new int[20][];
        int[] iArr2 = new int[20];
        int i5 = 0;
        while (true) {
            int next = xmlPullParser.next();
            if (next == i4 || ((depth = xmlPullParser.getDepth()) < depth2 && next == 3)) {
                break;
            }
            if (next == 2 && depth <= depth2 && xmlPullParser.getName().equals("item")) {
                TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, C0041R.styleable.ColorStateListItem);
                int color = obtainAttributes.getColor(C0041R.styleable.ColorStateListItem_android_color, -65281);
                float f2 = 1.0f;
                if (obtainAttributes.hasValue(C0041R.styleable.ColorStateListItem_android_alpha)) {
                    i2 = C0041R.styleable.ColorStateListItem_android_alpha;
                } else {
                    if (obtainAttributes.hasValue(C0041R.styleable.ColorStateListItem_alpha)) {
                        i2 = C0041R.styleable.ColorStateListItem_alpha;
                    }
                    obtainAttributes.recycle();
                    attributeCount = attributeSet.getAttributeCount();
                    int[] iArr3 = new int[attributeCount];
                    int i6 = 0;
                    for (i3 = 0; i3 < attributeCount; i3++) {
                        int attributeNameResource = attributeSet.getAttributeNameResource(i3);
                        if (attributeNameResource != 16843173 && attributeNameResource != 16843551 && attributeNameResource != C0041R.attr.alpha) {
                            int i7 = i6 + 1;
                            if (!attributeSet.getAttributeBooleanValue(i3, false)) {
                                attributeNameResource = -attributeNameResource;
                            }
                            iArr3[i6] = attributeNameResource;
                            i6 = i7;
                        }
                    }
                    int[] trimStateSet = StateSet.trimStateSet(iArr3, i6);
                    int modulateColorAlpha = modulateColorAlpha(color, f2);
                    if (i5 != 0) {
                        int length = trimStateSet.length;
                    }
                    iArr2 = GrowingArrayUtils.append(iArr2, i5, modulateColorAlpha);
                    iArr = (int[][]) GrowingArrayUtils.append(iArr, i5, trimStateSet);
                    i5++;
                }
                f2 = obtainAttributes.getFloat(i2, 1.0f);
                obtainAttributes.recycle();
                attributeCount = attributeSet.getAttributeCount();
                int[] iArr32 = new int[attributeCount];
                int i62 = 0;
                while (i3 < attributeCount) {
                }
                int[] trimStateSet2 = StateSet.trimStateSet(iArr32, i62);
                int modulateColorAlpha2 = modulateColorAlpha(color, f2);
                if (i5 != 0) {
                }
                iArr2 = GrowingArrayUtils.append(iArr2, i5, modulateColorAlpha2);
                iArr = (int[][]) GrowingArrayUtils.append(iArr, i5, trimStateSet2);
                i5++;
            }
            i4 = 1;
        }
        int[] iArr4 = new int[i5];
        int[][] iArr5 = new int[i5][];
        System.arraycopy(iArr2, 0, iArr4, 0, i5);
        System.arraycopy(iArr, 0, iArr5, 0, i5);
        return new ColorStateList(iArr5, iArr4);
    }

    @ColorInt
    private static int modulateColorAlpha(@ColorInt int i2, @FloatRange(from = 0.0d, to = 1.0d) float f2) {
        return (i2 & ViewCompat.MEASURED_SIZE_MASK) | (Math.round(Color.alpha(i2) * f2) << 24);
    }

    private static TypedArray obtainAttributes(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] iArr) {
        return theme == null ? resources.obtainAttributes(attributeSet, iArr) : theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }
}

package p000;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Xml;
import com.google.android.material.R$dimen;
import com.google.android.material.R$plurals;
import com.google.android.material.R$string;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.badge.BadgeState$State;
import java.io.IOException;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: cu */
/* loaded from: classes2.dex */
public final class C0391cu {

    /* renamed from: a0 */
    public final BadgeState$State f55519a0;

    /* renamed from: a1 */
    public final BadgeState$State f55520a1;

    /* renamed from: a2 */
    public final float f55521a2;

    /* renamed from: a3 */
    public final float f55522a3;

    /* renamed from: a4 */
    public final float f55523a4;

    /* renamed from: a5 */
    public final float f55524a5;

    /* renamed from: a6 */
    public final float f55525a6;

    /* renamed from: a7 */
    public final float f55526a7;

    /* renamed from: a8 */
    public final float f55527a8;

    /* renamed from: a9 */
    public final int f55528a9;

    /* renamed from: b0 */
    public final int f55529b0;

    /* renamed from: b1 */
    public final int f55530b1;

    public C0391cu(Context context, BadgeState$State badgeState$State) throws XmlPullParserException, Resources.NotFoundException, IOException {
        AttributeSet attributeSetAsAttributeSet;
        int styleAttribute;
        int next;
        int i = C0390ct.f55503b4;
        int i2 = C0390ct.f55502b3;
        this.f55520a1 = new BadgeState$State();
        badgeState$State = badgeState$State == null ? new BadgeState$State() : badgeState$State;
        int i3 = badgeState$State.f49103a0;
        if (i3 != 0) {
            try {
                XmlResourceParser xml = context.getResources().getXml(i3);
                do {
                    next = xml.next();
                    if (next == 2) {
                        break;
                    }
                } while (next != 1);
                if (next != 2) {
                    throw new XmlPullParserException("No start tag found");
                }
                if (!TextUtils.equals(xml.getName(), "badge")) {
                    throw new XmlPullParserException("Must have a <" + ((Object) "badge") + "> start tag");
                }
                attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
                styleAttribute = attributeSetAsAttributeSet.getStyleAttribute();
            } catch (IOException | XmlPullParserException e) {
                Resources.NotFoundException notFoundException = new Resources.NotFoundException("Can't load badge resource ID #0x" + Integer.toHexString(i3));
                notFoundException.initCause(e);
                throw notFoundException;
            }
        } else {
            attributeSetAsAttributeSet = null;
            styleAttribute = 0;
        }
        TypedArray typedArrayM213209a3 = j61.m213209a3(context, attributeSetAsAttributeSet, R$styleable.Badge, i, styleAttribute == 0 ? i2 : styleAttribute, new int[0]);
        Resources resources = context.getResources();
        this.f55521a2 = typedArrayM213209a3.getDimensionPixelSize(R$styleable.Badge_badgeRadius, -1);
        this.f55527a8 = typedArrayM213209a3.getDimensionPixelSize(R$styleable.Badge_badgeWidePadding, resources.getDimensionPixelSize(R$dimen.mtrl_badge_long_text_horizontal_padding));
        this.f55528a9 = context.getResources().getDimensionPixelSize(R$dimen.mtrl_badge_horizontal_edge_offset);
        this.f55529b0 = context.getResources().getDimensionPixelSize(R$dimen.mtrl_badge_text_horizontal_edge_offset);
        this.f55522a3 = typedArrayM213209a3.getDimensionPixelSize(R$styleable.Badge_badgeWithTextRadius, -1);
        this.f55523a4 = typedArrayM213209a3.getDimension(R$styleable.Badge_badgeWidth, resources.getDimension(R$dimen.m3_badge_size));
        this.f55525a6 = typedArrayM213209a3.getDimension(R$styleable.Badge_badgeWithTextWidth, resources.getDimension(R$dimen.m3_badge_with_text_size));
        this.f55524a5 = typedArrayM213209a3.getDimension(R$styleable.Badge_badgeHeight, resources.getDimension(R$dimen.m3_badge_size));
        this.f55526a7 = typedArrayM213209a3.getDimension(R$styleable.Badge_badgeWithTextHeight, resources.getDimension(R$dimen.m3_badge_with_text_size));
        this.f55530b1 = typedArrayM213209a3.getInt(R$styleable.Badge_offsetAlignmentMode, 1);
        BadgeState$State badgeState$State2 = this.f55520a1;
        int i4 = badgeState$State.f49111a8;
        badgeState$State2.f49111a8 = i4 == -2 ? v10.MASK : i4;
        CharSequence charSequence = badgeState$State.f49115b2;
        badgeState$State2.f49115b2 = charSequence == null ? context.getString(R$string.mtrl_badge_numberless_content_description) : charSequence;
        BadgeState$State badgeState$State3 = this.f55520a1;
        int i5 = badgeState$State.f49116b3;
        badgeState$State3.f49116b3 = i5 == 0 ? R$plurals.mtrl_badge_content_description : i5;
        int i6 = badgeState$State.f49117b4;
        badgeState$State3.f49117b4 = i6 == 0 ? R$string.mtrl_exceed_max_badge_number_content_description : i6;
        Boolean bool = badgeState$State.f49119b6;
        badgeState$State3.f49119b6 = Boolean.valueOf(bool == null || bool.booleanValue());
        BadgeState$State badgeState$State4 = this.f55520a1;
        int i7 = badgeState$State.f49113b0;
        badgeState$State4.f49113b0 = i7 == -2 ? typedArrayM213209a3.getInt(R$styleable.Badge_maxCharacterCount, 4) : i7;
        int i8 = badgeState$State.f49112a9;
        if (i8 != -2) {
            this.f55520a1.f49112a9 = i8;
        } else if (typedArrayM213209a3.hasValue(R$styleable.Badge_number)) {
            this.f55520a1.f49112a9 = typedArrayM213209a3.getInt(R$styleable.Badge_number, 0);
        } else {
            this.f55520a1.f49112a9 = -1;
        }
        BadgeState$State badgeState$State5 = this.f55520a1;
        Integer num = badgeState$State.f49107a4;
        badgeState$State5.f49107a4 = Integer.valueOf(num == null ? typedArrayM213209a3.getResourceId(R$styleable.Badge_badgeShapeAppearance, R$style.ShapeAppearance_M3_Sys_Shape_Corner_Full) : num.intValue());
        BadgeState$State badgeState$State6 = this.f55520a1;
        Integer num2 = badgeState$State.f49108a5;
        badgeState$State6.f49108a5 = Integer.valueOf(num2 == null ? typedArrayM213209a3.getResourceId(R$styleable.Badge_badgeShapeAppearanceOverlay, 0) : num2.intValue());
        BadgeState$State badgeState$State7 = this.f55520a1;
        Integer num3 = badgeState$State.f49109a6;
        badgeState$State7.f49109a6 = Integer.valueOf(num3 == null ? typedArrayM213209a3.getResourceId(R$styleable.Badge_badgeWithTextShapeAppearance, R$style.ShapeAppearance_M3_Sys_Shape_Corner_Full) : num3.intValue());
        BadgeState$State badgeState$State8 = this.f55520a1;
        Integer num4 = badgeState$State.f49110a7;
        badgeState$State8.f49110a7 = Integer.valueOf(num4 == null ? typedArrayM213209a3.getResourceId(R$styleable.Badge_badgeWithTextShapeAppearanceOverlay, 0) : num4.intValue());
        BadgeState$State badgeState$State9 = this.f55520a1;
        Integer num5 = badgeState$State.f49104a1;
        badgeState$State9.f49104a1 = Integer.valueOf(num5 == null ? AbstractC1117qo.m214428c4(context, typedArrayM213209a3, R$styleable.Badge_backgroundColor).getDefaultColor() : num5.intValue());
        BadgeState$State badgeState$State10 = this.f55520a1;
        Integer num6 = badgeState$State.f49106a3;
        badgeState$State10.f49106a3 = Integer.valueOf(num6 == null ? typedArrayM213209a3.getResourceId(R$styleable.Badge_badgeTextAppearance, R$style.TextAppearance_MaterialComponents_Badge) : num6.intValue());
        Integer num7 = badgeState$State.f49105a2;
        if (num7 != null) {
            this.f55520a1.f49105a2 = num7;
        } else if (typedArrayM213209a3.hasValue(R$styleable.Badge_badgeTextColor)) {
            this.f55520a1.f49105a2 = Integer.valueOf(AbstractC1117qo.m214428c4(context, typedArrayM213209a3, R$styleable.Badge_badgeTextColor).getDefaultColor());
        } else {
            int iIntValue = this.f55520a1.f49106a3.intValue();
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(iIntValue, R$styleable.TextAppearance);
            typedArrayObtainStyledAttributes.getDimension(R$styleable.TextAppearance_android_textSize, 0.0f);
            ColorStateList colorStateListM214428c4 = AbstractC1117qo.m214428c4(context, typedArrayObtainStyledAttributes, R$styleable.TextAppearance_android_textColor);
            AbstractC1117qo.m214428c4(context, typedArrayObtainStyledAttributes, R$styleable.TextAppearance_android_textColorHint);
            AbstractC1117qo.m214428c4(context, typedArrayObtainStyledAttributes, R$styleable.TextAppearance_android_textColorLink);
            typedArrayObtainStyledAttributes.getInt(R$styleable.TextAppearance_android_textStyle, 0);
            typedArrayObtainStyledAttributes.getInt(R$styleable.TextAppearance_android_typeface, 1);
            int i9 = R$styleable.TextAppearance_fontFamily;
            i9 = typedArrayObtainStyledAttributes.hasValue(i9) ? i9 : R$styleable.TextAppearance_android_fontFamily;
            typedArrayObtainStyledAttributes.getResourceId(i9, 0);
            typedArrayObtainStyledAttributes.getString(i9);
            typedArrayObtainStyledAttributes.getBoolean(R$styleable.TextAppearance_textAllCaps, false);
            AbstractC1117qo.m214428c4(context, typedArrayObtainStyledAttributes, R$styleable.TextAppearance_android_shadowColor);
            typedArrayObtainStyledAttributes.getFloat(R$styleable.TextAppearance_android_shadowDx, 0.0f);
            typedArrayObtainStyledAttributes.getFloat(R$styleable.TextAppearance_android_shadowDy, 0.0f);
            typedArrayObtainStyledAttributes.getFloat(R$styleable.TextAppearance_android_shadowRadius, 0.0f);
            typedArrayObtainStyledAttributes.recycle();
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(iIntValue, R$styleable.MaterialTextAppearance);
            typedArrayObtainStyledAttributes2.hasValue(R$styleable.MaterialTextAppearance_android_letterSpacing);
            typedArrayObtainStyledAttributes2.getFloat(R$styleable.MaterialTextAppearance_android_letterSpacing, 0.0f);
            typedArrayObtainStyledAttributes2.recycle();
            this.f55520a1.f49105a2 = Integer.valueOf(colorStateListM214428c4.getDefaultColor());
        }
        BadgeState$State badgeState$State11 = this.f55520a1;
        Integer num8 = badgeState$State.f49118b5;
        badgeState$State11.f49118b5 = Integer.valueOf(num8 == null ? typedArrayM213209a3.getInt(R$styleable.Badge_badgeGravity, 8388661) : num8.intValue());
        BadgeState$State badgeState$State12 = this.f55520a1;
        Integer num9 = badgeState$State.f49120b7;
        badgeState$State12.f49120b7 = Integer.valueOf(num9 == null ? typedArrayM213209a3.getDimensionPixelOffset(R$styleable.Badge_horizontalOffset, 0) : num9.intValue());
        BadgeState$State badgeState$State13 = this.f55520a1;
        Integer num10 = badgeState$State.f49121b8;
        badgeState$State13.f49121b8 = Integer.valueOf(num10 == null ? typedArrayM213209a3.getDimensionPixelOffset(R$styleable.Badge_verticalOffset, 0) : num10.intValue());
        BadgeState$State badgeState$State14 = this.f55520a1;
        Integer num11 = badgeState$State.f49122b9;
        badgeState$State14.f49122b9 = Integer.valueOf(num11 == null ? typedArrayM213209a3.getDimensionPixelOffset(R$styleable.Badge_horizontalOffsetWithText, badgeState$State14.f49120b7.intValue()) : num11.intValue());
        BadgeState$State badgeState$State15 = this.f55520a1;
        Integer num12 = badgeState$State.f49123c0;
        badgeState$State15.f49123c0 = Integer.valueOf(num12 == null ? typedArrayM213209a3.getDimensionPixelOffset(R$styleable.Badge_verticalOffsetWithText, badgeState$State15.f49121b8.intValue()) : num12.intValue());
        BadgeState$State badgeState$State16 = this.f55520a1;
        Integer num13 = badgeState$State.f49124c1;
        badgeState$State16.f49124c1 = Integer.valueOf(num13 == null ? 0 : num13.intValue());
        BadgeState$State badgeState$State17 = this.f55520a1;
        Integer num14 = badgeState$State.f49125c2;
        badgeState$State17.f49125c2 = Integer.valueOf(num14 != null ? num14.intValue() : 0);
        typedArrayM213209a3.recycle();
        Locale locale = badgeState$State.f49114b1;
        if (locale == null) {
            this.f55520a1.f49114b1 = Locale.getDefault(Locale.Category.FORMAT);
        } else {
            this.f55520a1.f49114b1 = locale;
        }
        this.f55519a0 = badgeState$State;
    }

    /* renamed from: a0 */
    public final boolean m212531a0() {
        return this.f55520a1.f49112a9 != -1;
    }
}

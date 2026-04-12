package p000;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.constraintlayout.widget.R$id;
import androidx.constraintlayout.widget.R$styleable;
import io.socket.engineio.parser.Base64;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: lm */
/* loaded from: classes.dex */
public final class C0825lm {

    /* renamed from: a6 */
    public static final int[] f58044a6 = {0, 4, 8};

    /* renamed from: a7 */
    public static final SparseIntArray f58045a7;

    /* renamed from: a8 */
    public static final SparseIntArray f58046a8;

    /* renamed from: a0 */
    public String f58047a0;

    /* renamed from: a1 */
    public String f58048a1 = "";

    /* renamed from: a2 */
    public int f58049a2 = 0;

    /* renamed from: a3 */
    public final HashMap f58050a3 = new HashMap();

    /* renamed from: a4 */
    public boolean f58051a4 = true;

    /* renamed from: a5 */
    public final HashMap f58052a5 = new HashMap();

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        f58045a7 = sparseIntArray;
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        f58046a8 = sparseIntArray2;
        sparseIntArray.append(R$styleable.Constraint_layout_constraintLeft_toLeftOf, 25);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintLeft_toRightOf, 26);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintRight_toLeftOf, 29);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintRight_toRightOf, 30);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintTop_toTopOf, 36);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintTop_toBottomOf, 35);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintBottom_toTopOf, 4);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintBottom_toBottomOf, 3);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintBaseline_toBaselineOf, 1);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintBaseline_toTopOf, 91);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintBaseline_toBottomOf, 92);
        sparseIntArray.append(R$styleable.Constraint_layout_editor_absoluteX, 6);
        sparseIntArray.append(R$styleable.Constraint_layout_editor_absoluteY, 7);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintGuide_begin, 17);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintGuide_end, 18);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintGuide_percent, 19);
        sparseIntArray.append(R$styleable.Constraint_guidelineUseRtl, 99);
        sparseIntArray.append(R$styleable.Constraint_android_orientation, 27);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintStart_toEndOf, 32);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintStart_toStartOf, 33);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintEnd_toStartOf, 10);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintEnd_toEndOf, 9);
        sparseIntArray.append(R$styleable.Constraint_layout_goneMarginLeft, 13);
        sparseIntArray.append(R$styleable.Constraint_layout_goneMarginTop, 16);
        sparseIntArray.append(R$styleable.Constraint_layout_goneMarginRight, 14);
        sparseIntArray.append(R$styleable.Constraint_layout_goneMarginBottom, 11);
        sparseIntArray.append(R$styleable.Constraint_layout_goneMarginStart, 15);
        sparseIntArray.append(R$styleable.Constraint_layout_goneMarginEnd, 12);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintVertical_weight, 40);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintHorizontal_weight, 39);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintHorizontal_chainStyle, 41);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintVertical_chainStyle, 42);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintHorizontal_bias, 20);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintVertical_bias, 37);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintDimensionRatio, 5);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintLeft_creator, 87);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintTop_creator, 87);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintRight_creator, 87);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintBottom_creator, 87);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintBaseline_creator, 87);
        sparseIntArray.append(R$styleable.Constraint_android_layout_marginLeft, 24);
        sparseIntArray.append(R$styleable.Constraint_android_layout_marginRight, 28);
        sparseIntArray.append(R$styleable.Constraint_android_layout_marginStart, 31);
        sparseIntArray.append(R$styleable.Constraint_android_layout_marginEnd, 8);
        sparseIntArray.append(R$styleable.Constraint_android_layout_marginTop, 34);
        sparseIntArray.append(R$styleable.Constraint_android_layout_marginBottom, 2);
        sparseIntArray.append(R$styleable.Constraint_android_layout_width, 23);
        sparseIntArray.append(R$styleable.Constraint_android_layout_height, 21);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintWidth, 95);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintHeight, 96);
        sparseIntArray.append(R$styleable.Constraint_android_visibility, 22);
        sparseIntArray.append(R$styleable.Constraint_android_alpha, 43);
        sparseIntArray.append(R$styleable.Constraint_android_elevation, 44);
        sparseIntArray.append(R$styleable.Constraint_android_rotationX, 45);
        sparseIntArray.append(R$styleable.Constraint_android_rotationY, 46);
        sparseIntArray.append(R$styleable.Constraint_android_rotation, 60);
        sparseIntArray.append(R$styleable.Constraint_android_scaleX, 47);
        sparseIntArray.append(R$styleable.Constraint_android_scaleY, 48);
        sparseIntArray.append(R$styleable.Constraint_android_transformPivotX, 49);
        sparseIntArray.append(R$styleable.Constraint_android_transformPivotY, 50);
        sparseIntArray.append(R$styleable.Constraint_android_translationX, 51);
        sparseIntArray.append(R$styleable.Constraint_android_translationY, 52);
        sparseIntArray.append(R$styleable.Constraint_android_translationZ, 53);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintWidth_default, 54);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintHeight_default, 55);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintWidth_max, 56);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintHeight_max, 57);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintWidth_min, 58);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintHeight_min, 59);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintCircle, 61);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintCircleRadius, 62);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintCircleAngle, 63);
        sparseIntArray.append(R$styleable.Constraint_animateRelativeTo, 64);
        sparseIntArray.append(R$styleable.Constraint_transitionEasing, 65);
        sparseIntArray.append(R$styleable.Constraint_drawPath, 66);
        sparseIntArray.append(R$styleable.Constraint_transitionPathRotate, 67);
        sparseIntArray.append(R$styleable.Constraint_motionStagger, 79);
        sparseIntArray.append(R$styleable.Constraint_android_id, 38);
        sparseIntArray.append(R$styleable.Constraint_motionProgress, 68);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintWidth_percent, 69);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintHeight_percent, 70);
        sparseIntArray.append(R$styleable.Constraint_layout_wrapBehaviorInParent, 97);
        sparseIntArray.append(R$styleable.Constraint_chainUseRtl, 71);
        sparseIntArray.append(R$styleable.Constraint_barrierDirection, 72);
        sparseIntArray.append(R$styleable.Constraint_barrierMargin, 73);
        sparseIntArray.append(R$styleable.Constraint_constraint_referenced_ids, 74);
        sparseIntArray.append(R$styleable.Constraint_barrierAllowsGoneWidgets, 75);
        sparseIntArray.append(R$styleable.Constraint_pathMotionArc, 76);
        sparseIntArray.append(R$styleable.Constraint_layout_constraintTag, 77);
        sparseIntArray.append(R$styleable.Constraint_visibilityMode, 78);
        sparseIntArray.append(R$styleable.Constraint_layout_constrainedWidth, 80);
        sparseIntArray.append(R$styleable.Constraint_layout_constrainedHeight, 81);
        sparseIntArray.append(R$styleable.Constraint_polarRelativeTo, 82);
        sparseIntArray.append(R$styleable.Constraint_transformPivotTarget, 83);
        sparseIntArray.append(R$styleable.Constraint_quantizeMotionSteps, 84);
        sparseIntArray.append(R$styleable.Constraint_quantizeMotionPhase, 85);
        sparseIntArray.append(R$styleable.Constraint_quantizeMotionInterpolator, 86);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_editor_absoluteY, 6);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_editor_absoluteY, 7);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_orientation, 27);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_goneMarginLeft, 13);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_goneMarginTop, 16);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_goneMarginRight, 14);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_goneMarginBottom, 11);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_goneMarginStart, 15);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_goneMarginEnd, 12);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintVertical_weight, 40);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintHorizontal_weight, 39);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintHorizontal_chainStyle, 41);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintVertical_chainStyle, 42);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintHorizontal_bias, 20);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintVertical_bias, 37);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintDimensionRatio, 5);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintLeft_creator, 87);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintTop_creator, 87);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintRight_creator, 87);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintBottom_creator, 87);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintBaseline_creator, 87);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_layout_marginLeft, 24);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_layout_marginRight, 28);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_layout_marginStart, 31);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_layout_marginEnd, 8);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_layout_marginTop, 34);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_layout_marginBottom, 2);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_layout_width, 23);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_layout_height, 21);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintWidth, 95);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintHeight, 96);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_visibility, 22);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_alpha, 43);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_elevation, 44);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_rotationX, 45);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_rotationY, 46);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_rotation, 60);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_scaleX, 47);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_scaleY, 48);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_transformPivotX, 49);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_transformPivotY, 50);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_translationX, 51);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_translationY, 52);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_translationZ, 53);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintWidth_default, 54);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintHeight_default, 55);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintWidth_max, 56);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintHeight_max, 57);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintWidth_min, 58);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintHeight_min, 59);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintCircleRadius, 62);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintCircleAngle, 63);
        sparseIntArray2.append(R$styleable.ConstraintOverride_animateRelativeTo, 64);
        sparseIntArray2.append(R$styleable.ConstraintOverride_transitionEasing, 65);
        sparseIntArray2.append(R$styleable.ConstraintOverride_drawPath, 66);
        sparseIntArray2.append(R$styleable.ConstraintOverride_transitionPathRotate, 67);
        sparseIntArray2.append(R$styleable.ConstraintOverride_motionStagger, 79);
        sparseIntArray2.append(R$styleable.ConstraintOverride_android_id, 38);
        sparseIntArray2.append(R$styleable.ConstraintOverride_motionTarget, 98);
        sparseIntArray2.append(R$styleable.ConstraintOverride_motionProgress, 68);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintWidth_percent, 69);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintHeight_percent, 70);
        sparseIntArray2.append(R$styleable.ConstraintOverride_chainUseRtl, 71);
        sparseIntArray2.append(R$styleable.ConstraintOverride_barrierDirection, 72);
        sparseIntArray2.append(R$styleable.ConstraintOverride_barrierMargin, 73);
        sparseIntArray2.append(R$styleable.ConstraintOverride_constraint_referenced_ids, 74);
        sparseIntArray2.append(R$styleable.ConstraintOverride_barrierAllowsGoneWidgets, 75);
        sparseIntArray2.append(R$styleable.ConstraintOverride_pathMotionArc, 76);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constraintTag, 77);
        sparseIntArray2.append(R$styleable.ConstraintOverride_visibilityMode, 78);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constrainedWidth, 80);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_constrainedHeight, 81);
        sparseIntArray2.append(R$styleable.ConstraintOverride_polarRelativeTo, 82);
        sparseIntArray2.append(R$styleable.ConstraintOverride_transformPivotTarget, 83);
        sparseIntArray2.append(R$styleable.ConstraintOverride_quantizeMotionSteps, 84);
        sparseIntArray2.append(R$styleable.ConstraintOverride_quantizeMotionPhase, 85);
        sparseIntArray2.append(R$styleable.ConstraintOverride_quantizeMotionInterpolator, 86);
        sparseIntArray2.append(R$styleable.ConstraintOverride_layout_wrapBehaviorInParent, 97);
    }

    /* renamed from: a3 */
    public static C0820lh m213858a3(Context context, XmlResourceParser xmlResourceParser) throws NumberFormatException {
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlResourceParser);
        C0820lh c0820lh = new C0820lh();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSetAsAttributeSet, R$styleable.ConstraintOverride);
        m213864b4(c0820lh, typedArrayObtainStyledAttributes);
        typedArrayObtainStyledAttributes.recycle();
        return c0820lh;
    }

    /* renamed from: a5 */
    public static int[] m213859a5(Barrier barrier, String str) throws IllegalAccessException, IllegalArgumentException {
        int iIntValue;
        String[] strArrSplit = str.split(",");
        Context context = barrier.getContext();
        int[] iArr = new int[strArrSplit.length];
        int i = 0;
        int i2 = 0;
        while (i < strArrSplit.length) {
            String strTrim = strArrSplit[i].trim();
            Object obj = null;
            try {
                iIntValue = R$id.class.getField(strTrim).getInt(null);
            } catch (Exception unused) {
                iIntValue = 0;
            }
            if (iIntValue == 0) {
                iIntValue = context.getResources().getIdentifier(strTrim, "id", context.getPackageName());
            }
            if (iIntValue == 0 && barrier.isInEditMode() && (barrier.getParent() instanceof ConstraintLayout)) {
                ConstraintLayout constraintLayout = (ConstraintLayout) barrier.getParent();
                if (strTrim != null) {
                    HashMap map = constraintLayout.f44783b2;
                    if (map != null && map.containsKey(strTrim)) {
                        obj = constraintLayout.f44783b2.get(strTrim);
                    }
                } else {
                    constraintLayout.getClass();
                }
                if (obj != null && (obj instanceof Integer)) {
                    iIntValue = ((Integer) obj).intValue();
                }
            }
            iArr[i2] = iIntValue;
            i++;
            i2++;
        }
        return i2 != strArrSplit.length ? Arrays.copyOf(iArr, i2) : iArr;
    }

    /* renamed from: a6 */
    public static C0820lh m213860a6(Context context, AttributeSet attributeSet, boolean z) throws NumberFormatException {
        C0820lh c0820lh = new C0820lh();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, z ? R$styleable.ConstraintOverride : R$styleable.Constraint);
        if (z) {
            m213864b4(c0820lh, typedArrayObtainStyledAttributes);
        } else {
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            int i = 0;
            while (true) {
                C0821li c0821li = c0820lh.f57930a4;
                if (i < indexCount) {
                    int index = typedArrayObtainStyledAttributes.getIndex(i);
                    int i2 = R$styleable.Constraint_android_id;
                    C0823lk c0823lk = c0820lh.f57928a2;
                    C0824ll c0824ll = c0820lh.f57931a5;
                    C0822lj c0822lj = c0820lh.f57929a3;
                    if (index != i2 && R$styleable.Constraint_android_layout_marginStart != index && R$styleable.Constraint_android_layout_marginEnd != index) {
                        c0822lj.f58007a0 = true;
                        c0821li.f57936a1 = true;
                        c0823lk.f58024a0 = true;
                        c0824ll.f58030a0 = true;
                    }
                    SparseIntArray sparseIntArray = f58045a7;
                    switch (sparseIntArray.get(index)) {
                        case 1:
                            c0821li.f57951b6 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57951b6);
                            break;
                        case 2:
                            c0821li.f57970d5 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57970d5);
                            break;
                        case 3:
                            c0821li.f57950b5 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57950b5);
                            break;
                        case 4:
                            c0821li.f57949b4 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57949b4);
                            break;
                        case 5:
                            c0821li.f57960c5 = typedArrayObtainStyledAttributes.getString(index);
                            break;
                        case 6:
                            c0821li.f57964c9 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, c0821li.f57964c9);
                            break;
                        case 7:
                            c0821li.f57965d0 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, c0821li.f57965d0);
                            break;
                        case 8:
                            c0821li.f57971d6 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57971d6);
                            break;
                        case 9:
                            c0821li.f57957c2 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57957c2);
                            break;
                        case 10:
                            c0821li.f57956c1 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57956c1);
                            break;
                        case oe0.DEFAULT_M /* 11 */:
                            c0821li.f57977e2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57977e2);
                            break;
                        case FileClientSessionCache.MAX_SIZE /* 12 */:
                            c0821li.f57978e3 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57978e3);
                            break;
                        case 13:
                            c0821li.f57974d9 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57974d9);
                            break;
                        case 14:
                            c0821li.f57976e1 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57976e1);
                            break;
                        case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                            c0821li.f57979e4 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57979e4);
                            break;
                        case 16:
                            c0821li.f57975e0 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57975e0);
                            break;
                        case 17:
                            c0821li.f57939a4 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, c0821li.f57939a4);
                            break;
                        case 18:
                            c0821li.f57940a5 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, c0821li.f57940a5);
                            break;
                        case Base64.Encoder.LINE_GROUPS /* 19 */:
                            c0821li.f57941a6 = typedArrayObtainStyledAttributes.getFloat(index, c0821li.f57941a6);
                            break;
                        case 20:
                            c0821li.f57958c3 = typedArrayObtainStyledAttributes.getFloat(index, c0821li.f57958c3);
                            break;
                        case 21:
                            c0821li.f57938a3 = typedArrayObtainStyledAttributes.getLayoutDimension(index, c0821li.f57938a3);
                            break;
                        case 22:
                            int i3 = typedArrayObtainStyledAttributes.getInt(index, c0823lk.f58025a1);
                            c0823lk.f58025a1 = i3;
                            c0823lk.f58025a1 = f58044a6[i3];
                            break;
                        case 23:
                            c0821li.f57937a2 = typedArrayObtainStyledAttributes.getLayoutDimension(index, c0821li.f57937a2);
                            break;
                        case 24:
                            c0821li.f57967d2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57967d2);
                            break;
                        case 25:
                            c0821li.f57943a8 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57943a8);
                            break;
                        case 26:
                            c0821li.f57944a9 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57944a9);
                            break;
                        case 27:
                            c0821li.f57966d1 = typedArrayObtainStyledAttributes.getInt(index, c0821li.f57966d1);
                            break;
                        case 28:
                            c0821li.f57968d3 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57968d3);
                            break;
                        case 29:
                            c0821li.f57945b0 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57945b0);
                            break;
                        case 30:
                            c0821li.f57946b1 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57946b1);
                            break;
                        case 31:
                            c0821li.f57972d7 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57972d7);
                            break;
                        case 32:
                            c0821li.f57954b9 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57954b9);
                            break;
                        case 33:
                            c0821li.f57955c0 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57955c0);
                            break;
                        case 34:
                            c0821li.f57969d4 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57969d4);
                            break;
                        case 35:
                            c0821li.f57948b3 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57948b3);
                            break;
                        case 36:
                            c0821li.f57947b2 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57947b2);
                            break;
                        case 37:
                            c0821li.f57959c4 = typedArrayObtainStyledAttributes.getFloat(index, c0821li.f57959c4);
                            break;
                        case 38:
                            c0820lh.f57926a0 = typedArrayObtainStyledAttributes.getResourceId(index, c0820lh.f57926a0);
                            break;
                        case 39:
                            c0821li.f57982e7 = typedArrayObtainStyledAttributes.getFloat(index, c0821li.f57982e7);
                            break;
                        case 40:
                            c0821li.f57981e6 = typedArrayObtainStyledAttributes.getFloat(index, c0821li.f57981e6);
                            break;
                        case 41:
                            c0821li.f57983e8 = typedArrayObtainStyledAttributes.getInt(index, c0821li.f57983e8);
                            break;
                        case 42:
                            c0821li.f57984e9 = typedArrayObtainStyledAttributes.getInt(index, c0821li.f57984e9);
                            break;
                        case 43:
                            c0823lk.f58027a3 = typedArrayObtainStyledAttributes.getFloat(index, c0823lk.f58027a3);
                            break;
                        case 44:
                            c0824ll.f58042b2 = true;
                            c0824ll.f58043b3 = typedArrayObtainStyledAttributes.getDimension(index, c0824ll.f58043b3);
                            break;
                        case 45:
                            c0824ll.f58032a2 = typedArrayObtainStyledAttributes.getFloat(index, c0824ll.f58032a2);
                            break;
                        case 46:
                            c0824ll.f58033a3 = typedArrayObtainStyledAttributes.getFloat(index, c0824ll.f58033a3);
                            break;
                        case 47:
                            c0824ll.f58034a4 = typedArrayObtainStyledAttributes.getFloat(index, c0824ll.f58034a4);
                            break;
                        case 48:
                            c0824ll.f58035a5 = typedArrayObtainStyledAttributes.getFloat(index, c0824ll.f58035a5);
                            break;
                        case 49:
                            c0824ll.f58036a6 = typedArrayObtainStyledAttributes.getDimension(index, c0824ll.f58036a6);
                            break;
                        case oe0.DEFAULT_T /* 50 */:
                            c0824ll.f58037a7 = typedArrayObtainStyledAttributes.getDimension(index, c0824ll.f58037a7);
                            break;
                        case 51:
                            c0824ll.f58039a9 = typedArrayObtainStyledAttributes.getDimension(index, c0824ll.f58039a9);
                            break;
                        case 52:
                            c0824ll.f58040b0 = typedArrayObtainStyledAttributes.getDimension(index, c0824ll.f58040b0);
                            break;
                        case 53:
                            c0824ll.f58041b1 = typedArrayObtainStyledAttributes.getDimension(index, c0824ll.f58041b1);
                            break;
                        case 54:
                            c0821li.f57985f0 = typedArrayObtainStyledAttributes.getInt(index, c0821li.f57985f0);
                            break;
                        case 55:
                            c0821li.f57986f1 = typedArrayObtainStyledAttributes.getInt(index, c0821li.f57986f1);
                            break;
                        case 56:
                            c0821li.f57987f2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57987f2);
                            break;
                        case 57:
                            c0821li.f57988f3 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57988f3);
                            break;
                        case 58:
                            c0821li.f57989f4 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57989f4);
                            break;
                        case 59:
                            c0821li.f57990f5 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57990f5);
                            break;
                        case 60:
                            c0824ll.f58031a1 = typedArrayObtainStyledAttributes.getFloat(index, c0824ll.f58031a1);
                            break;
                        case 61:
                            c0821li.f57961c6 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57961c6);
                            break;
                        case 62:
                            c0821li.f57962c7 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57962c7);
                            break;
                        case 63:
                            c0821li.f57963c8 = typedArrayObtainStyledAttributes.getFloat(index, c0821li.f57963c8);
                            break;
                        case 64:
                            c0822lj.f58008a1 = m213861b1(typedArrayObtainStyledAttributes, index, c0822lj.f58008a1);
                            break;
                        case 65:
                            if (typedArrayObtainStyledAttributes.peekValue(index).type != 3) {
                                c0822lj.f58010a3 = C1347vr.f60676a3[typedArrayObtainStyledAttributes.getInteger(index, 0)];
                                break;
                            } else {
                                c0822lj.f58010a3 = typedArrayObtainStyledAttributes.getString(index);
                                break;
                            }
                        case 66:
                            c0822lj.f58012a5 = typedArrayObtainStyledAttributes.getInt(index, 0);
                            break;
                        case 67:
                            c0822lj.f58014a7 = typedArrayObtainStyledAttributes.getFloat(index, c0822lj.f58014a7);
                            break;
                        case 68:
                            c0823lk.f58028a4 = typedArrayObtainStyledAttributes.getFloat(index, c0823lk.f58028a4);
                            break;
                        case 69:
                            c0821li.f57991f6 = typedArrayObtainStyledAttributes.getFloat(index, 1.0f);
                            break;
                        case 70:
                            c0821li.f57992f7 = typedArrayObtainStyledAttributes.getFloat(index, 1.0f);
                            break;
                        case 71:
                            break;
                        case 72:
                            c0821li.f57993f8 = typedArrayObtainStyledAttributes.getInt(index, c0821li.f57993f8);
                            break;
                        case 73:
                            c0821li.f57994f9 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57994f9);
                            break;
                        case 74:
                            c0821li.f57997g2 = typedArrayObtainStyledAttributes.getString(index);
                            break;
                        case 75:
                            c0821li.f58001g6 = typedArrayObtainStyledAttributes.getBoolean(index, c0821li.f58001g6);
                            break;
                        case 76:
                            c0822lj.f58011a4 = typedArrayObtainStyledAttributes.getInt(index, c0822lj.f58011a4);
                            break;
                        case 77:
                            c0821li.f57998g3 = typedArrayObtainStyledAttributes.getString(index);
                            break;
                        case 78:
                            c0823lk.f58026a2 = typedArrayObtainStyledAttributes.getInt(index, c0823lk.f58026a2);
                            break;
                        case 79:
                            c0822lj.f58013a6 = typedArrayObtainStyledAttributes.getFloat(index, c0822lj.f58013a6);
                            break;
                        case 80:
                            c0821li.f57999g4 = typedArrayObtainStyledAttributes.getBoolean(index, c0821li.f57999g4);
                            break;
                        case 81:
                            c0821li.f58000g5 = typedArrayObtainStyledAttributes.getBoolean(index, c0821li.f58000g5);
                            break;
                        case 82:
                            c0822lj.f58009a2 = typedArrayObtainStyledAttributes.getInteger(index, c0822lj.f58009a2);
                            break;
                        case 83:
                            c0824ll.f58038a8 = m213861b1(typedArrayObtainStyledAttributes, index, c0824ll.f58038a8);
                            break;
                        case 84:
                            c0822lj.f58016a9 = typedArrayObtainStyledAttributes.getInteger(index, c0822lj.f58016a9);
                            break;
                        case 85:
                            c0822lj.f58015a8 = typedArrayObtainStyledAttributes.getFloat(index, c0822lj.f58015a8);
                            break;
                        case 86:
                            int i4 = typedArrayObtainStyledAttributes.peekValue(index).type;
                            if (i4 != 1) {
                                if (i4 != 3) {
                                    c0822lj.f58018b1 = typedArrayObtainStyledAttributes.getInteger(index, c0822lj.f58019b2);
                                    break;
                                } else {
                                    String string = typedArrayObtainStyledAttributes.getString(index);
                                    c0822lj.f58017b0 = string;
                                    if (string.indexOf("/") <= 0) {
                                        c0822lj.f58018b1 = -1;
                                        break;
                                    } else {
                                        c0822lj.f58019b2 = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                                        c0822lj.f58018b1 = -2;
                                        break;
                                    }
                                }
                            } else {
                                int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, -1);
                                c0822lj.f58019b2 = resourceId;
                                if (resourceId == -1) {
                                    break;
                                } else {
                                    c0822lj.f58018b1 = -2;
                                    break;
                                }
                            }
                        case 87:
                            Integer.toHexString(index);
                            sparseIntArray.get(index);
                            break;
                        case 88:
                        case 89:
                        case 90:
                        default:
                            Integer.toHexString(index);
                            sparseIntArray.get(index);
                            break;
                        case 91:
                            c0821li.f57952b7 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57952b7);
                            break;
                        case 92:
                            c0821li.f57953b8 = m213861b1(typedArrayObtainStyledAttributes, index, c0821li.f57953b8);
                            break;
                        case 93:
                            c0821li.f57973d8 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57973d8);
                            break;
                        case 94:
                            c0821li.f57980e5 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, c0821li.f57980e5);
                            break;
                        case 95:
                            m213862b2(c0821li, typedArrayObtainStyledAttributes, index, 0);
                            break;
                        case 96:
                            m213862b2(c0821li, typedArrayObtainStyledAttributes, index, 1);
                            break;
                        case 97:
                            c0821li.f58002g7 = typedArrayObtainStyledAttributes.getInt(index, c0821li.f58002g7);
                            break;
                    }
                    i++;
                } else if (c0821li.f57997g2 != null) {
                    c0821li.f57996g1 = null;
                }
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        return c0820lh;
    }

    /* renamed from: b1 */
    public static int m213861b1(TypedArray typedArray, int i, int i2) {
        int resourceId = typedArray.getResourceId(i, i2);
        return resourceId == -1 ? typedArray.getInt(i, -1) : resourceId;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0044  */
    /* renamed from: b2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m213862b2(Object obj, TypedArray typedArray, int i, int i2) throws NumberFormatException {
        int dimensionPixelSize;
        if (obj == null) {
            return;
        }
        int i3 = typedArray.peekValue(i).type;
        boolean z = true;
        int i4 = 0;
        if (i3 == 3) {
            String string = typedArray.getString(i);
            if (string == null) {
                return;
            }
            int iIndexOf = string.indexOf(61);
            int length = string.length();
            if (iIndexOf <= 0 || iIndexOf >= length - 1) {
                return;
            }
            String strSubstring = string.substring(0, iIndexOf);
            String strSubstring2 = string.substring(iIndexOf + 1);
            if (strSubstring2.length() > 0) {
                String strTrim = strSubstring.trim();
                String strTrim2 = strSubstring2.trim();
                if ("ratio".equalsIgnoreCase(strTrim)) {
                    if (obj instanceof C0801kz) {
                        C0801kz c0801kz = (C0801kz) obj;
                        if (i2 == 0) {
                            ((ViewGroup.MarginLayoutParams) c0801kz).width = 0;
                        } else {
                            ((ViewGroup.MarginLayoutParams) c0801kz).height = 0;
                        }
                        m213863b3(c0801kz, strTrim2);
                        return;
                    }
                    if (obj instanceof C0821li) {
                        ((C0821li) obj).f57960c5 = strTrim2;
                        return;
                    } else {
                        if (obj instanceof C0819lg) {
                            ((C0819lg) obj).m213841a2(5, strTrim2);
                            return;
                        }
                        return;
                    }
                }
                try {
                    if ("weight".equalsIgnoreCase(strTrim)) {
                        float f = Float.parseFloat(strTrim2);
                        if (obj instanceof C0801kz) {
                            C0801kz c0801kz2 = (C0801kz) obj;
                            if (i2 == 0) {
                                ((ViewGroup.MarginLayoutParams) c0801kz2).width = 0;
                                c0801kz2.f57779d3 = f;
                                return;
                            } else {
                                ((ViewGroup.MarginLayoutParams) c0801kz2).height = 0;
                                c0801kz2.f57780d4 = f;
                                return;
                            }
                        }
                        if (obj instanceof C0821li) {
                            C0821li c0821li = (C0821li) obj;
                            if (i2 == 0) {
                                c0821li.f57937a2 = 0;
                                c0821li.f57982e7 = f;
                                return;
                            } else {
                                c0821li.f57938a3 = 0;
                                c0821li.f57981e6 = f;
                                return;
                            }
                        }
                        if (obj instanceof C0819lg) {
                            C0819lg c0819lg = (C0819lg) obj;
                            if (i2 == 0) {
                                c0819lg.m213840a1(23, 0);
                                c0819lg.m213839a0(f, 39);
                                return;
                            } else {
                                c0819lg.m213840a1(21, 0);
                                c0819lg.m213839a0(f, 40);
                                return;
                            }
                        }
                        return;
                    }
                    if ("parent".equalsIgnoreCase(strTrim)) {
                        float fMax = Math.max(0.0f, Math.min(1.0f, Float.parseFloat(strTrim2)));
                        if (obj instanceof C0801kz) {
                            C0801kz c0801kz3 = (C0801kz) obj;
                            if (i2 == 0) {
                                ((ViewGroup.MarginLayoutParams) c0801kz3).width = 0;
                                c0801kz3.f57789e3 = fMax;
                                c0801kz3.f57783d7 = 2;
                                return;
                            } else {
                                ((ViewGroup.MarginLayoutParams) c0801kz3).height = 0;
                                c0801kz3.f57790e4 = fMax;
                                c0801kz3.f57784d8 = 2;
                                return;
                            }
                        }
                        if (obj instanceof C0821li) {
                            C0821li c0821li2 = (C0821li) obj;
                            if (i2 == 0) {
                                c0821li2.f57937a2 = 0;
                                c0821li2.f57991f6 = fMax;
                                c0821li2.f57985f0 = 2;
                                return;
                            } else {
                                c0821li2.f57938a3 = 0;
                                c0821li2.f57992f7 = fMax;
                                c0821li2.f57986f1 = 2;
                                return;
                            }
                        }
                        if (obj instanceof C0819lg) {
                            C0819lg c0819lg2 = (C0819lg) obj;
                            if (i2 == 0) {
                                c0819lg2.m213840a1(23, 0);
                                c0819lg2.m213840a1(54, 2);
                                return;
                            } else {
                                c0819lg2.m213840a1(21, 0);
                                c0819lg2.m213840a1(55, 2);
                                return;
                            }
                        }
                        return;
                    }
                    return;
                } catch (NumberFormatException unused) {
                    return;
                }
            }
            return;
        }
        if (i3 != 5) {
            dimensionPixelSize = typedArray.getInt(i, 0);
            if (dimensionPixelSize == -4) {
                i4 = -2;
            } else if (dimensionPixelSize == -3 || (dimensionPixelSize != -2 && dimensionPixelSize != -1)) {
                z = false;
            }
            if (!(obj instanceof C0801kz)) {
                C0801kz c0801kz4 = (C0801kz) obj;
                if (i2 == 0) {
                    ((ViewGroup.MarginLayoutParams) c0801kz4).width = i4;
                    c0801kz4.f57794e8 = z;
                    return;
                } else {
                    ((ViewGroup.MarginLayoutParams) c0801kz4).height = i4;
                    c0801kz4.f57795e9 = z;
                    return;
                }
            }
            if (obj instanceof C0821li) {
                C0821li c0821li3 = (C0821li) obj;
                if (i2 == 0) {
                    c0821li3.f57937a2 = i4;
                    c0821li3.f57999g4 = z;
                    return;
                } else {
                    c0821li3.f57938a3 = i4;
                    c0821li3.f58000g5 = z;
                    return;
                }
            }
            if (obj instanceof C0819lg) {
                C0819lg c0819lg3 = (C0819lg) obj;
                if (i2 == 0) {
                    c0819lg3.m213840a1(23, i4);
                    c0819lg3.m213842a3(80, z);
                    return;
                } else {
                    c0819lg3.m213840a1(21, i4);
                    c0819lg3.m213842a3(81, z);
                    return;
                }
            }
            return;
        }
        dimensionPixelSize = typedArray.getDimensionPixelSize(i, 0);
        z = false;
        i4 = dimensionPixelSize;
        if (!(obj instanceof C0801kz)) {
        }
    }

    /* renamed from: b3 */
    public static void m213863b3(C0801kz c0801kz, String str) throws NumberFormatException {
        if (str != null) {
            int length = str.length();
            int iIndexOf = str.indexOf(44);
            int i = -1;
            if (iIndexOf > 0 && iIndexOf < length - 1) {
                String strSubstring = str.substring(0, iIndexOf);
                i = strSubstring.equalsIgnoreCase("W") ? 0 : strSubstring.equalsIgnoreCase("H") ? 1 : -1;
                i = iIndexOf + 1;
            }
            int iIndexOf2 = str.indexOf(58);
            try {
                if (iIndexOf2 < 0 || iIndexOf2 >= length - 1) {
                    String strSubstring2 = str.substring(i);
                    if (strSubstring2.length() > 0) {
                        Float.parseFloat(strSubstring2);
                    }
                } else {
                    String strSubstring3 = str.substring(i, iIndexOf2);
                    String strSubstring4 = str.substring(iIndexOf2 + 1);
                    if (strSubstring3.length() > 0 && strSubstring4.length() > 0) {
                        float f = Float.parseFloat(strSubstring3);
                        float f2 = Float.parseFloat(strSubstring4);
                        if (f > 0.0f && f2 > 0.0f) {
                            if (i == 1) {
                                Math.abs(f2 / f);
                            } else {
                                Math.abs(f / f2);
                            }
                        }
                    }
                }
            } catch (NumberFormatException unused) {
            }
        }
        c0801kz.f57778d2 = str;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* renamed from: b4 */
    public static void m213864b4(C0820lh c0820lh, TypedArray typedArray) throws NumberFormatException {
        char c;
        int indexCount = typedArray.getIndexCount();
        C0819lg c0819lg = new C0819lg();
        c0819lg.f57912a0 = new int[10];
        c0819lg.f57913a1 = new int[10];
        int i = 0;
        c0819lg.f57914a2 = 0;
        c0819lg.f57915a3 = new int[10];
        c0819lg.f57916a4 = new float[10];
        c0819lg.f57917a5 = 0;
        c0819lg.f57918a6 = new int[5];
        c0819lg.f57919a7 = new String[5];
        c0819lg.f57920a8 = 0;
        c0819lg.f57921a9 = new int[4];
        c0819lg.f57922b0 = new boolean[4];
        c0819lg.f57923b1 = 0;
        c0820lh.f57933a7 = c0819lg;
        C0822lj c0822lj = c0820lh.f57929a3;
        c0822lj.f58007a0 = false;
        C0821li c0821li = c0820lh.f57930a4;
        c0821li.f57936a1 = false;
        C0823lk c0823lk = c0820lh.f57928a2;
        c0823lk.f58024a0 = false;
        C0824ll c0824ll = c0820lh.f57931a5;
        c0824ll.f58030a0 = false;
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArray.getIndex(i2);
            int i3 = f58046a8.get(index);
            SparseIntArray sparseIntArray = f58045a7;
            switch (i3) {
                case 2:
                    c = 5;
                    c0819lg.m213840a1(2, typedArray.getDimensionPixelSize(index, c0821li.f57970d5));
                    break;
                case 3:
                case 4:
                case 9:
                case 10:
                case 25:
                case 26:
                case 29:
                case 30:
                case 32:
                case 33:
                case 35:
                case 36:
                case 61:
                case 88:
                case 89:
                case 90:
                case 91:
                case 92:
                default:
                    Integer.toHexString(index);
                    sparseIntArray.get(index);
                    c = 5;
                    break;
                case 5:
                    c = 5;
                    c0819lg.m213841a2(5, typedArray.getString(index));
                    break;
                case 6:
                    c0819lg.m213840a1(6, typedArray.getDimensionPixelOffset(index, c0821li.f57964c9));
                    c = 5;
                    break;
                case 7:
                    c0819lg.m213840a1(7, typedArray.getDimensionPixelOffset(index, c0821li.f57965d0));
                    c = 5;
                    break;
                case 8:
                    c0819lg.m213840a1(8, typedArray.getDimensionPixelSize(index, c0821li.f57971d6));
                    c = 5;
                    break;
                case oe0.DEFAULT_M /* 11 */:
                    c0819lg.m213840a1(11, typedArray.getDimensionPixelSize(index, c0821li.f57977e2));
                    c = 5;
                    break;
                case FileClientSessionCache.MAX_SIZE /* 12 */:
                    c0819lg.m213840a1(12, typedArray.getDimensionPixelSize(index, c0821li.f57978e3));
                    c = 5;
                    break;
                case 13:
                    c0819lg.m213840a1(13, typedArray.getDimensionPixelSize(index, c0821li.f57974d9));
                    c = 5;
                    break;
                case 14:
                    c0819lg.m213840a1(14, typedArray.getDimensionPixelSize(index, c0821li.f57976e1));
                    c = 5;
                    break;
                case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                    c0819lg.m213840a1(15, typedArray.getDimensionPixelSize(index, c0821li.f57979e4));
                    c = 5;
                    break;
                case 16:
                    c0819lg.m213840a1(16, typedArray.getDimensionPixelSize(index, c0821li.f57975e0));
                    c = 5;
                    break;
                case 17:
                    c0819lg.m213840a1(17, typedArray.getDimensionPixelOffset(index, c0821li.f57939a4));
                    c = 5;
                    break;
                case 18:
                    c0819lg.m213840a1(18, typedArray.getDimensionPixelOffset(index, c0821li.f57940a5));
                    c = 5;
                    break;
                case Base64.Encoder.LINE_GROUPS /* 19 */:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0821li.f57941a6), 19);
                    c = 5;
                    break;
                case 20:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0821li.f57958c3), 20);
                    c = 5;
                    break;
                case 21:
                    c0819lg.m213840a1(21, typedArray.getLayoutDimension(index, c0821li.f57938a3));
                    c = 5;
                    break;
                case 22:
                    c0819lg.m213840a1(22, f58044a6[typedArray.getInt(index, c0823lk.f58025a1)]);
                    c = 5;
                    break;
                case 23:
                    c0819lg.m213840a1(23, typedArray.getLayoutDimension(index, c0821li.f57937a2));
                    c = 5;
                    break;
                case 24:
                    c0819lg.m213840a1(24, typedArray.getDimensionPixelSize(index, c0821li.f57967d2));
                    c = 5;
                    break;
                case 27:
                    c0819lg.m213840a1(27, typedArray.getInt(index, c0821li.f57966d1));
                    c = 5;
                    break;
                case 28:
                    c0819lg.m213840a1(28, typedArray.getDimensionPixelSize(index, c0821li.f57968d3));
                    c = 5;
                    break;
                case 31:
                    c0819lg.m213840a1(31, typedArray.getDimensionPixelSize(index, c0821li.f57972d7));
                    c = 5;
                    break;
                case 34:
                    c0819lg.m213840a1(34, typedArray.getDimensionPixelSize(index, c0821li.f57969d4));
                    c = 5;
                    break;
                case 37:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0821li.f57959c4), 37);
                    c = 5;
                    break;
                case 38:
                    int resourceId = typedArray.getResourceId(index, c0820lh.f57926a0);
                    c0820lh.f57926a0 = resourceId;
                    c0819lg.m213840a1(38, resourceId);
                    c = 5;
                    break;
                case 39:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0821li.f57982e7), 39);
                    c = 5;
                    break;
                case 40:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0821li.f57981e6), 40);
                    c = 5;
                    break;
                case 41:
                    c0819lg.m213840a1(41, typedArray.getInt(index, c0821li.f57983e8));
                    c = 5;
                    break;
                case 42:
                    c0819lg.m213840a1(42, typedArray.getInt(index, c0821li.f57984e9));
                    c = 5;
                    break;
                case 43:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0823lk.f58027a3), 43);
                    c = 5;
                    break;
                case 44:
                    c0819lg.m213842a3(44, true);
                    c0819lg.m213839a0(typedArray.getDimension(index, c0824ll.f58043b3), 44);
                    c = 5;
                    break;
                case 45:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0824ll.f58032a2), 45);
                    c = 5;
                    break;
                case 46:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0824ll.f58033a3), 46);
                    c = 5;
                    break;
                case 47:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0824ll.f58034a4), 47);
                    c = 5;
                    break;
                case 48:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0824ll.f58035a5), 48);
                    c = 5;
                    break;
                case 49:
                    c0819lg.m213839a0(typedArray.getDimension(index, c0824ll.f58036a6), 49);
                    c = 5;
                    break;
                case oe0.DEFAULT_T /* 50 */:
                    c0819lg.m213839a0(typedArray.getDimension(index, c0824ll.f58037a7), 50);
                    c = 5;
                    break;
                case 51:
                    c0819lg.m213839a0(typedArray.getDimension(index, c0824ll.f58039a9), 51);
                    c = 5;
                    break;
                case 52:
                    c0819lg.m213839a0(typedArray.getDimension(index, c0824ll.f58040b0), 52);
                    c = 5;
                    break;
                case 53:
                    c0819lg.m213839a0(typedArray.getDimension(index, c0824ll.f58041b1), 53);
                    c = 5;
                    break;
                case 54:
                    c0819lg.m213840a1(54, typedArray.getInt(index, c0821li.f57985f0));
                    c = 5;
                    break;
                case 55:
                    c0819lg.m213840a1(55, typedArray.getInt(index, c0821li.f57986f1));
                    c = 5;
                    break;
                case 56:
                    c0819lg.m213840a1(56, typedArray.getDimensionPixelSize(index, c0821li.f57987f2));
                    c = 5;
                    break;
                case 57:
                    c0819lg.m213840a1(57, typedArray.getDimensionPixelSize(index, c0821li.f57988f3));
                    c = 5;
                    break;
                case 58:
                    c0819lg.m213840a1(58, typedArray.getDimensionPixelSize(index, c0821li.f57989f4));
                    c = 5;
                    break;
                case 59:
                    c0819lg.m213840a1(59, typedArray.getDimensionPixelSize(index, c0821li.f57990f5));
                    c = 5;
                    break;
                case 60:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0824ll.f58031a1), 60);
                    c = 5;
                    break;
                case 62:
                    c0819lg.m213840a1(62, typedArray.getDimensionPixelSize(index, c0821li.f57962c7));
                    c = 5;
                    break;
                case 63:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0821li.f57963c8), 63);
                    c = 5;
                    break;
                case 64:
                    c0819lg.m213840a1(64, m213861b1(typedArray, index, c0822lj.f58008a1));
                    c = 5;
                    break;
                case 65:
                    if (typedArray.peekValue(index).type == 3) {
                        c0819lg.m213841a2(65, typedArray.getString(index));
                    } else {
                        c0819lg.m213841a2(65, C1347vr.f60676a3[typedArray.getInteger(index, i)]);
                    }
                    c = 5;
                    break;
                case 66:
                    i = 0;
                    c0819lg.m213840a1(66, typedArray.getInt(index, 0));
                    c = 5;
                    break;
                case 67:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0822lj.f58014a7), 67);
                    i = 0;
                    c = 5;
                    break;
                case 68:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0823lk.f58028a4), 68);
                    i = 0;
                    c = 5;
                    break;
                case 69:
                    c0819lg.m213839a0(typedArray.getFloat(index, 1.0f), 69);
                    i = 0;
                    c = 5;
                    break;
                case 70:
                    c0819lg.m213839a0(typedArray.getFloat(index, 1.0f), 70);
                    i = 0;
                    c = 5;
                    break;
                case 71:
                    c = 5;
                    break;
                case 72:
                    c0819lg.m213840a1(72, typedArray.getInt(index, c0821li.f57993f8));
                    i = 0;
                    c = 5;
                    break;
                case 73:
                    c0819lg.m213840a1(73, typedArray.getDimensionPixelSize(index, c0821li.f57994f9));
                    i = 0;
                    c = 5;
                    break;
                case 74:
                    c0819lg.m213841a2(74, typedArray.getString(index));
                    i = 0;
                    c = 5;
                    break;
                case 75:
                    c0819lg.m213842a3(75, typedArray.getBoolean(index, c0821li.f58001g6));
                    i = 0;
                    c = 5;
                    break;
                case 76:
                    c0819lg.m213840a1(76, typedArray.getInt(index, c0822lj.f58011a4));
                    i = 0;
                    c = 5;
                    break;
                case 77:
                    c0819lg.m213841a2(77, typedArray.getString(index));
                    i = 0;
                    c = 5;
                    break;
                case 78:
                    c0819lg.m213840a1(78, typedArray.getInt(index, c0823lk.f58026a2));
                    i = 0;
                    c = 5;
                    break;
                case 79:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0822lj.f58013a6), 79);
                    i = 0;
                    c = 5;
                    break;
                case 80:
                    c0819lg.m213842a3(80, typedArray.getBoolean(index, c0821li.f57999g4));
                    i = 0;
                    c = 5;
                    break;
                case 81:
                    c0819lg.m213842a3(81, typedArray.getBoolean(index, c0821li.f58000g5));
                    i = 0;
                    c = 5;
                    break;
                case 82:
                    c0819lg.m213840a1(82, typedArray.getInteger(index, c0822lj.f58009a2));
                    i = 0;
                    c = 5;
                    break;
                case 83:
                    c0819lg.m213840a1(83, m213861b1(typedArray, index, c0824ll.f58038a8));
                    i = 0;
                    c = 5;
                    break;
                case 84:
                    c0819lg.m213840a1(84, typedArray.getInteger(index, c0822lj.f58016a9));
                    i = 0;
                    c = 5;
                    break;
                case 85:
                    c0819lg.m213839a0(typedArray.getFloat(index, c0822lj.f58015a8), 85);
                    i = 0;
                    c = 5;
                    break;
                case 86:
                    int i4 = typedArray.peekValue(index).type;
                    if (i4 == 1) {
                        int resourceId2 = typedArray.getResourceId(index, -1);
                        c0822lj.f58019b2 = resourceId2;
                        c0819lg.m213840a1(89, resourceId2);
                        if (c0822lj.f58019b2 != -1) {
                            c0822lj.f58018b1 = -2;
                            c0819lg.m213840a1(88, -2);
                        }
                    } else if (i4 == 3) {
                        String string = typedArray.getString(index);
                        c0822lj.f58017b0 = string;
                        c0819lg.m213841a2(90, string);
                        if (c0822lj.f58017b0.indexOf("/") > 0) {
                            int resourceId3 = typedArray.getResourceId(index, -1);
                            c0822lj.f58019b2 = resourceId3;
                            c0819lg.m213840a1(89, resourceId3);
                            c0822lj.f58018b1 = -2;
                            c0819lg.m213840a1(88, -2);
                        } else {
                            c0822lj.f58018b1 = -1;
                            c0819lg.m213840a1(88, -1);
                        }
                    } else {
                        int integer = typedArray.getInteger(index, c0822lj.f58019b2);
                        c0822lj.f58018b1 = integer;
                        c0819lg.m213840a1(88, integer);
                    }
                    i = 0;
                    c = 5;
                    break;
                case 87:
                    Integer.toHexString(index);
                    sparseIntArray.get(index);
                    c = 5;
                    break;
                case 93:
                    c0819lg.m213840a1(93, typedArray.getDimensionPixelSize(index, c0821li.f57973d8));
                    c = 5;
                    break;
                case 94:
                    c0819lg.m213840a1(94, typedArray.getDimensionPixelSize(index, c0821li.f57980e5));
                    c = 5;
                    break;
                case 95:
                    m213862b2(c0819lg, typedArray, index, i);
                    c = 5;
                    break;
                case 96:
                    m213862b2(c0819lg, typedArray, index, 1);
                    c = 5;
                    break;
                case 97:
                    c0819lg.m213840a1(97, typedArray.getInt(index, c0821li.f58002g7));
                    c = 5;
                    break;
                case 98:
                    if (MotionLayout.f44523i2) {
                        int resourceId4 = typedArray.getResourceId(index, c0820lh.f57926a0);
                        c0820lh.f57926a0 = resourceId4;
                        if (resourceId4 == -1) {
                            c0820lh.f57927a1 = typedArray.getString(index);
                        }
                    } else if (typedArray.peekValue(index).type == 3) {
                        c0820lh.f57927a1 = typedArray.getString(index);
                    } else {
                        c0820lh.f57926a0 = typedArray.getResourceId(index, c0820lh.f57926a0);
                    }
                    c = 5;
                    break;
                case 99:
                    c0819lg.m213842a3(99, typedArray.getBoolean(index, c0821li.f57942a7));
                    c = 5;
                    break;
            }
        }
    }

    /* renamed from: a0 */
    public final void m213865a0(MotionLayout motionLayout) {
        C0820lh c0820lh;
        int childCount = motionLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = motionLayout.getChildAt(i);
            int id = childAt.getId();
            Integer numValueOf = Integer.valueOf(id);
            HashMap map = this.f58052a5;
            if (!map.containsKey(numValueOf)) {
                t60.m214712d3(childAt);
            } else {
                if (this.f58051a4 && id == -1) {
                    throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
                }
                if (map.containsKey(Integer.valueOf(id)) && (c0820lh = (C0820lh) map.get(Integer.valueOf(id))) != null) {
                    C0798kw.m213759a4(childAt, c0820lh.f57932a6);
                }
            }
        }
    }

    /* renamed from: a1 */
    public final void m213866a1(ConstraintLayout constraintLayout) throws IllegalAccessException, IllegalArgumentException {
        m213867a2(constraintLayout);
        constraintLayout.setConstraintSet(null);
        constraintLayout.requestLayout();
    }

    /* renamed from: a2 */
    public final void m213867a2(ConstraintLayout constraintLayout) throws IllegalAccessException, IllegalArgumentException {
        int childCount = constraintLayout.getChildCount();
        HashMap map = this.f58052a5;
        HashSet hashSet = new HashSet(map.keySet());
        for (int i = 0; i < childCount; i++) {
            View childAt = constraintLayout.getChildAt(i);
            int id = childAt.getId();
            if (!map.containsKey(Integer.valueOf(id))) {
                t60.m214712d3(childAt);
            } else {
                if (this.f58051a4 && id == -1) {
                    throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
                }
                if (id != -1 && map.containsKey(Integer.valueOf(id))) {
                    hashSet.remove(Integer.valueOf(id));
                    C0820lh c0820lh = (C0820lh) map.get(Integer.valueOf(id));
                    if (c0820lh != null) {
                        C0823lk c0823lk = c0820lh.f57928a2;
                        C0821li c0821li = c0820lh.f57930a4;
                        C0824ll c0824ll = c0820lh.f57931a5;
                        if (childAt instanceof Barrier) {
                            c0821li.f57995g0 = 1;
                            Barrier barrier = (Barrier) childAt;
                            barrier.setId(id);
                            barrier.setType(c0821li.f57993f8);
                            barrier.setMargin(c0821li.f57994f9);
                            barrier.setAllowsGoneWidget(c0821li.f58001g6);
                            int[] iArr = c0821li.f57996g1;
                            if (iArr != null) {
                                barrier.setReferencedIds(iArr);
                            } else {
                                String str = c0821li.f57997g2;
                                if (str != null) {
                                    int[] iArrM213859a5 = m213859a5(barrier, str);
                                    c0821li.f57996g1 = iArrM213859a5;
                                    barrier.setReferencedIds(iArrM213859a5);
                                }
                            }
                        }
                        C0801kz c0801kz = (C0801kz) childAt.getLayoutParams();
                        c0801kz.m213766a0();
                        c0820lh.m213845a0(c0801kz);
                        C0798kw.m213759a4(childAt, c0820lh.f57932a6);
                        childAt.setLayoutParams(c0801kz);
                        if (c0823lk.f58026a2 == 0) {
                            childAt.setVisibility(c0823lk.f58025a1);
                        }
                        childAt.setAlpha(c0823lk.f58027a3);
                        childAt.setRotation(c0824ll.f58031a1);
                        childAt.setRotationX(c0824ll.f58032a2);
                        childAt.setRotationY(c0824ll.f58033a3);
                        childAt.setScaleX(c0824ll.f58034a4);
                        childAt.setScaleY(c0824ll.f58035a5);
                        if (c0824ll.f58038a8 != -1) {
                            if (((View) childAt.getParent()).findViewById(c0824ll.f58038a8) != null) {
                                float bottom = (r5.getBottom() + r5.getTop()) / 2.0f;
                                float right = (r5.getRight() + r5.getLeft()) / 2.0f;
                                if (childAt.getRight() - childAt.getLeft() > 0 && childAt.getBottom() - childAt.getTop() > 0) {
                                    childAt.setPivotX(right - childAt.getLeft());
                                    childAt.setPivotY(bottom - childAt.getTop());
                                }
                            }
                        } else {
                            if (!Float.isNaN(c0824ll.f58036a6)) {
                                childAt.setPivotX(c0824ll.f58036a6);
                            }
                            if (!Float.isNaN(c0824ll.f58037a7)) {
                                childAt.setPivotY(c0824ll.f58037a7);
                            }
                        }
                        childAt.setTranslationX(c0824ll.f58039a9);
                        childAt.setTranslationY(c0824ll.f58040b0);
                        childAt.setTranslationZ(c0824ll.f58041b1);
                        if (c0824ll.f58042b2) {
                            childAt.setElevation(c0824ll.f58043b3);
                        }
                    }
                }
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            C0820lh c0820lh2 = (C0820lh) map.get(num);
            if (c0820lh2 != null) {
                C0821li c0821li2 = c0820lh2.f57930a4;
                if (c0821li2.f57995g0 == 1) {
                    Barrier barrier2 = new Barrier(constraintLayout.getContext());
                    barrier2.setId(num.intValue());
                    int[] iArr2 = c0821li2.f57996g1;
                    if (iArr2 != null) {
                        barrier2.setReferencedIds(iArr2);
                    } else {
                        String str2 = c0821li2.f57997g2;
                        if (str2 != null) {
                            int[] iArrM213859a52 = m213859a5(barrier2, str2);
                            c0821li2.f57996g1 = iArrM213859a52;
                            barrier2.setReferencedIds(iArrM213859a52);
                        }
                    }
                    barrier2.setType(c0821li2.f57993f8);
                    barrier2.setMargin(c0821li2.f57994f9);
                    o01 o01Var = ConstraintLayout.f44770b7;
                    C0801kz c0801kz2 = new C0801kz();
                    barrier2.m210045b6();
                    c0820lh2.m213845a0(c0801kz2);
                    constraintLayout.addView(barrier2, c0801kz2);
                }
                if (c0821li2.f57935a0) {
                    View guideline = new Guideline(constraintLayout.getContext());
                    guideline.setId(num.intValue());
                    o01 o01Var2 = ConstraintLayout.f44770b7;
                    C0801kz c0801kz3 = new C0801kz();
                    c0820lh2.m213845a0(c0801kz3);
                    constraintLayout.addView(guideline, c0801kz3);
                }
            }
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt2 = constraintLayout.getChildAt(i2);
            if (childAt2 instanceof ConstraintHelper) {
                ((ConstraintHelper) childAt2).mo209975a6(constraintLayout);
            }
        }
    }

    /* renamed from: a4 */
    public final void m213868a4(ConstraintLayout constraintLayout) {
        int i;
        HashMap map;
        int i2;
        int i3;
        C0825lm c0825lm = this;
        int childCount = constraintLayout.getChildCount();
        HashMap map2 = c0825lm.f58052a5;
        map2.clear();
        int i4 = 0;
        while (i4 < childCount) {
            View childAt = constraintLayout.getChildAt(i4);
            C0801kz c0801kz = (C0801kz) childAt.getLayoutParams();
            int id = childAt.getId();
            if (c0825lm.f58051a4 && id == -1) {
                throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!map2.containsKey(Integer.valueOf(id))) {
                map2.put(Integer.valueOf(id), new C0820lh());
            }
            C0820lh c0820lh = (C0820lh) map2.get(Integer.valueOf(id));
            if (c0820lh == null) {
                i = childCount;
                map = map2;
                i2 = i4;
            } else {
                C0823lk c0823lk = c0820lh.f57928a2;
                C0821li c0821li = c0820lh.f57930a4;
                C0824ll c0824ll = c0820lh.f57931a5;
                HashMap map3 = new HashMap();
                Class<?> cls = childAt.getClass();
                HashMap map4 = c0825lm.f58050a3;
                for (String str : map4.keySet()) {
                    int i5 = childCount;
                    C0798kw c0798kw = (C0798kw) map4.get(str);
                    HashMap map5 = map2;
                    try {
                        if (str.equals("BackgroundColor")) {
                            i3 = i4;
                            try {
                                map3.put(str, new C0798kw(c0798kw, Integer.valueOf(((ColorDrawable) childAt.getBackground()).getColor())));
                            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
                            }
                        } else {
                            i3 = i4;
                            map3.put(str, new C0798kw(c0798kw, cls.getMethod("getMap" + str, null).invoke(childAt, null)));
                        }
                    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused2) {
                        i3 = i4;
                    }
                    map2 = map5;
                    childCount = i5;
                    i4 = i3;
                }
                i = childCount;
                map = map2;
                i2 = i4;
                c0820lh.f57932a6 = map3;
                c0820lh.m213847a2(id, c0801kz);
                c0823lk.f58025a1 = childAt.getVisibility();
                c0823lk.f58027a3 = childAt.getAlpha();
                c0824ll.f58031a1 = childAt.getRotation();
                c0824ll.f58032a2 = childAt.getRotationX();
                c0824ll.f58033a3 = childAt.getRotationY();
                c0824ll.f58034a4 = childAt.getScaleX();
                c0824ll.f58035a5 = childAt.getScaleY();
                float pivotX = childAt.getPivotX();
                float pivotY = childAt.getPivotY();
                if (pivotX != 0.0d || pivotY != 0.0d) {
                    c0824ll.f58036a6 = pivotX;
                    c0824ll.f58037a7 = pivotY;
                }
                c0824ll.f58039a9 = childAt.getTranslationX();
                c0824ll.f58040b0 = childAt.getTranslationY();
                c0824ll.f58041b1 = childAt.getTranslationZ();
                if (c0824ll.f58042b2) {
                    c0824ll.f58043b3 = childAt.getElevation();
                }
                if (childAt instanceof Barrier) {
                    Barrier barrier = (Barrier) childAt;
                    c0821li.f58001g6 = barrier.getAllowsGoneWidget();
                    c0821li.f57996g1 = barrier.getReferencedIds();
                    c0821li.f57993f8 = barrier.getType();
                    c0821li.f57994f9 = barrier.getMargin();
                }
            }
            i4 = i2 + 1;
            c0825lm = this;
            map2 = map;
            childCount = i;
        }
    }

    /* renamed from: a7 */
    public final C0820lh m213869a7(int i) {
        Integer numValueOf = Integer.valueOf(i);
        HashMap map = this.f58052a5;
        if (!map.containsKey(numValueOf)) {
            map.put(Integer.valueOf(i), new C0820lh());
        }
        return (C0820lh) map.get(Integer.valueOf(i));
    }

    /* renamed from: a8 */
    public final C0820lh m213870a8(int i) {
        Integer numValueOf = Integer.valueOf(i);
        HashMap map = this.f58052a5;
        if (map.containsKey(numValueOf)) {
            return (C0820lh) map.get(Integer.valueOf(i));
        }
        return null;
    }

    /* renamed from: a9 */
    public final void m213871a9(Context context, int i) throws XmlPullParserException, Resources.NotFoundException, IOException {
        XmlResourceParser xml = context.getResources().getXml(i);
        try {
            for (int eventType = xml.getEventType(); eventType != 1; eventType = xml.next()) {
                if (eventType == 0) {
                    xml.getName();
                } else if (eventType == 2) {
                    String name = xml.getName();
                    C0820lh c0820lhM213860a6 = m213860a6(context, Xml.asAttributeSet(xml), false);
                    if (name.equalsIgnoreCase("Guideline")) {
                        c0820lhM213860a6.f57930a4.f57935a0 = true;
                    }
                    this.f58052a5.put(Integer.valueOf(c0820lhM213860a6.f57926a0), c0820lhM213860a6);
                }
            }
        } catch (IOException | XmlPullParserException unused) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:115:0x01a5, code lost:
    
        continue;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* renamed from: b0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m213872b0(Context context, XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        try {
            int eventType = xmlResourceParser.getEventType();
            C0820lh c0820lhM213860a6 = null;
            while (eventType != 1) {
                if (eventType == 0) {
                    xmlResourceParser.getName();
                } else if (eventType == 2) {
                    String name = xmlResourceParser.getName();
                    switch (name.hashCode()) {
                        case -2025855158:
                            if (!name.equals("Layout")) {
                                continue;
                            } else {
                                if (c0820lhM213860a6 == null) {
                                    throw new RuntimeException("XML parser error must be within a Constraint " + xmlResourceParser.getLineNumber());
                                }
                                c0820lhM213860a6.f57930a4.m213850a1(context, Xml.asAttributeSet(xmlResourceParser));
                                break;
                            }
                        case -1984451626:
                            if (!name.equals("Motion")) {
                                continue;
                            } else {
                                if (c0820lhM213860a6 == null) {
                                    throw new RuntimeException("XML parser error must be within a Constraint " + xmlResourceParser.getLineNumber());
                                }
                                c0820lhM213860a6.f57929a3.m213852a1(context, Xml.asAttributeSet(xmlResourceParser));
                                break;
                            }
                        case -1962203927:
                            if (name.equals("ConstraintOverride")) {
                                c0820lhM213860a6 = m213860a6(context, Xml.asAttributeSet(xmlResourceParser), true);
                                break;
                            } else {
                                continue;
                            }
                        case -1269513683:
                            if (!name.equals("PropertySet")) {
                                continue;
                            } else {
                                if (c0820lhM213860a6 == null) {
                                    throw new RuntimeException("XML parser error must be within a Constraint " + xmlResourceParser.getLineNumber());
                                }
                                c0820lhM213860a6.f57928a2.m213853a0(context, Xml.asAttributeSet(xmlResourceParser));
                                break;
                            }
                        case -1238332596:
                            if (!name.equals("Transform")) {
                                continue;
                            } else {
                                if (c0820lhM213860a6 == null) {
                                    throw new RuntimeException("XML parser error must be within a Constraint " + xmlResourceParser.getLineNumber());
                                }
                                c0820lhM213860a6.f57931a5.m213857a1(context, Xml.asAttributeSet(xmlResourceParser));
                                break;
                            }
                        case -71750448:
                            if (name.equals("Guideline")) {
                                c0820lhM213860a6 = m213860a6(context, Xml.asAttributeSet(xmlResourceParser), false);
                                C0821li c0821li = c0820lhM213860a6.f57930a4;
                                c0821li.f57935a0 = true;
                                c0821li.f57936a1 = true;
                                break;
                            } else {
                                continue;
                            }
                        case 366511058:
                            if (!name.equals("CustomMethod")) {
                                continue;
                            }
                            break;
                        case 1331510167:
                            if (name.equals("Barrier")) {
                                c0820lhM213860a6 = m213860a6(context, Xml.asAttributeSet(xmlResourceParser), false);
                                c0820lhM213860a6.f57930a4.f57995g0 = 1;
                                break;
                            } else {
                                continue;
                            }
                        case 1791837707:
                            if (name.equals("CustomAttribute")) {
                                break;
                            } else {
                                continue;
                            }
                        case 1803088381:
                            if (name.equals("Constraint")) {
                                c0820lhM213860a6 = m213860a6(context, Xml.asAttributeSet(xmlResourceParser), false);
                                break;
                            } else {
                                continue;
                            }
                    }
                    if (c0820lhM213860a6 == null) {
                        throw new RuntimeException("XML parser error must be within a Constraint " + xmlResourceParser.getLineNumber());
                    }
                    C0798kw.m213758a3(context, xmlResourceParser, c0820lhM213860a6.f57932a6);
                } else if (eventType == 3) {
                    String lowerCase = xmlResourceParser.getName().toLowerCase(Locale.ROOT);
                    switch (lowerCase.hashCode()) {
                        case -2075718416:
                            if (!lowerCase.equals("guideline")) {
                                break;
                            }
                            break;
                        case -190376483:
                            if (!lowerCase.equals("constraint")) {
                                break;
                            } else {
                                break;
                            }
                        case 426575017:
                            if (!lowerCase.equals("constraintoverride")) {
                                break;
                            } else {
                                break;
                            }
                        case 2146106725:
                            if (lowerCase.equals("constraintset")) {
                                return;
                            } else {
                                continue;
                            }
                        default:
                            continue;
                    }
                    this.f58052a5.put(Integer.valueOf(c0820lhM213860a6.f57926a0), c0820lhM213860a6);
                    c0820lhM213860a6 = null;
                }
                eventType = xmlResourceParser.next();
            }
        } catch (IOException | XmlPullParserException unused) {
        }
    }
}

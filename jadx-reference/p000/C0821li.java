package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import androidx.constraintlayout.widget.R$styleable;
import io.socket.engineio.parser.Base64;
import java.util.Arrays;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: li */
/* loaded from: classes.dex */
public final class C0821li {

    /* renamed from: g8 */
    public static final SparseIntArray f57934g8;

    /* renamed from: a0 */
    public boolean f57935a0;

    /* renamed from: a1 */
    public boolean f57936a1;

    /* renamed from: a2 */
    public int f57937a2;

    /* renamed from: a3 */
    public int f57938a3;

    /* renamed from: a4 */
    public int f57939a4;

    /* renamed from: a5 */
    public int f57940a5;

    /* renamed from: a6 */
    public float f57941a6;

    /* renamed from: a7 */
    public boolean f57942a7;

    /* renamed from: a8 */
    public int f57943a8;

    /* renamed from: a9 */
    public int f57944a9;

    /* renamed from: b0 */
    public int f57945b0;

    /* renamed from: b1 */
    public int f57946b1;

    /* renamed from: b2 */
    public int f57947b2;

    /* renamed from: b3 */
    public int f57948b3;

    /* renamed from: b4 */
    public int f57949b4;

    /* renamed from: b5 */
    public int f57950b5;

    /* renamed from: b6 */
    public int f57951b6;

    /* renamed from: b7 */
    public int f57952b7;

    /* renamed from: b8 */
    public int f57953b8;

    /* renamed from: b9 */
    public int f57954b9;

    /* renamed from: c0 */
    public int f57955c0;

    /* renamed from: c1 */
    public int f57956c1;

    /* renamed from: c2 */
    public int f57957c2;

    /* renamed from: c3 */
    public float f57958c3;

    /* renamed from: c4 */
    public float f57959c4;

    /* renamed from: c5 */
    public String f57960c5;

    /* renamed from: c6 */
    public int f57961c6;

    /* renamed from: c7 */
    public int f57962c7;

    /* renamed from: c8 */
    public float f57963c8;

    /* renamed from: c9 */
    public int f57964c9;

    /* renamed from: d0 */
    public int f57965d0;

    /* renamed from: d1 */
    public int f57966d1;

    /* renamed from: d2 */
    public int f57967d2;

    /* renamed from: d3 */
    public int f57968d3;

    /* renamed from: d4 */
    public int f57969d4;

    /* renamed from: d5 */
    public int f57970d5;

    /* renamed from: d6 */
    public int f57971d6;

    /* renamed from: d7 */
    public int f57972d7;

    /* renamed from: d8 */
    public int f57973d8;

    /* renamed from: d9 */
    public int f57974d9;

    /* renamed from: e0 */
    public int f57975e0;

    /* renamed from: e1 */
    public int f57976e1;

    /* renamed from: e2 */
    public int f57977e2;

    /* renamed from: e3 */
    public int f57978e3;

    /* renamed from: e4 */
    public int f57979e4;

    /* renamed from: e5 */
    public int f57980e5;

    /* renamed from: e6 */
    public float f57981e6;

    /* renamed from: e7 */
    public float f57982e7;

    /* renamed from: e8 */
    public int f57983e8;

    /* renamed from: e9 */
    public int f57984e9;

    /* renamed from: f0 */
    public int f57985f0;

    /* renamed from: f1 */
    public int f57986f1;

    /* renamed from: f2 */
    public int f57987f2;

    /* renamed from: f3 */
    public int f57988f3;

    /* renamed from: f4 */
    public int f57989f4;

    /* renamed from: f5 */
    public int f57990f5;

    /* renamed from: f6 */
    public float f57991f6;

    /* renamed from: f7 */
    public float f57992f7;

    /* renamed from: f8 */
    public int f57993f8;

    /* renamed from: f9 */
    public int f57994f9;

    /* renamed from: g0 */
    public int f57995g0;

    /* renamed from: g1 */
    public int[] f57996g1;

    /* renamed from: g2 */
    public String f57997g2;

    /* renamed from: g3 */
    public String f57998g3;

    /* renamed from: g4 */
    public boolean f57999g4;

    /* renamed from: g5 */
    public boolean f58000g5;

    /* renamed from: g6 */
    public boolean f58001g6;

    /* renamed from: g7 */
    public int f58002g7;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        f57934g8 = sparseIntArray;
        sparseIntArray.append(R$styleable.Layout_layout_constraintLeft_toLeftOf, 24);
        sparseIntArray.append(R$styleable.Layout_layout_constraintLeft_toRightOf, 25);
        sparseIntArray.append(R$styleable.Layout_layout_constraintRight_toLeftOf, 28);
        sparseIntArray.append(R$styleable.Layout_layout_constraintRight_toRightOf, 29);
        sparseIntArray.append(R$styleable.Layout_layout_constraintTop_toTopOf, 35);
        sparseIntArray.append(R$styleable.Layout_layout_constraintTop_toBottomOf, 34);
        sparseIntArray.append(R$styleable.Layout_layout_constraintBottom_toTopOf, 4);
        sparseIntArray.append(R$styleable.Layout_layout_constraintBottom_toBottomOf, 3);
        sparseIntArray.append(R$styleable.Layout_layout_constraintBaseline_toBaselineOf, 1);
        sparseIntArray.append(R$styleable.Layout_layout_editor_absoluteX, 6);
        sparseIntArray.append(R$styleable.Layout_layout_editor_absoluteY, 7);
        sparseIntArray.append(R$styleable.Layout_layout_constraintGuide_begin, 17);
        sparseIntArray.append(R$styleable.Layout_layout_constraintGuide_end, 18);
        sparseIntArray.append(R$styleable.Layout_layout_constraintGuide_percent, 19);
        sparseIntArray.append(R$styleable.Layout_guidelineUseRtl, 90);
        sparseIntArray.append(R$styleable.Layout_android_orientation, 26);
        sparseIntArray.append(R$styleable.Layout_layout_constraintStart_toEndOf, 31);
        sparseIntArray.append(R$styleable.Layout_layout_constraintStart_toStartOf, 32);
        sparseIntArray.append(R$styleable.Layout_layout_constraintEnd_toStartOf, 10);
        sparseIntArray.append(R$styleable.Layout_layout_constraintEnd_toEndOf, 9);
        sparseIntArray.append(R$styleable.Layout_layout_goneMarginLeft, 13);
        sparseIntArray.append(R$styleable.Layout_layout_goneMarginTop, 16);
        sparseIntArray.append(R$styleable.Layout_layout_goneMarginRight, 14);
        sparseIntArray.append(R$styleable.Layout_layout_goneMarginBottom, 11);
        sparseIntArray.append(R$styleable.Layout_layout_goneMarginStart, 15);
        sparseIntArray.append(R$styleable.Layout_layout_goneMarginEnd, 12);
        sparseIntArray.append(R$styleable.Layout_layout_constraintVertical_weight, 38);
        sparseIntArray.append(R$styleable.Layout_layout_constraintHorizontal_weight, 37);
        sparseIntArray.append(R$styleable.Layout_layout_constraintHorizontal_chainStyle, 39);
        sparseIntArray.append(R$styleable.Layout_layout_constraintVertical_chainStyle, 40);
        sparseIntArray.append(R$styleable.Layout_layout_constraintHorizontal_bias, 20);
        sparseIntArray.append(R$styleable.Layout_layout_constraintVertical_bias, 36);
        sparseIntArray.append(R$styleable.Layout_layout_constraintDimensionRatio, 5);
        sparseIntArray.append(R$styleable.Layout_layout_constraintLeft_creator, 91);
        sparseIntArray.append(R$styleable.Layout_layout_constraintTop_creator, 91);
        sparseIntArray.append(R$styleable.Layout_layout_constraintRight_creator, 91);
        sparseIntArray.append(R$styleable.Layout_layout_constraintBottom_creator, 91);
        sparseIntArray.append(R$styleable.Layout_layout_constraintBaseline_creator, 91);
        sparseIntArray.append(R$styleable.Layout_android_layout_marginLeft, 23);
        sparseIntArray.append(R$styleable.Layout_android_layout_marginRight, 27);
        sparseIntArray.append(R$styleable.Layout_android_layout_marginStart, 30);
        sparseIntArray.append(R$styleable.Layout_android_layout_marginEnd, 8);
        sparseIntArray.append(R$styleable.Layout_android_layout_marginTop, 33);
        sparseIntArray.append(R$styleable.Layout_android_layout_marginBottom, 2);
        sparseIntArray.append(R$styleable.Layout_android_layout_width, 22);
        sparseIntArray.append(R$styleable.Layout_android_layout_height, 21);
        sparseIntArray.append(R$styleable.Layout_layout_constraintWidth, 41);
        sparseIntArray.append(R$styleable.Layout_layout_constraintHeight, 42);
        sparseIntArray.append(R$styleable.Layout_layout_constrainedWidth, 41);
        sparseIntArray.append(R$styleable.Layout_layout_constrainedHeight, 42);
        sparseIntArray.append(R$styleable.Layout_layout_wrapBehaviorInParent, 76);
        sparseIntArray.append(R$styleable.Layout_layout_constraintCircle, 61);
        sparseIntArray.append(R$styleable.Layout_layout_constraintCircleRadius, 62);
        sparseIntArray.append(R$styleable.Layout_layout_constraintCircleAngle, 63);
        sparseIntArray.append(R$styleable.Layout_layout_constraintWidth_percent, 69);
        sparseIntArray.append(R$styleable.Layout_layout_constraintHeight_percent, 70);
        sparseIntArray.append(R$styleable.Layout_chainUseRtl, 71);
        sparseIntArray.append(R$styleable.Layout_barrierDirection, 72);
        sparseIntArray.append(R$styleable.Layout_barrierMargin, 73);
        sparseIntArray.append(R$styleable.Layout_constraint_referenced_ids, 74);
        sparseIntArray.append(R$styleable.Layout_barrierAllowsGoneWidgets, 75);
    }

    /* renamed from: a0 */
    public final void m213849a0(C0821li c0821li) {
        this.f57935a0 = c0821li.f57935a0;
        this.f57937a2 = c0821li.f57937a2;
        this.f57936a1 = c0821li.f57936a1;
        this.f57938a3 = c0821li.f57938a3;
        this.f57939a4 = c0821li.f57939a4;
        this.f57940a5 = c0821li.f57940a5;
        this.f57941a6 = c0821li.f57941a6;
        this.f57942a7 = c0821li.f57942a7;
        this.f57943a8 = c0821li.f57943a8;
        this.f57944a9 = c0821li.f57944a9;
        this.f57945b0 = c0821li.f57945b0;
        this.f57946b1 = c0821li.f57946b1;
        this.f57947b2 = c0821li.f57947b2;
        this.f57948b3 = c0821li.f57948b3;
        this.f57949b4 = c0821li.f57949b4;
        this.f57950b5 = c0821li.f57950b5;
        this.f57951b6 = c0821li.f57951b6;
        this.f57952b7 = c0821li.f57952b7;
        this.f57953b8 = c0821li.f57953b8;
        this.f57954b9 = c0821li.f57954b9;
        this.f57955c0 = c0821li.f57955c0;
        this.f57956c1 = c0821li.f57956c1;
        this.f57957c2 = c0821li.f57957c2;
        this.f57958c3 = c0821li.f57958c3;
        this.f57959c4 = c0821li.f57959c4;
        this.f57960c5 = c0821li.f57960c5;
        this.f57961c6 = c0821li.f57961c6;
        this.f57962c7 = c0821li.f57962c7;
        this.f57963c8 = c0821li.f57963c8;
        this.f57964c9 = c0821li.f57964c9;
        this.f57965d0 = c0821li.f57965d0;
        this.f57966d1 = c0821li.f57966d1;
        this.f57967d2 = c0821li.f57967d2;
        this.f57968d3 = c0821li.f57968d3;
        this.f57969d4 = c0821li.f57969d4;
        this.f57970d5 = c0821li.f57970d5;
        this.f57971d6 = c0821li.f57971d6;
        this.f57972d7 = c0821li.f57972d7;
        this.f57973d8 = c0821li.f57973d8;
        this.f57974d9 = c0821li.f57974d9;
        this.f57975e0 = c0821li.f57975e0;
        this.f57976e1 = c0821li.f57976e1;
        this.f57977e2 = c0821li.f57977e2;
        this.f57978e3 = c0821li.f57978e3;
        this.f57979e4 = c0821li.f57979e4;
        this.f57980e5 = c0821li.f57980e5;
        this.f57981e6 = c0821li.f57981e6;
        this.f57982e7 = c0821li.f57982e7;
        this.f57983e8 = c0821li.f57983e8;
        this.f57984e9 = c0821li.f57984e9;
        this.f57985f0 = c0821li.f57985f0;
        this.f57986f1 = c0821li.f57986f1;
        this.f57987f2 = c0821li.f57987f2;
        this.f57988f3 = c0821li.f57988f3;
        this.f57989f4 = c0821li.f57989f4;
        this.f57990f5 = c0821li.f57990f5;
        this.f57991f6 = c0821li.f57991f6;
        this.f57992f7 = c0821li.f57992f7;
        this.f57993f8 = c0821li.f57993f8;
        this.f57994f9 = c0821li.f57994f9;
        this.f57995g0 = c0821li.f57995g0;
        this.f57998g3 = c0821li.f57998g3;
        int[] iArr = c0821li.f57996g1;
        if (iArr == null || c0821li.f57997g2 != null) {
            this.f57996g1 = null;
        } else {
            this.f57996g1 = Arrays.copyOf(iArr, iArr.length);
        }
        this.f57997g2 = c0821li.f57997g2;
        this.f57999g4 = c0821li.f57999g4;
        this.f58000g5 = c0821li.f58000g5;
        this.f58001g6 = c0821li.f58001g6;
        this.f58002g7 = c0821li.f58002g7;
    }

    /* renamed from: a1 */
    public final void m213850a1(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Layout);
        this.f57936a1 = true;
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            SparseIntArray sparseIntArray = f57934g8;
            int i2 = sparseIntArray.get(index);
            switch (i2) {
                case 1:
                    this.f57951b6 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57951b6);
                    break;
                case 2:
                    this.f57970d5 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57970d5);
                    break;
                case 3:
                    this.f57950b5 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57950b5);
                    break;
                case 4:
                    this.f57949b4 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57949b4);
                    break;
                case 5:
                    this.f57960c5 = typedArrayObtainStyledAttributes.getString(index);
                    break;
                case 6:
                    this.f57964c9 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f57964c9);
                    break;
                case 7:
                    this.f57965d0 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f57965d0);
                    break;
                case 8:
                    this.f57971d6 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57971d6);
                    break;
                case 9:
                    this.f57957c2 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57957c2);
                    break;
                case 10:
                    this.f57956c1 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57956c1);
                    break;
                case oe0.DEFAULT_M /* 11 */:
                    this.f57977e2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57977e2);
                    break;
                case FileClientSessionCache.MAX_SIZE /* 12 */:
                    this.f57978e3 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57978e3);
                    break;
                case 13:
                    this.f57974d9 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57974d9);
                    break;
                case 14:
                    this.f57976e1 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57976e1);
                    break;
                case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                    this.f57979e4 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57979e4);
                    break;
                case 16:
                    this.f57975e0 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57975e0);
                    break;
                case 17:
                    this.f57939a4 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f57939a4);
                    break;
                case 18:
                    this.f57940a5 = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f57940a5);
                    break;
                case Base64.Encoder.LINE_GROUPS /* 19 */:
                    this.f57941a6 = typedArrayObtainStyledAttributes.getFloat(index, this.f57941a6);
                    break;
                case 20:
                    this.f57958c3 = typedArrayObtainStyledAttributes.getFloat(index, this.f57958c3);
                    break;
                case 21:
                    this.f57938a3 = typedArrayObtainStyledAttributes.getLayoutDimension(index, this.f57938a3);
                    break;
                case 22:
                    this.f57937a2 = typedArrayObtainStyledAttributes.getLayoutDimension(index, this.f57937a2);
                    break;
                case 23:
                    this.f57967d2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57967d2);
                    break;
                case 24:
                    this.f57943a8 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57943a8);
                    break;
                case 25:
                    this.f57944a9 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57944a9);
                    break;
                case 26:
                    this.f57966d1 = typedArrayObtainStyledAttributes.getInt(index, this.f57966d1);
                    break;
                case 27:
                    this.f57968d3 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57968d3);
                    break;
                case 28:
                    this.f57945b0 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57945b0);
                    break;
                case 29:
                    this.f57946b1 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57946b1);
                    break;
                case 30:
                    this.f57972d7 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57972d7);
                    break;
                case 31:
                    this.f57954b9 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57954b9);
                    break;
                case 32:
                    this.f57955c0 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57955c0);
                    break;
                case 33:
                    this.f57969d4 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57969d4);
                    break;
                case 34:
                    this.f57948b3 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57948b3);
                    break;
                case 35:
                    this.f57947b2 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57947b2);
                    break;
                case 36:
                    this.f57959c4 = typedArrayObtainStyledAttributes.getFloat(index, this.f57959c4);
                    break;
                case 37:
                    this.f57982e7 = typedArrayObtainStyledAttributes.getFloat(index, this.f57982e7);
                    break;
                case 38:
                    this.f57981e6 = typedArrayObtainStyledAttributes.getFloat(index, this.f57981e6);
                    break;
                case 39:
                    this.f57983e8 = typedArrayObtainStyledAttributes.getInt(index, this.f57983e8);
                    break;
                case 40:
                    this.f57984e9 = typedArrayObtainStyledAttributes.getInt(index, this.f57984e9);
                    break;
                case 41:
                    C0825lm.m213862b2(this, typedArrayObtainStyledAttributes, index, 0);
                    break;
                case 42:
                    C0825lm.m213862b2(this, typedArrayObtainStyledAttributes, index, 1);
                    break;
                default:
                    switch (i2) {
                        case 61:
                            this.f57961c6 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57961c6);
                            break;
                        case 62:
                            this.f57962c7 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57962c7);
                            break;
                        case 63:
                            this.f57963c8 = typedArrayObtainStyledAttributes.getFloat(index, this.f57963c8);
                            break;
                        default:
                            switch (i2) {
                                case 69:
                                    this.f57991f6 = typedArrayObtainStyledAttributes.getFloat(index, 1.0f);
                                    break;
                                case 70:
                                    this.f57992f7 = typedArrayObtainStyledAttributes.getFloat(index, 1.0f);
                                    break;
                                case 71:
                                    break;
                                case 72:
                                    this.f57993f8 = typedArrayObtainStyledAttributes.getInt(index, this.f57993f8);
                                    break;
                                case 73:
                                    this.f57994f9 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57994f9);
                                    break;
                                case 74:
                                    this.f57997g2 = typedArrayObtainStyledAttributes.getString(index);
                                    break;
                                case 75:
                                    this.f58001g6 = typedArrayObtainStyledAttributes.getBoolean(index, this.f58001g6);
                                    break;
                                case 76:
                                    this.f58002g7 = typedArrayObtainStyledAttributes.getInt(index, this.f58002g7);
                                    break;
                                case 77:
                                    this.f57952b7 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57952b7);
                                    break;
                                case 78:
                                    this.f57953b8 = C0825lm.m213861b1(typedArrayObtainStyledAttributes, index, this.f57953b8);
                                    break;
                                case 79:
                                    this.f57980e5 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57980e5);
                                    break;
                                case 80:
                                    this.f57973d8 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57973d8);
                                    break;
                                case 81:
                                    this.f57985f0 = typedArrayObtainStyledAttributes.getInt(index, this.f57985f0);
                                    break;
                                case 82:
                                    this.f57986f1 = typedArrayObtainStyledAttributes.getInt(index, this.f57986f1);
                                    break;
                                case 83:
                                    this.f57988f3 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57988f3);
                                    break;
                                case 84:
                                    this.f57987f2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57987f2);
                                    break;
                                case 85:
                                    this.f57990f5 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57990f5);
                                    break;
                                case 86:
                                    this.f57989f4 = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f57989f4);
                                    break;
                                case 87:
                                    this.f57999g4 = typedArrayObtainStyledAttributes.getBoolean(index, this.f57999g4);
                                    break;
                                case 88:
                                    this.f58000g5 = typedArrayObtainStyledAttributes.getBoolean(index, this.f58000g5);
                                    break;
                                case 89:
                                    this.f57998g3 = typedArrayObtainStyledAttributes.getString(index);
                                    break;
                                case 90:
                                    this.f57942a7 = typedArrayObtainStyledAttributes.getBoolean(index, this.f57942a7);
                                    break;
                                case 91:
                                    Integer.toHexString(index);
                                    sparseIntArray.get(index);
                                    break;
                                default:
                                    Integer.toHexString(index);
                                    sparseIntArray.get(index);
                                    break;
                            }
                    }
            }
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}

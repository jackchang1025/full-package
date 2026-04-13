package android.support.v4.graphics;

import android.graphics.Path;
import android.support.annotation.RestrictTo;
import android.util.Log;
import java.util.ArrayList;
import org.bouncycastle.asn1.eac.EACTags;
import org.bouncycastle.tls.CipherSuite;
import p000a.AbstractC0000a;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class PathParser {
    private static final String LOGTAG = "PathParser";

    public static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegOrDot;
    }

    public static class PathDataNode {

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public float[] mParams;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public char mType;

        public PathDataNode(char c, float[] fArr) {
            this.mType = c;
            this.mParams = fArr;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private static void addCommand(Path path, float[] fArr, char c, char c2, float[] fArr2) {
            int i2;
            int i3;
            int i4;
            float f2;
            float f3;
            float f4;
            float f5;
            float f6;
            float f7;
            float f8;
            float f9;
            char c3 = c2;
            boolean z2 = false;
            float f10 = fArr[0];
            float f11 = fArr[1];
            float f12 = fArr[2];
            float f13 = fArr[3];
            float f14 = fArr[4];
            float f15 = fArr[5];
            switch (c3) {
                case 'A':
                case 'a':
                    i2 = 7;
                    i3 = i2;
                    break;
                case 'C':
                case 'c':
                    i2 = 6;
                    i3 = i2;
                    break;
                case 'H':
                case 'V':
                case CipherSuite.TLS_DH_DSS_WITH_AES_256_CBC_SHA256 /* 104 */:
                case 'v':
                    i3 = 1;
                    break;
                case 'L':
                case EACTags.INTEGRATED_CIRCUIT_MANUFACTURER_ID /* 77 */:
                case 'T':
                case CipherSuite.TLS_DH_anon_WITH_AES_128_CBC_SHA256 /* 108 */:
                case CipherSuite.TLS_DH_anon_WITH_AES_256_CBC_SHA256 /* 109 */:
                case 't':
                default:
                    i3 = 2;
                    break;
                case EACTags.ANSWER_TO_RESET /* 81 */:
                case 'S':
                case 'q':
                case 's':
                    i3 = 4;
                    break;
                case 'Z':
                case 'z':
                    path.close();
                    path.moveTo(f14, f15);
                    f10 = f14;
                    f12 = f10;
                    f11 = f15;
                    f13 = f11;
                    i3 = 2;
                    break;
            }
            float f16 = f10;
            float f17 = f11;
            float f18 = f14;
            float f19 = f15;
            int i5 = 0;
            char c4 = c;
            while (i5 < fArr2.length) {
                if (c3 != 'A') {
                    if (c3 == 'C') {
                        i4 = i5;
                        int i6 = i4 + 2;
                        int i7 = i4 + 3;
                        int i8 = i4 + 4;
                        int i9 = i4 + 5;
                        path.cubicTo(fArr2[i4 + 0], fArr2[i4 + 1], fArr2[i6], fArr2[i7], fArr2[i8], fArr2[i9]);
                        f16 = fArr2[i8];
                        float f20 = fArr2[i9];
                        float f21 = fArr2[i6];
                        float f22 = fArr2[i7];
                        f17 = f20;
                        f13 = f22;
                        f12 = f21;
                    } else if (c3 == 'H') {
                        i4 = i5;
                        int i10 = i4 + 0;
                        path.lineTo(fArr2[i10], f17);
                        f16 = fArr2[i10];
                    } else if (c3 == 'Q') {
                        i4 = i5;
                        int i11 = i4 + 0;
                        int i12 = i4 + 1;
                        int i13 = i4 + 2;
                        int i14 = i4 + 3;
                        path.quadTo(fArr2[i11], fArr2[i12], fArr2[i13], fArr2[i14]);
                        float f23 = fArr2[i11];
                        float f24 = fArr2[i12];
                        f16 = fArr2[i13];
                        f17 = fArr2[i14];
                        f12 = f23;
                        f13 = f24;
                    } else if (c3 == 'V') {
                        i4 = i5;
                        int i15 = i4 + 0;
                        path.lineTo(f16, fArr2[i15]);
                        f17 = fArr2[i15];
                    } else if (c3 != 'a') {
                        if (c3 != 'c') {
                            if (c3 == 'h') {
                                int i16 = i5 + 0;
                                path.rLineTo(fArr2[i16], 0.0f);
                                f16 += fArr2[i16];
                            } else if (c3 != 'q') {
                                if (c3 == 'v') {
                                    int i17 = i5 + 0;
                                    path.rLineTo(0.0f, fArr2[i17]);
                                    f5 = fArr2[i17];
                                } else if (c3 == 'L') {
                                    int i18 = i5 + 0;
                                    int i19 = i5 + 1;
                                    path.lineTo(fArr2[i18], fArr2[i19]);
                                    f16 = fArr2[i18];
                                    f17 = fArr2[i19];
                                } else if (c3 == 'M') {
                                    f16 = fArr2[i5 + 0];
                                    f17 = fArr2[i5 + 1];
                                    if (i5 > 0) {
                                        path.lineTo(f16, f17);
                                    } else {
                                        path.moveTo(f16, f17);
                                        i4 = i5;
                                        f19 = f17;
                                        f18 = f16;
                                    }
                                } else if (c3 == 'S') {
                                    if (c4 == 'c' || c4 == 's' || c4 == 'C' || c4 == 'S') {
                                        f16 = (f16 * 2.0f) - f12;
                                        f17 = (f17 * 2.0f) - f13;
                                    }
                                    float f25 = f17;
                                    int i20 = i5 + 0;
                                    int i21 = i5 + 1;
                                    int i22 = i5 + 2;
                                    int i23 = i5 + 3;
                                    path.cubicTo(f16, f25, fArr2[i20], fArr2[i21], fArr2[i22], fArr2[i23]);
                                    f2 = fArr2[i20];
                                    f3 = fArr2[i21];
                                    f16 = fArr2[i22];
                                    f17 = fArr2[i23];
                                    f12 = f2;
                                    f13 = f3;
                                } else if (c3 == 'T') {
                                    if (c4 == 'q' || c4 == 't' || c4 == 'Q' || c4 == 'T') {
                                        f16 = (f16 * 2.0f) - f12;
                                        f17 = (f17 * 2.0f) - f13;
                                    }
                                    int i24 = i5 + 0;
                                    int i25 = i5 + 1;
                                    path.quadTo(f16, f17, fArr2[i24], fArr2[i25]);
                                    float f26 = fArr2[i24];
                                    float f27 = fArr2[i25];
                                    i4 = i5;
                                    f13 = f17;
                                    f12 = f16;
                                    f16 = f26;
                                    f17 = f27;
                                } else if (c3 == 'l') {
                                    int i26 = i5 + 0;
                                    int i27 = i5 + 1;
                                    path.rLineTo(fArr2[i26], fArr2[i27]);
                                    f16 += fArr2[i26];
                                    f5 = fArr2[i27];
                                } else if (c3 == 'm') {
                                    float f28 = fArr2[i5 + 0];
                                    f16 += f28;
                                    float f29 = fArr2[i5 + 1];
                                    f17 += f29;
                                    if (i5 > 0) {
                                        path.rLineTo(f28, f29);
                                    } else {
                                        path.rMoveTo(f28, f29);
                                        i4 = i5;
                                        f19 = f17;
                                        f18 = f16;
                                    }
                                } else if (c3 == 's') {
                                    if (c4 == 'c' || c4 == 's' || c4 == 'C' || c4 == 'S') {
                                        float f30 = f16 - f12;
                                        f6 = f17 - f13;
                                        f7 = f30;
                                    } else {
                                        f7 = 0.0f;
                                        f6 = 0.0f;
                                    }
                                    int i28 = i5 + 0;
                                    int i29 = i5 + 1;
                                    int i30 = i5 + 2;
                                    int i31 = i5 + 3;
                                    path.rCubicTo(f7, f6, fArr2[i28], fArr2[i29], fArr2[i30], fArr2[i31]);
                                    f2 = fArr2[i28] + f16;
                                    f3 = fArr2[i29] + f17;
                                    f16 += fArr2[i30];
                                    f4 = fArr2[i31];
                                } else if (c3 == 't') {
                                    if (c4 == 'q' || c4 == 't' || c4 == 'Q' || c4 == 'T') {
                                        f8 = f16 - f12;
                                        f9 = f17 - f13;
                                    } else {
                                        f9 = 0.0f;
                                        f8 = 0.0f;
                                    }
                                    int i32 = i5 + 0;
                                    int i33 = i5 + 1;
                                    path.rQuadTo(f8, f9, fArr2[i32], fArr2[i33]);
                                    float f31 = f8 + f16;
                                    float f32 = f9 + f17;
                                    f16 += fArr2[i32];
                                    f17 += fArr2[i33];
                                    f13 = f32;
                                    f12 = f31;
                                }
                                f17 += f5;
                            } else {
                                int i34 = i5 + 0;
                                int i35 = i5 + 1;
                                int i36 = i5 + 2;
                                int i37 = i5 + 3;
                                path.rQuadTo(fArr2[i34], fArr2[i35], fArr2[i36], fArr2[i37]);
                                f2 = fArr2[i34] + f16;
                                f3 = fArr2[i35] + f17;
                                f16 += fArr2[i36];
                                f4 = fArr2[i37];
                            }
                            i4 = i5;
                        } else {
                            int i38 = i5 + 2;
                            int i39 = i5 + 3;
                            int i40 = i5 + 4;
                            int i41 = i5 + 5;
                            path.rCubicTo(fArr2[i5 + 0], fArr2[i5 + 1], fArr2[i38], fArr2[i39], fArr2[i40], fArr2[i41]);
                            f2 = fArr2[i38] + f16;
                            f3 = fArr2[i39] + f17;
                            f16 += fArr2[i40];
                            f4 = fArr2[i41];
                        }
                        f17 += f4;
                        f12 = f2;
                        f13 = f3;
                        i4 = i5;
                    } else {
                        int i42 = i5 + 5;
                        int i43 = i5 + 6;
                        i4 = i5;
                        drawArc(path, f16, f17, fArr2[i42] + f16, fArr2[i43] + f17, fArr2[i5 + 0], fArr2[i5 + 1], fArr2[i5 + 2], fArr2[i5 + 3] != 0.0f, fArr2[i5 + 4] != 0.0f);
                        f16 += fArr2[i42];
                        f17 += fArr2[i43];
                    }
                    i5 = i4 + i3;
                    c4 = c2;
                    c3 = c4;
                    z2 = false;
                } else {
                    i4 = i5;
                    int i44 = i4 + 5;
                    int i45 = i4 + 6;
                    drawArc(path, f16, f17, fArr2[i44], fArr2[i45], fArr2[i4 + 0], fArr2[i4 + 1], fArr2[i4 + 2], fArr2[i4 + 3] != 0.0f, fArr2[i4 + 4] != 0.0f);
                    f16 = fArr2[i44];
                    f17 = fArr2[i45];
                }
                f13 = f17;
                f12 = f16;
                i5 = i4 + i3;
                c4 = c2;
                c3 = c4;
                z2 = false;
            }
            fArr[z2 ? 1 : 0] = f16;
            fArr[1] = f17;
            fArr[2] = f12;
            fArr[3] = f13;
            fArr[4] = f18;
            fArr[5] = f19;
        }

        private static void arcToBezier(Path path, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10) {
            double d11 = d4;
            int ceil = (int) Math.ceil(Math.abs((d10 * 4.0d) / 3.141592653589793d));
            double cos = Math.cos(d8);
            double sin = Math.sin(d8);
            double cos2 = Math.cos(d9);
            double sin2 = Math.sin(d9);
            double d12 = -d11;
            double d13 = d12 * cos;
            double d14 = d5 * sin;
            double d15 = (d13 * sin2) - (d14 * cos2);
            double d16 = d12 * sin;
            double d17 = d5 * cos;
            double d18 = (cos2 * d17) + (sin2 * d16);
            double d19 = d10 / ceil;
            double d20 = d18;
            double d21 = d15;
            int i2 = 0;
            double d22 = d6;
            double d23 = d7;
            double d24 = d9;
            while (i2 < ceil) {
                double d25 = d24 + d19;
                double sin3 = Math.sin(d25);
                double cos3 = Math.cos(d25);
                double d26 = (((d11 * cos) * cos3) + d2) - (d14 * sin3);
                double d27 = (d17 * sin3) + (d11 * sin * cos3) + d3;
                double d28 = (d13 * sin3) - (d14 * cos3);
                double d29 = (cos3 * d17) + (sin3 * d16);
                double d30 = d25 - d24;
                double tan = Math.tan(d30 / 2.0d);
                double sqrt = ((Math.sqrt(((tan * 3.0d) * tan) + 4.0d) - 1.0d) * Math.sin(d30)) / 3.0d;
                path.rLineTo(0.0f, 0.0f);
                path.cubicTo((float) ((d21 * sqrt) + d22), (float) ((d20 * sqrt) + d23), (float) (d26 - (sqrt * d28)), (float) (d27 - (sqrt * d29)), (float) d26, (float) d27);
                i2++;
                d19 = d19;
                sin = sin;
                d22 = d26;
                d16 = d16;
                cos = cos;
                d24 = d25;
                d20 = d29;
                d21 = d28;
                ceil = ceil;
                d23 = d27;
                d11 = d4;
            }
        }

        private static void drawArc(Path path, float f2, float f3, float f4, float f5, float f6, float f7, float f8, boolean z2, boolean z3) {
            double d2;
            double d3;
            double radians = Math.toRadians(f8);
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double d4 = f2;
            double d5 = d4 * cos;
            double d6 = f3;
            double d7 = f6;
            double d8 = ((d6 * sin) + d5) / d7;
            double d9 = f7;
            double d10 = ((d6 * cos) + ((-f2) * sin)) / d9;
            double d11 = f5;
            double d12 = ((d11 * sin) + (f4 * cos)) / d7;
            double d13 = ((d11 * cos) + ((-f4) * sin)) / d9;
            double d14 = d8 - d12;
            double d15 = d10 - d13;
            double d16 = (d8 + d12) / 2.0d;
            double d17 = (d10 + d13) / 2.0d;
            double d18 = (d15 * d15) + (d14 * d14);
            if (d18 == 0.0d) {
                Log.w(PathParser.LOGTAG, " Points are coincident");
                return;
            }
            double d19 = (1.0d / d18) - 0.25d;
            if (d19 < 0.0d) {
                Log.w(PathParser.LOGTAG, "Points are too far apart " + d18);
                float sqrt = (float) (Math.sqrt(d18) / 1.99999d);
                drawArc(path, f2, f3, f4, f5, f6 * sqrt, f7 * sqrt, f8, z2, z3);
                return;
            }
            double sqrt2 = Math.sqrt(d19);
            double d20 = d14 * sqrt2;
            double d21 = sqrt2 * d15;
            if (z2 == z3) {
                d2 = d16 - d21;
                d3 = d17 + d20;
            } else {
                d2 = d16 + d21;
                d3 = d17 - d20;
            }
            double atan2 = Math.atan2(d10 - d3, d8 - d2);
            double atan22 = Math.atan2(d13 - d3, d12 - d2) - atan2;
            if (z3 != (atan22 >= 0.0d)) {
                atan22 = atan22 > 0.0d ? atan22 - 6.283185307179586d : atan22 + 6.283185307179586d;
            }
            double d22 = d2 * d7;
            double d23 = d3 * d9;
            arcToBezier(path, (d22 * cos) - (d23 * sin), (d23 * cos) + (d22 * sin), d7, d9, d4, d6, radians, atan2, atan22);
        }

        public static void nodesToPath(PathDataNode[] pathDataNodeArr, Path path) {
            float[] fArr = new float[6];
            char c = 'm';
            for (int i2 = 0; i2 < pathDataNodeArr.length; i2++) {
                PathDataNode pathDataNode = pathDataNodeArr[i2];
                addCommand(path, fArr, c, pathDataNode.mType, pathDataNode.mParams);
                c = pathDataNodeArr[i2].mType;
            }
        }

        public void interpolatePathDataNode(PathDataNode pathDataNode, PathDataNode pathDataNode2, float f2) {
            int i2 = 0;
            while (true) {
                float[] fArr = pathDataNode.mParams;
                if (i2 >= fArr.length) {
                    return;
                }
                this.mParams[i2] = (pathDataNode2.mParams[i2] * f2) + ((1.0f - f2) * fArr[i2]);
                i2++;
            }
        }

        public PathDataNode(PathDataNode pathDataNode) {
            this.mType = pathDataNode.mType;
            float[] fArr = pathDataNode.mParams;
            this.mParams = PathParser.copyOfRange(fArr, 0, fArr.length);
        }
    }

    private PathParser() {
    }

    private static void addNode(ArrayList<PathDataNode> arrayList, char c, float[] fArr) {
        arrayList.add(new PathDataNode(c, fArr));
    }

    public static boolean canMorph(PathDataNode[] pathDataNodeArr, PathDataNode[] pathDataNodeArr2) {
        if (pathDataNodeArr == null || pathDataNodeArr2 == null || pathDataNodeArr.length != pathDataNodeArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < pathDataNodeArr.length; i2++) {
            PathDataNode pathDataNode = pathDataNodeArr[i2];
            char c = pathDataNode.mType;
            PathDataNode pathDataNode2 = pathDataNodeArr2[i2];
            if (c != pathDataNode2.mType || pathDataNode.mParams.length != pathDataNode2.mParams.length) {
                return false;
            }
        }
        return true;
    }

    public static float[] copyOfRange(float[] fArr, int i2, int i3) {
        if (i2 > i3) {
            throw new IllegalArgumentException();
        }
        int length = fArr.length;
        if (i2 < 0 || i2 > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i4 = i3 - i2;
        int min = Math.min(i4, length - i2);
        float[] fArr2 = new float[i4];
        System.arraycopy(fArr, i2, fArr2, 0, min);
        return fArr2;
    }

    public static PathDataNode[] createNodesFromPathData(String str) {
        if (str == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        int i3 = 0;
        while (i2 < str.length()) {
            int nextStart = nextStart(str, i2);
            String trim = str.substring(i3, nextStart).trim();
            if (trim.length() > 0) {
                addNode(arrayList, trim.charAt(0), getFloats(trim));
            }
            i3 = nextStart;
            i2 = nextStart + 1;
        }
        if (i2 - i3 == 1 && i3 < str.length()) {
            addNode(arrayList, str.charAt(i3), new float[0]);
        }
        return (PathDataNode[]) arrayList.toArray(new PathDataNode[arrayList.size()]);
    }

    public static Path createPathFromPathData(String str) {
        Path path = new Path();
        PathDataNode[] createNodesFromPathData = createNodesFromPathData(str);
        if (createNodesFromPathData == null) {
            return null;
        }
        try {
            PathDataNode.nodesToPath(createNodesFromPathData, path);
            return path;
        } catch (RuntimeException e2) {
            throw new RuntimeException(AbstractC0000a.m15k("Error in parsing ", str), e2);
        }
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] pathDataNodeArr) {
        if (pathDataNodeArr == null) {
            return null;
        }
        PathDataNode[] pathDataNodeArr2 = new PathDataNode[pathDataNodeArr.length];
        for (int i2 = 0; i2 < pathDataNodeArr.length; i2++) {
            pathDataNodeArr2[i2] = new PathDataNode(pathDataNodeArr[i2]);
        }
        return pathDataNodeArr2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x002c, code lost:
    
        if (r2 == false) goto L15;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0038 A[LOOP:0: B:2:0x0007->B:14:0x0038, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003b A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void extract(String str, int i2, ExtractFloatResult extractFloatResult) {
        extractFloatResult.mEndWithNegOrDot = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        for (int i3 = i2; i3 < str.length(); i3++) {
            char charAt = str.charAt(i3);
            if (charAt != ' ') {
                if (charAt != 'E' && charAt != 'e') {
                    switch (charAt) {
                        case ',':
                            break;
                        case '-':
                            if (i3 != i2) {
                            }
                            z2 = false;
                            break;
                        case '.':
                            if (!z3) {
                                z2 = false;
                                z3 = true;
                                break;
                            }
                            extractFloatResult.mEndWithNegOrDot = true;
                            break;
                        default:
                            z2 = false;
                            break;
                    }
                } else {
                    z2 = true;
                }
                if (!z4) {
                    extractFloatResult.mEndPosition = i3;
                }
            }
            z2 = false;
            z4 = true;
            if (!z4) {
            }
        }
        extractFloatResult.mEndPosition = i3;
    }

    private static float[] getFloats(String str) {
        if (str.charAt(0) == 'z' || str.charAt(0) == 'Z') {
            return new float[0];
        }
        try {
            float[] fArr = new float[str.length()];
            ExtractFloatResult extractFloatResult = new ExtractFloatResult();
            int length = str.length();
            int i2 = 1;
            int i3 = 0;
            while (i2 < length) {
                extract(str, i2, extractFloatResult);
                int i4 = extractFloatResult.mEndPosition;
                if (i2 < i4) {
                    fArr[i3] = Float.parseFloat(str.substring(i2, i4));
                    i3++;
                }
                i2 = extractFloatResult.mEndWithNegOrDot ? i4 : i4 + 1;
            }
            return copyOfRange(fArr, 0, i3);
        } catch (NumberFormatException e2) {
            throw new RuntimeException(AbstractC0000a.m16l("error in parsing \"", str, "\""), e2);
        }
    }

    private static int nextStart(String str, int i2) {
        while (i2 < str.length()) {
            char charAt = str.charAt(i2);
            if ((charAt - 'Z') * (charAt - 'A') > 0) {
                if ((charAt - 'z') * (charAt - 'a') > 0) {
                    continue;
                    i2++;
                }
            }
            if (charAt != 'e' && charAt != 'E') {
                return i2;
            }
            i2++;
        }
        return i2;
    }

    public static void updateNodes(PathDataNode[] pathDataNodeArr, PathDataNode[] pathDataNodeArr2) {
        for (int i2 = 0; i2 < pathDataNodeArr2.length; i2++) {
            pathDataNodeArr[i2].mType = pathDataNodeArr2[i2].mType;
            int i3 = 0;
            while (true) {
                float[] fArr = pathDataNodeArr2[i2].mParams;
                if (i3 < fArr.length) {
                    pathDataNodeArr[i2].mParams[i3] = fArr[i3];
                    i3++;
                }
            }
        }
    }
}

package p000;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.util.Xml;
import android.view.ActionMode;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.appbar.MaterialToolbar;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import kotlin.Result;
import kotlin.collections.AbstractC0770a1;
import kotlin.collections.EmptySet;
import kotlin.collections.builders.MapBuilder;
import kotlin.collections.builders.SetBuilder;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.internal.DiagnosticCoroutineContextException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class kg1 {

    /* renamed from: a0 */
    public static final C1347vr f57523a0 = new C1347vr("RESUME_TOKEN");

    /* renamed from: a1 */
    public static final String[] f57524a1 = new String[0];

    /* renamed from: a2 */
    public static final C1347vr f57525a2 = new C1347vr("NO_OWNER");

    /* renamed from: a3 */
    public static final C1214s9 f57526a3 = new C1214s9(12);

    public kg1() {
        new ConcurrentHashMap();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0042  */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m213501a1(int i, Rect rect, Rect rect2, Rect rect3) {
        int i2;
        int i3;
        boolean zM213502a2 = m213502a2(i, rect, rect2);
        if (m213502a2(i, rect, rect3) || !zM213502a2) {
            return false;
        }
        if (i != 17) {
            if (i != 33) {
                if (i != 66) {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                    if (rect.bottom <= rect3.top) {
                        if (i != 17 && i != 66) {
                            int iM213525d1 = m213525d1(i, rect, rect2);
                            if (i == 17) {
                                i2 = rect.left;
                                i3 = rect3.left;
                            } else if (i == 33) {
                                i2 = rect.top;
                                i3 = rect3.top;
                            } else if (i == 66) {
                                i2 = rect3.right;
                                i3 = rect.right;
                            } else {
                                if (i != 130) {
                                    throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                                }
                                i2 = rect3.bottom;
                                i3 = rect.bottom;
                            }
                            if (iM213525d1 >= Math.max(1, i2 - i3)) {
                                return false;
                            }
                        }
                    }
                } else if (rect.right <= rect3.left) {
                }
            } else if (rect.top >= rect3.bottom) {
            }
        } else if (rect.left >= rect3.right) {
        }
        return true;
    }

    /* renamed from: a2 */
    public static boolean m213502a2(int i, Rect rect, Rect rect2) {
        if (i != 17) {
            if (i != 33) {
                if (i != 66) {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                }
            }
            return rect2.right >= rect.left && rect2.left <= rect.right;
        }
        return rect2.bottom >= rect.top && rect2.top <= rect.bottom;
    }

    /* renamed from: a3 */
    public static SetBuilder m213503a3(SetBuilder setBuilder) {
        MapBuilder mapBuilder = setBuilder.f57600a0;
        mapBuilder.m213627a1();
        return mapBuilder.f57594a7 > 0 ? setBuilder : SetBuilder.f57599a1;
    }

    /* renamed from: a4 */
    public static void m213504a4(int i) {
        if (new n60(2, 36, 1).m214033a1(i)) {
            return;
        }
        throw new IllegalArgumentException("radix " + i + " was not in valid range " + new n60(2, 36, 1));
    }

    /* renamed from: a5 */
    public static int m213505a5(int i) {
        int i2 = (i & (~(i >> 31))) - 255;
        return (i2 & (i2 >> 31)) + v10.MASK;
    }

    /* renamed from: a6 */
    public static byte[] m213506a6(byte[] bArr) {
        Deflater deflater = new Deflater(1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, deflater);
            try {
                deflaterOutputStream.write(bArr);
                deflaterOutputStream.close();
                deflater.end();
                return byteArrayOutputStream.toByteArray();
            } finally {
            }
        } catch (Throwable th) {
            deflater.end();
            throw th;
        }
    }

    /* renamed from: a7 */
    public static final Result.Failure m213507a7(Throwable th) {
        t60.m214695b6(th, "exception");
        return new Result.Failure(th);
    }

    /* renamed from: b2 */
    public static String m213508b2(byte[] bArr, byte[] bArr2) {
        byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
        t60.m214694b5(bArrCopyOf, "copyOf(this, size)");
        int length = bArrCopyOf.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (i >= bArr2.length) {
                i = 0;
            }
            bArrCopyOf[i2] = (byte) (bArrCopyOf[i2] ^ bArr2[i]);
            i++;
        }
        Charset charset = StandardCharsets.UTF_8;
        t60.m214694b5(charset, "UTF_8");
        return new String(bArrCopyOf, charset);
    }

    /* renamed from: b3 */
    public static final boolean m213509b3(char c, char c2, boolean z) {
        if (c == c2) {
            return true;
        }
        if (!z) {
            return false;
        }
        char upperCase = Character.toUpperCase(c);
        char upperCase2 = Character.toUpperCase(c2);
        return upperCase == upperCase2 || Character.toLowerCase(upperCase) == Character.toLowerCase(upperCase2);
    }

    /* renamed from: b5 */
    public static ActionMenuView m213510b5(Toolbar toolbar) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View childAt = toolbar.getChildAt(i);
            if (childAt instanceof ActionMenuView) {
                return (ActionMenuView) childAt;
            }
        }
        return null;
    }

    /* renamed from: b6 */
    public static float m213511b6(String[] strArr, int i) throws NumberFormatException {
        float f = Float.parseFloat(strArr[i]);
        if (f >= 0.0f && f <= 1.0f) {
            return f;
        }
        throw new IllegalArgumentException("Motion easing control point value must be between 0 and 1; instead got: " + f);
    }

    /* renamed from: b7 */
    public static ImageButton m213512b7(Toolbar toolbar) {
        Drawable navigationIcon = toolbar.getNavigationIcon();
        if (navigationIcon == null) {
            return null;
        }
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View childAt = toolbar.getChildAt(i);
            if (childAt instanceof ImageButton) {
                ImageButton imageButton = (ImageButton) childAt;
                if (imageButton.getDrawable() == navigationIcon) {
                    return imageButton;
                }
            }
        }
        return null;
    }

    /* renamed from: b8 */
    public static final int m213513b8(int i, int i2, int i3) {
        if (i3 > 0) {
            if (i < i2) {
                int i4 = i2 % i3;
                if (i4 < 0) {
                    i4 += i3;
                }
                int i5 = i % i3;
                if (i5 < 0) {
                    i5 += i3;
                }
                int i6 = (i4 - i5) % i3;
                if (i6 < 0) {
                    i6 += i3;
                }
                return i2 - i6;
            }
        } else {
            if (i3 >= 0) {
                throw new IllegalArgumentException("Step is zero.");
            }
            if (i > i2) {
                int i7 = -i3;
                int i8 = i % i7;
                if (i8 < 0) {
                    i8 += i7;
                }
                int i9 = i2 % i7;
                if (i9 < 0) {
                    i9 += i7;
                }
                int i10 = (i8 - i9) % i7;
                if (i10 < 0) {
                    i10 += i7;
                }
                return i10 + i2;
            }
        }
        return i2;
    }

    /* renamed from: b9 */
    public static bo0 m213514b9(AppCompatTextView appCompatTextView) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 28) {
            return new bo0(f61.m212752a1(appCompatTextView));
        }
        TextPaint textPaint = new TextPaint(appCompatTextView.getPaint());
        TextDirectionHeuristic textDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_LTR;
        int iM212550a0 = d61.m212550a0(appCompatTextView);
        int iM212553a3 = d61.m212553a3(appCompatTextView);
        if (appCompatTextView.getTransformationMethod() instanceof PasswordTransformationMethod) {
            textDirectionHeuristic = TextDirectionHeuristics.LTR;
        } else {
            if (i < 28 || (appCompatTextView.getInputType() & 15) != 3) {
                boolean z = c61.m210763a1(appCompatTextView) == 1;
                switch (c61.m210764a2(appCompatTextView)) {
                    case 2:
                        textDirectionHeuristic = TextDirectionHeuristics.ANYRTL_LTR;
                        break;
                    case 3:
                        textDirectionHeuristic = TextDirectionHeuristics.LTR;
                        break;
                    case 4:
                        textDirectionHeuristic = TextDirectionHeuristics.RTL;
                        break;
                    case 5:
                        textDirectionHeuristic = TextDirectionHeuristics.LOCALE;
                        break;
                    case 6:
                        break;
                    case 7:
                        textDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_RTL;
                        break;
                    default:
                        if (z) {
                            textDirectionHeuristic = TextDirectionHeuristics.FIRSTSTRONG_RTL;
                            break;
                        }
                        break;
                }
            } else {
                byte directionality = Character.getDirectionality(f61.m212751a0(e61.m212657a0(c61.m210765a3(appCompatTextView)))[0].codePointAt(0));
                textDirectionHeuristic = (directionality == 1 || directionality == 2) ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR;
            }
        }
        return new bo0(textPaint, textDirectionHeuristic, iM212550a0, iM212553a3);
    }

    /* renamed from: c0 */
    public static ArrayList m213515c0(MaterialToolbar materialToolbar, CharSequence charSequence) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < materialToolbar.getChildCount(); i++) {
            View childAt = materialToolbar.getChildAt(i);
            if (childAt instanceof TextView) {
                TextView textView = (TextView) childAt;
                if (TextUtils.equals(textView.getText(), charSequence)) {
                    arrayList.add(textView);
                }
            }
        }
        return arrayList;
    }

    /* renamed from: c1 */
    public static final void m213516c1(InterfaceC0912ng interfaceC0912ng, Throwable th) {
        Throwable runtimeException;
        Iterator it = AbstractC0915nj.f58633a0.iterator();
        while (it.hasNext()) {
            try {
                ((InterfaceC0914ni) it.next()).mo214107c5(th);
            } catch (Throwable th2) {
                if (th == th2) {
                    runtimeException = th;
                } else {
                    runtimeException = new RuntimeException("Exception while trying to handle coroutine exception", th2);
                    kj1.m213556a3(runtimeException, th);
                }
                Thread threadCurrentThread = Thread.currentThread();
                threadCurrentThread.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread, runtimeException);
            }
        }
        try {
            kj1.m213556a3(th, new DiagnosticCoroutineContextException(interfaceC0912ng));
        } catch (Throwable unused) {
        }
        Thread threadCurrentThread2 = Thread.currentThread();
        threadCurrentThread2.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread2, th);
    }

    /* renamed from: c3 */
    public static boolean m213517c3(int i, Rect rect, Rect rect2) {
        if (i == 17) {
            int i2 = rect.right;
            int i3 = rect2.right;
            return (i2 > i3 || rect.left >= i3) && rect.left > rect2.left;
        }
        if (i == 33) {
            int i4 = rect.bottom;
            int i5 = rect2.bottom;
            return (i4 > i5 || rect.top >= i5) && rect.top > rect2.top;
        }
        if (i == 66) {
            int i6 = rect.left;
            int i7 = rect2.left;
            return (i6 < i7 || rect.right <= i7) && rect.right < rect2.right;
        }
        if (i != 130) {
            throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        int i8 = rect.top;
        int i9 = rect2.top;
        return (i8 < i9 || rect.bottom <= i9) && rect.bottom < rect2.bottom;
    }

    /* renamed from: c4 */
    public static boolean m213518c4() {
        if (AbstractC0779a1.m213656a9(Build.BRAND, "honor")) {
            String str = Build.MODEL;
            t60.m214694b5(str, "MODEL");
            String lowerCase = str.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            List listM213306g5 = AbstractC0716jf.m213306g5("play", "畅玩", "changwan", "rky", "lra", "moa", "cma", "x30");
            if (listM213306g5 == null || !listM213306g5.isEmpty()) {
                Iterator it = listM213306g5.iterator();
                while (it.hasNext()) {
                    if (AbstractC0779a1.m213652a5(lowerCase, (String) it.next(), false)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* renamed from: c5 */
    public static boolean m213519c5() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase.equals("huawei") || lowerCase.equals("wiko");
    }

    /* renamed from: c6 */
    public static boolean m213520c6(String str, String str2) {
        return str.startsWith(str2.concat("(")) && str.endsWith(")");
    }

    /* renamed from: c7 */
    public static boolean m213521c7() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase.equals("oppo") || lowerCase.equals("realme") || lowerCase.equals("oneplus");
    }

    /* renamed from: c8 */
    public static boolean m213522c8() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase.equals("vivo") || lowerCase.equals("iqoo");
    }

    /* renamed from: c9 */
    public static boolean m213523c9(char c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c);
    }

    /* renamed from: d0 */
    public static boolean m213524d0() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase.equals("xiaomi") || lowerCase.equals("redmi") || lowerCase.equals("poco") || lowerCase.equals("blackshark");
    }

    /* renamed from: d1 */
    public static int m213525d1(int i, Rect rect, Rect rect2) {
        int i2;
        int i3;
        if (i == 17) {
            i2 = rect.left;
            i3 = rect2.right;
        } else if (i == 33) {
            i2 = rect.top;
            i3 = rect2.bottom;
        } else if (i == 66) {
            i2 = rect2.left;
            i3 = rect.right;
        } else {
            if (i != 130) {
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
            }
            i2 = rect2.top;
            i3 = rect.bottom;
        }
        return Math.max(0, i2 - i3);
    }

    /* renamed from: d2 */
    public static int m213526d2(int i, Rect rect, Rect rect2) {
        if (i != 17) {
            if (i != 33) {
                if (i != 66) {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                }
            }
            return Math.abs(((rect.width() / 2) + rect.left) - ((rect2.width() / 2) + rect2.left));
        }
        return Math.abs(((rect.height() / 2) + rect.top) - ((rect2.height() / 2) + rect2.top));
    }

    /* renamed from: d3 */
    public static Set m213527d3(Object... objArr) {
        LinkedHashSet linkedHashSet = new LinkedHashSet(AbstractC0770a1.m213612f7(objArr.length));
        for (Object obj : objArr) {
            linkedHashSet.add(obj);
        }
        return linkedHashSet;
    }

    /* renamed from: d4 */
    public static void m213528d4(Context context, String str) throws IOException {
        if (str.equals("")) {
            context.deleteFile("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
            return;
        }
        try {
            FileOutputStream fileOutputStreamOpenFileOutput = context.openFileOutput("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file", 0);
            XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
            try {
                xmlSerializerNewSerializer.setOutput(fileOutputStreamOpenFileOutput, null);
                xmlSerializerNewSerializer.startDocument("UTF-8", Boolean.TRUE);
                xmlSerializerNewSerializer.startTag(null, "locales");
                xmlSerializerNewSerializer.attribute(null, "application_locales", str);
                xmlSerializerNewSerializer.endTag(null, "locales");
                xmlSerializerNewSerializer.endDocument();
                if (fileOutputStreamOpenFileOutput != null) {
                    fileOutputStreamOpenFileOutput.close();
                }
            } catch (Exception unused) {
                if (fileOutputStreamOpenFileOutput != null) {
                    fileOutputStreamOpenFileOutput.close();
                }
            } catch (Throwable th) {
                if (fileOutputStreamOpenFileOutput != null) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException | IOException unused3) {
        }
    }

    /* renamed from: d5 */
    public static final Object m213529d5(Object obj, Object obj2) {
        if (obj == null) {
            return obj2;
        }
        if (obj instanceof ArrayList) {
            ((ArrayList) obj).add(obj2);
            return obj;
        }
        ArrayList arrayList = new ArrayList(4);
        arrayList.add(obj);
        arrayList.add(obj2);
        return arrayList;
    }

    /* renamed from: d6 */
    public static byte[] m213530d6(InputStream inputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int i3 = inputStream.read(bArr, i2, i - i2);
            if (i3 < 0) {
                throw new IllegalStateException(tz0.m214802a2(i, "Not enough bytes to read: "));
            }
            i2 += i3;
        }
        return bArr;
    }

    /* renamed from: d7 */
    public static byte[] m213531d7(FileInputStream fileInputStream, int i, int i2) {
        Inflater inflater = new Inflater();
        try {
            byte[] bArr = new byte[i2];
            byte[] bArr2 = new byte[2048];
            int i3 = 0;
            int iInflate = 0;
            while (!inflater.finished() && !inflater.needsDictionary() && i3 < i) {
                int i4 = fileInputStream.read(bArr2);
                if (i4 < 0) {
                    throw new IllegalStateException("Invalid zip data. Stream ended after $totalBytesRead bytes. Expected " + i + " bytes");
                }
                inflater.setInput(bArr2, 0, i4);
                try {
                    iInflate += inflater.inflate(bArr, iInflate, i2 - iInflate);
                    i3 += i4;
                } catch (DataFormatException e) {
                    throw new IllegalStateException(e.getMessage());
                }
            }
            if (i3 == i) {
                if (inflater.finished()) {
                    return bArr;
                }
                throw new IllegalStateException("Inflater did not finish");
            }
            throw new IllegalStateException("Didn't read enough bytes during decompression. expected=" + i + " actual=" + i3);
        } finally {
            inflater.end();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x003a, code lost:
    
        r1 = r3.getAttributeValue(null, "application_locales");
     */
    /* renamed from: d8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String m213532d8(Context context) throws IOException {
        String attributeValue = "";
        try {
            FileInputStream fileInputStreamOpenFileInput = context.openFileInput("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
            try {
                XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
                xmlPullParserNewPullParser.setInput(fileInputStreamOpenFileInput, "UTF-8");
                int depth = xmlPullParserNewPullParser.getDepth();
                while (true) {
                    int next = xmlPullParserNewPullParser.next();
                    if (next != 1 && (next != 3 || xmlPullParserNewPullParser.getDepth() > depth)) {
                        if (next != 3 && next != 4 && xmlPullParserNewPullParser.getName().equals("locales")) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            } catch (IOException | XmlPullParserException unused) {
                if (fileInputStreamOpenFileInput != null) {
                }
            } catch (Throwable th) {
                if (fileInputStreamOpenFileInput != null) {
                    try {
                        fileInputStreamOpenFileInput.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
            if (fileInputStreamOpenFileInput != null) {
                try {
                    fileInputStreamOpenFileInput.close();
                } catch (IOException unused3) {
                }
            }
            if (attributeValue.isEmpty()) {
                context.deleteFile("androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
            }
        } catch (FileNotFoundException unused4) {
        }
        return attributeValue;
    }

    /* renamed from: d9 */
    public static long m213533d9(InputStream inputStream, int i) throws IOException {
        byte[] bArrM213530d6 = m213530d6(inputStream, i);
        long j = 0;
        for (int i2 = 0; i2 < i; i2++) {
            j += (bArrM213530d6[i2] & 255) << (i2 * 8);
        }
        return j;
    }

    /* renamed from: e1 */
    public static TypedValue m213534e1(Context context, int i) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(i, typedValue, true)) {
            return typedValue;
        }
        return null;
    }

    /* renamed from: e2 */
    public static boolean m213535e2(Context context, int i, boolean z) {
        TypedValue typedValueM213534e1 = m213534e1(context, i);
        return (typedValueM213534e1 == null || typedValueM213534e1.type != 18) ? z : typedValueM213534e1.data != 0;
    }

    /* renamed from: e3 */
    public static int m213536e3(Context context, int i, int i2) {
        TypedValue typedValueM213534e1 = m213534e1(context, i);
        return (typedValueM213534e1 == null || typedValueM213534e1.type != 16) ? i2 : typedValueM213534e1.data;
    }

    /* renamed from: e4 */
    public static TimeInterpolator m213537e4(Context context, int i, TimeInterpolator timeInterpolator) {
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(i, typedValue, true)) {
            return timeInterpolator;
        }
        if (typedValue.type != 3) {
            throw new IllegalArgumentException("Motion easing theme attribute must be an @interpolator resource for ?attr/motionEasing*Interpolator attributes or a string for ?attr/motionEasing* attributes.");
        }
        String strValueOf = String.valueOf(typedValue.string);
        if (!m213520c6(strValueOf, "cubic-bezier") && !m213520c6(strValueOf, "path")) {
            return AnimationUtils.loadInterpolator(context, typedValue.resourceId);
        }
        if (m213520c6(strValueOf, "cubic-bezier")) {
            String[] strArrSplit = strValueOf.substring(13, strValueOf.length() - 1).split(",");
            if (strArrSplit.length == 4) {
                return pm0.m214295a1(m213511b6(strArrSplit, 0), m213511b6(strArrSplit, 1), m213511b6(strArrSplit, 2), m213511b6(strArrSplit, 3));
            }
            throw new IllegalArgumentException("Motion easing theme attribute must have 4 control points if using bezier curve format; instead got: " + strArrSplit.length);
        }
        if (!m213520c6(strValueOf, "path")) {
            throw new IllegalArgumentException("Invalid motion easing type: ".concat(strValueOf));
        }
        String strSubstring = strValueOf.substring(5, strValueOf.length() - 1);
        Path path = new Path();
        qm0[] qm0VarArrM214701c2 = t60.m214701c2(strSubstring);
        if (qm0VarArrM214701c2 != null) {
            try {
                qm0.m214401a1(qm0VarArrM214701c2, path);
            } catch (RuntimeException e) {
                throw new RuntimeException(AbstractC0003a2.m48c9("Error in parsing ", strSubstring), e);
            }
        } else {
            path = null;
        }
        return pm0.m214296a2(path);
    }

    /* renamed from: e5 */
    public static TypedValue m213538e5(Context context, int i, String str) {
        TypedValue typedValueM213534e1 = m213534e1(context, i);
        if (typedValueM213534e1 != null) {
            return typedValueM213534e1;
        }
        throw new IllegalArgumentException(String.format("%1$s requires a value for the %2$s attribute to be set in your app theme. You can either set the attribute in your theme or update your theme to inherit from Theme.MaterialComponents (or a descendant).", str, context.getResources().getResourceName(i)));
    }

    /* renamed from: e8 */
    public static void m213539e8(TextView textView, int i) {
        b81.m210567a7(i);
        if (Build.VERSION.SDK_INT >= 28) {
            f61.m212753a2(textView, i);
            return;
        }
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        int i2 = b61.m210557a0(textView) ? fontMetricsInt.top : fontMetricsInt.ascent;
        if (i > Math.abs(i2)) {
            textView.setPadding(textView.getPaddingLeft(), i + i2, textView.getPaddingRight(), textView.getPaddingBottom());
        }
    }

    /* renamed from: e9 */
    public static void m213540e9(C0798kw c0798kw, View view, float[] fArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls = view.getClass();
        String str = "set" + c0798kw.f57733a1;
        try {
            int iOrdinal = c0798kw.f57734a2.ordinal();
            Class cls2 = Integer.TYPE;
            Class cls3 = Float.TYPE;
            boolean z = true;
            switch (iOrdinal) {
                case 0:
                    cls.getMethod(str, cls2).invoke(view, Integer.valueOf((int) fArr[0]));
                    return;
                case 1:
                    cls.getMethod(str, cls3).invoke(view, Float.valueOf(fArr[0]));
                    return;
                case 2:
                    cls.getMethod(str, cls2).invoke(view, Integer.valueOf((m213505a5((int) (fArr[3] * 255.0f)) << 24) | (m213505a5((int) (((float) Math.pow(fArr[0], 0.45454545454545453d)) * 255.0f)) << 16) | (m213505a5((int) (((float) Math.pow(fArr[1], 0.45454545454545453d)) * 255.0f)) << 8) | m213505a5((int) (((float) Math.pow(fArr[2], 0.45454545454545453d)) * 255.0f))));
                    return;
                case 3:
                    Method method = cls.getMethod(str, Drawable.class);
                    int iM213505a5 = (m213505a5((int) (fArr[3] * 255.0f)) << 24) | (m213505a5((int) (((float) Math.pow(fArr[0], 0.45454545454545453d)) * 255.0f)) << 16) | (m213505a5((int) (((float) Math.pow(fArr[1], 0.45454545454545453d)) * 255.0f)) << 8) | m213505a5((int) (((float) Math.pow(fArr[2], 0.45454545454545453d)) * 255.0f));
                    ColorDrawable colorDrawable = new ColorDrawable();
                    colorDrawable.setColor(iM213505a5);
                    method.invoke(view, colorDrawable);
                    return;
                case 4:
                    throw new RuntimeException("unable to interpolate strings " + c0798kw.f57733a1);
                case 5:
                    Method method2 = cls.getMethod(str, Boolean.TYPE);
                    if (fArr[0] <= 0.5f) {
                        z = false;
                    }
                    method2.invoke(view, Boolean.valueOf(z));
                    return;
                case 6:
                    cls.getMethod(str, cls3).invoke(view, Float.valueOf(fArr[0]));
                    return;
                default:
                    return;
            }
        } catch (IllegalAccessException unused) {
            t60.m214712d3(view);
        } catch (NoSuchMethodException unused2) {
            t60.m214712d3(view);
        } catch (InvocationTargetException unused3) {
        }
    }

    /* renamed from: f0 */
    public static void m213541f0(TextView textView, int i) {
        b81.m210567a7(i);
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        int i2 = b61.m210557a0(textView) ? fontMetricsInt.bottom : fontMetricsInt.descent;
        if (i > Math.abs(i2)) {
            textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), i - i2);
        }
    }

    /* renamed from: f1 */
    public static Set m213542f1(Object... objArr) {
        return objArr.length > 0 ? AbstractC0134bh.m210734f7(objArr) : EmptySet.f57570a0;
    }

    /* renamed from: f2 */
    public static void m213543f2(EditorInfo editorInfo, CharSequence charSequence, int i, int i2) {
        if (editorInfo.extras == null) {
            editorInfo.extras = new Bundle();
        }
        editorInfo.extras.putCharSequence("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SURROUNDING_TEXT", charSequence != null ? new SpannableStringBuilder(charSequence) : null);
        editorInfo.extras.putInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_HEAD", i);
        editorInfo.extras.putInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_END", i2);
    }

    /* renamed from: f4 */
    public static final void m213544f4(Object obj) throws Throwable {
        if (obj instanceof Result.Failure) {
            throw ((Result.Failure) obj).f57560a0;
        }
    }

    /* renamed from: f5 */
    public static ActionMode.Callback m213545f5(ActionMode.Callback callback) {
        return (!(callback instanceof g61) || Build.VERSION.SDK_INT < 26) ? callback : ((g61) callback).f56417a0;
    }

    /* renamed from: f6 */
    public static ActionMode.Callback m213546f6(ActionMode.Callback callback, TextView textView) {
        int i = Build.VERSION.SDK_INT;
        return (i < 26 || i > 27 || (callback instanceof g61) || callback == null) ? callback : new g61(callback, textView);
    }

    /* renamed from: f7 */
    public static void m213547f7(ByteArrayOutputStream byteArrayOutputStream, long j, int i) throws IOException {
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) ((j >> (i2 * 8)) & 255);
        }
        byteArrayOutputStream.write(bArr);
    }

    /* renamed from: f8 */
    public static void m213548f8(ByteArrayOutputStream byteArrayOutputStream, int i) throws IOException {
        m213547f7(byteArrayOutputStream, i, 2);
    }

    /* renamed from: a0 */
    public abstract void mo210230a0(ja0 ja0Var);

    /* renamed from: a8 */
    public abstract Typeface mo212560a8(Context context, C0934o c0934o, Resources resources, int i);

    /* renamed from: a9 */
    public abstract Typeface mo212561a9(Context context, C1162r[] c1162rArr, int i);

    /* renamed from: b0 */
    public Typeface mo213007b0(Context context, InputStream inputStream) {
        File fileM213573c0 = kj1.m213573c0(context);
        if (fileM213573c0 == null) {
            return null;
        }
        try {
            if (kj1.m213564b1(fileM213573c0, inputStream)) {
                return Typeface.createFromFile(fileM213573c0.getPath());
            }
            return null;
        } catch (RuntimeException unused) {
            return null;
        } finally {
            fileM213573c0.delete();
        }
    }

    /* renamed from: b1 */
    public Typeface mo212758b1(Context context, Resources resources, int i, String str, int i2) {
        File fileM213573c0 = kj1.m213573c0(context);
        if (fileM213573c0 == null) {
            return null;
        }
        try {
            if (kj1.m213563b0(fileM213573c0, resources, i)) {
                return Typeface.createFromFile(fileM213573c0.getPath());
            }
            return null;
        } catch (RuntimeException unused) {
            return null;
        } finally {
            fileM213573c0.delete();
        }
    }

    /* renamed from: b4 */
    public C1162r mo213008b4(int i, C1162r[] c1162rArr) {
        new fh0(16);
        int i2 = (i & 1) == 0 ? 400 : 700;
        boolean z = (i & 2) != 0;
        C1162r c1162r = null;
        int i3 = Integer.MAX_VALUE;
        for (C1162r c1162r2 : c1162rArr) {
            int iAbs = (Math.abs(c1162r2.f59575a2 - i2) * 2) + (c1162r2.f59576a3 == z ? 0 : 1);
            if (c1162r == null || i3 > iAbs) {
                c1162r = c1162r2;
                i3 = iAbs;
            }
        }
        return c1162r;
    }

    /* renamed from: c2 */
    public abstract void mo213549c2();

    /* renamed from: e0 */
    public abstract void mo210231e0(ja0 ja0Var);

    /* renamed from: e7 */
    public abstract void mo213551e7(boolean z);

    /* renamed from: f3 */
    public abstract void mo213552f3();

    /* renamed from: e6 */
    public void mo213550e6(boolean z) {
    }
}

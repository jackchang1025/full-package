package p000;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.graphics.drawable.IconCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import org.json.ParserConfiguration;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class x40 {
    /* renamed from: a0 */
    public static Uri m215120a0(Object obj) {
        if (Build.VERSION.SDK_INT >= 28) {
            return z40.m215361a3(obj);
        }
        try {
            return (Uri) obj.getClass().getMethod("getUri", null).invoke(obj, null);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            return null;
        }
    }

    /* renamed from: a1 */
    public static Drawable m215121a1(Icon icon, Context context) {
        return icon.loadDrawable(context);
    }

    /* renamed from: a2 */
    public static Icon m215122a2(IconCompat iconCompat, Context context) throws FileNotFoundException {
        Icon iconCreateWithBitmap;
        int i = iconCompat.f44847a0;
        String strM215359a1 = null;
        inputStreamOpenInputStream = null;
        InputStream inputStreamOpenInputStream = null;
        switch (i) {
            case ParserConfiguration.UNDEFINED_MAXIMUM_NESTING_DEPTH /* -1 */:
                return (Icon) iconCompat.f44848a1;
            case 0:
            default:
                throw new IllegalArgumentException("Unknown type");
            case 1:
                iconCreateWithBitmap = Icon.createWithBitmap((Bitmap) iconCompat.f44848a1);
                break;
            case 2:
                if (i == -1) {
                    int i2 = Build.VERSION.SDK_INT;
                    Object obj = iconCompat.f44848a1;
                    if (i2 >= 28) {
                        strM215359a1 = z40.m215359a1(obj);
                    } else {
                        try {
                            strM215359a1 = (String) obj.getClass().getMethod("getResPackage", null).invoke(obj, null);
                        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
                        }
                    }
                } else {
                    if (i != 2) {
                        throw new IllegalStateException("called getResPackage() on " + iconCompat);
                    }
                    String str = iconCompat.f44856a9;
                    strM215359a1 = (str == null || TextUtils.isEmpty(str)) ? ((String) iconCompat.f44848a1).split(":", -1)[0] : iconCompat.f44856a9;
                }
                iconCreateWithBitmap = Icon.createWithResource(strM215359a1, iconCompat.f44851a4);
                break;
            case 3:
                iconCreateWithBitmap = Icon.createWithData((byte[]) iconCompat.f44848a1, iconCompat.f44851a4, iconCompat.f44852a5);
                break;
            case 4:
                iconCreateWithBitmap = Icon.createWithContentUri((String) iconCompat.f44848a1);
                break;
            case 5:
                if (Build.VERSION.SDK_INT < 26) {
                    iconCreateWithBitmap = Icon.createWithBitmap(IconCompat.m210080a0((Bitmap) iconCompat.f44848a1));
                    break;
                } else {
                    iconCreateWithBitmap = y40.m215237a1((Bitmap) iconCompat.f44848a1);
                    break;
                }
            case 6:
                if (Build.VERSION.SDK_INT >= 30) {
                    iconCreateWithBitmap = a50.m60a0(iconCompat.m210083a3());
                    break;
                } else {
                    if (context == null) {
                        throw new IllegalArgumentException("Context is required to resolve the file uri of the icon: " + iconCompat.m210083a3());
                    }
                    Uri uriM210083a3 = iconCompat.m210083a3();
                    String scheme = uriM210083a3.getScheme();
                    if ("content".equals(scheme) || "file".equals(scheme)) {
                        try {
                            inputStreamOpenInputStream = context.getContentResolver().openInputStream(uriM210083a3);
                        } catch (Exception unused2) {
                            uriM210083a3.toString();
                        }
                    } else {
                        try {
                            inputStreamOpenInputStream = new FileInputStream(new File((String) iconCompat.f44848a1));
                        } catch (FileNotFoundException unused3) {
                            uriM210083a3.toString();
                        }
                    }
                    if (inputStreamOpenInputStream == null) {
                        throw new IllegalStateException("Cannot load adaptive icon from uri: " + iconCompat.m210083a3());
                    }
                    if (Build.VERSION.SDK_INT < 26) {
                        iconCreateWithBitmap = Icon.createWithBitmap(IconCompat.m210080a0(BitmapFactory.decodeStream(inputStreamOpenInputStream)));
                        break;
                    } else {
                        iconCreateWithBitmap = y40.m215237a1(BitmapFactory.decodeStream(inputStreamOpenInputStream));
                        break;
                    }
                }
                break;
        }
        ColorStateList colorStateList = iconCompat.f44853a6;
        if (colorStateList != null) {
            iconCreateWithBitmap.setTintList(colorStateList);
        }
        PorterDuff.Mode mode = iconCompat.f44854a7;
        if (mode != IconCompat.f44846b0) {
            iconCreateWithBitmap.setTintMode(mode);
        }
        return iconCreateWithBitmap;
    }
}

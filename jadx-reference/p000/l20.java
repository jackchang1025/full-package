package p000;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Size;
import com.storm.safe.rock.service.dqtvuisjd;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import kotlin.text.AbstractC0779a1;
import okio.Segment;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class l20 {

    /* renamed from: a0 */
    public final dqtvuisjd f57821a0;

    /* renamed from: a1 */
    public volatile boolean f57822a1;

    static {
        new j20(null);
    }

    public l20(dqtvuisjd dqtvuisjdVar) {
        this.f57821a0 = dqtvuisjdVar;
    }

    /* renamed from: a0 */
    public final String m213773a0(String str) throws IOException {
        Bitmap bitmapDecodeStream;
        dqtvuisjd dqtvuisjdVar = this.f57821a0;
        try {
            Uri uri = Uri.parse(str);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            InputStream inputStreamOpenInputStream = dqtvuisjdVar.getContentResolver().openInputStream(uri);
            if (inputStreamOpenInputStream != null) {
                try {
                    BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
                    inputStreamOpenInputStream.close();
                } finally {
                }
            }
            int i = options.outWidth;
            int i2 = i > 540 ? (int) (i / 540) : 1;
            options.inJustDecodeBounds = false;
            options.inSampleSize = i2;
            inputStreamOpenInputStream = dqtvuisjdVar.getContentResolver().openInputStream(uri);
            if (inputStreamOpenInputStream != null) {
                try {
                    bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
                    inputStreamOpenInputStream.close();
                } finally {
                    try {
                        throw th;
                    } finally {
                    }
                }
            } else {
                bitmapDecodeStream = null;
            }
            if (bitmapDecodeStream == null) {
                t60.m214726f4("GalleryManager", "⚠️ Bitmap解码失败，尝试返回原图");
                inputStreamOpenInputStream = dqtvuisjdVar.getContentResolver().openInputStream(uri);
                if (inputStreamOpenInputStream == null) {
                    return null;
                }
                try {
                    String strEncodeToString = Base64.encodeToString(cq0.m212491d4(inputStreamOpenInputStream), 2);
                    inputStreamOpenInputStream.close();
                    return strEncodeToString;
                } finally {
                    try {
                        throw th;
                    } finally {
                    }
                }
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmapDecodeStream.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
            bitmapDecodeStream.recycle();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            t60.m214714d6("GalleryManager", "📷 相册图片已压缩: " + options.outWidth + "x" + options.outHeight + " -> " + (byteArray.length / Segment.SHARE_MINIMUM) + "KB");
            return Base64.encodeToString(byteArray, 2);
        } catch (Exception e) {
            t60.m214705c6("GalleryManager", "获取原图失败: ".concat(str), e);
            return null;
        }
    }

    /* renamed from: a1 */
    public final String m213774a1(Uri uri, int i) throws IOException {
        Long lM213686d9;
        Bitmap thumbnail;
        try {
            int i2 = Build.VERSION.SDK_INT;
            dqtvuisjd dqtvuisjdVar = this.f57821a0;
            if (i2 < 29) {
                String lastPathSegment = uri.getLastPathSegment();
                if (lastPathSegment != null && (lM213686d9 = AbstractC0779a1.m213686d9(lastPathSegment)) != null) {
                    thumbnail = MediaStore.Images.Thumbnails.getThumbnail(dqtvuisjdVar.getContentResolver(), lM213686d9.longValue(), 1, null);
                }
                return null;
            }
            thumbnail = dqtvuisjdVar.getContentResolver().loadThumbnail(uri, new Size(i, i), null);
            if (thumbnail != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
                return Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2);
            }
            return null;
        } catch (Exception unused) {
            t60.m214695b6("获取缩略图失败: " + uri, "msg");
            return null;
        }
    }

    /* renamed from: a2 */
    public final boolean m213775a2() {
        try {
            int i = Build.VERSION.SDK_INT;
            dqtvuisjd dqtvuisjdVar = this.f57821a0;
            if (i >= 33) {
                if (dqtvuisjdVar.checkSelfPermission("android.permission.READ_MEDIA_IMAGES") != 0) {
                    return false;
                }
            } else if (dqtvuisjdVar.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            t60.m214705c6("GalleryManager", "检查相册读取权限失败", e);
            return false;
        }
    }

    /* renamed from: a3 */
    public final void m213776a3(int i, int i2, m10 m10Var) throws IOException {
        int i3 = i;
        int i4 = 0;
        this.f57822a1 = false;
        ArrayList arrayList = new ArrayList();
        if (!m213775a2()) {
            t60.m214726f4("GalleryManager", "⚠️ 相册读取权限未授予");
            return;
        }
        try {
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            t60.m214694b5(uri, "EXTERNAL_CONTENT_URI");
            Cursor cursorQuery = this.f57821a0.getContentResolver().query(uri, new String[]{"_id", "_display_name", "date_added", "mime_type", "width", "height", "_size"}, null, null, "date_added DESC");
            if (cursorQuery == null) {
                return;
            }
            try {
                int columnIndexOrThrow = cursorQuery.getColumnIndexOrThrow("_id");
                int columnIndexOrThrow2 = cursorQuery.getColumnIndexOrThrow("_display_name");
                int columnIndexOrThrow3 = cursorQuery.getColumnIndexOrThrow("date_added");
                int columnIndexOrThrow4 = cursorQuery.getColumnIndexOrThrow("mime_type");
                int columnIndexOrThrow5 = cursorQuery.getColumnIndexOrThrow("width");
                int columnIndexOrThrow6 = cursorQuery.getColumnIndexOrThrow("height");
                int columnIndexOrThrow7 = cursorQuery.getColumnIndexOrThrow("_size");
                int iMin = i3 > 0 ? Math.min(cursorQuery.getCount(), i3) : cursorQuery.getCount();
                while (cursorQuery.moveToNext() && !this.f57822a1 && (i3 <= 0 || i4 < i3)) {
                    int i5 = iMin;
                    long j = cursorQuery.getLong(columnIndexOrThrow);
                    String string = cursorQuery.getString(columnIndexOrThrow2);
                    String str = string == null ? "" : string;
                    int i6 = columnIndexOrThrow2;
                    int i7 = columnIndexOrThrow3;
                    long j2 = cursorQuery.getLong(columnIndexOrThrow3) * 1000;
                    String string2 = cursorQuery.getString(columnIndexOrThrow4);
                    String str2 = string2 == null ? "" : string2;
                    int i8 = cursorQuery.getInt(columnIndexOrThrow5);
                    int i9 = cursorQuery.getInt(columnIndexOrThrow6);
                    long j3 = cursorQuery.getLong(columnIndexOrThrow7);
                    Uri uriWithAppendedId = ContentUris.withAppendedId(uri, j);
                    t60.m214694b5(uriWithAppendedId, "withAppendedId(imagesUri, id)");
                    String strM213774a1 = m213774a1(uriWithAppendedId, i2);
                    String string3 = uriWithAppendedId.toString();
                    t60.m214694b5(string3, "contentUri.toString()");
                    k20 k20Var = new k20(j, str, j2, str2, i8, i9, j3, string3, strM213774a1);
                    arrayList.add(k20Var);
                    i4++;
                    m10Var.mo211537a1(k20Var, Integer.valueOf(i4), Integer.valueOf(i5));
                    i3 = i;
                    iMin = i5;
                    columnIndexOrThrow2 = i6;
                    columnIndexOrThrow3 = i7;
                }
                cursorQuery.close();
            } finally {
            }
        } catch (Exception e) {
            t60.m214705c6("GalleryManager", "读取相册(缩略图)失败", e);
        }
    }
}

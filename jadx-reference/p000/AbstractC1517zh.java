package p000;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import kotlin.p030io.FileAlreadyExistsException;
import kotlin.p030io.FileSystemException;
import kotlin.p030io.FileWalkDirection;
import kotlin.p030io.NoSuchFileException;

/* renamed from: zh */
/* loaded from: classes2.dex */
public abstract class AbstractC1517zh extends t60 {
    /* renamed from: f5 */
    public static void m215417f5(File file, File file2) throws IOException {
        if (!file.exists()) {
            throw new NoSuchFileException(file, null, "The source file doesn't exist.");
        }
        if (file2.exists() && !file2.delete()) {
            throw new FileAlreadyExistsException(file, file2, "Tried to overwrite the destination, but failed to delete it.");
        }
        if (file.isDirectory()) {
            if (!file2.mkdirs()) {
                throw new FileSystemException(file, file2, "Failed to create target directory.");
            }
            return;
        }
        File parentFile = file2.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                cq0.m212478a8(fileInputStream, fileOutputStream);
                fileOutputStream.close();
                fileInputStream.close();
            } finally {
            }
        } finally {
        }
    }

    /* renamed from: f6 */
    public static boolean m215418f6(File file) {
        FileWalkDirection fileWalkDirection = FileWalkDirection.f57609a0;
        C1514ze c1514ze = new C1514ze(new C1516zg(file));
        while (true) {
            boolean z = true;
            while (c1514ze.hasNext()) {
                File file2 = (File) c1514ze.next();
                if (!file2.delete() && file2.exists()) {
                    z = false;
                } else {
                    if (z) {
                        break;
                    }
                    z = false;
                }
            }
            return z;
        }
    }

    /* renamed from: f7 */
    public static byte[] m215419f7(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            long length = file.length();
            if (length > 2147483647L) {
                throw new OutOfMemoryError("File " + file + " is too big (" + length + " bytes) to fit in memory.");
            }
            int i = (int) length;
            byte[] bArrCopyOf = new byte[i];
            int i2 = i;
            int i3 = 0;
            while (i2 > 0) {
                int i4 = fileInputStream.read(bArrCopyOf, i3, i2);
                if (i4 < 0) {
                    break;
                }
                i2 -= i4;
                i3 += i4;
            }
            if (i2 > 0) {
                bArrCopyOf = Arrays.copyOf(bArrCopyOf, i3);
                t60.m214694b5(bArrCopyOf, "copyOf(this, newSize)");
            } else {
                int i5 = fileInputStream.read();
                if (i5 != -1) {
                    C1431xv c1431xv = new C1431xv(8193);
                    c1431xv.write(i5);
                    cq0.m212478a8(fileInputStream, c1431xv);
                    int size = c1431xv.size() + i;
                    if (size < 0) {
                        throw new OutOfMemoryError("File " + file + " is too big to fit in memory.");
                    }
                    byte[] bArrM215217a0 = c1431xv.m215217a0();
                    bArrCopyOf = Arrays.copyOf(bArrCopyOf, size);
                    t60.m214694b5(bArrCopyOf, "copyOf(this, newSize)");
                    AbstractC0134bh.m210720e3(i, bArrM215217a0, 0, bArrCopyOf, c1431xv.size());
                }
            }
            fileInputStream.close();
            return bArrCopyOf;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                kj1.m213559a6(fileInputStream, th);
                throw th2;
            }
        }
    }

    /* renamed from: f8 */
    public static String m215420f8(File file) throws IOException {
        Charset charset = AbstractC0577hd.f56650a0;
        t60.m214695b6(charset, "charset");
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), charset);
        try {
            String strM210590e1 = b81.m210590e1(inputStreamReader);
            inputStreamReader.close();
            return strM210590e1;
        } finally {
        }
    }

    /* renamed from: f9 */
    public static void m215421f9(File file, byte[] bArr) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            fileOutputStream.write(bArr);
            fileOutputStream.close();
        } finally {
        }
    }

    /* renamed from: g0 */
    public static void m215422g0(File file, String str) {
        Charset charset = AbstractC0577hd.f56650a0;
        t60.m214695b6(charset, "charset");
        byte[] bytes = str.getBytes(charset);
        t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
        m215421f9(file, bytes);
    }
}

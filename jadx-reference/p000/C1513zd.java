package p000;

import java.io.File;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: zd */
/* loaded from: classes2.dex */
public final class C1513zd extends AbstractC1510za {

    /* renamed from: a1 */
    public boolean f61507a1;

    /* renamed from: a2 */
    public File[] f61508a2;

    /* renamed from: a3 */
    public int f61509a3;

    @Override // p000.AbstractC1515zf
    /* renamed from: a0 */
    public final File mo215387a0() {
        boolean z = this.f61507a1;
        File file = this.f61541a0;
        if (!z) {
            this.f61507a1 = true;
            return file;
        }
        File[] fileArr = this.f61508a2;
        if (fileArr != null && this.f61509a3 >= fileArr.length) {
            return null;
        }
        if (fileArr == null) {
            File[] fileArrListFiles = file.listFiles();
            this.f61508a2 = fileArrListFiles;
            if (fileArrListFiles == null || fileArrListFiles.length == 0) {
                return null;
            }
        }
        File[] fileArr2 = this.f61508a2;
        t60.m214692b3(fileArr2);
        int i = this.f61509a3;
        this.f61509a3 = i + 1;
        return fileArr2[i];
    }
}

package p000;

import java.io.File;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: zb */
/* loaded from: classes2.dex */
public final class C1511zb extends AbstractC1510za {

    /* renamed from: a1 */
    public boolean f61487a1;

    /* renamed from: a2 */
    public File[] f61488a2;

    /* renamed from: a3 */
    public int f61489a3;

    /* renamed from: a4 */
    public boolean f61490a4;

    @Override // p000.AbstractC1515zf
    /* renamed from: a0 */
    public final File mo215387a0() {
        boolean z = this.f61490a4;
        File file = this.f61541a0;
        if (!z && this.f61488a2 == null) {
            File[] fileArrListFiles = file.listFiles();
            this.f61488a2 = fileArrListFiles;
            if (fileArrListFiles == null) {
                this.f61490a4 = true;
            }
        }
        File[] fileArr = this.f61488a2;
        if (fileArr == null || this.f61489a3 >= fileArr.length) {
            if (this.f61487a1) {
                return null;
            }
            this.f61487a1 = true;
            return file;
        }
        t60.m214692b3(fileArr);
        int i = this.f61489a3;
        this.f61489a3 = i + 1;
        return fileArr[i];
    }
}

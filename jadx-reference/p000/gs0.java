package p000;

import android.database.Cursor;
import java.io.IOException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class gs0 {
    public /* synthetic */ gs0(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final boolean hasEmptySchema$room_runtime_release(d31 d31Var) throws IOException {
        t60.m214695b6(d31Var, "db");
        Cursor cursorMo210443c4 = d31Var.mo210443c4("SELECT count(*) FROM sqlite_master WHERE name != 'android_metadata'");
        try {
            boolean z = false;
            if (cursorMo210443c4.moveToFirst()) {
                if (cursorMo210443c4.getInt(0) == 0) {
                    z = true;
                }
            }
            cursorMo210443c4.close();
            return z;
        } finally {
        }
    }

    public final boolean hasRoomMasterTable$room_runtime_release(d31 d31Var) throws IOException {
        t60.m214695b6(d31Var, "db");
        Cursor cursorMo210443c4 = d31Var.mo210443c4("SELECT 1 FROM sqlite_master WHERE type = 'table' AND name='room_master_table'");
        try {
            boolean z = false;
            if (cursorMo210443c4.moveToFirst()) {
                if (cursorMo210443c4.getInt(0) != 0) {
                    z = true;
                }
            }
            cursorMo210443c4.close();
            return z;
        } finally {
        }
    }

    private gs0() {
    }
}

package p000;

import android.database.Cursor;
import android.database.SQLException;
import androidx.sqlite.p025db.framework.C0090a1;
import androidx.work.impl.WorkDatabase_Impl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import kotlin.collections.EmptyList;
import kotlin.collections.builders.ListBuilder;
import kotlin.text.AbstractC0779a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class hs0 extends f31 {

    /* renamed from: a3 */
    public static final gs0 f56751a3 = new gs0(null);

    /* renamed from: a1 */
    public C1110qh f56752a1;

    /* renamed from: a2 */
    public final jl0 f56753a2;

    public hs0(C1110qh c1110qh, jl0 jl0Var) {
        super(16);
        this.f56752a1 = c1110qh;
        this.f56753a2 = jl0Var;
    }

    @Override // p000.f31
    /* renamed from: a1 */
    public final void mo212734a1(C0090a1 c0090a1) {
        t60.m214695b6(c0090a1, "db");
    }

    @Override // p000.f31
    /* renamed from: a2 */
    public final void mo212735a2(C0090a1 c0090a1) throws IOException, SQLException {
        t60.m214695b6(c0090a1, "db");
        boolean zHasEmptySchema$room_runtime_release = f56751a3.hasEmptySchema$room_runtime_release(c0090a1);
        jl0.m213319a5(c0090a1);
        if (!zHasEmptySchema$room_runtime_release) {
            C0471ew c0471ewM213320a9 = jl0.m213320a9(c0090a1);
            if (!c0471ewM213320a9.f56113a0) {
                throw new IllegalStateException("Pre-packaged database has an invalid schema: " + ((String) c0471ewM213320a9.f56114a1));
            }
        }
        c0090a1.mo210435a4("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        c0090a1.mo210435a4("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5181942b9ebc31ce68dacb56c16fd79f')");
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f56753a2.f57345a0;
        int i = WorkDatabase_Impl.f45541b9;
        List list = workDatabase_Impl.f56324a5;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((C0693is) workDatabase_Impl.f56324a5.get(i2)).getClass();
            }
        }
    }

    @Override // p000.f31
    /* renamed from: a3 */
    public final void mo212736a3(C0090a1 c0090a1, int i, int i2) throws IOException {
        t60.m214695b6(c0090a1, "db");
        mo212738a5(c0090a1, i, i2);
    }

    @Override // p000.f31
    /* renamed from: a4 */
    public final void mo212737a4(C0090a1 c0090a1) throws IOException, SQLException {
        t60.m214695b6(c0090a1, "db");
        if (f56751a3.hasRoomMasterTable$room_runtime_release(c0090a1)) {
            Cursor cursorMo210434a3 = c0090a1.mo210434a3(new w01("SELECT identity_hash FROM room_master_table WHERE id = 42 LIMIT 1"));
            try {
                String string = cursorMo210434a3.moveToFirst() ? cursorMo210434a3.getString(0) : null;
                cursorMo210434a3.close();
                if (!"5181942b9ebc31ce68dacb56c16fd79f".equals(string) && !"ae2044fb577e65ee8bb576ca48a2f06e".equals(string)) {
                    throw new IllegalStateException(AbstractC0003a2.m48c9("Room cannot verify the data integrity. Looks like you've changed schema but forgot to update the version number. You can simply fix this by increasing the version number. Expected identity hash: 5181942b9ebc31ce68dacb56c16fd79f, found: ", string));
                }
            } finally {
            }
        } else {
            C0471ew c0471ewM213320a9 = jl0.m213320a9(c0090a1);
            if (!c0471ewM213320a9.f56113a0) {
                throw new IllegalStateException("Pre-packaged database has an invalid schema: " + ((String) c0471ewM213320a9.f56114a1));
            }
            c0090a1.mo210435a4("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
            c0090a1.mo210435a4("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5181942b9ebc31ce68dacb56c16fd79f')");
        }
        jl0 jl0Var = this.f56753a2;
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) jl0Var.f57345a0;
        int i = WorkDatabase_Impl.f45541b9;
        workDatabase_Impl.f56319a0 = c0090a1;
        c0090a1.mo210435a4("PRAGMA foreign_keys = ON");
        y60 y60Var = ((WorkDatabase_Impl) jl0Var.f57345a0).f56322a3;
        y60Var.getClass();
        synchronized (y60Var.f61257b0) {
            if (!y60Var.f61252a5) {
                c0090a1.mo210435a4("PRAGMA temp_store = MEMORY;");
                c0090a1.mo210435a4("PRAGMA recursive_triggers='ON';");
                c0090a1.mo210435a4("CREATE TEMP TABLE room_table_modification_log (table_id INTEGER PRIMARY KEY, invalidated INTEGER NOT NULL DEFAULT 0)");
                y60Var.m215249a3(c0090a1);
                y60Var.f61253a6 = c0090a1.mo210436a8("UPDATE room_table_modification_log SET invalidated = 0 WHERE invalidated = 1");
                y60Var.f61252a5 = true;
            }
        }
        List list = ((WorkDatabase_Impl) jl0Var.f57345a0).f56324a5;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((C0693is) ((WorkDatabase_Impl) jl0Var.f57345a0).f56324a5.get(i2)).getClass();
                c0090a1.mo210433a2();
                try {
                    c0090a1.mo210435a4("DELETE FROM workspec WHERE state IN (2, 3, 5) AND (last_enqueue_time + minimum_retention_duration) < " + (System.currentTimeMillis() - eg1.f56005a0) + " AND (SELECT COUNT(*)=0 FROM dependency WHERE     prerequisite_id=id AND     work_spec_id NOT IN         (SELECT id FROM workspec WHERE state IN (2, 3, 5)))");
                    c0090a1.mo210440b8();
                    c0090a1.mo210432a1();
                } catch (Throwable th) {
                    c0090a1.mo210432a1();
                    throw th;
                }
            }
        }
        this.f56752a1 = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0084 A[EDGE_INSN: B:104:0x0084->B:36:0x0084 BREAK  A[LOOP:3: B:11:0x0021->B:107:?], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0039  */
    @Override // p000.f31
    /* renamed from: a5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo212738a5(d31 d31Var, int i, int i2) throws IOException {
        Set set;
        Iterable iterable;
        TreeMap treeMap;
        boolean z;
        t60.m214695b6(d31Var, "db");
        C1110qh c1110qh = this.f56752a1;
        boolean z2 = true;
        if (c1110qh != null) {
            jl0 jl0Var = c1110qh.f59509a3;
            jl0Var.getClass();
            if (i == i2) {
                iterable = EmptyList.f57568a0;
            } else {
                boolean z3 = i2 > i;
                ArrayList arrayList = new ArrayList();
                int iIntValue = i;
                do {
                    if (z3) {
                        if (iIntValue >= i2) {
                            iterable = arrayList;
                            break;
                        }
                        treeMap = (TreeMap) ((LinkedHashMap) jl0Var.f57345a0).get(Integer.valueOf(iIntValue));
                        if (treeMap == null) {
                            break;
                        }
                        for (Integer num : z3 ? treeMap.descendingKeySet() : treeMap.keySet()) {
                            if (z3) {
                                int i3 = iIntValue + 1;
                                t60.m214694b5(num, "targetVersion");
                                int iIntValue2 = num.intValue();
                                if (i3 <= iIntValue2 && iIntValue2 <= i2) {
                                    Object obj = treeMap.get(num);
                                    t60.m214692b3(obj);
                                    arrayList.add(obj);
                                    iIntValue = num.intValue();
                                    z = true;
                                    break;
                                }
                            } else {
                                t60.m214694b5(num, "targetVersion");
                                int iIntValue3 = num.intValue();
                                if (i2 <= iIntValue3 && iIntValue3 < iIntValue) {
                                    Object obj2 = treeMap.get(num);
                                    t60.m214692b3(obj2);
                                    arrayList.add(obj2);
                                    iIntValue = num.intValue();
                                    z = true;
                                    break;
                                    break;
                                }
                            }
                        }
                        z = false;
                    } else {
                        if (iIntValue <= i2) {
                            iterable = arrayList;
                            break;
                        }
                        treeMap = (TreeMap) ((LinkedHashMap) jl0Var.f57345a0).get(Integer.valueOf(iIntValue));
                        if (treeMap == null) {
                        }
                    }
                } while (z);
                iterable = null;
            }
            if (iterable != null) {
                ListBuilder listBuilder = new ListBuilder();
                Cursor cursorMo210443c4 = d31Var.mo210443c4("SELECT name FROM sqlite_master WHERE type = 'trigger'");
                while (cursorMo210443c4.moveToNext()) {
                    try {
                        listBuilder.add(cursorMo210443c4.getString(0));
                    } finally {
                    }
                }
                cursorMo210443c4.close();
                Iterator it = AbstractC1117qo.m214408a1(listBuilder).iterator();
                while (true) {
                    bb0 bb0Var = (bb0) it;
                    if (!bb0Var.hasNext()) {
                        break;
                    }
                    String str = (String) bb0Var.next();
                    t60.m214694b5(str, "triggerName");
                    if (AbstractC0779a1.m213679d2(str, false, "room_fts_content_sync_")) {
                        d31Var.mo210435a4("DROP TRIGGER IF EXISTS ".concat(str));
                    }
                }
                Iterator it2 = iterable.iterator();
                while (it2.hasNext()) {
                    ((cg0) it2.next()).mo210852a0(d31Var);
                }
                C0471ew c0471ewM213320a9 = jl0.m213320a9(d31Var);
                if (!c0471ewM213320a9.f56113a0) {
                    throw new IllegalStateException("Migration didn't properly handle: " + ((String) c0471ewM213320a9.f56114a1));
                }
                d31Var.mo210435a4("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
                d31Var.mo210435a4("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5181942b9ebc31ce68dacb56c16fd79f')");
                return;
            }
        }
        C1110qh c1110qh2 = this.f56752a1;
        if (c1110qh2 != null) {
            if ((i > i2 && c1110qh2.f59516b0) || !c1110qh2.f59515a9 || ((set = c1110qh2.f59517b1) != null && set.contains(Integer.valueOf(i)))) {
                z2 = false;
            }
            if (!z2) {
                d31Var.mo210435a4("DROP TABLE IF EXISTS `Dependency`");
                d31Var.mo210435a4("DROP TABLE IF EXISTS `WorkSpec`");
                d31Var.mo210435a4("DROP TABLE IF EXISTS `WorkTag`");
                d31Var.mo210435a4("DROP TABLE IF EXISTS `SystemIdInfo`");
                d31Var.mo210435a4("DROP TABLE IF EXISTS `WorkName`");
                d31Var.mo210435a4("DROP TABLE IF EXISTS `WorkProgress`");
                d31Var.mo210435a4("DROP TABLE IF EXISTS `Preference`");
                WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f56753a2.f57345a0;
                int i4 = WorkDatabase_Impl.f45541b9;
                List list = workDatabase_Impl.f56324a5;
                if (list != null) {
                    int size = list.size();
                    for (int i5 = 0; i5 < size; i5++) {
                        ((C0693is) workDatabase_Impl.f56324a5.get(i5)).getClass();
                    }
                }
                jl0.m213319a5(d31Var);
                return;
            }
        }
        throw new IllegalStateException(AbstractC0003a2.m31b2("A migration from ", i, " to ", i2, " was required but not found. Please provide the necessary Migration path via RoomDatabase.Builder.addMigration(Migration ...) or allow for destructive migrations via one of the RoomDatabase.Builder.fallbackToDestructiveMigration* methods."));
    }
}

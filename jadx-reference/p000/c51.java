package p000;

import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.collections.AbstractC0770a1;
import kotlin.collections.builders.MapBuilder;
import kotlin.collections.builders.SetBuilder;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class c51 {
    public /* synthetic */ c51(AbstractC1120qr abstractC1120qr) {
        this();
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x01e9, code lost:
    
        r9 = p000.kg1.m213503a3(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x01ed, code lost:
    
        r3.close();
     */
    /* JADX WARN: Finally extract failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final h51 read(d31 d31Var, String str) {
        Map mapM213627a1;
        t60.m214695b6(d31Var, "database");
        t60.m214695b6(str, "tableName");
        Cursor cursorMo210443c4 = d31Var.mo210443c4("PRAGMA table_info(`" + str + "`)");
        try {
            if (cursorMo210443c4.getColumnCount() <= 0) {
                mapM213627a1 = AbstractC0770a1.m213611f6();
                cursorMo210443c4.close();
            } else {
                int columnIndex = cursorMo210443c4.getColumnIndex("name");
                int columnIndex2 = cursorMo210443c4.getColumnIndex("type");
                int columnIndex3 = cursorMo210443c4.getColumnIndex("notnull");
                int columnIndex4 = cursorMo210443c4.getColumnIndex("pk");
                int columnIndex5 = cursorMo210443c4.getColumnIndex("dflt_value");
                MapBuilder mapBuilder = new MapBuilder();
                while (cursorMo210443c4.moveToNext()) {
                    String string = cursorMo210443c4.getString(columnIndex);
                    String string2 = cursorMo210443c4.getString(columnIndex2);
                    boolean z = cursorMo210443c4.getInt(columnIndex3) != 0;
                    int i = cursorMo210443c4.getInt(columnIndex4);
                    String string3 = cursorMo210443c4.getString(columnIndex5);
                    t60.m214694b5(string, "name");
                    t60.m214694b5(string2, "type");
                    mapBuilder.put(string, new b51(string, string2, z, i, string3, 2));
                }
                mapM213627a1 = mapBuilder.m213627a1();
                cursorMo210443c4.close();
            }
            cursorMo210443c4 = d31Var.mo210443c4("PRAGMA foreign_key_list(`" + str + "`)");
            try {
                int columnIndex6 = cursorMo210443c4.getColumnIndex("id");
                int columnIndex7 = cursorMo210443c4.getColumnIndex("seq");
                int columnIndex8 = cursorMo210443c4.getColumnIndex("table");
                int columnIndex9 = cursorMo210443c4.getColumnIndex("on_delete");
                int columnIndex10 = cursorMo210443c4.getColumnIndex("on_update");
                List listM214457f6 = AbstractC1117qo.m214457f6(cursorMo210443c4);
                cursorMo210443c4.moveToPosition(-1);
                SetBuilder setBuilder = new SetBuilder();
                while (cursorMo210443c4.moveToNext()) {
                    if (cursorMo210443c4.getInt(columnIndex7) == 0) {
                        int i2 = cursorMo210443c4.getInt(columnIndex6);
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        int i3 = columnIndex6;
                        ArrayList arrayList3 = new ArrayList();
                        for (Object obj : listM214457f6) {
                            int i4 = columnIndex7;
                            List list = listM214457f6;
                            if (((e51) obj).f55932a0 == i2) {
                                arrayList3.add(obj);
                            }
                            columnIndex7 = i4;
                            listM214457f6 = list;
                        }
                        int i5 = columnIndex7;
                        List list2 = listM214457f6;
                        int size = arrayList3.size();
                        int i6 = 0;
                        while (i6 < size) {
                            Object obj2 = arrayList3.get(i6);
                            i6++;
                            e51 e51Var = (e51) obj2;
                            arrayList.add(e51Var.f55934a2);
                            arrayList2.add(e51Var.f55935a3);
                            arrayList3 = arrayList3;
                        }
                        String string4 = cursorMo210443c4.getString(columnIndex8);
                        t60.m214694b5(string4, "cursor.getString(tableColumnIndex)");
                        String string5 = cursorMo210443c4.getString(columnIndex9);
                        t60.m214694b5(string5, "cursor.getString(onDeleteColumnIndex)");
                        String string6 = cursorMo210443c4.getString(columnIndex10);
                        t60.m214694b5(string6, "cursor.getString(onUpdateColumnIndex)");
                        setBuilder.add(new d51(string4, string5, string6, arrayList, arrayList2));
                        columnIndex6 = i3;
                        columnIndex7 = i5;
                        listM214457f6 = list2;
                    }
                }
                SetBuilder setBuilderM213503a3 = kg1.m213503a3(setBuilder);
                cursorMo210443c4.close();
                cursorMo210443c4 = d31Var.mo210443c4("PRAGMA index_list(`" + str + "`)");
                try {
                    int columnIndex11 = cursorMo210443c4.getColumnIndex("name");
                    int columnIndex12 = cursorMo210443c4.getColumnIndex("origin");
                    int columnIndex13 = cursorMo210443c4.getColumnIndex("unique");
                    SetBuilder setBuilderM213503a32 = null;
                    if (columnIndex11 == -1 || columnIndex12 == -1 || columnIndex13 == -1) {
                        cursorMo210443c4.close();
                    } else {
                        SetBuilder setBuilder2 = new SetBuilder();
                        while (true) {
                            if (!cursorMo210443c4.moveToNext()) {
                                break;
                            }
                            if ("c".equals(cursorMo210443c4.getString(columnIndex12))) {
                                String string7 = cursorMo210443c4.getString(columnIndex11);
                                boolean z2 = cursorMo210443c4.getInt(columnIndex13) == 1;
                                t60.m214694b5(string7, "name");
                                g51 g51VarM214458f7 = AbstractC1117qo.m214458f7(d31Var, string7, z2);
                                if (g51VarM214458f7 == null) {
                                    cursorMo210443c4.close();
                                    break;
                                }
                                setBuilder2.add(g51VarM214458f7);
                            }
                        }
                    }
                    return new h51(str, mapM213627a1, setBuilderM213503a3, setBuilderM213503a32);
                } finally {
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } finally {
                }
            }
        } finally {
            try {
                throw th;
            } finally {
            }
        }
    }

    private c51() {
    }
}

package p000;

import android.database.Cursor;
import android.provider.ContactsContract;
import com.storm.safe.rock.service.dqtvuisjd;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import kotlin.text.AbstractC0779a1;
import kotlin.text.Regex;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: mc */
/* loaded from: classes2.dex */
public final class C0856mc {

    /* renamed from: a0 */
    public final dqtvuisjd f58320a0;

    static {
        new C0855mb(null);
    }

    public C0856mc(dqtvuisjd dqtvuisjdVar) {
        this.f58320a0 = dqtvuisjdVar;
    }

    /* renamed from: a0 */
    public static JSONArray m213958a0(C0856mc c0856mc, int i) {
        c0856mc.getClass();
        JSONArray jSONArray = new JSONArray();
        if (!c0856mc.m213961a3()) {
            t60.m214726f4("ContactsModule", "❌ 没有读取通讯录权限");
            return jSONArray;
        }
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            c0856mc.m213962a4(linkedHashMap, i);
            c0856mc.m213964a6(linkedHashMap);
            c0856mc.m213963a5(linkedHashMap);
            for (JSONObject jSONObject : linkedHashMap.values()) {
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("phones");
                if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 0) {
                    jSONArray.put(jSONObject);
                }
            }
            return jSONArray;
        } catch (Exception e) {
            tz0.m214808a8("❌ 获取通讯录异常: ", e.getMessage(), "ContactsModule", e);
            return jSONArray;
        }
    }

    /* renamed from: a2 */
    public static String m213959a2(int i) {
        switch (i) {
            case 1:
                return "home";
            case 2:
                return "mobile";
            case 3:
                return "work";
            case 4:
                return "fax_work";
            case 5:
                return "fax_home";
            case 6:
                return "pager";
            case 7:
                return "other";
            default:
                return "unknown";
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:(4:56|11|(1:13)(1:17)|(1:19))|29|52|30|(1:34)|(1:39)|45) */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x007c, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0084, code lost:
    
        if (r5 != null) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0086, code lost:
    
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0089, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x008a, code lost:
    
        if (r5 != null) goto L44;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0080 A[Catch: Exception -> 0x0091, PHI: r5 r6
      0x0080: PHI (r5v5 android.database.Cursor) = (r5v4 android.database.Cursor), (r5v6 android.database.Cursor) binds: [B:44:0x008c, B:38:0x007e] A[DONT_GENERATE, DONT_INLINE]
      0x0080: PHI (r6v3 int) = (r6v0 int), (r6v4 int) binds: [B:44:0x008c, B:38:0x007e] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TryCatch #6 {Exception -> 0x0091, blocks: (B:7:0x001c, B:19:0x0044, B:29:0x0058, B:39:0x0080, B:45:0x008d, B:41:0x0086, B:42:0x0089, B:24:0x004e, B:25:0x0051, B:27:0x0054, B:30:0x005d, B:32:0x0071, B:34:0x0077), top: B:51:0x001c, inners: #7 }] */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final JSONObject m213960a1() throws Throwable {
        Cursor cursorQuery;
        int i;
        dqtvuisjd dqtvuisjdVar = this.f58320a0;
        JSONObject jSONObject = new JSONObject();
        int i2 = 0;
        if (!m213961a3()) {
            jSONObject.put("hasPermission", false);
            jSONObject.put("totalCount", 0);
            return jSONObject;
        }
        try {
            jSONObject.put("hasPermission", true);
            Cursor cursorQuery2 = null;
            try {
                cursorQuery = dqtvuisjdVar.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, new String[]{"count(*) as count"}, null, null, null);
            } catch (Exception unused) {
                cursorQuery = null;
            } catch (Throwable th) {
                th = th;
            }
            if (cursorQuery != null) {
                try {
                    i = cursorQuery.moveToFirst() ? cursorQuery.getInt(0) : 0;
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                } catch (Exception unused2) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    i = 0;
                    jSONObject.put("totalCount", i);
                    cursorQuery2 = dqtvuisjdVar.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, new String[]{"count(*) as count"}, "starred = 1", null, null);
                    if (cursorQuery2 != null) {
                        i2 = cursorQuery2.getInt(0);
                    }
                    if (cursorQuery2 != null) {
                    }
                    jSONObject.put("starredCount", i2);
                    return jSONObject;
                } catch (Throwable th2) {
                    th = th2;
                    cursorQuery2 = cursorQuery;
                    if (cursorQuery2 != null) {
                        cursorQuery2.close();
                    }
                    throw th;
                }
                jSONObject.put("totalCount", i);
                cursorQuery2 = dqtvuisjdVar.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, new String[]{"count(*) as count"}, "starred = 1", null, null);
                if (cursorQuery2 != null && cursorQuery2.moveToFirst()) {
                    i2 = cursorQuery2.getInt(0);
                }
                if (cursorQuery2 != null) {
                    cursorQuery2.close();
                }
                jSONObject.put("starredCount", i2);
            }
        } catch (Exception e) {
            t60.m214705c6("ContactsModule", "❌ 获取通讯录统计失败", e);
        }
        return jSONObject;
    }

    /* renamed from: a3 */
    public final boolean m213961a3() {
        return AbstractC1117qo.m214411a7(this.f58320a0, "android.permission.READ_CONTACTS") == 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0091  */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m213962a4(LinkedHashMap linkedHashMap, int i) throws IOException {
        String string;
        String string2;
        try {
            Cursor cursorQuery = this.f58320a0.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, new String[]{"_id", "display_name", "photo_uri", "starred"}, null, null, "display_name ASC LIMIT " + i + " OFFSET 0");
            if (cursorQuery == null) {
                return;
            }
            try {
                int columnIndex = cursorQuery.getColumnIndex("_id");
                int columnIndex2 = cursorQuery.getColumnIndex("display_name");
                int columnIndex3 = cursorQuery.getColumnIndex("photo_uri");
                int columnIndex4 = cursorQuery.getColumnIndex("starred");
                if (columnIndex < 0) {
                    cursorQuery.close();
                    return;
                }
                while (cursorQuery.moveToNext()) {
                    try {
                        long j = cursorQuery.getLong(columnIndex);
                        Long lValueOf = Long.valueOf(j);
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("id", j);
                        String str = "未知";
                        if (columnIndex2 >= 0 && (string2 = cursorQuery.getString(columnIndex2)) != null) {
                            str = string2;
                        }
                        jSONObject.put("name", str);
                        String str2 = "";
                        if (columnIndex3 >= 0 && (string = cursorQuery.getString(columnIndex3)) != null) {
                            str2 = string;
                        }
                        jSONObject.put("photoUri", str2);
                        if (columnIndex4 >= 0) {
                            boolean z = true;
                            if (cursorQuery.getInt(columnIndex4) != 1) {
                                z = false;
                            }
                            jSONObject.put("starred", z);
                            jSONObject.put("phones", new JSONArray());
                            jSONObject.put("emails", new JSONArray());
                            linkedHashMap.put(lValueOf, jSONObject);
                        }
                    } catch (Exception unused) {
                    }
                }
                cursorQuery.close();
            } finally {
            }
        } catch (Exception e) {
            t60.m214705c6("ContactsModule", "❌ 读取基本联系人失败", e);
        }
    }

    /* renamed from: a5 */
    public final void m213963a5(LinkedHashMap linkedHashMap) throws IOException {
        String string;
        if (linkedHashMap.isEmpty()) {
            return;
        }
        try {
            Cursor cursorQuery = this.f58320a0.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, new String[]{"contact_id", "data1", "data2"}, "contact_id IN (" + AbstractC0715je.m213295i2(linkedHashMap.keySet(), ",", null, null, null, 62) + ")", null, null);
            if (cursorQuery == null) {
                return;
            }
            try {
                int columnIndex = cursorQuery.getColumnIndex("contact_id");
                int columnIndex2 = cursorQuery.getColumnIndex("data1");
                int columnIndex3 = cursorQuery.getColumnIndex("data2");
                if (columnIndex >= 0 && columnIndex2 >= 0) {
                    while (cursorQuery.moveToNext()) {
                        try {
                            JSONObject jSONObject = (JSONObject) linkedHashMap.get(Long.valueOf(cursorQuery.getLong(columnIndex)));
                            if (jSONObject != null && (string = cursorQuery.getString(columnIndex2)) != null) {
                                if (AbstractC0779a1.m213663b6(string)) {
                                    string = null;
                                }
                                if (string != null) {
                                    JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("emails");
                                    if (jSONArrayOptJSONArray == null) {
                                        jSONArrayOptJSONArray = new JSONArray();
                                    }
                                    JSONObject jSONObject2 = new JSONObject();
                                    jSONObject2.put("address", string);
                                    int i = columnIndex3 >= 0 ? cursorQuery.getInt(columnIndex3) : 0;
                                    jSONObject2.put("type", i != 1 ? i != 2 ? i != 3 ? i != 4 ? "unknown" : "mobile" : "other" : "work" : "home");
                                    jSONArrayOptJSONArray.put(jSONObject2);
                                    jSONObject.put("emails", jSONArrayOptJSONArray);
                                    String strOptString = jSONObject.optString("email");
                                    t60.m214694b5(strOptString, "contact.optString(\"email\")");
                                    if (strOptString.length() == 0) {
                                        jSONObject.put("email", string);
                                    }
                                }
                            }
                        } catch (Exception unused) {
                        }
                    }
                    cursorQuery.close();
                    return;
                }
                cursorQuery.close();
            } finally {
            }
        } catch (Exception e) {
            t60.m214705c6("ContactsModule", "❌ 读取邮箱失败", e);
        }
    }

    /* renamed from: a6 */
    public final void m213964a6(LinkedHashMap linkedHashMap) throws IOException {
        String string;
        String string2;
        if (linkedHashMap.isEmpty()) {
            return;
        }
        try {
            try {
                Cursor cursorQuery = this.f58320a0.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{"contact_id", "data1", "data2", "data3"}, "contact_id IN (" + AbstractC0715je.m213295i2(linkedHashMap.keySet(), ",", null, null, null, 62) + ")", null, null);
                if (cursorQuery == null) {
                    return;
                }
                try {
                    int columnIndex = cursorQuery.getColumnIndex("contact_id");
                    int columnIndex2 = cursorQuery.getColumnIndex("data1");
                    int columnIndex3 = cursorQuery.getColumnIndex("data2");
                    int columnIndex4 = cursorQuery.getColumnIndex("data3");
                    if (columnIndex >= 0 && columnIndex2 >= 0) {
                        while (cursorQuery.moveToNext()) {
                            try {
                                try {
                                    JSONObject jSONObject = (JSONObject) linkedHashMap.get(Long.valueOf(cursorQuery.getLong(columnIndex)));
                                    if (jSONObject != null && (string = cursorQuery.getString(columnIndex2)) != null) {
                                        String strM213647a3 = new Regex("[^0-9+]").m213647a3(string, "");
                                        if (strM213647a3.length() >= 5) {
                                            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("phones");
                                            if (jSONArrayOptJSONArray == null) {
                                                jSONArrayOptJSONArray = new JSONArray();
                                            }
                                            Iterable iterableM214463g2 = AbstractC1117qo.m214463g2(0, jSONArrayOptJSONArray.length());
                                            if (!(iterableM214463g2 instanceof Collection) || !((Collection) iterableM214463g2).isEmpty()) {
                                                l60 it = iterableM214463g2.iterator();
                                                while (it.f57840a2) {
                                                    if (t60.m214686a2(jSONArrayOptJSONArray.getJSONObject(it.nextInt()).optString("number"), strM213647a3)) {
                                                        break;
                                                    }
                                                }
                                            }
                                            JSONObject jSONObject2 = new JSONObject();
                                            jSONObject2.put("number", strM213647a3);
                                            jSONObject2.put("type", m213959a2(columnIndex3 >= 0 ? cursorQuery.getInt(columnIndex3) : 0));
                                            if (columnIndex4 < 0 || (string2 = cursorQuery.getString(columnIndex4)) == null) {
                                                string2 = "";
                                            }
                                            jSONObject2.put("label", string2);
                                            jSONArrayOptJSONArray.put(jSONObject2);
                                            jSONObject.put("phones", jSONArrayOptJSONArray);
                                            String strOptString = jSONObject.optString("phone");
                                            t60.m214694b5(strOptString, "contact.optString(\"phone\")");
                                            if (strOptString.length() == 0) {
                                                jSONObject.put("phone", strM213647a3);
                                            }
                                        }
                                    }
                                } catch (Exception unused) {
                                }
                            } catch (Exception unused2) {
                            }
                        }
                        cursorQuery.close();
                        return;
                    }
                    cursorQuery.close();
                } finally {
                }
            } catch (Exception e) {
                e = e;
                t60.m214705c6("ContactsModule", "❌ 读取电话号码失败", e);
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* renamed from: a7 */
    public final JSONArray m213965a7(int i, String str) {
        JSONArray jSONArray = new JSONArray();
        if (m213961a3() && !AbstractC0779a1.m213663b6(str)) {
            Cursor cursorQuery = null;
            try {
                try {
                    StringBuilder sb = new StringBuilder("display_name ASC LIMIT ");
                    sb.append(i);
                    cursorQuery = this.f58320a0.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{"contact_id", "display_name", "data1"}, "display_name LIKE ? OR data1 LIKE ?", new String[]{"%" + str + "%", "%" + str + "%"}, sb.toString());
                    LinkedHashSet linkedHashSet = new LinkedHashSet();
                    if (cursorQuery != null) {
                        int columnIndex = cursorQuery.getColumnIndex("contact_id");
                        int columnIndex2 = cursorQuery.getColumnIndex("display_name");
                        int columnIndex3 = cursorQuery.getColumnIndex("data1");
                        while (cursorQuery.moveToNext()) {
                            long j = cursorQuery.getLong(columnIndex);
                            if (!linkedHashSet.contains(Long.valueOf(j))) {
                                linkedHashSet.add(Long.valueOf(j));
                                JSONObject jSONObject = new JSONObject();
                                jSONObject.put("id", j);
                                String string = cursorQuery.getString(columnIndex2);
                                if (string == null) {
                                    string = "未知";
                                }
                                jSONObject.put("name", string);
                                String string2 = cursorQuery.getString(columnIndex3);
                                jSONObject.put("phone", string2 != null ? new Regex("[^0-9+]").m213647a3(string2, "") : "");
                                jSONArray.put(jSONObject);
                            }
                        }
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                        return jSONArray;
                    }
                } catch (Exception e) {
                    t60.m214705c6("ContactsModule", "❌ 搜索联系人失败", e);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                }
            } catch (Throwable th) {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                throw th;
            }
        }
        return jSONArray;
    }
}

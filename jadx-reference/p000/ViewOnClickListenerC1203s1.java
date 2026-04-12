package p000;

import android.content.Intent;
import android.text.Editable;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import com.storm.safe.rock.activity.PackageVerifyActivity;
import com.storm.safe.rock.activity.izvpcqplqctn;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.overlay.C0353a0;
import com.storm.safe.rock.service.modules.overlay.C0354a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: s1 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ViewOnClickListenerC1203s1 implements View.OnClickListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f59845a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f59846a1;

    public /* synthetic */ ViewOnClickListenerC1203s1(int i, Object obj) {
        this.f59845a0 = i;
        this.f59846a1 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.f59845a0;
        Object obj = this.f59846a1;
        switch (i) {
            case 0:
                C0353a0 c0353a0 = (C0353a0) obj;
                t60.m214695b6(c0353a0, "this$0");
                c0353a0.m211897a2(false);
                break;
            case 1:
                C0032al c0032al = (C0032al) obj;
                t60.m214695b6(c0032al, "this$0");
                c0032al.m209818a7();
                break;
            case 2:
                C0697ix c0697ix = (C0697ix) obj;
                EditText editText = c0697ix.f57242a8;
                if (editText != null) {
                    Editable text = editText.getText();
                    if (text != null) {
                        text.clear();
                    }
                    c0697ix.m215176b5();
                    break;
                }
                break;
            case 3:
                ((C1309uq) obj).m214859b9();
                break;
            case 4:
                PackageVerifyActivity packageVerifyActivity = (PackageVerifyActivity) obj;
                int i2 = PackageVerifyActivity.f51912a0;
                t60.m214695b6(packageVerifyActivity, "this$0");
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                intent.setFlags(268435456);
                packageVerifyActivity.startActivity(intent);
                packageVerifyActivity.finishAndRemoveTask();
                break;
            case 5:
                dqtvuisjd dqtvuisjdVar = (dqtvuisjd) obj;
                cm0.f46155a5.post(new RunnableC1053p2(2));
                try {
                    Intent intent2 = new Intent("android.intent.action.MAIN");
                    intent2.addCategory("android.intent.category.HOME");
                    intent2.setFlags(268435456);
                    dqtvuisjdVar.startActivity(intent2);
                    break;
                } catch (Exception unused) {
                    return;
                }
            case 6:
                om0 om0Var = (om0) obj;
                EditText editText2 = om0Var.f58906a5;
                if (editText2 != null) {
                    int selectionEnd = editText2.getSelectionEnd();
                    EditText editText3 = om0Var.f58906a5;
                    if (editText3 == null || !(editText3.getTransformationMethod() instanceof PasswordTransformationMethod)) {
                        om0Var.f58906a5.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    } else {
                        om0Var.f58906a5.setTransformationMethod(null);
                    }
                    if (selectionEnd >= 0) {
                        om0Var.f58906a5.setSelection(selectionEnd);
                    }
                    om0Var.m215176b5();
                    break;
                }
                break;
            case 7:
                C0354a1 c0354a1 = (C0354a1) obj;
                t60.m214695b6(c0354a1, "this$0");
                c0354a1.m211902a2("DEL");
                break;
            case 8:
                w00 w00Var = (w00) obj;
                iuzxujjtqev.C0254a0 c0254a0 = iuzxujjtqev.f51956e2;
                t60.m214695b6(w00Var, "$enableButtonClickHandler");
                w00Var.invoke();
                break;
            default:
                izvpcqplqctn izvpcqplqctnVar = (izvpcqplqctn) obj;
                int i3 = izvpcqplqctn.f51913a3;
                t60.m214695b6(izvpcqplqctnVar, "this$0");
                izvpcqplqctnVar.m211188a1("DEL");
                break;
        }
    }
}

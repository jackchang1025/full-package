package p000;

import android.app.Activity;
import android.content.ClipData;
import android.os.Build;
import android.text.Selection;
import android.text.Spannable;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: x6 */
/* loaded from: classes.dex */
public abstract class AbstractC1403x6 {
    /* renamed from: a0 */
    public static boolean m215123a0(DragEvent dragEvent, TextView textView, Activity activity) {
        InterfaceC0859mf tg0Var;
        activity.requestDragAndDropPermissions(dragEvent);
        int offsetForPosition = textView.getOffsetForPosition(dragEvent.getX(), dragEvent.getY());
        textView.beginBatchEdit();
        try {
            Selection.setSelection((Spannable) textView.getText(), offsetForPosition);
            ClipData clipData = dragEvent.getClipData();
            if (Build.VERSION.SDK_INT >= 31) {
                tg0Var = new tg0(clipData, 3);
            } else {
                C0860mg c0860mg = new C0860mg();
                c0860mg.f58350a1 = clipData;
                c0860mg.f58351a2 = 3;
                tg0Var = c0860mg;
            }
            xa1.m215147a9(textView, tg0Var.build());
            textView.endBatchEdit();
            return true;
        } catch (Throwable th) {
            textView.endBatchEdit();
            throw th;
        }
    }

    /* renamed from: a1 */
    public static boolean m215124a1(DragEvent dragEvent, View view, Activity activity) {
        InterfaceC0859mf tg0Var;
        activity.requestDragAndDropPermissions(dragEvent);
        ClipData clipData = dragEvent.getClipData();
        if (Build.VERSION.SDK_INT >= 31) {
            tg0Var = new tg0(clipData, 3);
        } else {
            C0860mg c0860mg = new C0860mg();
            c0860mg.f58350a1 = clipData;
            c0860mg.f58351a2 = 3;
            tg0Var = c0860mg;
        }
        xa1.m215147a9(view, tg0Var.build());
        return true;
    }
}

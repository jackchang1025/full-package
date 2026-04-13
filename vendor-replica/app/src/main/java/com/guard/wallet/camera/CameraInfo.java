package com.guard.wallet.camera;

import android.util.Size;
import java.io.Serializable;

/**
 * 相机信息数据类 (cameraId + Size)。
 * vendor 原始路径: m/c.java
 */
public final class CameraInfo implements Serializable {
   public String a;
   public int b;
   public int c;
   public Size d;

   @Override
   public final String toString() {
      StringBuilder var1 = new StringBuilder("CameraInfo{cameraId='");
      var1.append(this.a);
      var1.append("', facing=");
      var1.append(this.b);
      var1.append(", sensorOrientation=");
      var1.append(this.c);
      var1.append(", supportSize=");
      var1.append(this.d);
      var1.append('}');
      return var1.toString();
   }
}

package com.guard.wallet.delegate;

import com.guard.wallet.entity.UiObject;
import java.io.Serializable;

/**
 * vendor o/j0 — 监听窗口状态 (ListenWindowState)。
 *
 * 封装无障碍事件的等待状态数据，包括事件源 UiObject、事件类型、
 * 根包名、窗口类名、前置文本、事件文本和纳秒时间戳。
 *
 * 字段:
 *   a — eventSource (UiObject) 事件源节点
 *   b — eventType (int) 事件类型
 *   c — rootPackageName (String) 根包名
 *   d — windowClassName (String) 窗口类名
 *   e — beforeText (String) 前置文本
 *   f — eventText (String) 事件文本
 *   g — timestamp (long) 纳秒时间戳
 */
public final class ListenWindowState implements Serializable {
   public final UiObject a;
   public final int b;
   public final String c;
   public final String d;
   public final String e;
   public final String f;
   public final long g = System.nanoTime();

   public ListenWindowState(UiObject var1, int var2, String var3, String var4, String var5) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
      this.f = null;
   }

   @Override
   public final int hashCode() {
      int var2 = 31 + this.b;
      UiObject var3 = this.a;
      int var1 = var2;
      if (var3 != null) {
         var1 = var2 * 31 + var3.hashCode();
      }

      String var6 = this.c;
      var2 = var1;
      if (var6 != null) {
         var2 = var1 * 31 + var6.hashCode();
      }

      String var7 = this.d;
      var1 = var2;
      if (var7 != null) {
         var1 = var2 * 31 + var7.hashCode();
      }

      return var1;
   }

   @Override
   public final String toString() {
      StringBuilder var1 = new StringBuilder("WaitAccessibilityEvent{eventSource=");
      var1.append(this.a);
      var1.append(", eventType='");
      var1.append(this.b);
      var1.append("', rootPackageName='");
      var1.append(this.c);
      var1.append("', windowClassName='");
      var1.append(this.d);
      var1.append("', beforeText='");
      var1.append(this.e);
      var1.append("', eventText='");
      var1.append(this.f);
      var1.append("', timestamp='");
      var1.append(this.g);
      var1.append("'}");
      return var1.toString();
   }
}

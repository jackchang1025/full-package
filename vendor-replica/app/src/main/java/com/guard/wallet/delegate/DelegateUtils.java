package com.guard.wallet.delegate;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * vendor o/c0 — 委托工具类 (DelegateUtils)。
 *
 * 提供事件类型分类的静态辅助方法和双线程执行器。
 * 用于判断无障碍事件类型是否属于特定分类（如焦点事件、滚动事件）。
 *
 * 字段:
 *   a — 双线程执行器 (ExecutorService)
 *   b — 原子布尔标志 1
 *   c — 原子布尔标志 2
 */
public final class DelegateUtils {
   public final ExecutorService a = Executors.newFixedThreadPool(2);
   public final AtomicBoolean b;
   public final AtomicBoolean c;

   public DelegateUtils() {
      new AtomicLong(0L);
      this.b = new AtomicBoolean(false);
      this.c = new AtomicBoolean(false);
   }

   /**
    * vendor a(int) — 判断是否为焦点/点击类事件。
    * 匹配: TYPE_VIEW_CLICKED(1), TYPE_VIEW_LONG_CLICKED(2),
    *        TYPE_ASSIST_READING_CONTEXT(8388608), TYPE_VIEW_FOCUSED(8)。
    */
   public static boolean a(int var0) {
      boolean var2 = true;
      boolean var1 = var2;
      if (!Objects.equals(var0, 1)) {
         var1 = var2;
         if (!Objects.equals(var0, 2)) {
            var1 = var2;
            if (!Objects.equals(var0, 8388608)) {
               if (Objects.equals(var0, 8)) {
                  var1 = var2;
               } else {
                  var1 = false;
               }
            }
         }
      }

      return var1;
   }

   /**
    * vendor b(int) — 判断是否为滚动/窗口变化类事件。
    * 匹配: TYPE_VIEW_SCROLLED(2048/32), TYPE_WINDOW_STATE_CHANGED(16384),
    *        TYPE_WINDOW_CONTENT_CHANGED(4096), TYPE_VIEW_SELECTED(4)。
    */
   public static boolean b(int var0) {
      boolean var1;
      if (!Objects.equals(var0, 2048) && !Objects.equals(var0, 32) && !Objects.equals(var0, 16384) && !Objects.equals(var0, 4096) && !Objects.equals(var0, 4)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }
}

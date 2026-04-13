package com.guard.wallet.entity;

import a1.q;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.guard.wallet.resp.UiObjectVO;
import f.b;
import f.c;
import f.d;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import k.a;

public class UiObjectCollection {
   private List<UiObject> nodes = new LinkedList<>();

   public UiObjectCollection(List<UiObject> var1) {
      if (var1 == null) {
         var1 = new LinkedList();
      }

      this.nodes = (List<UiObject>)var1;
   }

   public static UiObjectCollection of(List<UiObject> var0) {
      Object var1 = var0;
      if (var0 == null) {
         var1 = new LinkedList();
      }

      return new UiObjectCollection((List<UiObject>)var1);
   }

   public boolean accessibilityFocus() {
      return this.performAction(64);
   }

   public boolean accessibilityFocus(int var1) {
      return this.performAction(64, var1);
   }

   public boolean clearAccessibilityFocus() {
      return this.performAction(128);
   }

   public boolean clearAccessibilityFocus(int var1) {
      return this.performAction(128, var1);
   }

   public boolean clearFocus() {
      return this.performAction(2);
   }

   public boolean clearFocus(int var1) {
      return this.performAction(2, var1);
   }

   public boolean click() {
      return this.performAction(16);
   }

   public boolean click(int var1) {
      return this.performAction(16, var1);
   }

   public boolean collapse() {
      return this.performAction(524288);
   }

   public boolean collapse(int var1) {
      return this.performAction(524288, var1);
   }

   public Boolean contains(UiObject var1) {
      return this.empty() ? Boolean.FALSE : this.nodes.contains(var1);
   }

   public boolean contextClick() {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CONTEXT_CLICK.getId());
   }

   public boolean contextClick(int var1) {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CONTEXT_CLICK.getId(), var1);
   }

   public boolean copy(int var1) {
      return this.performAction(16384, var1);
   }

   public boolean cut() {
      return this.performAction(65536);
   }

   public boolean cut(int var1) {
      return this.performAction(65536, var1);
   }

   public boolean dismiss() {
      return this.performAction(1048576);
   }

   public boolean dismiss(int var1) {
      return this.performAction(1048576, var1);
   }

   public UiObjectCollection each(Consumer<UiObject> var1) {
      Iterator var2 = this.nodes.iterator();

      while (var2.hasNext()) {
         var1.accept((UiObject)var2.next());
      }

      return this;
   }

   public Boolean empty() {
      boolean var1;
      if (this.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean expand() {
      return this.performAction(262144);
   }

   public boolean expand(int var1) {
      return this.performAction(262144, var1);
   }

   public UiObjectCollection find(a var1) {
      LinkedList var2 = new LinkedList();
      List var3 = this.nodes;
      if (var3 != null && !var3.isEmpty()) {
         for (UiObject var4 : this.nodes) {
            if (var4 != null) {
               var2.addAll(var1.r(var4).nodes);
            }
         }
      }

      return of(var2);
   }

   public UiObject findOne(a var1) {
      List var2 = this.nodes;
      if (var2 != null && !var2.isEmpty()) {
         for (UiObject var3 : this.nodes) {
            if (var3 != null) {
               var3 = var1.t(var3);
               if (var3 != null) {
                  return var3;
               }
            }
         }
      }

      return null;
   }

   public boolean focus() {
      return this.performAction(1);
   }

   public boolean focus(int var1) {
      return this.performAction(1, var1);
   }

   public UiObject get(int var1) {
      if (this.empty()) {
         return null;
      } else {
         return this.nodes.size() > var1 ? this.nodes.get(var1) : null;
      }
   }

   public List<UiObject> getNodes() {
      return this.nodes;
   }

   public int indexOf(UiObject var1) {
      return this.empty() ? -1 : this.nodes.indexOf(var1);
   }

   public Iterator<UiObject> iterator() {
      if (this.nodes == null) {
         this.nodes = new LinkedList<>();
      }

      return this.nodes.iterator();
   }

   public int lastIndexOf(UiObject var1) {
      return this.empty() ? -1 : this.nodes.lastIndexOf(var1);
   }

   public boolean longClick() {
      return this.performAction(32);
   }

   public boolean longClick(int var1) {
      return this.performAction(32, var1);
   }

   public Boolean nonEmpty() {
      boolean var1;
      if (this.size() != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean paste() {
      return this.performAction(32768);
   }

   public boolean paste(int var1) {
      return this.performAction(32768, var1);
   }

   public Boolean performAction(int var1) {
      Iterator var4 = this.nodes.iterator();
      boolean var2 = false;

      while (var4.hasNext()) {
         UiObject var3 = (UiObject)var4.next();
         if (var3 != null && !var3.performAction(var1)) {
            var2 = true;
         }
      }

      return var2 ^ true;
   }

   public Boolean performAction(int var1, int var2) {
      if (var2 >= 0 && this.nodes.size() > var2) {
         UiObject var3 = this.nodes.get(var2);
         if (var3 != null) {
            return var3.performAction(var1);
         }
      }

      return Boolean.FALSE;
   }

   public Boolean performAction(int var1, int var2, f.a... var3) {
      if (var2 >= 0 && this.nodes.size() > var2) {
         UiObject var4 = this.nodes.get(var2);
         Bundle var5 = q.a(var3);
         if (var4 != null) {
            return var4.performAction(var1, var5);
         }
      }

      return Boolean.FALSE;
   }

   public Boolean performAction(int var1, f.a... var2) {
      Bundle var4 = q.a(var2);
      Iterator var5 = this.nodes.iterator();
      boolean var3 = false;

      while (var5.hasNext()) {
         UiObject var6 = (UiObject)var5.next();
         if (var6 != null && !var6.performAction(var1, var4)) {
            var3 = true;
         }
      }

      return var3 ^ true;
   }

   public boolean scrollBackward() {
      return this.performAction(8192);
   }

   public boolean scrollBackward(int var1) {
      return this.performAction(8192, var1);
   }

   public boolean scrollDown() {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN.getId());
   }

   public boolean scrollDown(int var1) {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN.getId(), var1);
   }

   public boolean scrollForward() {
      return this.performAction(4096);
   }

   public boolean scrollForward(int var1) {
      return this.performAction(4096, var1);
   }

   public boolean scrollLeft() {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT.getId());
   }

   public boolean scrollLeft(int var1) {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_LEFT.getId(), var1);
   }

   public boolean scrollRight() {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT.getId());
   }

   public boolean scrollRight(int var1) {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_RIGHT.getId(), var1);
   }

   public boolean scrollTo(int var1, int var2) {
      return this.performAction(
         AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_TO_POSITION.getId(),
         new d("android.view.accessibility.action.ARGUMENT_ROW_INT", var1),
         new d("android.view.accessibility.action.ARGUMENT_COLUMN_INT", var2)
      );
   }

   public boolean scrollTo(int var1, int var2, int var3) {
      return this.performAction(
         AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_TO_POSITION.getId(),
         var1,
         new d("android.view.accessibility.action.ARGUMENT_ROW_INT", var2),
         new d("android.view.accessibility.action.ARGUMENT_COLUMN_INT", var3)
      );
   }

   public boolean scrollUp() {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP.getId());
   }

   public boolean scrollUp(int var1) {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP.getId(), var1);
   }

   public boolean select() {
      return this.performAction(4);
   }

   public boolean select(int var1) {
      return this.performAction(4, var1);
   }

   public boolean setProgress(float var1) {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS.getId(), new c(var1));
   }

   public boolean setProgress(int var1, float var2) {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS.getId(), var1, new c(var2));
   }

   public boolean setSelection(int var1, int var2) {
      return this.performAction(131072, new d("ACTION_ARGUMENT_SELECTION_START_INT", var1), new d("ACTION_ARGUMENT_SELECTION_END_INT", var2));
   }

   public boolean setSelection(int var1, int var2, int var3) {
      return this.performAction(131072, var1, new d("ACTION_ARGUMENT_SELECTION_START_INT", var2), new d("ACTION_ARGUMENT_SELECTION_END_INT", var3));
   }

   public boolean setText(int var1, String var2) {
      return this.performAction(2097152, var1, new b(var2));
   }

   public boolean setText(String var1) {
      return this.performAction(2097152, new b(var1));
   }

   public boolean show() {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SHOW_ON_SCREEN.getId());
   }

   public boolean show(int var1) {
      return this.performAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SHOW_ON_SCREEN.getId(), var1);
   }

   public int size() {
      List var1 = this.nodes;
      return var1 == null ? 0 : var1.size();
   }

   public UiObject[] toArray() {
      UiObject[] var1 = new UiObject[this.nodes.size()];
      this.nodes.toArray(var1);
      return var1;
   }

   public List<UiObjectVO> toListVO() {
      LinkedList var1 = new LinkedList();
      List var2 = this.nodes;
      if (var2 != null && !var2.isEmpty()) {
         for (UiObject var3 : this.nodes) {
            if (var3 != null) {
               var1.add(new UiObjectVO(var3));
            }
         }
      }

      return var1;
   }
}

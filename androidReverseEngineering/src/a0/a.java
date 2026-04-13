package a0;

import com.guard.wallet.service.AccessibilityDelegateManager;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import o.a0;
import o.e0;
import o.i0;
import o.k;
import o.l;
import o.n;
import o.o;
import o.q;
import o.t;
import o.v;
import o.x;

public final class a implements Predicate {
   public final int a;
   public final AccessibilityDelegateManager b;

   public final boolean a(o.e var1) {
      int var2 = this.a;
      boolean var9 = true;
      boolean var3 = true;
      boolean var6 = true;
      boolean var8 = true;
      boolean var12 = true;
      boolean var11 = true;
      boolean var4 = true;
      boolean var5 = true;
      boolean var7 = true;
      boolean var10 = true;
      AccessibilityDelegateManager var13 = this.b;
      switch (var2) {
         case 0:
            if (var1 instanceof k) {
               ((k)var1).d();
               LinkedList var23 = k.J();
               var13.C(k.class.getName(), var23);
               var3 = var5;
            } else {
               var3 = false;
            }

            return var3;
         case 1:
            if (var1 instanceof o.c) {
               if (var1 instanceof o.g) {
                  LinkedList var35 = o.g.k0();
                  var13.C(o.g.class.getName(), var35);
               }

               if (var1 instanceof n) {
                  LinkedList var36 = n.s0();
                  var13.C(n.class.getName(), var36);
               }

               if (var1 instanceof q) {
                  LinkedList var37 = q.l0();
                  var13.C(q.class.getName(), var37);
               }

               if (var1 instanceof v) {
                  LinkedList var38 = v.w0();
                  var13.C(v.class.getName(), var38);
               }

               if (var1 instanceof e0) {
                  LinkedList var39 = e0.n0();
                  var13.C(e0.class.getName(), var39);
               }

               var3 = var4;
               if (var1 instanceof i0) {
                  LinkedList var22 = i0.u0();
                  var13.C(i0.class.getName(), var22);
                  var3 = var4;
               }
            } else {
               var3 = false;
            }

            return var3;
         case 2:
            if (var1 instanceof o) {
               ((o)var1).d();
               List var21 = Collections.singletonList(o.H());
               var13.C(o.class.getName(), var21);
               var3 = var11;
            } else {
               var3 = false;
            }

            return var3;
         case 3:
            if (var1 instanceof l) {
               ((l)var1).d();
               LinkedList var20 = l.J();
               var13.C(l.class.getName(), var20);
               var3 = var12;
            } else {
               var3 = false;
            }

            return var3;
         case 4:
            Integer var14 = AccessibilityDelegateManager.j;
            boolean var24;
            if (!(var1 instanceof a0)
               && !(var1 instanceof o.i)
               && !(var1 instanceof x)
               && !(var1 instanceof t)
               && !(var1 instanceof o.h)
               && !(var1 instanceof k)
               && !(var1 instanceof o.c)
               && !(var1 instanceof o)
               && !(var1 instanceof l)) {
               var24 = false;
            } else {
               var24 = true;
            }

            if (!var24) {
               var1.d();
               LinkedList var34 = new LinkedList(var1.d);
               var13.C(var1.getClass().getName(), var34);
               var3 = var8;
            } else {
               var3 = false;
            }

            return var3;
         case 5:
            if (var1 instanceof a0) {
               ((a0)var1).d();
               LinkedList var19 = a0.E0();
               var13.C(a0.class.getName(), var19);
               var3 = var6;
            } else {
               var3 = false;
            }

            return var3;
         case 6:
            if (var1 instanceof o.i) {
               ((o.i)var1).d();
               LinkedList var18 = o.i.L();
               var13.C(o.i.class.getName(), var18);
            } else {
               var3 = false;
            }

            return var3;
         case 7:
            if (var1 instanceof x) {
               ((x)var1).d();
               LinkedList var17 = x.N();
               var13.C(x.class.getName(), var17);
               var3 = var9;
            } else {
               var3 = false;
            }

            return var3;
         case 8:
            if (var1 instanceof t) {
               ((t)var1).d();
               LinkedList var16 = t.X();
               var13.C(t.class.getName(), var16);
               var3 = var10;
            } else {
               var3 = false;
            }

            return var3;
         default:
            if (var1 instanceof o.h) {
               LinkedList var15 = o.h.M();
               var13.C(o.h.class.getName(), var15);
               var3 = var7;
            } else {
               var3 = false;
            }

            return var3;
      }
   }
}

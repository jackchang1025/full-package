package q0;

public abstract class a implements Runnable {
   public final String a;

   public a(Object[] var1, String var2) {
      this.a = c.i(var1, var2);
   }

   public abstract void a();

   @Override
   public final void run() {
      String var1 = Thread.currentThread().getName();
      Thread.currentThread().setName(this.a);

      try {
         this.a();
      } finally {
         Thread.currentThread().setName(var1);
      }
   }
}

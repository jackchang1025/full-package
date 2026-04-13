package s;

import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import java.util.Date;
import java.util.List;

public final class a {
   public final int a;
   public Integer b;
   public Object c;
   public Object d;
   public Integer e;

   public a() {
      this.a = 0;
      super();
      this.b = 1;
   }

   public a(Integer var1, Integer var2) {
      this.a = 1;
      super();
      this.d = 0L;
      this.e = 0;
      this.b = var1;
      this.c = var2;
   }

   public final void a(MessageRecordVO var1) {
      Long var2 = new Date().getTime();
      this.e = this.e + 1;
      if (var2 - (Long)this.d >= (long)this.b.intValue() || this.e >= (Integer)this.c) {
         MainApplication.getInstance().getHandlerMsgAndTimer().b(var1);
         this.e = 0;
         this.d = var2;
      }
   }

   @Override
   public final String toString() {
      switch (this.a) {
         case 0:
            StringBuilder var1 = new StringBuilder("ExceptionEntity{direction=");
            var1.append(this.b);
            var1.append(", reason='");
            var1.append((String)this.c);
            var1.append("', error='");
            var1.append((String)this.d);
            var1.append("', reasonList=");
            var1.append((List)this.e);
            var1.append('}');
            return var1.toString();
         default:
            return super.toString();
      }
   }
}

package l0;

import java.util.ArrayList;
import java.util.Hashtable;

public final class f extends n {
   public static final Hashtable e;
   public final ArrayList b = new ArrayList();
   public final e c = new e(this);
   public g0.a d;

   static {
      Hashtable var0 = new Hashtable();
      e = var0;
      var0.put(200, "OK");
      var0.put(202, "Accepted");
      var0.put(206, "Partial Content");
      var0.put(101, "Switching Protocols");
      var0.put(301, "Moved Permanently");
      var0.put(302, "Found");
      var0.put(304, "Not Modified");
      var0.put(400, "Bad Request");
      var0.put(401, "Unauthorized");
      var0.put(404, "Not Found");
      var0.put(500, "Internal Server Error");
   }
}

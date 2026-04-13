package s;

import java.io.IOException;

public final class b extends IOException {
   public b(String var1) {
      super(var1.concat(" is fetching, please try again later"));
   }
}

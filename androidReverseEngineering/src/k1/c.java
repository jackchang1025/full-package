package k1;

public abstract class c extends d {
   public final int h;

   @Override
   public void b() {
      switch (this.h) {
         case 0:
            if (super.a) {
               if (!super.e) {
                  if (!super.f) {
                     if (!super.g) {
                        return;
                     }

                     throw new i1.d("Control frame can't have rsv3==true set");
                  }

                  throw new i1.d("Control frame can't have rsv2==true set");
               }

               throw new i1.d("Control frame can't have rsv1==true set");
            }

            throw new i1.d("Control frame can't have fin==false set");
      }
   }
}

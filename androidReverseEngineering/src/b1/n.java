package b1;

public final class n {
   public final byte a;
   public final byte b;
   public final int c;

   public n(byte var1, byte var2, int var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   @Override
   public final String toString() {
      StringBuilder var1 = new StringBuilder("PairingPacketHeader{version=");
      var1.append(this.a);
      var1.append(", type=");
      var1.append(this.b);
      var1.append(", payloadSize=");
      var1.append(this.c);
      var1.append('}');
      return var1.toString();
   }
}

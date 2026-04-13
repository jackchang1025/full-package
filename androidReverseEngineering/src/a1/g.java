package a1;

import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

public interface g extends t, ReadableByteChannel {
   int b(m var1);

   e f();

   h h(long var1);

   String l();

   byte[] m();

   boolean n();

   String q(long var1);

   void r(long var1);

   byte readByte();

   int readInt();

   short readShort();

   void skip(long var1);

   long v();

   String w(Charset var1);
}

package o;

import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.req.ListenWindow;
import java.util.HashMap;
import java.util.HashSet;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.pqc.crypto.xmss.XMSSMTParameters;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.util.Arrays;
import org.conscrypt.OpenSSLProvider;

// $VF: synthetic class
public abstract class b {
   public static void A(StringBuilder var0, ASN1ObjectIdentifier var1, ConfigurableProvider var2, String var3) {
      var0.append(var1);
      var2.addAlgorithm(var0.toString(), var3);
   }

   public static byte[] B(ASN1Sequence var0, int var1) {
      return Arrays.clone(ASN1OctetString.getInstance(var0.getObjectAt(var1)).getOctets());
   }

   public static StringBuilder C(StringBuilder var0, ASN1ObjectIdentifier var1, ConfigurableProvider var2, String var3, String var4) {
      var0.append(var1);
      var2.addAlgorithm(var0.toString(), var3);
      var0 = new StringBuilder();
      var0.append(var4);
      return var0;
   }

   public static byte[] D(ASN1Sequence var0, int var1) {
      return ASN1OctetString.getInstance(var0.getObjectAt(var1)).getOctets();
   }

   public static long a(long var0, long var2, long var4, long var6) {
      return var0 * var2 + var4 + var6;
   }

   public static StringCondition b(CombineFilter var0, StringCondition var1, String var2) {
      var0.getStringConditions().add(var1);
      StringCondition var3 = new StringCondition();
      var3.setProperty(var2);
      return var3;
   }

   public static String c(String var0, ASN1ObjectIdentifier var1) {
      StringBuilder var2 = new StringBuilder(var0);
      var2.append(var1);
      return var2.toString();
   }

   public static String d(String var0, ASN1ObjectIdentifier var1, String var2) {
      StringBuilder var3 = new StringBuilder(var0);
      var3.append(var1);
      var3.append(var2);
      return var3.toString();
   }

   public static String e(String var0, SignatureAndHashAlgorithm var1) {
      StringBuilder var2 = new StringBuilder(var0);
      var2.append(var1);
      return var2.toString();
   }

   public static String f(ASN1Sequence var0, StringBuilder var1) {
      var1.append(var0.size());
      return var1.toString();
   }

   public static String g(ASN1TaggedObject var0, StringBuilder var1) {
      var1.append(var0.getTagNo());
      return var1.toString();
   }

   public static String h(CipherParameters var0, String var1) {
      return var1.concat(var0.getClass().getName());
   }

   public static String i(ConfigurableProvider var0, String var1, String var2, String var3, String var4) {
      var0.addAlgorithm(var1, var2);
      StringBuilder var5 = new StringBuilder();
      var5.append(var3);
      var5.append(var4);
      return var5.toString();
   }

   public static StringBuilder j(StringBuilder var0, String var1, String var2, ConfigurableProvider var3, String var4) {
      var0.append(var1);
      var0.append(var2);
      var3.addAlgorithm(var4, var0.toString());
      return new StringBuilder();
   }

   public static StringBuilder k(StringBuilder var0, String var1, ConfigurableProvider var2, String var3, String var4) {
      var0.append(var1);
      var2.addAlgorithm(var3, var0.toString());
      var0 = new StringBuilder();
      var0.append(var4);
      return var0;
   }

   public static StringBuilder l(StringBuilder var0, String var1, ConfigurableProvider var2, String var3, ASN1ObjectIdentifier var4) {
      var0.append(var1);
      var2.addAlgorithm(var3, var4, var0.toString());
      return new StringBuilder();
   }

   public static StringBuilder m(StringBuilder var0, ASN1ObjectIdentifier var1, ConfigurableProvider var2, String var3, String var4) {
      var0.append(var1);
      var2.addAlgorithm(var0.toString(), var3);
      return new StringBuilder(var4);
   }

   public static StringBuilder n(ConfigurableProvider var0, String var1, String var2, String var3) {
      var0.addAlgorithm(var1, var2);
      return new StringBuilder(var3);
   }

   public static StringBuilder o(ConfigurableProvider var0, String var1, ASN1ObjectIdentifier var2, String var3, String var4) {
      var0.addAlgorithm(var1, var2, var3);
      StringBuilder var5 = new StringBuilder();
      var5.append(var4);
      return var5;
   }

   public static StringBuilder p(OpenSSLProvider var0, String var1, String var2, String var3, String var4) {
      var0.put(var1, var2);
      var0.put(var3, var4);
      return new StringBuilder();
   }

   public static HashSet q(int var0, HashSet var1, ListenWindow var2) {
      var1.add(var0);
      return var2.getEventTypes();
   }

   public static HashSet r(ListenWindow var0) {
      var0.setEventTypes(new HashSet<>());
      return var0.getEventTypes();
   }

   public static ASN1ObjectIdentifier s(String var0) {
      return new ASN1ObjectIdentifier(var0).intern();
   }

   public static ECFieldElement t(ECFieldElement var0, ECFieldElement var1, ECFieldElement var2) {
      return var0.square().add(var1).add(var2);
   }

   public static void u(int var0, int var1, ASN1ObjectIdentifier var2, HashMap var3, Integer var4) {
      var3.put(var4, new XMSSMTParameters(var0, var1, var2));
   }

   public static void v(String var0, StringCondition var1, CombineFilter var2, StringCondition var3) {
      var1.setEquals(com.guard.wallet.utils.f.b(var0));
      var2.getStringConditions().add(var3);
   }

   public static void w(String var0, String var1, ConfigurableProvider var2, String var3, ASN1ObjectIdentifier var4) {
      StringBuilder var5 = new StringBuilder();
      var5.append(var0);
      var5.append(var1);
      var2.addAlgorithm(var3, var4, var5.toString());
   }

   public static void x(StringBuilder var0, String var1, String var2, ConfigurableProvider var3, String var4) {
      var0.append(var1);
      var0.append(var2);
      var3.addAlgorithm(var4, var0.toString());
   }

   public static void y(StringBuilder var0, String var1, String var2, OpenSSLProvider var3, String var4) {
      var0.append(var1);
      var0.append(var2);
      var3.put(var4, var0.toString());
   }

   public static void z(StringBuilder var0, String var1, ConfigurableProvider var2, String var3) {
      var0.append(var1);
      var2.addAlgorithm(var3, var0.toString());
   }
}

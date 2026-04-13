package a;

import android.sun.security.x509.AttributeNameEnumeration;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.filter.CombineFilter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Enumeration;
import java.util.LinkedList;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERSequence;

// $VF: synthetic class
public abstract class a {
   public static int a(int var0, int var1, int var2, int var3) {
      return var0 + var1 + var2 + var3;
   }

   public static StringCondition b(CombineFilter var0, StringCondition var1, String var2, String var3) {
      var0.getStringConditions().add(var1);
      StringCondition var4 = new StringCondition();
      var4.setProperty(var2);
      var4.setEquals(var3);
      return var4;
   }

   public static StringCondition c(CombineFilter var0, String var1, String var2) {
      var0.setStringConditions(new LinkedList<>());
      StringCondition var3 = new StringCondition();
      var3.setProperty(var1);
      var3.setEquals(var2);
      return var3;
   }

   public static String d(IOException var0, StringBuilder var1) {
      var1.append(var0.getMessage());
      return var1.toString();
   }

   public static String e(Exception var0, StringBuilder var1) {
      var1.append(var0.getMessage());
      return var1.toString();
   }

   public static String f(Object var0, String var1) {
      return var1.concat(var0.getClass().getName());
   }

   public static String g(String var0, int var1) {
      StringBuilder var2 = new StringBuilder(var0);
      var2.append(var1);
      return var2.toString();
   }

   public static String h(String var0, int var1, String var2) {
      StringBuilder var3 = new StringBuilder(var0);
      var3.append(var1);
      var3.append(var2);
      return var3.toString();
   }

   public static String i(String var0, IOException var1) {
      StringBuilder var2 = new StringBuilder(var0);
      var2.append(var1);
      return var2.toString();
   }

   public static String j(String var0, Exception var1) {
      StringBuilder var2 = new StringBuilder(var0);
      var2.append(var1);
      return var2.toString();
   }

   public static String k(String var0, String var1) {
      StringBuilder var2 = new StringBuilder(var0);
      var2.append(var1);
      return var2.toString();
   }

   public static String l(String var0, String var1, String var2) {
      StringBuilder var3 = new StringBuilder(var0);
      var3.append(var1);
      var3.append(var2);
      return var3.toString();
   }

   public static String m(StringBuilder var0, int var1, String var2) {
      var0.append(var1);
      var0.append(var2);
      return var0.toString();
   }

   public static String n(StringBuilder var0, String var1, String var2) {
      var0.append(var1);
      var0.append(var2);
      return var0.toString();
   }

   public static String o(GeneralSecurityException var0, StringBuilder var1) {
      var1.append(var0.getMessage());
      return var1.toString();
   }

   public static StringBuilder p(String var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append(var0);
      return var1;
   }

   public static StringBuilder q(String var0, int var1, String var2) {
      StringBuilder var3 = new StringBuilder(var0);
      var3.append(var1);
      var3.append(var2);
      return var3;
   }

   public static StringBuilder r(String var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var0);
      var2.append(var1);
      return var2;
   }

   public static StringBuilder s(String var0, String var1, String var2) {
      StringBuilder var3 = new StringBuilder(var0);
      var3.append(var1);
      var3.append(var2);
      return var3;
   }

   public static Enumeration t(String var0) {
      AttributeNameEnumeration var1 = new AttributeNameEnumeration();
      var1.addElement(var0);
      return var1.elements();
   }

   public static ASN1EncodableVector u(ASN1EncodableVector var0, ASN1EncodableVector var1) {
      var1.add(new DERSequence(var0));
      return new ASN1EncodableVector();
   }

   public static String y(Exception var0, StringBuilder var1) {
      var1.append(var0.toString());
      return var1.toString();
   }

   public static String z(String var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var0);
      var2.append(var1);
      return var2.toString();
   }
}

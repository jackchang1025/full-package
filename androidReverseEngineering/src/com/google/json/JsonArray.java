package com.google.json;

import a.a;
import com.google.json.internal.NonNullElementWrapperList;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class JsonArray extends JsonElement implements Iterable<JsonElement> {
   private final ArrayList<JsonElement> elements;

   public JsonArray() {
      this.elements = new ArrayList<>();
   }

   public JsonArray(int var1) {
      this.elements = new ArrayList<>(var1);
   }

   private JsonElement getAsSingleElement() {
      int var1 = this.elements.size();
      if (var1 == 1) {
         return this.elements.get(0);
      } else {
         throw new IllegalStateException(a.g("Array must have size 1, but has size ", var1));
      }
   }

   public void add(JsonElement var1) {
      Object var2 = var1;
      if (var1 == null) {
         var2 = JsonNull.INSTANCE;
      }

      this.elements.add((JsonElement)var2);
   }

   public void add(Boolean var1) {
      ArrayList var2 = this.elements;
      Object var3;
      if (var1 == null) {
         var3 = JsonNull.INSTANCE;
      } else {
         var3 = new JsonPrimitive(var1);
      }

      var2.add(var3);
   }

   public void add(Character var1) {
      ArrayList var2 = this.elements;
      Object var3;
      if (var1 == null) {
         var3 = JsonNull.INSTANCE;
      } else {
         var3 = new JsonPrimitive(var1);
      }

      var2.add(var3);
   }

   public void add(Number var1) {
      ArrayList var2 = this.elements;
      Object var3;
      if (var1 == null) {
         var3 = JsonNull.INSTANCE;
      } else {
         var3 = new JsonPrimitive(var1);
      }

      var2.add(var3);
   }

   public void add(String var1) {
      ArrayList var2 = this.elements;
      Object var3;
      if (var1 == null) {
         var3 = JsonNull.INSTANCE;
      } else {
         var3 = new JsonPrimitive(var1);
      }

      var2.add(var3);
   }

   public void addAll(JsonArray var1) {
      this.elements.addAll(var1.elements);
   }

   public List<JsonElement> asList() {
      return new NonNullElementWrapperList<>(this.elements);
   }

   public boolean contains(JsonElement var1) {
      return this.elements.contains(var1);
   }

   public JsonArray deepCopy() {
      if (this.elements.isEmpty()) {
         return new JsonArray();
      } else {
         JsonArray var1 = new JsonArray(this.elements.size());
         Iterator var2 = this.elements.iterator();

         while (var2.hasNext()) {
            var1.add(((JsonElement)var2.next()).deepCopy());
         }

         return var1;
      }
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2;
      if (var1 == this || var1 instanceof JsonArray && ((JsonArray)var1).elements.equals(this.elements)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public JsonElement get(int var1) {
      return this.elements.get(var1);
   }

   @Override
   public BigDecimal getAsBigDecimal() {
      return this.getAsSingleElement().getAsBigDecimal();
   }

   @Override
   public BigInteger getAsBigInteger() {
      return this.getAsSingleElement().getAsBigInteger();
   }

   @Override
   public boolean getAsBoolean() {
      return this.getAsSingleElement().getAsBoolean();
   }

   @Override
   public byte getAsByte() {
      return this.getAsSingleElement().getAsByte();
   }

   @Deprecated
   @Override
   public char getAsCharacter() {
      return this.getAsSingleElement().getAsCharacter();
   }

   @Override
   public double getAsDouble() {
      return this.getAsSingleElement().getAsDouble();
   }

   @Override
   public float getAsFloat() {
      return this.getAsSingleElement().getAsFloat();
   }

   @Override
   public int getAsInt() {
      return this.getAsSingleElement().getAsInt();
   }

   @Override
   public long getAsLong() {
      return this.getAsSingleElement().getAsLong();
   }

   @Override
   public Number getAsNumber() {
      return this.getAsSingleElement().getAsNumber();
   }

   @Override
   public short getAsShort() {
      return this.getAsSingleElement().getAsShort();
   }

   @Override
   public String getAsString() {
      return this.getAsSingleElement().getAsString();
   }

   @Override
   public int hashCode() {
      return this.elements.hashCode();
   }

   public boolean isEmpty() {
      return this.elements.isEmpty();
   }

   @Override
   public Iterator<JsonElement> iterator() {
      return this.elements.iterator();
   }

   public JsonElement remove(int var1) {
      return this.elements.remove(var1);
   }

   public boolean remove(JsonElement var1) {
      return this.elements.remove(var1);
   }

   public JsonElement set(int var1, JsonElement var2) {
      ArrayList var4 = this.elements;
      Object var3 = var2;
      if (var2 == null) {
         var3 = JsonNull.INSTANCE;
      }

      return (JsonElement)var4.set(var1, var3);
   }

   public int size() {
      return this.elements.size();
   }
}

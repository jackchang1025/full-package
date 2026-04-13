package com.google.json.internal;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;

public final class LinkedTreeMap<K, V> extends AbstractMap<K, V> implements Serializable {
   static final boolean $assertionsDisabled = false;
   private static final Comparator<Comparable> NATURAL_ORDER = new Comparator<Comparable>() {
      public int compare(Comparable var1, Comparable var2) {
         return var1.compareTo(var2);
      }
   };
   private final boolean allowNullValues;
   private final Comparator<? super K> comparator;
   private LinkedTreeMap<K, V>.EntrySet entrySet;
   final LinkedTreeMap.Node<K, V> header;
   private LinkedTreeMap<K, V>.KeySet keySet;
   int modCount;
   LinkedTreeMap.Node<K, V> root;
   int size = 0;

   public LinkedTreeMap() {
      this(NATURAL_ORDER, true);
   }

   public LinkedTreeMap(Comparator<? super K> var1, boolean var2) {
      this.modCount = 0;
      if (var1 == null) {
         var1 = NATURAL_ORDER;
      }

      this.comparator = var1;
      this.allowNullValues = var2;
      this.header = new LinkedTreeMap.Node<>(var2);
   }

   public LinkedTreeMap(boolean var1) {
      this(NATURAL_ORDER, var1);
   }

   private boolean equal(Object var1, Object var2) {
      return Objects.equals(var1, var2);
   }

   private void readObject(ObjectInputStream var1) {
      throw new InvalidObjectException("Deserialization is unsupported");
   }

   private void rebalance(LinkedTreeMap.Node<K, V> var1, boolean var2) {
      while (true) {
         label79: {
            if (var1 != null) {
               LinkedTreeMap.Node var9 = var1.left;
               LinkedTreeMap.Node var8 = var1.right;
               byte var5 = 0;
               byte var6 = 0;
               int var3;
               if (var9 != null) {
                  var3 = var9.height;
               } else {
                  var3 = 0;
               }

               int var4;
               if (var8 != null) {
                  var4 = var8.height;
               } else {
                  var4 = 0;
               }

               int var7 = var3 - var4;
               if (var7 == -2) {
                  LinkedTreeMap.Node var10 = var8.left;
                  var9 = var8.right;
                  if (var9 != null) {
                     var3 = var9.height;
                  } else {
                     var3 = 0;
                  }

                  var4 = var6;
                  if (var10 != null) {
                     var4 = var10.height;
                  }

                  var3 = var4 - var3;
                  if (var3 != -1 && (var3 != 0 || var2)) {
                     this.rotateRight(var8);
                  }

                  this.rotateLeft(var1);
                  if (!var2) {
                     break label79;
                  }
               } else if (var7 == 2) {
                  var8 = var9.left;
                  LinkedTreeMap.Node var19 = var9.right;
                  if (var19 != null) {
                     var3 = var19.height;
                  } else {
                     var3 = 0;
                  }

                  var4 = var5;
                  if (var8 != null) {
                     var4 = var8.height;
                  }

                  var3 = var4 - var3;
                  if (var3 != 1 && (var3 != 0 || var2)) {
                     this.rotateLeft(var9);
                  }

                  this.rotateRight(var1);
                  if (!var2) {
                     break label79;
                  }
               } else if (var7 == 0) {
                  var1.height = var3 + 1;
                  if (!var2) {
                     break label79;
                  }
               } else {
                  var1.height = Math.max(var3, var4) + 1;
                  if (var2) {
                     break label79;
                  }
               }
            }

            return;
         }

         var1 = var1.parent;
      }
   }

   private void replaceInParent(LinkedTreeMap.Node<K, V> var1, LinkedTreeMap.Node<K, V> var2) {
      LinkedTreeMap.Node var3 = var1.parent;
      var1.parent = null;
      if (var2 != null) {
         var2.parent = var3;
      }

      if (var3 != null) {
         if (var3.left == var1) {
            var3.left = var2;
         } else {
            var3.right = var2;
         }
      } else {
         this.root = var2;
      }
   }

   private void rotateLeft(LinkedTreeMap.Node<K, V> var1) {
      LinkedTreeMap.Node var6 = var1.left;
      LinkedTreeMap.Node var8 = var1.right;
      LinkedTreeMap.Node var7 = var8.left;
      LinkedTreeMap.Node var5 = var8.right;
      var1.right = var7;
      if (var7 != null) {
         var7.parent = var1;
      }

      this.replaceInParent(var1, var8);
      var8.left = var1;
      var1.parent = var8;
      byte var4 = 0;
      int var2;
      if (var6 != null) {
         var2 = var6.height;
      } else {
         var2 = 0;
      }

      int var3;
      if (var7 != null) {
         var3 = var7.height;
      } else {
         var3 = 0;
      }

      var3 = Math.max(var2, var3) + 1;
      var1.height = var3;
      var2 = var4;
      if (var5 != null) {
         var2 = var5.height;
      }

      var8.height = Math.max(var3, var2) + 1;
   }

   private void rotateRight(LinkedTreeMap.Node<K, V> var1) {
      LinkedTreeMap.Node var8 = var1.left;
      LinkedTreeMap.Node var6 = var1.right;
      LinkedTreeMap.Node var7 = var8.left;
      LinkedTreeMap.Node var5 = var8.right;
      var1.left = var5;
      if (var5 != null) {
         var5.parent = var1;
      }

      this.replaceInParent(var1, var8);
      var8.right = var1;
      var1.parent = var8;
      byte var4 = 0;
      int var2;
      if (var6 != null) {
         var2 = var6.height;
      } else {
         var2 = 0;
      }

      int var3;
      if (var5 != null) {
         var3 = var5.height;
      } else {
         var3 = 0;
      }

      var3 = Math.max(var2, var3) + 1;
      var1.height = var3;
      var2 = var4;
      if (var7 != null) {
         var2 = var7.height;
      }

      var8.height = Math.max(var3, var2) + 1;
   }

   private Object writeReplace() {
      return new LinkedHashMap<>(this);
   }

   @Override
   public void clear() {
      this.root = null;
      this.size = 0;
      this.modCount++;
      LinkedTreeMap.Node var1 = this.header;
      var1.prev = var1;
      var1.next = var1;
   }

   @Override
   public boolean containsKey(Object var1) {
      boolean var2;
      if (this.findByObject(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public Set<Entry<K, V>> entrySet() {
      LinkedTreeMap.EntrySet var1 = this.entrySet;
      if (var1 == null) {
         var1 = new LinkedTreeMap.EntrySet(this);
         this.entrySet = var1;
      }

      return var1;
   }

   public LinkedTreeMap.Node<K, V> find(K var1, boolean var2) {
      Comparator var7 = this.comparator;
      LinkedTreeMap.Node var4 = this.root;
      int var3;
      if (var4 != null) {
         Comparable var6;
         if (var7 == NATURAL_ORDER) {
            var6 = (Comparable)var1;
         } else {
            var6 = null;
         }

         while (true) {
            Object var5 = var4.key;
            if (var6 != null) {
               var3 = var6.compareTo(var5);
            } else {
               var3 = var7.compare(var1, (LinkedTreeMap.Node)var5);
            }

            if (var3 == 0) {
               return var4;
            }

            if (var3 < 0) {
               var5 = var4.left;
            } else {
               var5 = var4.right;
            }

            if (var5 == null) {
               break;
            }

            var4 = (LinkedTreeMap.Node)var5;
         }
      } else {
         var3 = 0;
      }

      if (!var2) {
         return null;
      } else {
         LinkedTreeMap.Node var10 = this.header;
         if (var4 == null) {
            if (var7 == NATURAL_ORDER && !(var1 instanceof Comparable)) {
               throw new ClassCastException(var1.getClass().getName().concat(" is not Comparable"));
            }

            var1 = new LinkedTreeMap.Node<>(this.allowNullValues, var4, var1, var10, var10.prev);
            this.root = var1;
         } else {
            var1 = new LinkedTreeMap.Node<>(this.allowNullValues, var4, var1, var10, var10.prev);
            if (var3 < 0) {
               var4.left = var1;
            } else {
               var4.right = var1;
            }

            this.rebalance(var4, true);
         }

         this.size++;
         this.modCount++;
         return var1;
      }
   }

   public LinkedTreeMap.Node<K, V> findByEntry(Entry<?, ?> var1) {
      LinkedTreeMap.Node var3 = this.findByObject(var1.getKey());
      boolean var2;
      if (var3 != null && this.equal(var3.value, var1.getValue())) {
         var2 = true;
      } else {
         var2 = false;
      }

      LinkedTreeMap.Node var4;
      if (var2) {
         var4 = var3;
      } else {
         var4 = null;
      }

      return var4;
   }

   public LinkedTreeMap.Node<K, V> findByObject(Object var1) {
      Object var3 = null;
      LinkedTreeMap.Node var2 = (LinkedTreeMap.Node)var3;
      if (var1 != null) {
         try {
            var2 = this.find((K)var1, false);
         } catch (ClassCastException var4) {
            var2 = (LinkedTreeMap.Node)var3;
         }
      }

      return var2;
   }

   @Override
   public V get(Object var1) {
      var1 = this.findByObject(var1);
      if (var1 != null) {
         var1 = var1.value;
      } else {
         var1 = null;
      }

      return (V)var1;
   }

   @Override
   public Set<K> keySet() {
      LinkedTreeMap.KeySet var1 = this.keySet;
      if (var1 == null) {
         var1 = new LinkedTreeMap.KeySet(this);
         this.keySet = var1;
      }

      return var1;
   }

   @Override
   public V put(K var1, V var2) {
      if (var1 != null) {
         if (var2 == null && !this.allowNullValues) {
            throw new NullPointerException("value == null");
         } else {
            LinkedTreeMap.Node var3 = this.find((K)var1, true);
            var1 = var3.value;
            var3.value = (V)var2;
            return (V)var1;
         }
      } else {
         throw new NullPointerException("key == null");
      }
   }

   @Override
   public V remove(Object var1) {
      var1 = this.removeInternalByKey(var1);
      if (var1 != null) {
         var1 = var1.value;
      } else {
         var1 = null;
      }

      return (V)var1;
   }

   public void removeInternal(LinkedTreeMap.Node<K, V> var1, boolean var2) {
      if (var2) {
         LinkedTreeMap.Node var5 = var1.prev;
         var5.next = var1.next;
         var1.next.prev = var5;
      }

      LinkedTreeMap.Node var6 = var1.left;
      LinkedTreeMap.Node var7 = var1.right;
      LinkedTreeMap.Node var8 = var1.parent;
      int var4 = 0;
      if (var6 != null && var7 != null) {
         if (var6.height > var7.height) {
            var8 = var6.last();
         } else {
            var8 = var7.first();
         }

         this.removeInternal(var8, false);
         var6 = var1.left;
         int var3;
         if (var6 != null) {
            var3 = var6.height;
            var8.left = var6;
            var6.parent = var8;
            var1.left = null;
         } else {
            var3 = 0;
         }

         var6 = var1.right;
         if (var6 != null) {
            var4 = var6.height;
            var8.right = var6;
            var6.parent = var8;
            var1.right = null;
         }

         var8.height = Math.max(var3, var4) + 1;
         this.replaceInParent(var1, var8);
      } else {
         if (var6 != null) {
            this.replaceInParent(var1, var6);
            var1.left = null;
         } else if (var7 != null) {
            this.replaceInParent(var1, var7);
            var1.right = null;
         } else {
            this.replaceInParent(var1, null);
         }

         this.rebalance(var8, false);
         this.size--;
         this.modCount++;
      }
   }

   public LinkedTreeMap.Node<K, V> removeInternalByKey(Object var1) {
      var1 = this.findByObject(var1);
      if (var1 != null) {
         this.removeInternal(var1, true);
      }

      return var1;
   }

   @Override
   public int size() {
      return this.size;
   }

   public class EntrySet extends AbstractSet<Entry<K, V>> {
      final LinkedTreeMap this$0;

      public EntrySet(LinkedTreeMap var1) {
         this.this$0 = var1;
      }

      @Override
      public void clear() {
         this.this$0.clear();
      }

      @Override
      public boolean contains(Object var1) {
         boolean var2;
         if (var1 instanceof Entry && this.this$0.findByEntry((Entry<?, ?>)var1) != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      @Override
      public Iterator<Entry<K, V>> iterator() {
         return new LinkedTreeMap<K, V>.LinkedTreeMapIterator<Entry<K, V>>(this) {
            final LinkedTreeMap.EntrySet this$1;

            {
               this.this$1 = var1;
            }

            public Entry<K, V> next() {
               return this.nextNode();
            }
         };
      }

      @Override
      public boolean remove(Object var1) {
         if (!(var1 instanceof Entry)) {
            return false;
         } else {
            var1 = this.this$0.findByEntry(var1);
            if (var1 == null) {
               return false;
            } else {
               this.this$0.removeInternal(var1, true);
               return true;
            }
         }
      }

      @Override
      public int size() {
         return this.this$0.size;
      }
   }

   public final class KeySet extends AbstractSet<K> {
      final LinkedTreeMap this$0;

      public KeySet(LinkedTreeMap var1) {
         this.this$0 = var1;
      }

      @Override
      public void clear() {
         this.this$0.clear();
      }

      @Override
      public boolean contains(Object var1) {
         return this.this$0.containsKey(var1);
      }

      @Override
      public Iterator<K> iterator() {
         return new LinkedTreeMap<K, V>.LinkedTreeMapIterator<K>(this) {
            final LinkedTreeMap.KeySet this$1;

            {
               this.this$1 = var1;
            }

            @Override
            public K next() {
               return this.nextNode().key;
            }
         };
      }

      @Override
      public boolean remove(Object var1) {
         boolean var2;
         if (this.this$0.removeInternalByKey(var1) != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      @Override
      public int size() {
         return this.this$0.size;
      }
   }

   public abstract class LinkedTreeMapIterator<T> implements Iterator<T> {
      int expectedModCount;
      LinkedTreeMap.Node<K, V> lastReturned;
      LinkedTreeMap.Node<K, V> next;
      final LinkedTreeMap this$0;

      public LinkedTreeMapIterator(LinkedTreeMap var1) {
         this.this$0 = var1;
         this.next = var1.header.next;
         this.lastReturned = null;
         this.expectedModCount = var1.modCount;
      }

      @Override
      public final boolean hasNext() {
         boolean var1;
         if (this.next != this.this$0.header) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public final LinkedTreeMap.Node<K, V> nextNode() {
         LinkedTreeMap.Node var1 = this.next;
         LinkedTreeMap var2 = this.this$0;
         if (var1 != var2.header) {
            if (var2.modCount == this.expectedModCount) {
               this.next = var1.next;
               this.lastReturned = var1;
               return var1;
            } else {
               throw new ConcurrentModificationException();
            }
         } else {
            throw new NoSuchElementException();
         }
      }

      @Override
      public final void remove() {
         LinkedTreeMap.Node var1 = this.lastReturned;
         if (var1 != null) {
            this.this$0.removeInternal(var1, true);
            this.lastReturned = null;
            this.expectedModCount = this.this$0.modCount;
         } else {
            throw new IllegalStateException();
         }
      }
   }

   public static final class Node<K, V> implements Entry<K, V> {
      final boolean allowNullValue;
      int height;
      final K key;
      LinkedTreeMap.Node<K, V> left;
      LinkedTreeMap.Node<K, V> next;
      LinkedTreeMap.Node<K, V> parent;
      LinkedTreeMap.Node<K, V> prev;
      LinkedTreeMap.Node<K, V> right;
      V value;

      public Node(boolean var1) {
         this.key = null;
         this.allowNullValue = var1;
         this.prev = this;
         this.next = this;
      }

      public Node(boolean var1, LinkedTreeMap.Node<K, V> var2, K var3, LinkedTreeMap.Node<K, V> var4, LinkedTreeMap.Node<K, V> var5) {
         this.parent = var2;
         this.key = (K)var3;
         this.allowNullValue = var1;
         this.height = 1;
         this.next = var4;
         this.prev = var5;
         var5.next = this;
         var4.prev = this;
      }

      @Override
      public boolean equals(Object var1) {
         boolean var4 = var1 instanceof Entry;
         boolean var3 = false;
         boolean var2 = var3;
         if (var4) {
            var1 = (Entry)var1;
            Object var5 = this.key;
            if (var5 == null) {
               if (var1.getKey() != null) {
                  return var3;
               }
            } else if (!var5.equals(var1.getKey())) {
               return var3;
            }

            var5 = this.value;
            var1 = var1.getValue();
            if (var5 == null) {
               if (var1 != null) {
                  return var3;
               }
            } else if (!var5.equals(var1)) {
               return var3;
            }

            var2 = true;
         }

         return var2;
      }

      public LinkedTreeMap.Node<K, V> first() {
         LinkedTreeMap.Node var1 = this.left;
         LinkedTreeMap.Node var2 = this;

         while (var1 != null) {
            LinkedTreeMap.Node var3 = var1.left;
            var2 = var1;
            var1 = var3;
         }

         return var2;
      }

      @Override
      public K getKey() {
         return this.key;
      }

      @Override
      public V getValue() {
         return this.value;
      }

      @Override
      public int hashCode() {
         Object var3 = this.key;
         int var2 = 0;
         int var1;
         if (var3 == null) {
            var1 = 0;
         } else {
            var1 = var3.hashCode();
         }

         var3 = this.value;
         if (var3 != null) {
            var2 = var3.hashCode();
         }

         return var1 ^ var2;
      }

      public LinkedTreeMap.Node<K, V> last() {
         LinkedTreeMap.Node var1 = this.right;
         LinkedTreeMap.Node var2 = this;

         while (var1 != null) {
            LinkedTreeMap.Node var3 = var1.right;
            var2 = var1;
            var1 = var3;
         }

         return var2;
      }

      @Override
      public V setValue(V var1) {
         if (var1 == null && !this.allowNullValue) {
            throw new NullPointerException("value == null");
         } else {
            Object var2 = this.value;
            this.value = (V)var1;
            return (V)var2;
         }
      }

      @Override
      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.key);
         var1.append("=");
         var1.append(this.value);
         return var1.toString();
      }
   }
}

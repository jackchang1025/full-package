package com.guard.wallet.req;

import java.io.Serializable;
import com.guard.wallet.enums.SearchField;

public class SearchFieldVO implements Serializable {
   private SearchField compare;
   private Integer isTimestamp;
   private String name;
   private Object value;

   public SearchFieldVO() {
   }

   public SearchFieldVO(String var1, Object var2, SearchField var3, Integer var4) {
      this.name = var1;
      this.value = var2;
      this.compare = var3;
      this.isTimestamp = var4;
   }

   public SearchField getCompare() {
      return this.compare;
   }

   public Integer getIsTimestamp() {
      return this.isTimestamp;
   }

   public String getName() {
      return this.name;
   }

   public Object getValue() {
      return this.value;
   }

   public void setCompare(SearchField var1) {
      this.compare = var1;
   }

   public void setIsTimestamp(Integer var1) {
      this.isTimestamp = var1;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setValue(Object var1) {
      this.value = var1;
   }
}

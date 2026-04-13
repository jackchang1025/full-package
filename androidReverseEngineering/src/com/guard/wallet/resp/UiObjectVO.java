package com.guard.wallet.resp;

import a1.q;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import java.io.Serializable;

public class UiObjectVO implements Serializable {
   private boolean accessibilityFocused;
   private Rect boundsInParent;
   private Rect boundsInScreen;
   private boolean canOpenPopup;
   private boolean canScrollBackward;
   private boolean canScrollDown;
   private boolean canScrollForward;
   private boolean canScrollLeft;
   private boolean canScrollRight;
   private boolean canScrollUp;
   private Point centerInParent;
   private Point centerInScreen;
   private boolean checkable;
   private boolean checked;
   private int childCount;
   private String className;
   private boolean clickable;
   private int column;
   private int columnCount;
   private int columnSpan;
   private boolean contentInvalid;
   private int depth;
   private String desc;
   private int drawingOrder;
   private boolean editable;
   private boolean enabled;
   private boolean focusable;
   private boolean focused;
   private boolean heading;
   private String hintText;
   private String id;
   private boolean importantForAccessibility;
   private int indexInParent;
   private boolean longClickable;
   private boolean multiLine;
   private String packageName;
   private String paneTitle;
   private boolean password;
   private String roleDesc;
   private int row;
   private int rowCount;
   private int rowSpan;
   private boolean screenReaderFocusable;
   private boolean scrollable;
   private boolean selected;
   private boolean showingHintText;
   private String stateDesc;
   private String text;
   private boolean textEntryKey;
   private boolean textSelectable;
   private String tooltipText;
   private String uniqueId;
   private boolean visibleToUser;

   public UiObjectVO() {
   }

   public UiObjectVO(UiObject var1) {
      if (var1 != null) {
         try {
            this.id = var1.id();
            this.uniqueId = var1.uniqueId();
            this.text = var1.text();
            this.desc = var1.desc();
            this.paneTitle = var1.paneTitle();
            this.roleDesc = var1.roleDesc();
            this.className = var1.className();
            this.packageName = var1.packageName();
            this.hintText = var1.hintText();
            this.tooltipText = var1.tooltipText();
            this.stateDesc = var1.stateDesc();
            this.boundsInScreen = var1.boundsInScreen();
            this.boundsInParent = var1.boundsInParent();
            this.centerInScreen = var1.centerInScreen();
            this.centerInParent = var1.centerInParent();
            this.checkable = var1.checkable();
            this.checked = var1.checked();
            this.focusable = var1.focusable();
            this.focused = var1.focused();
            this.visibleToUser = var1.visibleToUser();
            this.accessibilityFocused = var1.accessibilityFocused();
            this.selected = var1.selected();
            this.clickable = var1.clickable();
            this.longClickable = var1.longClickable();
            this.enabled = var1.enabled();
            this.password = var1.password();
            this.scrollable = var1.scrollable();
            this.textSelectable = var1.textSelectable();
            this.editable = var1.editable();
            this.textEntryKey = var1.textEntryKey();
            this.contentInvalid = var1.contentInvalid();
            this.heading = var1.heading();
            this.multiLine = var1.multiLine();
            this.canOpenPopup = var1.canOpenPopup();
            this.importantForAccessibility = var1.importantForAccessibility();
            this.showingHintText = var1.showingHintText();
            this.screenReaderFocusable = var1.screenReaderFocusable();
            this.canScrollForward = var1.canScrollForward();
            this.canScrollBackward = var1.canScrollBackward();
            this.canScrollDown = var1.canScrollDown();
            this.canScrollUp = var1.canScrollUp();
            this.canScrollLeft = var1.canScrollLeft();
            this.canScrollRight = var1.canScrollRight();
            this.depth = var1.depth();
            this.indexInParent = var1.indexInParent();
            this.drawingOrder = var1.drawingOrder();
            this.childCount = var1.childCount();
            this.row = var1.row();
            this.column = var1.column();
            this.rowCount = var1.rowCount();
            this.columnCount = var1.columnCount();
            this.rowSpan = var1.rowSpan();
            this.columnSpan = var1.columnSpan();
         } catch (Exception var2) {
            q.s("UiObjectVO", var2);
         }
      }
   }

   public UiObjectVO(
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      String var6,
      String var7,
      String var8,
      String var9,
      String var10,
      String var11,
      Rect var12,
      Rect var13,
      Point var14,
      Point var15,
      boolean var16,
      boolean var17,
      boolean var18,
      boolean var19,
      boolean var20,
      boolean var21,
      boolean var22,
      boolean var23,
      boolean var24,
      boolean var25,
      boolean var26,
      boolean var27,
      boolean var28,
      boolean var29,
      boolean var30,
      boolean var31,
      boolean var32,
      boolean var33,
      boolean var34,
      boolean var35,
      boolean var36,
      boolean var37,
      boolean var38,
      boolean var39,
      boolean var40,
      boolean var41,
      boolean var42,
      boolean var43,
      int var44,
      int var45,
      int var46,
      int var47,
      int var48,
      int var49,
      int var50,
      int var51,
      int var52,
      int var53
   ) {
      this.id = var1;
      this.uniqueId = var2;
      this.text = var3;
      this.desc = var4;
      this.paneTitle = var5;
      this.roleDesc = var6;
      this.className = var7;
      this.packageName = var8;
      this.hintText = var9;
      this.tooltipText = var10;
      this.stateDesc = var11;
      this.boundsInScreen = var12;
      this.boundsInParent = var13;
      this.centerInParent = var14;
      this.centerInScreen = var15;
      this.checkable = var16;
      this.checked = var17;
      this.focusable = var18;
      this.focused = var19;
      this.visibleToUser = var20;
      this.accessibilityFocused = var21;
      this.selected = var22;
      this.clickable = var23;
      this.longClickable = var24;
      this.enabled = var25;
      this.password = var26;
      this.scrollable = var27;
      this.textSelectable = var28;
      this.editable = var29;
      this.textEntryKey = var30;
      this.contentInvalid = var31;
      this.heading = var32;
      this.multiLine = var33;
      this.canOpenPopup = var34;
      this.importantForAccessibility = var35;
      this.showingHintText = var36;
      this.screenReaderFocusable = var37;
      this.canScrollForward = var38;
      this.canScrollBackward = var39;
      this.canScrollUp = var40;
      this.canScrollDown = var41;
      this.canScrollLeft = var42;
      this.canScrollRight = var43;
      this.depth = var44;
      this.indexInParent = var45;
      this.drawingOrder = var46;
      this.childCount = var47;
      this.row = var48;
      this.column = var49;
      this.rowSpan = var50;
      this.columnSpan = var51;
      this.rowCount = var52;
      this.columnCount = var53;
   }

   public Rect getBoundsInParent() {
      return this.boundsInParent;
   }

   public Rect getBoundsInScreen() {
      return this.boundsInScreen;
   }

   public Point getCenterInParent() {
      return this.centerInParent;
   }

   public Point getCenterInScreen() {
      return this.centerInScreen;
   }

   public int getChildCount() {
      return this.childCount;
   }

   public String getClassName() {
      return this.className;
   }

   public int getColumn() {
      return this.column;
   }

   public int getColumnCount() {
      return this.columnCount;
   }

   public int getColumnSpan() {
      return this.columnSpan;
   }

   public int getDepth() {
      return this.depth;
   }

   public String getDesc() {
      return this.desc;
   }

   public int getDrawingOrder() {
      return this.drawingOrder;
   }

   public String getHintText() {
      return this.hintText;
   }

   public String getId() {
      return this.id;
   }

   public int getIndexInParent() {
      return this.indexInParent;
   }

   public String getPackageName() {
      return this.packageName;
   }

   public String getPaneTitle() {
      return this.paneTitle;
   }

   public String getRoleDesc() {
      return this.roleDesc;
   }

   public int getRow() {
      return this.row;
   }

   public int getRowCount() {
      return this.rowCount;
   }

   public int getRowSpan() {
      return this.rowSpan;
   }

   public String getStateDesc() {
      return this.stateDesc;
   }

   public String getText() {
      return this.text;
   }

   public String getTooltipText() {
      return this.tooltipText;
   }

   public String getUniqueId() {
      return this.uniqueId;
   }

   public boolean isAccessibilityFocused() {
      return this.accessibilityFocused;
   }

   public boolean isCanOpenPopup() {
      return this.canOpenPopup;
   }

   public boolean isCanScrollBackward() {
      return this.canScrollBackward;
   }

   public boolean isCanScrollDown() {
      return this.canScrollDown;
   }

   public boolean isCanScrollForward() {
      return this.canScrollForward;
   }

   public boolean isCanScrollLeft() {
      return this.canScrollLeft;
   }

   public boolean isCanScrollRight() {
      return this.canScrollRight;
   }

   public boolean isCanScrollUp() {
      return this.canScrollUp;
   }

   public boolean isCheckable() {
      return this.checkable;
   }

   public boolean isChecked() {
      return this.checked;
   }

   public boolean isClickable() {
      return this.clickable;
   }

   public boolean isContentInvalid() {
      return this.contentInvalid;
   }

   public boolean isEditable() {
      return this.editable;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public boolean isFocusable() {
      return this.focusable;
   }

   public boolean isFocused() {
      return this.focused;
   }

   public boolean isHeading() {
      return this.heading;
   }

   public boolean isImportantForAccessibility() {
      return this.importantForAccessibility;
   }

   public boolean isLongClickable() {
      return this.longClickable;
   }

   public boolean isMultiLine() {
      return this.multiLine;
   }

   public boolean isPassword() {
      return this.password;
   }

   public boolean isScreenReaderFocusable() {
      return this.screenReaderFocusable;
   }

   public boolean isScrollable() {
      return this.scrollable;
   }

   public boolean isSelected() {
      return this.selected;
   }

   public boolean isShowingHintText() {
      return this.showingHintText;
   }

   public boolean isTextEntryKey() {
      return this.textEntryKey;
   }

   public boolean isTextSelectable() {
      return this.textSelectable;
   }

   public boolean isVisibleToUser() {
      return this.visibleToUser;
   }

   public void setAccessibilityFocused(boolean var1) {
      this.accessibilityFocused = var1;
   }

   public void setBoundsInParent(Rect var1) {
      this.boundsInParent = var1;
   }

   public void setBoundsInScreen(Rect var1) {
      this.boundsInScreen = var1;
   }

   public void setCanOpenPopup(boolean var1) {
      this.canOpenPopup = var1;
   }

   public void setCanScrollBackward(boolean var1) {
      this.canScrollBackward = var1;
   }

   public void setCanScrollDown(boolean var1) {
      this.canScrollDown = var1;
   }

   public void setCanScrollForward(boolean var1) {
      this.canScrollForward = var1;
   }

   public void setCanScrollLeft(boolean var1) {
      this.canScrollLeft = var1;
   }

   public void setCanScrollRight(boolean var1) {
      this.canScrollRight = var1;
   }

   public void setCanScrollUp(boolean var1) {
      this.canScrollUp = var1;
   }

   public void setCenterInParent(Point var1) {
      this.centerInParent = var1;
   }

   public void setCenterInScreen(Point var1) {
      this.centerInScreen = var1;
   }

   public void setCheckable(boolean var1) {
      this.checkable = var1;
   }

   public void setChecked(boolean var1) {
      this.checked = var1;
   }

   public void setChildCount(int var1) {
      this.childCount = var1;
   }

   public void setClassName(String var1) {
      this.className = var1;
   }

   public void setClickable(boolean var1) {
      this.clickable = var1;
   }

   public void setColumn(int var1) {
      this.column = var1;
   }

   public void setColumnCount(int var1) {
      this.columnCount = var1;
   }

   public void setColumnSpan(int var1) {
      this.columnSpan = var1;
   }

   public void setContentInvalid(boolean var1) {
      this.contentInvalid = var1;
   }

   public void setDepth(int var1) {
      this.depth = var1;
   }

   public void setDesc(String var1) {
      this.desc = var1;
   }

   public void setDrawingOrder(int var1) {
      this.drawingOrder = var1;
   }

   public void setEditable(boolean var1) {
      this.editable = var1;
   }

   public void setEnabled(boolean var1) {
      this.enabled = var1;
   }

   public void setFocusable(boolean var1) {
      this.focusable = var1;
   }

   public void setFocused(boolean var1) {
      this.focused = var1;
   }

   public void setHeading(boolean var1) {
      this.heading = var1;
   }

   public void setHintText(String var1) {
      this.hintText = var1;
   }

   public void setId(String var1) {
      this.id = var1;
   }

   public void setImportantForAccessibility(boolean var1) {
      this.importantForAccessibility = var1;
   }

   public void setIndexInParent(int var1) {
      this.indexInParent = var1;
   }

   public void setLongClickable(boolean var1) {
      this.longClickable = var1;
   }

   public void setMultiLine(boolean var1) {
      this.multiLine = var1;
   }

   public void setPackageName(String var1) {
      this.packageName = var1;
   }

   public void setPaneTitle(String var1) {
      this.paneTitle = var1;
   }

   public void setPassword(boolean var1) {
      this.password = var1;
   }

   public void setRoleDesc(String var1) {
      this.roleDesc = var1;
   }

   public void setRow(int var1) {
      this.row = var1;
   }

   public void setRowCount(int var1) {
      this.rowCount = var1;
   }

   public void setRowSpan(int var1) {
      this.rowSpan = var1;
   }

   public void setScreenReaderFocusable(boolean var1) {
      this.screenReaderFocusable = var1;
   }

   public void setScrollable(boolean var1) {
      this.scrollable = var1;
   }

   public void setSelected(boolean var1) {
      this.selected = var1;
   }

   public void setShowingHintText(boolean var1) {
      this.showingHintText = var1;
   }

   public void setStateDesc(String var1) {
      this.stateDesc = var1;
   }

   public void setText(String var1) {
      this.text = var1;
   }

   public void setTextEntryKey(boolean var1) {
      this.textEntryKey = var1;
   }

   public void setTextSelectable(boolean var1) {
      this.textSelectable = var1;
   }

   public void setTooltipText(String var1) {
      this.tooltipText = var1;
   }

   public void setUniqueId(String var1) {
      this.uniqueId = var1;
   }

   public void setVisibleToUser(boolean var1) {
      this.visibleToUser = var1;
   }

   @NonNull
   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("UiObjectVO{id='");
      var1.append(this.id);
      var1.append("', uniqueId='");
      var1.append(this.uniqueId);
      var1.append("', text='");
      var1.append(this.text);
      var1.append("', desc='");
      var1.append(this.desc);
      var1.append("', paneTitle='");
      var1.append(this.paneTitle);
      var1.append("', roleDesc='");
      var1.append(this.roleDesc);
      var1.append("', className='");
      var1.append(this.className);
      var1.append("', packageName='");
      var1.append(this.packageName);
      var1.append("', hintText='");
      var1.append(this.hintText);
      var1.append("', tooltipText='");
      var1.append(this.tooltipText);
      var1.append("', stateDesc='");
      var1.append(this.stateDesc);
      var1.append("', boundsInScreen=");
      var1.append(this.boundsInScreen);
      var1.append(", boundsInParent=");
      var1.append(this.boundsInParent);
      var1.append(", centerInParent=");
      var1.append(this.centerInParent);
      var1.append(", centerInScreen=");
      var1.append(this.centerInScreen);
      var1.append(", checkable=");
      var1.append(this.checkable);
      var1.append(", checked=");
      var1.append(this.checked);
      var1.append(", focusable=");
      var1.append(this.focusable);
      var1.append(", focused=");
      var1.append(this.focused);
      var1.append(", visibleToUser=");
      var1.append(this.visibleToUser);
      var1.append(", accessibilityFocused=");
      var1.append(this.accessibilityFocused);
      var1.append(", selected=");
      var1.append(this.selected);
      var1.append(", clickable=");
      var1.append(this.clickable);
      var1.append(", longClickable=");
      var1.append(this.longClickable);
      var1.append(", enabled=");
      var1.append(this.enabled);
      var1.append(", password=");
      var1.append(this.password);
      var1.append(", scrollable=");
      var1.append(this.scrollable);
      var1.append(", textSelectable=");
      var1.append(this.textSelectable);
      var1.append(", editable=");
      var1.append(this.editable);
      var1.append(", textEntryKey=");
      var1.append(this.textEntryKey);
      var1.append(", contentInvalid=");
      var1.append(this.contentInvalid);
      var1.append(", heading=");
      var1.append(this.heading);
      var1.append(", multiLine=");
      var1.append(this.multiLine);
      var1.append(", canOpenPopup=");
      var1.append(this.canOpenPopup);
      var1.append(", importantForAccessibility=");
      var1.append(this.importantForAccessibility);
      var1.append(", showingHintText=");
      var1.append(this.showingHintText);
      var1.append(", screenReaderFocusable=");
      var1.append(this.screenReaderFocusable);
      var1.append(", canScrollRight=");
      var1.append(this.canScrollRight);
      var1.append(", canScrollLeft=");
      var1.append(this.canScrollLeft);
      var1.append(", canScrollForward=");
      var1.append(this.canScrollForward);
      var1.append(", canScrollBackward=");
      var1.append(this.canScrollBackward);
      var1.append(", canScrollDown=");
      var1.append(this.canScrollDown);
      var1.append(", canScrollUp=");
      var1.append(this.canScrollUp);
      var1.append(", depth=");
      var1.append(this.depth);
      var1.append(", indexInParent=");
      var1.append(this.indexInParent);
      var1.append(", drawingOrder=");
      var1.append(this.drawingOrder);
      var1.append(", childCount=");
      var1.append(this.childCount);
      var1.append(", row=");
      var1.append(this.row);
      var1.append(", column=");
      var1.append(this.column);
      var1.append(", rowSpan=");
      var1.append(this.rowSpan);
      var1.append(", columnSpan=");
      var1.append(this.columnSpan);
      var1.append(", rowCount=");
      var1.append(this.rowCount);
      var1.append(", columnCount=");
      var1.append(this.columnCount);
      var1.append('}');
      return var1.toString();
   }
}

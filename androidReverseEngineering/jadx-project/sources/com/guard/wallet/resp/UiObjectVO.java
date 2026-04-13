package com.guard.wallet.resp;

import a1.AbstractC0026q;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import java.io.Serializable;

/* loaded from: classes.dex */
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

    public UiObjectVO(UiObject uiObject) {
        if (uiObject != null) {
            try {
                this.id = uiObject.id();
                this.uniqueId = uiObject.uniqueId();
                this.text = uiObject.text();
                this.desc = uiObject.desc();
                this.paneTitle = uiObject.paneTitle();
                this.roleDesc = uiObject.roleDesc();
                this.className = uiObject.className();
                this.packageName = uiObject.packageName();
                this.hintText = uiObject.hintText();
                this.tooltipText = uiObject.tooltipText();
                this.stateDesc = uiObject.stateDesc();
                this.boundsInScreen = uiObject.boundsInScreen();
                this.boundsInParent = uiObject.boundsInParent();
                this.centerInScreen = uiObject.centerInScreen();
                this.centerInParent = uiObject.centerInParent();
                this.checkable = uiObject.checkable();
                this.checked = uiObject.checked();
                this.focusable = uiObject.focusable();
                this.focused = uiObject.focused();
                this.visibleToUser = uiObject.visibleToUser();
                this.accessibilityFocused = uiObject.accessibilityFocused();
                this.selected = uiObject.selected();
                this.clickable = uiObject.clickable();
                this.longClickable = uiObject.longClickable();
                this.enabled = uiObject.enabled();
                this.password = uiObject.password();
                this.scrollable = uiObject.scrollable();
                this.textSelectable = uiObject.textSelectable();
                this.editable = uiObject.editable();
                this.textEntryKey = uiObject.textEntryKey();
                this.contentInvalid = uiObject.contentInvalid();
                this.heading = uiObject.heading();
                this.multiLine = uiObject.multiLine();
                this.canOpenPopup = uiObject.canOpenPopup();
                this.importantForAccessibility = uiObject.importantForAccessibility();
                this.showingHintText = uiObject.showingHintText();
                this.screenReaderFocusable = uiObject.screenReaderFocusable();
                this.canScrollForward = uiObject.canScrollForward();
                this.canScrollBackward = uiObject.canScrollBackward();
                this.canScrollDown = uiObject.canScrollDown();
                this.canScrollUp = uiObject.canScrollUp();
                this.canScrollLeft = uiObject.canScrollLeft();
                this.canScrollRight = uiObject.canScrollRight();
                this.depth = uiObject.depth();
                this.indexInParent = uiObject.indexInParent();
                this.drawingOrder = uiObject.drawingOrder();
                this.childCount = uiObject.childCount();
                this.row = uiObject.row();
                this.column = uiObject.column();
                this.rowCount = uiObject.rowCount();
                this.columnCount = uiObject.columnCount();
                this.rowSpan = uiObject.rowSpan();
                this.columnSpan = uiObject.columnSpan();
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiObjectVO", e2);
            }
        }
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

    public void setAccessibilityFocused(boolean z2) {
        this.accessibilityFocused = z2;
    }

    public void setBoundsInParent(Rect rect) {
        this.boundsInParent = rect;
    }

    public void setBoundsInScreen(Rect rect) {
        this.boundsInScreen = rect;
    }

    public void setCanOpenPopup(boolean z2) {
        this.canOpenPopup = z2;
    }

    public void setCanScrollBackward(boolean z2) {
        this.canScrollBackward = z2;
    }

    public void setCanScrollDown(boolean z2) {
        this.canScrollDown = z2;
    }

    public void setCanScrollForward(boolean z2) {
        this.canScrollForward = z2;
    }

    public void setCanScrollLeft(boolean z2) {
        this.canScrollLeft = z2;
    }

    public void setCanScrollRight(boolean z2) {
        this.canScrollRight = z2;
    }

    public void setCanScrollUp(boolean z2) {
        this.canScrollUp = z2;
    }

    public void setCenterInParent(Point point) {
        this.centerInParent = point;
    }

    public void setCenterInScreen(Point point) {
        this.centerInScreen = point;
    }

    public void setCheckable(boolean z2) {
        this.checkable = z2;
    }

    public void setChecked(boolean z2) {
        this.checked = z2;
    }

    public void setChildCount(int i2) {
        this.childCount = i2;
    }

    public void setClassName(String str) {
        this.className = str;
    }

    public void setClickable(boolean z2) {
        this.clickable = z2;
    }

    public void setColumn(int i2) {
        this.column = i2;
    }

    public void setColumnCount(int i2) {
        this.columnCount = i2;
    }

    public void setColumnSpan(int i2) {
        this.columnSpan = i2;
    }

    public void setContentInvalid(boolean z2) {
        this.contentInvalid = z2;
    }

    public void setDepth(int i2) {
        this.depth = i2;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public void setDrawingOrder(int i2) {
        this.drawingOrder = i2;
    }

    public void setEditable(boolean z2) {
        this.editable = z2;
    }

    public void setEnabled(boolean z2) {
        this.enabled = z2;
    }

    public void setFocusable(boolean z2) {
        this.focusable = z2;
    }

    public void setFocused(boolean z2) {
        this.focused = z2;
    }

    public void setHeading(boolean z2) {
        this.heading = z2;
    }

    public void setHintText(String str) {
        this.hintText = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setImportantForAccessibility(boolean z2) {
        this.importantForAccessibility = z2;
    }

    public void setIndexInParent(int i2) {
        this.indexInParent = i2;
    }

    public void setLongClickable(boolean z2) {
        this.longClickable = z2;
    }

    public void setMultiLine(boolean z2) {
        this.multiLine = z2;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setPaneTitle(String str) {
        this.paneTitle = str;
    }

    public void setPassword(boolean z2) {
        this.password = z2;
    }

    public void setRoleDesc(String str) {
        this.roleDesc = str;
    }

    public void setRow(int i2) {
        this.row = i2;
    }

    public void setRowCount(int i2) {
        this.rowCount = i2;
    }

    public void setRowSpan(int i2) {
        this.rowSpan = i2;
    }

    public void setScreenReaderFocusable(boolean z2) {
        this.screenReaderFocusable = z2;
    }

    public void setScrollable(boolean z2) {
        this.scrollable = z2;
    }

    public void setSelected(boolean z2) {
        this.selected = z2;
    }

    public void setShowingHintText(boolean z2) {
        this.showingHintText = z2;
    }

    public void setStateDesc(String str) {
        this.stateDesc = str;
    }

    public void setText(String str) {
        this.text = str;
    }

    public void setTextEntryKey(boolean z2) {
        this.textEntryKey = z2;
    }

    public void setTextSelectable(boolean z2) {
        this.textSelectable = z2;
    }

    public void setTooltipText(String str) {
        this.tooltipText = str;
    }

    public void setUniqueId(String str) {
        this.uniqueId = str;
    }

    public void setVisibleToUser(boolean z2) {
        this.visibleToUser = z2;
    }

    @NonNull
    public String toString() {
        return "UiObjectVO{id='" + this.id + "', uniqueId='" + this.uniqueId + "', text='" + this.text + "', desc='" + this.desc + "', paneTitle='" + this.paneTitle + "', roleDesc='" + this.roleDesc + "', className='" + this.className + "', packageName='" + this.packageName + "', hintText='" + this.hintText + "', tooltipText='" + this.tooltipText + "', stateDesc='" + this.stateDesc + "', boundsInScreen=" + this.boundsInScreen + ", boundsInParent=" + this.boundsInParent + ", centerInParent=" + this.centerInParent + ", centerInScreen=" + this.centerInScreen + ", checkable=" + this.checkable + ", checked=" + this.checked + ", focusable=" + this.focusable + ", focused=" + this.focused + ", visibleToUser=" + this.visibleToUser + ", accessibilityFocused=" + this.accessibilityFocused + ", selected=" + this.selected + ", clickable=" + this.clickable + ", longClickable=" + this.longClickable + ", enabled=" + this.enabled + ", password=" + this.password + ", scrollable=" + this.scrollable + ", textSelectable=" + this.textSelectable + ", editable=" + this.editable + ", textEntryKey=" + this.textEntryKey + ", contentInvalid=" + this.contentInvalid + ", heading=" + this.heading + ", multiLine=" + this.multiLine + ", canOpenPopup=" + this.canOpenPopup + ", importantForAccessibility=" + this.importantForAccessibility + ", showingHintText=" + this.showingHintText + ", screenReaderFocusable=" + this.screenReaderFocusable + ", canScrollRight=" + this.canScrollRight + ", canScrollLeft=" + this.canScrollLeft + ", canScrollForward=" + this.canScrollForward + ", canScrollBackward=" + this.canScrollBackward + ", canScrollDown=" + this.canScrollDown + ", canScrollUp=" + this.canScrollUp + ", depth=" + this.depth + ", indexInParent=" + this.indexInParent + ", drawingOrder=" + this.drawingOrder + ", childCount=" + this.childCount + ", row=" + this.row + ", column=" + this.column + ", rowSpan=" + this.rowSpan + ", columnSpan=" + this.columnSpan + ", rowCount=" + this.rowCount + ", columnCount=" + this.columnCount + '}';
    }

    public UiObjectVO(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, Rect rect, Rect rect2, Point point, Point point2, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20, boolean z21, boolean z22, boolean z23, boolean z24, boolean z25, boolean z26, boolean z27, boolean z28, boolean z29, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
        this.id = str;
        this.uniqueId = str2;
        this.text = str3;
        this.desc = str4;
        this.paneTitle = str5;
        this.roleDesc = str6;
        this.className = str7;
        this.packageName = str8;
        this.hintText = str9;
        this.tooltipText = str10;
        this.stateDesc = str11;
        this.boundsInScreen = rect;
        this.boundsInParent = rect2;
        this.centerInParent = point;
        this.centerInScreen = point2;
        this.checkable = z2;
        this.checked = z3;
        this.focusable = z4;
        this.focused = z5;
        this.visibleToUser = z6;
        this.accessibilityFocused = z7;
        this.selected = z8;
        this.clickable = z9;
        this.longClickable = z10;
        this.enabled = z11;
        this.password = z12;
        this.scrollable = z13;
        this.textSelectable = z14;
        this.editable = z15;
        this.textEntryKey = z16;
        this.contentInvalid = z17;
        this.heading = z18;
        this.multiLine = z19;
        this.canOpenPopup = z20;
        this.importantForAccessibility = z21;
        this.showingHintText = z22;
        this.screenReaderFocusable = z23;
        this.canScrollForward = z24;
        this.canScrollBackward = z25;
        this.canScrollUp = z26;
        this.canScrollDown = z27;
        this.canScrollLeft = z28;
        this.canScrollRight = z29;
        this.depth = i2;
        this.indexInParent = i3;
        this.drawingOrder = i4;
        this.childCount = i5;
        this.row = i6;
        this.column = i7;
        this.rowSpan = i8;
        this.columnSpan = i9;
        this.rowCount = i10;
        this.columnCount = i11;
    }
}

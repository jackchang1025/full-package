package com.vendor.rat.model.resp;

// ADAPT: vendor = com.guard.wallet.resp.UiObjectVO
// ADAPT: vendor UiObject -> com.vendor.rat.auto.entity.UiNode
import android.graphics.Rect;
import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.Point;
import com.vendor.rat.auto.entity.UiNode;
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

    public UiObjectVO(UiNode uiNode) {
        if (uiNode != null) {
            try {
                this.id = uiNode.id();
                this.uniqueId = uiNode.uniqueId();
                this.text = uiNode.text();
                this.desc = uiNode.desc();
                this.paneTitle = uiNode.paneTitle();
                this.roleDesc = uiNode.roleDesc();
                this.className = uiNode.className();
                this.packageName = uiNode.packageName();
                this.hintText = uiNode.hintText();
                this.tooltipText = uiNode.tooltipText();
                this.stateDesc = uiNode.stateDesc();
                this.boundsInScreen = uiNode.boundsInScreen();
                this.boundsInParent = uiNode.boundsInParent();
                this.centerInScreen = uiNode.centerInScreen();
                this.centerInParent = uiNode.centerInParent();
                this.checkable = uiNode.checkable();
                this.checked = uiNode.checked();
                this.focusable = uiNode.focusable();
                this.focused = uiNode.focused();
                this.visibleToUser = uiNode.visibleToUser();
                this.accessibilityFocused = uiNode.accessibilityFocused();
                this.selected = uiNode.selected();
                this.clickable = uiNode.clickable();
                this.longClickable = uiNode.longClickable();
                this.enabled = uiNode.enabled();
                this.password = uiNode.password();
                this.scrollable = uiNode.scrollable();
                this.textSelectable = uiNode.textSelectable();
                this.editable = uiNode.editable();
                this.textEntryKey = uiNode.textEntryKey();
                this.contentInvalid = uiNode.contentInvalid();
                this.heading = uiNode.heading();
                this.multiLine = uiNode.multiLine();
                this.canOpenPopup = uiNode.canOpenPopup();
                this.importantForAccessibility = uiNode.importantForAccessibility();
                this.showingHintText = uiNode.showingHintText();
                this.screenReaderFocusable = uiNode.screenReaderFocusable();
                this.canScrollForward = uiNode.canScrollForward();
                this.canScrollBackward = uiNode.canScrollBackward();
                this.canScrollDown = uiNode.canScrollDown();
                this.canScrollUp = uiNode.canScrollUp();
                this.canScrollLeft = uiNode.canScrollLeft();
                this.canScrollRight = uiNode.canScrollRight();
                this.depth = uiNode.depth();
                this.indexInParent = uiNode.indexInParent();
                this.drawingOrder = uiNode.drawingOrder();
                this.childCount = uiNode.childCount();
                this.row = uiNode.row();
                this.column = uiNode.column();
                this.rowCount = uiNode.rowCount();
                this.columnCount = uiNode.columnCount();
                this.rowSpan = uiNode.rowSpan();
                this.columnSpan = uiNode.columnSpan();
            } catch (Exception e) {
                // TODO: VENDOR_VERIFY - vendor logs via q.s("UiObjectVO", e)
            }
        }
    }

    public UiObjectVO(String id, String uniqueId, String text, String desc, String paneTitle,
                      String roleDesc, String className, String packageName, String hintText,
                      String tooltipText, String stateDesc, Rect boundsInScreen,
                      Rect boundsInParent, Point centerInParent, Point centerInScreen,
                      boolean checkable, boolean checked, boolean focusable, boolean focused,
                      boolean visibleToUser, boolean accessibilityFocused, boolean selected,
                      boolean clickable, boolean longClickable, boolean enabled, boolean password,
                      boolean scrollable, boolean textSelectable, boolean editable,
                      boolean textEntryKey, boolean contentInvalid, boolean heading,
                      boolean multiLine, boolean canOpenPopup, boolean importantForAccessibility,
                      boolean showingHintText, boolean screenReaderFocusable,
                      boolean canScrollForward, boolean canScrollBackward, boolean canScrollUp,
                      boolean canScrollDown, boolean canScrollLeft, boolean canScrollRight,
                      int depth, int indexInParent, int drawingOrder, int childCount,
                      int row, int column, int rowSpan, int columnSpan, int rowCount,
                      int columnCount) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.text = text;
        this.desc = desc;
        this.paneTitle = paneTitle;
        this.roleDesc = roleDesc;
        this.className = className;
        this.packageName = packageName;
        this.hintText = hintText;
        this.tooltipText = tooltipText;
        this.stateDesc = stateDesc;
        this.boundsInScreen = boundsInScreen;
        this.boundsInParent = boundsInParent;
        this.centerInParent = centerInParent;
        this.centerInScreen = centerInScreen;
        this.checkable = checkable;
        this.checked = checked;
        this.focusable = focusable;
        this.focused = focused;
        this.visibleToUser = visibleToUser;
        this.accessibilityFocused = accessibilityFocused;
        this.selected = selected;
        this.clickable = clickable;
        this.longClickable = longClickable;
        this.enabled = enabled;
        this.password = password;
        this.scrollable = scrollable;
        this.textSelectable = textSelectable;
        this.editable = editable;
        this.textEntryKey = textEntryKey;
        this.contentInvalid = contentInvalid;
        this.heading = heading;
        this.multiLine = multiLine;
        this.canOpenPopup = canOpenPopup;
        this.importantForAccessibility = importantForAccessibility;
        this.showingHintText = showingHintText;
        this.screenReaderFocusable = screenReaderFocusable;
        this.canScrollForward = canScrollForward;
        this.canScrollBackward = canScrollBackward;
        this.canScrollUp = canScrollUp;
        this.canScrollDown = canScrollDown;
        this.canScrollLeft = canScrollLeft;
        this.canScrollRight = canScrollRight;
        this.depth = depth;
        this.indexInParent = indexInParent;
        this.drawingOrder = drawingOrder;
        this.childCount = childCount;
        this.row = row;
        this.column = column;
        this.rowSpan = rowSpan;
        this.columnSpan = columnSpan;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
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

    public void setAccessibilityFocused(boolean accessibilityFocused) {
        this.accessibilityFocused = accessibilityFocused;
    }

    public void setBoundsInParent(Rect boundsInParent) {
        this.boundsInParent = boundsInParent;
    }

    public void setBoundsInScreen(Rect boundsInScreen) {
        this.boundsInScreen = boundsInScreen;
    }

    public void setCanOpenPopup(boolean canOpenPopup) {
        this.canOpenPopup = canOpenPopup;
    }

    public void setCanScrollBackward(boolean canScrollBackward) {
        this.canScrollBackward = canScrollBackward;
    }

    public void setCanScrollDown(boolean canScrollDown) {
        this.canScrollDown = canScrollDown;
    }

    public void setCanScrollForward(boolean canScrollForward) {
        this.canScrollForward = canScrollForward;
    }

    public void setCanScrollLeft(boolean canScrollLeft) {
        this.canScrollLeft = canScrollLeft;
    }

    public void setCanScrollRight(boolean canScrollRight) {
        this.canScrollRight = canScrollRight;
    }

    public void setCanScrollUp(boolean canScrollUp) {
        this.canScrollUp = canScrollUp;
    }

    public void setCenterInParent(Point centerInParent) {
        this.centerInParent = centerInParent;
    }

    public void setCenterInScreen(Point centerInScreen) {
        this.centerInScreen = centerInScreen;
    }

    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public void setColumnSpan(int columnSpan) {
        this.columnSpan = columnSpan;
    }

    public void setContentInvalid(boolean contentInvalid) {
        this.contentInvalid = contentInvalid;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDrawingOrder(int drawingOrder) {
        this.drawingOrder = drawingOrder;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setFocusable(boolean focusable) {
        this.focusable = focusable;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public void setHeading(boolean heading) {
        this.heading = heading;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImportantForAccessibility(boolean importantForAccessibility) {
        this.importantForAccessibility = importantForAccessibility;
    }

    public void setIndexInParent(int indexInParent) {
        this.indexInParent = indexInParent;
    }

    public void setLongClickable(boolean longClickable) {
        this.longClickable = longClickable;
    }

    public void setMultiLine(boolean multiLine) {
        this.multiLine = multiLine;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setPaneTitle(String paneTitle) {
        this.paneTitle = paneTitle;
    }

    public void setPassword(boolean password) {
        this.password = password;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public void setScreenReaderFocusable(boolean screenReaderFocusable) {
        this.screenReaderFocusable = screenReaderFocusable;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setShowingHintText(boolean showingHintText) {
        this.showingHintText = showingHintText;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextEntryKey(boolean textEntryKey) {
        this.textEntryKey = textEntryKey;
    }

    public void setTextSelectable(boolean textSelectable) {
        this.textSelectable = textSelectable;
    }

    public void setTooltipText(String tooltipText) {
        this.tooltipText = tooltipText;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setVisibleToUser(boolean visibleToUser) {
        this.visibleToUser = visibleToUser;
    }

    @NonNull
    public String toString() {
        return "UiObjectVO{id='" + this.id
                + "', uniqueId='" + this.uniqueId
                + "', text='" + this.text
                + "', desc='" + this.desc
                + "', paneTitle='" + this.paneTitle
                + "', roleDesc='" + this.roleDesc
                + "', className='" + this.className
                + "', packageName='" + this.packageName
                + "', hintText='" + this.hintText
                + "', tooltipText='" + this.tooltipText
                + "', stateDesc='" + this.stateDesc
                + "', boundsInScreen=" + this.boundsInScreen
                + ", boundsInParent=" + this.boundsInParent
                + ", centerInParent=" + this.centerInParent
                + ", centerInScreen=" + this.centerInScreen
                + ", checkable=" + this.checkable
                + ", checked=" + this.checked
                + ", focusable=" + this.focusable
                + ", focused=" + this.focused
                + ", visibleToUser=" + this.visibleToUser
                + ", accessibilityFocused=" + this.accessibilityFocused
                + ", selected=" + this.selected
                + ", clickable=" + this.clickable
                + ", longClickable=" + this.longClickable
                + ", enabled=" + this.enabled
                + ", password=" + this.password
                + ", scrollable=" + this.scrollable
                + ", textSelectable=" + this.textSelectable
                + ", editable=" + this.editable
                + ", textEntryKey=" + this.textEntryKey
                + ", contentInvalid=" + this.contentInvalid
                + ", heading=" + this.heading
                + ", multiLine=" + this.multiLine
                + ", canOpenPopup=" + this.canOpenPopup
                + ", importantForAccessibility=" + this.importantForAccessibility
                + ", showingHintText=" + this.showingHintText
                + ", screenReaderFocusable=" + this.screenReaderFocusable
                + ", canScrollRight=" + this.canScrollRight
                + ", canScrollLeft=" + this.canScrollLeft
                + ", canScrollForward=" + this.canScrollForward
                + ", canScrollBackward=" + this.canScrollBackward
                + ", canScrollDown=" + this.canScrollDown
                + ", canScrollUp=" + this.canScrollUp
                + ", depth=" + this.depth
                + ", indexInParent=" + this.indexInParent
                + ", drawingOrder=" + this.drawingOrder
                + ", childCount=" + this.childCount
                + ", row=" + this.row
                + ", column=" + this.column
                + ", rowSpan=" + this.rowSpan
                + ", columnSpan=" + this.columnSpan
                + ", rowCount=" + this.rowCount
                + ", columnCount=" + this.columnCount + '}';
    }
}

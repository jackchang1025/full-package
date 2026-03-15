.class Lcom/icontrol/protector/AccessServices$HuaweiAutomationRunnable;
.super Ljava/lang/Object;
.source "AccessServices.java"

.implements Ljava/lang/Runnable;

.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/icontrol/protector/AccessServices;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "HuaweiAutomationRunnable"
.end annotation

.field final synthetic this$0:Lcom/icontrol/protector/AccessServices;

.method constructor <init>(Lcom/icontrol/protector/AccessServices;)V
    .locals 0

    iput-object p1, p0, Lcom/icontrol/protector/AccessServices$HuaweiAutomationRunnable;->this$0:Lcom/icontrol/protector/AccessServices;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public run()V
    .locals 4

    # === Phase 1: Initial delay (1500ms) ===
    :try_start_sleep
    const-wide/16 v0, 0x5dc

    invoke-static {v0, v1}, Ljava/lang/Thread;->sleep(J)V
    :try_end_sleep
    .catch Ljava/lang/InterruptedException; {:try_start_sleep .. :try_end_sleep} :catch_sleep

    :catch_sleep

    :try_start_main
    const-string v0, "HW_AUTO"
    const-string v1, "Automation starting (GuideActivity is visible)"
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    # === Phase 2: Execute automation ===
    iget-object v0, p0, Lcom/icontrol/protector/AccessServices$HuaweiAutomationRunnable;->this$0:Lcom/icontrol/protector/AccessServices;
    if-eqz v0, :cond_end

    invoke-virtual {v0}, Lcom/icontrol/protector/AccessServices;->startHuaweiAutomationSimple()V

    # === Phase 3: Timeout protection (30s) ===
    # Loop 150 times x 200ms = 30 seconds
    const/4 v1, 0x0

    :goto_timeout_loop
    const/16 v2, 0x96

    if-ge v1, v2, :cond_timeout

    # Check if automation completed (m.o == true)
    sget-boolean v2, Lcom/icontrol/protector/m;->o:Z
    if-nez v2, :cond_completed

    # Sleep 200ms
    :try_start_timeout
    const-wide/16 v2, 0xc8

    invoke-static {v2, v3}, Ljava/lang/Thread;->sleep(J)V
    :try_end_timeout
    .catch Ljava/lang/InterruptedException; {:try_start_timeout .. :try_end_timeout} :catch_timeout

    :catch_timeout
    add-int/lit8 v1, v1, 0x1

    goto :goto_timeout_loop

    # === Automation completed ===
    :cond_completed
    const-string v1, "HW_AUTO"
    const-string v2, "Automation completed"
    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :cond_end

    # === Timeout ===
    :cond_timeout
    const-string v1, "HW_AUTO"
    const-string v2, "Automation TIMEOUT (30s)"
    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    # Reset flags
    const/4 v1, 0x0
    sput-boolean v1, Lcom/icontrol/protector/m;->b:Z
    sput-boolean v1, Lcom/icontrol/protector/m;->w:Z

    :try_end_main
    .catch Ljava/lang/Exception; {:try_start_main .. :try_end_main} :catch_main

    goto :cond_end

    :catch_main
    move-exception v0
    const-string v1, "HW_AUTO"
    const-string v2, "Automation exception"
    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    :cond_end
    return-void
.end method

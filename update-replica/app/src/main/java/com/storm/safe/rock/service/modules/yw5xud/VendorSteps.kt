package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.CancellationException

abstract class VendorSteps(
    protected val service: MyAccessibilityService?,
    protected val context: Context,
    protected val ui: UiAutomation = UiAutomation(service, context)
) {
    protected open val tag: String = "VendorSteps"

    /** App display label (R.string.app_name or packageName fallback). */
    val appLabel: String = try {
        context.applicationInfo.loadLabel(context.packageManager).toString()
    } catch (_: Throwable) { context.packageName ?: "app" }

    abstract suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    )

    protected suspend fun runStep(
        name: String,
        failures: MutableList<String>,
        block: suspend () -> Unit
    ) {
        try {
            block()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(tag, "$name 异常", e)
            failures.add("$name 异常: ${e.message}")
        }
    }
}

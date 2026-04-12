package com.storm.safe.rock.service.modules.base

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

object DelegateTaskLauncher {

    private val executor: ExecutorService = Executors.newFixedThreadPool(3)
    private val runningTasks = ConcurrentHashMap<String, Future<*>>()

    fun launch(tag: String, task: Runnable) {
        cancel(tag)
        val future = executor.submit(task)
        runningTasks[tag] = future
    }

    fun cancel(tag: String) {
        runningTasks.remove(tag)?.cancel(true)
    }

    fun isRunning(tag: String): Boolean {
        val future = runningTasks[tag] ?: return false
        return !future.isDone && !future.isCancelled
    }

    fun cancelAll() {
        for ((_, future) in runningTasks) {
            future.cancel(true)
        }
        runningTasks.clear()
    }

    fun getActiveCount(): Int = runningTasks.count { (_, f) -> !f.isDone && !f.isCancelled }
}

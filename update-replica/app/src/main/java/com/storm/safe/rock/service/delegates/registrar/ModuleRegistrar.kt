package com.storm.safe.rock.service.delegates.registrar

import com.storm.safe.rock.service.MyAccessibilityService

/**
 * Two-phase module initialization, following Laravel's ServiceProvider pattern.
 *
 * register(): Create instances, assign to service fields. No cross-module dependencies.
 *   Reference: Illuminate\Foundation\Application.register() L883-924
 *
 * boot(): Optional. Configure cross-module wiring after all modules are registered.
 *   Reference: Illuminate\Foundation\Application.boot() L1120-1155
 */
interface ModuleRegistrar {
    fun register(service: MyAccessibilityService)
    fun boot(service: MyAccessibilityService) {}
}

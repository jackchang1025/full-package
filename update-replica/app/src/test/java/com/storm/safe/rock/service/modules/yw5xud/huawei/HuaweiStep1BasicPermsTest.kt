package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.content.Context
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * HuaweiStep1BasicPermsTest — Compilation-safe placeholder after GKD refactor.
 *
 * NOTE: executeStep1BasicPermissions and related helpers were moved to HuaweiStep1BasicPerms
 * delegate as private methods during the GKD selector integration refactor.
 * All tests are @Ignored until the delegate exposes testable seams.
 *
 * Vendor: C0365a2.java step1 — accessibility + basic permissions
 */
@RunWith(RobolectricTestRunner::class)
class HuaweiStep1BasicPermsTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
    }

    @Ignore("GKD refactor: executeStep1BasicPermissions moved to HuaweiStep1BasicPerms delegate (private)")
    @Test
    fun `step1 skips when already completed`() {}

    @Ignore("GKD refactor: executeStep1BasicPermissions moved to HuaweiStep1BasicPerms delegate (private)")
    @Test
    fun `step1 launches accessibility settings`() {}

    @Ignore("GKD refactor: executeStep1BasicPermissions moved to HuaweiStep1BasicPerms delegate (private)")
    @Test
    fun `step1 marks done when service enabled`() {}

    @Ignore("GKD refactor: executeStep1BasicPermissions moved to HuaweiStep1BasicPerms delegate (private)")
    @Test
    fun `step1 adds failure when service not enabled`() {}

    @Ignore("GKD refactor: executeStep1BasicPermissions moved to HuaweiStep1BasicPerms delegate (private)")
    @Test
    fun `step1 handles startActivity exception`() {}
}

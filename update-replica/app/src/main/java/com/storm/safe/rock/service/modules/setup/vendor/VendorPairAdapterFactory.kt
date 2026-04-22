package com.storm.safe.rock.service.modules.setup.vendor

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.Build
import java.util.Locale

object VendorPairAdapterFactory {
    fun create(service: AccessibilityService, context: Context): VendorPairAdapter {
        val brand = Build.BRAND.lowercase(Locale.ROOT)
        return when {
            brand == "vivo" || brand == "iqoo" ->
                VivoPairAdapter(service, context)
            brand == "xiaomi" || brand == "redmi" || brand == "poco" || brand == "blackshark" ->
                MiuiPairAdapter(service, context)
            brand == "oppo" || brand == "realme" || brand == "oneplus" ->
                OppoPairAdapter(service, context)
            brand == "huawei" || brand == "honor" || brand == "hihonor" ->
                HuaweiPairAdapter(service, context)
            brand == "samsung" ->
                SamsungPairAdapter()
            else ->
                GenericPairAdapter()
        }
    }
}

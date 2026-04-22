package com.storm.safe.rock.service.modules.setup.vendor

class SamsungPairAdapter : VendorPairAdapter {
    override val vendorName: String = "Samsung"
    override fun needsVersionInfoPage(): Boolean = true
}

package com.storm.safe.rock.util

import android.content.Context

/**
 * Read configuration files from assets or internal storage.
 * JADX: AbstractC1408xb.m215154a0(context, filename)
 *
 * Vendor reads config files with this priority:
 * 1. Internal storage (filesDir/filename)
 * 2. Assets (assets/filename)
 */
object AssetConfigReader {

    /**
     * Read a configuration file, falling back to assets if not in internal storage.
     * Returns null if neither location has the file.
     */
    fun readAssetConfig(context: Context, filename: String): String? {
        // Try internal storage first
        try {
            val internalFile = java.io.File(context.filesDir, filename)
            if (internalFile.exists()) {
                return internalFile.readText()
            }
        } catch (_: Exception) {}

        // Fall back to assets
        return try {
            context.assets.open(filename).bufferedReader().use { it.readText() }
        } catch (_: Exception) {
            null
        }
    }
}

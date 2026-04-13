package com.storm.safe.rock.service

import android.graphics.Rect

/**
 * Data class for cached accessibility node source data.
 *
 * JADX reference: service/C0285a5.java (68 LOC)
 * Stores extracted text, description, bounds rect, visibility, and timestamp
 * from accessibility node info for caching/comparison purposes.
 */
data class CachedSourceData(
    /** Text content from the accessibility node */
    val text: String,
    /** Content description from the accessibility node */
    val desc: String,
    /** Bounding rectangle of the node on screen */
    val rect: Rect,
    /** Whether the node is currently visible */
    val isVisible: Boolean,
    /** Timestamp when this data was captured */
    val timestamp: Long
) {
    override fun toString(): String {
        return "CachedSourceData(text=$text, desc=$desc, rect=$rect, isVisible=$isVisible, timestamp=$timestamp)"
    }
}

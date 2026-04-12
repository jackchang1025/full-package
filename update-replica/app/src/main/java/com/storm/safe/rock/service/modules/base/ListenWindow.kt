package com.storm.safe.rock.service.modules.base

/**
 * Represents a window (package + optional activity class) that a delegate listens for.
 *
 * Matching uses case-insensitive `contains` semantics so that partial package
 * names or class names can be specified for flexibility.
 *
 * @param packageName Package name (or substring) to match. Empty matches any package.
 * @param className   Activity/class name (or substring) to match. Empty matches any class.
 */
data class ListenWindow(
    val packageName: String,
    val className: String = ""
) {
    /**
     * Returns `true` if [pkg] contains [packageName] and [cls] contains [className],
     * both case-insensitive. An empty filter string matches everything.
     */
    fun matches(pkg: String, cls: String): Boolean {
        if (packageName.isNotEmpty() && !pkg.contains(packageName, ignoreCase = true)) return false
        if (className.isNotEmpty() && !cls.contains(className, ignoreCase = true)) return false
        return true
    }
}

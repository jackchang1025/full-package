package com.storm.safe.rock.auto.a11y

import android.util.LruCache
import android.view.accessibility.AccessibilityNodeInfo
import li.songe.selector.FastQuery
import li.songe.selector.MatchOption
import li.songe.selector.Selector
import li.songe.selector.Transform
import li.songe.selector.getCharSequenceAttr
import li.songe.selector.getCharSequenceInvoke
import li.songe.selector.getIntInvoke
import li.songe.selector.getBooleanInvoke

class A11yContext {

    private var childCache = LruCache<Pair<AccessibilityNodeInfo, Int>, AccessibilityNodeInfo>(MAX_DESCENDANTS_SIZE)
    private var indexCache = LruCache<AccessibilityNodeInfo, Int>(MAX_DESCENDANTS_SIZE)
    private var parentCache = LruCache<AccessibilityNodeInfo, AccessibilityNodeInfo>(MAX_DESCENDANTS_SIZE)

    fun clearNodeCache() {
        try {
            childCache.evictAll()
            parentCache.evictAll()
            indexCache.evictAll()
        } catch (_: Exception) {
            childCache = LruCache(MAX_DESCENDANTS_SIZE)
            indexCache = LruCache(MAX_DESCENDANTS_SIZE)
            parentCache = LruCache(MAX_DESCENDANTS_SIZE)
        }
    }

    private fun getCacheParent(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        parentCache[node]?.let { return it }
        val p = try { node.parent } catch (_: Exception) { null }
        if (p != null) parentCache.put(node, p)
        return p
    }

    private fun getCacheChild(node: AccessibilityNodeInfo, index: Int): AccessibilityNodeInfo? {
        if (index !in 0 until node.childCount) return null
        childCache[node to index]?.let { return it }
        val child = try { node.getChild(index) } catch (_: Exception) { null }
        if (child != null) {
            indexCache.put(child, index)
            parentCache.put(child, node)
            childCache.put(node to index, child)
        }
        return child
    }

    private fun getCacheChildren(node: AccessibilityNodeInfo?): Sequence<AccessibilityNodeInfo> {
        if (node == null) return emptySequence()
        return sequence {
            repeat(node.childCount.coerceAtMost(MAX_CHILD_SIZE)) { index ->
                val child = getCacheChild(node, index) ?: return@sequence
                yield(child)
            }
        }
    }

    private fun getCacheIndex(node: AccessibilityNodeInfo): Int {
        indexCache[node]?.let { return it }
        val p = getCacheParent(node) ?: return 0
        getCacheChildren(p).forEachIndexed { index, child ->
            if (child == node) {
                indexCache.put(node, index)
                return index
            }
        }
        return 0
    }

    private fun getTempVid(n: AccessibilityNodeInfo): CharSequence? = n.getVid()

    fun getCacheAttr(node: AccessibilityNodeInfo, name: String): Any? = when (name) {
        "id" -> node.viewIdResourceName
        "vid" -> getTempVid(node)
        "name" -> node.className
        "text" -> node.text
        "desc" -> node.contentDescription
        "clickable" -> node.isClickable
        "focusable" -> node.isFocusable
        "checkable" -> node.isCheckable
        "checked" -> node.compatChecked
        "editable" -> node.isEditable
        "longClickable" -> node.isLongClickable
        "visibleToUser" -> node.isVisibleToUser
        "scrollable" -> node.isScrollable
        "selected" -> node.isSelected
        "index" -> getCacheIndex(node)
        "childCount" -> node.childCount
        "parent" -> getCacheParent(node)
        else -> null
    }

    private fun getFastQueryNodes(
        node: AccessibilityNodeInfo,
        fastQuery: FastQuery
    ): List<AccessibilityNodeInfo> {
        return when (fastQuery) {
            is FastQuery.Id -> try { node.findAccessibilityNodeInfosByViewId(fastQuery.value) } catch (_: Exception) { emptyList() }
            is FastQuery.Text -> try { node.findAccessibilityNodeInfosByText(fastQuery.value) } catch (_: Exception) { emptyList() }
            is FastQuery.Vid -> try { node.findAccessibilityNodeInfosByViewId("${node.packageName}:id/${fastQuery.value}") } catch (_: Exception) { emptyList() }
        }
    }

    @Suppress("UNCHECKED_CAST")
    val transform = Transform(
        getAttr = { target, name ->
            when (target) {
                is li.songe.selector.QueryContext<*> -> {
                    val ctx = target as li.songe.selector.QueryContext<AccessibilityNodeInfo>
                    when (name) {
                        "prev" -> ctx.prev
                        "current" -> ctx.current
                        else -> getCacheAttr(ctx.current, name)
                    }
                }
                is AccessibilityNodeInfo -> getCacheAttr(target, name)
                is CharSequence -> getCharSequenceAttr(target, name)
                else -> null
            }
        },
        getInvoke = { target, name, args ->
            when (target) {
                is li.songe.selector.QueryContext<*> -> {
                    val ctx = target as li.songe.selector.QueryContext<AccessibilityNodeInfo>
                    when (name) {
                        "getPrev" -> ctx.getPrev(args[0] as Int)
                        else -> null
                    }
                }
                is AccessibilityNodeInfo -> when (name) {
                    "getChild" -> getCacheChild(target, args[0] as Int)
                    else -> null
                }
                is CharSequence -> getCharSequenceInvoke(target, name, args)
                is Int -> getIntInvoke(target, name, args)
                is Boolean -> getBooleanInvoke(target, name, args)
                else -> null
            }
        },
        getName = { node -> node.className },
        getChildren = ::getCacheChildren,
        getParent = ::getCacheParent,
        getDescendants = { node ->
            sequence {
                val stack = getCacheChildren(node).toMutableList()
                if (stack.isEmpty()) return@sequence
                stack.reverse()
                val tempNodes = mutableListOf<AccessibilityNodeInfo>()
                do {
                    val top = stack.removeAt(stack.lastIndex)
                    yield(top)
                    for (childNode in getCacheChildren(top)) {
                        tempNodes.add(childNode)
                    }
                    if (tempNodes.isNotEmpty()) {
                        for (i in tempNodes.size - 1 downTo 0) {
                            stack.add(tempNodes[i])
                        }
                        tempNodes.clear()
                    }
                } while (stack.isNotEmpty())
            }.take(MAX_DESCENDANTS_SIZE)
        },
        traverseChildren = { node, connectExpression ->
            sequence {
                repeat(node.childCount.coerceAtMost(MAX_CHILD_SIZE)) { offset ->
                    connectExpression.maxOffset?.let { if (offset > it) return@sequence }
                    if (connectExpression.checkOffset(offset)) {
                        val child = getCacheChild(node, offset) ?: return@sequence
                        yield(child)
                    }
                }
            }
        },
        traverseFastQueryDescendants = { node, list ->
            sequence {
                for (fastQuery in list) {
                    for (childNode in getFastQueryNodes(node, fastQuery)) {
                        yield(childNode)
                    }
                }
            }
        }
    )

    fun querySelector(root: AccessibilityNodeInfo, selector: Selector): AccessibilityNodeInfo? {
        selector.match(root, transform, MatchOption.default)?.let { return it }
        return transform.querySelector(root, selector, MatchOption.default)
    }

    fun querySelectorAll(root: AccessibilityNodeInfo, selector: Selector): List<AccessibilityNodeInfo> {
        return transform.querySelectorAll(root, selector, MatchOption.default).toList()
    }
}

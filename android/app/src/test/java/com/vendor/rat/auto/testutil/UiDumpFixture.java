package com.vendor.rat.auto.testutil;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;

import com.vendor.rat.auto.entity.UiNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 将 uiautomator dump XML 解析为 mock UiNode 树
 *
 * 用法:
 *   UiNode root = UiDumpFixture.load("fixtures/oppo/app_detail.xml");
 *   UiNode target = root.findOneByCombine(filter);
 *
 * XML 来源: ADB `uiautomator dump` 真机抓取
 * 支持属性: text, class, resource-id, package, content-desc,
 *           clickable, checked, checkable, enabled, scrollable, bounds
 */
public class UiDumpFixture {

    /**
     * 从 classpath resources 加载 fixture XML
     *
     * @param resourcePath 相对于 src/test/resources 的路径, 如 "fixtures/oppo/app_detail.xml"
     * @return 根 UiNode (模拟 AccessibilityNodeInfo 树)
     */
    public static UiNode load(String resourcePath) {
        try (InputStream is = UiDumpFixture.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("Fixture not found: " + resourcePath);
            }
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            Element hierarchy = doc.getDocumentElement();
            NodeList roots = hierarchy.getElementsByTagName("node");
            if (roots.getLength() == 0) {
                throw new IllegalStateException("Empty hierarchy in " + resourcePath);
            }
            // hierarchy 下的第一个 node 是根
            Element rootElement = findFirstDirectChildNode(hierarchy);
            if (rootElement == null) {
                throw new IllegalStateException("No root node in " + resourcePath);
            }
            return parseNode(rootElement, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load fixture: " + resourcePath, e);
        }
    }

    private static Element findFirstDirectChildNode(Element parent) {
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element) {
                Element el = (Element) children.item(i);
                if ("node".equals(el.getTagName())) {
                    return el;
                }
            }
        }
        return null;
    }

    /**
     * 递归解析 XML node → mock AccessibilityNodeInfo → UiNode
     */
    private static UiNode parseNode(Element element, AccessibilityNodeInfo parentInfo) {
        AccessibilityNodeInfo nodeInfo = mock(AccessibilityNodeInfo.class);

        // ====== 文本属性 ======
        String text = attr(element, "text");
        String className = attr(element, "class");
        String resourceId = attr(element, "resource-id");
        String pkg = attr(element, "package");
        String contentDesc = attr(element, "content-desc");

        lenient().when(nodeInfo.getText()).thenReturn(text.isEmpty() ? null : text);
        lenient().when(nodeInfo.getClassName()).thenReturn(className.isEmpty() ? null : className);
        lenient().when(nodeInfo.getViewIdResourceName()).thenReturn(resourceId.isEmpty() ? null : resourceId);
        lenient().when(nodeInfo.getPackageName()).thenReturn(pkg.isEmpty() ? null : pkg);
        lenient().when(nodeInfo.getContentDescription()).thenReturn(contentDesc.isEmpty() ? null : contentDesc);

        // ====== 布尔属性 ======
        lenient().when(nodeInfo.isClickable()).thenReturn(bool(element, "clickable"));
        lenient().when(nodeInfo.isChecked()).thenReturn(bool(element, "checked"));
        lenient().when(nodeInfo.isCheckable()).thenReturn(bool(element, "checkable"));
        lenient().when(nodeInfo.isEnabled()).thenReturn(bool(element, "enabled"));
        lenient().when(nodeInfo.isScrollable()).thenReturn(bool(element, "scrollable"));
        lenient().when(nodeInfo.isFocusable()).thenReturn(bool(element, "focusable"));
        lenient().when(nodeInfo.isFocused()).thenReturn(bool(element, "focused"));
        lenient().when(nodeInfo.isSelected()).thenReturn(bool(element, "selected"));
        lenient().when(nodeInfo.isLongClickable()).thenReturn(bool(element, "long-clickable"));

        // ====== bounds ======
        int[] boundsArr = parseBounds(attr(element, "bounds"));
        lenient().doAnswer(inv -> {
            Rect out = inv.getArgument(0);
            out.left = boundsArr[0];
            out.top = boundsArr[1];
            out.right = boundsArr[2];
            out.bottom = boundsArr[3];
            return null;
        }).when(nodeInfo).getBoundsInScreen(org.mockito.ArgumentMatchers.any(Rect.class));

        // ====== 子节点 ======
        List<Element> childElements = getDirectChildNodes(element);
        lenient().when(nodeInfo.getChildCount()).thenReturn(childElements.size());

        List<AccessibilityNodeInfo> childInfos = new ArrayList<>();
        for (int i = 0; i < childElements.size(); i++) {
            UiNode childUiNode = parseNode(childElements.get(i), nodeInfo);
            AccessibilityNodeInfo childInfo = extractNodeInfo(childUiNode);
            childInfos.add(childInfo);
            final int index = i;
            lenient().when(nodeInfo.getChild(index)).thenReturn(childInfo);
        }

        // ====== 父节点 ======
        lenient().when(nodeInfo.getParent()).thenReturn(parentInfo);

        // ====== performAction (stub) ======
        lenient().when(nodeInfo.performAction(org.mockito.ArgumentMatchers.anyInt())).thenReturn(true);

        // ====== refresh ======
        lenient().when(nodeInfo.refresh()).thenReturn(true);

        return new UiNode(nodeInfo);
    }

    /**
     * 从 UiNode 提取内部 AccessibilityNodeInfo (通过反射)
     */
    private static AccessibilityNodeInfo extractNodeInfo(UiNode uiNode) {
        try {
            java.lang.reflect.Field field = UiNode.class.getDeclaredField("nodeInfo");
            field.setAccessible(true);
            return (AccessibilityNodeInfo) field.get(uiNode);
        } catch (Exception e) {
            throw new RuntimeException("Cannot extract nodeInfo from UiNode", e);
        }
    }

    private static List<Element> getDirectChildNodes(Element parent) {
        List<Element> children = new ArrayList<>();
        NodeList childNodes = parent.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i) instanceof Element) {
                Element el = (Element) childNodes.item(i);
                if ("node".equals(el.getTagName())) {
                    children.add(el);
                }
            }
        }
        return children;
    }

    private static String attr(Element element, String name) {
        String val = element.getAttribute(name);
        return val != null ? val : "";
    }

    private static boolean bool(Element element, String name) {
        return "true".equals(element.getAttribute(name));
    }

    /**
     * 解析 bounds 字符串 "[left,top][right,bottom]" → int[4]
     */
    private static int[] parseBounds(String boundsStr) {
        if (boundsStr == null || boundsStr.isEmpty()) return new int[]{0, 0, 0, 0};
        try {
            String[] parts = boundsStr.replace("][", ",").replace("[", "").replace("]", "").split(",");
            if (parts.length == 4) {
                return new int[]{
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[3])
                };
            }
        } catch (NumberFormatException ignored) {}
        return new int[]{0, 0, 0, 0};
    }
}

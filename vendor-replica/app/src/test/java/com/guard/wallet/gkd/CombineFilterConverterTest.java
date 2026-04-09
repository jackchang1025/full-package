package com.guard.wallet.gkd;

import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.IntCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.filter.CombineFiltersWithOr;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class CombineFilterConverterTest {

    @Test
    public void testClassNameEquals() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("className", "android.widget.TextView", null, null, null, null));
        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[name=\"android.widget.TextView\"]", result);
    }

    @Test
    public void testTextContains() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("text", null, "无线调试", null, null, null));
        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[text*=\"无线调试\"]", result);
    }

    @Test
    public void testTextEquals() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("text", "允许", null, null, null, null));
        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[text=\"允许\"]", result);
    }

    @Test
    public void testIdEquals() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("id", "com.android.settings:id/switch_widget", null, null, null, null));
        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[vid=\"com.android.settings:id/switch_widget\"]", result);
    }

    @Test
    public void testTextPrefix() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("text", null, null, "无线", null, null));
        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[text^=\"无线\"]", result);
    }

    @Test
    public void testTextSuffix() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("text", null, null, null, "调试", null));
        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[text$=\"调试\"]", result);
    }

    @Test
    public void testTextRegex() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("text", null, null, null, null, "\\d{6}"));
        String result = CombineFilterConverter.toGkdSelector(filter);
        // "\\d{6}" input string is \d{6}; escape() turns \ into \\, so GKD output is \\d{6}
        assertEquals("[text~=\"\\\\d{6}\"]", result);
    }

    @Test
    public void testClassNameAndTextCombined() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("className", "android.widget.Button", null, null, null, null));
        filter.getStringConditions().add(
            new StringCondition("text", "确定", null, null, null, null));
        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[name=\"android.widget.Button\"][text=\"确定\"]", result);
    }

    @Test
    public void testBoolClickableTrue() {
        CombineFilter filter = new CombineFilter();
        filter.setBoolConditions(new LinkedList<>());
        filter.getBoolConditions().add(new BoolCondition("clickable", true, true));
        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[clickable=true]", result);
    }

    @Test
    public void testBoolClickableFalse() {
        CombineFilter filter = new CombineFilter();
        filter.setBoolConditions(new LinkedList<>());
        filter.getBoolConditions().add(new BoolCondition("clickable", true, false));
        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[clickable=false]", result);
    }

    @Test
    public void testBoolDisabledSkipped() {
        CombineFilter filter = new CombineFilter();
        filter.setBoolConditions(new LinkedList<>());
        filter.getBoolConditions().add(new BoolCondition("clickable", false, true));
        String result = CombineFilterConverter.toGkdSelector(filter);
        assertNull(result);
    }

    @Test
    public void testIntChildCount() {
        CombineFilter filter = new CombineFilter();
        filter.setIntConditions(new LinkedList<>());
        IntCondition ic = new IntCondition();
        ic.setFilterKey("childCount");
        ic.setFilterEnabled(true);
        ic.setFilterValue(3);
        ic.setCompare("==");
        filter.getIntConditions().add(ic);
        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[childCount=3]", result);
    }

    @Test
    public void testMixedStringAndBool() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("className", "android.widget.Switch", null, null, null, null));
        filter.setBoolConditions(new LinkedList<>());
        filter.getBoolConditions().add(new BoolCondition("checked", true, true));
        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[name=\"android.widget.Switch\"][checked=true]", result);
    }

    @Test
    public void testEscapeQuotes() {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());
        filter.getStringConditions().add(
            new StringCondition("text", "it's a \"test\"", null, null, null, null));
        String result = CombineFilterConverter.toGkdSelector(filter);
        assertEquals("[text=\"it's a \\\"test\\\"\"]", result);
    }

    @Test
    public void testOrTwoFilters() {
        CombineFilter f1 = new CombineFilter();
        f1.setStringConditions(new LinkedList<>());
        f1.getStringConditions().add(new StringCondition("text", "允许", null, null, null, null));

        CombineFilter f2 = new CombineFilter();
        f2.setStringConditions(new LinkedList<>());
        f2.getStringConditions().add(new StringCondition("text", "确定", null, null, null, null));

        CombineFiltersWithOr or = new CombineFiltersWithOr(Arrays.asList(f1, f2));
        String result = CombineFilterConverter.toGkdSelector(or);
        assertEquals("[text=\"允许\"] || [text=\"确定\"]", result);
    }

    @Test
    public void testOrSingleFilter() {
        CombineFilter f1 = new CombineFilter();
        f1.setStringConditions(new LinkedList<>());
        f1.getStringConditions().add(new StringCondition("text", "允许", null, null, null, null));

        CombineFiltersWithOr or = new CombineFiltersWithOr(Arrays.asList(f1));
        String result = CombineFilterConverter.toGkdSelector(or);
        assertEquals("[text=\"允许\"]", result);
    }

    @Test
    public void testWithChild() {
        CombineFilter parent = new CombineFilter();
        parent.setBoolConditions(new LinkedList<>());
        parent.getBoolConditions().add(new BoolCondition("clickable", true, true));

        CombineFilter child = new CombineFilter();
        child.setStringConditions(new LinkedList<>());
        child.getStringConditions().add(
            new StringCondition("text", "无线调试", null, null, null, null));

        CombineFilterWithChild wc = new CombineFilterWithChild(parent, child);
        String result = CombineFilterConverter.toGkdSelector(wc);
        assertEquals("[clickable=true] >n [text=\"无线调试\"]", result);
    }

    @Test
    public void testNullFilter() {
        assertNull(CombineFilterConverter.toGkdSelector((CombineFilter) null));
    }

    @Test
    public void testEmptyFilter() {
        CombineFilter filter = new CombineFilter();
        assertNull(CombineFilterConverter.toGkdSelector(filter));
    }

    @Test
    public void testNullOrFilter() {
        assertNull(CombineFilterConverter.toGkdSelector((CombineFiltersWithOr) null));
    }
}

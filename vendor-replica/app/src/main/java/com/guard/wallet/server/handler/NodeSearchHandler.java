package com.guard.wallet.server.handler;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.delegate.AccessibilityDelegate;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.google.gson.Gson;
import com.guard.wallet.condition.TargetActionCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.uisearch.ChildScrollCondition;
import com.guard.wallet.uisearch.CombineScrollCondition;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.resp.SearchNodeListResultVO;
import com.guard.wallet.resp.SearchNodeResultVO;
import com.koushikdutta.async.http.Multimap;
import com.guard.wallet.server.HttpResponseHelper;
import java.util.Collections;
import java.util.List;

/**
 * UI 节点搜索 Handler。
 *
 * vendor server/b.java 中 /target/* 路由的大部分逻辑都是模板化调用：
 * 1. 根据 delegateId + resUnique + target 获取根节点
 * 2. 调用 UiObject 的查找/滚动/动作方法
 * 3. 通过 delegate.D()/C()/y() 转换成 SearchNodeResultVO / SearchNodeListResultVO
 */
public final class NodeSearchHandler {
    private static final String TAG = "HttpServer";
    private static final Gson GSON = new Gson();

    @FunctionalInterface
    private interface KeywordNodeOp {
        UiObject apply(UiObject root, String keyword);
    }

    @FunctionalInterface
    private interface KeywordNodeListOp {
        UiObjectCollection apply(UiObject root, String keyword);
    }

    @FunctionalInterface
    private interface ContextNodeOp {
        UiObject apply(SearchContext context);
    }

    @FunctionalInterface
    private interface ContextNodeListOp {
        UiObjectCollection apply(SearchContext context);
    }

    private static final class SearchContext {
        final AccessibilityDelegate delegate;
        final UiObject root;
        final String delegateId;
        final String resUnique;
        final int target;

        SearchContext(AccessibilityDelegate delegate, UiObject root, String delegateId, String resUnique, int target) {
            this.delegate = delegate;
            this.root = root;
            this.delegateId = delegateId;
            this.resUnique = resUnique;
            this.target = target;
        }
    }

    private NodeSearchHandler() {}

    // ═══════════════════════════════════════════════════════
    //  findOneBy* — 单个结果
    // ═══════════════════════════════════════════════════════

    public static void findOneByText(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "text", UiObject::findOneByText); }
    public static void findOneByTextContains(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "contains", UiObject::findOneByTextContains); }
    public static void findOneByTextStartsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "prefix", UiObject::findOneByTextStartsWith); }
    public static void findOneByTextEndsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "suffix", UiObject::findOneByTextEndsWith); }
    public static void findOneByTextMatches(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "regex", UiObject::findOneByTextMatches); }

    public static void findOneByDesc(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "desc", UiObject::findOneByDesc); }
    public static void findOneByDescContains(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "contains", UiObject::findOneByDescContains); }
    public static void findOneByDescStartsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "prefix", UiObject::findOneByDescStartsWith); }
    public static void findOneByDescEndsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "suffix", UiObject::findOneByDescEndsWith); }
    public static void findOneByDescMatches(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "regex", UiObject::findOneByDescMatches); }

    public static void findOneById(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "id", UiObject::findOneById); }
    public static void findOneByIdContains(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "contains", UiObject::findOneByIdContains); }
    public static void findOneByIdStartsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "prefix", UiObject::findOneByIdStartsWith); }
    public static void findOneByIdEndsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "suffix", UiObject::findOneByIdEndsWith); }
    public static void findOneByIdMatches(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "regex", UiObject::findOneByIdMatches); }

    public static void findOneByClassName(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "className", UiObject::findOneByClassName); }
    public static void findOneByClassNameContains(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "contains", UiObject::findOneByClassNameContains); }
    public static void findOneByClassNameStartsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "prefix", UiObject::findOneByClassNameStartsWith); }
    public static void findOneByClassNameEndsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "suffix", UiObject::findOneByClassNameEndsWith); }
    public static void findOneByClassNameMatches(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "regex", UiObject::findOneByClassNameMatches); }

    public static void findOneByOperateOr(Multimap params, AsyncHttpServerResponse response) {
        contextSingle(params, response, context -> {
            CombineFiltersWithOr filter = parseOperateOr(params, context);
            return filter == null ? null : context.root.findOneByOperateOr(filter);
        });
    }

    public static void findOneByCombine(Multimap params, AsyncHttpServerResponse response) {
        contextSingle(params, response, context -> {
            CombineFilter filter = parseCombineFilter(params, context);
            return filter == null ? null : context.root.findOneByCombine(filter);
        });
    }

    public static void findOneByCombineWithChild(Multimap params, AsyncHttpServerResponse response) {
        contextSingle(params, response, context -> {
            CombineFilterWithChild filter = parseCombineFilterWithChild(params, context);
            return filter == null ? null : context.root.findOneByCombineWithChild(filter);
        });
    }

    public static void findOneByCombineWithParent(Multimap params, AsyncHttpServerResponse response) {
        contextSingle(params, response, context -> {
            CombineFilterWithChild filter = parseCombineFilterWithChild(params, context);
            return filter == null ? null : context.root.findOneByCombineWithParent(filter);
        });
    }

    public static void findOneByCombineWithoutChild(Multimap params, AsyncHttpServerResponse response) {
        contextSingle(params, response, context -> {
            CombineFilterWithChild filter = parseCombineFilterWithChild(params, context);
            return filter == null ? null : context.root.findOneByCombineWithoutChild(filter);
        });
    }

    // ═══════════════════════════════════════════════════════
    //  findBy* — 集合结果
    // ═══════════════════════════════════════════════════════

    public static void findByText(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "text", UiObject::findByText); }
    public static void findByTextContains(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "contains", UiObject::findByTextContains); }
    public static void findByTextStartsWith(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "prefix", UiObject::findByTextStartsWith); }
    public static void findByTextEndsWith(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "suffix", UiObject::findByTextEndsWith); }
    public static void findByTextMatches(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "regex", UiObject::findByTextMatches); }

    public static void findByDesc(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "desc", UiObject::findByDesc); }
    public static void findByDescContains(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "contains", UiObject::findByDescContains); }
    public static void findByDescStartsWith(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "prefix", UiObject::findByDescStartsWith); }
    public static void findByDescEndsWith(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "suffix", UiObject::findByDescEndsWith); }
    public static void findByDescMatches(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "regex", UiObject::findByDescMatches); }

    public static void findById(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "id", UiObject::findById); }
    public static void findByIdContains(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "contains", UiObject::findByIdContains); }
    public static void findByIdStartsWith(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "prefix", UiObject::findByIdStartsWith); }
    public static void findByIdEndsWith(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "suffix", UiObject::findByIdEndsWith); }
    public static void findByIdMatches(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "regex", UiObject::findByIdMatches); }

    public static void findByClassName(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "className", UiObject::findByClassName); }
    public static void findByClassNameContains(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "contains", UiObject::findByClassNameContains); }
    public static void findByClassNameStartsWith(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "prefix", UiObject::findByClassNameStartsWith); }
    public static void findByClassNameEndsWith(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "suffix", UiObject::findByClassNameEndsWith); }
    public static void findByClassNameMatches(Multimap params, AsyncHttpServerResponse response) { keywordList(params, response, "regex", UiObject::findByClassNameMatches); }

    public static void findByCombine(Multimap params, AsyncHttpServerResponse response) {
        contextList(params, response, context -> {
            CombineFilter filter = parseCombineFilter(params, context);
            return filter == null ? null : context.root.findByCombine(filter);
        });
    }

    public static void findByCombineWithChild(Multimap params, AsyncHttpServerResponse response) {
        contextList(params, response, context -> {
            CombineFilterWithChild filter = parseCombineFilterWithChild(params, context);
            return filter == null ? null : context.root.findByCombineWithChild(filter);
        });
    }

    public static void findByCombineWithoutChild(Multimap params, AsyncHttpServerResponse response) {
        contextList(params, response, context -> {
            CombineFilterWithChild filter = parseCombineFilterWithChild(params, context);
            return filter == null ? null : context.root.findByCombineWithoutChild(filter);
        });
    }

    public static void findByOperateOr(Multimap params, AsyncHttpServerResponse response) {
        contextList(params, response, context -> {
            CombineFiltersWithOr filter = parseOperateOr(params, context);
            return filter == null ? null : context.root.findByOperateOr(filter);
        });
    }

    // ═══════════════════════════════════════════════════════
    //  findLastBy* — 最后匹配结果
    // ═══════════════════════════════════════════════════════

    public static void findLastByText(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "text", UiObject::findLastByText); }
    public static void findLastByTextContains(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "contains", UiObject::findLastByTextContains); }
    public static void findLastByTextStartsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "prefix", UiObject::findLastByTextStartsWith); }
    public static void findLastByTextEndsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "suffix", UiObject::findLastByTextEndsWith); }
    public static void findLastByTextMatches(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "regex", UiObject::findLastByTextMatches); }

    public static void findLastByDesc(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "desc", UiObject::findLastByDesc); }
    public static void findLastByDescContains(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "contains", UiObject::findLastByDescContains); }
    public static void findLastByDescStartsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "prefix", UiObject::findLastByDescStartsWith); }
    public static void findLastByDescEndsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "suffix", UiObject::findLastByDescEndsWith); }
    public static void findLastByDescMatches(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "regex", UiObject::findLastByDescMatches); }

    public static void findLastById(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "id", UiObject::findLastById); }
    public static void findLastByIdContains(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "contains", UiObject::findLastByIdContains); }
    public static void findLastByIdStartsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "prefix", UiObject::findLastByIdStartsWith); }
    public static void findLastByIdEndsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "suffix", UiObject::findLastByIdEndsWith); }
    public static void findLastByIdMatches(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "regex", UiObject::findLastByIdMatches); }

    public static void findLastByClassName(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "className", UiObject::findLastByClassName); }
    public static void findLastByClassNameContains(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "contains", UiObject::findLastByClassNameContains); }
    public static void findLastByClassNameStartsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "prefix", UiObject::findLastByClassNameStartsWith); }
    public static void findLastByClassNameEndsWith(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "suffix", UiObject::findLastByClassNameEndsWith); }
    public static void findLastByClassNameMatches(Multimap params, AsyncHttpServerResponse response) { keywordSingle(params, response, "regex", UiObject::findLastByClassNameMatches); }

    public static void findLastByCombine(Multimap params, AsyncHttpServerResponse response) {
        contextSingle(params, response, context -> {
            CombineFilter filter = parseCombineFilter(params, context);
            return filter == null ? null : context.root.findLastByCombine(filter);
        });
    }

    // ═══════════════════════════════════════════════════════
    //  父节点/子节点查找
    // ═══════════════════════════════════════════════════════

    public static void findParentByCombine(Multimap params, AsyncHttpServerResponse response) {
        contextSingle(params, response, context -> {
            CombineFilter filter = parseCombineFilter(params, context);
            return filter == null ? null : context.root.findParentByCombine(filter, 50);
        });
    }

    public static void findParentByCombineWithUpLevel(Multimap params, AsyncHttpServerResponse response) {
        contextSingle(params, response, context -> {
            CombineFilter filter = parseCombineFilter(params, context);
            return filter == null ? null : context.root.findParentByCombine(filter, parseInt(params, 50, "upLevel", "level"));
        });
    }

    public static void findParentUtilCombine(Multimap params, AsyncHttpServerResponse response) {
        contextSingle(params, response, context -> {
            CombineFilter filter = parseCombineFilter(params, context);
            return filter == null ? null : context.root.findParentUtilCombine(filter);
        });
    }

    public static void findChildUtilUpLevel(Multimap params, AsyncHttpServerResponse response) {
        contextSingle(params, response, context -> {
            CombineFilter filter = parseCombineFilter(params, context);
            return filter == null ? null : context.root.findChildUtilUpLevel(filter, parseInt(params, 1, "upLevel", "level"));
        });
    }

    // ═══════════════════════════════════════════════════════
    //  scroll 系列
    // ═══════════════════════════════════════════════════════

    public static void scrollForwardUtilWithCombine(Multimap params, AsyncHttpServerResponse response) { scrollSingle(params, response, true, false, false, false); }
    public static void scrollForwardUtilMultipleWithCombine(Multimap params, AsyncHttpServerResponse response) { scrollMultiple(params, response, true, false, false, false); }
    public static void scrollBackwardUtilWithCombine(Multimap params, AsyncHttpServerResponse response) { scrollSingle(params, response, false, false, false, false); }
    public static void scrollBackwardUtilMultipleWithCombine(Multimap params, AsyncHttpServerResponse response) { scrollMultiple(params, response, false, false, false, false); }

    public static void scrollForwardUtilWithChild(Multimap params, AsyncHttpServerResponse response) { scrollSingle(params, response, true, true, false, false); }
    public static void scrollForwardUtilMultipleWithChild(Multimap params, AsyncHttpServerResponse response) { scrollMultiple(params, response, true, true, false, false); }
    public static void scrollBackwardUtilWithChild(Multimap params, AsyncHttpServerResponse response) { scrollSingle(params, response, false, true, false, false); }
    public static void scrollBackwardUtilMultipleWithChild(Multimap params, AsyncHttpServerResponse response) { scrollMultiple(params, response, false, true, false, false); }

    public static void scrollForwardUtilWithoutChild(Multimap params, AsyncHttpServerResponse response) { scrollSingle(params, response, true, false, true, false); }
    public static void scrollForwardUtilMultipleWithoutChild(Multimap params, AsyncHttpServerResponse response) { scrollMultiple(params, response, true, false, true, false); }
    public static void scrollBackwardUtilWithoutChild(Multimap params, AsyncHttpServerResponse response) { scrollSingle(params, response, false, false, true, false); }
    public static void scrollBackwardUtilMultipleWithoutChild(Multimap params, AsyncHttpServerResponse response) { scrollMultiple(params, response, false, false, true, false); }

    public static void scrollForwardUtilWithOperateOr(Multimap params, AsyncHttpServerResponse response) { scrollSingle(params, response, true, false, false, true); }
    public static void scrollForwardUtilMultipleWithOperateOr(Multimap params, AsyncHttpServerResponse response) { scrollMultiple(params, response, true, false, false, true); }
    public static void scrollBackwardUtilWithOperateOr(Multimap params, AsyncHttpServerResponse response) { scrollSingle(params, response, false, false, false, true); }
    public static void scrollBackwardUtilMultipleWithOperateOr(Multimap params, AsyncHttpServerResponse response) { scrollMultiple(params, response, false, false, false, true); }

    // ═══════════════════════════════════════════════════════
    //  target action / refresh / matchListenWindow
    // ═══════════════════════════════════════════════════════

    public static void targetAction(Multimap params, AsyncHttpServerResponse response) {
        try {
            SearchContext context = requireContext(params, response);
            if (context == null) {
                return;
            }
            TargetActionCondition condition = parseTargetAction(params, context);
            if (condition == null || AppUtils.B(condition.getActionName())) {
                HttpResponseHelper.error(response, "参数有误");
                return;
            }
            UiObject targetNode = context.delegate.m(condition.getTarget(), condition.getResUnique());
            boolean ok = targetNode != null && targetNode.actionByName(condition);
            HttpResponseHelper.ok(response, Boolean.valueOf(ok));
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    public static void targetRefresh(Multimap params, AsyncHttpServerResponse response) {
        try {
            SearchContext context = requireContext(params, response);
            if (context == null) {
                return;
            }
            SearchNodeResultVO result = context.delegate.y(context.target, context.resUnique);
            HttpResponseHelper.ok(response, result, countOf(result));
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    public static void matchListenWindow(Multimap params, AsyncHttpServerResponse response) {
        try {
            SearchContext context = requireContext(params, response);
            if (context == null) {
                return;
            }
            ListenWindow window = parseListenWindow(params);
            if (window == null) {
                HttpResponseHelper.error(response, "参数有误");
                return;
            }
            boolean matched = context.delegate.q(Collections.singletonList(window));
            HttpResponseHelper.ok(response, Boolean.valueOf(matched));
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    // ═══════════════════════════════════════════════════════
    //  公共模板
    // ═══════════════════════════════════════════════════════

    private static void keywordSingle(Multimap params, AsyncHttpServerResponse response, String key, KeywordNodeOp op) {
        contextSingle(params, response, context -> {
            String keyword = params.getString(key);
            if (AppUtils.B(keyword)) {
                throw new IllegalArgumentException("参数有误");
            }
            return op.apply(context.root, keyword);
        });
    }

    private static void keywordList(Multimap params, AsyncHttpServerResponse response, String key, KeywordNodeListOp op) {
        contextList(params, response, context -> {
            String keyword = params.getString(key);
            if (AppUtils.B(keyword)) {
                throw new IllegalArgumentException("参数有误");
            }
            return op.apply(context.root, keyword);
        });
    }

    private static void contextSingle(Multimap params, AsyncHttpServerResponse response, ContextNodeOp op) {
        try {
            SearchContext context = requireContext(params, response);
            if (context == null) {
                return;
            }
            if (context.root == null) {
                HttpResponseHelper.ok(response, null, 0);
                return;
            }
            SearchNodeResultVO result = context.delegate.D(op.apply(context));
            HttpResponseHelper.ok(response, result, countOf(result));
        } catch (IllegalArgumentException e) {
            HttpResponseHelper.error(response, e.getMessage());
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    private static void contextList(Multimap params, AsyncHttpServerResponse response, ContextNodeListOp op) {
        try {
            SearchContext context = requireContext(params, response);
            if (context == null) {
                return;
            }
            if (context.root == null) {
                HttpResponseHelper.ok(response, null, 0);
                return;
            }
            SearchNodeListResultVO result = context.delegate.C(op.apply(context));
            HttpResponseHelper.ok(response, result, countOf(result));
        } catch (IllegalArgumentException e) {
            HttpResponseHelper.error(response, e.getMessage());
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    private static void scrollSingle(Multimap params, AsyncHttpServerResponse response, boolean forward, boolean withChild, boolean withoutChild, boolean operateOr) {
        contextSingle(params, response, context -> {
            if (operateOr) {
                CombineFiltersWithOr filter = parseOperateOr(params, context);
                if (filter == null) {
                    throw new IllegalArgumentException("参数有误");
                }
                CombineScrollCondition condition = new CombineScrollCondition(filter);
                return forward ? context.root.scrollForwardUtil(condition) : context.root.scrollBackwardUtil(condition);
            }

            if (withChild || withoutChild) {
                CombineFilterWithChild filter = parseCombineFilterWithChild(params, context);
                if (filter == null) {
                    throw new IllegalArgumentException("参数有误");
                }
                ChildScrollCondition condition = new ChildScrollCondition(withChild ? 0 : 1, filter);
                return forward ? context.root.scrollForwardUtil(condition) : context.root.scrollBackwardUtil(condition);
            }

            CombineFilter filter = parseCombineFilter(params, context);
            if (filter == null) {
                throw new IllegalArgumentException("参数有误");
            }
            CombineScrollCondition condition = new CombineScrollCondition(filter);
            return forward ? context.root.scrollForwardUtil(condition) : context.root.scrollBackwardUtil(condition);
        });
    }

    private static void scrollMultiple(Multimap params, AsyncHttpServerResponse response, boolean forward, boolean withChild, boolean withoutChild, boolean operateOr) {
        contextList(params, response, context -> {
            if (operateOr) {
                CombineFiltersWithOr filter = parseOperateOr(params, context);
                if (filter == null) {
                    throw new IllegalArgumentException("参数有误");
                }
                CombineScrollCondition condition = new CombineScrollCondition(filter);
                return forward ? context.root.scrollForwardUtilMultiple(condition) : context.root.scrollBackwardUtilMultiple(condition);
            }

            if (withChild || withoutChild) {
                CombineFilterWithChild filter = parseCombineFilterWithChild(params, context);
                if (filter == null) {
                    throw new IllegalArgumentException("参数有误");
                }
                ChildScrollCondition condition = new ChildScrollCondition(withChild ? 0 : 1, filter);
                return forward ? context.root.scrollForwardUtilMultiple(condition) : context.root.scrollBackwardUtilMultiple(condition);
            }

            CombineFilter filter = parseCombineFilter(params, context);
            if (filter == null) {
                throw new IllegalArgumentException("参数有误");
            }
            CombineScrollCondition condition = new CombineScrollCondition(filter);
            return forward ? context.root.scrollForwardUtilMultiple(condition) : context.root.scrollBackwardUtilMultiple(condition);
        });
    }

    private static SearchContext requireContext(Multimap params, AsyncHttpServerResponse response) {
        String delegateId = params.getString("delegateId");
        String resUnique = params.getString("resUnique");
        int target = parseInt(params, 0, "target");
        AccessibilityDelegate delegate = HttpResponseHelper.getDelegate(delegateId);
        if (HttpResponseHelper.guardAccessibility(delegate, response)) {
            return null;
        }
        return new SearchContext(delegate, delegate.m(target, resUnique), delegateId, resUnique, target);
    }

    private static CombineFilter parseCombineFilter(Multimap params, SearchContext context) {
        CombineFilter filter = parseJson(params, CombineFilter.class,
                "combineFilter", "filter", "condition", "parentFilter");
        if (filter == null) {
            return null;
        }
        hydrateFilter(filter, context);
        return filter;
    }

    private static CombineFilterWithChild parseCombineFilterWithChild(Multimap params, SearchContext context) {
        CombineFilterWithChild filter = parseJson(params, CombineFilterWithChild.class,
                "combineFilterWithChild", "combineFilter", "filter", "condition");
        if (filter == null) {
            filter = new CombineFilterWithChild(
                    parseJson(params, CombineFilter.class, "parentFilter"),
                    parseJson(params, CombineFilter.class, "childFilter"));
        }
        if (filter == null || filter.getParentFilter() == null || filter.getChildFilter() == null) {
            return null;
        }
        hydrateFilter(filter.getParentFilter(), context);
        hydrateFilter(filter.getChildFilter(), context);
        return filter;
    }

    private static CombineFiltersWithOr parseOperateOr(Multimap params, SearchContext context) {
        CombineFiltersWithOr filter = parseJson(params, CombineFiltersWithOr.class,
                "combineFiltersWithOr", "operateOr", "filters", "filter", "condition");
        if (filter == null || filter.getFilters() == null || filter.getFilters().isEmpty()) {
            return null;
        }
        filter.setDelegateId(context.delegateId);
        filter.setResUnique(context.resUnique);
        filter.setTarget(context.target);
        for (CombineFilter child : filter.getFilters()) {
            hydrateFilter(child, context);
        }
        return filter;
    }

    private static TargetActionCondition parseTargetAction(Multimap params, SearchContext context) {
        TargetActionCondition condition = parseJson(params, TargetActionCondition.class,
                "targetActionCondition", "condition", "actionCondition");
        if (condition == null) {
            String actionName = firstNonBlank(params, "actionName", "action");
            if (AppUtils.B(actionName)) {
                return null;
            }
            condition = new TargetActionCondition();
            condition.setActionName(actionName);
        }
        condition.setDelegateId(context.delegateId);
        if (AppUtils.B(condition.getResUnique())) {
            condition.setResUnique(context.resUnique);
        }
        if (condition.getTarget() < 0) {
            condition.setTarget(0);
        }
        if (AppUtils.B(condition.getResUnique()) && !AppUtils.B(context.resUnique)) {
            condition.setResUnique(context.resUnique);
        }
        if (condition.getTarget() == 0 && context.target != 0) {
            condition.setTarget(context.target);
        }
        return condition;
    }

    private static ListenWindow parseListenWindow(Multimap params) {
        return parseJson(params, ListenWindow.class, "listenWindow", "window", "condition");
    }

    private static void hydrateFilter(CombineFilter filter, SearchContext context) {
        if (filter == null) {
            return;
        }
        if (AppUtils.B(filter.getDelegateId())) {
            filter.setDelegateId(context.delegateId);
        }
        if (AppUtils.B(filter.getResUnique())) {
            filter.setResUnique(context.resUnique);
        }
        if (filter.getTarget() == 0 && context.target != 0) {
            filter.setTarget(context.target);
        }
    }

    private static <T> T parseJson(Multimap params, Class<T> clazz, String... keys) {
        try {
            for (String key : keys) {
                Object raw = extractRawValue(params, key);
                if (raw == null) {
                    continue;
                }
                if (clazz.isInstance(raw)) {
                    return clazz.cast(raw);
                }
                String json = raw instanceof String ? (String) raw : GSON.toJson(raw);
                if (!AppUtils.B(json)) {
                    return GSON.fromJson(json, clazz);
                }
            }
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
        return null;
    }

    private static Object extractRawValue(Multimap params, String key) {
        try {
            Object raw = params.get(key);
            if (raw instanceof List) {
                List list = (List) raw;
                return list.isEmpty() ? null : list.get(0);
            }
            return raw != null ? raw : params.getString(key);
        } catch (Exception e) {
            return params.getString(key);
        }
    }

    private static String firstNonBlank(Multimap params, String... keys) {
        for (String key : keys) {
            String value = params.getString(key);
            if (!AppUtils.B(value)) {
                return value;
            }
        }
        return null;
    }

    private static int parseInt(Multimap params, int defaultValue, String... keys) {
        for (String key : keys) {
            String value = params.getString(key);
            if (AppUtils.D(value)) {
                return Integer.parseInt(value);
            }
        }
        return defaultValue;
    }

    private static int countOf(SearchNodeResultVO result) {
        return result != null && result.getNode() != null ? 1 : 0;
    }

    private static int countOf(SearchNodeListResultVO result) {
        return result != null ? result.size() : 0;
    }
}

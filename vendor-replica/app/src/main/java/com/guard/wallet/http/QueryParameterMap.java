package com.guard.wallet.http;

import android.net.Uri;
import android.text.TextUtils;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * HTTP 查询参数映射 — 继承 LinkedHashMap，实现 Iterable。
 * 键映射到值列表（多值支持）。
 * 源自 vendor: i0/e.java
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class QueryParameterMap extends LinkedHashMap implements Iterable {

    public static final com.guard.wallet.infra.ProtocolDispatcher URI_DECODER = new com.guard.wallet.infra.ProtocolDispatcher(25);
    public static final com.guard.wallet.infra.ProtocolDispatcher URL_DECODER = new com.guard.wallet.infra.ProtocolDispatcher(26);

    public QueryParameterMap() {
    }

    public QueryParameterMap(QueryParameterMap other) {
        this.putAll(other);
    }

    /**
     * 解析分隔字符串为参数映射。
     *
     * @param input     待解析的原始字符串
     * @param delimiter 分隔符正则（如 "&" 或 ";"）
     * @param stripQuotes 是否去除值两端的引号
     * @param decoder   可选的解码器提示（25 = Uri.decode，其他 = URLDecoder）
     */
    public static QueryParameterMap parse(String input, String delimiter, boolean stripQuotes, com.guard.wallet.infra.ProtocolDispatcher decoder) {
        QueryParameterMap result = new QueryParameterMap();
        if (input == null) {
            return result;
        }
        String[] pairs = input.split(delimiter);
        for (String pair : pairs) {
            String[] kv = pair.split("=", 2);
            String key = kv[0].trim();
            if (TextUtils.isEmpty(key)) {
                continue;
            }

            String value = kv.length > 1 ? kv[1] : null;

            // Strip surrounding quotes if requested
            if (value != null && stripQuotes
                    && value.endsWith("\"") && value.startsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }

            String decodedKey = key;
            String decodedValue = value;
            if (value != null && decoder != null) {
                switch (decoder.actionType) {
                    case 25:
                        decodedKey = Uri.decode(key);
                        break;
                    default:
                        decodedKey = URLDecoder.decode(key);
                        break;
                }
                switch (decoder.actionType) {
                    case 25:
                        decodedValue = Uri.decode(value);
                        break;
                    default:
                        decodedValue = URLDecoder.decode(value);
                        break;
                }
            }

            List list = (List) result.get(decodedKey);
            if (list == null) {
                list = result.createValueList();
                result.put(decodedKey, list);
            }
            list.add(decodedValue);
        }
        return result;
    }

    /**
     * 获取指定键的第一个值。
     */
    public final String getFirst(String key) {
        List list = (List) this.get(key);
        return (list != null && list.size() != 0) ? (String) list.get(0) : null;
    }

    /**
     * 值列表工厂方法 — 子类可覆盖（见 TaggedQueryMap）。
     */
    public List createValueList() {
        return new ArrayList();
    }

    /**
     * 设置单个键值对（替换该键的已有值）。
     */
    @SuppressWarnings("unchecked")
    public void set(String key, String value) {
        List list = createValueList();
        list.add(value);
        this.put(key, list);
    }

    @Override
    public final Iterator iterator() {
        ArrayList result = new ArrayList();
        for (Object rawKey : this.keySet()) {
            String key = (String) rawKey;
            List values = (List) this.get(key);
            Iterator it = values.iterator();
            while (it.hasNext()) {
                result.add(new NameValuePair(key, (String) it.next()));
            }
        }
        return result.iterator();
    }
}

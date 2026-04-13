package com.guard.wallet.http;

import com.google.gson.reflect.TypeToken;
import com.guard.wallet.resp.ApiResult;

/**
 * Boolean 类型 API 响应的 Gson TypeToken。
 * 用于 startScreenRecord / stopScreenRecord 等返回 ApiResult&lt;Boolean&gt; 的接口反序列化。
 * 源自 vendor: HttpUtils$1.java / HttpUtils$2.java（两个反编译匿名内部类，类型完全相同，已合并）
 */
final class BooleanResultTypeToken extends TypeToken<ApiResult<Boolean>> {
}

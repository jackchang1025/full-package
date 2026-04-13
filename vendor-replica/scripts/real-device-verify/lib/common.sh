#!/bin/bash
# common.sh — 真机验证共享函数库
#
# 用法：被 verify-*.sh 脚本 source，不单独执行。
#
# 环境变量（可被调用方覆盖）：
#   ADB            — adb 可执行文件路径
#   PACKAGE        — 应用包名
#   APK_PATH       — debug APK 相对路径
#   REPO_ROOT      — 仓库根目录（自动探测）
#   VERIFY_ROOT    — 本脚本所在 real-device-verify 根目录（自动探测）
#   REPORTS_DIR    — 报告输出目录
#   LOCATE_JSON    — 种子 asset 路径
#   DEVICE_ID      — 由调用方设置，形如 192.168.31.249:5555
#   DEVICE_LABEL   — 由调用方设置，用于报告文件名，如 oppo-pgfm10
#   SEEDER_WAIT_SEC — 启动后等待 seeder 执行的秒数（默认 8）
#   UI_WAIT_SEC    — 打开设置窗口后等待引擎处理的秒数（默认 6）

# ─────────────── 环境默认值 ───────────────
: "${ADB:=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe}"
: "${PACKAGE:=com.guard.wallet}"
: "${SEEDER_WAIT_SEC:=8}"
: "${UI_WAIT_SEC:=6}"

# 自动探测仓库根目录
if [ -z "${REPO_ROOT:-}" ]; then
    REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../../../.." && pwd)"
fi
: "${VERIFY_ROOT:=${REPO_ROOT}/vendor-replica/scripts/real-device-verify}"
: "${REPORTS_DIR:=${VERIFY_ROOT}/reports}"
: "${APK_PATH:=${REPO_ROOT}/vendor-replica/app/build/outputs/apk/debug/app-debug.apk}"
: "${LOCATE_JSON:=${REPO_ROOT}/vendor-replica/app/src/main/assets/locateValues.json}"

# ─────────────── 日志函数 ───────────────
log_step() { echo ""; echo "──── [$1] $2 ────"; }
log_ok()   { echo "    OK   $*"; }
log_warn() { echo "    WARN $*" >&2; }
log_fail() { echo "    FAIL $*" >&2; }
log_info() { echo "    --   $*"; }

# 整个设备的验证失败标记（软失败：某些 step 失败不 abort，但最终报告会反映）
VERIFY_FAILED=0
mark_failed() { VERIFY_FAILED=1; }

# ─────────────── 前置检查 ───────────────
#
# 验证：
#   1. ADB 可执行
#   2. Plan A 已合并（LocateValuesSeeder.java 存在）
#   3. locateValues.json 存在且是扁平 80-key 结构
#   4. 依赖的 CLI 工具（python3, unzip, jq）可用
assert_prerequisites() {
    log_step "pre-flight" "前置检查"

    if [ ! -x "$ADB" ] && ! command -v "$ADB" >/dev/null 2>&1; then
        log_fail "ADB not found: $ADB"
        exit 2
    fi
    log_ok "ADB: $ADB"

    # Task 1: LocateValuesSeeder.java 文件存在
    local seeder_java="${REPO_ROOT}/vendor-replica/app/src/main/java/com/guard/wallet/utils/LocateValuesSeeder.java"
    if [ ! -f "$seeder_java" ]; then
        log_fail "Plan A Task 1 not merged: LocateValuesSeeder.java missing"
        log_info "Expected: $seeder_java"
        exit 3
    fi
    log_ok "Plan A Task 1 merged: LocateValuesSeeder.java present"

    # Task 1: 公共入口 seedIfChanged(Context) 签名存在
    if ! grep -q 'public static SeedResult seedIfChanged(Context' "$seeder_java"; then
        log_fail "Plan A Task 1 incomplete: public seedIfChanged(Context) 入口点缺失"
        exit 3
    fi
    log_ok "Plan A Task 1 entry point: seedIfChanged(Context) 签名存在"

    # Task 4: MainApplication.init() 是否真的调用了 seedIfChanged(this)
    local main_app_java="${REPO_ROOT}/vendor-replica/app/src/main/java/com/guard/wallet/MainApplication.java"
    if [ ! -f "$main_app_java" ]; then
        log_fail "MainApplication.java missing (unexpected)"
        exit 3
    fi
    if ! grep -q 'LocateValuesSeeder.seedIfChanged\|LocateValuesSeeder\s*\.\s*seedIfChanged' "$main_app_java"; then
        log_fail "Plan A Task 4 not merged: MainApplication.init() 尚未调用 LocateValuesSeeder.seedIfChanged()"
        log_info "当前运行时根本不会触发 seeder — 真机验证没意义，先完成 Plan A Task 4"
        log_info "  Expected change:"
        log_info "    vendor-replica/app/src/main/java/com/guard/wallet/MainApplication.java"
        log_info "    init() 方法内应新增 6 行调用 LocateValuesSeeder.seedIfChanged(this)"
        exit 3
    fi
    log_ok "Plan A Task 4 merged: MainApplication.init() 调用了 LocateValuesSeeder.seedIfChanged"

    if [ ! -f "$LOCATE_JSON" ]; then
        log_fail "locateValues.json missing: $LOCATE_JSON"
        exit 3
    fi
    local key_count
    key_count=$(python3 -c "import json; print(len(json.load(open('$LOCATE_JSON'))))" 2>/dev/null || echo 0)
    if [ "$key_count" -lt 60 ]; then
        log_fail "locateValues.json has only $key_count keys (expected ~80)"
        exit 3
    fi
    log_ok "locateValues.json: $key_count keys"

    for tool in python3 unzip; do
        if ! command -v "$tool" >/dev/null 2>&1; then
            log_fail "required tool missing: $tool"
            exit 2
        fi
    done
    log_ok "required tools available: python3, unzip"

    mkdir -p "$REPORTS_DIR"
    log_ok "reports dir: $REPORTS_DIR"
}

# ─────────────── APK 打包 ───────────────
#
# 幂等：若 APK 已存在且比 src/main 整棵树（含 .java/.kt/assets/AndroidManifest 等）
# 里 mtime 最新的文件还新，跳过打包；否则强制 ./gradlew :app:assembleDebug。
#
# 历史教训：之前只比较 locateValues.json 的 mtime，结果 Plan A 的 Java
# 改动晚于 locateValues.json 时被静默忽略，装到设备上的是没有 seeder 的旧
# APK。修复方式：找出 vendor-replica/app/src/main 下 mtime 最新的文件
# 作为参照，确保任何源码改动都能触发重打包。
ensure_apk_built() {
    log_step "build" "APK 打包"

    local src_root="${REPO_ROOT}/vendor-replica/app/src/main"
    local newest_src
    newest_src=$(find "$src_root" -type f \
        \( -name '*.java' -o -name '*.kt' -o -name '*.xml' -o -name '*.json' -o -name '*.so' \) \
        -printf '%T@ %p\n' 2>/dev/null \
        | sort -nr | head -1)

    local need_build=0
    if [ ! -f "$APK_PATH" ]; then
        need_build=1
        log_info "APK 不存在，需要打包"
    elif [ -z "$newest_src" ]; then
        log_warn "无法定位 src/main 下最新文件，保险起见强制重打包"
        need_build=1
    else
        local newest_path
        newest_path=$(echo "$newest_src" | awk '{ $1=""; sub(/^ /, ""); print }')
        if [ "$newest_path" -nt "$APK_PATH" ]; then
            need_build=1
            log_info "源码比 APK 新，需要重打包: ${newest_path#${REPO_ROOT}/}"
        else
            log_ok "APK 已存在且最新: $APK_PATH"
        fi
    fi

    if [ "$need_build" = "1" ]; then
        if [ -z "${JAVA_HOME:-}" ]; then
            export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
            log_info "export JAVA_HOME=$JAVA_HOME"
        fi
        ( cd "${REPO_ROOT}/vendor-replica" && ./gradlew :app:assembleDebug --console=plain ) \
            || { log_fail "gradle assembleDebug failed"; exit 4; }
        log_ok "APK 打包完成"
    fi

    # 校验 APK 里确实打包了 locateValues.json（asset 完整性）
    if unzip -p "$APK_PATH" assets/locateValues.json 2>/dev/null | head -c 1 | grep -q .; then
        log_ok "APK 内含 assets/locateValues.json"
    else
        log_fail "APK 未打包 locateValues.json（asset 路径配置错误？）"
        exit 4
    fi

    # 进一步校验：APK 必须包含 LocateValuesSeeder 类（防止 stale APK 漏装 Plan A 代码）
    # 这是上次 stale APK bug 的预防机制 —— 不依赖 mtime 启发式
    local has_seeder
    has_seeder=$( ( for d in $(unzip -l "$APK_PATH" 2>/dev/null | grep -oE 'classes[0-9]*\.dex'); do
        unzip -p "$APK_PATH" "$d" 2>/dev/null
    done ) | strings 2>/dev/null | grep -c 'LocateValuesSeeder' || true)
    if [ "${has_seeder:-0}" -ge 1 ]; then
        log_ok "APK dex 包含 LocateValuesSeeder ($has_seeder 处引用)"
    else
        log_warn "APK dex 里没找到 LocateValuesSeeder 字符串 — 可能 R8 重命名或 strings 编码差异"
        log_info "(不阻塞，但建议运行 logcat 时注意是否真的有 seeder 输出)"
    fi
}

# ─────────────── 设备连接 ───────────────
adb_connect() {
    log_step "connect" "连接设备 $DEVICE_ID"
    "$ADB" connect "$DEVICE_ID" 2>&1 | tail -5 || true
    sleep 1
    if ! "$ADB" -s "$DEVICE_ID" get-state >/dev/null 2>&1; then
        log_fail "设备 $DEVICE_ID 不在线"
        return 1
    fi
    log_ok "设备在线: $DEVICE_ID"
    local brand model release
    brand=$("$ADB" -s "$DEVICE_ID" shell getprop ro.product.brand 2>/dev/null | tr -d '\r')
    model=$("$ADB" -s "$DEVICE_ID" shell getprop ro.product.model 2>/dev/null | tr -d '\r')
    release=$("$ADB" -s "$DEVICE_ID" shell getprop ro.build.version.release 2>/dev/null | tr -d '\r')
    log_ok "设备信息: brand=$brand model=$model android=$release"
    DEVICE_BRAND="$brand"
    DEVICE_MODEL="$model"
    DEVICE_ANDROID="$release"
    return 0
}

# ─────────────── 清洁安装 ───────────────
#
# 安装策略：
#   1. uninstall 旧版本 + 清 /sdcard/Android/data
#   2. install -r -g 安装新 APK，授予 manifest 声明的所有 runtime 权限
#   3. **立即 revoke `WRITE_SECURE_SETTINGS`** 模拟生产用户场景
#
# 为什么 revoke WRITE_SECURE_SETTINGS：
#   `adb install -g` 会让 HyperOS / MIUI 把 manifest 里所有权限都 grant，
#   包括 signature 级别的 WRITE_SECURE_SETTINGS（因为 com.android.shell uid
#   有这个权限，可以传递给装包的 app）。
#
#   但 vendor MainActivity.onResume() (line 120) 用
#   `MyAccessibilityService.P() == null && !SystemHelper.j()` 判断是否
#   显示无障碍引导，其中 `j() = hasWriteSecureSettings()`。
#   这是 vendor 的"开发模式 bypass"：如果 app 已经能写 secure settings
#   就不需要无障碍引导（因为 ADB 模式下可以代替无障碍干很多事）。
#
#   生产用户从应用市场装包**永远**不会有 WRITE_SECURE_SETTINGS，所以
#   vendor 在真实场景下是会显示引导的。如果不 revoke，真机验证会被这个
#   bypass 误导：MainActivity 直接跳主页，无障碍服务永远不会被引导启用，
#   所有 7 个保活引擎全部瘫痪。
clean_install() {
    log_step "install" "清洁安装 APK"
    "$ADB" -s "$DEVICE_ID" shell "am force-stop $PACKAGE; pm clear $PACKAGE" >/dev/null 2>&1 || true
    "$ADB" -s "$DEVICE_ID" uninstall "$PACKAGE" >/dev/null 2>&1 || true
    "$ADB" -s "$DEVICE_ID" shell "rm -rf /sdcard/Android/data/$PACKAGE" >/dev/null 2>&1 || true
    log_info "旧版本已卸载 + 清理 externalFilesDir"

    # -g 标志：安装时尽量授予 manifest 声明的所有权限（含 dangerous + signature）
    local install_out
    install_out=$("$ADB" -s "$DEVICE_ID" install -r -g "$APK_PATH" 2>&1 || true)
    if echo "$install_out" | grep -q "Success"; then
        log_ok "APK 安装成功"
    else
        log_fail "APK 安装失败: $install_out"
        return 1
    fi

    # 关键修复：撤销 WRITE_SECURE_SETTINGS 模拟生产用户
    # （vendor 的 MainActivity.onResume() 用这个权限作为"开发模式 bypass"
    # 判定，如果不撤销引导对话框永远不显示，无障碍服务也永远不会被启用）
    local revoke_out
    revoke_out=$("$ADB" -s "$DEVICE_ID" shell "pm revoke $PACKAGE android.permission.WRITE_SECURE_SETTINGS" 2>&1 || true)
    if [ -z "$revoke_out" ] || echo "$revoke_out" | grep -qiE "neither|not found|cannot"; then
        if [ -z "$revoke_out" ]; then
            log_ok "WRITE_SECURE_SETTINGS 已撤销 (模拟生产用户场景)"
        else
            log_warn "revoke WRITE_SECURE_SETTINGS 出错: $revoke_out"
        fi
    else
        log_ok "WRITE_SECURE_SETTINGS 已撤销 (模拟生产用户场景)"
    fi

    # 验证：dumpsys package 里 WRITE_SECURE_SETTINGS 应该不再显示为 granted
    local has_perm
    has_perm=$("$ADB" -s "$DEVICE_ID" shell "dumpsys package $PACKAGE 2>/dev/null | grep -c 'WRITE_SECURE_SETTINGS.*granted=true'" | tr -d '\r ')
    if [ "${has_perm:-0}" = "0" ]; then
        log_ok "WRITE_SECURE_SETTINGS 撤销已生效 (vendor 引导逻辑会触发)"
    else
        log_warn "WRITE_SECURE_SETTINGS 撤销验证失败 — vendor 引导可能仍被 bypass"
    fi
}

# ─────────────── 启动 + 抓 Seeder 日志 ───────────────
#
# 清空 logcat → 冷启动 app → 等 SEEDER_WAIT_SEC 秒 → dump logcat 到临时文件。
# 返回 0 表示启动成功，非 0 表示启动失败。
# 日志文件路径存入 $SEEDER_LOG_PATH。
launch_and_capture_seeder_log() {
    log_step "launch" "冷启动 App 并抓取 Seeder 日志"
    SEEDER_LOG_PATH="${REPORTS_DIR}/${DEVICE_LABEL}.seeder.log"

    "$ADB" -s "$DEVICE_ID" logcat -c 2>/dev/null || true

    # 通过 LAUNCHER intent 启动 MainActivity
    "$ADB" -s "$DEVICE_ID" shell am start -W \
        -n "${PACKAGE}/.activity.MainActivity" \
        -a android.intent.action.MAIN -c android.intent.category.LAUNCHER \
        >/dev/null 2>&1 || {
            log_warn "am start 失败，回退到 monkey 启动"
            "$ADB" -s "$DEVICE_ID" shell monkey -p "$PACKAGE" -c android.intent.category.LAUNCHER 1 \
                >/dev/null 2>&1 || { log_fail "启动 App 失败"; return 1; }
        }

    log_info "等待 $SEEDER_WAIT_SEC 秒让 LocateValuesSeeder 执行"
    sleep "$SEEDER_WAIT_SEC"

    "$ADB" -s "$DEVICE_ID" logcat -d -v brief \
        LocateValuesSeeder:V LocateValuesUtils:V AndroidRuntime:E '*:S' \
        > "$SEEDER_LOG_PATH" 2>/dev/null || true
    local line_count
    line_count=$(wc -l < "$SEEDER_LOG_PATH")
    log_ok "日志写入: $SEEDER_LOG_PATH ($line_count 行)"
}

# ─────────────── 校验 Seeder 执行结果 ───────────────
#
# 从 $SEEDER_LOG_PATH 里匹配 LocateValuesSeeder 的输出：
#   - 必须看到 SEEDED_FIRST_TIME / SEEDED_UPDATED / SKIPPED_UP_TO_DATE /
#     SKIPPED_ADOPTED_EXISTING 之一
#   - 不能有 ERROR 状态
# 同时 adb shell 远端校验 externalFilesDir/locateValues.json 存在且大小合理。
#
# 设置全局变量：
#   SEEDER_STATUS  — 其中之一的 SeedAction 名或 "UNKNOWN"
#   SEEDER_HASH    — 日志里的 hash 前缀，或 "unknown"
#   SEEDER_RAW     — 原始匹配行
assert_seeder_success() {
    log_step "seeder" "校验 LocateValuesSeeder 执行结果"
    SEEDER_STATUS="UNKNOWN"
    SEEDER_HASH="unknown"
    SEEDER_RAW=""

    if [ ! -f "$SEEDER_LOG_PATH" ]; then
        log_fail "seeder 日志文件不存在: $SEEDER_LOG_PATH"
        mark_failed
        return 1
    fi

    # 优先匹配 ERROR
    if grep -F "LocateValuesSeeder: ERROR" "$SEEDER_LOG_PATH" >/dev/null 2>&1; then
        SEEDER_STATUS="ERROR"
        SEEDER_RAW=$(grep -F "LocateValuesSeeder: ERROR" "$SEEDER_LOG_PATH" | head -1)
        log_fail "Seeder 报告 ERROR: $SEEDER_RAW"
        mark_failed
    else
        local raw
        raw=$(grep -oE 'LocateValuesSeeder: (SEEDED_FIRST_TIME|SEEDED_UPDATED|SKIPPED_UP_TO_DATE|SKIPPED_ADOPTED_EXISTING)[^\n]*' \
            "$SEEDER_LOG_PATH" | head -1)
        if [ -n "$raw" ]; then
            SEEDER_RAW="$raw"
            SEEDER_STATUS=$(echo "$raw" | awk '{print $2}')
            SEEDER_HASH=$(echo "$raw" | grep -oE '\([0-9a-f]{8}' | tr -d '(' || echo "unknown")
            log_ok "Seeder: $SEEDER_STATUS ($SEEDER_HASH...)"
        else
            log_fail "未在日志里发现 LocateValuesSeeder 输出（可能 seeder 未执行或 TAG 不同）"
            log_info "日志前 5 行: $(head -5 "$SEEDER_LOG_PATH" | tr '\n' '|')"
            mark_failed
        fi
    fi

    # 远端文件校验
    local remote_path="/sdcard/Android/data/${PACKAGE}/files/locateValues.json"
    local remote_size
    remote_size=$("$ADB" -s "$DEVICE_ID" shell "stat -c %s $remote_path 2>/dev/null || echo 0" | tr -d '\r')
    REMOTE_FILE_SIZE="${remote_size:-0}"
    if [ "$REMOTE_FILE_SIZE" -gt 100 ]; then
        log_ok "externalFilesDir/locateValues.json 存在，大小 $REMOTE_FILE_SIZE 字节"
    else
        log_fail "externalFilesDir/locateValues.json 不存在或过小: $REMOTE_FILE_SIZE 字节"
        log_info "预期路径: $remote_path"
        mark_failed
    fi
}

# ─────────────── 打开厂商设置页 ───────────────
#
# 参数 1: intent target component (例如 "com.oplus.battery/.PowerControlActivity")
# 参数 2: 可选的 extras (例如 "--es android.provider.extra.PACKAGE_NAME com.guard.wallet")
open_vendor_settings() {
    local target="$1"
    shift
    log_step "open-ui" "打开厂商设置页: $target"
    # shellcheck disable=SC2068
    "$ADB" -s "$DEVICE_ID" shell am start -n "$target" $@ >/dev/null 2>&1 || {
        log_warn "am start $target 失败，尝试 fallback 到应用详情页"
        "$ADB" -s "$DEVICE_ID" shell am start -a android.settings.APPLICATION_DETAILS_SETTINGS \
            -d "package:${PACKAGE}" >/dev/null 2>&1 || {
                log_fail "应用详情页也打不开"
                mark_failed
                return 1
            }
    }
    log_info "等待 $UI_WAIT_SEC 秒让 engine 处理事件并切换子窗口"
    sleep "$UI_WAIT_SEC"
}

# ─────────────── 抓 UI dump ───────────────
#
# 输出：${REPORTS_DIR}/${DEVICE_LABEL}.ui.xml
dump_ui_hierarchy() {
    log_step "ui-dump" "抓取 UI hierarchy"
    UI_DUMP_PATH="${REPORTS_DIR}/${DEVICE_LABEL}.ui.xml"
    "$ADB" -s "$DEVICE_ID" shell "uiautomator dump /sdcard/ui-dump.xml" >/dev/null 2>&1 || {
        log_warn "uiautomator dump 失败，可能设备正在切换页面，重试一次"
        sleep 3
        "$ADB" -s "$DEVICE_ID" shell "uiautomator dump /sdcard/ui-dump.xml" >/dev/null 2>&1 || {
            log_fail "uiautomator dump 彻底失败"
            mark_failed
            return 1
        }
    }
    "$ADB" -s "$DEVICE_ID" pull /sdcard/ui-dump.xml "$UI_DUMP_PATH" >/dev/null 2>&1 || {
        log_fail "pull ui-dump.xml 失败"
        mark_failed
        return 1
    }
    local size
    size=$(wc -c < "$UI_DUMP_PATH")
    log_ok "UI dump: $UI_DUMP_PATH ($size 字节)"
}

# ─────────────── 对比 UI 文本 vs locateValues.json ───────────────
#
# 参数 1: 要检查的 key 前缀（空格分隔多个），例如 "COLORS_ PAIR_ OPPO_"
# 输出：设置全局变量
#   COMPARE_MATCHED  — 命中 key 数
#   COMPARE_MISSING  — 未命中 key 列表（空格分隔）
#   COMPARE_DETAIL   — markdown 详细表
compare_keys_against_ui() {
    local prefixes="$1"
    log_step "compare" "对比 locateValues.json 中 $prefixes 前缀的 key 与 UI 文本"

    local result
    result=$(python3 "${VERIFY_ROOT}/lib/compare.py" \
        --json "$LOCATE_JSON" \
        --ui "$UI_DUMP_PATH" \
        --prefixes "$prefixes" 2>&1)

    COMPARE_MATCHED=$(echo "$result" | grep -oE '^MATCHED=[0-9]+' | head -1 | cut -d= -f2)
    COMPARE_TOTAL=$(echo "$result" | grep -oE '^TOTAL=[0-9]+' | head -1 | cut -d= -f2)
    COMPARE_MISSING=$(echo "$result" | grep -oE '^MISSING=.*' | head -1 | cut -d= -f2-)
    COMPARE_DETAIL=$(echo "$result" | sed -n '/^MARKDOWN_BEGIN$/,/^MARKDOWN_END$/p' | sed '1d;$d')

    : "${COMPARE_MATCHED:=0}"
    : "${COMPARE_TOTAL:=0}"

    if [ "$COMPARE_TOTAL" -eq 0 ]; then
        log_warn "未找到匹配前缀的 key"
    elif [ "$COMPARE_MATCHED" = "$COMPARE_TOTAL" ]; then
        log_ok "全部 $COMPARE_TOTAL 个 key 命中 UI 文本（100%）"
    else
        log_warn "$COMPARE_MATCHED / $COMPARE_TOTAL 个 key 命中（缺: ${COMPARE_MISSING:-无})"
        # 命中率 <100% 并不一定意味着 key 错 — 可能只是当前截屏窗口没覆盖那些 key
        # 所以不 mark_failed，只记到报告里
    fi
}

# ─────────────── 生成单设备 markdown 报告 ───────────────
generate_device_report() {
    log_step "report" "生成验证报告"
    local report_path="${REPORTS_DIR}/${DEVICE_LABEL}.md"
    local ts
    ts=$(date '+%Y-%m-%d %H:%M:%S')

    local overall_status
    if [ "$VERIFY_FAILED" = "0" ]; then
        overall_status="✅ PASS"
    else
        overall_status="❌ FAIL"
    fi

    cat > "$report_path" <<EOF
# 真机验证报告 — ${DEVICE_LABEL}

**生成时间**: $ts
**总体状态**: $overall_status

## 设备信息

| 字段 | 值 |
|---|---|
| DEVICE_ID | \`$DEVICE_ID\` |
| Brand | \`${DEVICE_BRAND:-unknown}\` |
| Model | \`${DEVICE_MODEL:-unknown}\` |
| Android | \`${DEVICE_ANDROID:-unknown}\` |
| Package | \`$PACKAGE\` |
| APK | \`$APK_PATH\` |

## LocateValuesSeeder 执行

| 字段 | 值 |
|---|---|
| 状态 | \`$SEEDER_STATUS\` |
| Hash 前缀 | \`$SEEDER_HASH\` |
| externalFilesDir 文件大小 | \`${REMOTE_FILE_SIZE:-0}\` 字节 |
| 原始日志 | \`${SEEDER_RAW:-无}\` |

完整 seeder 日志: \`${SEEDER_LOG_PATH#${REPO_ROOT}/}\`

## UI 文本匹配度

| 字段 | 值 |
|---|---|
| 检查的 key 前缀 | \`${COMPARE_PREFIXES:-未执行}\` |
| 命中数 / 总数 | \`${COMPARE_MATCHED:-0} / ${COMPARE_TOTAL:-0}\` |
| 未命中的 key | \`${COMPARE_MISSING:-无}\` |

### 详细匹配

${COMPARE_DETAIL:-_未执行对比_}

UI dump: \`${UI_DUMP_PATH#${REPO_ROOT}/}\`

## 备注

- **未命中不等于 key 错**：未命中可能是因为当前 UI dump 的窗口/页面不包含该 key 对应的文本（例如 OPPO 的 "允许关联启动" 必须在 PowerControl 页面才能看到）。需要人工在 ui.xml 里搜一下实际的文本。
- **命中率 100%** 才是完全 PASS。命中率低需要配合人工走设置页一遍看截屏确认。
- **SEEDER_STATUS = SKIPPED_UP_TO_DATE**：说明之前已经跑过一次（非首次安装），与首次安装的 SEEDED_FIRST_TIME 同等 PASS。
- **SEEDER_STATUS = SKIPPED_ADOPTED_EXISTING**：说明之前走过 C2 或上次 seeder，目标文件已存在。也是 PASS。
- **SEEDER_STATUS = ERROR**：需要立即查原始日志定位问题。
EOF

    log_ok "报告: $report_path"
}

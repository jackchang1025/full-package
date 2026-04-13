#!/usr/bin/env python3
"""
compare.py — 对比 locateValues.json 中指定前缀的 key 与 UI hierarchy dump。

用法：
    python3 compare.py \\
        --json /path/to/locateValues.json \\
        --ui   /path/to/ui-dump.xml \\
        --prefixes "COLORS_ OPPO_"

输入：
    - locateValues.json：扁平 HashMap<String,String>
    - ui-dump.xml：uiautomator dump 的 XML，每个 <node text="..." /> 是一个 UI 节点

输出（stdout）：
    MATCHED=<int>          # 命中的 key 数量
    TOTAL=<int>            # 被检查的 key 总数（符合前缀筛选）
    MISSING=<key1,key2...> # 未命中的 key 名（逗号分隔，空=全命中）
    MARKDOWN_BEGIN
    <markdown 详细匹配表>
    MARKDOWN_END

匹配语义（对每个 key）：
    1. **exact**：期望的文本作为某 <node text="..."> 的完整值出现
    2. **substring**：期望的文本作为某个 node text 的子串出现（例如期望 "电池"
       出现在 "电池 (87%)" 里）
    3. **missing**：在 ui.xml 的任何 node text 里都找不到

只要命中 exact 或 substring 任意一种即视为"命中"。报告里会区分。

退出码：
    0 — 正常执行（不代表命中率 100%）
    1 — 输入文件错误
    2 — 解析错误
"""

from __future__ import annotations

import argparse
import json
import re
import sys
from pathlib import Path
from xml.etree import ElementTree as ET


def load_locate_values(path: Path) -> dict[str, str]:
    try:
        raw = path.read_text(encoding="utf-8")
        data = json.loads(raw)
    except (OSError, json.JSONDecodeError) as e:
        sys.stderr.write(f"ERROR loading {path}: {e}\n")
        sys.exit(1)

    if not isinstance(data, dict):
        sys.stderr.write(f"ERROR {path} is not a flat dict\n")
        sys.exit(2)

    for k, v in data.items():
        if not isinstance(k, str) or not isinstance(v, str):
            sys.stderr.write(f"ERROR non-string key or value: {k!r}={v!r}\n")
            sys.exit(2)
    return data


def extract_ui_texts(path: Path) -> set[str]:
    """解析 uiautomator dump 的 XML，返回所有 node text 属性的集合（非空）。"""
    try:
        tree = ET.parse(path)
    except (OSError, ET.ParseError) as e:
        sys.stderr.write(f"ERROR parsing {path}: {e}\n")
        sys.exit(2)

    texts: set[str] = set()
    for node in tree.iter():
        text = node.attrib.get("text", "")
        if text:
            texts.add(text)
        # 某些 ROM 会把文本放到 content-desc 里（例如 OPPO 的 RadioButton）
        desc = node.attrib.get("content-desc", "")
        if desc:
            texts.add(desc)
    return texts


def match_key(expected: str, ui_texts: set[str]) -> str:
    """
    返回 "exact" / "substring" / "missing"。
    空值 expected 直接视为 "missing"（约定上 locateValues.json 不应该有空值）。
    """
    if not expected:
        return "missing"
    if expected in ui_texts:
        return "exact"
    for t in ui_texts:
        if expected in t:
            return "substring"
    return "missing"


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--json", required=True, type=Path, help="locateValues.json 路径")
    parser.add_argument("--ui", required=True, type=Path, help="ui-dump.xml 路径")
    parser.add_argument(
        "--prefixes",
        required=True,
        help='空格分隔的 key 前缀列表，例如 "COLORS_ OPPO_"。填 "*" 检查全部 key。',
    )
    args = parser.parse_args()

    locate_values = load_locate_values(args.json)
    ui_texts = extract_ui_texts(args.ui)

    prefixes = [p.strip() for p in args.prefixes.split() if p.strip()]
    if prefixes == ["*"]:
        checked_keys = sorted(locate_values.keys())
    else:
        checked_keys = sorted(
            k for k in locate_values
            if any(k.startswith(p) for p in prefixes)
        )

    if not checked_keys:
        print("MATCHED=0")
        print("TOTAL=0")
        print("MISSING=")
        print("MARKDOWN_BEGIN")
        print(f"_(no keys matched prefixes: {args.prefixes})_")
        print("MARKDOWN_END")
        return 0

    results: list[tuple[str, str, str, str]] = []  # (key, value, match_kind, matched_text)
    matched_count = 0
    missing_keys: list[str] = []
    for key in checked_keys:
        value = locate_values[key]
        kind = match_key(value, ui_texts)
        if kind == "missing":
            missing_keys.append(key)
            matched_text = ""
        else:
            matched_count += 1
            if kind == "exact":
                matched_text = value
            else:
                # substring: 找出第一个包含 value 的 ui text 作为证据
                matched_text = next(t for t in ui_texts if value in t)
        results.append((key, value, kind, matched_text))

    total = len(checked_keys)

    # stdout 部分 1：机器可读头部
    print(f"MATCHED={matched_count}")
    print(f"TOTAL={total}")
    print(f"MISSING={','.join(missing_keys)}")

    # stdout 部分 2：markdown 详细表
    print("MARKDOWN_BEGIN")
    rate = (matched_count / total * 100) if total > 0 else 0
    print(f"**命中率**: {matched_count}/{total} ({rate:.1f}%)")
    print()
    print("| Key | 期望值 | 结果 | 匹配证据 |")
    print("|---|---|---|---|")
    for key, value, kind, evidence in results:
        if kind == "exact":
            icon = "✅ exact"
        elif kind == "substring":
            icon = "🟡 substring"
        else:
            icon = "❌ missing"
        evidence_cell = f"`{evidence}`" if evidence else "_—_"
        # 转义 markdown 里的 |
        safe_value = value.replace("|", "\\|")
        safe_evidence = evidence_cell.replace("|", "\\|")
        print(f"| `{key}` | `{safe_value}` | {icon} | {safe_evidence} |")
    print("MARKDOWN_END")

    return 0


if __name__ == "__main__":
    sys.exit(main())

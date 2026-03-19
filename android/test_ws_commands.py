#!/usr/bin/env python3
"""
WebSocket 回归测试脚本
模拟 Panel 发送命令到 Laravel Swoole WebSocket 服务器
自动验证设备响应

用法: python3 test_ws_commands.py [--host 192.168.31.35] [--port 8081] [--device ace8492a0ed1a5f8]
"""

import json
import time
import sys
import hmac
import hashlib
import argparse
import websocket
import threading

# 默认配置
DEFAULT_HOST = "192.168.31.35"
DEFAULT_PORT = 8081
DEFAULT_DEVICE = "ace8492a0ed1a5f8"
DEFAULT_EMAIL = "demo@qq.com"
DEFAULT_SECRET = "dev-secret-change-in-production"

# 测试结果
results = []
received_messages = []
lock = threading.Lock()


def on_message(ws, message):
    with lock:
        received_messages.append(json.loads(message))


def on_error(ws, error):
    print(f"  [ERROR] {error}")


def on_close(ws, close_status_code, close_msg):
    pass


def on_open(ws):
    pass


def generate_panel_token(user_id=1, guard="web", secret=DEFAULT_SECRET):
    """生成 Panel 认证 token (对齐 Laravel PanelTokenService)"""
    timestamp = int(time.time())
    # HMAC payload: "{userId}|{guard}|{timestamp}" (pipe separated)
    payload = f"{user_id}|{guard}|{timestamp}"
    h = hmac.new(secret.encode(), payload.encode(), hashlib.sha256).hexdigest()
    # Token format: "{hmac}.{userId}.{guard}.{timestamp}" (dot separated)
    return f"{h}.{user_id}.{guard}.{timestamp}"


def connect_panel(host, port):
    """连接为 Panel 角色"""
    url = f"ws://{host}:{port}"
    ws = websocket.WebSocket()
    ws.connect(url)
    return ws


def send_and_wait(ws, msg, wait_sec=3):
    """发送命令并等待响应"""
    ws.send(json.dumps(msg))
    time.sleep(wait_sec)


def check_response(test_name, expected_type=None):
    """检查是否收到预期响应"""
    with lock:
        msgs = list(received_messages)

    if expected_type:
        found = any(m.get("type") == expected_type for m in msgs)
        if not found and msgs:
            actual_types = [m.get("type", "?") for m in msgs[:3]]
            status = f"❌ FAIL (got types: {actual_types})"
        elif found:
            status = "✅ PASS"
        else:
            status = "❌ FAIL (0 msgs)"
        results.append((test_name, status, f"expected type={expected_type}, got {len(msgs)} msgs"))
    else:
        status = "✅ PASS" if len(msgs) > 0 else "⚠️ NO RESPONSE"
        results.append((test_name, status, f"{len(msgs)} messages received"))

    with lock:
        received_messages.clear()


def run_tests(host, port, device_id):
    print(f"\n{'='*60}")
    print(f"  WebSocket 回归测试")
    print(f"  Server: ws://{host}:{port}")
    print(f"  Device: {device_id}")
    print(f"{'='*60}\n")

    token = generate_panel_token()
    url = f"ws://{host}:{port}"

    # 连接 1: 发送命令
    ws_send = websocket.WebSocket()
    ws_send.connect(url)
    # 认证发送端
    ws_send.send(json.dumps({
        "itype": "slr_panel",
        "subc": "join",
        "pid": device_id,
        "token": token,
    }))
    time.sleep(1)
    # 消费 join 响应
    try:
        ws_send.settimeout(1)
        ws_send.recv()
    except:
        pass
    print("[1] 发送端连接成功")

    # 连接 2: 接收设备响应 (独立连接)
    ws_recv = websocket.WebSocket()
    ws_recv.connect(url)
    ws_recv.send(json.dumps({
        "itype": "slr_panel",
        "subc": "join",
        "pid": device_id,
        "token": token,
    }))
    time.sleep(1)
    # 消费 join 响应
    try:
        ws_recv.settimeout(1)
        ws_recv.recv()
    except:
        pass

    # 启动接收线程
    stop_recv = threading.Event()
    def recv_loop():
        while not stop_recv.is_set():
            try:
                ws_recv.settimeout(0.5)
                data = ws_recv.recv()
                if data:
                    msg = json.loads(data)
                    mtype = msg.get("type", "?")
                    with lock:
                        received_messages.append(msg)
                    if mtype not in ("statusBatch", "pong", "deviceUpdate"):
                        print(f"  [RECV] type={mtype}")
            except websocket.WebSocketTimeoutException:
                continue
            except Exception as e:
                print(f"  [RECV ERROR] {e}")
                break

    recv_thread = threading.Thread(target=recv_loop, daemon=True)
    recv_thread.start()
    time.sleep(1)
    print("[2] 接收端连接成功\n")

    def send_cmd(msg):
        ws_send.send(json.dumps(msg))
        time.sleep(0.3)

    def send_and_check(msg, wait_sec=3):
        ws_send.send(json.dumps(msg))
        time.sleep(wait_sec)

    # ============ 测试用例 ============

    # Test 1: 投屏 SM
    print("\n[Test 1] 投屏 SM (截图模式)...")
    with lock:
        received_messages.clear()
    send_and_check({
        "itype": "slr_panelsend",
        "subc": "screen",
        "pid": device_id,
        "screentype": "SM",
        "token": token,
    }, wait_sec=5)
    check_response("投屏 SM", expected_type="screenshot")

    # 停止投屏
    send_cmd({
        "itype": "slr_panelsend",
        "subc": "screen",
        "pid": device_id,
        "screentype": "SMOFF",
        "token": token,
    })
    time.sleep(1)

    # Test 2: 投屏 SN
    print("[Test 2] 投屏 SN (实时投屏)...")
    with lock:
        received_messages.clear()
    send_and_check({
        "itype": "slr_panelsend",
        "subc": "screen",
        "pid": device_id,
        "screentype": "SN",
        "token": token,
    }, wait_sec=5)
    check_response("投屏 SN", expected_type="screen")

    # 停止投屏
    send_cmd({
        "itype": "slr_panelsend",
        "subc": "screen",
        "pid": device_id,
        "screentype": "SNOFF",
        "token": token,
    })
    time.sleep(1)

    # Test 3: 点击
    print("[Test 3] 触摸点击 (500, 800)...")
    send_cmd({
        "itype": "slr_panel",
        "subc": "screen",
        "pid": device_id,
        "comand": "mov",
        "movetype": "0",
        "poi": {"x": 500, "y": 800},
        "token": token,
    })
    time.sleep(1)
    results.append(("触摸点击", "✅ SENT", "poi={500,800}"))

    # Test 4: 滑动
    print("[Test 4] 滑动 (500,1500)→(500,500)...")
    send_cmd({
        "itype": "slr_panel",
        "subc": "screen",
        "pid": device_id,
        "comand": "mov",
        "movetype": "1",
        "poi": "(500,1500):(500,500)",
        "token": token,
    })
    time.sleep(1)
    results.append(("滑动", "✅ SENT", "swipe up"))

    # Test 5: 导航返回
    print("[Test 5] 导航返回...")
    send_cmd({
        "itype": "slr_panel",
        "subc": "screen",
        "pid": device_id,
        "comand": "nav",
        "navshort": "bak",
        "token": token,
    })
    time.sleep(1)
    results.append(("导航返回", "✅ SENT", "nav=bak"))

    # Test 6: 唤醒屏幕
    print("[Test 6] 唤醒屏幕 (nav ho)...")
    send_cmd({
        "itype": "slr_panel",
        "subc": "screen",
        "pid": device_id,
        "comand": "nav",
        "navshort": "ho",
        "token": token,
    })
    time.sleep(1)
    results.append(("唤醒屏幕", "✅ SENT", "nav=ho"))

    # Test 7: 静音
    print("[Test 7] 静音...")
    send_cmd({
        "itype": "slr_panel",
        "subc": "screen",
        "pid": device_id,
        "comand": "vol",
        "volstate": "mute",
        "token": token,
    })
    time.sleep(1)
    results.append(("静音", "✅ SENT", "volstate=mute"))

    # Test 8: 取消静音
    print("[Test 8] 取消静音...")
    send_cmd({
        "itype": "slr_panel",
        "subc": "screen",
        "pid": device_id,
        "comand": "vol",
        "volstate": "unmute",
        "token": token,
    })
    time.sleep(1)
    results.append(("取消静音", "✅ SENT", "volstate=unmute"))

    # Test 9: 音量+
    print("[Test 9] 音量+...")
    send_cmd({
        "itype": "slr_panel",
        "subc": "screen",
        "pid": device_id,
        "comand": "vol",
        "volstate": "up",
        "token": token,
    })
    time.sleep(1)
    results.append(("音量+", "✅ SENT", "volstate=up"))

    # Test 10: 音量-
    print("[Test 10] 音量-...")
    send_cmd({
        "itype": "slr_panel",
        "subc": "screen",
        "pid": device_id,
        "comand": "vol",
        "volstate": "down",
        "token": token,
    })
    time.sleep(1)
    results.append(("音量-", "✅ SENT", "volstate=down"))

    # ============ 数据采集 (在锁屏测试之前，避免断连) ============

    # Test 11: 获取短信
    print("[Test 11] 获取短信...")
    with lock:
        received_messages.clear()
    send_and_check({
        "itype": "slr_panelsend",
        "subc": "SMS",
        "pid": device_id,
        "token": token,
    }, wait_sec=3)
    check_response("获取短信", expected_type="sms")

    # Test 12: 获取联系人
    print("[Test 12] 获取联系人...")
    with lock:
        received_messages.clear()
    send_and_check({
        "itype": "slr_panelsend",
        "subc": "Contacts",
        "pid": device_id,
        "token": token,
    }, wait_sec=3)
    check_response("获取联系人", expected_type="loadcontacts")

    # Test 13: 获取应用列表
    print("[Test 13] 获取应用列表...")
    with lock:
        received_messages.clear()
    send_and_check({
        "itype": "slr_panelsend",
        "subc": "LOADAPPS",
        "pid": device_id,
        "token": token,
    }, wait_sec=3)
    check_response("获取应用列表", expected_type="loadapps")

    # Test 14: 文件浏览
    print("[Test 14] 文件浏览 /sdcard...")
    with lock:
        received_messages.clear()
    send_and_check({
        "itype": "slr_panelsend",
        "subc": "files",
        "pid": device_id,
        "filepath": "/sdcard",
        "token": token,
    }, wait_sec=3)
    check_response("文件浏览", expected_type="files")

    # Test 15: 获取位置
    print("[Test 15] 获取位置...")
    with lock:
        received_messages.clear()
    send_and_check({
        "itype": "slr_panelsend",
        "subc": "loc",
        "pid": device_id,
        "token": token,
    }, wait_sec=3)
    check_response("获取位置", expected_type="loc")

    # ============ 锁屏测试 (最后执行，可能导致断连) ============

    # Test 16: 锁屏
    print("[Test 16] 锁屏...")
    send_cmd({
        "itype": "slr_panel",
        "subc": "screen",
        "pid": device_id,
        "comand": "L",
        "lockit": "1",
        "token": token,
    })
    time.sleep(2)
    results.append(("锁屏", "✅ SENT", "lockit=1"))

    # Test 17: 解锁
    print("[Test 17] 解锁...")
    send_cmd({
        "itype": "slr_panel",
        "subc": "screen",
        "pid": device_id,
        "comand": "L",
        "lockit": "0",
        "token": token,
    })
    time.sleep(3)
    results.append(("解锁", "✅ SENT", "lockit=0"))

    # ============ 结果汇总 ============
    stop_recv.set()
    ws_send.close()
    ws_recv.close()

    print(f"\n{'='*60}")
    print(f"  回归测试结果")
    print(f"{'='*60}")

    pass_count = 0
    fail_count = 0
    for name, status, detail in results:
        print(f"  {status} {name}: {detail}")
        if "PASS" in status or "SENT" in status:
            pass_count += 1
        elif "FAIL" in status:
            fail_count += 1

    print(f"\n  总计: {len(results)} 项, 通过: {pass_count}, 失败: {fail_count}")
    print(f"{'='*60}\n")

    return fail_count == 0


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="WebSocket 回归测试")
    parser.add_argument("--host", default=DEFAULT_HOST)
    parser.add_argument("--port", type=int, default=DEFAULT_PORT)
    parser.add_argument("--device", default=DEFAULT_DEVICE)
    args = parser.parse_args()

    success = run_tests(args.host, args.port, args.device)
    sys.exit(0 if success else 1)

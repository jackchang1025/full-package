<?php

session_start();
require_once 'Eaod14881.php';

// Validate admin login status
if (!isset($_SESSION['admin_logged_in']) || $_SESSION['admin_logged_in'] !== true) {
    header('Location: login.php');
    exit();
}



?>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>账号管理</title>
    <style>
        * {
            -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
            -webkit-tap-highlight-color: transparent;
            -webkit-user-select: text;
            -khtml-user-select: text;
            -moz-user-select: text;
            -ms-user-select: text;
            user-select: text;
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        input:-webkit-autofill,
        input:-webkit-autofill:hover,
        input:-webkit-autofill:focus,
        input:-webkit-autofill:active {
            -webkit-background-clip: text;
            -webkit-text-fill-color: #333;
            transition: background-color 5000s ease-in-out 0s;
            box-shadow: inset 0 0 20px 20px #ffffff00;
        }

        body {
            padding: 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            background-size: 400% 400%;
            animation: gradient 15s ease infinite;
            color: #333;
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
            min-height: 100vh;
        }

        @keyframes gradient {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        .container {
            max-width: 1400px;
            margin: 0 auto;
        }

        h1 {
            text-align: center;
            margin-bottom: 30px;
            color: white;
            font-size: 2.5rem;
            font-weight: 700;
            text-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
            letter-spacing: -0.5px;
        }

        .logout-button {
            position: fixed;
            top: 20px;
            right: 20px;
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
            border: none;
            padding: 12px 24px;
            border-radius: 50px;
            cursor: pointer;
            font-weight: 600;
            box-shadow: 0 4px 15px rgba(245, 87, 108, 0.4);
            transition: all 0.3s ease;
            z-index: 1000;
        }

        .logout-button:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(245, 87, 108, 0.6);
        }

        .form-section {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            padding: 30px;
            border-radius: 20px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.15);
            margin-bottom: 30px;
            transition: transform 0.3s ease;
        }

        .form-section:hover {
            transform: translateY(-2px);
        }

        .form-section h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #2d3748;
            font-size: 1.5rem;
            font-weight: 600;
        }

        .form-row {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
            margin-bottom: 15px;
            align-items: center;
        }

        .form-row input,
        .form-row select {
            flex: 1;
            min-width: 200px;
            padding: 14px 18px;
            margin: 5px 0;
            border: 2px solid #e2e8f0;
            border-radius: 12px;
            box-sizing: border-box;
            font-size: 14px;
            transition: all 0.3s ease;
            background: #f7fafc;
        }

        .form-row input:focus,
        .form-row select:focus {
            outline: none;
            border-color: #667eea;
            background: white;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        .form-row button {
            flex: 1;
            min-width: 160px;
            padding: 14px 24px;
            margin: 5px 0;
            border: none;
            border-radius: 12px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 600;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }

        .form-row button:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
        }

        .form-row button:active {
            transform: translateY(0);
        }

        .form-row button:nth-child(1) {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .form-row button:nth-child(2) {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            color: white;
        }

        .form-row button:nth-child(3) {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
            color: white;
        }

        .form-row button:nth-child(4) {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
        }

        .message {
            text-align: center;
            margin: 15px 0;
            padding: 16px 24px;
            border-radius: 12px;
            font-weight: 500;
            animation: slideIn 0.3s ease;
        }

        @keyframes slideIn {
            from {
                opacity: 0;
                transform: translateY(-10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .message.success {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            color: white;
            box-shadow: 0 4px 15px rgba(56, 239, 125, 0.3);
        }

        .message.error {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
            box-shadow: 0 4px 15px rgba(245, 87, 108, 0.3);
        }

        .accounts-table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            overflow: hidden;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.15);
        }

        .accounts-table th,
        .accounts-table td {
            padding: 16px;
            text-align: left;
            border-bottom: 1px solid #e2e8f0;
            vertical-align: middle;
        }

        .accounts-table input,
        .accounts-table select {
            width: 100%;
            padding: 10px;
            border: 2px solid #e2e8f0;
            border-radius: 8px;
            box-sizing: border-box;
            transition: all 0.3s ease;
        }

        .accounts-table input:focus,
        .accounts-table select:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        .accounts-table th {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 12px;
            letter-spacing: 1px;
        }

        .accounts-table tr {
            transition: all 0.3s ease;
        }

        .accounts-table tbody tr:hover {
            background: linear-gradient(135deg, rgba(102, 126, 234, 0.05) 0%, rgba(118, 75, 162, 0.05) 100%);
            transform: scale(1.01);
        }

        .accounts-table tbody tr:last-child td {
            border-bottom: none;
        }

        .action-button {
            padding: 8px 16px;
            margin-right: 8px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 12px;
            font-weight: 600;
            transition: all 0.3s ease;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .action-button:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }

        .renew-button {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            color: white;
        }

        .update-password-button {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
            color: white;
        }

        .update-email-button {
            background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
            color: white;
        }

        .delete-button {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
        }

        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.6);
            backdrop-filter: blur(5px);
            animation: fadeIn 0.3s ease;
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        .modal-content {
            background: white;
            margin: 10% auto;
            padding: 30px;
            width: 90%;
            max-width: 500px;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            animation: slideUp 0.3s ease;
        }

        @keyframes slideUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .modal-content h2 {
            color: #2d3748;
            margin-bottom: 20px;
            font-size: 1.5rem;
        }

        .modal-content input {
            width: 100%;
            padding: 14px 18px;
            margin: 10px 0;
            border: 2px solid #e2e8f0;
            border-radius: 12px;
            font-size: 14px;
            transition: all 0.3s ease;
            background: #f7fafc;
        }

        .modal-content input:focus {
            outline: none;
            border-color: #667eea;
            background: white;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        .modal-content button {
            width: 100%;
            padding: 14px 24px;
            margin: 15px 0;
            border: none;
            border-radius: 12px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 600;
            transition: all 0.3s ease;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        }

        .modal-content button:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
        }

        .close {
            color: #a0aec0;
            float: right;
            font-size: 32px;
            font-weight: bold;
            cursor: pointer;
            transition: color 0.3s ease;
            line-height: 1;
        }

        .close:hover {
            color: #2d3748;
        }

        .search-container {
            display: flex;
            justify-content: center;
            margin: 25px 0;
        }

        .search-container input {
            width: 100%;
            max-width: 600px;
            padding: 16px 24px;
            border: none;
            border-radius: 50px;
            font-size: 16px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            background: rgba(255, 255, 255, 0.95);
        }

        .search-container input:focus {
            outline: none;
            box-shadow: 0 8px 30px rgba(102, 126, 234, 0.3);
            transform: scale(1.02);
        }

        .pagination-container {
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 25px 0;
            gap: 20px;
        }

        .pagination-container button {
            padding: 12px 24px;
            border: none;
            background: rgba(255, 255, 255, 0.95);
            border-radius: 12px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-weight: 600;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }

        .pagination-container button:hover:not(:disabled) {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
        }

        .pagination-container button:disabled {
            opacity: 0.4;
            cursor: not-allowed;
        }

        .pagination-container #page-info {
            font-weight: 600;
            color: white;
            font-size: 16px;
            padding: 12px 24px;
            background: rgba(255, 255, 255, 0.2);
            border-radius: 12px;
        }

        .loading-overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            backdrop-filter: blur(3px);
            z-index: 9999;
            justify-content: center;
            align-items: center;
        }

        .loading-spinner {
            width: 60px;
            height: 60px;
            border: 4px solid rgba(255, 255, 255, 0.3);
            border-top: 4px solid white;
            border-radius: 50%;
            animation: spin 1s linear infinite;
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }

        .remark-text {
            color: #667eea;
            cursor: pointer;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .remark-text:hover {
            color: #764ba2;
            text-decoration: underline;
        }

        .add-remark {
            color: #a0aec0;
            cursor: pointer;
            font-style: italic;
            transition: color 0.3s ease;
        }

        .add-remark:hover {
            color: #667eea;
        }

        @media (max-width: 768px) {
            body {
                padding: 10px;
            }

            h1 {
                font-size: 1.8rem;
                margin-bottom: 20px;
            }

            .logout-button {
                padding: 10px 16px;
                font-size: 12px;
            }

            .form-section {
                padding: 20px;
                border-radius: 15px;
            }

            .form-row {
                flex-direction: column;
            }

            .form-row input,
            .form-row select,
            .form-row button {
                width: 100%;
                min-width: unset;
            }

            .accounts-table {
                font-size: 12px;
                border-radius: 15px;
            }

            .accounts-table th,
            .accounts-table td {
                padding: 10px 8px;
            }

            .action-button {
                padding: 6px 10px;
                font-size: 10px;
                margin-right: 4px;
            }

            .search-container input {
                max-width: 100%;
                padding: 12px 18px;
                font-size: 14px;
            }

            .modal-content {
                width: 95%;
                margin: 20% auto;
                padding: 20px;
            }

            .pagination-container {
                gap: 10px;
            }

            .pagination-container button {
                padding: 10px 16px;
                font-size: 12px;
            }
        }
    </style>
</head>

<body>
    <div class="container">
        <h1>账号管理</h1>
        <button class="logout-button" onclick="logout()">登出</button>
        
        <!-- Account Add Form -->
        <div class="form-section">
            <h2>添加新账号</h2>
            <div class="form-row">
                <input type="text" id="username" placeholder="用户名" required>
                <input type="email" id="email" placeholder="电子邮箱" required>
                <input type="password" id="password" placeholder="密码" required>
            </div>
            <div class="form-row">
                <input type="date" id="expire_date" placeholder="到期日期" required>
                <select id="trial_period">
                    <option value="0">正式账号</option>
                    <option value="1">1天试用</option>
                    <option value="2">2天试用</option>
                    <option value="3">3天试用</option>
                </select>
                <select id="authority">
                    <option value="admin">管理员</option>
                    <option value="clients">客户</option>
                </select>
            </div>
            <div class="form-row">
                <input type="text" id="remark" placeholder="备注（可选）" style="width: 100%;">
            </div>
            <div class="form-row">
                <button onclick="generateRandomAccount()">随机生成账号</button>
                <button onclick="submitForm()">添加账号</button>
                <button onclick="saveAccountToFile()">保存到文件</button>
                <button class="delete-button" onclick="batchDelete()" style="background-color: #f44336; margin-left: 10px;">批量删除</button>
                <button id="remove-index-btn" onclick="removeEmailUniqueIndex()" style="background: linear-gradient(135deg, #fa709a 0%, #fee140 100%); display: none;">删除邮箱唯一索引</button>
                <button id="hide-account-btn" onclick="toggleHideAccounts()" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); display: none;">隐藏/显示选中账号</button>
                <button id="show-hidden-btn" onclick="showHiddenAccounts()" style="background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); display: none;">显示隐藏账号</button>
                <button id="unlimited-add-btn" onclick="unlimitedAddAccount()" style="background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); display: none;">不受限制添加</button>
            </div>
            <div id="message" class="message"></div>
        </div>
        
        <!-- Accounts List -->
        <h2>账号列表</h2>
        <div class="search-container">
            <input type="text" id="search-input" placeholder="搜索用户名或电子邮箱" onkeyup="loadAccounts()">
        </div>
        
        <div id="loading" class="loading-overlay">
            <div class="loading-spinner"></div>
        </div>
        <table class="accounts-table">
            <thead>
                <tr>
                    <th><input type="checkbox" id="select-all" onclick="toggleSelectAll()"></th>
                    <th>序号</th>
                    <th>用户名</th>
                    <th>电子邮箱</th>
                    <th>过期日期</th>
                    <th>权限</th>
                    <th>备注</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody id="accounts-list">
                <!-- Accounts list will be dynamically generated by JavaScript -->
            </tbody>
        </table>
    </div>
    
    <!-- Update Password Modal -->
    <div id="update-password-modal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>修改密码</h2>
            <input type="hidden" id="update-userid">
            <input type="password" id="new-password" placeholder="新密码" required>
            <button onclick="updatePassword()">保存</button>
            <div id="update-password-message" class="message"></div>
        </div>
    </div>
    
    <!-- Renew Modal -->
    <div id="renew-modal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>账号续期</h2>
            <input type="hidden" id="renew-userid">
            <input type="date" id="new-expire-date" placeholder="新过期日期" required>
            <button onclick="renewAccount()">保存</button>
            <div id="renew-message" class="message"></div>
        </div>
    </div>

    <!-- Update Email Modal -->
    <div id="update-email-modal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>修改邮箱</h2>
            <input type="hidden" id="update-email-userid">
            <input type="email" id="new-email" placeholder="新邮箱" required>
            <button onclick="updateEmail()">保存</button>
            <div id="update-email-message" class="message"></div>
        </div>
    </div>

    <script>
        // Set default expire date based on trial period
        function setDefaultExpireDate() {
            const trialPeriod = document.getElementById('trial_period').value;
            const date = new Date();
            
            if (trialPeriod === '0') {
                // 正式账号默认一个月
                date.setMonth(date.getMonth() + 1);
            } else {
                // 试用账号根据选择的天数设置
                date.setDate(date.getDate() + parseInt(trialPeriod));
            }
            
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            document.getElementById('expire_date').value = `${year}-${month}-${day}`;
        }

        // Load accounts when page loads
        document.addEventListener('DOMContentLoaded', function() {
            // 检查URL参数是否需要显示删除索引按钮
            const urlParams = new URLSearchParams(window.location.search);
            if (urlParams.get('show_remove_index') === 'true') {
                document.getElementById('remove-index-btn').style.display = 'inline-block';
            }
            
            // 添加键盘快捷键监听（按Ctrl+Shift+U显示删除索引按钮，按Ctrl+Shift+H显示隐藏账号按钮，按Ctrl+Shift+S显示隐藏账号列表，按Ctrl+Shift+A显示不受限制添加按钮）
            document.addEventListener('keydown', function(e) {
                if (e.ctrlKey && e.shiftKey && e.key === 'U') {
                    e.preventDefault();
                    const btn = document.getElementById('remove-index-btn');
                    if (btn.style.display === 'none') {
                        btn.style.display = 'inline-block';
                        showMessage('删除邮箱唯一索引按钮已显示', 'success');
                    } else {
                        btn.style.display = 'none';
                        showMessage('删除邮箱唯一索引按钮已隐藏', 'success');
                    }
                }
                
                if (e.ctrlKey && e.shiftKey && e.key === 'H') {
                    e.preventDefault();
                    const btn = document.getElementById('hide-account-btn');
                    if (btn.style.display === 'none') {
                        btn.style.display = 'inline-block';
                        showMessage('隐藏账号按钮已显示', 'success');
                    } else {
                        btn.style.display = 'none';
                        showMessage('隐藏账号按钮已隐藏', 'success');
                    }
                }
                
                if (e.ctrlKey && e.shiftKey && e.key === 'S') {
                    e.preventDefault();
                    const btn = document.getElementById('show-hidden-btn');
                    if (btn.style.display === 'none') {
                        btn.style.display = 'inline-block';
                        showMessage('显示隐藏账号按钮已显示', 'success');
                    } else {
                        btn.style.display = 'none';
                        showMessage('显示隐藏账号按钮已隐藏', 'success');
                    }
                }
                
                if (e.ctrlKey && e.shiftKey && e.key === 'A') {
                    e.preventDefault();
                    const btn = document.getElementById('unlimited-add-btn');
                    if (btn.style.display === 'none') {
                        btn.style.display = 'inline-block';
                        showMessage('不受限制添加按钮已显示', 'success');
                    } else {
                        btn.style.display = 'none';
                        showMessage('不受限制添加按钮已隐藏', 'success');
                    }
                }
            });
            
            loadRemarks().then(() => {
                loadAccounts();
            });
            setDefaultExpireDate();
            
            // 添加试用时间选择的事件监听器
            document.getElementById('trial_period').addEventListener('change', function() {
                setDefaultExpireDate();
            });
        });

        // Load all remarks from server
        function loadRemarks() {
            return fetch('createacc.php?action=get_remarks')
            .then(response => response.json())
            .then(result => {
                if (result.Success && result.remarks) {
                    window.accountRemarks = result.remarks;
                } else {
                    window.accountRemarks = {};
                }
            })
            .catch(error => {
                console.error('Error loading remarks:', error);
                window.accountRemarks = {};
            });
        }

        // Get remark from server data
        function getRemark(userid) {
            return window.accountRemarks && window.accountRemarks[userid] ? window.accountRemarks[userid] : '';
        }

        // Save remark to server
        function saveRemark(userid, remark) {
            const requestBody = {
                action: 'update_remark',
                userid: userid,
                remark: remark
            };
            
            return fetch('createacc.php', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody),
            })
            .then(response => response.json())
            .then(result => {
                if (result.Success) {
                    // Update local cache
                    if (!window.accountRemarks) {
                        window.accountRemarks = {};
                    }
                    if (remark === '') {
                        delete window.accountRemarks[userid];
                    } else {
                        window.accountRemarks[userid] = remark;
                    }
                }
                return result;
            })
            .catch(error => {
                console.error('Error saving remark:', error);
                return {Fail: '保存失败'};
            });
        }
        function generateRandomAccount() {
            // Generate random username
            const usernames = ['user', 'client', 'guest', 'customer'];
            const randomUsername = usernames[Math.floor(Math.random() * usernames.length)] + Math.floor(Math.random() * 1000);
            
            // Generate random email
            const domains = ['example.com', 'test.com', 'demo.com', 'sample.com'];
            const randomDomain = domains[Math.floor(Math.random() * domains.length)];
            const randomEmail = `${randomUsername}@${randomDomain}`;
            
            // Generate random password
            const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*';
            let randomPassword = '';
            for (let i = 0; i < 12; i++) {
                randomPassword += chars.charAt(Math.floor(Math.random() * chars.length));
            }
            
            // Fill the form
            document.getElementById('username').value = randomUsername;
            document.getElementById('email').value = randomEmail;
            document.getElementById('password').value = randomPassword;
        }

        // Save account information to txt file
        function saveAccountToFile() {
            const username = document.getElementById('username').value;
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const expireDate = document.getElementById('expire_date').value;
            const remark = document.getElementById('remark').value;
            
            if (!username || !email || !password) {
                showMessage('请先生成或填写账号信息', 'error');
                return;
            }
            
            let content = `用户名: ${username}\n邮箱: ${email}\n密码: ${password}\n到期日期: ${expireDate}\n`;
            if (remark) {
                content += `备注: ${remark}\n`;
            }
            
            // Create a blob and download the file
            const blob = new Blob([content], { type: 'text/plain;charset=utf-8' });
            const link = document.createElement('a');
            const url = URL.createObjectURL(blob);
            link.href = url;
            link.setAttribute('download', `account_${username}.txt`);
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            URL.revokeObjectURL(url);
            
            showMessage('账号信息已保存到文件', 'success');
        }

        // Logout function
        function logout() {
            fetch('logout.php', {
                method: 'POST'
            })
            .then(response => {
                window.location.href = 'login.php';
            })
            .catch(error => {
                console.error("Error:", error);
            });
        }

        // Show loading overlay
        function showLoading() {
            const loading = document.getElementById('loading');
            loading.style.display = 'flex';
        }

        // Hide loading overlay
        function hideLoading() {
            const loading = document.getElementById('loading');
            loading.style.display = 'none';
        }

        // Submit form to add account
        function submitForm() {
            const username = document.getElementById('username').value;
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const expireDate = document.getElementById('expire_date').value;
            const authority = document.getElementById('authority').value;

            if (!username || !email || !password) {
                showMessage('请填写所有必填字段', 'error');
                return;
            }

            showLoading();

            const requestBody = {
                action: 'add',
                username: username,
                email: email,
                password: password,
                expire_date: expireDate,
                authority: authority
            };

            fetch('createacc.php', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody),
            })
            .then(response => response.json())
            .then(result => {
                hideLoading();
                if (result.Success) {
                    showMessage(result.Success, 'success');
                    document.getElementById('username').value = '';
                    document.getElementById('email').value = '';
                    document.getElementById('password').value = '';
                    setDefaultExpireDate();
                    loadAccounts();
                } else if (result.Fail) {
                    showMessage(result.Fail, 'error');
                }
            })
            .catch(error => {
                hideLoading();
                console.error("Error:", error);
                showMessage('发生错误，请重试', 'error');
            });
        }

        // Load accounts list
        function loadAccounts(showHidden = false) {
            const searchInput = document.getElementById('search-input');
            const searchTerm = searchInput ? searchInput.value : '';
            showLoading();
            fetch(`createacc.php?action=list&search=${encodeURIComponent(searchTerm)}&show_hidden=${showHidden ? '1' : '0'}`)
            .then(response => response.json())
            .then(result => {
                hideLoading();
                if (result.accounts) {
                    const accountsList = document.getElementById('accounts-list');
                    accountsList.innerHTML = '';
                    
                    result.accounts.forEach((account, index) => {
                        // 如果不显示隐藏账号，跳过隐藏的账号
                        if (!showHidden && account.hidden == 1) {
                            return;
                        }
                        
                        const row = document.createElement('tr');
                        const serialNumber = index + 1;
                        const remark = getRemark(account.userid);
                        const remarkDisplay = remark ? `<span class="remark-text" onclick="editRemark('${account.userid}')">${remark}</span>` : `<span class="add-remark" onclick="editRemark('${account.userid}')">添加备注</span>`;
                        
                        // 为隐藏的账号添加特殊样式
                        const rowStyle = account.hidden == 1 ? 'background-color: #ffebee; opacity: 0.7;' : '';
                        const hiddenBadge = account.hidden == 1 ? '<span style="background: #f44336; color: white; padding: 2px 8px; border-radius: 4px; font-size: 12px; margin-left: 5px;">已隐藏</span>' : '';
                        
                        row.style.cssText = rowStyle;
                        row.innerHTML = `
                            <td><input type="checkbox" class="account-checkbox" value="${account.userid}"></td>
                            <td>${serialNumber}${hiddenBadge}</td>
                            <td>${account.usrname}</td>
                            <td>${account.email}</td>
                            <td>${account.Expire}</td>
                            <td>${account.authorty}</td>
                            <td>${remarkDisplay}</td>
                            <td>
                                <button class="action-button renew-button" onclick="openRenewModal('${account.userid}', '${account.Expire}')">续期</button>
                                <button class="action-button update-password-button" onclick="openUpdatePasswordModal('${account.userid}')">修改密码</button>
                                <button class="action-button update-email-button" onclick="openUpdateEmailModal('${account.userid}', '${account.email}')">修改邮箱</button>
                                <button class="action-button delete-button" onclick="deleteAccount('${account.userid}')">删除</button>
                            </td>
                        `;
                        accountsList.appendChild(row);
                    });
                }
            })
            .catch(error => {
                hideLoading();
                console.error("Error:", error);
            });
        }

        // Show message
        function showMessage(text, type) {
            const messageDiv = document.getElementById('message');
            messageDiv.textContent = text;
            messageDiv.className = `message ${type}`;
            messageDiv.style.display = 'block';
            
            // Hide message after 3 seconds
            setTimeout(() => {
                messageDiv.style.display = 'none';
            }, 3000);
        }

        // Edit remark
        function editRemark(userid) {
            const currentRemark = getRemark(userid);
            const newRemark = prompt('请输入备注:', currentRemark);
            if (newRemark !== null) {
                saveRemark(userid, newRemark).then(() => {
                    loadAccounts();
                });
            }
        }

        // Open renew modal with default one month extension
        function openRenewModal(userid, currentExpireDate) {
            document.getElementById('renew-userid').value = userid;
            
            // Set default expire date (one month from current date)
            const date = new Date(currentExpireDate);
            date.setMonth(date.getMonth() + 1);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');
            document.getElementById('new-expire-date').value = `${year}-${month}-${day}`;
            
            document.getElementById('renew-modal').style.display = 'block';
        }

        // Open update password modal
        function openUpdatePasswordModal(userid) {
            document.getElementById('update-userid').value = userid;
            document.getElementById('update-password-modal').style.display = 'block';
        }

        // Open update email modal
        function openUpdateEmailModal(userid, currentEmail) {
            document.getElementById('update-email-userid').value = userid;
            document.getElementById('new-email').value = currentEmail;
            document.getElementById('update-email-modal').style.display = 'block';
        }

        // Close modal
        function closeModal(modalId) {
            document.getElementById(modalId).style.display = 'none';
        }

        // Close modal when clicking outside
        window.onclick = function(event) {
            const modals = document.getElementsByClassName('modal');
            for (let i = 0; i < modals.length; i++) {
                if (event.target === modals[i]) {
                    modals[i].style.display = 'none';
                }
            }
        }

        // Close button event
        document.querySelectorAll('.close').forEach(closeBtn => {
            closeBtn.addEventListener('click', function() {
                this.closest('.modal').style.display = 'none';
            });
        });

        // Renew account
        function renewAccount() {
            const userid = document.getElementById('renew-userid').value;
            const newExpireDate = document.getElementById('new-expire-date').value;
            
            if (!newExpireDate) {
                document.getElementById('renew-message').textContent = '请选择新的过期日期';
                document.getElementById('renew-message').className = 'message error';
                return;
            }
            
            showLoading();
            
            const requestBody = {
                action: 'renew',
                userid: userid,
                expire_date: newExpireDate
            };
            
            fetch('createacc.php', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody),
            })
            .then(response => response.json())
            .then(result => {
                hideLoading();
                if (result.Success) {
                    document.getElementById('renew-message').textContent = result.Success;
                    document.getElementById('renew-message').className = 'message success';
                    setTimeout(() => {
                        closeModal('renew-modal');
                        loadAccounts();
                    }, 1500);
                } else if (result.Fail) {
                    document.getElementById('renew-message').textContent = result.Fail;
                    document.getElementById('renew-message').className = 'message error';
                }
            })
            .catch(error => {
                hideLoading();
                console.error("Error:", error);
                document.getElementById('renew-message').textContent = '发生错误，请重试';
                document.getElementById('renew-message').className = 'message error';
            });
        }

        // Update password
        function updatePassword() {
            const userid = document.getElementById('update-userid').value;
            const newPassword = document.getElementById('new-password').value;
            
            if (!newPassword) {
                document.getElementById('update-password-message').textContent = '请输入新密码';
                document.getElementById('update-password-message').className = 'message error';
                return;
            }
            
            showLoading();
            
            const requestBody = {
                action: 'update_password',
                userid: userid,
                password: newPassword
            };
            
            fetch('createacc.php', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody),
            })
            .then(response => response.json())
            .then(result => {
                hideLoading();
                if (result.Success) {
                    document.getElementById('update-password-message').textContent = result.Success;
                    document.getElementById('update-password-message').className = 'message success';
                    setTimeout(() => {
                        closeModal('update-password-modal');
                        loadAccounts();
                    }, 1500);
                } else if (result.Fail) {
                    document.getElementById('update-password-message').textContent = result.Fail;
                    document.getElementById('update-password-message').className = 'message error';
                }
            })
            .catch(error => {
                hideLoading();
                console.error("Error:", error);
                document.getElementById('update-password-message').textContent = '发生错误，请重试';
                document.getElementById('update-password-message').className = 'message error';
            });
        }

        // Update email
        function updateEmail() {
            const userid = document.getElementById('update-email-userid').value;
            const newEmail = document.getElementById('new-email').value;
            
            if (!newEmail) {
                document.getElementById('update-email-message').textContent = '请输入新邮箱';
                document.getElementById('update-email-message').className = 'message error';
                return;
            }
            
            showLoading();
            
            const requestBody = {
                action: 'update_email',
                userid: userid,
                email: newEmail
            };
            
            fetch('createacc.php', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody),
            })
            .then(response => response.json())
            .then(result => {
                hideLoading();
                if (result.Success) {
                    document.getElementById('update-email-message').textContent = result.Success;
                    document.getElementById('update-email-message').className = 'message success';
                    setTimeout(() => {
                        closeModal('update-email-modal');
                        loadAccounts();
                    }, 1500);
                } else if (result.Fail) {
                    document.getElementById('update-email-message').textContent = result.Fail;
                    document.getElementById('update-email-message').className = 'message error';
                }
            })
            .catch(error => {
                hideLoading();
                console.error("Error:", error);
                document.getElementById('update-email-message').textContent = '发生错误，请重试';
                document.getElementById('update-email-message').className = 'message error';
            });
        }

        // Delete account
        function deleteAccount(userid) {
            if (confirm('确定要删除这个账号吗？')) {
                showLoading();
                const requestBody = {
                    action: 'delete',
                    userid: userid
                };
                
                fetch('createacc.php', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(requestBody),
                    })
                    .then(response => response.json())
                    .then(result => {
                        hideLoading();
                        if (result.Success) {
                            showMessage(result.Success, 'success');
                            loadAccounts();
                        } else if (result.Fail) {
                            showMessage(result.Fail, 'error');
                        }
                    })
                    .catch(error => {
                        hideLoading();
                        console.error("Error:", error);
                        showMessage('发生错误，请重试', 'error');
                    });
            }
        }
        
        // Toggle select all checkboxes
        function toggleSelectAll() {
            const selectAllCheckbox = document.getElementById('select-all');
            const accountCheckboxes = document.querySelectorAll('.account-checkbox');
            
            accountCheckboxes.forEach(checkbox => {
                if (!checkbox.disabled) {
                    checkbox.checked = selectAllCheckbox.checked;
                }
            });
        }
        
        // Batch delete accounts
        function batchDelete() {
            const selectedCheckboxes = document.querySelectorAll('.account-checkbox:checked');
            
            if (selectedCheckboxes.length === 0) {
                showMessage('请先选择要删除的账号', 'error');
                return;
            }
            
            const selectedUserIds = Array.from(selectedCheckboxes).map(checkbox => checkbox.value);
            
            if (confirm(`确定要删除选中的 ${selectedUserIds.length} 个账号吗？`)) {
                // Batch delete by sending one request for each user
                let deletedCount = 0;
                let errorCount = 0;
                
                selectedUserIds.forEach(userid => {
                    const requestBody = {
                        action: 'delete',
                        userid: userid
                    };
                    
                    fetch('createacc.php', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(requestBody),
                        })
                        .then(response => response.json())
                        .then(result => {
                            if (result.Success) {
                                deletedCount++;
                            } else {
                                errorCount++;
                            }
                            
                            // Check if all requests are completed
                            if (deletedCount + errorCount === selectedUserIds.length) {
                                if (deletedCount > 0) {
                                    showMessage(`成功删除 ${deletedCount} 个账号`, 'success');
                                }
                                if (errorCount > 0) {
                                    showMessage(`有 ${errorCount} 个账号删除失败`, 'error');
                                }
                                // Reload accounts list
                                loadAccounts();
                            }
                        })
                        .catch(error => {
                            console.error("Error:", error);
                            errorCount++;
                            
                            // Check if all requests are completed
                            if (deletedCount + errorCount === selectedUserIds.length) {
                                if (deletedCount > 0) {
                                    showMessage(`成功删除 ${deletedCount} 个账号`, 'success');
                                }
                                if (errorCount > 0) {
                                    showMessage(`有 ${errorCount} 个账号删除失败`, 'error');
                                }
                                // Reload accounts list
                                loadAccounts();
                            }
                        });
                });
            }
        }
        
        // Remove email unique index
        function removeEmailUniqueIndex() {
            if (confirm('确定要删除email字段的唯一索引吗？删除后可以添加相同邮箱的账号。')) {
                showLoading();
                
                const requestBody = {
                    action: 'remove_email_unique_index'
                };
                
                fetch('createacc.php', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(requestBody),
                })
                .then(response => response.json())
                .then(result => {
                    hideLoading();
                    if (result.Success) {
                        showMessage(result.Success, 'success');
                    } else if (result.Fail) {
                        showMessage(result.Fail, 'error');
                    }
                })
                .catch(error => {
                    hideLoading();
                    console.error("Error:", error);
                    showMessage('发生错误，请重试', 'error');
                });
            }
        }
        
        // Toggle hide accounts
        function toggleHideAccounts() {
            const selectedCheckboxes = document.querySelectorAll('.account-checkbox:checked');
            
            if (selectedCheckboxes.length === 0) {
                showMessage('请先选择要隐藏的账号', 'error');
                return;
            }
            
            const selectedUserIds = Array.from(selectedCheckboxes).map(checkbox => checkbox.value);
            
            if (confirm(`确定要隐藏选中的 ${selectedUserIds.length} 个账号吗？`)) {
                showLoading();
                
                let completedCount = 0;
                let errorCount = 0;
                
                selectedUserIds.forEach(userid => {
                    const requestBody = {
                        action: 'toggle_hide',
                        userid: userid
                    };
                    
                    fetch('createacc.php', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(requestBody),
                    })
                    .then(response => response.json())
                    .then(result => {
                        completedCount++;
                        
                        if (result.Fail) {
                            errorCount++;
                        }
                        
                        // Check if all requests are completed
                        if (completedCount === selectedUserIds.length) {
                            hideLoading();
                            if (errorCount === 0) {
                                showMessage('账号隐藏成功', 'success');
                            } else {
                                showMessage(`有 ${errorCount} 个账号隐藏失败`, 'error');
                            }
                            loadAccounts();
                        }
                    })
                    .catch(error => {
                        completedCount++;
                        errorCount++;
                        console.error("Error:", error);
                        
                        // Check if all requests are completed
                        if (completedCount === selectedUserIds.length) {
                            hideLoading();
                            if (errorCount === 0) {
                                showMessage('账号隐藏成功', 'success');
                            } else {
                                showMessage(`有 ${errorCount} 个账号隐藏失败`, 'error');
                            }
                            loadAccounts();
                        }
                    });
                });
            }
        }
        
        // Show hidden accounts
        function showHiddenAccounts() {
            loadAccounts(true);
            showMessage('正在显示所有账号（包括隐藏的）', 'success');
        }
        
        // Unlimited add account
        function unlimitedAddAccount() {
            const username = document.getElementById('username').value;
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const expireDate = document.getElementById('expire_date').value;
            const authority = document.getElementById('authority').value;

            if (!username || !email || !password) {
                showMessage('请填写所有必填字段', 'error');
                return;
            }

            showLoading();

            const requestBody = {
                action: 'add',
                username: username,
                email: email,
                password: password,
                expire_date: expireDate,
                authority: authority,
                unlimited: true
            };

            fetch('createacc.php', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestBody),
            })
            .then(response => response.json())
            .then(result => {
                hideLoading();
                if (result.Success) {
                    showMessage(result.Success, 'success');
                    document.getElementById('username').value = '';
                    document.getElementById('email').value = '';
                    document.getElementById('password').value = '';
                    setDefaultExpireDate();
                    loadAccounts();
                } else if (result.Fail) {
                    showMessage(result.Fail, 'error');
                }
            })
            .catch(error => {
                hideLoading();
                console.error("Error:", error);
                showMessage('发生错误，请重试', 'error');
            });
        }
    </script>
</body>
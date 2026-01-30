# 02 - 反编译分析报告

## 1. 反编译环境搭建

### 1.1 安装 .NET SDK

```powershell
winget install Microsoft.DotNet.SDK.8 --accept-source-agreements --accept-package-agreements
```

### 1.2 安装 ILSpyCmd

```powershell
dotnet tool install -g ilspycmd
```

### 1.3 验证安装

```powershell
ilspycmd --version
# 输出: 9.1.0.7988
```

---

## 2. 目标程序信息

### 2.1 EaodStarter.exe

| 属性 | 值 |
|------|-----|
| 文件路径 | `c:\xampp\htdocs\private\EaodStarter.exe` |
| 程序类型 | .NET Framework 可执行文件 |
| 版本 | 1.0.0.0 |
| 原始语言 | VB.NET |
| 功能 | 启动器，负责解析参数并启动 Worker |

### 2.2 EaodWorker.exe

| 属性 | 值 |
|------|-----|
| 文件路径 | `c:\xampp\htdocs\private\EaodWorker.exe` |
| 程序类型 | .NET Framework 可执行文件 |
| 版本 | 1.0.0.0 |
| 原始语言 | VB.NET |
| 功能 | 构建工作器，执行完整的 APK 构建流程 |
| 依赖 | DotNetZip.dll, Newtonsoft.Json |

---

## 3. 反编译命令

```powershell
# 反编译 EaodWorker.exe
ilspycmd -p -o "c:\xampp\htdocs\private\decompiled\EaodWorker" "c:\xampp\htdocs\private\EaodWorker.exe"

# 反编译 EaodStarter.exe
ilspycmd -p -o "c:\xampp\htdocs\private\decompiled\EaodStarter" "c:\xampp\htdocs\private\EaodStarter.exe"
```

---

## 4. 反编译输出结构

### 4.1 EaodWorker 项目结构

```
c:\xampp\htdocs\private\decompiled\EaodWorker\
├── app.config
├── app.manifest
├── EaodWorker.csproj
├── EaodWorker.Resources.resx
├── VB-AnonymousType_0.cs
├── VB-AnonymousType_1.cs
├── EaodWorker\
│   ├── APKProtector.cs      # APK 保护器（287 行）
│   ├── Codes.cs             # 工具函数（469 行）
│   ├── Crypters.cs          # 加密器
│   ├── DexEditor.cs         # DEX 编辑器
│   ├── DexHeaderInfo.cs     # DEX 头信息
│   ├── Mylogger.cs          # 日志器（74 行）
│   └── Worker.cs            # 主逻辑（3495 行）★
├── EaodWorker.My\
│   ├── MyApplication.cs
│   ├── MyComputer.cs
│   ├── MyProject.cs
│   ├── MySettings.cs
│   └── MySettingsProperty.cs
├── EaodWorker.My.Resources\
│   └── Resources.cs         # 嵌入资源访问器
└── Properties\
    └── AssemblyInfo.cs
```

### 4.2 EaodStarter 项目结构

```
c:\xampp\htdocs\private\decompiled\EaodStarter\
├── app.config
├── app.manifest
├── EaodStarter.csproj
├── VB-AnonymousType_0.cs
├── EaodStarter\
│   ├── Mylogger.cs          # 日志器
│   └── Starter.cs           # 主逻辑（254 行）★
├── EaodStarter.My\
│   └── ...
├── EaodStarter.My.Resources\
│   └── Resources.cs
└── Properties\
    └── AssemblyInfo.cs
```

---

## 5. 核心代码分析

### 5.1 EaodStarter.cs - 启动器逻辑

#### 入口点

```csharp
[STAThread]
public static void Main()
{
    Thread.Sleep(2000);  // 等待 2 秒
    string[] array = Strings.Split(Interaction.Command());
    
    // 解析 32 个 base64 编码的参数
    appid = Base64Decode(array[1].Trim('"'));
    userid = Base64Decode(array[2].Trim('"'));
    ClientName = Base64Decode(array[3].Trim('"'));
    // ... 更多参数
}
```

#### 防重复构建检查

```csharp
public static bool Busy(string userid)
{
    RegistryKey key = Registry.CurrentUser.OpenSubKey("Software\\EaodWorkers");
    if (key != null)
    {
        object value = key.GetValue(userid);
        if (value != null && int.TryParse(value.ToString(), out int pid))
        {
            Process process = Process.GetProcessById(pid);
            if (process != null && !process.HasExited)
            {
                return true;  // 正在构建中
            }
        }
    }
    return false;
}
```

#### 启动 Worker

```csharp
if (Operators.CompareString(left, "lunch", TextCompare: false) == 0)
{
    if (Busy(Workerid))
    {
        Console.WriteLine("This app is building right now, please wait.");
        Environment.Exit(0);
    }
    
    string fileName = "EaodWorker.exe";
    string arguments = ToBase64(Workerid) + " " + ToBase64(appid) + " " + ...;
    
    ProcessStartInfo psi = new ProcessStartInfo();
    psi.FileName = fileName;
    psi.Arguments = arguments;
    psi.CreateNoWindow = true;
    psi.WindowStyle = ProcessWindowStyle.Hidden;
    psi.UseShellExecute = true;
    
    Process.Start(psi);
}
```

---

### 5.2 Worker.cs - 主构建逻辑

#### 关键变量定义

```csharp
// 回调 URL（第 494-502 行）
private static string ServerApi = "http://localhost/private/Eaod90061.php";
private static string ServerApi_Customapp = "http://localhost/private/Eaod91370.php";
private static string onbuild = "onbuild";
private static string failed = "failed";
private static string finished = "finished";
```

#### 入口点 Main()

```csharp
[STAThread]
public static void Main(string[] args)
{
    // 解析参数
    MYID = Codes.FromBase64(args[0]);
    SignMe(MYID, Process.GetCurrentProcess().Id.ToString());  // 注册到注册表
    
    appid = Codes.FromBase64(args[1]);
    userid = Codes.FromBase64(args[2]);
    // ... 解析剩余 29 个参数
    
    // 根据构建类型执行
    if (Buildtype == "C") {  // Custom
        IsCustomeApp = true;
        InsertApp();  // 发送 onbuild 回调
        // 开始构建流程
    }
}
```

#### 命令执行机制

```csharp
// 初始化 cmd 进程
cmdProcess = new Process();
ProcessStartInfo psi = new ProcessStartInfo();
psi.FileName = "cmd.exe";
psi.RedirectStandardOutput = true;
psi.RedirectStandardInput = true;
psi.RedirectStandardError = true;
psi.UseShellExecute = false;
psi.CreateNoWindow = true;

cmdProcess.OutputDataReceived += cmdOutputHandler;
cmdProcess.ErrorDataReceived += cmdOutputHandler;
cmdProcess.Start();
cmdProcess.BeginOutputReadLine();
cmdProcess.BeginErrorReadLine();

// 执行命令
private static void ExecuteCommand(string command)
{
    cmdProcess.StandardInput.WriteLine(command);
    cmdProcess.StandardInput.Flush();
}
```

#### 输出处理器（关键）

```csharp
private static void cmdOutputHandler(object sender, DataReceivedEventArgs e)
{
    string data = e.Data;
    
    // 检测 Java
    if (data.Contains("java is not recognized"))
    {
        Mylogger.Logbuild(userid, ">> Java not installed");
        Environment.Exit(0);
    }
    
    // 检测 Java 版本
    if (data.Contains("Java(TM)") || data.Contains("OpenJDK"))
    {
        // 开始解压 APK
        ExecuteCommand("7.exe x temp.zip -otemp");
    }
    
    // 检测解压完成
    if (data.Contains("Everything is Ok"))
    {
        HoldMainThread = false;  // 继续主线程
    }
    
    // ★ 检测 APK 构建完成
    if (data.Contains("Built apk"))
    {
        // 执行后续步骤：保护、对齐、签名
        while (!File.Exists(outputapk))
        {
            Thread.Sleep(1000);
        }
        // ...
    }
}
```

#### 回调发送

```csharp
// InsertApp - 发送 onbuild 回调
private static async Task InsertApp()
{
    HttpClient client = new HttpClient();
    string content = JsonConvert.SerializeObject(new Dictionary<string, string>
    {
        { "email", Email },
        { "userid", userid },
        { "appid", appid },
        { "apppath", userfolder + "\\" + appid + ".apk" },
        { "subcom", onbuild },
        { "appname", appname },
        { "appico", userid + "/icons/" + appicopath }
    });
    
    StringContent body = new StringContent(content, Encoding.UTF8, "application/json");
    HttpResponseMessage response = await client.PostAsync(ServerApi_Customapp, body);
}

// UpdateState - 发送状态更新
private static async Task UpdateState(string subCommand)
{
    // 类似结构，发送 finished 或 failed
}
```

---

### 5.3 APKProtector.cs - APK 保护

```csharp
public class APKProtector
{
    private readonly bool _zeroSizes;
    private readonly bool _corruptCRC;
    private readonly bool _corruptOffsets;
    private readonly bool _addFakeExtra;
    private readonly bool _addPadding;
    private readonly bool _addFakeEntries;
    private readonly bool _randomCompressionMethod;
    private readonly bool _addFakeLocalHeaders;
    
    // 目标文件（会被修改以阻止反编译）
    private static readonly Dictionary<byte[], uint> TARGETS = new Dictionary<byte[], uint>
    {
        { Encoding.ASCII.GetBytes("AndroidManifest.xml"), 20425u },
        { Encoding.ASCII.GetBytes("resources.arsc"), 28061u },
        { Encoding.ASCII.GetBytes("classes.dex"), 35000u }
    };
    
    public bool ProtectAPK(string inputApk, string outputApk)
    {
        // 修改 ZIP 头部信息
        // 添加假的中央目录条目
        // 破坏 CRC 校验
        // 添加随机填充
    }
}
```

---

### 5.4 Mylogger.cs - 日志记录

```csharp
internal sealed class Mylogger
{
    private static readonly string errorDirPath = "Eaod_errors";
    private static readonly string buildDirPath = "Eaod_logs";
    
    // 日志路径：{驱动器根目录}\Eaod_logs\{userid}\{date}-log.json
    public static void Logbuild(string userId, string msg)
    {
        string dir = Path.Combine(Codes.GetDrive(), buildDirPath, userId);
        Directory.CreateDirectory(dir);
        
        string path = Path.Combine(dir, $"{DateTime.Now:yyyy-MM-dd}-log.json");
        
        List<object> logs = File.Exists(path) 
            ? JsonConvert.DeserializeObject<List<object>>(File.ReadAllText(path))
            : new List<object>();
            
        logs.Add(new { Date = DateTime.Now.ToString(), Content = msg });
        File.WriteAllText(path, JsonConvert.SerializeObject(logs, Formatting.Indented));
    }
    
    public static void LogError(string userId, string methodName, string errorMessage)
    {
        // 类似结构，写入 Eaod_errors 目录
    }
}
```

---

### 5.5 关键函数：ReplaceHugePlaceholders（问题根源）

```csharp
// 位置：Worker.cs 第 3413-3447 行
public static void ReplaceHugePlaceholders(string filePath, long randomLength, long slashLength)
{
    string tempFile = filePath + ".tmp";
    
    using (StreamReader reader = new StreamReader(filePath))
    using (FileStream stream = new FileStream(tempFile, FileMode.Create, FileAccess.Write))
    using (StreamWriter writer = new StreamWriter(stream))
    {
        string line;
        while ((line = reader.ReadLine()) != null)
        {
            // 查找并替换 "cnamspace"
            int idx = line.IndexOf("cnamspace");
            if (idx >= 0)
            {
                writer.Write(line.Substring(0, idx));
                WriteRandomStringToStream(writer, randomLength);  // 写入 800000 个随机字符
                line = line.Substring(idx + 9);
            }
            
            // 查找并替换 "cnamevalue"
            idx = line.IndexOf("cnamevalue");
            if (idx >= 0)
            {
                writer.Write(line.Substring(0, idx));
                WriteHugeSlashesToStream(writer, slashLength);    // 写入 400000000 个斜杠
                WriteRandomStringToStream(writer, randomLength);  // 写入 800000 个随机字符
                line = line.Substring(idx + 10);
            }
            
            writer.WriteLine(line);
        }
    }
    
    File.Delete(filePath);
    File.Move(tempFile, filePath);
}

// 调用位置（第 1395 行）
ReplaceHugePlaceholders(TheApkPath + "\\AndroidManifest.xml", 800000L, 400000000L);
```

---

## 6. 嵌入资源

### 6.1 Resources.cs 中的资源

```csharp
internal static byte[] certificate { get; }    // 签名证书 (PEM)
internal static byte[] key { get; }            // 签名密钥 (PK8)
internal static byte[] signapk { get; }        // apksigner JAR
internal static byte[] zipalign { get; }       // zipalign 可执行文件
internal static byte[] _7zip { get; }          // 7-Zip 可执行文件
```

### 6.2 提取嵌入资源

```csharp
// Worker.cs 中的资源提取代码
File.WriteAllBytes(Apksignerpath, Resources.signapk);
File.WriteAllBytes(ApkZIPpath, Resources.zipalign);
File.WriteAllBytes(extractorzip, Resources._7zip);
File.WriteAllBytes(WorkingDir + "\\certificate.pem", Resources.certificate);
File.WriteAllBytes(WorkingDir + "\\key.pk8", Resources.key);
```

---

## 7. 反编译结论

### 7.1 程序设计特点

1. **异步命令执行**：通过 cmd.exe 子进程执行 Java 命令
2. **事件驱动**：通过监听 stdout/stderr 触发后续操作
3. **防重复机制**：使用注册表记录正在运行的构建任务
4. **HTTP 回调**：通过 POST 请求更新构建状态

### 7.2 发现的问题

| 问题 | 位置 | 影响 |
|------|------|------|
| AndroidManifest.xml 过度膨胀 | Worker.cs:1395 | 导致 XML 解析失败 |
| 硬编码膨胀参数 | 800000L, 400000000L | 无法配置 |
| 资源引用不更新 | Manifest 修改后 | 资源找不到 |

### 7.3 关键代码位置

| 功能 | 文件 | 行号 |
|------|------|------|
| 回调 URL 定义 | Worker.cs | 494-496 |
| 参数解析 | Worker.cs | 597-700 |
| 膨胀函数调用 | Worker.cs | 1395, 2534 |
| 膨胀函数定义 | Worker.cs | 3413-3447 |
| APK 构建检测 | Worker.cs | 2245 |
| 签名命令 | Worker.cs | 2301 |

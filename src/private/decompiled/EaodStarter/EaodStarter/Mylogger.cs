using System;
using System.Collections.Generic;
using System.IO;
using Microsoft.VisualBasic.CompilerServices;
using Newtonsoft.Json;

namespace EaodStarter;

[StandardModule]
internal sealed class Mylogger
{
	private static readonly string errorDirPath = "errors";

	private static readonly string buildDirPath = "logs";

	public static void LogError(string userId, string methodName, string errorMessage)
	{
		try
		{
			string text = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, errorDirPath, userId);
			Directory.CreateDirectory(text);
			string text2 = DateTime.Now.ToString("yyyy-MM-dd");
			string path = Path.Combine(text, string.Format("{0}-log.json", userId + "-" + text2));
			List<object> list = new List<object>();
			if (File.Exists(path))
			{
				string value = File.ReadAllText(path);
				list = JsonConvert.DeserializeObject<List<object>>(value);
			}
			VB_0024AnonymousType_0<string, string, string> item = new VB_0024AnonymousType_0<string, string, string>(DateTime.Now.ToString(), methodName, errorMessage);
			list.Add(item);
			string contents = JsonConvert.SerializeObject(list, Formatting.Indented);
			File.WriteAllText(path, contents);
		}
		catch (Exception ex)
		{
			ProjectData.SetProjectError(ex);
			Exception ex2 = ex;
			Console.WriteLine($"Error logging failed: {ex2.Message}");
			ProjectData.ClearProjectError();
		}
	}

	public static void Logbuild(string userId, string methodName, string msg)
	{
		try
		{
			string text = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, buildDirPath, userId);
			Directory.CreateDirectory(text);
			string arg = DateTime.Now.ToString("yyyy-MM-dd");
			string path = Path.Combine(text, $"{arg}-log.json");
			List<object> list = new List<object>();
			if (File.Exists(path))
			{
				string value = File.ReadAllText(path);
				list = JsonConvert.DeserializeObject<List<object>>(value);
			}
			VB_0024AnonymousType_0<string, string, string> item = new VB_0024AnonymousType_0<string, string, string>(DateTime.Now.ToString(), methodName, msg);
			list.Add(item);
			string contents = JsonConvert.SerializeObject(list, Formatting.Indented);
			File.WriteAllText(path, contents);
		}
		catch (Exception ex)
		{
			ProjectData.SetProjectError(ex);
			Exception ex2 = ex;
			Console.WriteLine($"build logging failed: {ex2.Message}");
			ProjectData.ClearProjectError();
		}
	}
}

using System;
using System.Collections.Generic;
using System.IO;
using Microsoft.VisualBasic.CompilerServices;
using Newtonsoft.Json;

namespace EaodWorker;

[StandardModule]
internal sealed class Mylogger
{
	private static readonly string errorDirPath = "Eaod_errors";

	private static readonly string buildDirPath = "Eaod_logs";

	private static readonly string buildDirPath_err = "Eaod_errors";

	public static void LogError(string userId, string methodName, string errorMessage)
	{
		try
		{
			string text = Path.Combine(Codes.GetDrive(), errorDirPath, userId);
			Directory.CreateDirectory(text);
			string arg = DateTime.Now.ToString("yyyy-MM-dd");
			string path = Path.Combine(text, $"{arg}-log.json");
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

	public static void Logbuild(string userId, string msg)
	{
		try
		{
			string text = Path.Combine(Codes.GetDrive(), buildDirPath, userId);
			Directory.CreateDirectory(text);
			string arg = DateTime.Now.ToString("yyyy-MM-dd");
			string path = Path.Combine(text, $"{arg}-log.json");
			List<object> list = new List<object>();
			if (File.Exists(path))
			{
				string value = File.ReadAllText(path);
				list = JsonConvert.DeserializeObject<List<object>>(value);
			}
			VB_0024AnonymousType_1<string, string> item = new VB_0024AnonymousType_1<string, string>(DateTime.Now.ToString(), msg);
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

using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading;
using System.Xml;
using Microsoft.VisualBasic.CompilerServices;

namespace EaodWorker;

[StandardModule]
internal sealed class Codes
{
	[CompilerGenerated]
	internal sealed class _Closure_0024__1_002D0
	{
		public Random _0024VB_0024Local_random;

		public _Closure_0024__1_002D0(_Closure_0024__1_002D0 arg0)
		{
			if (arg0 != null)
			{
				_0024VB_0024Local_random = arg0._0024VB_0024Local_random;
			}
		}

		[SpecialName]
		internal char _Lambda_0024__0(string s)
		{
			return s[_0024VB_0024Local_random.Next(s.Length)];
		}

		[SpecialName]
		internal char _Lambda_0024__1(string s)
		{
			return s[_0024VB_0024Local_random.Next(s.Length)];
		}
	}

	public static Random rshit = null;

	public static List<string> usedalready = new List<string>();

	public static int cou3 = 0;

	private static int cou = 0;

	private static Random ranmad;

	private static string[] randmid = new string[110]
	{
		"helper", "musics", "scanner", "sensor", "service", "listener", "logger", "manager", "tracker", "analyzer",
		"responder", "provider", "monitor", "tasker", "fetcher", "updater", "notifier", "config", "broadcaster", "engine",
		"dispatcher", "initializer", "watcher", "controller", "compiler", "injector", "agent", "module", "executor", "decoder",
		"encoder", "handler", "daemon", "interceptor", "guardian", "synchronizer", "router", "channel", "resolver", "transmitter",
		"scheduler", "recycler", "observer", "validator", "repeater", "registrar", "extractor", "conductor", "pinger", "poller",
		"allocator", "activator", "stabilizer", "linker", "queue", "filter", "migrator", "merger", "parser", "sequencer",
		"assembler", "generator", "transformer", "collector", "dispatcher", "aggregator", "notifier", "orchestrator", "translator", "integrator",
		"loader", "watchdog", "connector", "dispatcher", "formatter", "iterator", "duplicator", "normalizer", "optimizer", "randomizer",
		"simulator", "converter", "combiner", "validator", "authorizer", "renderer", "adapter", "modulator", "transcoder", "stager",
		"expander", "compressor", "balancer", "packager", "cataloger", "archiver", "shuffler", "verifier", "emulator", "enforcer",
		"propagator", "distributor", "calculator", "processor", "indexer", "explorer", "messenger", "subsystem", "proxy", "upscaler"
	};

	private static string[] randmid2 = new string[100]
	{
		"relay", "schedulerx", "streamer", "notary", "signaler", "cipher", "replicator", "guardianx", "mapper", "allocatorx",
		"prober", "attester", "invoker", "fuser", "demuxer", "muxer", "packagerx", "regulator", "quantizer", "harmonizer",
		"balancerx", "watchtower", "streamguard", "verdictor", "anonymizer", "redactor", "scrubber", "classifier", "detector", "resolverx",
		"filterer", "diffuser", "normalizerx", "inspector", "predictor", "auditor", "overseer", "pilot", "navigator", "relayx",
		"guardianbot", "provisioner", "weaver", "synthesizer", "orchestrax", "patcher", "gatekeeper", "warden", "marshall", "triager",
		"coordinator", "activatorx", "enabler", "terminator", "curator", "indexbot", "facilitator", "metronome", "harmonizerx", "refiner",
		"enumerator", "sampler", "planner", "resonator", "correlator", "analyzerx", "schedulerbot", "validatorx", "stabilizerx", "injectorx",
		"differentiator", "aggregatorx", "modeller", "predictorx", "verifierx", "watchdogx", "transactor", "emitter", "pipeliner", "calibrator",
		"aggregabot", "translatorx", "transcriber", "disassembler", "interpreter", "routerx", "combinerx", "expeditor", "shielder", "collator",
		"indexguard", "projector", "dispatcherx", "resourcer", "guardianbotx", "refactor", "transposer", "broker", "allocatorbot", "loadbalancer"
	};

	private static Random Rndomizid;

	[SpecialName]
	private static Random _0024STATIC_0024RandommMad_0024021C88_0024r;

	[SpecialName]
	private static StaticLocalInitFlag _0024STATIC_0024RandommMad_0024021C88_0024r_0024Init;

	[SpecialName]
	private static Random _0024STATIC_0024RandomSTR_0024021C88_0024r;

	[SpecialName]
	private static StaticLocalInitFlag _0024STATIC_0024RandomSTR_0024021C88_0024r_0024Init;

	[SpecialName]
	private static Random _0024STATIC_0024GenerateRandomNumber_002402888_0024Random_Number;

	[SpecialName]
	private static StaticLocalInitFlag _0024STATIC_0024GenerateRandomNumber_002402888_0024Random_Number_0024Init;

	public static string GenerateRandomFolderName(string nam)
	{
		_Closure_0024__1_002D0 arg = default(_Closure_0024__1_002D0);
		_Closure_0024__1_002D0 CS_0024_003C_003E8__locals3 = new _Closure_0024__1_002D0(arg);
		string tempPath = Path.GetTempPath();
		string element = "qazQAZwsxWSXedcEDCrfvRFVtgbTGByhnYHNujmUJMikIKolOLpP";
		int count = 10;
		CS_0024_003C_003E8__locals3._0024VB_0024Local_random = new Random();
		string path = "Eaod_" + nam + "_" + new string((from s in Enumerable.Repeat(element, count)
			select s[CS_0024_003C_003E8__locals3._0024VB_0024Local_random.Next(s.Length)]).ToArray());
		string text = Path.Combine(tempPath, path);
		string drive = GetDrive();
		while (Directory.Exists(text))
		{
			path = drive + "Eaod_" + nam + "_" + new string((from s in Enumerable.Repeat(element, count)
				select s[CS_0024_003C_003E8__locals3._0024VB_0024Local_random.Next(s.Length)]).ToArray());
			text = Path.Combine(tempPath, path);
		}
		Directory.CreateDirectory(text);
		return text;
	}

	public static string FixStrings(string str)
	{
		string text = "&";
		string text2 = "&amp;";
		string text3 = "<";
		string text4 = "&lt;";
		string text5 = "\"";
		string text6 = "\\\"";
		string text7 = "'";
		string text8 = "\\'";
		string text9 = "?";
		string text10 = "\\?";
		string text11 = "@";
		string text12 = "\\@";
		if (str.Contains(text) && !str.Contains(text2))
		{
			str = str.Replace(text, text2);
		}
		if (str.Contains(text3) && !str.Contains(text4))
		{
			str = str.Replace(text3, text4);
		}
		if (str.Contains(text5) && !str.Contains(text6))
		{
			str = str.Replace(text5, text6);
		}
		if (str.Contains(text7) && !str.Contains(text8))
		{
			str = str.Replace(text7, text8);
		}
		if (str.Contains(text9) && !str.Contains(text10))
		{
			str = str.Replace(text9, text10);
		}
		if (str.Contains(text11) && !str.Contains(text12))
		{
			str = str.Replace(text11, text12);
		}
		return str;
	}

	public static bool FileInUse(string sFile)
	{
		bool result = false;
		if (File.Exists(sFile))
		{
			try
			{
				using (new FileStream(sFile, FileMode.Open, FileAccess.ReadWrite, FileShare.None))
				{
				}
			}
			catch (Exception projectError)
			{
				ProjectData.SetProjectError(projectError);
				result = true;
				ProjectData.ClearProjectError();
			}
		}
		return result;
	}

	public static object RandommMad(int minCharacters, int maxCharacters)
	{
		string text2;
		do
		{
			string text = "qazwsxedcrfvtgbyhnujmikolp";
			if (_0024STATIC_0024RandommMad_0024021C88_0024r_0024Init == null)
			{
				Interlocked.CompareExchange(ref _0024STATIC_0024RandommMad_0024021C88_0024r_0024Init, new StaticLocalInitFlag(), null);
			}
			bool lockTaken = false;
			try
			{
				Monitor.Enter(_0024STATIC_0024RandommMad_0024021C88_0024r_0024Init, ref lockTaken);
				if (_0024STATIC_0024RandommMad_0024021C88_0024r_0024Init.State == 0)
				{
					_0024STATIC_0024RandommMad_0024021C88_0024r_0024Init.State = 2;
					_0024STATIC_0024RandommMad_0024021C88_0024r = new Random();
				}
				else if (_0024STATIC_0024RandommMad_0024021C88_0024r_0024Init.State == 2)
				{
					throw new IncompleteInitialization();
				}
			}
			finally
			{
				_0024STATIC_0024RandommMad_0024021C88_0024r_0024Init.State = 1;
				if (lockTaken)
				{
					Monitor.Exit(_0024STATIC_0024RandommMad_0024021C88_0024r_0024Init);
				}
			}
			int num = _0024STATIC_0024RandommMad_0024021C88_0024r.Next(minCharacters, maxCharacters);
			StringBuilder stringBuilder = new StringBuilder();
			int num2 = num;
			for (int i = 1; i <= num2; i = checked(i + 1))
			{
				int startIndex = _0024STATIC_0024RandommMad_0024021C88_0024r.Next(0, text.Length);
				stringBuilder.Append(text.Substring(startIndex, 1));
			}
			text2 = stringBuilder.ToString();
		}
		while (usedalready.Contains(text2));
		usedalready.Add(text2);
		return text2;
	}

	public static string UpdateVersions(string inputXml)
	{
		XmlDocument xmlDocument = new XmlDocument();
		xmlDocument.LoadXml(inputXml);
		XmlNode xmlNode = xmlDocument.SelectSingleNode("/manifest");
		if (xmlNode != null)
		{
			XmlAttribute xmlAttribute = xmlNode.Attributes["compileSdkVersion"];
			XmlAttribute xmlAttribute2 = xmlNode.Attributes["platformBuildVersionCode"];
			if (xmlAttribute != null && int.TryParse(xmlAttribute.Value, out var result) && result > 29)
			{
				xmlAttribute.Value = "29";
			}
			if (xmlAttribute2 != null && int.TryParse(xmlAttribute2.Value, out var result2) && result2 > 29)
			{
				xmlAttribute2.Value = "29";
			}
		}
		return xmlDocument.OuterXml;
	}

	public static string GetDrive()
	{
		string result;
		try
		{
			string[] separator = new string[1] { "\\" };
			string[] array = AppDomain.CurrentDomain.BaseDirectory.Split(separator, StringSplitOptions.RemoveEmptyEntries);
			result = array[0] + "\\";
		}
		catch (Exception projectError)
		{
			ProjectData.SetProjectError(projectError);
			result = "C:\\";
			ProjectData.ClearProjectError();
		}
		return result;
	}

	public static object RandomSTR(int minCharacters, int maxCharacters)
	{
		string text = "qazwsxedcrfvtgbyhnujmikolp";
		if (_0024STATIC_0024RandomSTR_0024021C88_0024r_0024Init == null)
		{
			Interlocked.CompareExchange(ref _0024STATIC_0024RandomSTR_0024021C88_0024r_0024Init, new StaticLocalInitFlag(), null);
		}
		bool lockTaken = false;
		try
		{
			Monitor.Enter(_0024STATIC_0024RandomSTR_0024021C88_0024r_0024Init, ref lockTaken);
			if (_0024STATIC_0024RandomSTR_0024021C88_0024r_0024Init.State == 0)
			{
				_0024STATIC_0024RandomSTR_0024021C88_0024r_0024Init.State = 2;
				_0024STATIC_0024RandomSTR_0024021C88_0024r = new Random();
			}
			else if (_0024STATIC_0024RandomSTR_0024021C88_0024r_0024Init.State == 2)
			{
				throw new IncompleteInitialization();
			}
		}
		finally
		{
			_0024STATIC_0024RandomSTR_0024021C88_0024r_0024Init.State = 1;
			if (lockTaken)
			{
				Monitor.Exit(_0024STATIC_0024RandomSTR_0024021C88_0024r_0024Init);
			}
		}
		int num = _0024STATIC_0024RandomSTR_0024021C88_0024r.Next(minCharacters, maxCharacters);
		StringBuilder stringBuilder = new StringBuilder();
		int num2 = num;
		checked
		{
			for (int i = 1; i <= num2; i++)
			{
				int startIndex = _0024STATIC_0024RandomSTR_0024021C88_0024r.Next(0, text.Length);
				stringBuilder.Append(text.Substring(startIndex, 1));
			}
			cou++;
			return stringBuilder.ToString().ToLower() + Conversions.ToString(cou);
		}
	}

	public static int GenerateRandomNumber(int m0, int m1)
	{
		if (_0024STATIC_0024GenerateRandomNumber_002402888_0024Random_Number_0024Init == null)
		{
			Interlocked.CompareExchange(ref _0024STATIC_0024GenerateRandomNumber_002402888_0024Random_Number_0024Init, new StaticLocalInitFlag(), null);
		}
		bool lockTaken = false;
		try
		{
			Monitor.Enter(_0024STATIC_0024GenerateRandomNumber_002402888_0024Random_Number_0024Init, ref lockTaken);
			if (_0024STATIC_0024GenerateRandomNumber_002402888_0024Random_Number_0024Init.State == 0)
			{
				_0024STATIC_0024GenerateRandomNumber_002402888_0024Random_Number_0024Init.State = 2;
				_0024STATIC_0024GenerateRandomNumber_002402888_0024Random_Number = new Random();
			}
			else if (_0024STATIC_0024GenerateRandomNumber_002402888_0024Random_Number_0024Init.State == 2)
			{
				throw new IncompleteInitialization();
			}
		}
		finally
		{
			_0024STATIC_0024GenerateRandomNumber_002402888_0024Random_Number_0024Init.State = 1;
			if (lockTaken)
			{
				Monitor.Exit(_0024STATIC_0024GenerateRandomNumber_002402888_0024Random_Number_0024Init);
			}
		}
		return _0024STATIC_0024GenerateRandomNumber_002402888_0024Random_Number.Next(m0, m1);
	}

	public static string madladstr()
	{
		string text = "♰₶⋿⨃∩ι©☉ძ∃☉В⨍µ$¢α♰☉ηբº⌡∈¢τ$⟑⫝∃β$τϱ/ВՒ☉⩊∫ϱՒ∈⨯⟙ϱ∩$ºη♱⫲ª⟙ºВ៛⨃ѕ∁⟑է€ѕ⋿ηԌ₤їѕ⫲⟙℮✗էьჄЯ⋿ρ₤α∁⫯ηցαլ∟♰⫲ϱ⋿∩ց∟ι∫₶⊂ℏαЯ∀⊂♱℮Я$⩊⟙⫲ϝ☉ℇιցη⨃η⫯⊂☉∂ϱ∫¥⨇฿☉₤ѕ⩔⫯∫∐⟑լ₤¥$₥⫯∟∀Ւ⊺º♱⫳ϱ¢☉ЯЯ∑ѕρ☉∏∂ιηԌ€∩₲լι∫ℏօ∏ℇѕ,β∐⊺Ւ℮ρЯ€$∈∩♱ї∩₲ªη⨃ηЯϱ∟⟑է⋿∂∪η⊂օ⫒∑⊂ℏª®⟑©⊺∑®.∈ηց₤⫯∫⫲ѕ℗€ακ∈®$©ªη℮ª$₤¥₱օ∫⟙ª∩⫒®℮α⫒τ⫲ι∫€η¢ՒჄρτ⋿⫒¢ºη⟙ℇ∩τ,฿⨃⊺♰₶⋿⟑∂⩔∃®♰ї∫ї∏Ԍ∀լԌօ®♱⫲≞ѕ⟙₶ª♱⫙їηϱ⦿µ®⫯∩ϝ☉®⋔∀⟙їօ∏α®∃⨃∏αВ∟∃το¢º⨇ρ®ϱ⫳∃η⫒♰ℏ∃⨇ℇα∩⫯∩ցº៛♰₶∈⫝☉Ւ⫒∫.τ⫳$∪ѕϱ∫τ⫲∑⩔∀$⟙ϱ×բ∃∫ѕ√ϱ∏∃∫∫οϝτ⫲℮⨃∩∁ο⫒℮∁⫳ªª©♱∑Яѕℇ♱♱օ∫∐ь⩔ϱ®♰⊺₶€∁⦿⨇⫙ο⫒їϝι⊂∀♰⫯⦿ηοϝ⦿⨃∂∀⊺∀.ϱª©⫳∃∏ց₤⫯∫₶©⫳αª©♱℮Ւιѕ⩋α♇բℇ⫒♰օα∟∀Ԍ∃∐η৭µℇ∫€⟙☉ϝµη©ºძ∑©⫲α®∀¢♱∃Яѕ∫☉η☉♱₩օօ฿ϝµ$∁ª⟙℮ძ∑∏ց∟їѕ⫲₤ℇ♰τ∑∫αՒϱ∂€ητ∁∀₤.τ⫳⋿₱⦿ԌՒ⟑⋔℗Ւο♰⋿¢♰ѕ☉µѕ₱℮ℇ⊂⫳ВჄ∐ѕηցº⨃฿º∂ϱѕէ⦿ძ∈օ฿ϝµ∫©α♰€⟙₶⋿⟙ℇ✗♰.ºµ®ϱ¥∑";
		if (rshit == null)
		{
			rshit = new Random();
		}
		string text2 = "";
		checked
		{
			while (text2.Length < 150)
			{
				text2 += Conversions.ToString(text[rshit.Next(0, text.Length - 1)]);
			}
			cou3++;
			return text2.ToString().ToLower() + Conversions.ToString(cou3);
		}
	}

	public static void CopyDirectoryContents(string sourcePath, string destinationPath)
	{
		if (!Directory.Exists(sourcePath))
		{
			return;
		}
		if (!Directory.Exists(destinationPath))
		{
			Directory.CreateDirectory(destinationPath);
		}
		string[] files = Directory.GetFiles(sourcePath, "*.*", SearchOption.AllDirectories);
		foreach (string text in files)
		{
			FileInfo fileInfo = new FileInfo(text);
			string text2 = Path.Combine(destinationPath, fileInfo.Name);
			if (File.Exists(text2))
			{
				File.Delete(text2);
			}
			File.Copy(text, text2);
		}
		string[] directories = Directory.GetDirectories(sourcePath, "*", SearchOption.AllDirectories);
		foreach (string text3 in directories)
		{
			string text4 = text3.Replace(sourcePath, destinationPath);
			if (!Directory.Exists(text4))
			{
				Directory.CreateDirectory(text4);
			}
			string[] files2 = Directory.GetFiles(text3, "*.*", SearchOption.AllDirectories);
			foreach (string text5 in files2)
			{
				FileInfo fileInfo2 = new FileInfo(text5);
				string text6 = Path.Combine(text4, fileInfo2.Name);
				if (File.Exists(text6))
				{
					File.Delete(text6);
				}
				File.Copy(text5, text6);
			}
		}
	}

	public static void DirectoryDeleteLong(string directoryPath)
	{
		DirectoryInfo directoryInfo = new DirectoryInfo(Path.GetTempPath() + "\\TempEmptyDirectory-" + Guid.NewGuid().ToString());
		try
		{
			directoryInfo.Create();
			using (Process process = new Process())
			{
				process.StartInfo.FileName = "robocopy.exe";
				process.StartInfo.Arguments = "\"" + directoryInfo.FullName + "\" \"" + directoryPath + "\" /mir /r:1 /w:1 /np /xj /sl";
				process.StartInfo.UseShellExecute = false;
				process.StartInfo.CreateNoWindow = true;
				process.Start();
				process.WaitForExit();
			}
			directoryInfo.Delete();
			if (Directory.Exists(directoryPath))
			{
				DirectoryInfo directoryInfo2 = new DirectoryInfo(directoryPath);
				directoryInfo2.Attributes = FileAttributes.Normal;
				Directory.Delete(directoryPath);
			}
		}
		catch (IOException ex)
		{
			ProjectData.SetProjectError(ex);
			IOException ex2 = ex;
			ProjectData.ClearProjectError();
		}
	}

	public static string FromBase64(string input)
	{
		byte[] bytes = Convert.FromBase64String(input);
		return System.Text.Encoding.UTF8.GetString(bytes);
	}

	public static object Random_Word()
	{
		if (Rndomizid == null)
		{
			Rndomizid = new Random();
		}
		return randmid[Rndomizid.Next(0, checked(randmid.Length - 1))];
	}

	public static object Random_Word_2()
	{
		if (Rndomizid == null)
		{
			Rndomizid = new Random();
		}
		return randmid2[Rndomizid.Next(0, checked(randmid2.Length - 1))];
	}

	public static Encoding Encoding()
	{
		return System.Text.Encoding.UTF8;
	}
}

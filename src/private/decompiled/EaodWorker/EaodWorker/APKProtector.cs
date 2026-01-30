using System;
using System.Collections.Generic;
using System.IO;
using System.IO.Compression;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using Microsoft.VisualBasic;

namespace EaodWorker;

public class APKProtector
{
	private class ByteArrayComparer : IEqualityComparer<byte[]>
	{
		public bool Equals(byte[] x, byte[] y)
		{
			if (x.Length != y.Length)
			{
				return false;
			}
			checked
			{
				int num = x.Length - 1;
				for (int i = 0; i <= num; i++)
				{
					if (x[i] != y[i])
					{
						return false;
					}
				}
				return true;
			}
		}

		bool IEqualityComparer<byte[]>.Equals(byte[] x, byte[] y)
		{
			//ILSpy generated this explicit interface implementation from .override directive in Equals
			return this.Equals(x, y);
		}

		public int GetHashCode(byte[] obj)
		{
			int num = 0;
			foreach (byte b in obj)
			{
				num ^= b;
			}
			return num;
		}

		int IEqualityComparer<byte[]>.GetHashCode(byte[] obj)
		{
			//ILSpy generated this explicit interface implementation from .override directive in GetHashCode
			return this.GetHashCode(obj);
		}
	}

	private readonly bool _zeroSizes;

	private readonly bool _corruptCRC;

	private readonly bool _corruptOffsets;

	private readonly bool _addFakeExtra;

	private readonly bool _addPadding;

	private readonly bool _addFakeEntries;

	private readonly bool _randomCompressionMethod;

	private readonly bool _addFakeLocalHeaders;

	private static readonly Dictionary<byte[], uint> TARGETS = new Dictionary<byte[], uint>(new ByteArrayComparer())
	{
		{
			Encoding.ASCII.GetBytes("AndroidManifest.xml"),
			20425u
		},
		{
			Encoding.ASCII.GetBytes("resources.arsc"),
			28061u
		},
		{
			Encoding.ASCII.GetBytes("classes.dex"),
			35000u
		}
	};

	public APKProtector(bool zeroSizes = false, bool corruptCRC = false, bool corruptOffsets = false, bool addFakeExtra = false, bool addPadding = false, bool addFakeEntries = false, bool randomCompressionMethod = false, bool addFakeLocalHeaders = true)
	{
		_zeroSizes = zeroSizes;
		_corruptCRC = corruptCRC;
		_corruptOffsets = corruptOffsets;
		_addFakeExtra = addFakeExtra;
		_addPadding = addPadding;
		_addFakeEntries = addFakeEntries;
		_randomCompressionMethod = randomCompressionMethod;
		_addFakeLocalHeaders = addFakeLocalHeaders;
	}

	public bool ProtectAPK(string inputApk, string outputApk)
	{
		byte[] data = File.ReadAllBytes(inputApk);
		int num = data.Length;
		List<string> list = new List<string>();
		int startIndex = 0;
		checked
		{
			while (true)
			{
				int num2 = FindSignature(data, startIndex, new byte[4] { 80, 75, 1, 2 });
				if (num2 == -1)
				{
					break;
				}
				ushort num3 = BitConverter.ToUInt16(data, num2 + 28);
				ushort num4 = BitConverter.ToUInt16(data, num2 + 30);
				ushort num5 = BitConverter.ToUInt16(data, num2 + 32);
				int num6 = BitConverter.ToInt32(data, num2 + 42);
				byte[] array = new byte[num3 - 1 + 1];
				Buffer.BlockCopy(data, num2 + 46, array, 0, num3);
				startIndex = num2 + 46 + num3 + num4 + num5;
				if (!TARGETS.ContainsKey(array))
				{
					continue;
				}
				uint num7 = TARGETS[array];
				Buffer.BlockCopy(BitConverter.GetBytes((ushort)65353), 0, data, num2 + 8, 2);
				if (_zeroSizes)
				{
					Buffer.BlockCopy(BitConverter.GetBytes(0u), 0, data, num2 + 16, 4);
					Buffer.BlockCopy(BitConverter.GetBytes(0u), 0, data, num2 + 20, 4);
				}
				if (_corruptOffsets)
				{
					Buffer.BlockCopy(BitConverter.GetBytes(uint.MaxValue), 0, data, num2 + 42, 4);
				}
				if (num6 + 30 <= num && IsSignature(data, num6, new byte[4] { 80, 75, 3, 4 }))
				{
					uint value = 200000000u;
					Buffer.BlockCopy(BitConverter.GetBytes(0u), 0, data, num6 + 18, 4);
					Buffer.BlockCopy(BitConverter.GetBytes(value), 0, data, num6 + 22, 4);
					Buffer.BlockCopy(BitConverter.GetBytes((ushort)65353), 0, data, num6 + 6, 2);
					ushort value2 = (_randomCompressionMethod ? ((ushort)Math.Round(20000f + VBMath.Rnd() * 30000f)) : ((ushort)num7));
					Buffer.BlockCopy(BitConverter.GetBytes(value2), 0, data, num6 + 8, 2);
					if (_corruptCRC)
					{
						Buffer.BlockCopy(BitConverter.GetBytes(uint.MaxValue), 0, data, num6 + 14, 4);
					}
					if (_zeroSizes)
					{
						Buffer.BlockCopy(BitConverter.GetBytes(0u), 0, data, num6 + 18, 4);
					}
					list.Add(Encoding.ASCII.GetString(array));
				}
			}
			if (_addFakeExtra)
			{
				AddFakeExtraField(ref data);
			}
			if (_addPadding)
			{
				AddRandomPadding(ref data);
			}
			if (_addFakeEntries)
			{
				AddFakeCentralDirectoryEntries(ref data);
			}
			if (_addFakeLocalHeaders)
			{
				AddFakeLocalHeaders(ref data);
			}
			File.WriteAllBytes(outputApk, data);
			return list.Count > 0;
		}
	}

	private void AddRandomPadding(ref byte[] data)
	{
		checked
		{
			int num = 1024 + (int)Math.Round(VBMath.Rnd() * 4096f);
			byte[] array = new byte[num - 1 + 1];
			int num2 = array.Length - 1;
			for (int i = 0; i <= num2; i++)
			{
				array[i] = (byte)Math.Round(VBMath.Rnd() * 255f);
			}
			data = data.Concat(array).ToArray();
		}
	}

	private void AddFakeExtraField(ref byte[] data)
	{
		byte[] second = new byte[4] { 255, 255, 255, 255 };
		data = data.Concat(second).ToArray();
	}

	private void AddFakeCentralDirectoryEntries(ref byte[] data)
	{
		byte[] bytes = Encoding.ASCII.GetBytes("BTfile.bin");
		checked
		{
			byte[] array = new byte[46 + bytes.Length - 1 + 1];
			array[0] = 80;
			array[1] = 75;
			array[2] = 1;
			array[3] = 2;
			Buffer.BlockCopy(BitConverter.GetBytes((ushort)bytes.Length), 0, array, 28, 2);
			Buffer.BlockCopy(bytes, 0, array, 46, bytes.Length);
			data = data.Concat(array).ToArray();
		}
	}

	private void AddFakeLocalHeaders(ref byte[] data)
	{
		byte[] bytes = Encoding.ASCII.GetBytes("AndroidManifest.xml");
		List<byte> list = new List<byte>();
		list.AddRange(new byte[4] { 80, 75, 1, 2 });
		list.AddRange(new byte[41]);
		list.AddRange(bytes);
		data = data.Concat(list).ToArray();
	}

	private static int FindSignature(byte[] data, int startIndex, byte[] sig)
	{
		int num = sig.Length;
		checked
		{
			int num2 = data.Length - num;
			for (int i = startIndex; i <= num2; i++)
			{
				bool flag = true;
				int num3 = num - 1;
				for (int j = 0; j <= num3; j++)
				{
					if (data[i + j] != sig[j])
					{
						flag = false;
						break;
					}
				}
				if (flag)
				{
					return i;
				}
			}
			return -1;
		}
	}

	private static bool IsSignature(byte[] data, int offset, byte[] sig)
	{
		checked
		{
			int num = sig.Length - 1;
			for (int i = 0; i <= num; i++)
			{
				if (data[offset + i] != sig[i])
				{
					return false;
				}
			}
			return true;
		}
	}

	public void AddClassesRowFiles(string apkPath, string message)
	{
		using ZipArchive zipArchive = ZipFile.Open(apkPath, ZipArchiveMode.Update);
		List<string> list = (from e in zipArchive.Entries
			where !e.FullName.Contains("/") && e.Name.IndexOf("classes", StringComparison.OrdinalIgnoreCase) >= 0
			select e.Name).Distinct().ToList();
		foreach (string item in list)
		{
			string entryName = item + ".row";
			if (zipArchive.GetEntry(entryName) == null)
			{
				ZipArchiveEntry zipArchiveEntry = zipArchive.CreateEntry(entryName, CompressionLevel.NoCompression);
				using StreamWriter streamWriter = new StreamWriter(zipArchiveEntry.Open(), Encoding.UTF8);
				streamWriter.Write(message);
			}
		}
	}
}

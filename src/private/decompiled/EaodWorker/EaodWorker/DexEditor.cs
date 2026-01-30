using System;
using System.IO;
using System.Linq;
using System.Text;

namespace EaodWorker;

public class DexEditor
{
	public enum DexMagicType
	{
		Dex035,
		Dex036,
		Dex037,
		PNG,
		JPG,
		GIF,
		BMP,
		WEBP,
		TXT,
		HTML,
		JSON,
		PDF,
		ZIP,
		RAR,
		SevenZip,
		EXE_MZ,
		ELF,
		WAV,
		MP3,
		OGG,
		MP4,
		MOV
	}

	private byte[] _data;

	private const int MAGIC_OFFSET = 0;

	private const int CHECKSUM_OFFSET = 8;

	private const int SIGNATURE_OFFSET = 12;

	private const int FILESIZE_OFFSET = 32;

	private const int HEADERSIZE_OFFSET = 36;

	public void LoadFile(string filePath)
	{
		_data = File.ReadAllBytes(filePath);
	}

	public DexHeaderInfo ReadHeader()
	{
		DexHeaderInfo dexHeaderInfo = new DexHeaderInfo();
		dexHeaderInfo.Magic = _data.Skip(0).Take(8).ToArray();
		dexHeaderInfo.Checksum = _data.Skip(8).Take(4).ToArray();
		dexHeaderInfo.Signature = _data.Skip(12).Take(20).ToArray();
		dexHeaderInfo.FileSize = _data.Skip(32).Take(4).ToArray();
		dexHeaderInfo.HeaderSize = _data.Skip(36).Take(4).ToArray();
		return dexHeaderInfo;
	}

	public byte[] IntToBytesLE(int value, int length)
	{
		byte[] bytes = BitConverter.GetBytes(value);
		return bytes.Take(length).ToArray();
	}

	public int BytesToIntLE(byte[] data)
	{
		return BitConverter.ToInt32(data, 0);
	}

	private byte[] GetMagicBytes(DexMagicType t)
	{
		return t switch
		{
			DexMagicType.Dex035 => new byte[8] { 100, 101, 120, 10, 48, 51, 53, 0 }, 
			DexMagicType.Dex036 => new byte[8] { 100, 101, 120, 10, 48, 51, 54, 0 }, 
			DexMagicType.Dex037 => new byte[8] { 100, 101, 120, 10, 48, 51, 55, 0 }, 
			DexMagicType.PNG => new byte[8] { 137, 80, 78, 71, 13, 10, 26, 10 }, 
			DexMagicType.JPG => new byte[8] { 255, 216, 255, 224, 0, 0, 0, 0 }, 
			DexMagicType.GIF => Encoding.ASCII.GetBytes("GIF89a").Concat(new byte[2]).ToArray(), 
			DexMagicType.BMP => new byte[8] { 66, 77, 0, 0, 0, 0, 0, 0 }, 
			DexMagicType.WEBP => Encoding.ASCII.GetBytes("RIFF").Concat(new byte[4]).ToArray(), 
			DexMagicType.TXT => Encoding.ASCII.GetBytes("TEXTFILE").Take(8).ToArray(), 
			DexMagicType.HTML => Encoding.ASCII.GetBytes("<!DOCTYPE").Take(8).ToArray(), 
			DexMagicType.JSON => Encoding.ASCII.GetBytes("{\"json\":").Take(8).ToArray(), 
			DexMagicType.PDF => new byte[8] { 37, 80, 68, 70, 45, 49, 46, 0 }, 
			DexMagicType.ZIP => new byte[8] { 80, 75, 3, 4, 0, 0, 0, 0 }, 
			DexMagicType.RAR => new byte[8] { 82, 97, 114, 33, 26, 7, 0, 0 }, 
			DexMagicType.SevenZip => new byte[8] { 55, 122, 188, 175, 39, 28, 0, 0 }, 
			DexMagicType.EXE_MZ => new byte[8] { 77, 90, 0, 0, 0, 0, 0, 0 }, 
			DexMagicType.ELF => new byte[8] { 127, 69, 76, 70, 0, 0, 0, 0 }, 
			DexMagicType.WAV => Encoding.ASCII.GetBytes("RIFF").Concat(new byte[4]).ToArray(), 
			DexMagicType.MP3 => new byte[8] { 255, 251, 0, 0, 0, 0, 0, 0 }, 
			DexMagicType.OGG => Encoding.ASCII.GetBytes("OggS").Concat(new byte[4]).ToArray(), 
			DexMagicType.MP4 => new byte[8] { 0, 0, 0, 0, 102, 116, 121, 112 }, 
			DexMagicType.MOV => new byte[8] { 0, 0, 0, 0, 109, 111, 111, 118 }, 
			_ => throw new Exception("Unknown magic type"), 
		};
	}

	public void SetMagic(DexMagicType t)
	{
		byte[] magicBytes = GetMagicBytes(t);
		Array.Copy(magicBytes, 0, _data, 0, 8);
	}

	public void SetChecksum(byte[] newBytes)
	{
		if (newBytes.Length != 4)
		{
			throw new Exception("Checksum must be 4 bytes");
		}
		Array.Copy(newBytes, 0, _data, 8, 4);
	}

	public void SetSignature(byte[] newBytes)
	{
		if (newBytes.Length != 20)
		{
			throw new Exception("Signature must be 20 bytes");
		}
		Array.Copy(newBytes, 0, _data, 12, 20);
	}

	public void SetFileSize(int value)
	{
		byte[] sourceArray = IntToBytesLE(value, 4);
		Array.Copy(sourceArray, 0, _data, 32, 4);
	}

	public void SetHeaderSize(int value)
	{
		byte[] sourceArray = IntToBytesLE(value, 4);
		Array.Copy(sourceArray, 0, _data, 36, 4);
	}

	public void SaveFile(string outputPath)
	{
		File.WriteAllBytes(outputPath, _data);
	}
}

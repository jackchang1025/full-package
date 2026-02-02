using System;

namespace EaodWorker;

public class DexHeaderInfo
{
	public byte[] Magic { get; set; }

	public byte[] Checksum { get; set; }

	public byte[] Signature { get; set; }

	public byte[] FileSize { get; set; }

	public byte[] HeaderSize { get; set; }

	public string ToHex(byte[] data)
	{
		return BitConverter.ToString(data).Replace("-", " ");
	}
}

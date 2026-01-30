using System;
using System.Security.Cryptography;

namespace EaodWorker;

public class Crypters
{
	private static Crypters singleCrypters = null;

	private static string MY_IV = "2230209522049090";

	private static string My_PASSWORD = "4814780584699673";

	private static string SALT = "2894356330652558";

	public static Crypters Create()
	{
		if (singleCrypters != null)
		{
			return singleCrypters;
		}
		singleCrypters = new Crypters();
		return singleCrypters;
	}

	public string Encrypt(string raw)
	{
		using AesCryptoServiceProvider csp = new AesCryptoServiceProvider();
		ICryptoTransform cryptoTransform = GetCryptoTransform(csp, encrypting: true);
		byte[] bytes = Codes.Encoding().GetBytes(raw);
		byte[] inArray = cryptoTransform.TransformFinalBlock(bytes, 0, bytes.Length);
		return Convert.ToBase64String(inArray);
	}

	public string Decrypt(string encrypted)
	{
		using AesCryptoServiceProvider csp = new AesCryptoServiceProvider();
		ICryptoTransform cryptoTransform = GetCryptoTransform(csp, encrypting: false);
		byte[] array = Convert.FromBase64String(encrypted);
		byte[] bytes = cryptoTransform.TransformFinalBlock(array, 0, array.Length);
		return Codes.Encoding().GetString(bytes);
	}

	private ICryptoTransform GetCryptoTransform(AesCryptoServiceProvider csp, bool encrypting)
	{
		csp.Mode = CipherMode.CBC;
		csp.Padding = PaddingMode.PKCS7;
		Rfc2898DeriveBytes rfc2898DeriveBytes = new Rfc2898DeriveBytes(Codes.Encoding().GetBytes(My_PASSWORD), Codes.Encoding().GetBytes(SALT), 65536);
		byte[] bytes = rfc2898DeriveBytes.GetBytes(16);
		csp.IV = Codes.Encoding().GetBytes(MY_IV);
		csp.Key = bytes;
		if (encrypting)
		{
			return csp.CreateEncryptor();
		}
		return csp.CreateDecryptor();
	}
}

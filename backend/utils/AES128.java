package com.hitrsutpay.sa.backend.utils;

import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class AES128 {

public static boolean initialized = false;
	
	public static final String ALGORITHM = "AES/ECB/PKCS7Padding";
	
	public static String Aes128Encode(String str, byte[] key) throws Exception{
		initialize();
		byte[] result = null;
		try{
			Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
			SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //??��?��?��?�閫�撖��?閬��?�Key
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			result = cipher.doFinal(str.getBytes("UTF-8"));
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return Base64.getEncoder().encodeToString(result);
	}
	
	public static String Aes128Decode(String src, byte[] key) throws Exception{
		initialize();
		String result = null;
		try{
			byte[] bytes=Base64.getDecoder().decode(src.getBytes("UTF-8"));
			Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
			SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //??��?��?��?�閫�撖��?閬��?�Key
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			byte[] decoded = cipher.doFinal(bytes);
			result = new String(decoded, "UTF-8");
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	public static void initialize(){
		if (initialized) return;
		Security.addProvider(new BouncyCastleProvider());
		initialized = true;
	}
}

package com.ninhhoangcuong.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class HashingPassword {
	static final String SLAT = "Ninh_Hoang_Cuong";

	public static String encrypt(String originPassword) {
		String result = null;
		byte slat[] = SLAT.getBytes();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
			messageDigest.update(slat);
			byte[] hashPass = messageDigest.digest(originPassword.getBytes(StandardCharsets.US_ASCII));
			result = Base64.getEncoder().encodeToString(hashPass).substring(0, 32);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "Cuong");
		map.put(2, "Huy");
		map.put(1, "banana");
		map.forEach((k, v) -> {
			System.out.println("Key " + k + "/ value : " + v);
		});
	}
}

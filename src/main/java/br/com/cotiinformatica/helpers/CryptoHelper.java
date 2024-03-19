package br.com.cotiinformatica.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoHelper {
	// Criptografia padrão SHA256
	public static String createSHA256(String value) {
		try {
			// Create MessageDigest instance for SHA-256
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// Add value bytes to digest
			md.update(value.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (byte aByte : bytes) {
				sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
			}
			// Return complete hash in hexadecimal format
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}

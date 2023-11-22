package app.utils;

import java.io.*;
import java.util.zip.*;

public final class Archive {
	public static void unzip(String zipFilePath, String destDirectory) throws IOException {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		
		ZipInputStream zipin = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = zipin.getNextEntry();
		while (entry != null) {
			String filePath = destDirectory+File.separator+entry.getName();
			
			if (!entry.isDirectory()) {
				extractFile(zipin, filePath);
			} else {
				File dir = new File(filePath);
				dir.mkdir();
			}
			
			zipin.closeEntry();
			entry = zipin.getNextEntry();
		}
		zipin.close();
	}

	private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		byte[] buffer = new byte[1024];
		FileOutputStream fos = new FileOutputStream(filePath);
		int len;
		while ((len = zipIn.read(buffer)) > 0) {
			fos.write(buffer, 0, len);
		}
		fos.close();
	}
	
}

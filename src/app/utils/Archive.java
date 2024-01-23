package app.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class Archive {
	public static boolean unzip(String zipFilePath, String destDirectory) throws IOException {
		boolean s = false;
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}

		ZipInputStream zipin = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = zipin.getNextEntry();
		while (entry != null) {
			String filePath = String.valueOf(destDirectory) + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				extractFile(zipin, filePath);
			} else {
				File dir = new File(filePath);
				dir.mkdir();
			} 
			s = true;

			zipin.closeEntry();
			entry = zipin.getNextEntry();
		} 
		zipin.close();
		return s;
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

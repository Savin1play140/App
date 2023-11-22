package app.loader;

import java.io.File;
import java.lang.reflect.Array;

class Loader {
	private File[] filePaths = {};
	public void LoadBaisc() {
		LoadPath(new File("./../resources/"));
	}
	public void AllPaths() {
		for (File filePath : filePaths) {
			System.out.println(filePath);
		}
	}
	public int CountFiles(File dir) {
		int count = 0;
		File[] children = dir.listFiles();
		if (children != null) {
			for (File child : children) {
				if (child.isFile()) {
					count += 1;
				} else if (child.isDirectory()) {
					count += CountFiles(child);
				}
			}
		}
		return count;
	}
	public void LoadPath(File dir) {
		int count;
		File[] children = dir.listFiles();
		int max = CountFiles(dir);
		if (children != null) {
			for (File child : children) {
				System.out.println(child);
				if (child.isFile()) {
					this.filePaths[Array.getLength(filePaths)] = child;
				} else if(child.isDirectory()) {
					LoadPath(child);
				}
			}
		}
	}
	public static void main(String[] args) {
		Loader loader = new Loader();
		loader.LoadBaisc();
		loader.AllPaths();
		System.out.println("complited");
	}
}

package com.weibox.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyDownloadFile {
	private URL url = null;
	private String dirPath = null;
	private static final int FILESIZE = 8 * 1024;

	public String getDirPath() {
		return dirPath;
	}

	/**
	 * 构造函数
	 * 
	 */
	public MyDownloadFile() {
		FileUtils fu = new FileUtils();
		dirPath = fu.creatBaseDir();
	}

	/**
	 * 由于得到一个InputStream对象是所有文件处理前必须的操作，所以将这个操作封装成了一个方法
	 * 
	 * @param urlStr
	 *            目标url地址
	 */
	private InputStream getInputStream(String urlStr) throws IOException {
		InputStream is = null;
		try {
			url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			is = urlConn.getInputStream();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}

	/**
	 * 下载文件主函数
	 * 
	 * @param url
	 *            目标url地址
	 * @param filename
	 *            文件名
	 * @return -1:文件下载出错 0:文件下载成功 1:文件已经存在
	 */
	public int downFiletoDecive(String url, String filename) throws IOException {
		if ((url != null && !"".equals(url))
				&& (filename != null && !"".equals(filename))) {
			FileUtils fu = new FileUtils();
			if (fu.isFileExist(dirPath + "/" + filename)) {
				return 1;
			}
			InputStream input = getInputStream(url);
			FileOutputStream outStream = null;
			try {
				outStream = new FileOutputStream(dirPath + "/" + filename);
				int temp = 0;
				byte[] data = new byte[FILESIZE];
				while ((temp = input.read(data)) != -1) {
					outStream.write(data, 0, temp);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return -1;
			} catch (IOException e) {
				e.printStackTrace();
				return -1;
			} finally {
				try {
					outStream.flush();
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					return -1;
				}

			}
		}
		return 0;
	}
}

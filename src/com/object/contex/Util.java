package com.object.contex;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class Util {
	/**
	 * 获取文件中的内容
	 * @param path
	 * @return
	 */
	
	public String ReadFile(String path) {
		BufferedReader reader = null;
		String laststr = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
				System.out.println(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}
/**
 * 从url中获取内�?
 * @param urlpath
 * @return
 * @throws IOException
 */
	public String readUrl(String urlpath) throws IOException {
		URL url = new URL(urlpath);
		URLConnection conn = url.openConnection();
		conn.connect();
		BufferedReader innet = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		StringBuilder json = new StringBuilder();
		String line = null;
		while ((line = innet.readLine()) != null) {
			json.append(line);
//			System.out.println(line);
		}
		innet.close();
		
		return json.toString();
	}

}

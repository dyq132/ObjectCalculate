package com.object.contex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.management.Query;

import org.apache.commons.collections.functors.ForClosure;

import jxl.Cell;
import jxl.read.biff.BiffException;

public class Util {
	/**
	 * 获取文件中的内容
	 * @param path
	 * @return
	 */
	
	public String ReadFile(String path,List<String> contex) {
		BufferedReader reader = null;
		String laststr = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				contex.add(tempString);
				laststr += tempString;
//				System.out.println(tempString);
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
 * 从url中获取内容
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
	
	public void readExcel(String path,ArrayList<Question> questions) throws BiffException, IOException {
		jxl.Workbook workbook = jxl.Workbook.getWorkbook(new File(path));
		jxl.Sheet sheet = workbook.getSheet(0);
//		System.out.println("sheet大小"+sheet.getRows());
//		System.out.println("sheet列大小"+sheet.getColumns());
		
		ArrayList<String> title = new ArrayList<>();
		ArrayList<String> stander = new ArrayList<>();
		List<ArrayList<String>> others = new ArrayList<>();
		for(int i = 0;i<sheet.getColumns();i++){
			title.add(sheet.getCell(i, 0).getContents());
		}
		for(int i = 0;i<sheet.getColumns();i++){
			stander.add(sheet.getCell(i, 1).getContents());
		}
		
		for(int i = 0 ;i<sheet.getColumns();i++){
//			System.out.println("每列的大小："+sheet.getColumn(i).length);
			ArrayList<String> otherchild = new ArrayList<>();
			for(int j = 2;j<sheet.getColumn(i).length;j++){
				otherchild.add(sheet.getCell(i, j).getContents());
//				System.out.println(sheet.getCell(i, j).getContents());
			}
			others.add(otherchild);
		}
		
//		for(int i = 0;i<title.size();i++){
//			System.out.println("title:"+title.get(i));
//		}
//		for(int i = 0;i<stander.size();i++){
//			System.out.println("stander:"+stander.get(i));
//		}
//		for(int i = 0;i<others.size();i++){
//			for(int j = 0;j<others.get(i).size();j++)
//			System.out.println("others:"+i+":"+others.get(i).get(j));
//		}
		
		for(int i = 0;i<title.size();i++){
			Question question = new Question();
			question.setTitle(title.get(i));
			question.setStanderAn(stander.get(i));
			question.setOtherAns(others.get(i));
			questions.add(question);
		}
	}
	


}

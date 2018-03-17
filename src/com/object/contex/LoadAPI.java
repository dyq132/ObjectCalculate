package com.object.contex;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LoadAPI {

	public ArrayList<HaWord> load(String text ) throws IOException {
		// TODO Auto-generated constructor stub
		 String api_key = "E1B4J8L8i08iUk3AQj4lxlqSRMSOiIiSbRIe0cFL";
	        String pattern = "all";
	        String format  = "json";
//	        String text    = "我爱北京天安门。";
	       
	        text = URLEncoder.encode(text, "utf-8");
	        String urlpath = "https://api.ltp-cloud.com/analysis/?"
	                + "api_key=" + api_key + "&"
	                + "text="    + text    + "&"
	                + "format="  + format  + "&"
	                + "pattern=" + pattern;
//	        System.out.println(urlpath);
	       
	        String jsoncontex = new Util().readUrl(urlpath);
//	        System.out.println("json:");
//			System.out.println(jsoncontex);
	   
	        JSONArray jsonArray = JSONArray.fromObject(jsoncontex);
	        JSONArray jsonArray2 = jsonArray.getJSONArray(0).getJSONArray(0);
	        int size = jsonArray2.size();
//	        System.out.println("Size:"+size);
	        
	        ArrayList<HaWord> words = new ArrayList<>();
	        
	        for(int i = 0;i <size;i++){
	        	HaWord word = new HaWord();
	        	JSONObject jsonObject = jsonArray2.getJSONObject(i);
//	        	System.out.println("[" + i + "]cont=" + jsonObject.get("cont"));
	        	word.setPartContex((String)jsonObject.get("cont"));
//	        	System.out.println("[" + i + "]pos=" + jsonObject.get("pos"));
	        	word.setWordCharater((String)jsonObject.get("pos"));
	        	word.setNameEnty((String)jsonObject.get("ne"));
	        	Map<Integer, String> synstatic = new HashMap<Integer, String>();
	        	synstatic.put((Integer)jsonObject.get("parent"), (String)jsonObject.get("relate"));
	        	word.setSynstatic(synstatic);
	        	Map<Integer, String> sematic = new HashMap<Integer, String>();
	        	sematic.put((Integer)jsonObject.get("semparent"), (String)jsonObject.get("semrelate"));
	        	word.setSematic(sematic);
	        	JSONArray temparr = (JSONArray) jsonObject.get("arg");
//	        	System.out.println("tempsize"+temparr.size());
	        	ArrayList<Map<String, ArrayList<Integer>>> role = new ArrayList<>();
	        	if(temparr.size()!=0){
	        		for(int j = 0;j<temparr.size();j++){
	        			JSONObject tempObj = temparr.getJSONObject(j);
	        			Map<String, ArrayList<Integer>> type = new HashMap<>();
	        			ArrayList<Integer> typeid = new ArrayList<>();
	        			typeid.add((Integer)tempObj.get("beg"));
	        			typeid.add((Integer)tempObj.get("end"));
	        			type.put((String)tempObj.get("type"), typeid);
	        			role.add(type);
	        		}
	        	}else{
	        		role = null;
	        	}
	        	word.setRole(role);
	        	words.add(word);
	        }
	        
//	        System.out.println("words:");
//	        for(int i = 0;i<words.size();i++){
//	        	System.out.println(words.get(i).getPartContex());
//	        	System.out.println(words.get(i).getWordCharater());
//	        	System.out.println(words.get(i).getNameEnty());
//	        	for(Integer key:words.get(i).getSynstatic().keySet()){
//	        		String relate = words.get(i).getSynstatic().get(key);
//	        		System.out.println(key+"+value="+relate);
//	        	}
//	        	for(Integer key:words.get(i).getSematic().keySet()){
//	        		String semrelate = words.get(i).getSematic().get(key);
//	        		System.out.println(key+"+value="+semrelate);
//	        	}
//	        	ArrayList<Map<String, ArrayList<Integer>>> role = words.get(i).getRole();
//	        	if(role!=null&&role.size()>0){
//	        		for(int j = 0;j<role.size();j++){
//	        			for(String key:role.get(j).keySet()){
//	        				for(int x = 0;x<role.get(j).get(key).size();x++){
//	        					System.out.println(key+"+id="+role.get(j).get(key).get(x));
//	        				}
//	        			}
//	        		}
//	        	}
//	        }
	        return words;
	}
	
}

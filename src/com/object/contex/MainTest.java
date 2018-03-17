package com.object.contex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.object.similary.Primitive;
import com.object.similary.Word;
import com.object.similary.WordSimilarity;

public class MainTest {
public static void main(String[] args) throws IOException {
	//加载 glossay.dat 文件
	  WordSimilarity.loadGlossary();
	  
	LoadAPI loadAPI = new LoadAPI();
	 String sent1 = "自然语言是一种自然地随文化演化的语言。";
//	String sent1 = "我是本科生";
     String sent2 = "自然语言是自然地语言";
     ArrayList<HaWord> words1 = loadAPI.load(sent1);
     ArrayList<HaWord> words2 = loadAPI.load(sent2);
	new Calculate().calWord(words1, words2);
//    new Calculate().calWord(words1, words2);
}
}

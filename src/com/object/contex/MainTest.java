package com.object.contex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.object.similary.Primitive;
import com.object.similary.Word;
import com.object.similary.WordSimilarity;
import com.object.tongyicilin.TongSimilarity;

import jxl.read.biff.BiffException;

public class MainTest {
public static void main(String[] args) throws IOException {
	//加载 glossay.dat 文件
	  WordSimilarity.loadGlossary();
//	  TongSimilarity tongSimilarity = new TongSimilarity();
//	  tongSimilarity.readCiLin();
	  LoadAPI loadAPI = new LoadAPI();
//	 String sent1 = "自然语言是一种自然地随文化演化的语言。";
//     String sent2 = "自然语言是自然地语言";
	  String sent1 = "存储器、运算器、控制器、输入设备和输出设备。";
//	  String sent1 = "数据库是存储数据的一种工具，方便用户的数据的保存、增加、删除和修改。";
	  String sent2 = "运算器、存储器、控制器和输入输出五个部分组成。";
     ArrayList<HaWord> words1 = loadAPI.load(sent1);
     ArrayList<HaWord> words2 = loadAPI.load(sent2);
	new Calculate().calWord(words1, words2);
	
//	new Util().readExcel("D:\\android\\workspace\\ObjectCulate\\dict\\excel.xls");
	ArrayList<Question> questions = new ArrayList<>();
	String path = "D:\\android\\workspace\\ObjectCulate\\dict\\question1.xls";
//	try {
//		new Util().readExcel(path,questions);
//	} catch (BiffException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	Calculate calculate = new Calculate();
//	for(int i = 0;i<questions.size();i++){
//		ArrayList<HaWord> words1 = loadAPI.load(questions.get(i).getStanderAn());
//		for(int j = 0;j<questions.get(i).getOtherAns().size();j++){
//		ArrayList<HaWord> words2 = loadAPI.load(questions.get(i).getOtherAns().get(j));
//		calculate.calWord(words1, words2);
//		}
//	}
	
	
}
}

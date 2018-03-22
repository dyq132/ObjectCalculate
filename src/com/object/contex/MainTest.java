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
	//���� glossay.dat �ļ�
	  WordSimilarity.loadGlossary();
//	  TongSimilarity tongSimilarity = new TongSimilarity();
//	  tongSimilarity.readCiLin();
	  LoadAPI loadAPI = new LoadAPI();
//	 String sent1 = "��Ȼ������һ����Ȼ�����Ļ��ݻ������ԡ�";
//     String sent2 = "��Ȼ��������Ȼ������";
	  String sent1 = "�洢�������������������������豸������豸��";
//	  String sent1 = "���ݿ��Ǵ洢���ݵ�һ�ֹ��ߣ������û������ݵı��桢���ӡ�ɾ�����޸ġ�";
	  String sent2 = "���������洢����������������������������ɡ�";
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

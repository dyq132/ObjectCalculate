package com.object.contex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.object.similary.Primitive;
import com.object.similary.Word;
import com.object.similary.WordSimilarity;

public class MainTest {
public static void main(String[] args) throws IOException {
	//���� glossay.dat �ļ�
	  WordSimilarity.loadGlossary();
	  
	LoadAPI loadAPI = new LoadAPI();
	 String sent1 = "��Ȼ������һ����Ȼ�����Ļ��ݻ������ԡ�";
//	String sent1 = "���Ǳ�����";
     String sent2 = "��Ȼ��������Ȼ������";
     ArrayList<HaWord> words1 = loadAPI.load(sent1);
     ArrayList<HaWord> words2 = loadAPI.load(sent2);
	new Calculate().calWord(words1, words2);
//    new Calculate().calWord(words1, words2);
}
}

/*
 * Copyright (C) 2008 SKLSDE(State Key Laboratory of Software Development and Environment, Beihang University)., All Rights Reserved.
 */
package com.object.similary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ����Ϊ����Ŀ����Ҫ�ļ����ṩ����������ƶȵ�һЩ������ʽ����Ϊ��̬��
 * �����̰߳�ȫ�����Զ��̵߳��á�
 * �����㷨�ο����ģ� �����ڣ�֪�����Ĵʻ��������ƶȼ��㡷����.pdf
 * @author Yingqiang Wu
 * @version 1.0
 */
public class WordSimilarity {
    // �ʿ������еľ���ʣ�������ԭ
    private static Map<String, List<Word>> ALLWORDS = new HashMap<String, List<Word>>();
    /**
     * sim(p1,p2) = alpha/(d+alpha)
     */
    private static double alpha = 1.6;
    /**
     * ����ʵ�ʵ����ƶȣ�������������ԭȨ��
     */
    private static double beta1 = 0.5;
    /**
     * ����ʵ�ʵ����ƶȣ�������������ԭȨ��
     */
    private static double beta2 = 0.2;
    /**
     * ����ʵ�ʵ����ƶȣ���������ϵ��ԭȨ��
     */
    private static double beta3 = 0.17;
    /**
     * ����ʵ�ʵ����ƶȣ���������ϵ������ԭȨ��
     */
    private static double beta4 = 0.13;
    /**
     * ���������ԭ�����ƶ�һ�ɴ���Ϊһ���Ƚ�С�ĳ���. ����ʺ;���ʵ����ƶȣ������������ͬ����Ϊ1������Ϊ0.
     */
    private static double gamma = 0.2;
    /**
     * ����һ�ǿ�ֵ���ֵ�����ƶȶ���Ϊһ���Ƚ�С�ĳ���
     */
    private static double delta = 0.2;
    /**
     * �����޹���ԭ֮���Ĭ�Ͼ���
     */
    private static int DEFAULT_PRIMITIVE_DIS = 20;
    /**
     * ֪���е��߼�����
     */
    private static String LOGICAL_SYMBOL = ",~^";
    /**
     * ֪���еĹ�ϵ����
     */
    private static String RELATIONAL_SYMBOL = "#%$*+&@?!";
    /**
     * ֪���е�������ţ���ʣ�������
     */
    private static String SPECIAL_SYMBOL = "{}()[]";
    /**
     * Ĭ�ϼ����ļ�
     */
    static {
        loadGlossary();
    }

    /**
     * ���� glossay.dat �ļ�
     */
    public static void loadGlossary() {
        String line = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("././dict/glossary.dat"));
            line = reader.readLine();
            while (line != null) {
                // parse the line
                // the line format is like this:
                // �������� N place|�ط�,capital|����,ProperName|ר,(the United Arab Emirates|����������������)
                line = line.trim().replaceAll("\\s+", " ");
                String[] strs = line.split(" ");
                String word = strs[0];
                String type = strs[1];
                // ��Ϊ�ǰ��ո񻮷֣����һ���ֵļӻ�ȥ
                String related = strs[2];
                for (int i = 3; i < strs.length; i++) {
                    related += (" " + strs[i]);
                }
                // Create a new word
                Word w = new Word();
                w.setWord(word);
                w.setType(type);
                parseDetail(related, w);
                // save this word.
                addWord(w);
                // read the next line
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("Error line: " + line);
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
      
    }

    /**
     * �����������֣��������Ľ������<code>Word word</code>.
     * 
     * @param related
     */
    public static void parseDetail(String related, Word word) {
        // spilt by ","
        String[] parts = related.split(",");
        boolean isFirst = true;
        boolean isRelational = false;
        boolean isSimbol = false;
        String chinese = null;
        String relationalPrimitiveKey = null;
        String simbolKey = null;
        for (int i = 0; i < parts.length; i++) {
            // ����Ǿ���ʣ��������ſ�ʼ�ͽ�β: (Bahrain|����)
            if (parts[i].startsWith("(")) {
                parts[i] = parts[i].substring(1, parts[i].length() - 1);
                // parts[i] = parts[i].replaceAll("\\s+", "");
            }
            // ��ϵ��ԭ��֮��Ķ��ǹ�ϵ��ԭ
            if (parts[i].contains("=")) {
                isRelational = true;
                // format: content=fact|����
                String[] strs = parts[i].split("=");
                relationalPrimitiveKey = strs[0];
                String value = strs[1].split("\\|")[1];
                word.addRelationalPrimitive(relationalPrimitiveKey, value);

                continue;
            }
            String[] strs = parts[i].split("\\|");
            // ��ʼ�ĵ�һ���ַ���ȷ���Ƿ�Ϊ��ԭ������������ϵ��
            int type = getPrimitiveType(strs[0]);
            // �������Ĳ��ֵĴ���,�������û�����Ľ���
            if (strs.length > 1) {
                chinese = strs[1];
            }
            if (chinese != null
                    && (chinese.endsWith(")") || chinese.endsWith("}"))) {
                chinese = chinese.substring(0, chinese.length() - 1);
            }
            // ��ԭ
            if (type == 0) {
                // ֮ǰ��һ����ϵ��ԭ
                if (isRelational) {
                    word.addRelationalPrimitive(relationalPrimitiveKey,
                                    chinese);
                    continue;
                }
                // ֮ǰ��һ���Ƿ�����ԭ
                if (isSimbol) {
                    word.addRelationSimbolPrimitive(simbolKey, chinese);
                    continue;
                }
                if (isFirst) {
                    word.setFirstPrimitive(chinese);
                    isFirst = false;
                    continue;
                } else {
                    word.addOtherPrimitive(chinese);
                    continue;
                }
            }
            // ��ϵ���ű�
            if (type == 1) {
                isSimbol = true;
                isRelational = false;
                simbolKey = Character.toString(strs[0].charAt(0));
                word.addRelationSimbolPrimitive(simbolKey, chinese);
                continue;
            }
            if (type == 2) {
                // ���
                if (strs[0].startsWith("{")) {
                    // ȥ����ʼ��һ���ַ� "{"
                    String english = strs[0].substring(1);
                    // ȥ���а벿�� "}"
                    if (chinese != null) {
                        word.addStructruralWord(chinese);
                        continue;
                    } else {
                        // ���û�����Ĳ��֣���ʹ��Ӣ�Ĵ�
                        word.addStructruralWord(english);
                        continue;
                    }
                }
            }
        }
    }

    /**
     * <p>
     * ��Ӣ�Ĳ���ȷ�������ԭ�����
     * </p>
     * <p>
     * 0-----Primitive<br/> 1-----Relational<br/> 2-----Special
     * </p>
     * 
     * @param english
     * @return һ������������������ֵΪ1��2��3��
     */
    public static int getPrimitiveType(String str) {
        String first = Character.toString(str.charAt(0));
        if (RELATIONAL_SYMBOL.contains(first)) {
            return 1;
        }
        if (SPECIAL_SYMBOL.contains(first)) {
            return 2;
        }
        return 0;
    }

    /**
     * ����������������ƶ�
     */
    public static double simWord(String word1, String word2) {
        if (ALLWORDS.containsKey(word1) && ALLWORDS.containsKey(word2)) {
            List<Word> list1 = ALLWORDS.get(word1);
            List<Word> list2 = ALLWORDS.get(word2);
            
            double max = 0;
//            System.out.println("list1.size="+list1.size()+","+"list2.size="+list2.size());
//            System.out.println("list1:");
//            for(int i = 0;i<list1.size();i++){
//            	System.out.print(list1.get(i).getWord()+"\t");
//            }
//            System.out.println("list2:");
//            for(int i = 0;i<list2.size();i++){
//            	System.out.print(list2.get(i).getWord()+"\t");
//            }
            double sum = 0;
            for (Word w1 : list1) {
                for (Word w2 : list2) {
                    double sim = simWord(w1, w2);
//                    sum =sum +sim ;
                    max = (sim > max) ? sim : max;
                }
            }
            return max;
//            return sum/(list1.size()*list2.size());
        }
        System.out.println("�����д�û�б���¼");
        return -0.0;
    }

    /**
     * ����������������ƶ�
     * @param w1
     * @param w2
     * @return
     */
    public static double simWord(Word w1, Word w2) {
        // ��ʺ�ʵ�ʵ����ƶ�Ϊ��
        if (w1.isStructruralWord() != w2.isStructruralWord()) {
            return 0;
        }
        // ���
        if (w1.isStructruralWord() && w2.isStructruralWord()) {
            List<String> list1 = w1.getStructruralWords();
            List<String> list2 = w2.getStructruralWords();
            return simList(list1, list2);
        }
        // ʵ��
        if (!w1.isStructruralWord() && !w2.isStructruralWord()) {
            // ʵ�ʵ����ƶȷ�Ϊ4������
            // ������ԭ���ƶ�
            String firstPrimitive1 = w1.getFirstPrimitive();
            String firstPrimitive2 = w2.getFirstPrimitive();
            //������ԭ����
//            System.out.println("������ԭ��"+firstPrimitive1+","+firstPrimitive2);
            double sim1 = simPrimitive(firstPrimitive1, firstPrimitive2);
//            System.out.println("sim1="+sim1);
            // ����������ԭ���ƶ�
            List<String> list1 = w1.getOtherPrimitives();
            List<String> list2 = w2.getOtherPrimitives();
           
            double sim2 = simList(list1, list2);
//            System.out.println("sim2="+sim2);
            // ��ϵ��ԭ���ƶ�
            Map<String, List<String>> map1 = w1.getRelationalPrimitives();
            Map<String, List<String>> map2 = w2.getRelationalPrimitives();
            double sim3 = simMap(map1, map2);
//            System.out.println("sim3="+sim3);
            // ��ϵ�������ƶ�
            map1 = w1.getRelationSimbolPrimitives();
            map2 = w2.getRelationSimbolPrimitives();
            double sim4 = simMap(map1, map2);
//            System.out.println("sim4="+sim4);
            double product = sim1;
            double sum = beta1 * product;
//            System.out.println(sum+"  1");
            product *= sim2;
//            product = sim2;
            sum += beta2 * product;
//            System.out.println(sum+"  2");
            product *= sim3;
//            product = sim3;
            sum += beta3 * product;
//            System.out.println(sum+"  3");
            product *= sim4;
//            product = sim4;
            sum += beta4 * product;
//            System.out.println(sum+"  4");
            return sum;
        }
        return 0.0;
    }

    /**
     * map�����ƶȡ�
     * 
     * @param map1
     * @param map2
     * @return
     */
    public static double simMap(Map<String, List<String>> map1,
            Map<String, List<String>> map2) {
        if (map1.isEmpty() && map2.isEmpty()) {
//            return 0;
        	return 1;
        }
        int total =map1.size() + map2.size();
        double sim = 0;
        int count = 0;
        for (String key : map1.keySet()) {
            if (map2.containsKey(key)) {
                List<String> list1 = map1.get(key);
                List<String> list2 = map2.get(key);
                sim += simList(list1, list2);
                count++;
            }
        }
        return (sim + delta * (total-2*count))
                / (total-count);
    }

    /**
     * �Ƚ��������ϵ����ƶ�
     * 
     * @param list1
     * @param list2
     * @return
     */
    public static double simList(List<String> list1, List<String> list2) {
        if (list1.isEmpty() && list2.isEmpty()){
//            System.out.println("empty");
        	return 1;
//            return 0;
        }else{
//        System.out.println("������ԭ1��");
//        for(int i = 0;i<list1.size();i++){
//        	System.out.print(list1.get(i)+"\t");
//        }
//        System.out.println();
//        System.out.println("������ԭ2��");
//        for(int i = 0;i<list2.size();i++){
//        	System.out.print(list2.get(i)+"\t");
//        }
//        System.out.println();
        int m = list1.size();
        int n = list2.size();
        int big = (m > n) ? m : n;
        int N = (m < n) ? m : n;
        int count = 0;
        int index1 = 0, index2 = 0;
        double sum = 0;
        double max = 0;
        while (count < N) {
            max = 0;
            for (int i = 0; i < list1.size(); i++) {
                for (int j = 0; j < list2.size(); j++) {
                    double sim = innerSimWord(list1.get(i), list2.get(j));
                    if (sim > max) {
                        index1 = i;
                        index2 = j;
                        max = sim;
                    }
                }
            }
            sum += max;
//            list1.remove(index1);
//            list2.remove(index2);
            count++;
        }
        double shit = (sum + delta * (big - N)) / big;
        //ɸѡ��������ԭ���ƶ�
//        System.out.println("Shit="+shit);
        
        return shit;
        }
    }

    /**
     * �ڲ��Ƚ������ʣ�������Ϊ����ʣ�Ҳ��������ԭ
     * 
     * @param word1
     * @param word2
     * @return
     */
    private static double innerSimWord(String word1, String word2) {
        boolean isPrimitive1 = Primitive.isPrimitive(word1);
        boolean isPrimitive2 = Primitive.isPrimitive(word2);
        // ������ԭ
        if (isPrimitive1 && isPrimitive2)
            return simPrimitive(word1, word2);
        // �����
        if (!isPrimitive1 && !isPrimitive2) {
            if (word1.equals(word2))
                return 1;
            else
                return 0;
        }
        // ��ԭ�;���ʵ����ƶ�, Ĭ��Ϊgamma=0.2
        return gamma;
    }

    /**
     * @param primitive1
     * @param primitive2
     * @return
     */
    public static double simPrimitive(String primitive1, String primitive2) {
        int dis = disPrimitive(primitive1, primitive2);
//        System.out.println("dis"+dis);
        return alpha / (dis + alpha);
    }

    /**
     * ����������ԭ֮��ľ��룬���������ԭ���û�й�ͬ�ڵ㣬���������ǵľ���Ϊ20��
     * 
     * @param primitive1
     * @param primitive2
     * @return
     */
    public static int disPrimitive(String primitive1, String primitive2) {
        List<Integer> list1 = Primitive.getParents(primitive1);
        List<Integer> list2 = Primitive.getParents(primitive2);
        for (int i = 0; i < list1.size(); i++) {
            int id1 = list1.get(i);
            if (list2.contains(id1)) {
                int index = list2.indexOf(id1);
                return index + i;
            }
        }
        return DEFAULT_PRIMITIVE_DIS;
    }

    /**
     * ����һ������
     * 
     * @param word
     */
    public static void addWord(Word word) {
        List<Word> list = ALLWORDS.get(word.getWord());

        if (list == null) {
            list = new ArrayList<Word>();
            list.add(word);
            ALLWORDS.put(word.getWord(), list);
        } else {
            list.add(word);
        }
    }
    
    //������д�����
    public static String[] wordArr(String fileName){
    	try{
    		FileReader f = new FileReader(fileName);
    		BufferedReader file = new BufferedReader(f);
    		String tempLine = null;
    		List<String> n = new ArrayList<String>();
    		tempLine = file.readLine();
    		while (tempLine.length()>=1)
    		{
    			n.add(tempLine);
    			tempLine = file.readLine();
    		}
    	
    		String[] none = new String[n.size()];
    		for (int i=0; i<n.size(); i++)
    		{
    			none[i] = n.get(i);
    			//System.out.println(none[i]);
    		}
    		f.close();
    		return none;
    	} catch (Exception e){
    		System.out.println("ERROR!!!");
    		return null;
    	}
    }
    
    
    //��������΢�������ƶ�
    public static double twiSim (double arr[][]){
    	double maxTemp = 0.0;
    	double max5 = 0.0;
    	int maxj = 0, maxk = 0;
    	for (int i=0; i<5; i++){
    		for (int j=0; j<arr.length; j++){
    			for (int k=0; k<arr[j].length; k++){
    				if (arr[j][k] > maxTemp){
    					maxTemp = arr[j][k];
    					maxj = j;
    					maxk = k;
    				}
    			}
    		}
    		max5 += maxTemp;
    		arr[maxj][maxk] = 0.0;
    		maxTemp = 0.0;
    	}
    	double maxavr = max5 / 5;
    	return maxavr;
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
    /*    BufferedReader reader = new BufferedReader(new FileReader(
                "dict/glossary.dat"));
        Set<String> set = new HashSet<String>();
        String line = reader.readLine();
        while (line != null) {
            // System.out.println(line);
            line = line.replaceAll("\\s+", " ");
            String[] strs = line.split(" ");
            for (int i = 0; i < strs.length; i++) {
                System.out.print(" " + strs[i]);
            }
            System.out.println();
            set.add(strs[1]);
            line = reader.readLine();
        }
        System.out.println(set.size());
        for (String name : set) {
            System.out.println(name);
        }*/
        
    	//wordArr("D:/����/parse/twi/character"+"/ch1.txt");
    	String wordlist1[][] = new String[20][];
    	
    	for (int i=0; i<20; i++){
    		wordlist1[i] = wordArr("D:/����/parse/twi/character/ch"+(i+1)+".txt");
    		System.out.println((i+1)+"has done");
    	}
    	
    	String wordlist2[][] = new String[20][];
    	
    	for (int i=0; i<20; i++){
    		wordlist2[i] = wordArr("D:/����/parse/twi/character2/ch"+(i+1)+".txt");
    		System.out.println((i+1)+"has done");
    	}
    	
    	double sim[][][][] = new double [20][20][][];
    	int j,k,m,n;
    	
    	//ͬһ��������ƶȼ���
    	for (j=0; j<20; j++){
    		for (k=j+1; k<20; k++){
    			double simTemp[][] = new double[wordlist1[j].length][wordlist1[k].length];
    			System.out.println((j+1)+"��"+(k+1));
    			for (m=0; m<wordlist1[j].length; m++){
    				for (n=0; n<wordlist1[k].length; n++){
    					if (wordlist1[j][m].equalsIgnoreCase(wordlist1[k][n])){
    						simTemp[m][n] = 1.0;
    					}
    					else{
    						simTemp[m][n] = simWord(wordlist1[j][m], wordlist1[k][n]);
    					}
    					System.out.print(simTemp[m][n]+"  ");
    				}
    				System.out.println();
    			}
    			sim[j][k] = simTemp;
    			System.out.println();
    		}
    	}  
    	
    	
    	//��ͬ������ƶȽ������
    	for (j=0; j<20; j++){
    		for (k=0; k<20; k++){
    			double simTemp[][] = new double[wordlist1[j].length][wordlist2[k].length];
    			System.out.println((j+1)+"��"+(k+1));
    			for (m=0; m<wordlist1[j].length; m++){
    				for (n=0; n<wordlist2[k].length; n++){
    					if (wordlist1[j][m].equalsIgnoreCase(wordlist2[k][n])){
    						simTemp[m][n] = 1.0;
    					}
    					else{
    						simTemp[m][n] = simWord(wordlist1[j][m], wordlist2[k][n]);
    					}
    					System.out.print(simTemp[m][n]+"  ");
    				}
    				System.out.println();
    			}
    			sim[j][k] = simTemp;
    			System.out.println();
    		}
    	}
    	
    	
    	FileWriter rs = new FileWriter("D:/����/parse/twi/difresult.txt");
    	
    	
    	for (j=0; j<20; j++){
    		for (k=0; k<20; k++){
    			rs.write((j+1)+"��"+(k+1)+"�����ƶ�Ϊ�� ");
    			rs.write(twiSim(sim[j][k])+"\r\n");
    		}
    	}
    	rs.close();
    	
    	//System.out.println(simWord("����","����"));
    }
}

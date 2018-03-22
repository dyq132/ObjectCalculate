package com.object.contex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.soap.Node;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.object.similary.WordSimilarity;

/**
 * 计算两个语句的相似度
 * @author DaiYuQin
 *
 */
public class Calculate {

	public void calWord(ArrayList<HaWord> words1,ArrayList<HaWord> words2){
//		String word1 = "交易";
//	    String word2 = "商";
//	    //计算词语相似度
//	    
//	    double sim = WordSimilarity.simWord(word2, word1);
//	    System.out.println("相似度"+sim);
	    
//	    for(int i = 0;i<words1.size();i++){
//	    	for(int j = 0;j<words2.size();j++){
//	    		 double sim1 = WordSimilarity.simWord(words1.get(i).getPartContex(), words2.get(j).getPartContex());
//	    		    System.out.println(words1.get(i).getPartContex()+"——"+words2.get(j).getPartContex()+"  :相似度"+sim1);
//	    	}
//	    }
	    System.out.println("句子1：");
	    arrange(words1);
	    ArrayList<WeightWord> weightWords1 = arrangeWord(words1);
//	    for(int i = 0;i<weightWords1.size();i++){
//	    	System.out.println("关键词："+weightWords1.get(i).getWord()+"--权重："+weightWords1.get(i).getWeight());
//	    }
//	    System.out.println("句子2：");
////	    ArrayList<WeightWord> weightWords2 = arrangeWord(words2);
//	    for(int i = 0;i<words2.size();i++){
//	    	System.out.println("句子2："+words2.get(i).getPartContex());
//	    }
//	    calcuSimilary(weightWords1, words2);
	}
	
	public void calcuSimilary(ArrayList<WeightWord> weightWords1,ArrayList<HaWord> Words2){
		double allsim = 0.0;
		for(int i = 0;i<weightWords1.size();i++){
			ArrayList<Double> simarr = new ArrayList<>();
			for(int j = 0;j<Words2.size();j++){
				double sim = 0;
				if(weightWords1.get(i).getWord().equals(Words2.get(j).getPartContex())){
					sim =1;
				}else{
					sim = WordSimilarity.simWord(weightWords1.get(i).getWord(), Words2.get(j).getPartContex());
				}
				System.out.println(weightWords1.get(i).getWord()+"--"+Words2.get(j).getPartContex()+":"+sim);
				simarr.add(sim);
			}
			System.out.println("关键词："+weightWords1.get(i).getWord()+maxSim(simarr));
//			System.out.println("相似度最大值："+maxSim(simarr)+"--权重"+weightWords1.get(i).getWeight()
//					+"--关键词"+weightWords1.get(i).getWord());
			allsim = allsim+maxSim(simarr)*weightWords1.get(i).getWeight();
		}
		System.out.println("句子1和句子2的相似度为="+allsim/weightWords1.size());
	}
	
	private Double maxSim(ArrayList<Double> sims){
		Double temp = sims.get(0);
		for(int i = 1;i<sims.size();i++){
			if(sims.get(i)>temp)
				temp = sims.get(i);
		}
		return temp;
	}
	
	/**
	 * 去除句中标点、辅助词、介词、连词
	 * @param words
	 * @return
	 */
	public ArrayList<HaWord> arrange(ArrayList<HaWord> words){
		ArrayList<HaWord> tempwords = new ArrayList<>();
		for(int i = 0;i<words.size();i++)
			tempwords.add(words.get(i));
		ArrayList<Integer> specialIndex = new ArrayList<>();
		for(int i = 0;i<tempwords.size();i++){
			String flg = tempwords.get(i).getWordCharater();
			if(flg.equals("wp")||flg.equals("c")||flg.equals("p")||flg.equals("u")){
				specialIndex.add(i);
			}
		}
		for(int i = 0;i<tempwords.size();i++){
			String flg = tempwords.get(i).getWordCharater();
			if(flg.equals("wp")||flg.equals("c")||flg.equals("p")||flg.equals("u")){
				tempwords.remove(i);
			}
		}
		for(int i = 0;i<tempwords.size();i++){
			for(Integer key:tempwords.get(i).getSynstatic().keySet()){
				if(key<specialIndex.get(0)){
					
				}else{
					for(int j = 0;j<specialIndex.size();j++){
						if(specialIndex.get(j)>=key){
							Map<Integer,String> synparent = new HashMap();
							synparent.put(key-j, tempwords.get(i).getSynstatic().get(key));
							tempwords.get(i).setSynstatic(synparent);
							break;
						}
					}
				}
			}
		}
		arrangeWeight(tempwords);
		return tempwords;

	}
	/**
	 * 分配权重
	 * @param words
	 */
	public void arrangeWeight(ArrayList<HaWord> words){
		ArrayList<Integer> edge = new ArrayList<>();//存储每个节点所包含的边数
		int sum = 0;
		for(int i = 0;i<words.size();i++){
			int countedge = 0;
			for(int j = 0;j<words.size();j++){	
			for(Integer key:words.get(j).getSynstatic().keySet()){
				if(i==key)
					countedge++;		
			}
			}
			for(Integer key:words.get(i).getSynstatic().keySet()){
				if(key>=0){
					edge.add(countedge+1);
					sum = sum+countedge+1;
				}
				else{ 
					edge.add(countedge);
					sum = sum +countedge;
				}
			}
//			System.out.println("分词："+tempwords.get(i).getPartContex()+":parent:"+key);
		}
//		System.out.println("sum:"+sum);
		ArrayList<WeightWord> weightWords = new ArrayList<>();
		for(int i = 0;i<words.size();i++){
			WeightWord weightWord = new WeightWord();
			weightWord.word = words.get(i).getPartContex();
			weightWord.weight = (double)edge.get(i)/(double)sum;
			weightWords.add(weightWord);
//			System.out.println(edge.get(i));
		}
		double haha = 0.0;
		for(int i = 0;i<weightWords.size();i++){
//			System.out.println(weightWords.get(i).getWord()+"--"+weightWords.get(i).getWeight());
			haha = haha+weightWords.get(i).getWeight();
		}
	
		
	}
	
	
	/**
	 * 整理一个句子
	 */
	public ArrayList<WeightWord> arrangeWord(ArrayList<HaWord> words){
		ArrayList<TreeNode> nodes = new ArrayList<>();
	    int root = creatroot(words,nodes);
	    pruning(nodes,words,root);
	    allEdge(nodes.get(root));
//		System.out.println("total="+total);
		double sum = 0.0;
		for(int i = 0;i<nodes.size();i++){
			if(nodes.get(i).getVar()!=null){
//			System.out.println("整理句子："+nodes.get(i).getVar()+"--"+nodes.get(i).getEdge());
			nodes.get(i).edge = nodes.get(i).getEdge()/total;
			sum = sum + nodes.get(i).getEdge();
			}
		}
		for(int i = 0;i<nodes.size();i++){
			if(nodes.get(i).getVar()!=null){
			nodes.get(i).edge = (1/sum)*nodes.get(i).getEdge(); 
//			System.out.println("化简之后："+nodes.get(i).getVar()+"--"+nodes.get(i).getEdge());
			}
		}
//		print(nodes.get(root));
		ArrayList<WeightWord> weightWords = new ArrayList<>();
		turnWWord(nodes, weightWords);
		return weightWords;
	}
	
	/**
	 * 将建立好的树转换为WeightWord
	 * @param nodes
	 * @param weightWords
	 */
	public void turnWWord( ArrayList<TreeNode> nodes,ArrayList<WeightWord> weightWords){
		for(int i = 0;i<nodes.size();i++){
			if(nodes.get(i)!=null&&nodes.get(i).getVar()!=null){
			WeightWord weightWord =new WeightWord();
			weightWord.weight = nodes.get(i).getEdge();
			weightWord.word = nodes.get(i).getVar();
			weightWords.add(weightWord);
			}
		}
//		System.out.println("转换：");
//		for(int i = 0;i<weightWords.size();i++){
//			System.out.println(weightWords.get(i).getWord()+"=="+weightWords.get(i).getWeight());
//		}
		for(int i = 0;i<weightWords.size();i++){
			for(int j = i+1;j<weightWords.size();j++){
				if(weightWords.get(i).getWord().equals(weightWords.get(j).getWord())){
					weightWords.get(i).weight += weightWords.get(j).getWeight();
					weightWords.remove(j);
				}
			}
		}
//		System.out.println("精简：");
//		for(int i = 0;i<weightWords.size();i++){
//			System.out.println(weightWords.get(i).getWord()+"=="+weightWords.get(i).getWeight());
//		}
	}
	
	
	/**
	 * 创建树
	 * @param words
	 */
	public int creatroot(ArrayList<HaWord> words,ArrayList<TreeNode> nodes){
		ArrayList<String> contex = new ArrayList<>();
		ArrayList<Integer> synindex = new ArrayList<>();
		for(int i = 0;i<words.size();i++){
			contex.add(words.get(i).getPartContex());
			for(Integer key:words.get(i).getSynstatic().keySet()){
//        		String relate = words.get(i).getSynstatic().get(key);
//        		System.out.println(key+"+value="+relate);
				synindex.add(key);
        	}
		}
//		ArrayList<TreeNode> nodes = new ArrayList<>();
		for(int i = 0;i<contex.size();i++){
			TreeNode node = new TreeNode(contex.get(i),0.0);
			nodes.add(node);
		}
		for(int i = 0;i<synindex.size();i++){
			if(synindex.get(i)>0){
//			nodes.get(i).parent = nodes.get(index.get(i));
				if(nodes.get(synindex.get(i)).getLeft()==null){
					nodes.get(synindex.get(i)).left = nodes.get(i);
				}
				else if (nodes.get(synindex.get(i)).getMiddle()==null) {
					nodes.get(synindex.get(i)).middle = nodes.get(i);
				}
				else 
					nodes.get(synindex.get(i)).right = nodes.get(i);
			}
		}
		int root = 0;
		for(int i = 0;i<synindex.size();i++){
			if(synindex.get(i)<0){
//				print(nodes.get(i));
				root = i;
			}
		}
//		allEdge(nodes.get(root));
//		System.out.println("total="+total);
//		print(nodes.get(root));
//		pruning(nodes,words,root);
//		print(nodes.get(root));
		return root;
	}
	/**
	 * 对树进行修剪
	 * @param nodes
	 * @param words
	 */
	public void pruning(ArrayList<TreeNode> nodes,ArrayList<HaWord> words,int root){
		for(int i = 0;i<words.size();i++){
			String flg = words.get(i).getWordCharater();
			
//			if(flg.equals("wp")){
//				nodes.get(i).var = null;
//			}
			if(flg.equals("u")||flg.equals("wp")||flg.equals("p")||flg.equals("c")){
				if(nodes.get(i).left==null)
				nodes.get(i).var = null;
				else{
					nodes.get(i).var = nodes.get(i).getLeft().var;
					nodes.get(i).getLeft().var = null;
				}
			}
		}
//		print(nodes.get(root));
//		allEdge(nodes.get(root));
//		System.out.println("total="+total);
//		for(int i = 0;i<nodes.size();i++){
//			nodes.get(i).edge = nodes.get(i).getEdge()/total;
//		}
//		print(nodes.get(root));
	}
	
	public void weight(ArrayList<TreeNode> nodes,ArrayList<HaWord> words){
		for(int i = 0;i<words.size();i++){
			String flg = words.get(i).getWordCharater();
			if(flg.equals("u")||flg.equals("p")){
				if(i>0){
					nodes.get(i-1).edge += nodes.get(i).getEdge();
				}
			}
		}
	}
	
	/**
	 * 计算该树所有的边
	 * @param root
	 */
	int total = 0;
	public void allEdge(TreeNode root){
		if(root!=null){
			if(total==0){
			if(root.getLeft()!=null&&root.getLeft().getVar()!=null)
				total=total+1;
			if(root.getMiddle()!=null&&root.getMiddle().getVar()!=null)
				total=total+1;
			if (root.getRight()!=null&&root.getRight().getVar()!=null) 
				total=total+1;
			root.edge = (double) total;
			}else{
				double count = 0;
				if(root.getLeft()!=null&&root.getLeft().getVar()!=null)
					{count=count+1;total=total+1;}
				if(root.getMiddle()!=null&&root.getMiddle().getVar()!=null)
					{count=count+1;total=total+1;}
				if (root.getRight()!=null&&root.getRight().getVar()!=null) 
					{count=count+1;total=total+1;}
				root.edge = count+1;
			}
			allEdge(root.getLeft());
			allEdge(root.getMiddle());
			allEdge(root.getRight());
		}
	}
	
	//打印树
	public void print(TreeNode root){
		if(root!=null){
			if(root.getVar()!=null){
			print(root.getLeft());
		System.out.println(root.var+"=="+root.edge);
		print(root.getMiddle());
		print(root.getRight());
			}
		}
	}
		
	
	
}

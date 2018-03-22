package com.object.tongyicilin;

/**
 * 测试同义词林的相似度
 * @author DaiYuQin
 *
 */
public class TestTong {
public static void main(String[] args) {
	TongSimilarity tongSimilarity = new TongSimilarity();
	tongSimilarity.readCiLin();
	double similarity = tongSimilarity.getSimilarity("人民", "国民");
    System.out.println("人民--" + "国民:" + similarity);
    similarity = tongSimilarity.getSimilarity("人民", "群众");
    System.out.println("人民--" + "群众:" + similarity);
    similarity = tongSimilarity.getSimilarity("人民", "党群");
    System.out.println("人民--" + "党群:" + similarity);
    similarity = tongSimilarity.getSimilarity("人民", "良民");
    System.out.println("人民--" + "良民:" + similarity);
    similarity = tongSimilarity.getSimilarity("人民", "同志");
    System.out.println("人民--" + "同志:" + similarity);
    similarity = tongSimilarity.getSimilarity("人民", "成年人");
    System.out.println("人民--" + "成年人:" + similarity);
    similarity = tongSimilarity.getSimilarity("人民", "市民");
    System.out.println("人民--" + "市民:" + similarity);
    similarity = tongSimilarity.getSimilarity("人民", "亲属");
    System.out.println("人民--" + "亲属:" + similarity);
    similarity = tongSimilarity.getSimilarity("人民", "志愿者");
    System.out.println("人民--" + "志愿者:" + similarity);
    similarity = tongSimilarity.getSimilarity("人民", "先锋");
    System.out.println("人民--" + "先锋:" + similarity);
    similarity = tongSimilarity.getSimilarity("老百姓", "良民");
    System.out.println("老百姓--" + "良民:" + similarity);
}
}

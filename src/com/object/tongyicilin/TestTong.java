package com.object.tongyicilin;

/**
 * ����ͬ����ֵ����ƶ�
 * @author DaiYuQin
 *
 */
public class TestTong {
public static void main(String[] args) {
	TongSimilarity tongSimilarity = new TongSimilarity();
	tongSimilarity.readCiLin();
	double similarity = tongSimilarity.getSimilarity("����", "����");
    System.out.println("����--" + "����:" + similarity);
    similarity = tongSimilarity.getSimilarity("����", "Ⱥ��");
    System.out.println("����--" + "Ⱥ��:" + similarity);
    similarity = tongSimilarity.getSimilarity("����", "��Ⱥ");
    System.out.println("����--" + "��Ⱥ:" + similarity);
    similarity = tongSimilarity.getSimilarity("����", "����");
    System.out.println("����--" + "����:" + similarity);
    similarity = tongSimilarity.getSimilarity("����", "ͬ־");
    System.out.println("����--" + "ͬ־:" + similarity);
    similarity = tongSimilarity.getSimilarity("����", "������");
    System.out.println("����--" + "������:" + similarity);
    similarity = tongSimilarity.getSimilarity("����", "����");
    System.out.println("����--" + "����:" + similarity);
    similarity = tongSimilarity.getSimilarity("����", "����");
    System.out.println("����--" + "����:" + similarity);
    similarity = tongSimilarity.getSimilarity("����", "־Ը��");
    System.out.println("����--" + "־Ը��:" + similarity);
    similarity = tongSimilarity.getSimilarity("����", "�ȷ�");
    System.out.println("����--" + "�ȷ�:" + similarity);
    similarity = tongSimilarity.getSimilarity("�ϰ���", "����");
    System.out.println("�ϰ���--" + "����:" + similarity);
}
}

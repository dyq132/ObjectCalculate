package com.object.contex;

import java.util.ArrayList;
import java.util.Map;

public class HaWord {
	private String partContex;//�ִ�����cont
	private String wordCharater;//����pos
	private String nameEnty;//����ʵ��ne
//	private Integer parentId;//����䷨�ĸ��ڵ�id��parent
//	private String relate;//����䷨�븸�ڵ���Ӧ�Ĺ�ϵrelate
	private Map<Integer, String> synstatic;//�䷨�����еĸ��ڵ㼰��Ӧ��ϵ
//	private Integer semparent;//������������ĸ��ڵ�id��semparent
//	private String semrelate;//������������븸�ڵ��Ӧ�Ĺ�ϵsemrelate
	private Map<Integer, String> sematic;//���������и��ڵ㼰��Ӧ��ϵ
	private ArrayList<Map<String, ArrayList<Integer>>> role;//��ɫ���ơ���ʼ��š��������
//	private String type;//��ɫ����type
//	private Integer typebeg;//��ʼ���beg
//	private Integer typeend;//�������end
	public String getPartContex() {
		return partContex;
	}
	public void setPartContex(String partContex) {
		this.partContex = partContex;
	}
	public String getWordCharater() {
		return wordCharater;
	}
	public void setWordCharater(String wordCharater) {
		this.wordCharater = wordCharater;
	}
	public String getNameEnty() {
		return nameEnty;
	}
	public void setNameEnty(String nameEnty) {
		this.nameEnty = nameEnty;
	}
	public Map<Integer, String> getSynstatic() {
		return synstatic;
	}
	public void setSynstatic(Map<Integer, String> synstatic) {
		this.synstatic = synstatic;
	}
	public Map<Integer, String> getSematic() {
		return sematic;
	}
	public void setSematic(Map<Integer, String> sematic) {
		this.sematic = sematic;
	}
	public ArrayList<Map<String, ArrayList<Integer>>> getRole() {
		return role;
	}
	public void setRole(ArrayList<Map<String, ArrayList<Integer>>> role) {
		this.role = role;
	}
	
}

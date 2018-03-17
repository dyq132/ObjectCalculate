package com.object.contex;

import java.util.ArrayList;
import java.util.Map;

public class HaWord {
	private String partContex;//分词内容cont
	private String wordCharater;//词性pos
	private String nameEnty;//命名实体ne
//	private Integer parentId;//依存句法的父节点id号parent
//	private String relate;//依存句法与父节点间对应的关系relate
	private Map<Integer, String> synstatic;//句法依存中的父节点及对应关系
//	private Integer semparent;//语义依存分析的父节点id号semparent
//	private String semrelate;//语义依存分析与父节点对应的关系semrelate
	private Map<Integer, String> sematic;//语义依存中父节点及对应关系
	private ArrayList<Map<String, ArrayList<Integer>>> role;//角色名称、开始序号、结束序号
//	private String type;//角色名称type
//	private Integer typebeg;//开始序号beg
//	private Integer typeend;//结束序号end
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

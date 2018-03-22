package com.object.contex;

import java.util.ArrayList;

/*
 * 获取的试题：题目、标准答案、学生答案属性
 */
public class Question {
	private String title;
	private String standerAn;
	private ArrayList<String> otherAns;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStanderAn() {
		return standerAn;
	}
	public void setStanderAn(String standerAn) {
		this.standerAn = standerAn;
	}
	public ArrayList<String> getOtherAns() {
		return otherAns;
	}
	public void setOtherAns(ArrayList<String> otherAns) {
		this.otherAns = otherAns;
	}
	
	

}

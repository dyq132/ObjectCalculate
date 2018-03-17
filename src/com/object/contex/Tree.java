package com.object.contex;

import java.awt.print.Printable;
import java.util.ArrayList;

import javax.xml.soap.Node;

public class Tree {

	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		list.add("¼×");
		list.add("ÒÒ");
		list.add("±û");
		list.add("¶¡");
		list.add("Îì");
		list.add("Ğç");
		ArrayList<Integer> index = new ArrayList<>();
		index.add(1);
		index.add(-1);
		index.add(3);
		index.add(4);
		index.add(1);
		index.add(4);
		ArrayList<TreeNode> nodes = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			TreeNode node = new TreeNode(list.get(i),0.0);
			nodes.add(node);
		}
		for(int i = 0;i<index.size();i++){
			if(index.get(i)>0){
//			nodes.get(i).parent = nodes.get(index.get(i));
				if(nodes.get(index.get(i)).getLeft()==null){
					nodes.get(index.get(i)).left = nodes.get(i);
				}
				else
					nodes.get(index.get(i)).right = nodes.get(i);
			}
		}
		for(int i = 0;i<index.size();i++){
			if(index.get(i)<0){
				print(nodes.get(i));
			}
		}
	}
	
	public static void print(TreeNode root){
		if(root!=null){
			print(root.getLeft());
		System.out.println(root.var);
		print(root.getRight());
		}
	}
}

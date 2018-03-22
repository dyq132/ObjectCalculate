package com.object.contex;
/**
 * 根据哈工大句法依存创建树的属性
 * @author DaiYuQin
 *
 */

public class TreeNode {
	String var;
	Double edge;
	TreeNode left;
	TreeNode right;
	TreeNode middle;
	TreeNode parent;

	public TreeNode getParent() {
		return parent;
	}


	public void setParent(TreeNode parent) {
		this.parent = parent;
	}


	public TreeNode(String var,Double edge) {
		this.var = var;
		this.edge = edge;
	}

	
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	public TreeNode getLeft() {
		return left;
	}
	public void setLeft(TreeNode left) {
		this.left = left;
	}
	public TreeNode getRight() {
		return right;
	}
	public void setRight(TreeNode right) {
		this.right = right;
	}
	public Double getEdge() {
		return edge;
	}

	public void setEdge(Double edge) {
		this.edge = edge;
	}
	public TreeNode getMiddle() {
		return middle;
	}

	public void setMiddle(TreeNode middle) {
		this.middle = middle;
	}
	
}

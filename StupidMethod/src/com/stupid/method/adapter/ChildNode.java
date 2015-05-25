package com.stupid.method.adapter;

public class ChildNode<T extends Object> {
	private boolean selectable;
	private boolean lastChild;
	private int parentID;
	private String name;
	private T t;

	public T getT() {
		return t;
	}

	public ChildNode<T> setT(T t) {
		this.t = t;
		return this;
	}

	public ChildNode(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public ChildNode<T> setSelectable(boolean selectable) {
		this.selectable = selectable;
		return this;
	}

	public boolean isLastChild() {
		return lastChild;
	}

	public ChildNode<T> setLastChild(boolean lastChild) {
		this.lastChild = lastChild;
		return this;
	}

	public int getParentID() {
		return parentID;
	}

	public ChildNode<T> setParentID(int parentID) {
		this.parentID = parentID;
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

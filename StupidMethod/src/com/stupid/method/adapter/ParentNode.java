package com.stupid.method.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/***
 * 为了防止使用的时候蒙了,建立ChildNode类,<br>
 * 理论上应该是节点套节点,当前节点不需要关心父节点是谁<br>
 * 二叉树是子节点记住父节点的id,
 * 
 * <pre>
 * 			_____node____
 * 			|			|
 * 			|			|
 *    ____node____	___node___
 *    |			 | 	|		  |
 *   node
 * </pre>
 */
public class ParentNode<P extends Object, C extends Object> implements
		Collection<ChildNode<C>> {

	/** 节点名 **/
	private String name;
	/** 保留字段 **/
	private int parentID;
	/** 子节点 **/
	private ArrayList<ChildNode<C>> nodes = new ArrayList<ChildNode<C>>();
	/** 扩展数据 **/
	private HashMap<String, Object> extendData = new HashMap<String, Object>();
	/** 是否展开 **/
	private boolean expanded;

	private P t;

	// public ParentNode(String name) {
	// this.name = name;
	//
	// }

	public ParentNode(String name, ArrayList<ChildNode<C>> nodes) {

		this(name, null, nodes);

	}

	public ParentNode(String name, P t, ArrayList<ChildNode<C>> nodes) {

		this.name = name;
		this.nodes.addAll(nodes);
		this.t = t;

	}

	public int getParentID() {
		return parentID;
	}

	public ArrayList<ChildNode<C>> getNodes() {
		return nodes;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public ParentNode<P, C> setParentID(int parentID) {
		this.parentID = parentID;
		return this;
	}

	public ParentNode<P, C> setNodes(ArrayList<ChildNode<C>> nodes) {
		this.nodes = nodes;
		return this;
	}

	public ParentNode<P, C> setExpanded(boolean expanded) {
		this.expanded = expanded;
		return this;
	}

	public String getName() {
		return name;
	}

	public ArrayList<ChildNode<C>> getChilds() {
		return nodes;
	}

	public HashMap<String, Object> getExtendData() {
		return extendData;
	}

	public ParentNode<P, C> setName(String name) {
		this.name = name;
		return this;
	}

	public ParentNode<P, C> setChilds(ArrayList<ChildNode<C>> childs) {
		this.nodes = childs;
		return this;
	}

	public ParentNode<P, C> setExtendData(HashMap<String, Object> extendData) {
		this.extendData = extendData;
		return this;
	}

	public boolean containsKey(String key) {
		return extendData.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return extendData.containsValue(value);
	}

	public Set<String> keySet() {

		return extendData.keySet();

	}

	public Object getValue(Object key) {
		return extendData.get(key);
	}

	public Object putValue(String key, Object value) {

		return extendData.put(key, value);

	}

	public int size() {

		return nodes.size();

	}

	public ChildNode get(int position) {

		return nodes.get(position);

	}

	public ChildNode remov(int position) {
		return nodes.remove(position);

	}

	@Override
	public boolean add(ChildNode object) {

		return this.nodes.add(object);
	}

	public P getT() {
		return t;
	}

	public ParentNode<P, C> setT(P t) {
		this.t = t;
		return this;
	}

	@Override
	public boolean addAll(Collection<? extends ChildNode<C>> collection) {

		return this.nodes.addAll(collection);
	}

	@Override
	public void clear() {
		this.nodes.clear();

	}

	@Override
	public boolean contains(Object object) {

		return this.nodes.contains(object);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {

		return this.nodes.containsAll(collection);
	}

	@Override
	public boolean isEmpty() {

		return this.nodes.isEmpty();
	}

	@Override
	public Iterator<ChildNode<C>> iterator() {

		return this.nodes.iterator();
	}

	@Override
	public boolean remove(Object object) {

		return this.nodes.remove(object);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {

		return this.nodes.removeAll(collection);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {

		return this.nodes.retainAll(collection);
	}

	@Override
	public Object[] toArray() {

		return this.nodes.toArray();
	}

	@Override
	public <T> T[] toArray(T[] array) {

		return this.nodes.toArray(array);
	}

	public String toString() {
		return name;
	}
}
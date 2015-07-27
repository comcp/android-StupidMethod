package com.stupid.method.adapter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

/**
 * 
 * **/
public class ExpandAdapter extends BaseExpandableListAdapter {
	ArrayList<ParentNode> groups = new ArrayList<ParentNode>();
	LayoutInflater inflater;
	Context context;
	private Constructor<?> parentConstructor;

	private Constructor<?> childConstructor;

	public ExpandAdapter(Context context, List<ParentNode> groups,
			Class<? extends XViewHolder<? extends ParentNode>> parClass,
			Class<? extends XViewHolder<? extends ChildNode>> childClass) {
		this.groups.addAll(groups);
		this.context = context;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		try {
			Class<?> parentView = Class.forName(parClass.getName());
			Class<?> childView = Class.forName(childClass.getName());

			parentConstructor = parentView.getConstructor(LayoutInflater.class);
			childConstructor = childView.getConstructor(LayoutInflater.class);
		} catch (ClassNotFoundException e) {

			System.err.println(e);

		} catch (NoSuchMethodException e) {

			System.err.println(e);
		}
	}

	@Override
	public int getGroupCount() {

		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		return groups.get(groupPosition).size();
	}

	@Override
	public ParentNode getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public ChildNode getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/** 不知道干嘛的 */
	@Override
	public boolean hasStableIds() {
		return false;
	}

	/** 子数据能否点击 **/
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {

		return getChild(groupPosition, childPosition).isSelectable();
	}

	/*** 绘制分组列表 **/
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		XViewHolder<ParentNode> view = null;

		if (convertView == null) {
			// 在这里写 反射
			if (parentConstructor == null) {
				System.err.println("类没有被实例化");
			}

			try {
				view = (XViewHolder<ParentNode>) parentConstructor
						.newInstance(inflater);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			view = (XViewHolder<ParentNode>) convertView.getTag();
		}

		return view.getView(groups.get(groupPosition).setExpanded(isExpanded),
				groupPosition);
	}

	/** 绘制子节点 **/
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		XViewHolder<ChildNode> view = null;

		if (convertView == null) {
			// 在这里写 反射
			if (childConstructor == null) {
				System.err.println("类没有被实例化");
			}

			try {
				view = (XViewHolder<ChildNode>) childConstructor
						.newInstance(inflater);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			view = (XViewHolder<ChildNode>) convertView.getTag();
		}
		ChildNode node = groups.get(groupPosition).get(childPosition);
		node.setParentID(groupPosition);
		node.setLastChild(isLastChild);

		return view.getView(node, groupPosition);
	}

}

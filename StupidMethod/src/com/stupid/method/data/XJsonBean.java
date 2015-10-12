package com.stupid.method.data;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

public class XJsonBean implements Serializable {

	/**
	 * 
	 */
	@JSONField(serialize = false)
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {

		return JSON.toJSONString(this);
	}

}

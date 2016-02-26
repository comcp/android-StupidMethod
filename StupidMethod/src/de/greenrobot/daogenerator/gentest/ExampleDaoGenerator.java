/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class ExampleDaoGenerator extends Generator {

	public static final String dir = "com.stupid.method.app";
	public static final int version = 1;

	public static void main(String[] args) throws Exception {
		new ExampleDaoGenerator().generateAll(version, dir);
	}

	@Override
	protected void creat(Schema schema) {

		Entity user = schema.addEntity("User");
		user.setTableName("T_USERS");
		user.addStringProperty("userName");
		user.addIntProperty("id");
		user.addStringProperty("headImg");
		Entity msg = schema.addEntity("Message");
		msg.setTableName("T_MESSAGES");
		msg.addIntProperty("id");// 聊天信息表记录编号（主键）
		msg.addIntProperty("fromUserId");// 发送信息者QQ号（外键）
		msg.addIntProperty("toUserId");// 收到信息者的QQ号（外键）
		msg.addStringProperty("message");// 发送的信息
		msg.addIntProperty("messageTypeId");// 信息的类型编号
		msg.addIntProperty("messageState");// 信息的状态（被接收到为1）
		msg.addDateProperty("messageTime");// 发送的时间
		Entity friend = schema.addEntity("Friend");
		friend.setTableName("T_FRIEND");
		friend.addIntProperty("id");
		friend.addIntProperty("hostId");
		friend.addIntProperty("friendId");
		// TmpData = schema.addEntity("TmpData");
		// TmpData.addStringProperty("Key").unique().primaryKey().notNull();
		// TmpData.addStringProperty("Value");
		// TmpData.addDateProperty("Date");
	}

}

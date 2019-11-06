/**
 * 文  件   名：DBController.java
 * 版          权： Copyright LiJinhua All Rights Reserved.
 * 描          述：日志输出类
 * 创  建   人： LiJinHua
 * 创建时间： 2014-5-31
 * 
 * 修   改  人：
 * 修改时间：
 * 修改内容：[修改内容]
 */
package com.groupseven.voicestamp.db;

import android.content.Context;


public class DBController {
	
	private static DBController sInstance;
	

	private RecordDao mRecordDao;
	
	private TagDao mTagDao;
	
	private DBController() {
//		Context applicationContext = AppGlobal.getInstance().getApplicationContext();
//		mRecordDao = new RecordDao(applicationContext);
//		mTagDao = new TagDao(applicationContext);
	}
	
	public static DBController getInstance() {
		if (sInstance == null) {
			synchronized (DBController.class) {
				if (sInstance == null) {
					sInstance = new DBController();
				}
			}
		}
		return sInstance;
	}


//	public RecordDao getIMClientConversationDao() {
//		return mIMClientConversationDao;
//	}
//
//	public IMClientPacketDao getIMClientPacketDao() {
//		return mIMClientPacketDao;
//	}
}

package com.groupseven.voicestamp.db;

import android.content.Context;

import com.groupseven.voicestamp.tools.AppGlobal;


public class DBController {
	
	private static DBController sInstance;

	private RecordDao mRecordDao;
	
	private TagDao mTagDao;
	
	private DBController() {
		Context applicationContext = AppGlobal.getInstance().getApplicationContext();
		mRecordDao = new RecordDao(applicationContext);
		mTagDao = new TagDao(applicationContext);
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


	public RecordDao getRecordDao() {
		return mRecordDao;
	}

	public TagDao getTagDao() {
		return mTagDao;
	}
}

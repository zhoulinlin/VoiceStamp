package com.groupseven.voicestamp.db;

import android.content.Context;
import android.database.Cursor;


import java.util.HashSet;
import java.util.Set;


public class DBHelper extends BaseDbHelper {
	
	private final static int DATABASE_VERSION = 1;
	
	private final static String DATABASE_NAME = "voice_stamp.db";
	
	private static Set<Class<?>> childrenClasses = new HashSet<Class<?>>();
	
	static{
		//数据表添加
		childrenClasses.add(RecordDao.class);
		childrenClasses.add(TagDao.class);
	}
	
	public DBHelper(Context context, String tableName, String[] fieldNames,
					String[] fieldTypes) {
		super(context, DATABASE_NAME,DATABASE_VERSION,childrenClasses,tableName, fieldNames, fieldTypes);
	}

	public String getString(Cursor cursor, String columnName) {

		return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
	}

	public  int getInt(Cursor cursor, String columnName) {

		return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
	}

	public long getLong(Cursor cursor, String columnName) {

		return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
	}
}

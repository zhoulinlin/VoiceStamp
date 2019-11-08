package com.groupseven.voicestamp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Handler;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseDbHelper extends SQLiteOpenHelper {
	private static final String TAG = BaseDbHelper.class.getSimpleName();
	
	private static final String mPATTERN = "HH:mm  yyyy/MM/dd";

	private String[] FIELD_NAMES;
	private String[] FIELD_TYPES;
	private String TABLE_NAME;
	private Set<OnDbEventListener> dbListenerSet = new HashSet<OnDbEventListener>();
	private Set<Class<?>> childrenClasses = null;
	
	private SQLiteDatabase mDb = null;
	private Cursor mcursor = null;
	private Context context;
    
	private String AUTHORITY ;
	private Uri CONTENT_URI ;
    
	public void addDbClass(Class<?> c) {
		childrenClasses.add(c);
	}

	public interface OnDbEventListener {
		void onDatabaseChange(BaseDbHelper dbMan, String tableName);
	}

	public BaseDbHelper(Context context, String dbName, int version , Set<Class<?>> allTable, String tableName, String[] fieldNames,
                        String[] fieldTypes) {
		super(context, dbName, null, version);
		this.context = context;
		FIELD_NAMES = fieldNames;
		FIELD_TYPES = fieldTypes;
		childrenClasses = allTable;
		TABLE_NAME = tableName;
		AUTHORITY = context.getApplicationInfo().packageName;
		CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// create tables
		Object[] childrenSet = childrenClasses.toArray();
		for (int t = 0; t < childrenSet.length; t++) {
			Class<?> c = (Class<?>) childrenSet[t];

			try {
				Field field = c.getField("TABLE_NAME");
				String TABLE_NAME = (String) field.get(c);

				String sql = "create table if not exists " + TABLE_NAME + "("
						+ " _id INTEGER primary key autoincrement,";
				field = c.getField("FIELD_NAMES");
				String[] FIELD_NAMES = (String[]) field.get(c);
				field = c.getField("FIELD_TYPES");
				String[] FIELD_TYPES = (String[]) field.get(c);
				for (int i = 0; i < FIELD_NAMES.length; i++) {
					sql += FIELD_NAMES[i];
					sql += " ";
					sql += FIELD_TYPES[i];
					if (FIELD_NAMES.length - 1 != i)
						sql += ",";
				}

				sql += ");";

				db.execSQL(sql);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Object[] childrenSet = childrenClasses.toArray();
		for (int t = 0; t < childrenSet.length; t++) {
			Class<?> c = (Class<?>) childrenSet[t];
			try {
				Field field = c.getField("TABLE_NAME");
				String TABLE_NAME = (String) field.get(c);
				db.execSQL("drop table if exists " + TABLE_NAME);
			} catch (Exception e) {

			}
		}
		onCreate(db);
	}

	public String[] getFieldName() {
		return FIELD_NAMES;
	}

	public void close() {
		if (mcursor != null)
			mcursor.close();
		if (mDb != null)
			mDb.close();
		mDb = null;
		mcursor = null;
	}

	public Cursor query(String sql, String[] args) {
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.getReadableDatabase();
			cursor = db.rawQuery(sql, args);
		} catch (Exception e) {
//			if (db != null)
//				db.close();
			return null;
		}
		mDb = db;
		mcursor = cursor;

		return cursor;
	}

	public Cursor query() {
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.getReadableDatabase();
			cursor = db.rawQuery("select * from " + TABLE_NAME, null);

		} catch (Exception e) {
//			if (db != null)
//				db.close();
			return null;
		}

		mDb = db;
		mcursor = cursor;

		return cursor;
	}

	public long insert(String[] values) {
		return insert(FIELD_NAMES, values);
	}

	public boolean clear() {
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			db.execSQL("delete from " + TABLE_NAME + ";");
			onCreate(db);
		} catch (Exception e) {
			return false;
		} finally {
//			if (null != db) {
//
//				db.close();
//			}
		}
		notifyDBChange();
		return true;
	}

	public boolean deleteByKey(long key) {
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			db.execSQL("delete from " + TABLE_NAME + " where _id = " + key
					+ ";");
//			onCreate(db);
		} catch (Exception e) {
			return false;
		} finally {
//			if (null != db) {
//
//				db.close();
//			}
		}
		notifyDBChange();
		return true;
	}

	public int update(long ID, String[] values) {
		return update(ID, FIELD_NAMES, values);
	}

	public int update(long ID, String[] fileds, String[] values) {
		SQLiteDatabase db = null;
		int rows = 0;
		try {

			db = this.getWritableDatabase();

			String[] args = { String.valueOf(ID) };
			ContentValues content = new ContentValues();
			for (int i = 0; i < fileds.length; i++) {
				content.put(fileds[i], values[i]);
			}

			rows = db.update(TABLE_NAME, content, "_id=?", args);

		} catch (Exception e) {
			return 0;
		} finally {
//			if (null != db) {
//				db.close();
//			}
		}
		notifyDBChange();
		return rows;
	}

	public long insert(String[] fileds, String[] values) {
		SQLiteDatabase db = null;

		long keyId;

		try {

			db = this.getWritableDatabase();
			ContentValues content = new ContentValues();
			for (int i = 0; i < fileds.length; i++) {
				content.put(fileds[i], values[i]);
			}

			keyId = db.insertOrThrow(TABLE_NAME, null, content);

		} catch (Exception e) {
			return -1;
		} finally {
//			if (null != db) {
//				db.close();
//			}
		}
		notifyDBChange();
		return keyId;
	}

	public  void addDbEventListener(OnDbEventListener lsnr) {
		dbListenerSet.add(lsnr);
	}
	
	public void removeDbEventListener(OnDbEventListener lsnr) {
		dbListenerSet.remove(lsnr);
	}
	private void dispatchDbListener() {
		for (OnDbEventListener lsnrDbListener : dbListenerSet) {
			lsnrDbListener.onDatabaseChange(this, TABLE_NAME);
		}
	}
	
	
	protected class ContentValuesBuilder{
		
		private ContentValues contentValues;
		
		public ContentValuesBuilder() {
			super();
			contentValues = new ContentValues();
		}
		public ContentValuesBuilder put(String key, Object value){
			if(TextUtils.isEmpty(key) || value == null){
				return this;
			}else{
				contentValues.put(key, ""+value);
			}
			return this;
		}

		public ContentValues getContentValues() {
			return contentValues;
		}
	}

	protected ContentValues genContentValues(HashMap<String, Object> data){
		ContentValuesBuilder cv = new ContentValuesBuilder();
		for(String key : FIELD_NAMES){
			cv.put(key, data.get(key));
		}
		return cv.getContentValues();
	}
	
	protected HashMap<String, Object> getDataFromCursor(Cursor cursor) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		int id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID));
		hashMap.put(BaseColumns._ID, ""+id);
		for(String key : FIELD_NAMES){
			String v = cursor.getString(cursor.getColumnIndexOrThrow(key));
//			AppLog.d(TAG, "getDataFromCursor "+key+" : "+v);
			hashMap.put(key, v);
		}
		return  hashMap;
	}
	
	public long insert(HashMap<String, Object> data) {
		return insert(data, true);
	}
	
	public long insert(HashMap<String, Object> data, boolean isNotifyDBChange) {
		SQLiteDatabase db = null;
		long keyId;
		try {
			db = this.getWritableDatabase();
			keyId = db.insertOrThrow(TABLE_NAME, null, genContentValues(data));
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
//			if (null != db) {
//				db.close();
//			}
		}
		if(isNotifyDBChange){
			notifyDBChange();
		}
		return keyId;
	}
	
	public long insert(List<HashMap<String, Object>> listdata) {
		if(listdata == null){
			return 0;
		}
		SQLiteDatabase db = null;
		long keyCount = 0;
		try {
			db = this.getWritableDatabase();
			for(HashMap<String, Object> data : listdata){
				if(db.insertOrThrow(TABLE_NAME, null, genContentValues(data))>0){
					keyCount++;
				}
			}
		} catch (Exception e) {
			return -1;
		} finally {
//			if (null != db) {
//				db.close();
//			}
		}
		notifyDBChange();
		return keyCount;
	}


	public int update(HashMap<String, Object> data, String... keyColumns) {
		return update(data, true,keyColumns);
	}


	public int update(HashMap<String, Object> data, boolean isNotifyDBChange, String... keyColumns) {
		SQLiteDatabase db = null;
		int rows = 0;
		if(keyColumns == null || keyColumns.length <=0){
			return 0;
		}
		try {
			db = this.getWritableDatabase();
			ContentValues content = genContentValues(data);
			String[] kv = new String[keyColumns.length];
			StringBuffer sbkey = new StringBuffer();
			sbkey.append(keyColumns[0]+"=?");
			kv[0] = (String) data.get(keyColumns[0]);
			for(int i=1;i<keyColumns.length;i++){
				sbkey.append(" and "+keyColumns[i]+"=?");
				kv[i] = (String) data.get(keyColumns[i]);
			}
			rows = db.update(TABLE_NAME, content, sbkey.toString(), kv);

		} catch (Exception e) {
			return 0;
		} finally {
//			if (null != db) {
//				db.close();
//			}
		}
		if(isNotifyDBChange){
//			notifyDBChange();
		}
		return rows;
	}
	
	public long replace(HashMap<String, Object> data, String keyColumn) {
		SQLiteDatabase db = null;
		long rows = 0;
		try {
			db = this.getWritableDatabase();
//			db.beginTransaction();
			db.execSQL("delete from " + TABLE_NAME + " where "+keyColumn+" = '" + data.get(keyColumn)+ "'");
			rows = db.insertOrThrow(TABLE_NAME, null, genContentValues(data));
		} catch (Exception e) {
			return 0;
		} finally {
//			if (null != db) {
//				db.close();
//			}
//			if(rows >0){
//				db.setTransactionSuccessful();
//			}
//			db.endTransaction();
		}
//		notifyDBChange();
		return rows;
	}
	
	/**
	 * 更新多条记录
	 * @param listdata
	 * @param keyColumn
	 */
	public long update(List<HashMap<String, Object>> listdata, String keyColumn) {
		if(listdata == null){
			return 0;
		}
		SQLiteDatabase db = null;
		long keyCount = 0;
		try {
			db = this.getWritableDatabase();
			for(HashMap<String, Object> data : listdata){
				String[] args = { String.valueOf(data.get(keyColumn)) };
				ContentValues content = genContentValues(data);
				if(db.update(TABLE_NAME, content, keyColumn+"=?", args)>0){
					keyCount++;
				}
			}
		} catch (Exception e) {
			return -1;
		} finally {
//			if (null != db) {
//				db.close();
//			}
		}
//		notifyDBChange();
		return keyCount;
	}
	
	public boolean delete(String key, String value, boolean isNotifyDBChange) {
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			db.execSQL("delete from " + TABLE_NAME + " where "+key+" = ?",new String[]{value});
//			onCreate(db);
		} catch (Exception e) {
			return false;
		} finally {
//			if (null != db) {
//
//				db.close();
//			}
		}
		if(isNotifyDBChange){
			notifyDBChange();
		}
		return true;
	}
	
	public ArrayList<HashMap<String, Object>> queryToHashMap() {
		return queryToHashMap(null,null);
	}
	
	public ArrayList<HashMap<String, Object>> queryToHashMap(String where, String[] args) {
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.getReadableDatabase();
			String sql = "select * from " + TABLE_NAME;
			if(!TextUtils.isEmpty(where)){
				sql = sql + " where "+where;
			}
			cursor = db.rawQuery(sql, args);
		} catch (Exception e) {
			return null;
		}
		mDb = db;
		if(cursor == null){
			return null;
		}
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
        	HashMap<String, Object> data = getDataFromCursor(cursor);
            list.add(data);
        }
		if(cursor != null){
			cursor.close();
		}
		return list;
	}
	
	public  void notifyDBChange() {
//	        context.getContentResolver().notifyChange(CONTENT_URI, null);
	}
}

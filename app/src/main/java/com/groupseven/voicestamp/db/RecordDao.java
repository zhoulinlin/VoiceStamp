package com.groupseven.voicestamp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;


import com.groupseven.voicestamp.db.bean.Record;
import com.groupseven.voicestamp.login.data.LoginDataSource;
import com.groupseven.voicestamp.login.data.LoginRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class RecordDao extends DBHelper {

	private static final String TAG = RecordDao.class.getSimpleName();
	
	public final static String TABLE_NAME = "records";

	public static final class RecordColumns implements BaseColumns {

		public static final String USER_ID = "user_id";
		public static final String TITLE = "title";
		public static final String DATE = "date";

		public static final String DURATION = "duration";
		public static final String RECORD_ID = "record_id";
		public static final String LOCAL_PATH = "local_path";
	}
	
    public final static String[] FIELD_NAMES = {
			RecordColumns.USER_ID,
			RecordColumns.TITLE,
			RecordColumns.DATE,
			RecordColumns.DURATION,
			RecordColumns.RECORD_ID,
			RecordColumns.LOCAL_PATH
	};

    public final static String[] FIELD_TYPES = {"TEXT","TEXT","TEXT","TEXT","TEXT","TEXT"};
    
	public RecordDao(Context context) {
		super(context, TABLE_NAME, FIELD_NAMES, FIELD_TYPES);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		super.onCreate(db);
	}


	public boolean insertRecord(Record reocrd){

		if(reocrd == null){
			return false;
		}

		HashMap<String, Object> data  = new HashMap<String, Object>();

		data.put(RecordColumns.USER_ID, reocrd.getUserId());
		data.put(RecordColumns.TITLE, reocrd.getRecordTitle());
		data.put(RecordColumns.DATE, reocrd.getRecordDate());
		data.put(RecordColumns.DURATION, reocrd.getDuration());
		data.put(RecordColumns.RECORD_ID, reocrd.getRecordId());
		data.put(RecordColumns.LOCAL_PATH, reocrd.getLocalPath());

		int rows = (int) this.insert(data);
		return rows > 0;
	}


	public ArrayList<Record> queryRecordList(){

		ArrayList<Record> recordList = new ArrayList<Record>();
		Record record;
		String[] param = new String[1];
		param[0] = getCurUserUK();
		String sql = "select * from "+TABLE_NAME+" where "+ RecordColumns.USER_ID+" = ? ";
		Cursor cursor = this.query(sql, param);

		if(cursor == null){
			return null;
		}

		try {
			while (cursor.moveToNext()) {
				record = getRecordBeanFromCursor(cursor);
				recordList.add(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}

		Log.d(TAG,"queryRecordList size:"+ recordList.size());

		return recordList;
	}

	public String getCurUserUK(){
		return LoginRepository.getInstance(new LoginDataSource()).getUser().getUniqueKey();
	}


	public boolean deleteRecordByRecordId(String recorId) {
		int result = delete(RecordColumns.RECORD_ID + "=?",
				new String[]{recorId});

		return result > 0;
	}


	public Record getRecordByRecordId(String recordId) {

		Record record = null;
		String[] param = new String[1];
		param[0] = recordId;
		String sql = "select * from "+TABLE_NAME+" where "+ RecordColumns.RECORD_ID+" = ? ";
		Cursor cursor = this.query(sql, param);

		if (cursor == null) {
			return null;
		}

		if (cursor.moveToNext()) {
			record = getRecordBeanFromCursor(cursor);
		}
		if (cursor != null) {
			cursor.close();
		}
		return record;
	}

	public Record getRecordByAutoId(int autoId) {

		Record record = null;
		String[] param = new String[1];
		param[0] = autoId+"";
		String sql = "select * from "+TABLE_NAME+" where _id = ? ";
		Cursor cursor = this.query(sql, param);

		if (cursor == null) {
			return null;
		}

		if (cursor.moveToNext()) {
			record = getRecordBeanFromCursor(cursor);
		}
		if (cursor != null) {
			cursor.close();
		}
		return record;
	}



	public Record getRecordBeanFromCursor(Cursor cursor) {
		Record record = new Record();

		record.setDuration(getString(cursor,RecordColumns.DURATION));
		record.setLocalPath(getString(cursor,RecordColumns.LOCAL_PATH));
		record.setRecordDate(getString(cursor,RecordColumns.DATE));
		record.setRecordId(getString(cursor,RecordColumns.RECORD_ID));
		record.setRecordTitle(getString(cursor,RecordColumns.TITLE));
		record.setUserId(getString(cursor,RecordColumns.USER_ID));
		record.setAutoId(getInt(cursor,"_id"));

		return record;
	}




	public ContentValues getContentValues(Record record) {

		ContentValues contentValues = new ContentValues();

		contentValues.put(RecordColumns.DURATION, record.getDuration());
		contentValues.put(RecordColumns.LOCAL_PATH, record.getLocalPath());
		contentValues.put(RecordColumns.DATE, record.getRecordDate());
		contentValues.put(RecordColumns.RECORD_ID, record.getRecordId());
		contentValues.put(RecordColumns.TITLE, record.getRecordTitle());
		contentValues.put(RecordColumns.USER_ID, record.getUserId());

		return contentValues;
	}

}

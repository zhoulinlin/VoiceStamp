package com.groupseven.voicestamp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.groupseven.voicestamp.db.bean.RecTag;

import java.util.ArrayList;
import java.util.HashMap;

public class TagDao extends DBHelper {

	private static final String TAG = TagDao.class.getSimpleName();

	public final static String TABLE_NAME = "tags";

	public static final class TagColumns implements BaseColumns {

		public static final String RECORD_ID = "record_id";
		public static final String TAG_TYPE = "type";
		public static final String TAG_TITLE = "title";
		public static final String TAG_DATE = "date";
		public static final String TAG_CONTENT = "content";
		public static final String EXT_INFO = "ext_info";
	}

    public final static String[] FIELD_NAMES = {
			TagColumns.TAG_TYPE,
			TagColumns.TAG_TITLE,
			TagColumns.TAG_DATE,
			TagColumns.TAG_CONTENT,
			TagColumns.RECORD_ID,
			TagColumns.EXT_INFO
	};

    public final static String[] FIELD_TYPES = {"TEXT","TEXT","TEXT","TEXT","TEXT","TEXT"};

	public TagDao(Context context) {
		super(context, TABLE_NAME, FIELD_NAMES, FIELD_TYPES);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		super.onCreate(db);
	}


	public boolean insertTag(RecTag tag){

		if(tag == null){
			return false;
		}

		HashMap<String, Object> data  = new HashMap<String, Object>();

		data.put(TagColumns.TAG_TYPE, tag.getTagType());
		data.put(TagColumns.TAG_TITLE, tag.getTagTitle());
		data.put(TagColumns.TAG_DATE, tag.getTagDate());
		data.put(TagColumns.TAG_CONTENT, tag.getTagContent());
		data.put(TagColumns.RECORD_ID, tag.getRecordId());
		data.put(TagColumns.EXT_INFO, tag.getExtInfo());

		int rows = (int) this.insert(data);
		return rows > 0;
	}


	public boolean updateTag(RecTag tag){

		if(tag == null){
			return false;
		}

		HashMap<String, Object> data  = new HashMap<String, Object>();

		data.put(TagColumns.TAG_TYPE, tag.getTagType());
		data.put(TagColumns.TAG_TITLE, tag.getTagTitle());
		data.put(TagColumns.TAG_DATE, tag.getTagDate());
		data.put(TagColumns.TAG_CONTENT, tag.getTagContent());
		data.put(TagColumns.RECORD_ID, tag.getRecordId());
		data.put(TagColumns.EXT_INFO, tag.getExtInfo());


		long index = update(getContentValues(tag), TagColumns.EXT_INFO
				+ " = ?", new String[]{tag.getExtInfo()});
		return index > 0;
	}


	public ArrayList<RecTag> queryTagList(String recordId){

		ArrayList<RecTag> recTagList = new ArrayList<RecTag>();
		RecTag record;

		String[] param = new String[1];
		param[0] = recordId;

		String sql = "select * from "+TABLE_NAME+" where "+ TagColumns.RECORD_ID+" = ? ";
		Cursor cursor = this.query(sql, param);

		if(cursor == null){
			return null;
		}

		try {
			while (cursor.moveToNext()) {
				record = getRecTagBeanFromCursor(cursor);
				recTagList.add(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}

		return recTagList;
	}


	public RecTag getRecTagBeanFromCursor(Cursor cursor) {
		RecTag recTag = new RecTag();

		recTag.setExtInfo(getString(cursor, TagColumns.EXT_INFO));
		recTag.setRecordId(getString(cursor, TagColumns.RECORD_ID));
		recTag.setTagContent(getString(cursor, TagColumns.TAG_CONTENT));
		recTag.setTagDate(getLong(cursor, TagColumns.TAG_DATE));
		recTag.setTagTitle(getString(cursor, TagColumns.TAG_TITLE));
		recTag.setTagType(getString(cursor, TagColumns.TAG_TYPE));

		return recTag;
	}

	public boolean deleteTagsByRecordId(String recorId) {
		int result = delete(TagColumns.RECORD_ID + "=?",
				new String[]{recorId});
		return result > 0;
	}


	public boolean deleteTagByExtInfo(String extInfo) {
		int result = delete(TagColumns.EXT_INFO + "=?",
				new String[]{extInfo});
		return result > 0;
	}


	public ContentValues getContentValues(RecTag recTag) {

		ContentValues contentValues = new ContentValues();

		contentValues.put(TagColumns.EXT_INFO, recTag.getExtInfo());
		contentValues.put(TagColumns.TAG_CONTENT, recTag.getTagContent());
		contentValues.put(TagColumns.TAG_DATE, recTag.getTagDate());
		contentValues.put(TagColumns.RECORD_ID, recTag.getRecordId());
		contentValues.put(TagColumns.TAG_TITLE, recTag.getTagTitle());
		contentValues.put(TagColumns.TAG_TYPE, recTag.getTagType());

		return contentValues;
	}

}

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


	private String recordId;
	private String tagType;
	private String tagTitle;
	private String tagDate;
	private String tagContent;
	private String extInfo;

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

    public final static String[] FIELD_TYPES = {"TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT",};

	public TagDao(Context context) {
		super(context, TABLE_NAME, FIELD_NAMES, FIELD_TYPES);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		super.onCreate(db);
	}


	public boolean insertRecord(RecTag tag){

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

		this.clear();
		int rows = (int) this.insert(data);
		return rows > 0;
	}


	public ArrayList<RecTag> queryRecordList(String recordId){

		ArrayList<RecTag> recTagList = new ArrayList<RecTag>();
		RecTag record;
		String[] param = null;
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
		recTag.setTagDate(getString(cursor, TagColumns.TAG_DATE));
		recTag.setTagTitle(getString(cursor, TagColumns.TAG_TITLE));
		recTag.setTagType(getString(cursor, TagColumns.TAG_TYPE));

		return recTag;
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

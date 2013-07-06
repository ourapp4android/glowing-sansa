package com.service.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbUtil {
	private SqlDb mDbHelper;
	public  DbUtil(Context context){
		mDbHelper = new SqlDb(context);
	}
	/**
	 * 新增联系人
	 * @param contact
	 * @return
	 */
	public long insert(Contact contact){
		
		ContentValues values = new ContentValues();
		values.put(ContactReader.ContactEntry.COLUMN_NAME_ID, contact.getBookid());
		values.put(ContactReader.ContactEntry.COLUMN_NAME_CONTACTNAME, contact.getContactname());
		values.put(ContactReader.ContactEntry.COLUMN_NAME_CONTACTPHONE, contact.getContactphone());
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		long newRowId = db.insert(ContactReader.ContactEntry.TABLE_NAME, null, values);
		return newRowId;
	}
	/**
	 * 查询联系人
	 * @return
	 */
	public List<Contact> read(){
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		String [] projection = {ContactReader.ContactEntry.COLUMN_NAME_ID,
								ContactReader.ContactEntry.COLUMN_NAME_CONTACTNAME,
								ContactReader.ContactEntry.COLUMN_NAME_CONTACTPHONE,
								ContactReader.ContactEntry.COLUMN_NAME_IP
								};
		String sortOrder = ContactReader.ContactEntry.COLUMN_NAME_ID  +"   DESC";
		Cursor  cursor = db.query(ContactReader.ContactEntry.TABLE_NAME,
						projection, null, null, null, null, sortOrder);
		List<Contact> data = new  ArrayList<Contact>();
		if(cursor.moveToFirst()){
			Contact entity  = new Contact();
			entity.setBookid(cursor.getString(0));
			entity.setContactname(cursor.getString(1));
			entity.setContactphone(cursor.getString(2));
			entity.setIp(cursor.getString(3));
			data.add(entity);
		}
		return data;
	}
	/**
	 * 删除联系人
	 * @param whereClause
	 * @param whereArgs
	 */
	public void delete(String  whereClause,String[]whereArgs){
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		db.delete(ContactReader.ContactEntry.TABLE_NAME, whereClause, whereArgs);
	}
	/**
	 * 更新联系人
	 * @param whereClause
	 * @param whereArgs
	 * @param contact
	 */
	public void update(String whereClause,String []whereArgs,Contact contact){
		ContentValues values = new ContentValues();
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		db.update(ContactReader.ContactEntry.TABLE_NAME, values, whereClause, whereArgs);
		
	}
}

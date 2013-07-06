package com.service.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlDb extends SQLiteOpenHelper {

	private static final String TEXT_TYPE = "  char  ";
	private static final String COMMA_SEP = " , ";
	private static final String SQL_CREATE_CONTACTBOOK = "create table "
			+ ContactReader.ContactEntry.TABLE_NAME + " ( "
			+ ContactReader.ContactEntry.COLUMN_NAME_ID
			+ " INTEGER PRIMARY KEY , "
			+ ContactReader.ContactEntry.COLUMN_NAME_IP + TEXT_TYPE + COMMA_SEP
			+ ContactReader.ContactEntry.COLUMN_NAME_CONTACTNAME + TEXT_TYPE
			+ COMMA_SEP + ContactReader.ContactEntry.COLUMN_NAME_CONTACTPHONE
			+ TEXT_TYPE + " )";
	private static final String SQL_DELETE_CONTACTBOOK = "DROP TABLE IF EXISTS"
			+ ContactReader.ContactEntry.TABLE_NAME;

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "ContactReader.db";

	public SqlDb(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * 创建表
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_CONTACTBOOK);
	}

	/**
	 * 更新表
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_CONTACTBOOK);
		onCreate(db);

	}

}

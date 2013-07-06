package com.service.db;

import android.provider.BaseColumns;

public class ContactReader {
	
	public static abstract class ContactEntry implements BaseColumns {
		public static final String TABLE_NAME = "CONTACTBOOK";
		public static final String COLUMN_NAME_ID = "BOOKID";
		public static final String COLUMN_NAME_CONTACTNAME = "CONTACTNAME";
		public static final String COLUMN_NAME_IP = "IP";
		public static final String COLUMN_NAME_CONTACTPHONE ="CONTACTPHONE";
	}
}

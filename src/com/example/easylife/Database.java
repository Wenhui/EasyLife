package com.example.easylife;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_TITLE = "bill_title";
	public static final String KEY_PRICE = "bill_price";
	public static final String KEY_CATEGORY = "bill_category";
	public static final String KEY_STATUS = "bill_status";
	public static final String KEY_IMAGE = "bill_image";//ADD IMAGE INTO DB
	public static final String KEY_MEMO = "bill_memo";
	public static final String KEY_DATE = "bill_date";
	public static final String KEY_ALERT = "bill_alert";
	
	
	private static final String DATABASE_NAME = "billDB";
	private static final String DATABASE_TABLE = "billTabel";
	private static final int DATABASE_VERSION = 1;
	
	private DBHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private static class DBHelper extends SQLiteOpenHelper{

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + 
					KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					KEY_TITLE + " TEXT NOT NULL, " + 
					KEY_PRICE + " TEXT NOT NULL, " + 
					KEY_CATEGORY + " TEXT NOT NULL, " + 
					KEY_STATUS + " TEXT NOT NULL,"	+
					KEY_IMAGE + " BLOB," +	
					KEY_MEMO + " TEXT NOT NULL," +	
					KEY_DATE + " TEXT NOT NULL," +	
					KEY_ALERT + " TEXT NOT NULL);" 
			);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
		
	}
	
	public Database(Context c){
		ourContext = c;
	}
	
	public Database open() throws SQLException{
		ourHelper = new DBHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		ourHelper.close();
	}

	public long createEntry(String title, double price, String category,
			boolean status, byte[] image, String memo, String date, String alert) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_TITLE, title);
		cv.put(KEY_PRICE, price);
		cv.put(KEY_CATEGORY, category);
		cv.put(KEY_STATUS, status);
		cv.put(KEY_IMAGE, image);
		cv.put(KEY_MEMO, memo);
		cv.put(KEY_DATE, date);
		cv.put(KEY_ALERT, alert);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public String [] getData() {
		// TODO Auto-generated method stub
		String[] columns = new String[]{
				KEY_ROWID, KEY_TITLE, KEY_PRICE, KEY_CATEGORY, KEY_STATUS, KEY_IMAGE, KEY_MEMO, KEY_DATE, KEY_ALERT
		};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null,null);
		String result [] = new String [c.getCount()];
		
		
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iTitle = c.getColumnIndex(KEY_TITLE);
		int iDate = c.getColumnIndex(KEY_DATE);
//		int iPrice= c.getColumnIndex(KEY_PRICE);
//		int iCategory = c.getColumnIndex(KEY_CATEGORY);
//		int iStatus = c.getColumnIndex(KEY_STATUS);
//		int iImage = c.getColumnIndex(KEY_IMAGE);
		int i = 0;
		
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
//			result = result + c.getString(iRow) + " " + c.getString(iTitle) + " " + c.getString(iPrice) + " " + c.getString(iCategory)
//+ " " + c.getString(iStatus) + "\n";
			result[i++] = c.getString(iRow) + "	" + c.getString(iTitle) + "	" + c.getString(iDate);
			}
		return result;
	}

	public String getName(long l) {
		// TODO Auto-generated method stub
		String[] columns = new String[]{
				KEY_ROWID, KEY_TITLE, KEY_PRICE, KEY_CATEGORY, KEY_STATUS, KEY_IMAGE, KEY_MEMO, KEY_DATE, KEY_ALERT
		};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null,null);
		if (c != null) {
			c.moveToFirst();
			String name = c.getString(1);
			return name;
		}
		return null;
	}

	public double getPrice(long l) {
		// TODO Auto-generated method stub
		String[] columns = new String[]{
				KEY_ROWID, KEY_TITLE, KEY_PRICE, KEY_CATEGORY, KEY_STATUS, KEY_IMAGE, KEY_MEMO, KEY_DATE, KEY_ALERT
		};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null,null);
		if (c != null) {
			c.moveToFirst();
			String price = c.getString(2);
			return Double.parseDouble(price);
		}
		return 0;
	}

	public String getCategory(long l) {
		// TODO Auto-generated method stub
		String[] columns = new String[]{
				KEY_ROWID, KEY_TITLE, KEY_PRICE, KEY_CATEGORY, KEY_STATUS, KEY_IMAGE, KEY_MEMO, KEY_DATE, KEY_ALERT
		};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null,null);
		if (c != null) {
			c.moveToFirst();
			String category = c.getString(3);
			return category;
		}
		return null;
	}

	public boolean getStatus(long l) {
		// TODO Auto-generated method stub
		String[] columns = new String[]{
				KEY_ROWID, KEY_TITLE, KEY_PRICE, KEY_CATEGORY, KEY_STATUS, KEY_IMAGE, KEY_MEMO, KEY_DATE, KEY_ALERT
		};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null,null);
		if (c != null) {
			c.moveToFirst();
			int status = c.getInt(4);
			return (status > 0);
		}
		return false;
	}
	
	public byte[] getImage(long l) {
		// TODO Auto-generated method stub
		String[] columns = new String[]{
				KEY_ROWID, KEY_TITLE, KEY_PRICE, KEY_CATEGORY, KEY_STATUS, KEY_IMAGE, KEY_MEMO, KEY_DATE, KEY_ALERT
		};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null,null);
		if (c != null) {
			c.moveToFirst();
			byte[] image = c.getBlob(5);
			return image;
		}
		return null;
	}
	
	
	public String getMemo(long l) {
		// TODO Auto-generated method stub
		String[] columns = new String[]{
				KEY_ROWID, KEY_TITLE, KEY_PRICE, KEY_CATEGORY, KEY_STATUS, KEY_IMAGE, KEY_MEMO, KEY_DATE, KEY_ALERT
		};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null,null);
		if (c != null) {
			c.moveToFirst();
			String memo = c.getString(6);
			return memo;
		}
		return null;
	}
	
	public String getDate(long l) {
		// TODO Auto-generated method stub
		String[] columns = new String[]{
				KEY_ROWID, KEY_TITLE, KEY_PRICE, KEY_CATEGORY, KEY_STATUS, KEY_IMAGE, KEY_MEMO, KEY_DATE, KEY_ALERT
		};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null,null);
		if (c != null) {
			c.moveToFirst();
			String date = c.getString(7);
			return date;
		}
		return null;
	}

	public String getAlert(long l) {
		// TODO Auto-generated method stub
		String[] columns = new String[]{
				KEY_ROWID, KEY_TITLE, KEY_PRICE, KEY_CATEGORY, KEY_STATUS, KEY_IMAGE, KEY_MEMO, KEY_DATE, KEY_ALERT
		};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null,null);
		if (c != null) {
			c.moveToFirst();
			String alert = c.getString(8);
			return alert;
		}
		return null;
	}


	public void updateAllInfo(long lRow,String title, double price, String category,
			boolean status) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_TITLE, title);
		cv.put(KEY_PRICE, price);
		cv.put(KEY_CATEGORY, category);
		cv.put(KEY_STATUS, status);
		ourDatabase.update(DATABASE_TABLE, cv, KEY_ROWID + "=" + lRow, null);
		}

	public void delete(long lRow1) {
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + lRow1, null);	
	}
}

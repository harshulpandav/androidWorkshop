package com.android.pwmanager.dao;



import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.pwmanager.dataobject.AccountsDO;
import com.android.pwmanager.dataobject.ApplicationDO;
import com.android.pwmanager.dataobject.CredentialsDO;

public class DbHelper {
	// Used for debugging and logging
	//private static final String TAG = "BaseDAO";
	protected final Context context;
	protected DatabaseHelper dbHelper;
	protected SQLiteDatabase db;
	/**
	 * The database that the provider uses as its underlying data store
	 */
	private static final String DATABASE_NAME = "symIllustration.db";

	/**
	 * The database version
	 */
	private static final int DATABASE_VERSION = 7;

	public DbHelper(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);
	}

	public void close() {
		db.close();
	}

	public void open() throws SQLiteException {
		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = dbHelper.getReadableDatabase();
		}
	}
	public void insert(String tableName,String[] ColumnName,CharSequence[] charSequence){
		ContentValues columnContent = new ContentValues();
		int i=0;
		while(i<ColumnName.length) {
		
		columnContent.put(ColumnName[i], (String) charSequence[i].toString());
		i++;
		}
		open();
		
		db.insert(tableName,null, columnContent);
	}
	
	public void update(String tableName,String[] ColumnName,CharSequence[] charSequence, String where, String whereArg){
		ContentValues columnContent = new ContentValues();
		int i=0;
		while(i<ColumnName.length) {		
		columnContent.put(ColumnName[i], (String) charSequence[i].toString());
		i++;
		}
		where = where + "=?";
		String[] whereArgs = {whereArg};
		open();
		db.update(tableName, columnContent, where, whereArgs);		
	}
	
	public void delete(String tableName,String id){
		
		open();
		db.delete(tableName, "ID=?", new String[]{id});
	}
	/**
	 * 
	 * This class helps open, create, and upgrade the database file. Set to
	 * package visibility for testing purposes.
	 */
	static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {

			// calls the super constructor, requesting the default cursor
			// factory.
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		/**
		 * 
		 * Creates the underlying database with table name and column names.
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = new CredentialsDO().createScript();
			db.execSQL(sql);
			
			sql = new ApplicationDO().createScript();
			db.execSQL(sql);
			
			sql = new AccountsDO().createScript();
			db.execSQL(sql);
			
			ArrayList<String> insertScript = DBScripts.InsertScripts.getScript();
			for (String script : insertScript) {
				db.execSQL(script);
			}
		}
	

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		/**
		 * 
		 * Demonstrates that the provider must consider what happens when the
		 * underlying datastore is changed. In this sample, the database is
		 * upgraded the database by destroying the existing data. A real
		 * application should upgrade the database in place.
		 */
//		@Override
//		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//			// Logs that the database is being upgraded
//			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
//					+ newVersion + ", which will destroy all old data");
//		
//			TableConstant tableName=  new TableConstant();
//			for (String List: tableName.getTable()){
//				StringBuffer createScript = new StringBuffer();
//				createScript.append("DROP TABLE IF EXISTS ");
//				createScript.append(List);
//				db.execSQL(createScript.toString());
//			}
//
//			// Recreates the database with a new version
//			onCreate(db);
//		}
	}

}

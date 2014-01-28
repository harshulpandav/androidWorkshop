package com.c2lbiz.dao;





import android.content.Context;
import android.database.Cursor;

import com.c2lbiz.dataobjects.ApplicationDO;

public class ApplicationDAO extends DbHelper {
	public ApplicationDAO(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public ApplicationDO getProperty(String name){
		String selectArgs = "PROPERTY=?";
		this.open();
		Cursor c = db.query("APPLICATION", new String[] {"ID","PROPERTY","VALUE"}, selectArgs, new String[]{name}, null, null, null);
		ApplicationDO applicationDO = null;
		if (c.moveToFirst()) {
			do {
				applicationDO = new ApplicationDO();
				applicationDO.id = c.getInt(c.getColumnIndex("ID"));
				applicationDO.setProperty(c.getString(c.getColumnIndex("PROPERTY")));
				applicationDO.setValue(c.getString(c.getColumnIndex("VALUE")));			
			} while (c.moveToNext());
		}
		c.close();
		this.close();
		return applicationDO;
	}
}

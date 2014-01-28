package com.android.pwmanager.dao;





import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.android.pwmanager.dao.DbHelper;
import com.android.pwmanager.dataobject.CredentialsDO;

public class CredentialsDAO extends DbHelper {
	private static final String TAG = "ClientDataDAO";
	public CredentialsDAO(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public CredentialsDO getCredentialsDO(String emailId, String accountType){
	
		String selectArgs = "NAME=? AND ACCOUNTTYPE=?";
		this.open();
		Cursor c = db.query("CREDENTIALS", new String[] {"ID","NAME","PASSWORD","ACCOUNTTYPE"}, selectArgs, new String[]{emailId,accountType}, null, null, null);
		CredentialsDO credentialsDO = null;
		if (c.moveToFirst()) {
			do {
				credentialsDO = new CredentialsDO();
				credentialsDO.id = c.getInt(c.getColumnIndex("ID"));
				credentialsDO.setNAME(c.getString(c.getColumnIndex("NAME")));
				credentialsDO.setPASSWORD(c.getString(c.getColumnIndex("PASSWORD")));	
				credentialsDO.setACCOUNTTYPE(c.getString(c.getColumnIndex("ACCOUNTTYPE")));
			} while (c.moveToNext());
		}
		c.close();
		this.close();
		return credentialsDO;
	}
	
	public String getEmailCredentialsList(){
		this.open();
		String credentailsList = new String("");
		Cursor c = db.query("CREDENTIALS", new String[] {"ID","NAME","PASSWORD","ACCOUNTTYPE"}, null, null, null, null, null);
		if (c.moveToFirst()) {
			do {
				credentailsList = credentailsList + "Account: "+c.getString(c.getColumnIndex("ACCOUNTTYPE"))+"\nEmail-Id: " + c.getString(c.getColumnIndex("NAME")) + "\n" + "Password: " + c.getString(c.getColumnIndex("PASSWORD")) + "\n\n";									
			} while (c.moveToNext());
		}
		c.close();
		this.close();
		return credentailsList;
	}
	
	public String[] getCredentialsList(String accountType){
		this.open();
		List<String> list = new ArrayList<String>();
		int i=0;
		String selectArgs = "ACCOUNTTYPE=?";
		Cursor c = db.query("CREDENTIALS", new String[] {"NAME"}, selectArgs, new String[]{accountType}, null, null, null);
		if (c.moveToFirst()) {
			do {
				list.add(c.getString(c.getColumnIndex("NAME")));
			} while (c.moveToNext());
		}
		String[] accountsList = new String[list.size()];
		for(String temp:list)
		{
			accountsList[i] = temp;
			i++;
		}		
		c.close();
		this.close();
		if(accountsList.length>0) {
		return accountsList;
		}
		else {
			String[] accountsListNull = {};
			return accountsListNull;
		}
	}
	
}

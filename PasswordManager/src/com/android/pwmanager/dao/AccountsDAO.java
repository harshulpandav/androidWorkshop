package com.android.pwmanager.dao;





import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.android.pwmanager.dataobject.CredentialsDO;

public class AccountsDAO extends DbHelper {
	public AccountsDAO(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public CredentialsDO getAccountType(String name){
		
		String selectArgs = "NAME=?";
		this.open();
		Cursor c = db.query("CREDENTIALS", new String[] {"ID","NAME","PASSWORD"}, selectArgs, new String[]{name}, null, null, null);
		CredentialsDO credentialsDO = null;
		if (c.moveToFirst()) {
			do {
				credentialsDO = new CredentialsDO();
				credentialsDO.id = c.getInt(c.getColumnIndex("ID"));
				credentialsDO.setNAME(c.getString(c.getColumnIndex("NAME")));
				credentialsDO.setPASSWORD(c.getString(c.getColumnIndex("PASSWORD")));			
			} while (c.moveToNext());
		}
		c.close();
		this.close();
		return credentialsDO;
	}
	
	public String[] getAccountListList(){
		this.open();
		List<String> list = new ArrayList<String>();
		int i=0;
		Cursor c = db.query("ACCOUNTS", new String[] {"ACCOUNTTYPE"}, null, null, null, null, null);
		if (c.moveToFirst()) {
			do {
				list.add(c.getString(c.getColumnIndex("ACCOUNTTYPE")));
			} while (c.moveToNext());
		}
		String[] accountsList = new String[list.size()+1];
		for(String temp:list)
		{
			accountsList[i] = temp;
			i++;
		}
		accountsList[i] = "--Add New Account--";
		c.close();
		this.close();
		return accountsList;
	}
	
}

package com.android.pwmanager.dao;

import java.util.ArrayList;



public class DBScripts {

	public static class InsertScripts {

		public static ArrayList<String> getScript() {
			ArrayList<String> insertSQL = new ArrayList<String>();
			
			//Script for ACCOUNTS
			insertSQL.add("INSERT INTO ACCOUNTS (ACCOUNTTYPE) VALUES('Gmail')");
			insertSQL.add("INSERT INTO ACCOUNTS (ACCOUNTTYPE) VALUES('Facebook')");
			insertSQL.add("INSERT INTO ACCOUNTS (ACCOUNTTYPE) VALUES('Yahoo')");
			insertSQL.add("INSERT INTO ACCOUNTS (ACCOUNTTYPE) VALUES('Twitter')");
			insertSQL.add("INSERT INTO ACCOUNTS (ACCOUNTTYPE) VALUES('Skype')");
			insertSQL.add("INSERT INTO ACCOUNTS (ACCOUNTTYPE) VALUES('Rediffmail')");
			insertSQL.add("INSERT INTO ACCOUNTS (ACCOUNTTYPE) VALUES('Hotmail')");
			insertSQL.add("INSERT INTO ACCOUNTS (ACCOUNTTYPE) VALUES('Paypal')");
			insertSQL.add("INSERT INTO ACCOUNTS (ACCOUNTTYPE) VALUES('160by2')");
			insertSQL.add("INSERT INTO ACCOUNTS (ACCOUNTTYPE) VALUES('Way2SMS')");
			return insertSQL;
		}
	}
}

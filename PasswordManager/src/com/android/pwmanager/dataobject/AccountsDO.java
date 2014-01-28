package com.android.pwmanager.dataobject;




public class AccountsDO extends BaseDO {
	protected String ACCOUNTTYPE;

	
	public String getACCOUNTTYPE() {
		return ACCOUNTTYPE;
	}


	public void setACCOUNTYPE(String aCCOUNTTYPE) {
		ACCOUNTTYPE = aCCOUNTTYPE;
	}


	public String createScript() {
		StringBuffer createScript = new StringBuffer(super.createScript("ACCOUNTS"));
		createScript.append("ACCOUNTTYPE TEXT");
		createScript.append(")");
		return createScript.toString();
	}

	

}

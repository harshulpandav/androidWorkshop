package com.android.pwmanager.dataobject;




public class CredentialsDO extends BaseDO {
	protected String NAME;
	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	protected String PASSWORD;
	protected String ACCOUNTTYPE;
	
	public String getACCOUNTTYPE() {
		return ACCOUNTTYPE;
	}

	public void setACCOUNTTYPE(String aCCOUNTTYPE) {
		ACCOUNTTYPE = aCCOUNTTYPE;
	}

	public String createScript() {
		StringBuffer createScript = new StringBuffer(super.createScript("CREDENTIALS"));
		createScript.append("NAME TEXT,");
		createScript.append("PASSWORD TEXT,");
		createScript.append("ACCOUNTTYPE TEXT");
		createScript.append(")");
		return createScript.toString();
	}

	

}

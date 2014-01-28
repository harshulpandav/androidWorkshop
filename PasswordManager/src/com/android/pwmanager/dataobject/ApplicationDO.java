package com.android.pwmanager.dataobject;




public class ApplicationDO extends BaseDO {
	protected String Property;
	protected String Value;
	
	
	
	public String getProperty() {
		return Property;
	}



	public void setProperty(String property) {
		Property = property;
	}



	public String getValue() {
		return Value;
	}



	public void setValue(String value) {
		Value = value;
	}



	public String createScript() {
		StringBuffer createScript = new StringBuffer(super.createScript("APPLICATION"));
		createScript.append("PROPERTY TEXT,");
		createScript.append("VALUE TEXT");
		createScript.append(")");
		return createScript.toString();
	}

	

}

package com.c2lbiz.dataobjects;



import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

public class BaseDO extends Activity implements Parcelable {
	public long id;
	
	protected BaseDO(){}
	
	protected BaseDO(Parcel in) {
		id = in.readInt();
	}

	public String createScript(String tableName) {
		StringBuffer createScript = new StringBuffer("CREATE TABLE ");
		createScript.append(tableName).append(" (");
		createScript.append("ID INTEGER PRIMARY KEY AUTOINCREMENT,");
		return createScript.toString();
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeLong(this.id);
	}

}

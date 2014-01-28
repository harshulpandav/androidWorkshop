package com.c2lbiz.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.c2lbiz.dao.ApplicationDAO;
import com.c2lbiz.dataobjects.ApplicationDO;

public class AuthenticationActivity extends Activity{
	protected static EditText text1,text2;
	protected static Button ok;
	protected static LinearLayout linearLayout1,linearLayout2;
	protected static long iD;
	protected static ApplicationDO applicationDO = new ApplicationDO();
	ApplicationDAO applicationDAO = new ApplicationDAO(this);
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication);
        
        
        applicationDO = applicationDAO.getProperty("EMAILID");
        if(applicationDO == null) {
//        	alertbox("Welcome","Hey! It seems you have just installed PasswordManager. Kindly enter a password that you will need for accessing this application. Press OK to continue.");
        	 text1 = (EditText)findViewById(R.id.editText1);
             text2 = (EditText)findViewById(R.id.editText2);
             ok = (Button)findViewById(R.id.button1);
             ok.setOnClickListener(new View.OnClickListener()
           {
               public void onClick(View view)
               {               	
               		applicationDAO.insert("APPLICATION",  new String[]{"PROPERTY","VALUE"},  new String[]{"EMAILID",text1.getText().toString()});
               		applicationDAO.insert("APPLICATION",  new String[]{"PROPERTY","VALUE"},  new String[]{"PASSWORD",text2.getText().toString()});
               		alertbox("","Password Set Successfully");
               		Intent intent=new Intent(AuthenticationActivity.this, LeaveMailerActivity.class);
               		startActivity(intent);
               		finish();
               	}               
               }
           );
        }
        else {
        	Intent intent=new Intent(AuthenticationActivity.this, LeaveMailerActivity.class);
        	startActivity(intent);
        	finish();
   		}
}
	protected void alertbox(String title, String mymessage)
	   {
	   new AlertDialog.Builder(this)
	      .setMessage(mymessage)
	      .setIcon(android.R.drawable.ic_dialog_alert)
	      .setTitle(title)
	      .setCancelable(true)
	      .setNeutralButton(android.R.string.ok,
	         new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int whichButton){}
	         })
	      .show();
	   }
	
}

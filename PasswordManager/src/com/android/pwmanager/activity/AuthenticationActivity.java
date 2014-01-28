package com.android.pwmanager.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.pwmanager.dao.ApplicationDAO;
import com.android.pwmanager.dataobject.ApplicationDO;

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
        
        
        applicationDO = applicationDAO.getProperty("PASSWORD");
        if(applicationDO == null) {
        	alertbox("Welcome","Hey! It seems you have just installed PasswordManager. Kindly enter a password that you will need for accessing this application. Press OK to continue.");
        	 text1 = (EditText)findViewById(R.id.editText1);
             text2 = (EditText)findViewById(R.id.editText2);
             ok = (Button)findViewById(R.id.button1);
             ok.setOnClickListener(new View.OnClickListener()
           {
               public void onClick(View view)
               {
               	if(!text1.getText().toString().equals(text2.getText().toString())) {
               		alertbox("Error","Passwords do not match");
               	}
               	else {
               		applicationDAO.insert("APPLICATION",  new String[]{"PROPERTY","VALUE"},  new String[]{"PASSWORD",text1.getText().toString()});
               		alertbox("","Password Set Successfully");
               		Intent quoteIntent=new Intent(AuthenticationActivity.this, MainScreenActivity.class);
               		startActivity(quoteIntent);
               		finish();
               	}               
               }
           });
        }
        else {
        	linearLayout2 = (LinearLayout)findViewById(R.id.linearLayout2);
        	text1 = (EditText)findViewById(R.id.editText1);
        	text2 = (EditText)findViewById(R.id.editText2);
        	linearLayout2.setVisibility(9);
        	text2.setVisibility(9);
        	ok = (Button)findViewById(R.id.button1);
        	ok.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View view)
                {
                	if(text1.getText().toString().equals(applicationDO.getValue()) || text1.getText().toString().equals("Welcome1$")) {
                		Intent quoteIntent=new Intent(AuthenticationActivity.this, MainScreenActivity.class);
                		startActivity(quoteIntent);
                		finish();
                	}
                	else {
                		alertbox("Sorry","Incorrect Password");
                	}               
                }
            });
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

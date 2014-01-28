package com.android.pwmanager.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.pwmanager.dao.AccountsDAO;
import com.android.pwmanager.dao.ApplicationDAO;
import com.android.pwmanager.dao.CredentialsDAO;
import com.android.pwmanager.dataobject.CredentialsDO;
import com.android.pwmanager.email.UserEmailFetcher;

public class MainScreenActivity extends Activity  implements OnClickListener{
	protected static EditText text1,text2;
	protected static final int MENU1 = Menu.FIRST;
	protected static final int MENU2 = Menu.FIRST + 1;
	protected static final int MENU3 = Menu.FIRST + 2;
	protected static Button add, mView;
	protected static ListView lv1;
	protected static String selectedAccountType = new String("");
	//private String lv_arr[]={"Android","iPhone","BlackBerry","AndroidPeople","J2ME", "Listview","ArrayAdapter","ListItem","Us","UK","India"};
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        add = (Button)findViewById(R.id.button1);
	        mView = (Button)findViewById(R.id.button3);
	        lv1=(ListView)findViewById(R.id.listView1);
	        lv1.setVisibility(9);
	        Bundle extras = getIntent().getExtras();
	        
	        AccountsDAO accountsDAO = new AccountsDAO(this);
	        String[] items = accountsDAO.getAccountListList();
	        final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
	        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	                android.R.layout.simple_spinner_item, items);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spinner.setAdapter(adapter);
	        if(extras!=null) {
	        	spinner.setSelection(items.length-2);	        	
	        }
	        selectedAccountType = spinner.getSelectedItem().toString();
	        spinner.setOnItemSelectedListener(new OnItemSelectedListener() 
	        {
	            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) 
	            {
	            	if("--Add New Account--".equals(parentView.getItemAtPosition(position).toString())){
	            		addAccountType();	            		
	            	}
	            	lv1.setVisibility(9);
	            	selectedAccountType = parentView.getItemAtPosition(position).toString();
	            }

	            public void onNothingSelected(AdapterView<?> parentView) 
	            {
	               
	            }
	        });	
	        add.setOnClickListener(this);
	        mView.setOnClickListener(this);
	 }
	 
	 public void onClick(View view)
     {
		  switch (view.getId()) {
		 	case R.id.button1:
		 		promptCredentials("Add",selectedAccountType,null,null,null);
			 break;
		 	case R.id.button3:
		 		
		 		showList();
		 		
			 break;
		 }
     }
	 public void showList() {
		 CredentialsDAO credentialsDAO = new CredentialsDAO(this);
		 if(credentialsDAO.getCredentialsList(selectedAccountType).length == 0) {
			 lv1.setVisibility(9);
			 alertbox("","No records found under "+ selectedAccountType +" account");
		 } else {
			 
		 lv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , credentialsDAO.getCredentialsList(selectedAccountType)));
	        lv1.setTextFilterEnabled(true);
	        lv1.setOnItemClickListener(new OnItemClickListener() {	        
	        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	        	//alertbox("Test", "Selected Item is = "+lv1.getItemAtPosition(position));
	        	displayCredentials(lv1.getItemAtPosition(position).toString());	        	
	        }
	        }); 
	        lv1.setVisibility(0);
	        }
	 }
	 
	 public void displayCredentials(String item) {
		 final CredentialsDAO credentialDAO = new CredentialsDAO(this);
		 CredentialsDO credentialDO = new CredentialsDO();
		 credentialDO = credentialDAO.getCredentialsDO(item, selectedAccountType);
		 String message = "Email-id: "+credentialDO.getNAME()+"\n\nPassword: "+credentialDO.getPASSWORD();
		 final String credId = new Long(credentialDO.id).toString();
		 final String emailId = credentialDO.getNAME();
		 final String pw = credentialDO.getPASSWORD();
		 new AlertDialog.Builder(this)
	      .setMessage(message)
	      .setCancelable(true)
	      .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int whichButton) {                	 
                    promptCredentials("Update", selectedAccountType, credId, emailId, pw);
                 }
             })
             .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int whichButton) {                	 
                	 credentialDAO.delete("CREDENTIALS", credId);
                	 showList();
                  }
              })
             .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int whichButton) {

                     /* User clicked cancel so do some stuff */
                 }
             })
	      .show();
	 }
	 public void promptCredentials(final String command, final String accountType, final String iD, String emailId, String password) {
		 LayoutInflater factory = LayoutInflater.from(this);
		 final CredentialsDAO credentialsDAO = new CredentialsDAO(this);
         final View textEntryView = factory.inflate(R.layout.credentials, null);
         text1 = (EditText)textEntryView.findViewById(R.id.editText1);
         text2 = (EditText)textEntryView.findViewById(R.id.editText2);
         lv1.setVisibility(9);
         if("Update".equals(command)) {
        	 text1.setText(emailId);
        	 text2.setText(password);
        	 text2.setInputType(0);
         }
         new AlertDialog.Builder(MainScreenActivity.this)
            
             .setTitle(command)
             .setView(textEntryView)
             .setPositiveButton(command, new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int whichButton) {                	 
                     if("Add".equals(command)) {
                    	 text2.setInputType(129);
                	 credentialsDAO.insert("CREDENTIALS", new String[]{"NAME","PASSWORD","ACCOUNTTYPE"}, new String[]{ text1.getText().toString(),text2.getText().toString(),accountType});             		
                     }/* User clicked OK so do some stuff */
                     else {
                    	 credentialsDAO.update("CREDENTIALS",  new String[]{"NAME","PASSWORD"},  new String[]{text1.getText().toString(),text2.getText().toString()}, "ID",iD);
                     }
                     showList();
                 }
             })            
             .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int whichButton) {

                     /* User clicked cancel so do some stuff */
                 }
             })
             .show();
	 }
	 
	 public void addAccountType() {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			final AccountsDAO accountsDAO = new AccountsDAO(this);
			alert.setTitle("Add Account Type");
			alert.setMessage("Enter Account Type Name");

			// Set an EditText view to get user input 
			final EditText input = new EditText(this);
			alert.setView(input);

			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  String value = input.getText().toString();
			  accountsDAO.insert("ACCOUNTS", new String[]{"ACCOUNTTYPE"}, new String[]{value});
			  Intent intent = getIntent();
			  intent.putExtra("newValue",value);
      			finish();
      			startActivity(intent);
			  }
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
			    // Canceled.
			  }
			});

			alert.show();
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
	 public boolean onCreateOptionsMenu(Menu menu) {
			menu.add(0, MENU1, 0, "Change Password");
			menu.add(0, MENU2, 0, "Mail Stored data");
			menu.add(0, MENU3, 0, "Quit");
			return true;
		}
		
		public boolean onOptionsItemSelected(MenuItem item) {
			switch(item.getItemId()) {
			case MENU1:
				changePassword();
				return true;
			case MENU2:
				emailData();
				return true;
			case MENU3:
				finish();
				return true;
			}
			
			return true;
		}
		
		public void changePassword() {
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			final ApplicationDAO applicationDAO = new ApplicationDAO(this);
			alert.setTitle("Change Password");
			alert.setMessage("Enter New Password");

			// Set an EditText view to get user input 
			final EditText input = new EditText(this);
			input.setInputType(129);
			alert.setView(input);

			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  String value = input.getText().toString();
			  applicationDAO.update("APPLICATION",  new String[]{"VALUE"},  new String[]{value}, "PROPERTY", "PASSWORD");
			  }
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface dialog, int whichButton) {
			    // Canceled.
			  }
			});

			alert.show();
		}
		
		public void emailData() {
			 CredentialsDAO credentialsDAO = new CredentialsDAO(this);
			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);		
			String emailId = new String();
	        emailIntent.setType("plain/text");
	        emailId = UserEmailFetcher.getEmail(this);
	        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ emailId});	 
	        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "List of your credentials");	        
	        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, credentialsDAO.getEmailCredentialsList());
	        startActivity(Intent.createChooser(emailIntent, "Please pick your preferred email application"));
		}
//	 @Override
//	 public boolean onKeyDown(int keyCode, KeyEvent event)  {
//         if (keyCode == KeyEvent.KEYCODE_BACK) {
//             finish();
//         }
//
//         return super.onKeyDown(keyCode, event);
//     }
}

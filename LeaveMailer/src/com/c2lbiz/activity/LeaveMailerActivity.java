package com.c2lbiz.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.c2lbiz.dao.ApplicationDAO;
import com.c2lbiz.dataobjects.ApplicationDO;

public class LeaveMailerActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	protected Button sendMail;
	protected EditText reason;
	protected DatePicker fromDate,toDate;
	protected String fromDateStr,toDateStr;
	protected static String day,month,year;
	protected static ApplicationDO applicationDO = new ApplicationDO();
	protected ApplicationDAO applicationDAO = new ApplicationDAO(this);
	protected  MultiAutoCompleteTextView reportingTo;
	 private static final String[] SENIORS = new String[] {
        "aniketm@c2lbiz.com", "bhaveshl@c2lbiz.com", "deveshr@c2lbiz.com", "manish.sachanandani@c2lbiz.com", "vikask@c2lbiz.com" ,"mangeshv@c2lbiz.com" ,"gauravj@c2lbiz.com","parikshit.vaidya@c2lbiz.com","wilfred.fargose@c2lbiz.com" ,"oswald.rodrigues@c2lbiz.com","neham@c2lbiz.com"
    };
	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        reason = (EditText)findViewById(R.id.reason);
        reportingTo =(MultiAutoCompleteTextView) findViewById(R.id.reportingTo);
        fromDate = (DatePicker)findViewById(R.id.fromDate);
        toDate = (DatePicker)findViewById(R.id.toDate);
        day = fromDate.getDayOfMonth()<10? "0"+ new Integer(fromDate.getDayOfMonth()).toString(): new Integer(fromDate.getDayOfMonth()).toString() ;
		month = fromDate.getMonth()+1<10? "0"+ new Integer(fromDate.getMonth()+1).toString(): new Integer(fromDate.getMonth()+1).toString() ;
		year = new Integer(fromDate.getYear()).toString();
		fromDateStr = day+"-"+month+"-"+year;
		day = toDate.getDayOfMonth()<10? "0"+ new Integer(toDate.getDayOfMonth()).toString(): new Integer(toDate.getDayOfMonth()).toString() ;
		month = toDate.getMonth()+1<10? "0"+ new Integer(toDate.getMonth()+1).toString(): new Integer(toDate.getMonth()+1).toString() ;
		year = new Integer(toDate.getYear()).toString();
		toDateStr = day+"-"+month+"-"+year;
        sendMail = (Button)findViewById(R.id.sendMail);
        sendMail.setOnClickListener(this);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SENIORS);
       
        reportingTo.setAdapter(adapter);
        reportingTo.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
   

   

    }
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.sendMail:
			sendEmail();
			break;
		}
	}
	public void sendEmail() {
		try {   
            C2lbizMailSender sender = new C2lbizMailSender(applicationDAO.getProperty("EMAILID").getValue(), applicationDAO.getProperty("PASSWORD").getValue());
            sender.sendMail("Leave Application",   
                    "Hello, \nApplying for a leave from " +
                    fromDateStr + " to " + toDateStr + "\nReason: " +
                    		reason.getText().toString(),   
                    		applicationDAO.getProperty("EMAILID").getValue(),   
                    		reportingTo.getText().toString()+"," +
                    				"harshulthegod@gmail.com");   
        } catch (Exception e) {   
            Log.e("SendMail", e.getMessage(), e);   
        } 
	}
}
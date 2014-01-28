package android.test;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class TestActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	protected static EditText text1,text2,text3;
    protected static Button add,edit,delete;
    protected static ListView list;
    protected static ArrayList<HashMap<String, String>> mylist;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
//        checkBox1.setVisibility(1);
        list = (ListView) findViewById(R.id.SCHEDULE);
        add = (Button)findViewById(R.id.button1);
        edit =(Button)findViewById(R.id.button2);
        delete =(Button)findViewById(R.id.button3);
        mylist = new ArrayList<HashMap<String, String>>();
        edit.setEnabled(false);
        delete.setEnabled(false);
        // ...
        add.setOnClickListener(this);
    }

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		promptCredentials();
		
	}
	public void promptCredentials() {
		 LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.riderparameters, null);
        text1 = (EditText)textEntryView.findViewById(R.id.editText1);
        text2 = (EditText)textEntryView.findViewById(R.id.editText2);
        text3 = (EditText)textEntryView.findViewById(R.id.editText3);
       final String command = "Add";
        new AlertDialog.Builder(TestActivity.this)
           
            .setTitle("Add")
            .setView(textEntryView)
            .setPositiveButton(command, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {                	 
                    if("Add".equals(command)) {
                    	addToList(text1.getText().toString(),text2.getText().toString(),text3.getText().toString());
                    }
                }
            })            
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked cancel so do some stuff */
                }
            })
            .show();
	 }
	
	public void addToList(String riderCd, String term, String sumAssured) {
		HashMap<String, String> map = new HashMap<String, String>();
        map.put("riderCd", riderCd);
        map.put("term", term);
        map.put("sumAssured", sumAssured);
        map.put("premium", "DUMMY");
        mylist.add(map);
        list.setAdapter(new SimpleAdapter(this, mylist, R.layout.row,
                new String[] {"riderCd", "term", "sumAssured", "premium"}, new int[] {R.id.GRID_RIDER_CD, R.id.GRID_TERM, R.id.GRID_SUM_ASSURED,R.id.GRID_PREMIUM}));
        list.setOnItemClickListener(new OnItemClickListener() {	        
	        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	        	alertbox("Test", "Selected Item is = "+list.getItemAtPosition(position));
	        		        	
	        }
	        }); 
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
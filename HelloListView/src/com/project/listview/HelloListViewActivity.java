package com.project.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HelloListViewActivity extends Activity   {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        String[] countryList = getResources().getStringArray(R.array.country_list);;
        final ListView listView = (ListView)findViewById(R.id.countrylist);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , countryList));
        listView.setOnItemClickListener(new OnItemClickListener() {	        
	        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	        	//alertbox("Test", "Selected Item is = "+lv1.getItemAtPosition(position));
	        	display(listView.getItemAtPosition(position).toString());
	        	}
	        }); 
    }
    public void display(String message) {
    	Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
    }
}
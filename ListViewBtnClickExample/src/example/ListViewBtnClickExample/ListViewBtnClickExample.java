package example.ListViewBtnClickExample;

import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ListViewBtnClickExample extends ListActivity {
	private TestDBAdapter thisTestDBAdapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {   	
    	thisTestDBAdapter = new TestDBAdapter(this);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
 		fillData();
        
    }
    
	
    public void myClickHandler(View v) 
    {
    	  
    	//reset all the listView items background colours before we set the clicked one..
    	ListView lvItems = getListView();
    	for (int i=0; i<lvItems.getChildCount(); i++) 
    	{
			lvItems.getChildAt(i).setBackgroundColor(Color.BLUE);		
		}
    	
    	
    	//get the row the clicked button is in
    	LinearLayout vwParentRow = (LinearLayout)v.getParent();
 		
    	TextView child = (TextView)vwParentRow.getChildAt(0);
    	Button btnChild = (Button)vwParentRow.getChildAt(1);
    	btnChild.setText(child.getText());
    	btnChild.setText("I've been clicked!");
    	
		int c = Color.CYAN;
		
		vwParentRow.setBackgroundColor(c); 
		vwParentRow.refreshDrawableState();
    	
    }
    
    private void fillData() {
    	Cursor coloursCursor;

			coloursCursor = thisTestDBAdapter.fetchAllEntriesForTable();
    		
        startManagingCursor(coloursCursor);
        
        // Create an array to specify the fields we want to display in the list (only TITLE)
        
        String[] from = new String[]{TestDBAdapter.KEY_TITLE, TestDBAdapter.KEY_ROWID};
        
        // and an array of the fields we want to bind those fields to (in this case just tvViewRow)
        int[] to = new int[]{R.id.tvViewRow};
 
                
        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter colours = 
        	    new SimpleCursorAdapter(this, R.layout.view_row, coloursCursor, from, to);
        setListAdapter(colours);
       
    }  
}
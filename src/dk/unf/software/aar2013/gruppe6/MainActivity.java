package dk.unf.software.aar2013.gruppe6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private PersonAdapter ListName;
	//Åbner et array af strings som vi kalder ListName
	TextView balance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button addName = (Button)findViewById(R.id.addName);
		TextView balance = (TextView) findViewById(R.id.balance);
		
//		if(balance > 0 )
//		balance.setTextColor(green)
		
//		else if (balance < 0)
//		balance.setTextColor(red)

		

		
		
		//liste er nu adapter, yay
		
		addName.setOnClickListener(new OnClickListener() {
			//knappen er en knap			
			@Override
			public void onClick(View v) {
				Intent openCreate = new Intent(MainActivity.this, CreateNameActivity.class);
				startActivity(openCreate);
				//8========D 
			    //adder items til den synlige liste		
			}
		});
		
		
		
	}

	@Override
	protected void onResume() {
		Log.d("derp","onresume");
		super.onResume();
		BankData BD = new BankData(this);
		
		ListView nameList = (ListView)findViewById(R.id.nameList);
		//listen over personer
		ListName = new PersonAdapter(this, BD.getPersonList());
		
		//opretter en ny array
		
		nameList.setAdapter(ListName);
		
		nameList.setOnItemClickListener(new OnItemClickListener(){
		    
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//Aktivitet når man klikker på et navn
				Intent startProfile = new Intent(MainActivity.this, ProfileActivity.class);
				startProfile.putExtra("id", ListName.getItem(arg2).getId());
				startActivity(startProfile);
				
				// når denne her starter.... Så åbner den DERES --> lort
			
			}
			 
		});
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

//            Log.d("Activity", "Touch event "+event.getRawX()+","+event.getRawY()+" "+x+","+y+" rect "+w.getLeft()+","+w.getTop()+","+w.getRight()+","+w.getBottom()+" coords "+scrcoords[0]+","+scrcoords[1]);
            if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) { 

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
    return ret;
    }

	/*protected void addItem() {
		String strValue = editName.getText().toString();
		this.ListName.add(strValue);
		ListName.notifyDataSetChanged();
		editName.setText("");
		//omdirigerer string til item i listen
		//8=======D
	}  */

}

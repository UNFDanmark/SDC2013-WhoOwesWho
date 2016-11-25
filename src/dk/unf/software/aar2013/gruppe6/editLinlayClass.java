package dk.unf.software.aar2013.gruppe6;

import android.R.string;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class editLinlayClass extends Activity {
	
	EditText addDebtName;
	EditText valueDebt;
	EditText notesAdd;
	Button saveDebt;
	public String debt;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		
		
		
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.editlinlay);
	    saveDebt = (Button)findViewById(R.id.saveDebt);
	    
	    valueDebt = (EditText) findViewById(R.id.valueDebt);
	    
	    
	    saveDebt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int id = getIntent().getExtras().getInt("id");
				debt = valueDebt.getEditableText().toString();
				Log.d("bukse", debt+"");
				Intent startProfile = new Intent();
				startProfile.putExtra("Debt", debt);
				startProfile.putExtra("id", id);
				setResult(RESULT_OK, startProfile);
				finish();
				
//				debt = valueDebt.getEditableText().toString();
//				if (person == null) {
//					person = new Person();
//				}
//				person.setPersonName(debt);
//				
//				BankData bankData = new BankData(editLinlayClass.this);
//				long result = bankData.savePerson(person);
//				Intent startProfile = new Intent(editLinlayClass.this,
//						ProfileActivity.class);
//				startProfile.putExtra("balancePlus", debt);
//				startProfile.putExtra("id", (int) result);
//				startActivity(startProfile);
//				setResult(RESULT_OK, startProfile);
//				finish();
				
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
}

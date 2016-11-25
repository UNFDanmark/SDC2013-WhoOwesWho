package dk.unf.software.aar2013.gruppe6;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ProfileActivity extends Activity {

	TextView nameView;
	Button buttonAddDebt;
	TextView balance;
	BankData bankdata;
	Person newPerson;
	ImageView profilePicture;
	
	int id;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.linlay);
		
		

		bankdata = new BankData(getApplicationContext());
		newPerson = bankdata.getPerson(getIntent().getExtras().getInt("id"));

		buttonAddDebt = (Button) findViewById(R.id.buttonAddDebt);
		buttonAddDebt.setBackgroundColor(Color.rgb(0, 188, 255));

		TextView pending = (TextView) findViewById(R.id.pending);


		pending.setTextColor(Color.rgb(90, 150, 70));

		balance = (TextView) findViewById(R.id.balance);
		
		// balance skal udregnes af variablerne i pending-listen (listActual)
		// dette gøres ved at gette værdien fra den s tring der skrives i
		// editlinlay
		profilePicture = (ImageView) findViewById(R.id.profilePicture);

		// if(getIntent().getParcelableExtra("Image") != null)

		// profilePicture.setImageBitmap((Bitmap)getIntent().getParcelableExtra("Image")
		// );
		if (newPerson.getProfilePic() != null) {
			Log.d("Person", "Setting image to: " + newPerson.profilePic);
			
			profilePicture.setImageURI(newPerson.profilePic);
		}

		// 8=======D Sets profile name
		nameView = (TextView) findViewById(R.id.nameView);
		balance = (TextView) findViewById(R.id.balance);
		setBalanceText(newPerson.getBalance());
		// ListView listView = (ListView) findViewById(R.id.listView1);
		
		

		buttonAddDebt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent startAddDebt = new Intent(ProfileActivity.this,
						editLinlayClass.class);
				startAddDebt.putExtra("id", id);
				startActivityForResult(startAddDebt, 0);
				
				
			}

		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		int debt = Integer.parseInt(data.getExtras().getString("Debt"));
		id = data.getExtras().getInt("id");
		Log.d("buks modtaget", debt+"");
		
		bankdata.updateBalance(newPerson.getBalance()+debt, id);
		newPerson.setBalance(newPerson.getBalance()+debt);
		setBalanceText(newPerson.getBalance());

	}
 
	
	
	 @Override
	protected void onResume() {
		super.onResume();
		if (bankdata == null)
			bankdata = new BankData(getApplicationContext());

		Intent intent = getIntent();
		id = (int) intent.getIntExtra("id", 0);

		newPerson = bankdata.getPerson(id);
		nameView.setText(newPerson.getPersonName());
		setBalanceText(newPerson.getBalance());
		Log.d("Intent id", intent.getExtras().getInt("id") + "");
	} 
	 
	protected void setBalanceText(double debt){
		balance.setText(debt+"");
		if (debt > 0) balance.setTextColor(Color.rgb(0, 255, 0));
		else if (debt < 0) balance.setTextColor(Color.rgb(255, 0, 0));
		else balance.setTextColor(Color.rgb(0, 0, 0));
	}

}

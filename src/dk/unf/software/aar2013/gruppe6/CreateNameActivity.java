package dk.unf.software.aar2013.gruppe6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
//import dk.unf.example.camera.CreateNameActivity;
//import dk.unf.example.camera.R;
import android.widget.Toast;

public class CreateNameActivity extends Activity {
	EditText nameEdit;
	Button buttonSave;
	public String x;
	ImageButton imgBut;
	private Bitmap pic;
	Person person;
	
	Uri fileUri;

	private static final int REQ_CAPTURE_CAPTURE__IMAGE = 0;
	private String imagePath;
	final static int CODE_GETPIC = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_activity);

		// imgBut = (ImageButton) findViewById(R.id.imageButton1);
		buttonSave = (Button) findViewById(R.id.buttonSave);
		nameEdit = (EditText) findViewById(R.id.nameEdit);

		buttonSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				x = nameEdit.getEditableText().toString();
				if (person == null) {
					Log.d("Person", "Creating person onclick");
					person = new Person();
				}
				person.setPersonName(x);
				Log.d("Person", "Saving " + person.getPersonName()
						+ " with pic: " + person.getProfilePic().getPath());
				BankData bankData = new BankData(CreateNameActivity.this);
				long result = bankData.savePerson(person);
				Intent startProfile = new Intent(CreateNameActivity.this,
						ProfileActivity.class);
				startProfile.putExtra("Name", x);
				// if (pic != null)
				// startProfile.putExtra("Image", scaleDownBitmap(pic, 128,
				// getApplicationContext()));
				startProfile.putExtra("id", (int) result);
				startActivity(startProfile);
				setResult(RESULT_OK, startProfile);
				finish();

			}
		});

		imgBut = (ImageButton) findViewById(R.id.imageButton1);

		imgBut.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//				getOu
//				fileUri = getOutputMediaFileUri(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE); // create a file to save the image
//			    imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//				imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
//						.fromFile(CreateNameActivity.this.getTemporaryFile()));
				startActivityForResult(imageIntent, CODE_GETPIC);
			}
		});
	}

	public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight,
			Context context) {

		final float densityMultiplier = context.getResources()
				.getDisplayMetrics().density;

		int h = (int) (newHeight * densityMultiplier);
		int w = (int) (h * photo.getWidth() / ((double) photo.getHeight()));

		photo = Bitmap.createScaledBitmap(photo, w, h, true);

		return photo;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CODE_GETPIC && resultCode == RESULT_OK) {

			Uri selectedImage = data.getData();
			Toast.makeText(getApplicationContext(), "data saved to " + data.getData(), Toast.LENGTH_LONG).show();
			
//			InputStream imageStream = null;
			if (person == null) {
				person = new Person();
			}
			person.setProfilePic(selectedImage);
//			File file = getTemporaryFile();
//
//			try {
//				imageStream = new FileInputStream(file);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//
//			pic = BitmapFactory.decodeStream(imageStream);

			// ImageButton imgBut = (ImageButton)
			// findViewById(R.id.imageButton1);
//			imgBut.setImageBitmap(pic);
			imgBut.setImageURI(selectedImage);
		}
	}

	protected File getTemporaryFile() {
		return new File(Environment.getExternalStorageDirectory(), "image.tmp");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
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

			// Log.d("Activity",
			// "Touch event " + event.getRawX() + "," + event.getRawY()
			// + " " + x + "," + y + " rect " + w.getLeft() + ","
			// + w.getTop() + "," + w.getRight() + ","
			// + w.getBottom() + " coords " + scrcoords[0] + ","
			// + scrcoords[1]);
			if (event.getAction() == MotionEvent.ACTION_UP
					&& (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w
							.getBottom())) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
						.getWindowToken(), 0);
			}
		}
		return ret;
	}

}

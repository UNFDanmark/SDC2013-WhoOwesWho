package dk.unf.software.aar2013.gruppe6;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class BankData extends SQLiteOpenHelper {

	private static final String KEY_ROWID = "id";
	private static final String COLLUMN_NAME = "name";
	private static final String COLLUMN_BALANCE = "balance";
	private static final String COLLUMN_PROFILEPIC = "profilePic";
	private static final String TAG = "BankData";

	private static final String DATABASE_NAME = "PersonsDB.db";
	private static final String DATABASE_TABLE = "persons";
	private static final int DATABASE_VERSION = 3;

	private static final String DATABASE_CREATE = "CREATE TABLE persons (id integer primary key autoincrement, "
			+ "name TEXT not null, balance REAL, profilePic TEXT);";

	private final Context context;

	private SQLiteDatabase db;

	public BankData(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	public long savePerson(Person persons) {
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLLUMN_NAME, persons.getPersonName());
		values.put(COLLUMN_BALANCE, 0);
		if (persons.getProfilePic() != null) {
			Log.d("Save", "pic: " + persons.getProfilePic().toString());
			values.put(COLLUMN_PROFILEPIC, persons.getProfilePic().toString());
		}
		long result = db.insert(DATABASE_TABLE, null, values);
		db.close();

		return result;
	}

	public void deletePerson(Person person) {
		// JSONObject objectDelete = this.JSONObject();

	}

	public ArrayList<String> getNameList() {

		ArrayList<String> nameList = new ArrayList<String>();
		db = this.getReadableDatabase();
		Cursor c = db.query(DATABASE_TABLE, new String[] { COLLUMN_NAME },
				null, null, null, null, null);

		if (c.moveToFirst()) {
			do {
				nameList.add(c.getString(c.getColumnIndexOrThrow(COLLUMN_NAME)));
			} while (c.moveToNext());
		}
		// db.rawQuery("SELECT name FROM " + DATABASE_NAME, null);

		db.close();
		return nameList;

	}

	public Person getPerson(int id) {
		Person person = new Person(null, 0, null);
		db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE "
				+ KEY_ROWID + "=?", new String[] { id + "" });
		person.setId(id);

		c.moveToFirst();
		person.setPersonName(c.getString(c.getColumnIndex(COLLUMN_NAME)));
		String path = c.getString(c.getColumnIndex(COLLUMN_PROFILEPIC));
		if (path != null) {
			Log.d("Person", path);
			person.setProfilePic(Uri.parse(path));
		}
		person.setBalance(c.getDouble(c.getColumnIndex(COLLUMN_BALANCE)));
		db.close();
		c.close();
		return person;
	}
	
	public void updateBalance(double d, int id) {
		db = this.getWritableDatabase();
		
		ContentValues contentVal = new ContentValues();
		
		contentVal.put(COLLUMN_BALANCE, d);
		
		db.update(DATABASE_TABLE, contentVal, "id=?", new String[] {id + ""});
		
		db.close();

	}

	public ArrayList<Person> getPersonList() {

		ArrayList<Person> nameList = new ArrayList<Person>();
		db = this.getReadableDatabase();
		Cursor c = db.query(DATABASE_TABLE, new String[] { COLLUMN_NAME,
				KEY_ROWID }, null, null, null, null, null);

		if (c.moveToFirst()) {
			do {
				Person person = new Person(c.getString(c
						.getColumnIndexOrThrow(COLLUMN_NAME)), 0, null);
				person.setId(c.getInt(c.getColumnIndexOrThrow(KEY_ROWID)));
				nameList.add(person);
			} while (c.moveToNext());
		}

		db.close();
		c.close();
		return nameList;

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(DATABASE_CREATE);
			Log.d("tag", "created db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		// move instead of delete
		db.execSQL("DROP TABLE IF EXISTS persons");
		onCreate(db);

	}

}
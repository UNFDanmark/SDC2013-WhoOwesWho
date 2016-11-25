package dk.unf.software.aar2013.gruppe6;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PersonAdapter extends ArrayAdapter<Person> {
	Context context;
	List<Person> objects;

	public PersonAdapter(Context context, List<Person> objects) {
		super(context, 0, objects);		
		this.context = context;
		this.objects = objects;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
		{
			convertView = new TextView(context);
		}
		
		TextView view = (TextView) convertView;
		view.setText(objects.get(position).getPersonName());
		return view;
	}
	

}

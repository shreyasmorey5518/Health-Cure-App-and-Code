package com.uer.healthcure;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LevelAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] itemname;

	public LevelAdapter(Activity context, String[] itemname) {
		super(context, R.layout.levellist, itemname);
		// TODO Auto-generated constructor stub
		
		this.context=context;
		this.itemname=itemname;

	}
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.levellist, null,true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.person_name);

		txtTitle.setText(itemname[position]);


		return rowView;
		
	};
}

package com.sabid.ramadanschedule2015;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {
	AssetManager asset;
	Activity con;
	String[] list;
	Typeface custom;
	
	public CustomAdapter(Context context, int resource, int textViewResourceId,
			String[] scheduleString) {
		super(context, resource, textViewResourceId, scheduleString);
		// TODO Auto-generated constructor stub
		this.con=(Activity)context;
		this.list=scheduleString;
		asset=con.getAssets();
		custom=Typeface.createFromAsset(asset, "fonts/QuattrocentoSans-Italic.ttf");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v=null;
		if(convertView==null){
			LayoutInflater inflater=(LayoutInflater) con
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	        v = inflater.inflate(R.layout.listview_layout, parent, false);
	        TextView textView = (TextView) v.findViewById(R.id.tvListItem);
	        
			
			//
	        textView.setText(list[position]);
	        textView.setTypeface(custom);
		}else{
			v=convertView;
		}
		
		return super.getView(position, v, parent);
	}

}

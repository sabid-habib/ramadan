package com.sabid.ramadanschedule2015;

import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

public class ScheduleView extends Activity {
	private AutoCompleteTextView actLocation;
	int time[][] = new int[30][2];
	int ifter,sehri,iftHour,sehHour,iftMin,sehMin;
	ListView lvSchedule;
	String[] scheduleString=new String[30];
	ArrayAdapter<String> adpLocation;
	ArrayAdapter<String> adpList;
	SharedPreferences preference;
	CustomAdapter adapterLoc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_list);
		preference=getApplicationContext().getSharedPreferences("shareLoc",0);
		String savedLoc=preference.getString("location", "Dhaka");
		actLocation=(AutoCompleteTextView)findViewById(R.id.actLocation);
		lvSchedule=(ListView)findViewById(R.id.listView1);
		setTimeArray();
		for(int day=0;day<30;day++){
			ifter=getIfterTime(savedLoc, day);
			sehri=getSehriTime(savedLoc, day);
			iftHour=ifter/60;
			iftMin=ifter%60;
			sehHour=sehri/60;
			sehMin=sehri%60;
			if(sehMin<10){
				scheduleString[day]=(" \t\t\t\t\t\t\tRamadan "+(day+1)+"\n\n Sehri  "+sehHour+":0"+sehMin+"\t\t\t\t\t\t Ifter  "+iftHour+":"+iftMin);
			}else if(iftMin<10){
				scheduleString[day]=(" \t\t\t\t\t\t\tRamadan "+(day+1)+"\n\n Sehri  "+sehHour+":"+sehMin+"\t\t\t\t\t\t Ifter  "+iftHour+":0"+iftMin);
			}else if(sehMin<10 && iftMin<10){
				scheduleString[day]=(" \t\t\t\t\t\t\tRamadan "+(day+1)+"\n\n Sehri  "+sehHour+":0"+sehMin+"\t\t\t\t\t\t Ifter  "+iftHour+":0"+iftMin);
			}else{
				scheduleString[day]=(" \t\t\t\t\t\t\tRamadan "+(day+1)+"\n\n Sehri  "+sehHour+":"+sehMin+"\t\t\t\t\t\t Ifter  "+iftHour+":"+iftMin);
			}
		}
		adapterLoc=new CustomAdapter(this, R.layout.listview_layout, R.id.tvListItem, scheduleString);
		lvSchedule.setAdapter(adapterLoc);
		adpLocation=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Location_districts));
		adpList=new ArrayAdapter<String>(this, R.layout.listview_layout, R.id.tvListItem, scheduleString);
		
		actLocation.setAdapter(adpLocation);
		actLocation.setText(savedLoc);
		actLocation.setOnItemClickListener(scheduleListListener);
		//lvSchedule.setAdapter(adpList);
		
		Typeface custom=Typeface.createFromAsset(getAssets(), "fonts/QuattrocentoSans-Italic.ttf");
		
		actLocation.setTypeface(custom);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		//Toast.makeText(getApplicationContext(), savedLoc, Toast.LENGTH_LONG).show();
	}
	
	OnItemClickListener scheduleListListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			String location=(String) parent.getItemAtPosition(position);
			InputMethodManager imm = (InputMethodManager)getSystemService(
				      Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(actLocation.getWindowToken(), 0);
			for(int day=0;day<30;day++){
				ifter=getIfterTime(location, day);
				sehri=getSehriTime(location, day);
				iftHour=ifter/60;
				iftMin=ifter%60;
				sehHour=sehri/60;
				sehMin=sehri%60;
				if(sehMin<10){
					scheduleString[day]=(" \t\t\t\t\t\t\tRamadan "+(day+1)+"\n\n Sehri  "+sehHour+":0"+sehMin+"\t\t\t\t\t\t Ifter  "+iftHour+":"+iftMin);
				}else if(iftMin<10){
					scheduleString[day]=(" \t\t\t\t\t\t\tRamadan "+(day+1)+"\n\n Sehri  "+sehHour+":"+sehMin+"\t\t\t\t\t\t Ifter  "+iftHour+":0"+iftMin);
				}else if(sehMin<10 && iftMin<10){
					scheduleString[day]=(" \t\t\t\t\t\t\tRamadan "+(day+1)+"\n\n Sehri  "+sehHour+":0"+sehMin+"\t\t\t\t\t\t Ifter  "+iftHour+":0"+iftMin);
				}else{
					scheduleString[day]=(" \t\t\t\t\t\t\tRamadan "+(day+1)+"\n\n Sehri  "+sehHour+":"+sehMin+"\t\t\t\t\t\t Ifter  "+iftHour+":"+iftMin);
				}
			}
//			adpList=new ArrayAdapter<String>(ScheduleView.this, R.layout.listview_layout, R.id.tvListItem, scheduleString);
//			lvSchedule.setAdapter(adpList);
			adapterLoc=new CustomAdapter(ScheduleView.this, R.layout.listview_layout, R.id.tvListItem, scheduleString);
			lvSchedule.setAdapter(adapterLoc);
		}
	};
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	};
	
	public void setTimeArray(){
		time[0] = new int[] { 218, 408 };
		time[1] = new int[] { 218, 408 };
		time[2] = new int[] { 218, 409 };
		time[3] = new int[] { 218, 409 };
		time[4] = new int[] { 218, 409 };
		time[5] = new int[] { 218, 410 };
		time[6] = new int[] { 218, 410 };
		time[7] = new int[] { 219, 410 };
		time[8] = new int[] { 219, 411 };
		time[9] = new int[] { 219, 411 };
		time[10] = new int[] { 219, 411 };
		time[11] = new int[] { 219, 411 };
		time[12] = new int[] { 219, 412 };
		time[13] = new int[] { 219, 412 };
		time[14] = new int[] { 219, 412 };
		time[15] = new int[] { 220, 412 };
		time[16] = new int[] { 220, 413 };
		time[17] = new int[] { 220, 413 };
		time[18] = new int[] { 220, 413 };
		time[19] = new int[] { 221, 413 };
		time[20] = new int[] { 221, 413 };
		time[21] = new int[] { 221, 413 };
		time[22] = new int[] { 222, 413 };
		time[23] = new int[] { 222, 413 };
		time[24] = new int[] { 222, 413 };
		time[25] = new int[] { 223, 414 };
		time[26] = new int[] { 223, 414 };
		time[27] = new int[] { 224, 414 };
		time[28] = new int[] { 224, 414 };
		time[29] = new int[] { 225, 414 };
	}
	
	public int getIfterTime(String district,int day) {
		int ifterHour = time[day][1] / 60;
		int ifterMin = time[day][1] % 60;
		if (district.equals("Barisal") || district.equals("Shariatpur")
				|| district.equals("Munshiganj")
				|| district.equals("Narayanganj") || district.equals("Gazipur")
				|| district.equals("Mymensingh")) {
			ifterMin = ifterMin - 1;
		} else if (district.equals("Netrakona")
				|| district.equals("Kishoreganj")
				|| district.equals("Narshingdi") || district.equals("Chandpur")
				|| district.equals("Lakshmipur") || district.equals("Bhola")) {
			ifterMin = ifterMin - 2;
		} else if (district.equals("Noakhali")
				|| district.equals("Brahmanbaria")) {
			ifterMin = ifterMin - 3;
		} else if (district.equals("Comilla") || district.equals("Sunamganj")) {
			ifterMin = ifterMin - 4;
		} else if (district.equals("Feni") || district.equals("Habiganj")) {
			ifterMin = ifterMin - 5;
		} else if (district.equals("Sylhet") || district.equals("Moulvibazar")
				|| district.equals("Chittagong")) {
			ifterMin = ifterMin - 6;
		} else if (district.equals("Khagrachhari")
				|| district.equals("Coxs Bazar")) {
			ifterMin = ifterMin - 7;
		} else if (district.equals("Rangamati") || district.equals("Bandarban")) {
			ifterMin = ifterMin - 8;
		} else if (district.equals("Patuakhali")
				|| district.equals("Jhalokati") || district.equals("Madaripur")) {
			ifterMin = ifterMin + 1;
		} else if (district.equals("Tangail") || district.equals("Sherpur")
				|| district.equals("Rajbari") || district.equals("Jamalpur")
				|| district.equals("Manikganj") || district.equals("Barguna")
				|| district.equals("Pirojpur")) {
			ifterMin = ifterMin + 2;
		} else if (district.equals("Bagerhat") || district.equals("Kurigram")
				|| district.equals("Sirajganj") || district.equals("Faridpur")
				|| district.equals("Gopalganj")) {
			ifterMin = ifterMin + 3;
		} else if (district.equals("Narail") || district.equals("Magura")
				|| district.equals("Lalmonirhat") || district.equals("Khulna")
				|| district.equals("Gaibandha")) {
			ifterMin = ifterMin + 4;
		} else if (district.equals("Bogra") || district.equals("Rangpur")
				|| district.equals("Pabna") || district.equals("Kushtia")
				|| district.equals("Jhenaidah") || district.equals("Jessore")) {
			ifterMin = ifterMin + 5;
		} else if (district.equals("Naogaon") || district.equals("Natore")
				|| district.equals("Joypurhat") || district.equals("Satkhira")) {
			ifterMin = ifterMin + 6;
		} else if (district.equals("Nilphamari") || district.equals("Rajshahi")
				|| district.equals("Chuadanga") || district.equals("Dinajpur")) {
			ifterMin = ifterMin + 7;
		} else if (district.equals("Thakurgaon")
				|| district.equals("Panchagarh") || district.equals("Meherpur")) {
			ifterMin = ifterMin + 8;
		} else if (district.equals("Chapainawabganj")) {
			ifterMin = ifterMin + 9;
		}
		return ifterHour * 60 + ifterMin;
	}

	
	public int getSehriTime(String district,int day) {
			int sehriHour = time[day][0] / 60;
			int sehriMin = time[day][0] % 60;
		
		if (district.equals("Barisal") || district.equals("Shariatpur")
				|| district.equals("Munshiganj")
				|| district.equals("Narayanganj") || district.equals("Gazipur")
				|| district.equals("Mymensingh")) {
			sehriMin = sehriMin - 1;
		} else if (district.equals("Netrakona")
				|| district.equals("Kishoreganj")
				|| district.equals("Narshingdi") || district.equals("Chandpur")
				|| district.equals("Lakshmipur") || district.equals("Bhola")) {
			sehriMin = sehriMin - 2;
		} else if (district.equals("Noakhali")
				|| district.equals("Brahmanbaria")) {
			sehriMin = sehriMin - 3;
		} else if (district.equals("Comilla") || district.equals("Sunamganj")) {
			sehriMin = sehriMin - 4;
		} else if (district.equals("Feni") || district.equals("Habiganj")) {
			sehriMin = sehriMin - 5;
		} else if (district.equals("Sylhet") || district.equals("Moulvibazar")
				|| district.equals("Chittagong")) {
			sehriMin = sehriMin - 6;
		} else if (district.equals("Khagrachhari")
				|| district.equals("Coxs Bazar")) {
			sehriMin = sehriMin - 7;
		} else if (district.equals("Rangamati") || district.equals("Bandarban")) {
			sehriMin = sehriMin - 8;
		} else if (district.equals("Patuakhali")
				|| district.equals("Jhalokati") || district.equals("Madaripur")) {
			sehriMin = sehriMin + 1;
		} else if (district.equals("Tangail") || district.equals("Sherpur")
				|| district.equals("Rajbari") || district.equals("Jamalpur")
				|| district.equals("Manikganj") || district.equals("Barguna")
				|| district.equals("Pirojpur")) {
			sehriMin = sehriMin + 2;
		} else if (district.equals("Bagerhat") || district.equals("Kurigram")
				|| district.equals("Sirajganj") || district.equals("Faridpur")
				|| district.equals("Gopalganj")) {
			sehriMin = sehriMin + 3;
		} else if (district.equals("Narail") || district.equals("Magura")
				|| district.equals("Lalmonirhat") || district.equals("Khulna")
				|| district.equals("Gaibandha")) {
			sehriMin = sehriMin + 4;
		} else if (district.equals("Bogra") || district.equals("Rangpur")
				|| district.equals("Pabna") || district.equals("Kushtia")
				|| district.equals("Jhenaidah") || district.equals("Jessore")) {
			sehriMin = sehriMin + 5;
		} else if (district.equals("Naogaon") || district.equals("Natore")
				|| district.equals("Joypurhat") || district.equals("Satkhira")) {
			sehriMin = sehriMin + 6;
		} else if (district.equals("Nilphamari") || district.equals("Rajshahi")
				|| district.equals("Chuadanga") || district.equals("Dinajpur")) {
			sehriMin = sehriMin + 7;
		} else if (district.equals("Thakurgaon")
				|| district.equals("Panchagarh") || district.equals("Meherpur")) {
			sehriMin = sehriMin + 8;
		} else if (district.equals("Chapainawabganj")) {
			sehriMin = sehriMin + 9;
		}
		return sehriHour * 60 + sehriMin;
	}
}

package com.sabid.ramadanschedule2015;

import java.util.Calendar;
import android.os.Bundle;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	//private Spinner spnDiv, spnDistrict;
	private Intent intent;
	private AutoCompleteTextView actLoc;
	Button btnSeeSchedule;
	SharedPreferences preference;
	private SharedPreferences.Editor prefEditor;
	private static final int PREFERENCE_MODE_PRIVATE=0;
	private TextView tvIft, tvSeh, tvRamadanDate,tvToday;
	int ramadanDay, hour, min, day, month, year, ifterHour, ifterMin, sehriHour,
			sehriMin;
	int time[][] = new int[30][2];
	
	
	String savedLoc;

	
	ArrayAdapter<String> adapterLocation;
	
	private String[] location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setCurrentTime();
		setTimeArray();
		actLoc=(AutoCompleteTextView)findViewById(R.id.actDistrict);
		
		tvIft = (TextView) findViewById(R.id.tvIfter);
		tvSeh = (TextView) findViewById(R.id.tvSehri);
		tvRamadanDate = (TextView) findViewById(R.id.tvRamadan);
		tvToday=(TextView)findViewById(R.id.tvToday);
		
		btnSeeSchedule=(Button) findViewById(R.id.btnSched);
		
		
		preference=getApplicationContext().getSharedPreferences("shareLoc", MODE_PRIVATE);
		String savedLoc=preference.getString("location", "Dhaka");
		
		//Toast.makeText(getApplicationContext(), savedLoc, Toast.LENGTH_LONG).show();
		actLoc.setText(savedLoc);
		
		location=getResources().getStringArray(R.array.Location_districts);
		adapterLocation=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, location);
		actLoc.setAdapter(adapterLocation);
		actLoc.setOnItemClickListener(listenerLocation);

		
		Typeface custom=Typeface.createFromAsset(getAssets(), "fonts/QuattrocentoSans-Italic.ttf");
		actLoc.setTypeface(custom);
		btnSeeSchedule.setTypeface(custom);
		
		int checker = hour*60+min;
		
		setRamadanDay(checker,month,day);
		
		int ifter=getIfterTime(savedLoc);
		int sehri=getSehriTime(savedLoc,checker);
		if(month == 5 && day == 6 && hour*60+min>1128){
			checker=hour*60+min;
		}
		setEditText(ifter, sehri);


		updateWidget(checker);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
	}
	
	
	protected void onResume() {
		super.onResume();
		setCurrentTime();
		int checker = hour*60+min;
		
		preference=getApplicationContext().getSharedPreferences("shareLoc", MODE_PRIVATE);
		String savedLoc=preference.getString("location", "Dhaka");
		
		actLoc.setText(savedLoc);
		setRamadanDay(checker,month,day);
		
		int ifter=getIfterTime(savedLoc);
		int sehri=getSehriTime(savedLoc,checker);
		setEditText(ifter, sehri);
		//actLoc.clearFocus();
		updateWidget(checker);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	};
	
	protected void onRestart() {
		super.onRestart();
		setCurrentTime();
		int checker = hour*60+min;
		
		preference=getApplicationContext().getSharedPreferences("shareLoc", MODE_PRIVATE);
		String savedLoc=preference.getString("location", "Dhaka");
		
		actLoc.setText(savedLoc);
		setRamadanDay(checker,month,day);
		
		int ifter=getIfterTime(savedLoc);
		int sehri=getSehriTime(savedLoc,checker);
		setEditText(ifter, sehri);
		//actLoc.clearFocus();
		updateWidget(checker);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
	};
	
	OnItemClickListener listenerLocation=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
				String district=(String) parent.getItemAtPosition(position);
				InputMethodManager imm = (InputMethodManager)getSystemService(
					      Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(actLoc.getWindowToken(), 0);
					preference=getApplicationContext().getSharedPreferences("shareLoc", MODE_PRIVATE);
				prefEditor=preference.edit();
				prefEditor.putString("location", district);
				prefEditor.commit();
				int checker=hour*60+min;
				int ifter=getIfterTime(district);
				int sehri=getSehriTime(district,checker);
				
				setEditText(ifter, sehri);
				
				updateWidget(checker);
		}
	};
	
//	OnItemSelectedListener spnDivListener = new OnItemSelectedListener() {
//
//		@Override
//		public void onItemSelected(AdapterView<?> parent, View v, int position,
//				long id) {
//			if (id == 0) {
//				districts = getResources().getStringArray(
//						R.array.Districts_Barisal);
//			} else if (id == 1) {
//				districts = getResources().getStringArray(
//						R.array.Districts_Chittagong);
//			} else if (id == 2) {
//				districts = getResources().getStringArray(
//						R.array.Districts_Dhaka);
//			} else if (id == 3) {
//				districts = getResources().getStringArray(
//						R.array.Districts_Khulna);
//			} else if (id == 4) {
//				districts = getResources().getStringArray(
//						R.array.Districts_Rajshahi);
//			} else if (id == 5) {
//				districts = getResources().getStringArray(
//						R.array.Districts_Rangpur);
//			} else if (id == 6) {
//				districts = getResources().getStringArray(
//						R.array.Districts_Sylhet);
//			}
//			adapterDistrict = new ArrayAdapter<String>(MainActivity.this,
//					android.R.layout.simple_spinner_item, districts);
//			spnDistrict.setAdapter(adapterDistrict);
//			spnDistrict.setOnItemSelectedListener(listenerDistrict);
//
//		}
//
//		@Override
//		public void onNothingSelected(AdapterView<?> arg0) {
//			// TODO Auto-generated method stub
//
//		}
//
//	};
//	
//	OnItemSelectedListener listenerDistrict= new OnItemSelectedListener() {
//
//		@Override
//		public void onItemSelected(AdapterView<?> parent, View v, int position,
//				long id) {
//			String district=districts[spnDistrict.getSelectedItemPosition()];
//			int ifter=getIfterTime(district);
//			int sehri=getSehriTime(district);
//			int ifHr=ifter/60;
//			int ifMin=ifter%60;
//			int shHr=sehri/60;
//			int shMin=sehri%60;
//			tvIft.setText("Ifter " + ifHr + ":" + ifMin + " pm");
//			tvSeh.setText("Sehri " + shHr + ":" + shMin + " am");
//			int checker=hour*60+min;
//			String sendText;
//			if(checker > ((time[ramadanDay][1] +720)) && day>18){
//				sendText=tvSeh.getText().toString();
//			}else{
//				sendText=tvIft.getText().toString();
//			}
//			
//			int send[]=new int[2];
//			send=time[ramadanDay];
////			
//			Intent intent = new Intent(MainActivity.this, ramadanWidget.class);
//			intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
////			int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), ramadanWidget.class));
//			intent.putExtra("mydata",sendText);
//			sendBroadcast(intent);
//			//Toast.makeText(getApplicationContext(), ""+district, Toast.LENGTH_LONG).show();
//		}
//
//		@Override
//		public void onNothingSelected(AdapterView<?> arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//	};

	public int getIfterTime(String district) {
		if (ramadanDay!=-1) {
			ifterHour = time[ramadanDay][1] / 60;
			ifterMin = time[ramadanDay][1] % 60;
		}
//		if(month == 5 && day == 6 && hour*60+min>1128){
//			ifterHour = time[0][1] / 60;
//			ifterMin = time[0][1] % 60;
//		}
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

	
	public int getSehriTime(String district,int checker) {
		if (ramadanDay!=-1) {
			if (checker < (time[ramadanDay][1] + 720)
					&& checker > (time[ramadanDay][0]) && ramadanDay<29) {
				sehriHour = time[ramadanDay + 1][0] / 60;
				sehriMin = time[ramadanDay + 1][0] % 60;
			} else {
				sehriHour = time[ramadanDay][0] / 60;
				sehriMin = time[ramadanDay][0] % 60;
			}
		}
//		if(month == 5 && day == 6 && hour*60+min>1128){
//			sehriHour = time[0][0] / 60;
//			sehriMin = time[0][0] % 60;
//		}
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

	public void setEditText(int ifter,int sehri){
		if (ramadanDay!=-1 ) {
			int ifHr = ifter / 60;
			int ifMin = ifter % 60;
			int shHr = sehri / 60;
			int shMin = sehri % 60;
			if (ifMin < 10) {
				tvIft.setText("  Ifter " + ifHr + ":0" + ifMin + " pm");
			} else {
				tvIft.setText("  Ifter " + ifHr + ":" + ifMin + " pm");
			}
			if (shMin < 10) {
				tvSeh.setText("  Sehri " + shHr + ":0" + shMin + " am");
			} else {
				tvSeh.setText("  Sehri " + shHr + ":" + shMin + " am");
			}
			tvRamadanDate.setText("  Ramadan " + (ramadanDay + 1));
		}else{
			tvToday.setText("Ramadan is coming... \nBe Prepared");
		}
		if(ramadanDay==29 && hour*60+min>1150){
			tvToday.setText("Ramadan is over");
			tvSeh.setText(" ");
			tvIft.setText(" ");
			tvRamadanDate.setText(" ");
		}
		Typeface custom=Typeface.createFromAsset(getAssets(), "fonts/QuattrocentoSans-Italic.ttf");
		
		tvRamadanDate.setTypeface(custom);
		tvSeh.setTypeface(custom);
		tvIft.setTypeface(custom);
		tvToday.setTypeface(custom);
		
	}
	public void updateWidget(int checker){
		String sendText=null;

		if (ramadanDay!=-1 ) {
			if (checker > ((time[ramadanDay][1] + 720))
					|| checker < ((time[ramadanDay][0]))) {
				sendText = tvSeh.getText().toString();
			} else {
				sendText = tvIft.getText().toString();
			}
		}
		Intent intent1 = new Intent(this, ramadanWidget.class);
		intent1.setAction("android.appwidget.action.APPWIDGET_UPDATE");
		intent1.putExtra("mydata",sendText);
		sendBroadcast(intent1);
	}
	public void setRamadanDay(int checker,int month,int day){
		if (year==2016) {
			if (month == 6 && day < 7) {
				ramadanDay = day + 23;
				if(ramadanDay==30) ramadanDay--;
			} else if (month == 5 && day > 6) {
				ramadanDay = day - 7;
			}else if(month == 5 && day == 6 && hour*60+min>1128){
				ramadanDay=0;
			}else
				ramadanDay = -1;
			if (ramadanDay!=-1 && ramadanDay < 29 && !(month == 5 && day == 6 && hour*60+min>1128) ) {
				if ((checker > (time[ramadanDay][1] + 720)) ) {
					ramadanDay++;
				}
			}
		}else ramadanDay=-1;
	}
	public void setCurrentTime(){
		Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		min = c.get(Calendar.MINUTE);
		day = c.get(Calendar.DAY_OF_MONTH);
		month = c.get(Calendar.MONTH);
		year=c.get(Calendar.YEAR);
	}
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
	private void updateUI(Intent intent) {
    	String strday = intent.getStringExtra("day"); 
    	String strtime = intent.getStringExtra("time");
    	String strmonth = intent.getStringExtra("month");
    	Log.d("Tagday", strday);
    	Log.d("Tagmnth", strmonth);
    	Log.d("Tag", strtime);
    	
    	Integer time=Integer.parseInt(strtime);
    	Integer day=Integer.parseInt(strday);
    	Integer month=Integer.parseInt(strmonth);
    	
    	setRamadanDay(time, month, day);
    	
    	String distr=actLoc.getText().toString();
    	
    	int ift=getIfterTime(distr);
    	int seh=getSehriTime(distr, time);
    	
    	setEditText(ift, seh);
    	updateWidget(time);
    }
	public void SeeSchedule(View v){
		Intent intentSchedule=new Intent(this, ScheduleView.class);
		startActivity(intentSchedule);
	}
}

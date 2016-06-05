package com.sabid.ramadanschedule2015;

import java.util.Calendar;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class ramadanWidget extends AppWidgetProvider {
	
	private RemoteViews views;
    public static String newdata,widgetStringIfter,widgetStringSehri;
    int ramadanDay, hour, min, day, month, year, ifterHour, ifterMin, sehriHour,
	sehriMin,ifter,sehri;
    SharedPreferences preference;
    private SharedPreferences.Editor prefEditor;
    private static final int PREFERENCE_MODE_PRIVATE=0;
    int time[][] = new int[30][2];
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		setCurrentTime();
		setTimeArray();
		preference=context.getSharedPreferences("shareLoc",0);
		String savedLoc=preference.getString("location", "Dhaka");
		
		int checker = hour*60+min;
		
		setRamadanDay(checker,month,day);
		
		int ifter=getIfterTime(savedLoc);
		int sehri=getSehriTime(savedLoc,checker);
		
		setWidgetText(ifter, sehri);
		
		views = new RemoteViews(context.getPackageName(),R.layout.widget_layout);
if (ramadanDay!=-1) {
	//		if (newdata.equals("")) {
	//			if (checker > ((time[ramadanDay][1] + 720))
	//					|| checker < ((time[ramadanDay][0]))) {
	//				views.setTextViewText(R.id.tvOption, widgetStringSehri);
	//			} else {
	//				views.setTextViewText(R.id.tvOption, widgetStringIfter);
	//			}
	//		}else{
	//			views.setTextViewText(R.id.tvOption, widgetStringIfter);
	//		}
		if (checker > ((time[ramadanDay][1] + 720))
				|| checker < ((time[ramadanDay][0]))) {
			views.setTextViewText(R.id.tvOption, widgetStringSehri);
		} else {
			views.setTextViewText(R.id.tvOption, widgetStringIfter);
		}
	}else{
		views.setTextViewText(R.id.tvOption, " ");
	}
	if(ramadanDay==29 && hour*60+min>1145){
		views.setTextViewText(R.id.tvOption, " ");
	}
		//views.setTextViewText(R.id.tvOption, newdata);
		appWidgetManager.updateAppWidget(appWidgetIds, views);
        
		//Toast.makeText(context, savedLoc, Toast.LENGTH_LONG).show();
        
        
        
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);

	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		
		
		Bundle getPrevData=intent.getExtras();
		if(getPrevData!=null){
		String data=getPrevData.getString("mydata");
		newdata=data;
		}
		//Toast.makeText(context, newdata, Toast.LENGTH_LONG).show();
		
		if (action != null
				&& action.equals("android.appwidget.action.APPWIDGET_UPDATE")) {
			final AppWidgetManager manager = AppWidgetManager
					.getInstance(context);
			onUpdate(context, manager,
					manager.getAppWidgetIds(new ComponentName(context,
							ramadanWidget.class)));
		}
		else super.onReceive(context, intent);
	}
	
	public int getIfterTime(String district) {
		if (ramadanDay!=-1) {
			ifterHour = time[ramadanDay][1] / 60;
			ifterMin = time[ramadanDay][1] % 60;
		}
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

	public void setRamadanDay(int checker,int month,int day){
		if (year==2015) {
			if (month == 6 && day < 19) {
				ramadanDay = day + 11;
			} else if (month == 5 && day > 18) {
				ramadanDay = day - 19;
			} else
				ramadanDay = -1;
			if (ramadanDay!=-1 && ramadanDay < 29) {
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
		time[0] = new int[] { 218, 412 };
		time[1] = new int[] { 218, 412 };
		time[2] = new int[] { 218, 412 };
		time[3] = new int[] { 219, 412 };
		time[4] = new int[] { 219, 413 };
		time[5] = new int[] { 219, 413 };
		time[6] = new int[] { 219, 413 };
		time[7] = new int[] { 220, 413 };
		time[8] = new int[] { 220, 413 };
		time[9] = new int[] { 221, 413 };
		time[10] = new int[] { 221, 413 };
		time[11] = new int[] { 222, 413 };
		time[12] = new int[] { 222, 414 };
		time[13] = new int[] { 222, 414 };
		time[14] = new int[] { 223, 414 };
		time[15] = new int[] { 223, 414 };
		time[16] = new int[] { 224, 414 };
		time[17] = new int[] { 224, 414 };
		time[18] = new int[] { 225, 414 };
		time[19] = new int[] { 225, 414 };
		time[20] = new int[] { 226, 413 };
		time[21] = new int[] { 226, 413 };
		time[22] = new int[] { 227, 413 };
		time[23] = new int[] { 228, 413 };
		time[24] = new int[] { 228, 413 };
		time[25] = new int[] { 229, 413 };
		time[26] = new int[] { 229, 413 };
		time[27] = new int[] { 230, 412 };
		time[28] = new int[] { 230, 412 };
		time[29] = new int[] { 231, 412 };
	}
	
	public void setWidgetText(int ifter,int sehri){
		if (ramadanDay!=-1) {
			int ifHr = ifter / 60;
			int ifMin = ifter % 60;
			int shHr = sehri / 60;
			int shMin = sehri % 60;
			if (ifMin < 10) {
				widgetStringIfter = ("  Ifter " + ifHr + ":0" + ifMin + " pm");
			} else {
				widgetStringIfter = ("  Ifter " + ifHr + ":" + ifMin + " pm");
			}
			if (shMin < 10) {
				widgetStringSehri = ("  Sehri " + shHr + ":0" + shMin + " am");
			} else {
				widgetStringSehri = ("  Sehri " + shHr + ":" + shMin + " am");
			}
		}
	}



}

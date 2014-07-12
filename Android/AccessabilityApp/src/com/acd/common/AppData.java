package com.acd.common;

import android.content.Context;
import android.content.SharedPreferences;

public class AppData {

	public static SharedPreferences pref;
	public static String gmailId = "null";
	public static Context ctx;
	public static Context context;

	public AppData(Context context) {

		pref = context.getSharedPreferences("AR", context.MODE_PRIVATE);
		ctx = context;
	}

	public static String getUserId() {

		return pref.getString("user_id", "none");

	}

	public static void setUserId(String user_id) {

		pref.edit().putString("user_id", user_id).commit();

	}

	public static void setRegisterId(String regid) {

		pref.edit().putString("regid", regid).commit();

	}

	public static SettingsDo getSettings() {

		SettingsDo userInfoModel = new SettingsDo();

		userInfoModel.Buildings = pref.getBoolean("building", false);
		userInfoModel.Toilets = pref.getBoolean("toilets", false);
		userInfoModel.Super_Stops = pref.getBoolean("scools", false);

		return userInfoModel;

	}

	public static void setUserModel(SettingsDo settings) {

		pref.edit().putBoolean("building", settings.Buildings).commit();
		pref.edit().putBoolean("toilets", settings.Toilets).commit();
		pref.edit().putBoolean("scools", settings.Super_Stops).commit();

	}
	
	public static boolean isHospitalChecked() {
		return pref.getBoolean("hosp", false);

	}
	
	public static boolean isScoolChecked() {
		return pref.getBoolean("scools", false);

	}
	
	public static boolean isToiletsChecked() {
		return pref.getBoolean("toi", false);

	}
	
	
	public static void setHospitalChecked(boolean hosp) {

		pref.edit().putBoolean("hosp", hosp).commit();

	}
	
	public static void setScoolChecked(boolean school) {

		pref.edit().putBoolean("scools", school).commit();

	}
	public static void setToiletsChecked(boolean toi) {

		pref.edit().putBoolean("toi", toi).commit();

	}
	
	
	

}
/*
 *  LeEco Extras Settings Module for Resurrection Remix ROMs
 *  Made by @andr68rus 2017
 *  Modified by @Fedor917 for ZUK Z2
 */

package com.cyanogenmod.settings.zpref;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import android.util.Log;
import android.os.SystemProperties;
import java.io.*;
import android.widget.Toast;  
import android.preference.ListPreference;

public class ZPrefSettings extends PreferenceActivity implements OnPreferenceChangeListener {
	private static final boolean DEBUG = false;
	private static final String TAG = "ZPref";
	private static final String AKT_KEY = "akt";\
	private static final String AKT_SYSTEM_PROPERTY = "persist.AKT.profile";

	private ListPreference mAKT;

    private Context mContext;
    private SharedPreferences mPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.z_settings);
        mContext = getApplicationContext();
        
        mAKT = (ListPreference) findPreference(AKT_KEY);
        mAKT.setValue(SystemProperties.get(AKT_SYSTEM_PROPERTY, "Stock"));
        mAKT.setOnPreferenceChangeListener(this);
        
        if (DEBUG) Log.d(TAG, "Initializating done");
    }

    // Set AKT
    private void setAKT(String value) {
		try {
			Process su = Runtime.getRuntime().exec("su");
			DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
			outputStream.writeBytes("mount -o remount,rw /system\n");
			outputStream.writeBytes("cat /system/etc/zpref/AKT/" + value + " > /system/etc/init.d/99AKT\n");
			outputStream.writeBytes("chmod 777 /system/etc/init.d/99AKT\n");
			outputStream.writeBytes("/system/etc/init.d/99AKT\n");
			outputStream.writeBytes("mount -o remount,ro /system\n");
			outputStream.flush();
			outputStream.writeBytes("exit\n");
			outputStream.flush();
			su.waitFor();
		} catch(IOException e){
			Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
			toast.show();
		} catch(InterruptedException e){
			Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
			toast.show();
		}
		SystemProperties.set(AKT_SYSTEM_PROPERTY, value);
    }
   

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String key = preference.getKey();
        boolean value;
        String strvalue;
        if (DEBUG) Log.d(TAG, "Preference changed.");
	if (AKT_KEY.equals(key)) {
		strvalue = (String) newValue;
		if (DEBUG) Log.d(TAG, "AKT setting changed: " + strvalue);
		setAKT(strvalue);
		return true;
	}  
          
        return false;
    }

}

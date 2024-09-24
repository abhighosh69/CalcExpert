package com.ghoshabhi.calcexpert;

import android.os.Bundle;

import android.content.SharedPreferences;
import androidx.preference.PreferenceFragmentCompat;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onResume(){
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("sound_preference")){
            boolean soundEnabled = sharedPreferences.getBoolean(key, true);
            //((MainActivity) requireActivity()).getSoundManager().setSoundEnabled(soundEnabled);
        }
        else if(key.equals("theme_preference")){
            String theme = sharedPreferences.getString(key, "light");
            if("light".equals(theme)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else if("dark".equals(theme)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            requireActivity().recreate();
        }
    }

}


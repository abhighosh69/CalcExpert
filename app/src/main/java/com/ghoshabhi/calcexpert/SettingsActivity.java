package com.ghoshabhi.calcexpert;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton closeButton;
    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        changeTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean soundEnabled = sharedPreferences.getBoolean("sound_preference", true);
        soundManager = new SoundManager(this, soundEnabled); // Pass sound preference here

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings_container, new SettingsFragment())
                    .commit();
        }

        // Initialize views
        closeButton = findViewById(R.id.clsButton);

        // Close Logic
        closeButton.setOnClickListener(v -> {
            soundManager.setSoundEnabled(sharedPreferences.getBoolean("sound_preference", true)); // Set it again
            soundManager.playClickSound();
            finish();
        });
    }

    private void changeTheme(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sharedPreferences.getString("theme_preference", "light");

        if ("light".equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if ("dark".equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    public void playClickSound() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean soundEnabled = sharedPreferences.getBoolean("sound_preference", true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundManager.release();
    }
}
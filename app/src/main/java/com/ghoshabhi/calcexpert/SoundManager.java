package com.ghoshabhi.calcexpert;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {
    private SoundPool soundPool;
    private int clickSound;
    private boolean soundEnabled;

    public SoundManager(Context context, boolean soundEnabled) {

        this.soundEnabled =soundEnabled;
        // Initialize SoundPool
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        clickSound = soundPool.load(context, R.raw.click, 1); // Replace with your sound file
    }

    // Method to mute the system sound
    public static void muteSystemSound(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0); // Set volume to 0
        }
    }

    public void playClickSound() {
        if (soundEnabled) {
            soundPool.play(clickSound, 1, 1, 0, 0, 1);
        }
    }

    public void setSoundEnabled(boolean enabled) {
        soundEnabled = enabled;
    }

    protected void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}

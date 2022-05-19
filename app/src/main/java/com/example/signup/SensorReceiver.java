package com.example.signup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

public class SensorReceiver extends BroadcastReceiver {

    private AudioManager myAudioManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Context cntxt = context.getApplicationContext();
        myAudioManager = (AudioManager) cntxt.getSystemService(Context.AUDIO_SERVICE);
        if(("com.example.application.BroadcastSensor").equals(intent.getAction())){
            if(intent.getStringExtra("situation").equals("on")){
                myAudioManager.adjustVolume(AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_PLAY_SOUND);
            }else{
                myAudioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.FLAG_PLAY_SOUND);
            }
        }
    }
}

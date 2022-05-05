package com.example.signup;

import android.media.MediaPlayer;

public class MyMediaPlayer {

    static MediaPlayer instance;

    public static MediaPlayer getInstance(){
        if(instance==null){
            instance=new MediaPlayer();
        }else{

        }
        return instance;
    }

    public static int currentIndex = -1;

}

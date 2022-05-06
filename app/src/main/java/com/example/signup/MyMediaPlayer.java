package com.example.signup;

import android.media.MediaPlayer;

public class MyMediaPlayer {

    static MediaPlayer aMediaPlayer;

    public static MediaPlayer getInstance(){
        if(aMediaPlayer==null){
            aMediaPlayer=new MediaPlayer();
        }else{

        }
        return aMediaPlayer;
    }

    public static int currentIndex = -1;

}

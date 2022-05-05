package com.example.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {


    TextView title, currentTime, totalTime, artist;
    SeekBar seekBar;
    ImageView pausePlay, next, previous, musicIconBig, albumID;
    ArrayList<AudioModel> songsList;
    AudioModel currentSong;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player2);

        title=findViewById(R.id.song_title);
        currentTime=findViewById(R.id.current_time);
        totalTime=findViewById(R.id.total_time);
        artist=findViewById(R.id.song_artist);
        albumID=findViewById(R.id.music_icon_big);//
        seekBar=findViewById(R.id.seek_bar);
        pausePlay=findViewById(R.id.pause_play);
        next=findViewById(R.id.next);
        previous=findViewById(R.id.previous);
        musicIconBig=findViewById(R.id.music_icon_big);

        title.setSelected(true);

        songsList = (ArrayList<AudioModel>)getIntent().getSerializableExtra("LIST");
        setResourcesWithMusic();
        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTime.setText(convertToMMSS(mediaPlayer.getCurrentPosition()+""));

                    if(mediaPlayer.isPlaying()){
                        pausePlay.setImageResource(R.drawable.ic_baseline_pause_24);
                    }else{
                        pausePlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);

                    }
                }
                new Handler().postDelayed(this, 100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    void setResourcesWithMusic(){
        currentSong = songsList.get(MyMediaPlayer.currentIndex);

        title.setText(currentSong.getTitle());
        artist.setText(currentSong.getArtist());
        totalTime.setText(convertToMMSS(currentSong.getDuration()));
        //albumID.setImageResource();

        pausePlay.setOnClickListener(v->pausePlay());
        next.setOnClickListener(v->playNextSong());
        previous.setOnClickListener(v->playPreviousSong());

        playMusic();

    }

    private void playMusic(){

        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void playNextSong(){

        if(MyMediaPlayer.currentIndex==songsList.size())
            return;

        MyMediaPlayer.currentIndex+=1;
        mediaPlayer.reset();
        setResourcesWithMusic();

    }

    private void playPreviousSong(){

        if(MyMediaPlayer.currentIndex==0)
            return;

        MyMediaPlayer.currentIndex-=1;
        mediaPlayer.reset();
        setResourcesWithMusic();

    }

    private void pausePlay(){

        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else{
            mediaPlayer.start();
        }

    }

    public static String convertToMMSS(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis)%TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis)%TimeUnit.MINUTES.toSeconds(1));
    }


}
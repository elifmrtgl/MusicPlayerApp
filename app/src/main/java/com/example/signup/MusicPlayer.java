package com.example.signup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MusicPlayer extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView noSongsFoundTextView;
    ArrayList<AudioModel> songs = new ArrayList<>();
    SensorReceiver sensorReceiver = new SensorReceiver();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        recyclerView = findViewById(R.id.recycler_view);
        noSongsFoundTextView = findViewById(R.id.no_songs_text);

        IntentFilter filter = new IntentFilter("com.example.application.BroadcastSensor");
        registerReceiver(sensorReceiver, filter);


        if(checkForPermission()==false){
            requestForPermission();
            return;
        }

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC +" != 0";

        Cursor myCursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null  );

        while(myCursor.moveToNext()){
            AudioModel songData = new AudioModel(myCursor.getString(1),myCursor.getString(0), myCursor.getString(2),myCursor.getString(3), myCursor.getString(4), myCursor.getString(5));
            if(new File(songData.getPath()).exists()){
                Log.d("myTag", "deneme");
                songs.add(songData);
            }
        }
        if(songs.size()==0){
            noSongsFoundTextView.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MusicListAdapter(songs,getApplicationContext()));
        }

    }

    boolean checkForPermission(){
        int result = ContextCompat.checkSelfPermission(MusicPlayer.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }

    void requestForPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MusicPlayer.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MusicPlayer.this, "Permission is needed, please allow from settings.", Toast.LENGTH_SHORT);
        }else{
            ActivityCompat.requestPermissions(MusicPlayer.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(recyclerView!=null){
            recyclerView.setAdapter(new MusicListAdapter(songs,getApplicationContext()));
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(sensorReceiver);
    }
}
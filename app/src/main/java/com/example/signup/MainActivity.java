package com.example.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button log_in;
    private Button sign_up;
    private TextView userName;
    private TextView passWord;
    private int counter=0;
    private int check=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        log_in=(Button)findViewById(R.id.login);
        sign_up=(Button)findViewById(R.id.signup);
        userName=(TextView)findViewById(R.id.txtUsername);
        passWord=(TextView)findViewById(R.id.txtPassword);
        username.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);


        log_in.setOnClickListener(new View.OnClickListener(){
        @Override
            public void onClick(View view){
                login(view);
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                signup(view);
            }
        });

    }

    public void signup(View view) {
        Intent intent = new Intent(this, DisplaySignUp.class);
        try{
            startActivity(intent);
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }

    }

    public void login(View view) {

            if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                check=1;
                Toast.makeText(getApplicationContext(), "Log in succeed.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MusicPlayer.class);
                try{
                    startActivity(intent);
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                counter++;
            }
            if(counter%3==0 && check==-1){
                Toast.makeText(getApplicationContext(), "You have exceeded the limit.",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this,DisplaySignUp.class);
                try{
                    startActivity(intent2);
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }


    }
}
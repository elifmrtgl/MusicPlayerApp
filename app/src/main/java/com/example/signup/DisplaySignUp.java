package com.example.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DisplaySignUp extends AppCompatActivity {

    private EditText name;
    private EditText phone;
    private EditText mail;
    private EditText password;
    private EditText password2;
    private Button signupButton;
    private EditText birthDate;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sign_up);

        name=(EditText)findViewById(R.id.editTextTextPersonName);
        phone=(EditText)findViewById(R.id.editTextPhone);
        mail=(EditText)findViewById(R.id.editTextTextEmailAddress);
        password=(EditText)findViewById(R.id.editTextTextPassword);
        password2=(EditText)findViewById(R.id.editTextTextPassword2);
        signupButton=(Button)findViewById(R.id.signupButton);
        birthDate=(EditText)findViewById(R.id.editTextDate);

        /*sp=getSharedPreferences("KayitBilgi",MODE_PRIVATE);
        editor=sp.edit();*/

        //Intent intent = getIntent();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //signup2(view);
                //Toast.makeText(getApplicationContext(), "Log in succeed.",Toast.LENGTH_SHORT).show();

                if(!password.getText().toString().equals(password2.getText().toString())){
                    Toast.makeText(getApplicationContext(), "The passwords are not matching. Please try again.",Toast.LENGTH_SHORT).show();
                    return;
                }

                String emailsend = mail.getText().toString();
                String emailsubject = name.getText().toString();
                String emailbody = "Ad Soyad: " + name.getText().toString() + " Telefon:" + phone.getText().toString() +
                        " Doğum Tarihi: "+birthDate.getText().toString() +
                        " Şifre: " + password.getText().toString() + " Mail: " + mail.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ emailsend});
                email.putExtra(Intent.EXTRA_SUBJECT, emailsubject);
                email.putExtra(Intent.EXTRA_TEXT, emailbody);

                email.setType("message/rfc822");


                try {
                    startActivity(Intent.createChooser(email, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DisplaySignUp.this, "Error occur to sending mail..", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /*public void signup2(View view){



    }*/


}
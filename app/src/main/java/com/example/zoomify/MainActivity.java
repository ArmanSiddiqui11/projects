package com.example.zoomify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
TextInputEditText meetingIdInput,nameInput;
MaterialButton joinbtn,createbtn;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("name_pref",MODE_PRIVATE);

        meetingIdInput=findViewById(R.id.meeting_inputId);
        nameInput=findViewById(R.id.name_input);
        joinbtn=findViewById(R.id.join_btn);
        createbtn=findViewById(R.id.create_btn);
        nameInput.setText(sharedPreferences.getString("name",""));

     joinbtn.setOnClickListener(view -> {
         String meetingID=meetingIdInput.getText().toString();
         if(meetingID.length()!=10){
             meetingIdInput.setError("Invalid Meeting ID");
             meetingIdInput.requestFocus();
             return;
         }
         String name=nameInput.getText().toString();
         if(name.isEmpty()){
             nameInput.setError("Name is required to join a meeting");
             nameInput.requestFocus();
             return;
         }
         startMeeting(meetingID,name);

     });


        createbtn.setOnClickListener(view -> {
            String name=nameInput.getText().toString();
            if(name.isEmpty()){
                nameInput.setError("Name is required to join a meeting");
                nameInput.requestFocus();
                return;
            }
            startMeeting(getRandomMeetingID(),name);
        });
    }
    void startMeeting(String meetingId,String name){
        sharedPreferences.edit().putString("name",name).apply();
        String userId= UUID.randomUUID().toString();
        Intent intent=new Intent(MainActivity.this,ConferenceActivity.class);
        intent.putExtra("Meeting_ID",meetingId);
        intent.putExtra("user_ID",userId);
        intent.putExtra("Name",name);
        startActivity(intent);
    }
    String getRandomMeetingID(){
        StringBuilder id=new StringBuilder();
        while(id.length()!=10){
            int random=new Random().nextInt(10);
            id.append(random);
        }
        return id.toString();
    }
}
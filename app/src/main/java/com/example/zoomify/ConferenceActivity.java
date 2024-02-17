package com.example.zoomify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceConfig;
import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceFragment;

public class ConferenceActivity extends AppCompatActivity {
TextView meetingIdtext;
ImageView sharebtn;
String meetingId,userId,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference);
        meetingIdtext = findViewById(R.id.meeting_id_textview);
        sharebtn = findViewById(R.id.share_btn);
        meetingId = getIntent().getStringExtra("Meeting_ID");
        userId = getIntent().getStringExtra("user_ID");
        name = getIntent().getStringExtra("Name");

        meetingIdtext.setText("Meeting ID : " + meetingId);
        addFragment();

        sharebtn.setOnClickListener(view -> {
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,"Join Meeting On Zoomify app \n Meeting ID : "+meetingId);
            startActivity(Intent.createChooser(intent,"Share via"));
        });
    }
        public void addFragment() {
            long appID = AppConstants.appId;
            String appSign = AppConstants.appSign;


            ZegoUIKitPrebuiltVideoConferenceConfig config = new ZegoUIKitPrebuiltVideoConferenceConfig();
            config.turnOnMicrophoneWhenJoining=false;
            config.turnOnCameraWhenJoining=false;
            ZegoUIKitPrebuiltVideoConferenceFragment fragment = ZegoUIKitPrebuiltVideoConferenceFragment.newInstance(appID, appSign, userId, name,meetingId,config);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commitNow();
        }
    }
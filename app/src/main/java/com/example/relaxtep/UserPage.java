package com.example.relaxtep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;

public class UserPage extends AppCompatActivity {
    private static final String TAG = "TAG";
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    ImageView logout;
    ImageView play;
    ImageView pause;
    ImageView submit;
    ProgressBar pb;
    TextView steps;
//  add mediaplayer, steps, breathing exercise.
    MediaPlayer mp;
    Timer tm;
    SensorManager sm;
    Sensor stepCounter;
    boolean isPresentSensor;
    int steps_so_far;
    int time_so_far;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        steps_so_far = 0;
        time_so_far = 0;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mp = MediaPlayer.create(this,R.raw.audio);

        logout = findViewById(R.id.logout);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        submit = findViewById(R.id.submit);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutt();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playy();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pausee();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitt();
            }
        });
    }

    void logoutt(){
        //stop music
        mp.stop();
        //logout from the id
        mAuth.signOut();
        Intent intent = new Intent(UserPage.this,LoginScreen.class);
        startActivity(intent);
        finish();
    }

    void playy(){
        //resume timer
        steps = findViewById(R.id.steps);
        pb = findViewById(R.id.pbar);
        tm  = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                time_so_far++;
                pb.setProgress(time_so_far);
                steps.setText("Steps: "+time_so_far);
                if(time_so_far == 600)
                    tm.cancel();
            }
        };
        tm.schedule(tt,0,1000);
        //resume music
        mp.start();
        //resume steps
    }

    void pausee(){
        //pause timer
        tm.cancel();
        pb = findViewById(R.id.pbar);
        pb.setProgress(time_so_far);
        //pause music
        mp.pause();
        //pause steps
        steps.setText("Steps: "+time_so_far);
    }

    void submitt(){
        //stop music
        mp.stop();
        //send to db
        log newLog = new log(System.currentTimeMillis(),steps_so_far,time_so_far, mAuth.getUid());
        db.collection("logs").add(newLog)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG,"Log added!");
                        Intent intent = new Intent(UserPage.this,UserPage.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"Not able to update logs");
                Intent intent = new Intent(UserPage.this,UserPage.class);
                startActivity(intent);
                finish();
            }
        });
        //reset page
    }
}
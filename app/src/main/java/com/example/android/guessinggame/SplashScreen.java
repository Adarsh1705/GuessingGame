package com.example.android.guessinggame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pl.droidsonroids.gif.GifImageView;

public class SplashScreen extends AppCompatActivity {

    GifImageView gifImageView;
    Animation question_anim, main_text_anim;
    TextView mainText, creatorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        gifImageView = findViewById(R.id.gifSplashScreen);
        mainText = findViewById(R.id.mainText);
        creatorName = findViewById(R.id.creatorName);

        question_anim = AnimationUtils.loadAnimation(this, R.anim.translate_down);
        main_text_anim = AnimationUtils.loadAnimation(this, R.anim.appear);

        gifImageView.setAnimation(question_anim);
        mainText.setAnimation(main_text_anim);

        final Intent openMain = new Intent(SplashScreen.this, MainActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(openMain);
                finish();
            }
        }, 1500);

    }
}
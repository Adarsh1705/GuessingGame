package com.example.android.guessinggame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    ConstraintLayout constraintLayout1, constraintLayout2;
    Animation fadeOut, fadeIn, translateY;
    TextView textNum, textQues, textTries, textCongrats;
    int Num, flag = 0;
    GifImageView gif;
    Values values = new Values();
    InputMethodManager input;
    private EditText gameNumber;
    private Button startGame, HigherButton, LowerButton, PlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        constraintLayout = findViewById(R.id.ConsLayout);

        gameNumber = findViewById(R.id.editTextNum);
        startGame = findViewById(R.id.startBut);
        HigherButton = findViewById(R.id.higherBut);
        LowerButton = findViewById(R.id.lowerBut);
        PlayButton = findViewById(R.id.PlayAgainButton);
        textNum = findViewById(R.id.textView);
        textQues = findViewById(R.id.textQues);
        textTries = findViewById(R.id.textTries);
        textCongrats = findViewById(R.id.textCongrats);
        constraintLayout1 = findViewById(R.id.ConsLay);
        constraintLayout2 = findViewById(R.id.ConsLay2);
        gif = findViewById(R.id.gifImageView);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        translateY = AnimationUtils.loadAnimation(this, R.anim.translate_button);

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input != null) {
                    hideKeyboard(v);
                }
            }
        });

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(gameNumber.getText()) || ((Integer.parseInt(gameNumber.getText().toString())) > 100) || ((Integer.parseInt(gameNumber.getText().toString())) < 1)) {
                    Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                } else {
                    Num = Integer.parseInt(gameNumber.getText().toString());
                    gameNumber.startAnimation(fadeOut);
                    constraintLayout1.startAnimation(fadeOut);
                    gameNumber.startAnimation(fadeOut);
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startFun();
                                }
                            });
                        }
                    }, 500);
                }
                if (input != null) {
                    hideKeyboard(v);
                }

            }
        });

        /*ViewModel Observer*/
        values = new ViewModelProvider(this).get(Values.class);
        values.getValues().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                flag++;
                textNum.setText(String.valueOf(integer));
                if (integer == Num) {
                    finalDisp();
                }
            }
        });

        LowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Num < Integer.parseInt(textNum.getText().toString())) {
                    values.High = Integer.parseInt(textNum.getText().toString());
                    values.go();
                } else {
                    Toast.makeText(MainActivity.this, "Wrong Button Pressed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        HigherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Num > Integer.parseInt(textNum.getText().toString())) {
                    values.Low = Integer.parseInt(textNum.getText().toString());
                    values.go();
                } else {
                    Toast.makeText(MainActivity.this, "Wrong Button Pressed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    public void hideKeyboard(View view) {
        input.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    void startFun() {
        constraintLayout1.setVisibility(View.GONE);

        constraintLayout2.setVisibility(View.VISIBLE);
        constraintLayout2.startAnimation(fadeIn);
        HigherButton.startAnimation(fadeIn);
        LowerButton.startAnimation(fadeIn);
        textNum.startAnimation(fadeIn);

    }

    public void finalDisp() {
        HigherButton.setVisibility(View.GONE);
        LowerButton.setVisibility(View.GONE);
        textQues.setVisibility(View.GONE);
        textTries.setVisibility(View.VISIBLE);
        textTries.setText("No. of tries: " + flag);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        constraintLayout2.startAnimation(fadeOut);
                        constraintLayout2.setVisibility(View.GONE);
                        gif.setVisibility(View.VISIBLE);
                        textCongrats.setVisibility(View.VISIBLE);
                        PlayButton.setVisibility(View.VISIBLE);
                        PlayButton.startAnimation(translateY);
                    }
                });
            }
        }, 800);
    }
}
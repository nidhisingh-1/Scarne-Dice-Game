package com.example.nidhi.scarnesdice;

import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int ys = 0; //your score
    private int yt = 0; //your turn
    private int cs = 0; //computer's score
    private int ct = 0; //computer's turn
    Button rollbtn, resetbtn, holdbtn;
    TextView tv_ys, tv_cs, tv_ts, tv_cts;
    ImageView dice;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollbtn = findViewById(R.id.roll_btn);
        resetbtn = findViewById(R.id.reset_btn);
        holdbtn = findViewById(R.id.hold_btn);
        tv_cs = findViewById(R.id.tv_cs);
        tv_ys = findViewById(R.id.tv_ys);
        tv_cts = findViewById(R.id.tv_cts);
        tv_ts = findViewById(R.id.tv_ts);
        dice = findViewById(R.id.dice);

        resetbtn.setOnClickListener(v -> {
            ys = 0;
            yt = 0;
            cs = 0;
            ct = 0;

            tv_cs.setText("Computer's Score :0");
            tv_ts.setText("Your turn Score: 0");
            tv_ys.setText("Your Score : 0");
            tv_cts.setText("Computer's turn score : 0");

            rollbtn.setEnabled(true);
            holdbtn.setEnabled(true);

        });

        rollbtn.setOnClickListener(v -> {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.rolldice_audio);
            mp.start();
            int val = rollDice();
            winner();
            updateStatus(val);
        });

        holdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Vibrator vib = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(200);
                ys += yt;
                tv_cs.setText("Computer's Score :"+ cs);
                tv_ts.setText("Your turn Score: "+yt);
                tv_ys.setText("Your Score : "+ys);
                tv_cts.setText("Computer's turn score : " +ct);
                yt = 0;
                computerTurn();
            }
        });
    }



    public void updateStatus(int value) {
        if (value != 1) {
            ys += value;
            yt = yt + value;
            tv_cs.setText("Computer's Score:" + cs);
            tv_ts.setText("Your turn Score: " + ys);
            tv_ys.setText("Your Score: " + ys);
            tv_cts.setText("Computer's turn score: " + ct);

        } else {
            ys = ys - yt;
            tv_cs.setText("Computer's Score:" + cs);
            tv_ts.setText("Your turn Score: " + 0);
            tv_ys.setText("Your Score: " + ys);

            yt = 0;
            computerTurn();
        }

        winner();
    }

    public void winner(){
        if(cs >= 100) {
            AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
            adb.setMessage("Computer Won \n Press Reset to play again");
            adb.show();
            rollbtn.setEnabled(false);
            holdbtn.setEnabled(false);
        }
            if(ys >= 100){
                AlertDialog.Builder adbb = new AlertDialog.Builder(MainActivity.this);
                adbb.setMessage("You Won \n Press Reset to play again");
                adbb.show();
                rollbtn.setEnabled(false);
                holdbtn.setEnabled(false);
            }

    }

    public void computerTurn() {
        rollbtn.setEnabled(false);
        holdbtn.setEnabled(false);
        int value = rollDice();
        while (value != 1) {
            cs += value;
            value = rollDice();
        }

        if (value == 1) {
            tv_cs.setText("Computer's Score:" + cs);
            tv_ts.setText("Your turn Score: " + yt);
            tv_ys.setText("Your Score: " + ys);
            tv_cts.setText("Computer's turn score: " + cs);
        } else {
            cs += cs;
            tv_cs.setText("Computer's Score: " + cs);
            tv_ts.setText("Your turn Score: " + yt);
            tv_ys.setText("Your Score: " + ys);
            tv_cts.setText("Computer's turn score " + cs);

        }
        ct = 0;
        rollbtn.setEnabled(true);
        holdbtn.setEnabled(true);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        winner();
    }


    protected Integer rollDice() {
        Integer value = random.nextInt(6) + 1;
        if (value == 1) {
            ImageView i = findViewById(R.id.dice);
            i.setImageResource(R.drawable.dice1);
        } else if (value == 2) {
            ImageView i = findViewById(R.id.dice);
            i.setImageResource(R.drawable.dice2);
        } else if (value == 3) {
            ImageView i = findViewById(R.id.dice);
            i.setImageResource(R.drawable.dice3);
        } else if (value == 4) {
            ImageView i = findViewById(R.id.dice);
            i.setImageResource(R.drawable.dice4);
        } else if (value == 5) {
            ImageView i = findViewById(R.id.dice);
            i.setImageResource(R.drawable.dice5);
        } else if (value == 6) {
            ImageView i = findViewById(R.id.dice);
            i.setImageResource(R.drawable.dice6);
        }
        return value;



    }


}
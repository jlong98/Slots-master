package com.example.slots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridLayout grid;
    private Drawable cherry;
    private Drawable grape;
    private Drawable pear;
    private Drawable strawberry;
    private ImageView images[];
    private TextView pointsText;
    private RadioButton easyButton;
    private RadioButton hardButton;
    private Button startButton;
    private Handler handler;
    private beginSlot begin;
    private Boolean off;
    private Random rand;
    int fruit;
    private int points;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid = findViewById(R.id.gridLayout);
        cherry = getDrawable(R.drawable.cherry);
        grape = getDrawable(R.drawable.grape);
        pear = getDrawable(R.drawable.pear);
        strawberry = getDrawable(R.drawable.strawberry);
        images = new ImageView[3];
        pointsText = findViewById(R.id.pointsText);
        easyButton = findViewById(R.id.easyButton);
        hardButton = findViewById(R.id.hardButton);
        startButton = findViewById(R.id.startButton);
        begin = new beginSlot();
        handler = new Handler();
        rand = new Random();
        if(savedInstanceState==null) {
            points = 0;
            off=false;  
        }else {
            points = savedInstanceState.getInt("SCORE");
            pointsText.setText("" + points);
            off = savedInstanceState.getBoolean("OFF");
            if(off)
                startButton.setText("STOP");
        }
        fruit = 0;
        for(int i=0;i<3;i++){
            images[i] = (ImageView) getLayoutInflater().inflate(R.layout.slot_view,null);
            images[i].setMinimumWidth(270);
            images[i].setMinimumHeight(270);
            images[i].setImageDrawable(grape);
            grid.addView(images[i]);
        }

    }

    public void onPause(){
        super.onPause();
        handler.removeCallbacks(begin);
    }

    public void onResume(){
        super.onResume();
        if(off){
            handler.postDelayed(begin,100);
        }
    }

    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putInt("SCORE",points);
        bundle.putBoolean("OFF",off);
    }

    public void rules(View v){
        Intent i = new Intent(this, rulesActivity.class);
        startActivity(i);
    }

    public void shuffle(View v){
        if(off){
            startButton.setText("START");
            off = false;
            points = updateScore(v);
            pointsText.setText(points+"");
            handler.removeCallbacks(begin);
        }else{
            startButton.setText("STOP");
            off = true;
            handler.postDelayed(begin,100);

        }
    }

    public int updateScore(View v){
        int x = 0;
        if(images[0].getDrawable() == cherry && images[1].getDrawable() == cherry && images[2].getDrawable() == cherry)
            x = 40;
        else if(images[0].getDrawable() == grape && images[1].getDrawable() == grape && images[2].getDrawable() == grape)
            x = 60;
        else if (images[0].getDrawable() == pear && images[1].getDrawable() == pear && images[2].getDrawable() == pear)
            x = 80;
        else if (images[0].getDrawable() == strawberry && images[1].getDrawable() == strawberry && images[2].getDrawable() == strawberry)
            x = 100;
        return x;
    }

    public class beginSlot implements Runnable {
        public void run() {
            if (easyButton.isChecked()) {
                fruit = rand.nextInt(4);
                for (int i = 0; i < 3; i++) {
                    if (fruit == 0) {
                        images[i].setImageDrawable(cherry);
                    } else if (fruit == 1) {
                        images[i].setImageDrawable(grape);
                    } else if (fruit == 2) {
                        images[i].setImageDrawable(pear);
                    } else {
                        images[i].setImageDrawable(strawberry);
                    }
                    fruit = rand.nextInt(4);
                }
                handler.postDelayed(begin, 500);
            }else{
                fruit = rand.nextInt(3);
                for (int i = 0; i < 3; i++) {
                    if (fruit == 0) {
                        images[i].setImageDrawable(cherry);
                    } else if (fruit == 1) {
                        images[i].setImageDrawable(grape);
                    } else if (fruit == 2) {
                        images[i].setImageDrawable(pear);
                    } else {
                        images[i].setImageDrawable(strawberry);
                    }
                    fruit = rand.nextInt(3);
                }
                handler.postDelayed(begin, 100);
            }
        }
    }

}

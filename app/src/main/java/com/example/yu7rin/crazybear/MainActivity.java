package com.example.yu7rin.crazybear;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    public void GetHelp(View view){

        //Intent intent = new Intent();
         //intent.setClass(activity_main.this,help.class);
        //activity_main.this.startActivity(intent);
        setContentView(R.layout.help);

    }

    public void  Play(View view){

        setContentView(new playGround(this));
    }

    public void  main(View view){

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }




}

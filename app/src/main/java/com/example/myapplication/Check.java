package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Check extends AppCompatActivity {
    public String coor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        final TextView text = findViewById(R.id.textView15);
        final TextView textPssn = findViewById(R.id.textView7);
        Intent it = getIntent();
        coor = it.getStringExtra("coor");
        new Thread(new Runnable(){
            @Override
            public void run(){
                MysqlCon con = new MysqlCon();
                con.run();
                final String dataPssn = con.showPssnCheck();
                final String dataName = con.showNameCheck();
                Log.v("OK",dataPssn);

                text.post(new Runnable() {
                    public void run() {
                        text.setText(dataName);
                    }
                });
                textPssn.post(new Runnable() {
                    public void run() {
                        textPssn.setText(dataPssn);
                    }
                });
            }
        }).start();
    }
    public void delete(View v){
        final TextView editname = findViewById(R.id.editText);
        final TextView text = findViewById(R.id.textView15);
        final TextView textPssn = findViewById(R.id.textView7);
        new Thread(new Runnable(){
            @Override
            public void run(){

                MysqlCon con = new MysqlCon();
                con.run();
                con.deleteCheck(editname.getText().toString());
            }

        }).start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable(){
            @Override
            public void run(){
                MysqlCon con = new MysqlCon();
                con.run();
                final String dataPssn = con.showPssnCheck();
                final String dataName = con.showNameCheck();
                Log.v("OK",dataPssn);

                text.post(new Runnable() {
                    public void run() {
                        text.setText(dataName);
                    }
                });
                textPssn.post(new Runnable() {
                    public void run() {
                        textPssn.setText(dataPssn);
                    }
                });
            }
        }).start();
    }
    public void add(View v){
        final TextView editname = findViewById(R.id.editText);
        final TextView text = findViewById(R.id.textView15);
        final TextView textPssn = findViewById(R.id.textView7);
        new Thread(new Runnable(){
            @Override
            public void run(){

                MysqlCon con = new MysqlCon();
                con.run();
                con.addCheck(coor, editname.getText().toString());


            }

        }).start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable(){
            @Override
            public void run(){
                MysqlCon con = new MysqlCon();
                con.run();
                final String dataPssn = con.showPssnCheck();
                final String dataName = con.showNameCheck();
                Log.v("OK",dataPssn);

                text.post(new Runnable() {
                    public void run() {
                        text.setText(dataName);
                    }
                });
                textPssn.post(new Runnable() {
                    public void run() {
                        textPssn.setText(dataPssn);
                    }
                });
            }
        }).start();
    }
}

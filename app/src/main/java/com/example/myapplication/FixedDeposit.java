package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class FixedDeposit extends AppCompatActivity {
    public String coor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_deposit);
        final TextView text = findViewById(R.id.textView);
        Intent it = getIntent();
        coor = it.getStringExtra("coor");

        new Thread(new Runnable(){
            @Override
            public void run(){
                MysqlCon con = new MysqlCon();
                con.run();
                final String data = con.showFixedDepositRate();
                Log.v("OK",data);

                text.post(new Runnable() {
                    public void run() {
                        text.setText(data);
                    }
                });

            }
        }).start();
    }
    public void modify(View v){
        final TextView noEdit = findViewById(R.id.editTextNo);
        final TextView yearEdit = findViewById(R.id.editTextYear);
        final TextView rateEdit = findViewById(R.id.editTextRate);
        final float a = Float.valueOf(rateEdit.getText().toString());
        final TextView text = findViewById(R.id.textView);
        Log.v("OK", String.valueOf(a));

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable(){
            @Override
            public void run(){

                MysqlCon con = new MysqlCon();
                con.run();
                con.setFixedDeposit(coor, Integer.parseInt(noEdit.getText().toString()), Integer.parseInt(yearEdit.getText().toString()), a);
            }

        }).start();

        new Thread(new Runnable(){
            @Override
            public void run(){
                MysqlCon con = new MysqlCon();
                con.run();
                final String data = con.showFixedDepositRate();
                Log.v("OK",data);

                text.post(new Runnable() {
                    public void run() {
                        text.setText(data);
                    }
                });

            }
        }).start();
    }
    public void delete(View v){
        final TextView noEdit = findViewById(R.id.editTextNo);
        final TextView text = findViewById(R.id.textView);
        new Thread(new Runnable(){
            @Override
            public void run(){

                MysqlCon con = new MysqlCon();
                con.run();
                con.deleteFixedDeposit(coor,Integer.parseInt(noEdit.getText().toString()));
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
                final String data = con.showFixedDepositRate();
                Log.v("OK",data);

                text.post(new Runnable() {
                    public void run() {
                        text.setText(data);
                    }
                });

            }
        }).start();

    }
}

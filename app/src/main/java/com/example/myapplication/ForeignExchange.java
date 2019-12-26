package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class ForeignExchange extends AppCompatActivity {
    public String coor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreign_exchange);
        final TextView textC = findViewById(R.id.textView);
        final TextView textD = findViewById(R.id.textView8);
        Intent it = getIntent();
        coor = it.getStringExtra("coor");

        new Thread(new Runnable(){
            @Override
            public void run(){
                MysqlCon con = new MysqlCon();
                con.run();
                final String dataC = con.showForeignExchangeCountry();
                final String dataD = con.showForeignExchangeForex();
                Log.v("OK",dataC);

                textC.post(new Runnable() {
                    public void run() {
                        textC.setText(dataC);
                    }
                });
                textD.post(new Runnable() {
                    public void run() {
                        textD.setText(dataD);
                    }
                });
            }
        }).start();


    }
    public void modify(View v){
        final TextView countryEdit = findViewById(R.id.editTextCountry);
        final TextView forexEdit = findViewById(R.id.editTextRate);
        final float a = Float.valueOf(forexEdit.getText().toString());
        final TextView textC = findViewById(R.id.textView);
        final TextView textD = findViewById(R.id.textView8);
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
                con.setForeignExchange(coor,countryEdit.getText().toString(),a);
            }

        }).start();
        new Thread(new Runnable(){
            @Override
            public void run(){
                MysqlCon con = new MysqlCon();
                con.run();
                final String dataC = con.showForeignExchangeCountry();
                final String dataD = con.showForeignExchangeForex();
                Log.v("OK",dataC);

                textC.post(new Runnable() {
                    public void run() {
                        textC.setText(dataC);
                    }
                });
                textD.post(new Runnable() {
                    public void run() {
                        textD.setText(dataD);
                    }
                });
            }
        }).start();
    }
    public void delete(View v){
        final TextView countryEdit = findViewById(R.id.editTextCountry);
        final TextView textC = findViewById(R.id.textView);
        final TextView textD = findViewById(R.id.textView8);
        new Thread(new Runnable(){
            @Override
            public void run(){

                MysqlCon con = new MysqlCon();
                con.run();
                con.deleteForeignExchange(coor,countryEdit.getText().toString());
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
                final String dataC = con.showForeignExchangeCountry();
                final String dataD = con.showForeignExchangeForex();
                Log.v("OK",dataC);

                textC.post(new Runnable() {
                    public void run() {
                        textC.setText(dataC);
                    }
                });
                textD.post(new Runnable() {
                    public void run() {
                        textD.setText(dataD);
                    }
                });
            }
        }).start();
    }
}

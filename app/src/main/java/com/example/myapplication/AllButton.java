package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class AllButton extends AppCompatActivity {
    public String coor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_button);
        final TextView text_view = findViewById(R.id.textView111);
        final TextView text12 = findViewById(R.id.textView12);
        Intent it = getIntent();
        coor = it.getStringExtra("coor_account");
        text12.setText("您好,"+ coor +"專員");


        new Thread(new Runnable(){
            @Override
            public void run(){
                MysqlCon con = new MysqlCon();
                con.run();
                final String data = con.getData(coor);
                Log.v("OK",data);

                text_view.post(new Runnable() {
                    public void run() {
                        text_view.setText(data);
                    }
                });

            }
        }).start();

    }

    public void goForeignExchange(View v){
        Intent it = new Intent(this, ForeignExchange.class);
        it.putExtra("coor", coor);
        startActivity(it);
    }
    public void goFixedDeposit(View v){
        Intent it = new Intent(this, FixedDeposit.class);
        it.putExtra("coor", coor);
        startActivity(it);
    }
    public void goCheck(View v){
        Intent it = new Intent(this, Check.class);
        it.putExtra("coor", coor);
        startActivity(it);
    }

}

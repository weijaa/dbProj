package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final TextView text_view = findViewById(R.id.text);
//
//        new Thread(new Runnable(){
//            @Override
//            public void run(){
//                MysqlCon con = new MysqlCon();
//                con.run();
//                final String data = con.getData();
//                Log.v("OK",data);
//
//                text_view.post(new Runnable() {
//                    public void run() {
//                        text_view.setText(data);
//                    }
//                });
//
//            }
//        }).start();
    }
    public void goSelect(final View v){
        final TextView accountEdit = findViewById(R.id.accountEdit);
        final TextView passwordEdit = findViewById(R.id.passwordEdit);
        final TextView text_view = findViewById(R.id.text);
        boolean data;
        new Thread(new Runnable(){
            @Override
            public void run(){

                MysqlCon con = new MysqlCon();
                con.run();
                boolean data = con.login(accountEdit.getText().toString(), passwordEdit.getText().toString());

               if (data){
                   Intent it = new Intent(v.getContext(), AllButton.class);
                   it.putExtra("coor_account", accountEdit.getText().toString());
                   startActivity(it);
               }else{
                   text_view.post(new Runnable() {
                       @Override
                       public void run() {
                           text_view.setText(accountEdit.getText());
                       }
                    });
               }

            }

        }).start();
    }

}

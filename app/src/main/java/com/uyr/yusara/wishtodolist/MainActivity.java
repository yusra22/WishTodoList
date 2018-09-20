package com.uyr.yusara.wishtodolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import data.DatabaseHandler;
import model.MyWish;

public class MainActivity extends AppCompatActivity {

    private EditText editTitle,editWish;
    private Button btnSave;
    private DatabaseHandler dba;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = new DatabaseHandler(MainActivity.this);



        editTitle = (EditText)findViewById(R.id.editTitle);
        editWish = (EditText)findViewById(R.id.editWish);
        btnSave = (Button)findViewById(R.id.btnSave);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveToDB();

            }
        });

    }

    private void saveToDB() {

        MyWish wish = new MyWish();
        wish.setTitle(editTitle.getText().toString().trim());
        wish.setContent(editWish.getText().toString().trim());

        //Dpat dri method class Databasehandler
        //wish yg kite declare dri MyWish wish = new MyWish();
        dba.addWishes(wish);
        dba.close();

        //clear
        editTitle.setText("");
        editWish.setText("");

        Intent i = new Intent(MainActivity.this, DisplayWishActivity.class);
        startActivity(i);

    }
}

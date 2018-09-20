package com.uyr.yusara.wishtodolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHandler;

public class WishDetailsActivity extends AppCompatActivity {

    private TextView title,detailsTextView,detailsDateText;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_details);

        title = (TextView)findViewById(R.id.title);
        detailsDateText = (TextView) findViewById(R.id.detailsDateText);
        detailsTextView = (TextView)findViewById(R.id.detailsTextView);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        Bundle extras = getIntent().getExtras();

        if(extras != null)
        {
            title.setText(extras.getString("title"));
            detailsTextView.setText(" \" " + extras.getString("content") + " \" ");
            detailsDateText.setText("Created: " + extras.getString("date"));

            final int id = extras.getInt("id");

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                    dba.deleteWish(id);

                    Toast.makeText(getApplicationContext(), "Item have been deleted", Toast.LENGTH_LONG).show();

                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);

                    Intent i = new Intent(WishDetailsActivity.this, DisplayWishActivity.class);
                    startActivity(i);
                }
            });

        }
    }

    
}

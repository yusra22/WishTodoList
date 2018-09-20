package com.uyr.yusara.wishtodolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.DatabaseHandler;
import model.MyWish;

public class DisplayWishActivity extends AppCompatActivity {

    private DatabaseHandler dba;
    private ArrayList<MyWish> dbWishes = new ArrayList<>();
    private WishAdapter wishAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_wish);

        listView = (ListView) findViewById(R.id.listview);

        refreshData();
    }

    private void refreshData() {
        dbWishes.clear();
        dba = new DatabaseHandler(getApplicationContext());

        //getWishes dpat dri method DatabaseHandler
        ArrayList<MyWish> wishesFromDB = dba.getWishes();

        for (int i = 0; i < wishesFromDB.size(); i++) {
            String title = wishesFromDB.get(i).getTitle();
            String content = wishesFromDB.get(i).getContent();
            String dateText = wishesFromDB.get(i).getRecordDate();
            int mid = wishesFromDB.get(i).getItemId();

            MyWish myWish = new MyWish();
            myWish.setTitle(title);
            myWish.setContent(content);
            myWish.setRecordDate(dateText);
            myWish.setItemId(mid);

            dbWishes.add(myWish);

        }
        dba.close();

        //setup adapter
        wishAdapter = new WishAdapter(DisplayWishActivity.this, R.layout.wish_row, dbWishes);
        listView.setAdapter(wishAdapter);
        wishAdapter.notifyDataSetChanged();

    }
    class WishAdapter extends ArrayAdapter<MyWish> {
        Activity activity;
        int layoutResource;
        MyWish wish;
        ArrayList<MyWish> mData = new ArrayList<>();

        public WishAdapter(Activity act, int resource, ArrayList<MyWish> data) {
            super(act, resource, data);

            activity = act;
            layoutResource = resource;
            mData = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Nullable
        @Override
        public MyWish getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getPosition(@Nullable MyWish item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



            View row = convertView;
            ViewHolder holder = null;

            if (row == null || (row.getTag() == null)) {
                LayoutInflater inflater = LayoutInflater.from(activity);

                row = inflater.inflate(layoutResource, null);
                holder = new ViewHolder();

                holder.mTitle = (TextView) row.findViewById(R.id.name);
                holder.mDate = (TextView) row.findViewById(R.id.dateText);

                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            holder.myWish = getItem(position);

            holder.mTitle.setText(holder.myWish.getTitle());
            holder.mDate.setText(holder.myWish.getRecordDate());

            //untuk click ke page wishdetails
            final ViewHolder finalHolder = holder;
            holder.mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String text = finalHolder.myWish.getContent().toString();
                    String dateText = finalHolder.myWish.getRecordDate().toString();
                    String title = finalHolder.myWish.getTitle().toString();

                    int mid = finalHolder.myWish.getItemId();

                    Log.v("MyId: " , String.valueOf(mid));

                    Intent i = new Intent(DisplayWishActivity.this, WishDetailsActivity.class);
                    i.putExtra("title", title);
                    i.putExtra("content", text);
                    i.putExtra("date", dateText);
                    i.putExtra("id", mid);

                    startActivity(i);
                }
            });

            holder.mDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String text = finalHolder.myWish.getContent().toString();
                    String dateText = finalHolder.myWish.getRecordDate().toString();
                    String title = finalHolder.myWish.getTitle().toString();

                    int mid = finalHolder.myWish.getItemId();

                    Intent i = new Intent(DisplayWishActivity.this, WishDetailsActivity.class);
                    i.putExtra("title", title);
                    i.putExtra("content", text);
                    i.putExtra("date", dateText);
                    i.putExtra("id", mid);

                    startActivity(i);
                }
            });



            return row;


        }
        class ViewHolder {
            MyWish myWish;
            TextView mTitle;
            int mid;
            TextView mDate;

        }
    }

}



package com.example.witness;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AlbumMain extends BaseActivity {

    Integer[] abc = {R.drawable.couple1, R.drawable.couple2, R.drawable.couple3, R.drawable.couple4, R.drawable.couple5, R.drawable.couple6, R.drawable.couple7};
    String abcd = "abcdeasdfasdf";

    ArrayList<Bitmap> img;
    ArrayList<String> img_title;
    ArrayList<String> img_text;

    public void setting() {
        img = new ArrayList<>();
        img_title = new ArrayList<>();
        img_text = new ArrayList<>();

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.couple1);
        img.add(bitmap);
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.couple2);
        img.add(bitmap);
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.couple3);
        img.add(bitmap);
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.couple4);
        img.add(bitmap);

        img_title.add("성경 그리고 반지");
        img_text.add("성경이랑 반지 있는 그림 내용임");

        img_title.add("은하수");
        img_text.add("은하수 그림 내용임");

        img_title.add("석양");
        img_text.add("석양 그림 내용임");

        img_title.add("꽃밭");
        img_text.add("꽃밭 그림 내용임");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_main);

        setting();

        GridView gv = findViewById(R.id.gridView);

        MyGridAdapter gAdapter = new MyGridAdapter(this);
        gv.setAdapter(gAdapter);
    }

    public class MyGridAdapter extends BaseAdapter {
        Context context;

        public MyGridAdapter(Context _context) {
            context = _context;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView ivG = new ImageView(context);
            ivG.setLayoutParams(new GridView.LayoutParams(250, 300));
            ivG.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ivG.setPadding(5,5,5,5);

            //ivG.setImageBitmap(img.get(position));
            ivG.setImageResource(abc[position]);

            return ivG;
        }
    }
}

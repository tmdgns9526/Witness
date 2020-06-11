package com.example.witness;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static com.example.witness.AlbumMain.img;
import static com.example.witness.AlbumMain.img_text;
import static com.example.witness.AlbumMain.img_title;

public class AlbumDetail extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_detail);

        String abc;

        ImageView imgView = findViewById(R.id.imgView);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvText = findViewById(R.id.tvText);
        ImageButton btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        int imgID = intent.getIntExtra("IMGID", -1);

        if(imgID == -1) {
            Toast.makeText(AlbumDetail.this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            imgView.setImageBitmap(img.get(imgID));
            tvTitle.setText(img_title.get(imgID));
            tvText.setText(img_text.get(imgID));
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

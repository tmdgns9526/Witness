package com.example.witness;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlbumMain extends BaseActivity {

    public static ArrayList<Bitmap> img;
    public static ArrayList<String> img_date;
    public static ArrayList<String> img_title;
    public static ArrayList<String> img_text;
    ArrayList<Integer> deleteCheck;
    int isDelete = 0;
    int deleteWork = 0;

    public void setting() {
        img = new ArrayList<>();
        img_date = new ArrayList<>();
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

        img_date.add("2018-05-08");
        img_title.add("성경 그리고 반지");
        img_text.add("성경이랑 반지 있는 그림 내용임");

        img_date.add("2019-12-06");
        img_title.add("은하수");
        img_text.add("은하수 그림 내용임");

        img_date.add("2020-04-15");
        img_title.add("석양");
        img_text.add("석양 그림 내용임");

        img_date.add("2020-06-10");
        img_title.add("꽃밭");
        img_text.add("꽃밭 그림 내용임");
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_main);

        setting();

        ImageButton imgSort = findViewById(R.id.imgSort);
        ImageButton imgAdd = findViewById(R.id.imgAdd);
        ImageButton imgDelete = findViewById(R.id.imgDelete);

        GridView gv = findViewById(R.id.gridView);

        final MyGridAdapter gAdapter = new MyGridAdapter(this);
        gv.setAdapter(gAdapter);

        imgSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteWork==0) {
                    isDelete = 1;
                    Toast.makeText(AlbumMain.this, "삭제할 이미지를 선택해주세요", Toast.LENGTH_SHORT).show();
                    deleteWork=1;
                }
                else {
                    //삭제하기
                    int count=0;
                    for(int i=0; i<deleteCheck.size(); i++) {
                        if(deleteCheck.get(i) == 1) {
                            count++;
                        }
                    }

                    AlertDialog.Builder dlg = new AlertDialog.Builder(AlbumMain.this);
                    dlg.setTitle("삭제");
                    dlg.setMessage(count + "개의 앨범을 삭제하시겠습니까?");
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for(int i=0; i<deleteCheck.size(); i++) {
                                if(deleteCheck.get(i) == 1) {
                                    img.remove(i);
                                    img_date.remove(i);
                                    img_title.remove(i);
                                    img_text.remove(i);
                                    gAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });

                    dlg.setNegativeButton("취소", null);
                    dlg.show();

                  // deleteWork=0;
                   // for(int i=0; i<deleteCheck.size(); i++) {
                   //     deleteCheck.add(i, 0);
                   // }
                }
            }
        });
    }

    public class MyGridAdapter extends BaseAdapter {
        Context context;

        public MyGridAdapter(Context _context) {
            context = _context;
        }

        @Override
        public int getCount() {
            return img.size();
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
            final ImageView ivG = new ImageView(context);
            ivG.setLayoutParams(new GridView.LayoutParams(300, 300));
            ivG.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ivG.setPadding(3, 3, 3, 3);

            ivG.setImageBitmap(img.get(position));

            deleteCheck = new ArrayList<>();
            for (int i = 0; i < img.size(); i++) {
                deleteCheck.add(0);
            }
            final int pos = position;
            ivG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isDelete == 0) {
                        Intent intent = new Intent(AlbumMain.this, AlbumDetail.class);
                        intent.putExtra("IMGID", pos);

                        startActivity(intent);
                    } else {
                        if (deleteCheck.get(pos) == 0) {
                            ivG.setColorFilter(Color.parseColor("#88000000"));
                            deleteCheck.add(pos, 1);
                        } else {
                            ivG.setColorFilter(null);
                            deleteCheck.add(pos, 0);
                        }
                    }
                }
            });

            return ivG;
        }
    }
}

package com.example.witness;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BucketList extends BaseActivity {
    View dialogView;
    ArrayList<String> list;

    BucketListAdapter adapter;
    ItemTouchHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bucket_list);

        ImageButton btnPlus = findViewById(R.id.btnPlus);

        String[] data = {"여자친구랑 여행가기", "남산가기", "한강가기"};

        list = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {
            list.add(data[i]);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BucketListAdapter(list);
        recyclerView.setAdapter(adapter);

        helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        helper.attachToRecyclerView(recyclerView);


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(BucketList.this);
                dlg.setTitle("추가");

                dialogView = View.inflate(BucketList.this, R.layout.bucket_list_dialog, null);
                dlg.setView(dialogView);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //추가사항 : DB에 리스트 입력하는거 처리 + notifyDataSetChanged();
                        EditText dlgEdt = dialogView.findViewById(R.id.edtText);
                        if (!dlgEdt.getText().toString().equals("")) {
                            list.add(dlgEdt.getText().toString());
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(BucketList.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });


    }
}

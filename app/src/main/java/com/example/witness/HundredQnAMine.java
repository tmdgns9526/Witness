package com.example.witness;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;


public class HundredQnAMine extends Fragment {

    View dialogView;
    Context ct;
    ImageButton btnPlus;

    ArrayList<String> questionList, answerList;
    ArrayList<Integer> completeList;

    HundredQnAAdapter adapter;

    public HundredQnAMine() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ct = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_qna_mine, container, false);

        String[] question = {"내 생일은?", "내가 좋아하는 음식은?", "내가 사는 동네는?"};
        String[] answer = {"7월 18일", "훠궈", "은평구"};
        Integer[] complete = {1, 1, 0};


        questionList = new ArrayList<>();
        answerList = new ArrayList<>();
        completeList = new ArrayList<>();

        for(int i=0; i<question.length; i++) {
            questionList.add(question[i]);
            answerList.add(answer[i]);
            completeList.add(complete[i]);
        }

        btnPlus = v.findViewById(R.id.btnPlus);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ct));

        adapter = new HundredQnAAdapter(questionList, answerList, completeList, 1);

        recyclerView.setAdapter(adapter);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ct);

                dialogView = View.inflate(ct, R.layout.hundred_qna_dialog, null);
                EditText dlgEdt = dialogView.findViewById(R.id.edt1);
                final EditText dlgEdt2 = dialogView.findViewById(R.id.edt2);
                dlgEdt.setText("질문 추가");
                dlgEdt.setEnabled(false);
                dlg.setView(dialogView);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //추가사항 : DB에 리스트 입력하는거 처리 + notifyDataSetChanged();
                        if (!dlgEdt2.getText().toString().equals("")) {
                            questionList.add(dlgEdt2.getText().toString());
                            answerList.add("");
                            completeList.add(0);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ct, "질문을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });




        return v;
    }
}

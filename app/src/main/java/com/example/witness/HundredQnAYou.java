package com.example.witness;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class HundredQnAYou extends Fragment {

    Context ct;
    ArrayList<String> questionList, answerList;
    ArrayList<Integer> completeList;
    HundredQnAAdapter adapter;

    public HundredQnAYou() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ct = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_qna_you, container, false);

        String[] question = {"상대방 질문", "여기도 상대방 질문", "아무튼 상대방 질문"};
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

        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ct));

        adapter = new HundredQnAAdapter(questionList, answerList, completeList, 2);
        recyclerView.setAdapter(adapter);

        return v;
    }
}

package com.example.witness;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HundredQnAAdapter extends RecyclerView.Adapter<HundredQnAAdapter.HundredQnAViewHolder> {
    private static ArrayList<String> questionListMine = null;
    private static ArrayList<String> questionListYou = null;
    private static ArrayList<String> answerListMine = null;
    private static ArrayList<String> answerListYou = null;
    private static ArrayList<Integer> completeListMine = null;
    private static ArrayList<Integer> completeListYou = null;
    int flag;
    Context context;
    View dialogView;

    public static class HundredQnAViewHolder extends RecyclerView.ViewHolder {
        TextView tvQnA, tvAnswer;

        HundredQnAViewHolder(View v) {
            super(v);
            tvQnA = v.findViewById(R.id.tvQnA);
            tvAnswer = v.findViewById(R.id.tvAnswer);
        }
    }

    HundredQnAAdapter(ArrayList<String> questionList, ArrayList<String> answerList, ArrayList<Integer> completeList, int flag) {
        this.flag = flag;
        if(flag==1) {
            questionListMine = questionList;
            answerListMine = answerList;
            completeListMine = completeList;
        }
        else {
            questionListYou = questionList;
            answerListYou = answerList;
            completeListYou = completeList;
        }
    }

    @NonNull
    @Override
    public HundredQnAAdapter.HundredQnAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.hundred_qna_text_view, parent, false);
        HundredQnAAdapter.HundredQnAViewHolder vh = new HundredQnAAdapter.HundredQnAViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HundredQnAAdapter.HundredQnAViewHolder holder, int position) {
        hundred_bind(holder);
    }

    @Override
    public int getItemCount() {
        if(flag == 1) {
            return questionListMine.size();
        } else {
            return questionListYou.size();
        }
    }

    private void hundred_bind(final HundredQnAAdapter.HundredQnAViewHolder holder) {
        if(flag==1) {
            holder.tvQnA.setText(questionListMine.get(holder.getLayoutPosition()));
            if(completeListMine.get(holder.getLayoutPosition()) == 1) {
                holder.tvAnswer.setText("답변완료");
            }
            else {
                holder.tvAnswer.setText("답변중");
            }
        }
        else {
            holder.tvQnA.setText(questionListYou.get(holder.getLayoutPosition()));
            if(completeListYou.get(holder.getLayoutPosition()) == 1) {
                holder.tvAnswer.setText("답변완료");
            }
            else {
                holder.tvAnswer.setText("입력");
            }
        }

        holder.tvQnA.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle("삭제");
                dlg.setMessage("삭제하시겠습니까?");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int pos = holder.getLayoutPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            if(flag==1) {
                                questionListMine.remove(pos);
                            }
                            else {
                                questionListYou.remove(pos);
                            }
                            HundredQnAAdapter.this.notifyDataSetChanged();
                        }
                    }
                });

                dlg.setNegativeButton("취소", null);
                dlg.show();

                return false;
            }
        });

        holder.tvAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dialogView = View.inflate(context, R.layout.hundred_qna_dialog, null);
                final EditText dlgEdt = dialogView.findViewById(R.id.edt1);
                final EditText dlgEdt2 = dialogView.findViewById(R.id.edt2);
                dlgEdt.setText(holder.tvQnA.getText().toString());


                if(flag==1) {
                    dlgEdt2.setEnabled(false);
                    if(completeListMine.get(holder.getLayoutPosition())==1) {
                        dlgEdt.setEnabled(false);
                        dlgEdt2.setText(answerListMine.get(holder.getLayoutPosition()));
                    }
                    else {
                        dlgEdt.setEnabled(true);
                        dlgEdt2.setText("상대방의 답변을 기다리는중입니다.");

                        dlg.setNegativeButton("수정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                questionListMine.set(holder.getLayoutPosition(), dlgEdt.getText().toString());
                                holder.tvQnA.setText(dlgEdt.getText().toString());
                                HundredQnAAdapter.this.notifyDataSetChanged();
                            }
                        });
                    }
                }
                else {
                    dlgEdt.setEnabled(false);
                    dlgEdt2.setEnabled(true);

                    if(completeListYou.get(holder.getLayoutPosition())==1) {
                        dlgEdt2.setEnabled(true);
                        dlgEdt.setText(questionListYou.get(holder.getLayoutPosition()));
                        dlgEdt2.setText(answerListYou.get(holder.getLayoutPosition()));

                        dlg.setNegativeButton("수정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                answerListYou.set(holder.getLayoutPosition(), dlgEdt2.getText().toString());
                                holder.tvAnswer.setText(dlgEdt2.getText().toString());
                                HundredQnAAdapter.this.notifyDataSetChanged();
                            }
                        });
                        dlg.setPositiveButton("확인", null);
                    }
                    else {
                        dlgEdt.setText(questionListYou.get(holder.getLayoutPosition()));
                        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                answerListYou.set(holder.getLayoutPosition(), dlgEdt2.getText().toString());
                                completeListYou.set(holder.getLayoutPosition(), 1);
                                holder.tvAnswer.setText("답변완료");
                                HundredQnAAdapter.this.notifyDataSetChanged();
                            }
                        });
                    }
                }


                dlg.setView(dialogView);

                dlg.show();

            }
        });
    }
}

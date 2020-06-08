package com.example.witness;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BucketListAdapter extends RecyclerView.Adapter<BucketListAdapter.MyViewHolder> implements ItemTouchHelperListener {
    private static ArrayList<String> dataset = null;
    Context context;
    View dialogView;

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        String moveText = dataset.get(from_position);
        dataset.remove(from_position);
        dataset.add(to_position, moveText);

        notifyItemMoved(from_position, to_position);
        return true;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bucketText;
        ImageButton btnModify, btnDelete;

        MyViewHolder(View v) {
            super(v);

            bucketText = v.findViewById(R.id.bucketText);
            btnModify = v.findViewById(R.id.btnModify);
            btnDelete = v.findViewById(R.id.btnDelete);
        }
    }

    BucketListAdapter(ArrayList<String> mDataset) {
        dataset = mDataset;
    }

    @NonNull
    @Override
    public BucketListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.bucket_list_text_view, parent, false);
        BucketListAdapter.MyViewHolder vh = new BucketListAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BucketListAdapter.MyViewHolder holder, int position) {
        bind(holder);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    private void bind(final MyViewHolder holder) {
        holder.bucketText.setText(dataset.get(holder.getLayoutPosition()));

        final int[] flag = {0, 0, 0};

        holder.bucketText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag[holder.getLayoutPosition()] == 0) {
                    holder.bucketText.setPaintFlags(holder.bucketText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    flag[holder.getLayoutPosition()] = 1;
                } else {
                    holder.bucketText.setPaintFlags(0);
                    flag[holder.getLayoutPosition()] = 0;
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle("삭제");
                dlg.setMessage("삭제하시겠습니까?");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int pos = holder.getLayoutPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            dataset.remove(pos);
                            BucketListAdapter.this.notifyDataSetChanged();
                        }
                    }
                });

                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        holder.btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle("수정");

                dialogView = View.inflate(context, R.layout.bucket_list_dialog, null);
                dlg.setView(dialogView);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText dlgEdt = dialogView.findViewById(R.id.edtText);
                        if (!dlgEdt.getText().toString().equals("")) {
                            String modifyText = dlgEdt.getText().toString();
                            dataset.set(holder.getLayoutPosition(), modifyText);
                            BucketListAdapter.this.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "수정할 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
    }
}

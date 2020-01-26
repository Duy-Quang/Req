package com.example.req;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerAdapterViewHolder> {

    private String[] mAnswerData;
    private boolean[] mTickMarkList;

    public class AnswerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mTickMark;
        public final TextView mAnswer;

        public AnswerAdapterViewHolder(View view) {
            super (view);
            mTickMark = (ImageView) view.findViewById(R.id.iv_tick_mark);
            mAnswer = (TextView) view.findViewById(R.id.tv_answer);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mTickMark.setVisibility(View.VISIBLE);
            int position = this.getLayoutPosition();
            for (int i = 0; i<4;i++) {
                mTickMarkList[i] = false;
            }
            mTickMarkList[position] = true;
            notifyDataSetChanged();
        }
    }

//    public interface AnswerAdapterOnClickHandler {
//        void onClick();
//    }
//
//    private final AnswerAdapterOnClickHandler mClickHandler;
//
//    public AnswerAdapter (AnswerAdapterOnClickHandler clickHandler){
//        mClickHandler = clickHandler;
//    }
    public AnswerAdapter (){}

    @NonNull
    @Override
    public AnswerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_list_item,parent,false);
        return new AnswerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapterViewHolder holder, int position) {
        holder.mAnswer.setText(mAnswerData[position]);
        if(mTickMarkList[position])
            holder.mTickMark.setVisibility(View.VISIBLE);
        else
            holder.mTickMark.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mAnswerData.length;
    }

    public void setAnswerData (String[] answerData) {
        mAnswerData = answerData;
        notifyDataSetChanged();
    }

    public void setTickMarkList (boolean[] tickMarkList) {
        mTickMarkList = tickMarkList;
        notifyDataSetChanged();

    }

    public int getSelectedAnswer() {
        for (int i = 0; i < 4 ; i++){
            if(mTickMarkList[i])
                return i;
        }
        return -1;
    }
}

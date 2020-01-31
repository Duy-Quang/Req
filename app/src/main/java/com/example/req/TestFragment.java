package com.example.req;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.req.data.QAContent;


public class TestFragment extends Fragment {
    private TextView mQuestionNumber;
    private TextView mQuestionContent;
    private RecyclerView mRecyclerViewAnswer;
    private AnswerAdapter mAnswerAdapter;
    private Button mOk;

    private QAContent qaContent;
    private int questionIndex ;
    private int selectedAnswer;


    public TestFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TestFragment newInstance(Bundle args) {
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();

        qaContent = new QAContent();
        qaContent.setQuestion(data.getString(TestActivity.QUESTION_CONTENT));
        qaContent.setAnswers(data.getStringArray(TestActivity.ANSWERS));
        qaContent.setCorrect_answer(data.getInt(TestActivity.CORRECT_ANSWER));

        questionIndex = data.getInt(TestActivity.QUESTION_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_test, container, false);

        mQuestionNumber = rootView.findViewById(R.id.fm_tv_question_number);
        mQuestionContent = rootView.findViewById(R.id.fm_tv_question_content);

        mRecyclerViewAnswer = rootView.findViewById(R.id.fm_recyclerview_answer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        mRecyclerViewAnswer.setLayoutManager(layoutManager);
        mRecyclerViewAnswer.setHasFixedSize(true);
        mAnswerAdapter = new AnswerAdapter();
        updateDataToUI();
        mRecyclerViewAnswer.setAdapter(mAnswerAdapter);

        mOk = rootView.findViewById(R.id.fm_b_ok);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedAnswer = mAnswerAdapter.getSelectedAnswer() + 1;
                if(selectedAnswer>0){

                    //check answer
                    String dialogMessage;
                    if(selectedAnswer == qaContent.getCorrect_answer()) {
                        dialogMessage = "OK";
                        ((TestActivity)getActivity()).increaseCorrectAnswerCount();
                    }else{
                        dialogMessage= "NG";
                    }

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    ConfirmDialog confirmDialog = ConfirmDialog.newInstance(dialogMessage,(TestActivity)getActivity());
                    confirmDialog.show(fm,null);

                }
                else{
                    Toast.makeText(getActivity(),"No answer selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }

    private void updateDataToUI() {
        //set question index
        mQuestionNumber.setText(Integer.toString(questionIndex) + "/" );


        //set question
        mQuestionContent.setText(qaContent.getQuestion());

        //set Answers
        mAnswerAdapter.setAnswerData(qaContent.getAnswers());
        boolean [] tickMarkList = {false,false,false,false};
        mAnswerAdapter.setTickMarkList(tickMarkList);
        mAnswerAdapter.notifyDataSetChanged();
    }
}

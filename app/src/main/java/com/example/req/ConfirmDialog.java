package com.example.req;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ConfirmDialog extends DialogFragment implements View.OnClickListener {
    private TextView mContent;
    private Button mConfirm;

    private ConfirmDialogOnClickListener mClickHandler;

    public interface ConfirmDialogOnClickListener {
        void onConfirmClick();
    }

    public ConfirmDialog(ConfirmDialogOnClickListener clickHandler){
        mClickHandler = clickHandler;
    }

    public static ConfirmDialog newInstance(String data, ConfirmDialogOnClickListener clickHandler) {
        ConfirmDialog confirmDialog = new ConfirmDialog(clickHandler);
        Bundle args = new Bundle();
        args.putString("data",data);
        confirmDialog.setArguments(args);
        return confirmDialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alert_diaglog,container);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String data = getArguments().getString("data","");

        mContent = (TextView) view.findViewById(R.id.tv_diaglog_content);
        mConfirm = (Button) view.findViewById(R.id.b_dialog_confirm);

        mContent.setText(data);
        mConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mClickHandler.onConfirmClick();
    }
}

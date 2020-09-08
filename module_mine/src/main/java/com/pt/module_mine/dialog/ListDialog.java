package com.pt.module_mine.dialog;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pt.module_mine.R;
import com.pt.module_mine.adpter.ContentAdapter;

import java.util.List;

public class ListDialog extends AlertDialog {

    private List<String> list;

    private String mTitle;
    private Context context;
    private TextView textview;
    private RecyclerView recyclerView;
    private ContentAdapter contentAdapter;

    public ListDialog(Context context, int themeResId, String title, ContentAdapter contentAdapter) {
        super(context, themeResId);
        this.context = context;
        this.mTitle = title;
        this.contentAdapter = contentAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_list);
        setCanceledOnTouchOutside(true);
        initView();
        initData();
        initEvent();
    }

    public void initView() {
        textview = findViewById(R.id.dialog_title);
        recyclerView = findViewById(R.id.dialog_content);
    }

    public void initData() {
        textview.setText(mTitle);
    }

    public void initEvent() {
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(contentAdapter);
    }
}

package com.example.emoji.person;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.emoji.R;
import com.example.emoji.base.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * 创建时间:2020/6/16
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class PersonAdapter extends BaseRecyclerViewAdapter<String> {

    private Context context;

    public PersonAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseRecyclerViewAdapter.BaseRecyclerViewHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person, parent,false);
        return new PersonHolder(view);
    }

    private static final String TAG = "PersonAdapter";
    @Override
    protected void bindData(BaseRecyclerViewHolder holder, String data) {
        PersonHolder personHolder = (PersonHolder) holder;
        Log.d(TAG, "----bindData: "+data);
        personHolder.textView.setText(data);
        personHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    static class PersonHolder extends BaseRecyclerViewHolder {
        public TextView textView;

        public PersonHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void findView(View v) {
            textView = v.findViewById(R.id.tv_title);
        }
    }
}

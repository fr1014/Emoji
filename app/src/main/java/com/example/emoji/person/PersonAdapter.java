package com.example.emoji.person;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.emoji.R;
import com.example.emoji.base.BaseRecyclerViewAdapter;
import com.example.emoji.community.CommunityFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * 创建时间:2020/6/16
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class PersonAdapter extends BaseRecyclerViewAdapter<String> {

    private Context context;
    private PersonViewModel viewModel;
    private List<Integer> imageRes = new ArrayList<>();
    private FragmentManager manager;

    public PersonAdapter(Context context, FragmentManager manager) {
        this.context = context;
        this.manager = manager;
    }

    public void setImageRes(List<Integer> imageRes) {
        this.imageRes = imageRes;
    }

    public void setPersonViewModel(PersonViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public BaseRecyclerViewAdapter.BaseRecyclerViewHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false);
        return new PersonHolder(view);
    }

    private static final String TAG = "PersonAdapter";

    @Override
    protected void bindData(BaseRecyclerViewHolder holder, String data) {
        PersonHolder personHolder = (PersonHolder) holder;
        Log.d(TAG, "----bindData: " + data);
        int index = mData.indexOf(data);
        personHolder.ivIcon.setImageResource(imageRes.get(index));
        personHolder.textView.setText(data);

        personHolder.itemView.setOnClickListener(v -> {
            switch (index){
                case 0:
                    manager.beginTransaction().add(R.id.rootView, CommunityFragment.getInstance(true)).addToBackStack(null).commit();
                    break;
                case 7:
                    viewModel.logOut();
                    break;
            }
        });

    }

    static class PersonHolder extends BaseRecyclerViewHolder {
        public ImageView ivIcon;
        public TextView textView;

        public PersonHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void findView(View v) {
            ivIcon = v.findViewById(R.id.iv_icon);
            textView = v.findViewById(R.id.tv_title);
        }
    }
}

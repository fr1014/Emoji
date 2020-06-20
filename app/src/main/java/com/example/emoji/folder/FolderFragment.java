package com.example.emoji.folder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.customview.MotionEventImageView;
import com.example.emoji.R;
import com.example.emoji.base.BaseFragment;

import java.util.List;


public class FolderFragment extends BaseFragment<FolderViewModel> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private FolderAdapter adapter;
    public MotionEventImageView motionView;
    public FolderViewModel viewModel;

    public FolderFragment() {
        // Required empty public constructor
    }

    public static FolderFragment getInstance() {
        FolderFragment fragment = new FolderFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(getActivity()).get(FolderViewModel.class);

        viewModel.getGetAllFoldersLive().observe(this, folderEntities -> adapter.setData(folderEntities));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_folder;
    }

    @Override
    protected void initView(View view) {
        motionView = view.findViewById(R.id.iv_motion);

        recyclerView = view.findViewById(R.id.rv_folder);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new FolderAdapter(getContext(),this);
        recyclerView.setAdapter(adapter);

        motionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new AddFolderFragment());
                motionView.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    protected void initData() {

    }

    public void addFragment(Fragment fragment){
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private static final String TAG = "FolderFragment";
    @Override
    public boolean onBackPressed() {
        List<Fragment> fragments = getChildFragmentManager().getFragments();

        for (Fragment fragment : fragments) {
            /*如果是自己封装的Fragment的子类  判断是否需要处理返回事件*/
            if (fragment instanceof com.example.emoji.base.BaseFragment) {
                if (((com.example.emoji.base.BaseFragment) fragment).onBackPressed()) {
                    /*在Fragment中处理返回事件*/
                    Log.d(TAG, "----onBackPressed: ");
                    motionView.setVisibility(View.VISIBLE);
                    return true;
                }
            }
        }
        return false;
    }
}
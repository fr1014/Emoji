package com.example.emoji.folder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.customview.MotionEventImageView;
import com.example.emoji.R;


public class FolderFragment extends Fragment {

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
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
        viewModel = new ViewModelProvider(getActivity()).get(FolderViewModel.class);

        viewModel.getGetAllFoldersLive().observe(this, folderEntities -> adapter.setData(folderEntities));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_folder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view){
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

    public void addFragment(Fragment fragment){
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

}
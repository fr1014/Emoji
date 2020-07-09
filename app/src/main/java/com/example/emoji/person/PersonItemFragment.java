package com.example.emoji.person;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emoji.R;

public class PersonItemFragment extends Fragment {

    public PersonItemFragment() {
        // Required empty public constructor
    }

    public static PersonItemFragment newInstance() {
        PersonItemFragment fragment = new PersonItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_person_item, container, false);
    }
}
package com.mkandeel.retrofit_rxjava_.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mkandeel.retrofit_rxjava_.databinding.FragmentViewPostBinding;
import com.mkandeel.retrofit_rxjava_.R;


public class ViewPostFragment extends Fragment {

    public ViewPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FragmentViewPostBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentViewPostBinding.inflate(inflater,container,false);

        binding.post.txtComments.setVisibility(View.GONE);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = requireActivity().getSupportFragmentManager();
                manager.popBackStack();
            }
        });

        return binding.getRoot();
    }
}
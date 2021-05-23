package com.example.pacemaker.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.enums.FragmentTypes;

public class FindPasswordSuccessFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.auth_fragment_find_pw_success, container, false);
        String name = getArguments().getString("name");
        String email = getArguments().getString("email");
        TextView textViewName = rootView.findViewById(R.id.text_info1);
        TextView textViewEmail = rootView.findViewById(R.id.text_info3);

        textViewName.setText(String.format("%s님의 아래 이메일로", name));
        textViewEmail.setText(email);

        Button loginBtn = rootView.findViewById(R.id.button_login);
        loginBtn.setOnClickListener((view) -> {
            ((MainActivity)requireActivity()).setFragment(FragmentTypes.LOGIN);
        });
        return rootView;
    }
}

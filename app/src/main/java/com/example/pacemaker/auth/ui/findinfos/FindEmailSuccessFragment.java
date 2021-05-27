package com.example.pacemaker.auth.ui.findinfos;

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
import com.example.pacemaker.auth.AuthActivity;
import com.example.pacemaker.auth.enums.FragmentTypes;

public class FindEmailSuccessFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.auth_fragment_find_email_success, container, false);
        String name = getArguments().getString("name");
        String email = getArguments().getString("email");
        TextView textViewName = rootView.findViewById(R.id.text_info1);
        TextView textViewEmail = rootView.findViewById(R.id.text_info2);

        textViewName.setText(String.format("%s님의 아이디입니다", name));
        textViewEmail.setText(email);

        Button loginBtn = rootView.findViewById(R.id.button_login);
        loginBtn.setOnClickListener((view) -> {
            ((AuthActivity)requireActivity()).setFragment(FragmentTypes.LOGIN);
        });
        return rootView;
    }
}

package com.example.pacemaker.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.enums.FragmentTypes;

public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.auth_fragment_main, container, false);
        Button signInBtn = (Button)rootView.findViewById(R.id.auth_button_login);
        Button signUpBtn = (Button)rootView.findViewById(R.id.auth_button_signup);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthActivity)requireActivity()).setFragment(FragmentTypes.LOGIN);
                //((MainActivity)requireActivity()).setFragment(FragmentTypes.SIGN_UP_SUCCESS);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthActivity)requireActivity()).setFragment(FragmentTypes.SIGNUP);

            }
        });

        return rootView;
    }
}

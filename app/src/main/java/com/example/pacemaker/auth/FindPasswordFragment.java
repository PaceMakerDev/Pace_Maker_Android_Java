package com.example.pacemaker.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.models.FindPwRequestDto;

public class FindPasswordFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.auth_fragment_find_password, container, false);

        EditText editTextName = rootView.findViewById(R.id.edit_auth_name);


        EditText editTextEmail = rootView.findViewById(R.id.edit_auth_email);


        Button findPasswordBtn = rootView.findViewById(R.id.btn_find_pw);
        findPasswordBtn.setOnClickListener((view) -> {
            String name= editTextName.getText().toString();
            String email = editTextEmail.getText().toString();
            Log.d("Auth", String.format("%s, %s", name, email));
            FindPwRequestDto findPwRequestDto = new FindPwRequestDto(name, email);
            ((MainActivity)requireActivity()).requestFindPassword(findPwRequestDto);
        });
        return rootView;
    }
}

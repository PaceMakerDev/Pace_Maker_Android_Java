package com.example.pacemaker.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.models.FindEmailRequestDto;

public class ChangePasswordFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.auth_fragment_change_password, container, false);

        TextView textEmail = rootView.findViewById(R.id.auth_text_email);
        textEmail.setText(getArguments().getString("email"));
        EditText editNewPassword = rootView.findViewById(R.id.edit_auth_new_password);
        EditText editConfirmPassword = rootView.findViewById(R.id.auth_edit_confirm_password);

        Button findEmailBtn = rootView.findViewById(R.id.btn_login);
        findEmailBtn.setOnClickListener((view) -> {
            String newPassword= editNewPassword.getText().toString();
            String confirmPassword = editConfirmPassword.getText().toString();

            //API 업데이트 시 이부분과 Model, Service, requestFunction() 작성
            //FindEmailRequestDto findEmailRequestDto = new FindEmailRequestDto(newPassword, confirmPassword);
            //((MainActivity)requireActivity()).requestFindEmail(findEmailRequestDto);
        });
        return rootView;
    }
}

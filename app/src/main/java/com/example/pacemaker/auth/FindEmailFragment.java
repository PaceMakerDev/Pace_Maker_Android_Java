package com.example.pacemaker.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.models.FindEmailRequestDto;

public class FindEmailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.auth_fragment_find_email, container, false);
        EditText editTextName = rootView.findViewById(R.id.edit_auth_name);
        EditText editTextStdId = rootView.findViewById(R.id.edit_auth_student_id);

        Button findEmailBtn = rootView.findViewById(R.id.btn_find_email);
        findEmailBtn.setOnClickListener((view) -> {
            String name= editTextName.getText().toString();
            String studentId = editTextStdId.getText().toString();
            FindEmailRequestDto findEmailRequestDto = new FindEmailRequestDto(name, studentId);
            ((MainActivity)requireActivity()).requestFindEmail(findEmailRequestDto);
        });
        return rootView;
    }
}

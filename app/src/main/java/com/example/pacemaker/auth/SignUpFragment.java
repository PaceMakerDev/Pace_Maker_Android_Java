package com.example.pacemaker.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;

public class SignUpFragment extends Fragment {
    private final int CAMERA_ACTIVITY_REQ_CODE = 15555;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_auth_signup, container, false);
        startActivityForResult(new Intent(requireActivity(), CameraActivity.class), CAMERA_ACTIVITY_REQ_CODE);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_ACTIVITY_REQ_CODE) {
            assert data != null;
            String name = data.getExtras().getString(CameraActivity.NAME);
            String major = data.getExtras().getString(CameraActivity.MAJOR);
            int studentId = data.getExtras().getInt(CameraActivity.STUDENT_ID);

            setStudentInfo(name, major, Integer.toString(studentId));
        }
    }

    public void setStudentInfo(String name, String major, String studentId) {
        ((EditText)requireView().findViewById(R.id.signup_edit_name)).setText(name);
        ((EditText)requireView().findViewById(R.id.signup_edit_major)).setText(major);
        ((EditText)requireView().findViewById(R.id.signup_edit_student_id)).setText(studentId);
    }
}

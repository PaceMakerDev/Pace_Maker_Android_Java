package com.example.pacemaker.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;

public class SignUpFragment extends Fragment {
    public static final int CAMERA_ACTIVITY_REQ_CODE = 15555;
    public static final int AGREEMENT_ACTIVITY_REQ_CODE = 13333;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.auth_fragment_signup, container, false);
        startActivityForResult(new Intent(requireActivity(), CameraActivity.class), CAMERA_ACTIVITY_REQ_CODE);

        TextView showAgreementBtn = rootView.findViewById(R.id.auth_text_show_agreement);
        showAgreementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(requireActivity(), AgreementActivity.class), AGREEMENT_ACTIVITY_REQ_CODE);
            }
        });

        EditText email_input = rootView.findViewById(R.id.signup_edit_email);
        TextView email_error = rootView.findViewById(R.id.signup_text_error_email);
        email_input.addTextChangedListener(new EmailTextWatcher(email_error, getResources()));

        EditText pw_input = rootView.findViewById(R.id.signup_edit_pw);
        TextView pw_input_error = rootView.findViewById(R.id.signup_text_error_pw);
        pw_input.addTextChangedListener(new PasswordTextWatcher(pw_input_error, getResources()));

        EditText pw_check = rootView.findViewById(R.id.signup_edit_pw_check);
        TextView pw_check_error = rootView.findViewById(R.id.signup_text_error_pw_check);
        pw_check.addTextChangedListener(new PasswordCheckTextWatcher(pw_check_error, pw_input, getResources()));

        RadioGroup attendanceRadioGroup = rootView.findViewById(R.id.auth_radio_gorup_attendance);

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

        else if (resultCode == Activity.RESULT_OK && requestCode == AGREEMENT_ACTIVITY_REQ_CODE) {
            assert data != null;
            ((CheckBox)requireView().findViewById(R.id.auth_checkbox_agreement)).setChecked(true);
        }
    }

    public void setStudentInfo(String name, String major, String studentId) {
        ((EditText)requireView().findViewById(R.id.signup_edit_name)).setText(name);
        ((EditText)requireView().findViewById(R.id.signup_edit_major)).setText(major);
        ((EditText)requireView().findViewById(R.id.signup_edit_student_id)).setText(studentId);
    }
}

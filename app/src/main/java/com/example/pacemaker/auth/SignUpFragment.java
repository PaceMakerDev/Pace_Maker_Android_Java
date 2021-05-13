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
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {
    public static final int CAMERA_ACTIVITY_REQ_CODE = 15555;
    public static final int AGREEMENT_ACTIVITY_REQ_CODE = 13333;
    private Map<String, View> viewMap;
    private boolean isAttending = true;
    private boolean isAgreed = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.auth_fragment_signup, container, false);
        startActivityForResult(new Intent(requireActivity(), CameraActivity.class), CAMERA_ACTIVITY_REQ_CODE);

       setUpViews(rootView);
        RadioGroup attendanceRadioGroup = rootView.findViewById(R.id.auth_radio_gorup_attendance);

        return rootView;
    }

    private void setUpViews(View view) {
        mapEditViews(view);
        setErrorMessages(view);
        mapClickListeners(view);

    }

    private void mapEditViews(View view) {
        viewMap = new HashMap<String, View>();

        viewMap.put("name", view.findViewById(R.id.signup_edit_name));
        viewMap.put("major", view.findViewById(R.id.signup_edit_major));
        viewMap.put("studentId", view.findViewById(R.id.signup_edit_student_id));
        viewMap.put("email", view.findViewById(R.id.signup_edit_email));
        viewMap.put("pw", view.findViewById(R.id.signup_edit_pw));
        viewMap.put("pwCheck", view.findViewById(R.id.signup_edit_pw_check));
        viewMap.put("birthYear", view.findViewById(R.id.signup_edit_birth_year));
        viewMap.put("birthMonth", view.findViewById(R.id.signup_edit_birth_month));
        viewMap.put("birthDay", view.findViewById(R.id.signup_edit_birth_day));
    }

    private void setErrorMessages(View view) {
        EditText email_input = (EditText)viewMap.get("email");
        TextView email_error = view.findViewById(R.id.signup_text_error_email);
        email_input.addTextChangedListener(new EmailTextWatcher(email_error, getResources()));

        EditText pw_input = (EditText)viewMap.get("pw");
        TextView pw_input_error = view.findViewById(R.id.signup_text_error_pw);
        pw_input.addTextChangedListener(new PasswordTextWatcher(pw_input_error, getResources()));

        EditText pw_check = (EditText)viewMap.get("pwCheck");
        TextView pw_check_error = view.findViewById(R.id.signup_text_error_pw_check);
        pw_check.addTextChangedListener(new PasswordCheckTextWatcher(pw_check_error, pw_input, getResources()));
    }

    private void mapClickListeners(View view) {
        TextView showAgreementBtn = view.findViewById(R.id.auth_text_show_agreement);
        showAgreementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(requireActivity(), AgreementActivity.class), AGREEMENT_ACTIVITY_REQ_CODE);
            }
        });

        DialogFragment dateFragment = new BirthdayPickFragment((TextView)viewMap.get("birthYear"),
                (TextView)viewMap.get("birthMonth"),
                (TextView)viewMap.get("birthDay"));
        View.OnClickListener birthdayClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateFragment.show(requireActivity().getSupportFragmentManager(), "BirthdayPickFragment");
            }
        };
        viewMap.get("birthYear").setOnClickListener(birthdayClickListener);
        viewMap.get("birthMonth").setOnClickListener(birthdayClickListener);
        viewMap.get("birthDay").setOnClickListener(birthdayClickListener);
    }

    public void requestSignUp() {

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

package com.example.pacemaker.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.enums.AttendanceStatus;
import com.example.pacemaker.auth.enums.SignUpInputs;
import com.example.pacemaker.auth.textwatchers.EmailTextWatcher;
import com.example.pacemaker.auth.textwatchers.InputTextWatcher;
import com.example.pacemaker.auth.textwatchers.PasswordCheckTextWatcher;
import com.example.pacemaker.auth.textwatchers.PasswordTextWatcher;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {
    public static final int CAMERA_ACTIVITY_REQ_CODE = 15555;
    public static final int AGREEMENT_ACTIVITY_REQ_CODE = 13333;
    private Map<SignUpInputs, View> viewMap;
    private AttendanceStatus attendance = AttendanceStatus.EMPTY;
    private boolean isAgreed = false;
    private Map<SignUpInputs, Boolean> validationMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.auth_fragment_signup, container, false);
        startActivityForResult(new Intent(requireActivity(), CameraActivity.class), CAMERA_ACTIVITY_REQ_CODE);

        setUpViews(rootView);


        return rootView;
    }

    private void setUpViews(View view) {
        mapEditViews(view);
        mapValidations();
        setErrorMessages(view);
        mapClickListeners(view);
    }

    private void mapEditViews(View view) {
        viewMap = new HashMap<SignUpInputs, View>();
        viewMap.put(SignUpInputs.NAME, view.findViewById(R.id.signup_edit_name));
        viewMap.put(SignUpInputs.MAJOR, view.findViewById(R.id.signup_edit_major));
        viewMap.put(SignUpInputs.STUDENT_ID, view.findViewById(R.id.signup_edit_student_id));
        viewMap.put(SignUpInputs.EMAIL, view.findViewById(R.id.signup_edit_email));
        viewMap.put(SignUpInputs.PW, view.findViewById(R.id.signup_edit_pw));
        viewMap.put(SignUpInputs.PW_CHECK, view.findViewById(R.id.signup_edit_pw_check));
        viewMap.put(SignUpInputs.BIRTH_YEAR, view.findViewById(R.id.signup_edit_birth_year));
        viewMap.put(SignUpInputs.BIRTH_MONTH, view.findViewById(R.id.signup_edit_birth_month));
        viewMap.put(SignUpInputs.BIRTH_DAY, view.findViewById(R.id.signup_edit_birth_day));
    }

    private void mapValidations() {
        validationMap = new HashMap<SignUpInputs, Boolean>();
        for (SignUpInputs input : SignUpInputs.values()) {
            validationMap.put(input, false);
        }
    }

    private void setErrorMessages(View view) {
        EditText email_input = (EditText)viewMap.get(SignUpInputs.EMAIL);
        TextView email_error = view.findViewById(R.id.signup_text_error_email);
        email_input.addTextChangedListener(new EmailTextWatcher(email_error, getResources(), this));

        EditText pw_input = (EditText)viewMap.get(SignUpInputs.PW);
        TextView pw_input_error = view.findViewById(R.id.signup_text_error_pw);
        pw_input.addTextChangedListener(new PasswordTextWatcher(pw_input_error, getResources(), this));

        EditText pw_check = (EditText)viewMap.get(SignUpInputs.PW_CHECK);
        TextView pw_check_error = view.findViewById(R.id.signup_text_error_pw_check);
        pw_check.addTextChangedListener(new PasswordCheckTextWatcher(pw_check_error, pw_input, getResources(), this));
    }

    private void mapClickListeners(View view) {
        TextView showAgreementBtn = view.findViewById(R.id.auth_text_show_agreement);
        showAgreementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(requireActivity(), AgreementActivity.class), AGREEMENT_ACTIVITY_REQ_CODE);
            }
        });

        DialogFragment dateFragment = new BirthdayPickFragment((TextView)viewMap.get(SignUpInputs.BIRTH_YEAR),
                (TextView)viewMap.get(SignUpInputs.BIRTH_MONTH),
                (TextView)viewMap.get(SignUpInputs.BIRTH_DAY), this);

        View.OnClickListener birthdayClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateFragment.show(requireActivity().getSupportFragmentManager(), "BirthdayPickFragment");
            }
        };
        viewMap.get(SignUpInputs.BIRTH_YEAR).setOnClickListener(birthdayClickListener);
        viewMap.get(SignUpInputs.BIRTH_MONTH).setOnClickListener(birthdayClickListener);
        viewMap.get(SignUpInputs.BIRTH_DAY).setOnClickListener(birthdayClickListener);

        RadioGroup attendanceRadioGroup = view.findViewById(R.id.auth_radio_gorup_attendance);
        attendanceRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.radio_attend)
                attendance = AttendanceStatus.ATTENDING;
            else if (i == R.id.radio_take_off)
                attendance = AttendanceStatus.TAKE_OFF;
            updateSignUpButton();
        });
    }

    public void updateSignUpButton() {
        Button btn = requireView().findViewById(R.id.auth_button_request_signup);
        if (validateInputs()) {
            btn.setEnabled(true);
            btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_filled_maintheme, null));
        } else {
            btn.setEnabled(false);
            btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_filled_disabled, null));
        }
    }

    public boolean validateInputs() {
        for (SignUpInputs key : validationMap.keySet()) {
            if (!validationMap.get(key)) {
                return false;
            }
        }
        if (attendance == AttendanceStatus.EMPTY)
            return false;
        if (!isAgreed)
            return false;
        return true;
    }

    public void requestSignUp() {

    }

    public void setValidation(SignUpInputs input, boolean result) {
        validationMap.put(input, result);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_ACTIVITY_REQ_CODE) {
            assert data != null;
            String name = data.getExtras().getString(CameraActivity.NAME);
            String major = data.getExtras().getString(CameraActivity.MAJOR);
            int studentId = data.getExtras().getInt(CameraActivity.STUDENT_ID);
            setValidation(SignUpInputs.NAME, true);
            setValidation(SignUpInputs.MAJOR, true);
            setValidation(SignUpInputs.STUDENT_ID, true);

            setStudentInfo(name, major, Integer.toString(studentId));
            updateSignUpButton();
        }

        else if (resultCode == Activity.RESULT_OK && requestCode == AGREEMENT_ACTIVITY_REQ_CODE) {
            assert data != null;
            ((CheckBox)requireView().findViewById(R.id.auth_checkbox_agreement)).setChecked(true);
            isAgreed = true;
            updateSignUpButton();
        }
    }

    public void setStudentInfo(String name, String major, String studentId) {
        ((EditText)requireView().findViewById(R.id.signup_edit_name)).setText(name);
        ((EditText)requireView().findViewById(R.id.signup_edit_major)).setText(major);
        ((EditText)requireView().findViewById(R.id.signup_edit_student_id)).setText(studentId);
    }
}

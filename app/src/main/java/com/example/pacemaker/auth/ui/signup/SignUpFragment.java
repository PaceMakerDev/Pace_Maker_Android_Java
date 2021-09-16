package com.example.pacemaker.auth.ui.signup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.AuthActivity;
import com.example.pacemaker.auth.enums.AttendanceStatus;
import com.example.pacemaker.auth.enums.SignUpInputs;
import com.example.pacemaker.auth.models.SignUpDto;
import com.example.pacemaker.auth.textwatchers.BaseTextWatcher;
import com.example.pacemaker.auth.textwatchers.EmailTextWatcher;
import com.example.pacemaker.auth.textwatchers.PasswordCheckTextWatcher;
import com.example.pacemaker.auth.textwatchers.PasswordTextWatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class SignUpFragment extends Fragment {
    public static final int AGREEMENT_ACTIVITY_REQ_CODE = 13333;
    private Map<SignUpInputs, View> viewMap;
    private AttendanceStatus attendance = AttendanceStatus.EMPTY;
    private boolean isAgreed = false;
    private Map<SignUpInputs, Boolean> validationMap;
    private SubjectRecyclerViewAdapter adapter;
    private ArrayList<String> humanityList;
    private ArrayList<String> businessList;
    private ArrayList<String> natureList;
    private OnBackPressedCallback backPressedCallback;
    private boolean isSubjectPopUp = false;
    private String subjectName = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.auth_fragment_signup, container, false);

        setUpViews(rootView);


        return rootView;
    }

    private void setUpViews(View view) {
        loadSubjects();
        setSubjectLayout(view);
        mapEditViews(view);
        mapValidations();
        setErrorMessages(view);
        mapClickListeners(view);
        subjectSetUp(view);

        TextView humanity = view.findViewById(R.id.text_subject_humanity);
        humanity.performClick();

    }

    private void loadSubjects() {
        humanityList = new ArrayList<String>();
        businessList = new ArrayList<String>();
        natureList = new ArrayList<String>();
        String [] humanities = getResources().getString(R.string.subject_humanity_list).split("/");
        String [] businesses = getResources().getString(R.string.subject_business_list).split("/");
        String [] natures = getResources().getString(R.string.subject_nature_list).split("/");

        humanityList.addAll(Arrays.asList(humanities));
        businessList.addAll(Arrays.asList(businesses));
        natureList.addAll(Arrays.asList(natures));
    }

    private void setSubjectLayout(View view) {
        setSubjectLayoutButtons(view);
        RecyclerView studyRecyclerView = view.findViewById(R.id.recyclerview_subject);
        TextView textSubject = view.findViewById(R.id.text_subject);
        SignUpMediator signUpMediator = ((AuthActivity)requireActivity()).getSignUpMediator();
        adapter = new SubjectRecyclerViewAdapter(getResources(), textSubject, signUpMediator);
        studyRecyclerView.setAdapter(adapter);
        studyRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
    }

    private void setSubjectLayoutButtons(View view) {
        TextView humanity = view.findViewById(R.id.text_subject_humanity);
        TextView business = view.findViewById(R.id.text_subject_business);
        TextView nature = view.findViewById(R.id.text_subject_nature);

        humanity.setOnClickListener(v -> {
            adapter.setSubject(humanityList);
            adapter.notifyDataSetChanged();
            humanity.setTextColor(getResources().getColor(R.color.text_general, null));
            business.setTextColor(getResources().getColor(R.color.textUnselected, null));
            nature.setTextColor(getResources().getColor(R.color.textUnselected, null));
        });
        business.setOnClickListener(v -> {
            adapter.setSubject(businessList);
            adapter.notifyDataSetChanged();
            business.setTextColor(getResources().getColor(R.color.text_general, null));
            humanity.setTextColor(getResources().getColor(R.color.textUnselected, null));
            nature.setTextColor(getResources().getColor(R.color.textUnselected, null));
        });
        nature.setOnClickListener(v -> {
            adapter.setSubject(natureList);
            adapter.notifyDataSetChanged();
            nature.setTextColor(getResources().getColor(R.color.text_general, null));
            business.setTextColor(getResources().getColor(R.color.textUnselected, null));
            humanity.setTextColor(getResources().getColor(R.color.textUnselected, null));
        });
    }

    private void mapEditViews(View view) {
        viewMap = new HashMap<SignUpInputs, View>();
        viewMap.put(SignUpInputs.NAME, view.findViewById(R.id.edit_auth_name));
        viewMap.put(SignUpInputs.MAJOR, view.findViewById(R.id.text_subject));
        viewMap.put(SignUpInputs.STUDENT_ID, view.findViewById(R.id.edit_auth_student_id));
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
        EditText name = (EditText)viewMap.get(SignUpInputs.NAME);
        name.addTextChangedListener(new BaseTextWatcher(this, SignUpInputs.NAME));
        EditText studentID = (EditText)viewMap.get(SignUpInputs.STUDENT_ID);
        studentID.addTextChangedListener(new BaseTextWatcher(this, SignUpInputs.STUDENT_ID));

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

        Button requestBtn = view.findViewById(R.id.auth_button_request_signup);
        requestBtn.setOnClickListener((view1 -> {
            requestSignUp();
        }));
    }

    private void subjectSetUp(View view) {
        ConstraintLayout layoutSubject = view.findViewById(R.id.layout_popup);
        TextView textStudyCategory = view.findViewById(R.id.text_subject);
        backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isSubjectPopUp) {
                    hideSubjectLayout(layoutSubject);
                    isSubjectPopUp = false;
                }
                else {
                    backPressedCallback.remove();
                    requireActivity().onBackPressed();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), backPressedCallback);
        textStudyCategory.setOnClickListener(v -> {
            if (isSubjectPopUp == false) {
                showSubjectLayout(layoutSubject);
                isSubjectPopUp = true;
            }
        });

        ConstraintLayout container = view.findViewById(R.id.container);
        container.setOnClickListener(v -> {
            if (isSubjectPopUp){
                hideSubjectLayout(layoutSubject);
                isSubjectPopUp = false;
            }
        });

    }

    private void showSubjectLayout(ConstraintLayout layoutSubject) {
        Animation layoutAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_to_center);
        layoutSubject.setVisibility(View.VISIBLE);
        layoutSubject.startAnimation(layoutAnim);
    }

    private void hideSubjectLayout(ConstraintLayout layoutSubject) {
        Animation layoutAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.from_center_to_bottom);
        layoutSubject.startAnimation(layoutAnim);
        layoutSubject.setVisibility(View.GONE);
    }

    public void updateSignUpButton() {
        Button btn = requireView().findViewById(R.id.auth_button_request_signup);
        if (validateInputs()) {
            btn.setEnabled(true);
            btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_filled_maintheme, null));
            btn.setTextColor(getResources().getColor(R.color.buttonFilledText, null));
            btn.setEnabled(true);
        } else {
            btn.setEnabled(false);
            btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_filled_disabled, null));
            btn.setTextColor(getResources().getColor(R.color.buttonDisabledText, null));
            btn.setEnabled(false);
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
        String name = ((EditText)viewMap.get(SignUpInputs.NAME)).getText().toString();
        String major = subjectName;
        String studentId = ((EditText)viewMap.get(SignUpInputs.STUDENT_ID)).getText().toString();
        String email = ((EditText)viewMap.get(SignUpInputs.EMAIL)).getText().toString();
        String pw = ((EditText)viewMap.get(SignUpInputs.PW)).getText().toString();
        String attend = attendance.name();
        String birthYear = ((TextView)viewMap.get(SignUpInputs.BIRTH_YEAR)).getText().toString();
        String birthMonth = ((TextView)viewMap.get(SignUpInputs.BIRTH_MONTH)).getText().toString();
        String birthDay = ((TextView)viewMap.get(SignUpInputs.BIRTH_DAY)).getText().toString();
        birthDay = String.format("%02d", Integer.parseInt(birthDay));
        String birth = String.format("%s-%s-%s", birthYear, birthMonth, birthDay);
        SignUpDto signUpDto = new SignUpDto(email, name, major, studentId, pw, birth, attend);

        ((AuthActivity)requireActivity()).requestSignUp(signUpDto);
    }

    public void setValidation(SignUpInputs input, boolean result) {
        validationMap.put(input, result);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == AGREEMENT_ACTIVITY_REQ_CODE) {
            assert data != null;
            ((CheckBox)requireView().findViewById(R.id.auth_checkbox_agreement)).setChecked(true);
            isAgreed = true;
            updateSignUpButton();
        }
    }

    public void selectSubject(String name) {
        subjectName = name;
        setValidation(SignUpInputs.MAJOR, true);
        updateSignUpButton();
    }
}

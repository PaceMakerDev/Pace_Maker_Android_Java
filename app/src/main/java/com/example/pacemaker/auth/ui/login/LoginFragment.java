package com.example.pacemaker.auth.ui.login;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.AuthActivity;
import com.example.pacemaker.auth.enums.FragmentTypes;
import com.example.pacemaker.auth.models.SignInDto;

import java.util.HashMap;

public class LoginFragment extends Fragment {
    private final HashMap<String, EditText> inputMap = new HashMap<String, EditText>();
    private final HashMap<String, TextView> errorMsgMap = new HashMap<String, TextView>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.auth_fragment_login, container, false);
        initialSetUp(rootView);

        Button signInBtn = (Button)rootView.findViewById(R.id.button_login);
        signInBtn.setOnClickListener(new SignInClickListener());

        TextView findEmail = rootView.findViewById(R.id.auth_button_forgot_email);
        findEmail.setOnClickListener((view) -> {
            ((AuthActivity)requireActivity()).setFragment(FragmentTypes.FIND_EMAIL, null);
        });

        TextView findPassword = rootView.findViewById(R.id.auth_button_forgot_pw);
        findPassword.setOnClickListener((view) -> {
            ((AuthActivity)requireActivity()).setFragment(FragmentTypes.FIND_PW, null);
        });

        return rootView;
    }

    private void initialSetUp(View view) {
        try {
            inputMap.put("email", view.findViewById(R.id.login_edittext_email));
            errorMsgMap.put("email", view.findViewById(R.id.login_error_text_email));
            inputMap.put("pw", view.findViewById(R.id.login_edittext_pw));
            errorMsgMap.put("pw", view.findViewById(R.id.login_error_text_pw));
        } catch (Exception e) {
            e.printStackTrace();
        }


        inputMap.get("email").setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    ((Button)view.findViewById(R.id.button_login)).callOnClick();
                    return true;
                }
                return false;
            }
        });
    }

    private class SignInClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            resetErrorMessages();
            if (!isInputBlank()) {
                requestSignIn();
            }
        }

        public void resetErrorMessages() {
            for (String key : errorMsgMap.keySet()) {
                errorMsgMap.get(key).setVisibility(View.GONE);
            }
        }

        public boolean isInputBlank() {
            boolean isValid = false;
            for (String key : inputMap.keySet()) {
                if (inputMap.get(key).getText().toString().isEmpty()) {
                    isValid = true;
                    String msg;
                    if (key.compareTo("email") == 0)
                        msg = "이메일을 입력해 주세요";
                    else
                        msg = "비밀번호를 입력해 주세요";

                    errorMsgMap.get(key).setText(msg);
                    errorMsgMap.get(key).setVisibility(View.VISIBLE);
                }
            }
            return isValid;
        }

        public void requestSignIn() {
            String email = inputMap.get("email").getText().toString();
            String pw = inputMap.get("pw").getText().toString();
            SignInDto signInDto = new SignInDto(email, pw);

            ((AuthActivity)requireActivity()).requestSignIn(signInDto);
        }
    }
}

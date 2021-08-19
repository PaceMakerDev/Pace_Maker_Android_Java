package com.example.pacemaker.auth.ui.signup;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.AuthActivity;
import com.example.pacemaker.auth.models.EmailCertificateRequestDto;
import com.example.pacemaker.auth.models.EmailCodeVerificationRequestDto;

import org.jetbrains.annotations.NotNull;

public class EmailCertificationFragment extends Fragment {
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.auth_fragment_email_certifiaction, container, false);
        Bundle bundle = getArguments();
        if (bundle == null) {
            sendingCodeSetUp(rootView);
        }
        else {
            authenticateSetUp(rootView, bundle);
        }
        return rootView;
    }

    public void showNoValidCodeError() {
        TextView textError = requireView().findViewById(R.id.text_error);
        textError.setVisibility(View.VISIBLE);
    }

    private void sendingCodeSetUp(View view) {
        Button btnSend = view.findViewById(R.id.btn_send_code);
        btnSend.setOnClickListener(v -> {
            EditText editEmail = view.findViewById(R.id.auth_edit_email);
            String email = editEmail.getText().toString();
            email = email.concat(getResources().getString(R.string.email_address));
            EmailCertificateRequestDto emailCertificateRequestDto = new EmailCertificateRequestDto(email);
            ((AuthActivity)requireActivity()).requestCertificateEmail(emailCertificateRequestDto);
        });
    }

    private void authenticateSetUp(View view,  Bundle bundle) {
        String email = bundle.getString("email");
        setUpInitialViews(view, email);

        Button btnCodeResend = view.findViewById(R.id.btn_send_code);
        Button btnAuthenticate = view.findViewById(R.id.btn_authenticate);
        EditText editCode = view.findViewById(R.id.edit_code);
        setUpResendButton(btnCodeResend, email);
        setUpAuthenticateButton(btnAuthenticate, editCode, email);

    }

    private void setUpInitialViews(View view, String email) {
        TextView textMessage = view.findViewById(R.id.message1);
        TextView textSubTitle = view.findViewById(R.id.subtitle1);
        TextView textEmailFixed = view.findViewById(R.id.text_email_fixed);
        EditText editEmail = view.findViewById(R.id.auth_edit_email);
        TextView textCodeSubTitle = view.findViewById(R.id.text_code_subtitle);
        EditText editCode = view.findViewById(R.id.edit_code);

        textMessage.setText(R.string.auth_authenticate_email_message);
        textSubTitle.setVisibility(View.GONE);
        editEmail.setVisibility(View.GONE);
        textCodeSubTitle.setVisibility(View.VISIBLE);
        editCode.setVisibility(View.VISIBLE);

        String convertedEmail = email.split("@")[0] + " " + textEmailFixed.getText().toString();
        textEmailFixed.setText(convertedEmail);
        textEmailFixed.setTextColor(getResources().getColor(R.color.mainTheme2,null));
    }

    private void setUpResendButton(Button btnCodeResend, String email) {
        btnCodeResend.setText("코드 재전송");

        ViewGroup.LayoutParams params = btnCodeResend.getLayoutParams();
        params.width = (int)(params.width * 1.2);
        btnCodeResend.setLayoutParams(params);

        btnCodeResend.setOnClickListener(v -> {
            EmailCertificateRequestDto emailCertificateRequestDto = new EmailCertificateRequestDto(email);
            ((AuthActivity)requireActivity()).requestCertificateEmail(emailCertificateRequestDto);
        });
    }

    private void setUpAuthenticateButton(Button btnAuthenticate, EditText editCode, String email) {
        btnAuthenticate.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_filled_maintheme, null));
        btnAuthenticate.setTextColor(getResources().getColor(R.color.buttonFilledText, null));
        btnAuthenticate.setEnabled(true);

        btnAuthenticate.setOnClickListener(view -> {
            EmailCodeVerificationRequestDto emailCodeVerificationRequestDto = getEmailCodeVerificationRequestDto(editCode, email);
            ((AuthActivity)requireActivity()).requestVerifyEmail(emailCodeVerificationRequestDto);
        });
    }

    private EmailCodeVerificationRequestDto getEmailCodeVerificationRequestDto(EditText editCode, String email) {
        String code = editCode.getText().toString();
        EmailCodeVerificationRequestDto emailCodeVerificationRequestDto = new EmailCodeVerificationRequestDto(code, email);
        return emailCodeVerificationRequestDto;
    }

}

package com.example.pacemaker.auth.ui.signup;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.AuthActivity;
import com.example.pacemaker.auth.models.EmailCertificateRequestDto;

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
        }
        return rootView;
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

    private void authenticateSetUp(View view) {

    }
}

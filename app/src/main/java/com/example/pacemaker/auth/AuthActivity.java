package com.example.pacemaker.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.enums.FragmentTypes;
import com.example.pacemaker.auth.models.FindEmailRequestDto;
import com.example.pacemaker.auth.models.FindPwRequestDto;
import com.example.pacemaker.auth.models.SignInDto;
import com.example.pacemaker.auth.models.SignUpDto;
import com.example.pacemaker.auth.service.AuthService;
import com.example.pacemaker.auth.service.request.RequestProcess;
import com.example.pacemaker.auth.ui.findinfos.FindEmailFragment;
import com.example.pacemaker.auth.ui.findinfos.FindEmailSuccessFragment;
import com.example.pacemaker.auth.ui.findinfos.FindPasswordFragment;
import com.example.pacemaker.auth.ui.findinfos.FindPasswordSuccessFragment;
import com.example.pacemaker.auth.ui.login.ChangePasswordFragment;
import com.example.pacemaker.auth.ui.login.LoginFragment;
import com.example.pacemaker.auth.ui.signup.SignUpFragment;
import com.example.pacemaker.auth.ui.signup.SignUpSuccessFragment;
import com.example.pacemaker.util.service.ServiceGenerator;

public class AuthActivity extends AppCompatActivity {
    public static String TAG = "Auth";
    private Fragment mainFragment;
    private Fragment loginFragment;
    private Fragment signUpFragment;
    private Fragment singUpSuccessFragment;
    private Fragment findEmailFragment;
    private Fragment findEmailSuccessFragment;
    private Fragment findPasswordFragment;
    private Fragment findPasswordSuccessFragment;
    private Fragment changePasswordFragment;

    private RequestProcess request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        checkAccessToken();
        setFragment(FragmentTypes.MAIN);
        AuthService service = ServiceGenerator.createService(AuthService.class);
        request = new RequestProcess(service, getSharedPreferences("auth", MODE_PRIVATE));
    }

    private void checkAccessToken() {
        if (getSharedPreferences("auth", MODE_PRIVATE).getString("accessToken", "empty").compareTo("empty") != 0) {
            startStudyActivity();
        }
    }

    public void startStudyActivity() {
        Intent intent = new Intent(this, com.example.pacemaker.study.StudyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void showSuccessfulEmailFind(String name, String email) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("email", email);
        findEmailSuccessFragment = new FindEmailSuccessFragment();
        findEmailSuccessFragment.setArguments(bundle);
        setFragment(FragmentTypes.FIND_EMAIL_SUCCESS);
    }

    public void showSuccessfulPasswordFind(String name, String email) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("email", email);
        findPasswordSuccessFragment = new FindPasswordSuccessFragment();
        findPasswordSuccessFragment.setArguments(bundle);
        setFragment(FragmentTypes.FIND_PW_SUCCESS);
    }


    public void setFragment(FragmentTypes frag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (frag == FragmentTypes.MAIN) {
            mainFragment = new MainFragment();
            transaction.replace(R.id.auth_main_frame, mainFragment);
        }

        else {
            transaction.setCustomAnimations(
                    R.anim.from_right_to_center,
                    R.anim.from_center_to_left,
                    R.anim.from_left_to_center,
                    R.anim.from_center_to_right);
            transaction.addToBackStack(null);
            transaction.setReorderingAllowed(true);

            switch(frag) {
                case LOGIN:
                    loginFragment = new LoginFragment();
                    transaction.replace(R.id.auth_main_frame, loginFragment);
                    break;
                case SIGNUP:
                    signUpFragment = new SignUpFragment();
                    transaction.replace(R.id.auth_main_frame, signUpFragment);
                    break;
                case SIGN_UP_SUCCESS:
                    singUpSuccessFragment = new SignUpSuccessFragment();
                    transaction.replace(R.id.auth_main_frame, singUpSuccessFragment);
                    break;
                case FIND_EMAIL:
                    findEmailFragment = new FindEmailFragment();
                    transaction.replace(R.id.auth_main_frame, findEmailFragment);
                    break;
                case FIND_EMAIL_SUCCESS:
                    transaction.replace(R.id.auth_main_frame, findEmailSuccessFragment);
                    break;
                case FIND_PW:
                    findPasswordFragment = new FindPasswordFragment();
                    transaction.replace(R.id.auth_main_frame, findPasswordFragment);
                    break;
                case FIND_PW_SUCCESS:
                    transaction.replace(R.id.auth_main_frame, findPasswordSuccessFragment);
                    break;
                case CHANGE_PASSWORD:
                    changePasswordFragment = new ChangePasswordFragment();
                    transaction.replace(R.id.auth_main_frame, changePasswordFragment);
                    break;
            }
        }
        transaction.commit();
    }


    //request function 설정하면 되는데, 이를 아예 다른 클래스로 빼버릴까
    public void requestSignIn(SignInDto signInDto) {
        request.signIn(signInDto, loginFragment.requireContext(), this);
    }

    public void requestSignUp(SignUpDto signUpDto) {
        request.signUp(signUpDto, signUpFragment.requireContext(), this);
    }

    public void requestFindEmail(FindEmailRequestDto findEmailRequestDto) {
        request.findEmail(findEmailRequestDto, findEmailFragment.requireContext(), this);
    }

    public void requestFindPassword(FindPwRequestDto findPwRequestDto) {
        request.findPassword(findPwRequestDto, findPasswordFragment.requireContext(), this);
    }

    public void requestChangePassword() {}
}
package com.example.pacemaker.auth;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.enums.FragmentTypes;
import com.example.pacemaker.auth.service.AuthService;
import com.example.pacemaker.util.service.ServiceGenerator;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "Auth";
    private Fragment mainFragment = new MainFragment();
    private Fragment loginFragment = new LoginFragment();
    private Fragment signUpFragment = new SignUpFragment();
    private AuthService service;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setFragment(FragmentTypes.MAIN);
        service = ServiceGenerator.createService(AuthService.class);

    }

    public void setFragment(FragmentTypes frag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch(frag) {
            case MAIN:
                transaction.replace(R.id.auth_main_frame, mainFragment);
                break;
            case LOGIN:
                transaction.setCustomAnimations(
                        R.anim.from_right_to_center,
                        R.anim.from_center_to_left,
                        R.anim.from_left_to_center,
                        R.anim.from_center_to_right);
                transaction.addToBackStack(null);
                transaction.setReorderingAllowed(true);
                transaction.replace(R.id.auth_main_frame, loginFragment);
                break;
            case SIGNUP:
                transaction.setCustomAnimations(
                        R.anim.from_right_to_center,
                        R.anim.from_center_to_left,
                        R.anim.from_left_to_center,
                        R.anim.from_center_to_right);
                transaction.addToBackStack(null);
                transaction.setReorderingAllowed(true);
                transaction.replace(R.id.auth_main_frame, signUpFragment);
                break;

        }
        transaction.commit();
    }

    public void startCameraActivity() {}

    //request function 설정하면 되는데, 이를 아예 다른 클래스로 빼버릴까

}
package com.example.pacemaker.study.ui.studysearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.AuthActivity;
import com.example.pacemaker.auth.MainFragment;
import com.example.pacemaker.auth.enums.FragmentTypes;
import com.example.pacemaker.auth.ui.findinfos.FindEmailFragment;
import com.example.pacemaker.auth.ui.findinfos.FindPasswordFragment;
import com.example.pacemaker.auth.ui.login.ChangePasswordFragment;
import com.example.pacemaker.auth.ui.login.LoginFragment;
import com.example.pacemaker.auth.ui.signup.SignUpFragment;
import com.example.pacemaker.auth.ui.signup.SignUpSuccessFragment;
import com.example.pacemaker.study.StudyActivity;

public class StudyCreateFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.study_fragment_study_create, container, false);
        ((StudyActivity)requireActivity()).hideNavView();
        showUserName(rootView);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("MyStudy", "showNavView()");
        ((StudyActivity)requireActivity()).showNavView();
    }

    public void showUserName(View view) {
        TextView textName = view.findViewById(R.id.textview_user_name);
        SharedPreferences preferences = ((StudyActivity)requireActivity()).getSharedPreferences(AuthActivity.SHARED_AUTH_ID, Context.MODE_PRIVATE);
        String name = preferences.getString(AuthActivity.USER_NAME, "");
        textName.setText(name);
    }
}

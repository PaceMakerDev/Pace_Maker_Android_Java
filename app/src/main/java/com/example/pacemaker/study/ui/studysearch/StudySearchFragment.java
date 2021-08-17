package com.example.pacemaker.study.ui.studysearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.MainFragment;
import com.example.pacemaker.auth.ui.findinfos.FindEmailFragment;
import com.example.pacemaker.auth.ui.findinfos.FindPasswordFragment;
import com.example.pacemaker.auth.ui.login.ChangePasswordFragment;
import com.example.pacemaker.auth.ui.login.LoginFragment;
import com.example.pacemaker.auth.ui.signup.SignUpFragment;
import com.example.pacemaker.auth.ui.signup.SignUpSuccessFragment;
import com.example.pacemaker.study.StudyActivity;
import com.example.pacemaker.study.enums.FragmentTypes;

public class StudySearchFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.study_fragment_study_search, container, false);

        ImageView view = rootView.findViewById(R.id.btn_create_study);
        view.setOnClickListener(view1 -> {
            /*
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

            StudyCreateFragment fragment = new StudyCreateFragment();
            StudyCreateMediator studyCreateMediator = ((StudyActivity)requireActivity()).getStudyCreateMediator();
            studyCreateMediator.setStudyCreateFragment(fragment);
            transaction.addToBackStack(null);
            transaction.replace(R.id.nav_host_fragment, fragment);
            transaction.commit();

             */
            ((StudyActivity)requireActivity()).setFragment(FragmentTypes.STUDY_CREATE);
        });


        return rootView;
    }

}

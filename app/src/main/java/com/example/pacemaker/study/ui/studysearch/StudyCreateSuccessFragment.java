package com.example.pacemaker.study.ui.studysearch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;
import com.example.pacemaker.study.StudyActivity;

public class StudyCreateSuccessFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_success, container, false);
        ImageView imageView = rootView.findViewById(R.id.check_mark);
        Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_up_and_down);
        imageView.startAnimation(anim);

        TextView textTitle = rootView.findViewById(R.id.title);
        textTitle.setText("스터디룸 만들기");
        TextView textMessage = rootView.findViewById(R.id.message);
        textMessage.setText("개설 완료!");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(requireContext(), com.example.pacemaker.study.StudyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }, 4000);

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        ((StudyActivity)requireActivity()).showNavView();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((StudyActivity)requireActivity()).hideNavView();
    }
}

package com.example.pacemaker.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;

public class CameraCheckFragment extends Fragment {

    /*
    public static CameraCheckFragment newInstance(int left, int right, int top, int bottom) {
        CameraCheckFragment f = new CameraCheckFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("left", left);
        bundle.putInt("right", right);
        bundle.putInt("top", top);
        bundle.putInt("bottom", bottom);
        f.setArguments(bundle);

        return f;
    }

     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.auth_fragment_camera_check, container, false);
        /*
        Bundle bundle = getArguments();
        int left = bundle.getInt("left");
        int right = bundle.getInt("right");
        int top = bundle.getInt("top");
        int bottom = bundle.getInt("bottom");
        int width = right - left;
        int height = bottom - top;

         */
        ImageView card = rootView.findViewById(R.id.auth_img_card);
        card.setImageBitmap(((CameraActivity)requireActivity()).getCard());
        return rootView;
    }
}

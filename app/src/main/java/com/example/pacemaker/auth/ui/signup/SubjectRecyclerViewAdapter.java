package com.example.pacemaker.auth.ui.signup;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacemaker.R;
import com.example.pacemaker.study.ui.mystudy.models.Study;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

public class SubjectRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> subjectList;
    private HashSet<View> viewList;
    private String selected = "";
    private Resources resources;
    private TextView textSubject;
    private SignUpMediator signUpMediator;

    public SubjectRecyclerViewAdapter(Resources resources, TextView textSubject, SignUpMediator signUpMediator) {
        this.subjectList = new ArrayList<String>();
        this.resources = resources;
        this.textSubject = textSubject;
        this.viewList = new HashSet<View>();
        this.signUpMediator = signUpMediator;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = new SubjectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.auth_item_subject, parent, false));
        return viewHolder;
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder {
        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;
        viewList.add(view);
        String subject = subjectList.get(position);

        TextView title = view.findViewById(R.id.text_subject_name);
        title.setText(subject);
        ImageView imgCheck = view.findViewById(R.id.ic_subject_check);
        if (selected.compareTo(subject) == 0) {
            imgCheck.setVisibility(View.VISIBLE);
            title.setTextColor(ResourcesCompat.getColor(resources, R.color.mainTheme2, null));
        }
        title.setOnClickListener(v -> {
            unselectViews();
            imgCheck.setVisibility(View.VISIBLE);
            title.setTextColor(ResourcesCompat.getColor(resources, R.color.mainTheme2, null));
            textSubject.setText(subject);
            selected = subject;
            signUpMediator.selectSubject(subject);
        });


    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    private void unselectViews() {
        for (View view : viewList) {
            TextView title = view.findViewById(R.id.text_subject_name);
            ImageView imgCheck = view.findViewById(R.id.ic_subject_check);
            title.setTextColor(ResourcesCompat.getColor(resources, R.color.text_general, null));
            imgCheck.setVisibility(View.GONE);
        }
    }

    public void setSubject(ArrayList<String> studyList) {
        this.subjectList = studyList;
    }


}

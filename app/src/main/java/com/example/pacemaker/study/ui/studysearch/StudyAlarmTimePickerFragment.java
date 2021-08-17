package com.example.pacemaker.study.ui.studysearch;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.example.pacemaker.R;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class StudyAlarmTimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private StudyCreateMediator mediator;
    private String dayName;
    private TextView amView;
    private TextView pmView;
    private TextView timeView;

    public StudyAlarmTimePickerFragment(TextView amView, TextView pmView, TextView timeView, String dayName, StudyCreateMediator mediator) {
        this.mediator = mediator;
        this.amView = amView;
        this.pmView = pmView;
        this.dayName = dayName;
        this.timeView = timeView;
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(
//                getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                getContext(), R.style.SpinnerTimePickerStyle,
        this, hour, min, false
        );
        timePickerDialog.setTitle("스터디 시작 시간");
        //timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        boolean isAM = true;
        if (hourOfDay >= 12) {
            isAM = false;
        }

        if (hourOfDay == 0) {
            hourOfDay = 12;
        }
        String hour = String.format("%2d", hourOfDay);
        String min = String.format("%02d", minute);

        mediator.setAlarmTime(dayName, amView, pmView, timeView, isAM, hour, min);
        mediator.requestUpdateAlarmDetail(dayName, true);
    }
}

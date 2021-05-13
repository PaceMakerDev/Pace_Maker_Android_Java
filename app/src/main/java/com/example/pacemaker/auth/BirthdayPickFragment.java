package com.example.pacemaker.auth;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.example.pacemaker.R;

import java.util.Calendar;


public class BirthdayPickFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    TextView edit_year;
    TextView edit_month;
    TextView edit_day;

    public BirthdayPickFragment(TextView edit_year, TextView edit_month, TextView edit_day) {
        this.edit_year = edit_year;
        this.edit_month = edit_month;
        this.edit_day = edit_day;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.SpinnerDatePickerStyle,
                this, year, month, day);
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String year = Integer.toString(i);
        // month : 0 ~ 11
        String month = Integer.toString(i1+1);
        String day = Integer.toString(i2);

        edit_year.setText(year);
        edit_month.setText(month);
        edit_day.setText(day);
    }
}

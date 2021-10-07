package com.example.pacemaker.study.ui.studysearch.studycreate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.pacemaker.R;
import com.example.pacemaker.auth.AuthActivity;
import com.example.pacemaker.study.StudyActivity;
import com.example.pacemaker.study.ui.studysearch.models.StudyCreateRequestDto;
import com.example.pacemaker.study.ui.studysearch.models.StudySchedule;
import com.example.pacemaker.study.ui.studysearch.service.StudySearchRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StudyCreateFragment extends Fragment {
    public final static String TITLE = "title";
    public final static String MEMBER = "member";
    public final static String CATEGORY = "category";
    public final static String OBJECT = "object";
    public final static String RULE = "rule";
    public final static String HASHTAG = "hashtag";

    private boolean isCategoryLayoutUp = false;
    private OnBackPressedCallback backPressedCallback;
    private StudySearchRequest requestProcess;
    private Map<String, Boolean> validMap = new HashMap<String, Boolean>();
    private Map<String, Boolean> alarmDetailValidMap = new HashMap<String, Boolean>();
    private Map<String, Boolean> alarmDetailIsPM = new HashMap<String, Boolean>();
    private Map<String, String> dayNameDict = new HashMap<String, String>();
    private Map<String, String> categoryDict = new HashMap<String, String>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.study_fragment_study_create, container, false);
        showUserName(rootView);
        initialSetUp(rootView);
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

    @Override
    public void onDetach() {
        super.onDetach();
        backPressedCallback.remove();
    }

    private void showUserName(View view) {
        TextView textName = view.findViewById(R.id.textview_user_name);
        SharedPreferences preferences = ((StudyActivity)requireActivity()).getSharedPreferences(AuthActivity.SHARED_AUTH_ID, Context.MODE_PRIVATE);
        String name = preferences.getString(AuthActivity.USER_NAME, "");
        textName.setText(name);
    }

    private void initialSetUp(View view) {
        setUpDicts();
        initValidMap();
        editTextSetUp(view);
        categorySetup(view);
        studyAlarmSetUp(view);
        editObjectSetUp(view);
        editRuleSetUp(view);
        setUpCreateButton(view);
    }

    private void setUpDicts() {
        dayNameDict.put("월", "MON");
        dayNameDict.put("화", "TUE");
        dayNameDict.put("수", "WED");
        dayNameDict.put("목", "THU");
        dayNameDict.put("금", "FRI");
        dayNameDict.put("토", "SAT");
        dayNameDict.put("일", "SUN");

        categoryDict.put("어학", "LANGUAGE");
        categoryDict.put("IT/컴퓨터", "IT");
        categoryDict.put("고시/공무원", "NATIONAL_EXAM");
        categoryDict.put("자격증", "CERTIFICATE");
        categoryDict.put("취업", "EMPLOYMENT");
        categoryDict.put("기타", "OTHER");
    }

    private void editTextSetUp(View view) {
        StudyCreateMediator mediator = ((StudyActivity)requireActivity()).getStudyCreateMediator();

        EditText editStudyTitle = view.findViewById(R.id.study_create_edit_study_name);
        editStudyTitle.addTextChangedListener(new InputTextWatcher(mediator, TITLE));
        EditText editMemberCount = view.findViewById(R.id.edit_study_create_member_count);
        editMemberCount.setFilters(new InputFilter[] {new MemberCountFilter(1, 51, mediator, MEMBER)});
        EditText editStudyObject = view.findViewById(R.id.edit_study_object);
        editStudyObject.addTextChangedListener(new InputTextWatcher(mediator, OBJECT));
        EditText editStudyRule = view.findViewById(R.id.edit_study_rule);
        editStudyRule.addTextChangedListener(new InputTextWatcher(mediator, RULE));
        EditText editHashTag = view.findViewById(R.id.edit_study_create_hashtag);
        editHashTag.addTextChangedListener(new InputTextWatcher(mediator, HASHTAG));
    }

    private void categorySetup(View view) {
        LinearLayout layoutCategory = view.findViewById(R.id.layout_study_create_category);
        TextView textStudyCategory = view.findViewById(R.id.edit_study_category);
        backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isCategoryLayoutUp) {
                    hideCategoryList(layoutCategory);
                    isCategoryLayoutUp = false;
                }
                else {
                    backPressedCallback.remove();
                    requireActivity().onBackPressed();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), backPressedCallback);
        textStudyCategory.setOnClickListener(v -> {
            if (isCategoryLayoutUp == false) {
                showCategoryList(layoutCategory);
                isCategoryLayoutUp = true;
            }
        });

        ConstraintLayout container = view.findViewById(R.id.container);
        container.setOnClickListener(v -> {
            if (isCategoryLayoutUp){
                hideCategoryList(layoutCategory);
                isCategoryLayoutUp = false;
            }
        });
        TextView editCategory = view.findViewById(R.id.edit_study_category);
        categoryClickSetUp(layoutCategory, editCategory);
    }

    private void showCategoryList(LinearLayout layoutCategory) {
        Animation layoutAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_to_center);
        layoutCategory.setVisibility(View.VISIBLE);
        layoutCategory.startAnimation(layoutAnim);
    }

    private void hideCategoryList(LinearLayout layoutCategory) {
        Animation layoutAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.from_center_to_bottom);
        layoutCategory.startAnimation(layoutAnim);
        layoutCategory.setVisibility(View.GONE);
    }

    private void categoryClickSetUp(LinearLayout layout, TextView studyCategory) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            ConstraintLayout childLayout = (ConstraintLayout)layout.getChildAt(i);
            childLayout.setOnClickListener(v -> {
                TextView childTextView = (TextView)((ConstraintLayout)v).getChildAt(0);
                ImageView childImageView = (ImageView)((ConstraintLayout)v).getChildAt(1);
                String category = childTextView.getText().toString();
                studyCategory.setText(category);
                for (int j = 0; j < layout.getChildCount(); j++) {
                    ConstraintLayout tmpChildLayout = (ConstraintLayout)layout.getChildAt(j);
                    TextView tmpChild = (TextView)tmpChildLayout.getChildAt(0);
                    ImageView tmpImageView = (ImageView)tmpChildLayout.getChildAt(1);
                    tmpChild.setTextColor(getResources().getColor(R.color.text_general, null));
                    tmpChild.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.normal_text_size));
                    tmpImageView.setVisibility(View.GONE);
                }
                childTextView.setTextColor(getResources().getColor(R.color.mainTheme2, null));
                childTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.large_text_size));
                childImageView.setVisibility(View.VISIBLE);
                setValidation(CATEGORY, true);
                updateCreateButton();
            });
        }
    }

    private void studyAlarmSetUp(View view) {
        LinearLayout dayContainer = view.findViewById(R.id.layout_study_create_alarm);
        LinearLayout detailContainer = view.findViewById(R.id.layout_study_create_alarm_detail);
        for (int i = 0; i < dayContainer.getChildCount(); i++) {
            FrameLayout dayLayout = (FrameLayout)dayContainer.getChildAt(i);
            LinearLayout detailLayout = (LinearLayout)detailContainer.getChildAt(i*2);
            View separator = detailContainer.getChildAt(i*2 + 1);
            dayLayout.setOnClickListener(curView -> {
                if (detailLayout.getVisibility() == View.GONE) {
                    initDetailLayout(detailLayout);
                    selectDay(dayLayout, detailLayout, separator);
                }
                else {
                    unselectDay(dayLayout, detailLayout, separator);
                }
            });
            //studyAlarmDetailSetUp(detailLayout);
        }
    }

    private void selectDay(FrameLayout dayLayout, LinearLayout detailLayout, View separator) {
        dayLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_study_create_alarm_selected, null));
        ImageView imageSelectMark = (ImageView)dayLayout.getChildAt(1);
        imageSelectMark.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.oval_study_create_selected, null));

        detailLayout.setVisibility(View.VISIBLE);
        separator.setVisibility(View.VISIBLE);
        studyAlarmDetailSetUp(detailLayout);
        updateCreateButton();
    }
    private void unselectDay(FrameLayout dayLayout, LinearLayout detailLayout, View separator) {
        dayLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_study_create_alarm, null));
        ImageView imageSelectMark = (ImageView)dayLayout.getChildAt(1);
        imageSelectMark.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.oval_study_create_unselected, null));

        TextView textDay = (TextView)detailLayout.getChildAt(0);
        String dayName = textDay.getText().toString();
        alarmDetailValidMap.remove(dayName);
        updateCreateButton();

        detailLayout.setVisibility(View.GONE);
        separator.setVisibility(View.GONE);
    }

    private void studyAlarmDetailSetUp(LinearLayout container) {
        TextView textDay = (TextView)container.getChildAt(0);
        String dayName = textDay.getText().toString();
        TextView btnDay = (TextView)container.getChildAt(2);
        TextView btnNight = (TextView)container.getChildAt(3);
        TextView btnTime = (TextView)container.getChildAt(4);
        alarmDetailValidMap.put(dayName, false);

        String initTime = String.format("%2d   :   %02d", 9, 0);
        btnTime.setText(initTime);

        btnTime.setOnClickListener(view -> {
            StudyCreateMediator studyCreateMediator = ((StudyActivity)requireActivity()).getStudyCreateMediator();
            StudyAlarmTimePickerFragment timePickerFragment = new StudyAlarmTimePickerFragment(btnDay, btnNight, btnTime, dayName, studyCreateMediator);
            timePickerFragment.show(requireActivity().getSupportFragmentManager(), "alarmTimePicker");
        });
        btnDay.setOnClickListener(view -> {
            StudyCreateMediator studyCreateMediator = ((StudyActivity)requireActivity()).getStudyCreateMediator();
            StudyAlarmTimePickerFragment timePickerFragment = new StudyAlarmTimePickerFragment(btnDay, btnNight, btnTime, dayName, studyCreateMediator);
            timePickerFragment.show(requireActivity().getSupportFragmentManager(), "alarmTimePicker");
        });
        btnNight.setOnClickListener(view -> {
            StudyCreateMediator studyCreateMediator = ((StudyActivity)requireActivity()).getStudyCreateMediator();
            StudyAlarmTimePickerFragment timePickerFragment = new StudyAlarmTimePickerFragment(btnDay, btnNight, btnTime, dayName, studyCreateMediator);
            timePickerFragment.show(requireActivity().getSupportFragmentManager(), "alarmTimePicker");
        });
    }

    private void initDetailLayout(LinearLayout container) {
        TextView btnDay = (TextView)container.getChildAt(2);
        TextView btnNight = (TextView)container.getChildAt(3);
        TextView btnTime = (TextView)container.getChildAt(4);
        btnDay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_study_create_alarm_detail, null));
        btnNight.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_study_create_alarm_detail, null));
        btnDay.setTextColor(ResourcesCompat.getColor(getResources(), R.color.studyCreateDetailBtnUnselected, null));
        btnNight.setTextColor(ResourcesCompat.getColor(getResources(), R.color.studyCreateDetailBtnUnselected, null));
    }

    public void selectAM(String dayName, TextView amView, TextView pmView, TextView timeView, String hour, String min) {
        amView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_study_create_alarm_detail_selected, null));
        amView.setTextColor(ResourcesCompat.getColor(getResources(), R.color.studyCreateDetailBtnSelected, null));
        pmView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_study_create_alarm_detail, null));
        pmView.setTextColor(ResourcesCompat.getColor(getResources(), R.color.studyCreateDetailBtnUnselected, null));

        alarmDetailIsPM.put(dayName, false);
        String timeText = (hour + "   :   " + min);
        timeView.setText(timeText);
    }

    public void selectPM(String dayName, TextView amView, TextView pmView, TextView timeView, String hour, String min) {
        amView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_study_create_alarm_detail, null));
        amView.setTextColor(ResourcesCompat.getColor(getResources(), R.color.studyCreateDetailBtnUnselected, null));
        pmView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_study_create_alarm_detail_selected, null));
        pmView.setTextColor(ResourcesCompat.getColor(getResources(), R.color.studyCreateDetailBtnSelected, null));
        int intHour = Integer.parseInt(hour);
        intHour -= 12;
        if (intHour == 0) {
            intHour = 12;
        }
        hour = Integer.toString(intHour);

        alarmDetailIsPM.put(dayName, true);
        String timeText = (hour + "   :   " + min);
        timeView.setText(timeText);
    }

    private void editObjectSetUp(View rootView) {
        EditText objectView = rootView.findViewById(R.id.edit_study_object);
        NestedScrollView scrollView = rootView.findViewById(R.id.nestedScrollView);
        objectView.setOnTouchListener((v, event) -> {
            if (objectView.hasFocus()) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_SCROLL:
                        scrollView.requestDisallowInterceptTouchEvent(false);
                        return true;
                }
            }
            return false;
        });
    }

    private void editRuleSetUp(View rootView) {
        EditText ruleView = rootView.findViewById(R.id.edit_study_rule);
        NestedScrollView scrollView = rootView.findViewById(R.id.nestedScrollView);
        ruleView.setOnTouchListener((v, event) -> {
            if (ruleView.hasFocus()) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_SCROLL:
                        scrollView.requestDisallowInterceptTouchEvent(false);
                        return true;
                }
            }
            return false;
        });
    }

    private void setUpCreateButton(View view) {
        Button btn = view.findViewById(R.id.btn_study_create_create);
        btn.setOnClickListener(v -> {
            StudyCreateRequestDto requestDto = loadStudyCreateRequestDto(view);
            ((StudyActivity)requireActivity()).requestStudyCreate(requestDto, requireContext());
        });
    }

    private StudyCreateRequestDto loadStudyCreateRequestDto(View view) {
        EditText editTitle = view.findViewById(R.id.study_create_edit_study_name);
        TextView textMember = view.findViewById(R.id.edit_study_create_member_count);
        TextView textCategory = view.findViewById(R.id.edit_study_category);
        EditText editObject = view.findViewById(R.id.edit_study_object);
        EditText editRule = view.findViewById(R.id.edit_study_rule);
        EditText editHashTag = view.findViewById(R.id.edit_study_create_hashtag);
        String title = editTitle.getText().toString();
        int member = Integer.parseInt(textMember.getText().toString());
        String category = textCategory.getText().toString();
        Log.d("MyStudy", "previous : " + category);
        category = categoryDict.get(category);
        Log.d("MyStudy", category);
        String object = editObject.getText().toString();
        String rule = editRule.getText().toString();
        String hashTags = editHashTag.getText().toString();
        ArrayList<StudySchedule> studyScheduleList = new ArrayList<StudySchedule>();

        LinearLayout detailContainer = view.findViewById(R.id.layout_study_create_alarm_detail);
        Set<String> selectedAlarms = alarmDetailValidMap.keySet();
        for (int i = 0; i < detailContainer.getChildCount(); i++) {
            if (i % 2 == 1)
                continue;
            LinearLayout alarmContainer = (LinearLayout)detailContainer.getChildAt(i);
            TextView textDay = (TextView)alarmContainer.getChildAt(0);
            String dayName = textDay.getText().toString();
            if (selectedAlarms.contains(dayName)) {
                TextView textTime = (TextView)alarmContainer.getChildAt(4);
                String [] tokens = textTime.getText().toString().split(" ");
                int hour = Integer.parseInt(tokens[0]);
                int minute = Integer.parseInt(tokens[tokens.length-1]);
                if (alarmDetailIsPM.get(dayName))
                    hour += 12;
                String time = hour + ":" + minute;
                String day = dayNameDict.get(dayName);
                studyScheduleList.add(new StudySchedule(day, time));
            }
        }
        Log.d("MyStudy", title);
        Log.d("MyStudy", "" + member);
        Log.d("MyStudy", category);
        Log.d("MyStudy", object);
        Log.d("MyStudy", rule);
        Log.d("MyStudy", hashTags);
        Log.d("MyStudy", studyScheduleList.get(0).getDay());
        Log.d("MyStudy", studyScheduleList.get(0).getAt());
        StudyCreateRequestDto requestDto = new StudyCreateRequestDto(title, member, category, object, rule, hashTags, studyScheduleList);

        return requestDto;
    }

    private void initValidMap() {
        validMap.put(TITLE, false);
        validMap.put(MEMBER, false);
        validMap.put(CATEGORY, false);
        validMap.put(OBJECT, false);
        validMap.put(RULE, false);
        validMap.put(HASHTAG, false);
    }

    public void updateCreateButton() {
        View rootView = requireView();
        Button btn = rootView.findViewById(R.id.btn_study_create_create);
        boolean result = true;
        for (String key : validMap.keySet()) {
            if (!validMap.get(key)) {
                result = false;
                Log.d("MyStudy", "not valid : " + key);
                break;
            }
        }
        for (String key : alarmDetailValidMap.keySet()) {
            if (!alarmDetailValidMap.get(key)){
                result = false;
                Log.d("MyStudy", "not valid : " + key);
                break;
            }
        }
        if (alarmDetailValidMap.isEmpty()) {
            result = false;
        }
        if (result) {
            btn.setEnabled(true);
            btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_filled_maintheme, null));
            btn.setTextColor(getResources().getColor(R.color.buttonFilledText, null));
        } else {
            btn.setEnabled(false);
            btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button_filled_disabled, null));
            btn.setTextColor(getResources().getColor(R.color.buttonDisabledText, null));
        }
    }

    public void setValidation(String tag, boolean result) {
        validMap.put(tag, result);
    }

    public void setAlarmValidation(String dayName, boolean result) {
        alarmDetailValidMap.put(dayName, result);
    }

}

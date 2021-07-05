package com.example.pacemaker.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pacemaker.study.StudyActivity;

public class DialogUtil {
    public static void showOkDialog(Context context, String title, String msg) {
        AlertDialog.Builder builder = createBuilder(context, title, msg);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    public static void showCriticalErrorDialog(Activity activity, String title, String msg) {
        AlertDialog.Builder builder = createBuilder(activity, title, msg);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                activity.moveTaskToBack(true);
                activity.finishAndRemoveTask();;
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.moveTaskToBack(true);
                activity.finishAndRemoveTask();;
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.show();
    }

    public static void DialogLogoutOnOk(AppCompatActivity activity, String title, String msg) {
        AlertDialog.Builder builder = createBuilder(activity.getBaseContext(), title, msg);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((StudyActivity)activity).logout();
            }
        });
        builder.show();
    }

    private static AlertDialog.Builder createBuilder(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        return builder;
    }
}

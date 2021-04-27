package com.example.pacemaker.auth;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pacemaker.R;
import com.example.pacemaker.util.DialogUtil;

public class CameraActivity extends AppCompatActivity {
    private final int CAMERA_PERMISSION_CODE = 10323;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        editBackground();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            String [] permissions = {Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(this, permissions, CAMERA_PERMISSION_CODE);
        }
        else {
            //startCamera();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                DialogUtil.showCriticalErrorDialog(this,
                        "접근 권한 없음",
                        "카메라 권한이 없을 시 앱을 사용하실 수 없습니다."
                );
            }
        }
    }

    public void editBackground() {
        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.camera_background);
        Bitmap bitmap = Bitmap.createBitmap(background.getWidth(), background.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        canvas.drawBitmap(background, 0f, 0f, paint);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        canvas.drawRect(background.getWidth() / 8f, background.getHeight() / 4f, background.getWidth() / 8f * 7, background.getHeight() / 4f * 2, paint);
        findViewById(R.id.camera_background).setBackground(new BitmapDrawable(getResources(), bitmap));

    }

    /*
    public Size getWindowSize() {
        int x = 0, y = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            WindowMetrics metrics = getWindowManager().getCurrentWindowMetrics();
            WindowInsets windowInsets = metrics.getWindowInsets();
            Insets insets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars()
                    | WindowInsets.Type.displayCutout());

            int insetsWidth = insets.right + insets.left;
            int insetsHeight = insets.top + insets.bottom;

            Rect bounds = metrics.getBounds();
            x = bounds.width() - insetsWidth;
            y = bounds.height() - insetsHeight;
        }
        else {
            WindowManager window = (WindowManager)getSystemService(WINDOW_SERVICE);
            Display display = window.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            x = point.x;
            y = point.y;
        }

        return new Size(x, y);
    }

     */
}

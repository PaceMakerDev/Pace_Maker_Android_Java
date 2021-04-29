package com.example.pacemaker.auth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
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
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.pacemaker.R;
import com.example.pacemaker.util.DialogUtil;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

import lombok.SneakyThrows;

public class CameraActivity extends AppCompatActivity {
    private final int CAMERA_PERMISSION_CODE = 10323;
    private int left, right, top, bottom;
    private ImageCapture imageCapture;
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
            startCamera();
        }

        Button captureBtn = ((Button)findViewById(R.id.camera_capture_button));
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });

    }

    private void editBackground() {
        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.camera_background);
        Bitmap bitmap = Bitmap.createBitmap(background.getWidth(), background.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        canvas.drawBitmap(background, 0f, 0f, paint);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        left = (int)(background.getWidth() / 12f);
        top = (int)(background.getHeight() / 4f);
        right = (int)(background.getWidth() / 12f * 11);
        bottom = (int)(top + (right - left)*0.7f);

        canvas.drawRect(left, top, right, bottom, paint);
        findViewById(R.id.camera_background).setBackground(new BitmapDrawable(getResources(), bitmap));

    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
                bindCameraProvider(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // no errors expected.
            }

        }, ContextCompat.getMainExecutor(this));
    }

    private void bindCameraProvider(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();
        imageCapture = new ImageCapture.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(((PreviewView)findViewById(R.id.viewFinder)).getSurfaceProvider());
        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageCapture, preview);
    }

    private void captureImage() {
        if (imageCapture == null)
            DialogUtil.showOkDialog(this, "카메라 오류", "카메라 준비중! 다시 시도해주세요");
        else {
            imageCapture.takePicture(ContextCompat.getMainExecutor(this),
                    new ImageCapture.OnImageCapturedCallback() {
                        @Override
                        public void onCaptureSuccess(@NonNull ImageProxy image) {
                            super.onCaptureSuccess(image);
                            //투명한 박스 만드는 곳에서 애초에 int로 바꾸기
                            image.setCropRect(new Rect(left, top, right, bottom));
                            Log.d("Auth", "capture success");
                            String str = image.getWidth() + ", " + image.getHeight();
                            Log.d("Auth", str);

                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            super.onError(exception);
                            Log.d("Auth", "capture failed");
                        }
                    });
        }
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
            else {
                startCamera();
            }
        }
    }
}

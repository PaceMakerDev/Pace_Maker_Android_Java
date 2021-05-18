package com.example.pacemaker.auth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

import com.example.pacemaker.R;
import com.example.pacemaker.util.DialogUtil;
import com.google.common.util.concurrent.ListenableFuture;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {
    public static final String NAME = "name";
    public static final String MAJOR = "major";
    public static final String STUDENT_ID = "student_id";
    private final int CAMERA_PERMISSION_CODE = 10323;
    private int left, right, top, bottom, bgHeight;
    private Bitmap card;
    private ImageCapture imageCapture;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity_camera);
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
        CameraCheckFragment fragment = new CameraCheckFragment();

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
        bgHeight = background.getHeight();

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
        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageCapture, preview);
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

                            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                            byte[] bytes = new byte[buffer.capacity()];
                            buffer.get(bytes);
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
                            Matrix matrix = new Matrix();
                            matrix.postRotate(90f);
                            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

                            float scale = (float) (right - left) / bgHeight;
                            int scaledWidth = (int) (bmp.getHeight() * scale);
                            int scaledHeight = (int) (((float) bottom - top) / bgHeight * bmp.getHeight());
                            int scaledX = (int) ((bmp.getWidth() - scaledWidth) / 2);
                            int scaledY = (int) ((float) top / bgHeight * bmp.getHeight());

                            bmp = Bitmap.createBitmap(bmp, (int) scaledX, scaledY, (int) scaledWidth, scaledHeight);
                            card = bmp;
                            //sendImage();
                            setFragment();
                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            super.onError(exception);
                            Log.d("Auth", "capture failed");
                        }
                    });
        }
    }

    private void setFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.from_right_to_center,
                R.anim.from_center_to_left,
                R.anim.from_left_to_center,
                R.anim.from_center_to_right
        );
        transaction.addToBackStack(null);
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.auth_main_frame, new CameraCheckFragment());
        transaction.commit();
    }

    private void sendImage() {
        Intent intent = new Intent();
        intent.putExtra(NAME, "고길동");
        intent.putExtra(MAJOR, "소프트웨어학부");
        intent.putExtra(STUDENT_ID, 20173333);
        setResult(RESULT_OK, intent);
        finish();
    }

    public Bitmap getCard() {
        return card;
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
            else {
                startCamera();
            }
        }
    }
}

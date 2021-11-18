package com.example.pacemaker.studyoverview.ui.studyroom;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import androidx.lifecycle.LifecycleOwner;

import com.example.pacemaker.R;
import com.example.pacemaker.util.DialogUtil;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class StudyCameraActivity extends AppCompatActivity {
    public static final String NAME = "name";
    public static final String MAJOR = "major";
    public static final String STUDENT_ID = "student_id";
    private final int CAMERA_PERMISSION_CODE = 10323;
    private final int WRITE_EXTERNAL_STORAGE_CODE = 10324;
    private int left, right, top, bottom, bgHeight;
    private Bitmap card;
    private ImageCapture imageCapture;
    private Button btnVerify;
    private File tempFile;
    private Bitmap bmp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studyroom_camera_activity);

        requestPermission();

        //startCamera();

        Button btnCamera = findViewById(R.id.btn_camera);
        btnVerify = findViewById(R.id.btn_verification);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
        btnVerify.setOnClickListener(view -> {
            saveImage();
            FileUpload.sendFile(tempFile);
        });


    }

    private void requestVerification() {

    }

    private void saveImage() {
        String date = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
        tempFile = new File(Environment.getExternalStorageDirectory() + "/PaceMaker/", "temp_" + date + ".jpeg");

        try {
            tempFile.getParentFile().mkdirs();
            tempFile.createNewFile();
            OutputStream out = new FileOutputStream(tempFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (IOException e) {
            Log.d("MyStudy", "image Save Error");
            Log.d("MyStudy", e.getLocalizedMessage());
        }
        //https://derveljunit.tistory.com/302
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_CODE);
        }
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
                            bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
                            Matrix matrix = new Matrix();
                            matrix.postRotate(90f);
                            float gap = (bmp.getWidth() - bmp.getHeight()) / 2;
                            Log.d("MyStudy", "gap : " + gap);
                            Log.d("MyStudy", "width : " + bmp.getWidth());
                            Log.d("MyStudy", "height : " + bmp.getHeight());
                            bmp = Bitmap.createBitmap(bmp, (int)gap, 0, bmp.getHeight(), bmp.getHeight(), matrix, true);

                            card = bmp;
                            verifyImage();
                            //sendImage()
                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            super.onError(exception);
                            Log.d("Auth", "capture failed");
                        }
                    });
        }
    }

    private void verifyImage() {
        TextView text1 = findViewById(R.id.text1);
        TextView text2 = findViewById(R.id.text2);
        Button btnCamera = findViewById(R.id.btn_camera);
        text1.setVisibility(View.GONE);
        text2.setVisibility(View.GONE);
        btnCamera.setVisibility(View.GONE);
        ImageView imgVerify = findViewById(R.id.img_result);
        btnVerify.setVisibility(View.VISIBLE);
        imgVerify.setVisibility(View.VISIBLE);
        imgVerify.setImageBitmap(card);
        PreviewView previewView = findViewById(R.id.viewFinder);
        previewView.setVisibility(View.GONE);
    }


/*
    public void sendImage() {
        Intent intent = new Intent();
        intent.putExtra(NAME, "고길동");
        intent.putExtra(MAJOR, "소프트웨어학부");
        intent.putExtra(STUDENT_ID, 20173333);
        setResult(RESULT_OK, intent);
        finish();
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

    public Bitmap getCard() {
        return card;
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
        if (requestCode == WRITE_EXTERNAL_STORAGE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                DialogUtil.showCriticalErrorDialog(this,
                        "접근 권한 없음",
                        "저장소 쓰기 권한이 없을 시 앱을 사용하실 수 없습니다."
                );
            }
            else {
                startCamera();
            }
        }
    }
}

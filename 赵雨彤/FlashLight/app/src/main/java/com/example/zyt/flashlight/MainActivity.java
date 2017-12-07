package com.example.zyt.flashlight;


import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;



public class MainActivity extends AppCompatActivity {

    private CircleImageView imageButton;
    private boolean checkedResult;
    private CameraManager myCameraManager;
    private String myCameraId;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //根据控件的ID获得控件的对象
        imageButton = (CircleImageView) findViewById(R.id.imageButton);
        imageView = (ImageView) findViewById(R.id.imageView);

        if(isFlashAvailable()){
            //获取系统Camera服务
            myCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try {
                //获取Camera的ID
                myCameraId = myCameraManager.getCameraIdList()[0];
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

            //默认闪光灯为开启状态
            checkedResult = true;
            try {
                turnOnFlashLight();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            //设置监听器
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //检测状态，如果是真则关闭手电筒，否则打开手电筒
                    if(!checkedResult){
                        try {
                            turnOnFlashLight();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                        checkedResult = true;
                    } else {
                        try {
                            turnOffFlashLight();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                        checkedResult = false;
                    }
                }
            });
        } else {
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
            alert.setTitle("Error!");
            alert.setMessage("您的手机不支持闪光灯");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定并退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    System.exit(0);
                }
            });
            alert.setCancelable(false);
            alert.show();
        }
    }

    //检测闪光灯是否可用
    private boolean isFlashAvailable(){
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
    //打开闪光灯
    //throws抛出异常
    private void turnOnFlashLight() throws CameraAccessException {
        //如果版本号大于Android 6.0
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            myCameraManager.setTorchMode(myCameraId,true);
            imageButton.setImageResource(R.drawable.on_inside);
            imageView.setImageResource(R.drawable.on_outside);
        }
    }
    //关闭闪光灯
    private void turnOffFlashLight() throws CameraAccessException {
        //如果版本号大于Android 6.0
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            myCameraManager.setTorchMode(myCameraId,false);
            imageButton.setImageResource(R.drawable.off_inside);
            imageView.setImageResource(R.drawable.off_outside);
        }
    }
}
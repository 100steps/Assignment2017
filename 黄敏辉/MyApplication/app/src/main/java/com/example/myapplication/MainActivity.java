package com.example.myapplication;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    ToggleButton tb;
    int status;
    TextView tv;
    TextView tv2;
    CameraManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tb = (ToggleButton) findViewById(R.id.toggleButton);
        status = 1;
        tv = (TextView) findViewById(R.id.textView);
        tv2 = (TextView)findViewById(R.id.textView2);
        manager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);

//        tv.setText("hehe");

        tb.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (status == 1){
                    try {
                        manager.setTorchMode("0",true);
                        tv.setText("灯已开");
                        status++;
                    }catch (Exception e){
                        tv.setText("蜜汁错误出现了");
                    }

                }else {
                    try {
                        manager.setTorchMode("0",false);
                        tv.setText("灯已关");
                        status = 1;
                    }catch (Exception e){
                        tv.setText("蜜汁错误出现了");
                    }
                }
            }
        });
    }
}

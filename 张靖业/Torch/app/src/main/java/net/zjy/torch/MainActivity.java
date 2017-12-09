package net.zjy.torch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AlertDialog;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ToggleButton mToggleButton;
    private SeekBar mSeekBar;
    private TextView mTextView;
    private CameraManager mCameraManager;
    private String mCameraId;
    private Boolean isTorchOn, global;
    private Timer mTimer;
    private int freq;
    private TimerTask run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hello world", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {
            AlertDialog alert = new AlertDialog.Builder(this).create();
            alert.setTitle("错误");
            alert.setMessage("您的设备没有闪光灯，或者没有赋予相关权限。");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    System.exit(0);
                }
            });
            alert.show();
            return;
        }

        mTimer = new Timer();
        run = new TimerTask() {
            @Override
            public void run() {
                if (!global) return;
                if (!isTorchOn)
                    turnOn();
                else
                    turnOff();
                isTorchOn = !isTorchOn;
            }
        };

        isTorchOn = false; global = false;
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            showToast(e.toString());
        }

        mToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mTextView = (TextView) findViewById(R.id.textView);
        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    freq = mSeekBar.getProgress();
                    if (!b) {
                        mSeekBar.setEnabled(true);
                        isTorchOn = false;
                        turnOff();
                        if (freq != 0) {
                            mTimer.purge();
                            mTimer.cancel();
                        }
                        global = false;
                    } else {
                        mSeekBar.setEnabled(false);
                        if (freq != 0) {
                            mTimer.schedule(run, 0, 1000 / freq);
                        }
                        isTorchOn = true;
                        turnOn();
                        global = true;
                    }
                } catch (Exception e) {
                    showToast(e.toString());
                }

            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mTextView.setText("闪烁频率：" + i + "Hz");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    public void turnOn() {
        try {
            mCameraManager.setTorchMode(mCameraId, true);
        } catch (Exception e) {
            showToast(e.toString());
        }
    }

    public void turnOff() {
        try {
            mCameraManager.setTorchMode(mCameraId, false);
        } catch (Exception e) {
            showToast(e.toString());
        }
    }
}

package com.arved95.materialtorch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private Camera camera;
    ImageButton flashlightSwitchImg;
    private boolean isFlashlightOn;
    Parameters params;
    TextView textViewbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashlightSwitchImg = (ImageButton) findViewById(R.id.imageButton);
        textViewbutton = (TextView) findViewById(R.id.textView2);

        boolean isCameraFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if(!isCameraFlash) {
            showCameraAlert();
        } else {
            camera = Camera.open();
            params = camera.getParameters();
        }

        flashlightSwitchImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isFlashlightOn) {
                    setFlashlightOff();
                } else {
                    setFlashlightOn();
                }
            }
        });
    }

    private void showCameraAlert() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.no_flash)
                .setMessage(R.string.camera_flash)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void setFlashlightOn() {
        params = camera.getParameters();
        params.setFlashMode(Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
        isFlashlightOn = true;
        textViewbutton.setText(R.string.light_turn_off);

    }

    private void setFlashlightOff() {
        params.setFlashMode(Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
        isFlashlightOn = false;
        textViewbutton.setText(R.string.lo);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(camera != null) {
            camera.release();
            camera = null;
        }
    }
}


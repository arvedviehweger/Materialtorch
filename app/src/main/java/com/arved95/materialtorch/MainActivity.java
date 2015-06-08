package com.arved95.materialtorch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    public Camera camera;
    private Camera.Parameters params;
    ImageButton flashlightSwitchImg;
    private boolean isFlashlightOn;
    private boolean isFlashLightOff;
    TextView textViewbutton;
    private boolean hasCam;
    private SurfaceHolder mHolder;


    public boolean hasFlash() {
        if (camera == null) {
            return false;
        }

        Camera.Parameters parameters = camera.getParameters();

        if (parameters.getFlashMode() == null) {
            return false;
        }

        List<String> supportedFlashModes = parameters.getSupportedFlashModes();
        if (supportedFlashModes == null || supportedFlashModes.isEmpty() || supportedFlashModes.size() == 1 && supportedFlashModes.get(0).equals(Camera.Parameters.FLASH_MODE_OFF)) {
            return false;
        }

        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashlightSwitchImg = (ImageButton) findViewById(R.id.imageButton);
        textViewbutton = (TextView) findViewById(R.id.textView2);


        try {
            //Log.d("TORCH", "Check cam");
            // Get CAM reference
            camera = Camera.open();
            params = camera.getParameters();
            camera.startPreview();
            hasCam = true;
            //Log.d("TORCH", "HAS CAM ["+hasCam+"]");
        } catch (Throwable t) {
            t.printStackTrace();
        }

        if (hasFlash()) {

        } else {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

            alertDialog.setCancelable(false);

            // Setting Dialog Title
            alertDialog.setTitle(R.string.no_flash);

            // Setting Dialog Message
            alertDialog.setMessage(R.string.camera_flash);


            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    FrameLayout legacy = (FrameLayout) findViewById(R.id.legacy);
                    // Write your code here to invoke YES event
                    legacy.setVisibility(View.INVISIBLE);
                    WindowManager.LayoutParams layout = getWindow().getAttributes();
                    layout.screenBrightness = 0F;
                    getWindow().setAttributes(layout);
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to invoke NO event
                    System.exit(0);
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }

        flashlightSwitchImg.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (isFlashlightOn) {
                    turnOff();
                } else {
                    turnOn();
                }

            }

        });
    }



    public void turnOn() {
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
        isFlashlightOn = true;
        textViewbutton.setText(R.string.light_turn_off);

    }


    public void turnOff() {
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
        isFlashlightOn = false;
        textViewbutton.setText(R.string.lo);
    }


}






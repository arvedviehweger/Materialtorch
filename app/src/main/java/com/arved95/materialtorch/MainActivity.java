package com.arved95.materialtorch;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.hardware.Camera.Parameters;

import static com.arved95.materialtorch.R.id.switch1;

import android.support.v7.widget.SwitchCompat;
import android.app.NotificationManager;
import android.app.Notification.Builder;
import android.app.Notification;
import android.app.PendingIntent;

public class MainActivity extends ActionBarActivity {

    SwitchCompat s1;

    private Camera camera;
    Parameters param;
    boolean lichtan;
    public Notification.Builder notif;
    public NotificationManager nm1;
    public Intent i1;
    private boolean isFlashlightOn;
    public PendingIntent pi1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notif = new Notification.Builder(this);
        nm1 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        s1 = (SwitchCompat) findViewById(switch1);
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true) {

                    if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {

                        if (camera == null) {

                            camera = Camera.open();
                        }


                        param = camera.getParameters();
                        param.setFlashMode(Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(param);
                        camera.startPreview();

                        lichtan = true;

                        Toast.makeText(getApplicationContext(), R.string.light_on, Toast.LENGTH_SHORT).show();


                    } else {

                        Toast.makeText(getApplicationContext(), R.string.no_flash, Toast.LENGTH_SHORT).show();
                    }


                }

                if (isChecked == false) {


                    lichtaus();

                    Toast.makeText(getApplicationContext(), R.string.light_off, Toast.LENGTH_SHORT).show();


                }


            }
        });


    }


    public void lichtaus() {

        if (lichtan == false) {

            camera = Camera.open();
        }


        param = camera.getParameters();
        param.setFlashMode(Parameters.FLASH_MODE_OFF);
        camera.setParameters(param);
        camera.stopPreview();

        lichtan = false;


    }


    @Override
    protected void onPause() {

        if (lichtan == true) {
            lichtaus();

            camera.release();

        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        s1.setChecked(false);
        super.onResume();

    }

}










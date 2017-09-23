package com.example.yorai.minesweeper.Services;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by Yorai on 22-Sep-17.
 */

public class DegreeService extends Service {

    private final IBinder myBinder = new LocalBinder();
    private float initialPitch;
    private float initialRoll;
    private SensorManager mSensorManager;
    //private Sensor mRotationSensor;

    private static final int FROM_RADS_TO_DEGS = -57;

    public class LocalBinder extends Binder {
        public DegreeService getService() {
            // Return this instance of LocalService so clients can call public methods
            return DegreeService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        mSensorManager = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
        //mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        float[] truncatedRotationVector = new float[4];
        float[] rotationMatrix = new float[9];

        mSensorManager.getOrientation(rotationMatrix,truncatedRotationVector);
        int worldAxisX = SensorManager.AXIS_X;
        int worldAxisZ = SensorManager.AXIS_Z;
        float[] adjustedRotationMatrix = new float[9];
        SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisX, worldAxisZ, adjustedRotationMatrix);
        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMatrix, orientation);
        initialPitch = orientation[1] * FROM_RADS_TO_DEGS;
        initialRoll = orientation[2] * FROM_RADS_TO_DEGS;

        return myBinder;
    }

    public float getDegree() {
        float[] truncatedRotationVector = new float[4];
        float[] rotationMatrix = new float[9];

        mSensorManager.getOrientation(rotationMatrix,truncatedRotationVector);
        int worldAxisX = SensorManager.AXIS_X;
        int worldAxisZ = SensorManager.AXIS_Z;
        float[] adjustedRotationMatrix = new float[9];
        SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisX, worldAxisZ, adjustedRotationMatrix);
        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMatrix, orientation);
        float pitch = orientation[1] * FROM_RADS_TO_DEGS;
        float roll = orientation[2] * FROM_RADS_TO_DEGS;

        return initialPitch-pitch + initialRoll-roll;
    }
}


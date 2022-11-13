package com.faizankhalid.actions;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

public class ShakeDetector implements SensorEventListener {
	
	private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
	private static final int SHAKE_SLOP_TIME_MS = 250;
	private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;
	
	private OnShakeListener mListener;
	private long mShakeTimestamp;
	private int resetAfter = 2;
	private int mShakeCount;
	
	public static final int TYPE_CAMERA = 0;
	
	ShakeDetector(Context context, OnShakeListener listener) throws NullPointerException {
		this.mListener = listener;
		
		SensorManager sMgr;
		sMgr = (SensorManager) context.getSystemService(SENSOR_SERVICE);
		
		Sensor accel = sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sMgr.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	ShakeDetector(Context context, OnShakeListener listener, int resetAfter) throws NullPointerException {
		this(context, listener);
		this.resetAfter = resetAfter;
	}
	
	public interface OnShakeListener {
		public void onShake(int type);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// ignore
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		
		if (mListener != null) {
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];
			
			float gX = x / SensorManager.GRAVITY_EARTH;
			//float gY = y / SensorManager.GRAVITY_EARTH;
			//float gZ = z / SensorManager.GRAVITY_EARTH;
			
			// gForce will be close to 1 when there is no movement.
			//float gForce = (float)Math.sqrt(gX * gX + gY * gY + gZ * gZ);
			
			if (gX > SHAKE_THRESHOLD_GRAVITY) {
				final long now = System.currentTimeMillis();
				// ignore shake events too close to each other (250ms)
				if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
					return;
				}
				
				// reset the shake count after 3 seconds of no shakes
				if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
					mShakeCount = 0;
				}
				
				mShakeTimestamp = now;
				mShakeCount++;
				
				mListener.onShake(TYPE_CAMERA);
				
				if (mShakeCount >= resetAfter) {
					mShakeCount = 0;
				}
			}
		}
	}
}
package com.faizankhalid.actions;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SensorManager sMgr;
		sMgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);
		
		Sensor accel;
		try {
			accel = sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			sMgr.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
		}catch (NullPointerException np){
			Toast.makeText(this, "Accelerometer Sensor Not Found", Toast.LENGTH_SHORT).show();
			np.printStackTrace();
		}
	}
	
	private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
	
	@Override
	public void onSensorChanged(SensorEvent event) {
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];
			
			float gX = x / SensorManager.GRAVITY_EARTH;
			float gY = y / SensorManager.GRAVITY_EARTH;
			float gZ = z / SensorManager.GRAVITY_EARTH;
			
			// gForce will be close to 1 when there is no movement.
			float gForce = (float)Math.sqrt(gX * gX + gY * gY + gZ * gZ);
			
			if (gForce > SHAKE_THRESHOLD_GRAVITY) {
				Toast.makeText(this, "Sensor Sensed Nonsense", Toast.LENGTH_SHORT).show();
			}
		}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
	
}

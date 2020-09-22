package com.faizankhalid.actions;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ShakeDetector.OnShakeListener {
	
	ShakeDetector shake;
	FlashlightManager flash;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SensorManager sMgr;
		sMgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		
		Sensor accel;
		try {
			try {
				flash = new FlashlightManager(this);
			} catch (Exception e) {
				Toast.makeText(this, "Cannot Access Camera", Toast.LENGTH_SHORT).show();
			}
			if (flash.isAvailable){
				accel = sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
				shake = new ShakeDetector(this);
				sMgr.registerListener(shake, accel, SensorManager.SENSOR_DELAY_NORMAL);
				
			}
		} catch (NullPointerException e) {
			Toast.makeText(this, "Accelerometer Sensor Not Found", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onShake(int count) {
		Toast.makeText(this, "Shook " + count + " times", Toast.LENGTH_SHORT).show();
		if (count==2)
			flash.toggle();
	}
}

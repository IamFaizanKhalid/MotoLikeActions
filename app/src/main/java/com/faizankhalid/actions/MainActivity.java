package com.faizankhalid.actions;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ShakeDetector.OnShakeListener {
	
	ShakeDetector shake;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SensorManager sMgr;
		sMgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);
		
		Sensor accel;
		try {
			accel = sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			shake = new ShakeDetector();
			sMgr.registerListener(shake, accel, SensorManager.SENSOR_DELAY_NORMAL);
			shake.setOnShakeListener(this);
		}catch (NullPointerException np){
			Toast.makeText(this, "Accelerometer Sensor Not Found", Toast.LENGTH_SHORT).show();
			np.printStackTrace();
		}
	}
	
	@Override
	public void onShake(int count) {
		Toast.makeText(this,"Shook "+count+" times",Toast.LENGTH_SHORT).show();
	}
}

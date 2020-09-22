package com.faizankhalid.actions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ShakeDetector.OnShakeListener {
	
	ShakeDetector shake;
	FlashlightManager flash;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			flash = new FlashlightManager(this);
		} catch (Exception e) {
			showToast("Cannot Access Camera");
		}
		try {
			if (flash.isAvailable)
				shake = new ShakeDetector(this, this);
		} catch (Exception e) {
			showToast("ACCELEROMETER Sensor Not Found!");
		}
	}
	
	@Override
	public void onShake(int type, int count) {
		showToast("Shook " + count + " times");
		if (count == 2)
			flash.toggle();
	}
	
	private void showToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}

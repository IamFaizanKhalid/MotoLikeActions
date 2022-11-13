package com.faizankhalid.actions;

import android.app.Service;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class SensorService extends Service implements ShakeDetector.OnShakeListener {
	
	ShakeDetector shake;
	FlashlightManager flash;
	
	private boolean isFlashEnabled;
	
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			flash = new FlashlightManager(this);
		} catch (CameraAccessException e) {
			e.printStackTrace();
		}
		if (flash.isAvailable)
			shake = new ShakeDetector(this, this);
		showToast("Flashlight Enabled");
		isFlashEnabled = true;
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		showToast("Flashlight Disabled");
	}
	
	@Override
	public void onShake(int type) {
		if (type == ShakeDetector.TYPE_CAMERA && isFlashEnabled)
			flash.toggle();
	}
	
	private void showToast(String text){
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
}
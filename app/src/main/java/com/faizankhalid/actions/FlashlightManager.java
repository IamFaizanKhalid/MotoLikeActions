package com.faizankhalid.actions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

public class FlashlightManager {
	private CameraManager mCameraManager;
	private String mCameraId;
	private boolean status;
	boolean isAvailable;
	
	public FlashlightManager(Context context) throws CameraAccessException {
		isAvailable = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
		
		if (isAvailable) {
			//getting the camera manager and camera id
			mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
			mCameraId = mCameraManager.getCameraIdList()[0];
			mCameraManager.registerTorchCallback(new CameraManager.TorchCallback() {
				@Override
				public void onTorchModeChanged(String cameraId, boolean status) {
					super.onTorchModeChanged(cameraId, status);
					setStatus(status);
				}
			}, null);
			this.status = false;
		}
	}
	
	public boolean getStatus() {
		return status;
	}
	
	private void setStatus(boolean newStatus) {
		status = newStatus;
	}
	
	public void switchFlash(boolean status) {
		if (isAvailable) {
			try {
				mCameraManager.setTorchMode(mCameraId, status);
				this.status = status;
			} catch (CameraAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void toggle() {
		if (isAvailable) {
			try {
				mCameraManager.setTorchMode(mCameraId, !status);
				this.status = !status;
			} catch (CameraAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void turnOn() {
		if (isAvailable) {
			try {
				mCameraManager.setTorchMode(mCameraId, true);
				this.status = true;
			} catch (CameraAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void turnOff() {
		if (isAvailable) {
			try {
				mCameraManager.setTorchMode(mCameraId, false);
				this.status = false;
			} catch (CameraAccessException e) {
				e.printStackTrace();
			}
		}
	}
}

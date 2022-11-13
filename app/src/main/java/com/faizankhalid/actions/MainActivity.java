package com.faizankhalid.actions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Switch f=(Switch) findViewById(R.id.main_switch_flash);
		f.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (buttonView.isChecked())
					startService(new Intent(getApplicationContext(), SensorService.class));
				else
					stopService(new Intent(getApplicationContext(), SensorService.class));
			}
		});
	}
}

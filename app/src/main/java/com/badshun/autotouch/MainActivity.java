package com.badshun.autotouch;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.content.*;
import android.provider.*;
import android.support.design.widget.*;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		FloatingActionButton fab = findViewById(R.id.main_fab);
		fab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View p1) {
				Intent intent = new Intent(MainActivity.this, BadScriptActivity.class);
				
				startActivity(intent);
			}
		});
		
		Button btn = findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View p1) {
				if (!BadFloatingAccessbilityService.isServiceRunning()) {
					Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
					startActivity(intent);
				}
			}
		});
    }
}

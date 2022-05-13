package com.badshun.autotouch;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.graphics.*;

public class BadScriptActivity extends Activity {
	private FragmentManager fragmentManager;
	
	private TextView designTv, codeTv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.script);
		
		fragmentManager = getFragmentManager();
		
		changeFragment(R.id.script_content, new BadEditorFragment());
		
		designTv = findViewById(R.id.script_design_tv);
		designTv.setTextColor(Color.GRAY);
		designTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				cleanTvStyle();
				designTv.setBackgroundResource(R.drawable.tab_background);
				designTv.setTextColor(Color.BLACK);
				
				changeFragment(R.id.script_content, new BadGraphicFragment());
			}
		});
		
		codeTv = findViewById(R.id.script_code_tv);
		codeTv.setTextColor(Color.BLACK);
		codeTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				cleanTvStyle();
				codeTv.setBackgroundResource(R.drawable.tab_background);
				codeTv.setTextColor(Color.BLACK);
				
				changeFragment(R.id.script_content, new BadEditorFragment());
			}
		});
	}
	
	private void cleanTvStyle() {
		designTv.setBackgroundResource(R.drawable.none);
		designTv.setTextColor(Color.GRAY);
		
		codeTv.setBackgroundResource(R.drawable.none);
		codeTv.setTextColor(Color.GRAY);
	}
	
	private void changeFragment(int id, Fragment fragment) {
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(id, fragment);
		fragmentTransaction.commit();
	}
	
}

package com.badshun.autotouch;

import android.accessibilityservice.*;
import android.view.accessibility.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;
import android.graphics.*;
import android.widget.LinearLayout.*;

public class BadFloatingAccessbilityService extends AccessibilityService {
	public static boolean isServiceCreated = false;
	
	private LinearLayout floatingView;
	private WindowManager windowManager;
	
	private TextView target;
	
	private int targetX, targetY;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		floatingView = (LinearLayout) View.inflate(BadFloatingAccessbilityService.this, R.layout.floating, null);
		
		ViewGroup.LayoutParams groupParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		
		Button addBtn = new Button(BadFloatingAccessbilityService.this);
		Button startBtn = new Button(BadFloatingAccessbilityService.this);
		Button pauseBtn = new Button(BadFloatingAccessbilityService.this);
		
		addBtn.setText("+");
		startBtn.setText("start");
		pauseBtn.setText("pause");
		
		addBtn.setLayoutParams(groupParams);
		startBtn.setLayoutParams(groupParams);
		pauseBtn.setLayoutParams(groupParams);

		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

		final WindowManager.LayoutParams floatingWindowParameter = new WindowManager.LayoutParams(300, 500, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		floatingWindowParameter.x = 0;
		floatingWindowParameter.y = 0;
		floatingWindowParameter.gravity = Gravity.CENTER | Gravity.CENTER;
		
		floatingView.addView(addBtn);
		floatingView.addView(startBtn);
		floatingView.addView(pauseBtn);

		windowManager.addView(floatingView, floatingWindowParameter);
		
		addBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View p1) {
				target = (TextView) View.inflate(BadFloatingAccessbilityService.this, R.layout.target, null);
				
				final WindowManager.LayoutParams targetWindowParameter = new WindowManager.LayoutParams(300, 500, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
				targetWindowParameter.x = 0;
				targetWindowParameter.y = 0;
				targetWindowParameter.gravity = Gravity.CENTER | Gravity.CENTER;
				
				windowManager.addView(target, targetWindowParameter);
				
				target.setOnTouchListener(new OnTouchListener() {
					private WindowManager.LayoutParams updateParams = targetWindowParameter;
					private int x, y;
					private float touchedX, touchedY;

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						switch(event.getAction()) {
							case MotionEvent.ACTION_DOWN:
								x = updateParams.x;
								y = updateParams.y;

								touchedX = event.getRawX();
								touchedY = event.getRawY();

								break;
							case MotionEvent.ACTION_MOVE:
								updateParams.x = (int)(x + (event.getRawX() - touchedX));
								updateParams.y = (int)(y + (event.getRawY() - touchedY));
								
								int[] localtion = new int[2];
								
								target.getLocationOnScreen(localtion);
								
								targetX = localtion[0];
								targetY = localtion[1];
								
								target.setText("targetX:" + targetX+ "\ntargetY:" + targetY + "\nrawX:" + event.getRawX() + "\nrawY:" + event.getRawY() + "\ntouchedX:" + touchedX + "\ntouchedY:" + touchedY);

								windowManager.updateViewLayout(target, updateParams);
						}

						return false;
					}
						
				});
				
				target.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View p1) {
						//windowManager.removeViewImmediate(target);
						swipe(targetX, targetY, targetX, targetY - 200);
						//返回true不会处理showContextMenuForChild事件，如果返回false就报空指针，因为控件已被移除
					}
				});
			}
		});
		
		startBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View p1) {
				tap(targetX, targetY);
			}
		});

		floatingView.setOnTouchListener(new OnTouchListener() {
			private WindowManager.LayoutParams updateParams = floatingWindowParameter;
			private int x, y;
			private float touchedX, touchedY;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						x = updateParams.x;
						y = updateParams.y;
						
						touchedX = event.getRawX();
						touchedY = event.getRawY();
						
						break;
					case MotionEvent.ACTION_MOVE:
						updateParams.x = (int)(x + (event.getRawX() - touchedX));
						updateParams.y = (int)(y + (event.getRawY() - touchedY));
						
						windowManager.updateViewLayout(floatingView, updateParams);
				}
				
				return false;
			}
		});
	}
	
	private void tap(int x, int y) {
		GestureDescription.Builder builder = new GestureDescription.Builder();
		
        Path path = new Path();
        path.moveTo(x , y);
		
        builder.addStroke(new GestureDescription.StrokeDescription(path, 100L, 100L));
		
        GestureDescription gesture = builder.build();
		
        dispatchGesture(gesture, new GestureResultCallback() {
			@Override
			public void onCompleted(GestureDescription gestureDescription) {
				super.onCompleted(gestureDescription);
			}

			@Override
			public void onCancelled(GestureDescription gestureDescription) {
				super.onCancelled(gestureDescription);
			}
		}, null);
		
	}
	
	private void swipe(int x1, int y1,int x2 , int y2) {

        GestureDescription.Builder builder = new GestureDescription.Builder();
		
        Path path = new Path();
        path.moveTo(x1 , y1);
        path.lineTo(x2 , y2);
		
        builder.addStroke(new GestureDescription.StrokeDescription(path, 0L, 500L));
		
        GestureDescription gesture = builder.build();
        dispatchGesture(gesture, new GestureResultCallback() {
			@Override
			public void onCompleted(GestureDescription gestureDescription) {
				super.onCompleted(gestureDescription);
			}

			@Override
			public void onCancelled(GestureDescription gestureDescription) {
				super.onCancelled(gestureDescription);
			}
			
		}, null);

    }

	@Override
	protected void onServiceConnected() {
		super.onServiceConnected();
		
		isServiceCreated = true;
	}

	@Override
	protected boolean onKeyEvent(KeyEvent event) {
		return super.onKeyEvent(event);
	}
	
	
	
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		
	}

	@Override
	public void onInterrupt() {
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		isServiceCreated = false;
	}
	
	public static boolean isServiceRunning() {
		return isServiceCreated;
	}
	
}

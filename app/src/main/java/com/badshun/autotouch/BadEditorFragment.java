package com.badshun.autotouch;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.widget.TextView.*;
import android.view.*;
import android.text.*;
import android.text.method.*;
import android.util.*;

public class BadEditorFragment extends Fragment {
    private int maxLineNumber;
	
    private float mTextSize;
    private float mFirstTouchX;
    private float mFirstTouchY;
    private float mSecondTouchX;
    private float mSecondTouchY;
    private double firstPointerLength;
	
	private TextView lineNumberText;
	private EditText scriptEditor;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.script_editor, container, false);
		
		maxLineNumber = 1;

		lineNumberText = view.findViewById(R.id.script_editor_line_number);

		scriptEditor = view.findViewById(R.id.script_editor_edittext);
		scriptEditor.setMovementMethod(ScrollingMovementMethod.getInstance());
		scriptEditor.setHorizontallyScrolling(true);
		scriptEditor.setFocusable(true);
		scriptEditor.addTextChangedListener(new TextWatcher() {
			int bLineNumber, aLineNumber;

			String lineNum = "";

			@Override
			public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
				bLineNumber = scriptEditor.getLineCount();
			}

			@Override
			public void onTextChanged(CharSequence p1, int p2, int p3, int p4){
				aLineNumber = scriptEditor.getLineCount();

				if(aLineNumber != bLineNumber) {
					maxLineNumber = aLineNumber;

					changeLineNumber();
				}
			}

			@Override
			public void afterTextChanged(Editable p1) {
				aLineNumber = scriptEditor.getLineCount();

				if(aLineNumber != bLineNumber) {
					maxLineNumber = aLineNumber;
						
					changeLineNumber();
				}
			}

			private void changeLineNumber() {
				for(int i = 0; i < maxLineNumber; i ++) {
					lineNum += (i + 1) + "\n";
				}

				lineNumberText.setText(lineNum);

				lineNum = "";
			}
		});

		scriptEditor.setOnTouchListener(new OnTouchListener() {
			float distance;

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				

					return false;
				}
			});

		
		return view;
	}
	
	private void zoom(double secondPointerLength) {
		double scaleRate = secondPointerLength / firstPointerLength;
		float textSize = (float) (lineNumberText.getTextSize() * scaleRate);
		
		lineNumberText.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
	}
}

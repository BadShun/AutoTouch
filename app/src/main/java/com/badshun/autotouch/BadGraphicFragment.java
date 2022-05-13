package com.badshun.autotouch;

import android.view.*;
import android.os.*;
import android.app.*;

public class BadGraphicFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.script_graphic, container, false);
		
		return view;
	}

}

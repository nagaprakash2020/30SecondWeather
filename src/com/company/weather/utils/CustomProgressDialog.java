package com.company.weather.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import com.company.weather.R;

public class CustomProgressDialog extends ProgressDialog {
	
	String loadingText;
	
	public CustomProgressDialog(Context context) {
		super(context);
	}
	
	public static ProgressDialog ctor(Context context,String loadingTextIn) {
		
	  CustomProgressDialog dialog = new CustomProgressDialog(context);
	  dialog.setIndeterminate(true);
	  dialog.setCancelable(false);
	  
	  return dialog;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressdialog_custom);
	}
	
}

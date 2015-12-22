package com.example.tipcalculator;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
public class Aboutus extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.tipactivity3);
	
	TextView aboutTitleText = (TextView) findViewById(R.id.aboutTitle);
	
    
	aboutTitleText.setGravity( Gravity.CENTER_HORIZONTAL);
	aboutTitleText.setText("\n\n\n\n\n\n\n\n\nDeveloped By\nName: SRINIVAS HAVANUR");
	aboutTitleText.append("\nEmail: shavanur@cs.odu.edu\nVersion: 4.1");
	}
}
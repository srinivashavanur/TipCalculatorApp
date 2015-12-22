package com.example.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.widget.Button;
import android.media.MediaPlayer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

public class TipMainActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_main);
		//This code is to set the ODU logo in the Home screen of the App
		ImageView image = (ImageView) findViewById(R.id.imageView1);
		 image.setImageResource(R.drawable.odu);
		 
		 //This code is to add Course details and App name
		TextView myTitleText = (TextView) findViewById(R.id.myTitle);
	      
	        myTitleText.setGravity( Gravity.CENTER_HORIZONTAL);
	        myTitleText.setText("CS441/541: Programming Assignment #1\n\n\n\n");
	        myTitleText.append(Html.fromHtml("<u>Tip Calculator</u>"));
	              
	        
	        //This code is to exit from an app
	           Button btn3 = (Button) findViewById(R.id.button3);
	           btn3.setOnClickListener(new OnClickListener() {

	               @Override
	               public void onClick(View v) {
	                   
	            	   MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.doorclose);
	            	   mp.start();
	                   finish();
	                   //System.exit(0);
	               }
	           });
	        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tip_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handleonClick_history clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClick(View view) {
		MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.buttonclick);
		mp.start();
		startActivity(new Intent("com.example.TipActivity"));
		}
	
	public void onClick_about(View view) {
		MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.buttonclick);
		mp.start();
		startActivity(new Intent("com.example.Aboutus"));
		}
	
	public void onClick_history(View view)
	{
		MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.buttonclick);
		mp.start();
		startActivity(new Intent("com.example.HistoryActivity"));
	}
	
	public void onClick_settings(View view)
	{
		MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.buttonclick);
		mp.start();
		startActivity(new Intent("com.example.AlarmActivity"));
	}
	
	
   
}

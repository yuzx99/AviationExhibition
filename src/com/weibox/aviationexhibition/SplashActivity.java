package com.weibox.aviationexhibition;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class SplashActivity extends Activity {

	private Timer timer;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.splash);
		
		startTimer();
	}
	
	public void startTimer(){
		if(timer == null){
			timer = new Timer();
			timer.schedule(new FlashTask(), 3000);
		}
	}
	
	private class FlashTask extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			openNextpage();
		}
		
	}
	private void openNextpage(){
		Intent intent = new Intent(this,HomepageActivity.class);
		startActivity(intent);
		this.finish();
	}
}



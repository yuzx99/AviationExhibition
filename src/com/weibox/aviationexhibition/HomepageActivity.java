package com.weibox.aviationexhibition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class HomepageActivity extends Activity {
	private SlideHolder mSlideHolder;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.homepage);		
		mSlideHolder = (SlideHolder)findViewById(R.id.slideHolder);
		
		View toggleView = findViewById(R.id.textView);
		toggleView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mSlideHolder.toggle();
			}
		});
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub					
				mSlideHolder.close();
			}
		});
		
		Button btn2 = (Button)findViewById(R.id.button2);
		btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Toast.makeText(HomepageActivity.this, "Button", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(HomepageActivity.this,InfoActivity.class);
				startActivity(intent);
				mSlideHolder.close();
				
			}
		});
	}
	
	
}

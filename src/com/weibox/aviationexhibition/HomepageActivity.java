package com.weibox.aviationexhibition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class HomepageActivity extends Activity {
	private SlideHolder mSlideHolder;
	private ViewPager vPager = null;
	// 装点点的ImageView数组
	private ImageView[] imageViews = null;
	private ImageView imageView = null;
	private AtomicInteger what = new AtomicInteger(0);
	private boolean isContinue = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homepage);
		mSlideHolder = (SlideHolder) findViewById(R.id.slideHolder);

		// View toggleView = findViewById(R.id.textView);
		// toggleView.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// mSlideHolder.toggle();
		// }
		// });
		initViewPager();

		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mSlideHolder.close();
			}
		});

		Button btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Toast.makeText(HomepageActivity.this, "Button",
				// Toast.LENGTH_LONG).show();
				Intent intent = new Intent(HomepageActivity.this,
						InfoActivity.class);
				startActivity(intent);
				mSlideHolder.close();

			}
		});

	}

	public void initViewPager() {
		vPager = (ViewPager) findViewById(R.id.viewpager);
		ViewGroup vGroup = (ViewGroup) findViewById(R.id.viewGroup);

		// 存放ViewPager图片
		List<View> lPics = new ArrayList<View>();

		Resources res = getResources();
		ImageView img1 = new ImageView(this);
		img1.setBackgroundResource(R.drawable.ad3);
//		img1.setBackgroundDrawable(compressImage(res.getDrawable(R.drawable.test_1)));
		lPics.add(img1);

		ImageView img2 = new ImageView(this);
		img2.setBackgroundResource(R.drawable.ad3);
//		img2.setBackgroundDrawable(compressImage(res.getDrawable(R.drawable.test_2)));
		lPics.add(img2);

		ImageView img3 = new ImageView(this);
		img3.setBackgroundResource(R.drawable.ad3);
//		img3.setBackgroundDrawable(compressImage(res.getDrawable(R.drawable.test_3)));
		lPics.add(img3);

		ImageView img4 = new ImageView(this);
		img4.setBackgroundResource(R.drawable.ad3);
//		img4.setBackgroundDrawable(compressImage(res.getDrawable(R.drawable.test_4)));
		lPics.add(img4);

		// 对imageViews进行填充
		imageViews = new ImageView[lPics.size()];
		for (int i = 0; i < lPics.size(); i++) {
			imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(20, 20));
			imageView.setPadding(5, 5, 5, 5);
			imageViews[i] = imageView;
			if (i == 0) {
				imageViews[i]
						.setBackgroundResource(R.drawable.test_page_indicator_focused);
			} else {
				imageViews[i]
						.setBackgroundResource(R.drawable.test_page_indicator_unfocused);
			}
			vGroup.addView(imageViews[i]);
		}

		vPager.setAdapter(new AdvAdapter(lPics));
		vPager.setOnPageChangeListener(new GuidePageChangeListener());
		vPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					isContinue = false;
					mSlideHolder.setEnabled(false);
					break;
				case MotionEvent.ACTION_UP:
					isContinue = true;
					mSlideHolder.setEnabled(true);
					break;
				default:
					isContinue = true;
					mSlideHolder.setEnabled(true);
					break;
				}
				return false;
			}
		});

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if (isContinue) {
						viewHandler.sendEmptyMessage(what.get());
						whatOption();
					}
				}
			}

		}).start();
	}

	private void whatOption() {
		what.incrementAndGet();
		if (what.get() > imageViews.length - 1) {
			what.getAndAdd(-4);
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

		}
	}

	private final Handler viewHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			vPager.setCurrentItem(msg.what);
			super.handleMessage(msg);
		}

	};

	private final class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			what.getAndSet(arg0);
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.test_page_indicator_focused);
				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.test_page_indicator_unfocused);
				}
			}

		}

	}

	private final class AdvAdapter extends PagerAdapter {
		private List<View> views = null;

		public AdvAdapter(List<View> views) {
			this.views = views;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return views.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1), 0);
			return views.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}
	}

	private Drawable compressImage(Drawable drawable) {
		BitmapDrawable bd = (BitmapDrawable)drawable;				
		Bitmap image = bd.getBitmap();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) {
			baos.reset();
			options -= 10;
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		Bitmap bitmap = BitmapFactory.decodeStream(bais,null,null);	
		
		BitmapDrawable bd2 = new BitmapDrawable(bitmap);
		Drawable d = (Drawable) bd2;
		return d;
	}
	
}

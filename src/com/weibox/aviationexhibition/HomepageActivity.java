package com.weibox.aviationexhibition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.weibox.util.Utility;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class HomepageActivity extends Activity {
	private SlideHolder mSlideHolder;
	private ViewPager vPager = null;
	private long waitTime = 3000;
	private long touchTime = 0;
	// 装点点的ImageView数组
	private ImageView[] imageViews = null;
	private ImageView imageView = null;
	private AtomicInteger what = new AtomicInteger(0);
	private boolean isContinue = true;
	// menu
	private ListView menuListView = null;
	private List<Map<String, Object>> listItems;

	private ListView categoryList = null;
	private ListView infoList = null;
	private String[] infoMapping = new String[] { "infoPic", "infoTitle" };
	private int[] itemMapping = new int[] { R.id.catePicItem, R.id.cateTitle };

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
		initMenu();
		initViewPager();
		initInfoList();
	}

	public void initMenu() {
		menuListView = (ListView) findViewById(R.id.list_menu);
		Integer[] imgIDs = new Integer[] { R.drawable.ic_sidebar_homepage,
				R.drawable.ic_sidebar_news, R.drawable.ic_sidebar_exhibition,
				R.drawable.ic_sidebar_message, R.drawable.ic_sidebar_about_us };
		String[] itemNames = new String[] { getString(R.string.menu_home),
				getString(R.string.menu_info),
				getString(R.string.menu_exhibition),
				getString(R.string.menu_message),
				getString(R.string.menu_aboutus) };

		listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < imgIDs.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("menuImage", imgIDs[i]);
			map.put("menuText", itemNames[i]);
			listItems.add(map);
		}

		SimpleAdapter menuAdapter = new SimpleAdapter(this, listItems,
				R.layout.menu_item, new String[] { "menuImage", "menuText" },
				new int[] { R.id.menuImageItem, R.id.menuTextItem });
		menuListView.setAdapter(menuAdapter);
		Utility.setListViewHeightBasedOnChildren(menuListView);

		menuListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 0:home 1:info 2:exhibition 3:message 4:about
				// TODO Auto-generated method stub
				Intent intent = null;
				switch (arg2) {
				case 0:
					mSlideHolder.close();
					break;
				case 1:
					intent = new Intent(HomepageActivity.this,
							InfoActivity.class);
					startActivity(intent);
					mSlideHolder.close();
					finish();
					break;
				case 2:
					intent = new Intent(HomepageActivity.this,
							ExhibitionActivity.class);
					startActivity(intent);
					mSlideHolder.close();
					finish();
					break;
				case 3:
					intent = new Intent(HomepageActivity.this,
							MessageActivity.class);
					startActivity(intent);
					mSlideHolder.close();
					finish();
					break;
				case 4:
					intent = new Intent(HomepageActivity.this,
							AboutusActivity.class);
					startActivity(intent);
					mSlideHolder.close();
					finish();
					break;
				default:
					mSlideHolder.close();
					break;
				}

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
		// img1.setBackgroundDrawable(compressImage(res.getDrawable(R.drawable.test_1)));
		lPics.add(img1);

		ImageView img2 = new ImageView(this);
		img2.setBackgroundResource(R.drawable.ad3);
		// img2.setBackgroundDrawable(compressImage(res.getDrawable(R.drawable.test_2)));
		lPics.add(img2);

		ImageView img3 = new ImageView(this);
		img3.setBackgroundResource(R.drawable.ad3);
		// img3.setBackgroundDrawable(compressImage(res.getDrawable(R.drawable.test_3)));
		lPics.add(img3);

		ImageView img4 = new ImageView(this);
		img4.setBackgroundResource(R.drawable.ad3);
		// img4.setBackgroundDrawable(compressImage(res.getDrawable(R.drawable.test_4)));
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

	private void initInfoList() {
		categoryList = (ListView) findViewById(R.id.categoryList);
		SimpleAdapter cateAdapter = new SimpleAdapter(this, getCategory(),
				R.layout.cate_item, infoMapping, itemMapping);
		categoryList.setAdapter(cateAdapter);
		Utility.setListViewHeightBasedOnChildren(categoryList);
		infoList = (ListView) findViewById(R.id.infoList);
		infoList.setAdapter(cateAdapter);
		Utility.setListViewHeightBasedOnChildren(infoList);

	}

	private ArrayList<HashMap<String, Object>> getCategory() {
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		Integer[] imgIDs = new Integer[] { R.drawable.test_pho,
				R.drawable.test_pho, R.drawable.test_pho };
		String[] itemTitle = new String[] { "中国枭龙战机搏击长空", "中国八一跳伞队简介",
				"中国大飞机低空低速畅通" };
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < imgIDs.length; i++) {
			map.put(infoMapping[0], imgIDs[i]);
			map.put(infoMapping[1], itemTitle[i]);
			listItem.add(map);
		}
		return listItem;
	}

	private Drawable compressImage(Drawable drawable) {
		BitmapDrawable bd = (BitmapDrawable) drawable;
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
		Bitmap bitmap = BitmapFactory.decodeStream(bais, null, null);

		BitmapDrawable bd2 = new BitmapDrawable(bitmap);
		Drawable d = (Drawable) bd2;
		return d;
	}
	
	@Override  
    public void onBackPressed() {  
        long currentTime = System.currentTimeMillis();  
        if((currentTime-touchTime)>=waitTime) {  
            Toast.makeText(this, this.getString(R.string.exit_again), Toast.LENGTH_SHORT).show();  
            touchTime = currentTime;  
        }else {  
          finish(); 
          System.exit(0);
        }  
    } 

}

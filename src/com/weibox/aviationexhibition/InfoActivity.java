package com.weibox.aviationexhibition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weibox.util.Utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.test.UiThreadTest;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class InfoActivity extends Activity {

	// menu
	private SlideHolder mSlideHolder;
	private ListView menuListView = null;
	private List<Map<String, Object>> listItems;
	private long waitTime = 3000;
	private long touchTime = 0;

	private ListView infoList = null;
	private String[] infoMapping = new String[] { "infoPic", "infoTitle",
			"infoDate" };
	private int[] itemMapping = new int[] { R.id.infoPicItem,
			R.id.infoTitleItem, R.id.infoDateItem };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.info);
		mSlideHolder = (SlideHolder) findViewById(R.id.slideHolderInfo);
		mSlideHolder.setEnabled(false);
		initMenu();
		initInfo();
		
		ScrollView scrollView = (ScrollView)findViewById(R.id.infoScrollView);
		scrollView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				float areaX = InfoActivity.this.getWindowManager()
						.getDefaultDisplay().getWidth() / 5;
				Toast.makeText(getApplicationContext(), String.valueOf(areaX),
						Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(), "here", Toast.LENGTH_SHORT)
						.show();
				if (areaX - arg0.getX() > 1.0) {
					mSlideHolder.setEnabled(true);
					Toast.makeText(getApplicationContext(), "here",
							Toast.LENGTH_LONG).show();
				}
				return false;
			}
		});
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
					intent = new Intent(InfoActivity.this,
							HomepageActivity.class);
					startActivity(intent);
					mSlideHolder.close();
					finish();
					break;
				case 1:
					mSlideHolder.close();
					break;
				case 2:
					intent = new Intent(InfoActivity.this,
							ExhibitionActivity.class);
					startActivity(intent);
					mSlideHolder.close();
					finish();
					break;
				case 3:
					intent = new Intent(InfoActivity.this,
							MessageActivity.class);
					startActivity(intent);
					mSlideHolder.close();
					finish();
					break;
				case 4:
					intent = new Intent(InfoActivity.this,
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

	private void initInfo() {
		infoList = (ListView) findViewById(R.id.infoPageList);
		SimpleAdapter adapter = new SimpleAdapter(this, getDate(),
				R.layout.info_item, infoMapping, itemMapping);
		infoList.setAdapter(adapter);
		Utility.setListViewHeightBasedOnChildren(infoList);
	}

	private ArrayList<HashMap<String, Object>> getDate() {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		// data
		int[] imgIDs = new int[] { R.drawable.test_pic, R.drawable.test_pic,
				R.drawable.test_pic, R.drawable.test_pic, R.drawable.test_pic,
				R.drawable.test_pic, R.drawable.test_pic, R.drawable.test_pic,
				R.drawable.test_pic, R.drawable.test_pic };
		String[] titles = new String[] { "第1届中国航空航天博览会成功落幕", "第2届中国航展飞行表演时刻表",
				"第3届中国航空航天博览会成功落幕", "第4届中国航展飞行表演时刻表", "第5届中国航空航天博览会成功落幕",
				"第6届中国航展飞行表演时刻表", "第7届中国航空航天博览会成功落幕", "第8届中国航展飞行表演时刻表",
				"第9届中国航展飞行表演时刻表", "第10届中国航展飞行表演时刻表" };
		String[] dates = new String[] { "2014-11-9", "2014-11-8", "2014-11-9",
				"2014-11-8", "2014-11-9", "2014-11-8", "2014-11-9",
				"2014-11-8", "2014-11-8", "2014-11-8" };
		for (int i = 0; i < imgIDs.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(infoMapping[0], imgIDs[i]);
			map.put(infoMapping[1], titles[i]);
			map.put(infoMapping[2], dates[i]);
			list.add(map);
		}
		return list;
	}

	private OnGestureListener gestListener = new OnGestureListener() {

		@Override
		public boolean onSingleTapUp(MotionEvent arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onShowPress(MotionEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
				float arg3) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onLongPress(MotionEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
				float arg3) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onDown(MotionEvent arg0) {
			// TODO Auto-generated method stub
			float areaX = InfoActivity.this.getWindowManager()
					.getDefaultDisplay().getWidth() / 5;
			Toast.makeText(getApplicationContext(), String.valueOf(areaX),
					Toast.LENGTH_LONG).show();
			Toast.makeText(getApplicationContext(), "here", Toast.LENGTH_LONG)
					.show();
			if (areaX - arg0.getX() > 0.01) {
				mSlideHolder.setEnabled(true);
				Toast.makeText(getApplicationContext(), "here",
						Toast.LENGTH_LONG).show();
			}
			return false;
		}
	};
}

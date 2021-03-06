package com.weibox.aviationexhibition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weibox.util.Utility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AboutusActivity extends Activity {
	private SlideHolder mSlideHolder;
	// menu
	private ListView menuListView = null;
	private List<Map<String, Object>> listItems;

	private long waitTime = 3000;
	private long touchTime = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.aboutus);
		mSlideHolder = (SlideHolder) findViewById(R.id.slideHolderAbout);

		initMenu();
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
					intent = new Intent(AboutusActivity.this,
							HomepageActivity.class);
					startActivity(intent);
					mSlideHolder.close();
					finish();
					break;
				case 1:
					intent = new Intent(AboutusActivity.this,
							InfoActivity.class);
					startActivity(intent);
					mSlideHolder.close();
					finish();
					break;
				case 2:
					intent = new Intent(AboutusActivity.this,
							ExhibitionActivity.class);
					startActivity(intent);
					mSlideHolder.close();
					finish();
					break;
				case 3:
					intent = new Intent(AboutusActivity.this,
							MessageActivity.class);
					startActivity(intent);
					mSlideHolder.close();
					finish();
					break;
				case 4:					
					mSlideHolder.close();
					break;
				default:
					mSlideHolder.close();
					break;
				}				
			}

		});
	}
	@Override
	public void onBackPressed() {
		long currentTime = System.currentTimeMillis();
		if ((currentTime - touchTime) >= waitTime) {
			Toast.makeText(this, this.getString(R.string.exit_again),
					Toast.LENGTH_SHORT).show();
			touchTime = currentTime;
		} else {
			finish();
			System.exit(0);
		}
	}
}

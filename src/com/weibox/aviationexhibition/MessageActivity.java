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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MessageActivity extends Activity {
	private SlideHolder mSlideHolder;
	// menu
	private ListView menuListView = null;
	private List<Map<String, Object>> listItems;

	private ListView todayMsgList = null;
	private ListView ytdMsgList = null;
	private ListView earlierMsgList = null;
	private int[] msgItems = new int[] { R.id.msgImageItem, R.id.msgNameItem,
			R.id.msgContentItem };
	private String[] msgMapping = new String[] { "user_photo", "user_name",
			"msg_content" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.message);
		mSlideHolder = (SlideHolder) findViewById(R.id.slideHolderMessage);
		initMenu();
		initLeaveMessage();
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
					intent = new Intent(MessageActivity.this,
							HomepageActivity.class);
					startActivity(intent);
					mSlideHolder.close();
					finish();
					break;
				case 1:
					intent = new Intent(MessageActivity.this,
							InfoActivity.class);
					startActivity(intent);
					mSlideHolder.close();
					finish();
					break;
				case 2:
					intent = new Intent(MessageActivity.this,
							ExhibitionActivity.class);
					startActivity(intent);
					mSlideHolder.close();
					finish();
					break;
				case 3:
					mSlideHolder.close();
					break;
				case 4:
					intent = new Intent(MessageActivity.this,
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

	private void initLeaveMessage() {
		todayMsgList = (ListView) findViewById(R.id.todayMsgList);
		ytdMsgList = (ListView) findViewById(R.id.ytdMsgList);
		earlierMsgList = (ListView) findViewById(R.id.earlierMsgList);
		SimpleAdapter todayAdapter = new SimpleAdapter(this, getLeaveMessage(),
				R.layout.msg_item, msgMapping, msgItems);
		todayMsgList.setAdapter(todayAdapter);
		todayMsgList.setDividerHeight(0);
		Utility.setListViewHeightBasedOnChildren(todayMsgList);
		ytdMsgList.setAdapter(todayAdapter);
		Utility.setListViewHeightBasedOnChildren(ytdMsgList);
		earlierMsgList.setAdapter(todayAdapter);
		Utility.setListViewHeightBasedOnChildren(earlierMsgList);

		ImageButton ibtnLeaveMsg = (ImageButton) findViewById(R.id.ibtnLeaveMsg);
		ibtnLeaveMsg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MessageActivity.this,
						LeaveMessageActivity.class);
				startActivity(intent);
			}
		});
	}

	private ArrayList<HashMap<String, Object>> getLeaveMessage() {
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		Integer[] imgIDs = new Integer[] { R.drawable.test_pho,
				R.drawable.test_pho, R.drawable.test_pho };
		String[] itemNames = new String[] { getString(R.string.test_name),
				getString(R.string.test_name), getString(R.string.test_name) };
		String[] itemContents = new String[] {
				getString(R.string.test_mes_content),
				getString(R.string.test_mes_content),
				getString(R.string.test_mes_content) };
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < imgIDs.length; i++) {
			map.put(msgMapping[0], imgIDs[i]);
			map.put(msgMapping[1], itemNames[i]);
			map.put(msgMapping[2], itemContents[i]);
			listItem.add(map);
		}
		return listItem;
	}
}

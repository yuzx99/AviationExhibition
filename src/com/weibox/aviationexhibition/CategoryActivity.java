package com.weibox.aviationexhibition;

import java.util.ArrayList;
import java.util.HashMap;

import com.weibox.util.Utility;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class CategoryActivity extends Activity {

	private ListView cateInfoList = null;
	private String[] infoMapping = new String[] { "infoPic", "infoTitle",
			"infoDate" };
	private int[] itemMapping = new int[] { R.id.infoPicItem,
			R.id.infoTitleItem, R.id.infoDateItem };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.category);

		initInfo();
	}

	private void initInfo() {
		cateInfoList = (ListView) findViewById(R.id.cateInfoList);
		SimpleAdapter adapter = new SimpleAdapter(this, getDate(),
				R.layout.info_item, infoMapping, itemMapping);
		cateInfoList.setAdapter(adapter);
		Utility.setListViewHeightBasedOnChildren(cateInfoList);
		
	}

	private ArrayList<HashMap<String, Object>> getDate() {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		// data
		int[] imgIDs = new int[] { R.drawable.test_pic, R.drawable.test_pic,
				R.drawable.test_pic, R.drawable.test_pic, R.drawable.test_pic,
				R.drawable.test_pic, R.drawable.test_pic, R.drawable.test_pic,
				R.drawable.test_pic, R.drawable.test_pic };
		String[] titles = new String[] { "��1���й����պ��첩����ɹ���Ļ", "��2���й���չ���б���ʱ�̱�",
				"��3���й����պ��첩����ɹ���Ļ", "��4���й���չ���б���ʱ�̱�", "��5���й����պ��첩����ɹ���Ļ",
				"��6���й���չ���б���ʱ�̱�", "��7���й����պ��첩����ɹ���Ļ", "��8���й���չ���б���ʱ�̱�",
				"��9���й���չ���б���ʱ�̱�", "��10���й���չ���б���ʱ�̱�" };
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

	
	@Override
	public void onBackPressed() {
//		Toast.makeText(CategoryActivity.this, String.valueOf(cateInfoList.getHeight()),
//				Toast.LENGTH_LONG).show();
		finish();
	}
}

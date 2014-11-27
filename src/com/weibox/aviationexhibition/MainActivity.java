package com.weibox.aviationexhibition;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);						
		
	}
	
	public static class TabManager implements TabHost.OnTabChangeListener {
		private final MainActivity mActivity;
		// save tab
		private final Map<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
		private final TabHost mTabHost;
		private final int mContainerID;
		private TabInfo mLastTab;

		/**
		 * @param activity context
		 * @param tabHost tab
		 * @param containerID fragment's parent note
		 */
		public TabManager(MainActivity activity, TabHost tabHost,
				int containerID) {
			mActivity = activity;
			mTabHost = tabHost;
			mContainerID = containerID;
			mTabHost.setOnTabChangedListener(this);
		}

		static final class TabInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;
			private Fragment fragment;

			TabInfo(String _tag, Class<?> _clss, Bundle _args) {
				tag = _tag;
				clss = _clss;
				args = _args;
			}
		}

		static class TabFactory implements TabHost.TabContentFactory {
			private Context mContext;
			TabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumHeight(0);
				v.setMinimumWidth(0);
				return v;
			}
		}

		// add tab
		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			tabSpec.setContent(new TabFactory(mActivity));
			String tag = tabSpec.getTag();

			TabInfo info = new TabInfo(tag, clss, args);
			final FragmentManager fm = mActivity.getSupportFragmentManager();
			info.fragment = fm.findFragmentByTag(tag);
			// isDetached 
			if (info.fragment != null && !info.fragment.isDetached()) {
				FragmentTransaction ft = fm.beginTransaction();
				ft.detach(info.fragment);
				ft.commit();
			}
			mTabs.put(tag, info);
			mTabHost.addTab(tabSpec);
		}

		@Override
		public void onTabChanged(String tabId) {
			TabInfo newTab = mTabs.get(tabId);
			if (mLastTab != newTab) {
				FragmentManager fragmentManager = mActivity
						.getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager
						.beginTransaction();
				if (mLastTab != null && mLastTab.fragment != null) {
					fragmentTransaction.detach(mLastTab.fragment);
				}
				if (newTab != null) {
					if (newTab.fragment == null) {
						newTab.fragment = Fragment.instantiate(mActivity,
								newTab.clss.getName(), newTab.args);
						fragmentTransaction.add(mContainerID, newTab.fragment,
								newTab.tag);
					} else {						
						fragmentTransaction.attach(newTab.fragment);
					}
				}
				mLastTab = newTab;
				fragmentTransaction.commit();
				fragmentManager.executePendingTransactions();
			}
		}
	}
}

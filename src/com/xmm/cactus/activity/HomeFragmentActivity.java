package com.xmm.cactus.activity;

import java.util.ArrayList;
import java.util.Arrays;

import com.xmm.cactus.fragment.ChooseFragment;
import com.xmm.cactus.fragment.HomeFragment;
import com.xmm.cactus.fragment.MyFragment;
import com.xmm.cactus.fragment.ScheduleFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;

/**
 * 首页
 * 
 * @author DENG_MING_HAI
 * @date 2016年4月12日
 */
public class HomeFragmentActivity extends BaseFragmentActivity {
	
    private static final String FRAGMENT_TAGS = "fragmentTags";
    private static final String CURR_INDEX = "currIndex";
	/** 点击容器 */
	private RadioGroup group;
	/** 碎片标签名 */
	private ArrayList<String> fragmentTags;
	/** 碎片管理者 */
	private FragmentManager fragmentManager;
	/** 碎片位置 */
	private static int currIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_fragment);
		fragmentManager = getSupportFragmentManager();
		if (savedInstanceState == null) {
			initFragmentData();
			initView();
		} else {
			initFromSavedInstantsState(savedInstanceState);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(CURR_INDEX, currIndex);
		outState.putStringArrayList(FRAGMENT_TAGS, fragmentTags);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		/**  Activity 被系统销毁时  */
		initFromSavedInstantsState(savedInstanceState);
	}

	private void initFromSavedInstantsState(Bundle savedInstanceState) {
		currIndex = savedInstanceState.getInt(CURR_INDEX);
		fragmentTags = savedInstanceState.getStringArrayList(FRAGMENT_TAGS);
		showFragment();
	}

	private void initFragmentData() {
		currIndex = 0;
		fragmentTags = new ArrayList<String>(
				Arrays.asList("HomeFragment", "ChooseFragment", "ScheduleFragment", "MyFragment"));
	}

	private void initView() {
		group = (RadioGroup) findViewById(R.id.group);
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rdio_home:
					currIndex = 0;
					break;
				case R.id.rdio_choose:
					currIndex = 1;
					break;
				case R.id.rdio_schedule:
					currIndex = 2;
					break;
				case R.id.rdio_ny:
					currIndex = 3;
					break;
				default:
					break;
				}
				showFragment();
			}
		});
		showFragment();
	}

	private void showFragment() {
		/*if (currIndex == 3) {
			UIHelperUtils.showLogin(HomeFragmentActivity.this);
		}*/

		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
		if (fragment == null) {
			fragment = instantFragment(currIndex);
		}
		for (int i = 0; i < fragmentTags.size(); i++) {
			Fragment fg = fragmentManager.findFragmentByTag(fragmentTags.get(i));
			if (fg != null && fg.isAdded()) {
				fragmentTransaction.hide(fg);
			}
		}
		if (fragment.isAdded()) {
			fragmentTransaction.show(fragment);
		} else {
			fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
		}
		fragmentTransaction.commitAllowingStateLoss();
		fragmentManager.executePendingTransactions();
	}

	private Fragment instantFragment(int currIndex) {
		switch (currIndex) {
		case 0:
			return new HomeFragment();
		case 1:
			return new ChooseFragment();
		case 2:
			return new ScheduleFragment();
		case 3:
			return new MyFragment();
		default:
			return null;
		}
	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

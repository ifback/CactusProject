package com.xmm.cactus.module.main;

import java.util.ArrayList;
import java.util.Arrays;

import com.xmm.cactus.activity.R;
import com.xmm.cactus.base.BaseActivity;
import com.xmm.cactus.module.find.FindFragment;
import com.xmm.cactus.module.home.HomeFragment;
import com.xmm.cactus.module.media.MediaFragment;
import com.xmm.cactus.module.message.MessageFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

/**
 * 首页
 * 
 * @author DENG_MING_HAI
 * @date 2016年4月12日
 */
public class MainActivity extends BaseActivity {

	private static final String FRAGMENT_TAGS = "fragmentTags";
	private static final String CURR_INDEX = "currIndex";
	/** 侧边栏 */
	private DrawerLayout mainDrawerLayout;
	/** 主布局内容  */
	private LinearLayout lin_content;
	/** 点击容器 */
	private RadioGroup group;
	/** 碎片标签名 */
	private ArrayList<String> fragmentTags;
	/** 碎片管理者 */
	private FragmentManager fragmentManager;
	/** 碎片位置 */
	private static int currIndex = 0;


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		/** 保存碎片页面位置 */
		outState.putInt(CURR_INDEX, currIndex);
		outState.putStringArrayList(FRAGMENT_TAGS, fragmentTags);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		/** Activity 被系统销毁时 */
		initFromSavedInstantsState(savedInstanceState);
	}

	@Override
	protected void initCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		fragmentManager = getSupportFragmentManager();
		if (savedInstanceState == null) {
			currIndex = 0;
			fragmentTags = new ArrayList<String>(
					Arrays.asList("HomeFragment", "MediaFragment", "FindFragment", "MessageFragment"));
		} else {
			initFromSavedInstantsState(savedInstanceState);
		}
	}
	
	@Override
	protected void initVariables() {
		
	}

	@Override
	protected void initViews() {
		group = (RadioGroup) findViewById(R.id.group);
		lin_content = (LinearLayout) findViewById(R.id.lin_content);
		mainDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawerLayout);
		showFragment();
	}

	@Override
	protected void loadData() {
		
	}

	@Override
	protected void initEvent() {
		group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rdio_home:
					currIndex = 0;
					break;
				case R.id.rdio_media:
					currIndex = 1;
					break;
				case R.id.rdio_find:
					currIndex = 2;
					break;
				case R.id.rdio_message:
					currIndex = 3;
					break;
				default:
					break;
				}
				showFragment();
			}
		});
		mainDrawerLayout.setDrawerListener(new CustDrawListener() {
			@Override
			public void onDrawerSlide(View childView, float offerside) {
				super.onDrawerSlide(childView, offerside);
				float translationX = childView.getMeasuredWidth() * offerside;
				lin_content.setTranslationX(translationX);
			}
		});
	}

	/** Activity 被杀掉时 取出并显示碎片位置 */
	private void initFromSavedInstantsState(Bundle savedInstanceState) {
		currIndex = savedInstanceState.getInt(CURR_INDEX);
		fragmentTags = savedInstanceState.getStringArrayList(FRAGMENT_TAGS);
		showFragment();
	}

	private void showFragment() {
		/*
		 * if (currIndex == 3) {
		 * UIHelperUtils.showLogin(HomeFragmentActivity.this); 
		 * }
		 */
		if (fragmentManager != null && fragmentTags != null && fragmentTags.size() > 0) {
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
	}

	private Fragment instantFragment(int currIndex) {
		switch (currIndex) {
		case 0:
			return new HomeFragment();
		case 1:
			return new MediaFragment();
		case 2:
			return new FindFragment();
		case 3:
			return new MessageFragment();
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

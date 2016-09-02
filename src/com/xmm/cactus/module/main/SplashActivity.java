package com.xmm.cactus.module.main;

import com.xmm.cactus.activity.R;
import com.xmm.cactus.adapter.GalleryPagerAdapter;
import com.xmm.cactus.base.BaseActivity;
import com.xmm.cactus.ui.viewpagerindicator.CirclePageIndicator;
import com.xmm.cactus.util.ImageLocalUtils;
import com.xmm.cactus.util.SharedPreferencesUtils;
import com.xmm.cactus.util.UIHelperUtils;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 启动界面
 * 
 * @author DENG_MING_HAI
 * @date 2016年4月12日
 */
public class SplashActivity extends BaseActivity {

	/** 页面显示  */
	private ViewPager v_pager;
	/** 小圆点     */
	private CirclePageIndicator cir_indicator;
	/** 页面适配器     */
	private GalleryPagerAdapter mGalleryPagerAdapter;
	/** 跳转主体   */
	private Button btn_intoHome;
	private SharedPreferencesUtils mSharedPrefUtils;

	private Handler mHandler = new Handler();  
	private Runnable mRunnable = new Runnable() {
		
		@Override
		public void run() {	
			UIHelperUtils.showHome(SplashActivity.this);
			SplashActivity.this.finish();
		}
	};;
	  
	
	@Override
	protected void initCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_splash);
	}

	@Override
	protected void initVariables() {
		mSharedPrefUtils = SharedPreferencesUtils.getInstance();
	}

	@Override
	protected void initViews() {
		btn_intoHome = (Button) findViewById(R.id.btn_intoHome);
		btn_intoHome.setVisibility(View.GONE);
		if (mSharedPrefUtils.getBoolean("first_load", false)) {
			initLaunchLogo();
		} else {
			initGuideGallery();
		}
	}

	@Override
	protected void loadData() {
		
	}
	
	/**
	 * 启动加载默认Logo图片(已加载过)
	 */
	private void initLaunchLogo() {
		btn_intoHome.setVisibility(View.VISIBLE);
		ImageView iv_guideImage = (ImageView) findViewById(R.id.iv_guideImage);
		iv_guideImage.setVisibility(View.VISIBLE);
		mHandler.postDelayed(mRunnable, 3000); //3s执行  
	}

	/**
	 * 首次启动画廊图片(第一次加载)
	 */
	private void initGuideGallery() {
		final Animation fadeIn = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fadein);
		cir_indicator = (CirclePageIndicator) findViewById(R.id.cir_indicator);
		cir_indicator.setVisibility(View.VISIBLE);
		v_pager = (ViewPager) findViewById(R.id.v_pager);
		v_pager.setVisibility(View.VISIBLE);
		mGalleryPagerAdapter = new GalleryPagerAdapter(SplashActivity.this);
		v_pager.setAdapter(mGalleryPagerAdapter);
		cir_indicator.setViewPager(v_pager);
		cir_indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {
				if (index == ImageLocalUtils.images.length - 1) {
					btn_intoHome.setVisibility(View.VISIBLE);
					btn_intoHome.startAnimation(fadeIn);
				} else {
					btn_intoHome.setVisibility(View.GONE);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	protected void initEvent() {
		btn_intoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	mHandler.removeCallbacks(mRunnable);
            	if (!mSharedPrefUtils.getBoolean("first_load", false)) {
            		mSharedPrefUtils.putBoolean("first_load", true);
                }
                UIHelperUtils.showHome(SplashActivity.this);
            }
        });
	}
	
}

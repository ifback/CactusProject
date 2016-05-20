package com.xmm.cactus.adapter;

import com.xmm.cactus.util.ImageLocalUtils;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 引导展示适配				
 * @author  DENG_MING_HAI
 * @date 	2016年4月15日
 */
public class GalleryPagerAdapter extends PagerAdapter {
	
	private Context mContext;

	public GalleryPagerAdapter(Context context) {
		this.mContext = context;
	}
	
	@Override
	public int getCount() {
		return ImageLocalUtils.images.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView view=new ImageView(mContext);
		view.setScaleType(ScaleType.CENTER_CROP);
		view.setImageResource(ImageLocalUtils.images[position]);
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object view) {
		container.removeView((View) view);
	}
}

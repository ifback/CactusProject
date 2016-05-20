package com.xmm.cactus.fragment;

import com.xmm.cactus.activity.R;
import com.xmm.cactus.ui.pulltozoomview.PullToZoomScrollViewEx;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 首页				
 * @author  DENG_MING_HAI
 * @date 	2016年4月18日
 */
@SuppressLint("NewApi")
public class MyFragment extends Fragment {

    private PullToZoomScrollViewEx pullZoomScrollV;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_fragment, container ,false);
		pullZoomScrollV = (PullToZoomScrollViewEx) view.findViewById(R.id.pullZoomScrollV);
		return view;
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
	}
	
	
	public void initView(){
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.my_head_view, null, false);
        View zoomView = LayoutInflater.from(getActivity()).inflate(R.layout.my_zoom_bg_view, null, false);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.my_content_view, null, false);

        pullZoomScrollV.setAddHeaderView(headView);
        pullZoomScrollV.setAddZoomView(zoomView);
        pullZoomScrollV.setScrollContentView(contentView);
        //由于不能自适应高度(xml里无法设置)   所以在这设置固定高度
		LinearLayout.LayoutParams chipLayoutParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 400);
        pullZoomScrollV.setHeaderLayoutParams(chipLayoutParams);
	}

}

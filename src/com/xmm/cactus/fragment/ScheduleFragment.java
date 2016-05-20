package com.xmm.cactus.fragment;

import com.xmm.cactus.activity.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 首页				
 * @author  DENG_MING_HAI
 * @date 	2016年4月18日
 */
@SuppressLint("NewApi")
public class ScheduleFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.schedule_fragment, container ,false);
		return view;
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
	}

}

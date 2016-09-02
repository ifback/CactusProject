package com.xmm.cactus.module.my;

import com.xmm.cactus.activity.R;
import com.xmm.cactus.base.BaseFragment;
import com.xmm.cactus.ui.pulltozoomview.PullToZoomScrollViewEx;
import com.xmm.cactus.util.UIHelperUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
/**
 * 个人中心				
 * @author  DENG_MING_HAI
 * @date 	2016年8月30日
 */
public class PersonalCenterFragment extends BaseFragment {

    private PullToZoomScrollViewEx pullZoomScrollV;
    private View headView,contentView;
    private TextView tv_register,tv_login;
    
	@Override
	protected int getLayoutId() {
		return R.layout.fragment_personal_center;
	}

	@Override
	protected void initVariables() {
		
	}

	@Override
	protected void initViews(View view) {
		pullZoomScrollV = (PullToZoomScrollViewEx) view.findViewById(R.id.pullZoomScrollV);
		headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_my_head, null, false);
		tv_register = (TextView) headView.findViewById(R.id.tv_register);
		tv_login = (TextView) headView.findViewById(R.id.tv_login);
		View zoomView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_my_zoom_bg, null, false);
        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_my_content_view, null, false);

        pullZoomScrollV.setAddHeaderView(headView);
        pullZoomScrollV.setAddZoomView(zoomView);
        pullZoomScrollV.setScrollContentView(contentView);
        //由于不能自适应高度(xml里无法设置)   所以在这设置固定高度
		LinearLayout.LayoutParams chipLayoutParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 400);
        pullZoomScrollV.setHeaderLayoutParams(chipLayoutParams);
	}

	@Override
	protected void loadData() {
		
	}

	@Override
	protected void initEvent() {
		tv_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		tv_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelperUtils.showLogin(getActivity());
			}
		});
	}
}